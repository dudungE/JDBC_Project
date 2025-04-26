package model.sql;

public class SampleDepDataInitSQL {

    public static final String INIT_DEPT =
            "DROP TABLE IF EXISTS dept;\n" +
                    "CREATE TABLE dept (\n" +
                    "    deptno INT PRIMARY KEY,\n" +
                    "    dname VARCHAR(10) NOT NULL,\n" +
                    "    loc VARCHAR(10) NOT NULL\n" +
                    ");\n" +
                    "INSERT INTO dept (deptno, dname, loc) VALUES\n" +
                    "(101, 'Alice', 'Seoul'),\n" +
                    "(102, 'Bob', 'Busan'),\n" +
                    "(103, 'Charlie', 'Incheon'),\n" +
                    "(104, 'Diana', 'Daegu'),\n" +
                    "(105, 'Ethan', 'Daejeon'),\n" +
                    "(106, 'Fiona', 'Gwangju'),\n" +
                    "(107, 'George', 'Suwon'),\n" +
                    "(108, 'Hannah', 'Ulsan'),\n" +
                    "(109, 'Ian', 'Jeonju'),\n" +
                    "(110, 'Julia', 'Changwon');";
}



