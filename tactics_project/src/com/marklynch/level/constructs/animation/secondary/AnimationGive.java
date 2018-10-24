package com.marklynch.level.constructs.animation.secondary;

import com.marklynch.Game;
import com.marklynch.level.constructs.animation.Animation;
import com.marklynch.objects.Arrow;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.templates.Templates;
import com.marklynch.utils.Texture;
import com.marklynch.utils.TextureUtils;

public class AnimationGive extends Animation {

	// pass obj person to person, used in buy, sell, give, for show only

	public GameObject giver;
	public GameObject receiver;
	float x, y, originX, originY, targetX, targetY, speedX, speedY;
	String imagePath;
	Texture imageTexture;
	float distanceToCoverX, distanceToCoverY, distanceCoveredX, distanceCoveredY;
	GameObject projectileObject;
	public static GameObject gold = Templates.GOLD.makeCopy(null, null);

	public AnimationGive(GameObject giver, GameObject receiver, GameObject projectileObject,
			OnCompletionListener onCompletionListener) {
		super(null, onCompletionListener, giver, receiver);
		if (!runAnimation)
			return;

		float speed = 0.5f;

		this.x = originX = (int) (giver.squareGameObjectIsOn.xInGridPixels
				+ (Game.SQUARE_WIDTH - projectileObject.width) / 2);
		this.y = originY = (int) (giver.squareGameObjectIsOn.yInGridPixels
				+ (Game.SQUARE_HEIGHT - projectileObject.height) / 2);

		targetX = (int) (receiver.squareGameObjectIsOn.xInGridPixels
				+ (Game.SQUARE_WIDTH - projectileObject.width) / 2);
		targetY = (int) (receiver.squareGameObjectIsOn.yInGridPixels
				+ (Game.SQUARE_HEIGHT - projectileObject.height) / 2);
		this.projectileObject = projectileObject;

		distanceToCoverX = this.targetX - this.originX;
		distanceToCoverY = this.targetY - this.originY;
		float totalDistanceToCover = Math.abs(distanceToCoverX) + Math.abs(distanceToCoverY);

		this.speedX = (distanceToCoverX / totalDistanceToCover) * speed;
		this.speedY = (distanceToCoverY / totalDistanceToCover) * speed;

		if (projectileObject instanceof Arrow && distanceToCoverX < 0) {
			projectileObject.backwards = true;
		}

		if (speed == 0 || totalDistanceToCover == 0)
			completed();

		blockAI = true;
	}

	@Override
	public void update(double delta) {

		if (getCompleted())
			return;

		float distanceX = (float) (speedX * delta);
		float distanceY = (float) (speedY * delta);

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
		runCompletionAlgorightm(true);
	}

	@Override
	public void draw2() {

		// Game.flush();
		// Matrix4f view = Game.activeBatch.getViewMatrix();
		// view.translate(new Vector2f(x, y));
		// Game.activeBatch.updateUniforms();

		TextureUtils.drawTexture(projectileObject.imageTexture, 1.0f, x, y, x + projectileObject.width,
				y + projectileObject.height);

		// Game.flush();
		// view.translate(new Vector2f(-x, -y));
		// Game.activeBatch.updateUniforms();
	}

	@Override
	public void draw1() {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawStaticUI() {
	}

	@Override
	public void draw3() {
		// TODO Auto-generated method stub

	}
}
