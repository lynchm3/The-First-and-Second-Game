package com.marklynch.objects;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.Square;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actions.ActionAttack;
import com.marklynch.objects.actions.ActionMine;
import com.marklynch.objects.units.Actor;
import com.marklynch.utils.TextureUtils;

import mdesl.graphics.Color;
import mdesl.graphics.Texture;

public class Vein extends Wall {

	public boolean connectedTop = false;
	public boolean connectedTopRight = false;
	public boolean connectedRight = false;
	public boolean connectedBottomRight = false;
	public boolean connectedBottom = false;
	public boolean connectedBottomLeft = true;
	public boolean connectedLeft = true;
	public boolean connectedTopLeft = true;

	public Texture textureTop;
	public Texture textureTopRight;
	public Texture textureRight;
	public Texture textureBottomRight;
	public Texture textureBottom;
	public Texture textureBottomLeft;
	public Texture textureLeft;
	public Texture textureTopLeft;

	public Vein(String name, int health, String imagePath, Square squareGameObjectIsOn, Inventory inventory,
			boolean showInventory, boolean canShareSquare, boolean fitsInInventory, boolean canContainOtherObjects,
			boolean blocksLineOfSight, boolean persistsWhenCantBeSeen, float widthRatio, float heightRatio,
			float soundHandleX, float soundHandleY, float soundWhenHit, float soundWhenHitting, Color light,
			float lightHandleX, float lightHandlY, boolean stackable, float fireResistance, float iceResistance,
			float electricResistance, float poisonResistance) {
		super(name, health, imagePath, squareGameObjectIsOn, inventory, showInventory, canShareSquare, fitsInInventory,
				canContainOtherObjects, blocksLineOfSight, persistsWhenCantBeSeen, widthRatio, heightRatio,
				soundHandleX, soundHandleY, soundWhenHit, soundWhenHitting, light, lightHandleX, lightHandlY, stackable,
				fireResistance, iceResistance, electricResistance, poisonResistance);
		loadImages();
	}

	@Override
	public void loadImages() {
		super.loadImages();
		textureTop = getGlobalImage("vein_top.png");
		textureTopRight = getGlobalImage("wall_top_right.png");
		textureRight = getGlobalImage("vein_right.png");
		textureBottomRight = getGlobalImage("wall_bottom_right.png");
		textureBottom = getGlobalImage("vein_bottom.png");
		textureBottomLeft = getGlobalImage("wall_bottom_left.png");
		textureLeft = getGlobalImage("vein_left.png");
		textureTopLeft = getGlobalImage("wall_top_left.png");
	}

	@Override
	public void draw1() {
		if (this.remainingHealth <= 0)
			return;

		if (this.squareGameObjectIsOn.visibleToPlayer == false && persistsWhenCantBeSeen == false)
			return;

		if (!this.squareGameObjectIsOn.seenByPlayer)
			return;

		// Draw object
		if (squareGameObjectIsOn != null) {
			int actorPositionXInPixels = (int) (this.squareGameObjectIsOn.xInGrid * (int) Game.SQUARE_WIDTH
					+ drawOffsetX);
			int actorPositionYInPixels = (int) (this.squareGameObjectIsOn.yInGrid * (int) Game.SQUARE_HEIGHT
					+ drawOffsetY);

			float alpha = 1.0f;

			if (connectedTop)
				TextureUtils.drawTexture(textureTop, alpha, actorPositionXInPixels, actorPositionXInPixels + width,
						actorPositionYInPixels, actorPositionYInPixels + height);
			if (connectedTopRight)
				TextureUtils.drawTexture(textureTopRight, alpha, actorPositionXInPixels, actorPositionXInPixels + width,
						actorPositionYInPixels, actorPositionYInPixels + height);
			if (connectedRight)
				TextureUtils.drawTexture(textureRight, alpha, actorPositionXInPixels, actorPositionXInPixels + width,
						actorPositionYInPixels, actorPositionYInPixels + height);
			if (connectedBottomRight)
				TextureUtils.drawTexture(textureBottomRight, alpha, actorPositionXInPixels,
						actorPositionXInPixels + width, actorPositionYInPixels, actorPositionYInPixels + height);
			if (connectedBottom)
				TextureUtils.drawTexture(textureBottom, alpha, actorPositionXInPixels, actorPositionXInPixels + width,
						actorPositionYInPixels, actorPositionYInPixels + height);
			if (connectedBottomLeft)
				TextureUtils.drawTexture(textureBottomLeft, alpha, actorPositionXInPixels,
						actorPositionXInPixels + width, actorPositionYInPixels, actorPositionYInPixels + height);
			if (connectedLeft)
				TextureUtils.drawTexture(textureLeft, alpha, actorPositionXInPixels, actorPositionXInPixels + width,
						actorPositionYInPixels, actorPositionYInPixels + height);
			if (connectedTopLeft)
				TextureUtils.drawTexture(textureTopLeft, alpha, actorPositionXInPixels, actorPositionXInPixels + width,
						actorPositionYInPixels, actorPositionYInPixels + height);
		}
	}

	@Override
	public Action getDefaultActionPerformedOnThisInWorld(Actor performer) {
		return new ActionMine(performer, this);
	}

	@Override
	public ArrayList<Action> getAllActionsPerformedOnThisInWorld(Actor performer) {
		ArrayList<Action> actions = new ArrayList<Action>();

		if (this.remainingHealth < 0)
			return actions;

		actions.add(new ActionMine(performer, this));

		actions.add(new ActionAttack(performer, this));

		return actions;

	}

	@Override
	public Vein makeCopy(Square square) {
		return new Vein(new String(name), (int) totalHealth, imageTexturePath, square, inventory.makeCopy(),
				showInventory, canShareSquare, fitsInInventory, canContainOtherObjects, blocksLineOfSight,
				persistsWhenCantBeSeen, widthRatio, heightRatio, soundHandleX, soundHandleY, soundWhenHit,
				soundWhenHitting, light, lightHandleX, lightHandlY, stackable, fireResistance, iceResistance,
				electricResistance, poisonResistance);
	}

}
