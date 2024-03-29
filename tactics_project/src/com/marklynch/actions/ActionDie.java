package com.marklynch.actions;

import java.util.concurrent.CopyOnWriteArrayList;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.animation.Animation.OnCompletionListener;
import com.marklynch.level.constructs.animation.primary.AnimationDie;
import com.marklynch.level.constructs.effect.EffectBurn;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.actors.RockGolem;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.inanimateobjects.Liquid;
import com.marklynch.objects.inanimateobjects.Mirror;
import com.marklynch.objects.inanimateobjects.Stump;
import com.marklynch.objects.inanimateobjects.Tree;
import com.marklynch.objects.inanimateobjects.Vein;
import com.marklynch.objects.inanimateobjects.Wall;
import com.marklynch.objects.templates.Templates;
import com.marklynch.ui.ActivityLog;

public class ActionDie extends Action {

	public static final String ACTION_NAME = "Die";

	public ActionDie(GameObject performer, Square targetSquare) {
		super(ACTION_NAME, textureDie, performer, targetSquare);
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

		logDeath();
		createCorpse();
		if (gameObjectPerformer != null && targetSquare.visibleToPlayer && gameObjectPerformer instanceof Actor)
			Game.level.player.addXP(gameObjectPerformer.level, gameObjectPerformer.squareGameObjectIsOn);

		if (gameObjectPerformer.squareGameObjectIsOn != null && !(gameObjectPerformer instanceof Actor)) {
			gameObjectPerformer.squareGameObjectIsOn.inventory.remove(gameObjectPerformer);
		}

		if (gameObjectPerformer.deathListener != null)
			gameObjectPerformer.deathListener.thisThingDied(gameObjectPerformer);

		if (sound != null)
			sound.play();
	}

	@Override
	public boolean check() {
		return true;
	}

	@Override
	public boolean checkRange() {
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

		if (!Game.level.shouldLog(gameObjectPerformer))
			return;

		if (gameObjectPerformer instanceof RockGolem) {

			Game.level.logOnScreen(new ActivityLog(
					new Object[] { gameObjectPerformer.destroyedBy, " broke ", gameObjectPerformer, this.image }));

		} else if (gameObjectPerformer instanceof Actor && gameObjectPerformer.destroyedBy instanceof EffectBurn) {

			Game.level.logOnScreen(
					new ActivityLog(new Object[] { gameObjectPerformer, " burned to death ", this.image }));
		} else if (gameObjectPerformer instanceof Vein) {

			Game.level.logOnScreen(new ActivityLog(new Object[] { gameObjectPerformer, " was depleted ", this.image }));
		} else if (gameObjectPerformer.destroyedByAction instanceof ActionSmash) {

			Game.level.logOnScreen(new ActivityLog(new Object[] { gameObjectPerformer, " smashed ", this.image }));
		} else if (gameObjectPerformer instanceof Tree
				&& gameObjectPerformer.destroyedByAction instanceof ActionChopping) {

			Game.level.logOnScreen(
					new ActivityLog(new Object[] { gameObjectPerformer, " was chopped down ", this.image }));
		} else if (gameObjectPerformer.destroyedBy instanceof EffectBurn) {

			Game.level.logOnScreen(new ActivityLog(new Object[] { gameObjectPerformer, " burned down ", this.image }));
		} else if (gameObjectPerformer instanceof Actor) {

			Game.level.logOnScreen(new ActivityLog(
					new Object[] { gameObjectPerformer.destroyedBy, " killed ", gameObjectPerformer, this.image }));

		} else if (gameObjectPerformer.diggable == true) {
		} else if (gameObjectPerformer instanceof Liquid) {

			Game.level.logOnScreen(new ActivityLog(new Object[] { gameObjectPerformer.destroyedBy, " evaporated a ",
					gameObjectPerformer, this.image }));

		} else {

			if (Game.level.shouldLog(gameObjectPerformer))
				Game.level.logOnScreen(new ActivityLog(new Object[] { gameObjectPerformer.destroyedBy, " destroyed a ",
						gameObjectPerformer, this.image }));

		}
	}

	private void poolBlood() {

		// Actor actor = (Actor) gameObjectPerformer;
		Liquid blood = gameObjectPerformer.squareGameObjectIsOn.liquidSpread(Templates.BLOOD);

	}

