package com.example.attendancesystem_client_android;

import android.app.Application;

import com.example.attendancesystem_client_android.bean.Course;

import java.util.List;

public class GlobalVariable extends Application {
    private static GlobalVariable instance = null;
    private static String account;
    private static String password;
    private static List<Course> course;
    private int type;

    public List<Course> getCourse() {
        return course;
    }

    public void setCourse(List<Course> mCourse) {
        course =mCourse;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public static synchronized GlobalVariable getInstance(){
        if(null == instance){
            instance = new GlobalVariable();
            course = null;
        }
        return instance;
    }

    public void onCreate() {
        super.onCreate();
        instance = this;
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
}
