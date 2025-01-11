package toby.spring.springbook.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import toby.spring.springbook.user.domain.User;

public class UserDao {

	private ConnectionMaker connectionMaker;

	// 생성자를 통한 의존관계 주입
	public UserDao(ConnectionMaker connectionMaker) {
		this.connectionMaker = connectionMaker;
	}

	// 수정자(setter) 메소드를 이용한 주입
	/*
	public void setConnectionMaker(ConnectionMaker connectionMaker) {
		this.connectionMaker = connectionMaker;
	}
	*/

	public void add(User user) throws ClassNotFoundException, SQLException {
		Connection c = connectionMaker.makeConnection();

		PreparedStatement ps = c.prepareStatement("insert into users values(?,?,?)");
		ps.setString(1, user.getId());
		ps.setString(2, user.getName());
		ps.setString(3, user.getPassword());

		ps.executeUpdate();

		ps.close();
		c.close();
	}

	public User get(String id) throws ClassNotFoundException, SQLException {
		Connection c = connectionMaker.makeConnection();

		PreparedStatement ps = c.prepareStatement("select * from users where id = ?");
		ps.setString(1, id);

		ResultSet rs = ps.executeQuery();
		rs.next();
		User user = new User();
		user.setId(rs.getString("id"));
		user.setName(rs.getString("name"));
		user.setPassword(rs.getString("password"));

		rs.close();
		ps.close();
		c.close();

		return user;
	}

	public void deleteAll() throws SQLException, ClassNotFoundException {
		Connection c = connectionMaker.makeConnection();

		PreparedStatement ps = c.prepareStatement("delete from users");

		ps.executeUpdate();

		ps.close();
		c.close();
	}

	public int getCount() throws SQLException, ClassNotFoundException {
		Connection c = connectionMaker.makeConnection();

		PreparedStatement ps = c.prepareStatement("select count(*) from users");

		ResultSet rs = ps.executeQuery();
		rs.next();
		int count = rs.getInt(1);

		rs.close();
		ps.close();
		c.close();

		return count;
	}
}
