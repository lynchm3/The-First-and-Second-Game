package com.marklynch.level.constructs.inventory;

import java.util.ArrayList;
import java.util.Comparator;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.Door;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.UpdatesWhenSquareContentsChange;
import com.marklynch.objects.WaterBody;
import com.marklynch.objects.Window;
import com.marklynch.objects.actions.ActionSmash;
import com.marklynch.objects.actors.Actor;

public class SquareInventory extends Inventory implements Comparator<GameObject> {

	public transient Square square;

	public boolean canShareSquare = true;
	public GameObject gameObjectThatCantShareSquare = null;
	public Actor actor = null;
	public Door door = null;
	public WaterBody waterBody = null;
	public ArrayList<GameObject> gameObjectsGround = new ArrayList<GameObject>();
	public ArrayList<GameObject> gameObjectsNonGround = new ArrayList<GameObject>();

	public SquareInventory() {

	}

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

			if (gameObject.lastSquare == null)
				gameObject.lastSquare = this.square;

			gameObject.squareGameObjectIsOn = null;

			// Remove from ground squares index
			// Game.level.inanimateObjectsOnGround.remove(gameObject);

			// Remove from another gameObjects inventory
			if (gameObject.inventoryThatHoldsThisObject != null)
				gameObject.inventoryThatHoldsThisObject.remove(gameObject);

			// add to this inventory
			gameObjects.add(gameObject);
			for (GameObject gameObjectsInInventory : gameObject.inventory.gameObjects) {
				gameObjectsInInventory.lastSquare = this.square;
			}

			gameObject.inventoryThatHoldsThisObject = this;
			gameObject.squareGameObjectIsOn = square;

			if (gameObject.isFloorObject) {
				gameObjectsGround.add(gameObject);
				this.gameObjectsGround.sort(this);
			} else {
				gameObjectsNonGround.add(gameObject);
				this.gameObjectsNonGround.sort(this);
			}

			if (!Game.level.inanimateObjectsOnGround.contains(gameObject) && !(gameObject instanceof Actor))
				Game.level.inanimateObjectsOnGround.add(gameObject);

			// this.gameObjects.sort(this);

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

			// if (gameObject instanceof VoidHole) {
			// System.out.println("Remove!");
			// Utils.printStackTrace();
			// }

			gameObject.lastSquare = this.square;
			gameObjects.remove(gameObject);

			if (gameObject.isFloorObject) {
				gameObjectsGround.remove(gameObject);
			} else {
				gameObjectsNonGround.remove(gameObject);
			}

			if (Game.level.inanimateObjectsOnGround.contains(gameObject) && !(gameObject instanceof Actor))
				Game.level.inanimateObjectsOnGround.remove(gameObject);

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

		UpdatesWhenSquareContentsChange updatesWhenSquareContentsChange = (UpdatesWhenSquareContentsChange) getGameObjectOfClass(
				UpdatesWhenSquareContentsChange.class);
		if (updatesWhenSquareContentsChange != null) {
			updatesWhenSquareContentsChange.squareContentsChanged();
		}

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

		if (a.orderingOnGound == b.orderingOnGound)
			return (int) (a.height - b.height);

		if (a.orderingOnGound != Integer.MAX_VALUE || b.orderingOnGound != Integer.MAX_VALUE)
			return a.orderingOnGound - b.orderingOnGound;

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

	@Override
	public String toString() {
		String string = "";
		string += "SquareInventory { ";
		string += "gameObjects [";
		for (GameObject gameObject : gameObjects) {
			string += "" + gameObject + ", ";
		}
		string += " ]";
		string += " }";
		return string;
	}

	public GameObject getDiggable() {
		for (GameObject gameObject : gameObjects) {
			if (gameObject.diggable)
				return gameObject;
		}
		return null;
	}

}
