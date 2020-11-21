package model;

import model.interfaces.Player;

/**
 * 
 * @author Shunhe Wang
 * 
 */

public class SimplePlayer implements Player{
	private String playerId;
	private String playerName;
	private int initialPoints;
	private int bet;
	private int result=0;
	
	public  SimplePlayer(String playerId, String playerName, int initialPoints) {
		this.playerId=playerId;
		this.playerName=playerName;
		this.initialPoints=initialPoints;
	}
	
	@Override
	public String getPlayerName() {
		return playerName;
	}

	@Override
	public void setPlayerName(String playerName) {
		this.playerName=playerName;
	}

	@Override
	public int getPoints() {
		return initialPoints;
	}

	@Override
	public void setPoints(int points) {
		this.initialPoints=points;
	}

	@Override
	public String getPlayerId() {
		return playerId;
	}

	@Override
	public boolean placeBet(int bet) {
		if(bet>=0 && bet<=initialPoints) { //Bet must be between 0 to player's point
			this.bet=bet;
			return true;
		}
		return false;
		
	}

	@Override
	public int getBet() {
		return bet;
	}

	@Override
	public void resetBet() {
		this.bet=0;
		
	}

	@Override
	public int getResult() {
		return result;
	}

	@Override
	public void setResult(int result) {
	this.result+=result;	
	}
	
	@Override
	public String toString() {
		return "Player: id="+this.getPlayerId()+", name="+ this.getPlayerName()+ ", points="+this.getPoints(); 
	}
	
}
