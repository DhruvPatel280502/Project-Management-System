package com.company.projectmanagement.service;

import com.company.projectmanagement.dto.UserDto;

public interface UserService {
	
	void register(UserDto dto);
	
    String login(UserDto dto);
}
