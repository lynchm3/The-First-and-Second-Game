package com.marklynch.ai.routines;

import com.marklynch.Game;
import com.marklynch.ai.utils.AIRoutineUtils;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.area.Area;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.actors.Follower;
import com.marklynch.objects.templates.Templates;

public class AIRoutineForFollower extends AIRoutine {

	public Follower follower;
	public Area area;

	public AIRoutineForFollower() {
		super();
		aiType = AI_TYPE.RUNNER;
	}

	public AIRoutineForFollower(Actor actor) {
		super(actor);
		this.follower = (Follower) actor;
		this.area = actor.area;
		aiType = AI_TYPE.RUNNER;
	}

	@Override
	public void update() {

		aiRoutineStart();

		if (Game.level.hour > 20 || Game.level.hour < 6) {
			state = STATE.GO_TO_BED_AND_GO_TO_SLEEP;
		} else {
			state = STATE.FOLLOWING;
		}

		if (runSleepRoutine())
			return;

		// Shout for help
		if (runGetHelpRoutine())
			return;

		// Shout for help cooldown
		if (runEscapeCooldown(true))
			return;

		// Crime reaction
		if (runCrimeReactionRoutine())
			return;

		// Search
		if (runSearchRoutine()) {
			this.actor.followersShouldFollow = true;
			return;
		}

		// Search cooldown
		if (runSearchCooldown()) {
			this.actor.followersShouldFollow = true;
			return;
		}

		// Loot from ground
		if (lootFromGround())
			return;

		// Defer to group leader
		if (deferToGroupLeader())
			return;

		// Defer to quest
		if (deferToQuest())
			return;

		// Update wanted poster
		if (reportToGuardRoutine())
			return;

		// Pick up from lost and found
		if (pickUpFromLostAndFoundRoutine())
			return;

		// Door maintenance routine
		if (runDoorRoutine())
			return;

		// Shopkeeper AI 1 - hang in shop
		if (state == STATE.FOLLOWING)

		{
			if (Level.player.area == this.area) {
				this.actor.thoughtBubbleImageTextureObject = Level.player.imageTexture;
				this.actor.activityDescription = ACTIVITY_DESCRIPTION_FOLLOWING;
				AIRoutineUtils.moveTowardsTargetSquare(Level.player.squareGameObjectIsOn);
				return;
			}
		}

		if (state == STATE.GO_TO_BED_AND_GO_TO_SLEEP) {
			actor.thoughtBubbleImageTextureObject = Templates.BED.imageTexture;
			goToBedAndSleep();
		}
	}

	@Override
	public AIRoutine getInstance(Actor actor) {
		return new AIRoutineForFollower(actor);
	}
}
