package com.hqyg.dubbo.annotation.core;

import com.alibaba.dubbo.config.*;
import org.slf4j.*;
import java.util.concurrent.*;
import org.apache.commons.lang3.*;

public class ServiceConfigHolder
{
    private Logger logger;
    private ConcurrentMap<String, ServiceConfig<?>> serviceConfigCache;
    
    public ServiceConfigHolder() {
        this.logger = LoggerFactory.getLogger((Class)ServiceConfigHolder.class);
        this.serviceConfigCache = new ConcurrentHashMap<String, ServiceConfig<?>>();
    }
    
    public static ServiceConfigHolder configHolder() {
        return Holder.holder;
    }
    
    public void export(final ServiceConfig<?> serviceConfig) {
    	//组装注册到dubbo上面的类的唯一key：接口路径+版本号
        final String key = this.generatorKey(serviceConfig);
        final ServiceConfig<?> cacheServiceConfig = (ServiceConfig<?>)this.serviceConfigCache.get(key);
        if (cacheServiceConfig != null) {
            cacheServiceConfig.export();
            return;
        }
        this.serviceConfigCache.putIfAbsent(key, serviceConfig);
        serviceConfig.export();
        this.logger.info("export service :" + serviceConfig.getInterface());
    }
    
    private String generatorKey(final ServiceConfig<?> serviceConfig) {
    	//注册服务的接口类名称
        String interfaceName = serviceConfig.getInterface();
        interfaceName = ((interfaceName == null || interfaceName.length() <= 0) ? serviceConfig.getInterfaceClass().getName() : interfaceName);
        if (StringUtils.isEmpty((CharSequence)interfaceName)) {
            throw new IllegalArgumentException("No interface info in serviceConfig " + serviceConfig);
        }
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(interfaceName).append(serviceConfig.getVersion());
        return stringBuilder.toString();
    };
    
    private static class Holder
    {
        private static ServiceConfigHolder holder;
        
        static {
            Holder.holder = new ServiceConfigHolder();
        }
    }
}
