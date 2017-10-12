package com.marklynch.level.constructs.adventurelog;

import com.marklynch.Game;
import com.marklynch.level.squares.Square;

public class AdventureInfo {

	public Object object;
	private int turn;
	private String turnString;
	private Square square;

	public AdventureInfo(Object object) {
		super();
		this.object = object;
	}

	public void setTurn(int turn) {
		this.turn = turn;
		this.turnString = Game.level.timeString + " (Turn " + turn + ") ";
	}

	public String getTurnString() {
		return turnString;
	}

	public void setSquare(Square square) {
		this.square = square;
	}

	public Square getSquare() {
		return square;
	}

}
