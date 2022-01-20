package services;

import dao.JDBCDAOStudentsGrades;
import dao.SpringDAOStudentsGrade;
import model.Student;
import model.StudentsGrades;
import model.Subjects;
import model.ejer4.StudentsSuspended;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GradesService {
    JDBCDAOStudentsGrades daoJDBC = new JDBCDAOStudentsGrades();
    SpringDAOStudentsGrade daoSpring = new SpringDAOStudentsGrade();

    public boolean save(int idStudent, int idSubject, double grade){
        boolean canSave = false;
        StudentsGrades sg = new StudentsGrades();
        List<StudentsGrades> lista = daoJDBC.getStudentsGradesByStudentsAndSubject(idStudent,idSubject);
        if(lista.isEmpty()){
            canSave=true;
            Student t = new Student();
            t.setId(idStudent);
            sg.setStudent(t);
            Subjects s  = new Subjects();
            s.setId(idSubject);
            sg.setSubjects(s);
            sg.setAttempt(0);
            sg.setGrade(grade);

        }else{
            //Bucle para comprobar su ultimo attempt.
            int call = 0;
            double nota=0;
            for (StudentsGrades s:lista) {
                if(call < s.getAttempt()){
                    call =s.getAttempt();
                    nota = s.getGrade();
                }
            }
            if(call < 4 && nota < 5){
                canSave = true;
                Student t = new Student();
                t.setId(idStudent);
                sg.setStudent(t);
                Subjects s  = new Subjects();
                s.setId(idSubject);
                sg.setSubjects(s);
                sg.setAttempt(call+1);
                sg.setGrade(grade);
            }
        }
        if(canSave){
            daoJDBC.save(sg);
        }


        return canSave;
    }

    public List<StudentsSuspended> StudentsSuspended(){
         return daoSpring.getStudentsWithSuspends();
    }

    public List<StudentsGrades> getStudentByNameTeacher(String name){
        return daoSpring.getStudentsByNameTeacher(name);
    }

    public int updateGrades(String name){
        return daoSpring.updateGrades(name);
    }


    public void delete(){
        List<Integer> lista = daoSpring.getTeachersWithAttemptFour();
        List<Integer> duplicateList =
                lista
                        .stream()
                        // agrupar por valores iguales
                        .collect(Collectors.groupingBy(s -> s))
                        .entrySet()
                        .stream()
                        // filtrar por los que tienen mas de un valor por grupo
                        .filter(e -> e.getValue().size() > 1)
                        .map(e -> e.getKey())
                        .collect(Collectors.toList());

        daoSpring.delete(duplicateList);
    }
}
