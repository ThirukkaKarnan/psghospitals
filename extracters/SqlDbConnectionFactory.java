package extracters;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;


public class SqlDbConnectionFactory {
	
	private final String jdbcDriver;
	private final String database;
	private final String username;
	private final String password;
	
	private static SqlDbConnectionFactory connectionFactory;
	static {
			Properties connParam = getDbProperties(); //if these properties not specified, the app should fail
			String jdbcDriver = connParam.getProperty("jdbcDriver");
			String database = connParam.getProperty("database");
			String username = connParam.getProperty("username");
			String password = connParam.getProperty("password");
			connectionFactory = new SqlDbConnectionFactory(jdbcDriver, database, username, password);
	}
	
	private final static Properties getDbProperties() {
		// parse the xml and get the values
		return null;
	}
	
	public static Connection getConnection() throws Exception {
		return connectionFactory.getConnectionFromPool();
	}
	
	private SqlDbConnectionFactory(String jdbcDriver,String database, String username, String password) {
		this.jdbcDriver = jdbcDriver;//"com.mysql.jdbc.Driver";
		this.database = database;
		this.username = username;
		this.password = password;
	}
	
	private Connection getConnectionFromPool() throws Exception {
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
