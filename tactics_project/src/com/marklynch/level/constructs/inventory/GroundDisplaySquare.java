package com.marklynch.level.constructs.inventory;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actions.ActionEquip;
import com.marklynch.objects.actions.ActionTakeItemsSelectedInInventory;
import com.marklynch.objects.units.Actor;
import com.marklynch.utils.TextureUtils;

public class GroundDisplaySquare extends InventorySquare {

	public GroundDisplaySquare(int x, int y, String imagePath, GroundDisplay groundDisplay) {
		super(x, y, imagePath, null);
		xInPixels = Math.round(groundDisplay.squaresX + xInGrid * Game.INVENTORY_SQUARE_WIDTH);
		yInPixels = Math.round(groundDisplay.squaresY + yInGrid * Game.INVENTORY_SQUARE_HEIGHT);
	}

	@Override
	public Action getDefaultActionForTheSquareOrObject(Actor performer) {
		if (gameObject == null)
			return null;
		return new ActionTakeItemsSelectedInInventory(performer, this.gameObject.inventoryThatHoldsThisObject.parent,
				this.gameObject);

	}

	@Override
	public ArrayList<Action> getAllActionsForTheSquareOrObject(Actor performer) {

		ArrayList<Action> actions = new ArrayList<Action>();
		if (gameObject != null) {
			actions.add(new ActionTakeItemsSelectedInInventory(performer,
					this.gameObject.inventoryThatHoldsThisObject.parent, this.gameObject));
			actions.add(new ActionEquip(performer, this.gameObject));
		}
		return actions;
	}

	@Override
	public void drawStaticUI() {

		if (gameObject.squareGameObjectIsOn != null && gameObject.squareGameObjectIsOn.imageTexture != null) {
			TextureUtils.drawTexture(gameObject.squareGameObjectIsOn.imageTexture, xInPixels, yInPixels,
					xInPixels + Game.INVENTORY_SQUARE_WIDTH, yInPixels + Game.INVENTORY_SQUARE_HEIGHT);
		} else if (gameObject.inventoryThatHoldsThisObject != null
				&& gameObject.inventoryThatHoldsThisObject.parent != null
				&& gameObject.inventoryThatHoldsThisObject.parent instanceof GameObject) {
			TextureUtils.drawTexture(((GameObject) gameObject.inventoryThatHoldsThisObject.parent).imageTexture,
					xInPixels, yInPixels, xInPixels + Game.INVENTORY_SQUARE_WIDTH,
					yInPixels + Game.INVENTORY_SQUARE_HEIGHT);
		}

		super.drawStaticUI();
	}
}
