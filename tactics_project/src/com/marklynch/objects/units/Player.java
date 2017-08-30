package com.marklynch.objects.units;

import com.marklynch.ai.routines.AIRoutineForHunter;
import com.marklynch.ai.utils.AIPath;
import com.marklynch.level.constructs.Faction;
import com.marklynch.level.constructs.inventory.Inventory;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.utils.ResourceUtils;

import mdesl.graphics.Color;

public class Player extends Actor {

	public static AIPath playerPathToDraw = null;
	public static AIPath playerPathToMove = null;
	public static Square playerTargetSquare = null;
	public static Actor playerTargetActor = null;
	public static boolean playerFirstMove = false;

	public Player(String name, String title, int actorLevel, int health, int strength, int dexterity, int intelligence,
			int endurance, String imagePath, Square squareActorIsStandingOn, int travelDistance, int sight,
			GameObject bed, Inventory inventory, float widthRatio, float heightRatio, float drawOffsetX,
			float drawOffsetY, float soundWhenHit, float soundWhenHitting, float soundDampening, Color light,
			float lightHandleX, float lightHandlY, boolean stackable, float fireResistance, float waterResistance,
			float electricResistance, float poisonResistance, float slashResistance, float weight, Actor owner,
			Faction faction, float handAnchorX, float handAnchorY, float headAnchorX, float headAnchorY,
			float bodyAnchorX, float bodyAnchorY, float legsAnchorX, float legsAnchorY, int gold,
			GameObject[] mustHaves, GameObject[] mightHaves) {
		super(name, title, actorLevel, health, strength, dexterity, intelligence, endurance, imagePath,
				squareActorIsStandingOn, travelDistance, sight, bed, inventory, true, true, widthRatio, heightRatio,
				drawOffsetX, drawOffsetY, soundWhenHit, soundWhenHitting, soundDampening, light, lightHandleX,
				lightHandlY, stackable, fireResistance, waterResistance, electricResistance, poisonResistance,
				slashResistance, weight, owner, faction, handAnchorX, handAnchorY, headAnchorX, headAnchorY,
				bodyAnchorX, bodyAnchorY, legsAnchorX, legsAnchorY, gold, mustHaves, mightHaves);
		hairImageTexture = ResourceUtils.getGlobalImage("hair.png");
		stepLeftTexture = ResourceUtils.getGlobalImage("player_step_left.png");
		stepRightTexture = ResourceUtils.getGlobalImage("player_step_right.png");
	}

	@Override
	public void postLoad1() {
		super.postLoad1();
		aiRoutine = new AIRoutineForHunter(this);
	}

	@Override
	public void postLoad2() {
		super.postLoad2();
	}

	@Override
	public Player makeCopy(Square square, Faction faction, GameObject bed, int gold, GameObject[] mustHaves,
			GameObject[] mightHaves) {

		Player actor = new Player(name, title, actorLevel, (int) totalHealth, strength, dexterity, intelligence,
				endurance, imageTexturePath, square, travelDistance, sight, bed, new Inventory(), widthRatio,
				heightRatio, drawOffsetX, drawOffsetY, soundWhenHit, soundWhenHitting, soundDampening, light,
				lightHandleX, lightHandlY, stackable, fireResistance, waterResistance, electricResistance,
				poisonResistance, slashResistance, weight, owner, faction, handAnchorX, handAnchorY, headAnchorX,
				headAnchorY, bodyAnchorX, bodyAnchorY, legsAnchorX, legsAnchorY, gold, mustHaves, mightHaves);
		return actor;
	}

}
