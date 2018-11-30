package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.utils.TextureUtils;

public class Vein extends Wall {

	public final static int totalOresForExhaustableVeins = 5;

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();

	public boolean infinite = false;

	public GameObject oreTemplate;

	public double dropChance;

	public ArrayList<GameObject> ores = new ArrayList<GameObject>();

	public Vein() {
		super();
		type = "Vein";
	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	@Override
	public boolean draw1() {
		boolean shouldDraw = super.draw1();
		if (!shouldDraw)
			return false;

		// DRAW INVENTORY
		for (GameObject ore : inventory.gameObjects) {
			int orePositionXInPixels = (int) (this.squareGameObjectIsOn.xInGridPixels
					+ ore.drawOffsetRatioX * Game.SQUARE_WIDTH);
			int orePositionYInPixels = (int) (this.squareGameObjectIsOn.yInGridPixels
					+ ore.drawOffsetRatioY * Game.SQUARE_HEIGHT);

			if (primaryAnimation != null && !(primaryAnimation.getCompleted())) {
				orePositionXInPixels += this.primaryAnimation.offsetX;
				orePositionYInPixels += this.primaryAnimation.offsetY;
			}
			// if (ore.primaryAnimation != null) {
			// orePositionXInPixels += ore.primaryAnimation.offsetX;
			// orePositionYInPixels += ore.primaryAnimation.offsetY;
			// }

			float alpha = 1.0f;
			if (primaryAnimation != null)
				alpha = primaryAnimation.alpha;

			TextureUtils.drawTexture(ore.imageTexture, alpha, orePositionXInPixels, orePositionYInPixels,
					orePositionXInPixels + ore.width, orePositionYInPixels + ore.height);
		}
		return true;
	}

	// , boolean infinite, Junk oreTemplate, double dropChance) {
	public Vein makeCopy(Square square, Actor owner, boolean infinite, GameObject oreTemplate, double dropChance) {
		Vein vein = new Vein();
		setInstances(vein);
		vein.infinite = infinite;
		vein.oreTemplate = oreTemplate;
		vein.dropChance = dropChance;

		// for(GameObject ore : v)

		super.setAttributesForCopy(vein, square, owner);
		vein.initWall(16f);

		if (!infinite) {
			for (int i = 0; i < totalOresForExhaustableVeins; i++) {
				GameObject ore = oreTemplate.makeCopy(null, vein.owner);
				float oreDrawOffsetXMax = 0.75f - ore.width / Game.SQUARE_WIDTH + ore.halfWidth / Game.SQUARE_WIDTH;
				float oreDrawOffsetXMin = 0.25f - ore.halfWidth / Game.SQUARE_WIDTH;
				float oreDrawOffsetYMax = 0.75f - ore.height / Game.SQUARE_HEIGHT + ore.halfHeight / Game.SQUARE_HEIGHT;
				float oreDrawOffsetYMin = 0.25f - ore.halfHeight / Game.SQUARE_HEIGHT;

				ore.drawOffsetRatioX = oreDrawOffsetXMin
						+ (float) (Math.random() * (oreDrawOffsetXMax - oreDrawOffsetXMin));
				ore.drawOffsetRatioY = oreDrawOffsetYMin
						+ (float) (Math.random() * (oreDrawOffsetYMax - oreDrawOffsetYMin));
				vein.inventory.add(ore);

			}
		}
		return vein;
	}

}
