package com.marklynch.objects.units;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.ai.routines.AIRoutineForRockGolem;
import com.marklynch.level.constructs.Faction;
import com.marklynch.level.constructs.structure.StructureRoom;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.Bed;
import com.marklynch.objects.Inventory;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actions.ActionAttack;
import com.marklynch.objects.actions.ActionPickUp;
import com.marklynch.ui.ActivityLog;
import com.marklynch.utils.ResourceUtils;
import com.marklynch.utils.TextureUtils;

import mdesl.graphics.Color;
import mdesl.graphics.Texture;

public class RockGolem extends Monster {

	public StructureRoom roomLivingIn;
	public boolean awake = false;
	public Texture sleepingTexture;
	public Texture awakeTexture;

	public RockGolem(String name, String title, int actorLevel, int health, int strength, int dexterity,
			int intelligence, int endurance, String imagePath, Square squareActorIsStandingOn, int travelDistance,
			int sight, Bed bed, Inventory inventory, boolean showInventory, boolean fitsInInventory,
			boolean canContainOtherObjects, boolean blocksLineOfSight, boolean persistsWhenCantBeSeen, float widthRatio,
			float heightRatio, float drawOffsetX, float drawOffsetY, float soundWhenHit, float soundWhenHitting,
			float soundDampening, Color light, float lightHandleX, float lightHandlY, boolean stackable,
			float fireResistance, float waterResistance, float electricResistance, float poisonResistance, float weight,
			Actor owner, Faction faction, float handAnchorX, float handAnchorY, float headAnchorX, float headAnchorY,
			float bodyAnchorX, float bodyAnchorY, float legsAnchorX, float legsAnchorY, StructureRoom roomLivingIn,
			boolean awake) {
		super(name, title, actorLevel, health, strength, dexterity, intelligence, endurance, imagePath,
				squareActorIsStandingOn, travelDistance, sight, bed, inventory, showInventory, fitsInInventory,
				canContainOtherObjects, blocksLineOfSight, persistsWhenCantBeSeen, false, false, widthRatio,
				heightRatio, drawOffsetX, drawOffsetY, soundWhenHit, soundWhenHitting, soundDampening, light,
				lightHandleX, lightHandlY, stackable, fireResistance, waterResistance, electricResistance,
				poisonResistance, weight, owner, faction, handAnchorX, handAnchorY, headAnchorX, headAnchorY,
				bodyAnchorX, bodyAnchorY, legsAnchorX, legsAnchorY);
		this.awake = awake;
		this.roomLivingIn = roomLivingIn;
		aiRoutine = new AIRoutineForRockGolem(this);
		sleepingTexture = ResourceUtils.getGlobalImage("rock_golem_sleeping.png");
		awakeTexture = ResourceUtils.getGlobalImage("rock_golem.png");

		anchorX = 16;
		anchorY = 64;

		if (!awake) {
			this.blocksLineOfSight = true;
			this.name = "Suspicious Boulder";
			imageTexture = sleepingTexture;
		}
	}

	@Override
	public void draw1() {

		// if (this.squareGameObjectIsOn.visibleToPlayer == false &&
		// persistsWhenCantBeSeen == false)
		// return;
		//
		// if (!this.squareGameObjectIsOn.seenByPlayer)
		// return;
		if (awake) {
			super.draw1();
			return;
		}

		if (this.remainingHealth <= 0)
			return;

		if (!Game.fullVisiblity) {
			if (this.squareGameObjectIsOn.visibleToPlayer == false && persistsWhenCantBeSeen == false)
				return;

			if (!this.squareGameObjectIsOn.seenByPlayer)
				return;
		}

		// Draw object
		if (squareGameObjectIsOn != null) {
			int actorPositionXInPixels = this.squareGameObjectIsOn.xInGrid * (int) Game.SQUARE_WIDTH;
			int actorPositionYInPixels = this.squareGameObjectIsOn.yInGrid * (int) Game.SQUARE_HEIGHT;

			float alpha = 1.0f;

			// TextureUtils.skipNormals = true;

			if (!this.squareGameObjectIsOn.visibleToPlayer)
				alpha = 0.5f;
			TextureUtils.drawTexture(sleepingTexture, alpha, actorPositionXInPixels, actorPositionXInPixels + 64,
					actorPositionYInPixels, actorPositionYInPixels + 64, backwards);
			// TextureUtils.skipNormals = false;
		}

	}

	@Override
	public void postLoad1() {
		super.postLoad1();
		aiRoutine = new AIRoutineForRockGolem(this);
	}

	@Override
	public void postLoad2() {
		super.postLoad2();
	}

	@Override
	public void attackedBy(Object attacker, Action action) {
		if (!awake)
			wakeUp();
		super.attackedBy(attacker, action);
	}

	@Override
	public Action getDefaultActionPerformedOnThisInWorld(Actor performer) {
		if (!awake) {
			return null;
		} else {
			ActionAttack actionAttack = new ActionAttack(performer, this);
			return actionAttack;
		}
	}

	@Override
	public ArrayList<Action> getAllActionsPerformedOnThisInWorld(Actor performer) {
		ArrayList<Action> actions = new ArrayList<Action>();
		// Pick up
		if (!awake)
			actions.add(new ActionPickUp(performer, this));

		actions.addAll(super.getAllActionsPerformedOnThisInWorld(performer));

		// Attack
		// actions.add(new ActionAttack(performer, this));
		// actions.add(new ActionThrow(performer, this, performer.equipped));
		return actions;
	}

	public RockGolem makeCopy(Square square, Faction faction, StructureRoom roomLivingIn, boolean awake) {

		RockGolem actor = new RockGolem(name, title, actorLevel, (int) totalHealth, strength, dexterity, intelligence,
				endurance, imageTexturePath, square, travelDistance, sight, bed, inventory.makeCopy(), showInventory,
				fitsInInventory, canContainOtherObjects, blocksLineOfSight, persistsWhenCantBeSeen, widthRatio,
				heightRatio, drawOffsetX, drawOffsetY, soundWhenHit, soundWhenHitting, soundDampening, light,
				lightHandleX, lightHandlY, stackable, fireResistance, waterResistance, electricResistance,
				poisonResistance, weight, owner, faction, handAnchorX, handAnchorY, headAnchorX, headAnchorY,
				bodyAnchorX, bodyAnchorY, legsAnchorX, legsAnchorY, roomLivingIn, awake);
		return actor;
	}

	@Override
	public void landed(Actor shooter, Action action) {
		if (!awake)
			wakeUp();
		this.remainingHealth -= 10;
		this.attackedBy(shooter, action);
	}

	public void wakeUp() {

		awake = true;
		this.blocksLineOfSight = false;
		this.name = "Rock Golem";
		imageTexture = awakeTexture;
		if (this.squareGameObjectIsOn.visibleToPlayer) {
			Game.level.logOnScreen(new ActivityLog(new Object[] { this, " woke up" }));
		}
	}

	public void sleep() {

		awake = false;
		this.blocksLineOfSight = true;
		this.name = "Suspicious Boulder";
		imageTexture = sleepingTexture;
		if (this.squareGameObjectIsOn.visibleToPlayer) {
			Game.level.logOnScreen(new ActivityLog(new Object[] { this, " went to sleep" }));
		}

	}

}
