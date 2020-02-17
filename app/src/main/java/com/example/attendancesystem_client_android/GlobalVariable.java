package com.example.attendancesystem_client_android;

import android.app.Application;

import com.example.attendancesystem_client_android.bean.Course;

import java.util.List;

public class GlobalVariable{
    private static GlobalVariable instance = new GlobalVariable();
    private String account;
    private String password;
    private List<Course> course;
    private int type;

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
