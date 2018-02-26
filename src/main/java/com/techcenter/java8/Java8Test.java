package com.techcenter.java8;

import com.techcenter.java8.bean.Student;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 有关java8的一些新功能研究中心
 * @author starSky
 * @date 2/26/2018 9:39 PM.
 */
public class Java8Test {

    public static void main(String[] args) {
        List<Student> students = Arrays.asList(new Student(1, "张三", 10,100),
                new Student(1, "李四", 11,10),
                new Student(2, "许三多", 20,100));
        //将所有的名字汇总在一起
        List<String> collect = students.stream().collect(Collectors.mapping(Student::getName, Collectors.toList()));
        System.out.println(collect);
        //按班级分组取每个班级的学生名字
        Map<Integer, List<String>> className = students.stream().collect(Collectors.groupingBy(Student::getClassId, Collectors.mapping(Student::getName, Collectors.toList())));
        System.out.println(className);
        //如何一次汇总成绩、年龄？

    }
}
