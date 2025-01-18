package toby.spring.springbook.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

// 3-21 JDBC 작업 흐름을 분리해서 만든 JdbcContext 클래스
public class JdbcContext {

	private ConnectionMaker connectionMaker;

	public void setConnectionMaker(ConnectionMaker connectionMaker) {
		this.connectionMaker = connectionMaker;
	}

	public void workWithStatementStrategy(StatementStrategy stmt)
		throws SQLException, ClassNotFoundException {
		Connection c = null;
		PreparedStatement ps = null;

		try {
			c = this.connectionMaker.makeConnection();

			ps = stmt.makePreparedStatement(c);

			ps.executeUpdate();
		} catch (ClassNotFoundException e) {
			throw e;
		} catch (SQLException e) {
			throw e;
		} finally {
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
}
