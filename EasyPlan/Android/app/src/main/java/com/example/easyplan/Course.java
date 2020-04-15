package com.example.easyplan;

public class Course {

    public String code;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public Course() {
    }

    public Course(String code) {
        this.code = code;
    }
}
