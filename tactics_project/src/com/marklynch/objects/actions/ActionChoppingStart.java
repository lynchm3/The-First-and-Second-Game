package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.Level.LevelMode;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.animation.AnimationShake;
import com.marklynch.level.constructs.animation.AnimationTake;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Junk;
import com.marklynch.objects.templates.Templates;
import com.marklynch.objects.tools.Axe;
import com.marklynch.objects.tools.Pickaxe;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.Player;
import com.marklynch.ui.ActivityLog;

public class ActionChoppingStart extends Action {

	public static final String ACTION_NAME = "Chop";
	public static final String ACTION_NAME_CANT_REACH = ACTION_NAME + " (can't reach)";
	public static final String ACTION_NAME_NEED_AXE = ACTION_NAME + " (need axe)";

	Actor performer;
	GameObject target;

	// Default for hostiles
	public ActionChoppingStart(Actor attacker, GameObject vein) {
		super(ACTION_NAME, "action_chop.png");
		this.performer = attacker;
		this.target = vein;
		if (!check()) {
			enabled = false;
		}
		legal = checkLegality();
		sound = createSound();
	}

	@Override
	public void perform() {

		if (!enabled)
			return;

		if (!checkRange())
			return;

		performer.choppingTarget = target;
		target.beingChopped = true;

		Axe axe = (Axe) performer.inventory.getGameObjectOfClass(Axe.class);
		if (performer.equipped != axe)
			performer.equippedBeforePickingUpObject = performer.equipped;
		performer.equipped = axe;

		float damage = target.totalHealth / 4f;
		target.changeHealth(-damage, null, null);
		performer.distanceMovedThisTurn = performer.travelDistance;
		performer.hasAttackedThisTurn = true;

		Actor oreOwner = performer;
		if (target.owner != null)
			oreOwner = target.owner;

		if (Game.level.shouldLog(target, performer))
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " chopped at ", target, " with ", axe }));

		if (Game.level.shouldLog(target, performer)) {
			target.primaryAnimation = new AnimationShake();
		}

		boolean destroyed = target.checkIfDestroyed(performer, this);

		Junk wood = null;
		if (destroyed) {
			wood = Templates.WOOD.makeCopy(target.squareGameObjectIsOn, oreOwner);
			if (Game.level.openInventories.size() > 0) {
			} else if (performer.squareGameObjectIsOn.onScreen() && performer.squareGameObjectIsOn.visibleToPlayer) {
				performer.secondaryAnimations.add(new AnimationTake(wood, performer, 0, 0, 1f));
			}
			performer.inventory.add(wood);
			if (Game.level.shouldLog(target, performer)) {
				Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " received ", wood }));
			}

		} else {

		}

		if (performer == Game.level.player) {
			if (destroyed) {
				Level.pausePlayer();
				target.primaryAnimation = null;
				if (performer.equippedBeforePickingUpObject != null) {
					performer.equipped = performer.equippedBeforePickingUpObject;
					performer.equippedBeforePickingUpObject = null;
				}
			} else {
				Level.levelMode = LevelMode.LEVEL_MODE_CHOPPING;
				Player.playerTargetAction = new ActionChoppingStart(performer, target);
				Player.playerTargetSquare = performer.squareGameObjectIsOn;
				Player.playerFirstMove = true;

			}
		} else {

		}

		target.showPow();

		if (performer.faction == Game.level.factions.player) {
			Game.level.undoList.clear();
		}

		if (performer == Game.level.player && Game.level.activeActor == Game.level.player)
			Game.level.endPlayerTurn();
		performer.actionsPerformedThisTurn.add(this);
		if (sound != null)
			sound.play();

		if (!legal) {
			Crime crime = new Crime(this, this.performer, this.target.owner, Crime.TYPE.CRIME_VANDALISM, wood);
			this.performer.crimesPerformedThisTurn.add(crime);
			this.performer.crimesPerformedInLifetime.add(crime);
			notifyWitnessesOfCrime(crime);
		} else {
			trespassingCheck(this, performer, performer.squareGameObjectIsOn);
		}
	}

	@Override
	public boolean check() {

		if (!performer.inventory.contains(Axe.class)) {
			disabledReason = "You need an axe";
			actionName = ACTION_NAME_NEED_AXE;
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

		if (!performer.canSeeGameObject(target))
			return false;

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
		Pickaxe pickaxe = (Pickaxe) performer.inventory.getGameObjectOfClass(Pickaxe.class);
		if (pickaxe != null) {
			float loudness = Math.max(target.soundWhenHit, pickaxe.soundWhenHitting);
			return new Sound(performer, pickaxe, target.squareGameObjectIsOn, loudness, legal, this.getClass());
		}
		return null;
	}

}
