package extracters;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;

/**
 * Public Static (singleton) final class to store the state of the JDBC connection(connection 
 * parameters) and produce SQL connection objects, irrespective of the Db Vendor
 * 
 * Should be the only source of JDBC connections.
 * 
 * Db properties is moved out to a separate file preferably XML.
 * TODO Move all properties to a key value pair outside this class.
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
            
            //TODO get directly from the map and remove 
            String database = DbConnectionParams.database;
            String username = DbConnectionParams.username;
            String password = DbConnectionParams.password;
            conn = DriverManager.getConnection(database, username, password);

            // verifying the connection object's quality
            String verificationQuery = DbConnectionParams.verificationQuery;
            Statement stmt = conn.createStatement();
            stmt.execute(verificationQuery);
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
        private static final File configFile = new File(".\\resources\\db.conf");
        //TODO To read from XML file
        static {
            
            try(FileReader reader = new FileReader(configFile);) {
                Properties props = new Properties();
                props.load(reader);
                database = props.getProperty("database");
                jdbcDriver = props.getProperty("jdbcDriver");
                username = props.getProperty("username");
                password = props.getProperty("password");
                verificationQuery = props.getProperty("verificationQuery");
            } catch(FileNotFoundException e) {
                //throw fatal
                System.err.println("File Not Found!! Check for the config file::"+ configFile);
                e.printStackTrace();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        
    }
}
