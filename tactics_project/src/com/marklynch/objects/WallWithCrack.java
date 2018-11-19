package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;
import com.marklynch.utils.Texture;
import com.marklynch.utils.TextureUtils;

public class WallWithCrack extends Wall {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();
	public static Texture wallCrackTexture;

	public WallWithCrack() {
		super();
		type = "Wall";
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

		int crackPositionXInPixels = (int) (this.squareGameObjectIsOn.xInGridPixels);
		int crackPositionYInPixels = (int) (this.squareGameObjectIsOn.yInGridPixels);

		if (primaryAnimation != null && !(primaryAnimation.getCompleted())) {
			crackPositionXInPixels += this.primaryAnimation.offsetX;
			crackPositionYInPixels += this.primaryAnimation.offsetY;
		}

		float alpha = 1.0f;
		if (primaryAnimation != null)
			alpha = primaryAnimation.alpha;

		System.out.println("wallCrackTexture = " + wallCrackTexture);
		System.out.println("wallCrackTexture.getWidth() = " + wallCrackTexture.getWidth());
		System.out.println("wallCrackTexture.getHeight() = " + wallCrackTexture.getHeight());
		System.out.println("crackPositionXInPixels = " + crackPositionXInPixels);
		System.out.println("crackPositionYInPixels = " + crackPositionYInPixels);
		System.out.println("alpha = " + alpha);
		TextureUtils.drawTexture(wallCrackTexture, alpha, crackPositionXInPixels, crackPositionYInPixels,
				crackPositionXInPixels + wallCrackTexture.getWidth(),
				crackPositionYInPixels + wallCrackTexture.getHeight());

		return true;
	}

	@Override
	public WallWithCrack makeCopy(Square square, Actor owner) {
		WallWithCrack wallWithCrack = new WallWithCrack();
		setInstances(wallWithCrack);
		super.setAttributesForCopy(wallWithCrack, square, owner);
		wallWithCrack.initWall(16f);
		return wallWithCrack;
	}

}
