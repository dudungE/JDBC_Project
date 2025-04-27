import java.sql.Connection;
import java.sql.SQLException;

import controller.AccountController;
import util.DBUtil;
import util.DataInit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;




public class Main {
    public static void main(String[] args) {

        AccountController accountController = new AccountController();

        DataInit.initData();

        accountController.findAllAccount();







    }
}
