package com.marklynch.level.squares;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

import org.lwjgl.input.Keyboard;

import com.marklynch.Game;
import com.marklynch.ai.utils.AStarNode;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.bounds.Area;
import com.marklynch.level.constructs.bounds.structure.Structure;
import com.marklynch.level.constructs.bounds.structure.StructureRoom;
import com.marklynch.level.constructs.bounds.structure.StructureSection;
import com.marklynch.level.constructs.power.Power;
import com.marklynch.objects.BrokenGlass;
import com.marklynch.objects.Door;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.HidingPlace;
import com.marklynch.objects.InventoryParent;
import com.marklynch.objects.SquareInventory;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actions.ActionAttack;
import com.marklynch.objects.actions.ActionDropItemsInInventory;
import com.marklynch.objects.actions.ActionHide;
import com.marklynch.objects.actions.ActionMove;
import com.marklynch.objects.actions.ActionPlaceMapMarker;
import com.marklynch.objects.actions.ActionPourContainerInInventory;
import com.marklynch.objects.actions.ActionStopHiding;
import com.marklynch.objects.actions.ActionTakeAll;
import com.marklynch.objects.actions.ActionTeleport;
import com.marklynch.objects.actions.ActionThrowItemInInventory;
import com.marklynch.objects.actions.ActionWait;
import com.marklynch.objects.actions.ActionableInWorld;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.weapons.Weapon;
import com.marklynch.utils.ArrayUtils;
import com.marklynch.utils.ResourceUtils;
import com.marklynch.utils.TextureUtils;

import mdesl.graphics.Color;
import mdesl.graphics.Texture;

public class Square extends AStarNode implements ActionableInWorld, InventoryParent {

	public String guid = UUID.randomUUID().toString();
	public final static String[] editableAttributes = { "elevation", "travelCost", "imageTexture" };

	public final int xInGrid;
	public final int yInGrid;
	public final int elevation;
	public int travelCost;
	public SquareInventory inventory;
	public boolean showInventory;

	// public transient boolean reachableBySelectedCharater = false;
	public transient boolean visibleToSelectedCharacter = false;
	public transient boolean visibleToPlayer = false;
	public transient boolean seenByPlayer = false;
	public transient boolean inPath = false;
	public transient Vector<Weapon> weaponsThatCanAttack;

	// image
	public String imageTexturePath;
	public transient Texture imageTexture = null;
	public static Texture GRASS_TEXTURE;
	public static Texture DARK_GRASS_TEXTURE;
	public static Texture STONE_TEXTURE;
	public static Texture MUD_TEXTURE;
	public static Texture BLANK_TEXTURE;
	public static Texture W_TEXTURE;
	public static Texture A_TEXTURE;
	public static Texture S_TEXTURE;
	public static Texture D_TEXTURE;
	public static Texture SHIFT_W_TEXTURE;
	public static Texture SHIFT_A_TEXTURE;
	public static Texture SHIFT_S_TEXTURE;
	public static Texture SHIFT_D_TEXTURE;
	public static Texture ATTACK_LEFT_BORDER_TEXTURE;
	public static Texture ATTACK_RIGHT_BORDER_TEXTURE;
	public static Texture ATTACK_TOP_BORDER_TEXTURE;
	public static Texture ATTACK_BOTTOM_BORDER_TEXTURE;
	public static Texture SOUND_TEXTURE;

	public transient boolean showingDialogs = false;
	// public transient int walkingDistanceToSquare = Integer.MAX_VALUE;

	public transient static PathComparator pathComparator;

	public transient Area areaSquareIsIn;
	public transient Structure structureSquareIsIn;
	public transient StructureSection structureSectionSquareIsIn;
	public transient StructureRoom structureRoomSquareIsIn;

	public ArrayList<Sound> sounds = new ArrayList<Sound>();

	public boolean highlight = false;

	public ArrayList<Actor> owners;
	public boolean restricted;

	public Square(int x, int y, String imagePath, int travelCost, int elevation, SquareInventory inventory,
			boolean restricted, Actor... owners) {
		this(x, y, imagePath, null, travelCost, elevation, inventory, restricted, owners);

	}

