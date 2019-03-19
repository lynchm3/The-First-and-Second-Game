package com.marklynch.objects.inanimateobjects;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor.Direction;
import com.marklynch.objects.utils.SwitchListener;
import com.marklynch.utils.ArrayList;
import com.marklynch.utils.Texture;

public abstract class Line extends GameObject implements SwitchListener {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>(GameObject.class);
	public Direction direction1;
	public Direction direction2;
	public boolean drawLeftBufferStop = false;
	public boolean drawRightBufferStop = false;
	public boolean drawUpBufferStop = false;
	public boolean drawDownBufferStop = false;

	public Texture imageTextureLeftRight;
	public Texture imageTextureUpDown;
	public Texture imageTextureLeftUp;
	public Texture imageTextureRightUp;
	public Texture imageTextureLeftDown;
	public Texture imageTextureRightDown;
	public Texture imageTextureLeftEnd;
	public Texture imageTextureRightEnd;
	public Texture imageTextureUpEnd;
	public Texture imageTextureDownEnd;

	public Line() {
		super();
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	public void updateNeighborLines() {

		for (Direction direction : Direction.values()) {
			Square neightborSquare = this.getSquareInDirection(direction);
			if (neightborSquare == null)
				continue;
			Line neighborLine = (Line) neightborSquare.inventory.getGameObjectWithTemplateId(this.templateId);
			if (neighborLine == null)
				continue;
			neighborLine.updateImageTextures();
		}
	}

	@Override
	public boolean draw1() {
		boolean shouldDraw = super.draw1();
		if (!shouldDraw)
			return false;

		Texture lineTexture = imageTexture;

		if (drawLeftBufferStop) {
			imageTexture = imageTextureLeftEnd;
			super.draw1();
		}

		if (drawRightBufferStop) {
			imageTexture = imageTextureRightEnd;
			super.draw1();

		}

		if (drawUpBufferStop) {
			imageTexture = imageTextureUpEnd;
			super.draw1();

		}

		if (drawDownBufferStop) {
			imageTexture = imageTextureDownEnd;
			super.draw1();
		}

		imageTexture = lineTexture;
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

		Line railToCheckIfConnected = (Line) squareToCheckIfConnected.inventory.getGameObjectOfClass(Line.class);

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

}
