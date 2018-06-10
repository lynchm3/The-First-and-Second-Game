package com.marklynch.objects.actions;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.animation.secondary.AnimationTake;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.tools.Shovel;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionDig extends Action {

	public static final String ACTION_NAME = "Dig";
	public static final String ACTION_NAME_CANT_REACH = ACTION_NAME + " (can't reach)";
	public static final String ACTION_NAME_NEED_SHOVEL = ACTION_NAME + " (need shovel)";

	Actor performer;
	GameObject target;

	// Default for hostiles
	public ActionDig(Actor attacker, GameObject target) {
		super(ACTION_NAME, "action_dig.png");
		super.gameObjectPerformer = this.performer = attacker;
		this.target = target;
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

		Shovel shovel = (Shovel) performer.inventory.getGameObjectOfClass(Shovel.class);

		performer.distanceMovedThisTurn = performer.travelDistance;
		performer.hasAttackedThisTurn = true;

		target.squareGameObjectIsOn.imageTexture = Square.MUD_TEXTURE;

		if (Game.level.shouldLog(target, performer))
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " dug up ", target, " with ", shovel }));

		for (GameObject buriedGamObject : (ArrayList<GameObject>) target.inventory.gameObjects.clone()) {
			if (Game.level.openInventories.size() > 0) {
			} else if (performer.squareGameObjectIsOn.onScreen() && performer.squareGameObjectIsOn.visibleToPlayer) {
				performer.addSecondaryAnimation(new AnimationTake(buriedGamObject, performer, 0, 0, 1f));
			}
			performer.inventory.add(buriedGamObject);
			if (Game.level.shouldLog(target, performer))
				Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " received ", buriedGamObject }));
			if (!legal) {
				Crime crime = new Crime(this, this.performer, this.target.owner, Crime.TYPE.CRIME_THEFT,
						buriedGamObject);
				this.performer.crimesPerformedThisTurn.add(crime);
				this.performer.crimesPerformedInLifetime.add(crime);
				notifyWitnessesOfCrime(crime);
			} else {
				trespassingCheck(this, performer, performer.squareGameObjectIsOn);
			}
		}

		float damage = target.remainingHealth;
		target.changeHealth(-damage, null, null);
		target.checkIfDestroyed(performer, this);

		target.showPow();

		if (performer.faction == Game.level.factions.player) {
			Game.level.undoList.clear();
		}

		if (performer == Game.level.player && Game.level.activeActor == Game.level.player)
			Game.level.endPlayerTurn();
		performer.actionsPerformedThisTurn.add(this);
		if (sound != null)
			sound.play();
	}

	@Override
	public boolean check() {

		if (!performer.inventory.contains(Shovel.class)) {
			actionName = ACTION_NAME_NEED_SHOVEL;
			disabledReason = "You need a shovel";
			return false;
		}

		return true;
	}

	@Override
	public boolean checkRange() {

		if (performer.straightLineDistanceTo(target.squareGameObjectIsOn) > 1) {
			actionName = ACTION_NAME_CANT_REACH;
			return false;
		}

		return true;
	}

	@Override
	public boolean checkLegality() {
		if (target.owner != null && target.owner != performer)
			return false;
		return true;
	}

	@Override
	public Sound createSound() {
		Shovel shovel = (Shovel) performer.inventory.getGameObjectOfClass(Shovel.class);
		if (shovel != null) {
			float loudness = Math.max(target.soundWhenHit, shovel.soundWhenHitting);
			return new Sound(performer, shovel, target.squareGameObjectIsOn, loudness, legal, this.getClass());
		}
		return null;
	}

}
