package com.hqyg.dubbo.annotation.utils;

import org.slf4j.*;
import java.util.*;
import java.net.*;
import java.io.*;
import java.util.jar.*;
@SuppressWarnings("rawtypes")
public class BeanUtils {
	    private static Logger log;
	    static {
	    	BeanUtils.log = LoggerFactory.getLogger((Class)BeanUtils.class);
	    }
	    
	    /**
	     * @param packageName 需要扫描的包路径
	     * 
	     * */
		public static List<Class> getClasses(final String packageName) throws IOException {
	        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	        //输入的是java的包路径，所以要转化为文件夹形式的路径
	        final String path = packageName.replace(".", "/");
	        BeanUtils.log.info("reflect Path:" + path);
	        //获取待扫描包路径下的文件及文件夹
	        final Enumeration<URL> resources = classLoader.getResources(path);
	        final List<Class> classes = new ArrayList<Class>();
	        final List<File> dirs = new ArrayList<File>();
	        while (resources.hasMoreElements()) {
	            final URL urlResource = resources.nextElement();
	            final String type = urlResource.getProtocol();
	            BeanUtils.log.info("urlResource type:" + type);
	            if (type.equals("file")) {
	                dirs.add(new File(urlResource.getFile()));
	            }
	            else {
	                if (!type.equals("jar")) {
	                    continue;
	                }
	                classes.addAll(getClassNameByJar(urlResource.getPath()));
	            }
	        }
	        for (final File directory : dirs) {
	            classes.addAll(findClasses(directory, packageName));
	        }
	        BeanUtils.log.info("classSize:" + classes.size());
	        return classes;
	    }
	    
	    private static List<Class> findClasses(final File directory, final String packageName) {
	        final List<Class> classes = new ArrayList<Class>();
	        if (!directory.isDirectory()) {
	            return classes;
	        }
	        final File[] files = directory.listFiles();
	        File[] array;
	        for (int length = (array = files).length, i = 0; i < length; ++i) {
	            final File file = array[i];
	            if (file.isDirectory()) {
	                assert !file.getName().contains(".");
	                BeanUtils.log.info(String.valueOf(packageName) + "." + file.getName());
	                classes.addAll(findClasses(file, String.valueOf(packageName) + "." + file.getName()));
	            }
	            else if (file.getName().endsWith(".class")) {
	                final String className = String.valueOf(packageName) + "." + file.getName().substring(0, file.getName().length() - 6);
	                try {
	                    classes.add(Class.forName(className));
	                }
	                catch (ClassNotFoundException e) {
	                    e.printStackTrace();
	                    BeanUtils.log.error("Class.forName '" + className + "' ClassNotFoundException:", (Throwable)e);
	                }
	                catch (ExceptionInInitializerError e2) {
	                    e2.printStackTrace();
	                    BeanUtils.log.error("Class.forName '" + className + "' ExceptionInInitializerError:", (Throwable)e2);
	                }
	                catch (Throwable e3) {
	                    e3.printStackTrace();
	                    BeanUtils.log.error("Class.forName '" + className + "' Throwable:", e3);
	                }
	            }
	        }
	        return classes;
	    }
	    
	    public static List<Class> getClassNameByJar(final String jarPath) {
	        final List<Class> myClassName = new ArrayList<Class>();
	        final String[] jarInfo = jarPath.split("!");
	        String jarFilePath = null;
	        try {
	            jarFilePath = URLDecoder.decode(jarInfo[0].substring(jarInfo[0].indexOf("/")), "UTF-8");
	        }
	        catch (UnsupportedEncodingException e) {
	            BeanUtils.log.error("UnsupportedEncodingException:", (Throwable)e);
	        }
	        final String packagePath = jarInfo[1].substring(1);
	        JarFile jarFile = null;
	        try {
	            jarFile = new JarFile(jarFilePath);
	            final Enumeration<JarEntry> entrys = jarFile.entries();
	            while (entrys.hasMoreElements()) {
	                final JarEntry jarEntry = entrys.nextElement();
	                String entryName = jarEntry.getName();
	                if (entryName.endsWith(".class") && entryName.startsWith(packagePath)) {
	                    entryName = entryName.replace("/", ".").substring(0, entryName.lastIndexOf("."));
	                    try {
	                        myClassName.add(Class.forName(entryName));
	                    }
	                    catch (ClassNotFoundException e2) {
	                        e2.printStackTrace();
	                        BeanUtils.log.error("Class.forName '" + entryName + "' ClassNotFoundException:", (Throwable)e2);
	                    }
	                    catch (ExceptionInInitializerError e3) {
	                        e3.printStackTrace();
	                        BeanUtils.log.error("Class.forName '" + entryName + "' ExceptionInInitializerError:", (Throwable)e3);
	                    }
	                    catch (Throwable e4) {
	                        e4.printStackTrace();
	                        BeanUtils.log.error("Class.forName '" + entryName + "' Throwable:", e4);
	                    }
	                }
	            }
	        }
	        catch (Exception e5) {
	            BeanUtils.log.error("Exception:", (Throwable)e5);
	            if (jarFile != null) {
	                try {
	                    jarFile.close();
	                }
	                catch (IOException e6) {
	                    BeanUtils.log.error("IOException:", (Throwable)e6);
	                }
	                return (List<Class>)myClassName;
	            }
	            return (List<Class>)myClassName;
	        }
	        finally {
	            if (jarFile != null) {
	                try {
	                    jarFile.close();
	                }
	                catch (IOException e6) {
	                	BeanUtils.log.error("IOException:", (Throwable)e6);
	                }
	            }
	        }
	        if (jarFile != null) {
	            try {
	                jarFile.close();
	            }
	            catch (IOException e6) {
	            	BeanUtils.log.error("IOException:", (Throwable)e6);
	            }
	        }
	        return (List<Class>)myClassName;
	    }
}
