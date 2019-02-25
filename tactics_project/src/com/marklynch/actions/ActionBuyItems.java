package com.marklynch.actions;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.animation.secondary.AnimationGive;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.actors.Trader;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.ui.ActivityLog;

public class ActionBuyItems extends VariableQtyAction {

	public static final String ACTION_NAME = "Buy";
	GameObject[] objects;

	public ActionBuyItems(Actor performer, Actor target, ArrayList<GameObject> objects) {
		this(performer, target, objects.toArray(new GameObject[objects.size()]), false);
	}

	public ActionBuyItems(Actor performer, Actor target, GameObject... objects) {
		this(performer, target, objects, false);
	}

	public ActionBuyItems(Actor performer, Actor target, GameObject[] objects, boolean doesnothing) {
		super(ACTION_NAME, textureBuy, performer, target);
		this.objects = objects;
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

		int amountToSell = Math.min(objects.length, qty);

		if (amountToSell == 0)
			return;

		if (Game.level.openInventories.size() > 0) {
		} else if (performer.squareGameObjectIsOn.onScreen() && performer.squareGameObjectIsOn.visibleToPlayer) {
			Level.addSecondaryAnimation(new AnimationGive(targetGameObject, performer, objects[0], null));
			Level.addSecondaryAnimation(new AnimationGive(performer, targetGameObject, AnimationGive.gold, null));
		}

		for (int i = 0; i < amountToSell; i++) {

			GameObject object = objects[i];

			performer.removeFromCarriedGoldValue(object.value);
			((Actor) targetGameObject).addToCarriedGoldValue(object.value);
			object.owner = performer;

			if (targetGameObject != null)
				targetGameObject.inventory.remove(object);

			performer.inventory.add(object);
			if (object.owner == null)
				object.owner = performer;
			if (sound != null)
				sound.play();
		}

		if (Game.level.shouldLog(targetGameObject, performer)) {
			String amountToDropString = "";
			if (amountToSell > 1)
				amountToDropString = "x" + amountToSell;
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " bought ", objects[0], amountToDropString,
					" from ", targetGameObject, " for ", (objects[0].value * amountToSell), " gold" }));
		}
	}

	@Override
	public boolean check() {
		if (!(performer instanceof Trader) && performer.getCarriedGoldValue() < objects[0].value) {
			disabledReason = NOT_ENOUGH_GOLD;
			return false;
		}
		return true;
	}

	@Override
	public boolean checkRange() {
		if (!performer.canSeeSquare(targetGameObject.squareGameObjectIsOn)) {
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