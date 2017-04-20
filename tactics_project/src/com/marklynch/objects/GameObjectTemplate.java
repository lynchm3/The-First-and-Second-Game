package com.marklynch.objects;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import java.util.Date;

import com.marklynch.level.Square;
import com.marklynch.objects.units.Actor;

import mdesl.graphics.Color;
import mdesl.graphics.Texture;

public class GameObjectTemplate implements InventoryParent {

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
	public boolean attackable;

	public float value = 1;
	public Date pickUpdateDateTime = new Date();
	public float widthRatio = 1;
	public float heightRatio = 1;

	public float soundHandleX;
	public float soundHandleY;
	public float soundWhenHit;
	public float soundWhenHitting;
	public float soundDampening = 1;
	public Color light;
	public float lightHandleX;
	public float lightHandlY;
	public boolean stackable;
	public float fireResistance;
	public float iceResistance;
	public float electricResistance;
	public float poisonResistance;

	public float weight;

	public transient Texture imageTexture = null;

	public GameObjectTemplate(String name, float totalHealth, String imageTexturePath, Square squareGameObjectIsOn,
			Inventory inventory, boolean showInventory, boolean canShareSquare, boolean fitsInInventory,
			boolean canContainOtherObjects, boolean blocksLineOfSight, boolean persistsWhenCantBeSeen,
			boolean attackable, float widthRatio, float heightRatio, float soundHandleX, float soundHandleY,
			float soundWhenHit, float soundWhenHitting, float soundDampening, Color light, float lightHandleX,
			float lightHandlY, boolean stackable, float fireResistance, float iceResistance, float electricResistance,
			float poisonResistance, float weight) {
		super();
		this.name = name;
		this.totalHealth = totalHealth;
		this.imageTexturePath = imageTexturePath;
		this.squareGameObjectIsOn = squareGameObjectIsOn;
		this.inventory = inventory;
		this.inventory.parent = this;
		this.showInventory = showInventory;
		this.canShareSquare = canShareSquare;
		this.fitsInInventory = fitsInInventory;
		this.canContainOtherObjects = canContainOtherObjects;
		this.widthRatio = widthRatio;
		this.heightRatio = heightRatio;
		this.blocksLineOfSight = blocksLineOfSight;
		this.persistsWhenCantBeSeen = persistsWhenCantBeSeen;
		this.attackable = attackable;

		this.soundHandleX = soundHandleX;
		this.soundHandleY = soundHandleY;
		this.soundWhenHit = soundWhenHit;
		this.soundWhenHitting = soundWhenHitting;
		this.soundDampening = soundDampening;
		this.light = light;
		this.lightHandleX = lightHandleX;
		this.lightHandlY = lightHandlY;
		this.stackable = stackable;
		this.fireResistance = fireResistance;
		this.iceResistance = iceResistance;
		this.electricResistance = electricResistance;
		this.poisonResistance = poisonResistance;
		this.weight = weight;
	}

	public GameObject makeCopy(Square square, Actor owner) {
		return new GameObject(new String(name), (int) totalHealth, imageTexturePath, square, inventory.makeCopy(),
				showInventory, canShareSquare, fitsInInventory, canContainOtherObjects, blocksLineOfSight,
				persistsWhenCantBeSeen, true, widthRatio, heightRatio, soundHandleX, soundHandleY,
				soundWhenHit, soundWhenHitting, soundDampening, light, lightHandleX, lightHandlY, stackable,
				fireResistance, iceResistance, electricResistance, poisonResistance, weight, owner);
	}

	public GameObject makeCopy(Square square, Actor owner, boolean backwards) {
		GameObject copy = new GameObject(new String(name), (int) totalHealth, imageTexturePath, square,
				inventory.makeCopy(), showInventory, canShareSquare, fitsInInventory, canContainOtherObjects,
				blocksLineOfSight, persistsWhenCantBeSeen, true, widthRatio, heightRatio, soundHandleX,
				soundHandleY, soundWhenHit, soundWhenHitting, soundDampening, light, lightHandleX, lightHandlY,
				stackable, fireResistance, iceResistance, electricResistance, poisonResistance, weight, owner);
		copy.backwards = backwards;
		return copy;
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

	@Override
	public void inventoryChanged() {
	}
}
