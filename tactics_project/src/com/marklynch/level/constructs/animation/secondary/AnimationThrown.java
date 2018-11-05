package com.marklynch.level.constructs.animation.secondary;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.animation.Animation;
import com.marklynch.level.constructs.animation.primary.AnimationFlinch;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.Arrow;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Searchable;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;
import com.marklynch.utils.Texture;
import com.marklynch.utils.TextureUtils;

public class AnimationThrown extends Animation {

	public String name;
	float x, y, originX, originY, targetX, targetY, speedX, speedY;
	float angleInRadians = 0;
	boolean onTarget;
	String imagePath;
	Texture imageTexture;
	float distanceToCoverX, distanceToCoverY, distanceCoveredX, distanceCoveredY;
	float rotationSpeed = 0;

	public AnimationThrown(String name, Actor shooter, Action action, GameObject performer, Square targetSquare,
			GameObject projectileObject, GameObject weapon, float speed, float rotationSpeed, boolean onTarget,
			OnCompletionListener onCompletionListener) {

		super(performer, onCompletionListener, null, targetSquare, projectileObject, action, shooter, weapon, shooter,
				performer, targetSquare);

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
		this.performer = performer;
		this.targetSquare = targetSquare;
		this.projectileObject = projectileObject;

		if (shooter.backwards)
			this.x = this.originX = shooter.squareGameObjectIsOn.xInGridPixels;// shooter.getCenterX();
		else
			this.x = this.originX = shooter.squareGameObjectIsOn.xInGridPixels;// + Game.SQUARE_WIDTH;//
																				// shooter.getCenterX();

		projectileObject.backwards = shooter.backwards;

		this.y = this.originY = shooter.actorPositionYInPixels + shooter.shoulderY;// shooter.getCenterY();

		this.targetX = this.targetSquare.xInGridPixels + Game.HALF_SQUARE_WIDTH * this.projectileObject.drawOffsetRatioX
				+ this.projectileObject.width / 2f;
		this.targetY = this.targetSquare.yInGridPixels
				+ Game.HALF_SQUARE_HEIGHT * this.projectileObject.drawOffsetRatioY + this.projectileObject.height / 2f;

		this.targetY += Math.random() * 16f;
		this.targetY -= 8;

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
			Square square = Game.level.squares[(int) Math.floor(x / Game.SQUARE_WIDTH)][(int) Math
					.floor(y / Game.SQUARE_HEIGHT)];
			square.inventory.smashWindows(shooter);

		}

		float progress = distanceCoveredX / distanceToCoverX;
		angleInRadians = progress * 6.28f;
		if (rotationSpeed < 0)
			angleInRadians = -angleInRadians;
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
		Matrix4f view = Game.activeBatch.getViewMatrix();
		view.translate(new Vector2f(x + projectileObject.halfWidth, y + projectileObject.halfHeight));
		view.rotate(angleInRadians, new Vector3f(0f, 0f, 1f));
		view.translate(new Vector2f(-(x + projectileObject.halfWidth), -(y + projectileObject.halfHeight)));
		Game.activeBatch.updateUniforms();

		TextureUtils.drawTexture(projectileObject.imageTexture, 1.0f, x, y, x + projectileObject.width,
				y + projectileObject.height, projectileObject.backwards);

		Game.flush();
		view.translate(new Vector2f(x + projectileObject.halfWidth, y + projectileObject.halfHeight));
		view.rotate(-angleInRadians, new Vector3f(0f, 0f, 1f));
		view.translate(new Vector2f(-(x + projectileObject.halfWidth), -(y + projectileObject.halfHeight)));
		Game.activeBatch.updateUniforms();
	}

	@Override
	public void draw1() {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawStaticUI() {
	}

	// public static void smashContainer(Actor performer, GameObject
	// performer, ContainerForLiquids container) {
	// if (performer != null)
	// performer.squareGameObjectIsOn.inventory.add(container);
	//
	// new ActionSmash(performer, container).perform();
	//
	// // Find a square for broken glass and put it there
	// Square squareForGlass = null;
	// if (!container.squareGameObjectIsOn.inventory.contains(Wall.class)) {
	// squareForGlass = container.squareGameObjectIsOn;
	// }
	//
	// if (squareForGlass != null)
	// Templates.BROKEN_GLASS.makeCopy(squareForGlass, container.owner);
	//
	// System.out.println("container.liquid = " + container.liquid);
	// System.out.println("squareForGlass = " + squareForGlass);
	//
	// if (container.liquid != null && squareForGlass != null) {
	// Liquid liquid = container.liquid;
	// for (GameObject gameObject :
	// container.squareGameObjectIsOn.inventory.getGameObjects()) {
	// System.out.println("gameObject = " + gameObject);
	// if (gameObject != container) {
	// for (Effect effect : liquid.touchEffects) {
	// System.out.println("effect = " + effect);
	// gameObject.addEffect(effect.makeCopy(performer, gameObject));
	// }
	// if (gameObject instanceof Actor)
	// gameObject.addEffect(new EffectBleed(performer, performer, 5));
	// }
	//
	// }
	// }
	// for (GameObject gameObject :
	// container.squareGameObjectIsOn.inventory.getGameObjects()) {
	// if (gameObject instanceof Actor)
	// gameObject.addEffect(new EffectBleed(performer, performer, 5));
	//
	// }
	//
	// }

	@Override
	public void childRunCompletionAlgorightm(boolean wait) {
		// super.runCompletionAlgorightm(wait);
		postRangedAnimation();
	}

	public void postRangedAnimation() {

		if (performer != null && performer.attackable)
			performer.showPow();
		if (!(projectileObject instanceof Arrow)) {
			if (performer != null && performer instanceof Searchable && projectileObject.canShareSquare) {
				performer.inventory.add(projectileObject);
			} else {
				targetSquare.inventory.add(projectileObject);
				projectileObject.landed(shooter, action);
			}

		} else if (performer != null) {
			performer.arrowsEmbeddedInThis.add((Arrow) projectileObject);
		}

		if (Level.player.inventory.groundDisplay != null)
			Level.player.inventory.groundDisplay.refreshGameObjects();

		// Carry out the dmg, attack, logging...
		if (performer != null && performer.attackable && !(performer instanceof Searchable)) {
			float damage = performer.changeHealth(shooter, action, weapon);

			if (shooter.squareGameObjectIsOn.visibleToPlayer) {
				if (Game.level.shouldLog(performer, shooter))
					Game.level.logOnScreen(new ActivityLog(new Object[] { shooter, " threw ", weapon, " at ", performer,
							" for " + damage + " damage" }));
			}

			if (performer instanceof Actor && performer.remainingHealth > 0)
				performer.setPrimaryAnimation(new AnimationFlinch(performer, shooter.squareGameObjectIsOn,
						performer.getPrimaryAnimation(), null));
		} else if (performer != null && performer instanceof Searchable) {
			Game.level.logOnScreen(new ActivityLog(new Object[] { shooter, " threw ", weapon, " in to ", performer }));
		}
	}

	@Override
	public void draw3() {
		// TODO Auto-generated method stub

	}
}
