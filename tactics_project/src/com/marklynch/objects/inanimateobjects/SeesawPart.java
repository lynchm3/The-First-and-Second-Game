package com.marklynch.objects.inanimateobjects;

import com.marklynch.Game;
import com.marklynch.ai.utils.AILine;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.animation.Animation.OnCompletionListener;
import com.marklynch.level.constructs.animation.primary.AnimationFall;
import com.marklynch.level.constructs.animation.primary.AnimationFallFromTheSky;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.utils.SwitchListener;
import com.marklynch.utils.CopyOnWriteArrayList;

public class SeesawPart extends PressurePlate {

	public int weightOnPlate = 0;
	public boolean up;
	public Square connectedSquare = null;

	public SeesawPart makeCopy(Square square, Actor owner, SWITCH_TYPE switchType, int targetWeight,
			Square connectedSquare, SwitchListener... switchListeners) {

		SeesawPart seesawPart = new SeesawPart();
		seesawPart.connectedSquare = connectedSquare;
		seesawPart.switchListeners = switchListeners;
		for (SwitchListener switchListener : switchListeners) {

			if (switchListener instanceof GameObject) {
				GameObject switchListenerGameObject = (GameObject) switchListener;
				seesawPart.linkedObjects.add(switchListenerGameObject);
				switchListenerGameObject.linkedObjects.add(seesawPart);
			}
		}
		setInstances(seesawPart);
		super.setAttributesForCopy(seesawPart, square, owner);
		seesawPart.actionName = actionName;
		seesawPart.actionVerb = actionVerb;
		seesawPart.switchType = switchType;
		seesawPart.targetWeight = targetWeight;

		for (SwitchListener switchListener : switchListeners) {
			if (switchListener != null && switchListener instanceof GameObject)
				this.aiLine = new AILine(AILine.AILineType.AI_LINE_TYPE_SWITCH, seesawPart,
						((GameObject) switchListener).squareGameObjectIsOn);
		}

		return seesawPart;
	}

	@Override
	public void squareContentsChanged() {

		if (squareGameObjectIsOn == null)
			return;
		doTheThing(null);

	}

	@Override
	public void doTheThing(final GameObject g) {
		if (squareGameObjectIsOn == null)
			return;
		weightOnPlate = 0;
		for (GameObject gameObject : squareGameObjectIsOn.inventory.gameObjects) {
			if (gameObject.isFloorObject == false) {
				weightOnPlate += gameObject.weight;
			}
		}
		use();

		if (up)
			return;

		for (final GameObject gameObject : (CopyOnWriteArrayList<GameObject>) squareGameObjectIsOn.inventory.gameObjects) {
			if (gameObject.isFloorObject == false) {
				gameObject.setPrimaryAnimation(new AnimationFall(gameObject, 1f, 0f, 400, null));

				gameObject.getPrimaryAnimation().onCompletionListener = new OnCompletionListener() {
					@Override
					public void animationComplete(GameObject gameObject) {
						Square square = gameObject.lastSquare;
						if (square == null)
							square = connectedSquare;

						square.inventory.add(gameObject);

						if (gameObject == Level.player) {
							// Game.ca
							Game.level.centerToSquare = true;
							Game.level.squareToCenterTo = square;
						} else {
							Level.gameObjectsToFlash.add(gameObject);
							Level.flashGameObjectCounters.put(gameObject, 0);
						}
						// squareGameObjectIsOn.inventory.remove(gameObject);
						gameObject.setPrimaryAnimation(
								new AnimationFallFromTheSky(gameObject, Game.MINIMUM_TURN_TIME, null));
					}
				};

			}
		}

	}
}