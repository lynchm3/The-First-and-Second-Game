package com.marklynch.objects.tools;

import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.constructs.effect.EffectBurn;
import com.marklynch.level.constructs.effect.EffectWet;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.utils.ArrayList;
import com.marklynch.utils.Texture;

public class FlammableLightSource extends Tool {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>(GameObject.class);

	public transient Texture imageTextureUnlit = null;
	public transient Texture imageTextureLit = null;
	public boolean lit = false;

	public FlammableLightSource() {
		super();
		type = "Light Source";
	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	@Override
	public FlammableLightSource makeCopy(Square square, Actor owner) {
		FlammableLightSource weapon = new FlammableLightSource();
		setInstances(weapon);
		setAttributesForCopy(weapon, square, owner);
		weapon.imageTextureUnlit = this.imageTextureUnlit;
		weapon.imageTextureLit = this.imageTextureLit;
		// weapon.lit = this.lit;
		weapon.setLighting(this.lit);
		return weapon;
	}

	public void setLighting(boolean lit) {
		this.lit = lit;
		if (lit) {
			imageTexture = this.imageTextureLit;
		} else {
			imageTexture = this.imageTextureUnlit;
		}
	}

	@Override
	public void addEffect(Effect effectToAdd) {
		if (remainingHealth <= 0)
			return;
		if (effectToAdd instanceof EffectBurn) {
			((FlammableLightSource) this).setLighting(true);
		} else if (effectToAdd instanceof EffectWet) {
			((FlammableLightSource) this).setLighting(false);
		} else {
			super.addEffect(effectToAdd);
		}
	}

}
