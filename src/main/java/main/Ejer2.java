package main;

import services.GradesService;
import services.StudentsGradesServices;

import java.util.Scanner;

public class Ejer2 {
    public static void main(String[] args) {
        StudentsGradesServices studentsGradesServices = new StudentsGradesServices();
        Scanner input = new Scanner(System.in);

        System.out.println("Tell me the student id");
        int studentID = input.nextInt();
        System.out.println("Tell me the subject id");
        int subject = input.nextInt();
        System.out.println("Tell me the grade");
        double grade = input.nextInt();

        try {
            int result = studentsGradesServices.ex2JDBC(studentID, subject, grade);
            if (result == 3) {
                System.out.println("Grade added");
            } else if (result == 1) {
                System.out.println("That student has 4 attempts");
            } else if (result == 2) {
                System.out.println("The student passed in the last attempt");
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Server is down");
        } finally {
            //Close pool
        }
    }
}
