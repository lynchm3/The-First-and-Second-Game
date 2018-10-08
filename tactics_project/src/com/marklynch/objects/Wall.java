package com.marklynch.objects;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.squares.Square;
//import com.marklynch.objects.actions.ActionPickUp;
import com.marklynch.objects.units.Actor;
import com.marklynch.utils.Texture;
import com.marklynch.utils.TextureUtils;

//import mdesl.graphics.Texture;

public class Wall extends GameObject {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();

	public boolean fullWall;
	public boolean fullRightWall;
	public boolean fullLeftWall;
	public boolean fullTopWall;
	public boolean fullBottomWall;
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
	public static Texture textureTop;
	public static Texture textureTopRight;
	public static Texture textureRight;
	public static Texture textureBottomRight;
	public static Texture textureBottom;
	public static Texture textureBottomLeft;
	public static Texture textureLeft;
	public static Texture textureTopLeft;

	public float drawX1, drawX2, drawY1, drawY2;

	public float fullLeftDrawX1, fullLeftDrawX2, fullLeftDrawY1, fullLeftDrawY2;
	public float fullRightDrawX1, fullRightDrawX2, fullRightDrawY1, fullRightDrawY2;
	public float fullTopDrawX1, fullTopDrawX2, fullTopDrawY1, fullTopDrawY2;
	public float fullBottomDrawX1, fullBottomDrawX2, fullBottomDrawY1, fullBottomDrawY2;

	public float topLeftDrawX1, topLeftDrawX2, topLeftDrawY1, topLeftDrawY2;
	public float topDrawX1, topDrawX2, topDrawY1, topDrawY2;
	public float topRightDrawX1, topRightDrawX2, topRightDrawY1, topRightDrawY2;
	public float rightDrawX1, rightDrawX2, rightDrawY1, rightDrawY2;
	public float bottomRightDrawX1, bottomRightDrawX2, bottomRightDrawY1, bottomRightDrawY2;
	public float bottomDrawX1, bottomDrawX2, bottomDrawY1, bottomDrawY2;
	public float bottomLeftDrawX1, bottomLeftDrawX2, bottomLeftDrawY1, bottomLeftDrawY2;
	public float leftDrawX1, leftDrawX2, leftDrawY1, leftDrawY2;

	// public float halfWidth = Game.HALF_SQUARE_WIDTH;
	// public float halfHeight = Game.HALF_SQUARE_HEIGHT;
	public float quarterWidth = Game.SQUARE_WIDTH / 4;
	public float quarterHeight = Game.SQUARE_HEIGHT / 4;

