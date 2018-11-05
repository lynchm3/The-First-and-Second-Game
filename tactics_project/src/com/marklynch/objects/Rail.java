package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.templates.Templates;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.Actor.Direction;
import com.marklynch.utils.Texture;

public class Rail extends GameObject implements SwitchListener {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();
	Direction direction1;
	Direction direction2;
	public static Texture imageTextureLeftRight;
	public static Texture imageTextureUpDown;
	// boolean up, down, left, right;
	public static Texture imageTextureLeftUp;
	public static Texture imageTextureRightUp;
	public static Texture imageTextureLeftDown;
	public static Texture imageTextureRightDown;
	public boolean turnsClockwiseFirst = true;

	public boolean drawLeftBufferStop = false;
	public boolean drawRightBufferStop = false;
	public boolean drawUpBufferStop = false;
	public boolean drawDownBufferStop = false;
	public static Texture imageTextureLeftBufferStop;
	public static Texture imageTextureRightBufferStop;
	public static Texture imageTextureUpBufferStop;
	public static Texture imageTextureDownBufferStop;

	public Rail() {
		super();
		canBePickedUp = false;
		fitsInInventory = false;
		isFloorObject = true;
		type = "Rail";
	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	public Rail makeCopy(Square square, Actor owner, Direction direction1, Direction direction2) {
		Rail rail = new Rail();
		setInstances(rail);
		super.setAttributesForCopy(rail, square, owner);
		rail.direction1 = direction1;
		rail.direction2 = direction2;

		rail.updateImageTextures();

		rail.updateNeightborRails();

		return rail;
	}

	public void updateNeightborRails() {

		for (Direction direction : Direction.values()) {
			Square neightborSquare = this.getSquareInDirection(direction);
			if (neightborSquare == null)
				continue;
			Rail neighborRail = (Rail) neightborSquare.inventory.getGameObjectWithTemplateId(Templates.RAIL.templateId,
					Templates.RAIL_INVISIBLE.templateId);
			if (neighborRail == null)
				continue;
			neighborRail.updateImageTextures();
		}
	}

	@Override
	public boolean draw1() {
		boolean shouldDraw = super.draw1();
		if (!shouldDraw)
			return false;

		Texture railTexture = imageTexture;

		if (drawLeftBufferStop) {
			imageTexture = imageTextureLeftBufferStop;
			super.draw1();
		}

		if (drawRightBufferStop) {
			imageTexture = imageTextureRightBufferStop;
			super.draw1();

		}

		if (drawUpBufferStop) {
			imageTexture = imageTextureUpBufferStop;
			super.draw1();

		}

		if (drawDownBufferStop) {
			imageTexture = imageTextureDownBufferStop;
			super.draw1();
		}

		imageTexture = railTexture;
		return true;
	}

	public Direction getOppositeDirection(Direction direction) {
		if (direction1 == direction) {
			return direction2;
		} else if (direction2 == direction) {
			return direction1;
		}
		return null;
	}

	@Override
	public void zwitch(Switch zwitch) {
		// rotate 90 degrees
		// if(direction)
		if (turnsClockwiseFirst) {
			if (zwitch.pressed) {
				direction1 = rotate90Degrees(direction1);
				direction2 = rotate90Degrees(direction2);
			} else {

				direction1 = rotateMinus90Degrees(direction1);
				direction2 = rotateMinus90Degrees(direction2);
			}
		} else {

			if (zwitch.pressed) {
				direction1 = rotateMinus90Degrees(direction1);
				direction2 = rotateMinus90Degrees(direction2);
			} else {

				direction1 = rotate90Degrees(direction1);
				direction2 = rotate90Degrees(direction2);
			}
		}

		this.showPow();
		updateImageTextures();
		updateNeightborRails();

	}

	public Direction rotate90Degrees(Direction direction) {
		if (direction == Direction.RIGHT) {
			return Direction.DOWN;
		} else if (direction == Direction.DOWN) {
			return Direction.LEFT;
		} else if (direction == Direction.LEFT) {
			return Direction.UP;
		} else if (direction == Direction.UP) {
			return Direction.RIGHT;
		}
		return Direction.DOWN;

	}

	public Direction rotateMinus90Degrees(Direction direction) {
		if (direction == Direction.RIGHT) {
			return Direction.UP;
		} else if (direction == Direction.DOWN) {
			return Direction.RIGHT;
		} else if (direction == Direction.LEFT) {
			return Direction.DOWN;
		} else if (direction == Direction.UP) {
			return Direction.LEFT;
		}
		return Direction.DOWN;

	}

	public void updateImageTextures() {

		if ((direction1 == Direction.LEFT || direction1 == Direction.RIGHT)
				&& (direction2 == Direction.LEFT || direction2 == Direction.RIGHT)) {
			// left right
			imageTexture = imageTextureLeftRight;
		} else if ((direction1 == Direction.UP || direction1 == Direction.DOWN)
				&& (direction2 == Direction.UP || direction2 == Direction.DOWN)) {
			// up down
			imageTexture = imageTextureUpDown;
		} else if ((direction1 == Direction.UP || direction1 == Direction.LEFT)
				&& (direction2 == Direction.UP || direction2 == Direction.LEFT)) {
			// up left
			imageTexture = imageTextureLeftUp;
		} else if ((direction1 == Direction.DOWN || direction1 == Direction.RIGHT)
				&& (direction2 == Direction.DOWN || direction2 == Direction.RIGHT)) {
			// down right
			imageTexture = imageTextureRightDown;
		} else if ((direction1 == Direction.UP || direction1 == Direction.RIGHT)
				&& (direction2 == Direction.UP || direction2 == Direction.RIGHT)) {
			// up right
			imageTexture = imageTextureRightUp;
		} else if ((direction1 == Direction.DOWN || direction1 == Direction.LEFT)
				&& (direction2 == Direction.DOWN || direction2 == Direction.LEFT)) {
			// down left
			imageTexture = imageTextureLeftDown;
		} else {
			imageTexture = imageTextureLeftRight;
		}

		// Buffer Stops
		drawLeftBufferStop = false;
		drawRightBufferStop = false;
		drawUpBufferStop = false;
		drawDownBufferStop = false;

		if (direction1 == Direction.LEFT || direction2 == Direction.LEFT) {
			if (!checkIfConnected(Direction.LEFT)) {
				drawLeftBufferStop = true;
			}
		}

		if (direction1 == Direction.RIGHT || direction2 == Direction.RIGHT) {
			if (!checkIfConnected(Direction.RIGHT)) {
				drawRightBufferStop = true;
			}
		}

		if (direction1 == Direction.UP || direction2 == Direction.UP) {
			if (!checkIfConnected(Direction.UP)) {
				drawUpBufferStop = true;
			}
		}

		if (direction1 == Direction.DOWN || direction2 == Direction.DOWN) {
			if (!checkIfConnected(Direction.DOWN)) {
				drawDownBufferStop = true;
			}
		}

	}

	public boolean checkIfConnected(Direction inDirection) {

		Square squareToCheckIfConnected = getSquareInDirection(inDirection);

		if (squareToCheckIfConnected == null)
			return false;

		Rail railToCheckIfConnected = (Rail) squareToCheckIfConnected.inventory.getGameObjectOfClass(Rail.class);

		if (railToCheckIfConnected == null) {
			return false;
		} else if (inDirection == Direction.RIGHT && railToCheckIfConnected.direction1 != Direction.LEFT
				&& railToCheckIfConnected.direction2 != Direction.LEFT) {
			return false;
		} else if (inDirection == Direction.LEFT && railToCheckIfConnected.direction1 != Direction.RIGHT
				&& railToCheckIfConnected.direction2 != Direction.RIGHT) {
			return false;
		} else if (inDirection == Direction.UP && railToCheckIfConnected.direction1 != Direction.DOWN
				&& railToCheckIfConnected.direction2 != Direction.DOWN) {
			return false;
		} else if (inDirection == Direction.DOWN && railToCheckIfConnected.direction1 != Direction.UP
				&& railToCheckIfConnected.direction2 != Direction.UP) {
			return false;
		}

		return true;
	}

	public Square getSquareInDirection(Direction direction) {

		if (this.squareGameObjectIsOn == null)
			return null;

		if (direction == Direction.RIGHT) {
			return this.squareGameObjectIsOn.getSquareToRightOf();
		} else if (direction == Direction.LEFT) {
			return this.squareGameObjectIsOn.getSquareToLeftOf();
		} else if (direction == Direction.UP) {
			return this.squareGameObjectIsOn.getSquareAbove();
		} else if (direction == Direction.DOWN) {
			return this.squareGameObjectIsOn.getSquareBelow();
		}
		return null;
	}

}
