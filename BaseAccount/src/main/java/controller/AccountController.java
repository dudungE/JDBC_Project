package controller;

import model.dto.AccountDto;
import service.AccountService;

import java.util.List;

public class AccountController {

    private final AccountService accountService;

    public AccountController() {
        this.accountService = new AccountService();
    }

    public void findAllAccount() {
        List<AccountDto> accountList = accountService.findAllAccounts();
        // 헤더 출력
        System.out.printf("%-10s %-10s %-15s %-15s %-15s %-10s%n",
                "계좌ID", "고객ID", "계좌종류", "잔액", "개설일", "상태");

        accountList.forEach(a -> {
            System.out.printf("%-10d %-10d %-15s %,-15.2f %-15tF %-10s%n",
                    a.getAccountId(),
                    a.getCustomerId(),
                    a.getAccountType(),
                    a.getBalance().doubleValue(),  // BigDecimal -> double 변환
                    a.getDateOpened(),
                    a.getStatus());
        });
    }





}
