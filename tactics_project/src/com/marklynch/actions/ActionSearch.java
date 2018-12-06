package com.marklynch.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.inanimateobjects.Searchable;
import com.marklynch.ui.ActivityLog;
import com.marklynch.utils.ArrayList;

public class ActionSearch extends Action {

	public static final String ACTION_NAME = "Search";

	Searchable searchable;

	public ActionSearch(Actor performer, Searchable target) {
		super(ACTION_NAME, textureSearch, performer, target);
		this.searchable = target;
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

		if (Game.level.shouldLog(target, performer))
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " searched ", target }));

		ArrayList<GameObject> gameObjectsToLoot = searchable.search();
		for (GameObject gameObjectToLoot : gameObjectsToLoot) {

			if (Game.level.shouldLog(gameObjectToLoot, performer))
				Game.level.logOnScreen(
						new ActivityLog(new Object[] { performer, " found ", gameObjectToLoot, " in ", target }));
			target.inventory.remove(gameObjectToLoot);
			performer.inventory.add(gameObjectToLoot);
			if (gameObjectToLoot.owner == null)
				gameObjectToLoot.owner = performer;
		}

		if (gameObjectsToLoot.size() == 0)
			if (performer.squareGameObjectIsOn.visibleToPlayer)
				Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " found nothing" }));

		for (Effect effect : searchable.effectsFromInteracting) {
			performer.addEffect(effect.makeCopy(target, performer));
		}

		if (sound != null)
			sound.play();

		if (!legal) {

			ArrayList<GameObject> stolenObjects = new ArrayList<GameObject>(GameObject.class);
			for (GameObject gameObjectToLoot : gameObjectsToLoot) {
				if (gameObjectToLoot.owner != null && gameObjectToLoot.owner != performer) {
					stolenObjects.add(gameObjectToLoot);
				}
			}
			if (stolenObjects.size() > 0) {
				Crime crime = new Crime(this, this.performer, stolenObjects.get(0).owner, Crime.TYPE.CRIME_THEFT,
						stolenObjects);
				this.performer.crimesPerformedThisTurn.add(crime);
				this.performer.crimesPerformedInLifetime.add(crime);
				notifyWitnessesOfCrime(crime);
			}
		}
	}

	@Override
	public boolean check() {
		return true;
	}

	@Override
	public boolean checkRange() {
		if (performer.straightLineDistanceTo(target.squareGameObjectIsOn) < 2) {
			return true;
		}
		return false;
	}

	@Override
	public boolean checkLegality() {
		if (target.owner != null && target.owner != performer) {
			illegalReason = THEFT;
			return false;
		}
		return true;
	}

	@Override
	public Sound createSound() {
		return null;
	}

}