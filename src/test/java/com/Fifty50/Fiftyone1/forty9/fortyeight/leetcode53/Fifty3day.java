package com.Fifty50.Fiftyone1.forty9.fortyeight.leetcode53;

import java.util.*;
import java.util.stream.Collectors;

public class Fifty3day {

    public static String validnumber(){
        String str="leEeetCcode";
        String fina="";
        char ca[]=str.toCharArray();
        Stack<Character> st= new Stack<>();
        //now I am adding charcter into stak
                    for(char current: ca){
                        if(st.size()==0){
                            st.push(current);
                        }
                        else {
                            char top=st.peek();
                            if(Character.toUpperCase(current)==Character.toUpperCase(top)){  //if both arrr AA then

                                if(Character.isLowerCase(current) && Character.isUpperCase(top) ||
                                        Character.isUpperCase(current) && Character.isLowerCase(top)){
                                    st.pop();
                                }else{
                                    st.push(current);
                                }
                            }
                            else {
                                st.push(current);
                            }
                        }
                    }

        StringBuilder sb= new StringBuilder();
            while(st.size()!=0){
               sb.append(st.pop());
        }
        fina=sb.reverse().toString();
        System.out.println(fina);
            return fina;

    }

    /////////////////////////////////////////////////////////////
    public static void streamAPI(){
        List<String> coures= Arrays.asList("Java","Selenium", "Java", "Kotlin");
        List<String>res= new ArrayList<>();

        for(String cou:coures){
            if(!res.contains(cou)){
                res.add(cou);
            }
        }
       // System.out.println(res);
      //by Using stream API
       List<String> fina= coures.stream().distinct().collect(Collectors.toList());
       // System.out.println(fina);

        //To filter the data
        List<String> coures1= Arrays.asList("Java coures","Somke testing", "Selenium", "Selenium testing",

                " Functional testing", "Regression testing");
        List<String>res1= new ArrayList<>();

        for(String test: coures1){
            if(test.contains("testing")) {
                res1.add(test);
            }
        }
       // System.out.println(res1);
        List<String> fia=coures1.stream().filter(re->re.contains("testing") || re.contains("Selenium") ).
                collect(Collectors.toList());
        System.out.println(fia);

    }

    public static void streamAPI2(){
        List<Integer> werr=Arrays.asList(1,5,3,6,2,10,25);
            List<Integer> re=werr.stream().sorted().collect(Collectors.toList());
       // System.out.println(re);

        List<Integer> werr1=Arrays.asList(1,5,3,6,2,10,25);
        List<Integer> re1=werr.stream().sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
        System.out.println(re1);
    }




    public static void main(String[] args) {
        //validnumber();
        streamAPI2();
    }
}
