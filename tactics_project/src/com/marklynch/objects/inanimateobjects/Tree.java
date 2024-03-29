package com.marklynch.objects.inanimateobjects;

import com.marklynch.Game;
import com.marklynch.actions.ActionDropItems;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.templates.Templates;
import com.marklynch.objects.utils.UpdatableGameObject;
import com.marklynch.utils.CopyOnWriteArrayList;
import com.marklynch.utils.TextureUtils;

public class Tree extends GameObject implements UpdatableGameObject {

	public static final CopyOnWriteArrayList<GameObject> instances = new CopyOnWriteArrayList<GameObject>(GameObject.class);

	public float appleMaxRatioSize = 0.25f;
	public float healthWhenLastDroppedFruit;

	public Tree() {
		super();
		// addApple(appleMaxRatioSize);

		// BIG TREE
		canBePickedUp = false;

		fitsInInventory = false;
		this.canShareSquare = true;

		blocksLineOfSight = true;
		persistsWhenCantBeSeen = true;

		moveable = false;
		orderingOnGound = 110;

		type = "Tree";
	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	public void addApple(float sizeRatio) {
		Food apple = Templates.APPLE.makeCopy(null, null);

		apple.widthRatio = sizeRatio;
		apple.heightRatio = sizeRatio;

		apple.width = Game.SQUARE_WIDTH * apple.widthRatio;
		apple.height = Game.SQUARE_HEIGHT * apple.heightRatio;
		apple.halfWidth = apple.width / 2;
		apple.halfHeight = apple.height / 2;

		apple.anchorX = 6;
		apple.anchorY = 6;

		float appleDrawOffsetXMax = 0.75f - apple.width / Game.SQUARE_WIDTH;
		float appleDrawOffsetXMin = 0.25f;
		float appleDrawOffsetYMin = -0.35f;
		float appleDrawOffsetYMax = 0.35f;

		apple.drawOffsetRatioX = appleDrawOffsetXMin
				+ (float) (Math.random() * (appleDrawOffsetXMax - appleDrawOffsetXMin));
		apple.drawOffsetX = apple.drawOffsetRatioX * Game.SQUARE_WIDTH;
		apple.drawOffsetRatioY = apple.drawOffsetYInTree = appleDrawOffsetYMin
				+ (float) (Math.random() * (appleDrawOffsetYMax - appleDrawOffsetYMin));
		apple.drawOffsetY = apple.drawOffsetRatioY * Game.SQUARE_HEIGHT;

		inventory.add(apple);

	}

	@Override
	public boolean draw1() {

		boolean shouldDraw = super.draw1();
		if (!shouldDraw)
			return false;

		// DRAW INVENTORY
		for (GameObject fruit : inventory.gameObjects) {
			int fruitPositionXInPixels = (int) (this.squareGameObjectIsOn.xInGridPixels
					+ fruit.drawOffsetRatioX * Game.SQUARE_WIDTH);
			int fruitPositionYInPixels = (int) (this.squareGameObjectIsOn.yInGridPixels
					+ fruit.drawOffsetRatioY * Game.SQUARE_HEIGHT);

			float alpha = 1.0f;
			if (primaryAnimation != null)
				alpha = primaryAnimation.alpha;

			// TextureUtils.skipNormals = true;

			TextureUtils.drawTexture(fruit.imageTexture, alpha, fruitPositionXInPixels, fruitPositionYInPixels,
					fruitPositionXInPixels + fruit.width, fruitPositionYInPixels + fruit.height);
		}
		return true;
	}

	@Override
	public void update() {

		// WE were hit, drop all fruit
		if (remainingHealth < healthWhenLastDroppedFruit && inventory.size() > 0) {

			CopyOnWriteArrayList<GameObject> objectsToDropFromHit = new CopyOnWriteArrayList<GameObject>(GameObject.class);
			objectsToDropFromHit.addAll(this.inventory.gameObjects);
			for (GameObject objectToDrop : objectsToDropFromHit) {
				objectToDrop.drawOffsetRatioY = 1 - (objectToDrop.height / Game.SQUARE_HEIGHT);
				new ActionDropItems(this, this.squareGameObjectIsOn, objectToDrop).perform();
			}
			healthWhenLastDroppedFruit = this.remainingHealth;

		}

		// Decide what to drop
		for (GameObject gameObject : inventory.gameObjects) {
			if (gameObject instanceof Food) {
				if (gameObject.widthRatio < appleMaxRatioSize) {
					gameObject.widthRatio += 0.01f;
					gameObject.heightRatio += 0.01f;

					// old
					gameObject.drawOffsetRatioX -= 0.005f;

					gameObject.width = Game.SQUARE_WIDTH * gameObject.widthRatio;
					gameObject.height = Game.SQUARE_HEIGHT * gameObject.heightRatio;
					gameObject.halfWidth = gameObject.width / 2;
					gameObject.halfHeight = gameObject.height / 2;

					// new
					// float appleDrawOffsetXMax = 0.5f - gameObject.width /
					// Game.SQUARE_WIDTH;
					// float appleDrawOffsetXMin = 0.5f;
					// float appleDrawOffsetYMax = 1 - gameObject.height /
					// Game.SQUARE_HEIGHT;
					// gameObject.drawOffsetX = appleDrawOffsetXMin
					// + (float) (Math.random() * (appleDrawOffsetXMax -
					// appleDrawOffsetXMin));
					// gameObject.drawOffsetY = (float) (Math.random() *
					// appleDrawOffsetYMax);

				} else {
					gameObject.name = "Apple";
				}
			}
		}

		if (Math.random() > 0.99999d) {
			addApple(0.1f);
		}

		// Drop apples?
		CopyOnWriteArrayList<GameObject> objectsToDropRandomly = new CopyOnWriteArrayList<GameObject>(GameObject.class);
		for (GameObject gameObject : inventory.gameObjects) {

			if (gameObject.widthRatio >= appleMaxRatioSize) {
				objectsToDropRandomly.add(gameObject);
			}
		}

		for (GameObject objectToDrop : objectsToDropRandomly) {
			objectToDrop.drawOffsetRatioY = 1 - (objectToDrop.height / Game.SQUARE_HEIGHT);
			new ActionDropItems(this, this.squareGameObjectIsOn, objectToDrop).perform();
		}
	}

	// @Override
	// public Action getSecondaryActionPerformedOnThisInWorld(Actor performer) {
	// return new ActionChopping(performer, this);
	// }

	@Override
	public Tree makeCopy(Square square, Actor owner) {
		Tree tree = new Tree();
		setInstances(tree);
		super.setAttributesForCopy(tree, square, owner);
		healthWhenLastDroppedFruit = this.totalHealth;
		return tree;
	}

}
