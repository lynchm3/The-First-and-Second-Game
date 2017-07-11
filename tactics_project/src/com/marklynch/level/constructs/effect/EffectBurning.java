package com.marklynch.level.constructs.effect;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import java.util.Vector;

import com.marklynch.Game;
import com.marklynch.level.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.ui.ActivityLog;
import com.marklynch.utils.TextureUtils;

public class EffectBurning extends Effect {

	public EffectBurning(GameObject source, GameObject target, int totalTurns) {
		this.logString = " burned by ";
		this.effectName = "Burn";
		this.source = source;
		this.target = target;
		this.totalTurns = totalTurns;
		this.turnsRemaining = totalTurns;
		this.image = getGlobalImage("effect_burn.png");
	}

	public EffectBurning(int totalTurns) {
		this(null, null, totalTurns);
	}

	@Override
	public void activate() {
		float damage = 10;
		target.remainingHealth -= damage;
		Game.level.logOnScreen(new ActivityLog(new Object[] { target, " lost " + damage + " HP to ", this }));
		target.attackedBy(this);

		// Spread fire if not turn 1
		if (totalTurns != turnsRemaining) {

			Square squareTargetIsOn = target.squareGameObjectIsOn;
			for (GameObject gameObject : squareTargetIsOn.inventory.getGameObjects()) {

				if (gameObject != target && Math.random() * 100 > gameObject.getEffectiveFireResistance()) {
					gameObject.removeWetEffect();
					gameObject.addEffect(this.makeCopy(source, gameObject));
					Game.level.logOnScreen(new ActivityLog(new Object[] { this, " spread to ", gameObject }));
				}
			}

			Vector<Square> adjacentSquares = target.getAllSquaresAtDistance(1);
			for (Square adjacentSquare : adjacentSquares) {
				for (GameObject gameObject : adjacentSquare.inventory.getGameObjects()) {
					if (Math.random() * 100 > gameObject.getEffectiveFireResistance()) {
						gameObject.removeWetEffect();
						gameObject.addEffect(this.makeCopy(source, gameObject));
						Game.level.logOnScreen(new ActivityLog(new Object[] { this, " spread to ", gameObject }));
					}
				}
			}
		}

		turnsRemaining--;

	}

	@Override
	public EffectBurning makeCopy(GameObject source, GameObject target) {
		return new EffectBurning(source, target, totalTurns);
	}

	@Override
	public void draw2() {
		// Draw object
		if (target.squareGameObjectIsOn != null) {

			int actorPositionXInPixels = (int) (target.squareGameObjectIsOn.xInGrid * (int) Game.SQUARE_WIDTH
					+ target.drawOffsetX);
			int actorPositionYInPixels = (int) (target.squareGameObjectIsOn.yInGrid * (int) Game.SQUARE_HEIGHT
					+ target.drawOffsetY);

			float alpha = 1.0f;

			// TextureUtils.skipNormals = true;

			if (!target.squareGameObjectIsOn.visibleToPlayer)
				alpha = 0.5f;
			if (target.hiding)
				alpha = 0.5f;

			TextureUtils.drawTexture(image, alpha, actorPositionXInPixels, actorPositionXInPixels + target.width,
					actorPositionYInPixels, actorPositionYInPixels + target.height, target.backwards);
			// TextureUtils.skipNormals = false;
		}
	}

}
