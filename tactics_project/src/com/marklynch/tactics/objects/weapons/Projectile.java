package com.marklynch.tactics.objects.weapons;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import com.marklynch.Game;
import com.marklynch.tactics.objects.GameObject;
import com.marklynch.utils.QuadUtils;

import mdesl.graphics.Color;
import mdesl.graphics.Texture;

public class Projectile {

	GameObject shooter, target;
	float x, y, originX, originY, targetX, targetY, speedX, speedY;
	boolean onTarget;
	String imagePath;
	Texture imageTexture;
	float distanceToCoverX, distanceToCoverY, distanceCoveredX, distanceCoveredY;

	public Projectile(GameObject shooter, GameObject target, float speed, boolean onTarget, String imagePath) {
		super();

		this.shooter = shooter;
		this.target = target;

		this.x = this.originX = shooter.getCenterX();
		this.y = this.originY = shooter.getCenterY();
		this.targetX = target.getCenterX();
		this.targetY = target.getCenterY();

		distanceToCoverX = this.targetX - this.originX;
		distanceToCoverY = this.targetY - this.originY;
		float totalDistanceToCover = Math.abs(distanceToCoverX) + Math.abs(distanceToCoverY);

		this.speedX = (distanceToCoverX / totalDistanceToCover) * 4;
		this.speedY = (distanceToCoverY / totalDistanceToCover) * 4;

		this.onTarget = onTarget;
		this.imagePath = imagePath;
		loadImages();
	}

	public void loadImages() {
		imageTexture = getGlobalImage("imagePath");
	}

	public void update(float delta) {

		float distanceX = speedX * delta;
		float distanceY = speedY * delta;

		distanceCoveredX += distanceX;
		distanceCoveredY += distanceY;

		if (Math.abs(distanceCoveredX) >= Math.abs(distanceToCoverX)
				&& Math.abs(distanceCoveredY) >= Math.abs(distanceToCoverY)) {
			Game.level.projectilesToRemove.add(this);
			shooter.showPow(target);
		} else {
			x += distanceX;
			y += distanceY;

		}

		// if reached target,
		// this.showPow(gameObject);
	}

	public void drawForeground() {

		// if (activityDescription != null && activityDescription.length() > 0)
		// {
		float x1 = x;
		float x2 = x + 10;
		float y1 = y;
		float y2 = y + 10;
		QuadUtils.drawQuad(Color.BLACK, x1, x2, y1, y2);
		// }

	}

}
