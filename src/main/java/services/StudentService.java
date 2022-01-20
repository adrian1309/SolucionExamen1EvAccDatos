package services;

import dao.JDBCDAOStudent;

public class StudentService {
    public void deleteStudentById(int id){
        JDBCDAOStudent dao = new JDBCDAOStudent();
        dao.deleteStudentById(id);
    }
}
