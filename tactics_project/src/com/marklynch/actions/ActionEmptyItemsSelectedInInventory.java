package com.marklynch.actions;

import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.inventory.InventorySquare;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.tools.Jar;

public class ActionEmptyItemsSelectedInInventory extends Action {

	public static final String ACTION_NAME = "Empty";
	Jar jar;
	InventorySquare inventorySquare;

	public ActionEmptyItemsSelectedInInventory(GameObject performer, Object target, Jar jar) {
		super(ACTION_NAME, textureDrop, performer, target);
		this.jar = jar;
		this.inventorySquare = jar.inventorySquare;

		if (!check()) {
			enabled = false;
		} else {
			actionName = ACTION_NAME + " " + jar.name;
		}
		legal = checkLegality();
		sound = createSound();
	}

	@Override
	public void perform() {
		super.perform();

		if (!enabled)
			return;

		if (!checkRange())
			return;

//		if (inventorySquare.stack.size() <= 5) {
		new ActionEmptyItem(gameObjectPerformer, targetGameObjectOrSquare, jar).perform();
//		} else
//
//		{
//			Game.level.player.inventory.showQTYDialog(
//					new ActionDropItems(gameObjectPerformer, targetSquare, objectToDrop.inventorySquare.stack),
//					inventorySquare.stack.size(), "Enter qty to drop (have: " + inventorySquare.stack.size() + ")", 0);
//		}
	}

	@Override
	public boolean check() {
		if (targetSquare == null && targetGameObject == null)
			return false;

		if (gameObjectPerformer instanceof Actor) {
			Actor actor = (Actor) gameObjectPerformer;
			if (!actor.inventory.contains(jar)) {
				return false;
			}
		}

		return true;
	}

	@Override
	public boolean checkRange() {
		if (gameObjectPerformer.straightLineDistanceTo(targetSquare) > 1) {
			return false;
		}

		return true;
	}

	@Override
	public boolean checkLegality() {
		return true;
	}

	@Override
	public Sound createSound() {
		return null;
	}
}
