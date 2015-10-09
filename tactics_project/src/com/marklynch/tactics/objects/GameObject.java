package com.marklynch.tactics.objects;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.Vector;

import mdesl.graphics.Texture;

import org.newdawn.slick.openal.Audio;

import com.marklynch.Game;
import com.marklynch.tactics.objects.level.Faction;
import com.marklynch.tactics.objects.level.Square;
import com.marklynch.tactics.objects.unit.Actor.Direction;
import com.marklynch.tactics.objects.unit.Path;
import com.marklynch.tactics.objects.weapons.Weapon;
import com.marklynch.tactics.objects.weapons.Weapons;
import com.marklynch.utils.ArrayUtils;
import com.marklynch.utils.ResourceUtils;
import com.marklynch.utils.TextureUtils;

public class GameObject {

	public final static String[] editableAttributes = { "name", "imageTexture",
			"weapons", "strength", "dexterity", "intelligence", "endurance",
			"totalHealth", "remainingHealth" };
	public String guid = UUID.randomUUID().toString();

	public String name = "";

	// attributes
	public int strength = 0;
	public int dexterity = 0;
	public int intelligence = 0;
	public int endurance = 0;
	public float totalHealth = 0;
	public float remainingHealth = 0;

	public transient boolean hasAttackedThisTurn = false;

	// Inventory
	public Weapons weapons = new Weapons();

	// Interaction with the level
	public Square squareGameObjectIsOn = null;

	// image
	public transient Texture imageTexture = null;
	public String imageTexturePath = null;

	// images
	public static transient Texture powTexture = null;
	public static transient Texture vsTexture = null;
	public transient Texture fightTexture = null;
	public transient Texture skullTexture = null;
	public transient Texture xTexture = null;
	public transient Texture upTexture = null;
	public transient Texture downTexture = null;
	public transient Texture armTexture = null;
	public static transient Texture grassNormalTexture = null;
	public static transient Texture skipNormalTexture = null;

	// sounds
	public static transient Audio screamAudio = null;

	// paths
	public transient HashMap<Square, Path> paths = new HashMap<Square, Path>();

	// POW
	public transient GameObject powTarget = null;
	public transient boolean showPow = false;

	public transient Faction faction;

	public GameObject(String name, int health, int strength, int dexterity,
			int intelligence, int endurance, String imagePath,
			Square squareGameObjectIsOn, ArrayList<Weapon> weapons) {
		super();
		this.name = name;
		this.totalHealth = health;
		this.remainingHealth = health;
		this.strength = strength;
		this.dexterity = dexterity;
		this.intelligence = intelligence;
		this.endurance = endurance;
		this.imageTexturePath = imagePath;
		this.squareGameObjectIsOn = squareGameObjectIsOn;
		this.squareGameObjectIsOn.gameObject = this;
		this.weapons = new Weapons();
		this.weapons.weapons = weapons;

		loadImages();
	}

	public void loadImages() {
		this.imageTexture = getGlobalImage(imageTexturePath);
		this.powTexture = getGlobalImage("pow.png");
		this.vsTexture = getGlobalImage("vs.png");
		this.fightTexture = getGlobalImage("fight.png");
		this.skullTexture = getGlobalImage("skull.png");
		this.xTexture = getGlobalImage("x.png");
		this.upTexture = getGlobalImage("up.png");
		this.downTexture = getGlobalImage("down.png");
		this.armTexture = getGlobalImage("arm.png");
		grassNormalTexture = getGlobalImage("grass_NRM.png");
		skipNormalTexture = getGlobalImage("skip_with_shadow_NRM.png");
		screamAudio = ResourceUtils.getGlobalSound("scream.wav");
		for (Weapon weapon : this.weapons.weapons) {
			weapon.loadImages();
		}

	}

	public void postLoad(Faction faction) {
		this.faction = faction;
		this.squareGameObjectIsOn = Game.level.squares[this.squareGameObjectIsOn.x][this.squareGameObjectIsOn.y];
		this.squareGameObjectIsOn.gameObject = this;
		this.paths = new HashMap<Square, Path>();
	}

	public void drawForeground() {

		// Draw object
		int actorPositionXInPixels = this.squareGameObjectIsOn.x
				* (int) Game.SQUARE_WIDTH;
		int actorPositionYInPixels = this.squareGameObjectIsOn.y
				* (int) Game.SQUARE_HEIGHT;

		float alpha = 1.0f;
		if (Game.level.activeActor != null
				&& Game.level.activeActor.showHoverFightPreview == true
				&& Game.level.activeActor.hoverFightPreviewDefender != this) {
			alpha = 0.5f;
		}

		if (hasAttackedThisTurn == true && this.faction != null
				&& Game.level.currentFactionMoving == this.faction) {
			alpha = 0.5f;
		}

		TextureUtils.skipNormals = true;
		TextureUtils.drawTexture(imageTexture, alpha, actorPositionXInPixels,
				actorPositionXInPixels + Game.SQUARE_WIDTH,
				actorPositionYInPixels, actorPositionYInPixels
						+ Game.SQUARE_HEIGHT);
		TextureUtils.skipNormals = false;
	}

	public void drawUI() {

		// Draw POW
		if (showPow == true) {
			int powPositionXInPixels = Math
					.abs((powTarget.squareGameObjectIsOn.x * (int) Game.SQUARE_WIDTH));
			int powPositionYInPixels = powTarget.squareGameObjectIsOn.y
					* (int) Game.SQUARE_HEIGHT;

			TextureUtils.drawTexture(this.powTexture, powPositionXInPixels,
					powPositionXInPixels + Game.SQUARE_WIDTH,
					powPositionYInPixels, powPositionYInPixels
							+ Game.SQUARE_HEIGHT);

		}
	}

	public boolean checkIfDestroyed() {
		if (remainingHealth <= 0) {
			this.squareGameObjectIsOn.gameObject = null;
			// Game.level.inanimateObjects.remove(this);

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

	public Vector<Square> getAllSquaresAtDistance(float distance) {
		Vector<Square> squares = new Vector<Square>();

		boolean xGoingUp = true;
		boolean yGoingUp = true;
		for (float i = 0, x = -distance, y = 0; i < distance * 4; i++) {
			if (ArrayUtils.inBounds(Game.level.squares,
					this.squareGameObjectIsOn.x + x,
					this.squareGameObjectIsOn.y + y)) {
				squares.add(Game.level.squares[this.squareGameObjectIsOn.x
						+ (int) x][this.squareGameObjectIsOn.y + (int) y]);
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

	public void showPow(GameObject target) {
		powTarget = target;
		showPow = true;
		new HidePowThread(this).start();
	}

	public class HidePowThread extends Thread {

		GameObject gameObject;

		public HidePowThread(GameObject gameObject) {
			this.gameObject = gameObject;
		}

		@Override
		public void run() {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			showPow = false;
		}
	}

	public Weapon bestCounterWeapon(GameObject attacker, Weapon attackerWeapon,
			float range) {
		for (Weapon weapon : weapons.weapons) {
			if (range >= weapon.minRange && range <= weapon.maxRange) {
				return weapon;
			}
		}
		return null;
	}

	public GameObject makeCopy(Square square) {

		ArrayList<Weapon> weaponArray = new ArrayList<Weapon>();
		for (Weapon weapon : this.weapons.weapons) {
			weaponArray.add(weapon.makeCopy());
		}
		return new GameObject(name, (int) totalHealth, strength, dexterity,
				intelligence, endurance, imageTexturePath, square, weaponArray);
	}

	public void update(int delta) {
		// TODO Auto-generated method stub

	}
}
