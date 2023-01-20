package com.cos.blog.config;

import javax.annotation.security.PermitAll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cos.blog.config.auth.PrincipalDetailService;


@Configuration           // 빈등록
@EnableWebSecurity // 시큐리티 필터가 등록이 된다.
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소로 접근을 하면 요청 수행전 권한 및 인증을 밀 체크하겠다.
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private PrincipalDetailService principalDetailService;
	
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean //IoC가 된다.
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder(); // spring이 관리
	}
	
	// 시큐리티가 대신 로그인해주는데 password를 가로 채기를 하는데 
	// 해당 password가 뭘로 해쉬가 되어 가입이 되었는지 알아야 같은 해쉬로 암호화 해서 DB와 비교할수있음
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable() // csrf 토큰 비활성화 (테스트시 걸어주는 게 좋다.)
			.authorizeRequests()            // auth의 요청이 들어오면
				.antMatchers("/", "/auth/**", "/js/**", "/css/**", "/image/**")   // auth쪽으로 들어 오는건 요청없이 누구나 들어올수 있다.
				.permitAll()
				.anyRequest()      // 위의 것이 아닌 다른 모든 요청은
				.authenticated() // 인증이 되어야한다.
			.and()
				.formLogin() //인증이 요한 요청이오면
				.loginPage("/auth/loginForm") // 여기로 이동
				.loginProcessingUrl("/auth/loginProc") // 스프링 시큐리티가 해당 주소로 요청오는 로그인을 가로채서 대신 로그인을 해준다.
				.defaultSuccessUrl("/"); // 정상처리시 이동 .failureUrl("/fail"); 실패시 이동
		// 로그인 요청시 .loginProcessingUrl("/auth/loginProc")가 가로채서 PrincipalDetailService의 loadUserByUsername으로 던져둔다.
		// 여기서 username정보가 들어가서 DB에서 username을 찾아서 PrincipalDetail에 담아서 return을 할때 password 비교를 해준다.
		// 정상을 확인하면 스프링 시큐리티의 고유한 세션저장소에 저장된다.
	}


}
