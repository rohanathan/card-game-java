package structures;

import akka.actor.ActorRef;
import structures.basic.Card;
import structures.basic.Player;
import structures.basic.Tile;
import structures.basic.Unit;
import structures.basic.GameBoard;
import utils.OrderedCardLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

/**
 * Stores the current state of the game, including player data, selected cards,
 * board state, and turn management.
 * 
 * @author Dr. Richard McCreadie
 */
public class GameState {

    private boolean gameInitialized = false;
    private Player player1;
    private Player player2;
    private Card selectedCard;
    private boolean isPlayer1Turn = true;  // Track turn
    private GameBoard gameBoard;
    private List<Card> player1Hand;
    private List<Card> player2Hand;
    private HashMap<Tile, Unit> unitMap;  // Map of board tiles to units

    /**
     * Constructor initializes game with two players and a game board.
     */
    public GameState(ActorRef out) {
        this.player1 = new Player();
        this.player2 = new Player();
        this.gameBoard = new GameBoard(out);
		System.out.println("✅ GameState Initialized");
        this.unitMap = new HashMap<>();

        // Load Player 1's deck
        this.player1Hand = OrderedCardLoader.getPlayer1Cards(1);
        this.player2Hand = OrderedCardLoader.getPlayer2Cards(1);
    }

    // ------------------ GAME INITIALIZATION ------------------
    
    public boolean isGameInitialized() {
        return gameInitialized;
    }

    public void setGameInitialized(boolean initialized) {
        this.gameInitialized = initialized;
    }

    // ------------------ PLAYER MANAGEMENT ------------------
    
    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public boolean isPlayer1Turn() {
        return isPlayer1Turn;
    }

    public void switchTurn() {
        isPlayer1Turn = !isPlayer1Turn;
    }

    // ------------------ CARD MANAGEMENT ------------------

    public List<Card> getPlayer1Hand() {
        return player1Hand;
    }

    public List<Card> getPlayer2Hand() {
        return player2Hand;
    }

    public Card getPlayer1Card(int handPosition) {
        if (handPosition >= 1 && handPosition <= player1Hand.size()) {
            return player1Hand.get(handPosition - 1);
        }
        return null;
    }

    public Card getSelectedCard() {
        return selectedCard;
    }

    public void setSelectedCard(Card card) {
        this.selectedCard = card;
    }

    // ------------------ GAME BOARD ------------------
    
    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public Tile getTile(int x, int y) {
        return gameBoard.getTile(x, y);
    }

    // ------------------ UNIT MANAGEMENT ------------------

	private int nextUnitId = 1;

	public int getNextUnitId() {
    return nextUnitId++;
	}


    public void addUnit(Tile tile, Unit unit) {
        unitMap.put(tile, unit);
    }

    public void removeUnit(Tile tile) {
        unitMap.remove(tile);
    }

    public Unit getUnit(Tile tile) {
        return unitMap.get(tile);
    }
}
