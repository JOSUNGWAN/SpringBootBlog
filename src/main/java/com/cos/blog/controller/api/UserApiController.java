package com.cos.blog.controller.api;

import javax.servlet.http.HttpSession;

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
	
//	@Autowired 
//	private HttpSession session; 매개변수 받는 방법말고 DI에서 받아서 사용이 가능 하다.

	@PostMapping("/api/user")
	public  ResponseDto<Integer> save(@RequestBody User user) {
		System.out.println("join호출됨");
		//  실제로 DB에 insert를 하고 아래에서 return 이 되면 된다.
		user.setRole(RoleType.USER);
		userService.insert(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	@PostMapping("/api/user/login")
	public  ResponseDto<Integer> login(@RequestBody User user, HttpSession session) {
		System.out.println("login 호출");
		User principal = userService.login(user); // principal (접근주체)
		
		if(principal != null) {
			session.setAttribute("principal", principal);
		}
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
}
