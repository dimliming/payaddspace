package com.payadd.framework.common.extension;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.payadd.framework.common.exception.SystemException;

public class ExtensionManager<T> {
	private static ConcurrentMap<Class<?>, ExtensionManager<?>> EXTENSION_MANAGER = new ConcurrentHashMap<Class<?>, ExtensionManager<?>>();
//	private static ConcurrentMap<Class<?>, Class<Adaptor>> ADAPTOR_TYPE = new ConcurrentHashMap<Class<?>, Class<Adaptor>>();
	private static ConcurrentMap<Class<?>, Map<String,Class<?>>> ROUTER_TYPE= null;
	private static ConcurrentMap<Class<?>, Class<?>> EXTENSION_POINT = null;
	
	
	private Class<T> type;
	private ServiceLoader<T> loader;
	//private ConcurrentMap<String, Class<T>> cachedExtensions = new ConcurrentHashMap<String, Class<T>>();
	//private ConcurrentMap<String, Object> cachedExtInstances = new ConcurrentHashMap<String, Object>();
	//private ConcurrentMap<Class<T>, T> instanceTypeMap = new ConcurrentHashMap<Class<T>, T>();
	private ConcurrentMap<String,Extension<T>> extensionMap = new ConcurrentHashMap<String, Extension<T>>();
	private Map<String,T> extensionInstanceMap = new HashMap<String, T>();
	
	//private ConcurrentMap<String,T> extensionInstanceMap = new ConcurrentHashMap<String, T>();
	
	private Map<String, Class<?>> extensionRouterTypes = null;
	//private ConcurrentMap<String, Object> routerMap = new ConcurrentHashMap<String, Object>();
	private ConcurrentMap<String, Class<T>> adaptorTypes = new ConcurrentHashMap<String, Class<T>>();
	private ConcurrentMap<String, T> adaptorInstances = new ConcurrentHashMap<String, T>();
	
	
	public static <T> ExtensionManager<T> getInstance(Class<T> type){
		ExtensionManager<T> extManager = (ExtensionManager<T>)EXTENSION_MANAGER.get(type);
		if (extManager!=null){
			return extManager;
		}else{
			extManager = new ExtensionManager<T>(type);
			EXTENSION_MANAGER.put(type, extManager);
			return extManager;
		}
	}
	private ExtensionManager(Class<T> type){
		this.type = type;
		this.loader = ServiceLoader.load(type);
		resolve();
	}
	
