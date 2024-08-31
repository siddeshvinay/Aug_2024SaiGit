package com.Fifty50.Fiftyone1.forty9.fortyeight.leetcode53.REstAPI1.SYJPractice;

import org.bson.io.BsonOutput;

public class FortyFive {

    //Java Program to copy all elements of one array into another array
    public static void test1(){
        int arr[]={3,4,6,1,8,4,5,2,22,11,10};
        int copyarr[]=new int[arr.length];

        System.arraycopy(arr, 0, copyarr, 1, 9);

        for(int i=0; i<copyarr.length; i++){
           // copyarr[i]=arr[i];
            System.out.print(copyarr[i]+ " ");
        }
    }

    //Java Program to find the frequency of each element in the array
    public static void frequecny(){
        int arr[]={3,4,6,1,7,3,4,5,2,22,11,10,22,1,4,7};
        int fre[]=new int[arr.length];
        int visited=-1;
        for(int i=0; i<arr.length; i++){
            int count=1;
            for(int j=i+1; j<arr.length; j++){
                if(arr[i]==arr[j]){
                    count++;
                    fre[j]=visited;
                }
            }
            if(fre[i]!=visited){
                fre[i]=count;
            }
        }
        for(int i=0; i<fre.length; i++){
            if(fre[i]!=visited){
                System.out.println(arr[i]+" the count is "+fre[i]);
            }
        }
    }

    //Java Program to left rotate the elements of an array
    public static void leftarrr(){
        int arr[]={1, 2, 3, 4, 5};
        int res[]= new int[arr.length];

        //how to rotate the values think brute force method
        int leftemp=0;
        for(int i=0; i<arr.length;i++){
            for(int j=i; j<arr.length; j++){
                int temp=arr[i];
                arr[i]=arr[j];
                arr[j]=temp;
            }
            System.out.print(arr[i]);
        }
    }

    //Keep the spaces intact and reverse the string
    public static void strver(){
        String str="I am String";
        //create a empty character string
        char c[]=str.toCharArray();
        char res[]=new char[c.length];

        for(int i=0; i<c.length; i++){
            if(c[i]==' '){
                res[i]=c[i];
            }
        }
        int j=res.length-1;
        for(int i=0; i<c.length; i++){
            if(res[i]!=' '){
                if(res[j]==' '){
                    j--;
                }
                res[j]=c[i];
                j--;
            }
        }
        System.out.println(String.valueOf(res));
    }

//get the string as per the numerical values
    public static void numersa(){
        String str="a2b3c4";
        String sre="tet";
       // System.out.println(str.substring(5, 6));

        // there are two methods
        // convert get the integer value
        StringBuilder sb= new StringBuilder();
        String res="";
      for(int i=0; i<str.length(); i+=2){
          int count= Integer.parseInt(str.substring(i+1, i+2));
           for(int j=0; j<count; j++){
               res+=str.charAt(i);
           }
       }
        System.out.println(res);
    }

//Second methdd
    public static void strein(){
        String str="a2b3c4";

        int count=0;
        StringBuilder sb= new StringBuilder();
        for(int i=0; i<str.length(); i+=2){
            char c=str.charAt(i);
            count=str.charAt(i+1)-'0';
            for(int j=0; j<count; j++){
                sb.append(c);
            }
        }
        System.out.println(sb.toString());
    }

//To the the Ip address with this format
    public static boolean ipddress(){
        String str="4.3.6";
        String set[]=str.split("\\.");
        for(String dig:set){
            if(dig.length()>3 && dig.length()<0){
                return false;
            }
        }
        //System.out.println(set[0].length());
        int sum1=0;
        for(int i=0; i<set[0].length(); i++){
            String x=String.valueOf(set[0].charAt(i));
            int x1=Integer.parseInt(x);
            sum1+=x1;
        }
        if(sum1 % 2!=0){
            return false;
        }

        int sum2=0;
        for(int i=0; i<set[1].length(); i++){
            String x=String.valueOf(set[1].charAt(i));
            int x1=Integer.parseInt(x);
            sum2+=x1;
        }
        if(sum2 % 2==0){
            return false;
        }
        int sum3=0;
        for(int i=0; i<set[2].length(); i++){
            String x=String.valueOf(set[1].charAt(i));
            int x1=Integer.parseInt(x);
            sum3+=x1;
        }
        if(sum3>sum2 && sum3>sum1){
            return false;
        }
       return true;
    }

    public static void appred(){
        String str="a3b4c5";
        StringBuilder sb= new StringBuilder();
        for(int i=1; i<str.length(); i+=2){
            int index=Character.getNumericValue(str.charAt(i));
            for(int j=0; j<index; j++)
            sb.append(str.charAt(i-1));
        }
        System.out.print(sb);
    }

    public static void main(String[] args) {
       // test1();
       // frequecny();
        leftarrr();
        //strver();
        //numersa();
        //strein();
        //strein();
        //appred();
       // System.out.println(ipddress());

    }
}
