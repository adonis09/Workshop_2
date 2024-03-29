package pl.coderslab.administrative_programs;

import pl.coderslab.dao.GroupDao;
import pl.coderslab.dao.UserDao;
import pl.coderslab.model.User;

import java.util.Scanner;

public class UserAdministration {

    public static void main(String[] args) {

        System.out.println("Welcome to programming school user administration program.");

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
                editUser();
                introMenu();
            } else if (input.equals("delete")) {
                System.out.println("You are going to delete a user.");
                deleteUser();
                introMenu();
            } else if (input.equals("quit")) {
                System.out.println("You are quitting the program.");
                break;
            } else {
                System.out.println("Please make sure your input matches one of the options. Try again.");
            }
            input = scan.nextLine();
        }
    }

    protected static void introMenu() {
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
                "and press ENTER.");
    }

    protected static void addNewUser() {
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

        while (!input.equals(null)) {
            if (isInteger(input) && isIdOfExistingGroup(Integer.parseInt(input))) {
                newUserGroupIdInt = Integer.parseInt(input);
                break;
            } else {
                System.out.println("New user's group's id must be an integer representing the id of an existing group. Try again.");
                input = scan.nextLine();
            }
        }

        User newUser = new User(newUserName, newUserEmail, newUserPassword, newUserGroupIdInt);

        UserDao userDao = new UserDao();
        userDao.create(newUser);
        System.out.println("The following user has been added to the database:\n" + newUser + "\n");
    }

    protected static void editUser() {

        String editUserName;
        String editUserEmail;
        String editUserPassword;
        int editUserGroupIdInt = 0;
        int editUserIdInt = 0;

        UserDao userDao = new UserDao();

        System.out.println("Plese type in the id of the user you wish to edit and press ENTER");

        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();

        while (!input.equals(null)) {
            if (isInteger(input) && isIdOfExistingUser(Integer.parseInt(input))) {
                editUserIdInt = Integer.parseInt(input);
                break;
            } else {
                System.out.println("User's id must be an integer representing the id of an existing user. Try again.");
                input = scan.nextLine();
            }
        }

        System.out.println("You are going to edit the following user:\n" + userDao.read(editUserIdInt));

        System.out.println("Please type in new name of the selected user and press ENTER");

        input = scan.nextLine();
        editUserName = input;

        System.out.println("Please type in new email of the selected user and press ENTER");
        input = scan.nextLine();
        editUserEmail = input;


        System.out.println("Please type in new password of the selected user and press ENTER");
        input = scan.nextLine();
        editUserPassword = input;


        System.out.println("Please type in new user's group's id of the selected user and press ENTER");
        input = scan.nextLine();

        while (!input.equals(null)) {
            if (isInteger(input) && isIdOfExistingGroup(Integer.parseInt(input))) {
                editUserGroupIdInt = Integer.parseInt(input);
                break;
            } else {
                System.out.println("User's group's id must be an integer representing the id of an existing group. Try again.");
                input = scan.nextLine();
            }
        }

        User editUser = new User(editUserName, editUserEmail, editUserPassword, editUserGroupIdInt);
        editUser.setId(editUserIdInt);
        userDao.update(editUser);
        System.out.println("You have successfully edited a user with id: " + editUser.getId() + ":\n" + editUser + "\n");
    }

    protected static void deleteUser() {

        UserDao userDao = new UserDao();
        System.out.println("Please type in the id of the user you wish to remove and press ENTER.");
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();

        int idToDelete = 0;

        while (!input.equals(null)) {
            if (isInteger(input) && isIdOfExistingUser(Integer.parseInt(input))) {
                idToDelete = Integer.parseInt(input);
                break;
            } else {
                System.out.println("User's id must be an integer representing the id of an existing user. Try again.");
                input = scan.nextLine();
            }
        }

        User userToDelete = userDao.read(idToDelete);
        userDao.delete(idToDelete);
        System.out.println("The following user has been successfully removed from the databse:\n" + userToDelete + "\n");
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    static boolean isIdOfExistingUser(int userId) {
        UserDao userDao = new UserDao();
        if (userDao.read(userId) == null) {
            return false;
        } else {
            return true;
        }
    }

    static boolean isIdOfExistingGroup(int groupId) {
        GroupDao groupDao = new GroupDao();
        if (groupDao.read(groupId) == null) {
            return false;
        } else {
            return true;
        }
    }
}
