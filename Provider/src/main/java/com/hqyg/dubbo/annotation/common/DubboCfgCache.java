package com.hqyg.dubbo.annotation.common;

public class DubboCfgCache
{
    private String dubboZkPort;
    private String dubboZkAddress;
    
    public static DubboCfgCache cfgHolder() {
        return CfgCacheHolder.holder;
    }
    
    public String getDubboZkPort() {
        return this.dubboZkPort;
    }
    
    public void setDubboZkPort(final String dubboZkPort) {
        this.dubboZkPort = dubboZkPort;
    }
    
    public String getDubboZkAddress() {
        return this.dubboZkAddress;
    }
    
    public void setDubboZkAddress(final String dubboZkAddress) {
        this.dubboZkAddress = dubboZkAddress;
    }
    
    private static class CfgCacheHolder
    {
        private static DubboCfgCache holder;
        
        static {
            CfgCacheHolder.holder = new DubboCfgCache();
        }
    }
}
