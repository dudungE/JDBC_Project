package util;



import model.sql.DataSQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataInit {

    public static void initData() {
        String sql = DataSQL.INIT_DATA;

        try (Connection connection = DBUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            for (String query : DataSQL.INIT_DATA.split(";")) {
                if (!query.trim().isEmpty()) {
                    preparedStatement.executeUpdate(query);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
