import java.sql.Connection;
import java.sql.SQLException;
import util.DBUtil;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;




public class Main {
    public static void main(String[] args) {
        try (Connection conn = DBUtil.getConnection()) {
            if (conn != null) {
                System.out.println("DB 연결 성공!");
            } else {
                System.out.println("DB 연결 실패...");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }



    }
}
