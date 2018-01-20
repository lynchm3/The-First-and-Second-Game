package com.marklynch.level.constructs.animation;

import com.marklynch.Game;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.units.Actor;

public class AnimationDrop extends Animation {

	public Square targetSquare;
	float originX, originY, targetX, targetY;
	public float startOffsetX = 0;
	public float startOffsetY = 0;

	public AnimationDrop(String name, GameObject shooter, Action action, Square targetSquare,
			GameObject projectileObject, float speed, float rotationSpeed, boolean onTarget) {

		if (shooter == Game.level.player) {
			name = "Your " + name;
		} else {
			name = shooter.name + "'s " + name;
		}

		if (shooter instanceof Actor) {
			Actor shooterActor = (Actor) shooter;
			originX = (int) (shooter.squareGameObjectIsOn.xInGridPixels + shooter.drawOffsetX * Game.SQUARE_WIDTH
					+ shooterActor.handAnchorX - projectileObject.anchorX);
			originY = (int) (shooter.squareGameObjectIsOn.yInGridPixels + shooter.drawOffsetY * Game.SQUARE_HEIGHT
					+ shooterActor.handAnchorY - projectileObject.anchorY);
		} else {
			originX = (int) (shooter.squareGameObjectIsOn.xInGridPixels
					+ (Game.SQUARE_WIDTH - projectileObject.width) / 2);
			originY = (int) (shooter.squareGameObjectIsOn.yInGridPixels
					+ (Game.SQUARE_HEIGHT - projectileObject.height) / 2);
		}

		targetX = (int) (targetSquare.xInGridPixels + Game.SQUARE_WIDTH * projectileObject.drawOffsetX);
		targetY = (int) (targetSquare.yInGridPixels + Game.SQUARE_HEIGHT * projectileObject.drawOffsetY);

		startOffsetX = offsetX = originX - targetX;
		startOffsetY = offsetY = originY - targetY;

		blockAI = false;
	}

	@Override
	public void update(double delta) {

		if (completed)
			return;

		durationSoFar += delta;
		double progress = durationSoFar / durationToReach;
		if (progress >= 1) {
			completed = true;
			offsetX = 0;
			offsetY = 0;
		} else {
			offsetX = (int) (startOffsetX * (1 - progress));
			offsetY = (int) (startOffsetY * (1 - progress));
		}

	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub

	}

	// @Override
	// public void update(double delta) {
	//
	// if (completed)
	// return;
	//
	// float distanceX = (float) (speedX * delta);
	// float distanceY = (float) (speedY * delta);
	//
	// angle += rotationSpeed * delta;
	//
	// distanceCoveredX += distanceX;
	// distanceCoveredY += distanceY;
	//
	// if (Math.abs(distanceCoveredX) >= Math.abs(distanceToCoverX)
	// && Math.abs(distanceCoveredY) >= Math.abs(distanceToCoverY)) {
	// completed();
	// } else {
	// x += distanceX;
	// y += distanceY;
	// }
	// }

	// public void completed() {
	// completed = true;
	//
	// // receiver.inventory.add(object);
	// if (targetSquare.inventory.contains(Searchable.class)) {
	// Searchable searchable = (Searchable)
	// targetSquare.inventory.getGameObjectOfClass(Searchable.class);
	// searchable.inventory.add(projectileObject);
	// } else {
	// Game.level.inanimateObjectsToAdd.add(new
	// InanimateObjectToAddOrRemove(projectileObject, targetSquare));
	// }
	//
	// if (Game.level.player.inventory.groundDisplay != null)
	// Game.level.player.inventory.groundDisplay.refreshGameObjects();
	// }

	// @Override
	// public void draw() {
	// float alpha = 1.0f;
	//
	// float radians = (float) Math.toRadians(angle);
	// Game.activeBatch.flush();
	// Matrix4f view = Game.activeBatch.getViewMatrix();
	// view.translate(new Vector2f(x, y));
	// view.rotate(radians, new Vector3f(0f, 0f, 1f));
	// Game.activeBatch.updateUniforms();
	//
	// TextureUtils.drawTexture(projectileObject.imageTexture, alpha, 0, 0, 0 +
	// projectileObject.width,
	// 0 + projectileObject.height);
	//
	// Game.activeBatch.flush();
	// view.rotate(-radians, new Vector3f(0f, 0f, 1f));
	// view.translate(new Vector2f(-x, -y));
	// Game.activeBatch.updateUniforms();
	// }
}
