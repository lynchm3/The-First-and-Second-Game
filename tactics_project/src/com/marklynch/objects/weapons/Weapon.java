package com.marklynch.objects.weapons;

import com.marklynch.level.constructs.inventory.Inventory;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.units.Actor;

import mdesl.graphics.Color;

public class Weapon extends GameObject {
	public final static String[] editableAttributes = { "name", "imageTexture", "damage", "minRange", "maxRange",
			"totalHealth", "remainingHealth", "owner", "inventory", "showInventory", "fitsInInventory",
			"canContainOtherObjects" };

	// attributes
	public float slashDamage = 0;
	public float pierceDamage = 0;
	public float bluntDamage = 0;
	public float fireDamage = 0; // fire/purify/clean
	public float waterDamage = 0; // water/life
	public float electricalDamage = 0; // lightning/light/electrical/speed
	public float poisonDamage = 0;// poison/ground/contaminate/neutralize/slow/corruption
	public float minRange = 0;
	public float maxRange = 0;

	public Weapon(String name, float slashDamage, float pierceDamage, float bluntDamage, float fireDamage,
			float waterDamage, float electricalDamage, float poisonDamage, float minRange, float maxRange,
			String imagePath, float health, Square squareGameObjectIsOn, float widthRatio, float heightRatio,
			float drawOffsetX, float drawOffsetY, float soundWhenHit, float soundWhenHitting, float soundDampening,
			Color light, float lightHandleX, float lightHandlY, boolean stackable, float fireResistance,
			float waterResistance, float electricResistance, float poisonResistance, float slashResistance,
			float weight, int value, Actor owner, float anchorX, float anchorY, int templateId) {

		super(name, (int) health, imagePath, squareGameObjectIsOn, new Inventory(), widthRatio, heightRatio,
				drawOffsetX, drawOffsetY, soundWhenHit, soundWhenHitting, soundDampening, light, lightHandleX,
				lightHandlY, stackable, fireResistance, waterResistance, electricResistance, poisonResistance,
				slashResistance, weight, value, owner, templateId);

		this.owner = owner;
		this.anchorX = anchorX;
		this.anchorY = anchorY;
		this.slashDamage = slashDamage;
		this.pierceDamage = pierceDamage;
		this.bluntDamage = bluntDamage;
		this.fireDamage = fireDamage;
		this.waterDamage = waterDamage;
		this.electricalDamage = electricalDamage;
		this.poisonDamage = poisonDamage;
		this.minRange = minRange;
		this.maxRange = maxRange;

		canBePickedUp = true;
		showInventory = false;
		fitsInInventory = true;
		canShareSquare = true;
		canContainOtherObjects = false;
		blocksLineOfSight = false;
		persistsWhenCantBeSeen = false;
		attackable = true;

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
		if (getEffectiveMinRange() == 1 && weaponDistanceTo == 0)
			return true;

		if (weaponDistanceTo >= getEffectiveMinRange() && weaponDistanceTo <= getEffectiveMaxRange()) {
			return true;
		}
		return false;
	}

	public int compareWeaponToWeapon(Weapon otherGameObject) {

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

	public Action getUtilityAction(Actor performer) {
		return null;
	}

	public float getEffectiveSlashDamage() {
		return slashDamage;
	}

	public float getEffectivePierceDamage() {
		return pierceDamage;
	}

	public float getEffectiveBluntDamage() {
		return bluntDamage;
	}

	public float getEffectiveFireDamage() {
		return fireDamage;
	}

	public float getEffectiveWaterDamage() {
		return waterDamage;
	}

	public float getEffectiveElectricalDamage() {
		return electricalDamage;
	}

	public float getEffectivePoisonDamage() {
		return poisonDamage;
	}

	public float getEffectiveMinRange() {
		return minRange;
	}

	public float getEffectiveMaxRange() {
		return maxRange;
	}

	public float getTotalDamage() {
		return slashDamage + pierceDamage + bluntDamage + fireDamage + waterDamage + electricalDamage + poisonDamage;
	}

	public float getTotalEffectiveDamage() {
		return getEffectiveSlashDamage() + getEffectivePierceDamage() + getEffectiveBluntDamage()
				+ getEffectiveFireDamage() + getEffectiveWaterDamage() + getEffectiveElectricalDamage()
				+ getEffectivePoisonDamage();
	}

	@Override
	public Weapon makeCopy(Square square, Actor owner) {
		return new Weapon(new String(name), slashDamage, pierceDamage, bluntDamage, fireDamage, waterDamage,
				electricalDamage, poisonDamage, minRange, maxRange, imageTexturePath, totalHealth, square, widthRatio,
				heightRatio, drawOffsetX, drawOffsetY, soundWhenHit, soundWhenHitting, soundDampening, light,
				lightHandleX, lightHandlY, stackable, fireResistance, waterResistance, electricResistance,
				poisonResistance, slashResistance, weight, value, owner, anchorX, anchorY, templateId);
	}
}
