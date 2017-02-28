package com.marklynch.objects.weapons;

import java.util.ArrayList;
import java.util.Vector;

import com.marklynch.level.Square;
import com.marklynch.objects.Inventory;
import com.marklynch.objects.Owner;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actions.ActionEquip;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.Actor.Direction;

public class Weapon extends WeaponTemplate {

	public Owner owner;

	public Weapon(String name, float damage, float minRange, float maxRange, String imagePath, float health,
			Square squareGameObjectIsOn, boolean fitsInInventory, boolean canContainOtherObjects,
			boolean blocksLineOfSight, boolean persistsWhenCantBeSeen, float widthRatio, float heightRatio, float anchorX, float anchorY) {
		super(name, damage, minRange, maxRange, imagePath, health, squareGameObjectIsOn, fitsInInventory,
				canContainOtherObjects, blocksLineOfSight, persistsWhenCantBeSeen, widthRatio, heightRatio);

		this.owner = owner;
		this.anchorX = anchorX;
		this.anchorY = anchorY;
	}

	public void calculateAttackableSquares(Square[][] squares) {
		for (int i = 0; i < squares.length; i++) {
			for (int j = 0; j < squares[0].length; j++) {
				if (squares[i][j].reachableBySelectedCharater) {
					for (float range = getEffectiveMinRange(); range <= getEffectiveMaxRange(); range++) {
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
		if (weaponDistanceTo >= getEffectiveMinRange() && weaponDistanceTo <= getEffectiveMaxRange()) {
			return true;
		}
		return false;
	}

	public int compareWeaponToWeapon(Weapon otherGameObject) {

		System.out.println("compareWeaponToWeapon");
		if (Inventory.inventorySortBy == Inventory.INVENTORY_SORT_BY.SORT_BY_TOTAL_DAMAGE) {
			return Math.round(otherGameObject.getTotalDamage() - this.getTotalDamage());
		}

		if (Inventory.inventorySortBy == Inventory.INVENTORY_SORT_BY.SORT_BY_SLASH_DAMAGE) {
			return Math.round(otherGameObject.slashDamage - this.slashDamage);
		}

		if (Inventory.inventorySortBy == Inventory.INVENTORY_SORT_BY.SORT_BY_BLUNT_DAMAGE) {
			return Math.round(otherGameObject.bluntDamage - this.bluntDamage);
		}

		if (Inventory.inventorySortBy == Inventory.INVENTORY_SORT_BY.SORT_BY_PIERCE_DAMAGE) {
			return Math.round(otherGameObject.pierceDamage - this.pierceDamage);
		}

		if (Inventory.inventorySortBy == Inventory.INVENTORY_SORT_BY.SORT_BY_FIRE_DAMAGE) {
			return Math.round(otherGameObject.fireDamage - this.fireDamage);
		}

		if (Inventory.inventorySortBy == Inventory.INVENTORY_SORT_BY.SORT_BY_WATER_DAMAGE) {
			return Math.round(otherGameObject.waterDamage - this.waterDamage);
		}

		if (Inventory.inventorySortBy == Inventory.INVENTORY_SORT_BY.SORT_BY_POISON_DAMAGE) {
			return Math.round(otherGameObject.electricalDamage - this.electricalDamage);
		}

		if (Inventory.inventorySortBy == Inventory.INVENTORY_SORT_BY.SORT_BY_ELECTRICAL_DAMAGE) {
			return Math.round(otherGameObject.poisonDamage - this.poisonDamage);
		}

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
}
