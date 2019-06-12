package com.marklynch.objects.inanimateobjects;

import com.marklynch.Game;
import com.marklynch.actions.Action;
import com.marklynch.actions.ActionMove;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.animation.Animation.OnCompletionListener;
import com.marklynch.level.constructs.animation.primary.AnimationStraightLine;
import com.marklynch.level.constructs.bounds.structure.structureroom.StructureRoom;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.actors.Actor.Direction;
import com.marklynch.objects.utils.SwitchListener;
import com.marklynch.utils.ArrayList;
import com.marklynch.utils.Color;
import com.marklynch.utils.ResourceUtils;
import com.marklynch.utils.Texture;

public class ConveyerBelt extends GameObject implements OnCompletionListener, SwitchListener {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>(GameObject.class);
	public Square connectedSquare = null;
	public Direction direction = Direction.LEFT;

	public Texture topImageTexture, bottomImageTexture;

	public static Texture textureUpDown, textureLeftRight;
	public static Texture textureUpDownUnder, textureLeftRightUnder;

	public ConveyerBelt() {
		super();
		canBePickedUp = false;
		fitsInInventory = false;
		canShareSquare = true;
		canContainOtherObjects = false;
		persistsWhenCantBeSeen = true;
		attackable = false;
		moveable = false;
		isFloorObject = true;
		type = "Conveyer Belt";
	}

