package com.marklynch.level.constructs.animation;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import com.marklynch.Game;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.Arrow;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.InanimateObjectToAddOrRemove;
import com.marklynch.objects.Searchable;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.units.Actor;
import com.marklynch.utils.TextureUtils;

import mdesl.graphics.Texture;

public class AnimationDrop extends Animation {

	public String name;
	public GameObject shooter;
	public Action action;
	public Square targetSquare;
	float x, y, originX, originY, targetX, targetY, speedX, speedY;
	float angle = 0;
	boolean onTarget;
	String imagePath;
	Texture imageTexture;
	float distanceToCoverX, distanceToCoverY, distanceCoveredX, distanceCoveredY;
	GameObject projectileObject;
	float rotationSpeed = 0;

	public AnimationDrop(String name, GameObject shooter, Action action, Square targetSquare,
			GameObject projectileObject, float speed, float rotationSpeed, boolean onTarget) {

		if (shooter == Game.level.player) {
			name = "Your " + name;
		} else {
			name = shooter.name + "'s " + name;
		}

		if (shooter instanceof Actor) {
			System.out.println("A");
			Actor shooterActor = (Actor) shooter;
			this.x = originX = (int) (shooter.squareGameObjectIsOn.xInGridPixels
					+ shooter.drawOffsetX * Game.SQUARE_WIDTH + shooterActor.handAnchorX - projectileObject.anchorX);
			this.y = originY = (int) (shooter.squareGameObjectIsOn.yInGridPixels
					+ shooter.drawOffsetY * Game.SQUARE_HEIGHT + shooterActor.handAnchorY - projectileObject.anchorY);
		} else {
			System.out.println("B");
			this.x = originX = (int) (shooter.squareGameObjectIsOn.xInGridPixels
					+ (Game.SQUARE_WIDTH - projectileObject.width) / 2);
			this.y = originY = (int) (shooter.squareGameObjectIsOn.yInGridPixels
					+ (Game.SQUARE_HEIGHT - projectileObject.height) / 2);
		}

		targetX = (int) (targetSquare.xInGridPixels + Game.SQUARE_WIDTH * projectileObject.drawOffsetX);
		targetY = (int) (targetSquare.yInGridPixels + Game.SQUARE_HEIGHT * projectileObject.drawOffsetY);

		this.name = name;
		this.shooter = shooter;
		this.action = action;
		this.targetSquare = targetSquare;
		this.projectileObject = projectileObject;

		// this.x = this.originX = shooter.getCenterX();
		// this.y = this.originY = shooter.getCenterY();
		// this.targetX = this.targetSquare.xInGridPixels + Game.SQUARE_WIDTH *
		// this.projectileObject.drawOffsetX
		// + this.projectileObject.width / 2;
		// this.targetY = this.targetSquare.yInGridPixels + Game.SQUARE_HEIGHT *
		// this.projectileObject.drawOffsetY
		// + this.projectileObject.height / 2;
		// (int) (this.targetSquare.yInGridPixels
		// + Game.SQUARE_HEIGHT * this.projectileObject.drawOffsetY)

		distanceToCoverX = this.targetX - this.originX;
		distanceToCoverY = this.targetY - this.originY;
		float totalDistanceToCover = Math.abs(distanceToCoverX) + Math.abs(distanceToCoverY);

		this.speedX = (distanceToCoverX / totalDistanceToCover) * speed;
		this.speedY = (distanceToCoverY / totalDistanceToCover) * speed;

		if (speedX < 0)
			this.rotationSpeed = -rotationSpeed;
		else
			this.rotationSpeed = rotationSpeed;

		if (projectileObject instanceof Arrow && distanceToCoverX < 0) {
			projectileObject.backwards = true;
		}

		if (speed == 0 || totalDistanceToCover == 0)
			completed();

		this.onTarget = onTarget;

		blockAI = true;
	}

	@Override
	public void update(double delta) {

		if (completed)
			return;

		float distanceX = (float) (speedX * delta);
		float distanceY = (float) (speedY * delta);

		angle += rotationSpeed * delta;

		distanceCoveredX += distanceX;
		distanceCoveredY += distanceY;

		if (Math.abs(distanceCoveredX) >= Math.abs(distanceToCoverX)
				&& Math.abs(distanceCoveredY) >= Math.abs(distanceToCoverY)) {
			completed();
		} else {
			x += distanceX;
			y += distanceY;
		}
	}

	public void completed() {
		completed = true;

		// receiver.inventory.add(object);
		if (targetSquare.inventory.contains(Searchable.class)) {
			Searchable searchable = (Searchable) targetSquare.inventory.getGameObjectOfClass(Searchable.class);
			searchable.inventory.add(projectileObject);
		} else {
			Game.level.inanimateObjectsToAdd.add(new InanimateObjectToAddOrRemove(projectileObject, targetSquare));
		}
	}

	@Override
	public void draw() {
		float alpha = 1.0f;

		float radians = (float) Math.toRadians(angle);
		Game.activeBatch.flush();
		Matrix4f view = Game.activeBatch.getViewMatrix();
		view.translate(new Vector2f(x, y));
		view.rotate(radians, new Vector3f(0f, 0f, 1f));
		Game.activeBatch.updateUniforms();

		TextureUtils.drawTexture(projectileObject.imageTexture, alpha, 0, 0, 0 + projectileObject.width,
				0 + projectileObject.height);

		Game.activeBatch.flush();
		view.rotate(-radians, new Vector3f(0f, 0f, 1f));
		view.translate(new Vector2f(-x, -y));
		Game.activeBatch.updateUniforms();
	}
}
