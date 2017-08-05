package com.marklynch.objects;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actions.ActionMine;
import com.marklynch.objects.actions.ActionPickUp;
import com.marklynch.objects.units.Actor;
import com.marklynch.utils.TextureUtils;

import mdesl.graphics.Color;
import mdesl.graphics.Texture;

public class Vein extends Wall {

	public boolean fullWall;
	public boolean fullRightWall;
	public boolean fullLeftWall;
	public boolean fullTopWall;
	public boolean fullBottomWall;
	public boolean horizontalWall;
	public boolean verticalWall;
	public boolean connectedTop;
	public boolean connectedTopRight;
	public boolean connectedRight;
	public boolean connectedBottomRight;
	public boolean connectedBottom;
	public boolean connectedBottomLeft;
	public boolean connectedLeft;
	public boolean connectedTopLeft;

	public static Texture textureFullWall;
	public static Texture textureFullTopWall;
	public static Texture textureFullRightWall;
	public static Texture textureFullBottomWall;
	public static Texture textureFullLeftWall;
	public static Texture textureHorizontalWall;
	public static Texture textureVerticalWall;
	public static Texture textureTop;
	public static Texture textureTopRight;
	public static Texture textureRight;
	public static Texture textureBottomRight;
	public static Texture textureBottom;
	public static Texture textureBottomLeft;
	public static Texture textureLeft;
	public static Texture textureTopLeft;

	public float drawX1, drawX2, drawY1, drawY2;

	public float topLeftDrawX1, topLeftDrawX2, topLeftDrawY1, topLeftDrawY2;
	public float topDrawX1, topDrawX2, topDrawY1, topDrawY2;
	public float topRightDrawX1, topRightDrawX2, topRightDrawY1, topRightDrawY2;
	public float rightDrawX1, rightDrawX2, rightDrawY1, rightDrawY2;
	public float bottomRightDrawX1, bottomRightDrawX2, bottomRightDrawY1, bottomRightDrawY2;
	public float bottomDrawX1, bottomDrawX2, bottomDrawY1, bottomDrawY2;
	public float bottomLeftDrawX1, bottomLeftDrawX2, bottomLeftDrawY1, bottomLeftDrawY2;
	public float leftDrawX1, leftDrawX2, leftDrawY1, leftDrawY2;

	public float halfWidth = Game.HALF_SQUARE_WIDTH;
	public float halfHeight = Game.HALF_SQUARE_HEIGHT;
	public float quarterWidth = Game.SQUARE_WIDTH / 4;
	public float quarterHeight = Game.SQUARE_HEIGHT / 4;

	public Vein(String name, int health, String imagePath, Square squareGameObjectIsOn, Inventory inventory,
			boolean showInventory, boolean canShareSquare, boolean fitsInInventory, boolean canContainOtherObjects,
			boolean blocksLineOfSight, boolean persistsWhenCantBeSeen, float widthRatio, float heightRatio,
			float drawOffsetX, float drawOffsetY, float soundWhenHit, float soundWhenHitting, float soundDampening,
			Color light, float lightHandleX, float lightHandlY, boolean stackable, float fireResistance,
			float waterResistance, float electricResistance, float poisonResistance, float slashResistance, float weight, Actor owner) {
		super(name, health, imagePath, squareGameObjectIsOn, inventory, showInventory, canShareSquare, fitsInInventory,
				canContainOtherObjects, blocksLineOfSight, persistsWhenCantBeSeen, widthRatio, heightRatio, drawOffsetX,
				drawOffsetY, soundWhenHit, soundWhenHitting, soundDampening, light, lightHandleX, lightHandlY,
				stackable, fireResistance, waterResistance, electricResistance, poisonResistance, slashResistance, weight, owner);
		if (squareGameObjectIsOn != null) {
			drawX1 = (int) (squareGameObjectIsOn.xInGrid * (int) Game.SQUARE_WIDTH + drawOffsetX);
			drawX2 = (int) (drawX1 + width);
			drawY1 = (int) (squareGameObjectIsOn.yInGrid * (int) Game.SQUARE_HEIGHT + drawOffsetY);
			drawY2 = (int) (drawY1 + height);

		}
	}

	public static void loadStaticImages() {
		textureFullWall = getGlobalImage("vein.png");
		textureFullTopWall = getGlobalImage("vein_full_top.png");
		textureFullRightWall = getGlobalImage("vein_full_right.png");
		textureFullBottomWall = getGlobalImage("vein_full_bottom.png");
		textureFullLeftWall = getGlobalImage("vein_full_left.png");
		textureTop = getGlobalImage("vein_top.png");
		textureTopRight = getGlobalImage("vein_top_right.png");
		textureRight = getGlobalImage("vein_right.png");
		textureBottomRight = getGlobalImage("vein_bottom_right.png");
		textureBottom = getGlobalImage("vein_bottom.png");
		textureBottomLeft = getGlobalImage("vein_bottom_left.png");
		textureLeft = getGlobalImage("vein_left.png");
		textureTopLeft = getGlobalImage("vein_top_left.png");
	}

