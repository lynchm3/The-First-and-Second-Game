package com.marklynch.objects;

import com.marklynch.Game;
import com.marklynch.ai.utils.AILine;
import com.marklynch.level.constructs.requirementtomeet.RequirementToMeet;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

public class Switch extends GameObject {

	public String actionName;
	public String actionVerb;
	public RequirementToMeet[] requirementsToMeet;

	public SwitchListener switchListener;
	public SWITCH_TYPE switchType;
	public AILine aiLine;

	public enum SWITCH_TYPE {
		OPEN_CLOSE, LOCK_UNLOCK
	}

	public Switch() {
		super();
		canBePickedUp = false;
		showInventory = false;
		fitsInInventory = false;
		canShareSquare = false;
		canContainOtherObjects = false;
		blocksLineOfSight = false;
		persistsWhenCantBeSeen = true;
		attackable = true;
	}

	@Override
	public void draw2() {
		super.draw2();
		if (Game.showTriggerLines) {
			if (switchListener instanceof GameObject) {
				aiLine.target = ((GameObject) switchListener).squareGameObjectIsOn;
			}
			aiLine.draw2();
		}
	}

	public void use() {
		switchListener.zwitch(this);
	}

	public Switch makeCopy(Square square, Actor owner, SwitchListener switchListener, SWITCH_TYPE switchType,
			RequirementToMeet[] requirementsToMeet) {

		Switch zwitch = new Switch();
		super.setAttributesForCopy(zwitch, square, owner);
		zwitch.actionName = actionName;
		zwitch.actionVerb = actionVerb;
		zwitch.switchListener = switchListener;
		zwitch.switchType = switchType;
		zwitch.requirementsToMeet = requirementsToMeet;
		if (switchListener != null)
			this.aiLine = new AILine(AILine.AILineType.AI_LINE_TYPE_SWITCH, this,
					((GameObject) switchListener).squareGameObjectIsOn);

		return zwitch;
	}

}
