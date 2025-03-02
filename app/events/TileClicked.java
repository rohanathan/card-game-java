package events;

import com.fasterxml.jackson.databind.JsonNode;
import akka.actor.ActorRef;
import commands.BasicCommands;
import structures.GameState;
import structures.basic.Card;
import structures.basic.Tile;
import structures.basic.Unit;
import utils.BasicObjectBuilders;

/**
 * Handles tile click events for summoning units.
 */
public class TileClicked implements EventProcessor {

    @Override
    public void processEvent(ActorRef out, GameState gameState, JsonNode message) {
        int x = message.get("tilex").asInt();
        int y = message.get("tiley").asInt();

        Tile clickedTile = gameState.getGameBoard().getTile(x, y);

        if (clickedTile == null) {
            System.err.println("ERROR: Clicked tile is null at (" + x + "," + y + ")");
            return;
        }

        // Prevent summoning outside the player's allowed area
        if (gameState.isPlayer1Turn()) {
            if (x > 3) { // Player 1 can only summon in the first 4 rows
                System.err.println("❌ ERROR: Cannot summon outside Player 1's half!");
                return;
            }
        } else {
            if (x < 5) { // Player 2 can only summon in the last 4 rows
                System.err.println("❌ ERROR: Cannot summon outside Player 2's half!");
                return;
            }
        }

        // Check if tile is already occupied
        if (gameState.getUnit(clickedTile) != null) {
            System.err.println("❌ ERROR: Tile is already occupied by a unit!");
            return;
        }

        // Get the selected card (must be a creature to summon)
        Card selectedCard = gameState.getSelectedCard();
        if (selectedCard == null) {
            System.err.println("ERROR: No card selected to summon!");
            return;
        }

        if (!selectedCard.isCreature()) {
            System.err.println("ERROR: Selected card is not a creature!");
            return;
        }

        // Load the unit from the selected card configuration
        Unit unit = BasicObjectBuilders.loadUnit(selectedCard.getUnitConfig(), gameState.getNextUnitId(), Unit.class);
        if (unit == null) {
            System.err.println("ERROR: Failed to load unit from card config!");
            return;
        }

        // Set the unit's position on the tile
        unit.setPositionByTile(clickedTile);

        // Draw the unit on the board
        BasicCommands.drawUnit(out, unit, clickedTile);

        // Update unit stats in UI
        //BasicCommands.setUnitAttack(out, unit, unit.getAttack());
        //BasicCommands.setUnitHealth(out, unit, unit.getHealth());

        // Store the unit in GameState (tracking future actions)
        gameState.addUnit(clickedTile, unit);

        System.out.println("✅ Unit summoned successfully at (" + x + ", " + y + ")");

        // **Commented for later features (turn-based logic, animations, etc.)**
        // gameState.removeCardFromHand(selectedCard);
        // gameState.switchTurn();
    }
}