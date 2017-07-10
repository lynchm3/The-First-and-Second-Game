package com.marklynch.objects;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import java.util.ArrayList;
import java.util.UUID;
import java.util.Vector;

import org.lwjgl.util.Point;
import org.newdawn.slick.openal.Audio;

import com.marklynch.Game;
import com.marklynch.level.Square;
import com.marklynch.level.constructs.Group;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.conversation.Conversation;
import com.marklynch.level.quest.Quest;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actions.ActionAttack;
import com.marklynch.objects.actions.ActionBurn;
import com.marklynch.objects.actions.ActionDrop;
import com.marklynch.objects.actions.ActionLootAll;
import com.marklynch.objects.actions.ActionPickUp;
import com.marklynch.objects.actions.ActionTake;
import com.marklynch.objects.actions.ActionThrow;
import com.marklynch.objects.actions.ActionableInInventory;
import com.marklynch.objects.actions.ActionableInWorld;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.weapons.Weapon;
import com.marklynch.ui.ActivityLog;
import com.marklynch.utils.ArrayUtils;
import com.marklynch.utils.ResourceUtils;
import com.marklynch.utils.TextureUtils;

import mdesl.graphics.Color;
import mdesl.graphics.Texture;

public class GameObject extends GameObjectTemplate implements ActionableInWorld, ActionableInInventory, Comparable {

	public final static String[] editableAttributes = { "name", "imageTexture", "totalHealth", "remainingHealth",
			"owner", "inventory", "showInventory", "canShareSquare", "fitsInInventory", "canContainOtherObjects" };
	public String guid = UUID.randomUUID().toString();

	// attributes
	public float remainingHealth = 0;
	boolean favourite = false;
	public transient boolean hasAttackedThisTurn = false;

	// images
	public static transient Texture powTexture = null;
	public static transient Texture vsTexture = null;
	public transient Texture fightTexture = null;
	public transient Texture skullTexture = null;
	public transient Texture xTexture = null;
	public transient Texture upTexture = null;
	public transient Texture downTexture = null;
	public transient Texture armTexture = null;
	public static transient Texture grassNormalTexture = null;
	public static transient Texture skipNormalTexture = null;

	// sounds
	public static transient Audio screamAudio = null;

	// POW
	public transient GameObject powTarget = null;
	public transient boolean showPow = false;

	// Placement in inventory
	public transient Inventory inventoryThatHoldsThisObject;
	public transient InventorySquare inventorySquareGameObjectIsOn;

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
	private ArrayList<Effect> activeEffectsOnGameObject = new ArrayList<Effect>();

	public boolean hiding = false;
	public HidingPlace hidingPlace = null;

	public transient ArrayList<GameObject> attackers = new ArrayList<GameObject>();

	public transient Group group;

	public GameObject(String name, int health, String imagePath, Square squareGameObjectIsOn, Inventory inventory,
			boolean showInventory, boolean canShareSquare, boolean fitsInInventory, boolean canContainOtherObjects,
			boolean blocksLineOfSight, boolean persistsWhenCantBeSeen, boolean attackable, float widthRatio,
			float heightRatio, float drawOffsetX, float drawOffsetY, float soundWhenHit, float soundWhenHitting,
			float soundDampening, Color light, float lightHandleX, float lightHandlY, boolean stackable,
			float fireResistance, float iceResistance, float electricResistance, float poisonResistance, float weight,
			Actor owner) {

		super(name, health, imagePath, squareGameObjectIsOn, inventory, showInventory, canShareSquare, fitsInInventory,
				canContainOtherObjects, blocksLineOfSight, persistsWhenCantBeSeen, attackable, widthRatio, heightRatio,
				drawOffsetX, drawOffsetY, soundWhenHit, soundWhenHitting, soundDampening, light, lightHandleX,
				lightHandlY, stackable, fireResistance, iceResistance, electricResistance, poisonResistance, weight);
		this.remainingHealth = health;
		this.owner = owner;
		this.drawOffsetX = drawOffsetX;
		this.drawOffsetY = drawOffsetY;

		if (squareGameObjectIsOn != null) {
			this.squareGameObjectIsOn = squareGameObjectIsOn;
			this.squareGameObjectIsOn.inventory.add(this);
		}

		loadImages();

		width = Game.SQUARE_WIDTH * widthRatio;
		height = Game.SQUARE_HEIGHT * heightRatio;
		halfWidth = width / 2;
		halfHeight = height / 2;

		if (widthRatio < 1f && heightRatio < 1f) {
			float drawOffsetXMax = Game.SQUARE_WIDTH - width;
			float drawOffsetYMax = Game.SQUARE_HEIGHT - height;
			drawOffsetX = (float) (Math.random() * drawOffsetXMax);
			drawOffsetY = (float) (Math.random() * drawOffsetYMax);
		} else {
			if (this instanceof Weapon)
				drawOffsetX = Game.HALF_SQUARE_WIDTH - width / 2;
			drawOffsetY = Game.SQUARE_HEIGHT - height;
		}
	}

