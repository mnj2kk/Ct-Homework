package com.company;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class B {
    public static void main(String[] args) {
        boolean is0 = true, is1 = true, isM = true, isL = true, isD = true;
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        for (int i = 0; i < n; i++) {
            int a = scan.nextInt();

            String f = scan.next();
            String[] ist = new String[f.length()];
            for (int j = 0; j < f.length(); j++) {
                String k = Integer.toBinaryString(j);
                String ans = "0".repeat(Math.max(0, a - k.length())) +
                        k;
                ist[j] = ans;
            }
            if (!isSamoDvoy(f)) {
                isD = false;
            }
            if (!isMonotonic(n,ist, f)) {
                isM = false;
            }
            if (f.charAt(0) == '1') {
                is0 = false;
            }
            if (f.charAt(f.length() - 1) == '0') {
                is1 = false;
            }
            if (!isPolidrom(n, ist, f)) {
                isL = false;
            }

        }
        System.err.println(isM);
        if (!is0 && !is1 && !isM && !isL && !isD) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }
    }

    static boolean isPolidrom(int n, String[] ist, String res) {
        LinkedHashMap<String, Byte> map = new LinkedHashMap<>();
        String arr = ist[0];
        byte answer = (byte) (res.charAt(0) - 48);
        byte base = answer;
        String start = arr;
        map.put(arr, answer);
        boolean f = true;
        for (int i = 1; i < ist.length; i++) {
            arr = ist[i];
            byte value = (byte) (res.charAt(i) - 48);
            for (String ch : map.keySet()) {
                if (ch.equals(start)) {
                    continue;
                }
                boolean smaller = true;
                for (int j = 0; j < ch.length(); j++) {
                    if (ch.charAt(j) > arr.charAt(j)) {
                        smaller = false;
                        break;
                    }
                }
                if (smaller) {
                    answer = (byte) (answer ^ map.get(ch));
                }
            }
            if ((answer == 0 && value == 1) || (answer == 1 && value == 0)) {
                answer = 1;
            } else {
                answer = 0;
            }
            if (answer == 1 && !(i == 1 || i == 2 || i == 4 || i == 8 || i == 16 || i == 32)) {
                return false;
            }
            map.put(arr, answer);
            answer = base;

        }
        return true;
    }

    static boolean isMonotonic(int n, String[] ist, String res) {
        for (int i = 0; i < ist.length-1; i++) {
            for (int j = i+1; j < ist.length; j++) {
                if(isMas(ist[i],ist[j])){
                    if(res.charAt(i)> res.charAt(j)){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private static boolean isMas(String s1, String s2) {
        for (int i = 0; i < s1.length() ; i++) {
            if(s1.charAt(i)>s2.charAt(i)){
                return false;
            }
        }
        return true;
    }

    static boolean isSamoDvoy(String str) {
        int len = str.length();
        for (int i = 0; i < len; i++) {
            if (str.charAt(i) == str.charAt(len - i - 1)) {
                return false;
            }
        }
        return true;
    }
}
