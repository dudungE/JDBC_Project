package model.dao;

import model.dto.SampleDepDto;
import model.sql.SampleDepSql;
import model.sql.SampleDepDataInitSQL;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SampleDepDao {

    // 데이터 init 객체
    public void initDep() {

        String sql = SampleDepDataInitSQL.INIT_DEPT;

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ) {

            for (String query : SampleDepDataInitSQL.INIT_DEPT.split(";")) {
                if (!query.trim().isEmpty()) {
                    preparedStatement.executeUpdate(query);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    //C
    // primary key 중복되는 경우 예외 처리하기
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

            if (e.getErrorCode() == 1062) {
                System.out.println("중복된 부서 번호입니다: " + sampleDepDto.getDeptno());
                return -2;
            } else {
                e.printStackTrace();
                return -1; // 오류 발생 시 -1 출력
            }

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
    public int updateDep(int updateDeptno, String updateDname, String updateLoc) {
        String sql = SampleDepSql.UPDATE;

        try (Connection connection = DBUtil.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, updateDname);
            preparedStatement.setString(2, updateLoc);
            preparedStatement.setInt(3, updateDeptno);

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }


    //D
    public int deleteDep(int deleteDeptno) {
        String sql = SampleDepSql.DELETE;

        try (Connection connection = DBUtil.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, deleteDeptno);

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }

    }



}
