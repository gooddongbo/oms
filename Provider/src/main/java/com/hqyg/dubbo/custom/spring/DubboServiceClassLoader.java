package com.hqyg.dubbo.custom.spring;

import com.hqyg.dubbo.annotation.SoaService;
import com.hqyg.dubbo.annotation.core.SoaDubboFactory;
import com.hqyg.dubbo.custom.util.BeanUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.List;

public class DubboServiceClassLoader{

	private ApplicationContext applicationContext;
	
	private String packageName;
	
	public void onApplicationEvent(ContextRefreshedEvent event) {
		applicationContext = event.getApplicationContext();
		try {
			final List<Class> classList = BeanUtils.getClass(packageName);
			for(final Class clazz : classList){
				//注册服务提供者
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void publishSoaService(Class clazz){
		//获取clazz标注的注解
		Annotation[] annotations = clazz.getAnnotations();
		if(annotations == null || annotations.length <= 0){
			return ;
		}
		for(Annotation annotation : annotations){
			if(annotation instanceof SoaService){
				 Class[] clazzIntergaces = clazz.getInterfaces();
				 Object obj = applicationContext.getBean(clazzIntergaces[0]);
				 for(int j=0;j<clazzIntergaces.length; j++){
					String version = ((SoaService) annotation).value();
					String group = ((SoaService) annotation).group();
					 //dubbo服务注册
					 SoaDubboFactory.registryService(group, clazzIntergaces[j], obj, version);
				 }

			}
		}
	}
}
