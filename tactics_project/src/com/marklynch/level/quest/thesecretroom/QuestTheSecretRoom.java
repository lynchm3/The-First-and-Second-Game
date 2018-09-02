package com.marklynch.level.quest.thesecretroom;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.constructs.Path;
import com.marklynch.level.constructs.bounds.structure.Structure;
import com.marklynch.level.constructs.bounds.structure.StructureFeature;
import com.marklynch.level.constructs.bounds.structure.StructurePath;
import com.marklynch.level.constructs.bounds.structure.StructureRoom;
import com.marklynch.level.constructs.bounds.structure.StructureRoom.RoomPart;
import com.marklynch.level.constructs.bounds.structure.StructureSection;
import com.marklynch.level.quest.Quest;
import com.marklynch.level.squares.Node;
import com.marklynch.level.squares.Nodes;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.RemoteDoor;
import com.marklynch.objects.Sign;
import com.marklynch.objects.Wall;
import com.marklynch.objects.WaterSource;
import com.marklynch.objects.templates.Templates;
import com.marklynch.objects.units.Actor;
import com.marklynch.utils.TextUtils;

public class QuestTheSecretRoom extends Quest {

	public QuestTheSecretRoom(int posX, int posY) {
		name = "THE SECRET ROOM";

		ArrayList<Wall> structureExtraWalls = new ArrayList<Wall>();
		ArrayList<StructureFeature> structureFeatures = new ArrayList<StructureFeature>();
		ArrayList<StructurePath> structurePaths = new ArrayList<StructurePath>();
		ArrayList<StructureSection> structureSections = new ArrayList<StructureSection>();
		ArrayList<StructureRoom> structureRooms = new ArrayList<StructureRoom>();
		ArrayList<Square> structureSquaresToRemove = new ArrayList<Square>();
		ArrayList<Square> structureEntranceSquares = new ArrayList<Square>();

		// Front section
		structureSections.add(new StructureSection("A Cozy Place", posX + 21, posY + 30, posX + 29, posY + 36, false));

		// Front door
		structureFeatures.add(new StructureFeature(Templates.DOOR.makeCopy("Front Door",
				Game.level.squares[posX + 24][posY + 30], false, false, false, null), Nodes.dungeonHouseOuter));

		// Front room
		StructureRoom livingRoom = new StructureRoom("Living Room", posX + 22, posY + 31, false, new ArrayList<Actor>(),
				new Node[] { Nodes.dungeonHouseOuter, Nodes.dungeonHouseHiddenArea },
				new RoomPart(posX + 22, posY + 31, posX + 28, posY + 35));
		structureRooms.add(livingRoom);

		// Front room decorative walls
		// structureExtraWalls.add(Templates.WALL.makeCopy(Game.level.squares[55][20],
		// null));
		// structureExtraWalls.add(Templates.WALL.makeCopy(Game.level.squares[55][26],
		// null));
		// structureExtraWalls.add(Templates.WALL.makeCopy(Game.level.squares[46][23],
		// null));
		// structureExtraWalls.add(Templates.WALL.makeCopy(Game.level.squares[51][23],
		// null));

		// Back Section
		structureSections.add(new StructureSection("A Cozy Place", posX + 19, posY + 36, posX + 36, posY + 46, false));

		// False wall
		RemoteDoor falseWall = Templates.OPENABLE_WALL.makeCopy("Wall", Game.level.squares[posX + 22][posY + 36], false,
				null);
		structureFeatures.add(new StructureFeature(falseWall, Nodes.dungeonHouseHiddenArea));

		// Back room
		StructureRoom backRoom = new StructureRoom("Back room", posX + 20, posY + 37, false, new ArrayList<Actor>(), 4,
				new Node[] { Nodes.dungeonHouseHiddenArea }, new RoomPart(posX + 20, posY + 37, posX + 35, posY + 45));
		structureRooms.add(backRoom);

		// back room decorative walls
		// structureExtraWalls.add(Templates.WALL.makeCopy(Game.level.squares[53][30],
		// null));
		// structureExtraWalls.add(Templates.WALL.makeCopy(Game.level.squares[53][34],
		// null));
		// structureExtraWalls.add(Templates.WALL.makeCopy(Game.level.squares[42][34],
		// null));

		// Rat
		Templates.RAT.makeCopy("Rat", Game.level.squares[posX + 25][posY + 39], Game.level.factions.rats, null,
				new GameObject[] {}, new GameObject[] {}, null);

		Game.level.structures.add(new Structure("A Lovely House", structureSections, structureRooms, structurePaths,
				structureFeatures, structureEntranceSquares, null, posX + 0, posY + 0, posX + 0, posY + 0, true, null,
				structureSquaresToRemove, structureExtraWalls, Templates.WALL, Square.STONE_TEXTURE, 2));

		// Path to town 24,21 -> 24,29
		Path pathToTown = new Path(posX + 24, posY + 21, posX + 24, posY + 29);

		// Bushed along path to town
		Templates.BUSH.makeCopy(Game.level.squares[posX + 23][posY + 25], null);
		Templates.BUSH.makeCopy(Game.level.squares[posX + 25][posY + 25], null);
		Templates.BUSH.makeCopy(Game.level.squares[posX + 23][posY + 27], null);
		Templates.BUSH.makeCopy(Game.level.squares[posX + 25][posY + 27], null);
		Templates.BUSH.makeCopy(Game.level.squares[posX + 23][posY + 29], null);
		Templates.BUSH.makeCopy(Game.level.squares[posX + 25][posY + 29], null);

		// TownSquare Well 24,21
		WaterSource well = Templates.WELL.makeCopy(Game.level.squares[posX + 23][posY + 20], null);

		// Town square pavement
		Path townSquare = new Path(Game.level.squares[posX + 23][posY + 19], Game.level.squares[posX + 24][posY + 19],
				Game.level.squares[posX + 25][posY + 19], Game.level.squares[posX + 22][posY + 20],
				Game.level.squares[posX + 23][posY + 20], Game.level.squares[posX + 24][posY + 20],
				Game.level.squares[posX + 25][posY + 20], Game.level.squares[posX + 26][posY + 20],
				Game.level.squares[posX + 22][posY + 21], Game.level.squares[posX + 23][posY + 21],
				Game.level.squares[posX + 24][posY + 21], Game.level.squares[posX + 25][posY + 21],
				Game.level.squares[posX + 26][posY + 21], Game.level.squares[posX + 22][posY + 22],
				Game.level.squares[posX + 23][posY + 22], Game.level.squares[posX + 24][posY + 22],
				Game.level.squares[posX + 25][posY + 22], Game.level.squares[posX + 26][posY + 22],
				Game.level.squares[posX + 23][posY + 23], Game.level.squares[posX + 24][posY + 23],
				Game.level.squares[posX + 25][posY + 23]);

		// Path left of fountain
		new Path(posX + 4, posY + 21, posX + 21, posY + 21);

		// Path to shop
		new Path(posX + 4, posY + 4, posX + 4, posY + 21);

		// Path to farm
		new Path(posX + 4, posY + 21, posX + 4, posY + 62);
		new Path(posX + 4, posY + 62, posX + 18, posY + 62);
		new Path(posX + 18, posY + 62, posX + 18, posY + 68);

		// Signpost 5,20
		Sign signpost = Templates.SIGNPOST.makeCopy(Game.level.squares[posX + 5][posY + 20], "Signpost",

				new Object[] { GameObject.upTexture, " Shop  ", GameObject.rightTexture, " Estates  ",
						GameObject.downTexture, " Farm" },
				null);

		// "North - Shop, Hunter's Lodge; East - Estates; South - Farm",

		// Trees around town square
		Templates.TREE.makeCopy(Game.level.squares[posX + 22][posY + 19], null);
		Templates.TREE.makeCopy(Game.level.squares[posX + 22][posY + 23], null);
		Templates.TREE.makeCopy(Game.level.squares[posX + 26][posY + 19], null);
		Templates.TREE.makeCopy(Game.level.squares[posX + 26][posY + 23], null);

		links = TextUtils.getLinks(true, this);

	}

	@Override
	public void update() {
		if (resolved)
			return;

		super.update();

	}

}
