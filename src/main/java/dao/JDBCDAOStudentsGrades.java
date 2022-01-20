package dao;

import model.Student;
import model.StudentsGrades;
import model.Subjects;
import model.Teacher;
import utils.Querys;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JDBCDAOStudentsGrades {
    private Statement stmt;
    private Connection con;
    private ResultSet rs;
    private DBConnection db;
    private PreparedStatement pstmt;

    public int addGrade(int idStudent, int idSubject, double grade, int attempts){
        db = new DBConnection();
        int result = -1;
        try{
            con = db.getConnection();
            pstmt = con.prepareStatement(Querys.ADD_GRADE);
            pstmt.setInt(1, idStudent);
            pstmt.setInt(2, idSubject);
            pstmt.setDouble(3, grade);
            pstmt.setInt(4, attempts);
            pstmt.executeUpdate();
            result = 0;

        }
        catch (SQLException sqle){
            sqle.printStackTrace();
        }
        finally {
            db.releaseResource(rs);
            db.releaseResource(pstmt);
            db.closeConnection(con);
        }

        return result;
    }


    public int gradeLastAttempt(int idSubject, int idStudent){
        db =new DBConnection();
        int number = 0;
        try{
            con = db.getConnection();
            pstmt = con.prepareStatement(Querys.GET_LAST_GRADE_OF_STUDENT);
            pstmt.setInt(1, idStudent);
            pstmt.setInt(2, idSubject);
            rs = pstmt.executeQuery();

            while (rs.next()){
                number = rs.getInt("grade");

            }

        } catch (SQLException ex){
            Logger.getLogger(ex.getMessage());
        }
        finally {
            db.releaseResource(rs);
            db.releaseResource(pstmt);
            db.closeConnection(con);

        }

        return number;


    }

    public int getNumberOfAttempts(int idSubject, int idStudent){
        db =new DBConnection();
        int number = 0;
        try{
            con = db.getConnection();
            pstmt = con.prepareStatement(Querys.GET_NUMBER_OF_ATTEMPTS);
            pstmt.setInt(1, idSubject);
            pstmt.setInt(2, idStudent);
            rs = pstmt.executeQuery();

            while (rs.next()){
                number = rs.getInt("count(attempt)");

            }

        } catch (SQLException ex){
            Logger.getLogger(ex.getMessage());
        }
        finally {
            db.releaseResource(rs);
            db.releaseResource(pstmt);
            db.closeConnection(con);

        }

        return number;
    }

    public List<StudentsGrades> getStudentsGradesByStudentsAndSubject(int idStudent, int idSubject){
        List<StudentsGrades> result = null;
        pstmt = null;
        rs = null;
        db = new DBConnection();
        try {
            con = db.getConnection();
            pstmt = con.prepareStatement(Querys.SELECT_StudentsGradesByStudentAndSubject_QUERY);
            pstmt.setInt(1, idStudent);
            pstmt.setInt(2, idSubject);
            rs = pstmt.executeQuery();
            result = buildListStudentsGrades(rs);
        } catch (SQLException e) {
            Logger.getLogger(DAOSubjects.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        } finally {
            db.releaseResource(rs);
            db.releaseResource(pstmt);
            db.closeConnection(con);
        }
        return result;
    }


    public int save(StudentsGrades sg){
        int isSave=-1;
        pstmt = null;
        db = new DBConnection();

        try{
            con = db.getConnection();
            pstmt = con.prepareStatement(Querys.INSERT_StudentGrades_QUERY,Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1,sg.getStudent().getId());
            pstmt.setInt(2,sg.getSubjects().getId());
            pstmt.setDouble(3,sg.getGrade());
            pstmt.setInt(4,sg.getAttempt());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            if(rs.next()){
                isSave = rs.getInt(1);
            }

        } catch (SQLException e) {
            Logger.getLogger(JDBCDAOStudentsGrades.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }finally {
            db.releaseResource(pstmt);
            db.closeConnection(con);
        }
        return isSave;
    }


    public List<StudentsGrades> buildListStudentsGrades(ResultSet rs) throws SQLException {
        List<StudentsGrades> result = new ArrayList<>();
        while (rs.next()) {
            int idTeacher = rs.getInt(1);
            String nameTeacher = rs.getString(2);
            int idSubject = rs.getInt(3);
            String nameSubject = rs.getString(4);
            int idStudents = rs.getInt(5);
            int sgId = rs.getInt(6);
            double grade = rs.getDouble(7);
            int attempt = rs.getInt(8);
            Teacher t = new Teacher(idTeacher,nameTeacher);
            Subjects s = new Subjects(idSubject,nameSubject,t);
            Student st = new Student();
            st.setId(idStudents);
            StudentsGrades sg = new StudentsGrades(sgId,grade,attempt,st,s);
            result.add(sg);
        }
        return result;
    }

}
