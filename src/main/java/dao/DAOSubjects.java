package dao;

import model.Subjects;
import model.Teacher;
import utils.Querys;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOSubjects {
    private Statement stmt;
    private Connection con;
    private ResultSet rs;
    private DBConnection db;
    private PreparedStatement pstmt;

    //Ejer1

    public List<Subjects> getSubjectsByIdStudent(int idStudent){
        List<Subjects> subjects = new ArrayList<>();
        pstmt = null;
        rs = null;
        db = new DBConnection();
        try {
            con = db.getConnection();
            pstmt = con.prepareStatement(Querys.SELECT_subjectByIDStudent_QUERY);
            pstmt.setInt(1, idStudent);
            rs = pstmt.executeQuery();
            //Ya que solo quiero en nombre subject y nombre teacher, creo el objeto Subject vacio y lo relleno solo con esos dos campos.
            while(rs.next()){
                Subjects s = new Subjects();
                Teacher t = new Teacher();
                s.setName(rs.getString(1));
                t.setName(rs.getString(2));
                s.setTeacher(t);
                subjects.add(s);
            }
        } catch (SQLException e) {
            Logger.getLogger(DAOSubjects.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        } finally {
            db.releaseResource(rs);
            db.releaseResource(pstmt);
            db.closeConnection(con);
        }
        return subjects;
    }

}
