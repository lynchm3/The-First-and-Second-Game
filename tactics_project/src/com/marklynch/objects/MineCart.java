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

	Square squareToMoveTo = null;

	@Override
	public void update(int delta) {
		super.update(delta);
		if (squareGameObjectIsOn != null) {
			squareToMoveTo = null;
			// System.out.println("squareToMoveTo 1 = " + squareToMoveTo);
			System.out.println("direction 1 = " + direction);

			Rail currentRail = (Rail) this.squareGameObjectIsOn.inventory
					.getObjectWithTemplateId(Templates.RAIL.templateId);

			currentRail = (Rail) this.squareGameObjectIsOn.inventory.getObjectWithTemplateId(Templates.RAIL.templateId);
			if (direction == Direction.RIGHT) {
				direction = currentRail.getOppositeDirection(Direction.LEFT);
			} else if (direction == Direction.LEFT) {
				direction = currentRail.getOppositeDirection(Direction.RIGHT);
			} else if (direction == Direction.UP) {
				direction = currentRail.getOppositeDirection(Direction.DOWN);
			} else if (direction == Direction.DOWN) {
				direction = currentRail.getOppositeDirection(Direction.UP);
			}

			if (direction == null)
				return;

			setSquareToMoveTo();

			// if (squareToMoveTo == null) {
			// direction = currentRail.getOppositeDirection(this.direction);
			// setSquareToMoveTo();
			// }

			// System.out.println("squareToMoveTo 2 = " + squareToMoveTo);
			System.out.println("direction 2 = " + direction);

			Rail railToMoveTo = (Rail) squareToMoveTo.inventory.getObjectWithTemplateId(Templates.RAIL.templateId);
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

			if (railToMoveTo != null && squareToMoveTo.inventory.canShareSquare) {
				this.setPrimaryAnimation(new AnimationStraightLine(this, 1f, squareToMoveTo) {
					@Override
					public void runCompletionAlgorightm(boolean wait) {
						super.runCompletionAlgorightm(wait);
						postRangedAnimation(MineCart.this, squareToMoveTo);
						// postRangedAnimation(arrow);
					}
				});
			} else {

			}

			// if()

			// Redirect -
		}
	}

	public void setSquareToMoveTo() {

		if (direction == Direction.RIGHT) {
			squareToMoveTo = squareGameObjectIsOn.getSquareToRightOf();
		} else if (direction == Direction.LEFT) {
			squareToMoveTo = squareGameObjectIsOn.getSquareToLeftOf();
		} else if (direction == Direction.UP) {
			squareToMoveTo = squareGameObjectIsOn.getSquareAbove();
		} else if (direction == Direction.DOWN) {
			squareToMoveTo = squareGameObjectIsOn.getSquareBelow();
		}
	}

}
