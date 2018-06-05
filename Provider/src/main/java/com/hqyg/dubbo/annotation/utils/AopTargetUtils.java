package com.hqyg.dubbo.annotation.utils;

import org.springframework.aop.support.*;
import java.lang.reflect.*;

import org.springframework.aop.framework.*;

public class AopTargetUtils
{
    public static Object getTarget(final Object proxy) throws Exception {
        if (!AopUtils.isAopProxy(proxy)) {
            return proxy;
        }
        if (AopUtils.isJdkDynamicProxy(proxy)) {
            return getJdkDynamicProxyTargetObject(proxy);
        }
        return getCglibProxyTargetObject(proxy);
    }
    
    private static Object getCglibProxyTargetObject(final Object proxy) throws Exception {
    	//getDeclaredField方法是获取对象的所有字段，包括public，private protected,但是不包括继承字段
        final Field h = proxy.getClass().getDeclaredField("CGLIB$CALLBACK_0");
        h.setAccessible(true);
        final Object dynamicAdvisedInterceptor = h.get(proxy);
        final Field advised = dynamicAdvisedInterceptor.getClass().getDeclaredField("advised");
        advised.setAccessible(true);
        final Object target = ((AdvisedSupport)advised.get(dynamicAdvisedInterceptor)).getTargetSource().getTarget();
        return target;
    }
    
    private static Object getJdkDynamicProxyTargetObject(final Object proxy) throws Exception {
        final Field h = proxy.getClass().getSuperclass().getDeclaredField("h");
        h.setAccessible(true);
        final AopProxy aopProxy = (AopProxy)h.get(proxy);
        final Field advised = aopProxy.getClass().getDeclaredField("advised");
        advised.setAccessible(true);
        final Object target = ((AdvisedSupport)advised.get(aopProxy)).getTargetSource().getTarget();
        return target;
    }
}
