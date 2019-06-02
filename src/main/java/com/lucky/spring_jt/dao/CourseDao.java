package com.lucky.spring_jt.dao;


import com.lucky.spring_jt.entity.Course;

import java.util.List;

public interface CourseDao {
    void insert(Course course);
    void update(Course course);
    void delete(int id);
    Course select(int id);
    List<Course> selectAll();
}
