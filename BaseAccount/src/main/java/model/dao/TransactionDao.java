package model.dao;

import model.dto.AccountDto;
import model.dto.TransactionDto;
import util.DBUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionDao {

    // 모든 거래 조회
    public List<TransactionDto> findAll() {
        List<TransactionDto> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions";

        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                TransactionDto transaction = mapResultSetToTransaction(rs);
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            System.err.println("거래 정보 조회 중 오류 발생: " + e.getMessage());
        }

        return transactions;
    }

    // ID로 거래 조회
    public TransactionDto findById(int transactionId) {
        String sql = "SELECT * FROM transactions WHERE transaction_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, transactionId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToTransaction(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("ID로 거래 조회 중 오류 발생: " + e.getMessage());
        }

        return null;
    }

    // 특정 계좌의 모든 거래 조회
    public List<TransactionDto> findByAccountId(int accountId) {
        List<TransactionDto> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE account_id = ? ORDER BY transaction_date DESC";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, accountId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    TransactionDto transaction = mapResultSetToTransaction(rs);
                    transactions.add(transaction);
                }
            }
        } catch (SQLException e) {
            System.err.println("계좌 ID로 거래 조회 중 오류 발생: " + e.getMessage());
        }

        return transactions;
    }

    // 새 거래 기록 추가 (트랜잭션 처리 추가)
    public int save(TransactionDto transaction, AccountDto account) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean autoCommit = false;

        try {
            conn = DBUtil.getConnection();
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false); // 트랜잭션 시작

            // 1. 거래 기록 추가
            String sql = "INSERT INTO transactions (account_id, transaction_date, transaction_type, amount, balance_after, description) VALUES (?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, transaction.getAccountId());
            pstmt.setTimestamp(2, Timestamp.valueOf(transaction.getTransactionDate()));
            pstmt.setString(3, transaction.getTransactionType());
            pstmt.setBigDecimal(4, transaction.getAmount());
            pstmt.setBigDecimal(5, transaction.getBalanceAfter());
            pstmt.setString(6, transaction.getDescription());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                conn.rollback();
                throw new SQLException("거래 기록 추가 실패, 영향받은 행이 없습니다.");
            }

            int generatedId = -1;

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    generatedId = generatedKeys.getInt(1);
                } else {
                    conn.rollback();
                    throw new SQLException("거래 기록 추가 실패, ID를 가져올 수 없습니다.");
                }
            }

            // 2. 계좌 잔액 업데이트
            pstmt.close();
            sql = "UPDATE accounts SET balance = ? WHERE account_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setBigDecimal(1, transaction.getBalanceAfter());
            pstmt.setInt(2, transaction.getAccountId());

            affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                conn.rollback();
                throw new SQLException("계좌 잔액 업데이트 실패");
            }

            // 모든 작업이 성공하면 커밋
            conn.commit();
            return generatedId;

        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback(); // 예외 발생 시 롤백
                }
            } catch (SQLException ex) {
                System.err.println("롤백 중 오류 발생: " + ex.getMessage());
            }
            System.err.println("거래 추가 중 오류 발생: " + e.getMessage());
            return -1;
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

    // 단순 거래 기록 추가 (계좌 잔액 업데이트 없음)
    public int saveOnly(TransactionDto transaction) {
        String sql = "INSERT INTO transactions (account_id, transaction_date, transaction_type, amount, balance_after, description) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, transaction.getAccountId());
            pstmt.setTimestamp(2, Timestamp.valueOf(transaction.getTransactionDate()));
            pstmt.setString(3, transaction.getTransactionType());
            pstmt.setBigDecimal(4, transaction.getAmount());
            pstmt.setBigDecimal(5, transaction.getBalanceAfter());
            pstmt.setString(6, transaction.getDescription());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("거래 기록 추가 실패, 영향받은 행이 없습니다.");
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("거래 기록 추가 실패, ID를 가져올 수 없습니다.");
                }
            }
        } catch (SQLException e) {
            System.err.println("거래 기록 추가 중 오류 발생: " + e.getMessage());
        }

        return -1; // 실패 시 -1 반환
    }

    // 특정 기간 내의 거래 기록 조회
    public List<TransactionDto> findByDateRange(int accountId, LocalDateTime startDate, LocalDateTime endDate) {
        List<TransactionDto> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE account_id = ? AND transaction_date BETWEEN ? AND ? ORDER BY transaction_date DESC";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, accountId);
            pstmt.setTimestamp(2, Timestamp.valueOf(startDate));
            pstmt.setTimestamp(3, Timestamp.valueOf(endDate));

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    TransactionDto transaction = mapResultSetToTransaction(rs);
                    transactions.add(transaction);
                }
            }
        } catch (SQLException e) {
            System.err.println("날짜 범위로 거래 조회 중 오류 발생: " + e.getMessage());
        }

        return transactions;
    }

    // 특정 거래 유형별 조회
    public List<TransactionDto> findByTransactionType(int accountId, String transactionType) {
        List<TransactionDto> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE account_id = ? AND transaction_type = ? ORDER BY transaction_date DESC";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, accountId);
            pstmt.setString(2, transactionType);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    TransactionDto transaction = mapResultSetToTransaction(rs);
                    transactions.add(transaction);
                }
            }
        } catch (SQLException e) {
            System.err.println("거래 유형별 조회 중 오류 발생: " + e.getMessage());
        }

        return transactions;
    }

    // ResultSet을 TransactionDto 객체로 매핑하는 도우미 메서드
    private TransactionDto mapResultSetToTransaction(ResultSet rs) throws SQLException {
        TransactionDto transaction = new TransactionDto();
        transaction.setTransactionId(rs.getInt("transaction_id"));
        transaction.setAccountId(rs.getInt("account_id"));
        transaction.setTransactionDate(rs.getTimestamp("transaction_date").toLocalDateTime());
        transaction.setTransactionType(rs.getString("transaction_type"));
        transaction.setAmount(rs.getBigDecimal("amount"));
        transaction.setBalanceAfter(rs.getBigDecimal("balance_after"));
        transaction.setDescription(rs.getString("description"));
        return transaction;
    }
}