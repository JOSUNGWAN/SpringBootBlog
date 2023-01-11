package com.cos.blog.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.blog.model.User;

import lombok.Data;
import lombok.Getter;

// 스프링 시큐리티가 로그인 요청을 가로채서 로그인을 진행하고 완료가 되며 UserDetail 타입의 오브젝트를
//스프링 시큐리티의 고유한 세션저장소에 저장을 해준다.
@Data
public class PrincipalDetail implements UserDetails { // extends  상속이라하 
	private User user; // 객체를 품고있는것은 컴포지션 이라한다.


	public PrincipalDetail(User user) {
		this.user = user;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}
	// 계정 만료 여부 리턴 true = 만료 안됨  false = 계정만료
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	// 계정이 잠겨있는지 여부 리턴 true = 안잠김  false = 잠김
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	// 비밀번호 만료 여부 리턴 true = 만료 안됨  false = 비번만료
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	// 계정 활성화(사용가능) 여부 리턴 true = 활성화  false = 비활성화
	@Override
	public boolean isEnabled() {
		return true;
	}

	// 계정이 가진 권한 목록 리턴 (권한이 여러개 있다면 루프를 돌려야 하지만 지금은 하나만 있기 때문에 add)
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		Collection<GrantedAuthority> collectors = new ArrayList<GrantedAuthority>();
		//		collectors.add(new GrantedAuthority() {
		//			
		//			@Override
		//			public String getAuthority() {
		//				return "ROLE_"+user.getRole(); // ROLE_USER  "ROLE_" 필수!
		//			}
		//		});	
		collectors.add(()->{return "ROLE_"+user.getRole();}); // 하나의 타입만 들어올수있고 함수가 하나밖에 없어서 람다식 표현 가능
		return collectors;
	}


}
