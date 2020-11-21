package model;

import model.interfaces.PlayingCard;

/**
 * 
 * @author Shunhe Wang
 * 
 */

public class PlayingCardImpl implements PlayingCard {
	private Suit cardSuit;
	private Value cardValue;
	
	public PlayingCardImpl(Suit cardSuit, Value cardValue) {
		this.cardSuit=cardSuit;
		this.cardValue=cardValue;
	}
	
	@Override
	public Suit getSuit() {
		return cardSuit;
	}

	@Override
	public Value getValue() {
		return cardValue;
	}
	
	/*
	 * to get different score based on the different card value
	 * @see model.interfaces.PlayingCard#getScore()
	 */
	@Override
	public int getScore() {
		int score=-1;
		switch(cardValue) {
		case ACE:
			score=1;
			break;
		case TWO:
			score=2;
			break;
		case THREE:
			score=3;
			break;
		case FOUR:
			score=4;
			break;
		case FIVE:
			score=5;
			break;
		case SIX:
			score=6;
			break;
		case SEVEN:
			score=7;
			break;
		case EIGHT:
			score=8;
			break;
		case NINE:
			score=9;
			break;
		case TEN:
		case JACK:
		case QUEEN:
		case KING:
			score =10;
			break;
		}
		return score;
	}

	@Override
	public boolean equals(PlayingCard card) {
		if(this.getSuit().equals(card.getSuit())&&this.getValue().equals(card.getValue())){
			return true;
		}
		return false;
	}

	/* to override hashCode()
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cardSuit == null) ? 0 : cardSuit.hashCode());
		result = prime * result + ((cardValue == null) ? 0 : cardValue.hashCode());
		return result;
	}

	/* to override equals method
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) //Same obj, return true
			return true;
		if (obj == null) //obj is null, return false
			return false;
		if(obj instanceof PlayingCardImpl) { //if obj is PlayingCardImpl's obj
			PlayingCard pc = (PlayingCardImpl) obj;
			return this.equals(pc);
		}
		return false;
	}

	@Override
	public String toString() {
	return " Suit: "+ cardSuit+" ,Value: "+ cardValue+ " ,Score: "+this.getScore();
	 }

}
