package com.example.easyplan;

public class ScheduleContentItem {
    String courseName, instructorName, meetingTime;

    public ScheduleContentItem() {

    }

    public ScheduleContentItem(String courseName, String instructorName, String meetingTime) {
        this.courseName = courseName;
        this.instructorName = instructorName;
        this.meetingTime = meetingTime;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public String getMeetingTime() {
        return meetingTime;
    }

    public void setMeetingTime(String meetingTime) {
        this.meetingTime = meetingTime;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
