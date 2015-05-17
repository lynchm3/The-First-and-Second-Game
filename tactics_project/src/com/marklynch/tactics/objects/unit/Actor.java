package com.marklynch.tactics.objects.unit;

import java.util.Vector;

import com.marklynch.tactics.objects.GameObject;
import com.marklynch.tactics.objects.level.Square;
import com.marklynch.tactics.objects.weapons.Weapon;

public class Actor extends GameObject {

	public enum Direction {
		UP, RIGHT, DOWN, LEFT
	}

	public String title = "";
	public int level = 1;
	public String name = "";

	public Actor(String name, String title, int level, int strength,
			int dexterity, int intelligence, int endurance, String imagePath,
			Square squareActorIsStandingOn, Vector<Weapon> weapons) {
		super(strength, dexterity, intelligence, endurance, imagePath,
				squareActorIsStandingOn, weapons);
		this.name = name;
		this.title = title;
		this.level = level;
	}

	public void calculateReachableSquares(Square[][] squares) {
		for (int i = 0; i < squares.length; i++) {
			for (int j = 0; j < squares.length; j++) {
				squares[i][j].reachableBySelectedCharater = false;
			}
		}

		Vector<Square> squaresInThisPath = new Vector<Square>();
		squaresInThisPath.add(this.squareGameObjectIsOn);

		if (travelDistance > 0) {
			calculateReachableSquares(squares, this.travelDistance,
					this.squareGameObjectIsOn, Direction.UP, squaresInThisPath);
			calculateReachableSquares(squares, this.travelDistance,
					this.squareGameObjectIsOn, Direction.RIGHT,
					squaresInThisPath);
			calculateReachableSquares(squares, this.travelDistance,
					this.squareGameObjectIsOn, Direction.DOWN,
					squaresInThisPath);
			calculateReachableSquares(squares, this.travelDistance,
					this.squareGameObjectIsOn, Direction.LEFT,
					squaresInThisPath);
		}
	}

	public void calculateReachableSquares(Square[][] squares,
			int remainingDistance, Square parentSquare, Direction direction,
			Vector<Square> squaresInThisPath) {
		Square currentSquare = null;

		if (direction == Direction.UP) {
			if (parentSquare.y - 1 >= 0) {
				currentSquare = squares[parentSquare.x][parentSquare.y - 1];
			}
		} else if (direction == Direction.RIGHT) {
			if (parentSquare.x + 1 < squares.length) {
				currentSquare = squares[parentSquare.x + 1][parentSquare.y];
			}
		} else if (direction == Direction.DOWN) {

			if (parentSquare.y + 1 < squares[0].length) {
				currentSquare = squares[parentSquare.x][parentSquare.y + 1];
			}
		} else if (direction == Direction.LEFT) {
			if (parentSquare.x - 1 >= 0) {
				currentSquare = squares[parentSquare.x - 1][parentSquare.y];
			}
		}

		if (currentSquare != null && currentSquare.gameObject == null
				&& !squaresInThisPath.contains(currentSquare)) {
			squaresInThisPath.add(currentSquare);
			currentSquare.reachableBySelectedCharater = true;
			remainingDistance -= parentSquare.travelCost;
			if (remainingDistance > 0) {
				calculateReachableSquares(squares, remainingDistance,
						currentSquare, Direction.UP, squaresInThisPath);
				calculateReachableSquares(squares, remainingDistance,
						currentSquare, Direction.RIGHT, squaresInThisPath);
				calculateReachableSquares(squares, remainingDistance,
						currentSquare, Direction.DOWN, squaresInThisPath);
				calculateReachableSquares(squares, remainingDistance,
						currentSquare, Direction.LEFT, squaresInThisPath);
			}
			squaresInThisPath.remove(currentSquare);
		}

	}

	public void calculateAttackableSquares(Square[][] squares) {
		for (int i = 0; i < squares.length; i++) {
			for (int j = 0; j < squares.length; j++) {
				squares[i][j].weaponsThatCanAttack = new Vector<Weapon>();
			}
		}

		for (Weapon weapon : weapons) {
			weapon.calculateAttackableSquares(squares);
		}
	}
}
