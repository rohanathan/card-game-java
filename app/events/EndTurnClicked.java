package events;

import com.fasterxml.jackson.databind.JsonNode;

import akka.actor.ActorRef;
import commands.BasicCommands;
import structures.GameState;
import structures.basic.Card;
import utils.OrderedCardLoader;

/**
 * Indicates that the user has clicked an object on the game canvas, in this case
 * the end-turn button.
 * 
 * { 
 *   messageType = “endTurnClicked”
 * }
 * 
 * @author Dr. Richard McCreadie
 *
 */
public class EndTurnClicked implements EventProcessor{

	@Override
	public void processEvent(ActorRef out, GameState gameState, JsonNode message) {
		System.out.println("Tetsing mesg");
		gameState.switchTurn();
		int handPosition = 1;
        for (Card card : OrderedCardLoader.getPlayer2Cards(1)) {
            BasicCommands.drawCard(out, card, handPosition, 0);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            handPosition++;

            if (handPosition > 6) break; // Display only the first 6 cards
        }
	}

}
