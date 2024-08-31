package com.Fifty50.Fiftyone1.forty9.fortyeight.leetcode53.REstAPI1.SYJPractice;

public class DigitChecker {
    public static boolean checkDigits(String input) {
        String[] sets = input.split("\\.");
        if (sets.length != 3) {
            return false;
        }

        // Check first set of digits (add up to an even number)
        int sum1 = 0;
        for (char c : sets[0].toCharArray()) {
            sum1 += c - '0';
        }
        if (sum1 % 2 != 0) {
            return false;
        }

        // Check second set of digits (add up to an odd number)
        int sum2 = 0;
        for (char c : sets[1].toCharArray()) {
            sum2 += c - '0';
        }
        if (sum2 % 2 == 0) {
            return false;
        }

        // Check third set of digits (each digit larger than previous two)
        int[] digits = new int[3];
        for (int i = 0; i < 3; i++) {
            digits[i] = sets[2].charAt(i) - '0';
        }
        if (digits[0] >= digits[1] || digits[1] >= digits[2]) {
            return false;
        }

        return true;
    }

    public static void main(String[] args) {
        String input = "1.1.4";
        System.out.println(checkDigits(input)); // Output: true
    }
}
