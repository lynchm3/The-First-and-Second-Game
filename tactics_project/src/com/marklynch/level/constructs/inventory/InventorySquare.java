package com.marklynch.level.constructs.inventory;

import org.lwjgl.input.Keyboard;

import com.marklynch.Game;
import com.marklynch.actions.Action;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.constructs.inventory.Inventory.INVENTORY_MODE;
import com.marklynch.level.constructs.rarity.Rarity;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.inanimateobjects.Gold;
import com.marklynch.utils.CopyOnWriteArrayList;
import com.marklynch.utils.Color;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.StringWithColor;
import com.marklynch.utils.TextUtils;
import com.marklynch.utils.Texture;
import com.marklynch.utils.TextureUtils;

public class InventorySquare extends Square {

	public static Color defaultBackgroundColor = new Color(50, 50, 50);
	public static Color equippedBackgroundColor = new Color(200, 200, 200);
	public static Color illegalBackgroundColor = new Color(200, 50, 50);
	public static Color equippedAndIllegalBackgroundColor = new Color(250, 100, 100);

	public transient Inventory inventoryThisBelongsTo;

	public int xInPixels = 0;
	public int yInPixels = 0;

	public static Texture imageTexture;

	public transient CopyOnWriteArrayList<GameObject> stack = new CopyOnWriteArrayList<GameObject>(GameObject.class);

	public InventorySquare(int x, int y, String imagePath, Inventory inventoryThisBelongsTo) {
		super(x, y, imagePath, 1, 1, null, false);

		this.inventoryThisBelongsTo = inventoryThisBelongsTo;

		if (inventoryThisBelongsTo != null) {
			xInPixels = Math.round(inventoryThisBelongsTo.squaresX + xInGrid * Game.INVENTORY_SQUARE_WIDTH);
			yInPixels = Math.round(inventoryThisBelongsTo.squaresY + yInGrid * Game.INVENTORY_SQUARE_HEIGHT);
		}
	}

	@Override
	public void postLoad1() {

	}

	@Override
	public void postLoad2() {

	}

	public void drawStaticUI() {

		// square texture
		Color squareOutlineColor = Rarity.COMMON.color;
		Color backgroundColor = getBackgroundColorForInventorySquare(stack.get(0));

		if (stack.get(0) != null) {
			squareOutlineColor = stack.get(0).rarity.color;
		}

		TextureUtils.drawTexture(GREY_TRANSLUCENT_SQUARE, xInPixels, yInPixels, xInPixels + Game.INVENTORY_SQUARE_WIDTH,
				yInPixels + Game.INVENTORY_SQUARE_HEIGHT, backgroundColor);
		TextureUtils.drawTexture(WHITE_SQUARE, xInPixels, yInPixels, xInPixels + Game.INVENTORY_SQUARE_WIDTH,
				yInPixels + Game.INVENTORY_SQUARE_HEIGHT, squareOutlineColor);

		if (stack.get(0) != null) {

			float drawWidth = Game.INVENTORY_SQUARE_WIDTH;
			float drawHeight = Game.INVENTORY_SQUARE_HEIGHT;
			float realTextureWidth = stack.get(0).imageTexture.getWidth();
			float realTextureHeight = stack.get(0).imageTexture.getHeight();
			if (realTextureWidth >= realTextureHeight) {// knife
				drawHeight = Game.INVENTORY_SQUARE_HEIGHT * realTextureHeight / realTextureWidth;
			} else {
				drawWidth = Game.INVENTORY_SQUARE_WIDTH * realTextureWidth / realTextureHeight;
			}
			// TextureUtils.skipNormals = false;
			TextureUtils.drawTexture(stack.get(0).imageTexture, xInPixels, yInPixels, xInPixels + drawWidth,
					yInPixels + drawHeight);

			for (Effect effect : stack.get(0).activeEffectsOnGameObject) {
				TextureUtils.drawTexture(effect.imageTexture, 0.75f, xInPixels, yInPixels,
						xInPixels + Game.INVENTORY_SQUARE_WIDTH, yInPixels + Game.INVENTORY_SQUARE_HEIGHT);
			}

			if (Inventory.inventoryMode == INVENTORY_MODE.MODE_TRADE) {

				Color goldTextColor = Color.WHITE;

				if (!this.stack.get(0).getDefaultActionPerformedOnThisInInventory(Game.level.player).enabled) {
					goldTextColor = Color.RED;
				}

				String goldTextString = "" + stack.get(0).value;
				int goldTextLength = Game.smallFont.getWidth(goldTextString);
				StringWithColor goldTextStringWithColor = new StringWithColor(goldTextString, goldTextColor);

				// String amtString =

				QuadUtils.drawQuad(Color.BLACK, xInPixels + Game.INVENTORY_SQUARE_WIDTH - goldTextLength - 10 - 16,
						yInPixels + Game.INVENTORY_SQUARE_HEIGHT - 27, xInPixels + Game.INVENTORY_SQUARE_WIDTH,
						yInPixels + Game.INVENTORY_SQUARE_HEIGHT);

				TextUtils.printTextWithImages(xInPixels + Game.INVENTORY_SQUARE_WIDTH - goldTextLength - 10,
						yInPixels + Game.INVENTORY_SQUARE_HEIGHT - 27, Integer.MAX_VALUE, false, null, Color.WHITE,
						1f, new Object[] { goldTextStringWithColor });

				TextureUtils.drawTexture(Inventory.textureGold,
						xInPixels + Game.INVENTORY_SQUARE_WIDTH - goldTextLength - 10 - 16,
						yInPixels + Game.INVENTORY_SQUARE_HEIGHT - 27,
						xInPixels + Game.INVENTORY_SQUARE_WIDTH - goldTextLength - 10,
						yInPixels + Game.INVENTORY_SQUARE_HEIGHT - 27 + 16);
			}

			if (stack.get(0) instanceof Gold) {
				String amtString = stack.get(0).value + "x";
				QuadUtils.drawQuad(Color.BLACK, xInPixels, yInPixels,
						xInPixels + 10 + Game.smallFont.getWidth(amtString), yInPixels + 7 + 20);
				TextUtils.printTextWithImages(xInPixels + 10, yInPixels + 7, Integer.MAX_VALUE, false, null,
						Color.WHITE, 1f, new Object[] { amtString });

			} else {
				String amtString = stack.size() + "x";
				QuadUtils.drawQuad(Color.BLACK, xInPixels, yInPixels,
						xInPixels + 10 + Game.smallFont.getWidth(amtString), yInPixels + 7 + 20);
				TextUtils.printTextWithImages(xInPixels + 10, yInPixels + 7, Integer.MAX_VALUE, false, null,
						Color.WHITE, 1f, new Object[] { amtString });

			}

			// Star
			if (this.inventoryThisBelongsTo == Game.level.player.inventory && stack.get(0).starred) {
				TextureUtils.drawTexture(Inventory.textureStar, xInPixels + Game.INVENTORY_SQUARE_WIDTH - 24,
						yInPixels + 8, xInPixels + Game.INVENTORY_SQUARE_WIDTH - 8, yInPixels + 24);

			}

			stack.get(0).inventorySquare = this;
		}

	}

