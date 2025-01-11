package toby.spring.springbook.user.dao;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.SQLException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import toby.spring.springbook.user.domain.User;

@SpringBootTest
class UserDaoTest {

	@Autowired
	private UserDao userDao;

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

	// 2.2.1 테스트 검증의 자동화
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

		if (!user.getName().equals(user2.getName())) {
			System.out.println("테스트 실패 (name)");
		} else if (!user.getPassword().equals(user2.getPassword())) {
			System.out.println("테스트 실패 (password)");
		} else {
			System.out.println("조회 테스트 성공");
		}
	}

	// 2.2.2 테스트의 효율적인 수행과 결과 관리
	@Test
	void addAndGet() throws SQLException, ClassNotFoundException {
		ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
		UserDao dao = context.getBean("userDao", UserDao.class);

		// 2.3.3 get() 테스트 기능을 보완한 addAndGet() 테스트(리스트 2-12)
		User user1 = new User("gyumee", "박성철", "springno1");
		User user2 = new User("leegw700", "이길원", "springno2");

		// 2.3.2 테스트 결과의 일관성(deleteAll()과 getCount() 추가)
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));

		dao.add(user1);
		dao.add(user2);
		assertThat(dao.getCount(), is(2));

		User userget1 = dao.get(user1.getId());
		assertThat(userget1.getName(), is(user1.getName()));
		assertThat(userget1.getPassword(), is(user1.getPassword()));

		User userget2 = dao.get(user2.getId());
		assertThat(userget2.getName(), is(user2.getName()));
		assertThat(userget2.getPassword(), is(user2.getPassword()));
	}

	// 2.3.3 테스트(리스트 2-11)
	@Test
	void count() throws SQLException, ClassNotFoundException {
		ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
		UserDao dao = context.getBean("userDao", UserDao.class);

		User user1 = new User("gyumee", "박성철", "springno1");
		User user2 = new User("leegw700", "이길원", "springno2");
		User user3 = new User("bumjin", "박범진", "springno3");

		dao.deleteAll();
		assertThat(dao.getCount(), is(0));

		dao.add(user1);
		assertThat(dao.getCount(), is(1));

		dao.add(user2);
		assertThat(dao.getCount(), is(2));

		dao.add(user3);
		assertThat(dao.getCount(), is(3));
	}

	// 2.3.3 테스트(리스트 2-13)
	@Test
	void getUserFailure() throws SQLException, ClassNotFoundException {
		ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
		UserDao dao = context.getBean("userDao", UserDao.class);

		dao.deleteAll();
		assertThat(dao.getCount(), is(0));

		assertThrows(SQLException.class, () -> dao.get("unknown_id"));
	}
}