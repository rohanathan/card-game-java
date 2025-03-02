package demo;

import akka.actor.ActorRef;
import commands.BasicCommands;
import structures.basic.Card;
import structures.basic.GameBoard;
import structures.basic.Player;
import structures.basic.Tile;
import structures.basic.Unit;
import structures.basic.UnitAnimationType;
import utils.BasicObjectBuilders;
import utils.OrderedCardLoader;
import utils.StaticConfFiles;
import events.TileClicked;
import structures.GameState;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * This class contains an illustration of calling all of the commands provided
 * in the ITSD Card Game Template
 * @author Dr. Richard McCreadie
 */
public class CommandDemo {

    /**
     * This is a demo of the various commands that can be executed.
     * 
     * WARNING: This is a very long-running method, as it has a lot
     * of thread sleeps in it. The back-end will not respond to commands
     * while this method is processing, e.g., if you refresh the game
     * page or try to kill the server, you will get a long delay before
     * anything happens.
     */
    public static void executeDemo(ActorRef out) {

        // Step 1: Draw Game Board
        GameBoard gameBoard = new GameBoard(out);
        gameBoard.drawBoard();

        // Step 2: Initialize GameState
        GameState gameState = new GameState(out);

        // Step 3: Load Cards
        int handPosition = 1;
        for (Card card : OrderedCardLoader.getPlayer1Cards(1)) {
            BasicCommands.drawCard(out, card, handPosition, 0);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            handPosition++;

            if (handPosition > 6) break; // Display only the first 6 cards
        }

        // Step 4: Simulate a tile click for summoning a unit
        TileClicked tileClick = new TileClicked();
        tileClick.processEvent(out, gameState, createTestJsonNode(3, 3));

        // Step 5: Check if the unit is drawn
        System.out.println("Unit summoned at (3,3)");
    }

    /**
     * Creates a mock JSON message to simulate a tile click event.
     * @param x The x-coordinate of the tile.
     * @param y The y-coordinate of the tile.
     * @return A JSON object representing the tile click event.
     */
    private static JsonNode createTestJsonNode(int x, int y) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("tilex", x);
        node.put("tiley", y);
        return node;
    }
}
