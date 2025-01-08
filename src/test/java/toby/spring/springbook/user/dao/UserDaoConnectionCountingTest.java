package toby.spring.springbook.user.dao;

import java.sql.SQLException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import toby.spring.springbook.user.domain.User;

@SpringBootTest
class UserDaoConnectionCountingTest {

	// 1.7.4 테스트
	@Test
	void test1() throws SQLException, ClassNotFoundException {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
			CountingDaoFactory.class);
		UserDao dao = context.getBean("userDao", UserDao.class);

		User user = new User();
		user.setId("whiteship");
		user.setName("백기선");
		user.setPassword("married");

		dao.add(user);

		System.out.println(user.getId() + " 등록 성공");

		User user2 = dao.get(user.getId());
		System.out.println(user2.getName());
		System.out.println(user2.getPassword());

		System.out.println(user2.getId() + " 조회 성공");

		/*
		DAO 사용 코드
		 */
		CountingConnectionMaker ccm = context.getBean("connectionMaker", CountingConnectionMaker.class);
		System.out.println("Connection counter : " + ccm.getCounter());
	}

}