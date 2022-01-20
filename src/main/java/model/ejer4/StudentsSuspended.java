package model.ejer4;

public class StudentsSuspended {
    private String nameStudent;
    private String nameSubject;
    private boolean maxCall=false;

    public StudentsSuspended(String nameStudent, String nameSubject, int call) {
        this.nameStudent = nameStudent;
        this.nameSubject = nameSubject;
        if(call==4) {
            this.maxCall=true;
        }
    }

    public StudentsSuspended(String nameStudent, String nameSubject, boolean maxCall) {
        this.nameStudent = nameStudent;
        this.nameSubject = nameSubject;
        this.maxCall = maxCall;
    }

    public String getNameStudent() {
        return nameStudent;
    }

    public void setNameStudent(String nameStudent) {
        this.nameStudent = nameStudent;
    }

    public String getNameSubject() {
        return nameSubject;
    }

    public void setNameSubject(String nameSubject) {
        this.nameSubject = nameSubject;
    }

    public boolean isMaxCall() {
        return maxCall;
    }

    public void setMaxCall(boolean maxCall) {
        this.maxCall = maxCall;
    }

    @Override
    public String toString() {
        return "StudentsSuspendes{" +
                "nameStudent='" + nameStudent + '\'' +
                ", nameSubject='" + nameSubject + '\'' +
                ", maxCall=" + maxCall +
                '}';
    }
}
