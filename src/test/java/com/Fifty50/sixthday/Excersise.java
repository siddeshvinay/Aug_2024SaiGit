package com.Fifty50.sixthday;

public class Excersise {

    public static void primenum() {
        //what is primenmber--> it should divide by itself
        for (int i = 0; i <= 100; i++) {
            if(isprime(i)) {
                System.out.println(i);
            }
        }
    }
    public static boolean isprime(int num){
        for(int i=2; i*i<=num; i++){
            if(num%i==0){
                return false;
            }
        }
        return true;
    }

/*    public static void numispro() {
        int num = 97;
        boolean isPrime = true;
        int sqrtNum = (int) Math.sqrt(num);
        for (int i = 2; i <= sqrtNum; i++) {
            if (num % i == 0) {
                isPrime = false;
                break;
            }
        }
        System.out.println(num + " is " + (isPrime ? "prime" : "not prime"));

    }*/
////////////////////////////////////////////////////////////////////////////
    public static void fibinociser(){
        int num=50;
        //how fibino works -- add the previous number to it
        //ex: 1,2,3,4,5,6,7,8,9,10
        //o/p==1+2=3, 2+3=5, 3+5=8, 4+8=12, 5+12=17, 6+17=23
        //0  +  1=  1,          1+1=2,     2+1=3, 2+3=5, 5+3=8, 5+8=13, 8+13=21, 13+21=34, 21+34=54


        //have to write the code
        int sum=0;
        int n1=0, n2=1;
        for(int i=0; i<10; i++){
            sum=n1+n2;
            n1=n2;
            n2=sum;
            System.out.println(sum);
        }
    }


    public static void leet1(){
        String str="is2 come4 and3 This1";
        String spl[]=str.split(" ");
        String finlre[]= new String[spl.length];
        int index;
        for(int i=0; i<=spl.length-1; i++){
            index= Character.getNumericValue(spl[i].charAt(spl[i].length()-1));
           finlre[index-1]=spl[i].substring(0,spl[i].length()-1);

        }
      for(String x:finlre){
            System.out.print(x+" ");
        }
    }

    public static void main(String[] args) {
       // primenum();
       // fibinociser();
        leet1();


    }
}

