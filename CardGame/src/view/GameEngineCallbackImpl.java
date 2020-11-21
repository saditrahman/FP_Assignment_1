package view;

import java.util.Collection;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.interfaces.GameEngine;
import model.interfaces.Player;
import model.interfaces.PlayingCard;
import view.interfaces.GameEngineCallback;

/**
 * 
 * @author Shunhe Wang
 * 
 */
public class GameEngineCallbackImpl implements GameEngineCallback {
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	public GameEngineCallbackImpl() {
		// FINE shows dealing output, INFO only shows result
		logger.setLevel(Level.FINE);
		// System.out.println(this.getClass().getName());
		// Create a ConsoleHandler object
		ConsoleHandler consoleHandler = new ConsoleHandler();
		// Set level of the ConsoleHandler object to FINER
		consoleHandler.setLevel(Level.FINER);
		// Stop the logger object from using the parent handler
		logger.setUseParentHandlers(false);
		// Add the ConsoleHandler object to the logger object to use
		logger.addHandler(consoleHandler);
	}
	
	/*
	 * to display card on console
	 * @see view.interfaces.GameEngineCallback#nextCard(model.interfaces.Player, model.interfaces.PlayingCard, model.interfaces.GameEngine)
	 */
	@Override
	public void nextCard(Player player, PlayingCard card, GameEngine engine) {
		String showInfoOnHand = "Card Dealt to " + player.getPlayerName()+" .. " + card.toString();
		logger.log(Level.FINE, showInfoOnHand);
	}

	/*
	 * to display player's result on console
	 * @see view.interfaces.GameEngineCallback#result(model.interfaces.Player, int, model.interfaces.GameEngine)
	 */
	@Override
	public void result(Player player, int result, GameEngine engine) {
		String showResult = player.getPlayerName() + ", final result=" + result;
		logger.log(Level.INFO, showResult);
	}
	
	/*
	 * to display bust card on console
	 * @see view.interfaces.GameEngineCallback#bustCard(model.interfaces.Player, model.interfaces.PlayingCard, model.interfaces.GameEngine)
	 */
	@Override
	public void bustCard(Player player, PlayingCard card, GameEngine engine) {
		String showBustCard = "Card Dealt to " + player.getPlayerName() + card.toString() + " ... YOU BUSTED!";
		logger.log(Level.FINE, showBustCard);

	}
	
	/*
	 * to display house card on console
	 * @see view.interfaces.GameEngineCallback#nextHouseCard(model.interfaces.PlayingCard, model.interfaces.GameEngine)
	 */
	@Override
	public void nextHouseCard(PlayingCard card, GameEngine engine) {
		String showHouseOnHand = "Card Dealt to House .. "+card.toString();
		// System.out.println(showInfo);
		logger.log(Level.FINE, showHouseOnHand);

	}
	
	/*
	 * to display house bust card on console
	 * @see view.interfaces.GameEngineCallback#houseBustCard(model.interfaces.PlayingCard, model.interfaces.GameEngine)
	 */
	@Override
	public void houseBustCard(PlayingCard card, GameEngine engine) {
		String showHouseBustCard ="Card Dealt to House .."+ card.toString() + " ... YOU BUSTED!";
		logger.log(Level.FINE, showHouseBustCard);
	}
	
	/*
	 * to display house result and final points for each players on console
	 * @see view.interfaces.GameEngineCallback#houseResult(int, model.interfaces.GameEngine)
	 */
	@Override
	public void houseResult(int result, GameEngine engine) {
		Collection<Player> pTmp=engine.getAllPlayers();
		String finalResult="";
		for(Player p: pTmp) {
			finalResult+=p.toString()+"\r\n";
		}
		String showHouseResult="House, final result= "+result;
		logger.log(Level.INFO, showHouseResult);
		logger.log(Level.INFO,"Final Player Results\r\n"+finalResult);
	}

	// TODO implement the rest of the GameEngineCallback interface
}
