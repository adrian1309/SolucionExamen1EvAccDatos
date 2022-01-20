package utils;

public class Querys {

    public static final String SELECT_subjectByIDStudent_QUERY = "SELECT DISTINCT subjects.name, teachers.name " +
            "FROM ((teachers INNER JOIN subjects ON teachers.id=subjects.id_teacher)" +
            "INNER JOIN students_grades ON subjects.id = students_grades.id_subject) WHERE id_student =?";

    public final static String GET_NUMBER_OF_ATTEMPTS = "select id_student, count(attempt) from students_grades where id_subject = ? and id_student = ? group by id_student, id_subject;";
    public final static String GET_LAST_GRADE_OF_STUDENT = "select grade from students_grades where id = \n" +
            "                (select max(id) from students_grades where id_student = ? and id_subject = ?);";

    public final static String ADD_GRADE = "insert into students_grades (id_student, id_subject, grade, attempt)\n" +
            "values (?, ?, ?, ?);";

    public static final String DELETE_STUDENT_BY_ID = "delete from students where id = ?";
    public static final String DELETE_GRADE_BY_STUDENT_ID = "delete from students_grades where id_student =?";

    public final static String SELECT_ALL_SUBJECTS_FROM_STUDENT =
            "SELECT * from students_grades inner join subjects s on students_grades.id_subject = s.id "+
            "where id_student=?";

    public final static String GET_STUDENT = "select * from students where id = ?";

    public static final String SELECT_StudentsGradesByStudentAndSubject_QUERY =
            "SELECT teachers.id, teachers.name, subjects.id, subjects.name,students_grades.id_student, students_grades.id, students_grades.grade, students_grades.attempt " +
                    "FROM ((teachers INNER JOIN subjects ON teachers.id=subjects.id_teacher) " +
                    "INNER JOIN students_grades ON subjects.id = students_grades.id_subject)" +
                    " WHERE id_student =? and id_subject = ?";

    public static final String SELECT_StudentsSuspend_QUERY =
            "SELECT st.name, sb.name, attempt from students_grades sg INNER JOIN subjects sb ON sg.id_subject=sb.id "+
            "INNER JOIN students st ON sg.id_student = st.id "+
            "where grade < 5 "+
            "and attempt= (select max(attempt) from students_grades sg2 "+
            "where sg.id_subject= sg2.id_subject and sg.id_student=sg2.id_student "+
            "group by id_student, id_subject)";

    public static final String SELECT_StudentsByNameTeacher_QUERY=
            "SELECT  subjects.id, students_grades.id_student, students_grades.id, students_grades.grade, students_grades.attempt, students.name, subjects.name " +
            "FROM (((teachers INNER JOIN subjects ON teachers.id=subjects.id_teacher) " +
            "INNER JOIN students_grades ON subjects.id = students_grades.id_subject) " +
            "INNER JOIN students ON students.id = students_grades.id_student)" +
            "WHERE teachers.name = ?";

    public static final String SELECT_MaxConvByNameTeacher_QUERY=
            "SELECT  sg.id FROM students_grades sg INNER JOIN subjects sb ON sg.id_subject = sb.id "+
            "INNER JOIN teachers t ON sb.id_teacher = t.id "+
            "WHERE t.name = ? "+
            "and attempt= (select max(attempt) from students_grades sg2 "+
            "where sg.id_subject= sg2.id_subject and sg.id_student=sg2.id_student "+
            "group by id_student, id_subject)";

    public static final String SELECT_teachersAttemptFour_QUERY =
            "SELECT  sb.id_teacher "+
            "FROM students_grades sg INNER JOIN subjects sb ON sg.id_subject = sb.id "+
            "WHERE attempt=4 and grade<5";

    public static final String INSERT_StudentGrades_QUERY = "INSERT INTO students_grades (id_student, id_subject, grade, attempt) values (?,?,?,?)";
    public static final String UPDATE_GradesTeacher_QUERY = "UPDATE students_grades SET grade = grade+0.5 where id= ?";

    public static final String DELETE_grades_QUERY = "DELETE FROM students_grades where id_subject IN (SELECT id FROM subjects where id_teacher=?)";
    public static final String DELETE_subjects_QUERY = "DELETE FROM subjects where id_teacher = ?";
    public static final String DELETE_teachers_QUERY = "DELETE FROM teachers where id = ?";
}
