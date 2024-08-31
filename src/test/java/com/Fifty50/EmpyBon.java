package com.Fifty50;

import java.util.ArrayList;
import java.util.function.Function;
import java.util.function.Predicate;

public class EmpyBon {

    int sal;
    String name;

    EmpyBon(int sal, String name){
        this.sal=sal;
        this.name=name;
    }

    public static void main(String[] args) {

        ArrayList<EmpyBon> arbn= new ArrayList<>();
        arbn.add(new EmpyBon(50000, "joy"));
        arbn.add(new EmpyBon(75000, "Roy"));
        arbn.add(new EmpyBon(78000, "Deep"));
        arbn.add(new EmpyBon(35000, "Tod"));

        Function<EmpyBon, Integer> fnbb=e->{
                       int sal=e.sal;
                       if(sal>50000){
                           return (sal*10/100);
                       }else if(sal>60000) {
                           return (sal * 20 / 100);
                       }
                       else if(sal>70000) {
                           return (sal * 30 / 100);
                       }
                       else
                           return (sal * 40 / 100);
                       };


        Predicate<Integer> p=b->b>15000;
           for(EmpyBon ebn: arbn){
              int bouns=fnbb.apply(ebn);
              if(p.test(bouns)) {
                  System.out.println(ebn.name + "--" + ebn.sal);
                  System.out.println(bouns);
              }
              }
           }

         }

