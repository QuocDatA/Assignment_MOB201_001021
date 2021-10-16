package com.quocdat.assignment_mob201.models;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    private Integer id, role;
    private String username, password, name, phone, dob;

    public User() {
    }

    public User(Integer id, Integer role, String username, String password, String name, String phone, String dob) {
        this.id = id;
        this.role = role;
        this.username = username;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.dob = dob;
    }

    protected User(Parcel in) {
        id = in.readInt();
        role = in.readInt();
        username = in.readString();
        password = in.readString();
        name = in.readString();
        phone = in.readString();
        dob = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(role);
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(name);
        dest.writeString(phone);
        dest.writeString(dob);
    }
}
