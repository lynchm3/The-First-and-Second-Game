package com.marklynch.objects.inanimateobjects;

import java.util.Random;

import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.constructs.effect.EffectBurn;
import com.marklynch.level.constructs.effect.EffectShock;
import com.marklynch.level.constructs.effect.EffectWet;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.utils.Consumable;
import com.marklynch.objects.utils.UpdatableGameObject;
import com.marklynch.utils.ArrayList;
import com.marklynch.utils.Texture;

public class WaterShallow extends WaterSource implements Consumable, UpdatableGameObject {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>(GameObject.class);

	public ArrayList<Texture> textures = new ArrayList<Texture>(Texture.class);
	public int texturesIndex = 0;

	public float drawX1, drawX2, drawY1, drawY2;

	public WaterShallow() {
		super();

		canBePickedUp = false;

		fitsInInventory = false;
		canShareSquare = true;

		blocksLineOfSight = false;
		persistsWhenCantBeSeen = true;
		attackable = true;
		isFloorObject = true;
		orderingOnGound = 99;
		moveable = false;
		canContainOtherObjects = false;

		flipYAxisInMirror = false;

		type = "Shallow Water";

		touchEffects = new Effect[] { new EffectWet(3) };

	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	@Override
	public boolean draw1() {
		boolean draw = super.draw1();

		if (!draw)
			return false;

		texturesIndex++;
		if (texturesIndex == textures.size())
			texturesIndex = 0;
		imageTexture = textures.get(texturesIndex);
		return true;
	}

	@Override
	public WaterShallow makeCopy(Square square, Actor owner) {
		WaterShallow waterShallow = new WaterShallow();
		waterShallow.consumeEffects = consumeEffects;
		setInstances(waterShallow);
		super.setAttributesForCopy(waterShallow, square, owner);
		waterShallow.effectsFromInteracting = effectsFromInteracting;
		if (waterShallow.squareGameObjectIsOn != null) {
			waterShallow.drawX1 = (int) (waterShallow.squareGameObjectIsOn.xInGridPixels
					+ waterShallow.drawOffsetRatioX);
			waterShallow.drawX2 = (int) (waterShallow.drawX1 + waterShallow.width);
			waterShallow.drawY1 = (int) (waterShallow.squareGameObjectIsOn.yInGridPixels
					+ waterShallow.drawOffsetRatioY);
			waterShallow.drawY2 = (int) (waterShallow.drawY1 + waterShallow.height);
		}

		waterShallow.textures = this.textures;
		waterShallow.texturesIndex = new Random().nextInt(waterShallow.textures.size());
		waterShallow.imageTexture = waterShallow.textures.get(texturesIndex);

		return waterShallow;
	}

	@Override
	public void update(int delta) {
		if (this.squareGameObjectIsOn == null)
			return;

		for (GameObject gameObject : this.squareGameObjectIsOn.inventory.gameObjects) {
			if (gameObject == this)
				continue;

			for (Effect effect : this.touchEffects) {
				gameObject.addEffect(effect.makeCopy(this, gameObject));
			}
		}
	}

	@Override
	public Effect[] getConsumeEffects() {
		return consumeEffects;
	}

	@Override
	public void addEffect(Effect effectToAdd) {
		if (effectToAdd instanceof EffectBurn) {
			this.changeHealthSafetyOff(-this.remainingHealth, effectToAdd.source, null);
		} else if (effectToAdd instanceof EffectShock) {
			addEffectSafetyOff(effectToAdd);
		}
	}

	@Override
	public void squareContentsChanged() {
		if (!this.hasActiveEffectOfType(EffectShock.class)) {
			return;
		}

		if (this.squareGameObjectIsOn == null)
			return;

		EffectShock shockEffect = (EffectShock) this.getActiveEffectOfType(EffectShock.class);

		for (GameObject gameObject : this.squareGameObjectIsOn.inventory.getGameObjects()) {
			if (!gameObject.hasActiveEffectOfType(EffectShock.class)) {
				gameObject.addEffect(new EffectShock(this, gameObject, shockEffect.turnsRemaining));
			}
		}
	}

}
