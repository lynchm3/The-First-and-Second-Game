package com.marklynch.tactics.objects.level;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import java.util.Comparator;
import java.util.Vector;

import mdesl.graphics.Texture;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import com.marklynch.Game;
import com.marklynch.tactics.objects.GameObject;
import com.marklynch.tactics.objects.unit.Actor;
import com.marklynch.tactics.objects.weapons.Weapon;
import com.marklynch.ui.Dialog;
import com.marklynch.utils.TextUtils;
import com.marklynch.utils.TextureUtils;

public class Square {

	public final int x;
	public final int y;
	public final int elevation;
	public int travelCost;
	public transient boolean reachableBySelectedCharater = false;
	public transient boolean inPath = false;
	public transient Vector<Weapon> weaponsThatCanAttack;

	// image
	public String imagePath;
	public transient Texture imageTexture = null;
	public transient GameObject gameObject = null;
	public transient Vector<Dialog> dialogs;

	public transient boolean showingDialogs = false;
	public transient int distanceToSquare = Integer.MAX_VALUE;

	public transient static PathComparator pathComparator;

	public transient Level level;

	public Square(int x, int y, String imagePath, int travelCost,
			int elevation, Level level) {
		super();
		this.x = x;
		this.y = y;
		this.imagePath = imagePath;
		this.travelCost = travelCost;
		this.elevation = elevation;
		this.level = level;
		loadImages();
		weaponsThatCanAttack = new Vector<Weapon>();
		dialogs = new Vector<Dialog>();
	}

	public void loadImages() {
		this.imageTexture = getGlobalImage(imagePath);

	}

	public void postLoad(Level level) {
		this.level = level;
		weaponsThatCanAttack = new Vector<Weapon>();
		dialogs = new Vector<Dialog>();
	}

	public void draw(Level level) {

		// square texture
		int squarePositionX = x * (int) Game.SQUARE_WIDTH;
		int squarePositionY = y * (int) Game.SQUARE_HEIGHT;
		TextureUtils.drawTexture(imageTexture, squarePositionX, squarePositionX
				+ Game.SQUARE_WIDTH, squarePositionY, squarePositionY
				+ Game.SQUARE_HEIGHT);

		// square highlights

		if (reachableBySelectedCharater || weaponsThatCanAttack.size() > 0) {

			Texture highlightTexture = null;

			if (level.activeActor != null
					&& level.activeActor.equippedWeapon != null
					&& this.gameObject != null
					&& level.activeActor.equippedWeapon
							.hasRange(level.activeActor.weaponDistanceTo(this))
					&& !level.activeActor.hasAttackedThisTurn) {
				highlightTexture = level.gameCursor.imageTexture4;
			} else if (level.currentFactionMovingIndex == 0 && (inPath))// ||
																		// this
																		// ==
																		// Game.squareMouseIsOver))
				highlightTexture = level.gameCursor.imageTexture3;
			else if (reachableBySelectedCharater)
				highlightTexture = level.gameCursor.imageTexture;
			else
				highlightTexture = level.gameCursor.imageTexture2;
			TextureUtils.drawTexture(highlightTexture, squarePositionX,
					squarePositionX + Game.SQUARE_WIDTH, squarePositionY,
					squarePositionY + Game.SQUARE_HEIGHT);
		}

		// if (this.reachableBySelectedCharater) {
		int costTextWidth = GameObject.font.getWidth("" + distanceToSquare);
		float costPositionX = squarePositionX
				+ (Game.SQUARE_WIDTH - costTextWidth) / 2f;
		float costPositionY = squarePositionY + (Game.SQUARE_HEIGHT - 60) / 2f;

		if (distanceToSquare != Integer.MAX_VALUE && level.activeActor != null) {
			TextUtils.printTextWithImages(
					new Object[] { "" + distanceToSquare }, costPositionX,
					costPositionY);
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
		// get the instance of the view matrix for our batch
		Matrix4f view = GameObject.batch.getViewMatrix();

		// reset the matrix to identity, i.e. "no camera transform"

		GameObject.batch.flush();
		view.setIdentity();

		view.translate(new Vector2f(Game.windowWidth / 2, Game.windowHeight / 2));
		view.scale(new Vector3f(Game.zoom, Game.zoom, 1f));
		view.translate(new Vector2f(-Game.windowWidth / 2,
				-Game.windowHeight / 2));
		view.translate(new Vector2f(Game.dragX, Game.dragY));

		// update the new view matrix
		GameObject.batch.updateUniforms();

		int squarePositionX = x * (int) Game.SQUARE_WIDTH;
		int squarePositionY = y * (int) Game.SQUARE_HEIGHT;
		TextureUtils.drawTexture(level.gameCursor.imageTexture2,
				squarePositionX, squarePositionX + Game.SQUARE_WIDTH,
				squarePositionY, squarePositionY + Game.SQUARE_HEIGHT);

		// reset the matrix to identity, i.e. "no camera transform"

		GameObject.batch.flush();
		view.setIdentity();
		GameObject.batch.updateUniforms();
	}

	public void drawCursor() {
		// GL11.glPushMatrix();

		// GL11.glTranslatef(Game.windowWidth / 2, Game.windowHeight / 2, 0);
		// GL11.glScalef(Game.zoom, Game.zoom, 0);
		// GL11.glTranslatef(Game.dragX, Game.dragY, 0);
		// GL11.glTranslatef(-Game.windowWidth / 2, -Game.windowHeight / 2, 0);
		int squarePositionX = x * (int) Game.SQUARE_WIDTH;
		int squarePositionY = y * (int) Game.SQUARE_HEIGHT;

		TextureUtils.drawTexture(level.gameCursor.cursor, squarePositionX,
				squarePositionX + Game.SQUARE_WIDTH, squarePositionY,
				squarePositionY + Game.SQUARE_HEIGHT);
		// GL11.glPopMatrix();
	}

	public void drawDialogs() {

		for (Dialog dialog : dialogs) {
			dialog.draw();
		}

	}

	public void showDialogs() {
		dialogs.add(new Dialog(this, 200, 200, "dialogbg.png",
				"KeepCalm-Medium.ttf", level));
		showingDialogs = true;
	}

	public void clearDialogs() {
		dialogs.clear();
		showingDialogs = false;
	}

	public String[] getDetails() {

		if (this.gameObject == null) {
			// Nothing on the square
			return new String[] { "" + x + " , " + y,
					"\nTravel Cost = " + travelCost,
					"\nElevation = " + elevation, "(Click again to dismiss)" };
		} else if (this.gameObject instanceof Actor) {
			// Actor on the square
			Actor actor = (Actor) this.gameObject;
			return new String[] { "" + x + " , " + y,
					"\nTravel Cost = " + travelCost,
					"\nElevation = " + elevation, "" + actor.name,
					"lvl" + actor.actorLevel + " " + actor.title,
					"(Click again to dismiss)" };

		} else {
			// Object on the square
			return new String[] { "" + x + " , " + y,
					"\nTravel Cost = " + travelCost,
					"\nElevation = " + elevation, "(Click again to dismiss)" };

		}
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
		return "" + this.x + "," + this.y;

	}
}
