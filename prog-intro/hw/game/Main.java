package game;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        final GameSettings gameSettings = new GameSettings(scan);
        if (gameSettings.isSettingsAreInputed()) {
            Match match = new Match(new ExceptionPlayer(), new RandomPlayer(gameSettings), gameSettings);

            match.play();
        } else {
            System.out.println("Couldn't start game: Settings aren't inputed");
        }
    }
}
