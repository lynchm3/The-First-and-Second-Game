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

public class Liquid extends GameObject implements Consumable, UpdatableGameObject {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>(GameObject.class);
	public float volume;
	public GameObject gasForm;
	public GameObject solidForm;

	public ArrayList<Texture> textures = new ArrayList<Texture>(Texture.class);
	public int texturesIndex = 0;

	public float drawX1, drawX2, drawY1, drawY2;

	public Liquid() {
		super();
		canBePickedUp = false;

		fitsInInventory = false;

		attackable = true;
		type = "Liquid";

		canShareSquare = true;

		blocksLineOfSight = false;
		persistsWhenCantBeSeen = true;
		isFloorObject = true;
		orderingOnGound = 99;
		moveable = false;
		canContainOtherObjects = false;
		flipYAxisInMirror = false;
		touchEffects = new Effect[] { new EffectWet(3) };

	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	public Liquid makeCopy(Square square, Actor owner, float volume) {
		Liquid liquid = new Liquid();
		setInstances(liquid);
		super.setAttributesForCopy(liquid, square, owner);
		liquid.volume = volume;
		if (liquid.squareGameObjectIsOn != null) {
			liquid.drawX1 = (int) (liquid.squareGameObjectIsOn.xInGridPixels + liquid.drawOffsetRatioX);
			liquid.drawX2 = (int) (liquid.drawX1 + liquid.width);
			liquid.drawY1 = (int) (liquid.squareGameObjectIsOn.yInGridPixels + liquid.drawOffsetRatioY);
			liquid.drawY2 = (int) (liquid.drawY1 + liquid.height);
		}
		liquid.textures = this.textures;
		liquid.texturesIndex = new Random().nextInt(liquid.textures.size());
		liquid.imageTexture = liquid.textures.get(texturesIndex);
		liquid.solidForm = solidForm;
		liquid.gasForm = gasForm;
		return liquid;
	}

	@Override
	public Effect[] getConsumeEffects() {
		return consumeEffects;
	}

	@Override
	public void update(int delta) {

		if (this.squareGameObjectIsOn == null)
			return;

		if (this.squareGameObjectIsOn.visibleToPlayer && Math.random() > 0.99d) {
			texturesIndex++;
			if (texturesIndex == textures.size())
				texturesIndex = 0;
			imageTexture = textures.get(texturesIndex);
		}

		for (GameObject gameObject : this.squareGameObjectIsOn.inventory.gameObjects) {
			if (gameObject == this)
				continue;

			for (Effect effect : this.touchEffects) {
				gameObject.addEffect(effect.makeCopy(this, gameObject));
			}
		}
	}

	@Override
	public void addEffect(Effect effectToAdd) {
		if (remainingHealth <= 0)
			return;

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
