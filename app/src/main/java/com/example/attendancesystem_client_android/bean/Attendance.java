package com.example.attendancesystem_client_android.bean;

public class Attendance {
    private String student_id;
    private String student_name;
    private String type;

    public Attendance() {
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        String typeString = "";
        switch (type) {
            case "0":
                typeString = "出勤";
                break;
            case "1":
                typeString = "病假";
                break;
            case "2":
                typeString = "事假";
                break;
            case "3":
                typeString = "缺勤";
                break;
        }
        return student_id + "\t" + student_name + "\t" + typeString;
    }
}
