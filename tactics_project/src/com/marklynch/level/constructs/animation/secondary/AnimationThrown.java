package com.marklynch.level.constructs.animation.secondary;

import com.marklynch.Game;
import com.marklynch.actions.Action;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.animation.primary.AnimationFlinch;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.Arrow;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.inanimateobjects.Searchable;
import com.marklynch.objects.templates.Templates;
import com.marklynch.ui.ActivityLog;
import com.marklynch.utils.Texture;
import com.marklynch.utils.TextureUtils;

public class AnimationThrown extends SecondaryAnimation {

	public String name;
	float x, y, originX, originY, targetX, targetY, speedX, speedY;
	float angleInRadians = 0;
	boolean onTarget;
	String imagePath;
	Texture imageTexture;
	float distanceToCoverX, distanceToCoverY, distanceCoveredX, distanceCoveredY;
	float rotationSpeed = 0;
	GameObject targetGameObject;

	public AnimationThrown(String name, Actor shooter, Action action, GameObject target, Square targetSquare,
			GameObject projectileObject, GameObject weapon, float speed, float rotationSpeed, boolean onTarget,
			OnCompletionListener onCompletionListener) {

		super(projectileObject, onCompletionListener, null, targetSquare, projectileObject, action, shooter, weapon,
				false, shooter, projectileObject, targetSquare);

		if (!runAnimation) {
			return;
		}

		if (shooter == Game.level.player) {
			name = "Your " + name;
		} else {
			name = shooter.name + "'s " + name;
		}

		this.name = name;
		this.shooter = shooter;
		this.action = action;
		this.targetGameObject = target;
		this.targetSquare = targetSquare;
		this.projectileObject = projectileObject;

		if (shooter.backwards)
			this.x = this.originX = shooter.getHandXY().x - projectileObject.halfWidth;// shooter.getCenterX();
		else
			this.x = this.originX = shooter.getHandXY().x - projectileObject.halfWidth;
		;// + Game.SQUARE_WIDTH;//
			// shooter.getCenterX();

		projectileObject.backwards = shooter.backwards;

		this.y = this.originY = shooter.getHandXY().y - projectileObject.halfHeight;// shooter.getCenterY();

		// this.targetX = this.targetSquare.xInGridPixels + Game.HALF_SQUARE_WIDTH *
		// this.projectileObject.drawOffsetRatioX
		// + this.projectileObject.width / 2f;
		// this.targetY = this.targetSquare.yInGridPixels
		// + Game.HALF_SQUARE_HEIGHT * this.projectileObject.drawOffsetRatioY +
		// this.projectileObject.height / 2f;
		// this.targetY += Math.random() * 16f;
		// this.targetY -= 8;

		this.targetX = (int) (targetSquare.xInGridPixels + projectileObject.drawOffsetX);
		this.targetY = (int) (targetSquare.yInGridPixels + projectileObject.drawOffsetY);

		if (projectileObject.backwards) {

			targetX += projectileObject.width;
		} else {
			targetX -= projectileObject.width;
		}

		// (int) (this.targetSquare.yInGridPixels
		// + Game.SQUARE_HEIGHT * this.projectileObject.drawOffsetY)

		distanceToCoverX = this.targetX - this.originX;
		distanceToCoverY = this.targetY - this.originY;
		float totalDistanceToCover = Math.abs(distanceToCoverX) + Math.abs(distanceToCoverY);

//		speed = 0.001f;
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
			runCompletionAlorightm(true);

		} else {
			x += distanceX;
			y += distanceY;

			// int squareX = (int) Math.floor(x / Game.SQUARE_WIDTH);
			// int squareY = (int) Math.floor(y / Game.SQUARE_HEIGHT);
			// if (Square.squareExists(squareX, squareY)) {
			// Square square = Level.squares[squareX][squareY];
			// square.inventory.smashWindows(shooter);
			// }
		}

		float progress = distanceCoveredX / distanceToCoverX;

