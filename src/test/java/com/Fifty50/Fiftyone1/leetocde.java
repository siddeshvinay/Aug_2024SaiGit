package com.Fifty50.Fiftyone1;

public class leetocde {

    public static void leet1(){
        String str="is2 come4 and3 This1";
        String res[]= new String[str.length()];
        String fina="";

        String spl[]=str.split(" ");

        for(int i=0; i<=spl.length-1; i++){
            char c= spl[i].charAt(spl[i].length()-1);
            int num=Character.getNumericValue(c);
            res[num-1]=spl[i].substring(0, spl[i].length()-1);
            String.join(" ",res);
            System.out.print(res[i]+" ");
        }

    }

    public static void main(String[] args) {
        leet1();
    }
}
