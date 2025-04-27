package model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AccountDto {
    private int accountId;
    private int customerId;
    private String accountType;
    private BigDecimal balance;
    private LocalDate dateOpened;
    private String status;

    // 기본 생성자
    public AccountDto() {
    }

    // 모든 필드를 포함한 생성자
    public AccountDto(int accountId, int customerId, String accountType, BigDecimal balance,
                      LocalDate dateOpened, String status) {
        this.accountId = accountId;
        this.customerId = customerId;
        this.accountType = accountType;
        this.balance = balance;
        this.dateOpened = dateOpened;
        this.status = status;
    }

    // Getter 및 Setter 메서드
    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public LocalDate getDateOpened() {
        return dateOpened;
    }

    public void setDateOpened(LocalDate dateOpened) {
        this.dateOpened = dateOpened;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AccountDto{" +
                "accountId=" + accountId +
                ", customerId=" + customerId +
                ", accountType='" + accountType + '\'' +
                ", balance=" + balance +
                ", dateOpened=" + dateOpened +
                ", status='" + status + '\'' +
                '}';
    }
}