package model;

import lombok.Builder;

import java.util.Objects;
@Builder
public class StudentsGrades {
    private int id;
    private double grade;
    private int attempt;
    //Attempt = Convocatorias, de 1 a 4. No 0, no 5.
    private  Student student;
    private Subjects subjects;

    public StudentsGrades(int id, double grade, int attempt, Student student, Subjects subjects) {
        this.id = id;
        this.grade = grade;
        this.attempt = attempt;
        this.student = student;
        this.subjects = subjects;
    }
    public StudentsGrades(double grade, int attempt) {
        this.grade = grade;
        this.attempt = attempt;
    }

    public StudentsGrades() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public int getAttempt() {
        return attempt;
    }

    public void setAttempt(int attempt) {
        this.attempt = attempt;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Subjects getSubjects() {
        return subjects;
    }

    public void setSubjects(Subjects subjects) {
        this.subjects = subjects;
    }

    @Override
    public String toString() {
        return "StudentsGrades{" +
                "id=" + id +
                ", grade=" + grade +
                ", attempt=" + attempt +
                ", student=" + student +
                ", subjects=" + subjects +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentsGrades that = (StudentsGrades) o;
        return id == that.id && Double.compare(that.grade, grade) == 0 && attempt == that.attempt && Objects.equals(student, that.student) && Objects.equals(subjects, that.subjects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, grade, attempt, student, subjects);
    }
}
