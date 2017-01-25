package com.marklynch.tactics.objects.level.script;

import java.util.Vector;

import com.marklynch.tactics.objects.level.script.trigger.ScriptTrigger;

public class ScriptEventGroup extends ScriptEvent {

	public final static String[] editableAttributes = { "name", "blockUserInput", "scriptTrigger", "scriptEvents" };

	Vector<ScriptEvent> scriptEvents;
	int scriptEventIndex = 0;

	public ScriptEventGroup() {
		name = "ScriptEventGroup";
	}

	public ScriptEventGroup(boolean blockUserInput, ScriptTrigger scriptTrigger, Vector<ScriptEvent> scriptEvents) {
		super(blockUserInput, scriptTrigger);
		this.scriptEvents = scriptEvents;
	}

	@Override
	public boolean checkIfCompleted() {
		if (scriptEventIndex >= scriptEvents.size())
			return true;
		if (scriptEventIndex == scriptEvents.size() - 1 && scriptEvents.lastElement().checkIfCompleted())
			return true;
		return false;
	}

	@Override
	public void click() {

		if (scriptEventIndex >= scriptEvents.size())
			return;
		scriptEvents.get(scriptEventIndex).click();

	}

	@Override
	public void update(int delta) {

		if (scriptEventIndex >= scriptEvents.size())
			return;

		if (scriptEvents.get(scriptEventIndex).checkIfCompleted())
			scriptEventIndex++;

		if (scriptEventIndex >= scriptEvents.size())
			return;

		scriptEvents.get(scriptEventIndex).update(delta);

	}

	@Override
	public void draw() {

		if (scriptEventIndex >= scriptEvents.size())
			return;
		scriptEvents.get(scriptEventIndex).draw();
	}

	@Override
	public void postLoad() {
		scriptTrigger.postLoad();
	}
}
