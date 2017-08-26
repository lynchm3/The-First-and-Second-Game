package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionTakeSpecificItem extends Action {

	public static final String ACTION_NAME = "Take";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";

	Actor performer;
	Object target;
	Square targetSquare;
	GameObject targetGameObject;
	GameObject object;

	public ActionTakeSpecificItem(Actor performer, Object target, GameObject object) {
		super(ACTION_NAME, "left.png");
		this.performer = performer;
		this.target = target;
		if (this.target instanceof Square)
			targetSquare = (Square) target;
		if (this.target instanceof GameObject)
			targetGameObject = (GameObject) target;
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
		if (Game.level.shouldLog(object, performer)) {
			if (legal) {
				if (targetGameObject == null)
					Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " took ", object }));
				else
					Game.level.logOnScreen(
							new ActivityLog(new Object[] { performer, " took ", object, " from ", targetGameObject }));
			} else {
				if (targetGameObject == null)
					Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " stole ", object }));
				else
					Game.level.logOnScreen(
							new ActivityLog(new Object[] { performer, " stole ", object, " from ", targetGameObject }));
			}
		}

		if (targetSquare != null)
			targetSquare.inventory.remove(object);

		if (targetGameObject != null)
			targetGameObject.inventory.remove(object);

		performer.inventory.add(object);
		if (object.owner == null)
			object.owner = performer;
		performer.actionsPerformedThisTurn.add(this);
		if (sound != null)
			sound.play();

		if (!legal) {
			Crime crime = new Crime(this, this.performer, object.owner, Crime.CRIME_SEVERITY_THEFT, object);
			this.performer.crimesPerformedThisTurn.add(crime);
			this.performer.crimesPerformedInLifetime.add(crime);
			notifyWitnessesOfCrime(crime);
		}
	}

	@Override
	public boolean check() {
		if (targetSquare != null && performer.straightLineDistanceTo(targetSquare) < 2) {
			return true;
		}
		if (targetGameObject != null && performer.straightLineDistanceTo(targetGameObject.squareGameObjectIsOn) < 2) {
			return true;
		}
		return false;
	}

	@Override
	public boolean checkLegality() {
		if (object.owner != null && object.owner != performer)
			return false;
		return true;
	}

	@Override
	public Sound createSound() {
		return null;
	}

}