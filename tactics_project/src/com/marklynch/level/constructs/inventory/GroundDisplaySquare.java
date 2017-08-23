package com.marklynch.level.constructs.inventory;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actions.ActionEquip;
import com.marklynch.objects.actions.ActionTakeSpecificItem;
import com.marklynch.objects.units.Actor;

public class GroundDisplaySquare extends InventorySquare {

	public GroundDisplaySquare(int x, int y, String imagePath, GroundDisplay groundDisplay) {
		super(x, y, imagePath, null);
		xInPixels = Math.round(groundDisplay.x + xInGrid * Game.INVENTORY_SQUARE_WIDTH);
		yInPixels = Math.round(groundDisplay.y + yInGrid * Game.INVENTORY_SQUARE_HEIGHT);
	}

	@Override
	public Action getDefaultActionForTheSquareOrObject(Actor performer) {
		if (gameObject == null)
			return null;
		return new ActionTakeSpecificItem(performer, this.gameObject.squareGameObjectIsOn, this.gameObject);
	}

	@Override
	public ArrayList<Action> getAllActionsForTheSquareOrObject(Actor performer) {

		ArrayList<Action> actions = new ArrayList<Action>();
		if (gameObject != null) {
			actions.add(new ActionTakeSpecificItem(performer, this.gameObject.squareGameObjectIsOn, this.gameObject));
			actions.add(new ActionEquip(performer, this.gameObject));
		}
		return actions;
	}
}
