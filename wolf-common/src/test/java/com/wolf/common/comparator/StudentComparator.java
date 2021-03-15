package com.wolf.common.comparator;

import java.util.Comparator;

/**
 * @Description TODO
 * @Author zhanghong
 * @Date 2021/1/4 4:09 PM
 * @Version 1.0
 */
public class StudentComparator implements Comparator<Student> {

    @Override
    public int compare(Student o1, Student o2) {
        if (o1.getScore() > o2.getScore()) {
            return 1;
        } else if (o1.getScore() < o2.getScore()) {
            return -1;
        } else {
            return 0;
        }
    }

    public static void main(String[] args) {
        Student[] sts = new Student[]{
                new Student("小戴", 60.01),
                new Student("小王", 90.01),
                new Student("老王", 80),
                new Student("小萱", 95)
        };

        java.util.Arrays.sort(sts, new StudentComparator());
        System.out.println(java.util.Arrays.toString(sts));
    }
}
