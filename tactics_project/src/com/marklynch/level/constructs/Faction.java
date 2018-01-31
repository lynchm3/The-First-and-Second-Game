package com.marklynch.level.constructs;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.Vector;

import com.marklynch.Game;
import com.marklynch.ai.utils.AIPath;
import com.marklynch.objects.units.Actor;

import com.marklynch.utils.Texture;

public class Faction {
	public final static String[] editableAttributes = { "name", "color" };

	enum AI_MODE {
		TARGET_SPECIFIC_OBJECT, TARGET_NEAREST_ENEMY, TARGET_NEAREST_ENEMY_IN_A_FACTION, TARGET_WEAKEST_ENEMY, TARGET_WEAKEST_ENEMY_IN_A_FACTION, RANDOM_ATTACK_ENEMIES_ONLY, RANDOM_ATTACK_ENEMIES_AND_ALLIES, RANDOM_ATTACK_ENEMIES_AND_ALLIES_AND_OBJECT, TARGET_NEAREST_OBJECT
	};

	public String name;

	/**
	 * Map relationships of this faction towards others +-100
	 */
	public transient Map<Faction, FactionRelationship> relationships = new HashMap<Faction, FactionRelationship>();
	public transient Vector<Actor> actors = new Vector<Actor>();

	public transient Actor currentActor;
	public transient int currentActorIndex = 0;

	public String imageTexturePath = null;
	public transient Texture imageTexture = null;

	public enum STAGE {
		SELECT, MOVE, ATTACK
	};

	public transient STAGE currentStage = STAGE.SELECT;
	public transient int timeAtCurrentStage = 0;
	public transient final int STAGE_DURATION = 1000;

	// For saving and loading
	public String guid = UUID.randomUUID().toString();
	public Map<String, FactionRelationship> relationshipGUIDs = new HashMap<String, FactionRelationship>();

	public Faction(String name, String imagePath) {
		this.name = name;
		this.imageTexturePath = imagePath;
		loadImages();
	}

	public void loadImages() {
		// actors = new Vector<Actor>();
		this.imageTexture = getGlobalImage(imageTexturePath);
		// for (Actor actor : actors) {
		// actor.loadImages();
		// }
	}

	public void postLoad() {
		this.actors = new Vector<Actor>();

		currentStage = STAGE.SELECT;
		relationships = new HashMap<Faction, FactionRelationship>();
		for (String factionGUID : relationshipGUIDs.keySet()) {
			relationships.put(Game.level.findFactionFromGUID(factionGUID), relationshipGUIDs.get(factionGUID));
		}

		for (Faction faction : relationships.keySet()) {
			relationships.get(faction).postLoad(this, faction);
		}
	}

	public void update(int delta) {

		long start = System.currentTimeMillis();

		for (int i = 0; i < actors.size(); i++) {
			Actor actor = actors.get(i);
			Game.level.activeActor = actor;
			actor.update(delta);
		}

		ArrayList<Actor> deadActors = new ArrayList<Actor>();
		for (Actor actor : actors) {
			if (actor.remainingHealth <= 0)
				deadActors.add(actor);
		}

		actors.removeAll(deadActors);

		Game.level.endTurn();
	}

