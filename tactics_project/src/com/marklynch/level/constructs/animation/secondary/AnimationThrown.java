package com.marklynch.level.constructs.animation.secondary;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.animation.Animation;
import com.marklynch.level.constructs.animation.primary.AnimationFlinch;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.constructs.effect.EffectBleed;
import com.marklynch.level.constructs.effect.EffectWet;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.Arrow;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Liquid;
import com.marklynch.objects.Searchable;
import com.marklynch.objects.Wall;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actions.ActionSmash;
import com.marklynch.objects.templates.Templates;
import com.marklynch.objects.tools.ContainerForLiquids;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;
import com.marklynch.utils.Texture;
import com.marklynch.utils.TextureUtils;

public class AnimationThrown extends Animation {

	public String name;
	public Actor shooter;
	public Action action;
	public GameObject targetGameObject;
	public Square targetSquare;
	float x, y, originX, originY, targetX, targetY, speedX, speedY;
	float angleInRadians = 0;
	boolean onTarget;
	String imagePath;
	Texture imageTexture;
	float distanceToCoverX, distanceToCoverY, distanceCoveredX, distanceCoveredY;
	GameObject projectileObject;
	float rotationSpeed = 0;
	GameObject weapon;

	public AnimationThrown(String name, Actor shooter, Action action, GameObject targetGameObject, Square targetSquare,
			GameObject projectileObject, GameObject weapon, float speed, float rotationSpeed, boolean onTarget) {

		super(null, shooter, targetGameObject, targetSquare);

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
		this.targetGameObject = targetGameObject;
		this.targetSquare = targetSquare;
		this.projectileObject = projectileObject;
		this.weapon = weapon;

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
			runCompletionAlgorightm();

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

	public static void smashContainer(Actor performer, GameObject targetGameObject, ContainerForLiquids container) {
		if (targetGameObject != null)
			targetGameObject.squareGameObjectIsOn.inventory.add(container);
		new ActionSmash(performer, container).perform();

		// Find a square for broken glass and put it there
		Square squareForGlass = null;
		if (!container.squareGameObjectIsOn.inventory.contains(Wall.class)) {
			squareForGlass = container.squareGameObjectIsOn;
		}

		if (squareForGlass != null)
			Templates.BROKEN_GLASS.makeCopy(squareForGlass, container.owner);

		if (container.inventory.size() > 0 && container.inventory.get(0) instanceof Liquid) {
			Liquid liquid = (Liquid) container.inventory.get(0);
			for (GameObject gameObject : container.squareGameObjectIsOn.inventory.getGameObjects()) {
				if (gameObject != container) {
					// new ActionDouse(shooter, gameObject).perform();
					for (Effect effect : liquid.touchEffects) {
						gameObject.addEffect(effect.makeCopy(performer, gameObject));
						if (effect instanceof EffectWet)
							gameObject.removeBurningEffect();
					}
					if (gameObject instanceof Actor)
						gameObject.addEffect(new EffectBleed(performer, targetGameObject, 5));
				}

			}
		}
		for (GameObject gameObject : container.squareGameObjectIsOn.inventory.getGameObjects()) {
			if (gameObject instanceof Actor)
				gameObject.addEffect(new EffectBleed(performer, targetGameObject, 5));

		}

	}

	public static void postRangedAnimation(Actor performer, GameObject weapon, GameObject target, Square targetSquare,
			GameObject projectileObject, Action action) {

		if (target != null && target.attackable)
			target.showPow();
		if (!(projectileObject instanceof Arrow)) {
			if (target != null && target instanceof Searchable && projectileObject.canShareSquare) {
				target.inventory.add(projectileObject);
			} else {
				targetSquare.inventory.add(projectileObject);
			}
			projectileObject.landed(performer, action);
		} else if (target != null) {
			target.arrowsEmbeddedInThis.add((Arrow) projectileObject);
		}

		if (Level.player.inventory.groundDisplay != null)
			Level.player.inventory.groundDisplay.refreshGameObjects();

		// Carry out the dmg, attack, logging...
		if (target != null && target.attackable) {
			float damage = target.changeHealth(performer, action, weapon);
			String attackTypeString;
			attackTypeString = "attacked ";

			if (performer.squareGameObjectIsOn.visibleToPlayer) {

				if (weapon != performer) {
					if (Game.level.shouldLog(target, performer))
						Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " " + attackTypeString + " ",
								target, " with ", weapon, " for " + damage + " damage" }));
				} else {
					if (Game.level.shouldLog(target, performer))
						Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " " + attackTypeString + " ",
								target, " for " + damage + " damage" }));
				}
			}

			if (weapon instanceof ContainerForLiquids) {
				smashContainer(performer, target, (ContainerForLiquids) weapon);
			}

			if (target != null && target instanceof Actor && target.remainingHealth > 0)
				target.setPrimaryAnimation(
						new AnimationFlinch(target, performer.squareGameObjectIsOn, target.getPrimaryAnimation()));
		}
	}

	@Override
	public void draw3() {
		// TODO Auto-generated method stub

	}
}
