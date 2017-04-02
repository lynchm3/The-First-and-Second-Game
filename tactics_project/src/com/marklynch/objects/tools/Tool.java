package com.marklynch.objects.tools;

import java.util.ArrayList;

import com.marklynch.level.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Inventory;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actions.ActionEquip;
import com.marklynch.objects.units.Actor;

import mdesl.graphics.Color;

public class Tool extends GameObject {

	public float minRange = 0;
	public float maxRange = 0;

	public Tool(String name, float minRange, float maxRange, String imagePath, float health,
			Square squareGameObjectIsOn, boolean fitsInInventory, boolean canContainOtherObjects,
			boolean blocksLineOfSight, boolean persistsWhenCantBeSeen, float widthRatio, float heightRatio,
			float soundHandleX, float soundHandleY, float soundWhenHit, float soundWhenHitting, float soundDampening, Color light,
			float lightHandleX, float lightHandlY, boolean stackable, float fireResistance, float iceResistance,
			float electricResistance, float poisonResistance, Actor owner, float anchorX, float anchorY) {

		super(name, (int) health, imagePath, squareGameObjectIsOn, new Inventory(), false, true, fitsInInventory,
				canContainOtherObjects, blocksLineOfSight, persistsWhenCantBeSeen, widthRatio, heightRatio,
				soundHandleX, soundHandleY, soundWhenHit, soundWhenHitting, soundDampening, light, lightHandleX, lightHandlY, stackable,
				fireResistance, iceResistance, electricResistance, poisonResistance, owner);

		this.minRange = minRange;
		this.maxRange = maxRange;
		this.owner = owner;
		this.anchorX = anchorX;
		this.anchorY = anchorY;
	}

	// public void calculateAttackableSquares(Square[][] squares) {
	// for (int i = 0; i < squares.length; i++) {
	// for (int j = 0; j < squares[0].length; j++) {
	// if (squares[i][j].reachableBySelectedCharater) {
	// for (float range = getEffectiveMinRange(); range <=
	// getEffectiveMaxRange(); range++) {
	// Vector<Square> squaresInThisPath = new Vector<Square>();
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
	// Direction direction, Vector<Square> squaresInThisPath) {
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

	public boolean hasRange(int weaponDistanceTo) {
		if (weaponDistanceTo >= getEffectiveMinRange() && weaponDistanceTo <= getEffectiveMaxRange()) {
			return true;
		}
		return false;
	}

	public float getEffectiveMinRange() {
		return minRange;
	}

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
	public Action getDefaultActionInInventory(Actor performer) {
		return new ActionEquip(performer, this);
	}

	@Override
	public ArrayList<Action> getAllActionsInInventory(Actor performer) {
		return new ArrayList<Action>();
	}

	public Action getUtilityAction(Actor performer) {
		return null;
	}

	@Override
	public Tool makeCopy(Square square, Actor owner) {
		return new Tool(new String(name), minRange, maxRange, imageTexturePath, totalHealth, square, fitsInInventory,
				canContainOtherObjects, blocksLineOfSight, persistsWhenCantBeSeen, widthRatio, heightRatio,
				soundHandleX, soundHandleY, soundWhenHit, soundWhenHitting, soundDampening, light, lightHandleX, lightHandlY, stackable,
				fireResistance, iceResistance, electricResistance, poisonResistance, owner, anchorX, anchorY);
	}
}