	public static void loadStaticImages() {
		textureUpDown = ResourceUtils.getGlobalImage("conveyer_belt_up_down.png", true);
		textureLeftRight = ResourceUtils.getGlobalImage("conveyer_belt_left_right.png", true);
		textureUpDownUnder = ResourceUtils.getGlobalImage("conveyer_belt_up_down_under.png", true);
		textureLeftRightUnder = ResourceUtils.getGlobalImage("conveyer_belt_left_right_under.png", true);
	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	public ConveyerBelt makeCopy(Square square, Actor owner, Direction direction) {
		ConveyerBelt conveyorBelt = new ConveyerBelt();
		setInstances(conveyorBelt);
		super.setAttributesForCopy(conveyorBelt, square, owner);
		conveyorBelt.direction = direction;

		// Set where people are moved to
		if (conveyorBelt.direction == Direction.LEFT) {
			conveyorBelt.connectedSquare = conveyorBelt.squareGameObjectIsOn.getSquareToLeftOf();
		} else if (conveyorBelt.direction == Direction.RIGHT) {
			conveyorBelt.connectedSquare = conveyorBelt.squareGameObjectIsOn.getSquareToRightOf();
		} else if (conveyorBelt.direction == Direction.UP) {
			conveyorBelt.connectedSquare = conveyorBelt.squareGameObjectIsOn.getSquareAbove();
		} else if (conveyorBelt.direction == Direction.DOWN) {
			conveyorBelt.connectedSquare = conveyorBelt.squareGameObjectIsOn.getSquareBelow();

		}

		// Set up drawables
		if (conveyorBelt.direction == Direction.LEFT || conveyorBelt.direction == Direction.RIGHT) {
			conveyorBelt.imageTexture = textureLeftRight;
			conveyorBelt.topImageTexture = textureLeftRight;
			conveyorBelt.bottomImageTexture = textureLeftRightUnder;
			conveyorBelt.width = 192;
			conveyorBelt.halfWidth = 192 / 2;
			conveyorBelt.widthRatio = 1.5f;
		} else if (conveyorBelt.direction == Direction.UP || conveyorBelt.direction == Direction.DOWN) {
			conveyorBelt.imageTexture = textureUpDown;
			conveyorBelt.topImageTexture = textureUpDown;
			conveyorBelt.bottomImageTexture = textureUpDownUnder;
			conveyorBelt.height = 192;
			conveyorBelt.halfHeight = 192 / 2;
			conveyorBelt.heightRatio = 1.5f;
		}

		return conveyorBelt;
	}

	float offsetLeftX = 0;
	float offsetRightX = 0;
	float offsetUpY = 0;
	float offsetDownY = 0;
	float offsetY = 0;

	@Override
	public boolean draw1() {

		if (!shouldDraw())
			return false;

//		Time this frame showing same value every fucking time.

		long generalOffset = (Game.timeThisFrame / 4l) % 64l;
//		float generalOffset = 1f;
//		System.out.println("generalOffset = " + generalOffset);
//		System.out.println("Game.timeThisFrame = " + Game.timeThisFrame);
//		System.out.println("(int)Game.timeThisFrame = " + (int) Game.timeThisFrame);
//		System.out.println("Long.MAX_VALUE = " + Long.MAX_VALUE);

		if (direction == Direction.LEFT || direction == Direction.RIGHT) {
			offsetLeftX = -generalOffset;
			offsetRightX = generalOffset - 64;
		} else if (direction == Direction.UP || direction == Direction.DOWN) {
			offsetUpY = -generalOffset;
			offsetDownY = generalOffset - 64;
		}

//		int offset = Game.delta % maxOffset;
		int actorPositionXInPixels = (int) (this.squareGameObjectIsOn.xInGridPixels);
		int actorUnderPositionXInPixels = (int) (this.squareGameObjectIsOn.xInGridPixels);
		int actorPositionYInPixels = (int) (this.squareGameObjectIsOn.yInGridPixels);
		int actorUnderPositionYInPixels = (int) (this.squareGameObjectIsOn.yInGridPixels);

		if (direction == Direction.LEFT) {
			actorPositionXInPixels += (int) (offsetLeftX);
		} else if (direction == Direction.RIGHT) {
			actorPositionXInPixels += (int) (offsetRightX);
		} else if (direction == Direction.UP) {
			actorPositionYInPixels += (int) (offsetUpY);
		} else if (direction == Direction.DOWN) {
			actorPositionYInPixels += (int) (offsetDownY);
		}

		float alpha = 1.0f;

		if (primaryAnimation != null)
			alpha = primaryAnimation.alpha;
		if (!this.squareGameObjectIsOn.visibleToPlayer)
			alpha = 0.5f;

		boundsX1Relative = 0;
		boundsY1Relative = 0;
		boundsX2Relative = Game.SQUARE_WIDTH;
		boundsY2Relative = Game.SQUARE_HEIGHT;

		boundsX1 = actorPositionXInPixels + boundsX1Relative;
		boundsY1 = actorPositionYInPixels + boundsY1Relative;
		boundsX2 = actorPositionXInPixels + boundsX2Relative;
		boundsY2 = actorPositionYInPixels + boundsY2Relative;

		float scaleX = 1;
		float scaleY = 1;

		Color color = Level.dayTimeOverlayColor;
		if (this.squareGameObjectIsOn.structureSquareIsIn != null)
			color = StructureRoom.roomColor;
		color = calculateColor(color);

		// under part
		drawGameObject(actorUnderPositionXInPixels, actorUnderPositionYInPixels, width, height, halfWidth, halfHeight,
				alpha,
				flash || this == Game.gameObjectMouseIsOver
						|| (Game.gameObjectMouseIsOver != null
								&& Game.gameObjectMouseIsOver.gameObjectsToHighlight.contains(this)),
				scaleX, scaleY, 0f, boundsX1, boundsY1, boundsX2, boundsY2, color, true, bottomImageTexture);

		// over part
		drawGameObject(actorPositionXInPixels, actorPositionYInPixels, width, height, halfWidth, halfHeight, alpha,
				flash || this == Game.gameObjectMouseIsOver
						|| (Game.gameObjectMouseIsOver != null
								&& Game.gameObjectMouseIsOver.gameObjectsToHighlight.contains(this)),
				scaleX, scaleY, 0f, boundsX1, boundsY1, boundsX2, boundsY2, color, true, topImageTexture);

		return true;
	}

	@Override
	public void squareContentsChanged() {

		if (squareGameObjectIsOn == null)
			return;

		for (final GameObject gameObject : (ArrayList<GameObject>) squareGameObjectIsOn.inventory.gameObjects.clone()) {

			if (gameObject == this || gameObject.isFloorObject)
				continue;

			if (gameObject.primaryAnimation != null && gameObject.primaryAnimation.completed == false) {
				gameObject.primaryAnimation.onCompletionListener = this;
			} else {
				doTheThing(gameObject);
			}
		}

	}

	public void doTheThing(final GameObject gameObject) {

		if (squareGameObjectIsOn == null || gameObject == null || connectedSquare == null)
			return;

//		new ActionMove(gameObject, connectedSquare, false, true);

//		new ActionTeleport(ConveyerBelt.this, gameObject, connectedSquare, true, true, false).perform();

//		AnimationStraightLine(GameObject performer, float time, boolean blockAI, double delay,
//				OnCompletionListener onCompletionListener, Square... targetSquares) {

		gameObject.setPrimaryAnimation(new AnimationStraightLine(gameObject, 2f, false, 0f, null, connectedSquare));

	}

	@Override
	public Action getDefaultActionPerformedOnThisInWorld(Actor performer) {
		return new ActionMove(performer, squareGameObjectIsOn, true, true);
	}

	@Override
	public Action getSecondaryActionPerformedOnThisInWorld(Actor performer) {
		return new ActionMove(performer, squareGameObjectIsOn, true, true);
	}

	@Override
	public ArrayList<Action> getAllActionsPerformedOnThisInWorld(Actor performer) {
		ArrayList<Action> actions = new ArrayList<Action>(Action.class);
		actions.add(new ActionMove(performer, squareGameObjectIsOn, true, true));
		actions.addAll(super.getAllActionsPerformedOnThisInWorld(performer));
		return actions;
	}

	@Override
	public void animationComplete(GameObject gameObject) {
//		if (gameObject == this)
//			this.continueAnimation();
//		else
		doTheThing(gameObject);
	}

	@Override
	public void zwitch(Switch zwitch) {
		if (direction == Direction.LEFT) {
			direction = Direction.RIGHT;
		} else if (direction == Direction.RIGHT) {

			direction = Direction.LEFT;
		} else if (direction == Direction.UP) {
			direction = Direction.DOWN;

		} else if (direction == Direction.DOWN) {
			direction = Direction.UP;

		}

		if (direction == Direction.LEFT) {
			connectedSquare = squareGameObjectIsOn.getSquareToLeftOf();
		} else if (direction == Direction.RIGHT) {
			connectedSquare = squareGameObjectIsOn.getSquareToRightOf();
		} else if (direction == Direction.UP) {
			connectedSquare = squareGameObjectIsOn.getSquareAbove();
		} else if (direction == Direction.DOWN) {
			connectedSquare = squareGameObjectIsOn.getSquareBelow();
		}
	}

}
