package com.marklynch.actions;

import java.util.concurrent.CopyOnWriteArrayList;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.animation.Animation.OnCompletionListener;
import com.marklynch.level.constructs.animation.primary.AnimationEatDrink;
import com.marklynch.level.constructs.animation.secondary.AnimationTake;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.inanimateobjects.Liquid;
import com.marklynch.objects.inanimateobjects.WaterBody;
import com.marklynch.objects.templates.Templates;
import com.marklynch.objects.tools.Jar;
import com.marklynch.ui.ActivityLog;

public class ActionEatDrinkItems extends VariableQtyAction {

	public static final String ACTION_NAME = "Eat";
	public static final String ACTION_NAME_DRINK = "Drink";

	GameObject[] targets;
	private GameObject previouslyEquipped;
	int amountToEat;

	public ActionEatDrinkItems(Actor performer, CopyOnWriteArrayList<GameObject> objects) {
		this(performer, objects.toArray(new GameObject[objects.size()]), false);
	}

	public ActionEatDrinkItems(Actor performer, GameObject... objects) {
		this(performer, objects, false);
	}

	public ActionEatDrinkItems(Actor performer, GameObject[] objects, boolean doesNothing) {
		super(ACTION_NAME, textureEat, performer, null);
		if (objects != null && objects.length > 0
				&& (objects[0] instanceof Liquid || objects[0] instanceof Jar || objects[0] instanceof WaterBody)) {
			this.actionName = ACTION_NAME_DRINK;
			this.image = textureDrink;
		}
		this.targets = objects;
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

		amountToEat = Math.min(targets.length, qty);

		if (amountToEat == 0)
			return;

		if (targets[0].inventoryThatHoldsThisObject.parent instanceof Square && !(targets[0] instanceof WaterBody)) {
			Level.addSecondaryAnimation(
					new AnimationTake(targets[0], performer, performer.getHandXY().x - targets[0].anchorX,
							performer.getHandXY().y - targets[0].anchorY, 1f, new OnCompletionListener() {
								@Override
								public void animationComplete(GameObject gameObject) {
									postPickupAnimation();
								}
							}));
			performer.inventory.add(targets[0]);
		} else {
			postPickupAnimation();
		}
	}

	private void postPickupAnimation() {

//		if (targets[0].inventoryThatHoldsThisObject.parent instanceof Square) {
//			performer.inventory.add(targets[0]);
//		}

		if (performer.equipped != targets[0])
			previouslyEquipped = performer.equipped;
		performer.equip(targets[0]);

		gameObjectPerformer.setPrimaryAnimation(new AnimationEatDrink(gameObjectPerformer,
				gameObjectPerformer.getPrimaryAnimation(), new OnCompletionListener() {
					@Override
					public void animationComplete(GameObject gameObject) {
						postAnimation();
					}
				}));
	}

	private void postAnimation() {

		// Logging
		if (Game.level.shouldLog(performer)) {
			String amountText = "";
			if (amountToEat > 1) {
				amountText = "x" + amountToEat;
			}

			String actionWord = " ate ";
			if (actionName == ACTION_NAME_DRINK) {
				actionWord = " drank ";
			}
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, actionWord, targets[0], amountText }));
		}

		for (int i = 0; i < amountToEat; i++) {
			GameObject object = targets[i];
			GameObject replacement = null;
			// Management of objects
			if (object instanceof WaterBody) {

			} else {

				if (object instanceof Jar) {
					replacement = Templates.JAR.makeCopy(null, object.owner);
				} else if (object.templateId == Templates.APPLE.templateId) {
					replacement = Templates.APPLE_CORE.makeCopy(null, object.owner);
				}

			}

			object.inventoryThatHoldsThisObject.remove(object);

			System.out.println("previouslyEquipped = " + previouslyEquipped);
			System.out.println("performer.equippedBeforePickingUpObject = " + performer.equippedBeforePickingUpObject);
			System.out.println("replacement = " + replacement);

			// Put stuff in actor's hand

			if (replacement != null)
				performer.inventory.add(replacement);

			if (previouslyEquipped != null)
				performer.equip(previouslyEquipped);
			else if (performer.inventory.contains(performer.equippedBeforePickingUpObject)) {
				performer.equip(performer.equippedBeforePickingUpObject);
			} else if (performer.inventory.containsDuplicateOf(targets[0])) {
				performer.equip(performer.inventory.getDuplicateOf(targets[0]));
			} else if (replacement != null) {
				performer.equip(replacement);
			} else {
				performer.equip(null);
			}

			performer.equippedBeforePickingUpObject = null;

			if (object.getConsumeEffects() != null) {
				for (Effect effect : object.getConsumeEffects()) {
					performer.addEffect(effect.makeCopy(performer, performer));
				}
			}

			if (sound != null)
				sound.play();

			if (!legal) {
				Crime crime = new Crime(this.performer, object.owner, Crime.TYPE.CRIME_THEFT, object);
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
		if (performer.straightLineDistanceTo(targets[0].squareGameObjectIsOn) < 2) {
			return true;
		}
		if (performer.inventory == targets[0].inventoryThatHoldsThisObject)
			return true;
		return false;
	}

	@Override
	public boolean checkLegality() {
		return standardAttackLegalityCheck(performer, targets[0]);
	}

	@Override
	public Sound createSound() {
		return null;
	}

}