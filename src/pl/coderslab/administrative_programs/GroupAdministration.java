package pl.coderslab.administrative_programs;

import pl.coderslab.DbConnection;
import pl.coderslab.dao.GroupDao;
import pl.coderslab.model.Group;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import static pl.coderslab.administrative_programs.UserAdministration.isInteger;

public class GroupAdministration {
    public static void main(String[] args) throws SQLException {

        Connection connection = DbConnection.getConnection();

        System.out.println("Welcome to programming school group administration program.");

        if (!connection.equals(null)) {
            System.out.println("Database connection established.");

            introMenu();

            Scanner scan = new Scanner(System.in);
            String input = scan.nextLine();

            while (!input.equals(null)) {
                if (input.equals("add")) {
                    System.out.println("You are going to add a new group.");
                    addNewGroup();
                    introMenu();
                } else if (input.equals("edit")) {
                    System.out.println("You are going to edit a group.");
                    editGroup();
                    introMenu();
                } else if (input.equals("delete")) {
                    System.out.println("You are going to delete a group.");
                    deleteGroup();
                    introMenu();
                } else if (input.equals("quit")) {
                    System.out.println("You are quitting the program.");
                    break;
                } else {
                    System.out.println("Please make sure your input matches one of the options. Try again.");
                }
                input = scan.nextLine();
            }

        } else {
            System.out.println("Connection failed.");
        }

    }

    protected static void introMenu() {
        System.out.println("All groups:");
        GroupDao groupDao = new GroupDao();
        Group[] allGroups = groupDao.findAll();

        for (Group oneGroup : allGroups) {
            System.out.println(oneGroup);
        }

        System.out.println("In order to select an option, type in:\n" +
                " - \'add\' - to add new group\n" +
                " - \'edit\' - to edit an existing group\n" +
                " - \'delete\' - to delete an group\n" +
                " - \'quit\' - to end the program\n" +
                "and press ENTER."
        );
    }

    protected static void addNewGroup() {
        String newGroupName;

        System.out.println("Please type in new group's name and press ENTER");

        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        newGroupName = input;


        Group newGroup = new Group(newGroupName);

        GroupDao groupDao = new GroupDao();
        groupDao.create(newGroup);
        System.out.println("The following group has been added to the database:\n" + newGroup + "\n");

    }

    protected static void editGroup() {

        String editGroupName;
        int editGroupIdInt = 0;

        GroupDao groupDao = new GroupDao();

        System.out.println("Plese type in the id of the group you wish to edit and press ENTER");

        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();

        while (!input.equals(null)) {
            if (isInteger(input) && isIdOfExistingGroup(Integer.parseInt(input))) {
                editGroupIdInt = Integer.parseInt(input);
                break;
            } else {
                System.out.println("Group's id must be an integer representing the id of an existing group. Try again.");
                input = scan.nextLine();
            }
        }

        System.out.println("You are going to edit the following group:\n" + groupDao.read(editGroupIdInt));

        System.out.println("Please type in new name of the selected group and press ENTER");

        input = scan.nextLine();
        editGroupName = input;

        Group editGroup = new Group(editGroupName);
        editGroup.setId(editGroupIdInt);
        groupDao.update(editGroup);
        System.out.println("You have successfully edited a group with id: " + editGroup.getId() + ":\n" + editGroup + "\n");

    }

    protected static void deleteGroup() {

        GroupDao groupDao = new GroupDao();
        System.out.println("Please type in the id of the group you wish to remove and press ENTER.");
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();

        int idToDelete = 0;

        while (!input.equals(null)) {
            if (isInteger(input) && isIdOfExistingGroup(Integer.parseInt(input))) {
                idToDelete = Integer.parseInt(input);
                break;
            } else {
                System.out.println("Group id must be an integer representing the id of an existing group. Try again.");
                input = scan.nextLine();
            }
        }

        Group groupToDelete = groupDao.read(idToDelete);
        groupDao.delete(idToDelete);
        System.out.println("The following group has been successfully removed from the databse:\n" + groupToDelete + "\n");

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
