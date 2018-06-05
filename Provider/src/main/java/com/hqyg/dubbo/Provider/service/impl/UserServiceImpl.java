package com.hqyg.dubbo.Provider.service.impl;


import org.springframework.stereotype.Service;

import com.hqyg.dubbo.Provider.dto.UserDto;
import com.hqyg.dubbo.Provider.service.UserService;
import com.hqyg.dubbo.annotation.SoaService;
import com.hqyg.dubbo.custom.access.annotion.LimitAccess;

@Service
@SoaService
public class UserServiceImpl implements UserService{

	@LimitAccess(limit=3)
	public UserDto getUserById(Integer id) {
		UserDto userDto = new UserDto();
		userDto.setId(id);
		userDto.setUsername("dongbo");
		userDto.setPassword("123456dong");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return userDto;
	}

}
