package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class A {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        scan.nextLine();
        byte[][] o1 = new byte[n][n];
        byte[][] o2 = new byte[n][n];
        for (int i = 0; i < n; i++) {
            String line = scan.nextLine();
            for (int j = 0; j < n * 2; j += 2) {
                o1[i][j / 2] = (byte) (line.charAt(j) - 48);
            }
        }
        for (int i = 0; i < n; i++) {
            String line = scan.nextLine();
            for (int j = 0; j < n * 2; j += 2) {
                o2[i][j / 2] = (byte) (line.charAt(j) - 48);
            }
        }
        byte isTrans = 1;
        byte isSym = 1;
        byte isAntSym = 1;
        byte isRef = 1;
        byte isAntRef = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (o1[i][i] == 1) {
                    isAntRef = 0;
                }
                if (o1[i][i] == 0) {
                    isRef = 0;
                }
                if (o1[i][j] != o1[j][i]) {
                    isSym = 0;
                }
                if (o1[i][j] == 1 && o1[j][i]==1 && i != j) {
                    isAntSym = 0;
                }
                for (int k = 0; k < n; k++) {
                    if (o1[i][k] == 1 && o1[k][j] == 1 && o1[i][j] == 0) {
                        isTrans = 0;
                        break;
                    }
                }
            }
        }
        System.out.println(isRef + " " + isAntRef + " " + isSym + " " + isAntSym + " " + isTrans);
        isTrans = 1;
        isSym = 1;
        isAntSym = 1;
        isRef = 1;
        isAntRef = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (o2[i][i] == 1) {
                    isAntRef = 0;
                }
                if (o2[i][i] == 0) {
                    isRef = 0;
                }
                if (o2[i][j] != o2[j][i]) {
                    isSym = 0;
                }
                if (o2[i][j] ==1 && o2[j][i]==1 && i != j) {
                    isAntSym = 0;
                }
                for (int k = 0; k < n; k++) {
                    if (o2[i][k] == 1 && o2[k][j] == 1 && o2[i][j] == 0) {
                        isTrans = 0;
                        break;
                    }
                }

            }
        }
        System.out.println(isRef + " " + isAntRef + " " + isSym + " " + isAntSym + " " + isTrans);
        byte[][] comp = new byte[n][n];
        System.err.println(Arrays.deepToString(comp));
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (o1[i][j] == 1) {
                    for (int k = 0; k < n; k++) {
                        if (o2[j][k] == 1) {
                            comp[i][k]=1;
                        }
                    }

                }
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(comp[i][j] + " ");
            }
            System.out.println();
        }
    }

}
