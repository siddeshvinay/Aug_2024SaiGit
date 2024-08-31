package com.Fifty50.Fiftyone1.forty9;

import java.util.function.Predicate;

@FunctionalInterface
interface cab{
    public void bookcab();
}



public class lambdaclass {

    public static void predica(){
        Predicate<String> p=s -> (s.length()>4);
        //System.out.println(p.test("Siddesh"));
        String name[]={"siddesh", "vinu", "diaya"};

        for(String st: name){
            if(p.test(st)){
                System.out.println(st);
            }
        }
    }


    public static void main(String[] args) {
        cab c = ()->System.out.println("ola cab is booked............");
         c.bookcab();
         predica();
    }
}
