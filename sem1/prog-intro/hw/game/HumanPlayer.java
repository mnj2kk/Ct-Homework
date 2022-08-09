package game;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class HumanPlayer implements Player {
    private final Scanner in;
    private int x, y;
    private boolean closedScanner = false;

    public HumanPlayer(Scanner in) {
        this.in = in;
    }

    @Override
    public Move makeMove(Position position) {
        System.out.println();
        System.out.println("Current position");
        System.out.println(position);
        System.out.println("Enter you move for " + position.getTurn());
        readFromConsole();
        if (closedScanner) {
            return null;
        }
        Move move = new Move(x, y, position.getTurn());
        while (!position.isValid(move)) {

            System.out.println("Re input you coordinates");
            readFromConsole();
            if (closedScanner) {
                return null;
            }
            move = new Move(x, y, position.getTurn());
        }
        return move;
    }

    private void readFromConsole() {
        while (true) {
            try {
                scan();
                in.nextLine();
                break;
            } catch (InputMismatchException exception) {
                System.out.println("Re input you coordinates");
                in.nextLine();
            } catch (NoSuchElementException exception) {
                closedScanner = true;
                return;
            }
        }
    }

    private void scan() throws InputMismatchException {
        x = in.nextInt() - 1;
        y = in.nextInt() - 1;
    }
}
