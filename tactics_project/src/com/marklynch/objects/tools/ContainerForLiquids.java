package com.marklynch.objects.tools;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.constructs.effect.EffectWet;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.Consumable;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Liquid;
import com.marklynch.objects.Wall;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.templates.Templates;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ContainerForLiquids extends Tool implements Consumable {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();
	public float volume;
	public Liquid liquid;

	public ContainerForLiquids() {

		super();
		type = "Container";

	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	@Override
	public ContainerForLiquids makeCopy(Square square, Actor owner) {
		ContainerForLiquids weapon = new ContainerForLiquids();
		setInstances(weapon);
		setAttributesForCopy(weapon, square, owner);
		weapon.volume = volume;
		if (this.liquid != null)
			weapon.liquid = liquid.makeCopy(null, owner, volume);
		return weapon;
	}

	@Override
	public void landed(Actor shooter, Action action) {

		this.changeHealth(-this.remainingHealth, null, null);
		this.canShareSquare = true;
		this.blocksLineOfSight = false;

		soundDampening = 1;
		this.activeEffectsOnGameObject.clear();

		// Find a square for broken glass and put it there
		Square squareForGlass = null;
		if (!this.squareGameObjectIsOn.inventory.contains(Wall.class)) {
			squareForGlass = this.squareGameObjectIsOn;
		} else {
			if (squareForGlass == null && this.squareGameObjectIsOn.xInGrid > shooter.squareGameObjectIsOn.xInGrid
					&& this.squareGameObjectIsOn.xInGrid < Game.level.squares.length - 1) {
				squareForGlass = Game.level.squares[this.squareGameObjectIsOn.xInGrid
						- 1][this.squareGameObjectIsOn.yInGrid];
				if (!squareForGlass.inventory.canShareSquare) {
					squareForGlass = null;
				}
			}
			if (squareForGlass == null && this.squareGameObjectIsOn.xInGrid < shooter.squareGameObjectIsOn.xInGrid
					&& this.squareGameObjectIsOn.xInGrid > 0) {
				squareForGlass = Game.level.squares[this.squareGameObjectIsOn.xInGrid
						+ 1][this.squareGameObjectIsOn.yInGrid];
				if (!squareForGlass.inventory.canShareSquare) {
					squareForGlass = null;
				}
			}
			if (squareForGlass == null && this.squareGameObjectIsOn.yInGrid > shooter.squareGameObjectIsOn.yInGrid
					&& this.squareGameObjectIsOn.yInGrid < Game.level.squares[0].length - 1) {
				squareForGlass = Game.level.squares[this.squareGameObjectIsOn.xInGrid][this.squareGameObjectIsOn.yInGrid
						- 1];
				if (!squareForGlass.inventory.canShareSquare) {
					squareForGlass = null;
				}
			}
			if (squareForGlass == null && this.squareGameObjectIsOn.yInGrid < shooter.squareGameObjectIsOn.yInGrid
					&& this.squareGameObjectIsOn.yInGrid > 0) {
				squareForGlass = Game.level.squares[this.squareGameObjectIsOn.xInGrid][this.squareGameObjectIsOn.yInGrid
						+ 1];
				if (!squareForGlass.inventory.canShareSquare) {
					squareForGlass = null;
				}
			}
		}

		if (squareForGlass != null)
			Templates.BROKEN_GLASS.makeCopy(squareForGlass, this.owner);

		if (Game.level.shouldLog(this))
			Game.level.logOnScreen(new ActivityLog(new Object[] { this, " smashed" }));

		if (this.liquid != null) {
			Liquid liquid = this.liquid;
			for (GameObject gameObject : this.squareGameObjectIsOn.inventory.getGameObjects()) {
				if (gameObject != this) {
					// new ActionDouse(shooter, gameObject).perform();
					for (Effect effect : liquid.touchEffects) {
						gameObject.addEffect(effect.makeCopy(shooter, gameObject));
						if (effect instanceof EffectWet)
							gameObject.removeBurningEffect();
					}
				}
			}
		}

	}

	@Override
	public Effect[] getConsumeEffects() {
		return liquid.consumeEffects;
	}

}
