package com.marklynch.ui.button;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.journal.Journal;
import com.marklynch.level.constructs.journal.Objective;
import com.marklynch.level.quest.Quest;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.MapMarker;
import com.marklynch.objects.actions.ActionPin;

import mdesl.graphics.Color;

public class Link extends LevelButton {

	public Object object;

	public Link(float x, float y, float width, float height, String enabledTexturePath, String disabledTexturePath,
			String text, boolean xFromLeft, boolean yFromTop, Color buttonColor, Color textColor, Object object,
			String tooltip) {
		super(x, y, width, height, enabledTexturePath, disabledTexturePath, text, xFromLeft, yFromTop, buttonColor,
				textColor, tooltip);
		this.object = object;
		this.setClickListener(defaultClickListener);
	}

	private ClickListener defaultClickListener = new ClickListener() {

		@Override
		public void click() {
			if (object instanceof MapMarker) {
				Game.level.centerToSquare = true;
				Game.level.squareToCenterTo = ((MapMarker) object).squareGameObjectIsOn;
				if (Game.level.journal.showing)
					Game.level.openCloseJournal();
			} else if (object instanceof GameObject) {

				new ActionPin(Game.level.player, (GameObject) object).perform();
			} else if (object instanceof Quest) {
				// open quest log on that thing
				Journal.questToDisplayInJournal = (Quest) object;
				Journal.mode = Journal.MODE.LOG;
				Level.journal.generateLinks();

				if (!Level.journal.showing)
					Game.level.openCloseJournal();
			} else if (object instanceof Square) {
				// go to square
				Square square = (Square) object;
				Game.level.centerToSquare = true;
				Game.level.squareToCenterTo = square;
				if (Game.level.journal.showing)
					Game.level.openCloseJournal();
			} else if (object instanceof Objective) {
				Objective objective = (Objective) object;
				if (objective.gameObject != null) {
					Game.level.centerToSquare = true;
					Game.level.squareToCenterTo = objective.gameObject.squareGameObjectIsOn;
				} else if (objective.square != null) {
					Game.level.centerToSquare = true;
					Game.level.squareToCenterTo = objective.square;
				}
				if (Game.level.journal.showing)
					Game.level.openCloseJournal();
			}
		}
	};

}
