package com.quocdat.assignment_mob201.models;

public class Enroll {
    private int id_enroll, course_id, user_id;
    private String date_enroll;

    public Enroll() {
    }

    public Enroll(int id_enroll, int course_id, int user_id, String date_enroll) {
        this.id_enroll = id_enroll;
        this.course_id = course_id;
        this.user_id = user_id;
        this.date_enroll = date_enroll;
    }

    public int getId_enroll() {
        return id_enroll;
    }

    public void setId_enroll(int id_enroll) {
        this.id_enroll = id_enroll;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getDate_enroll() {
        return date_enroll;
    }

    public void setDate_enroll(String date_enroll) {
        this.date_enroll = date_enroll;
    }
}
