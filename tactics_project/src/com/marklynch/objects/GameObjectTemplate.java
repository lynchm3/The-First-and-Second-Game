package com.marklynch.objects;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import java.util.ArrayList;

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

	// Template id
	public int templateId;

	public String name = "";
	public float totalHealth = 0;
	public String imageTexturePath = null;
	public transient Square squareGameObjectIsOn = null;
	public Inventory inventory = new Inventory();
	public boolean showInventory = false;;
	public boolean canShareSquare = true;
	public boolean fitsInInventory = true;
	public boolean canContainOtherObjects = false;
	public boolean blocksLineOfSight = false;
	public boolean persistsWhenCantBeSeen = false;
	public boolean attackable = true;
	public boolean canBePickedUp = true;
	public boolean decorative = false;

	public int value = 1;
	public int turnAcquired = 1;
	public float widthRatio = 1;
	public float heightRatio = 1;

	public float drawOffsetX = 0;
	public float drawOffsetY = 0;
	public float soundWhenHit = 1;
	public float soundWhenHitting = 1;
	public float soundDampening = 1;
	public Color light;
	public float lightHandleX;
	public float lightHandlY;
	public boolean stackable;
	public float slashResistance;
	public float bluntResistance;
	public float pierceResistance;
	public float fireResistance;
	public float waterResistance;
	public float electricResistance;
	public float poisonResistance;

	public float weight;

	public transient Texture imageTexture = null;
	public ArrayList<Effect> activeEffectsOnGameObject = new ArrayList<Effect>();

	public GameObjectTemplate() {
	}

	public GameObjectTemplate(String name, float totalHealth, String imageTexturePath, Square squareGameObjectIsOn,
			Inventory inventory, float widthRatio, float heightRatio, float drawOffsetX, float drawOffsetY,
			float soundWhenHit, float soundWhenHitting, float soundDampening, Color light, float lightHandleX,
			float lightHandlY, boolean stackable, float fireResistance, float waterResistance, float electricResistance,
			float poisonResistance, float slashResistance, float weight, int value, int templateId) {
		super();
		this.templateId = templateId;
		this.name = name;
		this.totalHealth = totalHealth;
		this.imageTexturePath = imageTexturePath;
		this.squareGameObjectIsOn = squareGameObjectIsOn;
		this.inventory = inventory;
		this.inventory.parent = this;
		this.widthRatio = widthRatio;
		this.heightRatio = heightRatio;

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
		this.value = value;
	}

	public GameObject makeCopy(Square square, Actor owner) {
		return new GameObject(new String(name), (int) totalHealth, imageTexturePath, square, new Inventory(),
				widthRatio, heightRatio, drawOffsetX, drawOffsetY, soundWhenHit, soundWhenHitting, soundDampening,
				light, lightHandleX, lightHandlY, stackable, fireResistance, waterResistance, electricResistance,
				poisonResistance, slashResistance, weight, value, owner, templateId);
	}

	public GameObject makeCopy(Square square, Actor owner, boolean backwards) {
		GameObject copy = new GameObject(new String(name), (int) totalHealth, imageTexturePath, square, new Inventory(),
				widthRatio, heightRatio, drawOffsetX, drawOffsetY, soundWhenHit, soundWhenHitting, soundDampening,
				light, lightHandleX, lightHandlY, stackable, fireResistance, waterResistance, electricResistance,
				poisonResistance, slashResistance, weight, value, owner, templateId);
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
