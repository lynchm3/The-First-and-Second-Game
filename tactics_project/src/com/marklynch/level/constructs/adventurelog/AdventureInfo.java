package com.marklynch.level.constructs.adventurelog;

import com.marklynch.Game;
import com.marklynch.level.squares.Square;
import com.marklynch.utils.TextUtils;

public class AdventureInfo {

	public Object object;
	private int turn;
	private String turnString;
	private Square square;
	public float height;

	public AdventureInfo(Object object) {
		super();
		this.object = object;
	}

	public void setTurn(int turn) {
		this.turn = turn;
		this.turnString = Game.level.timeString + " (Turn " + turn + ") ";

		height = TextUtils.getDimensions(Integer.MAX_VALUE, TextUtils.NewLine.NEW_LINE, object)[1];
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
