package pl.coderslab.administrative_programs;

import pl.coderslab.dao.ExerciseDao;
import pl.coderslab.dao.GroupDao;
import pl.coderslab.dao.SolutionDao;
import pl.coderslab.dao.UserDao;
import pl.coderslab.model.Exercise;
import pl.coderslab.model.Group;
import pl.coderslab.model.Solution;
import pl.coderslab.model.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import static pl.coderslab.administrative_programs.ExerciseAdministration.isIdOfExistingExercise;
import static pl.coderslab.administrative_programs.UserAdministration.*;

public class AssignExerciseToUser {

    public static void main(String[] args) {

        System.out.println("Welcome to programming school exercise assignment program.");

        mainMenu();
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();

        while (!input.equals(null)) {
            if (input.equals("addU")) {
                System.out.println("You are going to assign exercises to a user.");
                addExerciseToUser();
                mainMenu();
            } else if (input.equals("addG")) {
                System.out.println("You are going to assign exercises to the whole group.");
                addExerciseToGroup();
                mainMenu();
            } else if (input.equals("view")) {
                System.out.println("You are going to view solutions submitted by a user.");
                viewSubmitedSolutions();
                mainMenu();
            } else if (input.equals("quit")) {
                System.out.println("You are quitting the program.");
                break;
            } else {
                System.out.println("Please make sure your input matches one of the options. Try again.");
            }
            input = scan.nextLine();
        }
        scan.close();
    }

    static void mainMenu() {
        System.out.println("In order to select an option, type in:\n" +
                " - \'addU\' - to assign exercises to a user\n" +
                " - \'addG\' - to assign exercises to the whole group\n" +
                " - \'view\' - to view solutions submitted by a user\n" +
                " - \'quit\' - to end the program\n" +
                "and press ENTER.");
    }

    static void addExerciseToUser() {
        printAllUsers();
        System.out.println("Please type in the id of the user you wish to assign exercise to and press ENTER.");
        UserDao userDao = new UserDao();
        int pickedUserId = 0;
        int pickedExerciseId = 0;
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        while (!input.equals(null)) {
            if (isInteger(input) && isIdOfExistingUser(Integer.parseInt(input))) {
                pickedUserId = Integer.parseInt(input);
                break;
            } else {
                System.out.println("User's id must be an integer representing the id of an existing user. Try again.");
                input = scan.nextLine();
            }
        }

        System.out.println("You are going to assign an exercise to the following user:\n" + userDao.read(pickedUserId));
        System.out.println("Available exercises:");
        ExerciseDao exerciseDao = new ExerciseDao();
        Exercise[] allExercises = exerciseDao.findAll();
        for (Exercise oneExercise : allExercises) {
            System.out.println(oneExercise);
        }
        System.out.println("Please type in the id of the exercise you wish to assign to the selected user and press ENTER.");
        input = scan.nextLine();
        while (!input.equals(null)) {
            if (isInteger(input) && isIdOfExistingExercise(Integer.parseInt(input))) {
                pickedExerciseId = Integer.parseInt(input);
                break;
            } else {
                System.out.println("Exercise id must be an integer representing the id of an existing exercise. Try again.");
                input = scan.nextLine();
            }
        }

        SolutionDao solutionDao = new SolutionDao();
        Solution solution = new Solution();
        solution.setUserId(pickedUserId);
        solution.setExerciseId(pickedExerciseId);
        solution.setCreated(generateSqlDate());
        solutionDao.create(solution);

        System.out.println("You have successfully assigned exercise:\n " + exerciseDao.read(pickedExerciseId) +
                "\nto user:\n" + userDao.read(pickedUserId));

    }

    static void viewSubmitedSolutions() throws NullPointerException {
        printAllUsers();
        System.out.println("Please type in the id of the user whose solutions you wish to view and press ENTER.");
        int pickedUserId = 0;
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        while (!input.equals(null)) {
            if (isInteger(input) && isIdOfExistingUser(Integer.parseInt(input))) {
                pickedUserId = Integer.parseInt(input);
                break;
            } else {
                System.out.println("User's id must be an integer representing the id of an existing user. Try again.");
                input = scan.nextLine();
            }
        }
        printAllSolutionsSubmittedByUser(pickedUserId);
    }

    static void addExerciseToGroup(){
        System.out.println("All groups:");
        GroupDao groupDao = new GroupDao();
        Group[] allGroups = groupDao.findAll();
        for (Group oneGroup : allGroups) {
            System.out.println(oneGroup);
        }
        System.out.println("Please type in the id of the group to members of which you wish to assign an exercise and press ENTER.");
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        int pickedGroupId = 0;

        while (!input.equals(null)) {
            if (isInteger(input) && isIdOfExistingGroup(Integer.parseInt(input))) {
                pickedGroupId = Integer.parseInt(input);
                break;
            } else {
                System.out.println("Group id must be an integer representing the id of an existing group. Try again.");
                input = scan.nextLine();
            }
        }
        System.out.println("You selected the group:\n" + groupDao.read(pickedGroupId));
        System.out.println("Available exercises:");
        int pickedExerciseId = 0;
        ExerciseDao exerciseDao = new ExerciseDao();
        Exercise[] allExercises = exerciseDao.findAll();
        for (Exercise oneExercise : allExercises) {
            System.out.println(oneExercise);
        }
        System.out.println("Please type in the id of the exercise you wish to assign to the selected group and press ENTER.");
        input = scan.nextLine();
        while (!input.equals(null)) {
            if (isInteger(input) && isIdOfExistingExercise(Integer.parseInt(input))) {
                pickedExerciseId = Integer.parseInt(input);
                break;
            } else {
                System.out.println("Exercise id must be an integer representing the id of an existing exercise. Try again.");
                input = scan.nextLine();
            }
        }
        SolutionDao solutionDao = new SolutionDao();
        UserDao userDao = new UserDao();
        User[] groupUsers = userDao.findAllByGroupId(pickedGroupId);
        for (User oneGroupUser : groupUsers) {
            Solution solution = new Solution();
            solution.setCreated(generateSqlDate());
            solution.setExerciseId(pickedExerciseId);
            solution.setUserId(oneGroupUser.getId());
            solutionDao.create(solution);
        }

        System.out.println("You have successfully assigned exercise:\n " + exerciseDao.read(pickedExerciseId) +
                "\nto group:\n" + groupDao.read(pickedGroupId));

    }

    public static String generateSqlDate() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = simpleDateFormat.format(date);
        return dateString;
    }

    static void printAllUsers() {
        UserDao userDao = new UserDao();
        User[] allUsers = userDao.findAll();
        System.out.println("All users:");
        for (User oneUser : allUsers) {
            System.out.println(oneUser);
        }
    }

    static void printAllSolutionsSubmittedByUser(int userId) {
        UserDao userDao = new UserDao();
        SolutionDao solutionDao = new SolutionDao();
        Solution[] allUsersSolutions = solutionDao.findAllByUserId(userId);
        int submittedSolutionsCounter = 0;
        System.out.println("Solutions submitted by user " + userDao.read(userId).getUserName() + ":");
        for (Solution oneSolution : allUsersSolutions) {
            if (oneSolution.getUpdated() != null) {
                submittedSolutionsCounter++;
                System.out.println(oneSolution);
            }
        }
        if (submittedSolutionsCounter == 0) {
            System.out.println("It seems that the user you selected have not submitted any solutions yet.\n");
        }
    }
}