	public Square(int x, int y, String imagePath, Texture imageTexture, int travelCost, int elevation,
			SquareInventory inventory, boolean restricted, Actor... owners) {
		super();
		this.xInGrid = x;
		this.yInGrid = y;
		this.imageTexturePath = imagePath;
		this.travelCost = travelCost;
		this.elevation = elevation;

		if (imageTexture == null)
			loadImages();
		else
			this.imageTexture = imageTexture;

		weaponsThatCanAttack = new Vector<Weapon>();
		this.inventory = inventory;
		if (this.inventory != null) {
			this.inventory.parent = this;
			this.inventory.square = this;
		}
		showInventory = true;

		this.owners = new ArrayList<Actor>();
		for (Actor owner : owners) {
			this.owners.add(owner);
		}
		this.restricted = restricted;

	}

	public static void loadStaticImages() {
		GRASS_TEXTURE = ResourceUtils.getGlobalImage("grass.png");
		DARK_GRASS_TEXTURE = ResourceUtils.getGlobalImage("dark_grass.png");
		STONE_TEXTURE = ResourceUtils.getGlobalImage("stone.png");
		MUD_TEXTURE = ResourceUtils.getGlobalImage("mud.png");
		BLANK_TEXTURE = ResourceUtils.getGlobalImage("square.png");

		W_TEXTURE = ResourceUtils.getGlobalImage("w.png");
		A_TEXTURE = ResourceUtils.getGlobalImage("a.png");
		S_TEXTURE = ResourceUtils.getGlobalImage("s.png");
		D_TEXTURE = ResourceUtils.getGlobalImage("d.png");
		SHIFT_W_TEXTURE = ResourceUtils.getGlobalImage("shiftw.png");
		SHIFT_A_TEXTURE = ResourceUtils.getGlobalImage("shifta.png");
		SHIFT_S_TEXTURE = ResourceUtils.getGlobalImage("shifts.png");
		SHIFT_D_TEXTURE = ResourceUtils.getGlobalImage("shiftd.png");

		ATTACK_LEFT_BORDER_TEXTURE = ResourceUtils.getGlobalImage("attack_left_border.png");
		ATTACK_RIGHT_BORDER_TEXTURE = ResourceUtils.getGlobalImage("attack_right_border.png");
		ATTACK_TOP_BORDER_TEXTURE = ResourceUtils.getGlobalImage("attack_top_border.png");
		ATTACK_BOTTOM_BORDER_TEXTURE = ResourceUtils.getGlobalImage("attack_bottom_border.png");

		SOUND_TEXTURE = ResourceUtils.getGlobalImage("sound.png");
	}

	public void postLoad1() {
		inventory.square = this;
		inventory.postLoad1();
		weaponsThatCanAttack = new Vector<Weapon>();

		loadImages();
	}

	public void postLoad2() {
		inventory.postLoad2();
	}

	public void loadImages() {
		this.imageTexture = getGlobalImage(imageTexturePath);

	}

