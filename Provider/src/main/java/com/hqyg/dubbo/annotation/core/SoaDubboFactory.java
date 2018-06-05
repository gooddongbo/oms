package com.hqyg.dubbo.annotation.core;


public class SoaDubboFactory
{
    private static SoaBubboExecutor dubboExecutor;
    
    static {
        SoaDubboFactory.dubboExecutor = new SoaBubboExecutor();
    }
    
    public static <T> T getDubboService(final String group, final Class<T> T) {
        return getDubboService(group, T, "1.0.0");
    }
    
    public static <T> T getDubboService(final Class<T> T) {
        return getDubboService(null, T, "1.0.0");
    }
    
    public static <T> T getDubboService(final Class<T> T, final String version) {
        return getDubboService(null, T, version);
    }
    
    public static <T> T getDubboService(final String group, final Class<T> T, final String version) {
        return SoaDubboFactory.dubboExecutor.getSoaService(group, T, version);
    }
    
    public static void registryService(final Class<?> clazz, final Object ref) {
        registryService(null, clazz, ref, "1.0.0");
    }
    
    public static void registryService(final Class<?> clazz, final Object ref, final String version) {
        registryService(null, clazz, ref, version);
    }
    
    public static void registryService(final String group, final Class<?> clazz, final Object ref) {
        registryService(group, clazz, ref, "1.0.0");
    }
    
    public static void registryService(final String group, final Class<?> clazz, final Object ref, final String version) {
        SoaDubboFactory.dubboExecutor.registryService(group, clazz, ref, version);
    }
}