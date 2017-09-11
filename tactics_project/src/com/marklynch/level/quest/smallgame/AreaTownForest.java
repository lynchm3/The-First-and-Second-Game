package com.marklynch.level.quest.smallgame;

import com.marklynch.Game;
import com.marklynch.level.constructs.Group;
import com.marklynch.level.constructs.bounds.Area;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Templates;
import com.marklynch.objects.Tree;

public class AreaTownForest {

	public static Area area;

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

		for (int i = 154; i < 166; i++) {
			if (Game.level.squares[i][47].inventory.getGameObjectOfClass(Tree.class) == null) {
				Templates.BIG_TREE.makeCopy(Game.level.squares[i][47], null);
			}

			if (Game.level.squares[i][48].inventory.getGameObjectOfClass(Tree.class) == null) {
				Templates.BIG_TREE.makeCopy(Game.level.squares[i][48], null);
			}

			if (Game.level.squares[i][63].inventory.getGameObjectOfClass(Tree.class) == null) {
				Templates.BIG_TREE.makeCopy(Game.level.squares[i][63], null);
			}

			if (Game.level.squares[i][64].inventory.getGameObjectOfClass(Tree.class) == null) {
				Templates.BIG_TREE.makeCopy(Game.level.squares[i][64], null);
			}

		}

		for (int j = 49; j < 62; j++) {
			if (Game.level.squares[152][j].inventory.getGameObjectOfClass(Tree.class) == null) {
				Templates.BIG_TREE.makeCopy(Game.level.squares[152][j], null);
			}

			if (Game.level.squares[153][j].inventory.getGameObjectOfClass(Tree.class) == null) {
				Templates.BIG_TREE.makeCopy(Game.level.squares[153][j], null);
			}

			if (Game.level.squares[167][j].inventory.getGameObjectOfClass(Tree.class) == null) {
				Templates.BIG_TREE.makeCopy(Game.level.squares[167][j], null);
			}

			if (Game.level.squares[168][j].inventory.getGameObjectOfClass(Tree.class) == null) {
				Templates.BIG_TREE.makeCopy(Game.level.squares[168][j], null);
			}

		}

		Templates.SCROLL.makeCopy(Game.level.squares[160][55], "Scroll of Poison Blast",
				new Object[] {
						"[You learn Poison Blast]\nPoisons for base 3 dmg/s Range:10\nUse the power of the forest to murder people with poisons. Nice" },
				null);

		// corners
		Templates.BIG_TREE.makeCopy(Game.level.squares[154][49], null);
		Templates.BIG_TREE.makeCopy(Game.level.squares[154][62], null);
		Templates.BIG_TREE.makeCopy(Game.level.squares[166][49], null);
		Templates.BIG_TREE.makeCopy(Game.level.squares[166][62], null);

		// smoother corners
		Templates.BIG_TREE.makeCopy(Game.level.squares[154][61], null);
		Templates.BIG_TREE.makeCopy(Game.level.squares[155][62], null);
		Templates.BIG_TREE.makeCopy(Game.level.squares[165][62], null);
		Templates.BIG_TREE.makeCopy(Game.level.squares[166][61], null);
		Templates.BIG_TREE.makeCopy(Game.level.squares[166][50], null);
		Templates.BIG_TREE.makeCopy(Game.level.squares[165][49], null);
		Templates.BIG_TREE.makeCopy(Game.level.squares[155][49], null);
		Templates.BIG_TREE.makeCopy(Game.level.squares[154][50], null);

		// smootherer
		// cornersTemplates.BIG_TREE.makeCopy(Game.level.square[157][49],null);
		Templates.BIG_TREE.makeCopy(Game.level.squares[157][49], null);
		Templates.BIG_TREE.makeCopy(Game.level.squares[156][49], null);
		Templates.BIG_TREE.makeCopy(Game.level.squares[155][50], null);
		Templates.BIG_TREE.makeCopy(Game.level.squares[154][51], null);
		Templates.BIG_TREE.makeCopy(Game.level.squares[154][52], null);
		Templates.BIG_TREE.makeCopy(Game.level.squares[163][49], null);
		Templates.BIG_TREE.makeCopy(Game.level.squares[164][49], null);
		Templates.BIG_TREE.makeCopy(Game.level.squares[165][50], null);
		Templates.BIG_TREE.makeCopy(Game.level.squares[166][51], null);
		Templates.BIG_TREE.makeCopy(Game.level.squares[166][60], null);
		Templates.BIG_TREE.makeCopy(Game.level.squares[165][61], null);
		Templates.BIG_TREE.makeCopy(Game.level.squares[164][62], null);
		Templates.BIG_TREE.makeCopy(Game.level.squares[156][62], null);
		Templates.BIG_TREE.makeCopy(Game.level.squares[155][61], null);
		Templates.BIG_TREE.makeCopy(Game.level.squares[154][60], null);
		Templates.BIG_TREE.makeCopy(Game.level.squares[166][52], null);
		Templates.BIG_TREE.makeCopy(Game.level.squares[166][59], null);
		Templates.BIG_TREE.makeCopy(Game.level.squares[163][62], null);
		Templates.BIG_TREE.makeCopy(Game.level.squares[157][62], null);
		Templates.BIG_TREE.makeCopy(Game.level.squares[154][59], null);

		area = new Area("Town Forest", "map_forest.png", Square.DARK_GRASS_TEXTURE, 121, 11, 199, 90);
		Game.level.areas.add(area);

