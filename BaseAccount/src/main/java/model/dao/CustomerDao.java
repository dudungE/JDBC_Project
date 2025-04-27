package model.dao;


import model.dto.CustomerDto;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDao {

    // 모든 고객 조회
    public List<CustomerDto> findAll() {
        List<CustomerDto> customers = new ArrayList<>();
        String sql = "SELECT * FROM customers";

        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                CustomerDto customer = new CustomerDto();
                customer.setCustomerId(rs.getInt("customer_id"));
                customer.setName(rs.getString("name"));
                customer.setEmail(rs.getString("email"));
                customer.setPhone(rs.getString("phone"));
                customer.setAddress(rs.getString("address"));
                customer.setDateOfBirth(rs.getDate("date_of_birth") != null ?
                        rs.getDate("date_of_birth").toLocalDate() : null);
                customers.add(customer);
            }
        } catch (SQLException e) {
            System.err.println("고객 정보 조회 중 오류 발생: " + e.getMessage());
        }

        return customers;
    }

    // ID로 고객 조회
    public CustomerDto findById(int customerId) {
        String sql = "SELECT * FROM customers WHERE customer_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, customerId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    CustomerDto customer = new CustomerDto();
                    customer.setCustomerId(rs.getInt("customer_id"));
                    customer.setName(rs.getString("name"));
                    customer.setEmail(rs.getString("email"));
                    customer.setPhone(rs.getString("phone"));
                    customer.setAddress(rs.getString("address"));
                    customer.setDateOfBirth(rs.getDate("date_of_birth") != null ?
                            rs.getDate("date_of_birth").toLocalDate() : null);
                    return customer;
                }
            }
        } catch (SQLException e) {
            System.err.println("ID로 고객 조회 중 오류 발생: " + e.getMessage());
        }

        return null;
    }

    // 이메일로 고객 조회
    public CustomerDto findByEmail(String email) {
        String sql = "SELECT * FROM customers WHERE email = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    CustomerDto customer = new CustomerDto();
                    customer.setCustomerId(rs.getInt("customer_id"));
                    customer.setName(rs.getString("name"));
                    customer.setEmail(rs.getString("email"));
                    customer.setPhone(rs.getString("phone"));
                    customer.setAddress(rs.getString("address"));
                    customer.setDateOfBirth(rs.getDate("date_of_birth") != null ?
                            rs.getDate("date_of_birth").toLocalDate() : null);
                    return customer;
                }
            }
        } catch (SQLException e) {
            System.err.println("이메일로 고객 조회 중 오류 발생: " + e.getMessage());
        }

        return null;
    }

    // 새 고객 추가
    public int save(CustomerDto customer) {
        String sql = "INSERT INTO customers (name, email, phone, address, date_of_birth) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, customer.getName());
            pstmt.setString(2, customer.getEmail());
            pstmt.setString(3, customer.getPhone());
            pstmt.setString(4, customer.getAddress());
            pstmt.setDate(5, customer.getDateOfBirth() != null ?
                    Date.valueOf(customer.getDateOfBirth()) : null);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("고객 추가 실패, 영향받은 행이 없습니다.");
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("고객 추가 실패, ID를 가져올 수 없습니다.");
                }
            }
        } catch (SQLException e) {
            System.err.println("고객 추가 중 오류 발생: " + e.getMessage());
        }

        return -1; // 실패 시 -1 반환
    }

    // 고객 정보 업데이트
    public boolean update(CustomerDto customer) {
        String sql = "UPDATE customers SET name = ?, email = ?, phone = ?, address = ?, date_of_birth = ? WHERE customer_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, customer.getName());
            pstmt.setString(2, customer.getEmail());
            pstmt.setString(3, customer.getPhone());
            pstmt.setString(4, customer.getAddress());
            pstmt.setDate(5, customer.getDateOfBirth() != null ?
                    Date.valueOf(customer.getDateOfBirth()) : null);
            pstmt.setInt(6, customer.getCustomerId());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("고객 정보 업데이트 중 오류 발생: " + e.getMessage());
            return false;
        }
    }

    // 고객 삭제
    public boolean delete(int customerId) {
        String sql = "DELETE FROM customers WHERE customer_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, customerId);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("고객 삭제 중 오류 발생: " + e.getMessage());
            return false;
        }
    }
}