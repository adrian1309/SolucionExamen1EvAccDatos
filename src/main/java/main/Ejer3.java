package main;

import services.StudentService;

import java.util.Scanner;

public class Ejer3 {
    public static void main(String[] args) {
        StudentService services = new StudentService();
        Scanner input = new Scanner(System.in);
        System.out.println("Delete an student");
        System.out.println("Please insert the id of the student to delete");

        int studentToDelete = input.nextInt();

        services.deleteStudentById(studentToDelete);
    }
}
