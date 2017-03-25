package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.Junk;
import com.marklynch.objects.Templates;
import com.marklynch.objects.Vein;
import com.marklynch.objects.tools.Pickaxe;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionMine extends Action {

	public static final String ACTION_NAME = "Mine";
	public static final String ACTION_NAME_CANT_REACH = ACTION_NAME + " (can't reach)";
	public static final String ACTION_NAME_NEED_PICKAXE = ACTION_NAME + " (need pickaxe)";

	Actor performer;
	Vein vein;

	// Default for hostiles
	public ActionMine(Actor attacker, Vein vein) {
		super(ACTION_NAME);
		this.performer = attacker;
		this.vein = vein;
		if (!check()) {
			enabled = false;
		}
		legal = checkLegality();
	}

	@Override
	public void perform() {

		if (!enabled)
			return;

		Pickaxe pickaxe = (Pickaxe) performer.inventory.getGameObectOfClass(Pickaxe.class);

		float damage = vein.totalHealth / 4f;
		vein.remainingHealth -= damage;
		performer.distanceMovedThisTurn = performer.travelDistance;
		performer.hasAttackedThisTurn = true;

		Junk ore = Templates.ORE.makeCopy(null, performer);
		performer.inventory.add(ore);

		if (performer.squareGameObjectIsOn.visibleToPlayer)
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " mined ", vein, " with ", pickaxe }));

		if (performer.squareGameObjectIsOn.visibleToPlayer)
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " received ", ore }));

		if (vein.checkIfDestroyed()) {
			if (performer.squareGameObjectIsOn.visibleToPlayer)
				Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " depleted a ", vein }));
		}

		performer.showPow(vein);

		// Sound
		float loudness = vein.soundWhenHit * pickaxe.soundWhenHitting;
		sound = new Sound(performer, pickaxe, performer.squareGameObjectIsOn, loudness, legal, this.getClass());

		if (performer.faction == Game.level.factions.get(0)) {
			Game.level.undoList.clear();
			Game.level.undoButton.enabled = false;
		}

		if (performer == Game.level.player)
			Game.level.endTurn();
		performer.actions.add(this);
	}

	@Override
	public boolean check() {
		if (!performer.visibleFrom(vein.squareGameObjectIsOn)) {
			actionName = ACTION_NAME_CANT_REACH;
			return false;
		}

		if (performer.straightLineDistanceTo(vein.squareGameObjectIsOn) > 1) {
			actionName = ACTION_NAME_CANT_REACH;
			return false;
		}

		if (!performer.inventory.contains(Pickaxe.class)) {
			actionName = ACTION_NAME_NEED_PICKAXE;
			return false;
		}

		return true;
	}

	@Override
	public boolean checkLegality() {
		if (vein.owner != null && vein.owner != performer)
			return false;
		return true;
	}

}
