package com.marklynch.level.squares;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.utils.Color;
import com.marklynch.utils.LineUtils;
import com.marklynch.utils.QuadUtils;

public class Nodes {

	static ArrayList<Node> nodes = new ArrayList<Node>();
	// Town
	public static Node townShopInner; // 11,4
	public static Node townShopOuter; // 5,4
	public static Node townShopCrossRoads; // 4,21
	public static Node townCenter; // 24,22
	public static Node townNorth; // 24,18
	public static Node townNorthEast; // 24,18
	public static Node townEast; // 24,18
	public static Node townSouthEast; // 24,18
	public static Node lodgeJunction; // 104,18
	public static Node lodgeEntrance; // 105,12

	public static Node dungeonHouseOuter; // 24,30
	public static Node dungeonHouseHiddenArea; // 22,36

	public static Node wallsHouseOuter; // 41,21
	public static Node wallHouseBedroom; // 42,28
	public static Node wallHouseHiddenArea; // 52,27

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

	// Forest
	public static Node forestNorthWest; // 111,18
	public static Node forestNorth; // 160,18
	public static Node forestNorthEast; // 210,18
	public static Node forestThiefHut; // 210,18
	public static Node forestRangersHut; // 210,18

	// Mines
	public static Node caveOfTheBlindWest; // 224,16
	public static Node minorMine; // 280,87

	public Nodes(Square[][] squares) {

		// town
		townShopInner = new Node("Town Shop Private Quarters", squares[11][4]);
		nodes.add(townShopInner);

		townShopOuter = new Node("Town Shop", squares[5][4]);
		nodes.add(townShopOuter);

		townShopCrossRoads = new Node("Town Shop Crossroads", squares[4][21]);
		nodes.add(townShopCrossRoads);

		townCenter = new Node("Town Center", squares[24][21]);
		nodes.add(townCenter);

		dungeonHouseOuter = new Node("Dungeon House", squares[24][30]);
		nodes.add(dungeonHouseOuter);

		dungeonHouseHiddenArea = new Node("Dungeon House Secret", squares[22][36]); // 22,36
		nodes.add(dungeonHouseHiddenArea);

		wallsHouseOuter = new Node("Walls House", squares[41][21]);
		nodes.add(wallsHouseOuter);

		wallHouseBedroom = new Node("Walls House Bedroom", squares[42][28]);// 42,28
		nodes.add(wallHouseBedroom);

		wallHouseHiddenArea = new Node("Walls House Secret", squares[52][27]); // 52,27
		nodes.add(wallHouseHiddenArea);

		townNorth = new Node("Town North", squares[24][18]);
		nodes.add(townNorth);

		townNorthEast = new Node("Town North East", squares[89][19]);
		nodes.add(townNorthEast);

		townEast = new Node("Town East", squares[89][40]);
		nodes.add(townEast);

		townSouthEast = new Node("Town South East", squares[89][63]);
		nodes.add(townSouthEast);

		barracksNorth = new Node("Barracks North", squares[80][52]);
		nodes.add(barracksNorth);

		barracksSouth = new Node("Barracks South", squares[80][61]);
		nodes.add(barracksSouth);

		lodgeJunction = new Node("Lodge Junction", squares[104][18]);
		nodes.add(lodgeJunction);

		lodgeEntrance = new Node("Lodge", squares[105][12]);
		nodes.add(lodgeEntrance);

		// Farm
		farmRoadWest = new Node("Farm Road West", squares[4][62]);
		nodes.add(farmRoadWest);

		farmRoadEast = new Node("Farm Road East", squares[18][62]);
		nodes.add(farmRoadEast);

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
		townCenter.neighbors.add(wallsHouseOuter);
		townCenter.neighbors.add(townNorth);
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

		wallsHouseOuter.neighbors.add(townCenter);
		wallsHouseOuter.neighbors.add(wallHouseBedroom);
		wallsHouseOuter.neighbors.add(wallHouseHiddenArea);

		wallHouseHiddenArea.neighbors.add(wallsHouseOuter);

		wallHouseBedroom.neighbors.add(wallsHouseOuter);

		townNorth.neighbors.add(townCenter);
		townNorth.neighbors.add(townNorthEast);

		townNorthEast.neighbors.add(townNorth);
		townNorthEast.neighbors.add(townEast);
		townNorthEast.neighbors.add(lodgeJunction);

		// townEast.neighbors.add(townCenter);
		townEast.neighbors.add(townNorthEast);
		townEast.neighbors.add(townSouthEast);
		townEast.neighbors.add(barracksNorth);

		townSouthEast.neighbors.add(townEast);
		townSouthEast.neighbors.add(barracksSouth);
		townSouthEast.neighbors.add(farmRoadEast);

		lodgeJunction.neighbors.add(forestNorthWest);
		lodgeJunction.neighbors.add(lodgeEntrance);
		lodgeJunction.neighbors.add(townNorthEast);

		lodgeEntrance.neighbors.add(lodgeJunction);

		// Farm
		farmRoadWest.neighbors.add(townShopCrossRoads);
		farmRoadWest.neighbors.add(farmRoadEast);

		farmRoadEast.neighbors.add(farmRoadWest);
		farmRoadEast.neighbors.add(farmEntrance);
		farmRoadEast.neighbors.add(farmPigPen);
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

		// forest
		forestNorthWest.neighbors.add(lodgeJunction);
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