	public void createCorpse() {

		// if(gameObjectPerformer.squareGameObjectIsOn == null)
		// return;

		if (gameObjectPerformer instanceof Actor) {

			Actor actor = (Actor) gameObjectPerformer;
			if (gameObjectPerformer.equipped != null && gameObjectPerformer != Level.player)
				new ActionDropItems(gameObjectPerformer, gameObjectPerformer.squareGameObjectIsOn,
						gameObjectPerformer.equipped).perform();
			actor.setPrimaryAnimation(new AnimationDie(gameObjectPerformer, new OnCompletionListener() {
				@Override
				public void animationComplete(GameObject gameObject) {
					poolBlood();

				}
			}));

			// GameObject body = null;
			// if (gameObjectPerformer instanceof RockGolem) {
			// Templates.ORE.makeCopy(gameObjectPerformer.squareGameObjectIsOn, null);
			// Templates.ORE.makeCopy(gameObjectPerformer.squareGameObjectIsOn, null);
			// Templates.ROCK.makeCopy(gameObjectPerformer.squareGameObjectIsOn, null);
			// Templates.ROCK.makeCopy(gameObjectPerformer.squareGameObjectIsOn, null);
			// Templates.ROCK.makeCopy(gameObjectPerformer.squareGameObjectIsOn, null);
			// } else if (gameObjectPerformer.destroyedBy instanceof EffectBurning) {
			// // Death by fire
			// Templates.ASH.makeCopy(gameObjectPerformer.squareGameObjectIsOn, null);
			// for (GameObject gameObject : (CopyOnWriteArrayList<GameObject>)
			// gameObjectPerformer.inventory.gameObjects) {
			// new ActionDropItems(gameObjectPerformer,
			// gameObjectPerformer.squareGameObjectIsOn, gameObject).perform();
			// }
			// } else if (gameObjectPerformer.destroyedByAction instanceof ActionSquash) {
			// // Deat by squashing
			// body =
			// Templates.BLOODY_PULP.makeCopy(gameObjectPerformer.squareGameObjectIsOn,
			// null);
			// body.name = "Former " + gameObjectPerformer.name;
			// body.weight = gameObjectPerformer.weight;
			// } else if (gameObjectPerformer instanceof AggressiveWildAnimal ||
			// gameObjectPerformer instanceof HerbivoreWildAnimal) {
			// // Dead animal
			// Templates.BLOOD.makeCopy(gameObjectPerformer.squareGameObjectIsOn, null);
			// body = Templates.CARCASS.makeCopy(gameObjectPerformer.name + " carcass",
			// gameObjectPerformer.squareGameObjectIsOn, null,
			// gameObjectPerformer.weight);
			// } else {
			// Templates.BLOOD.makeCopy(gameObjectPerformer.squareGameObjectIsOn, null);
			// body = Templates.CORPSE.makeCopy(gameObjectPerformer.name + " corpse",
			// gameObjectPerformer.squareGameObjectIsOn, null,
			// gameObjectPerformer.weight);
			// }
			// if (body != null) {
			// CopyOnWriteArrayList<GameObject> gameObjectsInInventory =
			// (CopyOnWriteArrayList<GameObject>)
			// gameObjectPerformer.inventory
			// .getGameObjects();
			// for (GameObject gameObjectInInventory : gameObjectsInInventory) {
			// gameObjectPerformer.inventory.remove(gameObjectInInventory);
			// body.inventory.add(gameObjectInInventory);
			// gameObjectInInventory.owner = null;
			// }
			// }
		} else {
			// GameObjects
			if (gameObjectPerformer.destroyedBy instanceof EffectBurn) {
				// Death by fire
				Templates.ASH.makeCopy(gameObjectPerformer.squareGameObjectIsOn, null);
				for (GameObject gameObject : (CopyOnWriteArrayList<GameObject>) gameObjectPerformer.inventory.gameObjects) {
					new ActionDropItems(gameObjectPerformer, gameObjectPerformer.squareGameObjectIsOn, gameObject)
							.perform();
				}
			} else if (gameObjectPerformer.templateId == Templates.CRATE.templateId) {
				// Death by fire
				Templates.WOOD_CHIPS.makeCopy(gameObjectPerformer.squareGameObjectIsOn, null);
				for (GameObject gameObject : (CopyOnWriteArrayList<GameObject>) gameObjectPerformer.inventory.gameObjects) {
					new ActionDropItems(gameObjectPerformer, gameObjectPerformer.squareGameObjectIsOn, gameObject)
							.perform();
				}
			} else if ((gameObjectPerformer instanceof Tree
					|| gameObjectPerformer.templateId == Templates.TREE_CONTAINER.templateId
					|| gameObjectPerformer.templateId == Templates.TREE_READABLE.templateId)
					&& gameObjectPerformer.destroyedByAction instanceof ActionChopping) {
				Templates.STUMP.makeCopy(gameObjectPerformer.squareGameObjectIsOn, null);
			} else if (gameObjectPerformer instanceof Stump) {
				if (gameObjectPerformer.squareGameObjectIsOn != null) {
					gameObjectPerformer.squareGameObjectIsOn.setFloorImageTexture(Square.MUD_TEXTURE);
				}
			} else if (gameObjectPerformer instanceof Mirror) {
				Templates.BROKEN_GLASS.makeCopy(gameObjectPerformer.squareGameObjectIsOn, null);
			} else if (gameObjectPerformer instanceof Wall) {
				Templates.RUBBLE.makeCopy(gameObjectPerformer.squareGameObjectIsOn, null);
				gameObjectPerformer.squareGameObjectIsOn.setFloorImageTexture(Square.STONE_TEXTURE);

				gameObjectPerformer.showPow(GameObject.dustCloudTexture, 200, 200);
			}

			for (GameObject gameObject : gameObjectPerformer.inventory.gameObjects) {
				gameObjectPerformer.squareGameObjectIsOn.inventory.add(gameObject);
			}

		}
	}
}
