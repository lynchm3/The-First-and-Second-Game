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
		System.out.println("ActionPourSpecificItem");

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

		System.out.println("Pouritem.perform 1");

		if (!enabled)
			return;
		System.out.println("Pouritem.perform 2");

		if (!checkRange())
			return;
		System.out.println("Pouritem.perform 3");

		if (Game.level.shouldLog(target, performer)) {
			if (target != null) {
				Game.level.logOnScreen(
						new ActivityLog(new Object[] { performer, " poured ", containerForLiquids, " on ", target }));
			} else {
				Game.level
						.logOnScreen(new ActivityLog(new Object[] { performer, " poured out ", containerForLiquids }));

			}
		}
		System.out.println("Pouritem.perform 4");

		for (GameObject gameObject : this.targetSquare.inventory.getGameObjects()) {
			// new ActionDouse(shooter, gameObject).perform();
			for (Effect effect : containerForLiquids.liquid.touchEffects) {
				gameObject.addEffect(effect.makeCopy(performer, gameObject));
			}
		}
		System.out.println("Pouritem.perform 5");

		GameObject newJar = Templates.JAR.makeCopy(null, containerForLiquids.owner);
		performer.inventory.add(newJar);
		if (performer.equipped == containerForLiquids)
			performer.equipped = newJar;
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

			if (target instanceof Actor)
				victim = (Actor) target;
			else if (target != null)
				victim = target.owner;
			if (victim != null) {
				Crime crime = new Crime(this.performer, victim, Crime.TYPE.CRIME_DOUSE);
				this.performer.crimesPerformedThisTurn.add(crime);
				this.performer.crimesPerformedInLifetime.add(crime);
				notifyWitnessesOfCrime(crime);
			}
		} else {
			trespassingCheck(this, performer, performer.squareGameObjectIsOn);
		}
		System.out.println("Pouritem.perform 6");
	}

	@Override
	public boolean check() {

		if (targetSquare == null && target == null) {
			System.out.println("ActionPourSpecificItem cehck false 1");
			System.out.println("targetSquare = " + targetSquare);
			System.out.println("target = " + target);
			return false;
		}

		if (containerForLiquids.liquid == null) {
			disabledReason = CONTAINER_IS_EMPTY;
			System.out.println("ActionPourSpecificItem cehck false 2");
			return false;
		}

		System.out.println("ActionPourSpecificItem cehck true 1");
		return true;
	}

	@Override
	public boolean checkRange() {

		if (performer.straightLineDistanceTo(targetSquare) > 1) {
			System.out.println("ActionPourSpecificItem cehck range false 1");
			return false;
		}

		if (!performer.canSeeSquare(targetSquare)) {
			System.out.println("ActionPourSpecificItem cehck range false 2");
			return false;
		}

		System.out.println("ActionPourSpecificItem cehck range true 1");
		return true;
	}

	@Override
	public boolean checkLegality() {
		// Empty square, it's fine
		if (target == null)
			return true;

		// Something that belongs to some one else
		if (target.owner != null && target.owner != performer) {
			illegalReason = VANDALISM;
			return false;
		}

		// Is human
		if (target instanceof Actor)
			if (!(target instanceof Monster) && !(target instanceof AggressiveWildAnimal)) {
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
