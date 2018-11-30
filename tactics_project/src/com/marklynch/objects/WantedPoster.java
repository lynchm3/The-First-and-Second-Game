package com.marklynch.objects;

import java.util.ArrayList;
import java.util.HashMap;

import com.marklynch.Game;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Crime.CrimeListener;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.templates.Templates;
import com.marklynch.utils.Color;
import com.marklynch.utils.TextUtils;
import com.marklynch.utils.TextureUtils;

public class WantedPoster extends GameObject implements CrimeListener {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();

	public ArrayList<Crime> crimes;
	public Actor criminal;
	public int reward;
	public int accumulatedSAeverity;

	public WantedPoster() {
		super();
		// type = "Wanted Poster";

	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	public WantedPoster makeCopy(Square square, String name, ArrayList<Crime> crimes, Actor owner) {
		WantedPoster wantedPoster = new WantedPoster();
		setInstances(wantedPoster);
		super.setAttributesForCopy(wantedPoster, square, owner);
		wantedPoster.crimes = crimes;
		conversation = createConversation(wantedPoster.generateText());
		return wantedPoster;
	}

	public void updateCrimes(ArrayList<Crime> crimes, Actor criminal) {
		this.crimes = crimes;
		this.criminal = criminal;
	}

	@Override
	public boolean draw1() {

		boolean shouldDraw = super.draw1();
		if (!shouldDraw)
			return false;

		// Draw crim on the wanted poster
		if (criminal == null)
			return true;

		// Draw object
		if (squareGameObjectIsOn != null) {

			int actorPositionXInPixels = (int) (this.squareGameObjectIsOn.xInGridPixels
					+ Game.SQUARE_WIDTH * drawOffsetRatioX + Game.QUARTER_SQUARE_WIDTH);
			int actorPositionYInPixels = (int) (this.squareGameObjectIsOn.yInGridPixels
					+ Game.SQUARE_HEIGHT * drawOffsetRatioY + Game.QUARTER_SQUARE_HEIGHT);
			float alpha = 1.0f;
			if (primaryAnimation != null)
				alpha = primaryAnimation.alpha;
			if (!this.squareGameObjectIsOn.visibleToPlayer)
				alpha = 0.5f;

			TextureUtils.drawTexture(criminal.imageTexture, alpha, actorPositionXInPixels, actorPositionYInPixels,
					actorPositionXInPixels + halfWidth, actorPositionYInPixels + halfWidth, backwards);

			if (flash) {
				TextureUtils.drawTexture(imageTexture, alpha, actorPositionXInPixels, actorPositionYInPixels,
						actorPositionXInPixels + halfWidth, actorPositionYInPixels + halfWidth, 0, 0, 0, 0, backwards,
						false, Color.BLACK, false);
			}

			Game.flush();
		}
		return true;
	}

	@Override
	public void update(int delta) {
		super.update(delta);
		for (Crime crime : crimes) {
			crime.notifyWitnessesOfCrime();
		}
	}

	public Object[] generateText() {

		reward = 0;
		accumulatedSAeverity = 0;

		if (crimes.size() == 0) {

			criminal = null;
			Object[] conversationText = { "For official use only" };
			return conversationText;

		}
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
			accumulatedSAeverity += crime.type.severity;
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

		return conversationText;

	}

	public void notifyWitnesses() {

	}

	@Override
	public void crimeUpdate(Crime crime) {
		if (crime.isResolved()) {
			crimes.remove(crime);
		}
		conversation = createConversation(generateText());
	}
}
