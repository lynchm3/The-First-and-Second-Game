package com.marklynch.objects.inanimateobjects;

import com.marklynch.actions.Action;
import com.marklynch.actions.ActionMove;
import com.marklynch.actions.ActionTeleport;
import com.marklynch.level.constructs.animation.Animation.OnCompletionListener;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.actors.Actor.Direction;
import com.marklynch.utils.ArrayList;
import com.marklynch.utils.ResourceUtils;
import com.marklynch.utils.Texture;

public class ConveyerBelt extends GameObject implements OnCompletionListener {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>(GameObject.class);
	public Square connectedSquare = null;
	public Direction direction = Direction.LEFT;

	public static Texture textureUp, textureDown, textureLeft, textureRight;

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
		textureUp = ResourceUtils.getGlobalImage("conveyer_belt_up.png", true);
		textureDown = ResourceUtils.getGlobalImage("conveyer_belt_down.png", true);
		textureLeft = ResourceUtils.getGlobalImage("conveyer_belt_left.png", true);
		textureRight = ResourceUtils.getGlobalImage("conveyer_belt_right.png", true);
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
		if (conveyorBelt.direction == Direction.LEFT) {
			conveyorBelt.connectedSquare = conveyorBelt.squareGameObjectIsOn.getSquareToLeftOf();
			conveyorBelt.imageTexture = textureLeft;
		} else if (conveyorBelt.direction == Direction.RIGHT) {
			conveyorBelt.connectedSquare = conveyorBelt.squareGameObjectIsOn.getSquareToRightOf();
			conveyorBelt.imageTexture = textureRight;
		} else if (conveyorBelt.direction == Direction.UP) {
			conveyorBelt.connectedSquare = conveyorBelt.squareGameObjectIsOn.getSquareAbove();
			conveyorBelt.imageTexture = textureUp;
		} else if (conveyorBelt.direction == Direction.DOWN) {
			conveyorBelt.connectedSquare = conveyorBelt.squareGameObjectIsOn.getSquareBelow();
			conveyorBelt.imageTexture = textureDown;
		}
		return conveyorBelt;
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

		new ActionTeleport(ConveyerBelt.this, gameObject, connectedSquare, true, true, false).perform();

	}

	@Override
	public Action getDefaultActionPerformedOnThisInWorld(Actor performer) {
		return new ActionMove(performer, squareGameObjectIsOn, true);
	}

	@Override
	public Action getSecondaryActionPerformedOnThisInWorld(Actor performer) {
		return new ActionMove(performer, squareGameObjectIsOn, true);
	}

	@Override
	public ArrayList<Action> getAllActionsPerformedOnThisInWorld(Actor performer) {
		ArrayList<Action> actions = new ArrayList<Action>(Action.class);
		actions.add(new ActionMove(performer, squareGameObjectIsOn, true));
		actions.addAll(super.getAllActionsPerformedOnThisInWorld(performer));
		return actions;
	}

	@Override
	public void animationComplete(GameObject gameObject) {
		doTheThing(gameObject);
	}

}
