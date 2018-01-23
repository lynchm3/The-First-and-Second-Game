package com.marklynch.objects;

import com.marklynch.Game;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;
import com.marklynch.utils.TextureUtils;

import mdesl.graphics.Texture;

public class Mirror extends GameObject {

	public Texture imageTextureBack;
	public Texture imageTextureFront;

	int boundsX1;
	int boundsY1;
	int boundsX2;
	int boundsY2;

	public Mirror() {
		canBePickedUp = false;
		showInventory = false;
		fitsInInventory = false;
		canShareSquare = false;
		canContainOtherObjects = false;
		blocksLineOfSight = true;
		persistsWhenCantBeSeen = true;
		attackable = true;
	}

	@Override
	public void draw1() {

		if (this.remainingHealth <= 0)
			return;
		if (squareGameObjectIsOn == null)
			return;
		if (hiding)
			return;

		if (!Game.fullVisiblity) {

			if (this.squareGameObjectIsOn.visibleToPlayer == false && persistsWhenCantBeSeen == false)
				return;

			if (!this.squareGameObjectIsOn.seenByPlayer)
				return;
		}

		boundsX1 = (int) (this.squareGameObjectIsOn.xInGridPixels + Game.SQUARE_WIDTH * drawOffsetX);
		boundsY1 = (int) (this.squareGameObjectIsOn.yInGridPixels + Game.SQUARE_HEIGHT * drawOffsetY);
		boundsX2 = (int) (boundsX1 + width);
		boundsY2 = (int) (boundsY1 + height);

		imageTexture = imageTextureBack;
		super.draw1();

		// 2 y sqrs away
		int squareToMirrorX = 0;
		int squareToMirrorY = 0;
		for (int offsetX = -1; offsetX < 2; offsetX++) {
			squareToMirrorX = squareGameObjectIsOn.xInGrid + offsetX;
			squareToMirrorY = squareGameObjectIsOn.yInGrid + 2;
			if (squareToMirrorX > 0 && squareToMirrorX < Game.level.squares.length
					&& squareToMirrorY < Game.level.squares[0].length) {
				Square squareToMirror = Game.level.squares[squareToMirrorX][squareToMirrorY];

				drawSquare(squareToMirror, offsetX, 1);
			}
		}

		// 1 sqr away
		for (int offsetX = -1; offsetX < 2; offsetX++) {
			squareToMirrorX = squareGameObjectIsOn.xInGrid + offsetX;
			squareToMirrorY = squareGameObjectIsOn.yInGrid + 1;
			if (squareToMirrorY < Game.level.squares[0].length) {
				Square squareToMirror = Game.level.squares[squareToMirrorX][squareToMirrorY];

				drawSquare(squareToMirror, offsetX, 0);
			}
		}

		for (int offsetX = -1; offsetX < 2; offsetX++) {
			squareToMirrorX = squareGameObjectIsOn.xInGrid + offsetX;
			squareToMirrorY = squareGameObjectIsOn.yInGrid + 2;
			if (squareToMirrorX > 0 && squareToMirrorX < Game.level.squares.length
					&& squareToMirrorY < Game.level.squares[0].length) {
				Square squareToMirror = Game.level.squares[squareToMirrorX][squareToMirrorY];

				for (int i = 0; i < squareToMirror.inventory.size(); i++) {
					GameObject gameObject = squareToMirror.inventory.gameObjects.get(i);
					if (gameObject instanceof Actor) {
						drawActor((Actor) gameObject, offsetX, 1);
					} else {
						drawGameObject(gameObject, offsetX, 1);
					}
				}
			}
		}

		// 1 sqr away
		for (int offsetX = -1; offsetX < 2; offsetX++) {
			squareToMirrorX = squareGameObjectIsOn.xInGrid + offsetX;
			squareToMirrorY = squareGameObjectIsOn.yInGrid + 1;
			if (squareToMirrorY < Game.level.squares[0].length) {
				Square squareToMirror = Game.level.squares[squareToMirrorX][squareToMirrorY];

				for (int i = 0; i < squareToMirror.inventory.size(); i++) {
					GameObject gameObject = squareToMirror.inventory.gameObjects.get(i);
					if (gameObject instanceof Actor) {
						drawActor((Actor) gameObject, offsetX, 0);
					} else {
						drawGameObject(gameObject, offsetX, 0);
					}
				}
			}
		}

		imageTexture = imageTextureFront;
		super.draw1();

	}

