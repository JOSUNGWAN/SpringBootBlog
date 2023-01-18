package com.cos.blog.controller.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

	
	@PostMapping("/auth/joinProc")
	public  ResponseDto<Integer> save(@RequestBody User user) {
		System.out.println("join호출됨");
		userService.insert(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	@PutMapping("/user")
	public  ResponseDto<Integer> update(@RequestBody User user) {
		userService.userupdate(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//security 적용 전 일반 로그인
//	@PostMapping("/api/user/login")
//	public  ResponseDto<Integer> login(@RequestBody User user, HttpSession session) {
//		System.out.println("login 호출");
//		User principal = userService.login(user); // principal (접근주체)
//		
//		if(principal != null) {
//			session.setAttribute("principal", principal);
//		}
//		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
//	}
	
	
	
	
