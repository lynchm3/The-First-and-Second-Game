package com.marklynch.level.squares;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.lwjgl.input.Keyboard;

import com.marklynch.Game;
import com.marklynch.GameCursor;
import com.marklynch.actions.Action;
import com.marklynch.actions.ActionAttack;
import com.marklynch.actions.ActionDigging;
import com.marklynch.actions.ActionDropItems;
import com.marklynch.actions.ActionHide;
import com.marklynch.actions.ActionMove;
import com.marklynch.actions.ActionOpenInventoryToDropItems;
import com.marklynch.actions.ActionOpenInventoryToEmptyItems;
import com.marklynch.actions.ActionOpenInventoryToThrowItems;
import com.marklynch.actions.ActionPlaceMapMarker;
import com.marklynch.actions.ActionStopHiding;
import com.marklynch.actions.ActionTakeAll;
import com.marklynch.actions.ActionTeleport;
import com.marklynch.actions.ActionTeleportSwap;
import com.marklynch.actions.ActionThrowItem;
import com.marklynch.actions.ActionViewInfo;
import com.marklynch.actions.ActionWait;
import com.marklynch.actions.ActionableInWorld;
import com.marklynch.data.Idable;
import com.marklynch.level.Level;
import com.marklynch.level.UserInputLevel;
import com.marklynch.level.constructs.animation.primary.AnimationLiquidSpread;
import com.marklynch.level.constructs.area.Area;
import com.marklynch.level.constructs.bounds.structure.Structure;
import com.marklynch.level.constructs.bounds.structure.StructureSection;
import com.marklynch.level.constructs.bounds.structure.structureroom.StructureRoom;
import com.marklynch.level.constructs.inventory.InventoryParent;
import com.marklynch.level.constructs.inventory.InventorySquare;
import com.marklynch.level.constructs.inventory.SquareInventory;
import com.marklynch.level.constructs.journal.Objective;
import com.marklynch.level.constructs.power.Power;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.armor.Weapon;
import com.marklynch.objects.inanimateobjects.BrokenGlass;
import com.marklynch.objects.inanimateobjects.FastTravelLocation;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.inanimateobjects.HidingPlace;
import com.marklynch.objects.inanimateobjects.Landmine;
import com.marklynch.objects.inanimateobjects.Liquid;
import com.marklynch.objects.inanimateobjects.MapLevelGameObject;
import com.marklynch.objects.inanimateobjects.MapMarker;
import com.marklynch.objects.inanimateobjects.Portal;
import com.marklynch.objects.inanimateobjects.PressurePlate;
import com.marklynch.objects.inanimateobjects.RemoteDoor;
import com.marklynch.objects.inanimateobjects.VoidHole;
import com.marklynch.objects.inanimateobjects.Wall;
import com.marklynch.objects.templates.Templates;
import com.marklynch.ui.button.Tooltip;
import com.marklynch.utils.ArrayList;
import com.marklynch.utils.ArrayUtils;
import com.marklynch.utils.Color;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.ResourceUtils;
import com.marklynch.utils.Texture;
import com.marklynch.utils.TextureUtils;
import com.marklynch.utils.Utils;

public class Square implements Idable, ActionableInWorld, InventoryParent, Comparable<Square> {

	// public static final ArrayList<Square> instances = new ArrayList<Square>();

	public Long id;

	public final static String[] editableAttributes = { "elevation", "travelCost", "imageTexture" };

	public transient int elevation;
	public transient int travelCost;
	public SquareInventory inventory;

	public transient boolean visibleToPlayer = false;
	public transient boolean playerCanCastTo = false;

	public boolean seenByPlayer = false;
	public transient boolean inPath = false;
	public transient ArrayList<Weapon> weaponsThatCanAttack = new ArrayList<Weapon>(Weapon.class);

	// image
	public transient Texture defaultImageTexture = null;

	private Texture floorImageTexture = null;

