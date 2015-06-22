package com.marklynch.tactics.objects;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import java.util.HashMap;
import java.util.Vector;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import com.marklynch.Game;
import com.marklynch.tactics.objects.level.Level;
import com.marklynch.tactics.objects.level.Square;
import com.marklynch.tactics.objects.unit.Actor.Direction;
import com.marklynch.tactics.objects.unit.Path;
import com.marklynch.tactics.objects.weapons.Weapon;

public class GameObject {

	public Level level;

	public String name = "";

	// attributes
	public int strength = 0;
	public int dexterity = 0;
	public int intelligence = 0;
	public int endurance = 0;
	public int totalHealth = 0;
	public int remainingHealth = 0;

	// Inventory
	public Vector<Weapon> weapons = new Vector<Weapon>();

	// Interaction with the level
	public Square squareGameObjectIsOn = null;

	// image
	public String imagePath = "";
	public Texture imageTexture = null;

	// paths
	public HashMap<Square, Path> paths = new HashMap<Square, Path>();

	public GameObject(String name, int health, int strength, int dexterity,
			int intelligence, int endurance, String imagePath,
			Square squareGameObjectIsOn, Vector<Weapon> weapons, Level level) {
		super();
		this.name = name;
		this.totalHealth = health;
		this.remainingHealth = health;
		this.strength = strength;
		this.dexterity = dexterity;
		this.intelligence = intelligence;
		this.endurance = endurance;
		this.imagePath = imagePath;
		this.imageTexture = getGlobalImage(imagePath);
		this.squareGameObjectIsOn = squareGameObjectIsOn;
		this.squareGameObjectIsOn.gameObject = this;
		this.weapons = weapons;
		this.level = level;
	}

	public void draw() {

		// Draw object
		this.imageTexture.bind();
		int actorPositionXInPixels = this.squareGameObjectIsOn.x
				* (int) Game.SQUARE_WIDTH;
		int actorPositionYInPixels = this.squareGameObjectIsOn.y
				* (int) Game.SQUARE_HEIGHT;

		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex2f(actorPositionXInPixels, actorPositionYInPixels);
		GL11.glTexCoord2f(1, 0);
		GL11.glVertex2f(actorPositionXInPixels + Game.SQUARE_WIDTH,
				actorPositionYInPixels);
		GL11.glTexCoord2f(1, 1);
		GL11.glVertex2f(actorPositionXInPixels + Game.SQUARE_WIDTH,
				actorPositionYInPixels + Game.SQUARE_HEIGHT);
		GL11.glTexCoord2f(0, 1);
		GL11.glVertex2f(actorPositionXInPixels, actorPositionYInPixels
				+ Game.SQUARE_HEIGHT);
		GL11.glEnd();

		// Draw health
		float healthWidthInPixels = Game.SQUARE_WIDTH / 2;
		float healthHeightInPixels = Game.SQUARE_HEIGHT / 5;

		float healthPositionXInPixels = (this.squareGameObjectIsOn.x * (int) Game.SQUARE_WIDTH)
				+ Game.SQUARE_WIDTH - healthWidthInPixels;
		float healthPositionYInPixels = this.squareGameObjectIsOn.y
				* (int) Game.SQUARE_HEIGHT;

		level.font12.drawString(healthPositionXInPixels, healthPositionYInPixels,
				"" + remainingHealth + "/" + totalHealth, Color.black);
		GL11.glColor3f(1.0f, 1.0f, 1.0f);

	}

	public boolean checkIfDestroyed() {
		if (remainingHealth <= 0) {
			this.squareGameObjectIsOn.gameObject = null;
			level.inanimateObjects.remove(this);
			return true;
		}
		return false;
	}

	int highestPathCostSeen = 0;

	public void calculatePathToAllSquares(Square[][] squares) {

		for (int i = 0; i < squares.length; i++) {
			for (int j = 0; j < squares.length; j++) {
				squares[i][j].distanceToSquare = Integer.MAX_VALUE;
			}
		}

		highestPathCostSeen = 0;
		paths.clear();
		Square currentSquare = squareGameObjectIsOn;
		currentSquare.distanceToSquare = 0;

		Vector<Square> startPath = new Vector<Square>();
		startPath.add(currentSquare);
		paths.put(currentSquare,
				new Path((Vector<Square>) startPath.clone(), 0));

		for (int i = 0; i <= highestPathCostSeen; i++) {
			// get all paths with that cost
			Vector<Path> pathsWithCurrentCost = new Vector<Path>();
			Vector<Path> pathsVector = new Vector<Path>();
			for (Path path : paths.values()) {
				pathsVector.add(path);
			}
			for (int j = 0; j < pathsVector.size(); j++) {
				if (pathsVector.get(j).travelCost == i)
					pathsWithCurrentCost.add(pathsVector.get(j));
			}

			for (int j = 0; j < pathsWithCurrentCost.size(); j++) {
				Vector<Square> squaresInThisPath = pathsWithCurrentCost.get(j).squares;
				calculatePathToAllSquares2(squares, Direction.UP,
						squaresInThisPath, i);
				calculatePathToAllSquares2(squares, Direction.RIGHT,
						squaresInThisPath, i);
				calculatePathToAllSquares2(squares, Direction.DOWN,
						squaresInThisPath, i);
				calculatePathToAllSquares2(squares, Direction.LEFT,
						squaresInThisPath, i);

			}
		}
	}

	public void calculatePathToAllSquares2(Square[][] squares,
			Direction direction, Vector<Square> squaresInThisPath, int pathCost) {

		Square newSquare = null;

		Square parentSquare = squaresInThisPath
				.get(squaresInThisPath.size() - 1);

		if (direction == Direction.UP) {
			if (parentSquare.y - 1 >= 0) {
				newSquare = squares[parentSquare.x][parentSquare.y - 1];
			}
		} else if (direction == Direction.RIGHT) {
			if (parentSquare.x + 1 < squares.length) {
				newSquare = squares[parentSquare.x + 1][parentSquare.y];
			}
		} else if (direction == Direction.DOWN) {

			if (parentSquare.y + 1 < squares[0].length) {
				newSquare = squares[parentSquare.x][parentSquare.y + 1];
			}
		} else if (direction == Direction.LEFT) {
			if (parentSquare.x - 1 >= 0) {
				newSquare = squares[parentSquare.x - 1][parentSquare.y];
			}
		}

		if (newSquare != null && newSquare.gameObject == null
				&& !squaresInThisPath.contains(newSquare)
				&& !paths.containsKey(newSquare)) {
			Vector<Square> newPathSquares = (Vector<Square>) squaresInThisPath
					.clone();
			newPathSquares.add(newSquare);
			int newDistance = pathCost + parentSquare.travelCost;
			newSquare.distanceToSquare = newDistance;
			if (newDistance > highestPathCostSeen)
				highestPathCostSeen = newDistance;
			Path newPath = new Path(newPathSquares, newDistance);
			paths.put(newSquare, newPath);
		}
	}
}
