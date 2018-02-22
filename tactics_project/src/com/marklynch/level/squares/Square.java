package com.marklynch.level.squares;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.lwjgl.input.Keyboard;

import com.marklynch.Game;
import com.marklynch.ai.utils.AStarNode;
import com.marklynch.level.Level;
import com.marklynch.level.UserInputLevel;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.area.Area;
import com.marklynch.level.constructs.bounds.structure.Structure;
import com.marklynch.level.constructs.bounds.structure.StructureRoom;
import com.marklynch.level.constructs.bounds.structure.StructureSection;
import com.marklynch.level.constructs.inventory.InventoryParent;
import com.marklynch.level.constructs.inventory.InventorySquare;
import com.marklynch.level.constructs.inventory.SquareInventory;
import com.marklynch.level.constructs.journal.Objective;
import com.marklynch.level.constructs.power.Power;
import com.marklynch.objects.BrokenGlass;
import com.marklynch.objects.Discoverable;
import com.marklynch.objects.Door;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.HidingPlace;
import com.marklynch.objects.MapMarker;
import com.marklynch.objects.Stump;
import com.marklynch.objects.Tree;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actions.ActionAttack;
import com.marklynch.objects.actions.ActionDropItems;
import com.marklynch.objects.actions.ActionHide;
import com.marklynch.objects.actions.ActionMove;
import com.marklynch.objects.actions.ActionOpenInventoryToDropItems;
import com.marklynch.objects.actions.ActionOpenInventoryToThrowItems;
import com.marklynch.objects.actions.ActionPin;
import com.marklynch.objects.actions.ActionPlaceMapMarker;
import com.marklynch.objects.actions.ActionPourContainerInInventory;
import com.marklynch.objects.actions.ActionStopHiding;
import com.marklynch.objects.actions.ActionTakeAll;
import com.marklynch.objects.actions.ActionTeleport;
import com.marklynch.objects.actions.ActionTeleportSwap;
import com.marklynch.objects.actions.ActionThrowItem;
import com.marklynch.objects.actions.ActionWait;
import com.marklynch.objects.actions.ActionableInWorld;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.weapons.Weapon;
import com.marklynch.utils.ArrayUtils;
import com.marklynch.utils.Color;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.ResourceUtils;
import com.marklynch.utils.Texture;
import com.marklynch.utils.TextureUtils;

public class Square extends AStarNode implements ActionableInWorld, InventoryParent {

	public String guid = UUID.randomUUID().toString();
	public final static String[] editableAttributes = { "elevation", "travelCost", "imageTexture" };

	public final int elevation;
	public int travelCost;
	public SquareInventory inventory;
	public boolean showInventory;

	// public transient boolean reachableBySelectedCharater = false;
	public transient boolean visibleToSelectedCharacter = false;
	public transient boolean visibleToPlayer = false;
	public transient boolean seenByPlayer = false;
	public transient boolean inPath = false;
	public transient ArrayList<Weapon> weaponsThatCanAttack;

	// image
	public String imageTexturePath;
	public transient Texture imageTexture = null;
	public static Texture GRASS_TEXTURE;
	public static Texture DARK_GRASS_TEXTURE;
	public static Texture STONE_TEXTURE;
	public static Texture MUD_TEXTURE;
	public static Texture GREY_TEXTURE;
	public static Texture WHITE_SQUARE;
	public static Texture YELLOW_SQUARE;
	public static Texture RED_SQUARE;
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
	public String name;
	public boolean flash;

	public Square(int x, int y, String imagePath, int travelCost, int elevation, SquareInventory inventory,
			boolean restricted, Actor... owners) {
		this(x, y, imagePath, null, travelCost, elevation, inventory, restricted, owners);

	}

	public Square(int x, int y, String imagePath, Texture imageTexture, int travelCost, int elevation,
			SquareInventory inventory, boolean restricted, Actor... owners) {
		super();
		this.xInGrid = x;
		this.yInGrid = y;
		name = "Square @ " + this.xInGrid + "," + this.yInGrid;
		name = this.xInGrid + "," + this.yInGrid;
		this.xInGridPixels = xInGrid * Game.SQUARE_WIDTH;
		this.yInGridPixels = yInGrid * Game.SQUARE_HEIGHT;

		this.imageTexturePath = imagePath;
		this.travelCost = travelCost;
		this.elevation = elevation;

		if (imageTexture == null)
			loadImages();
		else
			this.imageTexture = imageTexture;

		weaponsThatCanAttack = new ArrayList<Weapon>();
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
		if (!(this instanceof InventorySquare))
			calculatePathCost();

	}

