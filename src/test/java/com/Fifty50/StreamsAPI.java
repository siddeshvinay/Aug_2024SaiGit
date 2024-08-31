package com.Fifty50;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StreamsAPI {

    public static void main(String[] args) {
        List<Integer> are= Arrays.asList(10,20,15,45,68,78);
        List<Integer> evennum= new ArrayList<>();

       // evennum=are.stream().filter(n->n%2==0).collect(Collectors.toList());
        //System.out.println(evennum);
        //are.stream().filter(n->n%2==0).forEach(n-> System.out.println(n));
        are.stream().filter(n->n%2==0).forEach(System.out::println);
    }
}
