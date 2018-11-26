package com.marklynch.level.squares;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.lwjgl.input.Keyboard;

import com.marklynch.Game;
import com.marklynch.GameCursor;
import com.marklynch.level.Level;
import com.marklynch.level.UserInputLevel;
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
import com.marklynch.objects.GameObject;
import com.marklynch.objects.HidingPlace;
import com.marklynch.objects.Landmine;
import com.marklynch.objects.MapMarker;
import com.marklynch.objects.Portal;
import com.marklynch.objects.PressurePlate;
import com.marklynch.objects.RemoteDoor;
import com.marklynch.objects.Stump;
import com.marklynch.objects.Tree;
import com.marklynch.objects.VoidHole;
import com.marklynch.objects.Wall;
import com.marklynch.objects.WaterBody;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actions.ActionAttack;
import com.marklynch.objects.actions.ActionDigging;
import com.marklynch.objects.actions.ActionDropItems;
import com.marklynch.objects.actions.ActionHide;
import com.marklynch.objects.actions.ActionMove;
import com.marklynch.objects.actions.ActionOpenInventoryToDropItems;
import com.marklynch.objects.actions.ActionOpenInventoryToThrowItems;
import com.marklynch.objects.actions.ActionPlaceMapMarker;
import com.marklynch.objects.actions.ActionPourContainerInInventory;
import com.marklynch.objects.actions.ActionStopHiding;
import com.marklynch.objects.actions.ActionTakeAll;
import com.marklynch.objects.actions.ActionTeleport;
import com.marklynch.objects.actions.ActionTeleportSwap;
import com.marklynch.objects.actions.ActionThrowItem;
import com.marklynch.objects.actions.ActionViewInfo;
import com.marklynch.objects.actions.ActionWait;
import com.marklynch.objects.actions.ActionableInWorld;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.Human;
import com.marklynch.objects.weapons.Weapon;
import com.marklynch.ui.button.Tooltip;
import com.marklynch.utils.ArrayUtils;
import com.marklynch.utils.Color;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.ResourceUtils;
import com.marklynch.utils.Texture;
import com.marklynch.utils.TextureUtils;
import com.marklynch.utils.Utils.Point;

public class Square implements ActionableInWorld, InventoryParent, Comparable<Square> {

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
	public transient Texture floorImageTexture = null;
	public static Texture GRASS_TEXTURE;
	public static Texture DARK_GRASS_TEXTURE;
	public static Texture STONE_TEXTURE;
	public static Texture MUD_TEXTURE;
	public static Texture WATER_TEXTURE;
	public static Texture GREY_TEXTURE;
	public static Texture WHITE_SQUARE;
	public static Texture YELLOW_SQUARE;
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

	public transient boolean showingDialogs = false;

	public transient Area areaSquareIsIn;
	public transient Structure structureSquareIsIn;
	public transient StructureSection structureSectionSquareIsIn;
	public transient StructureRoom structureRoomSquareIsIn;

	public boolean highlight = false;
	public boolean drawPathDot = false;
	public boolean drawEndPathDot = false;
	// public boolean drawX = false;

	public ArrayList<Actor> owners;
	public boolean restricted;
	public boolean restrictedAtNight;
	public String name;
	public boolean flash;

	// path finding
	public ArrayList<Node> nodes;

	public Square pathParent;
	public float costFromStart;
	public float estimatedCostToGoal;

	public float cost = 1;
	public float costForPlayer = 1;

	public int xInGrid;
	public int yInGrid;

	public float xInGridPixels;
	public float yInGridPixels;

	public ArrayList<Square> neighbors;
	// end path finding
	public Node node;

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
			this.floorImageTexture = imageTexture;

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

	public static final HashMap<Texture, Color> tileColors = new HashMap<Texture, Color>();

	// public static TooltipGroup tooltipGroup = null;

