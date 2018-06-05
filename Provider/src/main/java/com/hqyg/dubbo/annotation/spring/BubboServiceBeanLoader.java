package com.hqyg.dubbo.annotation.spring;

import com.hqyg.dubbo.annotation.SoaConsumer;
import com.hqyg.dubbo.annotation.SoaService;
import com.hqyg.dubbo.annotation.core.SoaDubboFactory;
import com.hqyg.dubbo.annotation.utils.AopTargetUtils;
import com.hqyg.dubbo.annotation.utils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
//
public class BubboServiceBeanLoader implements ApplicationListener<ContextRefreshedEvent> {

    private Logger logger;
    private ApplicationContext applicationContext;
    //需要扫描的包路径
    private String packagePath;
    private String dubboZkPort;
    private String dubboZkAddress;
    
    public BubboServiceBeanLoader() {
        this.logger = (Logger) LoggerFactory.getLogger((Class)this.getClass());
//        this.packagePath = "com.hqyg.dubbo.Provider.service.impl";
//        this.dubboZkPort = "20880";
//        this.dubboZkAddress = "zookeeper://39.108.235.200:2182";
    }
	/**
	 * 注册 spring启动完成事件,spring启动完成之后，执行事件
	 * */
	//@override
	public void onApplicationEvent(ContextRefreshedEvent refreshedEvent) {
		//获取spring上下文
        this.applicationContext = refreshedEvent.getApplicationContext();
        try {
        	//获取工程项目下的所有class
            final List<Class> classList = BeanUtils.getClasses(this.packagePath);
            for (final Class clazz : classList) {
            	//扫描SoaService
                this.publishSoaService(clazz);
                //扫描SoaConsumer
                this.injectSoaService(clazz);
            }
        }
        catch (Exception e) {
            ((org.slf4j.Logger) this.logger).error("publish error", (Throwable)e);
        }
	}

	 private void injectSoaService(final Class clazz) throws Exception {
		 	//获取被soaConsumer注解标注的类字段
		 //getDeclaredFields方法是获取对象的所有字段，包括public，private protected,但是不包括继承字段
	        final Field[] fields = clazz.getDeclaredFields();
	        Field[] array;
	        for (int length = (array = fields).length, i = 0; i < length; ++i) {
	            final Field field = array[i];
	            //获取field上的注解
	            final Annotation[] fieldAnnotations = field.getAnnotations();
	            Annotation[] array2;
	            for (int length2 = (array2 = fieldAnnotations).length, j = 0; j < length2; ++j) {
	                final Annotation fieldAnnotation = array2[j];
	                //对soaConsumer注解标注的field进行处理
	                if (fieldAnnotation instanceof SoaConsumer) {
	                	//从spring上下文中获取field的实现类
	                    Object obj = this.applicationContext.getBean(clazz);
	                    if (obj != null) {
	                    	//如果obj是spring代理类
	                        if (AopUtils.isAopProxy(obj)) {
	                            obj = AopTargetUtils.getTarget(obj);
	                        }
	                        final SoaConsumer consumer = (SoaConsumer)fieldAnnotation;
	                        final String version = consumer.value();
	                        final String group = consumer.group();
	                        final Object soaConsumer = SoaDubboFactory.getDubboService(group, field.getType(), version);
	                        field.setAccessible(true);
	                        field.set(obj, soaConsumer);
	                    }
	                }
	            }
	        }
	    }
	    
	 /**
	  * @param clazz 只有被soaService注解标注的类，才会被处理
	  * */
    public void publishSoaService(final Class<?> clazz) {
    	//获取类上面的注解
        final Annotation[] annotations = clazz.getAnnotations();
        if (annotations == null || annotations.length <= 0) {
            return;
        }
        Annotation[] array;
        for (int length = (array = annotations).length, i = 0; i < length; ++i) {
            final Annotation annotation = array[i];
            if (annotation instanceof SoaService) {
            	//获取被soaService注解标注的类的所有接口
                final Class[] clazzInterfaces = clazz.getInterfaces();
                //获取被扫描类的唯一实现类
                final Object obj = this.applicationContext.getBean(clazzInterfaces[0]);
                if (obj != null) {
                	//初始化soaService注解类
                    final SoaService soaService = (SoaService)annotation;
                    //获取用户自定义注解的version
                    final String version = soaService.value();
                    //获取用户自定义注解的group
                    final String group = soaService.group();
                    Class[] array2;
                    for (int length2 = (array2 = clazzInterfaces).length, j = 0; j < length2; ++j) {
                        final Class cls = array2[j];
                        //dubbo服务注册
                        SoaDubboFactory.registryService(group, cls, obj, version);
                    }
                    break;
                }
            }
        }
    }
	public String getPackagePath() {
		return packagePath;
	}
	public void setPackagePath(String packagePath) {
		this.packagePath = packagePath;
	}
	public String getDubboZkPort() {
		return dubboZkPort;
	}
	public void setDubboZkPort(String dubboZkPort) {
		this.dubboZkPort = dubboZkPort;
	}
	public String getDubboZkAddress() {
		return dubboZkAddress;
	}
	public void setDubboZkAddress(String dubboZkAddress) {
		this.dubboZkAddress = dubboZkAddress;
	}
	    
}
