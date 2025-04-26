package model.sql;

public class SampleDepSql {
    public static final String INSERT = "INSERT INTO dept VALUES (?, ?, ?)";
    public static final String SELECT_ALL = "SELECT * FROM dept";
    public static final String UPDATE = "UPDATE dept SET dname=?, loc=? WHERE deptno=?";
    public static final String DELETE = "DELETE FROM dept WHERE deptno=?";
}
