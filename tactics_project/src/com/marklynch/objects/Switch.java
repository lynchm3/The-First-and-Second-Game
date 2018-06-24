package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.ai.utils.AILine;
import com.marklynch.level.constructs.requirementtomeet.RequirementToMeet;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

public class Switch extends GameObject {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();

	public String actionName;
	public String actionVerb;
	public RequirementToMeet[] requirementsToMeet;

	public SwitchListener switchListener;
	public SWITCH_TYPE switchType;
	public AILine aiLine;

	boolean pressed = false;

	public enum SWITCH_TYPE {
		OPEN_CLOSE, LOCK_UNLOCK
	}

	public Switch() {
		super();
		canBePickedUp = false;

		fitsInInventory = false;

		persistsWhenCantBeSeen = true;

	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	@Override
	public void draw1() {
	}

	@Override
	public void draw2() {
		super.draw1();
		if (Game.showTriggerLines) {
			if (switchListener instanceof GameObject) {
				aiLine.target = ((GameObject) switchListener).squareGameObjectIsOn;
			}
			aiLine.draw2();
		}
		super.draw2();
	}

	public void use() {
		pressed = !pressed;
		switchListener.zwitch(this);
	}

	public Switch makeCopy(Square square, Actor owner, SwitchListener switchListener, SWITCH_TYPE switchType,
			RequirementToMeet[] requirementsToMeet) {

		Switch zwitch = new Switch();
		setInstances(zwitch);
		super.setAttributesForCopy(zwitch, square, owner);
		zwitch.actionName = actionName;
		zwitch.actionVerb = actionVerb;
		zwitch.switchListener = switchListener;
		zwitch.switchType = switchType;
		zwitch.requirementsToMeet = requirementsToMeet;
		if (switchListener != null && switchListener instanceof GameObject)
			this.aiLine = new AILine(AILine.AILineType.AI_LINE_TYPE_SWITCH, this,
					((GameObject) switchListener).squareGameObjectIsOn);

		return zwitch;
	}

}
