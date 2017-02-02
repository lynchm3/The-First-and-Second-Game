package com.marklynch.tactics.objects.weapons;

import java.util.Vector;

import com.marklynch.tactics.objects.Owner;
import com.marklynch.tactics.objects.level.Square;
import com.marklynch.tactics.objects.unit.Actor.Direction;

public class Weapon extends WeaponTemplate {

	public Owner owner;

	public Weapon(String name, float damage, float minRange, float maxRange, String imagePath, float health,
			Square squareGameObjectIsOn, boolean fitsInInventory, boolean canContainOtherObjects) {
		super(name, damage, minRange, maxRange, imagePath, health, squareGameObjectIsOn, fitsInInventory,
				canContainOtherObjects);

		this.owner = owner;
	}

	public void calculateAttackableSquares(Square[][] squares) {
		for (int i = 0; i < squares.length; i++) {
			for (int j = 0; j < squares.length; j++) {
				if (squares[i][j].reachableBySelectedCharater) {
					for (float range = minRange; range <= maxRange; range++) {
						Vector<Square> squaresInThisPath = new Vector<Square>();
						squaresInThisPath.add(squares[i][j]);
						calculateAttackableSquares(squares, range, squares[i][j], Direction.UP, squaresInThisPath);
						calculateAttackableSquares(squares, range, squares[i][j], Direction.RIGHT, squaresInThisPath);
						calculateAttackableSquares(squares, range, squares[i][j], Direction.DOWN, squaresInThisPath);
						calculateAttackableSquares(squares, range, squares[i][j], Direction.LEFT, squaresInThisPath);
					}
				}
			}
		}
	}

	public void calculateAttackableSquares(Square[][] squares, float remainingRange, Square parentSquare,
			Direction direction, Vector<Square> squaresInThisPath) {
		Square currentSquare = null;

		if (direction == Direction.UP) {
			if (parentSquare.yInGrid - 1 >= 0) {
				currentSquare = squares[parentSquare.xInGrid][parentSquare.yInGrid - 1];
			}
		} else if (direction == Direction.RIGHT) {
			if (parentSquare.xInGrid + 1 < squares.length) {
				currentSquare = squares[parentSquare.xInGrid + 1][parentSquare.yInGrid];
			}
		} else if (direction == Direction.DOWN) {

			if (parentSquare.yInGrid + 1 < squares[0].length) {
				currentSquare = squares[parentSquare.xInGrid][parentSquare.yInGrid + 1];
			}
		} else if (direction == Direction.LEFT) {
			if (parentSquare.xInGrid - 1 >= 0) {
				currentSquare = squares[parentSquare.xInGrid - 1][parentSquare.yInGrid];
			}
		}

		if (currentSquare != null && !squaresInThisPath.contains(currentSquare)) {
			squaresInThisPath.add(currentSquare);
			if (!currentSquare.weaponsThatCanAttack.contains(this))
				currentSquare.weaponsThatCanAttack.add(this);
			remainingRange -= 1;
			if (remainingRange > 0) {
				calculateAttackableSquares(squares, remainingRange, currentSquare, Direction.UP, squaresInThisPath);
				calculateAttackableSquares(squares, remainingRange, currentSquare, Direction.RIGHT, squaresInThisPath);
				calculateAttackableSquares(squares, remainingRange, currentSquare, Direction.DOWN, squaresInThisPath);
				calculateAttackableSquares(squares, remainingRange, currentSquare, Direction.LEFT, squaresInThisPath);
			}
			squaresInThisPath.remove(currentSquare);
		}

	}

	public boolean hasRange(int weaponDistanceTo) {
		if (weaponDistanceTo >= minRange && weaponDistanceTo <= maxRange) {
			return true;
		}
		return false;
	}
}