	// public void updateFORBATTLE(int delta) {
	//
	// if (currentStage == STAGE.SELECT) {
	// // Selecting a hero
	// if (timeAtCurrentStage == 0) {
	// // start of stage, perform action
	// currentActor = actors.get(currentActorIndex);
	// if (Game.level.activeActor != null)
	// Game.level.activeActor.unselected();
	// Game.level.activeActor = currentActor;
	// Game.level.activeActor.calculatePathToAllSquares(Game.level.squares);
	// Game.level.activeActor.calculateReachableSquares(Game.level.squares);
	// Game.level.activeActor.calculateAttackableSquares(Game.level.squares);
	// timeAtCurrentStage += delta;
	// } else if (timeAtCurrentStage >= STAGE_DURATION) {
	// currentStage = STAGE.MOVE;
	// timeAtCurrentStage = 0;
	// } else {
	// timeAtCurrentStage += delta;
	// }
	// } else if (currentStage == STAGE.MOVE) {
	// // Moving with selected hero
	// if (timeAtCurrentStage == 0) {
	//
	// boolean performedMove = currentActor.ai.move();
	//
	// // Optimal enemy
	// if (performedMove == false) {
	// currentStage = STAGE.ATTACK;
	// timeAtCurrentStage = 0;
	// } else {
	// timeAtCurrentStage += delta;
	//
	// }
	//
	// // THIS IS ALL SORTS OF FUCKED UP COZ OF MY ORIGINAL PLAN :P
	//
	// GameObject target = AIRoutineUtils.getNearest(WildAnimal.class);
	// AIRoutineUtils.moveTowardsTargetToAttack(target);
	//
	// // moveTowardsTarget(level.inanimateObjects.elementAt(0));
	// // AIRoutineUtils.moveToRandomSquare();
	// // TODO lure enemy in (like in WOW)
	// // TODO moveTowardsNearestEnemy
	// // TODO moveTowardsNearestObjecy
	//
	// // /////////////////////////////////////////////////////////
	// } else if (timeAtCurrentStage >= STAGE_DURATION) {
	// // end of move phase
	// currentStage = STAGE.ATTACK;
	// timeAtCurrentStage = 0;
	// } else {
	// timeAtCurrentStage += delta;
	// }
	// } else if (currentStage == STAGE.ATTACK) {
	// // Attacking with selected hero
	//
	// if (timeAtCurrentStage == 0) {
	//
	// boolean performedAttack = currentActor.ai.attack();
	//
	// if (performedAttack == false) {
	// currentStage = STAGE.SELECT;
	// timeAtCurrentStage = 0;
	//
	// if (actors.size() <= currentActorIndex) {
	// // last dude in the array died, next faction's turn
	// currentActorIndex = 0;
	// Game.level.endTurn();
	// } else {
	// if (currentActor != actors.get(currentActorIndex)) {
	// // actor died, so the array has shifted...
	// // next actor is at his old position in the array,
	// // so don't increment
	// // index
	// } else {
	// // actor didnt die, proceed as expected...
	// currentActorIndex++;
	// }
	// if (currentActorIndex >= actors.size()) {
	// currentActorIndex = 0;
	// Game.level.endTurn();
	// }
	// }
	// } else {
	// timeAtCurrentStage += delta;
	// }
	//
	// // /////////////////////////////////////////////////////////
	// } else if (timeAtCurrentStage >= STAGE_DURATION) {
	// // end of attack phase
	// currentStage = STAGE.SELECT;
	// timeAtCurrentStage = 0;
	//
	// if (actors.size() <= currentActorIndex) {
	// // last dude in the array died, next faction's turn
	// currentActorIndex = 0;
	// Game.level.endTurn();
	// } else {
	// if (currentActor != actors.get(currentActorIndex)) {
	// // actor died, so the array has shifted...
	// // next actor is at his old position in the array,
	// // so don't increment
	// // index
	// } else {
	// // actor didnt die, proceed as expected...
	// currentActorIndex++;
	// }
	// if (currentActorIndex >= actors.size()) {
	// currentActorIndex = 0;
	// Game.level.endTurn();
	// }
	// }
	//
	// } else {
	// timeAtCurrentStage += delta;
	// }
	//
	// }
	// }

	class PathComparator implements Comparator<AIPath> {
		@Override
		public int compare(AIPath a, AIPath b) {
			return a.travelCost < b.travelCost ? -1 : a.travelCost == b.travelCost ? 0 : 1;
		}
	}

	public void checkIfDestroyed() {
		// if (this.actors.size() == 0)
		// Game.level.logOnScreen(new ActivityLog(new Object[] { this, " have
		// been stopped" }));

	}

	@Override
	public String toString() {
		return name;
	}
}
