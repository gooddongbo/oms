package com.hqyg.dubbo.custom.access.aop;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.aspectj.lang.*;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.hqyg.dubbo.custom.access.annotion.LimitAccess;
import com.hqyg.dubbo.custom.access.utils.LimitAccessCount;

@Aspect
@Component
public class LimitAccessInterceptor {

    //@Around("* com.yangxin.core.service.*.*.*(..)")
    @Pointcut("execution(* com.hqyg.dubbo.Provider.service.impl.*.*(..))")
    private void pointcut(){ }

//    @Before("pointcut()")
    public void before(JoinPoint joinPoint) {
    	//获取封装了署名信息的对象,在该对象中可以获取到目标方法名,所属类的Class等信息
        System.out.println("[Aspect1] before advise");
    }
    
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint ){
    	Method method = getMethod(joinPoint);
    	Annotation[] annotations = method.getAnnotations();
    	Object result = new Object();
    	for( int length = annotations.length, i=0; i<length; i++){
    		Annotation ta = annotations[i];
    		if(ta instanceof LimitAccess){
    			
    			int limit = ((LimitAccess) ta).limit();
    			int realCount = LimitAccessCount.getInstance().getAccessCount(method.getClass().getName());
    			if(realCount < limit){
    				try {
    					LimitAccessCount.getInstance().incrementCount(method.getClass().getName());
    					result = joinPoint.proceed();
    					LimitAccessCount.getInstance().decrementCount(method.getClass().getName());
					} catch (Throwable e) {
						e.printStackTrace();
					}
    			}else{
    				System.out.println("过滤一次请求");
    				return null;
    			}
    		}
    	}
    	return result;
    }
    
    private Method getMethod(ProceedingJoinPoint joinPoint){
    	//获取封装了署名信息的对象,在该对象中可以获取到目标方法名,所属类的Class等信息
    	MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
    	//被代理的对象
    	Object target = joinPoint.getTarget();
        Method method = null;
		try {
			method = target.getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
    	return method;
    }
}