	public static void loadStaticImages() {
		GRASS_TEXTURE = ResourceUtils.getGlobalImage("grass.png", false);
		tileColors.put(GRASS_TEXTURE, Color.GREEN);
		DARK_GRASS_TEXTURE = ResourceUtils.getGlobalImage("dark_grass.png", false);
		tileColors.put(DARK_GRASS_TEXTURE, Color.GREEN);
		STONE_TEXTURE = ResourceUtils.getGlobalImage("stone.png", false);
		tileColors.put(STONE_TEXTURE, Color.LIGHT_GRAY);
		MUD_TEXTURE = ResourceUtils.getGlobalImage("mud.png", false);
		tileColors.put(MUD_TEXTURE, Color.ORANGE);
		WATER_TEXTURE = ResourceUtils.getGlobalImage("water_tile.png", false);
		tileColors.put(WATER_TEXTURE, Color.BLUE);
		GREY_TEXTURE = ResourceUtils.getGlobalImage("square.png", false);
		tileColors.put(GREY_TEXTURE, Color.GRAY);
		WHITE_SQUARE = ResourceUtils.getGlobalImage("white_square.png", false);
		tileColors.put(WHITE_SQUARE, Color.WHITE);
		YELLOW_SQUARE = ResourceUtils.getGlobalImage("yellow_square.png", false);
		tileColors.put(YELLOW_SQUARE, Color.YELLOW);
		RED_SQUARE = ResourceUtils.getGlobalImage("red_square.png", false);
		tileColors.put(RED_SQUARE, Color.RED);
		VOID_SQUARE = ResourceUtils.getGlobalImage("void_hole.png", false);
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
		weaponsThatCanAttack = new ArrayList<Weapon>();

		loadImages();
	}

	public void postLoad2() {
		inventory.postLoad2();
	}

	public void loadImages() {
		this.floorImageTexture = getGlobalImage(imageTexturePath, false);

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

		if (Game.zoomLevelIndex == Game.zoomLevels.length - 1) {
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
		// return
	}

	public GameObject getGameObjectMouseIsOver() {

		// if (!this.seenByPlayer)
		// return null;

		ArrayList<GameObject> gameObjectsToCheck = new ArrayList<GameObject>();

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

			GameObject gameObject = gameObjectsToCheck.get(i);
			if (gameObject instanceof Discoverable) {
				Discoverable d = (Discoverable) gameObject;
				if (!d.discovered)
					continue;
			}

			if (gameObject instanceof WaterBody || gameObject instanceof Wall || gameObject.isFloorObject) {
				continue;
			}

			if (!gameObject.squareGameObjectIsOn.visibleToPlayer && !gameObject.persistsWhenCantBeSeen)
				continue;

			// gameObject.imageTexture.getTexture().

			int x = 0;
			if (gameObject.backwards) {
				x = (int) (gameObject.squareGameObjectIsOn.xInGridPixels
						+ Game.SQUARE_WIDTH * -gameObject.drawOffsetRatioX);
			} else {
				x = (int) (gameObject.squareGameObjectIsOn.xInGridPixels
						+ Game.SQUARE_WIDTH * gameObject.drawOffsetRatioX);
			}

			// int x = (int) (gameObject.squareGameObjectIsOn.xInGridPixels
			// + Game.SQUARE_WIDTH * gameObject.drawOffsetRatioX);
			int y = (int) (gameObject.squareGameObjectIsOn.yInGridPixels
					+ Game.SQUARE_HEIGHT * gameObject.drawOffsetRatioY);
			if (gameObject.getPrimaryAnimation() != null) {
				x += gameObject.getPrimaryAnimation().offsetX;
				y += gameObject.getPrimaryAnimation().offsetY;
			}

			Point point = new Point((int) (UserInputLevel.mouseXTransformed - x),
					(int) (UserInputLevel.mouseYTransformed - y));

			if (gameObject.backwards) {
				// how do i flip it?
				point.x = gameObject.width - point.x;
			}

			if (gameObject.getPrimaryAnimation() != null && gameObject.getPrimaryAnimation().torsoAngle != 0
					&& gameObject instanceof Human) {

				point = rotatePoint(gameObject.halfWidth, ((Human) gameObject).hipY,
						-gameObject.getPrimaryAnimation().torsoAngle, point);
			}

			// FirstCheckBounding box :P
			if (point.x > 0 && point.x < gameObject.width && point.y > 0 && point.y < gameObject.height) {
				Color color = null;
				if (gameObject instanceof Human) {

					Human human = (Human) gameObject;

					color = getPixel(human.torsoImageTexture, (int) point.x, (int) point.y);
					if (color == null || color.a == 0) {
						color = getPixel(human.pelvisImageTexture, (int) point.x, (int) point.y);
					}
				} else {
					color = getPixel(gameObject.imageTexture, (int) point.x, (int) point.y);
				}

				if (color != null && color.a > 0) {
					return gameObject;
				}
			}
		}
		return null;

	}

