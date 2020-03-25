package com.example.easyplan;

public class Course {

    private String id;
    private String catalog;
    private String subject;

    public Course(String id, String catalog, String subject) {
        this.id = id;
        this.catalog = catalog;
        this.subject = subject;
    }

    public Course() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
