package com.quocdat.assignment_mob201.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Enroll implements Parcelable {

    private Integer id, course_id, user_id;
    private String date;

    public Enroll() {
    }

    public Enroll(Integer id, Integer course_id, Integer user_id, String date) {
        this.id = id;
        this.course_id = course_id;
        this.user_id = user_id;
        this.date = date;
    }

    protected Enroll(Parcel in) {
        id = in.readInt();
        course_id = in.readInt();
        user_id = in.readInt();
        date = in.readString();
    }

    public static final Creator<Enroll> CREATOR = new Creator<Enroll>() {
        @Override
        public Enroll createFromParcel(Parcel in) {
            return new Enroll(in);
        }

        @Override
        public Enroll[] newArray(int size) {
            return new Enroll[size];
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

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(course_id);
        dest.writeInt(user_id);
        dest.writeString(date);
    }
}
