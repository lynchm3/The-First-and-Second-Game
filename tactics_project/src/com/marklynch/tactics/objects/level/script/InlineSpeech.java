package com.marklynch.tactics.objects.level.script;

import java.util.Vector;

public class InlineSpeech {

	public Vector<InlineSpeechPart> speechParts;
	public int speechIndex = 0;
	public int timeOnCurrentPart = 0;
	public int timePerPart = 10000;

	public InlineSpeech(Vector<InlineSpeechPart> speechParts) {
		super();
		this.speechParts = speechParts;
	}

	public boolean checkIfCompleted() {
		if (speechIndex >= speechParts.size())
			return true;
		return false;
	}

	public void click() {
	}

	public void draw() {
		if (speechIndex < speechParts.size()) {
			speechParts.get(speechIndex).draw();
		}
	}

	public void update(int delta) {
		timeOnCurrentPart += delta;
		if (timeOnCurrentPart >= timePerPart) {
			speechIndex++;
			timeOnCurrentPart = 0;
		}

	}
}
