package com.marklynch.objects.actions;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.animation.AnimationGive;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.Trader;
import com.marklynch.ui.ActivityLog;

public class ActionBuyItems extends VariableQtyAction {

	public static final String ACTION_NAME = "Buy";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";
	Actor performer;
	Actor target;
	GameObject[] objects;

	public ActionBuyItems(Actor performer, Actor target, ArrayList<GameObject> objects) {
		this(performer, target, objects.toArray(new GameObject[objects.size()]), false);
	}

	public ActionBuyItems(Actor performer, Actor target, GameObject... objects) {
		this(performer, target, objects, false);
	}

	public ActionBuyItems(Actor performer, Actor target, GameObject[] objects, boolean doesnothing) {
		super(ACTION_NAME, "left.png");
		this.performer = performer;
		this.target = target;
		this.objects = objects;
		if (!check()) {
			enabled = false;
			actionName = ACTION_NAME_DISABLED;
		}
		legal = checkLegality();
		sound = createSound();
	}

	@Override
	public void perform() {

		if (!enabled)
			return;

		int amountToSell = Math.min(objects.length, qty);

		if (amountToSell == 0)
			return;

		if (Game.level.openInventories.size() > 0) {
		} else if (performer.squareGameObjectIsOn.onScreen() && performer.squareGameObjectIsOn.visibleToPlayer) {
			target.secondaryAnimations.add(new AnimationGive(target, performer, objects[0]));
			performer.secondaryAnimations.add(new AnimationGive(performer, target, AnimationGive.gold));
		}

		for (int i = 0; i < amountToSell; i++) {

			GameObject object = objects[i];

			performer.removeFromCarriedGoldValue(object.value);
			target.addToCarriedGoldValue(object.value);
			object.owner = performer;

			if (target != null)
				target.inventory.remove(object);

			performer.inventory.add(object);
			if (object.owner == null)
				object.owner = performer;
			performer.actionsPerformedThisTurn.add(this);
			if (sound != null)
				sound.play();
		}

		if (Game.level.shouldLog(target, performer)) {
			String amountToDropString = "";
			if (amountToSell > 1)
				amountToDropString = "x" + amountToSell;
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " bought ", objects[0], amountToDropString,
					" from ", target, " for ", (objects[0].value * amountToSell), " gold" }));
		}
	}

	@Override
	public boolean check() {
		if (!(performer instanceof Trader) && performer.getCarriedGoldValue() < objects[0].value)
			return false;
		if (!performer.canSeeSquare(target.squareGameObjectIsOn)) {
			actionName = ACTION_NAME + " (can't reach)";
			return false;
		}
		return true;
		// if (target != null &&
		// performer.straightLineDistanceTo(target.squareGameObjectIsOn) < 2) {
		// return true;
		// }
		// return false;
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