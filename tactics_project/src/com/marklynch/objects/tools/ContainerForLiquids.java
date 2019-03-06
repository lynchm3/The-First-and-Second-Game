package com.marklynch.objects.tools;

import com.marklynch.Game;
import com.marklynch.actions.Action;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.constructs.effect.EffectWet;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.inanimateobjects.Liquid;
import com.marklynch.objects.inanimateobjects.Wall;
import com.marklynch.objects.templates.Templates;
import com.marklynch.objects.utils.Consumable;
import com.marklynch.ui.ActivityLog;
import com.marklynch.utils.ArrayList;

public class ContainerForLiquids extends Tool implements Consumable {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>(GameObject.class);
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

		Square squareLandedOn = this.squareGameObjectIsOn;

		this.changeHealth(-this.remainingHealth, shooter, action);
		this.canShareSquare = true;
		this.blocksLineOfSight = false;

		soundDampening = 1;
		this.activeEffectsOnGameObject.clear();

		// Find a square for broken glass and put it there
		Square squareForGlass = null;

		if (!squareLandedOn.inventory.containsGameObjectOfType(Wall.class)) {
			squareForGlass = squareLandedOn;
		} else {
			if (squareForGlass == null && squareLandedOn.xInGrid > shooter.squareGameObjectIsOn.xInGrid
					&& squareLandedOn.xInGrid < Game.level.squares.length - 1) {
				squareForGlass = Game.level.squares[squareLandedOn.xInGrid - 1][squareLandedOn.yInGrid];
				if (!squareForGlass.inventory.canShareSquare) {
					squareForGlass = null;
				}
			}
			if (squareForGlass == null && squareLandedOn.xInGrid < shooter.squareGameObjectIsOn.xInGrid
					&& squareLandedOn.xInGrid > 0) {
				squareForGlass = Game.level.squares[squareLandedOn.xInGrid + 1][squareLandedOn.yInGrid];
				if (!squareForGlass.inventory.canShareSquare) {
					squareForGlass = null;
				}
			}
			if (squareForGlass == null && squareLandedOn.yInGrid > shooter.squareGameObjectIsOn.yInGrid
					&& squareLandedOn.yInGrid < Game.level.squares[0].length - 1) {
				squareForGlass = Game.level.squares[squareLandedOn.xInGrid][squareLandedOn.yInGrid - 1];
				if (!squareForGlass.inventory.canShareSquare) {
					squareForGlass = null;
				}
			}
			if (squareForGlass == null && squareLandedOn.yInGrid < shooter.squareGameObjectIsOn.yInGrid
					&& squareLandedOn.yInGrid > 0) {
				squareForGlass = Game.level.squares[squareLandedOn.xInGrid][squareLandedOn.yInGrid + 1];
				if (!squareForGlass.inventory.canShareSquare) {
					squareForGlass = null;
				}
			}
		}

		if (squareForGlass != null)
			Templates.BROKEN_GLASS.makeCopy(squareForGlass, this.owner);

		if (Game.level.shouldLog(this))
			Game.level.logOnScreen(new ActivityLog(new Object[] { this, " smashed" }));

		if (this.liquid != null && squareLandedOn != null) {
			Liquid liquid = this.liquid;
			squareLandedOn.liquidSpread(liquid);
			for (GameObject gameObject : (ArrayList<GameObject>) squareLandedOn.inventory.getGameObjects().clone()) {
				if (gameObject != this) {
					// new ActionDouse(shooter, gameObject).perform();
					for (Effect effect : liquid.touchEffects) {
						gameObject.addEffect(effect.makeCopy(shooter, gameObject));
						if (effect instanceof EffectWet) {
							gameObject.removeBurningEffect();
						}
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
