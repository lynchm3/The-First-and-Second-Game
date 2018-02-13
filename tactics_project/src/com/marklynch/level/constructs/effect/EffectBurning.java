package com.marklynch.level.constructs.effect;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import java.util.Vector;

import com.marklynch.Game;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.ui.ActivityLog;

public class EffectBurning extends Effect {

	public EffectBurning(GameObject source, GameObject target, int totalTurns) {
		this.logString = " burned by ";
		this.effectName = "Burn";
		this.source = source;
		this.target = target;
		this.totalTurns = totalTurns;
		this.turnsRemaining = totalTurns;
		this.imageTexture = getGlobalImage("effect_burn.png", false);
		this.fireDamage = 5;
	}

	public EffectBurning(int totalTurns) {
		this(null, null, totalTurns);
	}

	@Override
	public void activate() {

		// float damage = 5 - (10 * (target.getEffectiveFireResistance() /
		// 100f));
		float damage = target.changeHealth(this, null, this);
		if (Game.level.shouldLog(target))
			Game.level.logOnScreen(new ActivityLog(new Object[] { target, " lost " + damage + " HP to ", this }));
		// target.attackedBy(this, null);

		// Spread fire if not turn 1
		if (totalTurns != turnsRemaining) {

			// If in world (not in inventory)
			Square squareTargetIsOn = target.squareGameObjectIsOn;
			if (squareTargetIsOn != null) {
				for (GameObject gameObject : squareTargetIsOn.inventory.getGameObjects()) {

					if (gameObject != target && Math.random() * 100 > gameObject.getEffectiveFireResistance()) {
						gameObject.removeWetEffect();
						gameObject.addEffect(this.makeCopy(source, gameObject));
						if (Game.level.shouldLog(gameObject))
							Game.level.logOnScreen(new ActivityLog(new Object[] { this, " spread to ", gameObject }));
					}
				}

				Vector<Square> adjacentSquares = target.getAllSquaresAtDistance(1);
				for (Square adjacentSquare : adjacentSquares) {
					for (GameObject gameObject : adjacentSquare.inventory.getGameObjects()) {
						if (Math.random() * 100 > gameObject.getEffectiveFireResistance()) {
							gameObject.removeWetEffect();
							gameObject.addEffect(this.makeCopy(source, gameObject));

							if (Game.level.shouldLog(gameObject))
								Game.level
										.logOnScreen(new ActivityLog(new Object[] { this, " spread to ", gameObject }));
						}
					}
				}
			}

			// If in inventory
			// InventorySquare inventorySquareTargetIsOn =
			// target.inventorySquareGameObjectIsOn;
			// if (inventorySquareTargetIsOn != null) {
			//
			// Vector<InventorySquare> adjacentSquares =
			// inventorySquareTargetIsOn.getAllInventorySquaresAtDistance(1);
			// for (InventorySquare adjacentSquare : adjacentSquares) {
			// if (adjacentSquare.gameObject != null
			// && Math.random() * 100 >
			// adjacentSquare.gameObject.getEffectiveFireResistance()) {
			// adjacentSquare.gameObject.removeWetEffect();
			// adjacentSquare.gameObject.addEffect(this.makeCopy(source,
			// adjacentSquare.gameObject));
			// if (inventorySquareTargetIsOn.inventoryThisBelongsTo.parent ==
			// Game.level.player)
			// Game.level.logOnScreen(
			// new ActivityLog(new Object[] { this, " spread to ",
			// adjacentSquare.gameObject }));
			// }
			//
			// }
			// }
		}

		turnsRemaining--;

	}

	@Override
	public EffectBurning makeCopy(GameObject source, GameObject target) {
		return new EffectBurning(source, target, totalTurns);
	}

}
