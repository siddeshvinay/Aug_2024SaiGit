package com.Fifty50.four4day;

import java.util.Stack;

public class Leetcoodes1 {

    public static String letcode() {
        String str = "leEtCccode";
        // By using stack I am going to perform this
        char[] ca = str.toCharArray();
        Stack<Character> sta = new Stack<>();
        for (char current : ca) {
            if (sta.size() == 0) {
                sta.push(current);
            } else {
                char top = sta.peek();
                if (Character.toUpperCase(current)==Character.toUpperCase(top)) {
                    if (Character.isLowerCase(current) && Character.isUpperCase(top) ||
                            Character.isUpperCase(current) && Character.isLowerCase(top)) {
                        sta.pop();
                    } else {
                        sta.push(current);
                    }
                } else {
                    sta.push(current);
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        while (sta.size() != 0) {
            sb.append(sta.peek());

        }
       String fina= sb.reverse().toString();
        System.out.println(fina);
        return fina;

    }

    public static void main(String[] args) {
        letcode();
    }
}