	private void resolve(){
		Iterator<T> it = loader.iterator();
		while(it.hasNext()){
			T ext = it.next();
			Class<T> extClass = (Class<T>)ext.getClass();
			resolveExtType(extClass);
		}
		
		//查找是所有adaptorTypes和adaptorInstances,并从从extension中剔除adaptor的实现类
		findAdaptorTypes();
		
		//实例化所有扩展类
		Iterator<String> extIt = extensionMap.keySet().iterator();
		while(extIt.hasNext()){
			String code = extIt.next();
			Extension<T> ext = extensionMap.get(code);
			T instance = createInstance(ext.getType(),false);
			ext.setInstance(instance);
			//extensionInstanceMap.put(code, instance);
			extensionInstanceMap.put(code, instance);
		}
	}
	private void resolveExtType(Class<T> extClass){
		//resolve extension code
		ExtensionDescription desc = resolveExtensionDesc(extClass);
		String code = desc.code();
		//存在同名的扩展，需要抛出异常
		if (this.extensionMap.containsKey(code)){
			throw new RuntimeException("Extension name has exist:"+code);
		}

		//resolve extension purpose
		Extension<T> extension = new Extension<T>(extClass);
		extension.setName(desc.name());
		extension.setCode(code);
		this.extensionMap.put(code, extension);
		
	}
	private void  findAdaptorTypes(){
		//查找所有extension class，如果存在一个参数为Map<T>的构造函数,那么这是一个适配者类
		Iterator<String> it = this.extensionMap.keySet().iterator();
		while (it.hasNext()){
			String code = it.next();
			Extension<T> ext = this.extensionMap.get(code);
			Class<?>[] parameterType = new Class<?>[1];
			parameterType[0] = Map.class;
			Class<T> extType = ext.getType();
			Constructor<T> constructor = null;
			try {
				constructor = extType.getConstructor(parameterType);
				//TODO:获取constructor的参数的泛型类型
			} catch (NoSuchMethodException e) {
				continue;
			} catch (SecurityException e) {
				e.printStackTrace();
				continue;
			}
			if (constructor!=null){
				//存在参数为Map<T>的构造函数，说明这是一个适配者类
				adaptorTypes.put(extType.getName(), extType);//手工实现的适配者类，以类名作为code
				
				//将这个adaptor class从extension列表中剔除
				this.extensionMap.remove(code);
			}
		}
		//如果存在一个实现了Router接口，并且适配类为当前扩展接口的类，那么可以通过这个类动态生成一个适配者类
		if (ROUTER_TYPE==null){
			findAllRouterTypes();
		}
		//查找所有属于本extension的router type
		if (extensionRouterTypes==null){
			findExtensionRouterTypes();
		}
		
		if (extensionRouterTypes!=null){
			Iterator<String> routerCodeIt = extensionRouterTypes.keySet().iterator();
			
			while (routerCodeIt.hasNext()){
				String routerCode = routerCodeIt.next();
				Class<?> routerType = extensionRouterTypes.get(routerCode);
				Class<T> adaptorType = AdaptorTypeGenerator.generator(type, routerType);
				adaptorTypes.put(routerCode, adaptorType);//通过router动态生成的适配者，以router的code作为code
			}
		}
	}
	
	private ExtensionDescription resolveExtensionDesc(Class<T> extClass){
		ExtensionDescription extDesc = extClass.getAnnotation(ExtensionDescription.class);
		if (extDesc==null){
			throw new RuntimeException("Extension class "+extClass.getName()+" must define annonation \"Extension\"");
		}
		return extDesc;
	}
	
	public T getExtension(String name){
		Extension<T> ext  = extensionMap.get(name);
		if (ext==null){
			//throw exception
			throw new SystemException("Doesn't exist this extension :"+name);
		}
		if (ext.getInstance()==null){
			T instance = createInstance(ext.getType(),false);
			ext.setInstance(instance);
		}
		return ext.getInstance();
	}
	public T getExtension(){
		int extensionNum = extensionMap.size();
		if (extensionNum==0){//该扩展接口没有实现类，那么抛异常
			//TODO:throw exception
			throw new SystemException("No implements");
		}else if (extensionNum==1){//该接口只有一个实现类，那么直接返回这个实现类的实例
			Extension<T> ext = extensionMap.values().iterator().next();
			T instance = getExtension(ext.getCode());
			return instance;
		}else{//如果有多个实现类，那么返回这个扩展接口的适配者
			T adaptorInstance = getExtensionAdaptor();
			
			return adaptorInstance;
		}
	}
	public T getExtensionAdaptor(String routerName){
		//get adaptor by name
		T instance = adaptorInstances.get(routerName);
		if (instance==null){
			Class<T> type = adaptorTypes.get(routerName);
			if (type==null){
				//TODO:throw exception
				throw new SystemException("can't find adaptor "+routerName);
			}
			instance  = createInstance(type, true);
			adaptorInstances.put(routerName, instance);
		}
		return instance;
	}
	public T getExtensionAdaptor(){
		//get single adaptor
		if (adaptorTypes.size()==1){
			String routeName = adaptorTypes.keySet().iterator().next();
			T instance = getExtensionAdaptor(routeName);
			return instance;
		}
		//TODO:throw exception
		throw new SystemException("no default adaptor");
	}
	
