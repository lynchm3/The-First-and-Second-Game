package com.marklynch.tactics.objects;

import com.marklynch.Game;
import com.marklynch.tactics.objects.level.Square;
import com.marklynch.utils.TextureUtils;

public class Roof extends GameObject {

	public Roof(String name, int health, String imagePath, Square squareGameObjectIsOn, Inventory inventory,
			boolean showInventory, boolean canShareSquare, boolean fitsInInventory, boolean canContainOtherObjects) {
		super(name, health, imagePath, squareGameObjectIsOn, inventory, showInventory, canShareSquare, fitsInInventory,
				canContainOtherObjects);
	}

	@Override
	public void draw1() {

	}

	@Override
	public void draw3() {
		// if (this.squareGameObjectIsOn.building !=
		// Game.level.factions.get(0).actors
		// .get(0).squareGameObjectIsOn.building) {
		// super.draw1();
		// } else {
		// Draw object
		if (squareGameObjectIsOn != null) {
			int actorPositionXInPixels = this.squareGameObjectIsOn.xInGrid * (int) Game.SQUARE_WIDTH;
			int actorPositionYInPixels = this.squareGameObjectIsOn.yInGrid * (int) Game.SQUARE_HEIGHT;

			float alpha = 0.33f;

			// TextureUtils.skipNormals = true;
			TextureUtils.drawTexture(imageTexture, alpha, actorPositionXInPixels,
					actorPositionXInPixels + Game.SQUARE_WIDTH, actorPositionYInPixels,
					actorPositionYInPixels + Game.SQUARE_HEIGHT);
			// TextureUtils.skipNormals = false;
		}
		// }
	}

	@Override
	public GameObject makeCopy(Square square) {
		return new Roof(new String(name), (int) totalHealth, imageTexturePath, square, inventory.makeCopy(),
				showInventory, canShareSquare, fitsInInventory, canContainOtherObjects);
	}

}
