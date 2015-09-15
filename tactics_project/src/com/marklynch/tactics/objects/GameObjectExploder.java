package com.marklynch.tactics.objects;

import java.util.ArrayList;

import com.marklynch.tactics.objects.level.Square;
import com.marklynch.tactics.objects.weapons.Weapon;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.TriangleUtils;

public class GameObjectExploder extends GameObject {

	public SquarePiece[] squarePieces;

	public TrianglePiece[] trianglePieces;

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
		// createTrianglePieces(4);
		createSquarePieces(4);
	}

	public void createSquarePieces(int root) {
		int pieceCount = root * root;
		squarePieces = new SquarePiece[pieceCount];
		float pieceWidth = this.imageTexture.getWidth() / root;
		float pieceHeight = this.imageTexture.getHeight() / root;

		for (float i = 0; i < root; i++) {
			for (float j = 0; j < root; j++) {

				SquarePiece squarePiece = squarePieces[(int) (i * root + j)] = new SquarePiece();
				squarePiece.x1 = i * pieceWidth;
				squarePiece.x2 = i * pieceWidth + pieceWidth;
				squarePiece.x3 = i * pieceWidth + pieceWidth;
				squarePiece.x4 = i * pieceWidth;
				squarePiece.y1 = j * pieceHeight;
				squarePiece.y2 = j * pieceHeight;
				squarePiece.y3 = j * pieceHeight + pieceHeight;
				squarePiece.y4 = j * pieceHeight + pieceHeight;
				squarePiece.u1 = (i * pieceWidth) / imageTexture.getWidth();
				squarePiece.u2 = (i * pieceWidth + pieceWidth)
						/ imageTexture.getWidth();
				squarePiece.u3 = (i * pieceWidth + pieceWidth)
						/ imageTexture.getWidth();
				squarePiece.u4 = (i * pieceWidth) / imageTexture.getWidth();
				squarePiece.v1 = (j * pieceHeight) / imageTexture.getHeight();
				squarePiece.v2 = (j * pieceHeight) / imageTexture.getHeight();
				squarePiece.v3 = (j * pieceHeight + pieceHeight)
						/ imageTexture.getHeight();
				squarePiece.v4 = (j * pieceHeight + pieceHeight)
						/ imageTexture.getHeight();
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
	public void drawForeground() {

		// MAYBE THE U AND V ARE A RATIO (0 to 1)? yup...
		// TRIED THAT below, didnt work, needs tsome debugging...

		// THEYRE INTS, WHOOPS :D:D:D

		if (this.remainingHealth > 0) {
			super.drawForeground();
			return;
		}

		for (int i = 0; trianglePieces != null && i < trianglePieces.length; i++) {

			if (trianglePieces[i] == null)
				continue;

			TriangleUtils.drawTriangle(imageTexture, trianglePieces[i].x1,
					trianglePieces[i].x2, trianglePieces[i].x3,
					trianglePieces[i].y1, trianglePieces[i].y2,
					trianglePieces[i].y3, trianglePieces[i].u1,
					trianglePieces[i].u2, trianglePieces[i].u3,
					trianglePieces[i].v1, trianglePieces[i].v2,
					trianglePieces[i].v3);
		}

		for (int i = 0; squarePieces != null && i < squarePieces.length; i++) {

			if (squarePieces[i] == null)
				continue;

			QuadUtils.drawQuad(imageTexture, squarePieces[i].x1,
					squarePieces[i].x2, squarePieces[i].x3, squarePieces[i].x4,
					squarePieces[i].y1, squarePieces[i].y2, squarePieces[i].y3,
					squarePieces[i].y4, squarePieces[i].u1, squarePieces[i].u2,
					squarePieces[i].u3, squarePieces[i].u4, squarePieces[i].v1,
					squarePieces[i].v2, squarePieces[i].v3, squarePieces[i].v4);
		}
	}

	public static class SquarePiece {
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
	}

	public static class TrianglePiece {
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
	}
}
