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
    
    /**
     * singleton object to contain the Db Connection parameters and properties
     */
    private static final DbConnectionParams dbConnectionParams;
    static {
        // if these properties are not specified, the app will & should fail
        dbConnectionParams = new DbConnectionParams();
        try {
            dbConnectionParams.initalizeDbProperties();
        } catch(Exception e) {
            //log fatal
        }
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
            String jdbcDriver = dbConnectionParams.jdbcDriver;
            Class.forName(jdbcDriver).newInstance();
            
            String database = dbConnectionParams.database;
            String username = dbConnectionParams.username;
            String password = dbConnectionParams.password;
            conn = DriverManager.getConnection(database, username, password);

            // verifying the connection object's quality
            String verificationQuery = dbConnectionParams.verificationQuery;
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
        private static String jdbcDriver;
        private static String database;
        private static String username;
        private static String password;
        private static String verificationQuery;
        
        private static final void initalizeDbProperties() throws Exception {
            // parse the xml and get the values
            
        }
    }
}
