package com.marklynch.level.constructs.effect;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import com.marklynch.Game;
import com.marklynch.level.constructs.Stat;
import com.marklynch.level.constructs.Stat.HIGH_LEVEL_STATS;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.tools.FlammableLightSource;
import com.marklynch.ui.ActivityLog;
import com.marklynch.utils.ArrayList;
import com.marklynch.utils.Color;
import com.marklynch.utils.Texture;
import com.marklynch.utils.TextureUtils;

public class EffectBurn extends Effect {

	public static Texture flameTexture = getGlobalImage("flame.png", false);

	ArrayList<Flame> flames = new ArrayList<Flame>(Flame.class);

	public EffectBurn() {
	}

	public EffectBurn(int totalTurns) {
		this.totalTurns = totalTurns;
		this.turnsRemaining = totalTurns;
	}

	public EffectBurn(GameObject source, GameObject target, int totalTurns) {
		this.logString = " burned by ";
		this.effectName = "Burn";
		this.source = source;
		this.target = target;
		this.totalTurns = totalTurns;
		this.turnsRemaining = totalTurns;
		this.imageTexture = getGlobalImage("effect_burn.png", false);
		highLevelStats.put(HIGH_LEVEL_STATS.FIRE_DAMAGE, new Stat(HIGH_LEVEL_STATS.FIRE_DAMAGE, 5));
		for (int i = 0; i < 5; i++) {
			flames.add(new Flame());
		}
	}

	@Override
	public void activate() {

		// float damage = 5 - (10 * (target.getEffectiveFireResistance() /
		// 100f));
		if (target instanceof FlammableLightSource) {
			((FlammableLightSource) target).setLighting(true);
			if (Game.level.shouldLog(this)) {
				Game.level.logOnScreen(new ActivityLog(new Object[] { this, " was lit" }));
			}
			turnsRemaining = 0;
			return;
		} else {
			float damage = target.changeHealth(this, null, this);
			if (Game.level.shouldLog(target))
				Game.level.logOnScreen(new ActivityLog(new Object[] { target, " lost " + damage + " HP to ", this }));
		}
		// target.attackedBy(this, null);

		// Spread fire if not turn 1
		if (totalTurns != turnsRemaining) {

			// If in world (not in inventory)
			Square squareTargetIsOn = target.squareGameObjectIsOn;
			if (squareTargetIsOn != null) {
				for (GameObject gameObject : squareTargetIsOn.inventory.getGameObjects()) {

					if (gameObject != target
							&& Math.random() * 100 > gameObject.highLevelStats.get(HIGH_LEVEL_STATS.FIRE_RES).value) {
						gameObject.removeWetEffect();
						gameObject.addEffect(this.makeCopy(source, gameObject));
						if (Game.level.shouldLog(gameObject))
							Game.level.logOnScreen(new ActivityLog(new Object[] { this, " spread to ", gameObject }));
					}
				}

				ArrayList<Square> adjacentSquares = target.getAllSquaresAtDistance(1);
				for (Square adjacentSquare : adjacentSquares) {
					for (GameObject gameObject : adjacentSquare.inventory.getGameObjects()) {

						if (Math.random() * 100 > gameObject.highLevelStats.get(HIGH_LEVEL_STATS.FIRE_RES).value) {
							gameObject.removeWetEffect();
							gameObject.addEffect(this.makeCopy(source, gameObject));

							if (Game.level.shouldLog(gameObject))
								Game.level
										.logOnScreen(new ActivityLog(new Object[] { this, " spread to ", gameObject }));
						}
					}
				}
			}
		}

		turnsRemaining--;
	}

	@Override
	public EffectBurn makeCopy(GameObject source, GameObject target) {
		return new EffectBurn(source, target, totalTurns);
	}

	public void onAdd() {
	}

	@Override
	public void draw2(int offsetY) {

		super.draw2(offsetY);

		if (!target.squareGameObjectIsOn.visibleToPlayer)
			return;

		if (target.squareGameObjectIsOn != null) {
//			target.actor

			int actorPositionXInPixels = (int) (target.squareGameObjectIsOn.xInGridPixels + target.drawOffsetX);
			int actorPositionYInPixels = (int) (target.squareGameObjectIsOn.yInGridPixels + target.drawOffsetY);
			if (target != null && target.getPrimaryAnimation() != null) {
				actorPositionXInPixels += target.getPrimaryAnimation().offsetX;
				actorPositionYInPixels += target.getPrimaryAnimation().offsetY;
			}

//			float alpha = 1.0f;
//			alpha = 0.75f;
//			TextureUtils.drawTexture(imageTexture, alpha, actorPositionXInPixels,
//					actorPositionYInPixels + offsetY * maxDropletHeight, actorPositionXInPixels + maxDropletHeight,
//					actorPositionYInPixels + offsetY * maxDropletHeight + maxDropletHeight, target.backwards);

			for (Flame flame : flames) {
				flame.draw2(actorPositionXInPixels, actorPositionYInPixels);
			}
		}

	}

	private class Flame {

		public static final float maxDropletScale = 8;
		public static final float minX = 52;
		public static final float maxX = 70;
		public static final float minY = 40;
		public static final float maxY = 188;
		public float x, y;
		public float scale = 0;
		boolean gettingBigger = true;

		public Flame() {
			scale = (float) (maxDropletScale * Math.random());
			x = (float) (Math.random() * (maxX - minX)) + minX;
			y = (float) (Math.random() * (maxY - minY)) + minY;
		}

		public void draw2(int actorPositionXInPixels, int actorPositionYInPixels) {

			if (gettingBigger) {
				scale += 0.5f;
				if (scale >= maxDropletScale) {
					scale = maxDropletScale;
					gettingBigger = false;
				}

			} else {
				scale -= 0.5f;
				if (scale <= 0) {
					scale = 0;
					x = (float) (Math.random() * (maxX - minX)) + minX;
					y = (float) (Math.random() * (maxY - minY)) + minY;
					gettingBigger = true;
				}

			}

			TextureUtils.drawTexture(flameTexture, 1f, actorPositionXInPixels + x + maxDropletScale / 2 - scale / 2,
					actorPositionYInPixels + y - scale, actorPositionXInPixels + x + scale, actorPositionYInPixels + y,
					target.backwards);

		}

	}

	@Override
	public Color getColor() {
		return Color.ORANGE;
	}

}