	public static Color getBackgroundColorForInventorySquare(GameObject gameObject) {

		if (gameObject != null) {
			boolean equipped = (gameObject != null
					&& (Level.player.equipped == gameObject || Level.player.helmet == gameObject
							|| Level.player.bodyArmor == gameObject || Level.player.legArmor == gameObject));
			boolean illegal = !objectLegal(gameObject, gameObject.inventoryThatHoldsThisObject);
			if (equipped && illegal) {
				return equippedAndIllegalBackgroundColor;
			} else if (equipped) {
				return equippedBackgroundColor;
			} else if (illegal) {
				return illegalBackgroundColor;
			}

		}

		return defaultBackgroundColor;

	}

	private static boolean objectLegal(GameObject gameObject, Inventory inventoryThisBelongsTo) {
		return Inventory.objectLegal(gameObject, inventoryThisBelongsTo);
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
			defaultAction = this.getSecondaryActionForTheSquareOrObject(Level.player, false);
		} else {
			defaultAction = this.getDefaultActionForTheSquareOrObject(Level.player, false);
		}

		if (defaultAction != null && defaultAction.image != null) {

			Color color = Color.WHITE;
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
	public Action getDefaultActionForTheSquareOrObject(Actor performer, boolean keyPress) {

		GameObject targetGameObject = this.stack.get(0);
		if (targetGameObject != null) {
			return targetGameObject.getDefaultActionPerformedOnThisInInventory(performer);
		}
		return null;
	}

	@Override
	public Action getSecondaryActionForTheSquareOrObject(Actor performer, boolean keyPress) {
		GameObject targetGameObject = this.stack.get(0);
		if (targetGameObject != null) {
			return targetGameObject.getSecondaryActionPerformedOnThisInInventory(performer);
		}
		return null;
	}

	public CopyOnWriteArrayList<Action> getAllActionsForTheSquareOrObject(Actor performer) {
		GameObject targetGameObject = this.stack.get(0);
		if (targetGameObject != null) {
			if (this.inventoryThisBelongsTo == Game.level.player.inventory)
				return targetGameObject.getAllActionsPerformedOnThisInInventory(performer);
			else
				return targetGameObject.getAllActionsPerformedOnThisInOtherInventory(performer);

		}
		return new CopyOnWriteArrayList<Action>(Action.class);
	}

	@Override
	public Action getDefaultActionPerformedOnThisInWorld(Actor performer) {
		return null;
	}

	@Override
	public CopyOnWriteArrayList<Action> getAllActionsPerformedOnThisInWorld(Actor performer) {
		CopyOnWriteArrayList<Action> actions = new CopyOnWriteArrayList<Action>(Action.class);
		return actions;
	}

	public CopyOnWriteArrayList<InventorySquare> getAllInventorySquaresAtDistance(float distance) {
		CopyOnWriteArrayList<InventorySquare> squares = new CopyOnWriteArrayList<InventorySquare>(InventorySquare.class);
		if (distance == 0) {
			squares.add(this);
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
