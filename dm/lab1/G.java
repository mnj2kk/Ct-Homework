package com.company;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class G {
    static String[] numbers;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        numbers = new String[n];
        int maxL = 0;
        for (int i = 0; i < n; i++) {
            String k = Long.toBinaryString(scan.nextLong());
            numbers[i] = k;
            System.err.println(k.length());
            if (maxL <k.length()) {
                maxL = k.length();
            }
        }
        String r = Long.toBinaryString(scan.nextLong());
        if (r.length() > maxL) {
            maxL = r.length();
        }
        for (int i = 0; i < n; i++) {
            String tmp = numbers[i];
            numbers[i] = "0".repeat(maxL + 1 - tmp.length()) + tmp;
        }
        if (!r.contains("1")) {
            System.out.println("1&~1");
            return;
        }
        r = "0".repeat(maxL + 1 - r.length()) + r;
        HashMap<String, Integer> values = new HashMap<>();
        for (int i = 0; i < maxL + 1; i++) {
            StringBuilder v = new StringBuilder();
            int ch = Character.getNumericValue(r.charAt(i));
            for (int j = 0; j < n; j++) {
                v.append(numbers[j].charAt(i));
            }
            if (values.containsKey(v.toString())) {
                if (values.get(v.toString()) != ch) {
                    System.out.println("Impossible");
                    return;
                }
            } else {
                values.put(v.toString(), ch);
            }
        }
        StringBuilder func = new StringBuilder();
        for (int i = 0; i < maxL + 1; i++) {

            int ch = Character.getNumericValue(r.charAt(i));
            if (ch == 1) {
                func.append("(");
                for (int j = 0; j < n; j++) {
                    if (numbers[j].charAt(i) == '1') {
                        func.append(j + 1);

                    } else {
                        func.append("~").append(j + 1);
                    }
                    if (j != n - 1) {
                        func.append("&");
                    }
                }
                if (i == r.lastIndexOf("1")) {
                    func.append(")");
                } else {
                    func.append(")|");
                }

            }

        }
        System.out.println(func);

    }
}
