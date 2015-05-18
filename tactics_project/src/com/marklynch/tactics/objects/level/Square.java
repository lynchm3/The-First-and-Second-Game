package com.marklynch.tactics.objects.level;

import static com.marklynch.utils.Resources.getGlobalImage;

import java.util.Comparator;
import java.util.Vector;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import com.marklynch.Dialog;
import com.marklynch.Game;
import com.marklynch.tactics.objects.GameObject;
import com.marklynch.tactics.objects.unit.Actor;
import com.marklynch.tactics.objects.weapons.Weapon;

public class Square {

	public final int x;
	public final int y;
	public final int elevation;
	public int travelCost;
	public boolean reachableBySelectedCharater = false;
	public boolean inPath = false;
	public Vector<Weapon> weaponsThatCanAttack = new Vector<Weapon>();

	// image
	public String imagePath;
	public Texture imageTexture = null;
	public GameObject gameObject = null;
	public Vector<Dialog> dialogs;

	public boolean showingDialogs = false;
	public Vector<Vector<Square>> pathsToSquare = new Vector<Vector<Square>>();
	public int distanceToSquare = 0;

	public static PathComparator pathComparator;

	public Square(int x, int y, String imagePath, int travelCost, int elevation) {
		super();
		this.x = x;
		this.y = y;
		this.imagePath = imagePath;
		this.imageTexture = getGlobalImage(imagePath);
		this.travelCost = travelCost;
		this.dialogs = new Vector<Dialog>();
		this.elevation = elevation;
		if (pathComparator == null)
			pathComparator = new PathComparator();
	}

	public void draw(Level level) {

		// square texture
		imageTexture.bind();

		int squarePositionX = x * (int) Game.SQUARE_WIDTH;
		int squarePositionY = y * (int) Game.SQUARE_HEIGHT;

		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex2f(squarePositionX, squarePositionY);
		GL11.glTexCoord2f(1, 0);
		GL11.glVertex2f(squarePositionX + Game.SQUARE_WIDTH, squarePositionY);
		GL11.glTexCoord2f(1, 1);
		GL11.glVertex2f(squarePositionX + Game.SQUARE_WIDTH, squarePositionY
				+ Game.SQUARE_HEIGHT);
		GL11.glTexCoord2f(0, 1);
		GL11.glVertex2f(squarePositionX, squarePositionY + Game.SQUARE_HEIGHT);
		GL11.glEnd();

		// square highlights

		if (reachableBySelectedCharater || weaponsThatCanAttack.size() > 0) {

			if (inPath || this == Game.squareMouseIsOver)
				level.gameCursor.imageTexture3.bind();
			else if (reachableBySelectedCharater)
				level.gameCursor.imageTexture.bind();
			else
				level.gameCursor.imageTexture2.bind();

			GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex2f(squarePositionX, squarePositionY);
			GL11.glTexCoord2f(1, 0);
			GL11.glVertex2f(squarePositionX + Game.SQUARE_WIDTH,
					squarePositionY);
			GL11.glTexCoord2f(1, 1);
			GL11.glVertex2f(squarePositionX + Game.SQUARE_WIDTH,
					squarePositionY + Game.SQUARE_HEIGHT);
			GL11.glTexCoord2f(0, 1);
			GL11.glVertex2f(squarePositionX, squarePositionY
					+ Game.SQUARE_HEIGHT);
			GL11.glEnd();
		}

		if (this.reachableBySelectedCharater) {
			level.font.drawString(squarePositionX, squarePositionY, "cost "
					+ distanceToSquare, Color.black);
			GL11.glColor3f(1.0f, 1.0f, 1.0f);
		}

	}

	public void drawDialogs() {

		for (Dialog dialog : dialogs) {
			dialog.draw();
		}

	}

	public void showDialogs() {
		dialogs.add(new Dialog(this, 200, 200, "dialogbg.png",
				"KeepCalm-Medium.ttf"));
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
					"lvl" + actor.level + " " + actor.title,
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

	public void calculateDistanceToSquare() {
		if (pathsToSquare.size() > 0) {
			distanceToSquare = 0;
			pathsToSquare.sort(pathComparator);

			Vector<Square> shortestPath = pathsToSquare.get(0);

			for (Square square : shortestPath) {
				distanceToSquare += square.travelCost;
			}
		}

	}
}
