package com.marklynch.objects;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.Vector;

import org.newdawn.slick.openal.Audio;

import com.marklynch.Game;
import com.marklynch.level.Square;
import com.marklynch.level.constructs.Quest;
import com.marklynch.level.conversation.Conversation;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actions.ActionAttack;
import com.marklynch.objects.actions.ActionLootAll;
import com.marklynch.objects.actions.ActionPickUp;
import com.marklynch.objects.actions.Actionable;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.Path;
import com.marklynch.objects.weapons.Weapon;
import com.marklynch.utils.ArrayUtils;
import com.marklynch.utils.ResourceUtils;
import com.marklynch.utils.TextureUtils;

import mdesl.graphics.Texture;

public class GameObject extends GameObjectTemplate implements Actionable {

	public final static String[] editableAttributes = { "name", "imageTexture", "totalHealth", "remainingHealth",
			"owner", "inventory", "showInventory", "canShareSquare", "fitsInInventory", "canContainOtherObjects" };
	public String guid = UUID.randomUUID().toString();

	// attributes
	public float remainingHealth = 0;

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

	// paths
	public transient HashMap<Square, Path> paths = new HashMap<Square, Path>();

	// POW
	public transient GameObject powTarget = null;
	public transient boolean showPow = false;

	// Placement in inventory
	public transient Inventory inventoryThatHoldsThisObject;
	public transient InventorySquare inventorySquareGameObjectIsOn;

	// Quest
	public transient Quest quest;

	float height;
	float width;
	float drawOffsetX;
	float drawOffsetY;

	public GameObject(String name, int health, String imagePath, Square squareGameObjectIsOn, Inventory inventory,
			boolean showInventory, boolean canShareSquare, boolean fitsInInventory, boolean canContainOtherObjects,
			float widthRatio, float heightRatio) {

		super(name, health, imagePath, squareGameObjectIsOn, inventory, showInventory, canShareSquare, fitsInInventory,
				canContainOtherObjects, widthRatio, heightRatio);
		this.remainingHealth = health;

		if (squareGameObjectIsOn != null) {
			this.squareGameObjectIsOn = squareGameObjectIsOn;
			this.squareGameObjectIsOn.inventory.add(this);
		}

		loadImages();

		width = Game.SQUARE_WIDTH * widthRatio;
		height = Game.SQUARE_HEIGHT * heightRatio;

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
		paths = new HashMap<Square, Path>();
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

		// Draw object
		if (squareGameObjectIsOn != null) {
			int actorPositionXInPixels = (int) (this.squareGameObjectIsOn.xInGrid * (int) Game.SQUARE_WIDTH
					+ drawOffsetX);
			int actorPositionYInPixels = (int) (this.squareGameObjectIsOn.yInGrid * (int) Game.SQUARE_HEIGHT
					+ drawOffsetY);

			float alpha = 1.0f;

			// TextureUtils.skipNormals = true;

			TextureUtils.drawTexture(imageTexture, alpha, actorPositionXInPixels, actorPositionXInPixels + width,
					actorPositionYInPixels, actorPositionYInPixels + height);
			// TextureUtils.skipNormals = false;
		}
	}

	public void draw2() {

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

	public boolean checkIfDestroyed() {
		if (remainingHealth <= 0) {

			this.canShareSquare = true;

			// if (squareGameObjectIsOn != null) {
			// this.squareGameObjectIsOn.inventory.remove(this);
			// }
			// Game.level.inanimateObjects.remove(this);

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
				// TODO Auto-generated catch block
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
				if (range >= weapon.minRange && range <= weapon.maxRange) {
					return weapon;
				}
			}
		}
		return null;
	}

	@Override
	public GameObject makeCopy(Square square) {
		return new GameObject(new String(name), (int) totalHealth, imageTexturePath, square, inventory.makeCopy(),
				showInventory, canShareSquare, fitsInInventory, canContainOtherObjects, widthRatio, heightRatio);
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

	}

	public float getCenterX() {
		return squareGameObjectIsOn.xInGrid * Game.SQUARE_WIDTH + Game.HALF_SQUARE_WIDTH;
	}

	public float getCenterY() {
		return squareGameObjectIsOn.yInGrid * Game.SQUARE_HEIGHT + Game.HALF_SQUARE_HEIGHT;
	}

	@Override
	public Action getDefaultAction(Actor performer) {
		return null;
	}

	@Override
	public ArrayList<Action> getAllActions(Actor performer) {
		ArrayList<Action> actions = new ArrayList<Action>();

		// public boolean showInventory;
		// public boolean canShareSquare;
		if (fitsInInventory) {
			actions.add(new ActionPickUp(performer, this));
		}
		if (canContainOtherObjects && this.inventory.size() > 0) {
			actions.add(new ActionLootAll(performer, this));
		}

		// Here put view loot

		// if (Game.level.activeActor != null &&
		// Game.level.activeActor.equippedWeapon != null
		// && Game.level.activeActor.equippedWeapon
		// .hasRange(Game.level.activeActor.straightLineDistanceTo(this.squareGameObjectIsOn)))
		// {
		actions.add(new ActionAttack(performer, this));
		// }
		return actions;

	}

	public Conversation getConversation() {
		return null;
	}
}
