package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

    // DB 연결 정보
    private static final String URL = "jdbc:mysql://localhost:3306/jdbc_test";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    // DB 연결 메소드
    public static Connection getConnection() {
        try {
            // MySQL JDBC 드라이버 로드 (생략해도 되지만 명시적으로 작성하면 안정적)
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 연결 객체 반환
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null; // 연결 실패 시 null 반환
        }
    }
}


