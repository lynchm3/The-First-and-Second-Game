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

	public Tree(String name, int health, String imagePath, Square squareGameObjectIsOn, Inventory inventory,
			boolean showInventory, boolean canShareSquare, boolean fitsInInventory, boolean canContainOtherObjects,
			float widthRatio, float heightRatio) {
		super(name, health, imagePath, squareGameObjectIsOn, inventory, showInventory, canShareSquare, fitsInInventory,
				canContainOtherObjects, widthRatio, heightRatio);
		Food apple = new Food("Apple", 5, "apple.png", null, new Inventory(), false, true, true, false, 0.25f, 0.25f);

		float appleDrawOffsetXMax = width - apple.width;
		float appleDrawOffsetYMax = Game.HALF_SQUARE_HEIGHT - apple.height;

		apple.drawOffsetX = (float) (Math.random() * appleDrawOffsetXMax);
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
		if (remainingHealth < totalHealth && inventory.size() > 0) {

			ArrayList<GameObject> objectsToDrop = new ArrayList<GameObject>();
			objectsToDrop.addAll(this.inventory.gameObjects);
			for (GameObject objectToDrop : objectsToDrop) {
				Level.actionQueue.add(new ActionDrop(this, this.squareGameObjectIsOn, objectToDrop));
				// float appleDrawOffsetYMax = Game.SQUARE_HEIGHT -
				// objectToDrop.height;
				// float appleDrawOffsetYMax = Game.SQUARE_HEIGHT -
				// objectToDrop.height;
				objectToDrop.drawOffsetY = Game.SQUARE_HEIGHT - objectToDrop.height - 32;
			}

		}
	}

	@Override
	public Action getDefaultAction(Actor performer) {
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
