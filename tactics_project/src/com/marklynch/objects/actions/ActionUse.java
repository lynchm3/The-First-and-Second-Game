package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.requirementtomeet.RequirementToMeet;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Switch;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionUse extends Action {

	public static String ACTION_NAME = "Use";
	public static String ACTION_NAME_CANT_REACH = ACTION_NAME + " (can't reach)";
	// SPECIAL CASE HERE IF UR ADDING NEW ITEMS, THE ACTION NAME GETS SET IN THE
	// CONSTRUCTOR

	GameObject performer;
	Switch switchToUse;
	String verb;
	RequirementToMeet[] requirementsToMeet;

	// Default for hostiles
	public ActionUse(GameObject performer, Switch switchToUse, String actionName, String verb,
			RequirementToMeet[] requirementsToMeet) {
		super(actionName, "action_use.png");
		ACTION_NAME = actionName;
		for (RequirementToMeet requirementToMeet : requirementsToMeet) {
			ACTION_NAME += " " + requirementToMeet.getText();
		}
		this.actionName = ACTION_NAME;
		ACTION_NAME_CANT_REACH = ACTION_NAME + " (can't reach)";
		this.performer = performer;
		this.switchToUse = switchToUse;
		this.verb = verb;
		this.requirementsToMeet = requirementsToMeet;
		if (!check()) {
			enabled = false;
		}
		legal = checkLegality();
		sound = createSound();
	}

	@Override
	public void perform() {

		if (performer.squareGameObjectIsOn.visibleToPlayer)
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " ", verb, " ", switchToUse }));

		switchToUse.use();

		performer.showPow(switchToUse);

		if (performer instanceof Actor) {
			Actor actor = (Actor) performer;
			if (actor.faction == Game.level.factions.get(0)) {
				Game.level.undoList.clear();
			}

			trespassingCheck(this, actor, actor.squareGameObjectIsOn);

			actor.actionsPerformedThisTurn.add(this);
		}
		if (sound != null)
			sound.play();
	}

	@Override
	public boolean check() {
		if (performer instanceof Actor) {
			Actor actor = (Actor) performer;
			if (!actor.canSeeGameObject(switchToUse)) {
				actionName = ACTION_NAME_CANT_REACH;
				return false;
			}

			if (performer.straightLineDistanceTo(switchToUse.squareGameObjectIsOn) != 1) {
				actionName = ACTION_NAME_CANT_REACH;
				return false;
			}

			for (RequirementToMeet requirementToMeet : requirementsToMeet) {
				if (!requirementToMeet.isRequirementMet(actor)) {
					return false;
				}
			}
		}

		return true;
	}

	@Override
	public boolean checkLegality() {
		return true;
	}

	@Override
	public Sound createSound() {
		return new Sound(performer, switchToUse, switchToUse.squareGameObjectIsOn, 1, legal, this.getClass());
	}

}
