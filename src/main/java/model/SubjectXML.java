package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Objects;


@Builder
@XmlRootElement
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)

public class SubjectXML {
    private int id;
    private String name;
    private List<StudentsGrades> grades;

    @Override
    public String toString() {
        return "SubjectXML{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", subjects=" + grades +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubjectXML that = (SubjectXML) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(grades, that.grades);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, grades);
    }

    public List<StudentsGrades> getGrades() {
        return grades;
    }
}