package extracters;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Public static class to deal with Db functionalities
 * Add all those queries as individual static functions and call from the front end
 * 
 * TODO move all the queries to a file
 * @author Thirukka Karnan
 *
 */

public class SqlDbConnection {
    
    /**
     * To prevent creation of objects
     */
    private SqlDbConnection() {
        
    }
    
    public static final int OPStatisticsTotal(String mistaaYearMonth) throws Exception {
        String s="select ifnull(sum(MISTAA_OP_COUNT),0) from mistaa_overall_stat where mistaa_year_month = '"+mistaaYearMonth+"';";
        Connection conn = SqlDbConnectionFactory.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(s);
        int opOutput = 0;
        while(rs.next()) {
            opOutput = rs.getInt(1);
        }
        return opOutput;
    }
    
    public static final int OPStatisticsGeneral(String mistaaYearMonth) throws Exception {
        String s="select ifnull(sum(MISTAA_OP_COUNT_GEN),0) from mistaa_overall_stat where mistaa_year_month ='"+mistaaYearMonth+"';";
        Connection conn = SqlDbConnectionFactory.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(s);
        int output = 0;
        while(rs.next()) {
            output = rs.getInt(1);
        }
        return output;
    }
    
    public static final int OPStatisticsSpecial(String mistaaYearMonth) throws Exception {
        String s="select ifnull(sum(MISTAA_OP_COUNT_SPL),0),0) from mistaa_overall_stat where mistaa_year_month = '"+mistaaYearMonth+"';";
        Connection conn = SqlDbConnectionFactory.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(s);
        int opOutput = 0;
        while(rs.next()) {
            opOutput = rs.getInt(1);
        }
        return opOutput;
    }
    
    public static final int IPAdmissionsTotal(String mistaaYearMonth) throws Exception {
        String s="select ifnull(sum(MISTAA_IP_ADM_total),0) from mistaa_overall_stat where mistaa_year_month = '"+mistaaYearMonth+"';";
        Connection conn = SqlDbConnectionFactory.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(s);
        int opOutput = 0;
        while(rs.next()) {
            opOutput = rs.getInt(1);
        }
        //System.out.println(opOutput);
        return opOutput;
    }
    
    public static final int IPAdmissionsGeneral(String mistaaYearMonth) throws Exception {
        String s="select ifnull(sum(MISTAA_IP_ADM_GW),0) from mistaa_overall_stat where mistaa_year_month = '"+mistaaYearMonth+"';";
        Connection conn = SqlDbConnectionFactory.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(s);
        int opOutput = 0;
        while(rs.next()) {
            opOutput = rs.getInt(1);
        }
        //System.out.println(opOutput);
        return opOutput;
    }
    
    public static final int IPAdmissionsSemiPrivate(String mistaaYearMonth) throws Exception {
        String s="select ifnull(sum(MISTAA_IP_ADM_PW),0) from mistaa_overall_stat where mistaa_year_month = '"+mistaaYearMonth+"';";
        Connection conn = SqlDbConnectionFactory.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(s);
        int opOutput = 0;
        while(rs.next()) {
            opOutput = rs.getInt(1);
        }
        //System.out.println(opOutput);
        return opOutput;
    }
    
    public static final int IPAdmissionsSpecial(String mistaaYearMonth) throws Exception {
        String s="select ifnull(sum(MISTAA_IP_ADM_SW),0) from mistaa_overall_stat where mistaa_year_month = '"+mistaaYearMonth+"';";
        Connection conn = SqlDbConnectionFactory.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(s);
        int opOutput = 0;
        while(rs.next()) {
            opOutput = rs.getInt(1);
        }
        //System.out.println(opOutput);
        return opOutput;
    }
    
    public static void main(String args[]) throws Exception {
        String month = "201104";
        OPStatisticsTotal(month);
        //OPStatisticsGeneral(month);
        //OPStatisticsSpecial(month);
        IPAdmissionsTotal(month);
        IPAdmissionsGeneral(month);
        IPAdmissionsSemiPrivate(month);
        IPAdmissionsSpecial(month);
    }
}
