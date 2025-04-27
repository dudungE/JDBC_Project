package model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

import model.dto.AccountDto;
import model.sql.AccountSQL;
import util.DBUtil;

public class AccountDao {

    // 모든 계좌 조회
    public List<AccountDto> findAll() {
        List<AccountDto> accounts = new ArrayList<>();
        String sql = AccountSQL.SELECT_ALL;

        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                AccountDto account = new AccountDto();
                account.setAccountId(rs.getInt("account_id"));
                account.setCustomerId(rs.getInt("customer_id"));
                account.setAccountType(rs.getString("account_type"));
                account.setBalance(rs.getBigDecimal("balance"));
                account.setDateOpened(rs.getDate("date_opened").toLocalDate());
                account.setStatus(rs.getString("status"));
                accounts.add(account);
            }
        } catch (SQLException e) {
            System.err.println("계좌 정보 조회 중 오류 발생: " + e.getMessage());
            System.out.println(e.getStackTrace());
        }

        return accounts;
    }

    // ID로 계좌 조회
    public AccountDto findById(int accountId) {
        String sql = "SELECT * FROM accounts WHERE account_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, accountId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    AccountDto account = new AccountDto();
                    account.setAccountId(rs.getInt("account_id"));
                    account.setCustomerId(rs.getInt("customer_id"));
                    account.setAccountType(rs.getString("account_type"));
                    account.setBalance(rs.getBigDecimal("balance"));
                    account.setDateOpened(rs.getDate("date_opened").toLocalDate());
                    account.setStatus(rs.getString("status"));
                    return account;
                }
            }
        } catch (SQLException e) {
            System.err.println("ID로 계좌 조회 중 오류 발생: " + e.getMessage());
        }

        return null;
    }

    // 특정 고객의 모든 계좌 조회
    public List<AccountDto> findByCustomerId(int customerId) {
        List<AccountDto> accounts = new ArrayList<>();
        String sql = "SELECT * FROM accounts WHERE customer_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, customerId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    AccountDto account = new AccountDto();
                    account.setAccountId(rs.getInt("account_id"));
                    account.setCustomerId(rs.getInt("customer_id"));
                    account.setAccountType(rs.getString("account_type"));
                    account.setBalance(rs.getBigDecimal("balance"));
                    account.setDateOpened(rs.getDate("date_opened").toLocalDate());
                    account.setStatus(rs.getString("status"));
                    accounts.add(account);
                }
            }
        } catch (SQLException e) {
            System.err.println("고객 ID로 계좌 조회 중 오류 발생: " + e.getMessage());
        }

        return accounts;
    }

    // 새 계좌 추가
    public int save(AccountDto account) {
        String sql = "INSERT INTO accounts (customer_id, account_type, balance, date_opened, status) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, account.getCustomerId());
            pstmt.setString(2, account.getAccountType());
            pstmt.setBigDecimal(3, account.getBalance());
            pstmt.setDate(4, Date.valueOf(account.getDateOpened()));
            pstmt.setString(5, account.getStatus());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("계좌 추가 실패, 영향받은 행이 없습니다.");
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("계좌 추가 실패, ID를 가져올 수 없습니다.");
                }
            }
        } catch (SQLException e) {
            System.err.println("계좌 추가 중 오류 발생: " + e.getMessage());
        }

        return -1; // 실패 시 -1 반환
    }

    // 계좌 정보 업데이트
    public boolean update(AccountDto account) {
        String sql = "UPDATE accounts SET customer_id = ?, account_type = ?, balance = ?, date_opened = ?, status = ? WHERE account_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, account.getCustomerId());
            pstmt.setString(2, account.getAccountType());
            pstmt.setBigDecimal(3, account.getBalance());
            pstmt.setDate(4, Date.valueOf(account.getDateOpened()));
            pstmt.setString(5, account.getStatus());
            pstmt.setInt(6, account.getAccountId());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("계좌 정보 업데이트 중 오류 발생: " + e.getMessage());
            return false;
        }
    }

    // 계좌 잔액 업데이트 (트랜잭션 처리 추가)
    public boolean updateBalance(int accountId, BigDecimal newBalance) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean autoCommit = false;

        try {
            conn = DBUtil.getConnection();
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false); // 트랜잭션 시작

            String sql = "UPDATE accounts SET balance = ? WHERE account_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setBigDecimal(1, newBalance);
            pstmt.setInt(2, accountId);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                conn.commit(); // 트랜잭션 커밋
                return true;
            } else {
                conn.rollback(); // 트랜잭션 롤백
                return false;
            }
        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback(); // 예외 발생 시 롤백
                }
            } catch (SQLException ex) {
                System.err.println("롤백 중 오류 발생: " + ex.getMessage());
            }
            System.err.println("계좌 잔액 업데이트 중 오류 발생: " + e.getMessage());
            return false;
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) {
                    conn.setAutoCommit(autoCommit); // 원래 상태로 복원
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("리소스 해제 중 오류 발생: " + e.getMessage());
            }
        }
    }

    // 계좌 삭제
    public boolean delete(int accountId) {
        String sql = "DELETE FROM accounts WHERE account_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, accountId);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("계좌 삭제 중 오류 발생: " + e.getMessage());
            return false;
        }
    }
}