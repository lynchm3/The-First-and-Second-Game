package com.marklynch.level.constructs.bounds.structure.structureroom.puzzleroom;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.animation.primary.AnimationFallFromTheSky;
import com.marklynch.level.constructs.bounds.structure.structureroom.StructureRoom;
import com.marklynch.level.constructs.faction.FactionList;
import com.marklynch.level.squares.Node;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.inanimateobjects.WallWithCrack;
import com.marklynch.objects.templates.Templates;
import com.marklynch.objects.utils.DeathListener;
import com.marklynch.ui.ActivityLog;
import com.marklynch.utils.ArrayList;

public class PuzzleRoomFightCaveIn extends StructureRoom implements DeathListener {
	int posX;
	int posY;
	final static int totalWidthInSquares = 13;
	final static int totalHeightInSquares = 20;
	final static int caveInYOffset = 10;
	ArrayList<Square> caveInSquares = new ArrayList<Square>(Square.class);
	WallWithCrack supportingWall;

	public PuzzleRoomFightCaveIn(int posX, int posY) {
		super("Cave In Room", posX, posY, false, false, new ArrayList<Actor>(Actor.class), 1, false, new Node[] {},
				new RoomPart[] {
						new RoomPart(posX, posY, posX + totalWidthInSquares - 1, posY + totalHeightInSquares - 1) });

		this.posX = posX;
		this.posY = posY;
		int caveInY = posY + caveInYOffset;

		for (int i = posX; i < posX + totalWidthInSquares; i++) {
			caveInSquares.add(Level.squares[i][caveInY]);
		}

		// On top side
		caveInSquares.add(Level.squares[posX][caveInY - 1]);
		caveInSquares.add(Level.squares[posX][caveInY - 2]);
		caveInSquares.add(Level.squares[posX + 1][caveInY - 1]);
		caveInSquares.add(Level.squares[posX + 4][caveInY - 1]);
		caveInSquares.add(Level.squares[posX + totalWidthInSquares - 1][caveInY - 1]);
		caveInSquares.add(Level.squares[posX + totalWidthInSquares - 2][caveInY - 1]);
		caveInSquares.add(Level.squares[posX + totalWidthInSquares - 2][caveInY - 2]);
		caveInSquares.add(Level.squares[posX + totalWidthInSquares - 3][caveInY - 1]);

		// random sqr on its own, enemy should walk at this spot
		caveInSquares.add(Level.squares[posX + 3][caveInY - 2]);

		// On bottom side
		caveInSquares.add(Level.squares[posX][caveInY + 1]);
		caveInSquares.add(Level.squares[posX + 1][caveInY + 1]);
		caveInSquares.add(Level.squares[posX + 4][caveInY + 1]);
		caveInSquares.add(Level.squares[posX + 8][caveInY + 1]);
		caveInSquares.add(Level.squares[posX + 9][caveInY + 1]);
		caveInSquares.add(Level.squares[posX + 9][caveInY + 2]);
		caveInSquares.add(Level.squares[posX + totalWidthInSquares - 1][caveInY + 1]);
		caveInSquares.add(Level.squares[posX + totalWidthInSquares - 2][caveInY + 1]);
		caveInSquares.add(Level.squares[posX + totalWidthInSquares - 3][caveInY + 1]);

		supportingWall = Templates.WALL_WITH_CRACK.makeCopy(Level.squares[posX + 5][caveInY], null);
		supportingWall.squaresToHighlight.addAll(caveInSquares);
		supportingWall.squaresToHighlight.remove(supportingWall.squareGameObjectIsOn);
		supportingWall.deathListener = this;

		// NOTE
		// 1 enemy should be standing under the cave in
		// 1 enemy should have a patrol that periodically puts him under the cave in
		// Rest of the enemies split on either side of the cave in

		Templates.BLIND.makeCopy(Level.squares[posX + 4][posY + 5], FactionList.blind, 14, this, new GameObject[] {},
				new GameObject[] {});

		Templates.BLIND.makeCopy(Level.squares[posX + 3][posY + 10], FactionList.blind, 29, this, new GameObject[] {},
				new GameObject[] {});

		Templates.BLIND.makeCopy(Level.squares[posX + 9][posY + 4], FactionList.blind, 13, this, new GameObject[] {},
				new GameObject[] {});

		Templates.BLIND.makeCopy(Level.squares[posX + 11][posY + 1], FactionList.blind, 22, this, new GameObject[] {},
				new GameObject[] {});

		Templates.BLIND.makeCopy(Level.squares[posX + 3][posY + 2], FactionList.blind, 9, this, new GameObject[] {},
				new GameObject[] {});

		Templates.BLIND.makeCopy(Level.squares[posX + 11][posY + 15], FactionList.blind, 22, this, new GameObject[] {},
				new GameObject[] {});

		Templates.BLIND.makeCopy(Level.squares[posX + 3][posY + 17], FactionList.blind, 9, this, new GameObject[] {},
				new GameObject[] {});

	}

	@Override
	public void thisThingDied(GameObject deadThing) {

		if (Game.level.shouldLog(deadThing))
			Game.level.logOnScreen(new ActivityLog(new Object[] { "Destruction of ", deadThing, " caused cave in" }));

		for (Square square : caveInSquares) {

			for (GameObject gameObject : (ArrayList<GameObject>) square.inventory.gameObjects.clone()) {

				System.out.println("Calling changeHealth on - " + gameObject);
				gameObject.changeHealthSafetyOff(-gameObject.remainingHealth, deadThing.destroyedBy,
						deadThing.destroyedByAction);
			}

			GameObject boulder = Templates.BOULDER.makeCopy(square, null);
			boulder.setPrimaryAnimation(new AnimationFallFromTheSky(boulder, 200, null));
		}
	}

}
