package com.hqyg.dubbo.custom.access.utils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class LimitAccessCount {
	private ConcurrentHashMap<String, AtomicInteger> map = new ConcurrentHashMap<String,AtomicInteger>();
	
	private LimitAccessCount(){}
	
	 private static class SingletonHolder {  
		    private static final LimitAccessCount INSTANCE = new LimitAccessCount();  
	 }
	 
     public static final LimitAccessCount getInstance() {  
    	return SingletonHolder.INSTANCE;  
     }
     
     public int getAccessCount(String key){
    	 AtomicInteger count = map.get(key);
    	 if(count == null){
    		 count = new AtomicInteger(0);  
    		 map.put(key, count);
    	 }
    	 return count.get();
     }
     
     public int incrementCount(String key){
    	 AtomicInteger count = map.get(key);
    	 if(count == null){
    		 count = new AtomicInteger(0);
    		 map.put(key,count);
    	 }
    	 int result = count.incrementAndGet();
    	 map.put(key,count);
    	 System.out.println(key+"有"+result+"在同时访问");
    	 return result;
     }
     
     public int decrementCount(String key){
    	 AtomicInteger count = map.get(key);
    	 if(count == null){
    		 count = new AtomicInteger(0);
    		 map.put(key,count);
    	 }
    	 int result = count.decrementAndGet();
    	 map.put(key,count);
    	 System.out.println(key+"使用次数减少1次,"+"有"+result+"在同时访问");
    	 return result; 
     }
}
