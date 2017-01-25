package com.marklynch.tactics.objects.level.script.trigger;

import com.marklynch.Game;
import com.marklynch.tactics.objects.level.Faction;

public class ScriptTriggerFactionSize extends ScriptTrigger {

	public transient Faction faction;
	int size;
	public final static String[] editableAttributes = { "name", "faction", "size" };
	String factionGUID;

	public ScriptTriggerFactionSize() {
		this.name = this.getClass().getSimpleName();

	}

	public ScriptTriggerFactionSize(Faction faction, int size) {
		this.name = this.getClass().getSimpleName();
		this.faction = faction;
		this.size = size;
		this.factionGUID = faction.guid;
	}

	@Override
	public boolean checkTrigger() {
		if (faction.actors.size() == size)
			return true;
		return false;
	}

	@Override
	public void postLoad() {
		faction = Game.level.findFactionFromGUID(factionGUID);
	}

	@Override
	public ScriptTrigger makeCopy() {
		return new ScriptTriggerFactionSize(faction, size);
	}

}
