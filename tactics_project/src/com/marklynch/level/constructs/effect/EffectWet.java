package com.marklynch.level.constructs.effect;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import com.marklynch.Game;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.tools.FlammableLightSource;
import com.marklynch.ui.ActivityLog;
import com.marklynch.utils.CopyOnWriteArrayList;
import com.marklynch.utils.Color;
import com.marklynch.utils.Texture;
import com.marklynch.utils.TextureUtils;

public class EffectWet extends Effect {

	private static Color wetColor = new Color(0, 0, 255);

	public EffectWet() {
	}

	public static Texture dropletTexture = getGlobalImage("droplet.png", false);

	public EffectWet(GameObject source, GameObject target, int totalTurns) {
		this.logString = " wet by ";
		this.effectName = "Wet";
		this.source = source;
		this.target = target;
		this.totalTurns = totalTurns;
		this.turnsRemaining = totalTurns;
		this.imageTexture = getGlobalImage("effect_wet.png", false);
		for (int i = 0; i < 5; i++) {
			droplets.add(new Droplet());
		}
	}

	public EffectWet(int totalTurns) {
		this(null, null, totalTurns);
	}

	@Override
	public void activate() {
		if (target instanceof FlammableLightSource) {
			((FlammableLightSource) target).setLighting(false);

			if (Game.level.shouldLog(this)) {
				Game.level.logOnScreen(new ActivityLog(new Object[] { target, " was doused" }));
			}
		}
		turnsRemaining--;
	}

	@Override
	public EffectWet makeCopy(GameObject source, GameObject target) {
		return new EffectWet(source, target, totalTurns);
	}

	public void onAdd() {
		target.removeBurningEffect();
	}

	CopyOnWriteArrayList<Droplet> droplets = new CopyOnWriteArrayList<Droplet>(Droplet.class);

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

			float alpha = 1.0f;
			alpha = 0.75f;
//			TextureUtils.drawTexture(imageTexture, alpha, actorPositionXInPixels,
//					actorPositionYInPixels + offsetY * maxDropletHeight, actorPositionXInPixels + maxDropletHeight,
//					actorPositionYInPixels + offsetY * maxDropletHeight + maxDropletHeight, target.backwards);

			for (Droplet droplet : droplets) {
				droplet.draw2(actorPositionXInPixels, actorPositionYInPixels);
			}
		}

	}

	private class Droplet {

		public static final float maxDropletScale = 8;
		public static final float minX = 48;
		public static final float maxX = 66;
		public static final float minY = 40;
		public static final float maxY = 188;
		public float x, y;
		public float scale = 0;

		public Droplet() {
			scale = (float) (maxDropletScale * Math.random());
			x = (float) (Math.random() * (maxX - minX)) + minX;
			y = (float) (Math.random() * (maxY - minY)) + minY;
		}

		public void draw2(int actorPositionXInPixels, int actorPositionYInPixels) {

			y += 0.5f;
			if (y > maxY) {
				y = minY;
				scale = 0;
				x = (float) (Math.random() * (maxX - minX)) + minX;
			}

			if (scale < maxDropletScale) {
				scale += 0.5f;
			}

			TextureUtils.drawTexture(dropletTexture, 1f, actorPositionXInPixels + x + maxDropletScale / 2 - scale / 2,
					actorPositionYInPixels + y, actorPositionXInPixels + x + scale, actorPositionYInPixels + y + scale,
					target.backwards);

//			QuadUtils.drawQuad(Color.RED, actorPositionXInPixels + minX, actorPositionYInPixels + minY,
//					actorPositionXInPixels + maxX + maxDropletScale, actorPositionYInPixels + maxY);

		}

	}

	@Override
	public Color getColor() {
		return wetColor;
	}

}
