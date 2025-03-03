package events;

import akka.actor.ActorRef;
import com.fasterxml.jackson.databind.JsonNode;
import structures.GameState;
import structures.basic.Card;
import commands.BasicCommands;
/**
 * Indicates that the user has clicked an object on the game canvas, in this case a card.
 * The event returns the position in the player's hand the card resides within.
 * 
 * { 
 *   messageType = “cardClicked”
 *   position = <hand index position [1-6]>
 * }
 * 
 * @author Dr. Richard McCreadie
 *
 */
public class CardClicked implements EventProcessor {

    @Override
    public void processEvent(ActorRef out, GameState gameState, JsonNode message) {
        System.out.println("Cardclicked"+message);
        
        int handPosition = message.get("position").asInt();
        Card selectedCard = gameState.getPlayer1Card(handPosition); // Get the selected card

        if (selectedCard != null) {
            gameState.setSelectedCard(selectedCard); // Store the selected card in GameState
            
            // Highlight the selected card
            BasicCommands.drawCard(out, selectedCard, handPosition, 1);
        }
    }
}
