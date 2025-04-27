package service;

import model.dao.AccountDao;
import model.dto.AccountDto;

import java.util.List;

public class AccountService {

    private final AccountDao accountDao;

    public AccountService() {this.accountDao = new AccountDao();}

    public List<AccountDto> findAllAccounts() {
        return accountDao.findAll();
    }





}
