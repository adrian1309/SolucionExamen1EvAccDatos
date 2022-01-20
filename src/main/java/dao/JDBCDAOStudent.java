package dao;

import config.ConfigProperties;
import model.*;
import utils.Querys;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JDBCDAOStudent {

    private Connection connection;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;
    private PreparedStatement preparedStatement2;
    private DBConnection dbConnection;

    public void deleteStudentById(int id){
        prepareCall();
        try {
            connection.setAutoCommit(false);

            //XML logging
            PreparedStatement preparedStatement = connection.prepareStatement(Querys.GET_STUDENT);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            StudentXML studentxml = null;
            int id_subject=-1;
            int rsSubject, cont=-1;
            if (resultSet.next()) {
                studentxml = StudentXML.builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .subjects(new ArrayList<>())
                        .build();
            }
//Select grades with name of subject, and save in subjects
            preparedStatement = connection.prepareStatement(Querys.SELECT_ALL_SUBJECTS_FROM_STUDENT);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                rsSubject= resultSet.getInt("id_subject");
                if (id_subject==-1 || id_subject != rsSubject)
                {
                    id_subject = rsSubject;
                    SubjectXML s= new SubjectXML(resultSet.getInt("id_subject"), resultSet.getString("name"), new ArrayList<StudentsGrades>());
                    s.getGrades().add(new StudentsGrades(resultSet.getInt("grade"),resultSet.getInt("attempt")));
                    studentxml.getSubjects().add(s);
                    cont++;

                }
                else {
                    studentxml.getSubjects().get(cont).getGrades().add(new StudentsGrades(resultSet.getInt("grade"),resultSet.getInt("attempt")));
                }
            }

            storeSubjectsIntoXML(studentxml);

            preparedStatement2 = connection.prepareStatement(Querys.DELETE_GRADE_BY_STUDENT_ID);
            preparedStatement2.setInt(1, id);
            preparedStatement2.executeUpdate();

            preparedStatement = connection.prepareStatement(Querys.DELETE_STUDENT_BY_ID);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            connection.commit();
        }catch (SQLException sqlException){
            Logger.getLogger(getClass().toString()).log(Level.SEVERE, sqlException.getMessage(), sqlException);
            try {
                connection.rollback();
            }catch (SQLException sqlException1){
                Logger.getLogger(getClass().toString()).log(Level.SEVERE, sqlException.getMessage(), sqlException1);
            }
        }finally {
            try {
                connection.setAutoCommit(true);
            }catch (SQLException sqlException2){
                Logger.getLogger(getClass().toString()).log(Level.SEVERE, sqlException2.getMessage(), sqlException2);
            }
            releaseAllResources();
        }
    }

    private void storeSubjectsIntoXML(StudentXML student) {
        try {
            Path file = Paths.get(ConfigProperties.getInstance().getProperty("student"));
            JAXBContext context = JAXBContext.newInstance(StudentXML.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.marshal(student, Files.newOutputStream(file));
        } catch (Exception e) {
            Logger.getLogger(getClass().toString()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    private void prepareCall(){
        preparedStatement = null;
        resultSet = null;
        dbConnection = new DBConnection();
        connection = dbConnection.getConnection();
    }
    private void releaseAllResources(){
        dbConnection.releaseResource(resultSet);
        dbConnection.releaseResource(preparedStatement);
        dbConnection.closeConnection(connection);
    }
}
