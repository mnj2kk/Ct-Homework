package com.company;

import java.util.Scanner;

public class H {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println(rec(scan.nextInt()));
    }

    public static String rec(int n) {
        if (n == 1) {
            return "((A0|B0)|(A0|B0))";
        } else {
            StringBuilder stringBuilder = new StringBuilder("((");
            return String.valueOf(stringBuilder.append(rec(n - 1)).
                    append("|((A").append(n - 1).append("|A").append(n - 1)
                    .append(")|(B").append(n - 1).append("|B").append(n - 1)
                    .append(")))|(A").append(n-1).append("|B").append(n-1).append("))"));


        }
    }
}
