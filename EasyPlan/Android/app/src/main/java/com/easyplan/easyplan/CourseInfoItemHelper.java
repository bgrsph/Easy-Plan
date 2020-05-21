package com.easyplan.easyplan;

public class CourseInfoItemHelper {

    String courseName, instructor, meetingTime, section;

    public CourseInfoItemHelper(String courseName, String instructor, String meetingTime, String section) {
        this.courseName = courseName;
        this.instructor = instructor;
        this.meetingTime = meetingTime;
        this.section = section;
    }

    public CourseInfoItemHelper() {
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getMeetingTime() {
        return meetingTime;
    }

    public void setMeetingTime(String meetingTime) {
        this.meetingTime = meetingTime;
    }
}