	public void drawSquare(Square square, int offsetX, int offsetY) {

		Texture textureToDraw = square.imageTexture;
		float squarePositionX = this.squareGameObjectIsOn.xInGridPixels + Game.SQUARE_WIDTH * offsetX;
		float squarePositionY = this.squareGameObjectIsOn.yInGridPixels - Game.SQUARE_HEIGHT * offsetY;

		float alpha = 1f;
		if (!this.squareGameObjectIsOn.visibleToPlayer)
			alpha = 0.5f;

		TextureUtils.drawTextureWithinBounds(textureToDraw, alpha, squarePositionX, squarePositionY,
				squarePositionX + Game.SQUARE_WIDTH, squarePositionY + Game.SQUARE_HEIGHT, boundsX1, boundsY1, boundsX2,
				boundsY2, false, true);

	}

	public void drawGameObject(GameObject gameObject, int offsetX, int offsetY) {

		float actorPositionXInPixels = this.squareGameObjectIsOn.xInGridPixels + Game.SQUARE_WIDTH * offsetX
				+ Game.SQUARE_WIDTH * gameObject.drawOffsetX;
		float actorPositionYInPixels = this.squareGameObjectIsOn.yInGridPixels
				+ Game.SQUARE_HEIGHT * gameObject.drawOffsetY - Game.SQUARE_HEIGHT * offsetY;

		float alpha = 1.0f;

		// TextureUtils.skipNormals = true;

		if (!this.squareGameObjectIsOn.visibleToPlayer && gameObject != Game.level.player)
			alpha = 0.5f;
		if (hiding)
			alpha = 0.5f;

		TextureUtils.drawTextureWithinBounds(gameObject.imageTexture, alpha, actorPositionXInPixels,
				actorPositionYInPixels, actorPositionXInPixels + gameObject.width,
				actorPositionYInPixels + gameObject.height, boundsX1, boundsY1, boundsX2, boundsY2, false, true);

	}

