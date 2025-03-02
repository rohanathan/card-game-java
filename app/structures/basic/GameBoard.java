package structures.basic;

import akka.actor.ActorRef;
import commands.BasicCommands;
import utils.BasicObjectBuilders;

public class GameBoard {
    private final Tile[][] board;
    private final ActorRef out;

    public GameBoard(ActorRef out) {
        this.out = out;
        this.board = new Tile[9][5];  // Correct board size (9x5)

        // Initialize all tiles
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = BasicObjectBuilders.loadTile(i, j);
            }
        }
    }
    // ✅ Ensure this method is present
    public void drawBoard() {
        System.out.println("✅ Drawing Game Board...");
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                Tile tile = board[i][j];
                BasicCommands.drawTile(out, tile, 0);
            }
        }
    }
    public Tile getTile(int x, int y) {
        if (x >= 0 && x < 9 && y >= 0 && y < 5) {
            if (board[x][y] == null) {
                System.err.println("❌ ERROR: Tile at (" + x + "," + y + ") was never initialized!");
            }
            return board[x][y];
        } else {
            System.err.println("❌ ERROR: Tile (" + x + "," + y + ") is out of bounds!");
            return null;
        }
    }

}
