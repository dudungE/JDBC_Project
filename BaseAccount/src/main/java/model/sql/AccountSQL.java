package model.sql;

public class AccountSQL {
    public static final String INSERT = "INSERT INTO account VALUES (?, ?, ?)";
    public static final String SELECT_ALL = "SELECT * FROM accounts";
    public static final String UPDATE = "UPDATE account SET dname=?, loc=? WHERE deptno=?";
    public static final String DELETE = "DELETE FROM account WHERE deptno=?";




}
