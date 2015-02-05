package extracters;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * Public Static final (singleton) class to store the state of the JDBC connection(connection 
 * parameters) and produce SQL connection objects, irrespective of the Db Vendor
 * 
 * Should be the only source of JDBC connections.
 * 
 * Db properties should be moved out to a separate file preferably XML and
 * the file should only be read within this class to get the Db credentials to avoid leakage.
 * 
 * @author Thirukka Karnan
 *
 */

public final class SqlDbConnectionFactory {
    
    /**
     * singleton object to produce connections, populated by the static initializer below.
     */
    private static final SqlDbConnectionFactory connectionFactory;
    
    static {
        /*// if these properties are not specified, the app will & should fail
        try {
            DbConnectionParams.initalizeDbProperties();
        } catch(Exception e) {
            //log fatal
        }*/
        connectionFactory = new SqlDbConnectionFactory();
        
        //verify the app's boot up
        try {
            getConnection();
        } catch(Exception e) {
            //log fatal
        }
    }

    public static Connection getConnection() throws Exception {
        return connectionFactory.getConnectionFromPool();
    }

    private SqlDbConnectionFactory() {
        
    }

    private Connection getConnectionFromPool() throws Exception {//implement pool
        Connection conn = null;
        try {
            String jdbcDriver = DbConnectionParams.jdbcDriver;
            Class.forName(jdbcDriver).newInstance();
            
            String database = DbConnectionParams.database;
            String username = DbConnectionParams.username;
            String password = DbConnectionParams.password;
            conn = DriverManager.getConnection(database, username, password);

            // verifying the connection object's quality
            String verificationQuery = DbConnectionParams.verificationQuery;
            Statement stmt = conn.createStatement();
            stmt.execute(verificationQuery);
            // stmt.execute("insert into mistaa_overall_stat_student (MISTAA_YEAR_MONTH) values (201104)");
        } catch (Exception e) {
            // log e
            e.printStackTrace();
            throw e;
        }
        return conn;
    }
    
    /**
     * To parse and get the connection parameters
     * @author Thirukka Karnan
     *
     */
    private static final class DbConnectionParams {
        private final static String jdbcDriver;
        private final static String database;
        private final static String username;
        private final static String password;
        private final static String verificationQuery;
        
        static {
            jdbcDriver = "com.mysql.jdbc.Driver";
            database = "";
            username = "";
            password = "";
            verificationQuery = "select 1";
        }
        
        /*private static final void initalizeDbProperties() throws Exception {
            // parse the xml and get the values
        }*/
    }
}
