package com.example.attendancesystem_client_android.bean;

import androidx.annotation.NonNull;

public class Attendance {
    private String serial_number;
    private String student_id;
    private String course_id;
    private String teacher_id;
    private String modify;
    private String type;
    private String time;
    private String student_name;
    private String course_name;
    private String teacher_name;

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    public Attendance() {
    }

    public String getSerial_number() {
        return serial_number;
    }

    public void setSerial_number(String serial_number) {
        this.serial_number = serial_number;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student) {
        this.student_id = student;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(String teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getModify() {
        return modify;
    }

    public void setModify(String modify) {
        this.modify = modify;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time.substring(5,16);
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Attendance{" +
                "serial_number='" + serial_number + '\'' +
                ", student_id='" + student_id + '\'' +
                ", course_id='" + course_id + '\'' +
                ", teacher_id='" + teacher_id + '\'' +
                ", modify='" + modify + '\'' +
                ", type='" + type + '\'' +
                ", time='" + time + '\'' +
                ", student_name='" + student_name + '\'' +
                ", course_name='" + course_name + '\'' +
                ", teacher_name='" + teacher_name + '\'' +
                '}';
    }

    public String toStudentInformation(){
        String typeString = "";
        if(modify.equals("0") || modify.equals("2")){
            typeString = "缺勤";
        }
        else{
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
        }
        return getStudent_id() + "\t" + getStudent_name() + "\t" + typeString;
    }

    public String getReasonString(){
        switch (type) {
            case "0":
                return "出勤";
            case "1":
                return "病假";
            case "2":
                return "事假";
            default:
                return "缺勤";
        }
    }
}
