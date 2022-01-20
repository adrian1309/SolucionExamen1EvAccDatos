package model;

import lombok.Builder;

import java.util.Objects;

@Builder
public class Subjects {
    private int id;
    private String name;
    private Teacher teacher;

    public Subjects(int id, String name, Teacher teacher) {
        this.id = id;
        this.name = name;
        this.teacher = teacher;
    }

    public Subjects() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return "Subjects{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", teacher=" + teacher +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subjects subjects = (Subjects) o;
        return id == subjects.id && Objects.equals(name, subjects.name) && Objects.equals(teacher, subjects.teacher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, teacher);
    }
}