		Area areaInnerTownForest = new Area("Inner Town Forest", null, Square.DARK_GRASS_TEXTURE, 146, 33, 180, 63);
		Game.level.areas.add(areaInnerTownForest);

		// Make the edges blend
		for (int i = 121; i < 200; i++) {
			for (int j = 11; j < 91; j++) {
				float distanceFromCenter = Math.abs(i - centerX) + Math.abs(j - centerY);
				if (Math.random() * 50 > distanceFromCenter)
					continue;

				// if (Math.random() * maxDiffFromCenterX > Math.abs(i -
				// centerX) * 2)
				// continue;
				// if (Math.random() * maxDiffFromCenterY > Math.abs(j -
				// centerY) * 2)
				// continue;

				Game.level.squares[i][j].imageTexture = Square.GRASS_TEXTURE;
			}
		}

		// How to give them an area?
		// Lets have a look @ Mort.

		// [188][61]

		new Group("Buns",
				Templates.RABBIT.makeCopy("Female Bun", Game.level.squares[183][61], Game.level.factions.get(2), null,
						area, new GameObject[] { Templates.MEAT_CHUNK.makeCopy(null, null) },
						new GameObject[] { Templates.FUR.makeCopy(null, null) }),
				Templates.RABBIT.makeCopy("Male Bun", Game.level.squares[182][62], Game.level.factions.get(2), null,
						area, new GameObject[] { Templates.MEAT_CHUNK.makeCopy(null, null) },
						new GameObject[] { Templates.FUR.makeCopy(null, null) }),
				Templates.BABY_RABBIT.makeCopy("Baby Bun", Game.level.squares[181][63], Game.level.factions.get(2),
						null, area, new GameObject[] { Templates.MEAT_CHUNK.makeCopy(null, null) },
						new GameObject[] { Templates.FUR.makeCopy(null, null) }),
				Templates.BABY_RABBIT.makeCopy("Baby Bun", Game.level.squares[183][62], Game.level.factions.get(2),
						null, area, new GameObject[] { Templates.MEAT_CHUNK.makeCopy(null, null) },
						new GameObject[] { Templates.FUR.makeCopy(null, null) }),
				Templates.BABY_RABBIT.makeCopy("Baby Bun", Game.level.squares[182][63], Game.level.factions.get(2),
						null, area, new GameObject[] { Templates.MEAT_CHUNK.makeCopy(null, null) },
						new GameObject[] { Templates.FUR.makeCopy(null, null) }),
				Templates.BABY_RABBIT.makeCopy("Baby Bun", Game.level.squares[181][61], Game.level.factions.get(2),
						null, area, new GameObject[] { Templates.MEAT_CHUNK.makeCopy(null, null) },
						new GameObject[] { Templates.FUR.makeCopy(null, null) }));

		new Group("Buns",
				Templates.RABBIT.makeCopy("Female Bun", Game.level.squares[93][20], Game.level.factions.get(2), null,
						area, new GameObject[] { Templates.MEAT_CHUNK.makeCopy(null, null) },
						new GameObject[] { Templates.FUR.makeCopy(null, null) }),
				Templates.RABBIT.makeCopy("Male Bun", Game.level.squares[94][21], Game.level.factions.get(2), null,
						area, new GameObject[] { Templates.MEAT_CHUNK.makeCopy(null, null) },
						new GameObject[] { Templates.FUR.makeCopy(null, null) }),
				Templates.BABY_RABBIT.makeCopy("Baby Bun", Game.level.squares[95][19], Game.level.factions.get(2), null,
						area, new GameObject[] { Templates.MEAT_CHUNK.makeCopy(null, null) },
						new GameObject[] { Templates.FUR.makeCopy(null, null) }),
				Templates.BABY_RABBIT.makeCopy("Baby Bun", Game.level.squares[96][20], Game.level.factions.get(2), null,
						area, new GameObject[] { Templates.MEAT_CHUNK.makeCopy(null, null) },
						new GameObject[] { Templates.FUR.makeCopy(null, null) }),
				Templates.BABY_RABBIT.makeCopy("Baby Bun", Game.level.squares[97][20], Game.level.factions.get(2), null,
						area, new GameObject[] { Templates.MEAT_CHUNK.makeCopy(null, null) },
						new GameObject[] { Templates.FUR.makeCopy(null, null) }),
				Templates.BABY_RABBIT.makeCopy("Baby Bun", Game.level.squares[98][20], Game.level.factions.get(2), null,
						area, new GameObject[] { Templates.MEAT_CHUNK.makeCopy(null, null) },
						new GameObject[] { Templates.FUR.makeCopy(null, null) }));

		Templates.FOX.makeCopy("Fox", Game.level.squares[200][78], Game.level.factions.get(2), null, area,
				new GameObject[] {}, new GameObject[] { Templates.FUR.makeCopy(null, null) });

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
		Templates.BURROW.makeCopy(Game.level.squares[131][70], null);
		Templates.BURROW.makeCopy(Game.level.squares[155][82], null);
		Templates.BURROW.makeCopy(Game.level.squares[170][75], null);
		Templates.BURROW.makeCopy(Game.level.squares[184][45], null);
		Templates.BURROW.makeCopy(Game.level.squares[175][33], null);
		Templates.BURROW.makeCopy(Game.level.squares[158][25], null);
		Templates.BURROW.makeCopy(Game.level.squares[143][31], null);

	}

}
