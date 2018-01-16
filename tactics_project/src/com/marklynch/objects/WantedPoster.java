package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Crime.CrimeListener;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;
import com.marklynch.utils.TextUtils;

public class WantedPoster extends Sign implements CrimeListener {

	public ArrayList<Crime> crimes;
	public Actor criminal;
	public int reward;
	public int accumulatedSAeverity;

	public WantedPoster() {
		super();

	}

	public WantedPoster makeCopy(Square square, String name, ArrayList<Crime> crimes, Actor owner) {
		WantedPoster wantedPoster = new WantedPoster();
		super.setAttributesForCopy(wantedPoster, square, owner);
		wantedPoster.crimes = crimes;
		wantedPoster.generateText();
		return wantedPoster;
	}

	public void updateCrimes(ArrayList<Crime> crimes, Actor criminal, int accumulatedSeverity) {
		this.crimes = crimes;
		this.criminal = criminal;
		this.accumulatedSAeverity = accumulatedSeverity;
		generateText();
	}

	public void generateText() {

		if (crimes.size() == 0) {

			Object[] conversationText = { "For official use only" };

			conversation = createConversation(conversationText);
			return;

		}

		reward = 0;
		String crimesString = "";

		for (Crime crime : crimes) {
			reward += crime.type.severity * 100;
			crimesString += crime.type.name + "\n";
			crime.addCrimeListener(this);
		}

		Object[] conversationText = { "WANTED!", TextUtils.NewLine.NEW_LINE, "JOHN DOE", TextUtils.NewLine.NEW_LINE,
				crimesString, "Reward " + reward };

		conversation = createConversation(conversationText);

	}

	@Override
	public void crimwUpdate(Crime crime) {

		if (crime.resolved) {
			crimes.remove(crime);
		}

	}
}
