package com.cos.blog.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

@Service // 빈등록
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Transactional // 전체성공시 commit, 실패시 rollback 짜야함ㅋ
	public int insert(User user) {
		try {
			userRepository.save(user);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("UserService : 회원가입 : " +e.getMessage());
		}
		return -1;
	}
	
}
