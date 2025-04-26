package model.dao;

import model.dto.SampleDepDto;
import model.sql.SampleDepSql;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SampleDepDao {


    //C
    public int insertDep(SampleDepDto sampleDepDto) {
        String sql = SampleDepSql.INSERT;

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, sampleDepDto.getDeptno());

            preparedStatement.setString(2, sampleDepDto.getDname());
            preparedStatement.setString(3, sampleDepDto.getLoc());

            //영향을 받은 행의 갯수를 출력하게 된다.
            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }


    //R
    public List<SampleDepDto> getAllDeps() {
        String sql = SampleDepSql.SELECT_ALL;
        List<SampleDepDto> sampleDepDtos = new ArrayList<>();

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                SampleDepDto sampleDepDto = new SampleDepDto();
                sampleDepDto.setDeptno(resultSet.getInt("deptno"));
                sampleDepDto.setDname(resultSet.getString("dname"));
                sampleDepDto.setLoc(resultSet.getString("loc"));

                sampleDepDtos.add(sampleDepDto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sampleDepDtos;
    }

    //U

    //D
}
