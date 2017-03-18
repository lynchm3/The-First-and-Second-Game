package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Junk;
import com.marklynch.objects.Templates;
import com.marklynch.objects.Vein;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.weapons.Pickaxe;
import com.marklynch.ui.ActivityLog;

public class ActionMine extends Action {

	public static final String ACTION_NAME = "Mine";
	public static final String ACTION_NAME_CANT_REACH = ACTION_NAME + " (can't reach)";
	public static final String ACTION_NAME_NEED_PICKAXE = ACTION_NAME + " (need pickaxe)";

	Actor miner;
	GameObject target;

	// Default for hostiles
	public ActionMine(Actor attacker, Vein target) {
		super(ACTION_NAME);
		this.miner = attacker;
		this.target = target;
		if (!check()) {
			enabled = false;
		}
	}

	@Override
	public void perform() {

		if (!enabled)
			return;

		float damage = target.totalHealth / 4f;
		target.remainingHealth -= damage;
		miner.distanceMovedThisTurn = miner.travelDistance;
		miner.hasAttackedThisTurn = true;

		Junk ore = Templates.ORE.makeCopy(null);
		miner.inventory.add(ore);

		if (miner.squareGameObjectIsOn.visibleToPlayer)
			Game.level.logOnScreen(new ActivityLog(new Object[] { miner, " mined ", target, " with ",
					miner.inventory.getGameObectOfClass(Pickaxe.class) }));

		if (miner.squareGameObjectIsOn.visibleToPlayer)
			Game.level.logOnScreen(new ActivityLog(new Object[] { miner, " received ", ore }));

		if (target.checkIfDestroyed()) {
			if (miner.squareGameObjectIsOn.visibleToPlayer)
				Game.level.logOnScreen(new ActivityLog(new Object[] { miner, " depleted a ", target }));
		}

		miner.showPow(target);

		// Sound
		float loudness = target.soundWhenHit * miner.equippedWeapon.soundWhenHitting;
		miner.sounds.add(new Sound(miner, miner.equippedWeapon, miner.squareGameObjectIsOn, loudness));

		if (miner.faction == Game.level.factions.get(0)) {
			Game.level.undoList.clear();
			Game.level.undoButton.enabled = false;
		}

		if (miner == Game.level.player)
			Game.level.endTurn();
	}

	@Override
	public boolean check() {
		if (!miner.visibleFrom(target.squareGameObjectIsOn)) {
			actionName = ACTION_NAME_CANT_REACH;
			return false;
		}

		if (miner.straightLineDistanceTo(target.squareGameObjectIsOn) > 1) {
			actionName = ACTION_NAME_CANT_REACH;
			return false;
		}

		if (!miner.inventory.contains(Pickaxe.class)) {
			actionName = ACTION_NAME_NEED_PICKAXE;
			return false;
		}

		return true;
	}

}
