package com.marklynch.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.HidingPlace;

public class ActionHide extends Action {

	public static final String ACTION_NAME = "Hide";

	public ActionMove actionMove;

	public ActionHide(Actor performer, HidingPlace target) {
		super(ACTION_NAME, textureHide, performer, target);
		if (!check()) {
			enabled = false;
			if (this.actionMove != null)
				actionName = this.actionMove.actionName;
		}

		if (actionMove != null)
			movement = true;

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

		if (actionMove != null)
			actionMove.perform();

		// if (performer.hiding == false) {
		//
		// for (GameObject attacker : performer.attackers) {
		// if (attacker instanceof Actor) {
		// Actor attackerActor = (Actor) attacker;
		// if (attackerActor.canSeeGameObject(performer)) {
		// attackerActor.addAttackerForThisAndGroupMembers(object);
		// attackerActor.addAttackerForNearbyFactionMembersIfVisible(object);
		// }
		// }
		// }
		//
		// if (Game.level.shouldLog(performer))
		// Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " hid in ",
		// object }));
		// }

		if (sound != null)
			sound.play();

		if (actionMove != null && performer == Game.level.player && Game.level.activeActor == Game.level.player)
			Game.level.endPlayerTurn();
	}

	@Override
	public boolean check() {
		if (performer.squareGameObjectIsOn != targetGameObject.squareGameObjectIsOn) {
			this.actionMove = new ActionMove(performer, targetGameObject.squareGameObjectIsOn, false, true);
			return this.actionMove.enabled;
		}
		return true;
	}

	@Override
	public boolean checkRange() {
		if (performer.straightLineDistanceTo(targetGameObject.squareGameObjectIsOn) > 1) {
			return false;
		}
		return true;
	}

	@Override
	public boolean checkLegality() {
		if (actionMove != null)
			return true;

		if (targetGameObject.squareGameObjectIsOn.restricted() == true
				&& !targetGameObject.squareGameObjectIsOn.owners.contains(performer)) {
			illegalReason = TRESPASSING;
			return false;
		}
		return true;
	}

	@Override
	public Sound createSound() {
		return null;
	}

}