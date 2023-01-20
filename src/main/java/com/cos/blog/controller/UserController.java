package com.cos.blog.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.cos.blog.model.KakaoProfile;
import com.cos.blog.model.OAuthToken;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


// 인증이 안된 사용자들이 출입할 수 있는 경로를 // auth/** 허용
// 그냥 주소가 / 이면 index.jsp 허용
// static 이하에 있는 /js/**, /css/**, /image/**

@Controller
public class UserController {
	
	@Value("${root.key}")
	private String rootKey;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;

	@GetMapping("auth/joinForm") // security 전 /user/joinForm
	public String joinForm() {
		return "user/joinForm";
	}
	@GetMapping("auth/loginForm") // security 전 /user/loginForm
	public String loginForm() {
		return "user/loginForm";
	}

	@GetMapping("/auth/kakao/callback")
	public String kakaoCallback(String code) {

		// POST 방식으로 key = value 데이터를 요청 (카카오쪽으로)
		// Retrofit2, OkHttp, Resttemplate
		RestTemplate rt = new RestTemplate();

		//HttpHeader 오브젝트 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8"); // key = value 형태의 데이터라는 것을 알려줌

		//HttpBody 오브젝트 생성
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		// 변수화 해서 사용하는 것이 좋지만 어떤값이 들어가는지 이해를 위해 직접 넣어준다.
		params.add("grant_type", "authorization_code");
		params.add("client_id", "1cffcdf83c7e392a1af4f53a5e435d76");
		params.add("redirect_uri", "http://localhost:8000/auth/kakao/callback");
		params.add("code", code);

		///HttpHeader와 HttpBody를 하나의 오브젝트에 담기 exchange함수가 HttpEntity라는 오브젝트를 넣어야하기때문
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = 
				new HttpEntity<>(params, headers); // body 데이터와 header값을 가지는 Entity 이다.

		// Http 요청하기 - Post방식으로 - 그리고 response 변수의 응답을 받음
		ResponseEntity<String> response = rt.exchange(
				"https://kauth.kakao.com/oauth/token", // 토큰 발급 요청주소
				HttpMethod.POST, // 요청 방식
				kakaoTokenRequest, // body 데이터와 header값
				String.class // 응답받을 타입
				);

		// Gson, Json Simple, ObjectMapper 사용가능
		ObjectMapper objectMapper = new ObjectMapper();
		OAuthToken oauthToken = null;
		try {
			oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		System.out.println("카카오 엑세스 토큰"+oauthToken.getAccess_token());
		// -----------------------------------------------------------

		RestTemplate rt2 = new RestTemplate();

		//HttpHeader 오브젝트 생성
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization", "Bearer "+ oauthToken.getAccess_token());
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		///HttpHeader와 HttpBody를 하나의 오브젝트에 담기 exchange함수가 HttpEntity라는 오브젝트를 넣어야하기때문
		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 = 
				new HttpEntity<>(headers2); // header값을 가지는 Entity

		// Http 요청하기 - Post방식으로 - 그리고 response 변수의 응답을 받음
		ResponseEntity<String> response2 = rt2.exchange(
				"https://kapi.kakao.com/v2/user/me", // 토큰 발급 요청주소
				HttpMethod.POST, // 요청 방식
				kakaoProfileRequest2, // body 데이터와 header값
				String.class // 응답받을 타입
				);
		
		// Gson, Json Simple, ObjectMapper 사용가능
				ObjectMapper objectMapper2 = new ObjectMapper();
				KakaoProfile kakaoProfile = null;
				try {
					kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
				} catch (JsonMappingException e) {
					e.printStackTrace();
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
				
				// User 오브젝트 : username, password, email 필요
				System.out.println("카카오 아이디(번호)"+kakaoProfile.getId());
				System.out.println("카카오 이메일"+kakaoProfile.getKakao_account().getEmail());
				
				System.out.println("블로그서버 카카오 유저네임 : " + kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId());
				System.out.println("블로그서버 이메일 : " + kakaoProfile.getKakao_account().getEmail());
				//UUID란 -> 중복되지 않은 특정 값을 만들어내는 알고리즘
				System.out.println("블로그 패스워드 : " + rootKey);
				
				User kakaoUser = User.builder()
						.username(kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId())
						.password(rootKey)
						.email(kakaoProfile.getKakao_account().getEmail())
						.oauth("kakao")
						.build();
				// 기존 가입자인디 비가입자인지 체크 처리
				User originUser = userService.userfind(kakaoUser.getUsername());
				
				if(originUser.getUsername() == null) { // 비가입자라면 회원가입후 로그인처리
					System.out.println("기존 회원이 아닙니다. 자동으로 회원가입을 진행합니다.");
					userService.insert(kakaoUser);					
				}
				System.out.println("자동 로그인을 진행합니다.");
				// 로그인 처리
				Authentication authentication = 
						authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(),rootKey));
				SecurityContextHolder.getContext().setAuthentication(authentication);

		return "redirect:/";  // 코드값 부여받음, 인증이 되었다,

	}

	@GetMapping("/user/updateForm")
	public String updateForm() {
		return "user/updateForm";
	}
}
