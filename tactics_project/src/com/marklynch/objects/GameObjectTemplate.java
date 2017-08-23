package com.marklynch.objects;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import java.util.ArrayList;
import java.util.Date;

import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.constructs.effect.EffectBurning;
import com.marklynch.level.constructs.effect.EffectPoison;
import com.marklynch.level.constructs.effect.EffectWet;
import com.marklynch.level.constructs.inventory.Inventory;
import com.marklynch.level.constructs.inventory.InventoryParent;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

import mdesl.graphics.Color;
import mdesl.graphics.Texture;

public class GameObjectTemplate implements InventoryParent {

	public String name = "";
	public float totalHealth = 0;
	public String imageTexturePath = null;
	public transient Square squareGameObjectIsOn = null;
	public Inventory inventory;
	public boolean showInventory = false;;
	public boolean canShareSquare = true;
	public boolean fitsInInventory = true;
	public boolean canContainOtherObjects = false;
	public boolean blocksLineOfSight = false;
	public boolean persistsWhenCantBeSeen = false;
	public boolean attackable = true;
	public boolean canBePickedUp = true;
	public boolean decorative = false;

	public float value = 1;
	public Date pickUpdateDateTime = new Date();
	public float widthRatio = 1;
	public float heightRatio = 1;

	public float drawOffsetX;
	public float drawOffsetY;
	public float soundWhenHit;
	public float soundWhenHitting;
	public float soundDampening = 1;
	public Color light;
	public float lightHandleX;
	public float lightHandlY;
	public boolean stackable;
	protected float fireResistance;
	protected float waterResistance;
	protected float electricResistance;
	protected float poisonResistance;
	protected float slashResistance;

	public float weight;

	public transient Texture imageTexture = null;
	public ArrayList<Effect> activeEffectsOnGameObject = new ArrayList<Effect>();

	public GameObjectTemplate(String name, float totalHealth, String imageTexturePath, Square squareGameObjectIsOn,
			Inventory inventory, float widthRatio, float heightRatio, float drawOffsetX, float drawOffsetY,
			float soundWhenHit, float soundWhenHitting, float soundDampening, Color light, float lightHandleX,
			float lightHandlY, boolean stackable, float fireResistance, float waterResistance, float electricResistance,
			float poisonResistance, float slashResistance, float weight) {
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

		this.drawOffsetX = drawOffsetX;
		this.drawOffsetY = drawOffsetY;

		this.soundWhenHit = soundWhenHit;
		this.soundWhenHitting = soundWhenHitting;
		this.soundDampening = soundDampening;
		this.light = light;
		this.lightHandleX = lightHandleX;
		this.lightHandlY = lightHandlY;
		this.stackable = stackable;
		this.fireResistance = fireResistance;
		this.waterResistance = waterResistance;
		this.electricResistance = electricResistance;
		this.poisonResistance = poisonResistance;
		this.weight = weight;
	}

	public GameObject makeCopy(Square square, Actor owner) {
		return new GameObject(new String(name), (int) totalHealth, imageTexturePath, square, new Inventory(),
				widthRatio, heightRatio, drawOffsetX, drawOffsetY, soundWhenHit, soundWhenHitting, soundDampening,
				light, lightHandleX, lightHandlY, stackable, fireResistance, waterResistance, electricResistance,
				poisonResistance, slashResistance, weight, owner);
	}

	public GameObject makeCopy(Square square, Actor owner, boolean backwards) {
		GameObject copy = new GameObject(new String(name), (int) totalHealth, imageTexturePath, square, new Inventory(),
				widthRatio, heightRatio, drawOffsetX, drawOffsetY, soundWhenHit, soundWhenHitting, soundDampening,
				light, lightHandleX, lightHandlY, stackable, fireResistance, waterResistance, electricResistance,
				poisonResistance, slashResistance, weight, owner);
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

	public void removeEffect(Effect effect) {
		this.activeEffectsOnGameObject.remove(effect);
	}

	public float getEffectiveSlashResistance() {
		return slashResistance;
	}

	public float getEffectiveFireResistance() {
		float res = fireResistance;
		if (isWet()) {
			res += 50;
		}

		if (res > 100)
			res = 100;

		return res;
	}

	public float getEffectiveWaterResistance() {
		return waterResistance;
	}

	public float getEffectivePosionResistance() {
		return poisonResistance;
	}

	public float getEffectiveelectricResistance() {
		return electricResistance;
	}

	public boolean isWet() {
		for (Effect effect : activeEffectsOnGameObject) {
			if (effect instanceof EffectWet) {
				return true;
			}
		}
		return false;
	}

	public void removeWetEffect() {
		Effect effectWet = null;
		for (Effect effect : activeEffectsOnGameObject) {
			if (effect instanceof EffectWet) {
				effectWet = effect;
			}
		}
		if (effectWet != null)
			removeEffect(effectWet);
	}

	public void removeBurningEffect() {
		Effect effectBurning = null;
		for (Effect effect : activeEffectsOnGameObject) {
			if (effect instanceof EffectBurning) {
				effectBurning = effect;
			}
		}
		if (effectBurning != null)
			removeEffect(effectBurning);
	}

	public void removePosionEffect() {
		Effect effectBurning = null;
		for (Effect effect : activeEffectsOnGameObject) {
			if (effect instanceof EffectPoison) {
				effectBurning = effect;
			}
		}
		if (effectBurning != null)
			removeEffect(effectBurning);
	}
}
