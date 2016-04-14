package com.payadd.framework.common.spring;

import java.lang.reflect.Field;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import com.payadd.framework.common.exception.SystemException;
import com.payadd.framework.common.extension.Concrete;
import com.payadd.framework.common.extension.ExtensionManager;
import com.payadd.framework.common.extension.Router;

public class ExtensionBeanPostProcessor implements BeanPostProcessor {

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("init bean "+beanName);
		
		Field[] fields = bean.getClass().getDeclaredFields();
		for (int i=0;i<fields.length;i++){
			Field field = fields[i];
			Class<?>[] implInterfaces = field.getType().getInterfaces();
			if (implInterfaces.length==0)continue;//不是扩展点的例子，无需实例化
			
			Concrete concrete = field.getAnnotation(Concrete.class);
			Router router = field.getAnnotation(Router.class);
			for (Class<?> implInt:implInterfaces){
				if (ExtensionManager.isExtensionPoint(implInt)){
					Object fieldInstance = null;
					if (concrete!=null){
						fieldInstance = ExtensionManager.getInstance(implInt).getExtension(concrete.value());
					}else if (router!=null){
						fieldInstance = ExtensionManager.getInstance(implInt).getExtensionAdaptor(router.value());
					}else{
						fieldInstance = ExtensionManager.getInstance(implInt).getExtension();
					}
					try {
						field.set(bean, fieldInstance);
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
		return bean;
	}

}
