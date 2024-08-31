package com.Fifty50;

import org.apache.poi.util.ArrayUtil;

import java.util.ArrayList;
import java.util.function.Predicate;

public class Employee {

    int salary;
    String name;
    int age;

    Employee(String Name, int Age, int Sal){
        name=Name;
        age=Age;
        salary=Sal;
    }

    public static void main(String[] args) {
       //Employee emp= new Employee("Siddesh", 45, 50000);

        Predicate<Employee> pre=e->(e.salary>40000 && e.age>41);

        ArrayList<Employee> ar= new ArrayList<Employee>();
        ar.add(new Employee("Siddesh", 45, 50000));
        ar.add(new Employee("Vinu", 35, 750000));
        ar.add(new Employee("Diya", 5, 15000));
        ar.add(new Employee("Naveen", 65, 950000));

        for(Employee ea: ar){
            if(pre.test(ea)){
                System.out.println(ea.name);
            }
        }
    }
}
