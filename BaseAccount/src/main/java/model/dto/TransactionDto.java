package model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionDto {
    private int transactionId;
    private int accountId;
    private LocalDateTime transactionDate;
    private String transactionType;
    private BigDecimal amount;
    private BigDecimal balanceAfter;
    private String description;

    // 기본 생성자
    public TransactionDto() {
    }

    // 모든 필드를 포함한 생성자
    public TransactionDto(int transactionId, int accountId, LocalDateTime transactionDate,
                          String transactionType, BigDecimal amount, BigDecimal balanceAfter,
                          String description) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.transactionDate = transactionDate;
        this.transactionType = transactionType;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.description = description;
    }

    // Getter 및 Setter 메서드
    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getBalanceAfter() {
        return balanceAfter;
    }

    public void setBalanceAfter(BigDecimal balanceAfter) {
        this.balanceAfter = balanceAfter;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "TransactionDto{" +
                "transactionId=" + transactionId +
                ", accountId=" + accountId +
                ", transactionDate=" + transactionDate +
                ", transactionType='" + transactionType + '\'' +
                ", amount=" + amount +
                ", balanceAfter=" + balanceAfter +
                ", description='" + description + '\'' +
                '}';
    }
}