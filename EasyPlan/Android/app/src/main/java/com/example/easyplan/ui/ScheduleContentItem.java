package com.example.easyplan.ui;

public class ScheduleContentItem {
    String courseName;

    public ScheduleContentItem() {

    }

    public ScheduleContentItem(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
