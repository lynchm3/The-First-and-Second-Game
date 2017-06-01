package com.marklynch.level;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

import com.marklynch.Game;
import com.marklynch.ai.utils.AStarNode;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.structure.Structure;
import com.marklynch.level.constructs.structure.StructureRoom;
import com.marklynch.level.constructs.structure.StructureSection;
import com.marklynch.objects.BrokenGlass;
import com.marklynch.objects.Door;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.HidingPlace;
import com.marklynch.objects.InventoryParent;
import com.marklynch.objects.SquareInventory;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actions.ActionDrop;
import com.marklynch.objects.actions.ActionHide;
import com.marklynch.objects.actions.ActionMove;
import com.marklynch.objects.actions.ActionTakeAll;
import com.marklynch.objects.actions.ActionThrow;
import com.marklynch.objects.actions.ActionableInWorld;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.weapons.Weapon;
import com.marklynch.utils.ArrayUtils;
import com.marklynch.utils.TextureUtils;

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

	public transient boolean showingDialogs = false;
	// public transient int walkingDistanceToSquare = Integer.MAX_VALUE;

	public transient static PathComparator pathComparator;

	public transient Structure structureSquareIsIn;
	public transient StructureSection structureSectionSquareIsIn;
	public transient StructureRoom structureRoomSquareIsIn;

	public ArrayList<Sound> sounds = new ArrayList<Sound>();

	public boolean highlight = false;

	public ArrayList<Actor> owners;
	public boolean restricted;

	public Square(int x, int y, String imagePath, int travelCost, int elevation, SquareInventory inventory,
			boolean restricted, Actor... owners) {
		super();
		this.xInGrid = x;
		this.yInGrid = y;
		this.imageTexturePath = imagePath;
		this.travelCost = travelCost;
		this.elevation = elevation;
		loadImages();
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

		if (!Game.fullVisiblity) {

			if (!this.seenByPlayer)
				return;
		}

		// square texture
		int squarePositionX = xInGrid * (int) Game.SQUARE_WIDTH;
		int squarePositionY = yInGrid * (int) Game.SQUARE_HEIGHT;
		// QuadUtils.drawQuad(new Color(0.2f, 0.4f, 0.1f, 1.0f),
		// squarePositionX, squarePositionX + Game.SQUARE_WIDTH,
		// squarePositionY, squarePositionY + Game.SQUARE_HEIGHT);
		float alpha = 1f;
		if (!this.visibleToPlayer)
			alpha = 0.25f;
		TextureUtils.drawTexture(imageTexture, alpha, squarePositionX, squarePositionX + Game.SQUARE_WIDTH,
				squarePositionY, squarePositionY + Game.SQUARE_HEIGHT);

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
		TextureUtils.drawTexture(Game.level.gameCursor.imageTexture2, squarePositionX,
				squarePositionX + Game.SQUARE_WIDTH, squarePositionY, squarePositionY + Game.SQUARE_HEIGHT);

	}

	public void drawRedHighlight() {

		int squarePositionX = xInGrid * (int) Game.SQUARE_WIDTH;
		int squarePositionY = yInGrid * (int) Game.SQUARE_HEIGHT;
		TextureUtils.drawTexture(Game.level.gameCursor.imageTexture4, squarePositionX,
				squarePositionX + Game.SQUARE_WIDTH, squarePositionY, squarePositionY + Game.SQUARE_HEIGHT);

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
		// GL11.glTranslatef(Game.dragX, Game.dragY, 0);
		// GL11.glTranslatef(-Game.windowWidth / 2, -Game.windowHeight / 2, 0);
		int squarePositionX = xInGrid * (int) Game.SQUARE_WIDTH;
		int squarePositionY = yInGrid * (int) Game.SQUARE_HEIGHT;

		TextureUtils.drawTexture(Game.level.gameCursor.cursor, squarePositionX, squarePositionX + Game.SQUARE_WIDTH,
				squarePositionY, squarePositionY + Game.SQUARE_HEIGHT);
		// GL11.glPopMatrix();
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
		GameObject targetGameObject = this.inventory.getGameObjectThatCantShareSquare();
		if (targetGameObject != null) {
			return targetGameObject.getDefaultActionPerformedOnThisInWorld(performer);
		} else {
			return getDefaultActionPerformedOnThisInWorld(performer);

		}
	}

	@Override
	public Action getDefaultActionPerformedOnThisInWorld(Actor performer) {

		if (this == Game.level.player.squareGameObjectIsOn) {
			return null;
		} else if (performer.travelDistance >= performer.straightLineDistanceTo(this)) {

			HidingPlace hidingPlace = (HidingPlace) this.inventory.getGameObjectOfClass(HidingPlace.class);
			if (hidingPlace != null) {
				return new ActionHide(performer, hidingPlace);
			} else {
				return new ActionMove(performer, this);
			}

		} else {
			return null;
		}

	}

	@Override
	public ArrayList<Action> getAllActionsPerformedOnThisInWorld(Actor performer) {
		ArrayList<Action> actions = new ArrayList<Action>();
		if (this != Game.level.player.squareGameObjectIsOn) {
			actions.add(new ActionMove(performer, this));
		}

		if (this.inventory.size() > 0 && this.inventory.hasGameObjectsThatFitInInventory()) {
			actions.add(new ActionTakeAll(performer, this));
		}

		HidingPlace hidingPlace = (HidingPlace) this.inventory.getGameObjectOfClass(HidingPlace.class);
		if (hidingPlace != null) {
			actions.add(new ActionHide(performer, hidingPlace));
		}

		if (performer.equipped != null) {
			if (performer.straightLineDistanceTo(this) < 2) {
				actions.add(new ActionDrop(performer, this, performer.equipped));
			}
			actions.add(new ActionThrow(performer, this, performer.equipped));
		}

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

	public boolean includableInPath() {
		if (inventory.canShareSquare()) {

			GameObject gameObjectDoor = inventory.getGameObjectOfClass(Door.class);
			if (gameObjectDoor instanceof Door) {
				Door door = (Door) gameObjectDoor;
				if (door.isLocked() && !Game.level.activeActor.hasKeyForDoor(door)) {
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

	public Vector<Square> getAllNeighbourSquaresThatCanBeMovedTo() {
		Vector<Square> squares = new Vector<Square>();
		Square square;
		// +1,0
		if (ArrayUtils.inBounds(Game.level.squares, this.xInGrid + 1, this.yInGrid)) {
			square = Game.level.squares[this.xInGrid + 1][this.yInGrid];
			if (square.includableInPath()) {
				squares.add(square);
			}
		}
		// -1,0
		if (ArrayUtils.inBounds(Game.level.squares, this.xInGrid - 1, this.yInGrid)) {
			square = Game.level.squares[this.xInGrid - 1][this.yInGrid];
			if (square.includableInPath()) {
				squares.add(square);
			}
		}
		// 0,+1
		if (ArrayUtils.inBounds(Game.level.squares, this.xInGrid, this.yInGrid + 1)) {
			square = Game.level.squares[this.xInGrid][this.yInGrid + 1];
			if (square.includableInPath()) {
				squares.add(square);
			}
		}
		// 0,-1
		if (ArrayUtils.inBounds(Game.level.squares, this.xInGrid, this.yInGrid - 1)) {
			square = Game.level.squares[this.xInGrid][this.yInGrid - 1];
			if (square.includableInPath()) {
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
	public List getNeighbors() {
		return getAllNeighbourSquaresThatCanBeMovedTo();
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
}
