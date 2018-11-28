package com.marklynch.objects;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.units.Actor;
import com.marklynch.utils.Texture;
import com.marklynch.utils.TextureUtils;

public class Bed extends GameObject {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();

	public String imagePathCovers;
	public Texture imageTextureCovers;

	public Bed() {
		super();
		loadCoverImage();

		canBePickedUp = false;

		fitsInInventory = false;
		type = "Bed";

	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	public void loadCoverImage() {
		this.imageTextureCovers = getGlobalImage(imagePathCovers, false);
	}

	@Override
	public Bed makeCopy(Square square, Actor owner) {
		Bed bed = new Bed();
		setInstances(bed);
		super.setAttributesForCopy(bed, square, owner);
		bed.imageTextureCovers = imageTextureCovers;
		return bed;
	}

	@Override
	public boolean checkIfDestroyed(Object attacker, Action action) {
		boolean destroyed = super.checkIfDestroyed(attacker, action);
		if (destroyed && owner != null && owner.bed == this) {
			owner.bed = null;
		}

		return destroyed;
	}

	@Override
	public void draw2() {

		if (!Game.fullVisiblity) {

			if (this.squareGameObjectIsOn.visibleToPlayer == false)
				return;
		}

		// Draw bed covers
		if (squareGameObjectIsOn != null) {
			float actorPositionXInPixels = this.squareGameObjectIsOn.xInGridPixels;
			float actorPositionYInPixels = this.squareGameObjectIsOn.yInGridPixels;

			// TextureUtils.skipNormals = true;
			TextureUtils.drawTexture(imageTextureCovers, 1f, actorPositionXInPixels, actorPositionYInPixels,
					actorPositionXInPixels + Game.SQUARE_WIDTH, actorPositionYInPixels + Game.SQUARE_HEIGHT);
			// TextureUtils.skipNormals = false;
		}

		super.draw2();

	}
}
