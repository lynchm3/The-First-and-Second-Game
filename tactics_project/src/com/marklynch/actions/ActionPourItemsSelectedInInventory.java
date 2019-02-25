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
		System.out.println("ActionPourItemsSelectedInInventory");
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
		System.out.println("ActionPourItemsSelectedInInventory.perform 1");

		if (!enabled)
			return;
		System.out.println("ActionPourItemsSelectedInInventory.perform 2");

		if (!checkRange())
			return;
		System.out.println("ActionPourItemsSelectedInInventory.perform 3");

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

		System.out.println("ActionPourItemsSelectedInInventory.check() 1");
		System.out.println("targetSquare = " + targetSquare);
		System.out.println("target = " + target);
		if (targetSquare == null && target == null)
			return false;
		System.out.println("ActionPourItemsSelectedInInventory.check() 2");

		if (gameObjectPerformer instanceof Actor) {
			Actor actor = (Actor) gameObjectPerformer;
			if (!actor.inventory.contains(objectToPour)) {
				return false;
			}
		}
		System.out.println("ActionPourItemsSelectedInInventory.check() 3");

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
