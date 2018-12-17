package com.marklynch.level.constructs.bounds.structure.structureroom.puzzleroom;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.animation.primary.AnimationFallFromTheSky;
import com.marklynch.level.constructs.bounds.structure.structureroom.StructureRoom;
import com.marklynch.level.constructs.journal.AreaList;
import com.marklynch.level.squares.Node;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.actors.Actor.HOBBY;
import com.marklynch.objects.actors.Trader;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.inanimateobjects.Wall;
import com.marklynch.objects.inanimateobjects.WallWithCrack;
import com.marklynch.objects.templates.Templates;
import com.marklynch.objects.utils.DeathListener;
import com.marklynch.ui.ActivityLog;
import com.marklynch.utils.ArrayList;

public class PuzzleRoomFriendlyCaveIn extends StructureRoom implements DeathListener {
	int posX;
	int posY;
	final static int totalWidthInSquares = 13;
	final static int totalHeightInSquares = 20;
	final static int caveInYOffset = 10;
	ArrayList<Square> caveInSquares = new ArrayList<Square>(Square.class);
	WallWithCrack supportingWall;

	public PuzzleRoomFriendlyCaveIn(int posX, int posY) {
		super("Cave Shop", posX, posY, false, false, new ArrayList<Actor>(Actor.class), 1, false, new Node[] {},
				new RoomPart[] {
						new RoomPart(posX, posY, posX + totalWidthInSquares - 1, posY + totalHeightInSquares - 1) });

		this.posX = posX;
		this.posY = posY;

		// On top side
		caveInSquares.add(Level.squares[posX][posY]);
		caveInSquares.add(Level.squares[posX][posY + 1]);
		caveInSquares.add(Level.squares[posX + 1][posY + 1]);
		caveInSquares.add(Level.squares[posX + 2][posY]);
		caveInSquares.add(Level.squares[posX + 2][posY + 1]);
		caveInSquares.add(Level.squares[posX + 2][posY + 2]);
		caveInSquares.add(Level.squares[posX + 3][posY]);
		caveInSquares.add(Level.squares[posX + 3][posY + 1]);
		caveInSquares.add(Level.squares[posX + 3][posY + 2]);
		caveInSquares.add(Level.squares[posX + 3][posY + 3]);

		Wall wall = Templates.WALL.makeCopy(Level.squares[posX + 1][posY], null);
		wall.initWall(0);

		supportingWall = Templates.WALL_WITH_CRACK.makeCopy(Level.squares[posX][posY], null);
		supportingWall.squaresToHighlight.addAll(caveInSquares);
		supportingWall.squaresToHighlight.remove(supportingWall.squareGameObjectIsOn);
		supportingWall.deathListener = this;

		// NOTE
		// Shopkeeper in cave, has a little random shop that no on eever visits :P Wju
		// would anyone set up a shop here? A friend told him it was lucrative... make
		// it funny and make reference to all those dungeon crawling games where there's
		// shopkeepers in the dungeons, not just at the entrance (crypt of the
		// necrodancer...). Maybe he could show up in all caves or like... a nurse joy
		// deal where him and his brothers have a chain of shops. Maybe hes mostly there
		// to acquire goods rather than selling.

		// NOTE
		// The cave in should be useful too, open up a way in/out.
		// oooo... the support is blocking your way

		Trader trader = Templates.TRADER.makeCopy("Trader Michael", Level.squares[posX + 2][posY + 2],
				Game.level.factions.townsPeople, Templates.BED.makeCopy(Game.level.squares[posX + 2][posY + 3], null),
				156,
				new GameObject[] { Templates.PICKAXE.makeCopy(null, null), Templates.PICKAXE.makeCopy(null, null),
						Templates.PICKAXE.makeCopy(null, null), Templates.PICKAXE.makeCopy(null, null),
						Templates.PICKAXE.makeCopy(null, null) },
				new GameObject[] {}, AreaList.mines, new int[] {}, new HOBBY[] { HOBBY.HUNTING });

		GameObject shopSign = Templates.SIGN.makeCopy(Game.level.squares[posX + 10][posY], trader);
		shopSign.conversation = shopSign.createConversation(new Object[] { this.name });

		trader.shopRoom = this;
		trader.shopSign = shopSign;

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
