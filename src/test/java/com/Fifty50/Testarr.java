package com.Fifty50;

import java.util.function.Predicate;

public class Testarr {
    public static void main(String[] args) {
        int arr[]={2,4,5, 6,7,8,9,23,11,56,60,67,45};

        Predicate<Integer> p1=i->(i%2==0);
        Predicate<Integer> p2=i->(i>50);

   /*     for(int n: arr){
            if(p1.test(n) && p2.test(n)){
                System.out.println(n);
            }
        }*/

 /*       for(int n: arr){
            if(p1.or(p2).test(n)){
                System.out.println(n);
            }
        }*/
        for(int n: arr){
            if(p2.negate().test(n)){
                System.out.println(n);
            }
        }

    }
}
