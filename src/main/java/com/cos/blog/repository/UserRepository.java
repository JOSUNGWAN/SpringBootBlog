package com.cos.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cos.blog.model.User;

// JSP - DAO
// Spring - 자동으로 bean  등록 @Repository 생략가능
public interface UserRepository extends JpaRepository<User, Integer> {

	// SELECT * from user WHERE username = 1?;
	Optional<User> findByUsername(String username); //네이밍 쿼리 findBy + where절의 값 첫글자 대문자로 적어줘야함
	
}








	//security 적용 전 일반 로그인
	// JPA 네이밍 쿼리
	// SELECT * FROM user Where username = ?1 AND password = ?2; 가 자동 동작함 ?에 매개변수가 입력됨
	//User findByUsernameAndPassword(String username, String password);	
	
//	@Query(value = "SELECT * FROM user Where username = ?1 AND password = ?2", nativeQuery = true) // 쿼리문을 직접사용 하는 방법
//	User login(String username, String password);	
	
