package com.marklynch.tactics.objects;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import com.marklynch.tactics.objects.level.Square;

import mdesl.graphics.Texture;

public class GameObjectTemplate {

	public String name = "";
	public float totalHealth = 0;
	public String imageTexturePath = null;
	public Square squareGameObjectIsOn = null;
	public Inventory inventory;
	public boolean showInventory;
	public boolean canShareSquare;
	public boolean fitsInInventory;
	public boolean canContainOtherObjects;

	public transient Texture imageTexture = null;

	public GameObjectTemplate(String name, float totalHealth, String imageTexturePath, Square squareGameObjectIsOn,
			Inventory inventory, boolean showInventory, boolean canShareSquare, boolean fitsInInventory,
			boolean canContainOtherObjects) {
		super();
		this.name = name;
		this.totalHealth = totalHealth;
		this.imageTexturePath = imageTexturePath;
		this.squareGameObjectIsOn = squareGameObjectIsOn;
		this.inventory = inventory;
		this.showInventory = showInventory;
		this.canShareSquare = canShareSquare;
		this.fitsInInventory = fitsInInventory;
		this.canContainOtherObjects = canContainOtherObjects;
		this.imageTexture = imageTexture;
	}

	public GameObject makeCopy(Square square) {
		return new GameObject(new String(name), (int) totalHealth, imageTexturePath, square, inventory.makeCopy(),
				showInventory, canShareSquare, fitsInInventory, canContainOtherObjects);
	}

	public void loadImages() {
		this.imageTexture = getGlobalImage(imageTexturePath);

	}

}
