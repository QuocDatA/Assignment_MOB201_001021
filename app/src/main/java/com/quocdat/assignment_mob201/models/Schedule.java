package com.quocdat.assignment_mob201.models;

public class Schedule {
    private Integer id_schedule, course_id, type;
    private String date, time, address, meet;

    public Schedule() {
    }

    public Schedule(int id_schedule, int course_id, int type, String date, String time, String address, String meet) {
        this.id_schedule = id_schedule;
        this.course_id = course_id;
        this.type = type;
        this.date = date;
        this.time = time;
        this.address = address;
        this.meet = meet;
    }

    public int getId_schedule() {
        return id_schedule;
    }

    public void setId_schedule(int id_schedule) {
        this.id_schedule = id_schedule;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMeet() {
        return meet;
    }

    public void setMeet(String meet) {
        this.meet = meet;
    }
}
