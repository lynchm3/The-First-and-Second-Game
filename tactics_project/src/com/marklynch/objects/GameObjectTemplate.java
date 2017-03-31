package com.marklynch.objects;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import java.util.Date;

import com.marklynch.level.Square;
import com.marklynch.objects.units.Actor;

import mdesl.graphics.Color;
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
	public boolean blocksLineOfSight;
	public boolean persistsWhenCantBeSeen;
	public float value = 1;
	public Date pickUpdateDateTime = new Date();
	public float widthRatio = 1;
	public float heightRatio = 1;

	public float soundHandleX;
	public float soundHandleY;
	public float soundWhenHit;
	public float soundWhenHitting;
	public Color light;
	public float lightHandleX;
	public float lightHandlY;
	public boolean stackable;
	public float fireResistance;
	public float iceResistance;
	public float electricResistance;
	public float poisonResistance;

	public transient Texture imageTexture = null;

	public GameObjectTemplate(String name, float totalHealth, String imageTexturePath, Square squareGameObjectIsOn,
			Inventory inventory, boolean showInventory, boolean canShareSquare, boolean fitsInInventory,
			boolean canContainOtherObjects, boolean blocksLineOfSight, boolean persistsWhenCantBeSeen, float widthRatio,
			float heightRatio, float soundHandleX, float soundHandleY, float soundWhenHit, float soundWhenHitting,
			Color light, float lightHandleX, float lightHandlY, boolean stackable, float fireResistance,
			float iceResistance, float electricResistance, float poisonResistance) {
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
		this.blocksLineOfSight = blocksLineOfSight;
		this.persistsWhenCantBeSeen = persistsWhenCantBeSeen;

		this.soundHandleX = soundHandleX;
		this.soundHandleY = soundHandleY;
		this.soundWhenHit = soundWhenHit;
		this.soundWhenHitting = soundWhenHitting;
		this.light = light;
		this.lightHandleX = lightHandleX;
		this.lightHandlY = lightHandlY;
		this.stackable = stackable;
		this.fireResistance = fireResistance;
		this.iceResistance = iceResistance;
		this.electricResistance = electricResistance;
		this.poisonResistance = poisonResistance;
	}

	public GameObject makeCopy(Square square, Actor owner) {
		return new GameObject(new String(name), (int) totalHealth, imageTexturePath, square, inventory.makeCopy(),
				showInventory, canShareSquare, fitsInInventory, canContainOtherObjects, blocksLineOfSight,
				persistsWhenCantBeSeen, widthRatio, heightRatio, soundHandleX, soundHandleY, soundWhenHit,
				soundWhenHitting, light, lightHandleX, lightHandlY, stackable, fireResistance, iceResistance,
				electricResistance, poisonResistance, owner);
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
