package com.test;

import com.google.common.collect.ComparisonChain;

/**
 * Created by Administrator on 2015/7/24 0024.
 */
public class H implements Comparable<H> {
    private String name;
    private Integer age;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    @Override
    public String toString() {
        return "H{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public static void main(String[] args) {
        H h = new H();
        System.out.println( h.toString() );
    }

    @Override
    public int compareTo(H o) {
        return ComparisonChain.start()
                .compare(this.name, o.name)
                .compare(this.age, o.age)
                .result();
    }
}
