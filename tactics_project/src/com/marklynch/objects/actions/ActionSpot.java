package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.area.Area;
import com.marklynch.level.constructs.bounds.structure.Structure;
import com.marklynch.level.constructs.bounds.structure.StructureRoom;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionSpot extends Action {

	public static final String ACTION_NAME = "Spot";

	public Actor performer;
	public Object spotted;
	public Square specificSquareSpotted;

	public ActionSpot(Actor spotter, Object spotted, Square specificSquareSpotted) {
		super(ACTION_NAME, "action_spot.png");
		super.gameObjectPerformer = this.performer = spotter;
		this.spotted = spotted;
		this.specificSquareSpotted = specificSquareSpotted;
		legal = checkLegality();
		sound = createSound();
	}

	@Override
	public void perform() {super.perform();

		if (!enabled)
			return;

		if (!checkRange())
			return;

		if (Game.level.shouldLog(performer))
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " spotted ", spotted }));

		Level.squareToFlash = specificSquareSpotted;
		Level.flashSquare = true;

		if (spotted instanceof Area) {
			Game.level.player.addXP((int) Math.pow(5, ((Area) spotted).level), specificSquareSpotted);
		} else if (spotted instanceof Structure) {
			Game.level.player.addXP((int) Math.pow(5, ((Structure) spotted).level), specificSquareSpotted);
		} else if (spotted instanceof StructureRoom) {
			StructureRoom structureRoom = (StructureRoom) spotted;
			if (structureRoom.level != 0)
				Game.level.player.addXP((int) Math.pow(5, structureRoom.level), specificSquareSpotted);
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
	public boolean checkRange() {
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
