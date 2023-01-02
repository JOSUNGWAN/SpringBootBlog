package com.cos.blog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;

@RestController
public class UserApiController {
	
	@Autowired
	private UserService userService;

	@PostMapping("/api/user")
	public  ResponseDto<Integer> save(@RequestBody User user) {
		System.out.println("호출됨");
		//  실제로 DB에 insert를 하고 아래에서 return 이 되면 된다.
		user.setRole(RoleType.USER);
		int result = userService.insert(user);
		return new ResponseDto<Integer>(HttpStatus.OK, result);
	}
	
}
