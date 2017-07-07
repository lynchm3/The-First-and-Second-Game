package com.marklynch.objects;

import java.awt.geom.AffineTransform;

import com.marklynch.Game;
import com.marklynch.level.Square;
import com.marklynch.objects.units.Actor;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.TriangleUtils;

import mdesl.graphics.Color;

public class GameObjectExploder extends GameObject {

	public GameObjectExploder(String name, int health, String imagePath, Square squareGameObjectIsOn,
			Inventory inventory, boolean showInventory, boolean canShareSquare, boolean fitsInInventory,
			boolean canContainOtherObjects, boolean blocksLineOfSight, boolean persistsWhenCantBeSeen, float widthRatio,
			float heightRatio, float drawOffsetX, float drawOffsetY, float soundWhenHit, float soundWhenHitting,
			float soundDampening, Color light, float lightHandleX, float lightHandlY, boolean stackable,
			float fireResistance, float iceResistance, float electricResistance, float poisonResistance, float weight,
			Actor owner) {
		super(name, health, imagePath, squareGameObjectIsOn, inventory, showInventory, canShareSquare, fitsInInventory,
				canContainOtherObjects, blocksLineOfSight, persistsWhenCantBeSeen, true, widthRatio, heightRatio,
				drawOffsetX, drawOffsetY, soundWhenHit, soundWhenHitting, soundDampening, light, lightHandleX,
				lightHandlY, stackable, fireResistance, iceResistance, electricResistance, poisonResistance, weight,
				owner);
	}

	public SquarePiece[] squarePieces;

	public TrianglePiece[] trianglePieces;

	@Override
	public boolean checkIfDestroyed(GameObject attacker) {

		boolean destroyed = super.checkIfDestroyed(attacker);

		if (destroyed) {
			explode();
		}

		return destroyed;
	}

	public void explode() {
		// createTrianglePieces(4);
		createSquarePieces(4);
	}

	public void createSquarePieces(int root) {
		int pieceCount = root * root;
		squarePieces = new SquarePiece[pieceCount];
		float pieceWidth = this.imageTexture.getWidth() / root;
		float pieceHeight = this.imageTexture.getHeight() / root;

		float positionXInPixels = this.squareGameObjectIsOn.xInGrid * (int) Game.SQUARE_WIDTH;
		float positionYInPixels = this.squareGameObjectIsOn.yInGrid * (int) Game.SQUARE_HEIGHT;

		for (float i = 0; i < root; i++) {
			for (float j = 0; j < root; j++) {

				SquarePiece squarePiece = squarePieces[(int) (i * root + j)] = new SquarePiece();
				squarePiece.x1 = i * pieceWidth + positionXInPixels;
				squarePiece.x2 = i * pieceWidth + pieceWidth + positionXInPixels;
				squarePiece.x3 = i * pieceWidth + pieceWidth + positionXInPixels;
				squarePiece.x4 = i * pieceWidth + positionXInPixels;
				squarePiece.y1 = j * pieceHeight + positionYInPixels;
				squarePiece.y2 = j * pieceHeight + positionYInPixels;
				squarePiece.y3 = j * pieceHeight + pieceHeight + positionYInPixels;
				squarePiece.y4 = j * pieceHeight + pieceHeight + positionYInPixels;
				squarePiece.u1 = (i * pieceWidth) / imageTexture.getWidth();
				squarePiece.u2 = (i * pieceWidth + pieceWidth) / imageTexture.getWidth();
				squarePiece.u3 = (i * pieceWidth + pieceWidth) / imageTexture.getWidth();
				squarePiece.u4 = (i * pieceWidth) / imageTexture.getWidth();
				squarePiece.v1 = (j * pieceHeight) / imageTexture.getHeight();
				squarePiece.v2 = (j * pieceHeight) / imageTexture.getHeight();
				squarePiece.v3 = (j * pieceHeight + pieceHeight) / imageTexture.getHeight();
				squarePiece.v4 = (j * pieceHeight + pieceHeight) / imageTexture.getHeight();

				squarePiece.centreX = squarePiece.x1 + pieceWidth / 2f;
				squarePiece.centreY = squarePiece.y1 + pieceHeight / 2f;
				squarePiece.velocityX = (float) Math.random() * 10f - 5f;
				squarePiece.velocityY = (float) Math.random() * 10f - 5f;
				squarePiece.rotationVelocity = (float) Math.random() * 10f - 5f;
			}
		}

	}

