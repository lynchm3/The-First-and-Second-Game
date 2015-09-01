package com.marklynch.tactics.objects;

import java.util.ArrayList;

import mdesl.graphics.Texture;

import com.marklynch.tactics.objects.level.Square;
import com.marklynch.tactics.objects.weapons.Weapon;

public class GameObjectExploder extends GameObject {

	public GameObjectExploder(String name, int health, int strength,
			int dexterity, int intelligence, int endurance, String imagePath,
			Square squareGameObjectIsOn, ArrayList<Weapon> weapons) {
		super(name, health, strength, dexterity, intelligence, endurance,
				imagePath, squareGameObjectIsOn, weapons);
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

		// 1. create pieces

		// explosion effect

		// o, o, o... break down the object in to pieces!!!

		// put some debris down
		// blow debris out
		// blow other debris out
		// scare the cat
		// damage shit
	}

	public void createPieces(int pieceCount) {
		int totalAmountOfPixels = this.imageTexture.getWidth()
				* this.imageTexture.getHeight();
		int imageWidth = this.imageTexture.getWidth();
		int imageHeight = this.imageTexture.getHeight();

		int fairShare = totalAmountOfPixels / pieceCount;
		int[] pieceSizes = new int[pieceCount];
		int pixelsAcountedFor = 0;
		for (int i = 0; i < pieceCount - 1; i++) {
			double pieceSize = (Math.random() * totalAmountOfPixels)
					/ pieceCount;
			pieceSizes[i] = (int) pieceSize;
			pixelsAcountedFor += pieceSize;
		}
		pieceSizes[pieceCount - 1] = totalAmountOfPixels - pixelsAcountedFor;

		int[][] pixelReservations = new int[imageWidth][imageHeight];
		for (int i = 0; i < imageWidth; i++) {
			for (int j = 0; j < imageHeight; j++) {
				pixelReservations[i][j] = -1;
			}
		}

		for (int i = 0; i < pieceCount; i++) {
			// 1 pick a random pixel to start with
			int pixelX = (int) (imageWidth * Math.random());
			int pixelY = (int) (imageHeight * Math.random());

			while (pixelReservations[pixelX][pixelY] != -1) {
				pixelX++;
				if (pixelX >= imageWidth) {
					pixelX = 0;
					pixelY++;
					if (pixelY >= imageHeight) {
						pixelY++;
					}
				}
			}

			pixelReservations[pixelX][pixelY] = i;

			for (int j = 1; j < pieceSizes[i]; j++) {
				int direction = (int) (Math.random() * 4);
				for (int k = 0; k < 4; k++) {

					int directionX = 1;
					int directionY = 1;

					if (direction == 0) {
						directionX = 1;
						directionY = 1;
					}

					if (direction == 1) {
						directionX = -1;
						directionY = 1;
					}

					if (direction == 2) {
						directionX = 1;
						directionY = -1;
					}

					if (direction == 3) {
						directionX = -1;
						directionY = -1;
					}

					int potentialPixelX = pixelX + directionX;
					int potentialPixelY = pixelY + directionX;

					if (potentialPixelX > -1
							&& potentialPixelX < imageWidth
							&& potentialPixelY > -1
							&& potentialPixelY < imageHeight
							&& pixelReservations[potentialPixelX][potentialPixelY] == -1) {
						pixelX = potentialPixelX;
						pixelY = potentialPixelY;
						pixelReservations[pixelX][pixelY] = i;
						break;
					}

					direction++;
					if (direction == 4)
						direction = 0;

					if (k == 3)
						j = pieceSizes[i];
				}
			}

		}

		 OK... now I have an array of what pixel belongs to what piece...
		 now to build a new texture from it...a

		Texture[] textures = new Texture[pieceCount];
		for (int i = 0; i < pieceCount; i++) {
			Texture texture = new Texture();
			
			
			
		}

	}
}
