package com.hqyg.dubbo.Provider;

import com.alibaba.dubbo.container.spring.SpringContainer;

public class ProviderMain {
	public static void main(String[] args) {
        System.setProperty(SpringContainer.SPRING_CONFIG, "classpath*:application.xml");
        com.alibaba.dubbo.container.Main.main(args);
	}
}
