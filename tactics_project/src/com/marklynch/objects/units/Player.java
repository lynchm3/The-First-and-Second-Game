package com.marklynch.objects.units;

import com.marklynch.Game;
import com.marklynch.ai.routines.AIRoutineForHunter;
import com.marklynch.ai.utils.AIPath;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.Faction;
import com.marklynch.level.constructs.beastiary.BestiaryKnowledge;
import com.marklynch.level.constructs.bounds.Area;
import com.marklynch.level.constructs.inventory.Inventory;
import com.marklynch.level.constructs.power.PowerBleed;
import com.marklynch.level.constructs.power.PowerInferno;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.actions.Action;
import com.marklynch.ui.ActivityLog;
import com.marklynch.ui.popups.Notification;
import com.marklynch.utils.ResourceUtils;

import mdesl.graphics.Color;

public class Player extends Actor {

	public static AIPath playerPathToDraw = null;
	public static AIPath playerPathToMove = null;
	public static Square playerTargetSquare = null;
	public static Actor playerTargetActor = null;
	public static boolean playerFirstMove = false;
	public static Action playerTargetAction = null;
	public static int xp;
	public static int xpPerLevel = 55;

	public Player(String name, String title, int actorLevel, int health, int strength, int dexterity, int intelligence,
			int endurance, String imagePath, Square squareActorIsStandingOn, int travelDistance, int sight,
			GameObject bed, Inventory inventory, float widthRatio, float heightRatio, float drawOffsetX,
			float drawOffsetY, float soundWhenHit, float soundWhenHitting, float soundDampening, Color light,
			float lightHandleX, float lightHandlY, boolean stackable, float fireResistance, float waterResistance,
			float electricResistance, float poisonResistance, float slashResistance, float weight, Actor owner,
			Faction faction, float handAnchorX, float handAnchorY, float headAnchorX, float headAnchorY,
			float bodyAnchorX, float bodyAnchorY, float legsAnchorX, float legsAnchorY, int gold,
			GameObject[] mustHaves, GameObject[] mightHaves, int templateId) {
		super(name, title, actorLevel, health, strength, dexterity, intelligence, endurance, imagePath,
				squareActorIsStandingOn, travelDistance, sight, bed, inventory, true, true, widthRatio, heightRatio,
				drawOffsetX, drawOffsetY, soundWhenHit, soundWhenHitting, soundDampening, light, lightHandleX,
				lightHandlY, stackable, fireResistance, waterResistance, electricResistance, poisonResistance,
				slashResistance, weight, owner, faction, handAnchorX, handAnchorY, headAnchorX, headAnchorY,
				bodyAnchorX, bodyAnchorY, legsAnchorX, legsAnchorY, gold, mustHaves, mightHaves, templateId);
		hairImageTexture = ResourceUtils.getGlobalImage("hair.png");
		stepLeftTexture = ResourceUtils.getGlobalImage("player_step_left.png");
		stepRightTexture = ResourceUtils.getGlobalImage("player_step_right.png");

		powers.add(new PowerBleed(this));
		powers.add(new PowerInferno(this));
		thoughtsOnPlayer = 100;
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
			GameObject[] mightHaves, Area area) {

		Player actor = new Player(name, title, actorLevel, (int) totalHealth, strength, dexterity, intelligence,
				endurance, imageTexturePath, square, travelDistance, sight, bed, new Inventory(), widthRatio,
				heightRatio, drawOffsetX, drawOffsetY, soundWhenHit, soundWhenHitting, soundDampening, light,
				lightHandleX, lightHandlY, stackable, fireResistance, waterResistance, electricResistance,
				poisonResistance, slashResistance, weight, owner, faction, handAnchorX, handAnchorY, headAnchorX,
				headAnchorY, bodyAnchorX, bodyAnchorY, legsAnchorX, legsAnchorY, gold, mustHaves, mightHaves,
				templateId);

		BestiaryKnowledge bestiaryKnowledge = Level.bestiaryKnowledgeCollection.get(templateId);

		bestiaryKnowledge.name = true;
		bestiaryKnowledge.level = true;
		bestiaryKnowledge.image = true;
		bestiaryKnowledge.totalHealth = true;
		bestiaryKnowledge.faction = true;
		bestiaryKnowledge.group = true;

		// Stats
		bestiaryKnowledge.strength = true;
		bestiaryKnowledge.dexterity = true;
		bestiaryKnowledge.intelligence = true;
		bestiaryKnowledge.endurance = true;

		// Damage
		bestiaryKnowledge.slashDamage = true;
		bestiaryKnowledge.bluntDamage = true;
		bestiaryKnowledge.pierceDamage = true;
		bestiaryKnowledge.fireDamage = true;
		bestiaryKnowledge.waterDamage = true;
		bestiaryKnowledge.electricDamage = true;
		bestiaryKnowledge.poisonDamage = true;
		bestiaryKnowledge.range = true;

		// Resistances
		bestiaryKnowledge.slashResistance = true;
		bestiaryKnowledge.bluntResistance = true;
		bestiaryKnowledge.pierceResistance = true;
		bestiaryKnowledge.fireResistance = true;
		bestiaryKnowledge.waterResistance = true;
		bestiaryKnowledge.electricResistance = true;
		bestiaryKnowledge.poisonResistance = true;

		// Powers
		bestiaryKnowledge.powers = true;

		return actor;
	}

	public void addXP(int xp) {
		Player.xp += xp;
		Game.level.activityLogger.addActivityLog(new ActivityLog(new Object[] { Game.level.player,
				" got " + xp + "XP (" + Player.xp + "/" + xpPerLevel * actorLevel + ")" }));
		if (Player.xp >= xpPerLevel * actorLevel) {
			actorLevel++;
			Game.level.notifications.add(new Notification(new Object[] { Game.level.player, " leveled Up!" }));
			Game.level.activityLogger.addActivityLog(
					new ActivityLog(new Object[] { Game.level.player, " are now Level " + actorLevel }));
		}
	}

}
