package com.marklynch.level.constructs.inventory;

import java.util.ArrayList;
import java.util.Vector;

import org.lwjgl.input.Keyboard;

import com.marklynch.Game;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.constructs.inventory.Inventory.INVENTORY_MODE;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Gold;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.units.Actor;
import com.marklynch.utils.StringWithColor;
import com.marklynch.utils.TextUtils;
import com.marklynch.utils.TextureUtils;

import mdesl.graphics.Color;
import mdesl.graphics.Texture;

public class InventorySquare extends Square {

	public transient GameObject gameObject;
	public transient Inventory inventoryThisBelongsTo;

	public int xInPixels = 0;
	public int yInPixels = 0;

	public static Texture imageTexture;

	public InventorySquare(int x, int y, String imagePath, Inventory inventoryThisBelongsTo) {
		super(x, y, imagePath, 1, 1, null, false);

		this.showInventory = false;
		this.inventoryThisBelongsTo = inventoryThisBelongsTo;

		if (inventoryThisBelongsTo != null) {
			xInPixels = Math.round(inventoryThisBelongsTo.squaresX + xInGrid * Game.INVENTORY_SQUARE_WIDTH);
			yInPixels = Math.round(inventoryThisBelongsTo.squaresY + yInGrid * Game.INVENTORY_SQUARE_HEIGHT);
		}
	}

	@Override
	public void loadImages() {
		// this.imageTexture = getGlobalImage(imageTexturePath);

	}

	@Override
	public void postLoad1() {

	}

	@Override
	public void postLoad2() {

	}

	public void drawStaticUI() {

		// square texture
		Texture squareTexture = WHITE_SQUARE;

		// Red border on sqr if illegal to take
		if (Inventory.inventoryMode != INVENTORY_MODE.MODE_TRADE
				&& (inventoryThisBelongsTo == null || inventoryThisBelongsTo.parent != Game.level.player)
				&& this.gameObject.owner != null && this.gameObject.owner != Game.level.player) {
			squareTexture = RED_SQUARE;
		}

		// Yellow border on sqr if item is equipped
		if (this.gameObject != null && (Game.level.player.equipped == this.gameObject
				|| Game.level.player.helmet == this.gameObject || Game.level.player.bodyArmor == this.gameObject
				|| Game.level.player.legArmor == this.gameObject)) {
			squareTexture = YELLOW_SQUARE;
		}

		TextureUtils.drawTexture(squareTexture, xInPixels, yInPixels, xInPixels + Game.INVENTORY_SQUARE_WIDTH,
				yInPixels + Game.INVENTORY_SQUARE_HEIGHT);

		if (gameObject != null) {

			float drawWidth = Game.INVENTORY_SQUARE_WIDTH;
			float drawHeight = Game.INVENTORY_SQUARE_HEIGHT;
			float realTextureWidth = gameObject.imageTexture.getWidth();
			float realTextureHeight = gameObject.imageTexture.getHeight();
			if (realTextureWidth >= realTextureHeight) {// knife
				drawHeight = Game.INVENTORY_SQUARE_HEIGHT * realTextureHeight / realTextureWidth;
			} else {
				drawWidth = Game.INVENTORY_SQUARE_WIDTH * realTextureWidth / realTextureHeight;
			}
			// TextureUtils.skipNormals = false;
			TextureUtils.drawTexture(gameObject.imageTexture, xInPixels, yInPixels, xInPixels + drawWidth,
					yInPixels + drawHeight);

			for (Effect effect : gameObject.activeEffectsOnGameObject) {
				TextureUtils.drawTexture(effect.imageTexture, 0.75f, xInPixels, yInPixels,
						xInPixels + Game.INVENTORY_SQUARE_WIDTH, yInPixels + Game.INVENTORY_SQUARE_HEIGHT);
			}

			// Count && value
			int count = 1;
			if (this instanceof GroundDisplaySquare)
				count = GroundDisplay.itemTypeCount.get(gameObject.name);
			else
				count = this.inventoryThisBelongsTo.itemTypeCount.get(gameObject.name);
			if (Inventory.inventoryMode == INVENTORY_MODE.MODE_TRADE) {

				Color goldTextColor = Color.WHITE;

				if (!this.gameObject.getDefaultActionPerformedOnThisInInventory(Game.level.player).enabled) {
					goldTextColor = Color.RED;
				}

				StringWithColor goldText = new StringWithColor(count + "x" + gameObject.value, goldTextColor);
				TextUtils.printTextWithImages(xInPixels + 10, yInPixels + 7, Integer.MAX_VALUE, false,
						new Object[] { goldText });
			} else if (gameObject instanceof Gold) {
				TextUtils.printTextWithImages(xInPixels + 10, yInPixels + 7, Integer.MAX_VALUE, false,
						new Object[] { gameObject.value });

			} else if (count > 1) {
				TextUtils.printTextWithImages(xInPixels + 10, yInPixels + 7, Integer.MAX_VALUE, false,
						new Object[] { count + "x" });

			}
		}

	}

