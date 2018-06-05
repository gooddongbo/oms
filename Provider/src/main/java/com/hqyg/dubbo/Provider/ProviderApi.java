package com.hqyg.dubbo.Provider;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import com.hqyg.dubbo.Provider.dto.UserDto;
import com.hqyg.dubbo.Provider.service.UserService;
import com.hqyg.dubbo.Provider.service.impl.UserServiceImpl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 通过dubbo API注册暴露服务
 * 
 * */
public class ProviderApi {

	
	public static void main(String[] args){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("application.xml");
		final UserServiceImpl bean = (UserServiceImpl)ctx.getBean("userServiceImpl");
		
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		executorService.execute(new Runnable(){
			@Override
			public void run() {
				UserDto dto = bean.getUserById(12);
				if(null != dto){
					System.out.println(dto.getUsername());
				}
			}
		});
		executorService.execute(new Runnable(){
			@Override
			public void run() {
				UserDto dto = bean.getUserById(12);
				if(null != dto){
					System.out.println(dto.getUsername());
				}
			}
		});
		executorService.execute(new Runnable(){
			@Override
			public void run() {
				UserDto dto = bean.getUserById(12);
				if(null != dto){
					System.out.println(dto.getUsername());
				}
			}
		});
		executorService.execute(new Runnable(){
			@Override
			public void run() {
				UserDto dto = bean.getUserById(12);
				if(null != dto){
					System.out.println(dto.getUsername());
				}
			}
		});
		executorService.execute(new Runnable(){
			@Override
			public void run() {
				UserDto dto = bean.getUserById(12);
				if(null != dto){
					System.out.println(dto.getUsername());
				}
			}
		});
	}
	public static void main1(String[] args) throws InterruptedException {
		ProviderApi api = new ProviderApi();
		api.registry();
		Thread.sleep(10000000);
	}
	
	public void registry(){
		
		UserService userService = new UserServiceImpl();
		
		//当前应用配置
		ApplicationConfig application = new ApplicationConfig();
		application.setName("Provider-app");
		//连接注册中心配置
		RegistryConfig registry = new RegistryConfig();
		registry.setAddress("zookeeper://39.108.235.200:2182");
		registry.setUsername("root");
		registry.setPassword("root");
		
		//服务提供在协议配置
		ProtocolConfig protocol = new ProtocolConfig();
		protocol.setName("dubbo");
		protocol.setPort(20880);
		protocol.setThreads(200);
		
		ServiceConfig<UserService> service = new ServiceConfig<UserService>();
		service.setApplication(application);
		service.setRegistry(registry);
		service.setProtocol(protocol);
		service.setInterface(UserService.class);
		service.setRef(userService);
		service.setVersion("1.0.0");
		service.export();
	}
}
