package com.marklynch.objects;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;
import com.marklynch.utils.Texture;
import com.marklynch.utils.TextureUtils;

public class Wall2 extends GameObject {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();

	// 3+ connections
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
	public boolean cross;
	public boolean topLeftOuterCorner;
	public boolean topRightOuterCorner;
	public boolean bottomRightOuterCorner;
	public boolean bottomLeftOuterCorner;
	public boolean topLeftInnerCorner;
	public boolean topRightInnerCorner;
	public boolean bottomRightInnerCorner;
	public boolean bottomLeftInnerCorner;

	// 2 connections
	public boolean horizontalWall;
	public boolean verticalWall;
	public boolean topLeftCorner;
	public boolean topRightCorner;
	public boolean bottomLeftCorner;
	public boolean bottomRightCorner;

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
	public static Texture textureCross;
	public static Texture textureTopLeftOuterCorner;
	public static Texture textureTopRightOuterCorner;
	public static Texture textureBottomRightOuterCorner;
	public static Texture textureBottomLeftOuterCorner;
	public static Texture textureTopLeftInnerCorner;
	public static Texture textureTopRightInnerCorner;
	public static Texture textureBottomRightInnerCorner;
	public static Texture textureBottomLeftInnerCorner;

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

	public Wall2() {
		super();

		canBePickedUp = false;

		fitsInInventory = false;
		canShareSquare = false;

		blocksLineOfSight = true;
		persistsWhenCantBeSeen = true;

	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	public static void loadStaticImages() {
		textureFullWall = getGlobalImage("wall.png", true);
		textureFullTopWall = getGlobalImage("wall_full_top.png", true);
		textureFullRightWall = getGlobalImage("wall_full_right.png", true);
		textureFullBottomWall = getGlobalImage("wall_full_bottom.png", true);
		textureFullLeftWall = getGlobalImage("wall_full_left.png", true);
		textureHorizontalWall = getGlobalImage("wall_horizontal.png", true);
		textureVerticalWall = getGlobalImage("wall_vertical.png", true);
		textureTop = getGlobalImage("wall_top.png", true);
		textureTopRight = getGlobalImage("wall_top_right_corner.png", true);
		textureRight = getGlobalImage("wall_right.png", true);
		textureBottomRight = getGlobalImage("wall_bottom_right_corner.png", true);
		textureBottom = getGlobalImage("wall_bottom.png", true);
		textureBottomLeft = getGlobalImage("wall_bottom_left_corner.png", true);
		textureLeft = getGlobalImage("wall_left.png", true);
		textureTopLeft = getGlobalImage("wall_top_left_corner.png", true);
		textureCross = getGlobalImage("wall_cross.png", true);
		textureTopLeftOuterCorner = getGlobalImage("wall_top_left_corner_outer.png", true);
		textureTopRightOuterCorner = getGlobalImage("wall_top_right_corner_outer.png", true);
		textureBottomRightOuterCorner = getGlobalImage("wall_bottom_right_corner_outer.png", true);
		textureBottomLeftOuterCorner = getGlobalImage("wall_bottom_left_corner_outer.png", true);
		textureTopLeftInnerCorner = getGlobalImage("wall_top_left_corner_inner.png", true);
		textureTopRightInnerCorner = getGlobalImage("wall_top_right_corner_inner.png", true);
		textureBottomRightInnerCorner = getGlobalImage("wall_bottom_right_corner_inner.png", true);
		textureBottomLeftInnerCorner = getGlobalImage("wall_bottom_left_corner_inner.png", true);
	}

	@Override
	public void draw1() {

		if (this.remainingHealth <= 0)
			return;
		super.draw1();

		if (!Game.fullVisiblity) {
			if (!this.squareGameObjectIsOn.seenByPlayer)
				return;

			if (this.squareGameObjectIsOn.visibleToPlayer == false && persistsWhenCantBeSeen == false)
				return;
		}

		// Draw object
		if (squareGameObjectIsOn != null) {

			float alpha = 1.0f;
			if (primaryAnimation != null)
				alpha = primaryAnimation.alpha;

			// 8
			if (fullWall) {
				TextureUtils.drawTexture(textureFullWall, alpha, drawX1, drawY1, drawX2, drawY2);
				return;
			}

			// 7
			else if (topLeftInnerCorner) {
				TextureUtils.drawTexture(textureTopLeftInnerCorner, alpha, drawX1, drawY1, drawX2, drawY2);

			}

			else if (topRightInnerCorner) {
				TextureUtils.drawTexture(textureTopRightInnerCorner, alpha, drawX1, drawY1, drawX2, drawY2);

			}

			else if (bottomRightInnerCorner) {
				TextureUtils.drawTexture(textureBottomRightInnerCorner, alpha, drawX1, drawY1, drawX2, drawY2);

			}

			else if (bottomLeftInnerCorner) {
				TextureUtils.drawTexture(textureBottomLeftInnerCorner, alpha, drawX1, drawY1, drawX2, drawY2);

			}

			// 5
			else if (fullLeftWall) {
				TextureUtils.drawTexture(textureFullLeftWall, alpha, drawX1, drawY1, drawX2, drawY2);
			}

			else if (fullRightWall) {
				TextureUtils.drawTexture(textureFullRightWall, alpha, drawX1, drawY1, drawX2, drawY2);
			}

			else if (fullTopWall) {
				TextureUtils.drawTexture(textureFullTopWall, alpha, drawX1, drawY1, drawX2, drawY2);
			}

			else if (fullBottomWall) {
				TextureUtils.drawTexture(textureFullBottomWall, alpha, drawX1, drawY1, drawX2, drawY2);
			}

			// 4
			else if (cross) {
				TextureUtils.drawTexture(textureCross, alpha, drawX1, drawY1, drawX2, drawY2);

			}

			// 3
			else if (topLeftOuterCorner) {
				TextureUtils.drawTexture(textureTopLeftOuterCorner, alpha, drawX1, drawY1, drawX2, drawY2);

			}

			else if (topRightOuterCorner) {
				TextureUtils.drawTexture(textureTopRightOuterCorner, alpha, drawX1, drawY1, drawX2, drawY2);

			}

			else if (bottomRightOuterCorner) {
				TextureUtils.drawTexture(textureBottomRightOuterCorner, alpha, drawX1, drawY1, drawX2, drawY2);

			}

			else if (bottomLeftOuterCorner) {
				TextureUtils.drawTexture(textureBottomLeftOuterCorner, alpha, drawX1, drawY1, drawX2, drawY2);

			}

			// 2
			else if (horizontalWall) {
				TextureUtils.drawTexture(textureHorizontalWall, alpha, drawX1, drawY1, drawX2, drawY2);
			}

			else if (verticalWall) {
				TextureUtils.drawTexture(textureVerticalWall, alpha, drawX1, drawY1, drawX2, drawY2);
			}

			else if (topLeftCorner) {
				TextureUtils.drawTexture(textureTopLeft, alpha, drawX1, drawY1, drawX2, drawY2);
			}

			else if (topRightCorner) {
				TextureUtils.drawTexture(textureTopRight, alpha, drawX1, drawY1, drawX2, drawY2);
			}

			else if (bottomLeftCorner) {
				TextureUtils.drawTexture(textureBottomLeft, alpha, drawX1, drawY1, drawX2, drawY2);
			}

			else if (bottomRightCorner) {
				TextureUtils.drawTexture(textureBottomRight, alpha, drawX1, drawY1, drawX2, drawY2);
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

			Game.flush();
		}
	}

	@Override
	public Wall2 makeCopy(Square square, Actor owner) {
		Wall2 wall = new Wall2();
		setInstances(wall);
		super.setAttributesForCopy(wall, square, owner);
		if (wall.squareGameObjectIsOn != null) {
			wall.drawX1 = (int) (wall.squareGameObjectIsOn.xInGridPixels + wall.drawOffsetRatioX);
			wall.drawX2 = (int) (wall.drawX1 + wall.width);
			wall.drawY1 = (int) (wall.squareGameObjectIsOn.yInGridPixels + wall.drawOffsetRatioY);
			wall.drawY2 = (int) (wall.drawY1 + wall.height);
		}
		return wall;
	}

	public void checkIfFullWall() {
		// 8
		if (fullWall = connectedTop && connectedTopRight && connectedRight && connectedBottomRight && connectedBottom
				&& connectedBottomLeft && connectedLeft && connectedTopLeft)
			return;

		// 7
		if (topLeftInnerCorner = connectedTop && connectedTopRight && connectedRight && connectedBottom
				&& connectedBottomLeft && connectedLeft && connectedTopLeft)
			return;

		if (topRightInnerCorner = connectedTop && connectedTopRight && connectedRight && connectedBottomRight
				&& connectedBottom && connectedLeft && connectedTopLeft)
			return;

		if (bottomRightInnerCorner = connectedTop && connectedTopRight && connectedRight && connectedBottomRight
				&& connectedBottom && connectedBottomLeft && connectedLeft)
			return;

		if (bottomLeftInnerCorner = connectedTop && connectedRight && connectedBottomRight && connectedBottom
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

		// 4
		if (cross = connectedTop && connectedBottom && connectedLeft && connectedRight)
			return;

		// 3
		if (topLeftOuterCorner = connectedRight && connectedBottom && connectedBottomRight)
			return;

		if (topRightOuterCorner = connectedLeft && connectedBottom && connectedBottomLeft)
			return;

		if (bottomRightOuterCorner = connectedLeft && connectedTop && connectedTopLeft)
			return;

		if (bottomLeftOuterCorner = connectedRight && connectedTop && connectedTopRight)
			return;

		// 2
		if (horizontalWall = connectedRight && connectedLeft)
			return;

		if (verticalWall = connectedTop && connectedBottom)
			return;

		if (topLeftCorner = connectedRight && connectedBottom)
			return;

		if (topRightCorner = connectedLeft && connectedBottom)
			return;

		if (bottomLeftCorner = connectedRight && connectedTop)
			return;

		if (bottomRightCorner = connectedLeft && connectedTop)
			return;

	}

}
