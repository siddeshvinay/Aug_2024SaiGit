package com.Fifty50.Fiftyone1.forty9.fortyeight.leetcode53.REstAPI1.SYJPractice;

import java.util.*;

public class FortySix {

    public static void swap(){
        String str="ccba";
        char ca[]=str.toCharArray();
        //swap the least lexogically value for double char
        HashMap<Character, Integer> emap= new HashMap<>();
        for(int i=0; i<ca.length; i++){
            char c=ca[i];
            if(emap.containsKey(c)){
                emap.put(c, emap.get(c)+1);
                }
            else{
                emap.put(c, 1);
            }
          }
        //to get the values for more than one count
       char c1='0';
        Set<Map.Entry<Character,Integer>> eset=emap.entrySet();
        for(Map.Entry<Character, Integer> te: eset){
             if(te.getValue()>1){
                 //System.out.println(te+" the value is"+te.getValue());
                 c1= te.getKey();
             }
        }
  //now you got the duoble value have replace with least
        Set<Character> keu=emap.keySet();
        List<Integer> lear= new ArrayList<>();
        for(Character cah: keu){
            char ca1=cah.charValue();
            int least=ca1-'0';
            lear.add(least);
        }
        for(int i=0; i<lear.size(); i++){
            System.out.println(lear.get(i));
        }
        System.out.println('0');
    }
    public static void main(String[] args) {
        swap();

    }
}
