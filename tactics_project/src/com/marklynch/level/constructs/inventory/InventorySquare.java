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
import com.marklynch.objects.tools.Axe;
import com.marklynch.objects.units.Actor;
import com.marklynch.utils.QuadUtils;
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

	public transient ArrayList<GameObject> stack = new ArrayList<GameObject>();

	static Color translucentBlack = new Color(0.5f, 0f, 0f, 0f);

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
		if (!objectLegal(gameObject)) {
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

			if (inventoryThisBelongsTo == Game.level.player.inventory && gameObject instanceof Axe) {
				System.out.println("" + gameObject + ", owner = " + gameObject.owner);
			}

			// Count && value
			if (objectLegal(gameObject)) {
				if (this instanceof GroundDisplaySquare)
					stack = GroundDisplay.legalStacks.get(gameObject.templateId);
				else
					stack = this.inventoryThisBelongsTo.legalStacks.get(gameObject.templateId);
			} else {
				if (this instanceof GroundDisplaySquare)
					stack = GroundDisplay.illegalStacks.get(gameObject.templateId);
				else
					stack = this.inventoryThisBelongsTo.illegalStacks.get(gameObject.templateId);
			}

			if (inventoryThisBelongsTo == Game.level.player.inventory && gameObject instanceof Axe) {
				System.out.println("stack = " + stack);
			}

			if (Inventory.inventoryMode == INVENTORY_MODE.MODE_TRADE) {

				Color goldTextColor = Color.WHITE;

				if (!this.gameObject.getDefaultActionPerformedOnThisInInventory(Game.level.player).enabled) {
					goldTextColor = Color.RED;
				}

				String goldTextString = "" + gameObject.value;
				int goldTextLength = Game.font.getWidth(goldTextString);
				StringWithColor goldTextStringWithColor = new StringWithColor(goldTextString, goldTextColor);

				// String amtString =

				QuadUtils.drawQuad(Color.BLACK, xInPixels + Game.INVENTORY_SQUARE_WIDTH - goldTextLength - 10 - 16,
						yInPixels + Game.INVENTORY_SQUARE_HEIGHT - 27, xInPixels + Game.INVENTORY_SQUARE_WIDTH,
						yInPixels + Game.INVENTORY_SQUARE_HEIGHT);

				TextUtils.printTextWithImages(xInPixels + Game.INVENTORY_SQUARE_WIDTH - goldTextLength - 10,
						yInPixels + Game.INVENTORY_SQUARE_HEIGHT - 27, Integer.MAX_VALUE, false, null,
						new Object[] { goldTextStringWithColor });

				TextureUtils.drawTexture(Inventory.textureGold,
						xInPixels + Game.INVENTORY_SQUARE_WIDTH - goldTextLength - 10 - 16,
						yInPixels + Game.INVENTORY_SQUARE_HEIGHT - 27,
						xInPixels + Game.INVENTORY_SQUARE_WIDTH - goldTextLength - 10,
						yInPixels + Game.INVENTORY_SQUARE_HEIGHT - 27 + 16);
			}

			if (gameObject instanceof Gold) {
				String amtString = gameObject.value + "x";
				QuadUtils.drawQuad(Color.BLACK, xInPixels, yInPixels, xInPixels + 10 + Game.font.getWidth(amtString),
						yInPixels + 7 + 20);
				TextUtils.printTextWithImages(xInPixels + 10, yInPixels + 7, Integer.MAX_VALUE, false, null,
						new Object[] { amtString });

			} else {
				String amtString = stack.size() + "x";
				QuadUtils.drawQuad(Color.BLACK, xInPixels, yInPixels, xInPixels + 10 + Game.font.getWidth(amtString),
						yInPixels + 7 + 20);
				TextUtils.printTextWithImages(xInPixels + 10, yInPixels + 7, Integer.MAX_VALUE, false, null,
						new Object[] { amtString });

			}

			// Star
			if (this.inventoryThisBelongsTo == Game.level.player.inventory && gameObject.starred) {
				TextureUtils.drawTexture(Inventory.textureStar, xInPixels + Game.INVENTORY_SQUARE_WIDTH - 24,
						yInPixels + 8, xInPixels + Game.INVENTORY_SQUARE_WIDTH - 8, yInPixels + 24);

			}

			gameObject.inventorySquare = this;
		}

	}

	private boolean objectLegal(GameObject gameObject) {
		if (this instanceof GroundDisplaySquare) {
			return GroundDisplay.objectLegal(gameObject);
		} else {
			return this.inventoryThisBelongsTo.objectLegal(gameObject);
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
	public Action drawActionThatWillBePerformed(boolean onMouse) {
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
		GameObject targetGameObject = this.gameObject;
		if (targetGameObject != null) {
			return targetGameObject.getDefaultActionPerformedOnThisInInventory(performer);
		}
		return null;
	}

	@Override
	public Action getSecondaryActionForTheSquareOrObject(Actor performer) {
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
