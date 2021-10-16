package com.quocdat.assignment_mob201.dao;

import com.quocdat.assignment_mob201.models.User;

public interface IUser {
    User login(String usernam, String password);
    boolean register(User user);
    boolean update(User user);
    boolean checkUsernameExist(String username);
}
