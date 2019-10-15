package com.marklynch.objects.inanimateobjects;

import com.marklynch.actions.Action;
import com.marklynch.level.constructs.Stat;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.utils.DamageDealer;
import com.marklynch.objects.utils.SwitchListener;
import com.marklynch.utils.CopyOnWriteArrayList;

public class AttackableSwitch extends Switch {

	public static final CopyOnWriteArrayList<GameObject> instances = new CopyOnWriteArrayList<GameObject>(GameObject.class);

	public AttackableSwitch() {
		super();
		canBePickedUp = false;
		fitsInInventory = false;
		persistsWhenCantBeSeen = true;
		attackable = true;
		moveable = false;
		orderingOnGound = 100;
		type = "Switch";
	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	@Override
	public void use() {
		for (SwitchListener switchListener : switchListeners)
			switchListener.zwitch(this);
	}

	@Override
	public void changeHealth(float change, Object attacker, Action action) {
		super.changeHealth(change, attacker, action);
	}

	@Override
	public float changeHealth(Object attacker, Action action, DamageDealer damageDealer) {
		use();
		return 0;
	}

	@Override
	public float changeHealth(Object attacker, Action action, Stat damage) {
		use();
		return 0;
	}

	public AttackableSwitch makeCopy(Square square, Actor owner, SWITCH_TYPE switchType,
			SwitchListener... switchListeners) {

		AttackableSwitch attackableSwitch = new AttackableSwitch();
		attackableSwitch.switchListeners = switchListeners;
		for (SwitchListener switchListener : switchListeners) {

			if (switchListener instanceof GameObject) {
				GameObject switchListenerGameObject = (GameObject) switchListener;
				attackableSwitch.linkedObjects.add(switchListenerGameObject);
				switchListenerGameObject.linkedObjects.add(attackableSwitch);
			}
		}
		setInstances(attackableSwitch);
		super.setAttributesForCopy(attackableSwitch, square, owner);
		attackableSwitch.actionName = actionName;
		attackableSwitch.actionVerb = actionVerb;
		attackableSwitch.switchType = switchType;

		return attackableSwitch;
	}

}
