package com.marklynch.actions;

import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.inventory.InventorySquare;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.tools.ContainerForLiquids;

public class ActionPourItemsSelectedInInventory extends Action {

	public static final String ACTION_NAME = "Pour";
	ContainerForLiquids objectToPour;
	InventorySquare inventorySquare;

	public ActionPourItemsSelectedInInventory(GameObject performer, Object target, ContainerForLiquids objectToPour) {
		super(ACTION_NAME, textureDrop, performer, target);
		this.objectToPour = objectToPour;
		this.inventorySquare = objectToPour.inventorySquare;

		if (!check()) {
			enabled = false;
		} else {
			actionName = ACTION_NAME + " " + objectToPour.name;
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
		new ActionPourItem(gameObjectPerformer, targetGameObjectOrSquare, objectToPour).perform();
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
			if (!actor.inventory.contains(objectToPour)) {
				return false;
			}
		}

//		else {
//			if (!gameObjectPerformer.inventory.contains(objectToPour)) {
//				return false;
//			}
//		}

//		if (!targetSquare.inventory.canShareSquare && !objectToPour.canShareSquare) {
//			disabledReason = NO_SPACE;
//			return false;
//		}

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