	public void draw1() {

		Texture textureToDraw = this.imageTexture;

		if (!Game.fullVisiblity) {

			if (!this.seenByPlayer) {
				textureToDraw = BLANK_TEXTURE;
			}
		}

		// square texture
		int squarePositionX = xInGrid * (int) Game.SQUARE_WIDTH;
		int squarePositionY = yInGrid * (int) Game.SQUARE_HEIGHT;
		// QuadUtils.drawQuad(new Color(0.2f, 0.4f, 0.1f, 1.0f),
		// squarePositionX, squarePositionX + Game.SQUARE_WIDTH,
		// squarePositionY, squarePositionY + Game.SQUARE_HEIGHT);
		float alpha = 1f;
		if (!this.visibleToPlayer)
			alpha = 0.5f;
		TextureUtils.drawTexture(textureToDraw, alpha, squarePositionX, squarePositionY,
				squarePositionX + Game.SQUARE_WIDTH, squarePositionY + Game.SQUARE_HEIGHT);

		if (restricted && Game.redHighlightOnRestrictedSquares) {
			drawRedHighlight();
		}

		// square highlights

		// if (reachableBySelectedCharater || weaponsThatCanAttack.size() > 0) {
		//
		// Texture highlightTexture = null;
		//
		// if (Game.level.activeActor != null &&
		// Game.level.activeActor.equippedWeapon != null
		// && this.inventory.size() != 0
		// && Game.level.activeActor.equippedWeapon
		// .hasRange(Game.level.activeActor.straightLineDistanceTo(this))
		// && !Game.level.activeActor.hasAttackedThisTurn) {
		// highlightTexture = Game.level.gameCursor.imageTexture4;
		// } else if (Game.level.currentFactionMovingIndex == 0 && (inPath))//
		// ||
		// // this
		// // ==
		// // Game.squareMouseIsOver))
		// highlightTexture = Game.level.gameCursor.imageTexture3;
		// else if (reachableBySelectedCharater)
		// highlightTexture = Game.level.gameCursor.imageTexture;
		// else
		// highlightTexture = Game.level.gameCursor.imageTexture2;
		// TextureUtils.drawTexture(highlightTexture, squarePositionX,
		// squarePositionX + Game.SQUARE_WIDTH,
		// squarePositionY, squarePositionY + Game.SQUARE_HEIGHT);
		// }

		// if (this.reachableBySelectedCharater) {
		// int costTextWidth = Game.font.getWidth("" + walkingDistanceToSquare);
		// float costPositionX = squarePositionX + (Game.SQUARE_WIDTH -
		// costTextWidth) / 2f;
		// float costPositionY = squarePositionY + (Game.SQUARE_HEIGHT - 60) /
		// 2f;

		// if (walkingDistanceToSquare != Integer.MAX_VALUE &&
		// Game.level.activeActor != null) {
		// TextUtils.printTextWithImages(new Object[] { "" +
		// walkingDistanceToSquare }, costPositionX, costPositionY,
		// Integer.MAX_VALUE, true);
		// }
		// GL11.glColor3f(1.0f, 1.0f, 1.0f);

		// }

		// draw weapon icons on square
		// if (this.gameObject != null
		// && level.currentFactionMoving == level.factions.get(0)) {
		// float weaponWidthInPixels = Game.SQUARE_WIDTH / 5;
		// float weaponHeightInPixels = Game.SQUARE_HEIGHT / 5;
		// for (int i = 0; i < this.weaponsThatCanAttack.size(); i++) {
		//
		// Weapon weapon = this.weaponsThatCanAttack.get(i);
		// float weaponPositionXInPixels = 0;
		// float weaponPositionYInPixels = 0;
		//
		// weaponPositionXInPixels = this.x * (int) Game.SQUARE_WIDTH;
		// weaponPositionYInPixels = this.y * (int) Game.SQUARE_HEIGHT
		// + (i * weaponHeightInPixels);
		//
		// TextureUtils.drawTexture(weapon.imageTexture,
		// weaponPositionXInPixels, weaponPositionXInPixels
		// + weaponWidthInPixels, weaponPositionYInPixels,
		// weaponPositionYInPixels + weaponHeightInPixels);
		// }
		// }
		if (highlight) {
			drawHighlight();
		}

	}

	public void drawHighlight() {

		int squarePositionX = xInGrid * (int) Game.SQUARE_WIDTH;
		int squarePositionY = yInGrid * (int) Game.SQUARE_HEIGHT;
		TextureUtils.drawTexture(Game.level.gameCursor.imageTexture2, squarePositionX, squarePositionY,
				squarePositionX + Game.SQUARE_WIDTH, squarePositionY + Game.SQUARE_HEIGHT);

	}

