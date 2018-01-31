package com.marklynch.objects.units;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.ai.routines.AIRoutineForRockGolem;
import com.marklynch.level.constructs.Faction;
import com.marklynch.level.constructs.bounds.structure.StructureRoom;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actions.ActionAttack;
import com.marklynch.objects.actions.ActionLift;
import com.marklynch.ui.ActivityLog;
import com.marklynch.utils.ResourceUtils;
import com.marklynch.utils.TextureUtils;

import com.marklynch.utils.Texture;

public class RockGolem extends Monster {

	public StructureRoom roomLivingIn;
	public boolean awake = false;
	public Texture sleepingTexture;
	public Texture awakeTexture;

	public RockGolem() {
		super();
		this.roomLivingIn = roomLivingIn;
		aiRoutine = new AIRoutineForRockGolem(this);
		sleepingTexture = ResourceUtils.getGlobalImage("rock_golem_sleeping.png");
		awakeTexture = ResourceUtils.getGlobalImage("rock_golem.png");

		anchorX = 16;
		anchorY = 64;

		this.blocksLineOfSight = true;
		this.name = "Suspicious Boulder";
		imageTexture = sleepingTexture;
		canBePickedUp = true;

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
			float actorPositionXInPixels = this.squareGameObjectIsOn.xInGridPixels;
			float actorPositionYInPixels = this.squareGameObjectIsOn.yInGridPixels;

			float alpha = 1.0f;

			// TextureUtils.skipNormals = true;

			if (!this.squareGameObjectIsOn.visibleToPlayer)
				alpha = 0.5f;
			TextureUtils.drawTexture(sleepingTexture, alpha, actorPositionXInPixels, actorPositionYInPixels,
					actorPositionXInPixels + 64, actorPositionYInPixels + 64, backwards);
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
	public Action getSecondaryActionPerformedOnThisInWorld(Actor performer) {
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
			actions.add(new ActionLift(performer, this));

		actions.addAll(super.getAllActionsPerformedOnThisInWorld(performer));

		// Attack
		// actions.add(new ActionAttack(performer, this));
		// actions.add(new ActionThrow(performer, this, performer.equipped));
		return actions;
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
		canBePickedUp = false;
		this.name = "Rock Golem";
		imageTexture = awakeTexture;
		if (Game.level.shouldLog(this))
			Game.level.logOnScreen(new ActivityLog(new Object[] { this, " woke up" }));
	}

	public void sleep() {

		awake = false;
		this.blocksLineOfSight = true;
		canBePickedUp = true;
		this.name = "Suspicious Boulder";
		imageTexture = sleepingTexture;
		if (Game.level.shouldLog(this))
			Game.level.logOnScreen(new ActivityLog(new Object[] { this, " went to sleep" }));

	}

	public RockGolem makeCopy(String name, Square square, Faction faction, StructureRoom roomLivingIn,
			GameObject[] mustHaves, GameObject[] mightHaves) {

		RockGolem actor = new RockGolem();
		actor.squareGameObjectIsOn = square;
		actor.faction = faction;
		this.roomLivingIn = roomLivingIn;

		actor.title = title;
		actor.name = name;
		actor.area = area;
		actor.level = level;
		actor.totalHealth = actor.remainingHealth = totalHealth;
		actor.strength = strength;
		actor.dexterity = dexterity;
		actor.intelligence = intelligence;
		actor.endurance = endurance;
		actor.imageTexturePath = imageTexturePath;
		// actor.squareGameObjectIsOn = null;
		actor.travelDistance = travelDistance;
		actor.sight = sight;
		// actor.bed = null;
		// actor.inventory = new Inventory();
		actor.widthRatio = widthRatio;
		actor.heightRatio = heightRatio;
		actor.drawOffsetRatioX = drawOffsetRatioX;
		actor.drawOffsetRatioY = drawOffsetRatioY;
		actor.soundWhenHit = soundWhenHit;
		actor.soundWhenHitting = soundWhenHitting;
		// actor.soundDampening = 1f;
		// actor.stackable = false;
		actor.weight = weight;
		actor.handAnchorX = handAnchorX;
		actor.handAnchorY = handAnchorY;
		actor.headAnchorX = headAnchorX;
		actor.headAnchorY = headAnchorY;
		actor.bodyAnchorX = bodyAnchorX;
		actor.bodyAnchorY = bodyAnchorY;
		actor.legsAnchorX = legsAnchorX;
		actor.legsAnchorY = legsAnchorY;
		actor.canOpenDoors = canOpenDoors;
		actor.canEquipWeapons = canEquipWeapons;
		// gold
		actor.templateId = templateId;

		actor.aiRoutine = aiRoutine.getInstance(actor);

		actor.init(0, mustHaves, mightHaves);
		return actor;
	}

}
