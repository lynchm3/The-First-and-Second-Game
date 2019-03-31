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

	public void updateNeighborLines(Square square) {

		for (Direction direction : Direction.values()) {
			Square neighborSquare = this.getSquareInDirection(direction, square);
			if (neighborSquare == null)
				continue;
			Line neighborLine = (Line) neighborSquare.inventory.getGameObjectWithTemplateId(this.templateId);
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
			if (!checkIfConnected(Direction.LEFT, this.squareGameObjectIsOn)) {
				drawLeftBufferStop = true;
			}
		}

		if (direction1 == Direction.RIGHT || direction2 == Direction.RIGHT) {
			if (!checkIfConnected(Direction.RIGHT, this.squareGameObjectIsOn)) {
				drawRightBufferStop = true;
			}
		}

		if (direction1 == Direction.UP || direction2 == Direction.UP) {
			if (!checkIfConnected(Direction.UP, this.squareGameObjectIsOn)) {
				drawUpBufferStop = true;
			}
		}

		if (direction1 == Direction.DOWN || direction2 == Direction.DOWN) {
			if (!checkIfConnected(Direction.DOWN, this.squareGameObjectIsOn)) {
				drawDownBufferStop = true;
			}
		}
	}

	public boolean checkIfConnected(Direction inDirection, Square square) {

		Square squareToCheckIfConnected = getSquareInDirection(inDirection, square);

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

	public Square getSquareInDirection(Direction direction, Square fromSquare) {

		if (this.squareGameObjectIsOn == null)
			return null;

		if (direction == Direction.RIGHT) {
			return fromSquare.getSquareToRightOf();
		} else if (direction == Direction.LEFT) {
			return fromSquare.getSquareToLeftOf();
		} else if (direction == Direction.UP) {
			return fromSquare.getSquareAbove();
		} else if (direction == Direction.DOWN) {
			return fromSquare.getSquareBelow();
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
