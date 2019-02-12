package com.marklynch.objects.inanimateobjects;

import com.marklynch.actions.Action;
import com.marklynch.actions.ActionMove;
import com.marklynch.level.Level;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.utils.SwitchListener;
import com.marklynch.objects.utils.UpdatableGameObject;
import com.marklynch.utils.ArrayList;
import com.marklynch.utils.Texture;

public class Spikes extends GameObject implements SwitchListener, UpdatableGameObject {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>(GameObject.class);
	public boolean spikesOut = true;
	public Texture spikesOutImage;
	public Texture spikesRetractedImage;
	public int lastTurnDidDamage;

	public Spikes() {
		super();
		canBePickedUp = false;
		fitsInInventory = false;
		canShareSquare = true;
		canContainOtherObjects = false;
		persistsWhenCantBeSeen = true;
		attackable = false;
		moveable = false;
		isFloorObject = true;
		type = "Trap";
	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	public Spikes makeCopy(Square square, Actor owner, int level) {
		Spikes spikeFloor = new Spikes();
		setInstances(spikeFloor);
		spikeFloor.spikesOut = spikesOut;
		spikeFloor.spikesOutImage = spikesOutImage;
		spikeFloor.spikesRetractedImage = spikesRetractedImage;
		spikeFloor.level = level;
		spikeFloor.updateImageTexture();
		super.setAttributesForCopy(spikeFloor, square, owner);
		return spikeFloor;
	}

	public void doDamage() {

		System.out.println("DODAMAGE");

		lastTurnDidDamage = Level.turn;

		if (squareGameObjectIsOn == null)
			return;

		if (!spikesOut)
			return;

		for (final GameObject gameObject : (ArrayList<GameObject>) squareGameObjectIsOn.inventory.gameObjects.clone()) {

			System.out.println("DODAMAGE - gameObject B = " + gameObject);

			if (gameObject == this || gameObject.isFloorObject || gameObject.attackable == false)
				continue;
			System.out.println("DODAMAGE - gameObject  C= " + gameObject);

			gameObject.changeHealthSafetyOff(-5f, this, null);
		}
	}

	@Override
	public Action getDefaultActionPerformedOnThisInWorld(Actor performer) {
		return new ActionMove(performer, squareGameObjectIsOn, true);
	}

	@Override
	public Action getSecondaryActionPerformedOnThisInWorld(Actor performer) {
		return new ActionMove(performer, squareGameObjectIsOn, true);
	}

	@Override
	public ArrayList<Action> getAllActionsPerformedOnThisInWorld(Actor performer) {
		ArrayList<Action> actions = new ArrayList<Action>(Action.class);
		actions.add(new ActionMove(performer, squareGameObjectIsOn, true));
		actions.addAll(super.getAllActionsPerformedOnThisInWorld(performer));
		return actions;
	}

	@Override
	public void zwitch(Switch zwitch) {
		spikesOut = !spikesOut;
		updateImageTexture();
		doDamage();
	}

	public void updateImageTexture() {
		if (spikesOut) {
			imageTexture = spikesOutImage;
		} else {
			imageTexture = spikesRetractedImage;
		}

	}

	@Override
	public void update(int delta) {
		if (lastTurnDidDamage == Level.turn)
			return;
		doDamage();
	}

}
