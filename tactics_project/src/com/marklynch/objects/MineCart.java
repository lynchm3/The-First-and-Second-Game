package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.level.constructs.animation.primary.AnimationStraightLine;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.templates.Templates;
import com.marklynch.objects.units.Actor;

public class MineCart extends GameObject {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();
	boolean up, down, left, right;

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

	public MineCart makeCopy(Square square, Actor owner, boolean up, boolean down, boolean left, boolean right) {
		MineCart mineCart = new MineCart();
		setInstances(mineCart);
		super.setAttributesForCopy(mineCart, square, owner);
		mineCart.up = up;
		mineCart.down = down;
		mineCart.left = left;
		mineCart.right = right;
		return mineCart;
	}

	Square squareToMoveTo = null;

	@Override
	public void update(int delta) {
		super.update(delta);
		if (squareGameObjectIsOn != null) {
			squareToMoveTo = null;
			if (right) {
				squareToMoveTo = squareGameObjectIsOn.getSquareToRightOf();
			} else if (left) {
				squareToMoveTo = squareGameObjectIsOn.getSquareToLeftOf();
			} else if (up) {
				squareToMoveTo = squareGameObjectIsOn.getSquareAbove();
			} else if (down) {
				squareToMoveTo = squareGameObjectIsOn.getSquareBelow();
			}

			if (squareToMoveTo == null)
				return;

			Rail railToMoveTo = (Rail) squareToMoveTo.inventory.getObjectWithTemplateId(Templates.RAIL.templateId);

			if (railToMoveTo == null)
				return;

			if (squareToMoveTo.inventory.containsObjectWithTemplateId(Templates.RAIL.templateId)
					&& squareToMoveTo.inventory.canShareSquare) {
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

			// Redirect -
			if (right) {
				this.right = false;
				if (railToMoveTo.down) {
					this.down = true;
				} else if (railToMoveTo.up) {
					this.up = true;
				} else if (railToMoveTo.right) {
					this.right = true;
				} else {
					this.left = true;
				}
			} else if (left) {
				this.left = false;
				if (railToMoveTo.down) {
					this.down = true;
				} else if (railToMoveTo.up) {
					this.up = true;
				} else if (railToMoveTo.left) {
					this.left = true;
				} else {
					this.right = true;
				}
			} else if (up) {
				this.up = false;
				if (railToMoveTo.up) {
					this.up = true;
				} else if (railToMoveTo.right) {
					this.right = true;
				} else if (railToMoveTo.left) {
					this.left = true;
				} else {
					down = true;
				}
			} else if (down) {
				this.down = false;
				if (railToMoveTo.right) {
					this.right = true;
				} else if (railToMoveTo.down) {
					this.down = true;
				} else if (railToMoveTo.left) {
					this.left = true;
				} else {
					up = true;
				}
			}
		}
	}

}
