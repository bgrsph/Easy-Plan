package com.example.easyplan;

public class User {

    public String admin;
    public String id;
    public String name;
    public String surname;

    public User(String admin, String id, String name, String surname) {
        this.admin = admin;
        this.id = id;
        this.name = name;
        this.surname = surname;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
