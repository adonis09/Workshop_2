package pl.coderslab.user_program;

import pl.coderslab.dao.ExerciseDao;
import pl.coderslab.dao.SolutionDao;
import pl.coderslab.dao.UserDao;
import pl.coderslab.model.Exercise;
import pl.coderslab.model.Solution;

import java.sql.SQLException;
import java.util.Scanner;

import static pl.coderslab.administrative_programs.AssignExerciseToUser.generateSqlDate;
import static pl.coderslab.administrative_programs.UserAdministration.isInteger;


public class SubmitSolution {

    public static void main(String[] args) throws SQLException {

        int userId = 1;

        /*int userId = 0;

        try {
            userId = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.err.println("Wrong startup parameter.");
        }*/

        if (userId != 0) {
            System.out.println("Welcome to solution submitting program.");
            System.out.println("User id submitted on startup: " + userId);

            UserDao userDao = new UserDao();

            if (userDao.read(userId) == null) {
                System.out.println("These is no user with the submitted id.");
            } else {
                menu();
                Scanner scan = new Scanner(System.in);
                String input = scan.nextLine();

                while (!input.equals(null)) {
                    if (input.equals("add")) {
                        System.out.println("You are going to submit a solution.");
                        submitSolution(userId);
                    } else if (input.equals("view")) {
                        System.out.println("You are going to view your solutions.");
                        viewSolutions(userId);
                    } else if (input.equals("quit")) {
                        System.out.println("You are quitting the program.");
                        break;
                    } else {
                        System.out.println("Please make sure your input matches one of the options. Try again.");
                    }
                    menu();
                    input = scan.nextLine();
                }
                scan.close();
            }
        }
    }

    static void menu() {
        System.out.println("In order to select an option, type in:\n" +
                " - \'add\' - to submit solution\n" +
                " - \'view\' - to view your solutions\n" +
                " - \'quit\' - to end the program\n" +
                "and press ENTER.");

    }

    static void submitSolution(int userId) {

        ExerciseDao exerciseDao = new ExerciseDao();
        SolutionDao solutionDao = new SolutionDao();
        Solution[] allUsersSolutions = solutionDao.findAllByUserId(userId);

        System.out.println("All unsolved exercises:");
        int unsolvedExercisesCounter = 0;
        for (Solution oneSolution : allUsersSolutions) {
            if (oneSolution.getDescription() == null) {
                System.out.println("solution id: " + oneSolution.getId() + " | task: " + exerciseDao.read(oneSolution.getExerciseId()).getDescription());
                unsolvedExercisesCounter++;
            }
        }
        if (unsolvedExercisesCounter == 0) {
            System.out.println("There are no available exercises at the moment. Please try again later.");
        } else {
            System.out.println("Please type in the id of the solution you wish to submit and press ENTER");
            int pickedSolutionId = 0;
            String pickedSolutionDescription;

            Scanner scan = new Scanner(System.in);
            String input = scan.nextLine();

            while (!input.equals(null)) {
                if (isInteger(input) && isIdOfAavailableSolution(Integer.parseInt(input), userId)) {
                    pickedSolutionId = Integer.parseInt(input);
                    break;
                } else {
                    System.out.println("Solution id must be an integer representing the id of an available solution. Try again.");
                    input = scan.nextLine();
                }
            }

            Solution pickedSolution = solutionDao.read(pickedSolutionId);

            Exercise pickedExercise = exerciseDao.read(pickedSolution.getExerciseId());
            System.out.println("You are going to do the following exercise:\n" + pickedExercise);

            System.out.println("Please type in your solution and press ENTER");

            input = scan.nextLine();
            pickedSolutionDescription = input;

            pickedSolution.setUpdated(generateSqlDate());
            pickedSolution.setDescription(pickedSolutionDescription);

            solutionDao.update(pickedSolution);

            System.out.println("You have successfully submitted the following solution:\n" + pickedSolution + "\n");
        }
    }

    static boolean isIdOfAavailableSolution(int solutionId, int userId) {

        SolutionDao solutionDao = new SolutionDao();
        Solution[] allUsersSolutions = solutionDao.findAllByUserId(userId);
        for (Solution oneSolution : allUsersSolutions) {
            if (oneSolution.getDescription() == null) {
                if (oneSolution.getId() == solutionId) {
                    return true;
                }
            }
        }
        return false;
    }

    static void viewSolutions(int userId) {

        SolutionDao solutionDao = new SolutionDao();
        ExerciseDao exerciseDao = new ExerciseDao();
        Solution[] allUsersSolutions = solutionDao.findAllByUserId(userId);
        int solvedExercisesCounter = 0;
        System.out.println("All submitted solutions:");
        for (Solution oneSolution : allUsersSolutions) {
            if (oneSolution.getDescription() != null) {
                System.out.println("solution id: " + oneSolution.getId() +
                        "\n task: " + exerciseDao.read(oneSolution.getExerciseId()).getDescription() +
                        "\n solution: " + oneSolution.getDescription() + "\n");
                solvedExercisesCounter++;
            }
        }
        if (solvedExercisesCounter == 0) {
            System.out.println("There no submitted solutions.");
        }
    }
}