	// This method is dodge AF :)
	public void createTrianglePieces(int pieceCount) {

		float centerPixelX;
		float centerPixelY;
		float[] edgePixelsX;
		float[] edgePixelsY;

		int imageWidth = this.imageTexture.getWidth();
		int imageHeight = this.imageTexture.getHeight();

		// 1. select random pixel for center breaking point
		centerPixelX = (float) ((Math.random() * imageWidth) / 2f + (imageWidth / 4f));
		centerPixelY = (float) ((Math.random() * imageHeight) / 2f + (imageHeight / 4f));

		// 2. select X random edge pixels
		edgePixelsX = new float[pieceCount];
		edgePixelsY = new float[pieceCount];
		for (int i = 0; i < pieceCount; i++) {
			if (i % pieceCount == 0) {
				edgePixelsX[i] = 0;
				edgePixelsY[i] = (int) (Math.random() * imageHeight);
			} else if (i % pieceCount == 1) {
				edgePixelsX[i] = (int) (Math.random() * imageWidth);
				edgePixelsY[i] = 0;
			} else if (i % pieceCount == 2) {
				edgePixelsX[i] = imageWidth - 1;
				edgePixelsY[i] = (int) (Math.random() * imageHeight);
			} else if (i % pieceCount == 3) {
				edgePixelsX[i] = (int) (Math.random() * imageWidth);
				edgePixelsY[i] = imageHeight - 1;
			}
		}

		// 3. Create triangles
		trianglePieces = new TrianglePiece[pieceCount];
		for (int i = 0; i < pieceCount; i++) {
			trianglePieces[i] = new TrianglePiece();
			trianglePieces[i].x1 = edgePixelsX[i] + i * 120f;
			trianglePieces[i].x2 = centerPixelX + i * 120f;
			trianglePieces[i].x3 = edgePixelsX[0] + i * 120f;
			trianglePieces[i].y1 = edgePixelsY[i];
			trianglePieces[i].y2 = centerPixelY;
			trianglePieces[i].y3 = edgePixelsY[0];
			trianglePieces[i].u1 = edgePixelsX[i] / imageTexture.getWidth();
			trianglePieces[i].u2 = centerPixelX / imageTexture.getWidth();
			trianglePieces[i].u3 = edgePixelsX[0] / imageTexture.getWidth();
			trianglePieces[i].v1 = edgePixelsY[i] / imageTexture.getHeight();
			trianglePieces[i].v2 = centerPixelY / imageTexture.getHeight();
			trianglePieces[i].v3 = edgePixelsY[0] / imageTexture.getHeight();
		}
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
			super.draw1();
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
	public void updateRealtime(int delta) {
		super.update(delta);

		for (int i = 0; trianglePieces != null && i < trianglePieces.length; i++) {

			if (trianglePieces[i] == null)
				continue;

			trianglePieces[i].update();
		}

		for (int i = 0; squarePieces != null && i < squarePieces.length; i++) {

			if (squarePieces[i] == null)
				continue;
			squarePieces[i].update();
		}

	}

	public class SquarePiece {
		float x1;
		float x2;
		float x3;
		float x4;
		float y1;
		float y2;
		float y3;
		float y4;
		float u1;
		float u2;
		float u3;
		float u4;
		float v1;
		float v2;
		float v3;
		float v4;

		float centreX;
		float centreY;

		float velocityX;
		float velocityY;
		float rotationVelocity;

		public void draw() {

			QuadUtils.drawQuad(imageTexture, this.x1, this.x2, this.x3, this.x4, this.y1, this.y2, this.y3, this.y4,
					this.u1, this.u2, this.u3, this.u4, this.v1, this.v2, this.v3, this.v4);
		}

		public void update() {

			if (velocityX == 0 && velocityY == 0)
				return;

			this.x1 += velocityX;
			this.x2 += velocityX;
			this.x3 += velocityX;
			this.x4 += velocityX;

			this.y1 += velocityY;
			this.y2 += velocityY;
			this.y3 += velocityY;
			this.y4 += velocityY;

			this.centreX += velocityX;
			this.centreY += velocityY;

			float[] pt1 = { x1, y1 };
			AffineTransform.getRotateInstance(Math.toRadians(rotationVelocity), centreX, centreY).transform(pt1, 0, pt1,
					0, 1);
			x1 = pt1[0];
			y1 = pt1[1];

			float[] pt2 = { x2, y2 };
			AffineTransform.getRotateInstance(Math.toRadians(rotationVelocity), centreX, centreY).transform(pt2, 0, pt2,
					0, 1);
			x2 = pt2[0];
			y2 = pt2[1];

			float[] pt3 = { x3, y3 };
			AffineTransform.getRotateInstance(Math.toRadians(rotationVelocity), centreX, centreY).transform(pt3, 0, pt3,
					0, 1);
			x3 = pt3[0];
			y3 = pt3[1];

			float[] pt4 = { x4, y4 };
			AffineTransform.getRotateInstance(Math.toRadians(rotationVelocity), centreX, centreY).transform(pt4, 0, pt4,
					0, 1);
			x4 = pt4[0];
			y4 = pt4[1];

			if (velocityX > 0) {
				velocityX -= velocityX / 25;
			} else if (velocityX < 0) {
				velocityX -= velocityX / 25;
				;
			}

			if (velocityX < 1 && velocityX > -1) {
				velocityX = 0;
			}

			if (velocityY > 0) {
				velocityY -= velocityY / 25;
			} else if (velocityY < 0) {
				velocityY -= velocityY / 25;
			}

			if (velocityY < 1 && velocityY > -1) {
				velocityY = 0;
			}

			if (rotationVelocity > 0) {
				rotationVelocity -= rotationVelocity / 25;
			} else if (rotationVelocity < 0) {
				rotationVelocity -= rotationVelocity / 25;
			}

			if (rotationVelocity < 1 && rotationVelocity > -1) {
				rotationVelocity = 0;
			}

			if (velocityX == 0 && velocityY == 0)
				rotationVelocity = 0;
		}
	}

	public class TrianglePiece {
		float x1;
		float x2;
		float x3;
		float y1;
		float y2;
		float y3;
		float u1;
		float u2;
		float u3;
		float v1;
		float v2;
		float v3;

		float velocityX;
		float velocityY;
		float rotationVelocityX;
		float rotationVelocityY;

		public void draw() {
			TriangleUtils.drawTriangle(imageTexture, this.x1, this.x2, this.x3, this.y1, this.y2, this.y3, this.u1,
					this.u2, this.u3, this.v1, this.v2, this.v3);
		}

		public void update() {
			this.x1 += velocityX;
			this.x2 += velocityX;
			this.x3 += velocityX;

			this.y1 += velocityY;
			this.y2 += velocityY;
			this.y3 += velocityY;
		}
	}

	@Override
	public GameObjectExploder makeCopy(Square square, Actor owner) {
		return new GameObjectExploder(new String(name), (int) totalHealth, imageTexturePath, square,
				inventory.makeCopy(), showInventory, canShareSquare, fitsInInventory, canContainOtherObjects,
				blocksLineOfSight, persistsWhenCantBeSeen, widthRatio, heightRatio, drawOffsetX, drawOffsetY,
				soundWhenHit, soundWhenHitting, soundDampening, light, lightHandleX, lightHandlY, stackable,
				fireResistance, iceResistance, electricResistance, poisonResistance, weight, owner);
	}
}
