package game;

import java.util.Random;

public class RandomPlayer implements Player {
    private final Random random = new Random();
    private final int n;
    private final int m;

    RandomPlayer(GameSettings gameSettings) {
        this.n = gameSettings.getN();
        this.m = gameSettings.getM();
    }

    @Override
    public Move makeMove(Position position) {
        while (true) {
            final Move move = new Move(
                    random.nextInt(n),
                    random.nextInt(m),
                    position.getTurn());
            if (position.isValid(move)) {
                return move;
            }
        }
    }
}
