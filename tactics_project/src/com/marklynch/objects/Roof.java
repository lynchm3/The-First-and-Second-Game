package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;
import com.marklynch.utils.TextureUtils;

public class Roof extends GameObject {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();

	public Roof() {
		super();
	}

	@Override
	public void draw1() {

		if (!Game.fullVisiblity) {
			if (this.squareGameObjectIsOn.visibleToPlayer == false && persistsWhenCantBeSeen == false)
				return;

			if (!this.squareGameObjectIsOn.seenByPlayer)
				return;
		}
	}

	@Override
	public void draw3() {
		// if (this.squareGameObjectIsOn.building !=
		// Game.level.factions.player.actors
		// .get(0).squareGameObjectIsOn.building) {
		// super.draw1();
		// } else {
		// Draw object
		if (squareGameObjectIsOn != null) {
			float actorPositionXInPixels = this.squareGameObjectIsOn.xInGridPixels;
			float actorPositionYInPixels = this.squareGameObjectIsOn.yInGridPixels;

			float alpha = 0.33f;

			// TextureUtils.skipNormals = true;
			TextureUtils.drawTexture(imageTexture, alpha, actorPositionXInPixels, actorPositionYInPixels,
					actorPositionXInPixels + Game.SQUARE_WIDTH, actorPositionYInPixels + Game.SQUARE_HEIGHT);
			// TextureUtils.skipNormals = false;
		}
		// }
	}

	@Override
	public Roof makeCopy(Square square, Actor owner) {
		Roof roof = new Roof();
		instances.add(roof);
		setAttributesForCopy(roof, square, owner);
		return roof;
	}

}
