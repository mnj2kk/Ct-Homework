package game;

public class Match {
    private Player player1;
    private Player player2;
    private Board board;
    private final GameSettings gameSettings;

    public Match(Player player1, Player player2, GameSettings gameSettings) {
        this.player1 = player1;
        this.player2 = player2;
        this.gameSettings = gameSettings;
    }

    public void play() {
        int points1 = 0;
        int points2 = 0;
        int result;
        TwoPlayerGame game;
        int i = 0;
        while (true) {
            board = new MNKBoard(gameSettings);
            game = new TwoPlayerGame(
                    board,
                    player1,
                    player2
            );
            result = game.play(false);
            int playerNumber1 = (i % 2) + 1;
            int playerNumber2 = ((i + 1) % 2) + 1;
            switch (result) {
                case 1:
                    System.out.println("Player " + playerNumber1 + " won");
                    points1 += 1;
                    break;
                case 2:
                    System.out.println("Player " + playerNumber2 + " won");
                    points2 += 1;
                    break;
                case 4:
                    System.out.println("Player" + playerNumber1 + " loose , because he inputed wrong coordinates ");
                    points2 += 1;
                    break;
                case 5:
                    System.out.println("Player" + playerNumber2 + " loose , because he inputed wrong coordinates ");
                    points1 += 1;
                    break;
                case 0:
                    System.out.println("Draw");
                    break;
                default:
                    throw new AssertionError("Unknown result " + result);
            }
            if (points1 >= gameSettings.getL() && points2 == points1) {
                System.out.println("Finally draw");
                break;
            } else if (points1 >= gameSettings.getL()) {
                System.out.println("Player " + playerNumber1 + " finally wins with score " + points1);
                break;
            } else if (points2 >= gameSettings.getL()) {
                System.out.println("Player " + playerNumber2 + " finally wins with score " + points2);
                break;
            }
            Player tmpPlayer = player1;
            int tmpPoints = points1;
            player1 = player2;
            player2 = tmpPlayer;
            points1 = points2;
            points2 = tmpPoints;
            i++;
        }
    }
}
