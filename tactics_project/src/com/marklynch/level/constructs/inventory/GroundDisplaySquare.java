package com.marklynch.level.constructs.inventory;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actions.ActionEquip;
import com.marklynch.objects.actions.ActionTakeItemsSelectedInInventory;
import com.marklynch.objects.actors.Actor;
import com.marklynch.utils.TextureUtils;

public class GroundDisplaySquare extends InventorySquare {

	public GroundDisplaySquare(int x, int y, String imagePath, GroundDisplay groundDisplay) {
		super(x, y, imagePath, null);
		xInPixels = Math.round(groundDisplay.squaresX + xInGrid * Game.INVENTORY_SQUARE_WIDTH);
		yInPixels = Math.round(groundDisplay.squaresY + yInGrid * Game.INVENTORY_SQUARE_HEIGHT);
	}

	@Override
	public Action getDefaultActionForTheSquareOrObject(Actor performer, boolean keyPress) {
		if (stack.get(0) == null)
			return null;
		return new ActionTakeItemsSelectedInInventory(performer, this.stack.get(0).inventoryThatHoldsThisObject.parent,
				this.stack.get(0));

	}

	@Override
	public ArrayList<Action> getAllActionsForTheSquareOrObject(Actor performer) {

		ArrayList<Action> actions = new ArrayList<Action>();
		if (stack.get(0) != null) {
			actions.add(new ActionTakeItemsSelectedInInventory(performer,
					this.stack.get(0).inventoryThatHoldsThisObject.parent, this.stack.get(0)));
			actions.add(new ActionEquip(performer, this.stack.get(0)));
		}
		return actions;
	}

	@Override
	public void drawStaticUI() {

		if (stack.get(0).squareGameObjectIsOn != null && stack.get(0).squareGameObjectIsOn.floorImageTexture != null) {
			TextureUtils.drawTexture(stack.get(0).squareGameObjectIsOn.floorImageTexture, xInPixels, yInPixels,
					xInPixels + Game.INVENTORY_SQUARE_WIDTH, yInPixels + Game.INVENTORY_SQUARE_HEIGHT);
		} else if (stack.get(0).inventoryThatHoldsThisObject != null
				&& stack.get(0).inventoryThatHoldsThisObject.parent != null
				&& stack.get(0).inventoryThatHoldsThisObject.parent instanceof GameObject) {
			TextureUtils.drawTexture(((GameObject) stack.get(0).inventoryThatHoldsThisObject.parent).imageTexture,
					xInPixels, yInPixels, xInPixels + Game.INVENTORY_SQUARE_WIDTH,
					yInPixels + Game.INVENTORY_SQUARE_HEIGHT);
		}

		super.drawStaticUI();
	}
}
