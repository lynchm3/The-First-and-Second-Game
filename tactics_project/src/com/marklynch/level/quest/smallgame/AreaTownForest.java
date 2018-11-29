package com.marklynch.level.quest.smallgame;

import com.marklynch.level.Level;
import com.marklynch.level.constructs.GroupOfActors;
import com.marklynch.level.constructs.actionlisteners.ActionListener;
import com.marklynch.level.constructs.journal.AreaList;
import com.marklynch.level.constructs.power.PowerPoisonThrowingKnives;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Tree;
import com.marklynch.objects.templates.Templates;

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

			// rangers lodge
			if (randomX >= 129 && randomX <= 133 && randomY >= 33 && randomY <= 37)
				continue;

			// thieves hut
			if (randomX >= 113 && randomX <= 117 && randomY >= 52 && randomY <= 56)
				continue;

			if (Math.random() * maxDiffFromCenterX < Math.abs(randomX - centerX))
				continue;
			if (Math.random() * maxDiffFromCenterY < Math.abs(randomY - centerY))
				continue;

			if (Level.squares[randomX][randomY].inventory.getGameObjectOfClass(Tree.class) == null) {
				if ((Math.abs(randomX - centerX) + Math.random() * 10) < 25
						&& (Math.abs(randomY - centerY) + Math.random() * 10) < 25) {
					Templates.BIG_TREE.makeCopy(Level.squares[randomX][randomY], null);
				} else {
					Templates.TREE.makeCopy(Level.squares[randomX][randomY], null);
				}
			}

		}

		// Cluster of trees in center
		for (int i = 154; i < 166; i++) {
			if (Level.squares[i][47].inventory.getGameObjectOfClass(Tree.class) == null) {
				Templates.BIG_TREE.makeCopy(Level.squares[i][47], null);
			}

			if (Level.squares[i][48].inventory.getGameObjectOfClass(Tree.class) == null) {
				Templates.BIG_TREE.makeCopy(Level.squares[i][48], null);
			}

			if (Level.squares[i][63].inventory.getGameObjectOfClass(Tree.class) == null) {
				Templates.BIG_TREE.makeCopy(Level.squares[i][63], null);
			}

			if (Level.squares[i][64].inventory.getGameObjectOfClass(Tree.class) == null) {
				Templates.BIG_TREE.makeCopy(Level.squares[i][64], null);
			}

		}

		for (int j = 49; j < 62; j++) {
			if (Level.squares[152][j].inventory.getGameObjectOfClass(Tree.class) == null) {
				Templates.BIG_TREE.makeCopy(Level.squares[152][j], null);
			}

			if (Level.squares[153][j].inventory.getGameObjectOfClass(Tree.class) == null) {
				Templates.BIG_TREE.makeCopy(Level.squares[153][j], null);
			}

			if (Level.squares[167][j].inventory.getGameObjectOfClass(Tree.class) == null) {
				Templates.BIG_TREE.makeCopy(Level.squares[167][j], null);
			}

			if (Level.squares[168][j].inventory.getGameObjectOfClass(Tree.class) == null) {
				Templates.BIG_TREE.makeCopy(Level.squares[168][j], null);
			}

		}

		GameObject scrollOfPoisonBlast = Templates.SCROLL.makeCopy(Level.squares[160][55], null);
		scrollOfPoisonBlast.name = "Scroll of Poison Blast";
		scrollOfPoisonBlast.conversation = scrollOfPoisonBlast.createConversation(new Object[] {
				"[You learn Poison Blast]\nPoisons for base 3 dmg/s Range:10\nUse the power of the forest to murder people with poisons. Nice" });
		scrollOfPoisonBlast.setOnReadListener(new ActionListener() {
			@Override
			public void onRead() {
				Level.player.powers.add(new PowerPoisonThrowingKnives(Level.player));
			}
		});

		// corners
		Templates.BIG_TREE.makeCopy(Level.squares[154][49], null);
		Templates.BIG_TREE.makeCopy(Level.squares[154][62], null);
		Templates.BIG_TREE.makeCopy(Level.squares[166][49], null);
		Templates.BIG_TREE.makeCopy(Level.squares[166][62], null);

		// smoother corners
		Templates.BIG_TREE.makeCopy(Level.squares[154][61], null);
		Templates.BIG_TREE.makeCopy(Level.squares[155][62], null);
		Templates.BIG_TREE.makeCopy(Level.squares[165][62], null);
		Templates.BIG_TREE.makeCopy(Level.squares[166][61], null);
		Templates.BIG_TREE.makeCopy(Level.squares[166][50], null);
		Templates.BIG_TREE.makeCopy(Level.squares[165][49], null);
		Templates.BIG_TREE.makeCopy(Level.squares[155][49], null);
		Templates.BIG_TREE.makeCopy(Level.squares[154][50], null);

		// smootherer
		// cornersTemplates.BIG_TREE.makeCopy(Level.square[157][49],null);
		Templates.BIG_TREE.makeCopy(Level.squares[157][49], null);
		Templates.BIG_TREE.makeCopy(Level.squares[156][49], null);
		Templates.BIG_TREE.makeCopy(Level.squares[155][50], null);
		Templates.BIG_TREE.makeCopy(Level.squares[154][51], null);
		Templates.BIG_TREE.makeCopy(Level.squares[154][52], null);
		Templates.BIG_TREE.makeCopy(Level.squares[163][49], null);
		Templates.BIG_TREE.makeCopy(Level.squares[164][49], null);
		Templates.BIG_TREE.makeCopy(Level.squares[165][50], null);
		Templates.BIG_TREE.makeCopy(Level.squares[166][51], null);
		Templates.BIG_TREE.makeCopy(Level.squares[166][60], null);
		Templates.BIG_TREE.makeCopy(Level.squares[165][61], null);
		Templates.BIG_TREE.makeCopy(Level.squares[164][62], null);
		Templates.BIG_TREE.makeCopy(Level.squares[156][62], null);
		Templates.BIG_TREE.makeCopy(Level.squares[155][61], null);
		Templates.BIG_TREE.makeCopy(Level.squares[154][60], null);
		Templates.BIG_TREE.makeCopy(Level.squares[166][52], null);
		Templates.BIG_TREE.makeCopy(Level.squares[166][59], null);
		Templates.BIG_TREE.makeCopy(Level.squares[163][62], null);
		Templates.BIG_TREE.makeCopy(Level.squares[157][62], null);
		Templates.BIG_TREE.makeCopy(Level.squares[154][59], null);

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

				Level.squares[i][j].floorImageTexture = Square.GRASS_TEXTURE;
			}
		}

		// How to give them an area?
		// Lets have a look @ Mort.

		// [188][61]

		new GroupOfActors("Buns",
				Templates.RABBIT.makeCopy("Female Bun", Level.squares[183][61], Level.factions.buns, null,
						new GameObject[] { Templates.MEAT_CHUNK.makeCopy(null, null) },
						new GameObject[] { Templates.FUR.makeCopy(null, null) }, AreaList.townForest),
				Templates.RABBIT.makeCopy("Male Bun", Level.squares[182][62], Level.factions.buns, null,
						new GameObject[] { Templates.MEAT_CHUNK.makeCopy(null, null) },
						new GameObject[] { Templates.FUR.makeCopy(null, null) }, AreaList.townForest),
				Templates.BABY_RABBIT.makeCopy("Baby Bun", Level.squares[181][63], Level.factions.buns, null,
						new GameObject[] { Templates.MEAT_CHUNK.makeCopy(null, null) },
						new GameObject[] { Templates.FUR.makeCopy(null, null) }, null),
				Templates.BABY_RABBIT.makeCopy("Baby Bun", Level.squares[183][62], Level.factions.buns, null,
						new GameObject[] { Templates.MEAT_CHUNK.makeCopy(null, null) },
						new GameObject[] { Templates.FUR.makeCopy(null, null) }, AreaList.townForest),
				Templates.BABY_RABBIT.makeCopy("Baby Bun", Level.squares[182][63], Level.factions.buns, null,
						new GameObject[] { Templates.MEAT_CHUNK.makeCopy(null, null) },
						new GameObject[] { Templates.FUR.makeCopy(null, null) }, AreaList.townForest),
				Templates.BABY_RABBIT.makeCopy("Baby Bun", Level.squares[181][61], Level.factions.buns, null,
						new GameObject[] { Templates.MEAT_CHUNK.makeCopy(null, null) },
						new GameObject[] { Templates.FUR.makeCopy(null, null) }, AreaList.townForest));

		new GroupOfActors("Buns",
				Templates.RABBIT.makeCopy("Female Bun", Level.squares[93][20], Level.factions.buns, null,
						new GameObject[] { Templates.MEAT_CHUNK.makeCopy(null, null) },
						new GameObject[] { Templates.FUR.makeCopy(null, null) }, AreaList.townForest),
				Templates.RABBIT.makeCopy("Male Bun", Level.squares[94][21], Level.factions.buns, null,
						new GameObject[] { Templates.MEAT_CHUNK.makeCopy(null, null) },
						new GameObject[] { Templates.FUR.makeCopy(null, null) }, AreaList.townForest),
				Templates.BABY_RABBIT.makeCopy("Baby Bun", Level.squares[95][19], Level.factions.buns, null,
						new GameObject[] { Templates.MEAT_CHUNK.makeCopy(null, null) },
						new GameObject[] { Templates.FUR.makeCopy(null, null) }, null),
				Templates.BABY_RABBIT.makeCopy("Baby Bun", Level.squares[96][20], Level.factions.buns, null,
						new GameObject[] { Templates.MEAT_CHUNK.makeCopy(null, null) },
						new GameObject[] { Templates.FUR.makeCopy(null, null) }, null),
				Templates.BABY_RABBIT.makeCopy("Baby Bun", Level.squares[97][20], Level.factions.buns, null,
						new GameObject[] { Templates.MEAT_CHUNK.makeCopy(null, null) },
						new GameObject[] { Templates.FUR.makeCopy(null, null) }, null),
				Templates.BABY_RABBIT.makeCopy("Baby Bun", Level.squares[98][20], Level.factions.buns, null,
						new GameObject[] { Templates.MEAT_CHUNK.makeCopy(null, null) },
						new GameObject[] { Templates.FUR.makeCopy(null, null) }, null));

		Templates.FOX.makeCopy("Fox", Level.squares[200][78], Level.factions.foxes, null,
				new GameObject[] { Templates.MEAT_CHUNK.makeCopy(null, null) },
				new GameObject[] { Templates.FUR.makeCopy(null, null) }, AreaList.townForest);

		// Mushrooms
		Templates.MUSHROOM.makeCopy(Level.squares[115][84], null);
		Templates.MUSHROOM.makeCopy(Level.squares[125][66], null);
		Templates.MUSHROOM.makeCopy(Level.squares[133][62], null);
		Templates.MUSHROOM.makeCopy(Level.squares[136][52], null);
		Templates.MUSHROOM.makeCopy(Level.squares[142][23], null);
		Templates.MUSHROOM.makeCopy(Level.squares[143][47], null);
		Templates.MUSHROOM.makeCopy(Level.squares[144][49], null);
		Templates.MUSHROOM.makeCopy(Level.squares[148][70], null);
		Templates.MUSHROOM.makeCopy(Level.squares[151][48], null);
		Templates.MUSHROOM.makeCopy(Level.squares[146][80], null);
		Templates.MUSHROOM.makeCopy(Level.squares[154][62], null);
		Templates.MUSHROOM.makeCopy(Level.squares[156][53], null);
		Templates.MUSHROOM.makeCopy(Level.squares[158][47], null);
		Templates.MUSHROOM.makeCopy(Level.squares[158][47], null);
		Templates.MUSHROOM.makeCopy(Level.squares[160][58], null);
		Templates.MUSHROOM.makeCopy(Level.squares[160][63], null);
		Templates.MUSHROOM.makeCopy(Level.squares[160][66], null);
		Templates.MUSHROOM.makeCopy(Level.squares[161][29], null);
		Templates.MUSHROOM.makeCopy(Level.squares[161][44], null);
		Templates.MUSHROOM.makeCopy(Level.squares[161][56], null);
		Templates.MUSHROOM.makeCopy(Level.squares[161][63], null);
		Templates.MUSHROOM.makeCopy(Level.squares[162][54], null);
		Templates.MUSHROOM.makeCopy(Level.squares[162][56], null);
		Templates.MUSHROOM.makeCopy(Level.squares[162][61], null);
		Templates.MUSHROOM.makeCopy(Level.squares[163][47], null);
		Templates.MUSHROOM.makeCopy(Level.squares[163][54], null);
		Templates.MUSHROOM.makeCopy(Level.squares[163][56], null);
		Templates.MUSHROOM.makeCopy(Level.squares[164][47], null);
		Templates.MUSHROOM.makeCopy(Level.squares[166][79], null);
		Templates.MUSHROOM.makeCopy(Level.squares[174][46], null);
		Templates.MUSHROOM.makeCopy(Level.squares[177][36], null);
		Templates.MUSHROOM.makeCopy(Level.squares[177][38], null);
		Templates.MUSHROOM.makeCopy(Level.squares[182][71], null);
		Templates.MUSHROOM.makeCopy(Level.squares[186][40], null);
		Templates.MUSHROOM.makeCopy(Level.squares[186][42], null);
		Templates.MUSHROOM.makeCopy(Level.squares[198][41], null);
		Templates.MUSHROOM.makeCopy(Level.squares[189][79], null);
		Templates.MUSHROOM.makeCopy(Level.squares[204][57], null);

		// Burrows
		Templates.BURROW.makeCopy(Level.squares[119][42], null);
		Templates.BURROW.makeCopy(Level.squares[188][61], null);
		Templates.BURROW.makeCopy(Level.squares[153][58], null);
		Templates.BURROW.makeCopy(Level.squares[160][67], null);
		Templates.BURROW.makeCopy(Level.squares[166][64], null);
		Templates.BURROW.makeCopy(Level.squares[171][61], null);
		Templates.BURROW.makeCopy(Level.squares[196][27], null);
		Templates.BURROW.makeCopy(Level.squares[131][70], null);
		Templates.BURROW.makeCopy(Level.squares[155][82], null);
		Templates.BURROW.makeCopy(Level.squares[170][75], null);
		Templates.BURROW.makeCopy(Level.squares[184][45], null);
		Templates.BURROW.makeCopy(Level.squares[175][33], null);
		Templates.BURROW.makeCopy(Level.squares[158][25], null);
		Templates.BURROW.makeCopy(Level.squares[143][31], null);

	}

}
