package com.example.attendancesystem_client_android.bean;

public class Course {
    private String course_id;
    private String name;

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Course{" +
                "course_id='" + course_id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
