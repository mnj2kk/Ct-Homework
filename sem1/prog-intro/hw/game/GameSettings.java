package game;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class GameSettings {
    private int n;
    private int m;
    private int k;
    private int l;
    private boolean settingsAreInputed = false;

    GameSettings(Scanner in) {
        readFromConsole(in);
    }

    GameSettings(int m, int n, int k, int l) {
        this.n = n;
        this.m = m;
        this.k = k;
        this.l = l;
    }

    public int getN() {
        return n;
    }

    public int getM() {
        return m;
    }

    public int getK() {
        return k;
    }

    public int getL() {
        return l;
    }

    public boolean isSettingsAreInputed() {
        return settingsAreInputed;
    }

    private void readFromConsole(Scanner in) {
        while (true) {
            try {
                scan(in);
                if (settingsAreCorrect()) {
                    settingsAreInputed = true;
                    return;
                }
                System.out.println("Wrong format of input settings");
            } catch (InputMismatchException exception) {
                System.out.println("Re input you coordinates");
            } catch (NoSuchElementException exception) {
                System.out.println("popalsa Nikolay Vedernikov ");
                return;
            }
            in.nextLine();
        }
    }

    private boolean settingsAreCorrect() {
        return m > 0 && n > 0 && 0 < k && k <= Math.max(n, m) && l > 0;
    }

    private void scan(Scanner scan) throws InputMismatchException {
        System.out.println("Input your game parameters in order - m,n,k,l");
        this.m = scan.nextInt();
        this.n = scan.nextInt();
        this.k = scan.nextInt();
        this.l = scan.nextInt();
    }
}
