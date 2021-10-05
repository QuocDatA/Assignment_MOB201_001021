package com.quocdat.assignment_mob201.dao;

import com.quocdat.assignment_mob201.models.Course;

import java.util.List;

public interface ICourse {
    List<Course> getAllCourses();
    //select * from courses inner join enroll on courseid = courseid where userid =2
    List<Course> getAllCoursesByStudentId(int studentId);
    Course getCourseById(int id);
    boolean insert(Course course);
    boolean update(Course course);
    boolean delete(int id);
}