	public void drawAttackHighlight(ArrayList<Square> attackableSquares) {

		int squarePositionX = xInGrid * (int) Game.SQUARE_WIDTH;
		int squarePositionY = yInGrid * (int) Game.SQUARE_HEIGHT;

		if (this.getSquareAbove() != null && !attackableSquares.contains(this.getSquareAbove())
				&& this.getSquareAbove() != Game.level.player.squareGameObjectIsOn) {
			TextureUtils.drawTexture(ATTACK_TOP_BORDER_TEXTURE, squarePositionX, squarePositionY,
					squarePositionX + Game.SQUARE_WIDTH, squarePositionY + Game.SQUARE_HEIGHT);
		}

		if (this.getSquareBelow() != null && !attackableSquares.contains(this.getSquareBelow())
				&& this.getSquareBelow() != Game.level.player.squareGameObjectIsOn) {
			TextureUtils.drawTexture(ATTACK_BOTTOM_BORDER_TEXTURE, squarePositionX, squarePositionY,
					squarePositionX + Game.SQUARE_WIDTH, squarePositionY + Game.SQUARE_HEIGHT);
		}

		if (this.getSquareToLeftOf() != null && !attackableSquares.contains(this.getSquareToLeftOf())
				&& this.getSquareToLeftOf() != Game.level.player.squareGameObjectIsOn) {
			TextureUtils.drawTexture(ATTACK_LEFT_BORDER_TEXTURE, squarePositionX, squarePositionY,
					squarePositionX + Game.SQUARE_WIDTH, squarePositionY + Game.SQUARE_HEIGHT);
		}

		if (this.getSquareToRightOf() != null && !attackableSquares.contains(this.getSquareToRightOf())
				&& this.getSquareToRightOf() != Game.level.player.squareGameObjectIsOn) {
			TextureUtils.drawTexture(ATTACK_RIGHT_BORDER_TEXTURE, squarePositionX, squarePositionY,
					squarePositionX + Game.SQUARE_WIDTH, squarePositionY + Game.SQUARE_HEIGHT);
		}

	}

	public void drawSoundHighlight2() {

		int squarePositionX = xInGrid * (int) Game.SQUARE_WIDTH;
		int squarePositionY = yInGrid * (int) Game.SQUARE_HEIGHT;

		TextureUtils.drawTexture(Game.level.gameCursor.imageTexture2, squarePositionX, squarePositionY,
				squarePositionX + Game.SQUARE_WIDTH, squarePositionY + Game.SQUARE_HEIGHT);

		float distanceX = Math.abs(this.xInGrid - Game.level.player.squareGameObjectIsOn.xInGrid);
		float u1 = (distanceX + 10) * 0.09f;
		float u2 = (distanceX + 10) * 0.09f + 0.09f;

		float distanceY = Math.abs(this.yInGrid - Game.level.player.squareGameObjectIsOn.yInGrid);
		float v1 = (distanceY + 10) * 0.09f;
		float v2 = (distanceY + 10) * 0.09f + 0.09f;

		// if (this.xInGrid > Game.level.player.squareGameObjectIsOn.xInGrid) {
		// float temp = u1;
		// u1 = u2;
		// u2 = temp;
		// }
		//
		// if (this.yInGrid > Game.level.player.squareGameObjectIsOn.yInGrid) {
		// float temp = v1;
		// v1 = v2;
		// v2 = temp;
		// }

		TextureUtils.drawTexture(Square.SOUND_TEXTURE, squarePositionX, squarePositionY,
				squarePositionX + Game.SQUARE_WIDTH, squarePositionY + Game.SQUARE_HEIGHT, u1, v1, u2, v2);
	}

	public void drawSoundHighlight() {

		int squarePositionX = xInGrid * (int) Game.SQUARE_WIDTH;
		int squarePositionY = yInGrid * (int) Game.SQUARE_HEIGHT;

		TextureUtils.drawTexture(Game.level.gameCursor.imageTexture2, squarePositionX, squarePositionY,
				squarePositionX + Game.SQUARE_WIDTH, squarePositionY + Game.SQUARE_HEIGHT);

		float distanceX = this.xInGrid - Game.level.player.squareGameObjectIsOn.xInGrid;
		float u1 = (distanceX + 11) * 0.09f;
		float u2 = (distanceX + 11) * 0.09f + 0.09f;

		float distanceY = this.yInGrid - Game.level.player.squareGameObjectIsOn.yInGrid;
		float v1 = (distanceY + 11) * 0.09f;
		float v2 = (distanceY + 11) * 0.09f + 0.09f;

		if (this.xInGrid > Game.level.player.squareGameObjectIsOn.xInGrid) {
			u1 = 1 - u1;
			u2 = 1 - u2;
		}

		if (this.yInGrid > Game.level.player.squareGameObjectIsOn.yInGrid) {
			v1 = 1 - v1;
			v2 = 1 - v2;
		}

		TextureUtils.drawTexture(Square.SOUND_TEXTURE, squarePositionX, squarePositionY,
				squarePositionX + Game.SQUARE_WIDTH, squarePositionY + Game.SQUARE_HEIGHT, u1, v1, u2, v2);
	}

