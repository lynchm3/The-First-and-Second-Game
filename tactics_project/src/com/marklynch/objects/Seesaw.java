package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.ai.utils.AILine;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.animation.Animation.OnCompletionListener;
import com.marklynch.level.constructs.animation.primary.AnimationFall;
import com.marklynch.level.constructs.animation.primary.AnimationFallFromTheSky;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.templates.Templates;
import com.marklynch.utils.Color;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.Texture;
import com.marklynch.utils.TextureUtils;

public class Seesaw extends GameObject implements SwitchListener {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();

	public Square square1;
	public Square square2;
	public Seesaw.SeesawPart pressurePlate1;
	public Seesaw.SeesawPart pressurePlate2;

	public static Texture gradientRightUp;
	public static Texture gradientLeftUp;

	public Seesaw() {
		super();
		canBePickedUp = false;
		fitsInInventory = false;
		isFloorObject = true;
		moveable = false;
		attackable = false;
	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void draw2() {
		super.draw2();
		QuadUtils.drawQuad(Color.WHITE, square1.xInGridPixels + Game.SQUARE_WIDTH, square1.getCenterY() - 5,
				square2.xInGridPixels, square2.getCenterY() + 5);

		if (pressurePlate1.up == true && pressurePlate2.up == true) {

		} else {
			int actorPositionXInPixels = (int) (this.squareGameObjectIsOn.xInGridPixels
					+ Game.SQUARE_WIDTH * drawOffsetRatioX);
			int actorPositionYInPixels = (int) (this.squareGameObjectIsOn.yInGridPixels
					+ Game.SQUARE_HEIGHT * drawOffsetRatioY);

			if (pressurePlate1.up) {
				TextureUtils.drawTexture(gradientLeftUp, 1f, actorPositionXInPixels - 256, actorPositionYInPixels,
						actorPositionXInPixels + 384, actorPositionYInPixels + 128);
			} else {
				TextureUtils.drawTexture(gradientRightUp, 1f, actorPositionXInPixels - 256, actorPositionYInPixels,
						actorPositionXInPixels + 384, actorPositionYInPixels + 128);
			}
		}
	}

	public Seesaw makeCopy(Square square, Actor owner, Square square1, Square square2, Square connectedSquare) {

		Seesaw seesaw = new Seesaw();
		setInstances(seesaw);

		super.setAttributesForCopy(seesaw, square, owner);

		seesaw.square1 = square1;
		seesaw.square2 = square2;
		seesaw.pressurePlate1 = Templates.SEESAW_PART.makeCopy(square1, null, Switch.SWITCH_TYPE.OPEN_CLOSE, 0,
				connectedSquare, seesaw);
		seesaw.pressurePlate1.up = true;
		seesaw.pressurePlate2 = Templates.SEESAW_PART.makeCopy(square2, null, Switch.SWITCH_TYPE.OPEN_CLOSE, 0,
				connectedSquare, seesaw);
		seesaw.pressurePlate1.up = true;

		return seesaw;
	}

	@Override
	public void zwitch(Switch zwitch) {
		if (pressurePlate1 == null || pressurePlate2 == null)
			return;

		if (pressurePlate1.weightOnPlate > pressurePlate2.weightOnPlate) {
			// pressurePlate1.
			if (pressurePlate1.up == false && pressurePlate2.up == true) {
				// already in position
			} else {
				pressurePlate1.up = false;
				pressurePlate2.up = true;
				moveSeesaw();
			}

		} else if (pressurePlate2.weightOnPlate > pressurePlate1.weightOnPlate) {
			if (pressurePlate1.up == true && pressurePlate2.up == false) {
				// already in position
			} else {
				pressurePlate1.up = true;
				pressurePlate2.up = false;
				moveSeesaw();
			}

		} else {
			if (pressurePlate1.up == true && pressurePlate2.up == true) {
				// already in position
			} else {
				pressurePlate1.up = true;
				pressurePlate2.up = true;
				moveSeesaw();
			}
		}
	}

	public void moveSeesaw() {
		if (pressurePlate1.weightOnPlate > pressurePlate2.weightOnPlate) {
			// pressurePlate1.
			if (pressurePlate1.up == false) {
				// already in position
			} else {
				pressurePlate1.up = false;
			}

			if (pressurePlate2.up == true) {
				// already in position
			} else {
				pressurePlate2.up = true;
			}

		} else if (pressurePlate2.weightOnPlate > pressurePlate1.weightOnPlate) {
			if (pressurePlate1.up == true) {
				// already in position
			} else {
				pressurePlate1.up = true;
			}

			if (pressurePlate2.up == false) {
				// already in position
			} else {
				pressurePlate2.up = false;

			}

		} else {
			if (pressurePlate1.up == true) {
				// already in position
			} else {
				pressurePlate1.up = true;
			}

			if (pressurePlate2.up == true) {
				// already in position
			} else {
				pressurePlate2.up = true;
			}
		}
	}

	public static class SeesawPart extends PressurePlate implements UpdatesWhenSquareContentsChange {

		public int weightOnPlate = 0;
		public boolean up;
		public Square connectedSquare = null;

		public SeesawPart makeCopy(Square square, Actor owner, SWITCH_TYPE switchType, int targetWeight,
				Square connectedSquare, SwitchListener... switchListeners) {

			SeesawPart seesawPart = new SeesawPart();
			seesawPart.connectedSquare = connectedSquare;
			seesawPart.switchListeners = switchListeners;
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
		public void update(int delta) {
			super.update(delta);
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

			for (final GameObject gameObject : (ArrayList<GameObject>) squareGameObjectIsOn.inventory.gameObjects
					.clone()) {
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
							gameObject.setPrimaryAnimation(new AnimationFallFromTheSky(gameObject, 200, null));
						}
					};

				}
			}

		}
	}

}
