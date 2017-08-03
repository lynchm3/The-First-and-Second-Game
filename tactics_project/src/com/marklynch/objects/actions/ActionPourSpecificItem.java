package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.constructs.effect.EffectWet;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Liquid;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.AggressiveWildAnimal;
import com.marklynch.objects.units.Monster;
import com.marklynch.ui.ActivityLog;

public class ActionPourSpecificItem extends Action {

	public static final String ACTION_NAME = "Pour";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";

	Actor performer;
	Square targetSquare;
	GameObject targetGameObject;
	GameObject container;

	// Default for hostiles
	public ActionPourSpecificItem(Actor performer, Object target, GameObject object) {
		super(ACTION_NAME, "action_pour.png");
		this.performer = performer;
		if (target instanceof Square) {
			targetSquare = (Square) target;
			targetGameObject = targetSquare.inventory.getGameObjectThatCantShareSquare();
		} else if (target instanceof GameObject) {
			targetGameObject = (GameObject) target;
			targetSquare = targetGameObject.squareGameObjectIsOn;
		}
		this.container = object;
		if (!check()) {
			enabled = false;
		} else {
			actionName = ACTION_NAME + " " + object.name;
		}
		legal = checkLegality();
		sound = createSound();
	}

	@Override
	public void perform() {

		if (!enabled)
			return;

		if (Game.level.shouldLog(targetGameObject, performer)) {
			if (targetGameObject != null) {
				Game.level.logOnScreen(
						new ActivityLog(new Object[] { performer, " poured ", container, " on ", targetGameObject }));
			} else {
				Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " poured out ", container }));

			}
		}

		if (container.inventory.size() > 0 && container.inventory.get(0) instanceof Liquid) {
			Liquid liquid = (Liquid) this.container.inventory.get(0);
			for (GameObject gameObject : this.targetSquare.inventory.getGameObjects()) {
				// new ActionDouse(shooter, gameObject).perform();
				for (Effect effect : liquid.touchEffects) {
					gameObject.addEffect(effect.makeCopy(performer, gameObject));
					if (effect instanceof EffectWet)
						gameObject.removeBurningEffect();
				}
			}
			container.inventory.remove(container.inventory.get(0));
		}

		Game.level.openInventories.clear();

		performer.distanceMovedThisTurn = performer.travelDistance;

		if (performer.faction == Game.level.factions.get(0)) {
			Game.level.undoList.clear();
		}

		if (performer == Game.level.player)
			Game.level.endTurn();

		performer.actionsPerformedThisTurn.add(this);
		if (sound != null)
			sound.play();

		if (!legal) {

			Actor victim = null;

			if (targetGameObject instanceof Actor)
				victim = (Actor) targetGameObject;
			else if (targetGameObject != null)
				victim = targetGameObject.owner;
			if (victim != null) {
				Crime crime = new Crime(this, this.performer, victim, 1);
				this.performer.crimesPerformedThisTurn.add(crime);
				this.performer.crimesPerformedInLifetime.add(crime);
				notifyWitnessesOfCrime(crime);
			}
		} else {
			trespassingCheck(this, performer, performer.squareGameObjectIsOn);
		}
	}

	@Override
	public boolean check() {

		if (container.inventory.size() != 0)
			actionName = ACTION_NAME + " " + container.name + " (empty)";

		if (performer.straightLineDistanceTo(targetSquare) > 1) {
			actionName = ACTION_NAME + " " + container.name + " (can't reach)";
			return false;
		}

		if (!performer.canSeeSquare(targetSquare)) {
			actionName = ACTION_NAME + " " + container.name + " (can't reach)";
			return false;
		}

		return true;
	}

	@Override
	public boolean checkLegality() {
		// Empty square, it's fine
		if (targetGameObject == null)
			return true;

		// Something that belongs to some one else
		if (targetGameObject.owner != null && targetGameObject.owner != performer)
			return false;

		// Is human
		if (targetGameObject instanceof Actor)
			if (!(targetGameObject instanceof Monster) && !(targetGameObject instanceof AggressiveWildAnimal))
				return false;

		return true;
	}

	@Override
	public Sound createSound() {

		// Sound
		if (targetGameObject != null) {
			float loudness = targetGameObject.soundWhenHit * container.soundWhenHitting;
			if (performer.equipped != null)
				return new Sound(performer, container, targetSquare, loudness, legal, this.getClass());
		} else {
			float loudness = container.soundWhenHitting;
			if (performer.equipped != null)
				return new Sound(performer, container, targetSquare, loudness, legal, this.getClass());

		}
		return null;
	}

}
