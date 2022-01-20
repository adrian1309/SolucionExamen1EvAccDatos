package services;

import dao.DAOSubjects;
import model.Subjects;

import java.util.List;

public class SubjectService {
    DAOSubjects dao = new DAOSubjects();

    public List<Subjects> getSubjectByIdStudent(int idStudent) {
        return dao.getSubjectsByIdStudent(idStudent);
    }

}
