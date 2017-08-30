package com.marklynch.objects;

import com.marklynch.Game;
import com.marklynch.level.constructs.inventory.Inventory;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;
import com.marklynch.utils.TextureUtils;

import mdesl.graphics.Color;

public class Window extends GameObjectExploder {

	public Window(String name, int health, String imagePath, Square squareGameObjectIsOn, Inventory inventory,

			float widthRatio, float heightRatio, float drawOffsetX, float drawOffsetY, float soundWhenHit,
			float soundWhenHitting, float soundDampening, Color light, float lightHandleX, float lightHandlY,
			boolean stackable, float fireResistance, float waterResistance, float electricResistance,
			float poisonResistance, float slashResistance, float weight, int value, Actor owner) {
		super(name, health, imagePath, squareGameObjectIsOn, inventory, widthRatio, heightRatio, drawOffsetX,
				drawOffsetY, soundWhenHit, soundWhenHitting, soundDampening, light, lightHandleX, lightHandlY,
				stackable, fireResistance, waterResistance, electricResistance, poisonResistance, slashResistance,
				weight, value, owner);

		canBePickedUp = false;
		showInventory = false;
		fitsInInventory = false;
		canShareSquare = false;
		canContainOtherObjects = false;
		blocksLineOfSight = false;
		persistsWhenCantBeSeen = true;
		attackable = true;
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
				int actorPositionXInPixels = (int) (this.squareGameObjectIsOn.xInGrid * (int) Game.SQUARE_WIDTH
						+ drawOffsetX);
				int actorPositionYInPixels = (int) (this.squareGameObjectIsOn.yInGrid * (int) Game.SQUARE_HEIGHT
						+ drawOffsetY);
				TextureUtils.drawTexture(imageTexture, alpha, actorPositionXInPixels, actorPositionYInPixels,
						actorPositionXInPixels + width, actorPositionYInPixels + height);
				// stub
				actorPositionXInPixels = this.squareGameObjectIsOn.xInGrid * (int) Game.SQUARE_WIDTH;
				actorPositionYInPixels = this.squareGameObjectIsOn.yInGrid * (int) Game.SQUARE_HEIGHT;
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
		return new Window(new String(name), (int) totalHealth, imageTexturePath, square, new Inventory(),

				widthRatio, heightRatio, drawOffsetX, drawOffsetY, soundWhenHit, soundWhenHitting, soundDampening,
				light, lightHandleX, lightHandlY, stackable, fireResistance, waterResistance, electricResistance,
				poisonResistance, slashResistance, weight, value, owner);
	}

}
