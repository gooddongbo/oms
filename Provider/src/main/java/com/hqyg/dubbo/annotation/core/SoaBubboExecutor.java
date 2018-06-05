package com.hqyg.dubbo.annotation.core;

import com.alibaba.dubbo.config.utils.*;
import com.hqyg.dubbo.annotation.common.ConfigConstant;
import com.hqyg.dubbo.annotation.common.DubboCfgCache;

import org.slf4j.*;
import org.apache.commons.lang3.*;
import com.alibaba.dubbo.config.*;

public class SoaBubboExecutor
{
    private Logger log;
    private ConfigCollector configCollector;
    private ReferenceConfigCache referenceConfigCache;
    
    private static final String DUBBO_REGISTRY_ADDRESS = DubboCfgCache.cfgHolder().getDubboZkAddress();
    
    public SoaBubboExecutor() {
        this.log = LoggerFactory.getLogger((Class)this.getClass());
        this.configCollector = ConfigCollector.get();
        this.referenceConfigCache = ReferenceConfigCache.getCache();
    }
    
    public void registryService(final String group, final Class<?> clazz, final Object ref, final String version) {
        this.log.info("registry dubboService class \uff1a" + clazz + " ref:" + ref);
        final ServiceConfig<Object> serviceConfig = (ServiceConfig<Object>)new ServiceConfig();
        //设置注册应用名称
        //this.configCollector.getApplication(this.getApplicationName(clazz))
        serviceConfig.setApplication(this.configCollector.getApplication(this.getApplicationName(clazz)));
        serviceConfig.setRegistry(this.configCollector.getRegistry());
        serviceConfig.setInterface((Class)clazz);
        serviceConfig.setRef(ref);
        serviceConfig.setVersion(version);
        //dubbo服务线程池数
        serviceConfig.setExecutes(ConfigConstant.DUBBO_EXECUTES);
        //provider服务超时(建立provider端设置，consumer端不建议设置)
        serviceConfig.setTimeout(ConfigConstant.DUBBO_TIMEOUT);
        //#负载平衡  random:随机、roundrobin：轮循leastactive：最少活跃调用数、consistenthash：一致hash倿
        serviceConfig.setLoadbalance(ConfigConstant.DUBBO_LOADBALANCE);
        if (StringUtils.isNotEmpty((CharSequence)group)) {
            serviceConfig.setGroup(group);
        }
        final ProtocolConfig protocol = new ProtocolConfig();
        //#缺省配置为dubbo协议，该采用单一长连接和NIO异步通讯，鿂合于小数据量大并发的服务调用，以及服务消费者机器数远大于服务提供迅机器数的情况㿿
        protocol.setName(ConfigConstant.DUBBO_PROTOCOL_PROTOCOL);
        //#序列互方弿 json/hessian2
        protocol.setSerialization(ConfigConstant.DUBBO_PROTOCOL_SERIALIZATION);
        
        protocol.setPort(ConfigConstant.DUBBO_PROTOCOL_PORT);
        protocol.setDispatcher(ConfigConstant.DUBBO_PROTOCOL_DISPATCHER);
        protocol.setThreadpool(ConfigConstant.DUBBO_PROTOCOL_THREADPOOL);
        protocol.setThreads(ConfigConstant.DUBBO_PROTOCOL_THREADS);
        serviceConfig.setProtocol(protocol);
        ServiceConfigHolder.configHolder().export(serviceConfig);
    }
    
    /**
     * 注解需要扫描的包路径
     * */
    private String getApplicationName(final Class<?> clazz) {
        final String clazzStr = clazz.toString();
        final String[] splitStr = clazzStr.split("\\.");
        if (clazzStr.contains("com.hqyg.dubbo.Provider")) {
            return splitStr[3];
        }
        return splitStr[2];
    }
    
    public <T> T getSoaService(final String group, final Class<T> clazz, final String version) {
        this.log.info("consumer dubboService class\uff1a" + clazz);
        final ReferenceConfig<T> referenceConfig = (ReferenceConfig<T>)new ReferenceConfig();
        referenceConfig.setApplication(this.configCollector.getApplication(this.getApplicationName(clazz)));
        referenceConfig.setRegistry(this.configCollector.getRegistry());
        referenceConfig.setInterface((Class)clazz);
        referenceConfig.setVersion(version);
        referenceConfig.setRetries(ConfigConstant.DUBBO_RETRIES);
        referenceConfig.setCheck(ConfigConstant.DUBBO_CHECK);
        referenceConfig.setTimeout(ConfigConstant.DUBBO_TIMEOUT);
        if (StringUtils.isNotEmpty((CharSequence)group)) {
            referenceConfig.setGroup(group);
        }
        return (T)this.referenceConfigCache.get((ReferenceConfig)referenceConfig);
    }
}
