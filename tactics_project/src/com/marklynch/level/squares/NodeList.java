package com.marklynch.level.squares;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.constructs.area.town.AreaTown;
import com.marklynch.utils.Color;
import com.marklynch.utils.LineUtils;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.Utils.Point;

public class NodeList {

	public static ArrayList<Node> nodes = new ArrayList<Node>();
	// Town
	public static Node townShopInner; // 11,4
	public static Node townShopOuter; // 5,4
	public static Node townShopCrossRoads; // 4,21
	public static Node townCenter; // 24,22
	public static Node townNorth; // 24,18
	public static Node townDoctorsInner; // 11,4
	public static Node townDoctorsOuter; // 5,4
	public static Node townNorthEast; // 24,18
	public static Node townEast; // 24,18
	public static Node townSouthEast; // 24,18
//	public static Node lodgeJunction; // 104,18
	public static Node lodgeEntrance; // 105,12

	public static Node dungeonHouseOuter; // 24,30
	public static Node dungeonHouseHiddenArea; // 22,36

	public static Node wallHouseOuter; // 41,26
	public static Node wallHouseBedroom; // 42,28
	public static Node wallHouseFireplace;
	public static Node wallHouseFalseWall; // 52,27

	public static Node barracksNorth; // 80,52
	public static Node barracksSouth; // 80,61

	// Farm
	public static Node farmRoadWest; // 4,62
	public static Node farmRoadEast; // 18,62
	public static Node farmEntrance; // 18,62
	public static Node farmHallway; // 4,62
	public static Node farmBedroom; // 18,62
	public static Node farmStorage; // 4,62
	public static Node farmBackDoor; // 18,62
	public static Node farmPigPen; // 4,62
	public static Node farmBarnDoorway; // 19,95

	// Forest
	public static Node forestNorthWest; // 111,18
	public static Node forestNorth; // 160,18
	public static Node forestNorthEast; // 210,18
	public static Node forestThiefHut; // 210,18
	public static Node forestRangersHut; // 210,18

	// Mines
	public static Node caveOfTheBlindWest; // 224,16
	public static Node minorMine; // 280,87

