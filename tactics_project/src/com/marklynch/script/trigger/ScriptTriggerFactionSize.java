package com.marklynch.script.trigger;

import com.marklynch.level.constructs.Faction;

public class ScriptTriggerFactionSize extends ScriptTrigger {

	public transient Faction faction;
	int size;
	public final static String[] editableAttributes = { "name", "faction", "size" };

	public ScriptTriggerFactionSize() {
		this.name = this.getClass().getSimpleName();

	}

	public ScriptTriggerFactionSize(Faction faction, int size) {
		this.name = this.getClass().getSimpleName();
		this.faction = faction;
		this.size = size;
	}

	@Override
	public boolean checkTrigger() {
		if (faction.actors.size() == size)
			return true;
		return false;
	}

	@Override
	public void postLoad() {
	}

	@Override
	public ScriptTrigger makeCopy() {
		return new ScriptTriggerFactionSize(faction, size);
	}

}