	public static Texture GRASS_TEXTURE;
	public static Texture DIGGABLE_GRASS_TEXTURE;
	public static Texture DARK_GRASS_TEXTURE;
	public static Texture STONE_TEXTURE;
	public static Texture ETCHED_FLOOR_TEXTURE;
	public static Texture MUD_TEXTURE;
	public static Texture WATER_TEXTURE;
	public static Texture GREY_TEXTURE;
	public static Texture GREY_OUT;
	public static Texture WHITE_SQUARE;
//	public static Texture YELLOW_SQUARE;
	public static Texture GREY_TRANSLUCENT_SQUARE;
	public static Texture RED_SQUARE;
	public static Texture VOID_SQUARE;
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

	public transient Area areaSquareIsIn;
	public transient Structure structureSquareIsIn;
	public transient StructureSection structureSectionSquareIsIn;
	public transient StructureRoom structureRoomSquareIsIn;

	public transient boolean highlight = false;
	public transient boolean drawPathDot = false;
	public transient boolean drawEndPathDot = false;
	// public boolean drawX = false;

	public transient ArrayList<Actor> owners = new ArrayList<Actor>(Actor.class);
	public transient boolean restricted;
	public transient boolean restrictedAtNight;
	public transient String name;
	public transient boolean flash;

	// path finding
	public transient ArrayList<Node> nodes = new ArrayList<Node>(Node.class);

	public transient Square pathParent;
	public transient float costFromStart;
	public transient float estimatedCostToGoal;

	public transient float pathCostForAI = 1;
	public transient float pathCostForPlayer = 1;
	public transient float pathCostForLiquid = 1;

	public transient int xInGrid;
	public transient int yInGrid;

	public transient float xInGridPixels;
	public transient float yInGridPixels;

	public transient ArrayList<Square> neighbors = new ArrayList<Square>(Square.class);

	public transient Node node;

	public Square() {
	}

	public Square(int x, int y, String imagePath, int travelCost, int elevation, SquareInventory inventory,
			boolean restricted, Actor... owners) {
		this(x, y, imagePath, null, travelCost, elevation, inventory, restricted, owners);
	}

