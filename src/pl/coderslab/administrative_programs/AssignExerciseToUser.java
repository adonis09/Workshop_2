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
                System.out.println("You are going to view solutions submitted by a user, mark and comment.");
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
                " - \'view\' - to view solutions submitted by a user, mark and comment\n" +
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

        System.out.println("Please type in the id of the solution you wish to mark and comment and press ENTER");
        System.out.println("If you wish to go back, type in \"quit\" and press ENTER");
        int pickedSolutionId = 0;
        input = scan.nextLine();

        while (!input.equals(null)) {
            if (isInteger(input) && isIdOfAvailableSolution(Integer.parseInt(input), pickedUserId)) {
                pickedSolutionId = Integer.parseInt(input);
                break;
            } else if (input.equals("quit")){
                break;
            }else{
                System.out.println("Solution's id must be an integer representing the id of an available solution. Try again.");
                input = scan.nextLine();
            }
        }

        if(pickedSolutionId != 0){
            ExerciseDao exerciseDao = new ExerciseDao();
            SolutionDao solutionDao = new SolutionDao();
            Solution pickedSolution = solutionDao.read(pickedSolutionId);
            Exercise exerciseToMark = exerciseDao.read(pickedSolution.getExerciseId());
            System.out.println("You are going to mark and comment on the following solution:\n" +
                    "exercise: " + exerciseToMark.getTitle() + " | " + exerciseToMark.getDescription() +
                    "\nuser solution: " + pickedSolution.getDescription());
            System.out.println("Please type in mark (1-6) you wish to give to the solution and pres ENTER.");
            int mark = 0;
            input = scan.nextLine();
            while (!input.equals(null)) {
                if (isInteger(input) && isMarkInRange(Integer.parseInt(input))) {
                    mark = Integer.parseInt(input);
                    break;
                }else{
                    System.out.println("Mark must be an integer in range (1-6). Try again.");
                    input = scan.nextLine();
                }
            }
            System.out.println("Please type in comment you wish to give to the solution and pres ENTER.");
            String comment = scan.nextLine();
            pickedSolution.setMark(mark);
            pickedSolution.setCommentary(comment);
            solutionDao.update(pickedSolution);
            System.out.println("You have marked and commented the following sollution:\n" + pickedSolution ) ;
        }
    }

    static boolean isMarkInRange(int mark){
        int[] possibleMarks = {1, 2, 3, 4, 5, 6};
        for (int oneMark : possibleMarks) {
            if(oneMark == mark){
                return true;
            }
        }
        return false;
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
                System.out.println(oneSolution + "\n");
            }
        }
        if (submittedSolutionsCounter == 0) {
            System.out.println("It seems that the user you selected have not submitted any solutions yet.\n");
        }
    }
    static boolean isIdOfAvailableSolution(int solutionIdToCheck, int userId){

        UserDao userDao = new UserDao();
        SolutionDao solutionDao = new SolutionDao();
        Solution[] allUsersSolutions = solutionDao.findAllByUserId(userId);
        for (Solution oneSolution : allUsersSolutions) {
            if (oneSolution.getUpdated() != null) {
                if(oneSolution.getId() == solutionIdToCheck){
                    return true;
                }
            }
        }
        return false;
    }
}
