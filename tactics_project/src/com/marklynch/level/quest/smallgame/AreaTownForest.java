package com.marklynch.level.quest.smallgame;

import com.marklynch.Game;
import com.marklynch.level.constructs.Group;
import com.marklynch.level.constructs.bounds.Area;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.Templates;
import com.marklynch.objects.Tree;

public class AreaTownForest {
	public static void createForest() {

		// How I mafe the forest shape -

		// Rough calculations;
		int centerX = 160;
		int centerY = 50;

		int maxDiffFromCenterX = 50;
		int maxDiffFromCenterY = 50;

		for (int i = 0; i < 10000; i++) {

			int randomX = (int) (103 + (113 * Math.random()));
			int randomY = (int) (3 + (95 * Math.random()));

			if (randomX > 152 && randomX < 167 && randomY > 47 && randomY < 63)
				continue;

			if (Math.random() * maxDiffFromCenterX < Math.abs(randomX - centerX))
				continue;
			if (Math.random() * maxDiffFromCenterY < Math.abs(randomY - centerY))
				continue;

			if (Game.level.squares[randomX][randomY].inventory.getGameObjectOfClass(Tree.class) == null) {
				if ((Math.abs(randomX - centerX) + Math.random() * 10) < 25
						&& (Math.abs(randomY - centerY) + Math.random() * 10) < 25) {
					// System.out.println(
					// "Templates.BIG_TREE.makeCopy(Game.level.squares[" +
					// randomX + "][" + randomY + "], null);");
					Templates.BIG_TREE.makeCopy(Game.level.squares[randomX][randomY], null);
				} else {
					// System.out.println(
					// "Templates.TREE.makeCopy(Game.level.squares[" + randomX +
					// "][" + randomY + "], null);");
					Templates.TREE.makeCopy(Game.level.squares[randomX][randomY], null);
				}
			}

		}

		for (int i = 152; i < 167; i++) {
			if (Game.level.squares[i][47].inventory.getGameObjectOfClass(Tree.class) == null) {
				Templates.BIG_TREE.makeCopy(Game.level.squares[i][47], null);
			}

			if (Game.level.squares[i][48].inventory.getGameObjectOfClass(Tree.class) == null) {
				Templates.BIG_TREE.makeCopy(Game.level.squares[i][48], null);
			}

			if (Game.level.squares[i][62].inventory.getGameObjectOfClass(Tree.class) == null) {
				Templates.BIG_TREE.makeCopy(Game.level.squares[i][62], null);
			}

			if (Game.level.squares[i][63].inventory.getGameObjectOfClass(Tree.class) == null) {
				Templates.BIG_TREE.makeCopy(Game.level.squares[i][63], null);
			}

		}

		for (int j = 47; j < 63; j++) {
			if (Game.level.squares[151][j].inventory.getGameObjectOfClass(Tree.class) == null) {
				Templates.BIG_TREE.makeCopy(Game.level.squares[151][j], null);
			}

			if (Game.level.squares[152][j].inventory.getGameObjectOfClass(Tree.class) == null) {
				Templates.BIG_TREE.makeCopy(Game.level.squares[152][j], null);
			}

			if (Game.level.squares[167][j].inventory.getGameObjectOfClass(Tree.class) == null) {
				Templates.BIG_TREE.makeCopy(Game.level.squares[167][j], null);
			}

			if (Game.level.squares[168][j].inventory.getGameObjectOfClass(Tree.class) == null) {
				Templates.BIG_TREE.makeCopy(Game.level.squares[168][j], null);
			}

		}

		Templates.BIG_TREE.makeCopy(Game.level.squares[153][49], null);
		Templates.BIG_TREE.makeCopy(Game.level.squares[153][52], null);
		Templates.BIG_TREE.makeCopy(Game.level.squares[153][55], null);
		Templates.BIG_TREE.makeCopy(Game.level.squares[153][55], null);
		Templates.BIG_TREE.makeCopy(Game.level.squares[153][61], null);
		Templates.BIG_TREE.makeCopy(Game.level.squares[166][49], null);
		Templates.BIG_TREE.makeCopy(Game.level.squares[166][52], null);
		Templates.BIG_TREE.makeCopy(Game.level.squares[166][55], null);
		Templates.BIG_TREE.makeCopy(Game.level.squares[166][58], null);
		Templates.BIG_TREE.makeCopy(Game.level.squares[166][55], null);
		Templates.BIG_TREE.makeCopy(Game.level.squares[166][61], null);
		// Templates.BIG_TREE.makeCopy(Game.level.squares[166][49], null);
		// Templates.BIG_TREE.makeCopy(Game.level.squares[166][62], null);

		Area areaTownForest = new Area("Town Forest", "map_forest.png", Square.DARK_GRASS_TEXTURE, 121, 11, 199, 90);
		Game.level.areas.add(areaTownForest);
		Area areaInnerTownForest = new Area("Inner Town Forest", null, Square.DARK_GRASS_TEXTURE, 146, 33, 180, 63);
		Game.level.areas.add(areaInnerTownForest);

		// How to give them an area?
		// Lets have a look @ Mort.

		// [188][61]

		new Group("Rabbits",
				Templates.RABBIT.makeCopy("Female Rabbit", Game.level.squares[183][61], Game.level.factions.get(2),
						null, areaTownForest),
				Templates.RABBIT.makeCopy("Male Rabbit", Game.level.squares[182][62], Game.level.factions.get(2), null,
						areaTownForest),
				Templates.BABY_RABBIT.makeCopy("Rabbit", Game.level.squares[181][63], Game.level.factions.get(2), null,
						areaTownForest),
				Templates.BABY_RABBIT.makeCopy("Baby Rabbit", Game.level.squares[183][62], Game.level.factions.get(2),
						null, areaTownForest),
				Templates.BABY_RABBIT.makeCopy("Baby Rabbit", Game.level.squares[182][63], Game.level.factions.get(2),
						null, areaTownForest),
				Templates.BABY_RABBIT.makeCopy("Baby Rabbit", Game.level.squares[181][61], Game.level.factions.get(2),
						null, areaTownForest));

		Templates.FOX.makeCopy("Fox", Game.level.squares[200][78], Game.level.factions.get(2), null, areaTownForest);

		// Mushrooms
		Templates.MUSHROOM.makeCopy(Game.level.squares[115][84], null);
		Templates.MUSHROOM.makeCopy(Game.level.squares[125][66], null);
		Templates.MUSHROOM.makeCopy(Game.level.squares[133][62], null);
		Templates.MUSHROOM.makeCopy(Game.level.squares[136][52], null);
		Templates.MUSHROOM.makeCopy(Game.level.squares[142][23], null);
		Templates.MUSHROOM.makeCopy(Game.level.squares[143][47], null);
		Templates.MUSHROOM.makeCopy(Game.level.squares[144][49], null);
		Templates.MUSHROOM.makeCopy(Game.level.squares[148][70], null);
		Templates.MUSHROOM.makeCopy(Game.level.squares[151][48], null);
		Templates.MUSHROOM.makeCopy(Game.level.squares[146][80], null);
		Templates.MUSHROOM.makeCopy(Game.level.squares[154][62], null);
		Templates.MUSHROOM.makeCopy(Game.level.squares[156][53], null);
		Templates.MUSHROOM.makeCopy(Game.level.squares[158][47], null);
		Templates.MUSHROOM.makeCopy(Game.level.squares[158][47], null);
		Templates.MUSHROOM.makeCopy(Game.level.squares[160][58], null);
		Templates.MUSHROOM.makeCopy(Game.level.squares[160][63], null);
		Templates.MUSHROOM.makeCopy(Game.level.squares[160][66], null);
		Templates.MUSHROOM.makeCopy(Game.level.squares[161][29], null);
		Templates.MUSHROOM.makeCopy(Game.level.squares[161][44], null);
		Templates.MUSHROOM.makeCopy(Game.level.squares[161][56], null);
		Templates.MUSHROOM.makeCopy(Game.level.squares[161][63], null);
		Templates.MUSHROOM.makeCopy(Game.level.squares[162][54], null);
		Templates.MUSHROOM.makeCopy(Game.level.squares[162][56], null);
		Templates.MUSHROOM.makeCopy(Game.level.squares[162][61], null);
		Templates.MUSHROOM.makeCopy(Game.level.squares[163][47], null);
		Templates.MUSHROOM.makeCopy(Game.level.squares[163][54], null);
		Templates.MUSHROOM.makeCopy(Game.level.squares[163][56], null);
		Templates.MUSHROOM.makeCopy(Game.level.squares[164][47], null);
		Templates.MUSHROOM.makeCopy(Game.level.squares[166][79], null);
		Templates.MUSHROOM.makeCopy(Game.level.squares[174][46], null);
		Templates.MUSHROOM.makeCopy(Game.level.squares[177][36], null);
		Templates.MUSHROOM.makeCopy(Game.level.squares[177][38], null);
		Templates.MUSHROOM.makeCopy(Game.level.squares[182][71], null);
		Templates.MUSHROOM.makeCopy(Game.level.squares[186][40], null);
		Templates.MUSHROOM.makeCopy(Game.level.squares[186][42], null);
		Templates.MUSHROOM.makeCopy(Game.level.squares[198][41], null);
		Templates.MUSHROOM.makeCopy(Game.level.squares[189][79], null);
		Templates.MUSHROOM.makeCopy(Game.level.squares[204][57], null);

		// Burrows
		Templates.BURROW.makeCopy(Game.level.squares[119][42], null);
		Templates.BURROW.makeCopy(Game.level.squares[188][61], null);
		Templates.BURROW.makeCopy(Game.level.squares[153][58], null);
		Templates.BURROW.makeCopy(Game.level.squares[160][67], null);
		Templates.BURROW.makeCopy(Game.level.squares[166][64], null);
		Templates.BURROW.makeCopy(Game.level.squares[171][61], null);
		Templates.BURROW.makeCopy(Game.level.squares[196][27], null);

	}

}
