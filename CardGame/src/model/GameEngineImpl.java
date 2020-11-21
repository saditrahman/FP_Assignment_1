package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import model.interfaces.GameEngine;
import model.interfaces.Player;
import model.interfaces.PlayingCard;
import model.interfaces.PlayingCard.Suit;
import model.interfaces.PlayingCard.Value;
import view.interfaces.GameEngineCallback;


/**
 * 
 * @author Shunhe Wang
 * 
 */
public class GameEngineImpl implements GameEngine {
	private Collection<Player> players;// store all players
	private List<GameEngineCallback> gameEngineCallbacks;//store all gameEngineCallback objs
	private Deque<PlayingCard> deckCard = this.getShuffledDeck();//initialize one pack of cards

	public GameEngineImpl() {
		players = new ArrayList<Player>();
		gameEngineCallbacks = new ArrayList<GameEngineCallback>();
	}
	
	/*
	 * To deal all players
	 * @see model.interfaces.GameEngine#dealPlayer(model.interfaces.Player, int)
	 */
	@Override
	public void dealPlayer(Player player, int delay) {
		int minBet = 0;
		//if player does not exist in Collection, return directly
		if (!players.contains(player)) {
			return;
		}
		//if player's bet over 0, to handle game process for each player
		if (player.getBet() > minBet) {
			PlayingCard card = deckCard.getFirst(); //Get first card
			do {
				for (GameEngineCallback gec : gameEngineCallbacks) {
					gec.nextCard(player, card, this); //Display this card on console 
				}
				try {
					Thread.sleep(delay); //sleep 1 ms
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
				player.setResult(card.getScore()); //Record current score
				deckCard.removeFirst(); // Remove this card
				card = deckCard.getFirst(); //Get the next card
			} while ((player.getResult() + card.getScore()) <= BUST_LEVEL);//When the result + next card value over 21, break

			for (GameEngineCallback gec : gameEngineCallbacks) {
				gec.bustCard(player, card, this); //Display the bust card on console
				gec.result(player, player.getResult(), this); //Display the result on console
			}
		}
	}
	
	/*
	 * To deal house
	 * @see model.interfaces.GameEngine#dealPlayer(model.interfaces.Player, int)
	 */
	@Override
	public void dealHouse(int delay) {
		PlayingCard card = deckCard.getFirst();//Get first card
		int houseResult = 0;
		do {
			for (GameEngineCallback gec : gameEngineCallbacks) {
				gec.nextHouseCard(card, this);//Display this card on console 
			}
			try {
				Thread.sleep(delay); //sleep 10 ms
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
			houseResult += card.getScore();//Record current score
			deckCard.removeFirst();// Remove this card
			card = deckCard.getFirst();//Get the next card
		} while ((houseResult + card.getScore()) <= BUST_LEVEL);//When the result + next card value over 21, break

		for (Player player : players) {
			int num1 = player.getResult();
			//When the final result of each player over house, the player will get bet
			if (num1 > houseResult) {
				player.setPoints(player.getPoints() + player.getBet());
			}
			//When the final result of each player below house, the player will loss bet
			if (num1 < houseResult) {
				player.setPoints(player.getPoints() - player.getBet());
			}
			//else,all unchanged
		}
		for (GameEngineCallback gec : gameEngineCallbacks) {
			gec.houseBustCard(card, this); //Display the bust card on console
			gec.houseResult(houseResult, this); //Display the result on console
		}
	}
	
	/*
	 * to add player
	 * @see model.interfaces.GameEngine#addPlayer(model.interfaces.Player)
	 */
	@Override
	public void addPlayer(Player player) {
		players.add(player);
	}
	
	/*
	 * to get player from ArrayList
	 * @see model.interfaces.GameEngine#getPlayer(java.lang.String)
	 */
	@Override
	public Player getPlayer(String id) {
		for (Player p : players) {
			if (p.getPlayerId().equals(id)) { //if player id found, return this obj
				return p;
			}
		}
		return null;
	}
	
	/*
	 * to remove player from ArrayList
	 * @see model.interfaces.GameEngine#removePlayer(model.interfaces.Player)
	 */
	@Override
	public boolean removePlayer(Player player) {
		for (Player p : players) {
			if (p.getPlayerName().equals(player.getPlayerName())) {//if player found, return this obj
				players.remove(p);
				return true;
			}
		}
		return false;
	}
	
	/*
	 * to add gameEngineCallBack obj into ArrayList
	 * @see model.interfaces.GameEngine#addGameEngineCallback(view.interfaces.GameEngineCallback)
	 */
	@Override
	public void addGameEngineCallback(GameEngineCallback gameEngineCallback) {
		this.gameEngineCallbacks.add(gameEngineCallback);

	}
	
	/*
	 * to add gameEngineCallBack obj from ArrayList
	 * @see model.interfaces.GameEngine#removeGameEngineCallback(view.interfaces.GameEngineCallback)
	 */
	@Override
	public boolean removeGameEngineCallback(GameEngineCallback gameEngineCallback) {
		for (GameEngineCallback g : gameEngineCallbacks) {
			if (g.toString().equals(gameEngineCallback.toString())) {
				this.gameEngineCallbacks.remove(gameEngineCallback);
				return true;
			}
		}
		return false;
	}
	
	/*
	 * to get all players
	 * @see model.interfaces.GameEngine#getAllPlayers()
	 */
	@Override
	public Collection<Player> getAllPlayers() {
		return Collections.unmodifiableCollection(this.players);
	}
	
	/*
	 * to check placing the bet
	 * @see model.interfaces.GameEngine#placeBet(model.interfaces.Player, int)
	 */
	@Override
	public boolean placeBet(Player player, int bet) {
		return player.placeBet(bet);
	}
	
	/*
	 * initialize one pack of cards
	 * @see model.interfaces.GameEngine#getShuffledDeck()
	 */
	@Override
	public Deque<PlayingCard> getShuffledDeck() {
		Deque<PlayingCard> deckCard = new LinkedList<PlayingCard>();
		List<PlayingCard> cTmp = new ArrayList<PlayingCard>();
		Suit[] cardSuit = { Suit.HEARTS, Suit.SPADES, Suit.CLUBS, Suit.DIAMONDS };
		Value[] cardValue = { Value.ACE, Value.TWO, Value.THREE, Value.FOUR, Value.FIVE, Value.SIX, Value.SEVEN,
				Value.EIGHT, Value.NINE, Value.TEN, Value.JACK, Value.QUEEN, Value.KING };
		for (int i = 0; i < cardSuit.length; i++) {
			for (int j = 0; j < cardValue.length; j++) {// Get one pack of card
				PlayingCard pTmp = new PlayingCardImpl(cardSuit[i], cardValue[j]);
				cTmp.add(pTmp);
			}
		}
		Collections.shuffle(cTmp); // disrupt randomly the order
		for (PlayingCard p : cTmp) {  // add all cards into deckCard
			deckCard.add(p);
		}
		return deckCard;
	}

}
