package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.Square;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actions.ActionSmash;
import com.marklynch.objects.units.Actor;
import com.marklynch.utils.TextureUtils;

import mdesl.graphics.Color;

public class Window extends GameObjectExploder {

	public Window(String name, int health, String imagePath, Square squareGameObjectIsOn, Inventory inventory,
			boolean showInventory, boolean canShareSquare, boolean fitsInInventory, boolean canContainOtherObjects,
			boolean blocksLineOfSight, boolean persistsWhenCantBeSeen, float widthRatio, float heightRatio,
			float soundHandleX, float soundHandleY, float soundWhenHit, float soundWhenHitting, Color light,
			float lightHandleX, float lightHandlY, boolean stackable, float fireResistance, float iceResistance,
			float electricResistance, float poisonResistance) {
		super(name, health, imagePath, squareGameObjectIsOn, inventory, showInventory, canShareSquare, fitsInInventory,
				canContainOtherObjects, blocksLineOfSight, persistsWhenCantBeSeen, widthRatio, heightRatio,
				soundHandleX, soundHandleY, soundWhenHit, soundWhenHitting, light, lightHandleX, lightHandlY, stackable,
				fireResistance, iceResistance, electricResistance, poisonResistance);
	}

	@Override
	public void draw1() {

		if (this.squareGameObjectIsOn.visibleToPlayer == false && persistsWhenCantBeSeen == false)
			return;

		if (!this.squareGameObjectIsOn.seenByPlayer)
			return;

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
				TextureUtils.drawTexture(imageTexture, alpha, actorPositionXInPixels, actorPositionXInPixels + width,
						actorPositionYInPixels, actorPositionYInPixels + height);
				// stub
				actorPositionXInPixels = this.squareGameObjectIsOn.xInGrid * (int) Game.SQUARE_WIDTH;
				actorPositionYInPixels = this.squareGameObjectIsOn.yInGrid * (int) Game.SQUARE_HEIGHT;
				TextureUtils.drawTexture(imageTexture, 1 - alpha, actorPositionXInPixels,
						actorPositionXInPixels + Game.SQUARE_WIDTH, actorPositionYInPixels,
						actorPositionYInPixels + Game.SQUARE_HEIGHT);
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
	public ArrayList<Action> getAllActionsInWorld(Actor performer) {
		ArrayList<Action> actions = new ArrayList<Action>();

		if (this.remainingHealth < 0)
			return actions;

		actions.add(new ActionSmash(performer, this));
		return actions;

	}

	@Override
	public Window makeCopy(Square square) {
		return new Window(new String(name), (int) totalHealth, imageTexturePath, square, inventory.makeCopy(),
				showInventory, canShareSquare, fitsInInventory, canContainOtherObjects, blocksLineOfSight,
				persistsWhenCantBeSeen, widthRatio, heightRatio, soundHandleX, soundHandleY, soundWhenHit,
				soundWhenHitting, light, lightHandleX, lightHandlY, stackable, fireResistance, iceResistance,
				electricResistance, poisonResistance);
	}

}
