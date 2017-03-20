package com.marklynch.level.quest.caveoftheblind;

import com.marklynch.Game;
import com.marklynch.ai.routines.AIRoutine;
import com.marklynch.ai.utils.AIRoutineUtils;
import com.marklynch.level.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.actions.ActionRing;
import com.marklynch.objects.weapons.Bell;

public class AIRoutineForMort extends AIRoutine {

	Mort mort;
	GameObject target;
	Square lastLocationSeenPlayer;
	boolean rangBell;
	// Square squareToMoveTo;

	enum HUNT_STATE {
		PICK_WILD_ANIMAL, GO_TO_WILD_ANIMAL_AND_ATTACK, GO_TO_WILD_ANIMAL_AND_LOOT, PICK_SHOP_KEEPER, GO_TO_SHOP_KEEPER_AND_SELL_JUNK, GO_TO_BED_AND_GO_TO_SLEEP, SLEEP
	};

	final String ACTIVITY_DESCRIPTION_LOOTING = "Looting!";
	final String ACTIVITY_DESCRIPTION_SKINNING = "Skinning";
	final String ACTIVITY_DESCRIPTION_HUNTING = "Goin' hunting";
	final String ACTIVITY_DESCRIPTION_SELLING_LOOT = "Selling spoils";
	final String ACTIVITY_DESCRIPTION_GOING_TO_BED = "Bed time";
	final String ACTIVITY_DESCRIPTION_SLEEPING = "Zzzzzz";
	final String ACTIVITY_DESCRIPTION_FIGHTING = "Fighting";
	final String ACTIVITY_DESCRIPTION_SEARCHING = "Searching";
	final String ACTIVITY_DESCRIPTION_FOLLOWING = "Following";

	public HUNT_STATE huntState = HUNT_STATE.PICK_WILD_ANIMAL;

	int sleepCounter = 0;
	final int SLEEP_TIME = 1000;

	public AIRoutineForMort(Mort mort) {

		super(mort);
		this.mort = mort;
	}

	@Override
	public void update() {

		this.actor.activityDescription = null;
		this.actor.expressionImageTexture = null;
		createSearchLocationsBasedOnSounds();
		createSearchLocationsBasedOnVisibleAttackers();
		if (!rangBell && mort.remainingHealth < mort.totalHealth / 2) {
			Bell bell = (Bell) mort.inventory.getGameObectOfClass(Bell.class);
			if (bell != null) {
				new ActionRing(mort, bell).perform();
				rangBell = true;
				return;
			}
		}

		if (runFightRoutine())
			return;
		if (runSearchRoutine()) {
			return;
		}

		Square squareWherePlayerVisibleInTerritory = createSearchLocationForPlayerIfVisibleAndInTerritory();
		// System.out.println("squareWherePlayerVisibleInTerritory x,y = " +
		// squareWherePlayerVisibleInTerritory.xInGrid
		// + "," + squareWherePlayerVisibleInTerritory.yInGrid);
		if (squareWherePlayerVisibleInTerritory == null) {

			if (lastLocationSeenPlayer != null) {

				System.out.println("lastLocationSeenPlayer x,y = " + lastLocationSeenPlayer.xInGrid + ","
						+ lastLocationSeenPlayer.yInGrid);

				System.out.println("Calling moveTowardsTargetSquare");
				AIRoutineUtils.moveTowardsTargetSquare(lastLocationSeenPlayer);

				// refuses to walk through door

				if (mort.squareGameObjectIsOn == lastLocationSeenPlayer) {
					System.out.println("calling lastLocationSeenPlayer = null");
					lastLocationSeenPlayer = null;
				}
				return;
			}
		} else {
			lastLocationSeenPlayer = squareWherePlayerVisibleInTerritory;

			System.out.println("lastLocationSeenPlayer x,y = " + lastLocationSeenPlayer.xInGrid + ","
					+ lastLocationSeenPlayer.yInGrid);

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
