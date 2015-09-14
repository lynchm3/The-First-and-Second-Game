package com.marklynch.tactics.objects;

import java.util.ArrayList;

import com.marklynch.tactics.objects.level.Square;
import com.marklynch.tactics.objects.weapons.Weapon;
import com.marklynch.utils.QuadUtils;

public class GameObjectExploder extends GameObject {

	public float centerPixelX;
	public float centerPixelY;
	public float[] edgePixelsX;
	public float[] edgePixelsY;
	public float pieceCount;

	public GameObjectExploder(String name, int health, int strength,
			int dexterity, int intelligence, int endurance, String imagePath,
			Square squareGameObjectIsOn, ArrayList<Weapon> weapons) {
		super(name, health, strength, dexterity, intelligence, endurance,
				imagePath, squareGameObjectIsOn, weapons);
		remainingHealth = 0;
		explode();
	}

	@Override
	public boolean checkIfDestroyed() {

		boolean destroyed = super.checkIfDestroyed();

		if (destroyed) {
			explode();
		}

		return destroyed;
	}

	public void explode() {
		createPieces(4);
	}

	public void createPieces(int pieceCount) {
		this.pieceCount = pieceCount;
		int imageWidth = this.imageTexture.getWidth();
		int imageHeight = this.imageTexture.getHeight();

		// 1. select random pixel
		centerPixelX = (int) (Math.random() * imageWidth);
		centerPixelY = (int) (Math.random() * imageHeight);

		// 2. select X random edge pixels
		edgePixelsX = new float[pieceCount];
		edgePixelsY = new float[pieceCount];
		for (int i = 0; i < pieceCount; i++) {
			if (i % 4 == 0) {
				edgePixelsX[i] = 0;
				edgePixelsY[i] = (int) (Math.random() * imageHeight);
			} else if (i % 4 == 1) {
				edgePixelsX[i] = (int) (Math.random() * imageWidth);
				edgePixelsY[i] = 0;
			} else if (i % 4 == 2) {
				edgePixelsX[i] = imageWidth - 1;
				edgePixelsY[i] = (int) (Math.random() * imageHeight);
			} else if (i % 4 == 3) {
				edgePixelsX[i] = (int) (Math.random() * imageWidth);
				edgePixelsY[i] = imageHeight - 1;
			}
		}

	}