	public Square(int x, int y, String imagePath, Texture imageTexture, int travelCost, int elevation,
			SquareInventory inventory, boolean restricted, Actor... owners) {
		super();
		// instances.add(this);
		this.id = Level.generateNewId(this);
		this.xInGrid = x;
		this.yInGrid = y;
		name = "Square @ " + this.xInGrid + "," + this.yInGrid;
		name = this.xInGrid + "," + this.yInGrid;
		this.xInGridPixels = xInGrid * Game.SQUARE_WIDTH;
		this.yInGridPixels = yInGrid * Game.SQUARE_HEIGHT;

		this.travelCost = travelCost;
		this.elevation = elevation;
		this.floorImageTexture = imageTexture;

		weaponsThatCanAttack = new ArrayList<Weapon>(Weapon.class);
		this.inventory = inventory;
		if (this.inventory != null) {
			this.inventory.parent = this;
			this.inventory.square = this;
		}

		this.owners = new ArrayList<Actor>(Actor.class);
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

	public static final HashMap<Texture, Color> tileColors = new HashMap<Texture, Color>();

	// public static TooltipGroup tooltipGroup = null;

	public static void loadStaticImages() {
		GRASS_TEXTURE = ResourceUtils.getGlobalImage("grass.png", true);
		tileColors.put(GRASS_TEXTURE, new Color(135, 163, 71));
		DIGGABLE_GRASS_TEXTURE = ResourceUtils.getGlobalImage("diggable_grass.png", true);
		tileColors.put(DIGGABLE_GRASS_TEXTURE, new Color(135, 163, 71));
		DARK_GRASS_TEXTURE = ResourceUtils.getGlobalImage("dark_grass.png", true);
		tileColors.put(DARK_GRASS_TEXTURE, new Color(103, 135, 47));
		STONE_TEXTURE = ResourceUtils.getGlobalImage("stone.png", true);
		tileColors.put(STONE_TEXTURE, Color.LIGHT_GRAY);
		ETCHED_FLOOR_TEXTURE = ResourceUtils.getGlobalImage("etched_floor.png", true);
		tileColors.put(ETCHED_FLOOR_TEXTURE, Color.LIGHT_GRAY);
		MUD_TEXTURE = ResourceUtils.getGlobalImage("mud.png", true);
		tileColors.put(MUD_TEXTURE, new Color(144, 88, 31));
		WATER_TEXTURE = ResourceUtils.getGlobalImage("water_tile.png", true);
		tileColors.put(WATER_TEXTURE, Color.BLUE);
		GREY_TEXTURE = ResourceUtils.getGlobalImage("square.png", true);
		tileColors.put(GREY_TEXTURE, Color.GRAY);
		GREY_OUT = ResourceUtils.getGlobalImage("grey_out.png", true);
		WHITE_SQUARE = ResourceUtils.getGlobalImage("white_square.png", true);
		tileColors.put(WHITE_SQUARE, Color.WHITE);
		GREY_TRANSLUCENT_SQUARE = ResourceUtils.getGlobalImage("grey_translucent_square.png", true);
		tileColors.put(GREY_TRANSLUCENT_SQUARE, Color.DARK_GRAY);
//		YELLOW_SQUARE = ResourceUtils.getGlobalImage("yellow_square.png", false);
//		tileColors.put(YELLOW_SQUARE, Color.YELLOW);
		RED_SQUARE = ResourceUtils.getGlobalImage("red_square.png", true);
		tileColors.put(RED_SQUARE, Color.RED);
		VOID_SQUARE = ResourceUtils.getGlobalImage("void_hole.png", true);
		tileColors.put(VOID_SQUARE, Color.BLACK);

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
		weaponsThatCanAttack = new ArrayList<Weapon>(Weapon.class);
	}

	public void postLoad2() {
		inventory.postLoad2();
	}

	public void draw1() {

		Texture textureToDraw = this.floorImageTexture;

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

		if (Game.zoom <= Game.zoomLevels[Game.MAP_MODE_ZOOM_LEVEL_INDEX]) {
			if ((!Game.fullVisiblity && !this.seenByPlayer) || textureToDraw == null) {

			} else {
				QuadUtils.drawQuad(Square.tileColors.get(textureToDraw), squarePositionX, squarePositionY,
						squarePositionX + Game.SQUARE_WIDTH, squarePositionY + Game.SQUARE_HEIGHT);
			}
		} else if (textureToDraw != null) {

			Color color = Level.dayTimeOverlayColor;
			if (structureSquareIsIn != null && textureToDraw != GREY_TEXTURE)
				color = StructureRoom.roomColor;

			TextureUtils.drawTexture(textureToDraw, alpha, squarePositionX, squarePositionY,
					squarePositionX + Game.SQUARE_WIDTH, squarePositionY + Game.SQUARE_HEIGHT, color);
		}

		// if (sounds.size() > 0) {
		// drawRedHighlight();
		// }

		if (restricted && Game.redHighlightOnRestrictedSquares) {
			drawRedHighlight();
		}
		if (highlight) {
			drawHighlight();
		}
		if (drawPathDot) {
			drawPathDot();
		}
		if (drawEndPathDot) {
			drawEndPathDot();
		}

		if (flash) {
			drawHighlight();
		}

	}

	public void draw2() {

		// if (Game.gameObjectMouseIsOver.squareGameObjectIsOn == this) {
		//
		// }

		// if (!this.seenByPlayer || !this.visibleToPlayer)
		// return;
		//
		// float squarePositionX = xInGridPixels;
		// float squarePositionY = yInGridPixels;
		//
		// QuadUtils.drawQuad(Level.dayTimeOverlayColor, squarePositionX,
		// squarePositionY,
		// squarePositionX + Game.SQUARE_WIDTH, squarePositionY + Game.SQUARE_HEIGHT);
		// TextureUtils.drawTexture(textureToDraw, alpha, squarePositionX,
		// squarePositionY,
		// squarePositionX + Game.SQUARE_WIDTH, squarePositionY + Game.SQUARE_HEIGHT);

	}

	public void drawHighlight() {
		float squarePositionX = xInGridPixels;
		float squarePositionY = yInGridPixels;
		TextureUtils.drawTexture(Game.level.gameCursor.imageTexture2, squarePositionX, squarePositionY,
				squarePositionX + Game.SQUARE_WIDTH, squarePositionY + Game.SQUARE_HEIGHT);

	}

	public void drawPathDot() {

		float squarePositionX = xInGridPixels;
		float squarePositionY = yInGridPixels;
		TextureUtils.drawTexture(GameCursor.pathDot, squarePositionX, squarePositionY,
				squarePositionX + Game.SQUARE_WIDTH, squarePositionY + Game.SQUARE_HEIGHT);

	}

	public void drawEndPathDot() {

		float squarePositionX = xInGridPixels;
		float squarePositionY = yInGridPixels;
		TextureUtils.drawTexture(GameCursor.endPathDot, squarePositionX, squarePositionY,
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

		if (!this.seenByPlayer)
			return;

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

	// public void drawSoundHighlight() {
	//
	// if (!this.seenByPlayer)
	// return;
	//
	// float squarePositionX = xInGridPixels;
	// float squarePositionY = yInGridPixels;
	//
	// TextureUtils.drawTexture(Game.level.gameCursor.imageTexture2,
	// squarePositionX, squarePositionY,
	// squarePositionX + Game.SQUARE_WIDTH, squarePositionY + Game.SQUARE_HEIGHT);
	//
	// float distanceX = this.xInGrid -
	// Game.level.player.squareGameObjectIsOn.xInGrid;
	// float u1 = (distanceX + 11) * 0.09f;
	// float u2 = (distanceX + 11) * 0.09f + 0.09f;
	//
	// float distanceY = this.yInGrid -
	// Game.level.player.squareGameObjectIsOn.yInGrid;
	// float v1 = (distanceY + 11) * 0.09f;
	// float v2 = (distanceY + 11) * 0.09f + 0.09f;
	//
	// if (this.xInGrid > Game.level.player.squareGameObjectIsOn.xInGrid) {
	// u1 = 1 - u1;
	// u2 = 1 - u2;
	// }
	//
	// if (this.yInGrid > Game.level.player.squareGameObjectIsOn.yInGrid) {
	// v1 = 1 - v1;
	// v2 = 1 - v2;
	// }
	//
	// TextureUtils.drawTexture(Square.SOUND_TEXTURE, squarePositionX,
	// squarePositionY,
	// squarePositionX + Game.SQUARE_WIDTH, squarePositionY + Game.SQUARE_HEIGHT,
	// u1, v1, u2, v2);
	// }

	public void drawRedHighlight() {

		if (!this.seenByPlayer)
			return;

		float squarePositionX = xInGridPixels;
		float squarePositionY = yInGridPixels;
		TextureUtils.drawTexture(Game.level.gameCursor.imageTexture4, squarePositionX, squarePositionY,
				squarePositionX + Game.SQUARE_WIDTH, squarePositionY + Game.SQUARE_HEIGHT);

	}

	public void drawCursor() {
		// float squarePositionX = xInGridPixels;
		// float squarePositionY = yInGridPixels;
		// TextureUtils.drawTexture(Game.level.gameCursor.cursor,
		// squarePositionX, squarePositionY,
		// squarePositionX + Game.SQUARE_WIDTH, squarePositionY +
		// Game.SQUARE_HEIGHT);
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
				action = this.getAttackActionForTheSquareOrObject(Game.level.player, false);

			} else if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
				action = this.getSecondaryActionForTheSquareOrObject(Game.level.player, false);

			} else {
				action = this.getDefaultActionForTheSquareOrObject(Game.level.player, false);

			}

			if (action != null && action.image != null) {
				Color color = Color.WHITE;

				if (!action.enabled) {
					color = Color.GRAY;
				} else if (!action.legal) {
					color = Color.RED;
				}

				if (onMouse) {

					if (Game.pinWindowHoveringOver == null
							&& UserInputLevel.scrollableMouseIsOver != Game.level.activityLogger) {
						TextureUtils.drawTexture(action.image, UserInputLevel.mouseLastX + 16,
								Game.windowHeight - UserInputLevel.mouseLastY + 16,
								UserInputLevel.mouseLastX + Game.QUARTER_SQUARE_WIDTH + 16,
								Game.windowHeight - UserInputLevel.mouseLastY + Game.QUARTER_SQUARE_HEIGHT + 16, color);

						// Square.tooltipGroup = new TooltipGroup();

						if (Game.level.showWindowPixelCoords) {
							Level.tooltipGroup.add(new Tooltip(false, Tooltip.WHITE,
									"" + UserInputLevel.mouseLastX + "," + UserInputLevel.mouseLastY));
						}

						Level.tooltipGroup.add(new Tooltip(false, Tooltip.WHITE, action.actionName));

						if (action.disabledReason != null) {
							Level.tooltipGroup.add(new Tooltip(false, Tooltip.WHITE, action.disabledReason));
						}

						if (action.illegalReason != null) {
							Level.tooltipGroup.add(new Tooltip(false, Tooltip.RED, action.illegalReason));
						}
					}
					// } else {
					// Square.tooltipGroup = null;
					// }

					// if (Level.tooltipGroup != null) {
					// Square.tooltipGroup.drawStaticUI();
					// }
				} else {

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
			TextureUtils.drawTexture(Action.textureX, 1f, UserInputLevel.mouseLastX + 16,
					Game.windowHeight - UserInputLevel.mouseLastY + 16,
					UserInputLevel.mouseLastX + Game.QUARTER_SQUARE_WIDTH + 16,
					Game.windowHeight - UserInputLevel.mouseLastY + Game.QUARTER_SQUARE_HEIGHT + 16);
		} else {

			float squarePositionX = xInGridPixels;
			float squarePositionY = yInGridPixels;
			TextureUtils.drawTexture(Action.textureX, squarePositionX, squarePositionY,
					squarePositionX + Game.SQUARE_WIDTH, squarePositionY + Game.SQUARE_HEIGHT);
		}

	}

	public void drawAction(Action action, boolean onMouse) {

		if (onMouse) {
			TextureUtils.drawTexture(action.image, 1f, UserInputLevel.mouseLastX + 16,
					Game.windowHeight - UserInputLevel.mouseLastY + 16,
					UserInputLevel.mouseLastX + Game.QUARTER_SQUARE_WIDTH + 16,
					Game.windowHeight - UserInputLevel.mouseLastY + Game.QUARTER_SQUARE_HEIGHT + 16);

		} else {

			float squarePositionX = xInGridPixels;
			float squarePositionY = yInGridPixels;
			TextureUtils.drawTexture(action.image, squarePositionX + Game.QUARTER_SQUARE_WIDTH,
					squarePositionY + Game.QUARTER_SQUARE_WIDTH,
					squarePositionX + Game.SQUARE_WIDTH - Game.QUARTER_SQUARE_WIDTH,
					squarePositionY + Game.SQUARE_HEIGHT - Game.QUARTER_SQUARE_WIDTH);
		}

	}

	@Override
	public String toString() {
		return "" + this.xInGrid + "," + this.yInGrid;

	}

	public GameObject getGameObjectMouseIsOver() {

		if (Game.zoom == Game.zoomLevels[Game.MAP_MODE_ZOOM_LEVEL_INDEX])
			return null;

		// if (!this.seenByPlayer)
		// return null;

		ArrayList<GameObject> gameObjectsToCheck = new ArrayList<GameObject>(GameObject.class);

		// GameObject gameObject = null;

		// Y - 1
		if (yInGrid - 1 > 0) {
			// sqr @ +1,+1
			if (xInGrid + 1 < Game.level.squares.length) {
				if (Game.level.squares[xInGrid + 1][yInGrid - 1].seenByPlayer)
					gameObjectsToCheck.addAll(Game.level.squares[xInGrid + 1][yInGrid - 1].inventory.gameObjects);
			}

			// sqr @ 0,+1
			if (Game.level.squares[xInGrid][yInGrid - 1].seenByPlayer)
				gameObjectsToCheck.addAll(Game.level.squares[xInGrid][yInGrid - 1].inventory.gameObjects);

			// sqr @ -11,+1
			if (xInGrid - 1 > 0) {
				if (Game.level.squares[xInGrid - 1][yInGrid - 1].seenByPlayer)
					gameObjectsToCheck.addAll(Game.level.squares[xInGrid - 1][yInGrid - 1].inventory.gameObjects);
			}
		}

		// Y+0
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

		// Y + 1
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

			GameObject gameObjectToCheck = gameObjectsToCheck.get(i);
			if (!gameObjectToCheck.discoveredObject)
				continue;

			if (!gameObjectToCheck.squareGameObjectIsOn.visibleToPlayer && !gameObjectToCheck.persistsWhenCantBeSeen)
				continue;

			if (gameObjectToCheck instanceof MapLevelGameObject)
				continue;

			boolean pointOnGameObject = gameObjectToCheck.checkIfPointOnGameObject(new Utils.Point(
					(int) (UserInputLevel.mouseXTransformed), (int) (UserInputLevel.mouseYTransformed)));
			if (pointOnGameObject)
				return gameObjectToCheck;
		}
		return null;

	}

	// this.xInGridPixels;
	//
	// mouseXTransformed
	// mouseYTransformed

	public Action getDefaultActionForTheSquareOrObject(Actor performer, boolean keyPress) {
		if (keyPress == false && !seenByPlayer) {
			return new ActionMove(performer, this, true);
		}

		GameObject targetGameObject = null;

		if (keyPress == false && Game.gameObjectMouseIsOver != null) {
			targetGameObject = Game.gameObjectMouseIsOver;
		}

		if (targetGameObject == null) {
			targetGameObject = this.inventory.gameObjectThatCantShareSquare;
		}

		if (targetGameObject != null) {
			return targetGameObject.getDefaultActionPerformedOnThisInWorld(performer);
		} else {
			return getDefaultActionPerformedOnThisInWorld(performer);
		}
	}

	public Action getSecondaryActionForTheSquareOrObject(Actor performer, boolean keyPress) {

		if (keyPress == false && !seenByPlayer) {
			return new ActionMove(performer, this, true);
		}

		GameObject targetGameObject = null;

		if (keyPress == false && Game.gameObjectMouseIsOver != null) {
			targetGameObject = Game.gameObjectMouseIsOver;
		}

		if (targetGameObject == null) {
			targetGameObject = this.inventory.gameObjectThatCantShareSquare;
		}

		if (targetGameObject != null) {
			return targetGameObject.getSecondaryActionPerformedOnThisInWorld(performer);
		} else {
			return getSecondaryActionPerformedOnThisInWorld(performer);
		}
	}

	public Action getAttackActionForTheSquareOrObject(Actor performer, boolean keyPress) {

		// if (inventory.contains(Game.level.player))
		// return null;

		if (keyPress == false && Game.gameObjectMouseIsOver != null) {
			if (Game.gameObjectMouseIsOver.attackable) {
				return new ActionAttack(performer, Game.gameObjectMouseIsOver);
			} else {
				return null;
			}
		}

		GameObject targetGameObject = this.inventory.gameObjectThatCantShareSquare;
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
		ArrayList<Action> actions = new ArrayList<Action>(Action.class);
		// Move, teleport, loiter
		if (this != Game.level.player.squareGameObjectIsOn) {
			actions.add(new ActionMove(performer, this, true));
			actions.add(new ActionTeleport(performer, performer, this, true, true, false));
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

		// Empty from inventory
		actions.add(new ActionOpenInventoryToEmptyItems(performer, this));

		GameObject diggable = this.inventory.getDiggable();
		if (diggable != null) {
			actions.add(new ActionDigging(performer, diggable));
		} else {
			actions.add(new ActionDigging(performer, this));
		}

		if (!this.inventory.containsGameObjectOfType(MapMarker.class))
			actions.add(new ActionPlaceMapMarker(this));

		if (this.inventory.containsGameObjectOfType(FastTravelLocation.class) && this.seenByPlayer) {
			actions.add(new ActionTeleport(performer, performer, this, true, true, true));
		}

		actions.add(new ActionViewInfo(performer, this));

		return actions;
	}

	public float getCenterX() {
		return xInGridPixels + Game.HALF_SQUARE_WIDTH;
	}

	public float getCenterY() {
		return yInGridPixels + Game.HALF_SQUARE_HEIGHT;
	}

	public ArrayList<Square> getAllSquaresAtDistance(int distance) {
		ArrayList<Square> squares = new ArrayList<Square>(Square.class);
		if (distance == 0) {
			squares.add(this);
			return squares;
		}

		boolean xGoingUp = true;
		boolean yGoingUp = true;
		for (int i = 0, x = -distance, y = 0; i < distance * 4; i++) {
			if (ArrayUtils.inBounds(Game.level.squares, this.xInGrid + x, this.yInGrid + y)) {
				squares.add(Game.level.squares[this.xInGrid + x][this.yInGrid + y]);
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

	// PATH FINDING

	public void calculatePathCost() {

		if (inventory.containsGameObjectOfType(Landmine.class)) {
			Landmine landmine = (Landmine) inventory.getGameObjectOfClass(Landmine.class);
			if (landmine.discoveredObject) {// This is dumb, there's only a flag for player discovered... needs a
											// change...
				pathCostForAI = 10;
				return;
			}
		}

		if (inventory.containsGameObjectOfType(BrokenGlass.class)) {
			pathCostForAI = 10;
		} else if (inventory.containsGameObjectOfType(Actor.class)) {
			pathCostForAI = 10;
		} else if (inventory.containsGameObjectOfType(Portal.class)) {
			pathCostForAI = 10;
		} else if (inventory.containsGameObjectOfType(VoidHole.class)) {
			pathCostForAI = 10;
		} else if (inventory.containsGameObjectOfType(PressurePlate.class)) {
			pathCostForAI = 10;
		} else if (this.floorImageTexture == Square.STONE_TEXTURE) {
			pathCostForAI = 1;
		} else {
			pathCostForAI = 2;
		}
	}

	public void calculatePathCostForPlayer() {

		if (inventory.containsGameObjectOfType(Landmine.class)) {
			Landmine landmine = (Landmine) inventory.getGameObjectOfClass(Landmine.class);
			if (landmine.discoveredObject) {
				pathCostForPlayer = 10;
				return;
			}
		}

		if (inventory.containsGameObjectOfType(BrokenGlass.class)) {
			pathCostForPlayer = 10;
			// } else if (inventory.contains(Actor.class)) {
			// costForPlayer = 10;
		} else if (inventory.containsGameObjectOfType(Portal.class)) {
			pathCostForPlayer = 10;
		} else if (inventory.containsGameObjectOfType(VoidHole.class)) {
			pathCostForPlayer = 10;
		} else if (inventory.containsGameObjectOfType(PressurePlate.class)) {
			pathCostForPlayer = 10;
		} else if (this.floorImageTexture == Square.STONE_TEXTURE) {
			pathCostForPlayer = 1;
		} else {
			pathCostForPlayer = 2;
		}

	}

	// added by me
	public int straightLineDistanceTo(Square otherNode) {
		return Math.abs(otherNode.xInGrid - this.xInGrid) + Math.abs(otherNode.yInGrid - this.yInGrid);
	}

	public float getCost() {
		return costFromStart + estimatedCostToGoal;
	}

	@Override
	public int compareTo(Square other) {
		float thisValue = this.getCost();
		float otherValue = other.getCost();

		float v = thisValue - otherValue;
		return (v > 0) ? 1 : (v < 0) ? -1 : 0; // sign function
	}

	public List getAllNeighbourSquaresThatCanBeMovedTo(Actor actor, Square goalSquare) {
		Game.getNeighborsThatCanBeMovedTo++;
		Game.getAllNeighbourSquaresThatCanBeMovedTo++;
		ArrayList<Square> squares = new ArrayList<Square>(Square.class);

		for (Square square : neighbors) {
			if (square.includableInPath(actor, goalSquare, false)) {
				squares.add(square);
			}
		}
		return squares;
	}

	public float getEstimatedCost(Square node) {
		Game.getEstimatedCost++;
		return this.straightLineDistanceTo(node) + this.pathCostForAI - 1;
	}

	public List getNeighborsThatCanBeMovedTo(Actor actor) {
		return null;
	}

	public boolean includableInPath(Actor actor, Square goalSquare, boolean nodeAsking) {
		Game.includableInPath++;

		// if
		if (actor == Game.level.player && !this.seenByPlayer)
			return true;

		if (this.restricted && actor != Game.level.player && !this.owners.contains(actor)) {
			return false;
		}

		// Stop path moving in to nodes we dont want (either needs to be linked
		// to current sqr or target sqr

		if (!nodeAsking && this == goalSquare && actor.straightLineDistanceTo(this) > 1) {
			return true;
		}

		// if (!nodeAsking) {
		//
		//
		//
		// if (this.node != null && !goalSquare.nodes.contains(this.node)
		// && !actor.squareGameObjectIsOn.nodes.contains(this.node))
		// return false;
		// }

		// if (this == goalSquare && this.node == null)
		// return true;

		if (inventory.canShareSquare) {
			// doors
			if (inventory.door != null) {
				if (!inventory.door.open && (!actor.canOpenDoors || inventory.door instanceof RemoteDoor)) {
					return false;
				} else if (inventory.door.locked && !actor.hasKeyForDoor(inventory.door)) {
					return false;
				} else {
					return true;
				}
			}
			return true;
		} else if (inventory.actor != null) {
			return true;
		}

		return false;
	}
	/// END PATH FINDING

	public boolean restricted() {
		if (restricted)
			return true;

		if (restrictedAtNight && (Game.level.hour < 6 || Game.level.hour > 21)) {
			return true;
		}

		return false;
	}

	public static boolean squareExists(int x, int y) {

		if (x >= 0 && y >= 0 && x < Game.level.squares.length && y < Game.level.squares[0].length)
			return true;

		return false;
	}

	public Texture getFloorImageTexture() {
		return floorImageTexture;
	}

	public void setFloorImageTexture(Texture floorImageTexture) {
		if (defaultImageTexture != null && floorImageTexture != defaultImageTexture) {
		}
		this.floorImageTexture = floorImageTexture;
		updateSquaresToSave();
	}

	public void updateSquaresToSave() {

		if (inventory.size() > 0) {
			if (!Level.squaresToSave.contains(this)) {
				Level.squaresToSave.add(this);
			}
		} else if (seenByPlayer) {
			if (!Level.squaresToSave.contains(this)) {
				Level.squaresToSave.add(this);
			}
		} else if (floorImageTexture != defaultImageTexture) {
			if (!Level.squaresToSave.contains(this)) {
				Level.squaresToSave.add(this);
			}
		} else {
			Level.squaresToSave.remove(this);
		}

	}

	@Override
	public Long getId() {
		return id;
	}

	public Liquid liquidSpread(Liquid templateLiquid) {

		final int maxDepth = 10;

		ArrayList<Square> squaresAtCurrentLevel = new ArrayList<Square>(Square.class);
		ArrayList<Square> squaresAtNextLevel = new ArrayList<Square>(Square.class);
		squaresAtNextLevel.add(this);
		for (int i = 0; i < maxDepth; i++) {
			squaresAtCurrentLevel.clear();
			squaresAtCurrentLevel.addAll(squaresAtNextLevel);
			Collections.shuffle(squaresAtCurrentLevel);
			squaresAtNextLevel.clear();
			for (Square squareToPotentiallySpreadTo : squaresAtCurrentLevel) {
				if (!squareToPotentiallySpreadTo.inventory
						.containsGameObjectWithTemplateId(Templates.WATER_BODY.templateId)
						&& !squareToPotentiallySpreadTo.inventory
								.containsGameObjectWithTemplateId(Templates.VOID_HOLE.templateId)
						&& !squareToPotentiallySpreadTo.inventory.containsGameObjectOfType(Wall.class)) {
					if (squareToPotentiallySpreadTo.inventory
							.containsGameObjectWithTemplateId(templateLiquid.templateId)) {
						for (Square squareAtNextLevel : squareToPotentiallySpreadTo.getAllSquaresAtDistance(1)) {
							if (!squaresAtNextLevel.contains(squareAtNextLevel)) {
								squaresAtNextLevel.add(squareAtNextLevel);
							}
						}
					} else {
						squareToPotentiallySpreadTo.inventory.removeGameObjecsOfType(Liquid.class);
						Liquid liquidToSpread = templateLiquid.makeCopy(null, null);
						liquidToSpread.setPrimaryAnimation(
								new AnimationLiquidSpread(liquidToSpread, squareToPotentiallySpreadTo, null));
						squareToPotentiallySpreadTo.inventory.add(liquidToSpread);
						return liquidToSpread;
					}
				}
			}
		}
		return null;
	}
}
