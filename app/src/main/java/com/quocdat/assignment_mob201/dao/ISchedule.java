package com.quocdat.assignment_mob201.dao;

import androidx.recyclerview.widget.LinearSmoothScroller;

import com.quocdat.assignment_mob201.models.Schedule;

import java.util.List;

public interface ISchedule {
    List<Schedule> getAllSchedule(int type, int courseId);
    List<Schedule> get(int type);
    boolean insert(Schedule schedule);
    boolean update(Schedule schedule);
    boolean delete(int id);
}