	@Override
	public void postLoad1() {
		super.postLoad1();
		// paths = new HashMap<Square, Path>();
		if (squareGameObjectIsOn != null) {
			this.squareGameObjectIsOn.inventory.add(this);
		}

		loadImages();
	}

	@Override
	public void postLoad2() {
		super.postLoad2();
	}

	@Override
	public void loadImages() {
		super.loadImages();
		this.powTexture = getGlobalImage("pow.png");
		this.vsTexture = getGlobalImage("vs.png");
		this.fightTexture = getGlobalImage("fight.png");
		this.skullTexture = getGlobalImage("skull.png");
		this.xTexture = getGlobalImage("x.png");
		this.upTexture = getGlobalImage("up.png");
		this.downTexture = getGlobalImage("down.png");
		this.armTexture = getGlobalImage("arm.png");
		grassNormalTexture = getGlobalImage("grass_NRM.png");
		skipNormalTexture = getGlobalImage("skip_with_shadow_NRM.png");
		screamAudio = ResourceUtils.getGlobalSound("scream.wav");
		inventory.loadImages();

	}

	public void draw1() {

		if (this.remainingHealth <= 0)
			return;

		if (!Game.fullVisiblity) {
			if (this.squareGameObjectIsOn.visibleToPlayer == false && persistsWhenCantBeSeen == false)
				return;

			if (!this.squareGameObjectIsOn.seenByPlayer)
				return;
		}

		// Draw object
		if (squareGameObjectIsOn != null) {

			int actorPositionXInPixels = (int) (this.squareGameObjectIsOn.xInGrid * (int) Game.SQUARE_WIDTH
					+ drawOffsetX);
			int actorPositionYInPixels = (int) (this.squareGameObjectIsOn.yInGrid * (int) Game.SQUARE_HEIGHT
					+ drawOffsetY);

			float alpha = 1.0f;

			// TextureUtils.skipNormals = true;

			if (!this.squareGameObjectIsOn.visibleToPlayer)
				alpha = 0.5f;
			if (hiding)
				alpha = 0.5f;

			TextureUtils.drawTexture(imageTexture, alpha, actorPositionXInPixels, actorPositionXInPixels + width,
					actorPositionYInPixels, actorPositionYInPixels + height, backwards);
			// TextureUtils.skipNormals = false;
		}
	}

	public void draw2() {
		for (Effect effect : activeEffectsOnGameObject) {
			effect.draw2();
		}
	}

	public void draw3() {

	}

	public void drawUI() {

		// Draw POW

		if (squareGameObjectIsOn != null) {
			if (showPow == true) {
				int powPositionXInPixels = Math.abs((powTarget.squareGameObjectIsOn.xInGrid * (int) Game.SQUARE_WIDTH));
				int powPositionYInPixels = powTarget.squareGameObjectIsOn.yInGrid * (int) Game.SQUARE_HEIGHT;

				TextureUtils.drawTexture(this.powTexture, powPositionXInPixels,
						powPositionXInPixels + Game.SQUARE_WIDTH, powPositionYInPixels,
						powPositionYInPixels + Game.SQUARE_HEIGHT);

			}
		}
	}