	public NodeList(Square[][] squares) {

		// Town outdoors
		townShopCrossRoads = new Node("Town Shop Crossroads", squares[AreaTown.posX + 4][AreaTown.posY + 21],
				new Point(0, 0), new Point(14, 38));
		nodes.add(townShopCrossRoads);

		townNorth = new Node("Town North", squares[AreaTown.posX + 24][AreaTown.posY + 7], new Point(15, 0),
				new Point(69, 14));
		nodes.add(townNorth);

		townNorthEast = new Node("Town North East", squares[AreaTown.posX + 89][AreaTown.posY + 19], new Point(70, 0),
				new Point(100, 38));
		nodes.add(townNorthEast);

		townCenter = new Node("Town Center", squares[AreaTown.posX + 24][AreaTown.posY + 21], new Point(15, 15),
				new Point(69, 38));
		nodes.add(townCenter);

		townEast = new Node("Town East", squares[AreaTown.posX + 89][AreaTown.posY + 40], new Point(70, 39),
				new Point(100, 56));
		nodes.add(townEast);

		townSouthEast = new Node("Town South East", squares[AreaTown.posX + 89][AreaTown.posY + 63], new Point(70, 57),
				new Point(100, 86));
		nodes.add(townSouthEast);

		farmRoadWest = new Node("Farm Road West", squares[4][62], new Point(0, 39), new Point(14, 86));
		nodes.add(farmRoadWest);

		farmRoadEast = new Node("Farm Road East", squares[18][62], new Point(15, 39), new Point(69, 86));
		nodes.add(farmRoadEast);

		// Town indoors
		townShopInner = new Node("Town Shop Private Quarters", squares[AreaTown.posX + 11][AreaTown.posY + 4],
				new Point(11, 0), new Point(17, 5));
		nodes.add(townShopInner);

		townShopOuter = new Node("Town Shop", squares[AreaTown.posX + 5][AreaTown.posY + 4], new Point(5, 0),
				new Point(10, 5));
		nodes.add(townShopOuter);

		dungeonHouseOuter = new Node("Dungeon House", squares[AreaTown.posX + 24][AreaTown.posY + 30],
				new Point(21, 30), new Point(29, 35));
		nodes.add(dungeonHouseOuter);

		dungeonHouseHiddenArea = new Node("Dungeon House Secret", squares[AreaTown.posX + 22][AreaTown.posY + 36],
				new Point(19, 36), new Point(36, 46));
		nodes.add(dungeonHouseHiddenArea);

		wallHouseOuter = new Node("Walls House", squares[AreaTown.posX + 41][AreaTown.posY + 26]);
		nodes.add(wallHouseOuter);

		wallHouseBedroom = new Node("Walls House Bedroom", squares[AreaTown.posX + 42][AreaTown.posY + 33]);// 42,28
		nodes.add(wallHouseBedroom);

		wallHouseFireplace = new Node("Walls House Fireplace", squares[AreaTown.posX + 48][AreaTown.posY + 34]);// 42,28
		nodes.add(wallHouseFireplace);

		wallHouseFalseWall = new Node("Walls House Secret", squares[AreaTown.posX + 52][AreaTown.posY + 31]); // 52,27
		nodes.add(wallHouseFalseWall);

		townDoctorsOuter = new Node("Doctor's Practice", squares[AreaTown.posX + 40][AreaTown.posY + 7]);
		nodes.add(townDoctorsOuter);

		townDoctorsInner = new Node("Doctor's Practice Private Quarters",
				squares[AreaTown.posX + 46][AreaTown.posY + 7]);
		nodes.add(townDoctorsInner);

		barracksNorth = new Node("Barracks North", squares[AreaTown.posX + 80][AreaTown.posY + 52]);
		nodes.add(barracksNorth);

		barracksSouth = new Node("Barracks South", squares[AreaTown.posX + 80][AreaTown.posY + 61]);
		nodes.add(barracksSouth);

		lodgeEntrance = new Node("Lodge", squares[105][12]);
		nodes.add(lodgeEntrance);

		farmEntrance = new Node("Farm Entrance", squares[18][69]);
		nodes.add(farmEntrance);

		farmHallway = new Node("Farm Hallway", squares[18][75]);
		nodes.add(farmHallway);

		farmBedroom = new Node("Farm Bedroom", squares[17][78]);
		nodes.add(farmBedroom);

		farmStorage = new Node("Farm Storage", squares[21][78]);
		nodes.add(farmStorage);

		farmBackDoor = new Node("Farm Back Door", squares[21][85]);
		nodes.add(farmBackDoor);

		farmPigPen = new Node("Farm Pig Pen", squares[32][72]);
		nodes.add(farmPigPen);

		farmBarnDoorway = new Node("Farm Pig Pen", squares[21][95]);
		nodes.add(farmBarnDoorway);

		// forest
		forestNorthWest = new Node("Town North East", squares[111][18]);
		nodes.add(forestNorthWest);

		forestNorth = new Node("Forest North", squares[160][18]);
		nodes.add(forestNorth);

		forestNorthEast = new Node("Forest North East", squares[210][18]);
		nodes.add(forestNorthEast);

		forestThiefHut = new Node("Hut", squares[113][53]);
		nodes.add(forestThiefHut);

		forestRangersHut = new Node("Forest Ranger's Hut", squares[133][34]);
		nodes.add(forestRangersHut);

		// Mine
		caveOfTheBlindWest = new Node("Cave of the Blind West", squares[234][16]);
		nodes.add(caveOfTheBlindWest);

		minorMine = new Node("Minor Mine", squares[280][87]);
		nodes.add(minorMine);

		// Making links
		// town
		townShopInner.neighbors.add(townShopOuter);

		townShopOuter.neighbors.add(townShopInner);
		townShopOuter.neighbors.add(townShopCrossRoads);

		townShopCrossRoads.neighbors.add(townShopOuter);
		townShopCrossRoads.neighbors.add(townCenter);
		townShopCrossRoads.neighbors.add(farmRoadWest);

		townCenter.neighbors.add(townShopCrossRoads);
		townCenter.neighbors.add(dungeonHouseOuter);
		townCenter.neighbors.add(wallHouseOuter);
		townCenter.neighbors.add(townNorth);
		townCenter.neighbors.add(townNorthEast);
		// townCenter.neighbors.add(townEast);
		// townCenter.neighbors.add(barracksNorth);
		// townCenter.neighbors.add(barracksSouth);
		// townCenter.neighbors.add(farmRoadEast);

		// barracksNorth.neighbors.add(townCenter);
		barracksNorth.neighbors.add(barracksSouth);
		barracksNorth.neighbors.add(townEast);

		// barracksSouth.neighbors.add(townCenter);
		barracksSouth.neighbors.add(barracksNorth);
		barracksSouth.neighbors.add(townSouthEast);

		dungeonHouseOuter.neighbors.add(townCenter);
		dungeonHouseOuter.neighbors.add(dungeonHouseHiddenArea);

		dungeonHouseHiddenArea.neighbors.add(dungeonHouseOuter);

		wallHouseOuter.neighbors.add(townCenter);
		wallHouseOuter.neighbors.add(wallHouseBedroom);
		wallHouseOuter.neighbors.add(wallHouseFalseWall);

		wallHouseFalseWall.neighbors.add(wallHouseOuter);

		wallHouseBedroom.neighbors.add(wallHouseOuter);

		wallHouseFireplace.neighbors.add(wallHouseFalseWall);
		wallHouseFireplace.neighbors.add(wallHouseBedroom);

		townNorth.neighbors.add(townCenter);
		// townNorth.neighbors.add(townNorthEast);
		townNorth.neighbors.add(townDoctorsOuter);

		townDoctorsOuter.neighbors.add(townDoctorsInner);
		townDoctorsOuter.neighbors.add(townNorth);

		townDoctorsInner.neighbors.add(townDoctorsOuter);

		townNorthEast.neighbors.add(townCenter);
		townNorthEast.neighbors.add(townEast);
		townNorthEast.neighbors.add(lodgeEntrance);
		townNorthEast.neighbors.add(forestNorthWest);

		// townEast.neighbors.add(townCenter);
		townEast.neighbors.add(townNorthEast);
		townEast.neighbors.add(townSouthEast);
		townEast.neighbors.add(barracksNorth);

		townSouthEast.neighbors.add(townEast);
		townSouthEast.neighbors.add(barracksSouth);
		townSouthEast.neighbors.add(farmRoadEast);

		lodgeEntrance.neighbors.add(townNorthEast);

		// Farm
		farmRoadWest.neighbors.add(townShopCrossRoads);
		farmRoadWest.neighbors.add(farmRoadEast);

		farmRoadEast.neighbors.add(farmRoadWest);
		farmRoadEast.neighbors.add(farmEntrance);
		farmRoadEast.neighbors.add(farmPigPen);
		farmRoadEast.neighbors.add(farmBarnDoorway);
		farmRoadEast.neighbors.add(farmBackDoor);
		farmRoadEast.neighbors.add(townSouthEast);
		// farmRoadEast.neighbors.add(townCenter);

		farmEntrance.neighbors.add(farmRoadEast);
		farmEntrance.neighbors.add(farmHallway);

		farmHallway.neighbors.add(farmEntrance);
		farmHallway.neighbors.add(farmBedroom);
		farmHallway.neighbors.add(farmStorage);

		farmBedroom.neighbors.add(farmHallway);

		farmStorage.neighbors.add(farmHallway);
		farmStorage.neighbors.add(farmBackDoor);

		farmBackDoor.neighbors.add(farmStorage);
		farmBackDoor.neighbors.add(farmRoadEast);

		farmPigPen.neighbors.add(farmRoadEast);
		farmBarnDoorway.neighbors.add(farmRoadEast);

		// forest
		forestNorthWest.neighbors.add(townNorthEast);
		forestNorthWest.neighbors.add(forestNorth);
		forestNorthWest.neighbors.add(forestThiefHut);
		forestNorthWest.neighbors.add(forestRangersHut);

		forestNorth.neighbors.add(forestNorthWest);
		forestNorth.neighbors.add(forestNorthEast);
		forestNorth.neighbors.add(forestThiefHut);
		forestNorth.neighbors.add(forestRangersHut);

		forestNorthEast.neighbors.add(forestNorth);
		forestNorthEast.neighbors.add(caveOfTheBlindWest);

		forestThiefHut.neighbors.add(forestNorth);
		forestThiefHut.neighbors.add(forestNorthWest);
		forestThiefHut.neighbors.add(forestRangersHut);

		forestRangersHut.neighbors.add(forestNorthWest);
		forestRangersHut.neighbors.add(forestNorth);
		forestRangersHut.neighbors.add(forestThiefHut);

		// mine
		caveOfTheBlindWest.neighbors.add(forestNorthEast);
		caveOfTheBlindWest.neighbors.add(minorMine);

		minorMine.neighbors.add(caveOfTheBlindWest);

	}

	public static Color color = new Color(1, 1, 1, 0.5f);

	public static void draw() {
		if (!Game.drawNodes)
			return;

		for (Node node : nodes) {
			QuadUtils.drawQuad(color, node.square.xInGridPixels, node.square.yInGridPixels,
					node.square.xInGridPixels + Game.SQUARE_WIDTH, node.square.yInGridPixels + Game.SQUARE_HEIGHT);
			for (Node neighbor : node.neighbors) {
				LineUtils.drawLine(color, node.square.xInGridPixels, node.square.yInGridPixels,
						neighbor.square.xInGridPixels + Game.SQUARE_WIDTH,
						neighbor.square.yInGridPixels + Game.SQUARE_HEIGHT, 10);
			}
		}
	}

}
