package com.marklynch.level.squares;

import java.util.ArrayList;

import com.marklynch.utils.Color;

public class Nodes {

	static ArrayList<Node> nodes = new ArrayList<Node>();
	// Town
	public static Node townShopInner; // 11,4
	public static Node townShopOuter; // 5,4
	public static Node townShopCrossRoads; // 4,21
	public static Node townCenter; // 24,22
	public static Node townNorth; // 24,18
	public static Node townEast; // 111,18
	public static Node lodgeJunction; // 104,18
	public static Node lodgeEntrance; // 105,12

	public static Node forestNorth; // 160,18
	public static Node forestNorthEast; // 210,18

	public static Node caveOfTheBlindWest; // 224,16
	public static Node minorMine; // 280,87

	public static Node dungeonHouseOuter; // 24,30

	public static Node wallsHouseOuter; // 41,21

	// Farm
	public static Node farmOuterBend; // 4,62
	public static Node farmInnerBend; // 18,62

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

		wallsHouseOuter = new Node("Walls House", squares[41][21]);
		nodes.add(wallsHouseOuter);

		townNorth = new Node("Town North", squares[24][18]);
		nodes.add(townNorth);

		townEast = new Node("Town East", squares[111][18]);
		nodes.add(townEast);

		lodgeJunction = new Node("Lodge Junction", squares[104][18]);
		nodes.add(lodgeJunction);

		lodgeEntrance = new Node("Lodge", squares[105][12]);
		nodes.add(lodgeEntrance);

		farmOuterBend = new Node("Farm Road West", squares[4][62]);
		nodes.add(farmOuterBend);

		farmInnerBend = new Node("Farm Road", squares[18][62]);
		nodes.add(farmInnerBend);

		// forest
		forestNorth = new Node("Forest North", squares[160][18]);
		nodes.add(forestNorth);

		forestNorthEast = new Node("Forest North East", squares[210][18]);
		nodes.add(forestNorthEast);

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
		townShopCrossRoads.neighbors.add(farmOuterBend);

		townCenter.neighbors.add(townShopCrossRoads);
		townCenter.neighbors.add(dungeonHouseOuter);
		townCenter.neighbors.add(wallsHouseOuter);
		townCenter.neighbors.add(townNorth);

		dungeonHouseOuter.neighbors.add(townCenter);

		wallsHouseOuter.neighbors.add(townCenter);

		townNorth.neighbors.add(townCenter);
		townNorth.neighbors.add(lodgeJunction);

		lodgeJunction.neighbors.add(townEast);
		lodgeJunction.neighbors.add(lodgeEntrance);
		lodgeJunction.neighbors.add(townNorth);

		lodgeEntrance.neighbors.add(lodgeJunction);

		townEast.neighbors.add(lodgeJunction);
		townEast.neighbors.add(forestNorth);

		farmOuterBend.neighbors.add(townShopCrossRoads);
		farmOuterBend.neighbors.add(farmInnerBend);

		farmInnerBend.neighbors.add(farmOuterBend);

		// forest
		forestNorth.neighbors.add(townEast);
		forestNorth.neighbors.add(forestNorthEast);

		forestNorthEast.neighbors.add(forestNorth);
		forestNorthEast.neighbors.add(caveOfTheBlindWest);

		// mine
		caveOfTheBlindWest.neighbors.add(forestNorthEast);
		caveOfTheBlindWest.neighbors.add(minorMine);

		minorMine.neighbors.add(caveOfTheBlindWest);

	}

	public static Color color = new Color(1, 1, 1, 0.5f);

	public static void draw() {
		// for (Node node : nodes) {
		// QuadUtils.drawQuad(color, node.square.xInGridPixels,
		// node.square.yInGridPixels,
		// node.square.xInGridPixels + Game.SQUARE_WIDTH,
		// node.square.yInGridPixels + Game.SQUARE_HEIGHT);
		// for (Node neighbor : node.neighbors) {
		// LineUtils.drawLine(color, node.square.xInGridPixels,
		// node.square.yInGridPixels,
		// neighbor.square.xInGridPixels + Game.SQUARE_WIDTH,
		// neighbor.square.yInGridPixels + Game.SQUARE_HEIGHT, 10);
		// }
		// }
	}

}