	public void drawStaticUI() {

		// inventory.drawStaticUI();

	}

	public boolean checkIfDestroyed(GameObject attacker) {
		if (remainingHealth <= 0) {
			this.canShareSquare = true;
			this.blocksLineOfSight = false;
			persistsWhenCantBeSeen = false;
			soundDampening = 1;
			this.activeEffectsOnGameObject.clear();
			return true;
		}
		return false;
	}

	public Vector<Square> getAllSquaresAtDistance(float distance) {
		Vector<Square> squares = new Vector<Square>();
		if (distance == 0) {
			squares.addElement(this.squareGameObjectIsOn);
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

	public ArrayList<Square> getAllSquaresWithinDistance(float maxDistance) {
		ArrayList<Square> squares = new ArrayList<Square>();

		for (int distance = 0; distance <= maxDistance; distance++) {

			if (distance == 0)

			{
				squares.add(this.squareGameObjectIsOn);
				continue;
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
		}

		return squares;
	}

	public ArrayList<Point> getAllCoordinatesAtDistance(int distance) {
		ArrayList<Point> coordinates = new ArrayList<Point>();
		if (distance == 0) {
			coordinates.add(new Point(this.squareGameObjectIsOn.xInGrid, this.squareGameObjectIsOn.yInGrid));
			return coordinates;
		}

		boolean xGoingUp = true;
		boolean yGoingUp = true;
		for (int i = 0, x = -distance, y = 0; i < distance * 4; i++) {
			coordinates.add(new Point(this.squareGameObjectIsOn.xInGrid + x, this.squareGameObjectIsOn.yInGrid + y));

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

	public void showPow(GameObject target) {
		powTarget = target;
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

	@Override
	public GameObject makeCopy(Square square, Actor owner) {
		return new GameObject(new String(name), (int) totalHealth, imageTexturePath, square, inventory.makeCopy(),
				showInventory, canShareSquare, fitsInInventory, canContainOtherObjects, blocksLineOfSight,
				persistsWhenCantBeSeen, true, widthRatio, heightRatio, drawOffsetX, drawOffsetY, soundWhenHit,
				soundWhenHitting, soundDampening, light, lightHandleX, lightHandlY, stackable, fireResistance,
				iceResistance, electricResistance, poisonResistance, weight, owner);
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
		if (this.remainingHealth > 0) {
			activateEffects();
		}
	}

	public void updateRealtime(int delta) {

	}

	public float getCenterX() {
		return squareGameObjectIsOn.xInGrid * Game.SQUARE_WIDTH + Game.HALF_SQUARE_WIDTH;
	}

	public float getCenterY() {
		return squareGameObjectIsOn.yInGrid * Game.SQUARE_HEIGHT + Game.HALF_SQUARE_HEIGHT;
	}

	@Override
	public Action getDefaultActionPerformedOnThisInWorld(Actor performer) {
		return null;
	}

	@Override
	public ArrayList<Action> getAllActionsPerformedOnThisInWorld(Actor performer) {
		ArrayList<Action> actions = new ArrayList<Action>();

		if (this.remainingHealth <= 0)
			return actions;

		if (!(this instanceof Actor))
			actions.add(new ActionPickUp(performer, this));
		// public boolean showInventory;
		// public boolean canShareSquare;
		if (fitsInInventory) {
			actions.add(new ActionTake(performer, this));
		}
		if (!(this instanceof Actor) && canContainOtherObjects && this.inventory.size() > 0) {
			actions.add(new ActionLootAll(performer, this));
		}

		// Here put view loot

		// if (Game.level.activeActor != null &&
		// Game.level.activeActor.equippedWeapon != null
		// && Game.level.activeActor.equippedWeapon
		// .hasRange(Game.level.activeActor.straightLineDistanceTo(this.squareGameObjectIsOn)))
		// {
		if (this != Game.level.player && attackable)
			actions.add(new ActionAttack(performer, this));
		// }

		if (performer.equipped != null) {
			actions.add(new ActionThrow(performer, this, performer.equipped));
		}

		actions.add(new ActionBurn(performer, this));

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

		return straightLineDistanceBetween(this.squareGameObjectIsOn, square);
	}

	@Override
	public int compareTo(Object otherObject) {

		if (otherObject instanceof GameObject) {
			return compareGameObjectToGameObject((GameObject) otherObject);
		}

		if (Inventory.inventorySortBy == Inventory.INVENTORY_SORT_BY.SORT_BY_SLASH_DAMAGE) {
			if (!(this instanceof Weapon) && !(otherObject instanceof Weapon)) {
				return 0;
			} else if (!(this instanceof Weapon)) {
				return +1;
			} else if (!(otherObject instanceof Weapon)) {
				return -1;
			} else {
				return Math.round((((Weapon) otherObject).slashDamage - ((Weapon) this).slashDamage));
			}
		}
		return 0;
	}

	public int compareGameObjectToGameObject(GameObject otherGameObject) {

		if (Inventory.inventorySortBy == Inventory.INVENTORY_SORT_BY.SORT_ALPHABETICALLY) {
			return this.name.compareToIgnoreCase(otherGameObject.name);
		}

		if (Inventory.inventorySortBy == Inventory.INVENTORY_SORT_BY.SORT_BY_NEWEST) {
			return otherGameObject.pickUpdateDateTime.compareTo(this.pickUpdateDateTime);
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
	public Action getDefaultActionInInventory(Actor performer) {
		return new ActionDrop(performer, performer.squareGameObjectIsOn, this);
	}

	@Override
	public ArrayList<Action> getAllActionsInInventory(Actor performer) {
		ArrayList<Action> actions = new ArrayList<Action>();
		actions.add(new ActionDrop(performer, performer.squareGameObjectIsOn, this));
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

	public void attackedBy(Object attacker) {

		GameObject gameObjectAttacker = null;
		if (attacker instanceof GameObject) {
			gameObjectAttacker = (GameObject) attacker;
		}

		if (checkIfDestroyed(gameObjectAttacker)) {
			if (this instanceof Actor) {
				if (this.squareGameObjectIsOn.visibleToPlayer)
					Game.level.logOnScreen(new ActivityLog(new Object[] { attacker, " killed ", this }));
				((Actor) this).faction.checkIfDestroyed();
			} else {
				if (this.squareGameObjectIsOn.visibleToPlayer)
					Game.level.logOnScreen(new ActivityLog(new Object[] { attacker, " destroyed a ", this }));
			}
		}
	}

	public void addEffect(Effect effectToAdd) {

		if (remainingHealth <= 0)
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
		Game.level.logOnScreen(new ActivityLog(new Object[] { this, effectToAdd.logString, effectToAdd.source }));
	}

	public void removeEffect(Effect effect) {
		this.activeEffectsOnGameObject.remove(effect);
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

	public void landed(Actor shooter) {

	}

	public void addAttackerForThisAndGroupMembers(GameObject attacker) {

		if (this == attacker)
			return;

		if (!attacker.attackers.contains(this)) {
			attacker.attackers.add(this);
		}

		if (!this.attackers.contains(attacker)) {
			this.attackers.add(attacker);
		}

		if (this.group != null) {
			if (!this.group.getAttackers().contains(attacker)) {
				this.group.addAttacker(attacker);
			}
			for (Actor groupMember : this.group.getMembers()) {
				if (!groupMember.attackers.contains(attacker)) {
					groupMember.attackers.add(attacker);
				}
				if (!attacker.attackers.contains(groupMember)) {
					attacker.attackers.add(groupMember);
				}
			}
		}

		if (attacker.group != null) {
			if (!attacker.group.getAttackers().contains(this)) {
				attacker.group.addAttacker(this);
			}
			for (Actor groupMember : attacker.group.getMembers()) {
				if (!groupMember.attackers.contains(this)) {
					groupMember.attackers.add(this);
				}
				if (!this.attackers.contains(groupMember)) {
					this.attackers.add(groupMember);
				}
			}
		}

	}

	public ArrayList<Effect> getActiveEffectsOnGameObject() {
		return activeEffectsOnGameObject;
	}
}
