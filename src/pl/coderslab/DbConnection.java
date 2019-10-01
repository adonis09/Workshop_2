package pl.coderslab;

import java.sql.*;

public class DbConnection {

    public static Connection getConnection() throws SQLException {

        final String URL = "jdbc:mysql://localhost:3306/school?useSSL=false&characterEncoding=utf8&serverTimezone=UTC";
        final String USER = "root";
        final String PASSWORD = "coderslab";

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
