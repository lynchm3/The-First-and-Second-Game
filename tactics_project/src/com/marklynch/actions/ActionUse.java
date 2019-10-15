package com.marklynch.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.requirementtomeet.RequirementToMeet;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.inanimateobjects.Switch;
import com.marklynch.ui.ActivityLog;

public class ActionUse extends Action {

	public static String ACTION_NAME = "Use";

	Switch switchToUse;
	String verb;
	RequirementToMeet[] requirementsToMeet;

	// Default for hostiles
	public ActionUse(GameObject performer, Switch switchToUse, String actionName, String verb,
			RequirementToMeet[] requirementsToMeet) {
		super(actionName, textureUse, performer, switchToUse);
		ACTION_NAME = actionName;
		for (RequirementToMeet requirementToMeet : requirementsToMeet) {
			ACTION_NAME += " " + requirementToMeet.getText();
		}
		if (performer.equipped == switchToUse) {
			this.targetSquare = performer.squareGameObjectIsOn;
			this.actionName = ACTION_NAME + " " + targetGameObject.name;
		}
		this.actionName = ACTION_NAME;
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
		super.perform();

		if (!enabled)
			return;

		if (!checkRange())
			return;

		if (Game.level.shouldLog(gameObjectPerformer))
			Game.level.logOnScreen(new ActivityLog(new Object[] { gameObjectPerformer, " ", verb, " ", switchToUse }));

		switchToUse.use();

		switchToUse.showPow();

		if (gameObjectPerformer instanceof Actor) {
			Actor actor = (Actor) gameObjectPerformer;
			if (actor.faction == Game.level.factions.player) {
				Game.level.undoList.clear();
			}

			trespassingCheck(actor, actor.squareGameObjectIsOn);

		}
		if (sound != null)
			sound.play();
	}

	@Override
	public boolean check() {
		if (gameObjectPerformer instanceof Actor) {
			Actor actor = (Actor) gameObjectPerformer;

			boolean requirementsMet = true;

			for (RequirementToMeet requirementToMeet : requirementsToMeet) {
				if (!requirementToMeet.isRequirementMet(actor)) {
					if (disabledReason.length() == 0)
						disabledReason += "Requirements not met: " + requirementToMeet.getText();
					else
						disabledReason += ", " + requirementToMeet.getText();
					requirementsMet = false;
				}
			}

			if (requirementsMet == false)
				return false;

		}

		return true;
	}

	@Override
	public boolean checkRange() {

		if (gameObjectPerformer instanceof Actor) {

			Actor actor = (Actor) gameObjectPerformer;

			if (actor.equipped == switchToUse)
				return true;

			if (!actor.canSeeGameObject(switchToUse)) {
				return false;
			}

			if (gameObjectPerformer.straightLineDistanceTo(switchToUse.squareGameObjectIsOn) != 1) {
				return false;
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
		return new Sound(gameObjectPerformer, switchToUse, targetSquare, 1, legal, this.getClass());
	}

}
