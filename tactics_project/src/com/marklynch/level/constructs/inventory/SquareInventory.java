package com.marklynch.level.constructs.inventory;

import java.util.ArrayList;
import java.util.Comparator;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.Door;
import com.marklynch.objects.Floor;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.PressurePlate;
import com.marklynch.objects.VoidHole;
import com.marklynch.objects.WaterBody;
import com.marklynch.objects.Window;
import com.marklynch.objects.actions.ActionSmash;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.Fish;

public class SquareInventory extends Inventory implements Comparator<GameObject> {

	public transient Square square;

	public boolean canShareSquare = true;
	public GameObject gameObjectThatCantShareSquare = null;
	public Actor actor = null;
	public Door door = null;
	public WaterBody waterBody = null;
	public ArrayList<GameObject> gameObjectsGround = new ArrayList<GameObject>();
	public ArrayList<GameObject> gameObjectsNonGround = new ArrayList<GameObject>();

	@Override
	public void postLoad1() {
	}

	@Override
	public void postLoad2() {

		super.postLoad2();
		for (GameObject gameObject : gameObjects) {
			gameObject.postLoad2();
		}
	}

	@Override
	public void add(GameObject gameObject) {
		if (!gameObjects.contains(gameObject)) {

			// Remove references with square
			if (gameObject.squareGameObjectIsOn != null)
				gameObject.squareGameObjectIsOn.inventory.remove(gameObject);
			gameObject.squareGameObjectIsOn = null;

			// Remove from ground squares index
			// Game.level.inanimateObjectsOnGround.remove(gameObject);

			// Remove from another gameObjects inventory
			if (gameObject.inventoryThatHoldsThisObject != null)
				gameObject.inventoryThatHoldsThisObject.remove(gameObject);

			// add to this inventory
			gameObjects.add(gameObject);
			gameObject.inventoryThatHoldsThisObject = this;
			gameObject.squareGameObjectIsOn = square;

			if (gameObject.isFloorObject) {
				gameObjectsGround.add(gameObject);
			} else {
				gameObjectsNonGround.add(gameObject);
			}

			if (!Game.level.inanimateObjectsOnGround.contains(gameObject) && !(gameObject instanceof Actor))
				Game.level.inanimateObjectsOnGround.add(gameObject);

			this.gameObjects.sort(this);

			if (gameObject == Level.player) {
				Level.player.calculateVisibleSquares(Level.player.squareGameObjectIsOn);
				Level.player.peekSquare = null;
			}

			refresh();
		}
	}

	@Override
	public int remove(GameObject gameObject) {
		if (gameObjects.contains(gameObject)) {
			gameObjects.remove(gameObject);

			if (gameObject.isFloorObject) {
				gameObjectsGround.remove(gameObject);
			} else {
				gameObjectsNonGround.remove(gameObject);
			}

			refresh();
		}
		return -1;
	}

	public void refresh() {

		square.calculatePathCost();
		square.calculatePathCostForPlayer();

		updateStacks();
		matchStacksToSquares();

		canShareSquare = canShareSquare();
		gameObjectThatCantShareSquare = getGameObjectThatCantShareSquare1();
		if (gameObjectThatCantShareSquare instanceof Actor)
			actor = (Actor) gameObjectThatCantShareSquare;
		else
			actor = null;
		door = (Door) getGameObjectOfClass(Door.class);
		waterBody = (WaterBody) getGameObjectOfClass(WaterBody.class);

		PressurePlate pressurePlate = (PressurePlate) getGameObjectOfClass(PressurePlate.class);
		if (pressurePlate != null)
			pressurePlate.updateWeight();

	}

	private boolean canShareSquare() {
		for (GameObject gameObject : gameObjects) {
			if (gameObject != null && !gameObject.canShareSquare)
				return false;
		}
		return true;
	}

	public boolean canBeMovedTo() {
		if (canShareSquare) {
			return true;
		} else {
			if (contains(Actor.class))
				return true;
		}
		return false;
	}

	public ArrayList<GameObject> getGameObjectsThatFitInInventory() {
		ArrayList<GameObject> gameObjectsThatFitInInventory = new ArrayList<GameObject>();
		for (GameObject gameObject : gameObjects) {
			if (gameObject.fitsInInventory)
				gameObjectsThatFitInInventory.add(gameObject);
		}
		return gameObjectsThatFitInInventory;
	}

	public boolean hasGameObjectsThatFitInInventory() {
		for (GameObject gameObject : gameObjects) {
			if (gameObject.fitsInInventory)
				return true;
		}
		return false;
	}

	public boolean blocksLineOfSight() {
		for (GameObject gameObject : gameObjects) {
			if (gameObject.blocksLineOfSight)
				return true;
		}
		return false;
	}

	public void smashWindows(GameObject smasher) {
		for (GameObject gameObject : gameObjects) {
			if (gameObject.remainingHealth > 0 && gameObject instanceof Window) {
				new ActionSmash(smasher, gameObject).perform();

			}
		}

	}

	public float getSoundDampening() {
		float soundDampening = 1;

		for (GameObject gameObject : gameObjects) {
			if (gameObject.remainingHealth > 0 && gameObject.soundDampening > soundDampening) {
				soundDampening = gameObject.soundDampening;
			}
		}
		return soundDampening;
	}

	@Override
	public int compare(GameObject a, GameObject b) {

		if (a instanceof VoidHole && b instanceof VoidHole) {
			return 0;
		} else if (a instanceof VoidHole)

		{
			return -1;
		} else if (b instanceof VoidHole) {
			return 1;
		}

		if (a instanceof Floor && b instanceof Floor) {
			return 0;
		} else if (a instanceof Floor)

		{
			return -1;
		} else if (b instanceof Floor) {
			return 1;
		}

		if (a instanceof PressurePlate && b instanceof PressurePlate) {
			return 0;
		} else if (a instanceof PressurePlate)

		{
			return -1;
		} else if (b instanceof PressurePlate) {
			return 1;
		}

		if (a instanceof WaterBody && b instanceof WaterBody) {
			return 0;
		} else if (a instanceof WaterBody)

		{
			return 1;
		} else if (b instanceof WaterBody) {
			return -1;
		}

		if (a instanceof Fish && b instanceof Fish) {
			return 0;
		} else if (a instanceof Fish)

		{
			return 1;
		} else if (b instanceof Fish) {
			return -1;
		}

		return (int) (a.height - b.height);
	}

	public int getDecorativeCount() {
		int count = 0;
		for (GameObject gameObject : gameObjects) {
			if (gameObject.decorative) {
				count++;
			}
		}

		return count;
	}

	public GameObject getNonDecorativeGameObject() {
		for (GameObject gameObject : gameObjects) {
			if (!gameObject.decorative) {
				return gameObject;
			}
		}
		return null;

	}

	public ArrayList<GameObject> getGameObjectsGround() {
		return gameObjectsGround;
	}

	public void setGameObjectsGround(ArrayList<GameObject> gameObjectsGround) {
		this.gameObjectsGround = gameObjectsGround;
	}

	public ArrayList<GameObject> getGameObjectsNonGround() {
		return gameObjectsNonGround;
	}

	public void setGameObjectsNonGround(ArrayList<GameObject> gameObjectsNonGround) {
		this.gameObjectsNonGround = gameObjectsNonGround;
	}

}
