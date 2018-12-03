package com.marklynch.objects.inanimateobjects;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.actions.Action;
import com.marklynch.level.constructs.effect.EffectCurse;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.utils.Texture;
import com.marklynch.utils.TextureUtils;

public class Mirror extends GameObject {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();

	public Texture imageTextureBack;
	public Texture imageTextureFront;
	public Texture imageTextureCrack;

	public int boundsX1;
	public int boundsY1;
	public int boundsX2;
	public int boundsY2;

	public boolean hasBeenAttackedBefore = false;

	public Mirror() {
		canBePickedUp = false;

		fitsInInventory = false;
		canShareSquare = false;

		blocksLineOfSight = true;
		persistsWhenCantBeSeen = true;
		type = "Mirror";

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

		boundsX1 = (int) (this.squareGameObjectIsOn.xInGridPixels + Game.SQUARE_WIDTH * drawOffsetRatioX);
		boundsY1 = (int) (this.squareGameObjectIsOn.yInGridPixels + Game.SQUARE_HEIGHT * drawOffsetRatioY);
		boundsX2 = (int) (boundsX1 + width);
		boundsY2 = (int) (boundsY1 + height);

		imageTexture = imageTextureBack;

		super.draw1();

		int squareToMirrorX = 0;
		int squareToMirrorY = 0;

		// sqr 2 sqrs away
		for (int offsetX = -1; offsetX < 2; offsetX++) {
			squareToMirrorX = squareGameObjectIsOn.xInGrid + offsetX;
			squareToMirrorY = squareGameObjectIsOn.yInGrid + 2;
			if (squareToMirrorX >= 0 && squareToMirrorX < Game.level.squares.length && squareToMirrorY >= 0
					&& squareToMirrorY < Game.level.squares[0].length) {
				Square squareToMirror = Game.level.squares[squareToMirrorX][squareToMirrorY];

				drawSquare(squareToMirror, offsetX, 1);
			}
		}

		// sqe 1 sqr away
		for (int offsetX = -1; offsetX < 2; offsetX++) {
			squareToMirrorX = squareGameObjectIsOn.xInGrid + offsetX;
			squareToMirrorY = squareGameObjectIsOn.yInGrid + 1;
			if (squareToMirrorX >= 0 && squareToMirrorX < Game.level.squares.length && squareToMirrorY >= 0
					&& squareToMirrorY < Game.level.squares[0].length) {
				Square squareToMirror = Game.level.squares[squareToMirrorX][squareToMirrorY];
				drawSquare(squareToMirror, offsetX, 0);
			}
		}

		// object 2 sqr away
		for (int offsetX = -1; offsetX < 2; offsetX++) {
			squareToMirrorX = squareGameObjectIsOn.xInGrid + offsetX;
			squareToMirrorY = squareGameObjectIsOn.yInGrid + 2;
			if (squareToMirrorX >= 0 && squareToMirrorX < Game.level.squares.length && squareToMirrorY >= 0
					&& squareToMirrorY < Game.level.squares[0].length) {
				Square squareToMirror = Game.level.squares[squareToMirrorX][squareToMirrorY];

				for (int i = 0; i < squareToMirror.inventory.size(); i++) {
					GameObject gameObject = squareToMirror.inventory.gameObjects.get(i);
					if (gameObject instanceof Actor) {
					} else {
						drawGameObject(gameObject, offsetX, 1);
					}
				}
			}
		}
		// object 1 sqr away
		for (int offsetX = -1; offsetX < 2; offsetX++) {
			squareToMirrorX = squareGameObjectIsOn.xInGrid + offsetX;
			squareToMirrorY = squareGameObjectIsOn.yInGrid + 1;
			if (squareToMirrorX >= 0 && squareToMirrorX < Game.level.squares.length && squareToMirrorY >= 0
					&& squareToMirrorY < Game.level.squares[0].length) {
				Square squareToMirror = Game.level.squares[squareToMirrorX][squareToMirrorY];
				for (int i = 0; i < squareToMirror.inventory.size(); i++) {
					GameObject gameObject = squareToMirror.inventory.gameObjects.get(i);
					if (gameObject instanceof Actor) {
					} else {
						drawGameObject(gameObject, offsetX, 0);
					}
				}
			}
		}
		// actor 2 sqr away
		for (int offsetX = -1; offsetX < 2; offsetX++) {
			squareToMirrorX = squareGameObjectIsOn.xInGrid + offsetX;
			squareToMirrorY = squareGameObjectIsOn.yInGrid + 2;
			if (squareToMirrorX >= 0 && squareToMirrorX < Game.level.squares.length && squareToMirrorY >= 0
					&& squareToMirrorY < Game.level.squares[0].length) {
				Square squareToMirror = Game.level.squares[squareToMirrorX][squareToMirrorY];

				for (int i = 0; i < squareToMirror.inventory.size(); i++) {
					GameObject gameObject = squareToMirror.inventory.gameObjects.get(i);
					if (gameObject instanceof Actor) {
						drawActor((Actor) gameObject, offsetX, 1);
					} else {
					}
				}
			}
		}
		// actor 1 sqr away
		for (int offsetX = -1; offsetX < 2; offsetX++) {
			squareToMirrorX = squareGameObjectIsOn.xInGrid + offsetX;
			squareToMirrorY = squareGameObjectIsOn.yInGrid + 1;
			if (squareToMirrorX >= 0 && squareToMirrorX < Game.level.squares.length && squareToMirrorY >= 0
					&& squareToMirrorY < Game.level.squares[0].length) {
				Square squareToMirror = Game.level.squares[squareToMirrorX][squareToMirrorY];

				for (int i = 0; i < squareToMirror.inventory.size(); i++) {
					GameObject gameObject = squareToMirror.inventory.gameObjects.get(i);
					if (gameObject instanceof Actor) {
						drawActor((Actor) gameObject, offsetX, 0);
					} else {
					}
				}
			}
		}

		if (remainingHealth != totalHealth) {
			imageTexture = imageTextureCrack;
			super.draw1();
		}

		imageTexture = imageTextureFront;
		super.draw1();

		return true;

	}

