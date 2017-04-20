package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.Square;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionDrop extends Action {

	public static final String ACTION_NAME = "Drop";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";
	GameObject performer;
	Square square;
	GameObject object;

	public ActionDrop(GameObject performer, Square square, GameObject object) {
		super(ACTION_NAME);
		this.performer = performer;
		this.square = square;
		this.object = object;
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
		if (performer.squareGameObjectIsOn.visibleToPlayer)
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " dropped ", object }));
		if (!performer.inventory.contains(object)) {
			performer.inventory.remove(object);
		}
		if (performer instanceof Actor) {
			Actor actor = (Actor) performer;
			if (actor.equipped == object) {
				actor.equipped = null;
			}
		}

		// receiver.inventory.add(object);
		square.inventory.add(object);
		if (performer instanceof Actor)
			((Actor) performer).actionsPerformedThisTurn.add(this);
	}

	@Override
	public boolean check() {
		if (performer.straightLineDistanceTo(square) > 1) {
			return false;
		}
		if (performer instanceof Actor) {
			Actor actor = (Actor) performer;
			if (!actor.inventory.contains(object) && actor.equipped != object) {
				return false;
			}
		} else {
			if (!performer.inventory.contains(object)) {
				return false;
			}

		}

		if (!square.inventory.canShareSquare() && !object.canShareSquare)
			return false;

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
