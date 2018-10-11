package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.HidingPlace;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionHide extends Action {

	public static final String ACTION_NAME = "Hide";

	Actor performer;
	HidingPlace object;

	public ActionMove actionMove;

	public ActionHide(Actor performer, HidingPlace object) {
		super(ACTION_NAME, "action_hide.png");
		super.gameObjectPerformer = this.performer = performer;
		this.object = object;
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

		if (performer.hiding == false) {

			for (GameObject attacker : performer.attackers) {
				if (attacker instanceof Actor) {
					Actor attackerActor = (Actor) attacker;
					if (attackerActor.canSeeGameObject(performer)) {
						attackerActor.addAttackerForThisAndGroupMembers(object);
						attackerActor.addAttackerForNearbyFactionMembersIfVisible(object);
					}
				}
			}

			performer.hiding = true;
			performer.hidingPlace = object;
			object.actorsHidingHere.add(performer);

			if (Game.level.shouldLog(performer))
				Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " hid in ", object }));
		}

		performer.actionsPerformedThisTurn.add(this);
		if (sound != null)
			sound.play();

		if (actionMove != null && performer == Game.level.player && Game.level.activeActor == Game.level.player)
			Game.level.endPlayerTurn();
	}

	@Override
	public boolean check() {
		if (performer.squareGameObjectIsOn != object.squareGameObjectIsOn) {
			this.actionMove = new ActionMove(performer, object.squareGameObjectIsOn, false);
			return this.actionMove.enabled;
		}
		return true;
	}

	@Override
	public boolean checkRange() {
		if (performer.straightLineDistanceTo(object.squareGameObjectIsOn) > 1) {
			return false;
		}
		return true;
	}

	@Override
	public boolean checkLegality() {
		if (actionMove != null)
			return true;

		if (object.squareGameObjectIsOn.restricted() == true
				&& !object.squareGameObjectIsOn.owners.contains(performer)) {
			illegalReason = TRESSPASSING;
			return false;
		}
		return true;
	}

	@Override
	public Sound createSound() {
		return null;
	}

}