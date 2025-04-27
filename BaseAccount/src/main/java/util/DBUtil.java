package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    // DB 연결 정보
    private static final String URL = "jdbc:mysql://localhost:3306/jdbc_test";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    private static Connection connection = null;

    // DB 연결 메소드
    public static Connection getConnection() throws SQLException {
        try {
            // MySQL JDBC 드라이버 로드
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 연결 객체가 null이거나 닫혀 있는 경우에만 새로 생성
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("데이터베이스 연결 성공!");
            }
            return connection;
        } catch (ClassNotFoundException e) {
            // 드라이버 로드 실패 시 구체적인 예외 메시지와 함께 SQLException 발생
            throw new SQLException("MySQL JDBC 드라이버를 찾을 수 없습니다: " + e.getMessage());
        } catch (SQLException e) {
            // 연결 실패 시 원래 예외 그대로 전달
            System.err.println("데이터베이스 연결 실패: " + e.getMessage());
            throw e;
        }
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                    System.out.println("데이터베이스 연결 종료!");
                }
                connection = null;
            } catch (SQLException e) {
                System.err.println("연결 종료 오류: " + e.getMessage());
            }
        }
    }
}


//getConnection() 메소드가 이제 SQLException을 던지도록 변경하여 호출자가 예외 처리를 할 수 있게 함
//드라이버 로드 실패 시 더 구체적인 예외 메시지 제공
//이미 연결이 존재하고 열려있는 경우 재사용하도록 검사 로직 추가
//연결 성공 메시지 추가
//System.out.println 대신 오류 메시지는 System.err.println으로 출력
//closeConnection() 메소드에서 connection이 이미 닫혔는지 확인하는 검사 추가
//이렇게 수정하면 예외 처리가 더 명확해지고, 연결 관리도 더 안정적으로 할 수 있습니다.