package com.marklynch.ai.routines;

import com.marklynch.Game;
import com.marklynch.actions.Action;
import com.marklynch.actions.ActionTakeItems;
import com.marklynch.actions.ActiontTakeAll;
import com.marklynch.ai.utils.AIRoutineUtils;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.inanimateobjects.Storage;
import com.marklynch.objects.templates.Templates;
import com.marklynch.utils.Color;

public class AIRoutineForThief extends AIRoutine {

	public int theftCooldown = 0;

	public AIRoutineForThief(Actor actor) {

		super(actor);
		aiType = AI_TYPE.FIGHTER;
	}

	@Override
	public void update() {
		aiRoutineStart();

		if (Game.level.hour > 20 || Game.level.hour < 6) {
			state = STATE.GO_TO_BED_AND_GO_TO_SLEEP;
		} else {
			state = STATE.THIEVING;
		}

		if (runSleepRoutine())
			return;

		// Fight
		if (runFightRoutine(true))
			return;

		// Crime reaction
		if (runCrimeReactionRoutine()) {
			this.actor.followersShouldFollow = true;
			return;
		}

		// Search
		if (runSearchRoutine())
			return;

		// Search cooldown
		if (runSearchCooldown())
			return;

		// Door maintenance routine
		if (runDoorRoutine())
			return;

		// Defer to group leader
		if (deferToGroupLeader())
			return;

		if (theftCooldown <= 0) {

			// 1. loot corpses, even if owned
			GameObject container = AIRoutineUtils.getNearestForPurposeOfBeingAdjacent(10f, false, true, false, true, 0,
					false, false, Actor.class, Storage.class);
			if (container != null) {
				if (container.owner != null && container.owner != actor)
					this.actor.activityDescription = ACTIVITY_DESCRIPTION_THIEVING;
				else
					this.actor.activityDescription = ACTIVITY_DESCRIPTION_LOOTING;
				this.actor.thoughtBubbleImageTextureObject = container.imageTexture;
				if (container.owner != null && container.owner != actor) {
					this.actor.thoughtBubbleImageTextureAction = Action.textureLeft;
					this.actor.thoughtBubbleImageTextureActionColor = Color.RED;
				}

				int weaponDistance = Game.level.activeActor.straightLineDistanceTo(container.squareGameObjectIsOn);
				if (weaponDistance > 1) {
					AIRoutineUtils.moveTowards(AIRoutineUtils.tempPath);
					return;
				} else {
					ActiontTakeAll actionTakeAll = new ActiontTakeAll(Game.level.activeActor, container);
					if (actionTakeAll.legal) {
						actionTakeAll.perform();
						return;
					} else {
						if (canSeeAnyone()) {
							theftCooldown = 100;
						} else {
							actionTakeAll.perform();
							return;
						}
					}
				}
			}

			// 1. pick up loot on ground, even if owned, all specific stuff, no
			// stupid generic game object

			GameObject loot = AIRoutineUtils.getNearestForPurposeOfBeingAdjacent(10f, true, false, false, true, 0,
					false, true, GameObject.class);
			if (loot != null) {
				if (loot.owner != null && loot.owner != actor)
					this.actor.activityDescription = ACTIVITY_DESCRIPTION_THIEVING;
				else
					this.actor.activityDescription = ACTIVITY_DESCRIPTION_LOOTING;
				this.actor.thoughtBubbleImageTextureObject = loot.imageTexture;
				if (loot.owner != null && loot.owner != actor) {
					this.actor.thoughtBubbleImageTextureAction = Action.textureLeft;
					this.actor.thoughtBubbleImageTextureActionColor = Color.RED;
				}

				int weaponDistance = Game.level.activeActor.straightLineDistanceTo(loot.squareGameObjectIsOn);

				if (weaponDistance > 1) {
					AIRoutineUtils.moveTowards(AIRoutineUtils.tempPath);
					return;
				} else {
					ActionTakeItems actionTake = new ActionTakeItems(Game.level.activeActor, loot.squareGameObjectIsOn,
							loot);
					if (actionTake.legal) {
						actionTake.perform();
						return;
					} else {
						if (canSeeAnyone()) {
							theftCooldown = 100;
						} else {
							actionTake.perform();
							return;
						}
					}
				}
			}
		} else {
			theftCooldown--;
		}

		// Defer to quest
		if (deferToQuest())
			return;

		// Pick up from lost and found
		if (pickUpFromLostAndFoundRoutine())
			return;

		// Sell items
		if (sellItems(10))
			return;

		if (state == STATE.THIEVING) {
			// Go about ur business... (move around randomly...)
			if (targetSquare == null || this.actor.getPathTo(targetSquare) == null) {
				targetSquare = AIRoutineUtils.getRandomSquare(7, 10, true, true);
			}

			if (targetSquare != null) {

				this.actor.thoughtBubbleImageTextureObject = Action.textureMusic;
				this.actor.thoughtBubbleImageTextureAction = null;
				this.actor.thoughtBubbleImageTextureActionColor = Color.WHITE;
				if (AIRoutineUtils.moveTowards(targetSquare))
					return;
			}
		}

		if (state == STATE.GO_TO_BED_AND_GO_TO_SLEEP) {
			this.actor.thoughtBubbleImageTextureObject = Templates.BED.imageTexture;
			this.actor.thoughtBubbleImageTextureAction = Action.textureSleep;
			this.actor.thoughtBubbleImageTextureActionColor = Color.WHITE;
			goToBedAndSleep();
		}
	}

	@Override
	public AIRoutine getInstance(Actor actor) {
		return new AIRoutineForThief(actor);
	}

}
