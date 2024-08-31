package com.Fifty50.sixthday;

import java.util.Stack;

public class fiftysix6 {

    public static boolean ipadress(String str) {

        String spl[] = str.split("\\.");
        for(int i=0; i<spl.length; i++){
            if(spl[i].length()==0){
                return false;
            }

        int sum=0;//the sum of the first three digits must be even
        for(int l=0; l<=spl[0].length()-1; l++){
            sum+=spl[0].charAt(l)-'0';}

           if(sum%2!=0){
               return false;
           }


            int sum1=0;//the sum of the first three digits must be even
            for(int l=0; l<=spl[1].length()-1; l++){
                sum1+=spl[1].charAt(l)-'0';}

            if(sum1%2==0){
                return false;
            }

        int sum2=0;
        for(int k=0; k<spl[2].length()-1; k++){
            sum+=spl[2].charAt(k)-'0';}
            if(sum2>sum1 && sum2>sum){
                return true;
            }

        }
     return true;
    }

    public static void main(String[] args) {
        String str = "123.53.234";
        System.out.println(ipadress(str));
    }

}
