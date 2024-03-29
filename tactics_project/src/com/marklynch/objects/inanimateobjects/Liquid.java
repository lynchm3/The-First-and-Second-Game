package com.marklynch.objects.inanimateobjects;

import java.util.Random;

import com.marklynch.level.Level;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.constructs.effect.EffectBurn;
import com.marklynch.level.constructs.effect.EffectShock;
import com.marklynch.level.constructs.effect.EffectWet;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.utils.UpdatableGameObject;
import com.marklynch.ui.ActivityLog;
import com.marklynch.utils.CopyOnWriteArrayList;
import com.marklynch.utils.Texture;

public class Liquid extends GameObject implements UpdatableGameObject {

	public static final CopyOnWriteArrayList<GameObject> instances = new CopyOnWriteArrayList<GameObject>(GameObject.class);
	public float volume = 1;
	public GameObject gasForm;
	public GameObject solidForm;
	public GameObject jarForm;

	public CopyOnWriteArrayList<Texture> textures = new CopyOnWriteArrayList<Texture>(Texture.class);
	public int texturesIndex = 0;

	public float drawX1, drawX2, drawY1, drawY2;

	public Liquid() {
		super();
		canBePickedUp = false;

		fitsInInventory = false;

		attackable = false;
		type = "Liquid";

		canShareSquare = true;

		blocksLineOfSight = false;
		persistsWhenCantBeSeen = true;
		isFloorObject = false;
		orderingOnGound = 99;
		moveable = false;
		canContainOtherObjects = false;
		flipYAxisInMirror = false;
		drawShadow = false;
		touchEffects = new Effect[] { new EffectWet(3) };

	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	public Liquid makeCopy(Square square, Actor owner) {
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

		liquid.jarForm = jarForm;
		return liquid;
	}

	@Override
	public void update() {

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
			if (this.squareGameObjectIsOn != null) {
				if (Level.shouldLog(this)) {
					Level.logOnScreen(new ActivityLog(new Object[] { this, " evaporated" }));
				}
				this.squareGameObjectIsOn.inventory.remove(this);
			}
//			this.changeHealthSafetyOff(-this.remainingHealth, effectToAdd.source, null);
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
