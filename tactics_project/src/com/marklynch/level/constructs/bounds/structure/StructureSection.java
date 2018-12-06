package com.marklynch.level.constructs.bounds.structure;

import com.marklynch.Game;
import com.marklynch.data.Idable;
import com.marklynch.level.Level;
import com.marklynch.objects.actors.Actor;
import com.marklynch.utils.ArrayList;

public class StructureSection implements Idable {

	public String name;
	public int gridX1, gridY1, gridX2, gridY2;
	public Long id;

	public StructureSection(String name, int gridX1, int gridY1, int gridX2, int gridY2, boolean restricted,
			boolean restrictedAtNight, Actor... ownersArray) {
		super();
		this.id = Level.generateNewId(this);
		this.name = name;
		this.gridX1 = gridX1;
		this.gridY1 = gridY1;
		this.gridX2 = gridX2;
		this.gridY2 = gridY2;

		ArrayList<Actor> ownersArrayList = new ArrayList<Actor>(Actor.class);
		for (Actor owner : ownersArray) {
			ownersArrayList.add(owner);
		}

		if (restricted) {
			for (int i = gridX1; i <= gridX2; i++) {
				for (int j = gridY1; j <= gridY2; j++) {
					Game.level.squares[i][j].restricted = true;
				}
			}
		}

		if (restrictedAtNight) {
			for (int i = gridX1; i <= gridX2; i++) {
				for (int j = gridY1; j <= gridY2; j++) {
					Game.level.squares[i][j].restrictedAtNight = true;
				}
			}
		}

		if (ownersArrayList.size() > 0) {
			for (int i = gridX1; i <= gridX2; i++) {
				for (int j = gridY1; j <= gridY2; j++) {
					Game.level.squares[i][j].owners = ownersArrayList;
				}
			}
		}
	}

	@Override
	public Long getId() {
		return id;
	}

}
