package com.marklynch.objects.actions;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.effect.EffectBurning;
import com.marklynch.objects.Discoverable;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.InanimateObjectToAddOrRemove;
import com.marklynch.objects.Tree;
import com.marklynch.objects.Vein;
import com.marklynch.objects.templates.Templates;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.AggressiveWildAnimal;
import com.marklynch.objects.units.HerbivoreWildAnimal;
import com.marklynch.objects.units.RockGolem;
import com.marklynch.ui.ActivityLog;

public class ActionDiscover extends Action {

	public static final String ACTION_NAME = "Discover";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";
	GameObject performer;
	Discoverable target;

	public ActionDiscover(GameObject performer, Discoverable target) {
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

		target.discovered();

		if (Game.level.shouldLog(performer))
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " discovered ", target }));

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
				Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " burned to death ", this.image }));
		} else if (performer instanceof Vein) {

			if (Game.level.shouldLog(performer))
				Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " was depleted ", this.image }));
		} else if (performer.destroyedByAction instanceof ActionSmash) {

			if (Game.level.shouldLog(performer))
				Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " smashed ", this.image }));
		} else if (performer instanceof Tree && performer.destroyedByAction instanceof ActionChop) {

			if (Game.level.shouldLog(performer))
				Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " was chopped down ", this.image }));
		} else if (performer.destroyedBy instanceof EffectBurning) {

			if (Game.level.shouldLog(performer))
				Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " burned down ", this.image }));
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

		if (performer instanceof Actor) {
			// add a carcass

			GameObject body = null;
			if (performer instanceof RockGolem) {
				Templates.ORE.makeCopy(performer.squareGameObjectIsOn, null);
				Templates.ORE.makeCopy(performer.squareGameObjectIsOn, null);
				Templates.ROCK.makeCopy(performer.squareGameObjectIsOn, null);
				Templates.ROCK.makeCopy(performer.squareGameObjectIsOn, null);
				Templates.ROCK.makeCopy(performer.squareGameObjectIsOn, null);
			} else if (performer.destroyedBy instanceof EffectBurning) {
				// Death by fire
				Templates.ASH.makeCopy(performer.squareGameObjectIsOn, null);
				for (GameObject gameObject : (ArrayList<GameObject>) performer.inventory.gameObjects.clone()) {
					new ActionDropSpecificItem(performer, performer.squareGameObjectIsOn, gameObject).perform();
				}
			} else if (performer.destroyedByAction instanceof ActionSquash) {
				// Deat by squashing
				body = Templates.BLOODY_PULP.makeCopy(performer.squareGameObjectIsOn, null);
				body.name = "Former " + performer.name;
				body.weight = performer.weight;
			} else if (performer instanceof AggressiveWildAnimal || performer instanceof HerbivoreWildAnimal) {
				// Dead animal
				Templates.BLOOD.makeCopy(performer.squareGameObjectIsOn, null);
				body = Templates.CARCASS.makeCopy(performer.name + " carcass", performer.squareGameObjectIsOn, null,
						performer.weight);
			} else {
				Templates.BLOOD.makeCopy(performer.squareGameObjectIsOn, null);
				body = Templates.CORPSE.makeCopy(performer.name + " corpse", performer.squareGameObjectIsOn, null,
						performer.weight);
			}
			if (body != null) {
				ArrayList<GameObject> gameObjectsInInventory = (ArrayList<GameObject>) performer.inventory
						.getGameObjects().clone();
				for (GameObject gameObjectInInventory : gameObjectsInInventory) {
					performer.inventory.remove(gameObjectInInventory);
					body.inventory.add(gameObjectInInventory);
					gameObjectInInventory.owner = null;
				}
			}
		} else {
			// GameObjects
			if (performer.destroyedBy instanceof EffectBurning) {
				// Death by fire
				Game.level.inanimateObjectsToAdd.add(new InanimateObjectToAddOrRemove(
						Templates.ASH.makeCopy(null, null), performer.squareGameObjectIsOn));
			} else if (performer instanceof Tree && performer.destroyedByAction instanceof ActionChop) {

				if (!performer.name.contains("Big")) {

					Game.level.inanimateObjectsToAdd.add(new InanimateObjectToAddOrRemove(
							Templates.STUMP.makeCopy(null, null), performer.squareGameObjectIsOn));

				} else {

					Game.level.inanimateObjectsToAdd.add(new InanimateObjectToAddOrRemove(
							Templates.BIG_STUMP.makeCopy(null, null), performer.squareGameObjectIsOn));
				}

				// Dead animal
				// Templates.BLOOD.makeCopy(performer.squareGameObjectIsOn,
				// null);
			}

			for (GameObject gameObject : performer.inventory.gameObjects) {
				Game.level.inanimateObjectsToAdd
						.add(new InanimateObjectToAddOrRemove(gameObject, performer.squareGameObjectIsOn));
			}

		}
	}
}