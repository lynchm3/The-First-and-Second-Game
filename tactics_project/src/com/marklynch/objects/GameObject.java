package com.marklynch.objects;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import java.util.ArrayList;
import java.util.UUID;

import org.lwjgl.util.Point;
import org.newdawn.slick.openal.Audio;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.Group;
import com.marklynch.level.constructs.animation.Animation;
import com.marklynch.level.constructs.animation.primary.AnimationWait;
import com.marklynch.level.constructs.animation.secondary.AnimationDamageText;
import com.marklynch.level.constructs.beastiary.BestiaryKnowledge;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.constructs.effect.EffectBleeding;
import com.marklynch.level.constructs.effect.EffectBurning;
import com.marklynch.level.constructs.effect.EffectPoison;
import com.marklynch.level.constructs.effect.EffectWet;
import com.marklynch.level.constructs.enchantment.Enhancement;
import com.marklynch.level.constructs.inventory.Inventory;
import com.marklynch.level.constructs.inventory.InventoryParent;
import com.marklynch.level.constructs.inventory.InventorySquare;
import com.marklynch.level.constructs.journal.Journal;
import com.marklynch.level.conversation.Conversation;
import com.marklynch.level.quest.Quest;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actions.ActionAttack;
import com.marklynch.objects.actions.ActionBuytemsSelectedInInventory;
import com.marklynch.objects.actions.ActionChangeAppearance;
import com.marklynch.objects.actions.ActionChoppingStart;
import com.marklynch.objects.actions.ActionClose;
import com.marklynch.objects.actions.ActionDie;
import com.marklynch.objects.actions.ActionDig;
import com.marklynch.objects.actions.ActionDropItems;
import com.marklynch.objects.actions.ActionDropItemsSelectedInInventory;
import com.marklynch.objects.actions.ActionEatItems;
import com.marklynch.objects.actions.ActionEatItemsSelectedInInventory;
import com.marklynch.objects.actions.ActionEquip;
import com.marklynch.objects.actions.ActionFillContainersInInventory;
import com.marklynch.objects.actions.ActionFillSpecificContainer;
import com.marklynch.objects.actions.ActionFishingStart;
import com.marklynch.objects.actions.ActionFollow;
import com.marklynch.objects.actions.ActionGiveItemsSelectedInInventory;
import com.marklynch.objects.actions.ActionHide;
import com.marklynch.objects.actions.ActionInitiateTrade;
import com.marklynch.objects.actions.ActionInspect;
import com.marklynch.objects.actions.ActionLift;
import com.marklynch.objects.actions.ActionLock;
import com.marklynch.objects.actions.ActionMiningStart;
import com.marklynch.objects.actions.ActionMove;
import com.marklynch.objects.actions.ActionOpen;
import com.marklynch.objects.actions.ActionOpenInventoryToDropItems;
import com.marklynch.objects.actions.ActionOpenInventoryToGiveItems;
import com.marklynch.objects.actions.ActionOpenInventoryToThrowItems;
import com.marklynch.objects.actions.ActionOpenOtherInventory;
import com.marklynch.objects.actions.ActionPeek;
import com.marklynch.objects.actions.ActionPin;
import com.marklynch.objects.actions.ActionPourContainerInInventory;
import com.marklynch.objects.actions.ActionPourSpecificItem;
import com.marklynch.objects.actions.ActionRead;
import com.marklynch.objects.actions.ActionRemoveMapMarker;
import com.marklynch.objects.actions.ActionRename;
import com.marklynch.objects.actions.ActionSearch;
import com.marklynch.objects.actions.ActionSellItemsSelectedInInventory;
import com.marklynch.objects.actions.ActionSkin;
import com.marklynch.objects.actions.ActionSmash;
import com.marklynch.objects.actions.ActionStarSpecificItem;
import com.marklynch.objects.actions.ActionStopHiding;
import com.marklynch.objects.actions.ActionStopPeeking;
import com.marklynch.objects.actions.ActionTakeItems;
import com.marklynch.objects.actions.ActionTakeItemsSelectedInInventory;
import com.marklynch.objects.actions.ActionTeleportOther;
import com.marklynch.objects.actions.ActionThrowItem;
import com.marklynch.objects.actions.ActionTrackMapMarker;
import com.marklynch.objects.actions.ActionUnequip;
import com.marklynch.objects.actions.ActionUnlock;
import com.marklynch.objects.actions.ActionUntrackMapMarker;
import com.marklynch.objects.actions.ActionUse;
import com.marklynch.objects.actions.ActionableInInventory;
import com.marklynch.objects.actions.ActionableInWorld;
import com.marklynch.objects.templates.Templates;
import com.marklynch.objects.tools.ContainerForLiquids;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.Fish;
import com.marklynch.objects.units.NonHuman;
import com.marklynch.objects.weapons.Weapon;
import com.marklynch.ui.ActivityLog;
import com.marklynch.utils.ArrayUtils;
import com.marklynch.utils.Color;
import com.marklynch.utils.LineUtils;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.ResourceUtils;
import com.marklynch.utils.Texture;
import com.marklynch.utils.TextureUtils;

public class GameObject implements ActionableInWorld, ActionableInInventory, Comparable, InventoryParent, DamageDealer {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();

	public final static String[] editableAttributes = { "name", "imageTexture", "totalHealth", "remainingHealth",
			"owner", "inventory", "showInventory", "canShareSquare", "fitsInInventory", "canContainOtherObjects" };
	public String guid = UUID.randomUUID().toString();

	// Template id
	public int templateId;

	public String name = "";
	public float totalHealth = 0;
	public String imageTexturePath = null;
	public transient Square squareGameObjectIsOn = null;
	public transient InventorySquare inventorySquare = null;
	public Inventory inventory = new Inventory();
	public boolean showInventoryInGroundDisplay = false;;
	public boolean canShareSquare = true;
	public boolean fitsInInventory = true;
	public boolean canContainOtherObjects = false;
	public boolean blocksLineOfSight = false;
	public boolean persistsWhenCantBeSeen = false;
	public boolean attackable = true;
	public boolean canBePickedUp = true;
	public boolean decorative = false;
	public boolean floats = false;

	public int value = 1;
	public int turnAcquired = 1;
	public float widthRatio = 1;
	public float heightRatio = 1;

	public float drawOffsetRatioX = 0;
	public float drawOffsetRatioY = 0;
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
	public float bleedingResistance;

	public float weight;

	public transient Texture imageTexture = null;
	public ArrayList<Effect> activeEffectsOnGameObject = new ArrayList<Effect>();
	public ArrayList<Action> actionsPerformedThisTurn = new ArrayList<Action>();

	// attributes
	public float remainingHealth = 0;
	boolean favourite = false;
	public transient boolean hasAttackedThisTurn = false;

	// images
	public static transient Texture powTexture = null;
	public static transient Texture vsTexture = null;
	public static transient Texture fightTexture = null;
	public static transient Texture skullTexture = null;
	public static transient Texture xTexture = null;
	public static transient Texture upTexture = null;
	public static transient Texture downTexture = null;
	public static transient Texture leftTexture = null;
	public static transient Texture rightTexture = null;
	// public static transient Texture armTexture = null;
	public static transient Texture grassNormalTexture = null;
	public static transient Texture skipNormalTexture = null;

	// sounds
	public static transient Audio screamAudio = null;

	// POW
	public transient boolean showPow = false;

	// Placement in inventory
	public transient Inventory inventoryThatHoldsThisObject;

	// Quest
	public transient Quest quest;

	// Owner
	public transient Actor owner;

	public float height;
	public float width;
	public float halfHeight;
	public float halfWidth;
	// public float drawOffsetX;
	// public float drawOffsetY;

	public float anchorX, anchorY;

	public boolean backwards = false;

	public boolean hiding = false;
	public HidingPlace hidingPlace = null;

	public transient ArrayList<GameObject> attackers = new ArrayList<GameObject>();

	public transient Group group;

	public Object destroyedBy = null;
	public Action destroyedByAction = null;

	public Animation primaryAnimation = new AnimationWait(null);
	public ArrayList<Animation> secondaryAnimations = new ArrayList<Animation>();

	public boolean toSell = false;
	public boolean starred = false;
	public boolean flash = false;

	// weapons
	public float slashDamage = 0;
	public float pierceDamage = 0;
	public float bluntDamage = 0;
	public float fireDamage = 0; // fire/purify/clean
	public float waterDamage = 0; // water/life
	public float electricalDamage = 0; // lightning/light/electrical/speed
	public float poisonDamage = 0;// poison/ground/contaminate/neutralize/slow/corruption
	public float bleedingDamage = 0;
	public float healing = 0;
	public float minRange = 1;
	public float maxRange = 1;

	public Enhancement enhancement;
	public int level = 1;

	public int thoughtsOnPlayer = 0;

	public static float healthWidthInPixels = Game.SQUARE_WIDTH / 20;

	public boolean diggable = false;

	public boolean flipYAxisInMirror = true;

	public Actor beingFishedBy = null;
	public boolean fightingFishingRod = false;
	public boolean beingChopped = false;
	public boolean beingMined = false;
	public boolean beingDigged = false;
	// public static float healthHeightInPixels = Game.SQUARE_HEIGHT;

	// public ArrayList<DestructionListener> destructionListeners = new
	// ArrayList<DestructionListener>();

	// Fishing stuff
	public float swimmingChangeX = 0;
	public float swimmingChangeY = 0;

	public ArrayList<Arrow> arrows = new ArrayList<Arrow>();

