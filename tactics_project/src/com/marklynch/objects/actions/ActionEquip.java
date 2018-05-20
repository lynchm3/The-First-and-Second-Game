package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.weapons.BodyArmor;
import com.marklynch.objects.weapons.Helmet;
import com.marklynch.objects.weapons.LegArmor;
import com.marklynch.ui.ActivityLog;
import com.marklynch.utils.ResourceUtils;

public class ActionEquip extends Action {

	public static final String ACTION_NAME = "Equip";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (unequippable)";

	Actor performer;
	GameObject gameObject;
	ActionTakeItems actionTake;

	public ActionEquip(Actor performer, GameObject gameObject) {
		super(ACTION_NAME, "left.png");
		super.gameObjectPerformer = this.performer = performer;
		this.gameObject = gameObject;
		if (!Game.level.player.inventory.contains(gameObject)) {
			actionTake = new ActionTakeItems(performer, gameObject.inventoryThatHoldsThisObject.parent, gameObject);

		}
		if (!Game.level.player.inventory.contains(gameObject) && Game.level.openInventories.size() > 0) {
			image = ResourceUtils.getGlobalImage("leftleft.png", false);
		}
		if (!check()) {
			enabled = false;
			actionName = ACTION_NAME_DISABLED;
		}
		legal = checkLegality();
		sound = createSound();
	}

	@Override
	public void perform() {super.perform();

		if (!enabled)
			return;

		if (!checkRange())
			return;

		if (actionTake != null)
			actionTake.perform();

		if (Game.level.shouldLog(performer))
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " equipped ", gameObject }));

		if (gameObject instanceof Helmet) {
			performer.helmet = (Helmet) gameObject;
		} else if (gameObject instanceof BodyArmor) {
			performer.bodyArmor = (BodyArmor) gameObject;
		} else if (gameObject instanceof LegArmor) {
			performer.legArmor = (LegArmor) gameObject;
		} else {
			performer.equip(gameObject);
		}

		performer.actionsPerformedThisTurn.add(this);
		if (sound != null)
			sound.play();
	}

	@Override
	public boolean check() {

		if (actionTake != null) {
			if (!actionTake.enabled)
				disabledReason = "Can't reach";
			return actionTake.enabled;
		}

		return true;
	}

	@Override
	public boolean checkRange() {

		if (actionTake != null) {
			return actionTake.checkRange();
		}
		return true;
	}

	@Override
	public boolean checkLegality() {
		if (actionTake != null) {
			return actionTake.legal;
		}
		return true;
	}

	@Override
	public Sound createSound() {
		return null;
	}

}