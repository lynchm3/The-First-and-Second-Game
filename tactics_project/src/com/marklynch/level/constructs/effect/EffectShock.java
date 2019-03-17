package com.marklynch.level.constructs.effect;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import com.marklynch.Game;
import com.marklynch.level.constructs.Stat;
import com.marklynch.level.constructs.Stat.HIGH_LEVEL_STATS;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.templates.Templates;
import com.marklynch.ui.ActivityLog;
import com.marklynch.utils.ArrayList;
import com.marklynch.utils.Color;
import com.marklynch.utils.Texture;
import com.marklynch.utils.TextureUtils;

public class EffectShock extends Effect {

	public static Texture electricityTexture = getGlobalImage("electricity.png", false);

	ArrayList<Electricity> electricities = new ArrayList<Electricity>(Electricity.class);

	public EffectShock() {
	}

	public EffectShock(GameObject source, GameObject target, int totalTurns) {
		this.logString = " shocked by ";
		this.effectName = "Shock";
		this.source = source;
		this.target = target;
		this.totalTurns = totalTurns;
		this.turnsRemaining = totalTurns;
		this.imageTexture = getGlobalImage("spark.png", false);
		highLevelStats.put(HIGH_LEVEL_STATS.ELECTRICAL_DAMAGE, new Stat(HIGH_LEVEL_STATS.ELECTRICAL_DAMAGE, 5));
		for (int i = 0; i < 5; i++) {
			electricities.add(new Electricity());
		}
	}

	@Override
	public void activate() {
		float damage = target.changeHealth(this, null, this);

		if (target.attackable && Game.level.shouldLog(target))
			Game.level.logOnScreen(new ActivityLog(new Object[] { target, " lost " + damage + " HP to ", this }));

		turnsRemaining--;

	}

	@Override
	public EffectShock makeCopy(GameObject source, GameObject target) {
		return new EffectShock(source, target, totalTurns);
	}

	public void onAdd() {

		// If in world (not in inventory)
		Square squareTargetIsOn = target.squareGameObjectIsOn;
		if (squareTargetIsOn != null) {
			ArrayList<Square> adjacentSquares = target.getAllSquaresWithinDistance(0, 1);
			for (Square adjacentSquare : adjacentSquares) {
				if (adjacentSquare.inventory.containsGameObjectWithTemplateId(Templates.WATER.templateId)) {
					for (GameObject gameObject : adjacentSquare.inventory.getGameObjects()) {
						gameObject.addEffect(this.makeCopy(this.source, gameObject));
					}
				}
			}
		}
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

			for (Electricity electricity : electricities) {
				electricity.draw2(actorPositionXInPixels, actorPositionYInPixels);
			}
		}

	}

	private class Electricity {

		public static final float maxDropletScale = 32;
		public static final float minX = 52;
		public static final float maxX = 70;
		public static final float minY = 40;
		public static final float maxY = 188;
		public float x, y;
		public float scale = 0;
		boolean gettingBigger = true;

		public Electricity() {
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

			TextureUtils.drawTexture(electricityTexture, 1f,
					actorPositionXInPixels + x + maxDropletScale / 2 - scale / 2, actorPositionYInPixels + y - scale,
					actorPositionXInPixels + x + scale, actorPositionYInPixels + y, target.backwards);

		}

	}

	@Override
	public Color getColor() {
		return Color.CYAN;
	}

}
