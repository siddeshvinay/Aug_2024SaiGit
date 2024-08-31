package com.Fifty50.Fiftyone1.forty9.fortyeight.leetcode53.REstAPI1;

public class threadlo {
    public static void main(String[] args) {
        ThreadLocal cout2= new ThreadLocal();
        cout2.set("Siddehs");

        String ac=(String)cout2.get();
        System.out.println(ac);

        cout2.remove();
        System.out.println((String)cout2.get());

        ThreadLocal cow1=ThreadLocal.withInitial(()->"Sididya");
        String ac1=(String)cow1.get();
        System.out.println(ac1);

    }

}
