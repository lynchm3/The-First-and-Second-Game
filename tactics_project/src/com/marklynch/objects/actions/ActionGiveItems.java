package com.marklynch.objects.actions;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.animation.secondary.AnimationGive;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Openable;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionGiveItems extends VariableQtyAction {

	public static final String ACTION_NAME = "Give";
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
		super(ACTION_NAME, textureGive, performer, receiver);
		if (!(receiver instanceof Actor))
			this.actionName = "Put";
		this.objects = objects;
		this.logAsTake = logAsTake;
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

		int amountToGive = Math.min(objects.length, qty);

		if (amountToGive == 0)
			return;

		if (Game.level.openInventories.size() > 0) {
		} else if (gameObjectPerformer.squareGameObjectIsOn.onScreen()
				&& gameObjectPerformer.squareGameObjectIsOn.visibleToPlayer) {
			gameObjectPerformer.addSecondaryAnimation(new AnimationGive(gameObjectPerformer, target, objects[0], null));
		}

		if (target instanceof Openable) {
			((Openable) target).open();
		}

		for (int i = 0; i < amountToGive; i++) {

			GameObject object = objects[i];

			if (gameObjectPerformer instanceof Actor) {
				Actor actor = (Actor) gameObjectPerformer;
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
			gameObjectPerformer.inventory.remove(object);

			if (target instanceof Actor && !logAsTake) {
				object.owner = (Actor) target;
			}

			target.inventory.add(object);
		}

		if (Game.level.shouldLog(target, gameObjectPerformer)) {
			String amountToDropString = "";
			if (amountToGive > 1)
				amountToDropString = "x" + amountToGive;
			if (logAsTake)
				Game.level.logOnScreen(new ActivityLog(new Object[] { target, " took ", objects[0], amountToDropString,
						" from ", gameObjectPerformer }));
			else if (target instanceof Actor)
				Game.level.logOnScreen(new ActivityLog(new Object[] { gameObjectPerformer, " gave ", objects[0],
						amountToDropString, " to ", target }));
			else
				Game.level.logOnScreen(new ActivityLog(
						new Object[] { gameObjectPerformer, " put ", objects[0], amountToDropString, " in ", target }));
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

		if (gameObjectPerformer instanceof Actor
				&& !((Actor) gameObjectPerformer).canSeeSquare(target.squareGameObjectIsOn)) {
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
