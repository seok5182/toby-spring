package toby.spring.springbook.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import toby.spring.springbook.user.domain.User;

public class UserDao {

	private ConnectionMaker connectionMaker;

	// 3-22 JdbcContext를 DI 받아서 사용하도록 만든 UserDao
	// JdbcContext를 DI 받도록 만듬
	private JdbcContext jdbcContext;

	// 생성자를 통한 의존관계 주입
	public UserDao(ConnectionMaker connectionMaker) {
		this.jdbcContext = new JdbcContext();

		this.jdbcContext.setConnectionMaker(connectionMaker);

		this.connectionMaker = connectionMaker;
	}

	// 수정자(setter) 메소드를 이용한 주입
	/*
	public void setConnectionMaker(ConnectionMaker connectionMaker) {
		this.connectionMaker = connectionMaker;
	}
	*/

	// 리스트 3-15 user 정보를 AddStatement에 전달해주는 add() 메소드
	public void add(final User user) throws ClassNotFoundException, SQLException {
		// DI 받은 JdbcContext의 컨텍스트 메소드를 사용하도록 변경한다.
		this.jdbcContext.workWithStatementStrategy(
			new StatementStrategy() {
				public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
					PreparedStatement ps = c.prepareStatement(
						"insert into users(id, name, password) values(?,?,?)");
					ps.setString(1, user.getId());
					ps.setString(2, user.getName());
					ps.setString(3, user.getPassword());

					return ps;
				}
			}
		);

		// 리스트 3-16 add() 메소드 내의 로컬 클래스로 이전한 AddStatement
		// 리스트 3-17 add() 메소드의 로컬 변수를 직접 사용하도록 수정한 AddStatement
		// 내부 클래스에서 외부의 변수를 사용할 때는 외부 변수는 반드시 final로 선언해야 한다.(user 파라미터는 메소드 내부에서 변경될 일이 없으므로 final로 선언해도 무방)
		// 리스트 3-18 AddStatement를 익명 내부 클래스로 전환
//		jdbcContextWithStatementStrategy(
//			new StatementStrategy() {
//				public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
//					PreparedStatement ps = c.prepareStatement("insert into users(id, name, password) values(?,?,?)");
//					ps.setString(1, user.getId());
//					ps.setString(2, user.getName());
//					ps.setString(3, user.getPassword());
//
//					return ps;
//				}
//			}
//		);
//		StatementStrategy st = new AddStatement();
//		jdbcContextWithStatementStrategy(st);
	}

	public User get(String id) throws ClassNotFoundException, SQLException {
		Connection c = connectionMaker.makeConnection();

		PreparedStatement ps = c.prepareStatement("select * from users where id = ?");
		ps.setString(1, id);

		ResultSet rs = ps.executeQuery();
//		rs.next();
//		User user = new User();
//		user.setId(rs.getString("id"));
//		user.setName(rs.getString("name"));
//		user.setPassword(rs.getString("password"));

		// 리스트 2-14 데이터를 찾지 못하면 예외를 발생시키도록 수정한 get() 메소드
		// User는 null 상태로 초기화
		User user = null;
		// id를 조건으로 한 쿼리의 결과가 있으면 User 오브젝트를 만들고 값을 넣어준다.
		if (rs.next()) {
			user = new User();
			user.setId(rs.getString("id"));
			user.setName(rs.getString("name"));
			user.setPassword(rs.getString("password"));
		}

		rs.close();
		ps.close();
		c.close();

		// 결과가 없으면 User는 null 상태 그대로일 것
		// 이를 확인해서 예외를 던져준다.
		if (user == null) {
			throw new SQLException("User not found");
		}

		return user;
	}

	// 리스트 3-27 변하지 않는 부분을 분리시킨 deleteAll() 메소드
	public void deleteAll() throws SQLException, ClassNotFoundException {
		executeSql("delete from users");
	}

	public int getCount() throws SQLException, ClassNotFoundException {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			c = connectionMaker.makeConnection();

			ps = c.prepareStatement("select count(*) from users");

			rs = ps.executeQuery();
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
				}
			}
			if (c != null) {
				try {
					c.close();
				} catch (SQLException e) {
				}
			}
		}
	}

	private void executeSql(final String query) throws ClassNotFoundException, SQLException {
		this.jdbcContext.workWithStatementStrategy(
			new StatementStrategy() {
				public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
					return c.prepareStatement(query);
				}
			}
		);
	}
}