	public void afterContructor() {
		neighbors = getAllSquaresAtDistance(1);
		if (isEven(xInGrid + yInGrid)) {
			Collections.reverse(neighbors);
		}
	}

	public static boolean isEven(int number) {
		return (number & 1) == 0;
	}

	public static void loadStaticImages() {
		GRASS_TEXTURE = ResourceUtils.getGlobalImage("grass.png", false);
		DARK_GRASS_TEXTURE = ResourceUtils.getGlobalImage("dark_grass.png", false);
		STONE_TEXTURE = ResourceUtils.getGlobalImage("stone.png", false);
		MUD_TEXTURE = ResourceUtils.getGlobalImage("mud.png", false);
		GREY_TEXTURE = ResourceUtils.getGlobalImage("square.png", false);
		WHITE_SQUARE = ResourceUtils.getGlobalImage("white_square.png", false);
		YELLOW_SQUARE = ResourceUtils.getGlobalImage("yellow_square.png", false);
		RED_SQUARE = ResourceUtils.getGlobalImage("red_square.png", false);

		W_TEXTURE = ResourceUtils.getGlobalImage("w.png", false);
		A_TEXTURE = ResourceUtils.getGlobalImage("a.png", false);
		S_TEXTURE = ResourceUtils.getGlobalImage("s.png", false);
		D_TEXTURE = ResourceUtils.getGlobalImage("d.png", false);
		SHIFT_W_TEXTURE = ResourceUtils.getGlobalImage("shiftw.png", false);
		SHIFT_A_TEXTURE = ResourceUtils.getGlobalImage("shifta.png", false);
		SHIFT_S_TEXTURE = ResourceUtils.getGlobalImage("shifts.png", false);
		SHIFT_D_TEXTURE = ResourceUtils.getGlobalImage("shiftd.png", false);

		ATTACK_LEFT_BORDER_TEXTURE = ResourceUtils.getGlobalImage("attack_left_border.png", false);
		ATTACK_RIGHT_BORDER_TEXTURE = ResourceUtils.getGlobalImage("attack_right_border.png", false);
		ATTACK_TOP_BORDER_TEXTURE = ResourceUtils.getGlobalImage("attack_top_border.png", false);
		ATTACK_BOTTOM_BORDER_TEXTURE = ResourceUtils.getGlobalImage("attack_bottom_border.png", false);

		SOUND_TEXTURE = ResourceUtils.getGlobalImage("sound.png", false);
	}

	public void postLoad1() {
		inventory.square = this;
		inventory.postLoad1();
		weaponsThatCanAttack = new ArrayList<Weapon>();

		loadImages();
	}

	public void postLoad2() {
		inventory.postLoad2();
	}

	public void loadImages() {
		this.imageTexture = getGlobalImage(imageTexturePath, false);

	}

	public void draw1() {

		Texture textureToDraw = this.imageTexture;

		if (!Game.fullVisiblity) {

			if (!this.seenByPlayer) {
				textureToDraw = GREY_TEXTURE;
			}
		}

		// square texture
		float squarePositionX = xInGridPixels;
		float squarePositionY = yInGridPixels;
		// QuadUtils.drawQuad(new Color(0.2f, 0.4f, 0.1f, 1.0f),
		// squarePositionX, squarePositionX + Game.SQUARE_WIDTH,
		// squarePositionY, squarePositionY + Game.SQUARE_HEIGHT);
		float alpha = 1f;
		if (!this.visibleToPlayer)
			alpha = 0.5f;

		TextureUtils.drawTexture(textureToDraw, alpha, squarePositionX, squarePositionY,
				squarePositionX + Game.SQUARE_WIDTH, squarePositionY + Game.SQUARE_HEIGHT);

		if (sounds.size() > 0) {
			drawRedHighlight();
		}

		if (restricted && Game.redHighlightOnRestrictedSquares) {
			drawRedHighlight();
		}
		if (highlight) {
			drawHighlight();
		}
		if (flash) {
			drawHighlight();
		}

	}