	private T createInstance(Class<T> type,boolean isAdaptor){
		//simple IOC
		T instance = null;
		try {
			if (isAdaptor){
				Constructor<T> constructor = type.getConstructor(new Class<?>[]{Map.class});
				instance = constructor.newInstance(new Object[]{extensionInstanceMap});
			}else{
				instance = type.newInstance();
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			throw new SystemException(e);
		} catch (SecurityException e) {
			e.printStackTrace();
			throw new SystemException(e);
		} catch (InstantiationException e) {
			e.printStackTrace();
			throw new SystemException(e);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new SystemException(e);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new SystemException(e);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			throw new SystemException(e);
		}
		
		if (EXTENSION_POINT==null){
			findAllExtensionPoints();
		}
		
		Field[] fields = type.getDeclaredFields();
		for (int i=0;i<fields.length;i++){
			Field field = fields[i];
			Class<?>[] implInterfaces = null;
			Class<?> fieldType = field.getType();
			if (fieldType.isInterface()){
				implInterfaces = new Class<?>[]{field.getType()};
			}else{
				implInterfaces = field.getType().getInterfaces();
			}
			if (implInterfaces.length==0)continue;//不是扩展点的例子，无需实例化
			
			Concrete concrete = field.getAnnotation(Concrete.class);
			Router router = field.getAnnotation(Router.class);
			for (Class<?> implInt:implInterfaces){
				if (EXTENSION_POINT.containsKey(implInt)){
					Object fieldInstance = null;
					if (concrete!=null){
						fieldInstance = ExtensionManager.getInstance(implInt).getExtension(concrete.value());
					}else if (router!=null){
						fieldInstance = ExtensionManager.getInstance(implInt).getExtensionAdaptor(router.value());
					}else{
						fieldInstance = ExtensionManager.getInstance(implInt).getExtension();
					}
					try {
						field.setAccessible(true);
						field.set(instance, fieldInstance);
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
						throw new SystemException(e);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
						throw new SystemException(e);
					}
					
					break;
				}
			}
		}
		
		return instance;
	}
	private static void findAllExtensionPoints(){
		EXTENSION_POINT = new ConcurrentHashMap<Class<?>, Class<?>>();
		//查找所有注解为Multiple的interface
		List<Class<?>> list = AnnotationScanner.scan(Multiple.class);
		Iterator<Class<?>> it = list.iterator();
		while (it.hasNext()){
			Class<?> point = it.next();
			System.out.println("Find extension point:"+point.getName());
			EXTENSION_POINT.put(point, point);
		}
	}
	private void findAllRouterTypes(){
		//查找所有注解为Route的类
		List<Class<?>> routeList = AnnotationScanner.scan(Route.class);
		
		ROUTER_TYPE = new ConcurrentHashMap<Class<?>, Map<String,Class<?>>>();
		Iterator<Class<?>> it = routeList.iterator();
		while (it.hasNext()){
			Class<?> routeType = it.next();
			Route route = routeType.getAnnotation(Route.class);
			if (route==null)continue;
			
			Class<?> extensionPoint = route.target();
			String name = route.name();
			String code = route.code();
			Map<String,Class<?>> extenionRouterMap = ROUTER_TYPE.get(extensionPoint);
			if (extenionRouterMap==null){
				extenionRouterMap = new HashMap<String, Class<?>>();
				ROUTER_TYPE.put(extensionPoint, extenionRouterMap);
			}
			if (extenionRouterMap.containsKey(code)){//存在同名的router，抛异常
				throw new SystemException("Duplicate Router code: "+code);
			}
			extenionRouterMap.put(code, routeType);
			
		}
	}
	
	private void findExtensionRouterTypes(){
		//查找所有属于本extension的router type,查找方法为查找所有类的Route注解，如果其target为本extension point，那么即为本extension的router
		if (ROUTER_TYPE==null){
			findAllRouterTypes();
		}
		extensionRouterTypes = (Map<String,Class<?>>)ROUTER_TYPE.get(this.type);
		
	}
	
	public static boolean isExtensionPoint(Class type){
		if (EXTENSION_POINT==null){
			findAllExtensionPoints();
		}
		return EXTENSION_POINT.containsKey(type);
	}
	
	
}
