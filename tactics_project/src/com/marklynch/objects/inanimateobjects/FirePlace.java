package com.marklynch.objects.inanimateobjects;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.tools.FlammableLightSource;
import com.marklynch.utils.ArrayList;
import com.marklynch.utils.Texture;

public class FirePlace extends FlammableLightSource {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>(GameObject.class);

	public transient Texture imageTextureUnlit = null;
	public transient Texture imageTextureLit = null;
	public boolean lit = false;

	public FirePlace() {
		super();
		type = "Fireplace";
		moveable = false;
		fitsInInventory = false;
		canContainOtherObjects = false;
		persistsWhenCantBeSeen = false;
		attackable = false;
		canBePickedUp = false;
		floatsInWater = false;
	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	@Override
	public FirePlace makeCopy(Square square, Actor owner) {
		FirePlace weapon = new FirePlace();
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
			blocksLineOfSight = true;
			canShareSquare = false;
		} else {
			imageTexture = this.imageTextureUnlit;
			blocksLineOfSight = false;
			canShareSquare = true;
		}
	}

}