	public void drawHighlight() {

		float squarePositionX = xInGridPixels;
		float squarePositionY = yInGridPixels;
		TextureUtils.drawTexture(Game.level.gameCursor.imageTexture2, squarePositionX, squarePositionY,
				squarePositionX + Game.SQUARE_WIDTH, squarePositionY + Game.SQUARE_HEIGHT);

	}

	public void drawAttackHighlight(ArrayList<Square> attackableSquares) {

		float squarePositionX = xInGridPixels;
		float squarePositionY = yInGridPixels;

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

		float squarePositionX = xInGridPixels;
		float squarePositionY = yInGridPixels;

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

		float squarePositionX = xInGridPixels;
		float squarePositionY = yInGridPixels;

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

		float squarePositionX = xInGridPixels;
		float squarePositionY = yInGridPixels;
		TextureUtils.drawTexture(Game.level.gameCursor.imageTexture4, squarePositionX, squarePositionY,
				squarePositionX + Game.SQUARE_WIDTH, squarePositionY + Game.SQUARE_HEIGHT);

	}

	public void draw2() {

		// int squarePositionX = xInGridPixels;
		// int squarePositionY = yInGridPixels;
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
		float squarePositionX = xInGridPixels;
		float squarePositionY = yInGridPixels;

		// System.out.println("VISIBLE");
		TextureUtils.drawTexture(Game.level.gameCursor.cursor, squarePositionX, squarePositionY,
				squarePositionX + Game.SQUARE_WIDTH, squarePositionY + Game.SQUARE_HEIGHT);
		// GL11.glPopMatrix();
	}

	public void drawQuestsMarkersForVisibleOnScreenObjectives(Objective objective) {
		float squarePositionX = xInGridPixels;
		float squarePositionY = yInGridPixels;

		if (this.visibleToPlayer && onScreen()) {
			TextureUtils.drawTexture(Game.level.gameCursor.cursor, squarePositionX, squarePositionY,
					squarePositionX + Game.SQUARE_WIDTH, squarePositionY + Game.SQUARE_HEIGHT);
		}
	}

	public void drawQuestsMarkersForNonVisibleOnScreenObjectives(Objective objective) {
		float squarePositionX = xInGridPixels;
		float squarePositionY = yInGridPixels;

		if (!this.visibleToPlayer && onScreen()) {

			float drawPositionX = (Game.halfWindowWidth) + (Game.zoom
					* (squarePositionX + Game.HALF_SQUARE_WIDTH - Game.halfWindowWidth + Game.getDragXWithOffset()));
			float drawPositionY = (Game.halfWindowHeight) + (Game.zoom
					* (squarePositionY + Game.HALF_SQUARE_HEIGHT - Game.halfWindowHeight + Game.getDragYWithOffset()));
			QuadUtils.drawQuad(Color.WHITE, drawPositionX - 10, drawPositionY - 10, drawPositionX + 10,
					drawPositionY + 10);
			TextureUtils.drawTexture(objective.texture, drawPositionX - 10, drawPositionY - 10, drawPositionX + 10,
					drawPositionY + 10);

		}
	}

	public boolean onScreen() {

		if (this.xInGrid >= Level.gridX1Bounds && this.xInGrid < Level.gridX2Bounds
				&& this.yInGrid >= Level.gridY1Bounds && this.yInGrid < Level.gridY2Bounds) {
			return true;
		}
		return false;
	}

