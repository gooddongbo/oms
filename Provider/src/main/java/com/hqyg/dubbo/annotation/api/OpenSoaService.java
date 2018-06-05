package com.hqyg.dubbo.annotation.api;

import org.slf4j.*;
import com.alibaba.dubbo.common.utils.*;
import com.alibaba.dubbo.rpc.*;
import com.hqyg.dubbo.annotation.core.SoaDubboFactory;
import com.hqyg.dubbo.annotation.utils.JsonUtil;

import java.lang.reflect.*;
import java.io.*;

public class OpenSoaService
{
    private Logger logger;
    private String service;
    private String method;
    private String domain;
    private Object data;
    
    public OpenSoaService() {
        this.logger = LoggerFactory.getLogger((Class)OpenSoaService.class);
    }
    
    public String invokeRpcService() {
        final StringBuilder serviceEx = new StringBuilder();
        try {
            try {
                Class<?> serviceClass;
                try {
                    serviceClass = Class.forName("com.globalegrow.spi." + this.getDomain() + "." + this.getService());
                }
                catch (ClassNotFoundException e1) {
                    serviceClass = Class.forName("com.globalegrow." + this.getDomain() + ".spi." + this.getService());
                }
                Method method = this.getMethod(serviceClass, this.getMethod());
                if (method == null) {
                    serviceClass = Class.forName("com.globalegrow." + this.getDomain() + ".spi." + this.getService());
                    method = this.getMethod(serviceClass, this.getMethod());
                    if (method == null) {
                        serviceEx.append("Exception:can`t find method for ").append(this.getService()).append(":").append(this.getMethod());
                        return serviceEx.toString();
                    }
                }
                return this.findAndInvokeRpcCall(serviceClass, method);
            }
            catch (IllegalStateException | RpcException ex8) {
                final RuntimeException ex7 = null;
                final RuntimeException ex = ex7;
                final String exMessage = this.parseExMessage(ex);
                if (exMessage.contains("No provider")) {
                    serviceEx.append("Exception No provider for ").append(this.getService()).append(":").append(this.getMethod());
                }
                else {
                    serviceEx.append("Exception provider un normal for ").append(this.getService()).append(":").append(this.getMethod());
                }
                if (StringUtils.isNotEmpty(serviceEx.toString())) {
                    return serviceEx.toString();
                }
                return serviceEx.toString();
            }
        }
        catch (IllegalAccessException ex2) {
            serviceEx.append("IllegalAccessException:").append(ex2.getMessage());
            this.logger.error("IllegalAccessException:", (Throwable)ex2);
        }
        catch (InvocationTargetException ex3) {
            final String exMessage = this.parseExMessage(ex3);
            if (exMessage.contains("No provider")) {
                serviceEx.append("Exception No provider for ").append(this.getService()).append(":").append(this.getMethod());
            }
            else if (exMessage.contains("Forbid consumer")) {
                serviceEx.append("Exception Forbid consumer for ").append(this.getService()).append(":").append(this.getMethod());
            }
            else {
                serviceEx.append("InvocationTargetException:").append(ex3.getMessage());
                this.logger.error("InvocationTargetException:{}", (Throwable)ex3);
            }
        }
        catch (ClassNotFoundException ex6) {
            serviceEx.append("ClassNotFoundException for ").append(this.getService()).append(":").append(this.getMethod());
            return serviceEx.toString();
        }
        catch (IllegalArgumentException ex4) {
            serviceEx.append("IllegalArgumentException:").append(ex4.getMessage());
            this.logger.error("IllegalArgumentException:", (Throwable)ex4);
        }
        catch (Exception ex5) {
            serviceEx.append("unKnow system Exception: ").append(ex5.getMessage());
            this.logger.error("unKnow system Exception:", (Throwable)ex5);
        }
        return serviceEx.toString();
    }
    
    private String findAndInvokeRpcCall(final Class<?> serviceClass, final Method method) throws ClassNotFoundException, IllegalAccessException, InvocationTargetException {
        final Class[] parameterTypes = method.getParameterTypes();
        Object[] paramObj = null;
        if (parameterTypes != null && parameterTypes.length > 0) {
            final Object obj = JsonUtil.fromJson(this.getData().toString(), (Class<Object>)parameterTypes[0]);
            paramObj = new Object[] { obj };
        }
        final Object rpcService = SoaDubboFactory.getDubboService(serviceClass);
        final Object result = method.invoke(rpcService, paramObj);
        return JsonUtil.toJsonFormat(result);
    }
    
    private Method getMethod(final Class c, final String methodName1) {
        final Method[] methods = c.getMethods();
        Method[] array;
        for (int length = (array = methods).length, i = 0; i < length; ++i) {
            final Method method = array[i];
            if (methodName1.equals(method.getName())) {
                return method;
            }
        }
        return null;
    }
    
    public String getService() {
        return this.service;
    }
    
    public void setService(final String service) {
        this.service = service;
    }
    
    public String getMethod() {
        return this.method;
    }
    
    public void setMethod(final String method) {
        this.method = method;
    }
    
    public Object getData() {
        return this.data;
    }
    
    public void setData(final Object data) {
        this.data = data;
    }
    
    public String getDomain() {
        return this.domain;
    }
    
    public void setDomain(final String domain) {
        this.domain = domain;
    }
    
    private String parseExMessage(final Throwable result) {
        final StringWriter writer = new StringWriter();
        result.printStackTrace(new PrintWriter(writer, true));
        return writer.toString();
    }
}
