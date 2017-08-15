package com.marklynch.objects.units;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.marklynch.Game;
import com.marklynch.ai.routines.AIRoutineForTrader;
import com.marklynch.level.constructs.Faction;
import com.marklynch.level.constructs.bounds.structure.Structure;
import com.marklynch.level.constructs.bounds.structure.StructureRoom;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Inventory;
import com.marklynch.objects.Readable;
import com.marklynch.objects.weapons.Weapon;

import mdesl.graphics.Color;

public class Trader extends Actor implements Comparator<GameObject> {

	public StructureRoom room;
	public Structure shop;
	public Readable shopSign;
	public Weapon broom;

	public Trader(String name, String title, int actorLevel, int health, int strength, int dexterity, int intelligence,
			int endurance, String imagePath, Square squareActorIsStandingOn, int travelDistance, int sight,
			GameObject bed, Inventory inventory,  
			   float widthRatio,
			float heightRatio, float drawOffsetX, float drawOffsetY, float soundWhenHit, float soundWhenHitting,
			float soundDampening, Color light, float lightHandleX, float lightHandlY, boolean stackable,
			float fireResistance, float waterResistance, float electricResistance, float poisonResistance, float slashResistance, float weight,
			Actor owner, Faction faction, float handAnchorX, float handAnchorY, float headAnchorX, float headAnchorY,
			float bodyAnchorX, float bodyAnchorY, float legsAnchorX, float legsAnchorY) {
		super(name, title, actorLevel, health, strength, dexterity, intelligence, endurance, imagePath,
				squareActorIsStandingOn, travelDistance, sight, bed, inventory,  
				   true, true, widthRatio, heightRatio,
				drawOffsetX, drawOffsetY, soundWhenHit, soundWhenHitting, soundDampening, light, lightHandleX,
				lightHandlY, stackable, fireResistance, waterResistance, electricResistance, poisonResistance, slashResistance, weight,
				owner, faction, handAnchorX, handAnchorY, headAnchorX, headAnchorY, bodyAnchorX, bodyAnchorY,
				legsAnchorX, legsAnchorY);

		aiRoutine = new AIRoutineForTrader(this);
		equip(broom);
	}

	public boolean isPlayerInTheShop() {
		return Game.level.player.squareGameObjectIsOn.structureSquareIsIn == shop;
	}

	public Object[] getTextForSign() {

		ArrayList<GameObject> temp = (ArrayList<GameObject>) this.inventory.getGameObjects().clone();
		temp.remove(equipped);
		temp.remove(broom);
		Collections.sort(temp, this);

		if (temp.size() == 0) {
			if (shopSign.getConversation().openingConversationPart.text.length != 1) {
				return new Object[] { this.shop };
			} else {
				return null;
			}

		} else if (temp.size() == 1) {
			if (shopSign.getConversation().openingConversationPart.text.length != 3) {
				return new Object[] { this.shop, " - FEATURED INVENTORY - ", temp.get(0) };
			} else if (shopSign.getConversation().openingConversationPart.text[2] != temp.get(0)) {
				return new Object[] { this.shop, " - FEATURED INVENTORY - ", temp.get(0) };
			} else {
				return null;
			}

		} else {
			if (shopSign.getConversation().openingConversationPart.text.length != 5) {
				return new Object[] { this.shop, " - FEATURED INVENTORY - ", temp.get(0), " - ", temp.get(1) };
			} else if (shopSign.getConversation().openingConversationPart.text[2] != temp.get(0)
					|| shopSign.getConversation().openingConversationPart.text[4] != temp.get(1)) {
				return new Object[] { this.shop, " - FEATURED INVENTORY - ", temp.get(0), " - ", temp.get(1) };
			} else {
				return null;
			}

		}
	}

	@Override
	public void postLoad1() {
		super.postLoad1();
		aiRoutine = new AIRoutineForTrader(this);
	}

	@Override
	public void postLoad2() {
		super.postLoad2();
	}

	@Override
	public Trader makeCopy(Square square, Faction faction, GameObject bed) {

		Trader actor = new Trader(name, title, actorLevel, (int) totalHealth, strength, dexterity, intelligence,
				endurance, imageTexturePath, square, travelDistance, sight, bed, new Inventory(), 
				    widthRatio,
				heightRatio, drawOffsetX, drawOffsetY, soundWhenHit, soundWhenHitting, soundDampening, light,
				lightHandleX, lightHandlY, stackable, fireResistance, waterResistance, electricResistance,
				poisonResistance, slashResistance, weight, owner, faction, handAnchorX, handAnchorY, headAnchorX, headAnchorY,
				bodyAnchorX, bodyAnchorY, legsAnchorX, legsAnchorY);
		return actor;
	}

	@Override
	public int compare(GameObject gameObject1, GameObject gameObject2) {
		return (int) (gameObject2.value - gameObject1.value);
	}

}
