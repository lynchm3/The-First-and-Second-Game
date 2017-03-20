package com.marklynch.level.quest.caveoftheblind;

import com.marklynch.Game;
import com.marklynch.ai.routines.AIRoutine;
import com.marklynch.ai.utils.AIRoutineUtils;
import com.marklynch.level.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.actions.ActionDrop;
import com.marklynch.objects.actions.ActionRing;
import com.marklynch.objects.weapons.Bell;

public class AIRoutineForMort extends AIRoutine {

	Mort mort;
	GameObject target;
	Square lastLocationSeenPlayer;
	boolean rangBell;

	enum FEEDING_DEMO_STATE {
		WALK_TO_TROUGH, PLACE_MEAT, RING_BELL, WALK_AWAY
	};

	public FEEDING_DEMO_STATE feedingDemoState = FEEDING_DEMO_STATE.WALK_TO_TROUGH;

	final String ACTIVITY_DESCRIPTION_LOOTING = "Looting!";
	final String ACTIVITY_DESCRIPTION_SKINNING = "Skinning";
	final String ACTIVITY_DESCRIPTION_HUNTING = "Goin' hunting";
	final String ACTIVITY_DESCRIPTION_SELLING_LOOT = "Selling spoils";
	final String ACTIVITY_DESCRIPTION_GOING_TO_BED = "Bed time";
	final String ACTIVITY_DESCRIPTION_SLEEPING = "Zzzzzz";
	final String ACTIVITY_DESCRIPTION_FIGHTING = "Fighting";
	final String ACTIVITY_DESCRIPTION_SEARCHING = "Searching";
	final String ACTIVITY_DESCRIPTION_FOLLOWING = "Following";
	final String ACTIVITY_DESCRIPTION_RINGING_DINNER_BELL = "Ringing Dinner Bell";

	int sleepCounter = 0;
	final int SLEEP_TIME = 1000;

	public AIRoutineForMort(Mort mort) {

		super(mort);
		this.mort = mort;
	}

	@Override
	public void update() {

		this.actor.miniDialogue = null;
		this.actor.activityDescription = null;
		this.actor.miniDialogue = null;
		this.actor.expressionImageTexture = null;
		createSearchLocationsBasedOnSounds();
		createSearchLocationsBasedOnVisibleAttackers();

		if (runFightRoutine())
			return;

		if (runSearchRoutine()) {
			return;
		}

		if (!rangBell && mort.remainingHealth < mort.totalHealth / 2) {
			Bell bell = (Bell) mort.inventory.getGameObectOfClass(Bell.class);
			if (bell != null && mort.getAttackers().contains(Game.level.player)) {
				new ActionRing(mort, bell).perform();
				this.actor.activityDescription = ACTIVITY_DESCRIPTION_RINGING_DINNER_BELL;
				this.actor.miniDialogue = "You won't get out of here alive";
				rangBell = true;
				return;
			}
		}

		if (mort.performingFeedingDemo) {

			if (feedingDemoState == FEEDING_DEMO_STATE.WALK_TO_TROUGH) {
				AIRoutineUtils.moveTowardsSquareToBeAdjacent(mort.questCaveOfTheBlind.troughSquare);

				if (mort.straightLineDistanceTo(mort.questCaveOfTheBlind.troughSquare) <= 1)
					feedingDemoState = FEEDING_DEMO_STATE.PLACE_MEAT;
				return;
			}

			if (feedingDemoState == FEEDING_DEMO_STATE.PLACE_MEAT) {

				if (!mort.inventory.contains(mort.mortsMeatChunk)) {
					mort.addAttackerForThisAndGroupMembers(Game.level.player);
					this.actor.activityDescription = "FEELING BETRAYED";
					this.actor.miniDialogue = "You! You robbed me!";
					mort.performingFeedingDemo = false;
					return;
				}

				new ActionDrop(mort, mort.questCaveOfTheBlind.troughSquare, mort.mortsMeatChunk).perform();

				feedingDemoState = FEEDING_DEMO_STATE.RING_BELL;

				return;
			}

			if (feedingDemoState == FEEDING_DEMO_STATE.RING_BELL) {

				if (!mort.inventory.contains(mort.mortsBell)) {
					mort.addAttackerForThisAndGroupMembers(Game.level.player);
					this.actor.activityDescription = "FEELING BETRAYED";
					this.actor.miniDialogue = "You! You robbed me!";
					mort.performingFeedingDemo = false;
					return;
				}

				new ActionRing(mort, mort.mortsBell).perform();
				this.actor.activityDescription = ACTIVITY_DESCRIPTION_RINGING_DINNER_BELL;
				this.actor.miniDialogue = "Dinner time!!!";

				feedingDemoState = FEEDING_DEMO_STATE.WALK_AWAY;
				return;
			}

			if (feedingDemoState == FEEDING_DEMO_STATE.WALK_AWAY) {

				// MOVE BACK
				AIRoutineUtils.moveTowardsSquareToBeAdjacent(mort.questCaveOfTheBlind.safeSquare);
				return;
			}
		}

		Square squareWherePlayerVisibleInTerritory = createSearchLocationForPlayerIfVisibleAndInTerritory();
		if (squareWherePlayerVisibleInTerritory == null) {

			if (lastLocationSeenPlayer != null) {
				AIRoutineUtils.moveTowardsTargetSquare(lastLocationSeenPlayer);

				// refuses to walk through door

				if (mort.squareGameObjectIsOn == lastLocationSeenPlayer) {
					lastLocationSeenPlayer = null;
				}
				return;
			}
		} else {
			lastLocationSeenPlayer = squareWherePlayerVisibleInTerritory;

		}

		// if (followingPlayer)
		// this.actor.activityDescription = ACTIVITY_DESCRIPTION_FOLLOWING;
		// Do some mining (He needs a pickaxe)
		// Maybe his pickaxe broke, but hhe cant go get it coz someone will try

		// If not leader defer to pack
		if (this.actor.group != null && this.actor != this.actor.group.getLeader())

		{
			if (this.actor.group.update(this.actor)) {
				return;
			}
		}

		// if group leader wait for group
		if (this.actor.group != null && this.actor == this.actor.group.getLeader()) {
			if (this.actor.group.leaderNeedsToWait()) {
				this.actor.activityDescription = "Waiting for " + this.actor.group.name;
				return;
			}
		}
	}

	public Square createSearchLocationForPlayerIfVisibleAndInTerritory() {

		if (Game.level.player.squareGameObjectIsOn.structureSectionSquareIsIn != mort.mortsMine
				&& Game.level.player.squareGameObjectIsOn.structureSectionSquareIsIn != mort.mortsRooms)
			return null;

		float distanceToPlayer = this.actor.straightLineDistanceTo(Game.level.player.squareGameObjectIsOn);

		if (distanceToPlayer > this.actor.sight)
			return null;

		if (!this.actor.visibleFrom(Game.level.player.squareGameObjectIsOn))
			return null;

		// this.actor.locationsToSearch.put(Game.level.player,
		// Game.level.player.squareGameObjectIsOn);
		return Game.level.player.squareGameObjectIsOn;

	}
}
