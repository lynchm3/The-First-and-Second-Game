package com.marklynch.tactics.objects.unit.ai;

import com.marklynch.Game;
import com.marklynch.tactics.objects.level.Square;

public class AIMoveToSquare extends AI {
	public final static String[] editableAttributes = { "name", "square" };
	public transient Square square;

	// For loading and saving
	public String squareGUID;

	public AIMoveToSquare() {
		name = this.getClass().getSimpleName();
	}

	public AIMoveToSquare(Square square) {
		name = this.getClass().getSimpleName();
		this.square = square;
		squareGUID = square.guid;
	}

	@Override
	public boolean move() {

		if (Game.level.activeActor.squareGameObjectIsOn != square)
			return this.moveTowardsTargetSquare(square);
		else
			return false;
	}

	@Override
	public boolean attack() {
		return super.attackRandomEnemy();
	}

	@Override
	public void postLoad() {
		super.postLoad();
		square = Game.level.findSquareFromGUID(squareGUID);
	}

	@Override
	public AI makeCopy() {
		AIMoveToSquare ai = new AIMoveToSquare(square);
		ai.name = new String(name);
		return ai;
	}
}
