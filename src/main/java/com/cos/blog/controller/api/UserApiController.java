package com.cos.blog.controller.api;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;

@RestController
public class UserApiController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
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
		//  트랜젝션이 종료되기 때문에 DB에 값은 변경이되지만 세션값은 변경되지 않은 상태
		// 직접 세션값을 변경해줘야함.
		//세션등록
		Authentication authentication = 
											authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
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
	
	
	
	
