package com.marklynch.ai.routines;

import com.marklynch.Game;
import com.marklynch.ai.utils.AIRoutineUtils;
import com.marklynch.level.Square;

public class AIRoutineMoveToSquare extends AIRoutineUtils {
	public final static String[] editableAttributes = { "name", "square" };
	public transient Square square;

	// For loading and saving
	public String squareGUID;

	public AIRoutineMoveToSquare() {
		name = this.getClass().getSimpleName();
	}

	public AIRoutineMoveToSquare(Square square) {
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
	public AIRoutineUtils makeCopy() {
		AIRoutineMoveToSquare ai = new AIRoutineMoveToSquare(square);
		ai.name = new String(name);
		return ai;
	}
}
