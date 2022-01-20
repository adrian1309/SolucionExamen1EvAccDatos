package services;

import dao.JDBCDAOStudentsGrades;

public class StudentsGradesServices {
    private JDBCDAOStudentsGrades dao;


    public int ex2JDBC(int idStudent, int idSubject, double grade) {
        dao = new JDBCDAOStudentsGrades();
        int num = 0;
        int numberAttempts = dao.getNumberOfAttempts(idSubject, idStudent);
        if (numberAttempts < 4) {
            if (dao.gradeLastAttempt(idSubject, idStudent) >= 5) {
                num = 2;
            } else {
                dao.addGrade(idStudent, idSubject, grade, numberAttempts + 1);
                num = 3;
            }
        } else {
            num = 1;
        }


        return num;
    }


}


