package com.marklynch.objects;

import com.marklynch.Game;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;
import com.marklynch.utils.TextureUtils;

public class Window extends GameObjectExploder {

	public Window() {
		super();

		canBePickedUp = false;

		fitsInInventory = false;
		canShareSquare = false;


		persistsWhenCantBeSeen = true;

	}

	@Override
	public void draw1() {

		if (!Game.fullVisiblity) {
			if (this.squareGameObjectIsOn.visibleToPlayer == false && persistsWhenCantBeSeen == false)
				return;

			if (!this.squareGameObjectIsOn.seenByPlayer)
				return;
		}

		// MAYBE THE U AND V ARE A RATIO (0 to 1)? yup...
		// TRIED THAT below, didnt work, needs tsome debugging...

		// THEYRE INTS, WHOOPS :D:D:D

		if (this.remainingHealth > 0) {
			float alpha = this.squareGameObjectIsOn.structureSquareIsIn.roofAlpha;

			// Draw object
			if (squareGameObjectIsOn != null) {
				float actorPositionXInPixels = (this.squareGameObjectIsOn.xInGridPixels + drawOffsetRatioX);
				float actorPositionYInPixels = (this.squareGameObjectIsOn.yInGridPixels + drawOffsetRatioY);
				TextureUtils.drawTexture(imageTexture, alpha, actorPositionXInPixels, actorPositionYInPixels,
						actorPositionXInPixels + width, actorPositionYInPixels + height);
				// stub
				actorPositionXInPixels = this.squareGameObjectIsOn.xInGridPixels;
				actorPositionYInPixels = this.squareGameObjectIsOn.yInGridPixels;
				TextureUtils.drawTexture(imageTexture, 1 - alpha, actorPositionXInPixels, actorPositionYInPixels,
						actorPositionXInPixels + Game.SQUARE_WIDTH, actorPositionYInPixels + Game.SQUARE_HEIGHT);
			}

			return;
		}

		for (int i = 0; trianglePieces != null && i < trianglePieces.length; i++) {

			if (trianglePieces[i] == null)
				continue;

			trianglePieces[i].draw();
		}

		for (int i = 0; squarePieces != null && i < squarePieces.length; i++) {

			if (squarePieces[i] == null)
				continue;
			squarePieces[i].draw();
		}
	}

	@Override
	public void draw2() {

	}

	@Override
	public void draw3() {
	}

	@Override
	public Window makeCopy(Square square, Actor owner) {
		Window window = new Window();
		setAttributesForCopy(window, square, owner);
		return window;
	}

}
