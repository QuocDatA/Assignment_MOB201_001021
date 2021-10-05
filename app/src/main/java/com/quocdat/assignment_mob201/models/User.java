package com.quocdat.assignment_mob201.models;

public class User {
    private int id_user, role;
    private String username, password, nameUser, phone, dob;

    public User() {
    }

    public User(int id, int role, String username, String password, String nameUser, String phone, String dob) {
        this.id_user = id;
        this.role = role;
        this.username = username;
        this.password = password;
        this.nameUser = nameUser;
        this.phone = phone;
        this.dob = dob;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
}
