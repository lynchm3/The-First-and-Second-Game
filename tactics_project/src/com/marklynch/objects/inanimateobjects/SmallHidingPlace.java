package com.marklynch.objects.inanimateobjects;

import com.marklynch.Game;
import com.marklynch.actions.Action;
import com.marklynch.actions.ActionAttack;
import com.marklynch.actions.ActionStopHidingInside;
import com.marklynch.level.constructs.GroupOfActors;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.templates.Templates;
import com.marklynch.objects.utils.UpdatableGameObject;
import com.marklynch.utils.ArrayList;

public class SmallHidingPlace extends Searchable implements UpdatableGameObject {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>(GameObject.class);
	public ArrayList<Actor> actorsHidingHere = new ArrayList<Actor>(Actor.class);
	public GroupOfActors groupOfBuns;

	public SmallHidingPlace() {
		super();

		// Settings for BURROW
		canBePickedUp = false;

		fitsInInventory = false;

		canContainOtherObjects = true;

		persistsWhenCantBeSeen = true;
		attackable = false;
		orderingOnGound = 119;

	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	@Override
	public Action getDefaultActionPerformedOnThisInWorld(Actor performer) {
		return null;
	}

	@Override
	public Action getSecondaryActionPerformedOnThisInWorld(Actor performer) {
		return new ActionAttack(performer, this);
	}

	@Override
	public ArrayList<GameObject> search() {
		return (ArrayList<GameObject>) inventory.gameObjects.clone();
	}

	@Override
	public SmallHidingPlace makeCopy(Square square, Actor owner) {
		SmallHidingPlace smallHidingPlace = new SmallHidingPlace();
		setInstances(smallHidingPlace);
		super.setAttributesForCopy(smallHidingPlace, square, owner);
		return smallHidingPlace;
	}

	@Override
	public void update() {

		if (groupOfBuns == null || groupOfBuns.size() == 0) {
			if (Math.random() > 0.9f) {
				GroupOfActors newGroup = createBunGroup();
				if (newGroup != null)
					groupOfBuns = createBunGroup();
			}
		}
	}

	// @Override
	// public void attacked(Object attacker) {
	// super.attacked(attacker);
	// for (Actor actor : actorsHidingHere) {
	// rr
	// }
	// }

	public GroupOfActors createBunGroup() {

		if (squareGameObjectIsOn == null)
			return null;

		return new GroupOfActors("Buns",
				Templates.RABBIT.makeCopy("Female Bun", this.squareGameObjectIsOn.getSquareAbove(),
						Game.level.factions.buns, null, new GameObject[] { Templates.MEAT_CHUNK.makeCopy(null, null) },
						new GameObject[] { Templates.FUR.makeCopy(null, null) },
						this.squareGameObjectIsOn.areaSquareIsIn),
				Templates.RABBIT.makeCopy("Male Bun", this.squareGameObjectIsOn.getSquareToLeftOf(),
						Game.level.factions.buns, null, new GameObject[] { Templates.MEAT_CHUNK.makeCopy(null, null) },
						new GameObject[] { Templates.FUR.makeCopy(null, null) },
						this.squareGameObjectIsOn.areaSquareIsIn),
				Templates.BABY_RABBIT.makeCopy("Baby Bun", this.squareGameObjectIsOn.getSquareBelow(),
						Game.level.factions.buns, null, new GameObject[] { Templates.MEAT_CHUNK.makeCopy(null, null) },
						new GameObject[] { Templates.FUR.makeCopy(null, null) },
						this.squareGameObjectIsOn.areaSquareIsIn),
				Templates.BABY_RABBIT.makeCopy("Baby Bun", this.squareGameObjectIsOn.getSquareToRightOf(),
						Game.level.factions.buns, null, new GameObject[] { Templates.MEAT_CHUNK.makeCopy(null, null) },
						new GameObject[] { Templates.FUR.makeCopy(null, null) },
						this.squareGameObjectIsOn.areaSquareIsIn));
	}

	@Override
	public boolean checkIfDestroyed(Object attacker, Action action) {
		boolean destroyed = super.checkIfDestroyed(attacker, action);
		if (destroyed) {

			if (actorsHidingHere.size() > 0) {
				ArrayList<Actor> actorsToRemove = (ArrayList<Actor>) actorsHidingHere.clone();

				for (Actor gameObjectHidingHere : actorsToRemove) {
					if (attacker instanceof GameObject) {
						((GameObject) attacker).addAttackerForThisAndGroupMembers(gameObjectHidingHere);
					}
					new ActionStopHidingInside(gameObjectHidingHere, this).perform();
					// gameObjectHidingHere.hiding = false;
					// gameObjectHidingHere.hidingPlace = null;
				}
				actorsHidingHere.clear();
			} else {
				if (attacker != null) {
					ArrayList<Square> adjacentSquares = this.getAllSquaresWithinDistance(0, 1);
					for (Square adjacentSquare : adjacentSquares) {
						SmallHidingPlace hidingPlace = (SmallHidingPlace) adjacentSquare.inventory
								.getGameObjectOfClass(SmallHidingPlace.class);
						if (hidingPlace != null && attacker instanceof GameObject)
							((GameObject) attacker).addAttackerForThisAndGroupMembers(hidingPlace);
					}
				}
			}
		}
		return destroyed;

	}

}
