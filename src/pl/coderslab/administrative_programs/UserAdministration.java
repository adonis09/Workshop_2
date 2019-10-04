package pl.coderslab.administrative_programs;

import pl.coderslab.DbConnection;
import pl.coderslab.dao.UserDao;
import pl.coderslab.model.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class UserAdministration {

    public static void main(String[] args) throws SQLException {

        Connection connection = DbConnection.getConnection();

        System.out.println("Welcome to programming school user administration program.");

        if (!connection.equals(null)) {
            System.out.println("Database connection established.");

            introMenu();

            Scanner scan = new Scanner(System.in);
            String input = scan.nextLine();

            while (!input.equals(null)) {
                if (input.equals("add")) {
                    System.out.println("You are going to add a new user.");
                    addNewUser();
                    introMenu();
                } else if (input.equals("edit")) {
                    System.out.println("You are going to edit a user.");
                } else if (input.equals("delete")) {
                    System.out.println("You are going to delete a user.");
                }else if (input.equals("quit")) {
                    System.out.println("You are quitting the program.");
                    break;
                }else{
                    System.out.println("Please make sure your input matches one of the options. Try again.");
                }
                input = scan.nextLine();
            }

        } else {
            System.out.println("Connection failed.");
        }

    }

    protected static void introMenu(){
        System.out.println("All users:");
        UserDao userDao = new UserDao();
        User[] allUsers = userDao.findAll();

        for (User oneUser : allUsers) {
            System.out.println(oneUser);
        }

        System.out.println("In order to select an option, type in:\n" +
                " - \'add\' - to add a new user\n" +
                " - \'edit\' - to edit an existing user\n" +
                " - \'delete\' - to delete a user\n" +
                " - \'quit\' - to end the program\n" +
                "and press ENTER."
        );
    }

    protected static void addNewUser(){
        String newUserName;
        String newUserEmail;
        String newUserPassword;
        int newUserGroupIdInt = 0;

        System.out.println("Please type in new user's name and press ENTER");

        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        newUserName = input;

        System.out.println("Please type in new user's email and press ENTER");
        input = scan.nextLine();
        newUserEmail = input;


        System.out.println("Please type in new user's password and press ENTER");
        input = scan.nextLine();
        newUserPassword = input;


        System.out.println("Please type in new user's group's id and press ENTER");
        input = scan.nextLine();

        while(!input.equals(null)) {
            if (isInteger(input)) {
                newUserGroupIdInt = Integer.parseInt(input);
                break;
            } else {
                System.out.println("New user's group's id must be an integer. Try again.");
                input = scan.nextLine();
            }
        }

        User newUser = new User(newUserName, newUserEmail, newUserPassword, newUserGroupIdInt);

        UserDao userDao = new UserDao();
        userDao.create(newUser);
        System.out.println("The following user has been added to the database:\n" + newUser + "\n");

    }

    static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

}