	public Wall() {
		super();

		canBePickedUp = false;

		fitsInInventory = false;
		canShareSquare = false;

		blocksLineOfSight = true;
		persistsWhenCantBeSeen = true;
		type = "Wall";
	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	public void initWall() {

		if (squareGameObjectIsOn != null) {

			halfWidth = Game.HALF_SQUARE_WIDTH;
			halfHeight = Game.HALF_SQUARE_HEIGHT;
			quarterWidth = Game.SQUARE_WIDTH / 4;
			quarterHeight = Game.SQUARE_HEIGHT / 4;

			drawX1 = (int) (squareGameObjectIsOn.xInGrid * (int) Game.SQUARE_WIDTH + drawOffsetRatioX);
			drawX2 = (int) (drawX1 + width);
			drawY1 = (int) (squareGameObjectIsOn.yInGrid * (int) Game.SQUARE_HEIGHT + drawOffsetRatioY);
			drawY2 = (int) (drawY1 + height);

			fullLeftDrawX1 = drawX1;
			fullLeftDrawX2 = drawX2 - quarterWidth;
			fullLeftDrawY1 = drawY1;
			fullLeftDrawY2 = drawY2;

			fullRightDrawX1 = drawX1 + quarterWidth;
			fullRightDrawX2 = drawX2;
			fullRightDrawY1 = drawY1;
			fullRightDrawY2 = drawY2;

			fullTopDrawX1 = drawX1;
			fullTopDrawX2 = drawX2;
			fullTopDrawY1 = drawY1;
			fullTopDrawY2 = drawY2 - quarterHeight;

			fullBottomDrawX1 = drawX1;
			fullBottomDrawX2 = drawX2;
			fullBottomDrawY1 = drawY1 + quarterHeight;
			fullBottomDrawY2 = drawY2;

			topLeftDrawX1 = drawX1;
			topLeftDrawX2 = drawX1 + quarterWidth;
			topLeftDrawY1 = drawY1;
			topLeftDrawY2 = drawY1 + quarterHeight;

			topDrawX1 = drawX1 + quarterWidth;
			topDrawX2 = drawX2 - quarterWidth;
			topDrawY1 = drawY1;
			topDrawY2 = drawY1 + halfHeight;

			topRightDrawX1 = drawX2 - quarterWidth;
			topRightDrawX2 = drawX2;
			topRightDrawY1 = drawY1;
			topRightDrawY2 = drawY1 + quarterHeight;

			rightDrawX1 = drawX1 + halfWidth;
			rightDrawX2 = drawX2;
			rightDrawY1 = drawY1 + quarterHeight;
			rightDrawY2 = drawY2 - quarterHeight;

			bottomRightDrawX1 = drawX2 - quarterWidth;
			bottomRightDrawX2 = drawX2;
			bottomRightDrawY1 = drawY2 - quarterHeight;
			bottomRightDrawY2 = drawY2;

			bottomDrawX1 = drawX1 + quarterWidth;
			bottomDrawX2 = drawX2 - quarterWidth;
			bottomDrawY1 = drawY2 - halfHeight;
			bottomDrawY2 = drawY2;

			bottomLeftDrawX1 = drawX1;
			bottomLeftDrawX2 = drawX1 + quarterWidth;
			bottomLeftDrawY1 = drawY2 - quarterHeight;
			bottomLeftDrawY2 = drawY2;

			leftDrawX1 = drawX1;
			leftDrawX2 = drawX1 + halfWidth;
			leftDrawY1 = drawY1 + quarterHeight;
			leftDrawY2 = drawY2 - quarterHeight;

		}

	}

	public static void loadStaticImages() {
		textureFullWall = getGlobalImage("wall.png", true);
		textureFullTopWall = getGlobalImage("wall_full_top.png", true);
		textureFullRightWall = getGlobalImage("wall_full_right.png", true);
		textureFullBottomWall = getGlobalImage("wall_full_bottom.png", true);
		textureFullLeftWall = getGlobalImage("wall_full_left.png", true);
		textureTop = getGlobalImage("wall_top.png", true);
		textureTopRight = getGlobalImage("wall_top_right.png", true);
		textureRight = getGlobalImage("wall_right.png", true);
		textureBottomRight = getGlobalImage("wall_bottom_right.png", true);
		textureBottom = getGlobalImage("wall_bottom.png", true);
		textureBottomLeft = getGlobalImage("wall_bottom_left.png", true);
		textureLeft = getGlobalImage("wall_left.png", true);
		textureTopLeft = getGlobalImage("wall_top_left.png", true);
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

			// float alpha = 1.0f;

			if (fullWall) {
				TextureUtils.drawTexture(textureFullWall, drawX1, drawY1, drawX2, drawY2);
				return;
			}

			if (fullLeftWall) {
				TextureUtils.drawTexture(textureFullLeftWall, fullLeftDrawX1, fullLeftDrawY1, fullLeftDrawX2,
						fullLeftDrawY2);
				return;
			}

			if (fullRightWall) {
				TextureUtils.drawTexture(textureFullRightWall, fullRightDrawX1, fullRightDrawY1, fullRightDrawX2,
						fullRightDrawY2);
				return;
			}

			if (fullTopWall) {
				TextureUtils.drawTexture(textureFullTopWall, fullTopDrawX1, fullTopDrawY1, fullTopDrawX2,
						fullTopDrawY2);
				return;
			}

			if (fullBottomWall) {
				TextureUtils.drawTexture(textureFullBottomWall, fullBottomDrawX1, fullBottomDrawY1, fullBottomDrawX2,
						fullBottomDrawY2);
				return;
			}

			if (connectedTop)
				TextureUtils.drawTexture(textureTop, topDrawX1, topDrawY1, topDrawX2, topDrawY2);
			if (connectedTopRight)
				TextureUtils.drawTexture(textureTopRight, topRightDrawX1, topRightDrawY1, topRightDrawX2,
						topRightDrawY2);
			if (connectedRight)
				TextureUtils.drawTexture(textureRight, rightDrawX1, rightDrawY1, rightDrawX2, rightDrawY2);
			if (connectedBottomRight)
				TextureUtils.drawTexture(textureBottomRight, bottomRightDrawX1, bottomRightDrawY1, bottomRightDrawX2,
						bottomRightDrawY2);
			if (connectedBottom)
				TextureUtils.drawTexture(textureBottom, bottomDrawX1, bottomDrawY1, bottomDrawX2, bottomDrawY2);
			if (connectedBottomLeft)
				TextureUtils.drawTexture(textureBottomLeft, bottomLeftDrawX1, bottomLeftDrawY1, bottomLeftDrawX2,
						bottomLeftDrawY2);
			if (connectedLeft)
				TextureUtils.drawTexture(textureLeft, leftDrawX1, leftDrawY1, leftDrawX2, leftDrawY2);
			if (connectedTopLeft)
				TextureUtils.drawTexture(textureTopLeft, topLeftDrawX1, topLeftDrawY1, topLeftDrawX2, topLeftDrawY2);
		}
	}

	@Override
	public Wall makeCopy(Square square, Actor owner) {
		Wall wall = new Wall();
		setInstances(wall);
		super.setAttributesForCopy(wall, square, owner);
		// if (wall.squareGameObjectIsOn != null) {
		// wall.drawX1 = (int) (wall.squareGameObjectIsOn.xInGridPixels +
		// wall.drawOffsetRatioX);
		// wall.drawX2 = (int) (wall.drawX1 + wall.width);
		// wall.drawY1 = (int) (wall.squareGameObjectIsOn.yInGridPixels +
		// wall.drawOffsetRatioY);
		// wall.drawY2 = (int) (wall.drawY1 + wall.height);
		// }
		wall.initWall();
		return wall;
	}

	public void checkIfFullWall() {
		fullWall = connectedTop && connectedTopRight && connectedRight && connectedBottomRight && connectedBottom
				&& connectedBottomLeft && connectedLeft && connectedTopLeft;

		if (fullWall == false)
			fullWall = !connectedTop && !connectedTopRight && !connectedRight && !connectedBottomRight
					&& !connectedBottom && !connectedBottomLeft && !connectedLeft && !connectedTopLeft;

		fullLeftWall = connectedTop && connectedBottom && connectedBottomLeft && connectedLeft && connectedTopLeft;

		fullRightWall = connectedTop && connectedTopRight && connectedRight && connectedBottomRight && connectedBottom;

		fullTopWall = connectedTop && connectedTopRight && connectedRight && connectedLeft && connectedTopLeft;

		fullBottomWall = connectedRight && connectedBottomRight && connectedBottom && connectedBottomLeft
				&& connectedLeft;

	}

}