package com.example.easyplan;

import java.util.ArrayList;

public class Schedule {

    private ArrayList<Course> courses;
    private boolean isFavorite = false;
    private String name;

    public Schedule() {
        courses = new ArrayList<>();
    }

    public void addToSchedule(Course course) {
        courses.add(course);
    }

    public ArrayList<Course> getCourseList() {
        return courses;
    }

    public void setCourseList(ArrayList<Course> courses) {
        this.courses = courses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