	@Override
	public void drawHighlight() {

		TextureUtils.drawTexture(Game.level.gameCursor.imageTexture2, xInPixels, yInPixels,
				xInPixels + Game.INVENTORY_SQUARE_WIDTH, yInPixels + Game.INVENTORY_SQUARE_HEIGHT);

	}

	@Override
	public void drawCursor() {
		TextureUtils.drawTexture(Game.level.gameCursor.cursor, xInPixels, yInPixels,
				xInPixels + Game.INVENTORY_SQUARE_WIDTH, yInPixels + Game.INVENTORY_SQUARE_HEIGHT);
	}

	@Override
	public Action drawAction(boolean onMouse) {
		Action defaultAction;
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
			defaultAction = this.getSecondaryActionForTheSquareOrObject(Game.level.player);
		} else {
			defaultAction = this.getDefaultActionForTheSquareOrObject(Game.level.player);
		}

		if (defaultAction != null && defaultAction.image != null) {

			Color color = Color.BLACK;
			if (defaultAction.legal == false)
				color = Color.RED;
			TextureUtils.drawTexture(defaultAction.image, xInPixels + Game.INVENTORY_SQUARE_WIDTH / 2,
					yInPixels + Game.INVENTORY_SQUARE_HEIGHT / 2, xInPixels + Game.INVENTORY_SQUARE_WIDTH,
					yInPixels + Game.INVENTORY_SQUARE_HEIGHT, color);
		}
		return defaultAction;
	}

	@Override
	public String toString() {
		return "" + this.xInGrid + "," + this.yInGrid;

	}

	public boolean calculateIfPointInBoundsOfSquare(float mouseX, float mouseY) {
		if (mouseX > xInPixels && mouseX < xInPixels + (int) Game.INVENTORY_SQUARE_WIDTH && mouseY > yInPixels
				&& mouseY < yInPixels + (int) Game.INVENTORY_SQUARE_HEIGHT) {
			return true;
		}
		return false;
	}

	@Override
	public Action getDefaultActionForTheSquareOrObject(Actor performer) {
		// System.out.println("InventorySquare.getDefaultActionForTheSquareOrObject");
		// System.out.println("this = " + this);
		// System.out.println("this instanceof GroundDisplaySquare = " + (this
		// instanceof GroundDisplaySquare));
		GameObject targetGameObject = this.gameObject;
		if (targetGameObject != null) {
			return targetGameObject.getDefaultActionPerformedOnThisInInventory(performer);
		}
		return null;
	}

	@Override
	public Action getSecondaryActionForTheSquareOrObject(Actor performer) {
		// System.out.println("InventorySquare.getDefaultActionForTheSquareOrObject");
		// System.out.println("this = " + this);
		// System.out.println("this instanceof GroundDisplaySquare = " + (this
		// instanceof GroundDisplaySquare));
		GameObject targetGameObject = this.gameObject;
		if (targetGameObject != null) {
			return targetGameObject.getSecondaryActionPerformedOnThisInInventory(performer);
		}
		return null;
	}

	public ArrayList<Action> getAllActionsForTheSquareOrObject(Actor performer) {
		GameObject targetGameObject = this.gameObject;
		if (targetGameObject != null) {
			if (this.inventoryThisBelongsTo == Game.level.player.inventory)
				return targetGameObject.getAllActionsPerformedOnThisInInventory(performer);
			else
				return targetGameObject.getAllActionsPerformedOnThisInOtherInventory(performer);

		}
		return new ArrayList<Action>();
	}

	@Override
	public Action getDefaultActionPerformedOnThisInWorld(Actor performer) {
		return null;
	}

	@Override
	public ArrayList<Action> getAllActionsPerformedOnThisInWorld(Actor performer) {
		ArrayList<Action> actions = new ArrayList<Action>();
		return actions;
	}

	public Vector<InventorySquare> getAllInventorySquaresAtDistance(float distance) {
		Vector<InventorySquare> squares = new Vector<InventorySquare>();
		if (distance == 0) {
			squares.addElement(this);
			return squares;
		}

		boolean xGoingUp = true;
		boolean yGoingUp = true;
		for (float i = 0, x = -distance, y = 0; i < distance * 4; i++) {

			for (InventorySquare inventorySquare : inventoryThisBelongsTo.inventorySquares) {
				if (inventorySquare.xInGrid == x && inventorySquare.yInGrid == y) {
					squares.add(inventorySquare);
					break;
				}
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
}
