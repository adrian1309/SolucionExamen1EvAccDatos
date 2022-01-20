package dao;

import model.Student;
import model.StudentsGrades;
import model.Subjects;
import model.Teacher;
import model.ejer4.StudentsSuspended;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import utils.Querys;

import java.io.StringBufferInputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SpringDAOStudentsGrade {

    public List<StudentsSuspended> getStudentsWithSuspends() {
        List<StudentsSuspended> list = new ArrayList<>();
        try {
            JdbcTemplate bd = new JdbcTemplate(DBConnPool.getInstance().getDataSource());
            bd.query(Querys.SELECT_StudentsSuspend_QUERY, rs -> {
                StudentsSuspended ss = new StudentsSuspended(rs.getString(1), rs.getString(2), rs.getInt(3));
                list.add(ss);
            });
        } catch (Exception e) {
            Logger.getLogger(SpringDAOStudentsGrade.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }

        return list;
    }

    public List<StudentsGrades> getStudentsByNameTeacher(String name) {
        List<StudentsGrades> result = new ArrayList<>();
        try {
            JdbcTemplate bd = new JdbcTemplate(DBConnPool.getInstance().getDataSource());
            result = bd.query(Querys.SELECT_StudentsByNameTeacher_QUERY, new ProductMapper(), name);
        } catch (Exception e) {
            Logger.getLogger(SpringDAOStudentsGrade.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return result;
    }

    public List<StudentsSuspended> getMaxConvByNameTeacher(String name) {
        List<StudentsSuspended> result = new ArrayList<>();
        try {
            JdbcTemplate bd = new JdbcTemplate(DBConnPool.getInstance().getDataSource());
            result = bd.query(Querys.SELECT_MaxConvByNameTeacher_QUERY, BeanPropertyRowMapper.newInstance(StudentsSuspended.class), name);
        } catch (Exception e) {
            Logger.getLogger(SpringDAOStudentsGrade.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return result;
    }

    public int updateGrades(String name){
        int res=-1;
        TransactionDefinition txDef = new DefaultTransactionDefinition();
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(DBConnPool.getInstance().getDataSource());
        TransactionStatus txStatus = transactionManager.getTransaction(txDef);try {
            JdbcTemplate db = new JdbcTemplate(transactionManager.getDataSource());
             List<Integer> result = db.queryForList(Querys.SELECT_MaxConvByNameTeacher_QUERY,Integer.class, name);
            for (Integer i : result) {
                res= db.update(Querys.UPDATE_GradesTeacher_QUERY,i);
            }
            transactionManager.commit(txStatus);
        } catch (Exception e) {
            transactionManager.rollback(txStatus);
            Logger.getLogger(SpringDAOStudentsGrade.class.getName()).log(Level.SEVERE, null, e);
        }
        return res;
    }

    public void delete (List<Integer> lista){
        TransactionDefinition txDef = new DefaultTransactionDefinition();
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(DBConnPool.getInstance().getDataSource());
        TransactionStatus txStatus = transactionManager.getTransaction(txDef);try {
            JdbcTemplate db = new JdbcTemplate(transactionManager.getDataSource());
            for (Integer i : lista) {
                db.update(Querys.DELETE_grades_QUERY,i);
                db.update(Querys.DELETE_subjects_QUERY,i);
                db.update(Querys.DELETE_teachers_QUERY,i);
                transactionManager.commit(txStatus);
            }

        } catch (Exception e) {
            transactionManager.rollback(txStatus);
            Logger.getLogger(SpringDAOStudentsGrade.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public List<Integer> getTeachersWithAttemptFour(){
        List<Integer> result = new ArrayList<>();
        try {
            JdbcTemplate bd = new JdbcTemplate(DBConnPool.getInstance().getDataSource());
            result = bd.queryForList(Querys.SELECT_teachersAttemptFour_QUERY,Integer.class);
        } catch (Exception e) {
            Logger.getLogger(SpringDAOStudentsGrade.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return result;
    }

    private static final class ProductMapper implements RowMapper<StudentsGrades> {
        @Override
        public StudentsGrades mapRow(ResultSet rs, int rowNum) throws SQLException {
            StudentsGrades sg = new StudentsGrades();
            Subjects su = new Subjects();
            Student st = new Student();
            su.setId(rs.getInt(1));
            st.setId(rs.getInt(2));
            sg.setId(rs.getInt(3));
            sg.setGrade(rs.getDouble(4));
            sg.setAttempt(rs.getInt(5));
            st.setName(rs.getString(6));
            su.setName(rs.getString(7));
            sg.setStudent(st);
            sg.setSubjects(su);
            return sg;
        }
    }

    private static final class ProductMapperNew implements RowMapper<StudentsGrades> {
        @Override
        public StudentsGrades mapRow(ResultSet rs, int rowNum) throws SQLException {
            StudentsGrades sg = new StudentsGrades();
            Teacher te = new Teacher();
            Student st = new Student();
            Subjects su = new Subjects();
            su.setName(rs.getString("subjects.name"));
            su.setId(rs.getInt("id_subject"));
            te.setId(rs.getInt("teachers.id"));
            te.setName(rs.getString("teachers.name"));
            su.setTeacher(te);
            st.setId(rs.getInt("students.id"));
            st.setName(rs.getString("students.name"));
            sg.setGrade(rs.getDouble("grade"));
            sg.setAttempt(rs.getInt("attempt"));
            sg.setId(rs.getInt("students_grades.id"));
            sg.setStudent(st);
            sg.setSubjects(su);
            return sg;
        }
    }
}
