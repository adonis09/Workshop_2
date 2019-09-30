package pl.coderslab;

import java.sql.*;

public class App {

    public static Connection getConnection() throws SQLException {

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/school?useSSL=false&characterEncoding=utf8",
                    "root",
                    "coderslab");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return conn;

    }

}
