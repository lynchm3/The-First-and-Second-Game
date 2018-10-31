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

	public Object spotted;
	public Square specificSquareSpotted;

	public ActionSpot(Actor spotter, Object spotted, Square specificSquareSpotted) {
		super(ACTION_NAME, textureSpot, spotter, null, null);
		this.spotted = spotted;
		this.specificSquareSpotted = specificSquareSpotted;
		legal = checkLegality();
		sound = createSound();
	}

	@Override
	public void perform() {
		super.perform();

		if (!enabled)
			return;

		if (!checkRange())
			return;

		if (Game.level.shouldLog(performer))
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " spotted ", spotted }));

		Level.squareToFlash = specificSquareSpotted;
		Level.flashSquare = true;

		if (spotted instanceof Area) {
			Level.player.addXP((int) Math.pow(1, ((Area) spotted).level), specificSquareSpotted);
		} else if (spotted instanceof Structure) {
			Level.player.addXP((int) Math.pow(1, ((Structure) spotted).level), specificSquareSpotted);
		} else if (spotted instanceof StructureRoom) {
			StructureRoom structureRoom = (StructureRoom) spotted;
			if (structureRoom.level != 0)
				Level.player.addXP((int) Math.pow(1, structureRoom.level), specificSquareSpotted);
		}

		performer.actionsPerformedThisTurn.add(this);
		if (sound != null)
			sound.play();
	}

	@Override
	public boolean check() {
		return true;
	}

	@Override
	public boolean checkRange() {
		return true;
	}

	@Override
	public boolean checkLegality() {
		return true;
	}

	@Override
	public Sound createSound() {
		return null;
	}

}
