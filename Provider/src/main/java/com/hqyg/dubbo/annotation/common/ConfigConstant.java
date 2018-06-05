package com.hqyg.dubbo.annotation.common;

public class ConfigConstant
{
//    public static final String DUBBO_REGISTRY_ADDRESS;
    public static final String DUBBO_REGISTRY_PROTOCOL;
    public static final Boolean DUBBO_REGISTRY_DYNAMIC;
    public static final String DUBBO_PROTOCOL_PROTOCOL;
    public static final String DUBBO_PROTOCOL_SERIALIZATION;
    public static final Integer DUBBO_PROTOCOL_PORT;
    public static final String DUBBO_PROTOCOL_DISPATCHER;
    public static final String DUBBO_PROTOCOL_THREADPOOL;
    public static final Integer DUBBO_PROTOCOL_THREADS;
    public static final Integer DUBBO_TIMEOUT;
    public static final Boolean DUBBO_CHECK;
    public static final Integer DUBBO_EXECUTES;
    public static final String DUBBO_LOADBALANCE;
    public static final Integer DUBBO_RETRIES;
    
    static {
//        DUBBO_REGISTRY_ADDRESS = DubboCfgCache.cfgHolder().getDubboZkAddress();
        DUBBO_REGISTRY_PROTOCOL = DubboxConfig.getConfigValue("dubbo.registry.protocol");
        DUBBO_REGISTRY_DYNAMIC = Boolean.valueOf(DubboxConfig.getConfigValue("dubbo.registry.dynamic"));
        DUBBO_PROTOCOL_PROTOCOL = DubboxConfig.getConfigValue("dubbo.protocol.protocol");
        DUBBO_PROTOCOL_SERIALIZATION = DubboxConfig.getConfigValue("dubbo.protocol.serialization");
//        DUBBO_PROTOCOL_PORT = Integer.valueOf(DubboCfgCache.cfgHolder().getDubboZkPort());
        DUBBO_PROTOCOL_PORT = Integer.valueOf(DubboxConfig.getConfigValue("dubbo.protocol.port"));
        DUBBO_PROTOCOL_DISPATCHER = DubboxConfig.getConfigValue("dubbo.protocol.dispatcher");
        DUBBO_PROTOCOL_THREADPOOL = DubboxConfig.getConfigValue("dubbo.protocol.threadpool");
        DUBBO_PROTOCOL_THREADS = Integer.valueOf(DubboxConfig.getConfigValue("dubbo.protocol.threads"));
        DUBBO_TIMEOUT = Integer.valueOf(DubboxConfig.getConfigValue("dubbo.timeout"));
        DUBBO_CHECK = Boolean.valueOf(DubboxConfig.getConfigValue("dubbo.check"));
        DUBBO_EXECUTES = Integer.valueOf(DubboxConfig.getConfigValue("dubbo.protocol.threads"));
        DUBBO_LOADBALANCE = DubboxConfig.getConfigValue("dubbo.loadbalance");
        DUBBO_RETRIES = Integer.valueOf(DubboxConfig.getConfigValue("dubbo.retries"));
    }
}
