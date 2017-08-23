package com.marklynch.level.constructs.inventory;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actions.ActionEquip;
import com.marklynch.objects.actions.ActionTake;
import com.marklynch.objects.units.Actor;

public class GroundDisplaySquare extends InventorySquare {

	public GroundDisplaySquare(int x, int y, String imagePath, GroundDisplay groundDisplay) {
		super(x, y, imagePath, null);
		xInPixels = Math.round(groundDisplay.x + xInGrid * Game.INVENTORY_SQUARE_WIDTH);
		yInPixels = Math.round(groundDisplay.y + yInGrid * Game.INVENTORY_SQUARE_HEIGHT);
	}

	@Override
	public Action getDefaultActionForTheSquareOrObject(Actor performer) {
		// System.out.println("GroundDisplaySquare.getDefaultActionForTheSquareOrObject");
		// System.out.println("this = " + this);
		// System.out.println("this instanceof GroundDisplaySquare = " + (this
		// instanceof GroundDisplaySquare));
		if (gameObject == null)
			return null;
		return new ActionTake(performer, this.gameObject);
	}

	@Override
	public ArrayList<Action> getAllActionsForTheSquareOrObject(Actor performer) {

		ArrayList<Action> actions = new ArrayList<Action>();
		if (gameObject != null) {
			actions.add(new ActionTake(performer, this.gameObject));
			actions.add(new ActionEquip(performer, this.gameObject));
		}
		return actions;
	}
}
