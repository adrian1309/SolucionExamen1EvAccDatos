package main;

import model.Subjects;
import services.SubjectService;

import java.util.List;
import java.util.Scanner;

public class Ejer1 {
    public static void main(String[] args) {
        SubjectService service = new SubjectService();
        Scanner sc = new Scanner(System.in);
        int idStudent;
        System.out.print("Write IDStudent: ");
        idStudent = sc.nextInt();
        List<Subjects> list = service.getSubjectByIdStudent(idStudent);
        if (list.isEmpty()) {
            System.out.println("There isn't any student with this id.");
        } else {
            System.out.println("List Subject/s --- Teacher/s");
            for (Subjects s : list) {
                System.out.println(s.getName() + " --- " + s.getTeacher().getName());
            }
        }
    }
}
