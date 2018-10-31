package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.HidingPlace;
import com.marklynch.objects.units.Actor;

public class ActionHide extends Action {

	public static final String ACTION_NAME = "Hide";

	public ActionMove actionMove;

	public ActionHide(Actor performer, HidingPlace object) {
		super(ACTION_NAME, textureHide, performer, object, null);
		super.gameObjectPerformer = this.performer = performer;
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

		performer.actionsPerformedThisTurn.add(this);
		if (sound != null)
			sound.play();

		if (actionMove != null && performer == Game.level.player && Game.level.activeActor == Game.level.player)
			Game.level.endPlayerTurn();
	}

	@Override
	public boolean check() {
		if (performer.squareGameObjectIsOn != target.squareGameObjectIsOn) {
			this.actionMove = new ActionMove(performer, target.squareGameObjectIsOn, false);
			return this.actionMove.enabled;
		}
		return true;
	}

	@Override
	public boolean checkRange() {
		if (performer.straightLineDistanceTo(target.squareGameObjectIsOn) > 1) {
			return false;
		}
		return true;
	}

	@Override
	public boolean checkLegality() {
		if (actionMove != null)
			return true;

		if (target.squareGameObjectIsOn.restricted() == true
				&& !target.squareGameObjectIsOn.owners.contains(performer)) {
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