	public void drawRedHighlight() {

		int squarePositionX = xInGrid * (int) Game.SQUARE_WIDTH;
		int squarePositionY = yInGrid * (int) Game.SQUARE_HEIGHT;
		TextureUtils.drawTexture(Game.level.gameCursor.imageTexture4, squarePositionX, squarePositionY,
				squarePositionX + Game.SQUARE_WIDTH, squarePositionY + Game.SQUARE_HEIGHT);

	}

	public void draw2() {

		// int squarePositionX = xInGrid * (int) Game.SQUARE_WIDTH;
		// int squarePositionY = yInGrid * (int) Game.SQUARE_HEIGHT;
		//
		// if (!this.visibleToPlayer)
		// QuadUtils.drawQuad(new Color(0.5f, 0.5f, 0.5f, 0.75f),
		// squarePositionX, squarePositionX + Game.SQUARE_WIDTH,
		// squarePositionY, squarePositionY + Game.SQUARE_HEIGHT);

	}

	public void drawCursor() {
		// GL11.glPushMatrix();

		// GL11.glTranslatef(Game.windowWidth / 2, Game.windowHeight / 2, 0);
		// GL11.glScalef(Game.zoom, Game.zoom, 0);
		// GL11.glTranslatef(Game.getDragX(), Game.getDragY(), 0);
		// GL11.glTranslatef(-Game.windowWidth / 2, -Game.windowHeight / 2, 0);
		int squarePositionX = xInGrid * (int) Game.SQUARE_WIDTH;
		int squarePositionY = yInGrid * (int) Game.SQUARE_HEIGHT;

		TextureUtils.drawTexture(Game.level.gameCursor.cursor, squarePositionX, squarePositionY,
				squarePositionX + Game.SQUARE_WIDTH, squarePositionY + Game.SQUARE_HEIGHT);
		// GL11.glPopMatrix();
	}

	public Action drawAction() {

		Action action = null;
		if (Keyboard.isKeyDown(Keyboard.KEY_LMENU) || Keyboard.isKeyDown(Keyboard.KEY_RMENU)) {
			action = this.getAttackActionForTheSquareOrObject(Game.level.player);
		} else if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
			action = this.getSecondaryActionForTheSquareOrObject(Game.level.player);
		} else {
			action = this.getDefaultActionForTheSquareOrObject(Game.level.player);
		}