		if (projectileObject.templateId != Templates.ARROW.templateId) {
			angleInRadians = progress * 6.28f;
			if (rotationSpeed < 0)
				angleInRadians = -angleInRadians;
		}
	}

	@Override
	public String toString() {
		return "AnimationThrown";
	}

	@Override
	public void draw2() {

		if (getCompleted())
			return;

		Game.flush();
		// float radians = (float) Math.toRadians(angle);
		// Matrix4f view = Game.activeBatch.getViewMatrix();
		// view.translate(new Vector2f(x + projectileObject.halfWidth, y +
		// projectileObject.halfHeight));
		// view.rotate(angleInRadians, new Vector3f(0f, 0f, 1f));
		// view.translate(new Vector2f(-(x + projectileObject.halfWidth), -(y +
		// projectileObject.halfHeight)));
		// Game.activeBatch.updateUniforms();

		TextureUtils.drawTexture(projectileObject.imageTexture, 1.0f, x, y, x + projectileObject.width,
				y + projectileObject.height, projectileObject.backwards);

		Game.flush();
		// view.translate(new Vector2f(x + projectileObject.halfWidth, y +
		// projectileObject.halfHeight));
		// view.rotate(-angleInRadians, new Vector3f(0f, 0f, 1f));
		// view.translate(new Vector2f(-(x + projectileObject.halfWidth), -(y +
		// projectileObject.halfHeight)));
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
	public void animationSubclassRunCompletionAlgorightm(boolean wait) {
		// super.runCompletionAlgorightm(wait);
		postRangedAnimation();
	}

	public void postRangedAnimation() {

		if (targetGameObject != null) {

			// Target is a gameobject

			if (targetGameObject.attackable) {
				targetGameObject.showPow();
				float damage = targetGameObject.changeHealth(shooter, action, weapon);
				if (targetGameObject.remainingHealth > 0)
					targetGameObject.setPrimaryAnimation(new AnimationFlinch(targetGameObject,
							shooter.squareGameObjectIsOn, targetGameObject.getPrimaryAnimation(), null));
				if (projectileObject instanceof Arrow) {
					if (Game.level.shouldLog(targetGameObject, shooter))
						Game.level.logOnScreen(new ActivityLog(new Object[] { shooter, " shot ", targetGameObject,
								" with ", weapon, " for " + damage + " damage" }));
				} else {
					if (Game.level.shouldLog(targetGameObject, shooter))
						Game.level.logOnScreen(new ActivityLog(new Object[] { shooter, " threw ", projectileObject,
								" at ", targetGameObject, " for " + damage + " damage" }));
				}

			} else {
				if (projectileObject instanceof Arrow) {
					if (Game.level.shouldLog(targetGameObject, shooter))
						Game.level.logOnScreen(new ActivityLog(
								new Object[] { shooter, " shot ", projectileObject, " with ", weapon }));
				} else {
					if (Game.level.shouldLog(targetGameObject, shooter))
						Game.level.logOnScreen(new ActivityLog(
								new Object[] { shooter, " threw ", projectileObject, " at ", targetGameObject }));
				}
			}

			if (targetGameObject instanceof Searchable && targetGameObject.remainingHealth > 0) {
				targetGameObject.inventory.add(projectileObject);
			} else {
				if (projectileObject instanceof Arrow)
					targetGameObject.arrowsEmbeddedInThis.add((Arrow) projectileObject);
				else
					targetSquare.inventory.add(projectileObject);

			}

		} else if (targetSquare != null) {

			// Target is a square

			if (projectileObject instanceof Arrow) {
				if (Game.level.shouldLog(targetSquare, shooter))
					Game.level.logOnScreen(new ActivityLog(new Object[] { shooter, " shot ", projectileObject }));
			} else {
				if (Game.level.shouldLog(targetSquare, shooter))
					Game.level.logOnScreen(new ActivityLog(new Object[] { shooter, " threw ", projectileObject }));
			}

			if (!(projectileObject instanceof Arrow)) {
				targetSquare.inventory.add(projectileObject);
			}
		}

		projectileObject.landed(shooter, action);

		if (Level.player.inventory.groundDisplay != null)
			Level.player.inventory.groundDisplay.refreshGameObjects();
	}

	@Override
	public void draw3() {
		// TODO Auto-generated method stub

	}
}
