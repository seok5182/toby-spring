package toby.spring.springbook.user.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration // 애플리케이션 컨텍스트 또는 빈 팩토리가 사용할 설정정보라는 뜻
public class DaoFactory {

	// 생성자 DI를 사용하는 팩토리 메소드
	/*
	@Bean // 오브젝트 생성을 담당하는 IoC 메소드라는 뜻
	public UserDao userDao() {
		return new UserDao(connectionMaker());
	}
	*/

	// 수정자 메소드 DI를 사용하는 팩토리 메소드
	@Bean
	public UserDao userDao() {
		UserDao userDao = new UserDao();
		userDao.setConnectionMaker(connectionMaker());
		return userDao;
	}

	// 분리해서 중복을 제거한 ConnectionMaker 타입 오브젝트 생성 코드
	@Bean
	public ConnectionMaker connectionMaker() {
		return new DConnectionMaker();
	}
}
