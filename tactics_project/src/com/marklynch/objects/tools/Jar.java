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
import com.marklynch.ui.ActivityLog;
import com.marklynch.utils.CopyOnWriteArrayList;

public class Jar extends Tool {

	public static final CopyOnWriteArrayList<GameObject> instances = new CopyOnWriteArrayList<GameObject>(GameObject.class);
	public float volume;
	public GameObject contents;

	public Jar() {

		super();
		type = "Container";

	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	@Override
	public Jar makeCopy(Square square, Actor owner) {
		Jar weapon = new Jar();
		setInstances(weapon);
		setAttributesForCopy(weapon, square, owner);
		weapon.volume = volume;
		if (this.contents != null)
			weapon.contents = contents.makeCopy(null, owner);
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
		Square squareFoGlassAndLiquid = null;

		if (!squareLandedOn.inventory.containsGameObjectOfType(Wall.class)) {
			squareFoGlassAndLiquid = squareLandedOn;
		} else {
			if (squareFoGlassAndLiquid == null && squareLandedOn.xInGrid > shooter.squareGameObjectIsOn.xInGrid
					&& squareLandedOn.xInGrid < Game.level.squares.length - 1) {
				squareFoGlassAndLiquid = Game.level.squares[squareLandedOn.xInGrid - 1][squareLandedOn.yInGrid];
				if (!squareFoGlassAndLiquid.inventory.canShareSquare) {
					squareFoGlassAndLiquid = null;
				}
			}
			if (squareFoGlassAndLiquid == null && squareLandedOn.xInGrid < shooter.squareGameObjectIsOn.xInGrid
					&& squareLandedOn.xInGrid > 0) {
				squareFoGlassAndLiquid = Game.level.squares[squareLandedOn.xInGrid + 1][squareLandedOn.yInGrid];
				if (!squareFoGlassAndLiquid.inventory.canShareSquare) {
					squareFoGlassAndLiquid = null;
				}
			}
			if (squareFoGlassAndLiquid == null && squareLandedOn.yInGrid > shooter.squareGameObjectIsOn.yInGrid
					&& squareLandedOn.yInGrid < Game.level.squares[0].length - 1) {
				squareFoGlassAndLiquid = Game.level.squares[squareLandedOn.xInGrid][squareLandedOn.yInGrid - 1];
				if (!squareFoGlassAndLiquid.inventory.canShareSquare) {
					squareFoGlassAndLiquid = null;
				}
			}
			if (squareFoGlassAndLiquid == null && squareLandedOn.yInGrid < shooter.squareGameObjectIsOn.yInGrid
					&& squareLandedOn.yInGrid > 0) {
				squareFoGlassAndLiquid = Game.level.squares[squareLandedOn.xInGrid][squareLandedOn.yInGrid + 1];
				if (!squareFoGlassAndLiquid.inventory.canShareSquare) {
					squareFoGlassAndLiquid = null;
				}
			}
		}

		if (squareFoGlassAndLiquid != null)
			Templates.BROKEN_GLASS.makeCopy(squareFoGlassAndLiquid, this.owner);

		if (Game.level.shouldLog(this))
			Game.level.logOnScreen(new ActivityLog(new Object[] { this, " smashed" }));

		if (this.contents != null && squareFoGlassAndLiquid != null) {

			if (this.contents instanceof Liquid) {
				Liquid liquid = (Liquid) this.contents;
				squareFoGlassAndLiquid.liquidSpread(liquid);
				for (GameObject gameObject : (CopyOnWriteArrayList<GameObject>) squareFoGlassAndLiquid.inventory.getGameObjects()
						) {
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
			} else { // Not a liquid
				squareFoGlassAndLiquid.inventory.add(contents);
			}
		}

	}

	@Override
	public Effect[] getConsumeEffects() {
		return contents.consumeEffects;
	}

}
