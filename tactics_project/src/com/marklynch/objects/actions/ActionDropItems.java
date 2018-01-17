package com.marklynch.objects.actions;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.animation.AnimationDrop;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionDropItems extends VariableQtyAction {

	public static final String ACTION_NAME = "Drop";
	GameObject performer;
	Square square;
	GameObject[] objects;

	public ActionDropItems(GameObject performer, Square square, ArrayList<GameObject> objects) {
		this(performer, square, objects.toArray(new GameObject[objects.size()]), false);
	}

	public ActionDropItems(GameObject performer, Square square, GameObject... objects) {
		this(performer, square, objects, false);
	}

	public ActionDropItems(GameObject performer, Square square, GameObject[] objects, boolean doesnothing) {
		super(ACTION_NAME, "right.png");
		this.performer = performer;
		this.square = square;
		this.objects = objects;

		if (!check()) {
			enabled = false;
		} else {
			actionName = ACTION_NAME + " " + objects[0].name;
		}
		legal = checkLegality();
		sound = createSound();
	}

	@Override
	public void perform() {

		if (!enabled)
			return;

		int amountToDrop = Math.min(objects.length, qty);

		if (amountToDrop == 0)
			return;

		performer.animation = new AnimationDrop(objects[0].name, performer, this, null, square, objects[0], 1f, 0.5f,
				true);

		for (int i = 0; i < amountToDrop; i++) {

			GameObject object = objects[i];
			// if (!performer.inventory.contains(object)) {
			// }

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

			// receiver.inventory.add(object);
			// if (square.inventory.contains(Searchable.class)) {
			// Searchable searchable = (Searchable)
			// square.inventory.getGameObjectOfClass(Searchable.class);
			// searchable.inventory.add(object);
			// } else {
			// if (performer instanceof Actor)
			// square.inventory.add(object);
			// else
			// Game.level.inanimateObjectsToAdd.add(new
			// InanimateObjectToAddOrRemove(object, square));
			// }
		}

		if (Game.level.shouldLog(performer)) {
			String amountToDropString = "";
			if (amountToDrop > 1)
				amountToDropString = "x" + amountToDrop;
			Game.level.logOnScreen(
					new ActivityLog(new Object[] { performer, " dropped ", objects[0], amountToDropString }));
		}

		if (performer.inventory.groundDisplay != null)
			performer.inventory.groundDisplay.refreshGameObjects();

		if (performer instanceof Actor)
			((Actor) performer).actionsPerformedThisTurn.add(this);
	}

	@Override
	public boolean check() {
		if (performer.straightLineDistanceTo(square) > 1) {
			actionName = ACTION_NAME + " " + objects[0].name + " (can't reach)";
			return false;
		}
		if (performer instanceof Actor) {
			Actor actor = (Actor) performer;
			if (!actor.inventory.contains(objects[0]) && actor.equipped != objects[0]) {
				actionName = ACTION_NAME + " " + objects[0].name + " (can't reach)";
				return false;
			}
		} else {
			if (!performer.inventory.contains(objects[0])) {
				actionName = ACTION_NAME + " " + objects[0].name + " (can't reach)";
				return false;
			}

		}

		if (!square.inventory.canShareSquare() && !objects[0].canShareSquare) {
			actionName = ACTION_NAME + " " + objects[0].name + " (no space)";
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
