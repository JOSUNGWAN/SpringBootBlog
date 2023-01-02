package com.cos.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.User;

// JSP - DAO
// Spring - 자동으로 bean  등록 @Repository 생략가능
public interface UserRepository extends JpaRepository<User, Integer> {

}
