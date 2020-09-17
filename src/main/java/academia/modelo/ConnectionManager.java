package academia.modelo;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionManager {

	public static Connection getConnection() throws SQLException, ClassNotFoundException, NamingException {

		Connection con = null;

		Class.forName("com.mysql.jdbc.Driver");

		InitialContext initCtx = new InitialContext();
		Context envCtx = (Context) initCtx.lookup("java:comp/env");
		DataSource dataSource = (DataSource) envCtx.lookup("jdbc/super");

		con = dataSource.getConnection();

		return con;
	}

}
