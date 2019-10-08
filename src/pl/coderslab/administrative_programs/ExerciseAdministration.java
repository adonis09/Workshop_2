package pl.coderslab.administrative_programs;

import pl.coderslab.dao.ExerciseDao;
import pl.coderslab.model.Exercise;

import java.util.Scanner;

import static pl.coderslab.administrative_programs.UserAdministration.isInteger;

public class ExerciseAdministration {

    public static void main(String[] args) {

        System.out.println("Welcome to programming school exercise administration program.");

        introMenu();

        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();

        while (!input.equals(null)) {
            if (input.equals("add")) {
                System.out.println("You are going to add a new exercise.");
                addNewExercise();
                introMenu();
            } else if (input.equals("edit")) {
                System.out.println("You are going to edit a exercise.");
                editExercise();
                introMenu();
            } else if (input.equals("delete")) {
                System.out.println("You are going to delete a exercise.");
                deleteExercise();
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
        System.out.println("All exercises:");
        ExerciseDao exerciseDao = new ExerciseDao();
        Exercise[] allExercises = exerciseDao.findAll();

        for (Exercise oneExercise : allExercises) {
            System.out.println(oneExercise);
        }

        System.out.println("In order to select an option, type in:\n" +
                " - \'add\' - to add new exercise\n" +
                " - \'edit\' - to edit an existing exercise\n" +
                " - \'delete\' - to delete an exercise\n" +
                " - \'quit\' - to end the program\n" +
                "and press ENTER.");
    }

    protected static void addNewExercise() {
        String newExerciseTitle;
        String newExerciseDescription;

        System.out.println("Please type in new exercise's title and press ENTER");

        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        newExerciseTitle = input;

        System.out.println("Please type in new exercise's description and press ENTER");
        input = scan.nextLine();
        newExerciseDescription = input;

        Exercise newExercise = new Exercise(newExerciseTitle, newExerciseDescription);

        ExerciseDao exerciseDao = new ExerciseDao();
        exerciseDao.create(newExercise);
        System.out.println("The following exercise has been added to the database:\n" + newExercise + "\n");
    }

    protected static void editExercise() {

        String editExerciseTitle;
        String editExerciseDescription;
        int editExerciseIdInt = 0;

        ExerciseDao exerciseDao = new ExerciseDao();

        System.out.println("Plese type in the id of the exercise you wish to edit and press ENTER");

        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();

        while (!input.equals(null)) {
            if (isInteger(input) && isIdOfExistingExercise(Integer.parseInt(input))) {
                editExerciseIdInt = Integer.parseInt(input);
                break;
            } else {
                System.out.println("Exercise's id must be an integer representing the id of an existing exercise. Try again.");
                input = scan.nextLine();
            }
        }

        System.out.println("You are going to edit the following exercise:\n" + exerciseDao.read(editExerciseIdInt));

        System.out.println("Please type in new title of the selected exercise and press ENTER");

        input = scan.nextLine();
        editExerciseTitle = input;

        System.out.println("Please type in new desctiption of the selected exercise and press ENTER");
        input = scan.nextLine();
        editExerciseDescription = input;

        Exercise editExercise = new Exercise(editExerciseTitle, editExerciseDescription);
        editExercise.setId(editExerciseIdInt);
        exerciseDao.update(editExercise);
        System.out.println("You have successfully edited a exercise with id: " + editExercise.getId() + ":\n" + editExercise + "\n");
    }

    protected static void deleteExercise() {

        ExerciseDao exerciseDao = new ExerciseDao();
        System.out.println("Please type in the id of the exercise you wish to remove and press ENTER.");
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();

        int idToDelete = 0;

        while (!input.equals(null)) {
            if (isInteger(input) && isIdOfExistingExercise(Integer.parseInt(input))) {
                idToDelete = Integer.parseInt(input);
                break;
            } else {
                System.out.println("Exercise id must be an integer representing the id of an existing exercise. Try again.");
                input = scan.nextLine();
            }
        }

        Exercise exerciseToDelete = exerciseDao.read(idToDelete);
        exerciseDao.delete(idToDelete);
        System.out.println("The following exercise has been successfully removed from the databse:\n" + exerciseToDelete + "\n");
    }

    static boolean isIdOfExistingExercise(int exerciseId) {
        ExerciseDao exerciseDao = new ExerciseDao();
        if (exerciseDao.read(exerciseId) == null) {
            return false;
        } else {
            return true;
        }
    }
}
