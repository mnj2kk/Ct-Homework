package game;

public class SequentialPlayer implements Player {
    int n;
    int m;

    SequentialPlayer(GameSettings gameSettings) {
        this.n = gameSettings.getN();
        this.m = gameSettings.getM();
    }

    @Override
    public Move makeMove(Position position) {
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < m; c++) {
                final Move move = new Move(r, c, position.getTurn());
                if (position.isValid(move)) {
                    return move;
                }
            }
        }
        throw new AssertionError("No valid moves");
    }
}
