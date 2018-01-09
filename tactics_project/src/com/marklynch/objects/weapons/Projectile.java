package com.marklynch.objects.weapons;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import com.marklynch.Game;
import com.marklynch.level.constructs.animation.AnimationThrow;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.Arrow;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Searchable;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.units.Actor;
import com.marklynch.utils.TextureUtils;

import mdesl.graphics.Texture;

public class Projectile {

	public String name;
	public Actor shooter;
	public Action action;
	public GameObject targetGameObject;
	public Square targetSquare;
	float x, y, originX, originY, targetX, targetY, speedX, speedY;
	float angle = 0;
	boolean onTarget;
	String imagePath;
	Texture imageTexture;
	float distanceToCoverX, distanceToCoverY, distanceCoveredX, distanceCoveredY;
	GameObject projectileObject;
	float rotationSpeed = 0;
	public boolean landed = false;

	public Projectile(String name, Actor shooter, Action action, GameObject targetGameObject, Square targetSquare,
			GameObject projectileObject, float speed, float rotationSpeed, boolean onTarget) {
		super();

		if (shooter == Game.level.player) {
			name = "Your " + name;
		} else {
			name = shooter.name + "'s " + name;
		}

		this.name = name;
		this.shooter = shooter;
		this.action = action;
		this.targetGameObject = targetGameObject;
		this.targetSquare = targetSquare;
		this.projectileObject = projectileObject;

		this.x = this.originX = shooter.getCenterX();
		this.y = this.originY = shooter.getCenterY();
		this.targetX = this.targetSquare.xInGridPixels + Game.SQUARE_WIDTH * this.projectileObject.drawOffsetX
				+ this.projectileObject.width / 2;
		this.targetY = this.targetSquare.yInGridPixels + Game.SQUARE_HEIGHT * this.projectileObject.drawOffsetY
				+ this.projectileObject.height / 2;
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

		this.onTarget = onTarget;

		shooter.animation = new AnimationThrow(this);
	}

	public void update(float delta) {

		float distanceX = speedX * delta;
		float distanceY = speedY * delta;

		angle += rotationSpeed * delta;

		distanceCoveredX += distanceX;
		distanceCoveredY += distanceY;

		if (Math.abs(distanceCoveredX) >= Math.abs(distanceToCoverX)
				&& Math.abs(distanceCoveredY) >= Math.abs(distanceToCoverY)) {
			Game.level.projectilesToRemove.add(this);
			landed = true;
			if (targetGameObject != null)
				shooter.showPow(targetGameObject);
			if (!(projectileObject instanceof Arrow)) {
				if (targetGameObject instanceof Searchable && projectileObject.canShareSquare) {
					targetGameObject.inventory.add(projectileObject);
				} else {
					targetSquare.inventory.add(projectileObject);
				}
				projectileObject.landed(shooter, action);
			}

		} else {
			x += distanceX;
			y += distanceY;
			Square square = Game.level.squares[(int) Math.floor(x / Game.SQUARE_WIDTH)][(int) Math
					.floor(y / Game.SQUARE_HEIGHT)];
			square.inventory.smashWindows(this);

		}

		// if reached target,
		// this.showPow(gameObject);
	}

	public void drawForeground() {

		// GameObject exploder does spinning and angles and movemet...

		float alpha = 1.0f;
		// TextureUtils.drawTexture(projectileObject.imageTexture, alpha, x, y,
		// x + projectileObject.width,
		// y + projectileObject.height, projectileObject.backwards);

		// TextureUtils.drawTexture(projectileObject.imageTexture, alpha, x, y,
		// x + projectileObject.width,
		// y + projectileObject.height, projectileObject.backwards);

		float radians = (float) Math.toRadians(angle);
		Game.activeBatch.flush();
		Matrix4f view = Game.activeBatch.getViewMatrix();
		view.translate(new Vector2f(x, y));
		view.rotate(radians, new Vector3f(0f, 0f, 1f));
		Game.activeBatch.updateUniforms();

		TextureUtils.drawTexture(projectileObject.imageTexture, alpha, 0 - projectileObject.width / 2,
				0 - projectileObject.height / 2, 0 + projectileObject.width - projectileObject.width / 2,
				0 + projectileObject.height - projectileObject.height / 2, projectileObject.backwards);

		Game.activeBatch.flush();
		view.rotate(-radians, new Vector3f(0f, 0f, 1f));
		view.translate(new Vector2f(-x, -y));
		Game.activeBatch.updateUniforms();

		// translate to center of object

		// rotate
		// translate back out

	}

}
