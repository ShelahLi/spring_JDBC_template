package com.lucky.spring_jt.dao;

import com.lucky.spring_jt.entity.Student;

import java.util.List;

public interface StudentDao {
    void insert(Student stu);
    void update(Student stu);
    void delete(int id);
    Student select(int id);
    List<Student> selectAll();
}
