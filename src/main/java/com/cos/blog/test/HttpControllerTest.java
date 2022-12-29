package com.cos.blog.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HttpControllerTest {
	
	@GetMapping("/http/lombok")
	public String lombokTest() {
		Member m = new Member(1, "ssar", "1234", "ssar@nate.com");
		System.out.println("getter : " + m.getId());
		m.setId(5000);
		System.out.println("getter : " + m.getId());
		return "lombok test 완료";
	}
	
	@GetMapping("/http/get")
	public String getTest(Member m) {
		
		return m.getId() + " , " + m.getUsername() + " , " + m.getPassword() + " , "+ m.getEmail();
	}
}
