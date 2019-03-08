package com.marklynch.objects.inanimateobjects;

import com.marklynch.Game;
import com.marklynch.ai.utils.AILine;
import com.marklynch.level.constructs.requirementtomeet.RequirementToMeet;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.utils.SwitchListener;
import com.marklynch.utils.ArrayList;

public class Switch extends GameObject {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>(GameObject.class);

	public String actionName;
	public String actionVerb;
	public RequirementToMeet[] requirementsToMeet = new RequirementToMeet[] {};

	public SwitchListener[] switchListeners;
	public SWITCH_TYPE switchType;
	public AILine aiLine;

	public boolean pressed = false;

	public enum SWITCH_TYPE {
		OPEN_CLOSE, LOCK_UNLOCK, ON_OFF
	}

	public Switch() {
		super();
		canBePickedUp = false;

		fitsInInventory = false;

		persistsWhenCantBeSeen = true;
		type = "Switch";

	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	@Override
	public void draw2() {
		if (Game.showTriggerLines) {
			for (SwitchListener switchListener : switchListeners) {
				if (switchListener instanceof GameObject) {
					aiLine.target = ((GameObject) switchListener).squareGameObjectIsOn;
				}
				aiLine.draw2();
			}
		}
		super.draw2();
	}

	public void use() {
		pressed = !pressed;

		for (SwitchListener switchListener : switchListeners) {
			switchListener.zwitch(this);
		}
	}

	public Switch makeCopy(Square square, Actor owner, SWITCH_TYPE switchType, RequirementToMeet[] requirementsToMeet,
			SwitchListener... switchListeners) {

		Switch zwitch = new Switch();
		setInstances(zwitch);
		super.setAttributesForCopy(zwitch, square, owner);
		zwitch.actionName = actionName;
		zwitch.actionVerb = actionVerb;
		zwitch.switchListeners = switchListeners;
		for (SwitchListener switchListener : switchListeners) {

			if (switchListener instanceof GameObject) {
				GameObject switchListenerGameObject = (GameObject) switchListener;
				zwitch.linkedObjects.add(switchListenerGameObject);
				switchListenerGameObject.linkedObjects.add(zwitch);
			}
		}
		zwitch.switchType = switchType;
		zwitch.requirementsToMeet = requirementsToMeet;
		for (SwitchListener switchListener : switchListeners) {
			if (switchListener != null && switchListener instanceof GameObject)
				this.aiLine = new AILine(AILine.AILineType.AI_LINE_TYPE_SWITCH, this,
						((GameObject) switchListener).squareGameObjectIsOn);

		}
		return zwitch;
	}

}