	public Action drawActionThatWillBePerformed(boolean onMouse) {

		Action action = null;
		if (!this.seenByPlayer) {
			if (onMouse) {
				TextureUtils.drawTexture(Action.textureWalk, UserInputLevel.mouseLastX + 16,
						Game.windowHeight - UserInputLevel.mouseLastY + 16,
						UserInputLevel.mouseLastX + Game.QUARTER_SQUARE_WIDTH + 16,
						Game.windowHeight - UserInputLevel.mouseLastY + Game.QUARTER_SQUARE_HEIGHT + 16);
			} else {
				// float squarePositionX = xInGridPixels;
				// float squarePositionY = yInGridPixels;
				// TextureUtils.drawTexture(Action.textureWalk, squarePositionX
				// + Game.SQUARE_WIDTH - 48,
				// squarePositionY + Game.SQUARE_HEIGHT - 48, squarePositionX +
				// Game.SQUARE_WIDTH - 16,
				// squarePositionY + Game.SQUARE_HEIGHT - 16);
			}
			return null;
		}

		// if (action != null && !action.enabled)
		// action = null;

		if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)) {
			if (onMouse) {

				TextureUtils.drawTexture(Action.textureEllipse, 1f, UserInputLevel.mouseLastX + 16,
						Game.windowHeight - UserInputLevel.mouseLastY + 16,
						UserInputLevel.mouseLastX + Game.QUARTER_SQUARE_WIDTH + 16,
						Game.windowHeight - UserInputLevel.mouseLastY + Game.QUARTER_SQUARE_HEIGHT + 16);
			} else {
				// float squarePositionX = xInGridPixels;
				// float squarePositionY = yInGridPixels;
				// TextureUtils.drawTexture(Action.textureEllipse,
				// squarePositionX + Game.SQUARE_WIDTH - 64,
				// squarePositionY + Game.SQUARE_HEIGHT - 64, squarePositionX +
				// Game.SQUARE_WIDTH - 0,
				// squarePositionY + Game.SQUARE_HEIGHT - 0);

			}
		} else {
			if (Keyboard.isKeyDown(Keyboard.KEY_LMENU) || Keyboard.isKeyDown(Keyboard.KEY_RMENU)) {
				action = this.getAttackActionForTheSquareOrObject(Game.level.player);
				// if (action == null || !action.enabled) {
				// action =
				// this.getDefaultActionPerformedOnThisInWorld(Game.level.player);
				// }
			} else if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
				action = this.getSecondaryActionForTheSquareOrObject(Game.level.player);
				// if (action == null || !action.enabled) {
				// action =
				// this.getSecondaryActionPerformedOnThisInWorld(Game.level.player);
				// }
			} else {
				action = this.getDefaultActionForTheSquareOrObject(Game.level.player);
				// System.out.println("action @ a = " + action);
				// if (action != null)
				// System.out.println("action.enabled @ b = " + action.enabled);
				// if (!action.enabled) {
				// System.out.println("getting new");
				// action =
				// this.getDefaultActionPerformedOnThisInWorld(Game.level.player);
				// }
				// System.out.println("action @ c = " + action);
				// if (action != null)
				// System.out.println("action.enabled @ d = " + action.enabled);
			}

			// System.out.println("action = " + action);
			// System.out.println("action.enabled = " + action.enabled);
			if (action != null && action.image != null) {
				Color color = Color.WHITE;

				if (!action.enabled) {
					color = Color.GRAY;
				} else if (!action.legal) {
					color = Color.RED;
				}

				if (onMouse) {

					TextureUtils.drawTexture(action.image, UserInputLevel.mouseLastX + 16,
							Game.windowHeight - UserInputLevel.mouseLastY + 16,
							UserInputLevel.mouseLastX + Game.QUARTER_SQUARE_WIDTH + 16,
							Game.windowHeight - UserInputLevel.mouseLastY + Game.QUARTER_SQUARE_HEIGHT + 16, color);
				} else {

					// if (action instanceof ActionMove && action.legal)
					// return action;
					//
					// if (action instanceof ActionWait && action.legal)
					// return action;
					//
					// float squarePositionX = xInGridPixels;
					// float squarePositionY = yInGridPixels;
					// TextureUtils.drawTexture(action.image, squarePositionX +
					// Game.SQUARE_WIDTH - 48,
					// squarePositionY + Game.SQUARE_HEIGHT - 48,
					// squarePositionX +
					// Game.SQUARE_WIDTH - 16,
					// squarePositionY + Game.SQUARE_HEIGHT - 16, color);
				}
			}
		}
		return action;
	}

	public void drawKey(Texture texture) {
		float squarePositionX = xInGridPixels;
		float squarePositionY = yInGridPixels;
		TextureUtils.drawTexture(texture, squarePositionX, squarePositionY, squarePositionX + Game.SQUARE_WIDTH,
				squarePositionY + Game.SQUARE_HEIGHT);

	}

	public void drawPower(Power power) {

		if (power != null && power.image != null) {
			float squarePositionX = xInGridPixels;
			float squarePositionY = yInGridPixels;
			TextureUtils.drawTexture(power.image, squarePositionX + Game.QUARTER_SQUARE_WIDTH,
					squarePositionY + Game.QUARTER_SQUARE_WIDTH,
					squarePositionX + Game.SQUARE_WIDTH - Game.QUARTER_SQUARE_WIDTH,
					squarePositionY + Game.SQUARE_HEIGHT - Game.QUARTER_SQUARE_WIDTH);
		}
	}

	public void drawX(boolean onMouse) {

		if (onMouse) {
			TextureUtils.drawTexture(Action.x, 1f, UserInputLevel.mouseLastX + 16,
					Game.windowHeight - UserInputLevel.mouseLastY + 16,
					UserInputLevel.mouseLastX + Game.QUARTER_SQUARE_WIDTH + 16,
					Game.windowHeight - UserInputLevel.mouseLastY + Game.QUARTER_SQUARE_HEIGHT + 16);
		} else {

			float squarePositionX = xInGridPixels;
			float squarePositionY = yInGridPixels;
			TextureUtils.drawTexture(Action.x, squarePositionX + Game.QUARTER_SQUARE_WIDTH,
					squarePositionY + Game.QUARTER_SQUARE_WIDTH,
					squarePositionX + Game.SQUARE_WIDTH - Game.QUARTER_SQUARE_WIDTH,
					squarePositionY + Game.SQUARE_HEIGHT - Game.QUARTER_SQUARE_WIDTH);
		}

	}

	public void drawAction(Action action, boolean onMouse) {

		if (onMouse) {
			TextureUtils.drawTexture(action.image, 1f, UserInputLevel.mouseLastX + 16,
					Game.windowHeight - UserInputLevel.mouseLastY + 16,
					UserInputLevel.mouseLastX + Game.QUARTER_SQUARE_WIDTH + 16,
					Game.windowHeight - UserInputLevel.mouseLastY + Game.QUARTER_SQUARE_HEIGHT + 16);
		} else {

			// float squarePositionX = xInGridPixels;
			// float squarePositionY = yInGridPixels;
			// TextureUtils.drawTexture(action.image, squarePositionX +
			// Game.QUARTER_SQUARE_WIDTH,
			// squarePositionY + Game.QUARTER_SQUARE_WIDTH,
			// squarePositionX + Game.SQUARE_WIDTH - Game.QUARTER_SQUARE_WIDTH,
			// squarePositionY + Game.SQUARE_HEIGHT -
			// Game.QUARTER_SQUARE_WIDTH);
		}

	}

	public class PathComparator implements Comparator<ArrayList<Square>> {

		@Override
		public int compare(ArrayList<Square> path1, ArrayList<Square> path2) {

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
		if (!seenByPlayer) {
			return new ActionMove(performer, this, true);
		}

		GameObject targetGameObject = null;

		if (Game.gameObjectMouseIsOver != null) {
			targetGameObject = Game.gameObjectMouseIsOver;
		}

		if (targetGameObject == null) {
			targetGameObject = this.inventory.getGameObjectThatCantShareSquare();
		}

		if (targetGameObject != null) {
			return targetGameObject.getDefaultActionPerformedOnThisInWorld(performer);
		} else {
			return getDefaultActionPerformedOnThisInWorld(performer);
		}
		// return
	}

	public GameObject getGameObjectMouseIsOver() {

		// if (!this.seenByPlayer)
		// return null;

		ArrayList<GameObject> gameObjectsToCheck = new ArrayList<GameObject>();

		// sqr @ +1,0
		if (xInGrid + 1 < Game.level.squares.length) {
			if (Game.level.squares[xInGrid + 1][yInGrid].seenByPlayer)
				gameObjectsToCheck.addAll(Game.level.squares[xInGrid + 1][yInGrid].inventory.gameObjects);
		}

		// sqr @ +0,+0 (this sqr)
		if (this.visibleToPlayer)
			gameObjectsToCheck.addAll(inventory.gameObjects);

		// sqr @ -1,+0
		if (xInGrid - 1 > 0) {
			if (Game.level.squares[xInGrid - 1][yInGrid].seenByPlayer)
				gameObjectsToCheck.addAll(Game.level.squares[xInGrid - 1][yInGrid].inventory.gameObjects);
		}

		if (yInGrid + 1 < Game.level.squares[0].length) {
			// sqr @ +1,+1
			if (xInGrid + 1 < Game.level.squares.length) {
				if (Game.level.squares[xInGrid + 1][yInGrid + 1].seenByPlayer)
					gameObjectsToCheck.addAll(Game.level.squares[xInGrid + 1][yInGrid + 1].inventory.gameObjects);
			}

			// sqr @ 0,+1
			if (Game.level.squares[xInGrid][yInGrid + 1].seenByPlayer)
				gameObjectsToCheck.addAll(Game.level.squares[xInGrid][yInGrid + 1].inventory.gameObjects);

			// sqr @ -11,+1
			if (xInGrid - 1 > 0) {
				if (Game.level.squares[xInGrid - 1][yInGrid + 1].seenByPlayer)
					gameObjectsToCheck.addAll(Game.level.squares[xInGrid - 1][yInGrid + 1].inventory.gameObjects);
			}
		}

		for (int i = gameObjectsToCheck.size() - 1; i >= 0; i--) {

			GameObject gameObject = gameObjectsToCheck.get(i);
			if (gameObject instanceof Discoverable) {
				Discoverable d = (Discoverable) gameObject;
				if (!d.discovered)
					continue;
			}

			if (!gameObject.squareGameObjectIsOn.visibleToPlayer && !gameObject.persistsWhenCantBeSeen)
				continue;

			// gameObject.imageTexture.getTexture().

			int x = (int) (gameObject.squareGameObjectIsOn.xInGridPixels
					+ Game.SQUARE_WIDTH * gameObject.drawOffsetRatioX);
			int y = (int) (gameObject.squareGameObjectIsOn.yInGridPixels
					+ Game.SQUARE_HEIGHT * gameObject.drawOffsetRatioY);
			if (gameObject.primaryAnimation != null) {
				x += gameObject.primaryAnimation.offsetX;
				y += gameObject.primaryAnimation.offsetY;
			}

			// FirstCheckBounding box :P
			if (UserInputLevel.mouseXTransformed > x && UserInputLevel.mouseXTransformed < x + gameObject.width
					&& UserInputLevel.mouseYTransformed > y
					&& UserInputLevel.mouseYTransformed < y + gameObject.height) {
				Color color = getPixel(gameObject.imageTexture, (int) (UserInputLevel.mouseXTransformed - x),
						(int) (UserInputLevel.mouseYTransformed - y));

				if (color != null && color.a > 0)
					return gameObject;
			}
		}
		return null;

	}

	public Color getPixel(Texture texture, int x, int y) {

		if (texture == null)
			return null;
		// in method
		if (x < 0 || y < 0)
			return null;

		if (x > texture.getWidth() - 1 || y > texture.getHeight() - 1) {
			return null;
		}

		int index = (x + y * texture.getWidth());

		// int r = texture.pixels[index] & 0xFF;
		// int g = texture.pixels[index + 1] & 0xFF;
		// int b = texture.pixels[index + 2] & 0xFF;
		int a = texture.pixels[index + 3] & 0xFF;

		return new Color(0, 0, 0, a);
	}

	// this.xInGridPixels;
	//
	// mouseXTransformed
	// mouseYTransformed

	public Action getSecondaryActionForTheSquareOrObject(Actor performer) {

		GameObject targetGameObject = this.inventory.getGameObjectThatCantShareSquare();
		if (targetGameObject != null) {
			return targetGameObject.getSecondaryActionPerformedOnThisInWorld(performer);
		}

		Tree tree = (Tree) this.inventory.getGameObjectOfClass(Tree.class);
		if (tree != null) {
			return tree.getSecondaryActionPerformedOnThisInWorld(performer);
		}

		Stump stump = (Stump) this.inventory.getGameObjectOfClass(Stump.class);
		if (stump != null) {
			return stump.getSecondaryActionPerformedOnThisInWorld(performer);
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

		// if (inventory.contains(Game.level.player))
		// return null;

		if (Game.gameObjectMouseIsOver != null) {
			if (Game.gameObjectMouseIsOver.attackable) {
				return new ActionAttack(performer, Game.gameObjectMouseIsOver);
			} else {
				return null;
			}
		}

		GameObject targetGameObject = this.inventory.getGameObjectThatCantShareSquare();
		if (targetGameObject != null && targetGameObject.attackable) {
			return new ActionAttack(performer, targetGameObject);
		}

		if (this.inventory.size() == 1 && this.inventory.get(0).attackable) {
			return new ActionAttack(performer, this.inventory.get(0));
		}

		if (this.inventory.getDecorativeCount() == this.inventory.size() - 1) {
			GameObject onlyNonDecorativeObject = this.inventory.getNonDecorativeGameObject();
			if (onlyNonDecorativeObject != null && onlyNonDecorativeObject.attackable)
				return new ActionAttack(performer, onlyNonDecorativeObject);
		}

		return null;
	}

	@Override
	public Action getDefaultActionPerformedOnThisInWorld(Actor performer) {

		if (!seenByPlayer) {
			return new ActionMove(performer, this, true);
		} else if (this == Game.level.player.squareGameObjectIsOn) {
			return new ActionWait(performer, this);
		} else {

			HidingPlace hidingPlace = (HidingPlace) this.inventory.getGameObjectOfClass(HidingPlace.class);
			if (hidingPlace != null && hidingPlace.remainingHealth > 0) {
				if (performer.hiding && performer.squareGameObjectIsOn == this) {
					return new ActionStopHiding(performer, hidingPlace);
				} else {
					return new ActionHide(performer, hidingPlace);
				}
			}

			return new ActionMove(performer, this, true);

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
			actions.add(new ActionTeleportSwap(performer, performer, this, true));
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
		if (performer.equipped != null) {
			if (performer.straightLineDistanceTo(this) < 2) {
				actions.add(new ActionDropItems(performer, this, performer.equipped));
			}
		}

		// drop from inventory
		actions.add(new ActionOpenInventoryToDropItems(performer, this));

		// Throw equipped
		if (this != Game.level.player.squareGameObjectIsOn && performer.equipped != null) {
			actions.add(new ActionThrowItem(performer, this, performer.equipped));
		}

		// Throw from inventory
		if (this != Game.level.player.squareGameObjectIsOn)
			actions.add(new ActionOpenInventoryToThrowItems(performer, this));

		// Pour from inventory
		actions.add(new ActionPourContainerInInventory(performer, this));

		if (!this.inventory.contains(MapMarker.class))
			actions.add(new ActionPlaceMapMarker(this));

		actions.add(new ActionPin(performer, this));

		return actions;
	}

	public float getCenterX() {
		return xInGridPixels + Game.HALF_SQUARE_WIDTH;
	}

	public float getCenterY() {
		return yInGridPixels + Game.HALF_SQUARE_HEIGHT;
	}

	public boolean includableInPath(Actor actor, AStarNode goalNode) {

		if (actor == Game.level.player && !this.seenByPlayer)
			return true;

		if (this == goalNode)
			return true;

		if (inventory.canShareSquare()) {

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

	@Override
	public List getAllNeighbourSquaresThatCanBeMovedTo(Actor actor, AStarNode goalNode) {
		ArrayList<Square> squares = new ArrayList<Square>();

		for (Square square : neighbors) {
			if (square.includableInPath(actor, goalNode)) {
				squares.add(square);
			}
		}
		return squares;
	}

	@Override
	public float getEstimatedCost(AStarNode node) {
		return this.straightLineDistanceTo(node) + node.cost - 1;
	}

	public ArrayList<Square> getAllSquaresAtDistance(float distance) {
		ArrayList<Square> squares = new ArrayList<Square>();
		if (distance == 0) {
			squares.add(this);
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

	public void calculatePathCost() {
		// if (!inventory.canShareSquare())
		// cost = 999;
		if (inventory.contains(BrokenGlass.class))
			cost = 9;
		else if (inventory.contains(Actor.class))
			cost = 9;
		else
			cost = 1;
	}

	public void calculatePathCostForPlayer() {
		if (inventory.contains(BrokenGlass.class))
			costForPlayer = 9;
		else
			costForPlayer = 1;
	}
}
