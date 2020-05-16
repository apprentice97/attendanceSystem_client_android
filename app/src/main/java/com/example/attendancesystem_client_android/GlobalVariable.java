package com.example.attendancesystem_client_android;

import android.app.Application;

import com.example.attendancesystem_client_android.bean.Attendance;
import com.example.attendancesystem_client_android.bean.Course;
import com.example.attendancesystem_client_android.bean.Student;
import com.example.attendancesystem_client_android.bean.Teacher;

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
    private String student_name;
    private String student_class_id;
    private String teacher_name;
    private List<Attendance> application_for_leave;
    private int application_for_leave_position;
    private List<Attendance> teacher_check;
    private int teacher_check_position;
    private String teacher_email;
    private int manager_teacher_position;
    private List<Teacher> manager_teacher;
    private int manager_student_position;
    private List<Student> manager_student;
    private List<Attendance> student_attendance;
    private int student_attendance_position;

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getStudent_class_id() {
        return student_class_id;
    }

    public void setStudent_class_id(String student_class_id) {
        this.student_class_id = student_class_id;
    }

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

    public String getUrlStudent() {
        return "http://192.168.137.1/mgr/student/";
    }

    public String getUrlTeacher() {
        return "http://192.168.137.1/mgr/teacher/";
    }

    public List<Attendance> getApplication_for_leave() {
        return application_for_leave;
    }

    public void setApplication_for_leave(List<Attendance> application_for_leave) {
        this.application_for_leave = application_for_leave;
    }

    public int getApplication_for_leave_position() {
        return application_for_leave_position;
    }

    public void setApplication_for_leave_position(int application_for_leave_position) {
        this.application_for_leave_position = application_for_leave_position;
    }

    public List<Attendance> getTeacher_check() {
        return teacher_check;
    }

    public void setTeacher_check(List<Attendance> teacher_check) {
        this.teacher_check = teacher_check;
    }

    public int getTeacher_check_position() {
        return teacher_check_position;
    }

    public void setTeacher_check_position(int teacher_check_position) {
        this.teacher_check_position = teacher_check_position;
    }

    public String getTeacher_email() {
        return teacher_email;
    }

    public void setTeacher_email(String teacher_email) {
        this.teacher_email = teacher_email;
    }

    public List<Teacher> getManager_teacher() {
        return manager_teacher;
    }

    public void setManager_teacher(List<Teacher> manager_teacher) {
        this.manager_teacher = manager_teacher;
    }

    public int getManager_teacher_position() {
        return manager_teacher_position;
    }

    public void setManager_teacher_position(int manager_teacher_position) {
        this.manager_teacher_position = manager_teacher_position;
    }

    public int getManager_student_position() {
        return manager_student_position;
    }

    public void setManager_student_position(int manager_student_position) {
        this.manager_student_position = manager_student_position;
    }

    public List<Student> getManager_student() {
        return manager_student;
    }

    public void setManager_student(List<Student> manager_student) {
        this.manager_student = manager_student;
    }

    public List<Attendance> getStudent_attendance() {
        return student_attendance;
    }

    public void setStudent_attendance(List<Attendance> student_attendance) {
        this.student_attendance = student_attendance;
    }

    public int getStudent_attendance_position() {
        return student_attendance_position;
    }

    public void setStudent_attendance_position(int student_attendance_position) {
        this.student_attendance_position = student_attendance_position;
    }
}
