package com.marklynch.objects;

import java.util.ArrayList;
import java.util.HashMap;

import com.marklynch.Game;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Crime.CrimeListener;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.templates.Templates;
import com.marklynch.objects.units.Actor;
import com.marklynch.utils.TextUtils;
import com.marklynch.utils.TextureUtils;

import mdesl.graphics.Color;

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
		wantedPoster.crimesUpdated();
		return wantedPoster;
	}

	public void updateCrimes(ArrayList<Crime> crimes, Actor criminal, int accumulatedSeverity) {
		this.crimes = crimes;
		this.criminal = criminal;
		this.accumulatedSAeverity = accumulatedSeverity;
		crimesUpdated();
	}

	@Override
	public void draw1() {

		super.draw1();

		// Draw crim on the wanted poster
		if (criminal == null)
			return;

		if (this.remainingHealth <= 0)
			return;

		if (squareGameObjectIsOn == null)
			return;

		if (hiding)
			return;

		if (!Game.fullVisiblity) {

			if (this.squareGameObjectIsOn.visibleToPlayer == false && persistsWhenCantBeSeen == false)
				return;

			if (!this.squareGameObjectIsOn.seenByPlayer)
				return;
		}

		// Draw object
		if (squareGameObjectIsOn != null) {

			int actorPositionXInPixels = (int) (this.squareGameObjectIsOn.xInGridPixels
					+ Game.SQUARE_WIDTH * drawOffsetX + Game.QUARTER_SQUARE_WIDTH);
			int actorPositionYInPixels = (int) (this.squareGameObjectIsOn.yInGridPixels
					+ Game.SQUARE_HEIGHT * drawOffsetY + Game.QUARTER_SQUARE_HEIGHT);
			float alpha = 1.0f;
			if (!this.squareGameObjectIsOn.visibleToPlayer)
				alpha = 0.5f;
			if (hiding)
				alpha = 0.5f;

			TextureUtils.drawTexture(criminal.imageTexture, alpha, actorPositionXInPixels, actorPositionYInPixels,
					actorPositionXInPixels + halfWidth, actorPositionYInPixels + halfWidth, backwards);

			if (flash) {
				TextureUtils.drawTexture(imageTexture, alpha, actorPositionXInPixels, actorPositionYInPixels,
						actorPositionXInPixels + halfWidth, actorPositionYInPixels + halfWidth, backwards, Color.BLACK);
			}

			Game.activeBatch.flush();
		}
	}

	public void crimesUpdated() {

		if (crimes.size() == 0) {

			Object[] conversationText = { "For official use only" };

			conversation = createConversation(conversationText);
			return;

		}

		reward = 0;
		ArrayList<Crime.TYPE> uniqueCrimes = new ArrayList<Crime.TYPE>();
		HashMap<Crime.TYPE, Integer> crimeTypeCounts = new HashMap<Crime.TYPE, Integer>();
		for (Crime crime : crimes) {
			if (!uniqueCrimes.contains(crime.type)) {
				uniqueCrimes.add(crime.type);
			}
			if (crimeTypeCounts.containsKey(crime.type)) {
				crimeTypeCounts.put(crime.type, crimeTypeCounts.get(crime.type) + 1);
			} else {
				crimeTypeCounts.put(crime.type, 1);
			}
			reward += crime.type.severity * 100;
			crime.addCrimeListener(this);
		}

		String crimesString = "For ";
		for (Crime.TYPE crimeType : uniqueCrimes) {
			crimesString += crimeTypeCounts.get(crimeType) + " counts of " + crimeType.name;
			if (crimeType == uniqueCrimes.get(uniqueCrimes.size() - 1)) {

			} else if (crimeType == uniqueCrimes.get(uniqueCrimes.size() - 2)) {
				crimesString += " and ";
			} else {
				crimesString += ", ";
			}
		}

		Object[] conversationText = { "WANTED!", TextUtils.NewLine.NEW_LINE, crimesString, TextUtils.NewLine.NEW_LINE,
				Templates.GOLD.imageTexture, "Reward " + reward };

		conversation = createConversation(conversationText);

	}

	@Override
	public void crimwUpdate(Crime crime) {

		if (crime.resolved) {
			crimes.remove(crime);
		}

	}
}
