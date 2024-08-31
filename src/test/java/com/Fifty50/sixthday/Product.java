package com.Fifty50.sixthday;

import java.util.Arrays;
import java.util.List;

public class Product {

    private String name;
    private int age;
    private int salary;

    public Product(String name, int age, int salary){
        this.name=name;
        this.age=age;
        this.salary=salary;
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

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public static void main(String[] args) {
        List<Product> productslis= Arrays.asList(
                new Product("siddesh", 40, 50000),
                new Product("Diya", 12, 75000),
                new Product("Vinu", 36,100000),
                new Product("Home", 75, 85000));

        int avgsal = 0;
        int sumsal=0;
        for(Product P: productslis) {
            sumsal += P.getSalary();
        }
            avgsal=sumsal/ productslis.size();
            System.out.println(avgsal);
    }
}
