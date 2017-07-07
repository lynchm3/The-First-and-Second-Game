package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.Square;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actions.ActionAttack;
import com.marklynch.objects.actions.ActionDrop;
import com.marklynch.objects.units.Actor;
import com.marklynch.utils.TextureUtils;

import mdesl.graphics.Color;

public class Tree extends GameObject {

	float appleMaxRatioSize = 0.1f;
	float healthWhenLastDroppedFruit;

	public Tree(String name, int health, String imagePath, Square squareGameObjectIsOn, Inventory inventory,
			boolean showInventory, boolean canShareSquare, boolean fitsInInventory, boolean canContainOtherObjects,
			boolean blocksLineOfSight, boolean persistsWhenCantBeSeen, float widthRatio, float heightRatio,
			float drawOffsetX, float drawOffsetY, float soundWhenHit, float soundWhenHitting, float soundDampening,
			Color light, float lightHandleX, float lightHandlY, boolean stackable, float fireResistance,
			float iceResistance, float electricResistance, float poisonResistance, float weight, Actor owner) {
		super(name, health, imagePath, squareGameObjectIsOn, inventory, showInventory, canShareSquare, fitsInInventory,
				canContainOtherObjects, blocksLineOfSight, persistsWhenCantBeSeen, true, widthRatio, heightRatio,
				drawOffsetX, drawOffsetY, soundWhenHit, soundWhenHitting, soundDampening, light, lightHandleX,
				lightHandlY, stackable, fireResistance, iceResistance, electricResistance, poisonResistance, weight,
				owner);
		healthWhenLastDroppedFruit = this.totalHealth;
		// addApple(appleMaxRatioSize);
	}

	public void addApple(float maxSize) {

		float appleSize = (float) (Math.random() * maxSize);

		Food apple = new Food("Unripe Apple", 5, "apple.png", null, new Inventory(), false, true, true, false, false,
				false, appleSize, appleSize, 0.5f, 0.5f, 1f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 0.5f,
				null);
		apple.anchorX = 6;
		apple.anchorY = 6;

		float appleDrawOffsetXMax = width - apple.width - 32f;
		float appleDrawOffsetXMin = 32f;
		float appleDrawOffsetYMax = Game.HALF_SQUARE_HEIGHT - apple.height;

		apple.drawOffsetX = appleDrawOffsetXMin + (float) (Math.random() * (appleDrawOffsetXMax - appleDrawOffsetXMin));
		apple.drawOffsetY = (float) (Math.random() * appleDrawOffsetYMax);
		// apple.drawOffsetX = 0;
		// apple.drawOffsetY = 0;

		inventory.add(apple);

	}

	@Override
	public void draw1() {

		if (!Game.fullVisiblity) {
			if (this.squareGameObjectIsOn.visibleToPlayer == false && persistsWhenCantBeSeen == false)
				return;

			if (!this.squareGameObjectIsOn.seenByPlayer)
				return;
		}

		super.draw1();
		// DRAW INVENTORY
		for (GameObject fruit : inventory.gameObjects) {
			int fruitPositionXInPixels = (int) (this.squareGameObjectIsOn.xInGrid * (int) Game.SQUARE_WIDTH
					+ fruit.drawOffsetX);
			int fruitPositionYInPixels = (int) (this.squareGameObjectIsOn.yInGrid * (int) Game.SQUARE_HEIGHT
					+ fruit.drawOffsetY);

			float alpha = 1.0f;

			// TextureUtils.skipNormals = true;

			TextureUtils.drawTexture(fruit.imageTexture, alpha, fruitPositionXInPixels,
					fruitPositionXInPixels + fruit.width, fruitPositionYInPixels,
					fruitPositionYInPixels + fruit.height);
		}
	}

	@Override
	public void update(int delta) {
		super.update(delta);

		if (remainingHealth < healthWhenLastDroppedFruit && inventory.size() > 0) {

			ArrayList<GameObject> objectsToDropFromHit = new ArrayList<GameObject>();
			objectsToDropFromHit.addAll(this.inventory.gameObjects);
			for (GameObject objectToDrop : objectsToDropFromHit) {
				Level.actionQueueForInanimateObjects.add(new ActionDrop(this, this.squareGameObjectIsOn, objectToDrop));
				objectToDrop.drawOffsetY = Game.SQUARE_HEIGHT - objectToDrop.height;
			}
			healthWhenLastDroppedFruit = this.remainingHealth;

		}

		for (GameObject gameObject : inventory.gameObjects) {
			if (gameObject instanceof Food) {
				if (gameObject.widthRatio < appleMaxRatioSize) {
					gameObject.widthRatio += 0.01f;
					gameObject.heightRatio += 0.01f;
					gameObject.drawOffsetX -= 0.005f * Game.SQUARE_WIDTH;

					gameObject.width = Game.SQUARE_WIDTH * gameObject.widthRatio;
					gameObject.height = Game.SQUARE_HEIGHT * gameObject.heightRatio;
				} else {
					gameObject.name = "Apple";
				}
			}
		}

		// Start new apple?
		if (Math.random() > 0.990d)
			addApple(0.01f);

		// Drop apples?
		ArrayList<GameObject> objectsToDropRandomly = new ArrayList<GameObject>();
		for (GameObject gameObject : inventory.gameObjects) {
			if (gameObject instanceof Food && Math.random() > 0.990d)
				objectsToDropRandomly.add(gameObject);
		}

		for (GameObject objectToDrop : objectsToDropRandomly) {
			Level.actionQueueForInanimateObjects.add(new ActionDrop(this, this.squareGameObjectIsOn, objectToDrop));
			objectToDrop.drawOffsetY = Game.SQUARE_HEIGHT - objectToDrop.height;
			// - 32;
		}
	}

	@Override
	public Action getDefaultActionPerformedOnThisInWorld(Actor performer) {
		ActionAttack actionAttack = new ActionAttack(performer, this);
		if (actionAttack.enabled && actionAttack.legal) {
			return actionAttack;
		}
		return null;
	}

	@Override
	public Tree makeCopy(Square square, Actor owner) {
		return new Tree(new String(name), (int) totalHealth, imageTexturePath, square, inventory.makeCopy(),
				showInventory, canShareSquare, fitsInInventory, canContainOtherObjects, blocksLineOfSight,
				persistsWhenCantBeSeen, widthRatio, heightRatio, drawOffsetX, drawOffsetY, soundWhenHit,
				soundWhenHitting, soundDampening, light, lightHandleX, lightHandlY, stackable, fireResistance,
				iceResistance, electricResistance, poisonResistance, weight, owner);
	}

}
