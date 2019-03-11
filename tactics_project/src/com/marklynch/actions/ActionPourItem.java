package com.marklynch.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.actors.AggressiveWildAnimal;
import com.marklynch.objects.actors.Monster;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.templates.Templates;
import com.marklynch.objects.tools.ContainerForLiquids;
import com.marklynch.ui.ActivityLog;

public class ActionPourItem extends Action {

	public static final String ACTION_NAME = "Pour";
	ContainerForLiquids containerForLiquids;

	// Default for hostiles
	public ActionPourItem(GameObject performer, Object target, ContainerForLiquids container) {
		super(ACTION_NAME, texturePour, performer, target);

		this.containerForLiquids = container;
		if (!check()) {
			enabled = false;
		} else {
			actionName = ACTION_NAME + " " + container.name;
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

		if (Game.level.shouldLog(targetGameObject, performer)) {
			if (targetGameObject != null) {
				Game.level.logOnScreen(new ActivityLog(
						new Object[] { performer, " poured ", containerForLiquids, " on ", targetGameObject }));
			} else {
				Game.level
						.logOnScreen(new ActivityLog(new Object[] { performer, " poured out ", containerForLiquids }));

			}
		}

		for (GameObject gameObject : this.targetSquare.inventory.getGameObjects()) {
			// new ActionDouse(shooter, gameObject).perform();
			for (Effect effect : containerForLiquids.liquid.touchEffects) {
				gameObject.addEffect(effect.makeCopy(performer, gameObject));
			}
		}

		targetSquare.liquidSpread(containerForLiquids.liquid);

		GameObject newJar = Templates.JAR.makeCopy(null, containerForLiquids.owner);
		performer.inventory.add(newJar);
		if (performer.equipped == containerForLiquids) {
			if (performer.inventory.contains(performer.equippedBeforePickingUpObject)) {
				performer.equip(performer.equippedBeforePickingUpObject);
			} else if (performer.inventory.containsDuplicateOf(containerForLiquids)) {
				performer.equip(performer.inventory.getDuplicateOf(containerForLiquids));
			} else {
				performer.equip(newJar);
			}
			performer.equippedBeforePickingUpObject = null;
		}

//		if (performer.equipped == containerForLiquids)
//			performer.equipped = newJar;
		performer.inventory.remove(containerForLiquids);

		if (Game.level.openInventories.size() > 0)
			Game.level.openInventories.get(0).close();

		performer.distanceMovedThisTurn = performer.travelDistance;

		if (performer.faction == Game.level.factions.player) {
			Game.level.undoList.clear();
		}

		if (performer == Game.level.player)
			Game.level.endPlayerTurn();

		if (sound != null)
			sound.play();

		if (!legal) {

			Actor victim = null;

			if (targetGameObject instanceof Actor)
				victim = (Actor) targetGameObject;
			else if (targetGameObject != null)
				victim = targetGameObject.owner;
			if (victim != null) {
				Crime crime = new Crime(this.performer, victim, Crime.TYPE.CRIME_DOUSE);
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

		if (targetSquare == null && targetGameObject == null) {
			return false;
		}

		if (containerForLiquids.liquid == null) {
			disabledReason = CONTAINER_IS_EMPTY;
			return false;
		}

		return true;
	}

	@Override
	public boolean checkRange() {

		if (performer.straightLineDistanceTo(targetSquare) > 1) {
			return false;
		}

		if (!performer.canSeeSquare(targetSquare)) {
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
		if (targetGameObject.owner != null && targetGameObject.owner != performer) {
			illegalReason = VANDALISM;
			return false;
		}

		// Is human
		if (targetGameObject instanceof Actor)
			if (!(targetGameObject instanceof Monster) && !(targetGameObject instanceof AggressiveWildAnimal)) {
				illegalReason = ASSAULT;
				return false;
			}

		return true;
	}

	@Override
	public Sound createSound() {

		// Sound
		float loudness = 3;
		return new Sound(performer, containerForLiquids, targetSquare, loudness, legal, this.getClass());
	}

}
