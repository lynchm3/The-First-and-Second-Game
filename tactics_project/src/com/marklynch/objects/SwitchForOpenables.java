package com.marklynch.objects;

import com.marklynch.Game;
import com.marklynch.ai.utils.AILine;
import com.marklynch.level.constructs.requirementtomeet.RequirementToMeet;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actions.ActionClose;
import com.marklynch.objects.actions.ActionLock;
import com.marklynch.objects.actions.ActionOpen;
import com.marklynch.objects.actions.ActionUnlock;
import com.marklynch.objects.units.Actor;

public class SwitchForOpenables extends Switch {

	public Openable openable;
	public SWITCH_TYPE switchType;
	public AILine aiLine;

	public String actionName;
	public String actionVerb;
	public RequirementToMeet[] requirementsToMeet;

	public enum SWITCH_TYPE {
		OPEN_CLOSE, LOCK_UNLOCK
	}

	public SwitchForOpenables() {
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
			aiLine.target = this.openable.squareGameObjectIsOn;
			aiLine.draw2();
		}
	}

	@Override
	public void use() {
		if (switchType == SWITCH_TYPE.OPEN_CLOSE) {
			if (openable.open) {
				new ActionClose(this, openable).perform();
			} else {
				new ActionOpen(this, openable).perform();
			}
		} else if (switchType == SWITCH_TYPE.LOCK_UNLOCK) {
			if (openable.locked) {
				new ActionUnlock(this, openable).perform();
			} else {
				new ActionLock(this, openable).perform();
			}
		}

	}

	public SwitchForOpenables makeCopy(Square square, Actor owner, Openable openable, SWITCH_TYPE switchType,
			RequirementToMeet[] requirementsToMeet) {

		SwitchForOpenables switchForOpenables = new SwitchForOpenables();
		super.setAttributesForCopy(switchForOpenables, square, owner);
		switchForOpenables.actionName = actionName;
		switchForOpenables.actionVerb = actionVerb;
		switchForOpenables.openable = openable;
		switchForOpenables.switchType = switchType;
		switchForOpenables.requirementsToMeet = requirementsToMeet;
		if (openable != null)
			this.aiLine = new AILine(AILine.AILineType.AI_LINE_TYPE_SWITCH, this, openable.squareGameObjectIsOn);

		return switchForOpenables;
	}

}
