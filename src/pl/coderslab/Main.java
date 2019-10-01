package pl.coderslab;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {

        Connection connection = DbConnection.getConnection();

        if (!connection.equals(null)){
            System.out.println("Connection works!");
        }else{
            System.out.println("Connection failed.");
        }

    }

}
