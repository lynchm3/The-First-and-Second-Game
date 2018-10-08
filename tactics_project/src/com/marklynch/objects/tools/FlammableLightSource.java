package com.marklynch.objects.tools;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;
import com.marklynch.utils.Texture;

public class FlammableLightSource extends Tool {

	public transient Texture imageTextureUnlit = null;
	public transient Texture imageTextureLit = null;
	public boolean lit = false;

	public FlammableLightSource() {
		super();
	}

	@Override
	public FlammableLightSource makeCopy(Square square, Actor owner) {
		FlammableLightSource weapon = new FlammableLightSource();
		setInstances(weapon);
		setAttributesForCopy(weapon, square, owner);
		weapon.imageTextureUnlit = this.imageTextureUnlit;
		weapon.imageTextureLit = this.imageTextureLit;
		weapon.lit = this.lit;
		setLighting(lit);
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

}
