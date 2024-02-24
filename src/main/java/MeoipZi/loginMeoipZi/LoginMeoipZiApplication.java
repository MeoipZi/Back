package MeoipZi.loginMeoipZi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//최초 회원가입 전 데이터베이스의 Authority 테이블에 권한을 넣어야 함. - data.sql 확인
//AuthController의 /authenticate 가 로그인

@SpringBootApplication
public class LoginMeoipZiApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoginMeoipZiApplication.class, args);
	}

}
