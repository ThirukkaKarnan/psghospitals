package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class PropertyFileCreator {
    
    private static final String CONFIG_FILE = ".\\resources\\db.conf";
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver"; //"oracle.jdbc.driver.OracleDriver";
    private static final String DATABASE = "jdbc:mysql://127.0.0.1/psghospitals"; //"jdbc:oracle:thin:@<URL>:<port>:<Dbname>";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static final String VERIFICATION_QUERY = "select 1;"; //from dual;"; //for oracle 

    public static void reader() {
        try {
            FileReader reader = new FileReader(CONFIG_FILE);
            Properties props = new Properties();
            props.load(reader);
            
            String driver = props.getProperty("jdbcDriver");
            String database = props.getProperty("database");
            String username = props.getProperty("username");
            String password = props.getProperty("password");
            
            System.out.println(driver);
            System.out.println(database);
            System.out.println(username);
            System.out.println(password);
            
            reader.close();
        } catch (FileNotFoundException ex) {
            // file does not exist
            ex.printStackTrace();
        } catch (IOException ex) {
            // I/O error
            ex.printStackTrace();
        }
    }
    
    public static void main(String args[]) {
        
        File configFile = new File(CONFIG_FILE);
        try {
            Properties props = new Properties();
            props.setProperty("jdbcDriver", JDBC_DRIVER);
            props.setProperty("database", DATABASE);
            props.setProperty("username", USERNAME);
            props.setProperty("password", PASSWORD);
            props.setProperty("verificationQuery", VERIFICATION_QUERY);
            
            FileWriter writer = new FileWriter(configFile);
            props.store(writer, "DB Connection Parameters");
            writer.close();
        } catch (FileNotFoundException ex) {
            // file does not exist
            System.err.println("File not found");
        } catch (IOException ex) {
            // I/O error
        }
        reader();
    }
}
