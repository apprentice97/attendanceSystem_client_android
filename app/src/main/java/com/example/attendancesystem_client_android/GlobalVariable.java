package com.example.attendancesystem_client_android;

import android.app.Application;

public class GlobalVariable extends Application {
    private static GlobalVariable instance = null;
    private String account;
    private String password;
    private String[] course;
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public static synchronized GlobalVariable getInstance(){
        if(null == instance){
            instance = new GlobalVariable();
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
