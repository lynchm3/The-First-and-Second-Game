package com.marklynch.objects;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import java.util.Date;

import com.marklynch.level.Square;

import mdesl.graphics.Texture;

public class GameObjectTemplate {

	public String name = "";
	public float totalHealth = 0;
	public String imageTexturePath = null;
	public transient Square squareGameObjectIsOn = null;
	public Inventory inventory;
	public boolean showInventory;
	public boolean canShareSquare;
	public boolean fitsInInventory;
	public boolean canContainOtherObjects;
	public float value = 1;
	public Date pickUpdateDateTime = new Date();
	public float widthRatio = 1;
	public float heightRatio = 1;

	public transient Texture imageTexture = null;

	public GameObjectTemplate(String name, float totalHealth, String imageTexturePath, Square squareGameObjectIsOn,
			Inventory inventory, boolean showInventory, boolean canShareSquare, boolean fitsInInventory,
			boolean canContainOtherObjects, float widthRatio, float heightRatio) {
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
		this.widthRatio = widthRatio;
		this.heightRatio = heightRatio;
	}

	public GameObject makeCopy(Square square) {
		return new GameObject(new String(name), (int) totalHealth, imageTexturePath, square, inventory.makeCopy(),
				showInventory, canShareSquare, fitsInInventory, canContainOtherObjects, widthRatio, heightRatio);
	}

	public void postLoad1() {
		inventory.postLoad1();
		loadImages();
	}

	public void postLoad2() {
		inventory.postLoad2();
	}

	public void loadImages() {

		this.imageTexture = getGlobalImage(imageTexturePath);

	}
}
