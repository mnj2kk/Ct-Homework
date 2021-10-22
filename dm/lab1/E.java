package com.company;

import java.util.*;

public class E {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        LinkedHashMap<String, Byte> map = new LinkedHashMap<>();
        int n = scan.nextInt();
        String arr = scan.next();
        byte answer = scan.nextByte();
        byte base = answer;
        String start = arr;
        map.put(arr, answer);
        boolean f = true;
        System.out.println((arr) + " " + answer);
        for (int i = 1; i < Math.pow(2, n); i++) {
            arr = scan.next();
            byte value = scan.nextByte();
            for (String ch : map.keySet()) {
                if(ch.equals(start)){
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
            map.put(arr, answer);
            System.out.println((arr) + " " + answer);
            answer = base;

        }
    }
}
