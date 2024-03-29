package com.marklynch.level.constructs.inventory;

import java.util.Comparator;

import com.marklynch.actions.ActionSmash;
import com.marklynch.level.Level;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.Door;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.inanimateobjects.WaterBody;
import com.marklynch.objects.inanimateobjects.Window;
import com.marklynch.objects.utils.UpdatableGameObject;
import com.marklynch.utils.CopyOnWriteArrayList;

public class SquareInventory extends Inventory implements Comparator<GameObject> {

	public transient Square square;

	public boolean canShareSquare = true;
	public GameObject gameObjectThatCantShareSquare = null;
	public Actor actor = null;
	public Door door = null;
	public WaterBody waterBody = null;

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
			if (gameObject.isFloorObject) {
				floorObjects.add(gameObject);
			} else {
				nonFloorObjects.add(gameObject);
			}

			for (GameObject gameObjectsInInventory : gameObject.inventory.gameObjects) {
				gameObjectsInInventory.lastSquare = this.square;
			}

			gameObject.inventoryThatHoldsThisObject = this;
			gameObject.squareGameObjectIsOn = square;

//			if (!Game.level.inanimateObjectsOnGround.contains(gameObject) && !(gameObject instanceof Actor)) {
//				Game.level.inanimateObjectsOnGround.add(gameObject);
			if (gameObject instanceof UpdatableGameObject && !Level.updatableGameObjects.contains(gameObject)) {
				Level.updatableGameObjects.add((UpdatableGameObject) gameObject);
			}
//			}

			this.gameObjects.sort(this);
			this.floorObjects.sort(this);
			this.nonFloorObjects.sort(this);

			if (gameObject == Level.player) {
				Level.player.calculateVisibleAndCastableSquares(Level.player.squareGameObjectIsOn);
				Level.player.peekSquare = null;
			}

			refresh();
		}
	}

	@Override
	public int remove(GameObject gameObject) {
		if (gameObjects.contains(gameObject)) {

			gameObject.lastSquare = this.square;
			gameObject.squareGameObjectIsOn = null;
			gameObjects.remove(gameObject);
			if (gameObject.isFloorObject) {
				floorObjects.remove(gameObject);
			} else {
				nonFloorObjects.remove(gameObject);
			}

			if (gameObject instanceof UpdatableGameObject && Level.updatableGameObjects.contains(gameObject)) {
				Level.updatableGameObjects.remove(gameObject);
			}

			refresh();
		}
		return -1;
	}

	public void refresh() {

		square.calculatePathCost();
		square.calculatePathCostForPlayer();
		square.updateSquaresToSave();

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

		for (GameObject gameObject : (CopyOnWriteArrayList<GameObject>) gameObjects) {
			gameObject.squareContentsChanged();
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
			if (containsGameObjectOfType(Actor.class))
				return true;
		}
		return false;
	}

	public CopyOnWriteArrayList<GameObject> getGameObjectsThatFitInInventory() {
		CopyOnWriteArrayList<GameObject> gameObjectsThatFitInInventory = new CopyOnWriteArrayList<GameObject>(GameObject.class);
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

	public boolean blocksCasting() {
		for (GameObject gameObject : gameObjects) {
			if (gameObject.blocksCasting)
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

	// @Override
	// public String toString() {
	// String string = "";
	// string += "SquareInventory { ";
	// string += "gameObjects [";
	// for (GameObject gameObject : gameObjects) {
	// string += "" + gameObject + ", ";
	// }
	// string += " ]";
	// string += " }";
	// return string;
	// }

	public GameObject getDiggable() {
		for (GameObject gameObject : gameObjects) {
			if (gameObject.diggable)
				return gameObject;
		}
		return null;
	}

	@Override
	public String toString() {
		String s = "SquareInventory - parent = " + parent + " gameObjects = [";
		for (GameObject gameObject : gameObjects) {
			s += gameObject + ", ";
		}
		s += "]";
		return s;
	}

}
