package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actions.ActionInspect;
import com.marklynch.objects.units.Actor;
import com.marklynch.utils.TextureUtils;

import mdesl.graphics.Color;

public class MapMarker extends GameObject {

	public MapMarker(String name, int health, String imagePath, Square squareGameObjectIsOn, Inventory inventory,
			boolean showInventory, boolean canShareSquare, boolean fitsInInventory, boolean canContainOtherObjects,
			boolean blocksLineOfSight, boolean persistsWhenCantBeSeen, float widthRatio, float heightRatio,
			float drawOffsetX, float drawOffsetY, float soundWhenHit, float soundWhenHitting, float soundDampening,
			Color light, float lightHandleX, float lightHandlY, boolean stackable, float fireResistance,
			float waterResistance, float electricResistance, float poisonResistance, float weight, Actor owner) {
		super(name, health, imagePath, squareGameObjectIsOn, inventory, showInventory, canShareSquare, fitsInInventory,
				canContainOtherObjects, blocksLineOfSight, persistsWhenCantBeSeen, true, widthRatio, heightRatio,
				drawOffsetX, drawOffsetY, soundWhenHit, soundWhenHitting, soundDampening, light, lightHandleX,
				lightHandlY, stackable, fireResistance, waterResistance, electricResistance, poisonResistance, weight,
				owner);
	}

	@Override
	public ArrayList<Action> getAllActionsPerformedOnThisInWorld(Actor performer) {
		ArrayList<Action> actions = new ArrayList<Action>();
		actions.add(new ActionInspect(performer, this));
		return actions;

	}

	@Override
	public void draw1() {

	}

	@Override
	public void draw2() {

	}

	@Override
	public void draw3() {

	}

	@Override
	public void drawUI() {

	}

	@Override
	public void drawStaticUI() {

		System.out.println("MapMarker.drawStaticUI");

		float alpha = 1;
		if (Game.zoomLevelIndex < Game.MAP_MODE_ZOOM_LEVEL_INDEX)
			alpha = 0.5f;

		int squarePositionX1 = this.squareGameObjectIsOn.xInGrid * (int) Game.SQUARE_WIDTH;
		int squarePositionY1 = this.squareGameObjectIsOn.yInGrid * (int) Game.SQUARE_HEIGHT;
		float drawPositionX1 = (Game.windowWidth / 2)
				+ (Game.zoom * (squarePositionX1 - Game.windowWidth / 2 + Game.dragX))
				- (Game.HALF_SQUARE_WIDTH - Game.HALF_SQUARE_WIDTH * Game.zoom);
		float drawPositionY1 = (Game.windowHeight / 2)
				+ (Game.zoom * (squarePositionY1 - Game.windowHeight / 2 + Game.dragY))
				- (Game.SQUARE_HEIGHT - Game.SQUARE_HEIGHT * Game.zoom) - (Game.HALF_SQUARE_HEIGHT * Game.zoom);
		;
		float drawPositionX2 = drawPositionX1 + (int) Game.SQUARE_WIDTH;
		float drawPositionY2 = drawPositionY1 + (int) Game.SQUARE_HEIGHT;
		TextureUtils.drawTexture(imageTexture, alpha, drawPositionX1, drawPositionY1, drawPositionX2, drawPositionY2);
	}

	@Override
	public MapMarker makeCopy(Square square, Actor owner) {
		return new MapMarker(new String(name), (int) totalHealth, imageTexturePath, square, new Inventory(),
				showInventory, canShareSquare, fitsInInventory, canContainOtherObjects, blocksLineOfSight,
				persistsWhenCantBeSeen, widthRatio, heightRatio, drawOffsetX, drawOffsetY, soundWhenHit,
				soundWhenHitting, soundDampening, light, lightHandleX, lightHandlY, stackable, fireResistance,
				waterResistance, electricResistance, poisonResistance, weight, owner);
	}

}
