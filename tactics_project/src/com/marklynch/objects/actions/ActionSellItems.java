package com.marklynch.objects.actions;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.ai.routines.AIRoutine;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.animation.AnimationGive;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.Trader;
import com.marklynch.ui.ActivityLog;

public class ActionSellItems extends VariableQtyAction {

	public static final String ACTION_NAME = "Sell";
	public static final String ACTION_NAME_DISABLED = "(can't reach)";
	Actor performer;
	Actor receiver;
	GameObject[] objects;

	public ActionSellItems(Actor performer, Actor receiver, ArrayList<GameObject> objects) {
		this(performer, receiver, objects.toArray(new GameObject[objects.size()]), false);
	}

	public ActionSellItems(Actor performer, Actor receiver, GameObject... objects) {
		this(performer, receiver, objects, false);
	}

	public ActionSellItems(Actor performer, Actor receiver, GameObject[] objects, boolean doesnothing) {
		super(ACTION_NAME, "right.png");
		this.performer = performer;
		this.receiver = receiver;
		this.objects = objects;
		if (!check()) {
			enabled = false;
			actionName = actionName + ACTION_NAME_DISABLED;
		} else {
			actionName = actionName + " " + objects[0].name;
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

		for (GameObject object : objects) {
			if (object.owner == receiver) {
				Crime crime = new Crime(this, this.performer, object.owner, Crime.TYPE.CRIME_THEFT, object);
				receiver.addWitnessedCrime(crime);
				if (Game.level.openInventories.size() > 0)
					Game.level.openCloseInventory();
				ArrayList<GameObject> stolenObjects = new ArrayList<GameObject>();
				stolenObjects.add(object);
				new ActionTalk(this.receiver, performer,
						AIRoutine.createJusticeReclaimConversation(receiver, performer, stolenObjects)).perform();
				crime.hasBeenToldToStop = true;
				return;
			}
		}

		if (Game.level.openInventories.size() > 0) {
		} else {
			performer.animation = new AnimationGive(performer, receiver, objects[0]);
			receiver.animation = new AnimationGive(receiver, performer, AnimationGive.gold);
		}

		for (int i = 0; i < amountToSell; i++) {

			GameObject object = objects[i];

			performer.addToCarriedGoldValue(object.value);
			receiver.removeFromCarriedGoldValue(object.value);
			object.owner = receiver;

			if (performer instanceof Actor) {
				Actor actor = performer;
				if (actor.equipped == object) {
					if (actor.inventory.contains(actor.equippedBeforePickingUpObject)) {
						actor.equip(actor.equippedBeforePickingUpObject);
					} else if (actor.inventory.containsDuplicateOf(object)) {
						actor.equip(actor.inventory.getDuplicateOf(object));
					} else {
						actor.equip(null);
					}
					actor.equippedBeforePickingUpObject = null;
				}
				if (actor.helmet == object)
					actor.helmet = null;
				if (actor.bodyArmor == object)
					actor.bodyArmor = null;
				if (actor.legArmor == object)
					actor.legArmor = null;
			}
			performer.inventory.remove(object);

			receiver.inventory.add(object);
			if (receiver instanceof Actor) {
				object.owner = receiver;
			}
			if (sound != null)
				sound.play();
		}

		if (Game.level.shouldLog(receiver, performer)) {
			String amountToDropString = "";
			if (amountToSell > 1)
				amountToDropString = "x" + amountToSell;
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " sold ", objects[0], amountToDropString,
					" to ", receiver, " for ", (objects[0].value * amountToSell), " gold" }));
		}
	}

	@Override
	public boolean check() {
		if (!(receiver instanceof Trader) && receiver.getCarriedGoldValue() < objects[0].value)
			return false;
		if (!performer.canSeeSquare(receiver.squareGameObjectIsOn)) {
			actionName = ACTION_NAME + " (can't reach)";
			return false;
		}
		return true;
	}

	@Override
	public boolean checkLegality() {
		for (GameObject gameObject : objects) {
			if (gameObject.owner == receiver) {
				return false;
			}
		}
		return true;
	}

	@Override
	public Sound createSound() {
		return null;
	}
}
