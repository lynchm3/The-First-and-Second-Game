package com.marklynch.level.constructs.adventurelog;

import com.marklynch.Game;

public class AdventureInfo {

	public Object object;
	private int turn;
	private String turnString;

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

}
