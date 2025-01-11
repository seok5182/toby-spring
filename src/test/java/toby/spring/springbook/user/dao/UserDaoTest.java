package toby.spring.springbook.user.dao;

import java.sql.SQLException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import toby.spring.springbook.user.domain.User;

@SpringBootTest
class UserDaoTest {

	// 1.3.3 테스트
	@Test
	void test1() throws SQLException, ClassNotFoundException {
		ConnectionMaker connectionMaker = new DConnectionMaker();

		UserDao dao = new UserDao(connectionMaker);

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
	}

	// 1.4.1 테스트
	@Test
	void test2() throws SQLException, ClassNotFoundException {
		UserDao dao = new DaoFactory().userDao();

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
	}

	// 1.5.1 테스트
	@Test
	void test3() throws SQLException, ClassNotFoundException {
		ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
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
	}

	// 2.2.1 테스트
	@Test
	void test4() throws SQLException, ClassNotFoundException {
		ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
		UserDao dao = context.getBean("userDao", UserDao.class);

		User user = new User();
		user.setId("whiteship");
		user.setName("백기선");
		user.setPassword("married");

		dao.add(user);

		System.out.println(user.getId() + " 등록 성공");

		User user2 = dao.get(user.getId());

		if(!user.getName().equals(user2.getName())) {
			System.out.println("테스트 실패 (name)");
		} else if (!user.getPassword().equals(user2.getPassword())) {
			System.out.println("테스트 실패 (password)");
		} else {
			System.out.println("조회 테스트 성공");
		}
	}
}