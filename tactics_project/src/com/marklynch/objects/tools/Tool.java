package com.marklynch.objects.tools;

import java.util.ArrayList;

import com.marklynch.level.constructs.inventory.Inventory;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.weapons.Weapon;

public class Tool extends Weapon {
	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();

	public Tool() {

		super();
	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	// public void calculateAttackableSquares(Square[][] squares) {
	// for (int i = 0; i < squares.length; i++) {
	// for (int j = 0; j < squares[0].length; j++) {
	// if (squares[i][j].reachableBySelectedCharater) {
	// for (float range = getEffectiveMinRange(); range <=
	// getEffectiveMaxRange(); range++) {
	// ArrayList<Square> squaresInThisPath = new ArrayList<Square>();
	// squaresInThisPath.add(squares[i][j]);
	// calculateAttackableSquares(squares, range, squares[i][j], Direction.UP,
	// squaresInThisPath);
	// calculateAttackableSquares(squares, range, squares[i][j],
	// Direction.RIGHT, squaresInThisPath);
	// calculateAttackableSquares(squares, range, squares[i][j], Direction.DOWN,
	// squaresInThisPath);
	// calculateAttackableSquares(squares, range, squares[i][j], Direction.LEFT,
	// squaresInThisPath);
	// }
	// }
	// }
	// }
	// }
	//
	// public void calculateAttackableSquares(Square[][] squares, float
	// remainingRange, Square parentSquare,
	// Direction direction, ArrayList<Square> squaresInThisPath) {
	// Square currentSquare = null;
	//
	// if (direction == Direction.UP) {
	// if (parentSquare.yInGrid - 1 >= 0) {
	// currentSquare = squares[parentSquare.xInGrid][parentSquare.yInGrid - 1];
	// }
	// } else if (direction == Direction.RIGHT) {
	// if (parentSquare.xInGrid + 1 < squares.length) {
	// currentSquare = squares[parentSquare.xInGrid + 1][parentSquare.yInGrid];
	// }
	// } else if (direction == Direction.DOWN) {
	//
	// if (parentSquare.yInGrid + 1 < squares[0].length) {
	// currentSquare = squares[parentSquare.xInGrid][parentSquare.yInGrid + 1];
	// }
	// } else if (direction == Direction.LEFT) {
	// if (parentSquare.xInGrid - 1 >= 0) {
	// currentSquare = squares[parentSquare.xInGrid - 1][parentSquare.yInGrid];
	// }
	// }
	//
	// if (currentSquare != null && !squaresInThisPath.contains(currentSquare))
	// {
	// squaresInThisPath.add(currentSquare);
	// if (!currentSquare.weaponsThatCanAttack.contains(this))
	// currentSquare.weaponsThatCanAttack.add(this);
	// remainingRange -= 1;
	// if (remainingRange > 0) {
	// calculateAttackableSquares(squares, remainingRange, currentSquare,
	// Direction.UP, squaresInThisPath);
	// calculateAttackableSquares(squares, remainingRange, currentSquare,
	// Direction.RIGHT, squaresInThisPath);
	// calculateAttackableSquares(squares, remainingRange, currentSquare,
	// Direction.DOWN, squaresInThisPath);
	// calculateAttackableSquares(squares, remainingRange, currentSquare,
	// Direction.LEFT, squaresInThisPath);
	// }
	// squaresInThisPath.remove(currentSquare);
	// }
	//
	// }

	@Override
	public boolean hasRange(int weaponDistanceTo) {
		if (weaponDistanceTo >= getEffectiveMinRange() && weaponDistanceTo <= getEffectiveMaxRange()) {
			return true;
		}
		return false;
	}

	@Override
	public float getEffectiveMinRange() {
		return minRange;
	}

	@Override
	public float getEffectiveMaxRange() {
		return maxRange;
	}

	public int compareToolToTool(Tool otherGameObject) {

		if (Inventory.inventorySortBy == Inventory.INVENTORY_SORT_BY.SORT_BY_MAX_RANGE) {
			return Math.round(otherGameObject.maxRange - this.maxRange);
		}

		if (Inventory.inventorySortBy == Inventory.INVENTORY_SORT_BY.SORT_BY_MIN_RANGE) {
			return Math.round(otherGameObject.minRange - this.minRange);
		}

		return 0;

	}

	@Override
	public Action getUtilityAction(Actor performer) {
		return null;
	}
}