	Point rotatePoint(float cx, float cy, float angle, Point p) {
		double s = Math.sin(angle);
		double c = Math.cos(angle);

		// translate point back to origin:
		p.x -= cx;
		p.y -= cy;

		// rotate point
		double xnew = p.x * c - p.y * s;
		double ynew = p.x * s + p.y * c;

		// translate point back:
		p.x = (float) (xnew + cx);
		p.y = (float) (ynew + cy);
		return p;
	}

	public Color getPixel(Texture texture, int x, int y) {

		if (texture == null || texture.pixels == null)
			return null;
		// in method
		if (x < 0 || y < 0)
			return null;

		if (x > texture.getWidth() - 1 || y > texture.getHeight() - 1) {
			return null;
		}

		int index = (x + y * texture.getWidth());
		if (index + 3 >= texture.pixels.length)
			return null;

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

	public Action getSecondaryActionForTheSquareOrObject(Actor performer, boolean keyPress) {

		GameObject targetGameObject = this.inventory.gameObjectThatCantShareSquare;
		if (keyPress == false && targetGameObject != null) {
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
		ArrayList<Action> actions = new ArrayList<Action>();
		// Move, teleport, loiter
		if (this != Game.level.player.squareGameObjectIsOn) {
			actions.add(new ActionMove(performer, this, true));
			actions.add(new ActionTeleport(performer, performer, this, true, true));
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
			actions.add(new ActionOpenInventoryToThrowItems(performer, null, this));

		// Pour from inventory
		actions.add(new ActionPourContainerInInventory(performer, null, this));

		GameObject diggable = this.inventory.getDiggable();
		if (diggable != null) {
			actions.add(new ActionDigging(performer, diggable));
		} else {
			actions.add(new ActionDigging(performer, this));
		}

		if (!this.inventory.contains(MapMarker.class))
			actions.add(new ActionPlaceMapMarker(this));

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
		ArrayList<Square> squares = new ArrayList<Square>();
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

		if (inventory.contains(Landmine.class)) {
			Landmine landmine = (Landmine) inventory.getGameObjectOfClass(Landmine.class);
			if (landmine.discovered) {// This is dumb, there's only a flag for player discovered... needs a change...
				cost = 10;
				return;
			}
		}

		if (inventory.contains(BrokenGlass.class)) {
			cost = 10;
		} else if (inventory.contains(Actor.class)) {
			cost = 10;
		} else if (inventory.contains(Portal.class)) {
			cost = 10;
		} else if (inventory.contains(VoidHole.class)) {
			cost = 10;
		} else if (inventory.contains(PressurePlate.class)) {
			cost = 10;
		} else if (this.floorImageTexture == Square.STONE_TEXTURE) {
			cost = 1;
		} else {
			cost = 2;
		}
	}

	public void calculatePathCostForPlayer() {

		if (inventory.contains(Landmine.class)) {
			Landmine landmine = (Landmine) inventory.getGameObjectOfClass(Landmine.class);
			if (landmine.discovered) {
				cost = 10;
				return;
			}
		}

		if (inventory.contains(BrokenGlass.class)) {
			costForPlayer = 10;
			// } else if (inventory.contains(Actor.class)) {
			// costForPlayer = 10;
		} else if (inventory.contains(Portal.class)) {
			costForPlayer = 10;
		} else if (inventory.contains(VoidHole.class)) {
			costForPlayer = 10;
		} else if (inventory.contains(PressurePlate.class)) {
			costForPlayer = 10;
		} else if (this.floorImageTexture == Square.STONE_TEXTURE) {
			costForPlayer = 1;
		} else {
			costForPlayer = 2;
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
		ArrayList<Square> squares = new ArrayList<Square>();

		for (Square square : neighbors) {
			if (square.includableInPath(actor, goalSquare, false)) {
				squares.add(square);
			}
		}
		return squares;
	}

	public float getEstimatedCost(Square node) {
		Game.getEstimatedCost++;
		return this.straightLineDistanceTo(node) + this.cost - 1;
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
}
