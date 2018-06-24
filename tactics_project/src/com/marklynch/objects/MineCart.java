package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.level.constructs.animation.primary.AnimationStraightLine;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.templates.Templates;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.Actor.Direction;

public class MineCart extends GameObject {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();
	Direction direction;

	public MineCart() {
		super();
		canBePickedUp = false;
		fitsInInventory = false;
	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	public MineCart makeCopy(Square square, Actor owner, Direction direction) {
		MineCart mineCart = new MineCart();
		setInstances(mineCart);
		super.setAttributesForCopy(mineCart, square, owner);
		mineCart.direction = direction;
		return mineCart;
	}

	@Override
	public void update(int delta) {
		super.update(delta);
		if (squareGameObjectIsOn == null)
			return;

		ArrayList<SquareAndDirection> squaresForAnimation = new ArrayList<SquareAndDirection>();

		SquareAndDirection currentSquareAndDirection = new SquareAndDirection();
		currentSquareAndDirection.direction = this.direction;
		currentSquareAndDirection.square = this.squareGameObjectIsOn;
		for (int i = 0; i < 4; i++) {
			currentSquareAndDirection = move(currentSquareAndDirection);
			if (currentSquareAndDirection.square != null && currentSquareAndDirection.direction != null
					&& currentSquareAndDirection.square != this.squareGameObjectIsOn) {
				squaresForAnimation.add(currentSquareAndDirection);
			} else {
				break;
			}
		}

		System.out.println("squaresForAnimation.size()  = " + squaresForAnimation.size());
		if (squaresForAnimation.size() > 0) {
			final Square[] array = new Square[squaresForAnimation.size()];
			for (int i = 0; i < squaresForAnimation.size(); i++) {
				System.out.println("squaresForAnimation.get(" + i + ").square  = " + squaresForAnimation.get(i).square);
				System.out.println(
						"squaresForAnimation.get(" + i + ").direction  = " + squaresForAnimation.get(i).direction);
				array[i] = squaresForAnimation.get(i).square;
			}

			this.direction = squaresForAnimation.get(squaresForAnimation.size() - 1).direction;
			for (final GameObject gamObject : this.squareGameObjectIsOn.inventory.gameObjects) {

				if (gamObject.isFloorObject)
					continue;

				gamObject.setPrimaryAnimation(new AnimationStraightLine(this, 0.5f, array) {
					@Override
					public void runCompletionAlgorightm(boolean wait) {
						super.runCompletionAlgorightm(wait);
						postRangedAnimation(gamObject, array);
						// postRangedAnimation(arrow);
					}
				});
			}
		}
	}

	public class SquareAndDirection {
		public Square square;
		public Direction direction;
	}

	public SquareAndDirection move(SquareAndDirection oldSquareAndDirection) {
		SquareAndDirection newSquareAndDirection = new SquareAndDirection();
		// System.out.println("squareToMoveTo 1 = " + squareToMoveTo);
		// System.out.println("direction 1 = " + direction);

		Rail currentRail = (Rail) oldSquareAndDirection.square.inventory
				.getObjectWithTemplateId(Templates.RAIL.templateId);

		if (currentRail == null)
			return newSquareAndDirection;

		if (oldSquareAndDirection.direction == Direction.RIGHT) {
			newSquareAndDirection.direction = currentRail.getOppositeDirection(Direction.LEFT);
		} else if (oldSquareAndDirection.direction == Direction.LEFT) {
			newSquareAndDirection.direction = currentRail.getOppositeDirection(Direction.RIGHT);
		} else if (oldSquareAndDirection.direction == Direction.UP) {
			newSquareAndDirection.direction = currentRail.getOppositeDirection(Direction.DOWN);
		} else if (oldSquareAndDirection.direction == Direction.DOWN) {
			newSquareAndDirection.direction = currentRail.getOppositeDirection(Direction.UP);
		}

		if (newSquareAndDirection.direction == null)
			return newSquareAndDirection;

		newSquareAndDirection.square = getSquareToMoveTo(newSquareAndDirection.direction, oldSquareAndDirection.square);

		// if (squareToMoveTo == null) {
		// direction = currentRail.getOppositeDirection(this.direction);
		// setSquareToMoveTo();
		// }

		// System.out.println("squareToMoveTo 2 = " + squareToMoveTo);
		System.out.println("direction 2 = " + direction);

		Rail railToMoveTo = (Rail) newSquareAndDirection.square.inventory
				.getObjectWithTemplateId(Templates.RAIL.templateId);
		// if (railToMoveTo == null) {
		// direction = currentRail.getOppositeDirection(this.direction);
		// setSquareToMoveTo();
		// railToMoveTo = (Rail)
		// squareToMoveTo.inventory.getObjectWithTemplateId(Templates.RAIL.templateId);
		// }
		// System.out.println("squareToMoveTo 3 = " + squareToMoveTo);
		// System.out.println("direction 3 = " + direction);

		// if (railToMoveTo == null)
		// return;

		if (railToMoveTo != null) {// && newSquareAndDirection.square.inventory.canShareSquare) {
		} else {
			newSquareAndDirection.square = null;
		}

		return newSquareAndDirection;
	}

	public Square getSquareToMoveTo(Direction direction, Square currentSquare) {

		if (direction == Direction.RIGHT) {
			return currentSquare.getSquareToRightOf();
		} else if (direction == Direction.LEFT) {
			return currentSquare.getSquareToLeftOf();
		} else if (direction == Direction.UP) {
			return currentSquare.getSquareAbove();
		} else if (direction == Direction.DOWN) {
			return currentSquare.getSquareBelow();
		}
		return null;
	}

}
