package com.marklynch.actions;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.Door;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.inanimateobjects.Openable;
import com.marklynch.ui.ActivityLog;

public class ActionClose extends Action {

	public static final String ACTION_NAME = "Close";

	Openable openable;

	// Default for hostiles
	public ActionClose(GameObject opener, Openable target) {
		super(ACTION_NAME, textureClose, opener, target);
		super.gameObjectPerformer = this.gameObjectPerformer = opener;
		this.openable = target;
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

		// Key key = closer.getKeyFor(door);

		openable.close();

		if (Game.level.shouldLog(openable, gameObjectPerformer))
			Game.level.logOnScreen(new ActivityLog(new Object[] { gameObjectPerformer, " closed ", openable }));

		openable.showPow();

		if (gameObjectPerformer instanceof Actor) {
			Actor actor = (Actor) gameObjectPerformer;
			if (actor.faction == Game.level.factions.player) {
				Game.level.undoList.clear();
			}

			trespassingCheck(actor, actor.squareGameObjectIsOn);
		}
		if (sound != null)
			sound.play();

		if (performer == Level.player)
			Level.player.calculateVisibleAndCastableSquares(Level.player.squareGameObjectIsOn);
	}

	@Override
	public boolean check() {

		if (gameObjectPerformer instanceof Actor) {
			Actor actor = (Actor) gameObjectPerformer;
			if (openable.isLocked() && !actor.hasKeyForDoor(openable)) {
				disabledReason = NEED_A_KEY;
				return false;
			}
		}

		if (openable instanceof Door && openable.squareGameObjectIsOn.inventory.canShareSquare == false) {
			disabledReason = DOORWAY_BLOCKED;
		}

		if (openable instanceof Door && openable.squareGameObjectIsOn.inventory.containsGameObjectOfType(Actor.class)) {
			disabledReason = DOORWAY_BLOCKED;
			return false;
		}

		return true;
	}

	@Override
	public boolean checkRange() {

		if (gameObjectPerformer instanceof Actor) {
			Actor actor = (Actor) gameObjectPerformer;
			if (!actor.canSeeGameObject(openable)) {
				return false;
			}

			if (gameObjectPerformer.straightLineDistanceTo(openable.squareGameObjectIsOn) != 1) {
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
		return new Sound(gameObjectPerformer, openable, openable.squareGameObjectIsOn, 1, legal, this.getClass());
	}

}
