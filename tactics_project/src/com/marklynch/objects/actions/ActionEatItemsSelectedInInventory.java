package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.inventory.InventorySquare;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Liquid;
import com.marklynch.objects.WaterBody;
import com.marklynch.objects.tools.ContainerForLiquids;
import com.marklynch.objects.units.Actor;

public class ActionEatItemsSelectedInInventory extends Action {

	public static final String ACTION_NAME = "Eat";
	public static final String ACTION_NAME_DRINK = "Drink";

	Actor performer;
	GameObject target;
	InventorySquare inventorySquare;

	public ActionEatItemsSelectedInInventory(Actor performer, GameObject object) {

		// public ActionTakeItems(Actor performer, Object target, GameObject
		// object) {
		super(ACTION_NAME, textureEat);
		if (object instanceof Liquid || object instanceof ContainerForLiquids || object instanceof WaterBody) {
			this.actionName = ACTION_NAME_DRINK;
			this.image = textureDrink;
		}
		super.gameObjectPerformer = this.performer = performer;
		this.target = object;
		this.inventorySquare = object.inventorySquare;
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
			new ActionEatItems(performer, target).perform();
		} else {
			Game.level.player.inventory.showQTYDialog(new ActionEatItems(performer, target.inventorySquare.stack),
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
		if (performer.straightLineDistanceTo(target.squareGameObjectIsOn) < 2) {
			return true;
		}
		if (performer.inventory == target.inventoryThatHoldsThisObject)
			return true;

		return false;
	}

	@Override
	public boolean checkLegality() {
		return standardAttackLegalityCheck(performer, target);
	}

	@Override
	public Sound createSound() {
		return null;
	}

}