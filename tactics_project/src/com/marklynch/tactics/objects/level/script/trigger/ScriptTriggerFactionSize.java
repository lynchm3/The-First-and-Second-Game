package com.marklynch.tactics.objects.level.script.trigger;

import com.marklynch.tactics.objects.level.Faction;

public class ScriptTriggerFactionSize extends ScriptTrigger {

	Faction faction;
	int size;

	public ScriptTriggerFactionSize() {
		this.name = this.getClass().getSimpleName();
	}

	public ScriptTriggerFactionSize(Faction faction, int size) {
		this.faction = faction;
		this.size = size;
	}

	@Override
	public boolean checkTrigger() {
		if (faction.actors.size() == size)
			return true;
		return false;
	}

}