	public GameObject() {
	}

	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
	}

	public void init() {

		if (squareGameObjectIsOn != null) {
			this.squareGameObjectIsOn.inventory.add(this);
		}

		loadImages();

		width = Game.SQUARE_WIDTH * widthRatio;
		height = Game.SQUARE_HEIGHT * heightRatio;
		halfWidth = width / 2;
		halfHeight = height / 2;
		randomisePosition();

		inventory = new Inventory();
		inventory.parent = this;
	}

	public void randomisePosition() {
		if (widthRatio < 1f && heightRatio < 1f) {
			float drawOffsetXMax = 1 - width / Game.SQUARE_WIDTH;
			float drawOffsetYMax = 1 - height / Game.SQUARE_WIDTH;
			if (drawOffsetYMax < 0) {
				drawOffsetYMax = 0;
			}
			this.drawOffsetRatioX = (float) (Math.random() * drawOffsetXMax);
			this.drawOffsetRatioY = (/* Math.random() * */ drawOffsetYMax);
		}
	}

	public void postLoad1() {
		inventory.postLoad1();
		// loadImages();
		// paths = new HashMap<Square, Path>();
		if (squareGameObjectIsOn != null) {
			this.squareGameObjectIsOn.inventory.add(this);
		}

		loadImages();
	}

	public void postLoad2() {
		inventory.postLoad2();
	}

	public void loadImages() {
		// this.imageTexture = getGlobalImage(imageTexturePath, true);

	}

	public static void loadStaticImages() {

		powTexture = getGlobalImage("pow.png", false);
		vsTexture = getGlobalImage("vs.png", false);
		fightTexture = getGlobalImage("fight.png", false);
		skullTexture = getGlobalImage("skull.png", false);
		xTexture = getGlobalImage("x.png", false);
		upTexture = getGlobalImage("up.png", false);
		downTexture = getGlobalImage("down.png", false);
		leftTexture = getGlobalImage("left.png", false);
		rightTexture = getGlobalImage("right.png", false);
		// armTexture = getGlobalImage("arm.png", false);
		grassNormalTexture = getGlobalImage("grass_NRM.png", false);
		skipNormalTexture = getGlobalImage("skip_with_shadow_NRM.png", false);
		screamAudio = ResourceUtils.getGlobalSound("scream.wav");
	}

	protected Color flashColor = new Color(255f, 255f, 255f, 0.5f);
	protected Color underWaterColor = new Color(0.1f, 0.1f, 0.1f, 1f);

	public void draw1() {

		if (this.remainingHealth <= 0)
			return;
		if (squareGameObjectIsOn == null)
			return;
		if (hiding && this != Game.level.player)
			return;

		if (!Game.fullVisiblity && this != Game.level.player) {

			if (this.squareGameObjectIsOn.visibleToPlayer == false && persistsWhenCantBeSeen == false)
				return;

			if (!this.squareGameObjectIsOn.seenByPlayer)
				return;
		}

		if (primaryAnimation != null && primaryAnimation.completed == false)
			primaryAnimation.draw1();

		for (Animation secondaryAnimation : secondaryAnimations)
			secondaryAnimation.draw1();

		// Draw object
		if (squareGameObjectIsOn != null) {

			int actorPositionXInPixels = (int) (this.squareGameObjectIsOn.xInGridPixels
					+ Game.SQUARE_WIDTH * drawOffsetRatioX);
			int actorPositionYInPixels = (int) (this.squareGameObjectIsOn.yInGridPixels
					+ Game.SQUARE_HEIGHT * drawOffsetRatioY);
			if (primaryAnimation != null) {
				actorPositionXInPixels += primaryAnimation.offsetX;
				actorPositionYInPixels += primaryAnimation.offsetY;
			}

			float alpha = 1.0f;

			// TextureUtils.skipNormals = true;

			if (!this.squareGameObjectIsOn.visibleToPlayer && this != Game.level.player)
				alpha = 0.5f;
			if (hiding)
				alpha = 0.5f;

			for (Arrow arrow : arrows) {

				float arrowWidth = arrow.width;
				// arrowWidth = -arrowWidth;

				// QuadUtils.drawQuad(Color.RED,
				// this.squareGameObjectIsOn.xInGridPixels + Game.SQUARE_WIDTH *
				// arrow.drawOffsetRatioX,
				// this.squareGameObjectIsOn.yInGridPixels + Game.SQUARE_HEIGHT
				// * arrow.drawOffsetRatioY,
				// this.squareGameObjectIsOn.xInGridPixels + Game.SQUARE_WIDTH *
				// arrow.drawOffsetRatioX + 10,
				// this.squareGameObjectIsOn.yInGridPixels + Game.SQUARE_HEIGHT
				// * arrow.drawOffsetRatioY + 10);

				if (arrow.backwards) {
					TextureUtils.drawTexture(arrow.textureEmbeddedPoint, alpha,
							this.squareGameObjectIsOn.xInGridPixels + Game.SQUARE_WIDTH * arrow.drawOffsetRatioX
									+ arrowWidth,
							this.squareGameObjectIsOn.yInGridPixels + Game.SQUARE_HEIGHT * arrow.drawOffsetRatioY,
							this.squareGameObjectIsOn.xInGridPixels + Game.SQUARE_WIDTH * arrow.drawOffsetRatioX,
							this.squareGameObjectIsOn.yInGridPixels + Game.SQUARE_HEIGHT * arrow.drawOffsetRatioY
									+ arrow.height);
				} else {
					TextureUtils.drawTexture(arrow.textureEmbeddedPoint, alpha,
							this.squareGameObjectIsOn.xInGridPixels + Game.SQUARE_WIDTH * arrow.drawOffsetRatioX
									- arrowWidth,
							this.squareGameObjectIsOn.yInGridPixels + Game.SQUARE_HEIGHT * arrow.drawOffsetRatioY,
							this.squareGameObjectIsOn.xInGridPixels + Game.SQUARE_WIDTH * arrow.drawOffsetRatioX,
							this.squareGameObjectIsOn.yInGridPixels + Game.SQUARE_HEIGHT * arrow.drawOffsetRatioY
									+ arrow.height);

				}
			}

			// System.out.println("this = " + this + "..." + this.getClass());

			// GL11.glTexParameteri(target, pname, param);
			TextureUtils.drawTexture(imageTexture, alpha, actorPositionXInPixels, actorPositionYInPixels,
					actorPositionXInPixels + width, actorPositionYInPixels + height, backwards);

			if (flash || this == Game.gameObjectMouseIsOver) {
				TextureUtils.drawTexture(imageTexture, 0.5f, actorPositionXInPixels, actorPositionYInPixels,
						actorPositionXInPixels + width, actorPositionYInPixels + height, 0, 0, 0, 0, backwards, false,
						flashColor, false);
			} else if (squareGameObjectIsOn.inventory.waterBody != null && !(this instanceof Fish)
					&& !(this instanceof WaterBody)) {

				TextureUtils.drawTexture(imageTexture, 0.5f, actorPositionXInPixels, actorPositionYInPixels,
						actorPositionXInPixels + width, actorPositionYInPixels + height, 0, 0, 0, 0, backwards, false,
						underWaterColor, false);
				TextureUtils.drawTexture(Templates.WATER_BODY.imageTexture, alpha, actorPositionXInPixels,
						actorPositionYInPixels, actorPositionXInPixels + width, actorPositionYInPixels + height,
						backwards);

				// squareGameObjectIsOn.inventory.getGameObjectOfClass(WaterBody.class).draw1();
			}

			for (Arrow arrow : arrows) {

				float arrowWidth = arrow.width;
				// arrowWidth = -arrowWidth;

				// QuadUtils.drawQuad(Color.RED,
				// this.squareGameObjectIsOn.xInGridPixels + Game.SQUARE_WIDTH *
				// arrow.drawOffsetRatioX,
				// this.squareGameObjectIsOn.yInGridPixels + Game.SQUARE_HEIGHT
				// * arrow.drawOffsetRatioY,
				// this.squareGameObjectIsOn.xInGridPixels + Game.SQUARE_WIDTH *
				// arrow.drawOffsetRatioX + 10,
				// this.squareGameObjectIsOn.yInGridPixels + Game.SQUARE_HEIGHT
				// * arrow.drawOffsetRatioY + 10);

				if (arrow.backwards) {
					TextureUtils.drawTexture(arrow.textureEmbedded, alpha,
							this.squareGameObjectIsOn.xInGridPixels + Game.SQUARE_WIDTH * arrow.drawOffsetRatioX
									+ arrowWidth,
							this.squareGameObjectIsOn.yInGridPixels + Game.SQUARE_HEIGHT * arrow.drawOffsetRatioY,
							this.squareGameObjectIsOn.xInGridPixels + Game.SQUARE_WIDTH * arrow.drawOffsetRatioX,
							this.squareGameObjectIsOn.yInGridPixels + Game.SQUARE_HEIGHT * arrow.drawOffsetRatioY
									+ arrow.height);
				} else {
					TextureUtils.drawTexture(arrow.textureEmbedded, alpha,
							this.squareGameObjectIsOn.xInGridPixels + Game.SQUARE_WIDTH * arrow.drawOffsetRatioX
									- arrowWidth,
							this.squareGameObjectIsOn.yInGridPixels + Game.SQUARE_HEIGHT * arrow.drawOffsetRatioY,
							this.squareGameObjectIsOn.xInGridPixels + Game.SQUARE_WIDTH * arrow.drawOffsetRatioX,
							this.squareGameObjectIsOn.yInGridPixels + Game.SQUARE_HEIGHT * arrow.drawOffsetRatioY
									+ arrow.height);

				}
			}

			if (remainingHealth != totalHealth) {
				// draw sidebar on square
				float healthPercentage = (remainingHealth) / (totalHealth);
				float healthBarHeightInPixels = height * healthPercentage;
				float healthXInPixels = this.squareGameObjectIsOn.xInGridPixels;
				float healthYInPixels = this.squareGameObjectIsOn.yInGridPixels;
				if (primaryAnimation != null) {
					healthXInPixels += primaryAnimation.offsetX;
					healthYInPixels += primaryAnimation.offsetY;
				}

				Color color = Color.YELLOW;
				if (thoughtsOnPlayer > 50) {
					color = Color.GREEN;
				} else if (thoughtsOnPlayer < -50) {
					color = Color.RED;
				}

				// White bit under health bar
				QuadUtils.drawQuad(new Color(1.0f, 1.0f, 1.0f, 0.5f), actorPositionXInPixels + 1,
						actorPositionYInPixels + 1, actorPositionXInPixels + healthWidthInPixels - 1,
						actorPositionYInPixels + height - 1);

				// Colored health bar
				QuadUtils.drawQuad(color, actorPositionXInPixels + 1, actorPositionYInPixels + 1,
						actorPositionXInPixels + healthWidthInPixels - 1,
						actorPositionYInPixels + healthBarHeightInPixels - 1);
			}

			Game.flush();
		}
	}

	private void drawHighlight() {

	}

	public void draw2() {
		for (Effect effect : activeEffectsOnGameObject) {
			effect.draw2();
		}

		if (primaryAnimation != null && primaryAnimation.completed == false)
			primaryAnimation.draw2();
		for (Animation secondaryAnimation : secondaryAnimations) {
			secondaryAnimation.draw2();
		}
	}

	public void draw3() {

		if (squareGameObjectIsOn.inventory.waterBody == null)
			return;
		if (!floats)
			return;
		if (this.remainingHealth <= 0)
			return;
		if (squareGameObjectIsOn == null)
			return;
		if (hiding)
			return;

		if (!Game.fullVisiblity) {

			if (this.squareGameObjectIsOn.visibleToPlayer == false && persistsWhenCantBeSeen == false)
				return;

			if (!this.squareGameObjectIsOn.seenByPlayer)
				return;
		}

		// if (primaryAnimation != null && primaryAnimation.completed == false)
		// primaryAnimation.draw1();

		for (Animation secondaryAnimation : secondaryAnimations)
			secondaryAnimation.draw1();

		// Draw object
		if (squareGameObjectIsOn != null) {
			int actorPositionXInPixels = 0;
			int actorPositionYInPixels = 0;
			if (primaryAnimation != null) {
				actorPositionXInPixels = (int) (this.squareGameObjectIsOn.xInGridPixels
						+ Game.SQUARE_WIDTH * drawOffsetRatioX + primaryAnimation.offsetX);
				actorPositionYInPixels = (int) (this.squareGameObjectIsOn.yInGridPixels
						+ Game.SQUARE_HEIGHT * drawOffsetRatioY + primaryAnimation.offsetY);
			} else {
				actorPositionXInPixels = (int) (this.squareGameObjectIsOn.xInGridPixels
						+ Game.SQUARE_WIDTH * drawOffsetRatioX);
				actorPositionYInPixels = (int) (this.squareGameObjectIsOn.yInGridPixels
						+ Game.SQUARE_HEIGHT * drawOffsetRatioY);

			}

			float alpha = 1.0f;
			if (hiding)
				alpha = 0.5f;

			float boundsX1 = actorPositionXInPixels;
			float boundsY1 = actorPositionYInPixels;
			float boundsX2 = (boundsX1 + width);
			float boundsY2 = (boundsY1 + halfHeight);

			// GL11.glTexParameteri(target, pname, param);
			TextureUtils.drawTextureWithinBounds(imageTexture, alpha, actorPositionXInPixels, actorPositionYInPixels,
					actorPositionXInPixels + width, actorPositionYInPixels + height, boundsX1, boundsY1, boundsX2,
					boundsY2, backwards, false);

			Color color1 = new Color(1, 144, 255);
			Color color2 = new Color(9, 114, 255);
			Color color3 = new Color(18, 111, 236);

			LineUtils.drawLine(color1, boundsX1, boundsY2, boundsX2, boundsY2, 1);
			LineUtils.drawLine(color2, boundsX1, boundsY2 + 2, boundsX2, boundsY2 + 1, 1);
			LineUtils.drawLine(color3, boundsX1, boundsY2 + 1, boundsX2, boundsY2 + 2, 1);

			// TextureUtils.drawTextureWithinBounds(gameObject.imageTexture,
			// alpha, actorPositionXInPixels,
			// actorPositionYInPixels, actorPositionXInPixels +
			// gameObject.width,
			// actorPositionYInPixels + gameObject.height, boundsX1, boundsY1,
			// boundsX2, boundsY2, false,
			// gameObject.flipYAxisInMirror);

			if (flash || this == Game.gameObjectMouseIsOver) {
				TextureUtils.drawTexture(imageTexture, 0.5f, actorPositionXInPixels, actorPositionYInPixels,
						actorPositionXInPixels + width, actorPositionYInPixels + height, 0, 0, 0, 0, backwards, false,
						flashColor, false);
			}
		}

	}

	public void drawUI() {

		// Draw POW
		if (showPow == true) {
			float powPositionXInPixels = Math.abs(squareGameObjectIsOn.xInGridPixels);
			float powPositionYInPixels = squareGameObjectIsOn.yInGridPixels;
			if (primaryAnimation != null) {
				powPositionXInPixels += primaryAnimation.offsetX;
				powPositionYInPixels += primaryAnimation.offsetY;
			}
			TextureUtils.drawTexture(this.powTexture, powPositionXInPixels, powPositionYInPixels,
					powPositionXInPixels + Game.SQUARE_WIDTH, powPositionYInPixels + Game.SQUARE_HEIGHT);
		}
	}

	public void drawStaticUI() {

		if (primaryAnimation != null && primaryAnimation.completed == false)
			primaryAnimation.drawStaticUI();

		for (Animation secondaryAnimation : secondaryAnimations)
			secondaryAnimation.drawStaticUI();
	}

	public boolean checkIfDestroyed(Object attacker, Action action) {
		if (remainingHealth <= 0) {
			destroyedBy = attacker;
			destroyedByAction = action;
			this.canShareSquare = true;
			this.blocksLineOfSight = false;

			soundDampening = 1;
			this.activeEffectsOnGameObject.clear();

			// Unequip destroyed item
			if (inventoryThatHoldsThisObject != null && inventoryThatHoldsThisObject.parent instanceof Actor) {

				// GameObject holder = (GameObject)
				// inventorySquareGameObjectIsOn.inventoryThisBelongsTo.parent;

				Actor actor = (Actor) inventoryThatHoldsThisObject.parent;
				if (actor.equipped == this) {
					if (actor.inventory.contains(actor.equippedBeforePickingUpObject)) {
						actor.equip(actor.equippedBeforePickingUpObject);
					} else if (actor.inventory.containsDuplicateOf(this)) {
						actor.equip(actor.inventory.getDuplicateOf(this));
					} else {
						actor.equip(null);
					}
					actor.equippedBeforePickingUpObject = null;
				}
				if (actor.helmet == this)
					actor.helmet = null;
				if (actor.bodyArmor == this)
					actor.bodyArmor = null;
				if (actor.legArmor == this)
					actor.legArmor = null;

			}

			// for (DestructionListener destructionListener :
			// destructionListeners) {
			// destructionListener.onDestroy();
			// }

			new ActionDie(this, squareGameObjectIsOn).perform();

			return true;
		}
		return false;
	}

	public ArrayList<Square> getAllSquaresAtDistance(float distance) {
		ArrayList<Square> squares = new ArrayList<Square>();
		if (distance == 0) {
			squares.add(this.squareGameObjectIsOn);
			return squares;
		}

		boolean xGoingUp = true;
		boolean yGoingUp = true;
		for (float i = 0, x = -distance, y = 0; i < distance * 4; i++) {
			if (ArrayUtils.inBounds(Game.level.squares, this.squareGameObjectIsOn.xInGrid + x,
					this.squareGameObjectIsOn.yInGrid + y)) {
				squares.add(Game.level.squares[this.squareGameObjectIsOn.xInGrid
						+ (int) x][this.squareGameObjectIsOn.yInGrid + (int) y]);
			}

			if (xGoingUp) {
				if (x == distance) {
					xGoingUp = false;
					x--;
				} else {
					x++;
				}
			} else {
				if (x == -distance) {
					xGoingUp = true;
					x++;
				} else {
					x--;
				}
			}

			if (yGoingUp) {
				if (y == distance) {
					yGoingUp = false;
					y--;
				} else {
					y++;
				}
			} else {
				if (y == -distance) {
					yGoingUp = true;
					y++;
				} else {
					y--;
				}
			}

		}
		return squares;
	}

	public ArrayList<Square> getAllSquaresWithinDistance(int minDistance, int maxDistance) {
		return getAllSquaresWithinDistance(minDistance, maxDistance, this.squareGameObjectIsOn);
	}

	public static ArrayList<Square> getAllSquaresWithinDistance(int minDistance, int maxDistance, Square squareFrom) {
		ArrayList<Square> squares = new ArrayList<Square>();

		for (int distance = minDistance; distance <= maxDistance; distance++) {

			if (distance == 0)

			{
				squares.add(squareFrom);
				continue;
			}

			boolean xGoingUp = true;
			boolean yGoingUp = true;
			for (float i = 0, x = -distance, y = 0; i < distance * 4; i++) {
				if (ArrayUtils.inBounds(Game.level.squares, squareFrom.xInGrid + x, squareFrom.yInGrid + y)) {
					squares.add(Game.level.squares[squareFrom.xInGrid + (int) x][squareFrom.yInGrid + (int) y]);
				}

				if (xGoingUp) {
					if (x == distance) {
						xGoingUp = false;
						x--;
					} else {
						x++;
					}
				} else {
					if (x == -distance) {
						xGoingUp = true;
						x++;
					} else {
						x--;
					}
				}

				if (yGoingUp) {
					if (y == distance) {
						yGoingUp = false;
						y--;
					} else {
						y++;
					}
				} else {
					if (y == -distance) {
						yGoingUp = true;
						y++;
					} else {
						y--;
					}
				}

			}
		}

		return squares;
	}

	public ArrayList<Point> getAllCoordinatesAtDistanceFromSquare(int distance) {
		return getAllCoordinatesAtDistanceFromSquare(distance, this.squareGameObjectIsOn);
	}

	public ArrayList<Point> getAllCoordinatesAtDistanceFromSquare(int distance, Square square) {
		ArrayList<Point> coordinates = new ArrayList<Point>();
		if (distance == 0) {
			coordinates.add(new Point(square.xInGrid, square.yInGrid));
			return coordinates;
		}

		boolean xGoingUp = true;
		boolean yGoingUp = true;
		for (int i = 0, x = -distance, y = 0; i < distance * 4; i++) {
			coordinates.add(new Point(square.xInGrid + x, square.yInGrid + y));

			if (xGoingUp) {
				if (x == distance) {
					xGoingUp = false;
					x--;
				} else {
					x++;
				}
			} else {
				if (x == -distance) {
					xGoingUp = true;
					x++;
				} else {
					x--;
				}
			}

			if (yGoingUp) {
				if (y == distance) {
					yGoingUp = false;
					y--;
				} else {
					y++;
				}
			} else {
				if (y == -distance) {
					yGoingUp = true;
					y++;
				} else {
					y--;
				}
			}

		}
		return coordinates;
	}

	public void showPow() {
		showPow = true;
		new HidePowThread(this).start();
	}

	public class HidePowThread extends Thread {

		GameObject gameObject;

		public HidePowThread(GameObject gameObject) {
			this.gameObject = gameObject;
		}

		@Override
		public void run() {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			showPow = false;
		}
	}

	public Weapon bestCounterWeapon(GameObject attacker, Weapon attackerWeapon, float range) {

		if (inventory == null)
			return null;

		for (GameObject gameObject : inventory.getGameObjects()) {
			if (gameObject instanceof Weapon) {
				Weapon weapon = (Weapon) gameObject;
				if (range >= weapon.getEffectiveMinRange() && range <= weapon.getEffectiveMaxRange()) {
					return weapon;
				}
			}
		}
		return null;
	}

	public GameObject makeCopy(Square square, Actor owner) {
		GameObject gameObject = new GameObject();
		setInstances(gameObject);
		setAttributesForCopy(gameObject, square, owner);
		return gameObject;
	}

	public ArrayList<Weapon> getWeaponsInInventory() {
		ArrayList<Weapon> weapons = new ArrayList<Weapon>();
		for (GameObject gameObject : inventory.getGameObjects()) {
			if (gameObject instanceof Weapon) {
				weapons.add((Weapon) gameObject);
			}
		}
		return weapons;
	}

	public void update(int delta) {

		if (!(this instanceof Actor))
			clearActions();

		if (this.remainingHealth > 0) {
			activateEffects();
		}
		for (GameObject gameObjectInInventory : this.inventory.getGameObjects()) {
			if (!(gameObjectInInventory instanceof Actor))
				gameObjectInInventory.update(delta);
		}
	}

	public void updateRealtime(int delta) {
		if (primaryAnimation != null && !primaryAnimation.completed) {
			primaryAnimation.update(delta);
		} else {
		}

		for (Animation secondaryAnimation : (ArrayList<Animation>) secondaryAnimations.clone()) {
			secondaryAnimation.update(delta);
			if (secondaryAnimation.completed)
				secondaryAnimations.remove(secondaryAnimation);
		}
	}

	public float getCenterX() {
		return squareGameObjectIsOn.xInGridPixels + Game.HALF_SQUARE_WIDTH;
	}

	public float getCenterY() {
		return squareGameObjectIsOn.yInGridPixels + Game.HALF_SQUARE_HEIGHT;
	}

	@Override
	public Action getDefaultActionPerformedOnThisInWorld(Actor performer) {
		if (this instanceof Discoverable) {
			Discoverable discoverable = (Discoverable) this;
			if (!discoverable.discovered)
				return null;
		}

		// Water Source
		if (!(this instanceof WaterBody) && this.squareGameObjectIsOn != null
				&& this.squareGameObjectIsOn.inventory.waterBody != null) {
			return new ActionFishingStart(performer, this);
		}

		if (diggable) {
			// System.out.println("digable");
			Action action = new ActionDig(performer, this);
			// System.out.println("action = " + action);
			// System.out.println("action.enabled = " + action.enabled);
			return action;
		}

		if (this instanceof Vein) {
			Action action = new ActionMiningStart(performer, (Vein) this);
			return action;
		}

		if (this instanceof Stump || this instanceof Tree) {
			return new ActionChoppingStart(performer, this);
		}

		if (this instanceof PressurePlate) {
			return new ActionMove(performer, this.squareGameObjectIsOn, true);
		}

		if (this instanceof Door) {
			if (((Door) this).open) {
				return new ActionMove(performer, this.squareGameObjectIsOn, true);
			} else if (!(this instanceof RemoteDoor)) {
				return new ActionOpen(performer, ((Door) this));
			}
		}

		if ((this instanceof Openable) && this.canContainOtherObjects && !(this instanceof Actor)) {
			return new ActionOpenOtherInventory(performer, this);
		}

		if (this.fitsInInventory)
			return new ActionTakeItems(performer, this.squareGameObjectIsOn, this);

		return null;
	}

	@Override
	public Action getSecondaryActionPerformedOnThisInWorld(Actor performer) {
		if (this instanceof Discoverable) {
			Discoverable discoverable = (Discoverable) this;
			if (!discoverable.discovered)
				return null;
		}

		if (diggable) {
			return new ActionDig(performer, this);
		}

		if (this instanceof Vein) {
			Action action = new ActionMiningStart(performer, (Vein) this);
			return action;
		}

		if (this instanceof Stump || this instanceof Tree) {
			return new ActionChoppingStart(performer, this);
		}

		// Pressure Plate
		if (this instanceof PressurePlate) {
			return new ActionOpenInventoryToDropItems(performer, this.squareGameObjectIsOn);
		}

		if (this instanceof Door && !(this instanceof RemoteDoor)) {
			if (((Door) this).open) {
				return new ActionClose(performer, ((Door) this));
			}
		}

		// if ((this instanceof Openable) && this.canContainOtherObjects &&
		// !(this instanceof Actor)) {
		// return new ActionOpenOtherInventory(performer, this);
		// }

		if (this.fitsInInventory)
			return new ActionTakeItems(performer, this.squareGameObjectIsOn, this);

		return null;
	}

	@Override
	public ArrayList<Action> getAllActionsPerformedOnThisInWorld(Actor performer) {
		ArrayList<Action> actions = new ArrayList<Action>();

		if (this.remainingHealth <= 0)
			return actions;

		if (this instanceof Discoverable) {
			Discoverable discoverable = (Discoverable) this;
			if (!discoverable.discovered)
				return actions;
		}

		// Inspectable
		if (this instanceof Inspectable) {
			actions.add(new ActionInspect(performer, this));
			return actions;
		}

		// Map Marker
		if (this instanceof MapMarker) {
			MapMarker mapMarker = (MapMarker) this;

			actions.add(new ActionInspect(performer, mapMarker));
			if (Journal.markersToTrack.contains(mapMarker)) {
				actions.add(new ActionUntrackMapMarker(mapMarker));
			} else {
				actions.add(new ActionTrackMapMarker(mapMarker));
			}
			actions.add(new ActionRename(mapMarker));
			actions.add(new ActionChangeAppearance(mapMarker));
			actions.add(new ActionRemoveMapMarker(mapMarker));
			return actions;
		}

		// Water Source
		if (this instanceof WaterSource) {
			actions.add(new ActionFillContainersInInventory(performer, (WaterSource) this));
		}

		// Water Body
		if (!(this instanceof WaterBody) && this.squareGameObjectIsOn != null
				&& this.squareGameObjectIsOn.inventory.waterBody != null) {
			actions.add(new ActionFishingStart(performer, this));
		}

		// Skinnable
		if (this instanceof Carcass) {
			actions.add(new ActionSkin(performer, this));
		}

		// Readable
		if (this instanceof Readable) {
			actions.add(new ActionRead(performer, (Readable) this));
		}

		// Hiding place
		if (this instanceof HidingPlace) {
			HidingPlace hidingPlace = (HidingPlace) this;
			if (performer.hiding && performer.squareGameObjectIsOn == this.squareGameObjectIsOn) {
				actions.add(new ActionStopHiding(performer, hidingPlace));
			} else {
				actions.add(new ActionHide(performer, hidingPlace));
			}
		}

		// Searchable
		if (this instanceof Searchable) {
			actions.add(new ActionSearch(performer, (Searchable) this));
		}

		if (diggable) {
			actions.add(new ActionDig(performer, this));
		}

		// Tree and stump
		if (this instanceof Stump || this instanceof Tree) {
			actions.add(new ActionChoppingStart(performer, this));
		}

		// Food
		if (this instanceof Food) {
			actions.add(new ActionEatItems(performer, this));
		}

		// Switch
		if (this instanceof PressurePlate) {
			if (performer.equipped != null)
				actions.add(new ActionDropItems(performer, this.squareGameObjectIsOn, performer.equipped));
			actions.add(new ActionOpenInventoryToDropItems(performer, this.squareGameObjectIsOn));
		} else if (this instanceof Switch) {
			Switch zwitch = (Switch) this;
			actions.add(
					new ActionUse(performer, zwitch, zwitch.actionName, zwitch.actionVerb, zwitch.requirementsToMeet));
		}

		// Vein
		if (this instanceof Vein) {
			actions.add(new ActionMiningStart(performer, (Vein) this));
		}

		// Window
		if (this instanceof Window) {
			actions.add(new ActionSmash(performer, this));
		}

		// Loot
		if ((this instanceof Openable) && this.canContainOtherObjects && !(this instanceof Actor)) {
			actions.add(new ActionOpenOtherInventory(performer, this));
		}

		// Openable, Chests, Doors
		if (this instanceof Openable && !(this instanceof RemoteDoor)) {
			Openable openable = (Openable) this;

			if (!openable.open) {
				actions.add(new ActionOpen(performer, openable));
			}

			if (openable.open)
				actions.add(new ActionClose(performer, openable));

			if (openable.locked)
				actions.add(new ActionUnlock(performer, openable));

			if (!openable.locked)
				actions.add(new ActionLock(performer, openable));

			if (this instanceof Door) {
				if (!openable.open) {
					if (Game.level.player.peekingThrough == this)
						actions.add(new ActionStopPeeking(performer));
					else
						actions.add(new ActionPeek(performer, this));
				}
			}
		}

		// public boolean showInventory;
		// public boolean canShareSquare;
		if (!decorative && fitsInInventory) {
			actions.add(new ActionTakeItems(performer, this.squareGameObjectIsOn, this));
		}

		if (!decorative && canBePickedUp && !fitsInInventory) {
			actions.add(new ActionLift(performer, this));
		}

		if (!decorative && canBePickedUp && fitsInInventory) {
			actions.add(new ActionEquip(performer, this));
		}

		// Here put view loot

		// if (Game.level.activeActor != null &&
		// Game.level.activeActor.equippedWeapon != null
		// && Game.level.activeActor.equippedWeapon
		// .hasRange(Game.level.activeActor.straightLineDistanceTo(this.squareGameObjectIsOn)))
		// {
		if (!decorative && this != Game.level.player && attackable)
			actions.add(new ActionAttack(performer, this));

		if (!decorative && this != Game.level.player && attackable && !(this instanceof Wall)
				&& !(this instanceof Door))
			actions.add(new ActionTeleportOther(performer, this));
		// }
		if (!decorative && this != Game.level.player && this instanceof Actor)
			actions.add(new ActionFollow(Game.level.player, (Actor) this));

		// if (!decorative && performer.equipped != null &&
		// this.canContainOtherObjects) {
		// actions.add(new ActionGiveSpecificItem(performer, this,
		// performer.equipped, false));
		// }

		// Give from inventory
		if (!decorative && this.canContainOtherObjects) {
			actions.add(new ActionOpenInventoryToGiveItems(performer, this));
		}

		// Trade
		if (!decorative && this.canContainOtherObjects && this instanceof Actor && !(this instanceof NonHuman)) {
			actions.add(new ActionInitiateTrade(performer, (Actor) this));
		}

		if (!decorative && this.squareGameObjectIsOn != Game.level.player.squareGameObjectIsOn
				&& performer.equipped != null) {
			actions.add(new ActionThrowItem(performer, this, performer.equipped));
		}

		// Throw from inventory
		if (!decorative && this.squareGameObjectIsOn != Game.level.player.squareGameObjectIsOn)
			actions.add(new ActionOpenInventoryToThrowItems(performer, this));

		// Pour from inventory
		if (!decorative)
			actions.add(new ActionPourContainerInInventory(performer, this));

		// if (!decorative)
		// actions.add(new ActionCastBurn(performer, this));
		// if (!decorative)
		// actions.add(new ActionCastDouse(performer, this));
		// if (!decorative)
		// actions.add(new ActionCastPoison(performer, this));

		if (!(this instanceof MapMarker))
			actions.add(new ActionPin(performer, this));

		return actions;

	}

	public Conversation getConversation() {
		return null;
	}

	public int straightLineDistanceBetween(Square sourceSquare, Square targetSquare) {

		if (sourceSquare == null)
			return Integer.MAX_VALUE;

		if (targetSquare == null)
			return Integer.MAX_VALUE;

		return Math.abs(sourceSquare.xInGrid - targetSquare.xInGrid)
				+ Math.abs(sourceSquare.yInGrid - targetSquare.yInGrid);
	}

	public int straightLineDistanceTo(Square square) {
		Game.straightLineDistanceTo++;

		return straightLineDistanceBetween(this.squareGameObjectIsOn, square);
	}

	@Override
	public int compareTo(Object otherObject) {

		if (otherObject instanceof GameObject) {
			return compareGameObjectToGameObject((GameObject) otherObject);
		}

		// if (Inventory.inventorySortBy ==
		// Inventory.INVENTORY_SORT_BY.SORT_BY_SLASH_DAMAGE) {
		// if (!(this instanceof Weapon) && !(otherObject instanceof Weapon)) {
		// return 0;
		// } else if (!(this instanceof Weapon)) {
		// return +1;
		// } else if (!(otherObject instanceof Weapon)) {
		// return -1;
		// } else {
		// return Math.round((((Weapon) otherObject).slashDamage - ((Weapon)
		// this).slashDamage));
		// }
		// }
		return 0;
	}

	public int compareGameObjectToGameObject(GameObject otherGameObject) {

		if (Inventory.inventorySortBy == Inventory.INVENTORY_SORT_BY.SORT_ALPHABETICALLY) {
			return this.name.compareToIgnoreCase(otherGameObject.name);
		}

		if (Inventory.inventorySortBy == Inventory.INVENTORY_SORT_BY.SORT_BY_NEWEST) {
			return otherGameObject.turnAcquired - this.turnAcquired;
		}

		if (Inventory.inventorySortBy == Inventory.INVENTORY_SORT_BY.SORT_BY_FAVOURITE) {
			return Boolean.compare(this.favourite, otherGameObject.favourite);
		}

		if (Inventory.inventorySortBy == Inventory.INVENTORY_SORT_BY.SORT_BY_VALUE) {
			return Float.compare(otherGameObject.value, this.value);
		}

		if (Inventory.inventorySortBy == Inventory.INVENTORY_SORT_BY.SORT_BY_TOTAL_DAMAGE
				|| Inventory.inventorySortBy == Inventory.INVENTORY_SORT_BY.SORT_BY_SLASH_DAMAGE
				|| Inventory.inventorySortBy == Inventory.INVENTORY_SORT_BY.SORT_BY_BLUNT_DAMAGE
				|| Inventory.inventorySortBy == Inventory.INVENTORY_SORT_BY.SORT_BY_PIERCE_DAMAGE
				|| Inventory.inventorySortBy == Inventory.INVENTORY_SORT_BY.SORT_BY_FIRE_DAMAGE
				|| Inventory.inventorySortBy == Inventory.INVENTORY_SORT_BY.SORT_BY_WATER_DAMAGE
				|| Inventory.inventorySortBy == Inventory.INVENTORY_SORT_BY.SORT_BY_POISON_DAMAGE
				|| Inventory.inventorySortBy == Inventory.INVENTORY_SORT_BY.SORT_BY_ELECTRICAL_DAMAGE
				|| Inventory.inventorySortBy == Inventory.INVENTORY_SORT_BY.SORT_BY_MAX_RANGE
				|| Inventory.inventorySortBy == Inventory.INVENTORY_SORT_BY.SORT_BY_MIN_RANGE) {

			if (!(this instanceof Weapon) && !(otherGameObject instanceof Weapon)) {
				return 0;
			} else if (!(this instanceof Weapon)) {
				return +1;
			} else if (!(otherGameObject instanceof Weapon)) {
				return -1;
			} else {
				return ((Weapon) this).compareWeaponToWeapon((Weapon) otherGameObject);
			}

		}

		return 0;

	}

	@Override
	public Action getDefaultActionPerformedOnThisInInventory(Actor performer) {

		if (this.inventoryThatHoldsThisObject == null) {
			return null;
		}

		if (Inventory.inventoryMode == Inventory.INVENTORY_MODE.MODE_SELECT_ITEM_TO_FILL) {
			if (this instanceof ContainerForLiquids)
				return new ActionFillSpecificContainer(performer, Inventory.waterSource, (ContainerForLiquids) this);
			else
				return null;
		}

		if (Inventory.inventoryMode == Inventory.INVENTORY_MODE.MODE_SELECT_ITEM_TO_DROP) {
			return new ActionDropItemsSelectedInInventory(performer, (Square) Inventory.target, this);
		}

		if (Inventory.inventoryMode == Inventory.INVENTORY_MODE.MODE_SELECT_ITEM_TO_GIVE) {
			return new ActionGiveItemsSelectedInInventory(performer, (GameObject) Inventory.target, false, this);
		}

		if (Inventory.inventoryMode == Inventory.INVENTORY_MODE.MODE_SELECT_ITEM_TO_THROW) {
			return new ActionThrowItem(performer, Inventory.target, this);
		}

		if (Inventory.inventoryMode == Inventory.INVENTORY_MODE.MODE_SELECT_ITEM_TO_POUR) {
			return new ActionPourSpecificItem(performer, Inventory.target, (ContainerForLiquids) this);
		}

		if (Inventory.inventoryMode == Inventory.INVENTORY_MODE.MODE_LOOT) {
			if (this.inventoryThatHoldsThisObject != performer.inventory)
				return new ActionTakeItemsSelectedInInventory(performer, Inventory.target, this);
			else if (Inventory.target instanceof GameObject)
				return new ActionGiveItemsSelectedInInventory(performer, (GameObject) Inventory.target, false, this);
		}

		if (Inventory.inventoryMode == Inventory.INVENTORY_MODE.MODE_TRADE) {
			if (this.inventoryThatHoldsThisObject == performer.inventory)
				return new ActionSellItemsSelectedInInventory(performer, (Actor) Inventory.target, this);
			else
				return new ActionBuytemsSelectedInInventory(performer, (Actor) Inventory.target, this);
		}

		if (this instanceof Food) {
			return new ActionEatItemsSelectedInInventory(performer, this);
		}

		if (performer.equipped == this || performer.helmet == this || performer.bodyArmor == this
				|| performer.legArmor == this)
			return new ActionUnequip(performer, this);
		else
			return new ActionEquip(performer, this);

	}

	public Action getSecondaryActionPerformedOnThisInInventory(Actor performer) {

		if (this.inventoryThatHoldsThisObject == null) {
			return null;
		}

		if (Inventory.inventoryMode == Inventory.INVENTORY_MODE.MODE_NORMAL) {
			if (this.inventoryThatHoldsThisObject == performer.inventory) {
				return new ActionDropItemsSelectedInInventory(performer, performer.squareGameObjectIsOn, this);
			} else {
				return new ActionEquip(Game.level.player, this);
			}

		}

		if (Inventory.inventoryMode == Inventory.INVENTORY_MODE.MODE_LOOT) {
			if (performer.equipped == this || performer.helmet == this || performer.bodyArmor == this
					|| performer.legArmor == this)
				return new ActionUnequip(performer, this);
			else
				return new ActionEquip(performer, this);

		}

		return null;
	}

	@Override
	public ArrayList<Action> getAllActionsPerformedOnThisInInventory(Actor performer) {

		ArrayList<Action> actions = new ArrayList<Action>();

		if (this.inventoryThatHoldsThisObject == null) {
			return actions;
		}

		if (Inventory.inventoryMode == Inventory.INVENTORY_MODE.MODE_SELECT_ITEM_TO_FILL) {
			return actions;
		}

		if (Inventory.inventoryMode == Inventory.INVENTORY_MODE.MODE_SELECT_ITEM_TO_DROP) {
			return actions;
		}

		if (Inventory.inventoryMode == Inventory.INVENTORY_MODE.MODE_SELECT_ITEM_TO_GIVE) {
			return actions;
		}

		if (Inventory.inventoryMode == Inventory.INVENTORY_MODE.MODE_SELECT_ITEM_TO_THROW) {
			return actions;
		}

		if (Inventory.inventoryMode == Inventory.INVENTORY_MODE.MODE_SELECT_ITEM_TO_POUR) {
			return actions;
		}

		if (Inventory.inventoryMode == Inventory.INVENTORY_MODE.MODE_LOOT) {
			if (this.inventoryThatHoldsThisObject != performer.inventory)
				actions.add(new ActionTakeItemsSelectedInInventory(performer, Inventory.target, this));
		}

		if (Inventory.inventoryMode == Inventory.INVENTORY_MODE.MODE_TRADE) {
			if (this.inventoryThatHoldsThisObject == performer.inventory)
				actions.add(new ActionSellItemsSelectedInInventory(performer, (Actor) Inventory.target, this));
			else
				actions.add(new ActionBuytemsSelectedInInventory(performer, (Actor) Inventory.target, this));
		}

		if (performer.equipped == this || performer.helmet == this || performer.bodyArmor == this
				|| performer.legArmor == this)
			actions.add(new ActionUnequip(performer, this));
		else
			actions.add(new ActionEquip(performer, this));

		if (this instanceof Food) {
			actions.add(new ActionEatItemsSelectedInInventory(performer, this));
		}

		if (Inventory.inventoryMode == Inventory.INVENTORY_MODE.MODE_LOOT) {
			actions.add(new ActionGiveItemsSelectedInInventory(performer, (GameObject) Inventory.target, false, this));
		}

		actions.add(new ActionDropItemsSelectedInInventory(performer, performer.squareGameObjectIsOn, this));

		if (this.inventoryThatHoldsThisObject == Game.level.player.inventory && !(this instanceof Gold)) {
			actions.add(new ActionStarSpecificItem(this));
		}

		// actions.add(new ActionThrow(performer, this, performer.equipped));
		// actions.add(new ActionCastBurn(performer, this));
		// actions.add(new ActionCastDouse(performer, this));
		// actions.add(new ActionCastPoison(performer, this));

		return actions;
	}

	public ArrayList<Action> getAllActionsPerformedOnThisInOtherInventory(Actor performer) {
		ArrayList<Action> actions = new ArrayList<Action>();
		if (!(this.inventoryThatHoldsThisObject.parent instanceof Actor)) {
			actions.add(new ActionTakeItemsSelectedInInventory(performer, Inventory.target, this));
			actions.add(new ActionEquip(performer, this));
		} else if (Inventory.inventoryMode == Inventory.INVENTORY_MODE.MODE_TRADE) {
			actions.add(new ActionBuytemsSelectedInInventory(performer, (Actor) Inventory.target, this));
		}

		return actions;
	}

	public boolean visibleFrom(Square square) {
		return visibilityBetween(squareGameObjectIsOn, square);
	}

	public boolean visibilityBetween(Square sourceSquare, Square targetSquare) {
		return checkVisibilityBetweenTwoPoints(targetSquare.xInGrid + 0.5d, targetSquare.yInGrid + 0.5d,
				sourceSquare.xInGrid + 0.5d, sourceSquare.yInGrid + 0.5d);
	}

	// SUPERCOVER algorithm
	public boolean checkVisibilityBetweenTwoPoints(double x0, double y0, double x1, double y1) {
		double vx = x1 - x0;
		double vy = y1 - y0;
		double dx = Math.sqrt(1 + Math.pow((vy / vx), 2));
		double dy = Math.sqrt(1 + Math.pow((vx / vy), 2));
		double ix = Math.floor(x0);
		double iy = Math.floor(y0);
		double sx, ex;

		if (vx < 0) {
			sx = -1;
			ex = (x0 - ix) * dx;
		} else {
			sx = 1;
			ex = (ix + 1 - x0) * dx;
		}

		double sy, ey;
		if (vy < 0) {
			sy = -1;
			ey = (y0 - iy) * dy;
		} else {
			sy = 1;
			ey = (iy + 1 - y0) * dy;
		}

		boolean done = false;
		double len = Math.sqrt(vx * vx + vy * vy);

		while (done == false) {
			if (Math.min(ex, ey) <= len) {
				double rx = ix;
				double ry = iy;
				if (ex < ey) {
					ex = ex + dx;
					ix = ix + sx;
				} else {
					ey = ey + dy;
					iy = iy + sy;
				}

				if (Game.level.squares[(int) rx][(int) ry] != squareGameObjectIsOn) {
					if (Game.level.squares[(int) rx][(int) ry].inventory.blocksLineOfSight()) {

						if ((int) x0 == (int) rx && (int) y0 == (int) ry) {
							return true;
						} else {
							return false;

						}
					}
				}
				// done = markSquareAsVisibleToActiveCharacter((int) rx, (int)
				// ry);
			} else if (!done) {
				done = true;
				return true;
				// markSquareAsVisibleToActiveCharacter((int) ix, (int) iy);
			}
		}
		return true;
	}

	public void clearActions() {
		actionsPerformedThisTurn.clear();
	}

	public void attackedBy(Object attacker, Action action) {
		checkIfDestroyed(attacker, action);
	}

	public void addEffect(Effect effectToAdd) {

		if (remainingHealth <= 0 || !attackable)
			return;

		if (effectToAdd instanceof EffectBleeding && !(this instanceof Actor))
			return;

		Effect effectToRemove = null;
		for (Effect existingEffect : this.activeEffectsOnGameObject) {
			if (existingEffect.getClass() == effectToAdd.getClass()) {
				if (effectToAdd.turnsRemaining >= existingEffect.turnsRemaining) {
					effectToRemove = existingEffect;
				} else {
					return;
				}
			}
		}

		this.activeEffectsOnGameObject.remove(effectToRemove);
		this.activeEffectsOnGameObject.add(effectToAdd);
		if (Game.level.shouldLog(this))
			Game.level.logOnScreen(new ActivityLog(new Object[] { this, effectToAdd.logString, effectToAdd.source }));
	}

	public void activateEffects() {

		if (activeEffectsOnGameObject.size() == 0)
			return;

		ArrayList<Effect> effectsToRemove = new ArrayList<Effect>();
		for (Effect effect : (ArrayList<Effect>) this.activeEffectsOnGameObject.clone()) {
			effect.activate();
			if (effect.turnsRemaining == 0)
				effectsToRemove.add(effect);
		}

		for (Effect effect : effectsToRemove) {
			this.removeEffect(effect);
		}
	}

	public void looted() {

	}

	public void thrown(Actor shooter) {

	}

	public void landed(Actor shooter, Action action) {

	}

	protected void addAttacker(GameObject potentialAttacker) {

		if (potentialAttacker != null && potentialAttacker.remainingHealth > 0
				&& !this.attackers.contains(potentialAttacker) && potentialAttacker != this) {
			this.attackers.add(potentialAttacker);
			// potentialAttacker.addAttackerForThisAndGroupMembers(this);
		}
	}

	public void addAttackerForThisAndGroupMembers(GameObject attacker) {

		if (this == attacker || attacker == null || attacker.remainingHealth <= 0)
			return;

		attacker.addAttacker(this);

		this.addAttacker(attacker);

		if (this.group != null) {
			if (!this.group.getAttackers().contains(attacker)) {
				this.group.addAttacker(attacker);
			}
			for (Actor groupMember : this.group.getMembers()) {
				if (!groupMember.attackers.contains(attacker)) {
					groupMember.addAttacker(attacker);
				}
				if (!attacker.attackers.contains(groupMember)) {
					attacker.addAttacker(groupMember);
				}
			}
		}

		if (attacker.group != null) {
			if (!attacker.group.getAttackers().contains(this)) {
				attacker.group.addAttacker(this);
			}
			for (Actor groupMember : attacker.group.getMembers()) {
				if (!groupMember.attackers.contains(this)) {
					groupMember.addAttacker(this);
				}
				if (!this.attackers.contains(groupMember)) {
					this.addAttacker(groupMember);
				}
			}
		}

	}

	public ArrayList<Effect> getActiveEffectsOnGameObject() {
		return activeEffectsOnGameObject;
	}

	@Override
	public String toString() {
		return name + " @ " + squareGameObjectIsOn;
	}

	// (int) (mouseXTransformed / Game.SQUARE_WIDTH); gives u sqr[x][]

	public Conversation createConversation(String text) {
		return Conversation.createConversation(text, this);
	}

	public Conversation createConversation(Object[] text) {
		return Conversation.createConversation(text, this);
	}

	public static int currentTemplateIdCount = 0;

	public static int generateNewTemplateId() {
		currentTemplateIdCount++;

		Level.bestiaryKnowledgeCollection.put(currentTemplateIdCount, new BestiaryKnowledge(currentTemplateIdCount));

		return currentTemplateIdCount;
	}

	public void setAttributesForCopy(GameObject gameObject, Square square, Actor owner) {
		gameObject.owner = owner;
		gameObject.squareGameObjectIsOn = square;
		gameObject.anchorX = anchorX;
		gameObject.anchorY = anchorY;
		gameObject.name = name;
		gameObject.squareGameObjectIsOn = square;
		gameObject.value = value;
		gameObject.floats = floats;

		gameObject.totalHealth = gameObject.remainingHealth = totalHealth;
		gameObject.imageTexturePath = imageTexturePath;
		gameObject.imageTexture = imageTexture;
		gameObject.widthRatio = widthRatio;
		gameObject.heightRatio = heightRatio;
		gameObject.drawOffsetRatioX = drawOffsetRatioX;
		if (gameObject instanceof Arrow) {
			System.out.println("new ARROW.drawOffsetRatioX = " + Templates.ARROW.drawOffsetRatioX);
			System.out.println("setAttributesForCopy arrow.drawOffsetRatioX = " + gameObject.drawOffsetRatioX);
		}
		gameObject.drawOffsetRatioY = drawOffsetRatioY;
		gameObject.soundWhenHit = soundWhenHit;
		gameObject.soundWhenHitting = soundWhenHitting;
		gameObject.soundDampening = soundDampening;
		gameObject.weight = weight;

		gameObject.slashDamage = slashDamage;
		gameObject.pierceDamage = pierceDamage;
		gameObject.bluntDamage = bluntDamage;
		gameObject.fireDamage = fireDamage;
		gameObject.waterDamage = waterDamage;
		gameObject.electricalDamage = electricalDamage;
		gameObject.poisonDamage = poisonDamage;
		gameObject.bleedingDamage = bleedingDamage;
		gameObject.healing = healing;
		gameObject.minRange = minRange;
		gameObject.maxRange = maxRange;

		gameObject.slashResistance = slashResistance;
		gameObject.bluntResistance = bluntResistance;
		gameObject.pierceResistance = pierceResistance;
		gameObject.fireResistance = fireResistance;
		gameObject.waterResistance = waterResistance;
		gameObject.electricResistance = electricResistance;
		gameObject.poisonResistance = poisonResistance;
		gameObject.bleedingResistance = bleedingResistance;

		gameObject.templateId = templateId;

		gameObject.diggable = diggable;
		gameObject.flipYAxisInMirror = flipYAxisInMirror;

		gameObject.init();
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

	public float getEffectiveBluntResistance() {
		return bluntResistance;
	}

	public float getEffectivePierceResistance() {
		return pierceResistance;
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

	public float getEffectivePoisonResistance() {
		return poisonResistance;
	}

	public float getEffectiveBleedingResistance() {
		return bleedingResistance;
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

	// public static interface DestructionListener {
	// public void onDestroy();
	// }

	// public void addDestructionListener(DestructionListener
	// destructionListener) {
	// if (!destructionListeners.contains(destructionListener))
	// destructionListeners.add(destructionListener);
	// }

	public boolean hasRange(int weaponDistanceTo) {
		if (getEffectiveMinRange() == 1 && weaponDistanceTo == 0)
			return true;

		if (weaponDistanceTo >= getEffectiveMinRange() && weaponDistanceTo <= getEffectiveMaxRange()) {
			return true;
		}
		return false;
	}

	public int compareWeaponToWeapon(Weapon otherGameObject) {

		if (Inventory.inventorySortBy == Inventory.INVENTORY_SORT_BY.SORT_BY_TOTAL_DAMAGE) {
			return Math.round(otherGameObject.getTotalDamage() - this.getTotalDamage());
		}

		if (Inventory.inventorySortBy == Inventory.INVENTORY_SORT_BY.SORT_BY_SLASH_DAMAGE) {
			return Math.round(otherGameObject.slashDamage - this.slashDamage);
		}

		if (Inventory.inventorySortBy == Inventory.INVENTORY_SORT_BY.SORT_BY_BLUNT_DAMAGE) {
			return Math.round(otherGameObject.bluntDamage - this.bluntDamage);
		}

		if (Inventory.inventorySortBy == Inventory.INVENTORY_SORT_BY.SORT_BY_PIERCE_DAMAGE) {
			return Math.round(otherGameObject.pierceDamage - this.pierceDamage);
		}

		if (Inventory.inventorySortBy == Inventory.INVENTORY_SORT_BY.SORT_BY_FIRE_DAMAGE) {
			return Math.round(otherGameObject.fireDamage - this.fireDamage);
		}

		if (Inventory.inventorySortBy == Inventory.INVENTORY_SORT_BY.SORT_BY_WATER_DAMAGE) {
			return Math.round(otherGameObject.waterDamage - this.waterDamage);
		}

		if (Inventory.inventorySortBy == Inventory.INVENTORY_SORT_BY.SORT_BY_POISON_DAMAGE) {
			return Math.round(otherGameObject.poisonDamage - this.poisonDamage);
		}

		if (Inventory.inventorySortBy == Inventory.INVENTORY_SORT_BY.SORT_BY_ELECTRICAL_DAMAGE) {
			return Math.round(otherGameObject.electricalDamage - this.electricalDamage);

		}

		if (Inventory.inventorySortBy == Inventory.INVENTORY_SORT_BY.SORT_BY_BLEEDING_DAMAGE) {
			return Math.round(otherGameObject.bleedingDamage - this.bleedingDamage);

		}

		if (Inventory.inventorySortBy == Inventory.INVENTORY_SORT_BY.SORT_BY_HEALING) {
			return Math.round(otherGameObject.healing - this.healing);

		}

		if (Inventory.inventorySortBy == Inventory.INVENTORY_SORT_BY.SORT_BY_MAX_RANGE) {
			return Math.round(otherGameObject.maxRange - this.maxRange);
		}

		if (Inventory.inventorySortBy == Inventory.INVENTORY_SORT_BY.SORT_BY_MIN_RANGE) {
			return Math.round(otherGameObject.minRange - this.minRange);
		}

		return 0;

	}

	public Action getUtilityAction(Actor performer) {
		return null;
	}

	@Override
	public float getEffectiveSlashDamage() {
		if (enhancement != null)
			return slashDamage + enhancement.slashDamage;
		return slashDamage;
	}

	@Override
	public float getEffectivePierceDamage() {
		if (enhancement != null)
			return pierceDamage + enhancement.pierceDamage;
		return pierceDamage;
	}

	@Override
	public float getEffectiveBluntDamage() {
		if (enhancement != null)
			return bluntDamage + enhancement.bluntDamage;
		return bluntDamage;
	}

	@Override
	public float getEffectiveFireDamage() {
		if (enhancement != null)
			return fireDamage + enhancement.fireDamage;
		return fireDamage;
	}

	@Override
	public float getEffectiveWaterDamage() {
		if (enhancement != null)
			return waterDamage + enhancement.waterDamage;
		return waterDamage;
	}

	@Override
	public float getEffectiveElectricDamage() {
		if (enhancement != null)
			return electricalDamage + enhancement.electricalDamage;
		return electricalDamage;
	}

	@Override
	public float getEffectivePoisonDamage() {
		if (enhancement != null)
			return poisonDamage + enhancement.poisonDamage;
		return poisonDamage;
	}

	@Override
	public float getEffectiveBleedingDamage() {
		if (enhancement != null)
			return bleedingDamage + enhancement.bleedingDamage;
		return bleedingDamage;
	}

	@Override
	public float getEffectiveHealing() {
		if (enhancement != null)
			return healing + enhancement.healing;
		return healing;
	}

	public float getEffectiveMinRange() {
		return minRange;
	}

	public float getEffectiveMaxRange() {
		return maxRange;
	}

	public float getTotalDamage() {
		return slashDamage + pierceDamage + bluntDamage + fireDamage + waterDamage + electricalDamage + poisonDamage
				+ bleedingDamage;
	}

	// public float getTotalEffectiveDamage() {
	// return getEffectiveSlashDamage() + getEffectivePierceDamage() +
	// getEffectiveBluntDamage()
	// + getEffectiveFireDamage() + getEffectiveWaterDamage() +
	// getEffectiveElectricalDamage()
	// + getEffectivePoisonDamage();
	// }

	public boolean animationsBlockingAI() {
		if (!primaryAnimation.completed && primaryAnimation.blockAI)
			return true;

		for (Animation secondaryAnimation : secondaryAnimations) {
			if (!secondaryAnimation.completed && secondaryAnimation.blockAI)
				return true;
		}

		return false;
	}

	public void changeHealth(float change, Object attacker, Action action) {

		remainingHealth += change;
		if (remainingHealth > totalHealth)
			remainingHealth = totalHealth;
		if (remainingHealth < 0)
			remainingHealth = 0;

		if (change < 0)
			attackedBy(attacker, action);

	}

	public float changeHealth(Object attacker, Action action, DamageDealer damageDealer) {

		int offsetY = 0;
		boolean thisIsAnAttack = false;
		float totalDamage = 0;
		GameObject gameObjectAttacker = null;
		if (attacker instanceof GameObject)
			gameObjectAttacker = (GameObject) attacker;
		// System.out.println("damageDealer.bluntDamage = " + ((Actor)
		// damageDealer).bluntDamage);

		// Slash
		if (damageDealer.getEffectiveSlashDamage() != 0) {

			float resistance = (this.getEffectiveSlashResistance() / 100);
			float resistedDamage = damageDealer.getEffectiveSlashDamage() * resistance;
			float dmg = damageDealer.getEffectiveSlashDamage() - resistedDamage;
			doDamageAnimation(dmg, offsetY, DAMAGE_TYPE.SLASH, this.getEffectiveSlashResistance());
			remainingHealth -= dmg;
			totalDamage += dmg;
			if (dmg > 0)
				thisIsAnAttack = true;

			// Update bestiary
			if (Game.level.shouldLog(this))
				Game.level.bestiaryKnowledgeCollection.get(this.templateId).slashResistance = true;

			if (gameObjectAttacker != null)
				if (Game.level.shouldLog(gameObjectAttacker))
					Game.level.bestiaryKnowledgeCollection.get(gameObjectAttacker.templateId).slashDamage = true;

			offsetY += 48;
		}

		// Blunt
		if (damageDealer.getEffectiveBluntDamage() != 0) {

			float resistance = (this.getEffectiveBluntResistance() / 100);
			float resistedDamage = damageDealer.getEffectiveBluntDamage() * resistance;
			float dmg = damageDealer.getEffectiveBluntDamage() - resistedDamage;
			doDamageAnimation(dmg, offsetY, DAMAGE_TYPE.BLUNT, this.getEffectiveBluntResistance());
			remainingHealth -= dmg;
			totalDamage += dmg;
			if (dmg > 0)
				thisIsAnAttack = true;

			// Update bestiary
			if (Game.level.shouldLog(this))
				Game.level.bestiaryKnowledgeCollection.get(this.templateId).bluntResistance = true;

			if (gameObjectAttacker != null)
				if (Game.level.shouldLog(gameObjectAttacker))
					Game.level.bestiaryKnowledgeCollection.get(gameObjectAttacker.templateId).bluntDamage = true;

			offsetY += 48;
		}

		// Pierce
		if (damageDealer.getEffectivePierceDamage() != 0) {

			float resistance = (this.getEffectivePierceResistance() / 100);
			float resistedDamage = damageDealer.getEffectivePierceDamage() * resistance;
			float dmg = damageDealer.getEffectivePierceDamage() - resistedDamage;
			doDamageAnimation(dmg, offsetY, DAMAGE_TYPE.PIERCE, this.getEffectivePierceResistance());
			remainingHealth -= dmg;
			totalDamage += dmg;
			if (dmg > 0)
				thisIsAnAttack = true;

			// Update bestiary
			if (Game.level.shouldLog(this))
				Game.level.bestiaryKnowledgeCollection.get(this.templateId).pierceResistance = true;

			if (gameObjectAttacker != null)
				if (Game.level.shouldLog(gameObjectAttacker))
					Game.level.bestiaryKnowledgeCollection.get(gameObjectAttacker.templateId).pierceDamage = true;

			offsetY += 48;
		}

		// Fire
		if (damageDealer.getEffectiveFireDamage() != 0) {

			float resistance = (this.getEffectiveFireResistance() / 100);
			float resistedDamage = damageDealer.getEffectiveFireDamage() * resistance;
			float dmg = damageDealer.getEffectiveFireDamage() - resistedDamage;
			doDamageAnimation(dmg, offsetY, DAMAGE_TYPE.FIRE, this.getEffectiveFireResistance());
			remainingHealth -= dmg;
			totalDamage += dmg;
			if (dmg > 0)
				thisIsAnAttack = true;

			// Update bestiary
			if (Game.level.shouldLog(this))
				Game.level.bestiaryKnowledgeCollection.get(this.templateId).fireResistance = true;

			if (gameObjectAttacker != null)
				if (Game.level.shouldLog(gameObjectAttacker))
					Game.level.bestiaryKnowledgeCollection.get(gameObjectAttacker.templateId).fireDamage = true;

			offsetY += 48;
		}

		// Water
		if (damageDealer.getEffectiveWaterDamage() != 0) {

			float resistance = (this.getEffectiveWaterResistance() / 100);
			float resistedDamage = damageDealer.getEffectiveWaterDamage() * resistance;
			float dmg = damageDealer.getEffectiveWaterDamage() - resistedDamage;
			doDamageAnimation(dmg, offsetY, DAMAGE_TYPE.WATER, this.getEffectiveWaterResistance());
			remainingHealth -= dmg;
			totalDamage += dmg;
			if (dmg > 0)
				thisIsAnAttack = true;

			// Update bestiary
			if (Game.level.shouldLog(this))
				Game.level.bestiaryKnowledgeCollection.get(this.templateId).waterResistance = true;

			if (gameObjectAttacker != null)
				if (Game.level.shouldLog(gameObjectAttacker))
					Game.level.bestiaryKnowledgeCollection.get(gameObjectAttacker.templateId).waterDamage = true;

			offsetY += 48;
		}

		// Electric
		if (damageDealer.getEffectiveElectricDamage() != 0) {

			float resistance = (this.getEffectiveelectricResistance() / 100);
			float resistedDamage = damageDealer.getEffectiveElectricDamage() * resistance;
			float dmg = damageDealer.getEffectiveElectricDamage() - resistedDamage;
			doDamageAnimation(dmg, offsetY, DAMAGE_TYPE.ELECTRIC, this.getEffectiveelectricResistance());
			remainingHealth -= dmg;
			totalDamage += dmg;
			if (dmg > 0)
				thisIsAnAttack = true;

			// Update bestiary
			if (Game.level.shouldLog(this))
				Game.level.bestiaryKnowledgeCollection.get(this.templateId).electricResistance = true;

			if (gameObjectAttacker != null)
				if (Game.level.shouldLog(gameObjectAttacker))
					Game.level.bestiaryKnowledgeCollection.get(gameObjectAttacker.templateId).electricDamage = true;

			offsetY += 48;
		}

		// Poison
		if (damageDealer.getEffectivePoisonDamage() != 0) {

			float resistance = (this.getEffectivePoisonResistance() / 100);
			float resistedDamage = damageDealer.getEffectivePoisonDamage() * resistance;
			float dmg = damageDealer.getEffectivePoisonDamage() - resistedDamage;
			doDamageAnimation(dmg, offsetY, DAMAGE_TYPE.POISON, this.getEffectivePoisonResistance());
			remainingHealth -= dmg;
			totalDamage += dmg;
			if (dmg > 0)
				thisIsAnAttack = true;

			// Update bestiary
			if (Game.level.shouldLog(this))
				Game.level.bestiaryKnowledgeCollection.get(this.templateId).poisonResistance = true;

			if (gameObjectAttacker != null)
				if (Game.level.shouldLog(gameObjectAttacker))
					Game.level.bestiaryKnowledgeCollection.get(gameObjectAttacker.templateId).poisonDamage = true;

			offsetY += 48;
		}

		// Bleeding
		if (damageDealer.getEffectiveBleedingDamage() != 0) {

			float resistance = (this.getEffectiveBleedingResistance() / 100);
			float resistedDamage = damageDealer.getEffectiveBleedingDamage() * resistance;
			float dmg = damageDealer.getEffectiveBleedingDamage() - resistedDamage;
			doDamageAnimation(dmg, offsetY, DAMAGE_TYPE.BLEEDING, this.getEffectiveBleedingResistance());
			remainingHealth -= dmg;
			totalDamage += dmg;
			if (dmg > 0)
				thisIsAnAttack = true;

			// Update bestiary
			if (Game.level.shouldLog(this))
				Game.level.bestiaryKnowledgeCollection.get(this.templateId).bleedingResistance = true;

			if (gameObjectAttacker != null)
				if (Game.level.shouldLog(gameObjectAttacker))
					Game.level.bestiaryKnowledgeCollection.get(gameObjectAttacker.templateId).bleedingDamage = true;

			offsetY += 48;
		}

		// Healing
		if (damageDealer.getEffectiveHealing() != 0) {
			// float dmg = damageDealer.getEffectivePoisonDamage() /
			// (this.getEffectivePosionResistance() / 100);
			doDamageAnimation(damageDealer.getEffectiveHealing(), offsetY, DAMAGE_TYPE.HEALING, +200f);
			remainingHealth += damageDealer.getEffectiveHealing();
			totalDamage -= damageDealer.getEffectiveHealing();
			// thisIsAnAttack = true;

			if (gameObjectAttacker != null)
				if (Game.level.shouldLog(gameObjectAttacker))
					Game.level.bestiaryKnowledgeCollection.get(gameObjectAttacker.templateId).healing = true;

			offsetY += 48;
		}

		if (remainingHealth > totalHealth)
			remainingHealth = totalHealth;
		if (remainingHealth < 0)
			remainingHealth = 0;

		if (attacker != null && thisIsAnAttack == true) {
			if (this instanceof Actor) {
				Actor actor = (Actor) this;
				actor.attackedBy(attacker, action);
			} else {
				attackedBy(attacker, action);
			}
		}

		return totalDamage;
	}

	public enum DAMAGE_TYPE {
		SLASH, BLUNT, PIERCE, FIRE, WATER, ELECTRIC, POISON, BLEEDING, HEALING
	};

	public void doDamageAnimation(float healing, float offsetY, DAMAGE_TYPE damageType, float res) {

		Color color = Color.WHITE;

		// Heal
		if (res > 100)
			color = color.GREEN;

		// bad hit
		else if (res > 50)
			color = color.DARK_GRAY;
		else if (res > 0)
			color = color.LIGHT_GRAY;

		// good hit
		else if (res < -50)

			color = color.RED;
		else if (res < 0)
			color = color.ORANGE;

		// TYPES
		// CRIT large red, HIGH DMG red, NORMAL DMG white, resisted DMG grey,
		// HEAL green

		int x = (int) (squareGameObjectIsOn.xInGridPixels + Game.SQUARE_WIDTH * drawOffsetRatioX);
		int y = (int) (squareGameObjectIsOn.yInGridPixels + Game.SQUARE_HEIGHT * drawOffsetRatioY);

		this.secondaryAnimations
				.add(new AnimationDamageText((int) healing, this, x + 32, y - 64 + offsetY, 0.1f, damageType, color));
	}
}