	public void drawSquare(Square square, int offsetX, int offsetY) {

		Texture textureToDraw = square.getFloorImageTexture();
		float squarePositionX = this.squareGameObjectIsOn.xInGridPixels + Game.SQUARE_WIDTH * offsetX;
		float squarePositionY = this.squareGameObjectIsOn.yInGridPixels - Game.SQUARE_HEIGHT * offsetY;

		float alpha = 1f;
		if (primaryAnimation != null)
			alpha = primaryAnimation.alpha;
		if (!this.squareGameObjectIsOn.visibleToPlayer)
			alpha = 0.5f;

		TextureUtils.drawTextureWithinBounds(textureToDraw, alpha, squarePositionX, squarePositionY,
				squarePositionX + Game.SQUARE_WIDTH, squarePositionY + Game.SQUARE_HEIGHT, boundsX1, boundsY1, boundsX2,
				boundsY2, false, true, TextureUtils.neutralColor);

	}

	public void drawGameObject(GameObject gameObject, int offsetX, int offsetY) {

		// This was crashing when a static gameobject was put on front of it.
		// GameObject.primaryanimtion was null
		if (1 == 1)
			return;

		float actorPositionXInPixels = this.squareGameObjectIsOn.xInGridPixels + Game.SQUARE_WIDTH * offsetX
				+ Game.SQUARE_WIDTH * gameObject.drawOffsetRatioX;
		actorPositionXInPixels += gameObject.primaryAnimation.offsetX;

		float actorPositionYInPixels = 0;
		if (gameObject.flipYAxisInMirror == false) {
			actorPositionYInPixels = this.squareGameObjectIsOn.yInGridPixels
					+ Game.SQUARE_HEIGHT * gameObject.drawOffsetRatioY - Game.SQUARE_HEIGHT * offsetY;
			actorPositionYInPixels -= gameObject.primaryAnimation.offsetY;
		} else {
			actorPositionYInPixels = this.squareGameObjectIsOn.yInGridPixels
					+ Game.SQUARE_HEIGHT * gameObject.drawOffsetRatioY - Game.SQUARE_HEIGHT * offsetY
					- (Game.SQUARE_HEIGHT - gameObject.height);
			actorPositionYInPixels += gameObject.primaryAnimation.offsetY;
		}

		float alpha = 1.0f;

		// TextureUtils.skipNormals = true;

		if (primaryAnimation != null)
			alpha = primaryAnimation.alpha;
		if (!this.squareGameObjectIsOn.visibleToPlayer && gameObject != Game.level.player)
			alpha = 0.5f;

		TextureUtils.drawTextureWithinBounds(gameObject.imageTexture, alpha, actorPositionXInPixels,
				actorPositionYInPixels, actorPositionXInPixels + gameObject.width,
				actorPositionYInPixels + gameObject.height, boundsX1, boundsY1, boundsX2, boundsY2, false,
				gameObject.flipYAxisInMirror, TextureUtils.neutralColor);

	}

