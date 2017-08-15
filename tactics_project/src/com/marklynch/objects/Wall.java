package com.marklynch.objects;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import com.marklynch.Game;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;
import com.marklynch.utils.TextureUtils;

import mdesl.graphics.Color;
import mdesl.graphics.Texture;

public class Wall extends GameObject {

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
	public boolean cross;
	public boolean topLeftOuterCorner;
	public boolean topRightOuterCorner;
	public boolean bottomRightOuterCorner;
	public boolean bottomLeftOuterCorner;
	public boolean topLeftInnerCorner;
	public boolean topRightInnerCorner;
	public boolean bottomRightInnerCorner;
	public boolean bottomLeftInnerCorner;

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

	public Wall(String name, int health, String imagePath, Square squareGameObjectIsOn, Inventory inventory,

			float widthRatio, float heightRatio, float drawOffsetX, float drawOffsetY, float soundWhenHit,
			float soundWhenHitting, float soundDampening, Color light, float lightHandleX, float lightHandlY,
			boolean stackable, float fireResistance, float waterResistance, float electricResistance,
			float poisonResistance, float slashResistance, float weight, Actor owner) {
		super(name, health, imagePath, squareGameObjectIsOn, inventory, widthRatio, heightRatio, drawOffsetX,
				drawOffsetY, soundWhenHit, soundWhenHitting, soundDampening, light, lightHandleX, lightHandlY,
				stackable, fireResistance, waterResistance, electricResistance, poisonResistance, slashResistance,
				weight, owner);
		if (squareGameObjectIsOn != null) {
			drawX1 = (int) (squareGameObjectIsOn.xInGrid * (int) Game.SQUARE_WIDTH + drawOffsetX);
			drawX2 = (int) (drawX1 + width);
			drawY1 = (int) (squareGameObjectIsOn.yInGrid * (int) Game.SQUARE_HEIGHT + drawOffsetY);
			drawY2 = (int) (drawY1 + height);
		}

		canBePickedUp = false;
		showInventory = false;
		fitsInInventory = false;
		canShareSquare = false;
		canContainOtherObjects = false;
		blocksLineOfSight = true;
		persistsWhenCantBeSeen = true;
		attackable = true;
	}

	public static void loadStaticImages() {
		textureFullWall = getGlobalImage("wall.png");
		textureFullTopWall = getGlobalImage("wall_full_top.png");
		textureFullRightWall = getGlobalImage("wall_full_right.png");
		textureFullBottomWall = getGlobalImage("wall_full_bottom.png");
		textureFullLeftWall = getGlobalImage("wall_full_left.png");
		textureHorizontalWall = getGlobalImage("wall_horizontal.png");
		textureVerticalWall = getGlobalImage("wall_vertical.png");
		textureTop = getGlobalImage("wall_top.png");
		textureTopRight = getGlobalImage("wall_top_right.png");
		textureRight = getGlobalImage("wall_right.png");
		textureBottomRight = getGlobalImage("wall_bottom_right.png");
		textureBottom = getGlobalImage("wall_bottom.png");
		textureBottomLeft = getGlobalImage("wall_bottom_left.png");
		textureLeft = getGlobalImage("wall_left.png");
		textureTopLeft = getGlobalImage("wall_top_left.png");
		textureCross = getGlobalImage("wall_cross.png");
		textureTopLeftOuterCorner = getGlobalImage("wall_top_left_corner_outer.png");
		textureTopRightOuterCorner = getGlobalImage("wall_top_right_corner_outer.png");
		textureBottomRightOuterCorner = getGlobalImage("wall_bottom_right_corner_outer.png");
		textureBottomLeftOuterCorner = getGlobalImage("wall_bottom_left_corner_outer.png");
		textureTopLeftInnerCorner = getGlobalImage("wall_top_left_corner_inner.png");
		textureTopRightInnerCorner = getGlobalImage("wall_top_right_corner_inner.png");
		textureBottomRightInnerCorner = getGlobalImage("wall_bottom_right_corner_inner.png");
		textureBottomLeftInnerCorner = getGlobalImage("wall_bottom_left_corner_inner.png");
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

			Game.activeBatch.flush();
		}
	}

	@Override
	public Wall makeCopy(Square square, Actor owner) {
		return new Wall(new String(name), (int) totalHealth, imageTexturePath, square, new Inventory(),

				widthRatio, heightRatio, drawOffsetX, drawOffsetY, soundWhenHit, soundWhenHitting, soundDampening,
				light, lightHandleX, lightHandlY, stackable, fireResistance, waterResistance, electricResistance,
				poisonResistance, slashResistance, weight, owner);
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

	}

}
