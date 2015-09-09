package com.marklynch.tactics.objects;

import java.util.ArrayList;

import com.marklynch.tactics.objects.level.Square;
import com.marklynch.tactics.objects.weapons.Weapon;

public class GameObjectExploder extends GameObject {

	int centerPixelX;
	int centerPixelY;
	int[] edgePixelsX;
	int[] edgePixelsY;

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
		createPieces(4);

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
		int imageWidth = this.imageTexture.getWidth();
		int imageHeight = this.imageTexture.getHeight();

		// 1. select random pixel
		centerPixelX = (int) (Math.random() * imageWidth);
		centerPixelY = (int) (Math.random() * imageHeight);

		// 2. select X random edge pixels
		edgePixelsX = new int[pieceCount];
		edgePixelsY = new int[pieceCount];
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

		if (this.remainingHealth > 0) {
			super.drawForeground();
			return;
		}

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
