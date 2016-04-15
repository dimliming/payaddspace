package com.payadd.framework.common.extension;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;

import com.payadd.framework.common.exception.SystemException;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.LoaderClassPath;
import javassist.NotFoundException;

public class AdaptorTypeGenerator {
	public static <T> Class<T> generator(Class<T> adapteeType,Class<?> routerType){
		String adapteeTypeName = adapteeType.getName();
		String routerTypeName = routerType.getSimpleName();
		String adaptorTypeName = adapteeTypeName+"__"+routerTypeName;
		
		ClassPool pool = new ClassPool(true);
		pool.insertClassPath(new LoaderClassPath(Thread.currentThread().getContextClassLoader()));
		
		CtClass adaptorType = null;
		try {
			CtClass superClass = pool.get(Object.class.getName());
			adaptorType = pool.makeClass(adaptorTypeName, superClass);
			adaptorType.addInterface(pool.get(adapteeType.getName()));
			
			//创建一个router属性
			adaptorType.addField(CtField.make("private "+routerType.getName()+" router = new "+routerType.getName()+"();", adaptorType));
			//创建一个map属性，用来保存本扩展点的所有实现子类
			adaptorType.addField(CtField.make("private java.util.Map extensionMap;", adaptorType));
			
			//创建一个带有Map参数的构造函数
			CtClass mapClass = pool.get(Map.class.getName());
			CtConstructor constructor = new CtConstructor(new CtClass[]{mapClass},adaptorType);
			constructor.setModifiers(Modifier.PUBLIC);
			StringBuffer constructorBody = new StringBuffer();
			constructorBody.append("{");
			constructorBody.append("this.extensionMap = $1;");
			constructorBody.append("}");
			constructor.setBody(constructorBody.toString());
			adaptorType.addConstructor(constructor);
			
			//针对接口的每一个函数，生成一个带路由的方法
			Method[] methods = adapteeType.getMethods();
			for (Method method:methods){
				Class<?>[] paramTypes = method.getParameterTypes();
				String methodName = method.getName();
				boolean hasReturn = !method.getReturnType().equals(void.class);
				StringBuffer methodCode = new StringBuffer();
				
				methodCode.append("public ");
				if (hasReturn){
					methodCode.append(" ");
					methodCode.append(method.getReturnType().getName());
					methodCode.append(" ");
				}else{
					methodCode.append(" void ");
				}
				methodCode.append(methodName);
				methodCode.append("(");
				for (int i=0;i<paramTypes.length;i++){
					Class<?> paramType = paramTypes[i];
					if (i>0){
						methodCode.append(",");
					}
					methodCode.append(paramType.getName());
					methodCode.append(" arg"+i);
				}
				methodCode.append("){");
				methodCode.append("String code = this.router."+methodName+"(");
				for (int i=0;i<paramTypes.length;i++){
					Class<?> paramType = paramTypes[i];
					if (i>0){
						methodCode.append(",");
					}
					methodCode.append(" arg"+i);
				}
				methodCode.append(");");
				methodCode.append(adapteeType.getName()+" instance = ("+adapteeType.getName()+")this.extensionMap.get(code);");
				methodCode.append("if (instance==null) throw new "+SystemException.class.getName()+"(\"can't find extension:\"+code);");
				if (hasReturn){
					methodCode.append(method.getReturnType().getName()+" result = instance."+methodName+"(");
				}else{
					methodCode.append(" instance."+methodName+"(");
				}
				
				for (int i=0;i<paramTypes.length;i++){
					Class<?> paramType = paramTypes[i];
					if (i>0){
						methodCode.append(",");
					}
					methodCode.append(" arg"+i);
				}
				methodCode.append(");");
				if (hasReturn){
					methodCode.append("return result;");
				}
				methodCode.append("}");
				CtMethod ctMethod = CtNewMethod.make(methodCode.toString(), adaptorType);
				adaptorType.addMethod(ctMethod);
				
			}
			System.out.println(adaptorType.toString());
			Class<T> newClass = (Class<T>)adaptorType.toClass();
			return newClass;
		} catch (NotFoundException e) {
			e.printStackTrace();
			throw new SystemException(e);
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw new SystemException(e);
		} catch (CannotCompileException e) {
			e.printStackTrace();
			throw new SystemException(e);
		}
	}
	
	
	public static void main(String[] args){
		System.out.println(String.class.getName());
		System.out.println(String.class.getCanonicalName());
		System.out.println(String.class.getSimpleName());
		
		Method[] methods = AdaptorTypeGenerator.class.getMethods();
		for (Method method:methods){
			System.out.println("method name:"+method.getName());
			System.out.println("method return type:"+method.getReturnType().getName());
			System.out.println("is equal:"+method.getReturnType().equals(void.class));
			System.out.println("============");
			
		}
		Class<?>[] inters = AdaptorTypeGenerator.class.getInterfaces();
		System.out.println("interface:"+inters.length);
	}
}