	@Override
	public void draw1() {

		if (this.remainingHealth <= 0)
			return;

		if (!Game.fullVisiblity) {
			if (!this.squareGameObjectIsOn.seenByPlayer)
				return;

			if (this.squareGameObjectIsOn.visibleToPlayer == false && persistsWhenCantBeSeen == false)
				return;
		}

		// Draw object
		if (squareGameObjectIsOn != null) {

			float alpha = 1.0f;

			// 8
			if (fullWall) {
				TextureUtils.drawTexture(textureFullWall, alpha, drawX1, drawY1, drawX2, drawY2);
				return;
			}

			// 5
			if (fullLeftWall) {
				TextureUtils.drawTexture(textureFullLeftWall, alpha, drawX1, drawY1, drawX2, drawY2);
				return;
			}

			if (fullRightWall) {
				TextureUtils.drawTexture(textureFullRightWall, alpha, drawX1, drawY1, drawX2, drawY2);
				return;
			}

			if (fullTopWall) {
				TextureUtils.drawTexture(textureFullTopWall, alpha, drawX1, drawY1, drawX2, drawY2);
				return;
			}

			if (fullBottomWall) {
				TextureUtils.drawTexture(textureFullBottomWall, alpha, drawX1, drawY1, drawX2, drawY2);
				return;
			}

			// 2
			if (horizontalWall) {
				TextureUtils.drawTexture(textureHorizontalWall, alpha, drawX1, drawY1, drawX2, drawY2);
				return;
			}

			if (verticalWall) {
				TextureUtils.drawTexture(textureVerticalWall, alpha, drawX1, drawY1, drawX2, drawY2);
				return;
			}

			// if (connectedTop)
			// TextureUtils.drawTexture(textureTop, alpha, topDrawX1, topDrawX2,
			// topDrawY1, topDrawY2);
			// if (connectedTopRight)
			// TextureUtils.drawTexture(textureTopRight, alpha, topRightDrawX1,
			// topRightDrawX2, topRightDrawY1,
			// topRightDrawY2);
			// if (connectedRight)
			// TextureUtils.drawTexture(textureRight, alpha, rightDrawX1,
			// rightDrawX2, rightDrawY1, rightDrawY2);
			// if (connectedBottomRight)
			// TextureUtils.drawTexture(textureBottomRight, alpha,
			// bottomRightDrawX1, bottomRightDrawX2,
			// bottomRightDrawY1, bottomRightDrawY2);
			// if (connectedBottom)
			// TextureUtils.drawTexture(textureBottom, alpha, bottomDrawX1,
			// bottomDrawX2, bottomDrawY1, bottomDrawY2);
			// if (connectedBottomLeft)
			// TextureUtils.drawTexture(textureBottomLeft, alpha,
			// bottomLeftDrawX1, bottomLeftDrawX2, bottomLeftDrawY1,
			// bottomLeftDrawY2);
			// if (connectedLeft)
			// TextureUtils.drawTexture(textureLeft, alpha, leftDrawX1,
			// leftDrawX2, leftDrawY1, leftDrawY2);
			// if (connectedTopLeft)
			// TextureUtils.drawTexture(textureTopLeft, alpha, topLeftDrawX1,
			// topLeftDrawX2, topLeftDrawY1,
			// topLeftDrawY2);
		}
	}

	// @Override
	// public Action getDefaultActionPerformedOnThisInWorld(Actor performer) {
	// return new ActionMine(performer, this);
	// }

	@Override
	public Action getSecondaryActionPerformedOnThisInWorld(Actor performer) {
		return new ActionMine(performer, this);
	}

	@Override
	public ArrayList<Action> getAllActionsPerformedOnThisInWorld(Actor performer) {
		ArrayList<Action> actions = new ArrayList<Action>();

		if (this.remainingHealth <= 0)
			return actions;

		actions.add(new ActionMine(performer, this));

		actions.addAll(super.getAllActionsPerformedOnThisInWorld(performer));

		Action pickupAction = null;
		for (Action action : actions) {
			if (action instanceof ActionPickUp) {
				pickupAction = action;
			}
		}
		actions.remove(pickupAction);

		return actions;

	}

	@Override
	public Vein makeCopy(Square square, Actor owner) {
		return new Vein(new String(name), (int) totalHealth, imageTexturePath, square, new Inventory(), showInventory,
				canShareSquare, fitsInInventory, canContainOtherObjects, blocksLineOfSight, persistsWhenCantBeSeen,
				widthRatio, heightRatio, drawOffsetX, drawOffsetY, soundWhenHit, soundWhenHitting, soundDampening,
				light, lightHandleX, lightHandlY, stackable, fireResistance, waterResistance, electricResistance,
				poisonResistance, slashResistance, weight, owner);
	}

	@Override
	public void checkIfFullWall() {

		fullWall = true;
		if (fullWall)
			return;

		// 8
		if (fullWall = connectedTop && connectedTopRight && connectedRight && connectedBottomRight && connectedBottom
				&& connectedBottomLeft && connectedLeft && connectedTopLeft)
			return;

		// 5
		if (fullLeftWall = connectedTop && connectedBottom && connectedBottomLeft && connectedLeft && connectedTopLeft)
			return;

		if (fullRightWall = connectedTop && connectedTopRight && connectedRight && connectedBottomRight
				&& connectedBottom)
			return;

		if (fullTopWall = connectedTop && connectedTopRight && connectedRight && connectedLeft && connectedTopLeft)
			return;

		if (fullBottomWall = connectedRight && connectedBottomRight && connectedBottom && connectedBottomLeft
				&& connectedLeft)
			return;

		// 2
		if (horizontalWall = connectedRight && connectedLeft)
			return;

		if (verticalWall = connectedTop && connectedBottom)
			return;

	}

}