		// ...
		if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)) {
			int squarePositionX = xInGrid * (int) Game.SQUARE_WIDTH;
			int squarePositionY = yInGrid * (int) Game.SQUARE_HEIGHT;
			TextureUtils.drawTexture(ResourceUtils.getGlobalImage("action_select_object.png"),
					squarePositionX + Game.SQUARE_WIDTH - 64, squarePositionY + Game.SQUARE_HEIGHT - 64,
					squarePositionX + Game.SQUARE_WIDTH - 0, squarePositionY + Game.SQUARE_HEIGHT - 0);
			// specific action
		} else if (action != null && action.image != null) {

			if (action instanceof ActionMove && action.legal)
				return action;

			if (action instanceof ActionWait && action.legal)
				return action;

			Color color = Color.WHITE;
			if (!action.legal) {
				color = Color.RED;
			}

			int squarePositionX = xInGrid * (int) Game.SQUARE_WIDTH;
			int squarePositionY = yInGrid * (int) Game.SQUARE_HEIGHT;
			TextureUtils.drawTexture(action.image, squarePositionX + Game.SQUARE_WIDTH - 48,
					squarePositionY + Game.SQUARE_HEIGHT - 48, squarePositionX + Game.SQUARE_WIDTH - 16,
					squarePositionY + Game.SQUARE_HEIGHT - 16, color);
			// TextUtils.printTextWithImages(new Object[] { action },
			// squarePositionX + 16/* Game.SQUARE_WIDTH - 48 */, squarePositionY
			// + Game.SQUARE_HEIGHT - 48, 128,
			// false);
			// if (action.enabled == false) {
			// TextureUtils.drawTexture(Action.x, squarePositionX +
			// Game.SQUARE_WIDTH - 48,
			// squarePositionY + Game.SQUARE_HEIGHT - 48, squarePositionX +
			// Game.SQUARE_WIDTH - 16,
			// squarePositionY + Game.SQUARE_HEIGHT - 16, Color.RED);
			//
			// }
		}
		return action;
	}

	public void drawKey(Texture texture) {
		int squarePositionX = xInGrid * (int) Game.SQUARE_WIDTH;
		int squarePositionY = yInGrid * (int) Game.SQUARE_HEIGHT;
		TextureUtils.drawTexture(texture, squarePositionX, squarePositionY, squarePositionX + Game.SQUARE_WIDTH,
				squarePositionY + Game.SQUARE_HEIGHT);

	}

	public void drawPower(Power power) {

		if (power != null && power.image != null) {
			int squarePositionX = xInGrid * (int) Game.SQUARE_WIDTH;
			int squarePositionY = yInGrid * (int) Game.SQUARE_HEIGHT;
			TextureUtils.drawTexture(power.image, squarePositionX + Game.QUARTER_SQUARE_WIDTH,
					squarePositionY + Game.QUARTER_SQUARE_WIDTH,
					squarePositionX + Game.SQUARE_WIDTH - Game.QUARTER_SQUARE_WIDTH,
					squarePositionY + Game.SQUARE_HEIGHT - Game.QUARTER_SQUARE_WIDTH);
		}
	}

	public void drawX() {

		int squarePositionX = xInGrid * (int) Game.SQUARE_WIDTH;
		int squarePositionY = yInGrid * (int) Game.SQUARE_HEIGHT;
		TextureUtils.drawTexture(Action.x, squarePositionX + Game.QUARTER_SQUARE_WIDTH,
				squarePositionY + Game.QUARTER_SQUARE_WIDTH,
				squarePositionX + Game.SQUARE_WIDTH - Game.QUARTER_SQUARE_WIDTH,
				squarePositionY + Game.SQUARE_HEIGHT - Game.QUARTER_SQUARE_WIDTH);
	}

	public class PathComparator implements Comparator<Vector<Square>> {

		@Override
		public int compare(Vector<Square> path1, Vector<Square> path2) {

			int path1Distance = 0;
			for (Square squareInPath : path1)
				path1Distance += squareInPath.travelCost;

			int path2Distance = 0;
			for (Square squareInPath : path2)
				path2Distance += squareInPath.travelCost;

			if (path1Distance < path2Distance)
				return -1;
			if (path1Distance > path2Distance)
				return 1;
			return 0;
		}

	}

	@Override
	public String toString() {
		return "" + this.xInGrid + "," + this.yInGrid;

	}

	public Action getDefaultActionForTheSquareOrObject(Actor performer) {
		// GameObject targetGameObject =
		// this.inventory.getGameObjectThatCantShareSquare();
		// if (targetGameObject != null) {
		// return
		// targetGameObject.getDefaultActionPerformedOnThisInWorld(performer);
		// } else {
		return getDefaultActionPerformedOnThisInWorld(performer);
		// }
		// return
	}

	public Action getSecondaryActionForTheSquareOrObject(Actor performer) {

		if (this.inventory.size() == 0) {
			return null;
		}

		GameObject targetGameObject = this.inventory.getGameObjectThatCantShareSquare();
		if (targetGameObject != null) {
			return targetGameObject.getSecondaryActionPerformedOnThisInWorld(performer);
		}

		if (this.inventory.size() == 1) {
			return this.inventory.get(0).getSecondaryActionPerformedOnThisInWorld(performer);
		}

		if (this.inventory.getDecorativeCount() == this.inventory.size() - 1) {
			GameObject onlyNonDecorativeObject = this.inventory.getNonDecorativeGameObject();
			if (onlyNonDecorativeObject != null)
				return onlyNonDecorativeObject.getSecondaryActionPerformedOnThisInWorld(performer);
		}

		return null;

	}

	public Action getAttackActionForTheSquareOrObject(Actor performer) {

		if (this.inventory.size() == 0) {
			return null;
		}

		GameObject targetGameObject = this.inventory.getGameObjectThatCantShareSquare();
		if (targetGameObject != null) {
			return new ActionAttack(performer, targetGameObject);
		}

		if (this.inventory.size() == 1) {
			return new ActionAttack(performer, this.inventory.get(0));
		}

		return null;
	}

	@Override
	public Action getDefaultActionPerformedOnThisInWorld(Actor performer) {

		if (this == Game.level.player.squareGameObjectIsOn) {
			return new ActionWait(performer, this);
		} else if (performer.travelDistance >= performer.straightLineDistanceTo(this)) {

			HidingPlace hidingPlace = (HidingPlace) this.inventory.getGameObjectOfClass(HidingPlace.class);
			if (hidingPlace != null && hidingPlace.remainingHealth > 0) {
				if (performer.hiding && performer.squareGameObjectIsOn == this) {
					return new ActionStopHiding(performer, hidingPlace);
				} else {
					return new ActionHide(performer, hidingPlace);
				}
			}

			return new ActionMove(performer, this, true);

		} else {
			return null;
		}

	}

	@Override
	public Action getSecondaryActionPerformedOnThisInWorld(Actor performer) {

		if (this == Game.level.player.squareGameObjectIsOn) {
			return null;
		} else if (performer.travelDistance >= performer.straightLineDistanceTo(this)) {

			HidingPlace hidingPlace = (HidingPlace) this.inventory.getGameObjectOfClass(HidingPlace.class);
			if (hidingPlace != null && hidingPlace.remainingHealth > 0) {
				if (performer.hiding && performer.squareGameObjectIsOn == this) {
					return new ActionStopHiding(performer, hidingPlace);
				} else {
					return new ActionHide(performer, hidingPlace);
				}
			}

			return new ActionMove(performer, this, true);

		} else {
			return null;
		}

	}

	@Override
	public ArrayList<Action> getAllActionsPerformedOnThisInWorld(Actor performer) {
		ArrayList<Action> actions = new ArrayList<Action>();
		// Move, teleport, loiter
		if (this != Game.level.player.squareGameObjectIsOn) {
			actions.add(new ActionMove(performer, this, true));
			actions.add(new ActionTeleport(performer, performer, this, true));
		} else {
			actions.add(new ActionWait(performer, performer.squareGameObjectIsOn));
		}

		// Take all
		if (this.inventory.size() > 0 && this.inventory.hasGameObjectsThatFitInInventory()) {
			actions.add(new ActionTakeAll(performer, this));
		}

		// hide
		HidingPlace hidingPlace = (HidingPlace) this.inventory.getGameObjectOfClass(HidingPlace.class);
		if (hidingPlace != null && hidingPlace.remainingHealth > 0) {

			if (performer.hiding && performer.squareGameObjectIsOn == this) {
				actions.add(new ActionStopHiding(performer, hidingPlace));
			} else {
				actions.add(new ActionHide(performer, hidingPlace));
			}
		}

		// drop equipped
		// if (performer.equipped != null) {
		// if (performer.straightLineDistanceTo(this) < 2) {
		// actions.add(new ActionDropSpecificItem(performer, this,
		// performer.equipped));
		// }
		// }

		// drop from inventory
		actions.add(new ActionDropItemsInInventory(performer, this));

		// Throw equipped
		// if (this != Game.level.player.squareGameObjectIsOn &&
		// performer.equipped != null) {
		// actions.add(new ActionThrowSpecificItem(performer, this,
		// performer.equipped));
		// }

		// Throw from inventory
		if (this != Game.level.player.squareGameObjectIsOn)
			actions.add(new ActionThrowItemInInventory(performer, this));

		// Pour from inventory
		actions.add(new ActionPourContainerInInventory(performer, this));

		actions.add(new ActionPlaceMapMarker(this));

		return actions;
	}

	public int straightLineDistanceTo(Square otherSquare) {
		return Math.abs(otherSquare.xInGrid - this.xInGrid) + Math.abs(otherSquare.yInGrid - this.yInGrid);

	}

	public float getCenterX() {
		return xInGrid * Game.SQUARE_WIDTH + Game.HALF_SQUARE_WIDTH;
	}

	public float getCenterY() {
		return yInGrid * Game.SQUARE_HEIGHT + Game.HALF_SQUARE_HEIGHT;
	}

	public boolean includableInPath(Actor actor) {
		if (Game.level.activeActor == Game.level.player && !this.seenByPlayer) {
			return true;
		} else if (inventory.canShareSquare()) {

			GameObject gameObjectDoor = inventory.getGameObjectOfClass(Door.class);
			if (gameObjectDoor instanceof Door) {
				Door door = (Door) gameObjectDoor;
				if (!actor.canOpenDoors) {
					return false;
				} else if (door.isLocked() && !actor.hasKeyForDoor(door)) {
				} else {
					return true;
				}
			} else {
				return true;
			}
		} else {

			GameObject gameObjectThatCantShareSquare = inventory.getGameObjectThatCantShareSquare();

			if (gameObjectThatCantShareSquare instanceof Actor)
				return true;
		}
		return false;
	}

	public Vector<Square> getAllNeighbourSquaresThatCanBeMovedTo(Actor actor) {
		Vector<Square> squares = new Vector<Square>();
		Square square;
		// +1,0
		if (ArrayUtils.inBounds(Game.level.squares, this.xInGrid + 1, this.yInGrid)) {
			square = Game.level.squares[this.xInGrid + 1][this.yInGrid];
			if (square.includableInPath(actor)) {
				squares.add(square);
			}
		}
		// -1,0
		if (ArrayUtils.inBounds(Game.level.squares, this.xInGrid - 1, this.yInGrid)) {
			square = Game.level.squares[this.xInGrid - 1][this.yInGrid];
			if (square.includableInPath(actor)) {
				squares.add(square);
			}
		}
		// 0,+1
		if (ArrayUtils.inBounds(Game.level.squares, this.xInGrid, this.yInGrid + 1)) {
			square = Game.level.squares[this.xInGrid][this.yInGrid + 1];
			if (square.includableInPath(actor)) {
				squares.add(square);
			}
		}
		// 0,-1
		if (ArrayUtils.inBounds(Game.level.squares, this.xInGrid, this.yInGrid - 1)) {
			square = Game.level.squares[this.xInGrid][this.yInGrid - 1];
			if (square.includableInPath(actor)) {
				squares.add(square);
			}
		}
		return squares;
	}

	@Override
	public float getCost(AStarNode node) {
		Square otherSquare = (Square) node;
		if (otherSquare.inventory.contains(BrokenGlass.class))
			return 8;
		if (otherSquare.inventory.contains(Actor.class))
			return 8;
		return 1;
	}

	@Override
	public float getEstimatedCost(AStarNode node) {
		Square otherSquare = (Square) node;
		return this.straightLineDistanceTo(otherSquare);
	}

	@Override
	public List getNeighbors(Actor actor) {
		return getAllNeighbourSquaresThatCanBeMovedTo(actor);
	}

	public Vector<Square> getAllSquaresAtDistance(float distance) {
		Vector<Square> squares = new Vector<Square>();
		if (distance == 0) {
			squares.addElement(this);
			return squares;
		}

		boolean xGoingUp = true;
		boolean yGoingUp = true;
		for (float i = 0, x = -distance, y = 0; i < distance * 4; i++) {
			if (ArrayUtils.inBounds(Game.level.squares, this.xInGrid + x, this.yInGrid + y)) {
				squares.add(Game.level.squares[this.xInGrid + (int) x][this.yInGrid + (int) y]);
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

	public Square getSquareToLeftOf() {
		if (this.xInGrid == 0) {
			return null;
		} else {
			return Game.level.squares[this.xInGrid - 1][this.yInGrid];
		}
	}

	public Square getSquareToRightOf() {
		if (this.xInGrid == Game.level.width - 1) {
			return null;
		} else {
			return Game.level.squares[this.xInGrid + 1][this.yInGrid];
		}
	}

	public Square getSquareAbove() {
		if (this.yInGrid == 0) {
			return null;
		} else {
			return Game.level.squares[this.xInGrid][this.yInGrid - 1];
		}
	}

	public Square getSquareBelow() {
		if (this.yInGrid == Game.level.height - 1) {
			return null;
		} else {
			return Game.level.squares[this.xInGrid][this.yInGrid + 1];
		}
	}

	@Override
	public void inventoryChanged() {

	}

	public static boolean inRange(int x, int y) {
		if (x < 0)
			return false;
		if (x >= Game.level.squares.length)
			return false;
		if (y < 0)
			return false;
		if (y >= Game.level.squares[0].length)
			return false;
		return true;
	}
}
