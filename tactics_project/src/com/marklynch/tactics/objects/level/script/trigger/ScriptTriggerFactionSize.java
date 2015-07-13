package com.marklynch.tactics.objects.level.script.trigger;

import com.marklynch.tactics.objects.level.Faction;
import com.marklynch.tactics.objects.level.Level;

public class ScriptTriggerFactionSize extends ScriptTrigger {

	Faction faction;
	int size;

	public ScriptTriggerFactionSize(Level level, Faction faction, int size) {
		super(level);
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
