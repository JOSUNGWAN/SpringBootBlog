package com.cos.blog.test;

import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;


@RestController // html 파일이 아니라 data를 리턴해주는 controller
public class DummyControllerTest {

	@Autowired // 의존성 주입
	private UserRepository userRepository;
	
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		try {
			userRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) { // Exception 으로 걸어도 됨.
			return "삭제 실패 하였습니다. 해당id는 존재하지 않습니다.";
		}
		return "삭제 되었습니다. id : " + id;
	}
	
	// save  함수는 id를 전달하지 않으면 insert를 해주고
	// save  함수는 id를 전달하면 해당 id에 대한 데이터가 있으면 update를 해주고
	// save  함수는 id를 전달하면 해당 id에 대한 데이터가 으면 insert를 해줌
	@Transactional // 변경된것을 인식해서 함수종료시 자동 commit
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id, @RequestBody User requestUser) { // json 데이터를 요청 => Spring이 Java Object 로 변환해서 받아줌
		
		User user = userRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("수정 실패");
		});
	 	user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		
		//userRepository.save(user);
		return user;
	}
	
	@GetMapping("/dummy/users")
	public List<User> list() {
		return userRepository.findAll();
	}
	@GetMapping("/dummy/user")
	public List<User> pageList(@PageableDefault(size = 2, sort = "id", direction = Direction.DESC) Pageable pageable) {
		Page<User> PagingUser = userRepository.findAll(pageable);
		
		List<User> users = PagingUser.getContent();
		return users;
	}

	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		//User user = userRepository.findById(id).get(); // null 이 없다는걸 확신하 바로 뽑는다.

		// 람다식
//		User user = userRepository.findById(id).orElseThrow( ()-> {
//			return new IllegalArgumentException("해당 유저는 없습니다. id : " + id);
//		});
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
			@Override
			public IllegalArgumentException get() {
				return new IllegalArgumentException("해당 유저는 없습니다. id : " + id);
			}
		});
		// 자바에 오브젝트를 리턴하게 되면 MessageConvertor가 Jackson 라이브러리를 호출해서
		//user 오브젝트를 json 으로 변환해서 브라우저에게 던져줌
		return user;
	}

	@PostMapping("/dummy/join")
	public String join(User user) {

		user.setRole(RoleType.USER);
		userRepository.save(user);
		return "회원가입이 완료되었습니다";
	}

}
