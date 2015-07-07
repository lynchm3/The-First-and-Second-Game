package com.marklynch.tactics.objects.level.script;

import java.util.Vector;

public class Speech {

	public Vector<SpeechPart> dialogParts;
	public int dialogIndex = 0;

	public Speech(Vector<SpeechPart> dialogParts) {
		super();
		this.dialogParts = dialogParts;
	}

	public boolean checkIfCompleted() {
		if (dialogIndex >= dialogParts.size())
			return true;
		return false;
	}

	public void click() {
		dialogIndex++;
	}

	public void draw() {
		if (dialogIndex < dialogParts.size()) {
			dialogParts.get(dialogIndex).draw();
		}
	}
}
