package util;
import java.sql.*;



public class DBUtil {
//    public static final String DB_HOST = "jdbc:mysql://localhost:3306/mysql";
public static final String DB_HOST = "jdbc:mysql://localhost:3306/jdbc_test";
    public static final String DB_NAME = "root";
    public static final String DB_PW = "1234";

//    static String URL = System.getenv(DB_HOST);
//    static String DB_USER = System.getenv(DB_NAME);
//    static String DB_PASSWORD = System.getenv(DB_PW);

    // DB 연결 메서드
    public static Connection getConnection() throws SQLException {
//        return DriverManager.getConnection(URL, DB_USER, DB_PASSWORD);
        return DriverManager.getConnection(DB_HOST, DB_NAME, DB_PW);
    }
}
