package com.hqyg.dubbo.annotation.core;

import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.MonitorConfig;
import com.alibaba.dubbo.config.RegistryConfig;

public class ConfigCollector {
    private static ConcurrentHashMap<String, ApplicationConfig> domain2AppConfigMap;
    private RegistryConfig registry;
    private MonitorConfig monitorConfig;
    
    static {
        ConfigCollector.domain2AppConfigMap = new ConcurrentHashMap<String, ApplicationConfig>();
    }
    
    public ConfigCollector() {
    	//连接注册重点配置
        (this.registry = new RegistryConfig()).setAddress("39.108.235.200:2182");
        //#注册中心地址协议
        this.registry.setProtocol("zookeeper");
//        this.registry.setUsername("root");
//        this.registry.setPassword("root");
        //服务注册类型  false：静态类(监控中心管理上下线) true:自动管理
        this.registry.setDynamic(true);
        (this.monitorConfig = new MonitorConfig()).setProtocol("registry");
    }
    
    public static ConfigCollector get() {
        return Holder.holder;
    }
    
    public ApplicationConfig getApplication(final String domain) {
        if (!ConfigCollector.domain2AppConfigMap.containsKey(domain)) {
            final ApplicationConfig application = new ApplicationConfig();
            application.setName(domain);
            application.setOwner("dongbo");
            ConfigCollector.domain2AppConfigMap.put(domain, application);
        }
        return ConfigCollector.domain2AppConfigMap.get(domain);
    }
    
    public RegistryConfig getRegistry() {
        return this.registry;
    }
    
    public MonitorConfig getMonitorConfig() {
        return this.monitorConfig;
    }
    
    private static class Holder
    {
        private static ConfigCollector holder;
        
        static {
            Holder.holder = new ConfigCollector();
        }
    }
}
