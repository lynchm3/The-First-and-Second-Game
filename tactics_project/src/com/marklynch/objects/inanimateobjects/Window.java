package com.marklynch.objects.inanimateobjects;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.utils.TextureUtils;

public class Window extends GameObjectExploder {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();

	public Window() {
		super();

		canBePickedUp = false;

		fitsInInventory = false;
		canShareSquare = false;

		persistsWhenCantBeSeen = true;
		type = "Window";

	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	@Override
	public boolean draw1() {

		if (!shouldDraw())
			return false;

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

			return true;
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
		return true;
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
		setInstances(window);
		setAttributesForCopy(window, square, owner);
		return window;
	}

}
