package com.marklynch.objects.actions;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.effect.EffectBurning;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Templates;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.AggressiveWildAnimal;
import com.marklynch.objects.units.RockGolem;
import com.marklynch.ui.ActivityLog;

public class ActionDie extends Action {

	public static final String ACTION_NAME = "Die";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";
	GameObject performer;
	Square target;

	public ActionDie(GameObject performer, Square target) {
		super(ACTION_NAME, "action_die.png");
		this.performer = performer;
		this.target = target;
		if (!check()) {
			enabled = false;
			actionName = ACTION_NAME_DISABLED;
		}
		legal = checkLegality();
		sound = createSound();

	}

	@Override
	public void perform() {
		if (!enabled)
			return;

		logDeath();
		createCorpse();

		// Remove from draw/update
		if (performer instanceof Actor) {
			if (performer.squareGameObjectIsOn != null)
				performer.squareGameObjectIsOn.inventory.remove(performer);
			else if (performer.inventoryThatHoldsThisObject != null)
				performer.inventoryThatHoldsThisObject.remove(performer);
		}

		// this.faction.actors.remove(this);
		if (performer instanceof Actor) {
			((Actor) performer).actionsPerformedThisTurn.add(this);
		}

		if (sound != null)
			sound.play();
	}

	@Override
	public boolean check() {
		return true;
	}

	@Override
	public boolean checkLegality() {
		return true;
	}

	@Override
	public Sound createSound() {
		return null;
	}

	public void logDeath() {

		if (performer instanceof RockGolem) {

			if (Game.level.shouldLog(performer))
				Game.level.logOnScreen(
						new ActivityLog(new Object[] { performer.destroyedBy, " broke ", performer, this.image }));

		} else if (performer instanceof Actor && performer.destroyedBy instanceof EffectBurning) {

			if (Game.level.shouldLog(performer))
				Game.level.logOnScreen(new ActivityLog(new Object[] { performer, "burned to death", this.image }));
		} else if (performer.destroyedBy instanceof EffectBurning) {

			if (Game.level.shouldLog(performer))
				Game.level.logOnScreen(new ActivityLog(new Object[] { performer, "burned down", this.image }));
		} else if (performer instanceof Actor) {

			if (Game.level.shouldLog(performer))
				Game.level.logOnScreen(
						new ActivityLog(new Object[] { performer.destroyedBy, " killed ", performer, this.image }));

		} else {

			if (Game.level.shouldLog(performer))
				Game.level.logOnScreen(new ActivityLog(
						new Object[] { performer.destroyedBy, " destroyed a ", performer, this.image }));

		}
	}

	public void createCorpse() {

		if (performer instanceof RockGolem)

		{
			Templates.ORE.makeCopy(performer.squareGameObjectIsOn, null);
			Templates.ORE.makeCopy(performer.squareGameObjectIsOn, null);
			Templates.ROCK.makeCopy(performer.squareGameObjectIsOn, null);
			Templates.ROCK.makeCopy(performer.squareGameObjectIsOn, null);
			Templates.ROCK.makeCopy(performer.squareGameObjectIsOn, null);
		} else {
			// add a carcass

			// Death by fire
			GameObject body;
			if (performer.destroyedBy instanceof EffectBurning) {
				body = Templates.ASH.makeCopy(performer.squareGameObjectIsOn, null);
			} else if (performer.destroyedByAction instanceof ActionSquash) {
				body = Templates.BLOODY_PULP.makeCopy(performer.squareGameObjectIsOn, null);
				body.name = "Former " + performer.name;
				body.weight = performer.weight;
			} else if (performer instanceof AggressiveWildAnimal) {
				Templates.BLOOD.makeCopy(performer.squareGameObjectIsOn, null);
				body = Templates.CARCASS.makeCopy(performer.name + " carcass", performer.squareGameObjectIsOn, null,
						performer.weight);
			} else {
				Templates.BLOOD.makeCopy(performer.squareGameObjectIsOn, null);
				body = Templates.CORPSE.makeCopy(performer.name + " corpse", performer.squareGameObjectIsOn, null,
						performer.weight);
			}
			ArrayList<GameObject> gameObjectsInInventory = (ArrayList<GameObject>) performer.inventory.getGameObjects()
					.clone();
			for (GameObject gameObjectInInventory : gameObjectsInInventory) {
				performer.inventory.remove(gameObjectInInventory);
				body.inventory.add(gameObjectInInventory);
				gameObjectInInventory.owner = null;
			}
		}
	}

}
