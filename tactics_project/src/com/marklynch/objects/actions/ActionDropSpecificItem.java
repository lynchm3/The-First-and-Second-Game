package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.InanimateObjectToAddOrRemove;
import com.marklynch.objects.Searchable;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionDropSpecificItem extends Action {

	public static final String ACTION_NAME = "Drop";
	GameObject performer;
	Square square;
	GameObject object;

	public ActionDropSpecificItem(GameObject performer, Square square, GameObject object) {
		super(ACTION_NAME, "action_drop.png");
		this.performer = performer;
		this.square = square;
		this.object = object;
		if (!check()) {
			enabled = false;
		} else {
			actionName = ACTION_NAME + " " + object.name;
		}
		legal = checkLegality();
		sound = createSound();
	}

	@Override
	public void perform() {

		if (!enabled)
			return;

		if (Game.level.shouldLog(performer))
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " dropped ", object }));
		if (!performer.inventory.contains(object)) {
			performer.inventory.remove(object);
		}

		if (performer instanceof Actor) {
			Actor actor = (Actor) performer;
			if (actor.equipped == object) {
				if (actor.inventory.contains(actor.equippedBeforePickingUpObject)) {
					actor.equip(actor.equippedBeforePickingUpObject);
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

		// receiver.inventory.add(object);
		if (square.inventory.contains(Searchable.class)) {
			Searchable searchable = (Searchable) square.inventory.getGameObjectOfClass(Searchable.class);
			searchable.inventory.add(object);
		} else {
			if (performer instanceof Actor)
				square.inventory.add(object);
			else
				Game.level.inanimateObjectsToAdd.add(new InanimateObjectToAddOrRemove(object, square));
		}

		if (performer instanceof Actor)
			((Actor) performer).actionsPerformedThisTurn.add(this);
	}

	@Override
	public boolean check() {
		if (performer.straightLineDistanceTo(square) > 1) {
			actionName = ACTION_NAME + " " + object.name + " (can't reach)";
			return false;
		}
		if (performer instanceof Actor) {
			Actor actor = (Actor) performer;
			if (!actor.inventory.contains(object) && actor.equipped != object) {
				actionName = ACTION_NAME + " " + object.name + " (can't reach)";
				return false;
			}
		} else {
			if (!performer.inventory.contains(object)) {
				actionName = ACTION_NAME + " " + object.name + " (can't reach)";
				return false;
			}

		}

		if (!square.inventory.canShareSquare() && !object.canShareSquare) {
			actionName = ACTION_NAME + " " + object.name + " (no space)";
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
