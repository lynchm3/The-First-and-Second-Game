package com.marklynch.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.inventory.InventorySquare;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.inanimateobjects.Liquid;
import com.marklynch.objects.inanimateobjects.WaterBody;
import com.marklynch.objects.tools.ContainerForLiquids;

public class ActionEatItemsSelectedInInventory extends Action {

	public static final String ACTION_NAME = "Eat";
	public static final String ACTION_NAME_DRINK = "Drink";

	InventorySquare inventorySquare;

	public ActionEatItemsSelectedInInventory(Actor performer, GameObject target) {

		// public ActionTakeItems(Actor performer, Object target, GameObject
		// object) {
		super(ACTION_NAME, textureEat, performer, target);
		if (target instanceof Liquid || target instanceof ContainerForLiquids || target instanceof WaterBody) {
			this.actionName = ACTION_NAME_DRINK;
			this.image = textureDrink;
		}
		this.inventorySquare = target.inventorySquare;
		if (!check()) {
			enabled = false;
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

		if (inventorySquare.stack.size() <= 5) {
			new ActionEatItems(performer, targetGameObject).perform();
		} else {
			Game.level.player.inventory.showQTYDialog(new ActionEatItems(performer, targetGameObject.inventorySquare.stack),
					inventorySquare.stack.size(), "Enter qty to eat (available: " + inventorySquare.stack.size() + ")",
					0);
		}
	}

	@Override
	public boolean check() {

		return true;
	}

	@Override
	public boolean checkRange() {
		if (performer.straightLineDistanceTo(targetGameObject.squareGameObjectIsOn) < 2) {
			return true;
		}
		if (performer.inventory == targetGameObject.inventoryThatHoldsThisObject)
			return true;

		return false;
	}

	@Override
	public boolean checkLegality() {
		return standardAttackLegalityCheck(performer, targetGameObject);
	}

	@Override
	public Sound createSound() {
		return null;
	}

}