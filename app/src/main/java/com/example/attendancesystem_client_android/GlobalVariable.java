package com.example.attendancesystem_client_android;

import android.app.Application;

import com.example.attendancesystem_client_android.bean.Attendance;
import com.example.attendancesystem_client_android.bean.Course;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GlobalVariable{
    private static GlobalVariable instance = new GlobalVariable();
    private String account;
    private String password;
    private List<Course> course;
    private int type;
    private int teacher_course_id;
    private int student_course_id;
    private int student_position;
    private List<Attendance> student_message;
    private File file;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public int getStudent_position() {
        return student_position;
    }

    public void setStudent_position(int student_position) {
        this.student_position = student_position;
    }

    public List<Attendance> getStudent_message() {
        return student_message;
    }

    public void setStudent_message(List<Attendance> student_message) {
        this.student_message = student_message;
    }



    public int getStudent_course_id() {
        return student_course_id;
    }

    public void setStudent_course_id(int student_course_id) {
        this.student_course_id = student_course_id;
    }

    public int getTeacher_course_id() {
        return teacher_course_id;
    }

    public void setTeacher_course_id(int teacher_course_id) {
        this.teacher_course_id = teacher_course_id;
    }

    public int getTeacher_course_serial() {
        return teacher_course_serial;
    }

    public void setTeacher_course_serial(int teacher_course_serial) {
        this.teacher_course_serial = teacher_course_serial;
    }

    private int teacher_course_serial;

    public static GlobalVariable getInstance() {
        return instance;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Course> getCourse() {
        return course;
    }

    public void setCourse(List<Course> course) {
        this.course = course;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
