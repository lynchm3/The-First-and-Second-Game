package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.bounds.Area;
import com.marklynch.level.constructs.bounds.structure.Structure;
import com.marklynch.level.constructs.bounds.structure.StructureRoom;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionSpot extends Action {

	public static final String ACTION_NAME = "Spot";

	public Actor performer;
	public Object spotted;

	public ActionSpot(Actor spotter, Object spotted) {
		super(ACTION_NAME, "action_spot.png");
		this.performer = spotter;
		this.spotted = spotted;
		legal = checkLegality();
		sound = createSound();
	}

	@Override
	public void perform() {

		if (Game.level.shouldLog(performer))
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " spotted ", spotted }));

		if (spotted instanceof Area) {
			Game.level.player.addXP((int) Math.pow(5, ((Area) spotted).level));
		} else if (spotted instanceof Structure) {
			Game.level.player.addXP((int) Math.pow(5, ((Structure) spotted).level));
		} else if (spotted instanceof StructureRoom) {
			StructureRoom structureRoom = (StructureRoom) spotted;
			if (structureRoom.level != 0)
				Game.level.player.addXP((int) Math.pow(5, structureRoom.level));
		}

		performer.actionsPerformedThisTurn.add(this);
		if (sound != null)
			sound.play();
	}

	@Override
	public boolean check() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean checkLegality() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Sound createSound() {
		return null;
	}

}
