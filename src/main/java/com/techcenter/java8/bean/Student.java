package com.techcenter.java8.bean;

/**
 * @author starSky
 * @date 2/26/2018 9:40 PM.
 */
public class Student {
    private Integer classId;
    private String name;
    private Integer age;
    private int grade;


    public Student() {
    }

    public Student(Integer classId, String name, Integer age) {
        this.classId = classId;
        this.name = name;
        this.age = age;
    }

    public Student(Integer classId, String name, Integer age, int grade) {
        this.classId = classId;
        this.name = name;
        this.age = age;
        this.grade = grade;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
