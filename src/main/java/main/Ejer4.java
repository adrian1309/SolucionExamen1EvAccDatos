package main;

import model.ejer4.StudentsSuspended;
import services.GradesService;

public class Ejer4 {
    public static void main(String[] args) {
        GradesService gs = new GradesService();
        System.out.println("Students with subject failed: ");
        for (StudentsSuspended s: gs.StudentsSuspended()) {
            System.out.println(s);
        }
    }
}