	@Override
	public void drawForeground() {

		// MAYBE THE U AND V ARE A RATIO (0 to 1)? yup...
		// TRIED THAT below, didnt work, needs tsome debugging...

		// THEYRE INTS, WHOOPS :D:D:D

		if (this.remainingHealth > 0) {
			super.drawForeground();
			return;
		}

		float x1 = 0;
		float x2 = 120;
		float x3 = 120;
		float x4 = 0;
		float y1 = 0;
		float y2 = 0;
		float y3 = 120;
		float y4 = 120;
		float u1 = 0;
		float u2 = 0.5f;
		float u3 = 0.5f;
		float u4 = 0;
		float v1 = 0;
		float v2 = 0;
		float v3 = 0.5f;
		float v4 = 0.5f;
		QuadUtils.drawQuad(imageTexture, x1, x2, x3, x4, y1, y2, y3, y4, u1,
				u2, u3, u4, v1, v2, v3, v4);

		// System.out.println("Triangles " + 0 + " - x1 = " + x1 + ", x2 = " +
		// x2
		// + ", x3 = " + x3 + ", y1 = " + y1 + ", y2 = " + y2 + ", y3 = "
		// + y3 + ", u1 = " + u1 + ", u2 = " + u2 + ", u3 = " + u3
		// + ", v1 = " + v1 + ", v2 = " + v2 + ", v3 = " + v3);
		//
		// for (int i = 0; i < pieceCount; i++) {
		// if (i == pieceCount - 1) {
		//
		// float x1 = edgePixelsX[i] + i * 120f;
		// float x2 = centerPixelX + i * 120f;
		// float x3 = edgePixelsX[0] + i * 120f;
		// float y1 = edgePixelsY[i];
		// float y2 = centerPixelY;
		// float y3 = edgePixelsY[0];
		// float u1 = edgePixelsX[i] / imageTexture.getWidth();
		// float u2 = centerPixelX / imageTexture.getWidth();
		// float u3 = edgePixelsX[0] / imageTexture.getWidth();
		// float v1 = edgePixelsY[i] / imageTexture.getHeight();
		// float v2 = centerPixelY / imageTexture.getHeight();
		// float v3 = edgePixelsY[0] / imageTexture.getHeight();
		//
		// TriangleUtils.drawTriangle(imageTexture, x1, x2, x3, y1, y2,
		// y3, u1, u2, u3, v1, v2, v3);
		//
		// System.out.println("Triangles " + i + " - x1 = " + x1
		// + ", x2 = " + x2 + ", x3 = " + x3 + ", y1 = " + y1
		// + ", y2 = " + y2 + ", y3 = " + y3 + ", u1 = " + u1
		// + ", u2 = " + u2 + ", u3 = " + u3 + ", v1 = " + v1
		// + ", v2 = " + v2 + ", v3 = " + v3);
		//
		// } else {
		//
		// float x1 = edgePixelsX[i] + i * 120f;
		// float x2 = centerPixelX + i * 120f;
		// float x3 = edgePixelsX[i + 1] + i * 120f;
		// float y1 = edgePixelsY[i];
		// float y2 = centerPixelY;
		// float y3 = edgePixelsY[i + 1];
		// float u1 = edgePixelsX[i] / imageTexture.getWidth();
		// float u2 = centerPixelX / imageTexture.getWidth();
		// float u3 = edgePixelsX[i + 1] / imageTexture.getWidth();
		// float v1 = edgePixelsY[i] / imageTexture.getHeight();
		// float v2 = centerPixelY / imageTexture.getHeight();
		// float v3 = edgePixelsY[i + 1] / imageTexture.getHeight();
		// TriangleUtils.drawTriangle(imageTexture, x1, x2, x3, y1, y2,
		// y3, u1, u2, u3, v1, v2, v3);
		//
		// System.out.println("Triangles " + i + " - x1 = " + x1
		// + ", x2 = " + x2 + ", x3 = " + x3 + ", y1 = " + y1
		// + ", y2 = " + y2 + ", y3 = " + y3 + ", u1 = " + u1
		// + ", u2 = " + u2 + ", u3 = " + u3 + ", v1 = " + v1
		// + ", v2 = " + v2 + ", v3 = " + v3);
		// }
		// }

		// draw the pieces at 0,0
		// Game.activeBatch.dr

		// Draw object
		// int actorPositionXInPixels = this.squareGameObjectIsOn.x
		// * (int) Game.SQUARE_WIDTH;
		// int actorPositionYInPixels = this.squareGameObjectIsOn.y
		// * (int) Game.SQUARE_HEIGHT;
		//
		// float alpha = 1.0f;
		// if (Game.level.activeActor != null
		// && Game.level.activeActor.showHoverFightPreview == true
		// && Game.level.activeActor.hoverFightPreviewDefender != this) {
		// alpha = 0.5f;
		// }
		//
		// if (hasAttackedThisTurn == true && this.faction != null
		// && Game.level.currentFactionMoving == this.faction) {
		// alpha = 0.5f;
		// }
		//
		// TextureUtils.skipNormals = true;
		//
		// TextureUtils.drawTexture(imageTexture, alpha, actorPositionXInPixels,
		// actorPositionXInPixels + Game.SQUARE_WIDTH,
		// actorPositionYInPixels, actorPositionYInPixels
		// + Game.SQUARE_HEIGHT);
		// TextureUtils.skipNormals = false;
	}
}
