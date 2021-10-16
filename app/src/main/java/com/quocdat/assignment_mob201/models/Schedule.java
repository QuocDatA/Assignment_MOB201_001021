package com.quocdat.assignment_mob201.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Schedule implements Parcelable {

    private Integer id, course_id, type;
    private String date, time, address, meet;

    public Schedule() {
    }

    public Schedule(Integer id, Integer course_id, Integer type, String date, String time, String address, String meet) {
        this.id = id;
        this.course_id = course_id;
        this.type = type;
        this.date = date;
        this.time = time;
        this.address = address;
        this.meet = meet;
    }

    protected Schedule(Parcel in) {
        id = in.readInt();
        course_id = in.readInt();
        type = in.readInt();
        date = in.readString();
        time = in.readString();
        address = in.readString();
        meet = in.readString();
    }

    public static final Creator<Schedule> CREATOR = new Creator<Schedule>() {
        @Override
        public Schedule createFromParcel(Parcel in) {
            return new Schedule(in);
        }

        @Override
        public Schedule[] newArray(int size) {
            return new Schedule[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCourse_id() {
        return course_id;
    }

    public void setCourse_id(Integer course_id) {
        this.course_id = course_id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
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



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(course_id);
        dest.writeInt(type);
        dest.writeString(date);
        dest.writeString(time);
        dest.writeString(address);
        dest.writeString(meet);
    }
}
