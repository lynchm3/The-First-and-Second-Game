package com.marklynch.tactics.objects.level.script;

import java.util.Vector;

public class Speech {

	public Vector<SpeechPart> speechParts;
	public int speechIndex = 0;

	public Speech(Vector<SpeechPart> dialogParts) {
		super();
		this.speechParts = dialogParts;
	}

	public boolean checkIfCompleted() {
		if (speechIndex >= speechParts.size())
			return true;
		return false;
	}

	public void click() {
		speechIndex++;
	}

	public void draw() {
		if (speechIndex < speechParts.size()) {
			speechParts.get(speechIndex).draw();
		}
	}
}
