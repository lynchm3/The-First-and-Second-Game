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

public class Tree extends GameObject {

	float appleMaxRatioSize = 0.1f;
	float healthWhenLastDroppedFruit;

	public Tree(String name, int health, String imagePath, Square squareGameObjectIsOn, Inventory inventory,
			boolean showInventory, boolean canShareSquare, boolean fitsInInventory, boolean canContainOtherObjects,
			float widthRatio, float heightRatio) {
		super(name, health, imagePath, squareGameObjectIsOn, inventory, showInventory, canShareSquare, fitsInInventory,
				canContainOtherObjects, widthRatio, heightRatio);
		addApple(appleMaxRatioSize);
		healthWhenLastDroppedFruit = this.totalHealth;
	}

	public void addApple(float maxSize) {

		float appleSize = (float) (Math.random() * maxSize);

		Food apple = new Food("Unripe Apple", 5, "apple.png", null, new Inventory(), false, true, true, false,
				appleSize, appleSize);
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
		if (remainingHealth < healthWhenLastDroppedFruit && inventory.size() > 0) {

			ArrayList<GameObject> objectsToDropFromHit = new ArrayList<GameObject>();
			objectsToDropFromHit.addAll(this.inventory.gameObjects);
			for (GameObject objectToDrop : objectsToDropFromHit) {
				Level.actionQueue.add(new ActionDrop(this, this.squareGameObjectIsOn, objectToDrop));
				objectToDrop.drawOffsetY = Game.SQUARE_HEIGHT - objectToDrop.height - 16;
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

		if (Math.random() > 0.998d)
			addApple(0.01f);

		ArrayList<GameObject> objectsToDropRandomly = new ArrayList<GameObject>();
		for (GameObject gameObject : inventory.gameObjects) {
			if (gameObject instanceof Food && Math.random() > 0.999d)
				objectsToDropRandomly.add(gameObject);
		}

		for (GameObject objectToDrop : objectsToDropRandomly) {
			Level.actionQueue.add(new ActionDrop(this, this.squareGameObjectIsOn, objectToDrop));
			objectToDrop.drawOffsetY = Game.SQUARE_HEIGHT - objectToDrop.height - 32;
		}
	}

	@Override
	public Action getDefaultActionInWorld(Actor performer) {
		if (Game.level.activeActor != null && Game.level.activeActor.equippedWeapon != null
				&& Game.level.activeActor.equippedWeapon
						.hasRange(Game.level.activeActor.straightLineDistanceTo(this.squareGameObjectIsOn))) {
			return new ActionAttack(performer, this);
		}
		return null;
	}

	@Override
	public GameObject makeCopy(Square square) {
		return new Tree(new String(name), (int) totalHealth, imageTexturePath, square, inventory.makeCopy(),
				showInventory, canShareSquare, fitsInInventory, canContainOtherObjects, widthRatio, heightRatio);
	}

}
