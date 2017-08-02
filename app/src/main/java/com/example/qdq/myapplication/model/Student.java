package com.example.qdq.myapplication.model;

import java.util.List;

/**
 * Created by Administrator on 2017/7/31.
 */

public class Student {
    private String name;
    private int age;
    private List<Course> courses;
    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
