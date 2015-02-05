package extracters;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public final class MySqlDbConnectionFactory {
	private final String jdbcDriver;
	private final String database;
	private final String username;
	private final String password;
	
	private static final MySqlDbConnectionFactory connectionFactory;
	static {
		connectionFactory = new MySqlDbConnectionFactory("", "", "", "");
	}
	
	private MySqlDbConnectionFactory(String jdbcDriver,String database, String username, String password) {
		this.jdbcDriver = jdbcDriver;//"com.mysql.jdbc.Driver";
		this.database = database;
		this.username = username;
		this.password = password;
	}
	
	public Connection getConnection() throws Exception {
		Connection conn = null;
		try{
			Class.forName(jdbcDriver).newInstance();
			conn = DriverManager.getConnection(database, username, password);

			//connection verification
			Statement stmt = conn.createStatement();
			stmt.execute("select 1;");
			//stmt.execute("insert into mistaa_overall_stat_student (MISTAA_YEAR_MONTH) values (201104)");
		} catch(Exception e) {
			//log e
			e.printStackTrace();
			throw e;
		}
		return conn;
	}
}
