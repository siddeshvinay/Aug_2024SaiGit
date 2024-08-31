package com.Fifty50;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

class product{
    int sal;

    public int getSal() {
        return sal;
    }

    public void setSal(int sal) {
        this.sal = sal;
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

    String name;
    int age;

    product(String name, int age, int sal){
        this.name=name;
        this.age=age;
        this.sal=sal;
    }

}

public class streammap1 {
    public static void main(String[] args) {
        List<product> listpro= Arrays.asList(
                new product("Siddesh", 40, 2000),
                new product("Diya", 11, 50000),
                new product("Vinu", 36, 70000),
                new product("luhu", 15, 101210)

        );

     List<Integer> avags=listpro.stream().
                filter(e->e.sal>10000).
                map(e->e.sal/listpro.size()).
                collect(Collectors.toList());

      List<Double> ax= Collections.singletonList(listpro.stream().
              collect(Collectors.averagingInt(product::getSal)));
        System.out.println(ax);

    }

}
