package com.hqyg.dubbo.custom.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class BeanUtils {
	
	public static List<Class> getClass(final String packageName) throws IOException{
		final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		final String path = packageName.replace(".", "/");
		final Enumeration<URL> url = classLoader.getResources(path);
		final List<File> dirs = new ArrayList<File>();
		final List<Class> classList =new  ArrayList<Class>();
		while(url.hasMoreElements()){
			URL urlResource = url.nextElement();
			String type = urlResource.getProtocol();
			if(type.equals("file")){
				dirs.add(new File(urlResource.getFile()));
			}
		}
		for(File file : dirs){
            classList.addAll(findClassList(file,packageName)) ;
        }

		return classList;
	}

	public static List<Class> findClassList(final File path, final String packageName){
	    final List<Class> classList = new ArrayList<Class>();
        //如果path表示的是一个目录则返回true
	    if(!path.isDirectory()){
            return classList;
        }
        //获取path目录下的所有文件和文件夹
        final File[] files = path.listFiles();
        File[] array;
	    for(int length = (array = files).length,i=0; i< length; i++){
	        File file = files[i];
	        //file不是目录，则利用反射拿出class
	        if(!file.isDirectory()){
                String classnName = packageName+ "."+ file.getName().substring(0,file.getName().length() - 6);
                try {
                    classList.add(Class.forName(classnName));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }else{
                classList.addAll(findClassList(file,packageName+"."+file.getName()));
            }
        }
        return classList;
    }

	public static void main(String[] args) throws IOException {
		BeanUtils beanUtils = new BeanUtils();
		beanUtils.getClass("com.hqyg.dubbo.Provider");
	}
}