	public void drawActor(Actor actor, int offsetX, int offsetY) {

		float actorPositionXInPixels = this.squareGameObjectIsOn.xInGridPixels + Game.SQUARE_WIDTH * offsetX
				+ Game.SQUARE_WIDTH * actor.drawOffsetX;
		float actorPositionYInPixels = this.squareGameObjectIsOn.yInGridPixels + Game.SQUARE_HEIGHT * actor.drawOffsetY
				- Game.SQUARE_HEIGHT * offsetY;
		if (actor.animation != null) {
			actorPositionXInPixels += actor.animation.offsetX;
			actorPositionYInPixels -= actor.animation.offsetY;
		}

		float alpha = 1.0f;

		if (!this.squareGameObjectIsOn.visibleToPlayer && actor != Game.level.player)
			alpha = 0.5f;
		if (hiding)
			alpha = 0.5f;

		TextureUtils.drawTextureWithinBounds(actor.imageTexture, alpha, actorPositionXInPixels, actorPositionYInPixels,
				actorPositionXInPixels + actor.width, actorPositionYInPixels + actor.height, boundsX1, boundsY1,
				boundsX2, boundsY2, false, false);

		if (actor.helmet != null && !actor.sleeping) {

			int helmetPositionXInPixels = (int) (actorPositionXInPixels + actor.headAnchorX - actor.helmet.anchorX);
			int helmetPositionYInPixels = (int) (actorPositionYInPixels + actor.headAnchorY - actor.helmet.anchorY);
			alpha = 1.0f;
			TextureUtils.drawTextureWithinBounds(actor.helmet.imageTexture, alpha, helmetPositionXInPixels,
					helmetPositionYInPixels, helmetPositionXInPixels + actor.helmet.width,
					helmetPositionYInPixels + actor.helmet.height, boundsX1, boundsY1, boundsX2, boundsY2, false,
					false);
		} else if (actor.hairImageTexture != null) {
			int bodyArmorPositionXInPixels = (int) (actorPositionXInPixels + actor.bodyAnchorX - 0);
			int bodyArmorPositionYInPixels = (int) (actorPositionYInPixels + actor.bodyAnchorY - 0);
			alpha = 1.0f;
			TextureUtils.drawTextureWithinBounds(actor.hairImageTexture, alpha, bodyArmorPositionXInPixels,
					bodyArmorPositionYInPixels, bodyArmorPositionXInPixels + actor.hairImageTexture.getWidth(),
					bodyArmorPositionYInPixels + actor.hairImageTexture.getHeight(), boundsX1, boundsY1, boundsX2,
					boundsY2, false, false);
		}

		if (actor.bodyArmor != null && !actor.sleeping) {

			int bodyArmorPositionXInPixels = (int) (actorPositionXInPixels + actor.bodyAnchorX
					- actor.bodyArmor.anchorX);
			int bodyArmorPositionYInPixels = (int) (actorPositionYInPixels + actor.bodyAnchorY
					- actor.bodyArmor.anchorY);
			alpha = 1.0f;
			TextureUtils.drawTextureWithinBounds(actor.bodyArmor.imageTexture, alpha, bodyArmorPositionXInPixels,
					bodyArmorPositionYInPixels, bodyArmorPositionXInPixels + actor.bodyArmor.width,
					bodyArmorPositionYInPixels + actor.bodyArmor.height, boundsX1, boundsY1, boundsX2, boundsY2, false,
					false);
			// TextureUtils.drawTexture(actor.bodyArmor.imageTexture, alpha,
			// bodyArmorPositionXInPixels,
			// bodyArmorPositionYInPixels, bodyArmorPositionXInPixels +
			// actor.bodyArmor.width,
			// bodyArmorPositionYInPixels + actor.bodyArmor.height);
		}

		if (actor.legArmor != null && !actor.sleeping) {

			int legArmorPositionXInPixels = (int) (actorPositionXInPixels + actor.legsAnchorX - actor.legArmor.anchorX);
			int legArmorPositionYInPixels = (int) (actorPositionYInPixels + actor.legsAnchorY - actor.legArmor.anchorY);
			alpha = 1.0f;
			TextureUtils.drawTextureWithinBounds(actor.legArmor.imageTexture, alpha, legArmorPositionXInPixels,
					legArmorPositionYInPixels, legArmorPositionXInPixels + actor.legArmor.width,
					legArmorPositionYInPixels + actor.legArmor.height, boundsX1, boundsY1, boundsX2, boundsY2, false,
					false);
		}

		// weapon
		if (actor.equipped != null && !actor.sleeping) {

			int weaponPositionXInPixels = (int) (actorPositionXInPixels + actor.handAnchorX - actor.equipped.anchorX);
			int weaponPositionYInPixels = (int) (actorPositionYInPixels + actor.handAnchorY - actor.equipped.anchorY);
			alpha = 1.0f;
			TextureUtils.drawTextureWithinBounds(actor.equipped.imageTexture, alpha, weaponPositionXInPixels,
					weaponPositionYInPixels, weaponPositionXInPixels + actor.equipped.width,
					weaponPositionYInPixels + actor.equipped.height, boundsX1, boundsY1, boundsX2, boundsY2, false,
					false);
		}

	}

	@Override
	public Mirror makeCopy(Square square, Actor owner) {
		Mirror mirror = new Mirror();
		super.setAttributesForCopy(mirror, square, owner);
		mirror.imageTextureBack = this.imageTextureBack;
		mirror.imageTextureFront = this.imageTextureFront;
		return mirror;
	}

}
