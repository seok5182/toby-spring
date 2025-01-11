package toby.spring.springbook.user.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class CountingDaoFactory {

	// 생성자 DI를 사용하는 팩토리 메소드
	@Bean
	public UserDao userDao() {
		return new UserDao(connectionMaker());
	}

	// 수정자 메소드 DI를 사용하는 팩토리 메소드
	/*
	@Bean
	public UserDao userDao() {
		UserDao userDao = new UserDao();
		userDao.setConnectionMaker(connectionMaker());
		return userDao;
	}
	*/

	@Bean
	public ConnectionMaker connectionMaker() {
		return new CountingConnectionMaker(realConnectionMaker());
	}

	@Bean
	public ConnectionMaker realConnectionMaker() {
		return new DConnectionMaker();
	}
}
