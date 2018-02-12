package com.marklynch.objects.actions;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.animation.AnimationGive;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Openable;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionGiveItems extends VariableQtyAction {

	public static final String ACTION_NAME = "Give";
	public static final String ACTION_NAME_DISABLED = "(can't reach)";
	GameObject performer;
	GameObject receiver;
	GameObject[] objects;
	boolean logAsTake;

	public ActionGiveItems(GameObject performer, GameObject receiver, boolean logAsTake,
			ArrayList<GameObject> objects) {
		this(performer, receiver, logAsTake, objects.toArray(new GameObject[objects.size()]), false);
	}

	public ActionGiveItems(GameObject performer, GameObject receiver, boolean logAsTake, GameObject... objects) {
		this(performer, receiver, logAsTake, objects, false);
	}

	public ActionGiveItems(GameObject performer, GameObject receiver, boolean logAsTake, GameObject[] objects,
			boolean doesNothing) {
		super(ACTION_NAME, "right.png");
		if (!(receiver instanceof Actor))
			this.actionName = "Put";
		this.performer = performer;
		this.receiver = receiver;
		this.objects = objects;
		this.logAsTake = logAsTake;
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

		if (!checkRange())
			return;

		int amountToGive = Math.min(objects.length, qty);

		if (amountToGive == 0)
			return;

		if (Game.level.openInventories.size() > 0) {
		} else if (performer.squareGameObjectIsOn.onScreen() && performer.squareGameObjectIsOn.visibleToPlayer) {
			performer.secondaryAnimations.add(new AnimationGive(performer, receiver, objects[0]));
		}

		if (receiver instanceof Openable) {
			((Openable) receiver).open();
		}

		for (int i = 0; i < amountToGive; i++) {

			GameObject object = objects[i];

			if (performer instanceof Actor) {
				Actor actor = (Actor) performer;
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
				object.owner = (Actor) receiver;
			}
		}

		if (Game.level.shouldLog(receiver, performer)) {
			String amountToDropString = "";
			if (amountToGive > 1)
				amountToDropString = "x" + amountToGive;
			if (logAsTake)
				Game.level.logOnScreen(new ActivityLog(
						new Object[] { receiver, " took ", objects[0], amountToDropString, " from ", performer }));
			else if (receiver instanceof Actor)
				Game.level.logOnScreen(new ActivityLog(
						new Object[] { performer, " gave ", objects[0], amountToDropString, " to ", receiver }));
			else
				Game.level.logOnScreen(new ActivityLog(
						new Object[] { performer, " put ", objects[0], amountToDropString, " in ", receiver }));
		}

		if (sound != null)
			sound.play();
	}

	@Override
	public boolean check() {

		return true;
	}

	@Override
	public boolean checkRange() {

		if (performer instanceof Actor && !((Actor) performer).canSeeSquare(receiver.squareGameObjectIsOn)) {
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