	public void drawActor(Actor actor, int offsetX, int offsetY) {

		float actorPositionXInPixels = this.squareGameObjectIsOn.xInGridPixels + Game.SQUARE_WIDTH * offsetX
				+ Game.SQUARE_WIDTH * actor.drawOffsetRatioX;
		float actorPositionYInPixels = this.squareGameObjectIsOn.yInGridPixels
				+ Game.SQUARE_HEIGHT * actor.drawOffsetRatioY - Game.SQUARE_HEIGHT * offsetY;
		if (actor.primaryAnimation != null) {
			actorPositionXInPixels += actor.primaryAnimation.offsetX;
			actorPositionYInPixels -= actor.primaryAnimation.offsetY;
		}

		float alpha = 1.0f;

		if (primaryAnimation != null)
			alpha = primaryAnimation.alpha;
		if (!this.squareGameObjectIsOn.visibleToPlayer && actor != Game.level.player)
			alpha = 0.5f;

		TextureUtils.drawTextureWithinBounds(actor.imageTexture, alpha, actorPositionXInPixels, actorPositionYInPixels,
				actorPositionXInPixels + actor.width, actorPositionYInPixels + actor.height, boundsX1, boundsY1,
				boundsX2, boundsY2, false, false, TextureUtils.neutralColor);

		if (actor.helmet != null && !actor.sleeping) {

			int helmetPositionXInPixels = (int) (actorPositionXInPixels);
			int helmetPositionYInPixels = (int) (actorPositionYInPixels);
			alpha = 1.0f;
			if (primaryAnimation != null)
				alpha = primaryAnimation.alpha;
			TextureUtils.drawTextureWithinBounds(actor.helmet.imageTexture, alpha, helmetPositionXInPixels,
					helmetPositionYInPixels, helmetPositionXInPixels + actor.helmet.width,
					helmetPositionYInPixels + actor.helmet.height, boundsX1, boundsY1, boundsX2, boundsY2, false, false,
					TextureUtils.neutralColor);
		} else if (actor.hairImageTexture != null) {
			int bodyArmorPositionXInPixels = (int) (actorPositionXInPixels);
			int bodyArmorPositionYInPixels = (int) (actorPositionYInPixels);
			alpha = 1.0f;
			if (primaryAnimation != null)
				alpha = primaryAnimation.alpha;
			TextureUtils.drawTextureWithinBounds(actor.hairImageTexture, alpha, bodyArmorPositionXInPixels,
					bodyArmorPositionYInPixels, bodyArmorPositionXInPixels + actor.hairImageTexture.getWidth(),
					bodyArmorPositionYInPixels + actor.hairImageTexture.getHeight(), boundsX1, boundsY1, boundsX2,
					boundsY2, false, false, TextureUtils.neutralColor);
		}

		if (actor.bodyArmor != null && !actor.sleeping) {

			int bodyArmorPositionXInPixels = (int) (actorPositionXInPixels);
			int bodyArmorPositionYInPixels = (int) (actorPositionYInPixels);
			alpha = 1.0f;
			if (primaryAnimation != null)
				alpha = primaryAnimation.alpha;
			TextureUtils.drawTextureWithinBounds(actor.bodyArmor.imageTexture, alpha, bodyArmorPositionXInPixels,
					bodyArmorPositionYInPixels, bodyArmorPositionXInPixels + actor.bodyArmor.width,
					bodyArmorPositionYInPixels + actor.bodyArmor.height, boundsX1, boundsY1, boundsX2, boundsY2, false,
					false, TextureUtils.neutralColor);
			// TextureUtils.drawTexture(actor.bodyArmor.imageTexture, alpha,
			// bodyArmorPositionXInPixels,
			// bodyArmorPositionYInPixels, bodyArmorPositionXInPixels +
			// actor.bodyArmor.width,
			// bodyArmorPositionYInPixels + actor.bodyArmor.height);
		}

		if (actor.legArmor != null && !actor.sleeping) {

			int legArmorPositionXInPixels = (int) (actorPositionXInPixels);
			int legArmorPositionYInPixels = (int) (actorPositionYInPixels);
			alpha = 1.0f;
			if (primaryAnimation != null)
				alpha = primaryAnimation.alpha;
			TextureUtils.drawTextureWithinBounds(actor.legArmor.imageTexture, alpha, legArmorPositionXInPixels,
					legArmorPositionYInPixels, legArmorPositionXInPixels + actor.legArmor.width,
					legArmorPositionYInPixels + actor.legArmor.height, boundsX1, boundsY1, boundsX2, boundsY2, false,
					false, TextureUtils.neutralColor);
		}

		// weapon
		if (actor.equipped != null && !actor.sleeping) {

			int weaponPositionXInPixels = (int) (actorPositionXInPixels + actor.rightArmHingeX
					- actor.equipped.anchorX);
			int weaponPositionYInPixels = (int) (actorPositionYInPixels + actor.handY - actor.equipped.anchorY);
			alpha = 1.0f;
			if (primaryAnimation != null)
				alpha = primaryAnimation.alpha;
			TextureUtils.drawTextureWithinBounds(actor.equipped.imageTexture, alpha, weaponPositionXInPixels,
					weaponPositionYInPixels, weaponPositionXInPixels + actor.equipped.width,
					weaponPositionYInPixels + actor.equipped.height, boundsX1, boundsY1, boundsX2, boundsY2, false,
					false, TextureUtils.neutralColor);
		}

	}

	@Override
	public void attackedBy(Object attacker, Action action) {
		if (hasBeenAttackedBefore == false && attacker instanceof Actor && remainingHealth < totalHealth) {
			((Actor) attacker).addEffect(new EffectCurse(this, (Actor) attacker, 5));
		}
		super.attackedBy(attacker, action);
		hasBeenAttackedBefore = true;
	}

	@Override
	public Mirror makeCopy(Square square, Actor owner) {
		Mirror mirror = new Mirror();
		setInstances(mirror);
		super.setAttributesForCopy(mirror, square, owner);
		mirror.imageTextureBack = this.imageTextureBack;
		mirror.imageTextureFront = this.imageTextureFront;
		mirror.imageTextureCrack = this.imageTextureCrack;
		return mirror;
	}

}
