package com.marklynch.actions;

import java.util.ArrayList;
import java.util.Arrays;

import com.marklynch.Game;
import com.marklynch.ai.routines.AIRoutine;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.animation.secondary.AnimationGive;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.actors.Trader;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.ui.ActivityLog;

public class ActionSellItems extends VariableQtyAction {

	public static final String ACTION_NAME = "Sell";
	GameObject[] objects;
	Actor receiver;

	public ActionSellItems(Actor performer, Actor receiver, ArrayList<GameObject> objects) {
		this(performer, receiver, objects.toArray(new GameObject[objects.size()]), false);
	}

	public ActionSellItems(Actor performer, Actor receiver, GameObject... objects) {
		this(performer, receiver, objects, false);
	}

	public ActionSellItems(Actor performer, Actor receiver, GameObject[] objects, boolean doesnothing) {
		super(ACTION_NAME, textureSell, performer, receiver);

		this.objects = objects;
		this.receiver = receiver;
		if (!check()) {
			enabled = false;
		} else {
			actionName = actionName + " " + objects[0].name;
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

		for (GameObject object : objects) {
			if (object.owner == receiver) {
				Crime crime = new Crime(this.performer, object.owner, Crime.TYPE.CRIME_THEFT, object);

				for (Crime c : performer.crimesPerformedInLifetime) {
					if (Arrays.asList(c.stolenItems).contains(object)) {
						crime = c;
					}
				}

				receiver.addWitnessedCrime(crime);
				if (Game.level.openInventories.size() > 0)
					Game.level.openCloseInventory();
				ArrayList<GameObject> stolenObjects = new ArrayList<GameObject>();
				stolenObjects.add(object);
				new ActionTalk(this.receiver, performer,
						AIRoutine.createJusticeReclaimConversation(receiver, performer, stolenObjects)).perform();
				// crime.hasBeenToldToStop = true;
				return;
			}
		}

		if (Game.level.openInventories.size() > 0) {
		} else if (performer.squareGameObjectIsOn.onScreen() && performer.squareGameObjectIsOn.visibleToPlayer) {
			Level.addSecondaryAnimation(new AnimationGive(performer, receiver, objects[0], null));
			Level.addSecondaryAnimation(new AnimationGive(receiver, performer, AnimationGive.gold, null));
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
		if (!(receiver instanceof Trader) && receiver.getCarriedGoldValue() < objects[0].value) {
			disabledReason = NOT_ENOUGH_GOLD;
			return false;
		}
		if (receiver.knownCriminals.contains(performer)) {
			disabledReason = NOT_ENOUGH_TRUST;
			return false;
		}
		return true;
	}

	@Override
	public boolean checkRange() {

		if (!performer.canSeeSquare(receiver.squareGameObjectIsOn)) {
			return false;
		}
		return true;
	}

	@Override
	public boolean checkLegality() {
		for (GameObject gameObject : objects) {
			if (gameObject.owner == receiver) {
				illegalReason = THEFT;
				if (gameObject.value > 100)
					illegalReason = GRAND_THEFT;
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
