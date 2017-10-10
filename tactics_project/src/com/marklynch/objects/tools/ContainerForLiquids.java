package com.marklynch.objects.tools;

import com.marklynch.Game;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.constructs.effect.EffectWet;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Liquid;
import com.marklynch.objects.Templates;
import com.marklynch.objects.Wall;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;
import com.marklynch.utils.ResourceUtils;

import mdesl.graphics.Color;
import mdesl.graphics.Texture;

public class ContainerForLiquids extends Tool {
	protected String baseName;
	public float volume;
	public Texture baseImage;
	public Texture imageWhenFull;
	public String imageWhenFullPath;
	public int baseValue;

	public ContainerForLiquids(String name, float slashDamage, float pierceDamage, float bluntDamage, float fireDamage,
			float waterDamage, float electricalDamage, float poisonDamage, float minRange, float maxRange,
			String imagePath, float health, Square squareGameObjectIsOn, float widthRatio, float heightRatio,
			float drawOffsetX, float drawOffsetY, float soundWhenHit, float soundWhenHitting, float soundDampening,
			Color light, float lightHandleX, float lightHandlY, boolean stackable, float fireResistance,
			float waterResistance, float electricResistance, float poisonResistance, float slashResistance,
			float weight, int value, Actor owner, float anchorX, float anchorY, float volume, String imageWhenFullPath,
			int templateId) {

		super(name, slashDamage, pierceDamage, bluntDamage, fireDamage, waterDamage, electricalDamage, poisonDamage,
				minRange, maxRange, imagePath, health, squareGameObjectIsOn, widthRatio, heightRatio, drawOffsetX,
				drawOffsetY, soundWhenHit, soundWhenHitting, soundDampening, light, lightHandleX, lightHandlY,
				stackable, fireResistance, waterResistance, electricResistance, poisonResistance, slashResistance,
				weight, value, owner, anchorX, anchorY, templateId);
		this.baseValue = value;
		this.volume = volume;
		this.imageWhenFullPath = imageWhenFullPath;
		baseName = new String(name);
		baseImage = this.imageTexture;
		this.imageWhenFull = ResourceUtils.getGlobalImage(imageWhenFullPath);
		if (this.inventory.size() == 0) {
			this.name = baseName + " (empty)";
			this.imageTexture = baseImage;
			this.baseValue = value;
		} else {
			this.name = baseName + " of " + inventory.get(0).name;
			this.imageTexture = imageWhenFull;
			this.value = baseValue + inventory.get(0).value;
		}

	}

	@Override
	public ContainerForLiquids makeCopy(Square square, Actor owner) {

		return new ContainerForLiquids(new String(baseName), slashDamage, pierceDamage, bluntDamage, fireDamage,
				waterDamage, electricalDamage, poisonDamage, minRange, maxRange, imageTexturePath, (int) totalHealth,
				squareGameObjectIsOn, widthRatio, heightRatio, drawOffsetX, drawOffsetY, soundWhenHit, soundWhenHitting,
				soundDampening, light, lightHandleX, lightHandlY, stackable, fireResistance, waterResistance,
				electricResistance, poisonResistance, slashResistance, weight, value, owner, anchorX, anchorY, volume,
				imageWhenFullPath, templateId);
	}

	@Override
	public void inventoryChanged() {
		if (this.inventory.size() == 0) {
			this.name = baseName + " (empty)";
			this.imageTexture = baseImage;
		} else {
			this.name = baseName + " of " + inventory.get(0).name;
			this.imageTexture = imageWhenFull;
			this.value = baseValue + inventory.get(0).value;
		}
	}

	@Override
	public void landed(Actor shooter, Action action) {

		this.remainingHealth = 0;
		this.canShareSquare = true;
		this.blocksLineOfSight = false;
		persistsWhenCantBeSeen = false;
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
				if (!squareForGlass.inventory.canShareSquare()) {
					squareForGlass = null;
				}
			}
			if (squareForGlass == null && this.squareGameObjectIsOn.xInGrid < shooter.squareGameObjectIsOn.xInGrid
					&& this.squareGameObjectIsOn.xInGrid > 0) {
				squareForGlass = Game.level.squares[this.squareGameObjectIsOn.xInGrid
						+ 1][this.squareGameObjectIsOn.yInGrid];
				if (!squareForGlass.inventory.canShareSquare()) {
					squareForGlass = null;
				}
			}
			if (squareForGlass == null && this.squareGameObjectIsOn.yInGrid > shooter.squareGameObjectIsOn.yInGrid
					&& this.squareGameObjectIsOn.yInGrid < Game.level.squares[0].length - 1) {
				squareForGlass = Game.level.squares[this.squareGameObjectIsOn.xInGrid][this.squareGameObjectIsOn.yInGrid
						- 1];
				if (!squareForGlass.inventory.canShareSquare()) {
					squareForGlass = null;
				}
			}
			if (squareForGlass == null && this.squareGameObjectIsOn.yInGrid < shooter.squareGameObjectIsOn.yInGrid
					&& this.squareGameObjectIsOn.yInGrid > 0) {
				squareForGlass = Game.level.squares[this.squareGameObjectIsOn.xInGrid][this.squareGameObjectIsOn.yInGrid
						+ 1];
				if (!squareForGlass.inventory.canShareSquare()) {
					squareForGlass = null;
				}
			}
		}

		if (squareForGlass != null)
			Templates.BROKEN_GLASS.makeCopy(squareForGlass, this.owner);

		if (Game.level.shouldLog(this))
			Game.level.logOnScreen(new ActivityLog(new Object[] { this, " smashed" }));

		if (this.inventory.size() > 0 && this.inventory.get(0) instanceof Liquid) {
			Liquid liquid = (Liquid) this.inventory.get(0);
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

}
