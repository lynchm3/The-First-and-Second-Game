package com.marklynch.objects.inanimateobjects;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.templates.Templates;
import com.marklynch.utils.ArrayList;
import com.marklynch.utils.Texture;
import com.marklynch.utils.TextureUtils;

public class Well extends WaterSource {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>(GameObject.class);
	public Texture normalTexture;
	public Texture waterTexture;

	public Well() {
		super();

		canBePickedUp = false;

		fitsInInventory = false;
		canShareSquare = false;
		canContainOtherObjects = true;

		persistsWhenCantBeSeen = true;
		type = "Well";
		liquid = Templates.WATER;

		orderingOnGound = 101;

	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	@Override
	public boolean draw1() {
//		1. set image to well (w/ water)
		this.imageTexture = normalTexture;
		// 2. draw

		boolean shouldDraw = super.draw1();

		shouldDraw = shouldDraw && this.squareGameObjectIsOn.visibleToPlayer;

		if (shouldDraw) {
			// draw inventory
			for (GameObject gameObjectInWell : this.inventory.gameObjects) {

				int actorPositionXInPixels = (int) (this.squareGameObjectIsOn.xInGridPixels
						+ gameObjectInWell.drawOffsetX);

				int actorPositionYInPixels = (int) (this.squareGameObjectIsOn.yInGridPixels
						+ gameObjectInWell.drawOffsetYInWell);

				TextureUtils.drawTexture(gameObjectInWell.imageTexture, 1, actorPositionXInPixels,
						actorPositionYInPixels, actorPositionXInPixels + gameObjectInWell.width,
						actorPositionYInPixels + gameObjectInWell.height, backwards);
			}
			this.imageTexture = waterTexture;
			// draw water
			super.draw1();

			// set texture to well
			this.imageTexture = normalTexture;
		}

		return shouldDraw;

	}

	@Override
	public Well makeCopy(Square square, Actor owner) {
		Well well = new Well();
		setInstances(well);
		super.setAttributesForCopy(well, square, owner);
		well.liquid = liquid;
		well.normalTexture = normalTexture;
		well.waterTexture = waterTexture;
		return well;
	}

}
