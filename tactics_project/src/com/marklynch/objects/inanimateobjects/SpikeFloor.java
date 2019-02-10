package com.marklynch.objects.inanimateobjects;

import com.marklynch.actions.Action;
import com.marklynch.actions.ActionMove;
import com.marklynch.actions.ActionTeleport;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.utils.SwitchListener;
import com.marklynch.utils.ArrayList;
import com.marklynch.utils.Texture;

public class SpikeFloor extends GameObject implements SwitchListener {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>(GameObject.class);
	public boolean spikesOut = true;
	public Texture spikesOutImage;
	public Texture spikesRetractedImage;

	public SpikeFloor() {
		super();
		canBePickedUp = false;
		fitsInInventory = false;
		canShareSquare = true;
		canContainOtherObjects = false;
		persistsWhenCantBeSeen = true;
		attackable = false;
		moveable = false;
		type = "Trap";
	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	public SpikeFloor makeCopy(Square square, Actor owner) {
		SpikeFloor spikeFloor = new SpikeFloor();
		setInstances(spikeFloor);
		super.setAttributesForCopy(spikeFloor, square, owner);
		return spikeFloor;
	}

	@Override
	public void squareContentsChanged() {

		if (squareGameObjectIsOn == null)
			return;
		
		if(!spikesOut)
			return;

		for (final GameObject gameObject : (ArrayList<GameObject>) squareGameObjectIsOn.inventory.gameObjects.clone()) {

			if (gameObject == this || gameObject.isFloorObject || gameObject.attackable == false)
				continue;
			
			gameObject.attackPIERCE_DMG
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
		if (spikesOut) {
			imageTexture = spikesOutImage;
		} else {
			imageTexture = spikesRetractedImage;
		}
	}

}
