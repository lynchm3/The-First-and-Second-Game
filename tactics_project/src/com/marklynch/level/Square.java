package com.marklynch.level;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.UUID;
import java.util.Vector;

import com.marklynch.Game;
import com.marklynch.level.constructs.Building;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.SquareInventory;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actions.ActionMove;
import com.marklynch.objects.actions.Actionable;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.weapons.Weapon;
import com.marklynch.utils.TextUtils;
import com.marklynch.utils.TextureUtils;

import mdesl.graphics.Texture;

public class Square implements Actionable {

	public String guid = UUID.randomUUID().toString();
	public final static String[] editableAttributes = { "elevation", "travelCost", "imageTexture" };

	public final int xInGrid;
	public final int yInGrid;
	public final int elevation;
	public int travelCost;
	public SquareInventory inventory;
	public boolean showInventory;

	public transient boolean reachableBySelectedCharater = false;
	public transient boolean inPath = false;
	public transient Vector<Weapon> weaponsThatCanAttack;

	// image
	public String imageTexturePath;
	public transient Texture imageTexture = null;

	public transient boolean showingDialogs = false;
	public transient int walkingDistanceToSquare = Integer.MAX_VALUE;

	public transient static PathComparator pathComparator;

	public transient Building building;

	public Square(int x, int y, String imagePath, int travelCost, int elevation, SquareInventory inventory) {
		super();
		this.xInGrid = x;
		this.yInGrid = y;
		this.imageTexturePath = imagePath;
		this.travelCost = travelCost;
		this.elevation = elevation;
		loadImages();
		weaponsThatCanAttack = new Vector<Weapon>();
		this.inventory = inventory;
		if (this.inventory != null)
			this.inventory.square = this;
		showInventory = true;
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

	public void draw() {

		// square texture
		int squarePositionX = xInGrid * (int) Game.SQUARE_WIDTH;
		int squarePositionY = yInGrid * (int) Game.SQUARE_HEIGHT;
		// QuadUtils.drawQuad(new Color(0.2f, 0.4f, 0.1f, 1.0f),
		// squarePositionX, squarePositionX + Game.SQUARE_WIDTH,
		// squarePositionY, squarePositionY + Game.SQUARE_HEIGHT);
		TextureUtils.drawTexture(imageTexture, squarePositionX, squarePositionX + Game.SQUARE_WIDTH, squarePositionY,
				squarePositionY + Game.SQUARE_HEIGHT);

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
		int costTextWidth = Game.font.getWidth("" + walkingDistanceToSquare);
		float costPositionX = squarePositionX + (Game.SQUARE_WIDTH - costTextWidth) / 2f;
		float costPositionY = squarePositionY + (Game.SQUARE_HEIGHT - 60) / 2f;

		if (walkingDistanceToSquare != Integer.MAX_VALUE && Game.level.activeActor != null) {
			TextUtils.printTextWithImages(new Object[] { "" + walkingDistanceToSquare }, costPositionX, costPositionY,
					Integer.MAX_VALUE, true);
		}
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

	}

	public void drawHighlight() {

		int squarePositionX = xInGrid * (int) Game.SQUARE_WIDTH;
		int squarePositionY = yInGrid * (int) Game.SQUARE_HEIGHT;
		TextureUtils.drawTexture(Game.level.gameCursor.imageTexture2, squarePositionX,
				squarePositionX + Game.SQUARE_WIDTH, squarePositionY, squarePositionY + Game.SQUARE_HEIGHT);

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
			return targetGameObject.getDefaultAction(performer);
		} else {
			return getDefaultAction(performer);

		}
	}

	@Override
	public Action getDefaultAction(Actor performer) {
		if (this.reachableBySelectedCharater) {
			return new ActionMove(performer, this);
		} else {
			return null;
		}
	}

	@Override
	public ArrayList<Action> getAllActions(Actor performer) {
		ArrayList<Action> actions = new ArrayList<Action>();
		actions.add(new ActionMove(performer, this));
		return actions;
	}
}
