package main;

import model.StudentsGrades;
import services.GradesService;

public class Ejer5 {
    public static void main(String[] args) {
        GradesService gs = new GradesService();
        int res = gs.updateGrades("gringo");
        if (res >= 0)
        System.out.println("Doing update.........Update done.");
        else
        System.out.println("Error when updating ");
    }
}
