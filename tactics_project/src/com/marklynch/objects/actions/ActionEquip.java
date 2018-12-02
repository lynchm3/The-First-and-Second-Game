package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.weapons.BodyArmor;
import com.marklynch.objects.weapons.Helmet;
import com.marklynch.objects.weapons.LegArmor;
import com.marklynch.ui.ActivityLog;
import com.marklynch.utils.ResourceUtils;

public class ActionEquip extends Action {

	public static final String ACTION_NAME = "Equip";

	ActionTakeItems actionTake;

	public ActionEquip(Actor performer, GameObject gameObject) {
		super(ACTION_NAME, textureEquip, performer, gameObject);
		if (!Game.level.player.inventory.contains(gameObject)) {
			actionTake = new ActionTakeItems(performer, gameObject.inventoryThatHoldsThisObject.parent, gameObject);

		}
		if (!Game.level.player.inventory.contains(gameObject) && Game.level.openInventories.size() > 0) {
			image = ResourceUtils.getGlobalImage("leftleft.png", false);
		}
		if (!check()) {
			enabled = false;
		}
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

		if (actionTake != null)
			actionTake.perform();

		if (Game.level.shouldLog(performer))
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " equipped ", target }));

		if (target instanceof Helmet) {
			performer.helmet = (Helmet) target;
		} else if (target instanceof BodyArmor) {
			performer.bodyArmor = (BodyArmor) target;
		} else if (target instanceof LegArmor) {
			performer.legArmor = (LegArmor) target;
		} else {
			performer.equip(target);
		}

		if (sound != null)
			sound.play();
	}

	@Override
	public boolean check() {

		if (actionTake != null) {
			disabledReason = actionTake.disabledReason;
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
			this.illegalReason = actionTake.illegalReason;
			return actionTake.legal;
		}
		return true;
	}

	@Override
	public Sound createSound() {
		return null;
	}

}