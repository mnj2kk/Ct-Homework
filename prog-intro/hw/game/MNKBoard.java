package game;


import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MNKBoard implements Board, Position {
    private final int n;
    private final int m;
    private final int k;
    private int usedCells = 0;
    private final int cellSize;
    private final String cellSeparator;
    private static final Map<Cell, String> CELL_TO_STRING = Map.of(
            Cell.E, ".",
            Cell.X, "X",
            Cell.O, "0"
    );
    private static final List<Pair<Integer, Integer>> LINES_COEFFICIENTS = List.of(
            new Pair<>(1, 0), new Pair<>(1, 1), new Pair<>(1, -1), new Pair<>(0, 1));
    private final Cell[][] field;
    private Cell turn;

    MNKBoard(GameSettings gameSettings) {
        n = gameSettings.getN();
        m = gameSettings.getM();
        k = gameSettings.getK();
        field = new Cell[n][m];
        for (Cell[] row : field) {
            Arrays.fill(row, Cell.E);
        }
        turn = Cell.X;
        cellSize = getNumberLength(Math.max(n, m)) + 1;
        cellSeparator = " ".repeat(cellSize - 1);
    }

    @Override
    public Position getPosition() {
        return this;
    }

    @Override
    public GameResult makeMove(Move move) {
        if (!isValid(move)) {
            return GameResult.LOOSE_WRONG_INPUT;
        }
        int x = move.getRow();
        int y = move.getCol();
        Cell v = move.getValue();
        field[x][y] = move.getValue();
        usedCells++;
        if (checkWin(x, y, v)) {
            return GameResult.WIN;
        }

        if (checkDraw()) {
            return GameResult.DRAW;
        }

        turn = turn == Cell.X ? Cell.O : Cell.X;
        return GameResult.UNKNOWN;
    }

    private boolean checkWin(int x, int y, Cell v) {
        for (Pair<Integer, Integer> coefficients : LINES_COEFFICIENTS) {
            int x1 = x;
            int y1 = y;
            int counter = 0;
            while (isPartOfKLine(x1, y1, v)) {
                x1 += coefficients.first;
                y1 += coefficients.second;
                counter++;
            }
            x1 = x;
            y1 = y;
            while (isPartOfKLine(x1, y1, v)) {
                x1 -= coefficients.first;
                y1 -= coefficients.second;
                counter++;
            }
            if (counter > k) {
                return true;
            }

        }
        return false;
    }

    private boolean checkDraw() {
        return usedCells == m * n;
    }

    @Override
    public Cell getTurn() {
        return turn;
    }

    private boolean isPartOfKLine(int x, int y, Cell v) {
        return 0 <= x && x < n && 0 <= y && y < m && field[x][y] == v;
    }

    @Override
    public boolean isValid(Move move) {
        try {
            return 0 <= move.getRow() && move.getRow() < n
                    && 0 <= move.getCol() && move.getCol() < m
                    && field[move.getRow()][move.getCol()] == Cell.E
                    && turn == move.getValue();
        } catch (NullPointerException exception) {
            return false;
        }
    }

    @Override
    public Cell getCell(int row, int column) {
        return field[row][column];
    }

    @Override
    public String toString() {

        final StringBuilder sb = new StringBuilder(" ".repeat(cellSize));
        for (int i = 1; i <= m; i++) {
            sb.append(i).append(" ".repeat(cellSize - getNumberLength(i)));
        }
        for (int i = 0; i < n; i++) {
            sb.append(System.lineSeparator()).append(i + 1).append(" ".repeat(cellSize - getNumberLength(i + 1)));
            for (int j = 0; j < m; j++) {
                sb.append(CELL_TO_STRING.get(field[i][j])).append(cellSeparator);
            }
        }
        return sb.toString();
    }

    int getNumberLength(int x) {
        return (int) (Math.log10(x) + 1);
    }
}
