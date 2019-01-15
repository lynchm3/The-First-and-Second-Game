package com.marklynch.objects.inanimateobjects;

import com.marklynch.level.Level;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.tools.FlammableLightSource;
import com.marklynch.utils.ArrayList;
import com.marklynch.utils.Texture;

public class Fireplace extends FlammableLightSource {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>(GameObject.class);

	public transient Texture imageTextureUnlit = null;
	public transient Texture imageTextureLit = null;
	public boolean lit = false;

	public Fireplace() {
		super();
		type = "Fireplace";
		moveable = false;
		fitsInInventory = false;
		canContainOtherObjects = false;
		persistsWhenCantBeSeen = false;
		// attackable = false;
		canBePickedUp = false;
		floatsInWater = false;
		canShareSquare = false;
	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	@Override
	public Fireplace makeCopy(Square square, Actor owner) {
		Fireplace weapon = new Fireplace();
		setInstances(weapon);
		setAttributesForCopy(weapon, square, owner);
		weapon.imageTextureUnlit = this.imageTextureUnlit;
		weapon.imageTextureLit = this.imageTextureLit;
		weapon.setLighting(this.lit);
		return weapon;
	}

	@Override
	public void setLighting(boolean lit) {

		System.out.println("Fireplace.setLighting() - lit = " + lit);

		this.lit = lit;
		if (lit) {
			imageTexture = this.imageTextureLit;
			blocksLineOfSight = true;
			canShareSquare = false;
			this.squareGameObjectIsOn.inventory.refresh();
			Level.player.calculateVisibleSquares(Level.player.squareGameObjectIsOn);
		} else {
			imageTexture = this.imageTextureUnlit;
			blocksLineOfSight = false;
			canShareSquare = true;
			this.squareGameObjectIsOn.inventory.refresh();
			Level.player.calculateVisibleSquares(Level.player.squareGameObjectIsOn);
		}
	}

}
