package com.company;

import java.util.ArrayList;
import java.util.Scanner;

class Function {
    int depth;
    int[] input;
    int v;
    int[] values;
}

public class C {
    static Function[] arr;

    static int getFunctionResult(int element) {
        Function function = arr[element];
        if (function.depth == 0) {
            return function.v;
        } else {
            int n = 0;
            for (int i = 0; i < function.input.length; i++) {
                if (getFunctionResult(function.input[i]) == 1) {
                    n += (1 << ((function.input.length - i - 1)));
                }
            }
            return function.values[n];
        }
    }

    public static void main(String[] args) {
        ArrayList<Integer> lists = new ArrayList<Integer>();
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        arr = new Function[n];
        for (int i = 0; i < n; i++) {;
            Function function = new Function();
            int x = scan.nextInt();
            if (x != 0) {
                int depth = 0;
                function.input= new int[x];
                function.values = new int[1<<x];
                for (int j = 0; j < x; j++) {
                    int fun = scan.nextInt();
                    function.input[j] = fun - 1;
                    int value = arr[fun - 1].depth;
                    if (value > depth) {
                        depth = value;
                    }

                }
                function.depth = ++depth;
                for (int j = 0; j < Math.pow(2, x); j++) {
                    int v = scan.nextInt();
                    function.values[j] = v;
                }

            } else {
                function.depth = 0;
                lists.add(i);
            }
            arr[i] = function;
        }
        System.out.println(arr[n - 1].depth);

        int size = (1 << lists.size());
        int last = n - 1;
        StringBuilder result =new StringBuilder();
        for (int i = 0; i < size; i++) {
                String k = Integer.toBinaryString(i);
                String ans = "0".repeat( lists.size() - k.length()) +
                        k;
            for (int j = 0; j < ans.length(); j++) {
                arr[lists.get(j)].v = ans.charAt(j) - '0';
            }
            result.append(getFunctionResult(last));

        }
        System.out.println(result);
    }
}
