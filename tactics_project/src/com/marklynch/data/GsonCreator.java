package com.marklynch.data;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.marklynch.ai.routines.AIRoutine;
import com.marklynch.ai.routines.AIRoutineForBlind;
import com.marklynch.ai.routines.AIRoutineForCarnivoreNeutralWildAnimal;
import com.marklynch.ai.routines.AIRoutineForDoctor;
import com.marklynch.ai.routines.AIRoutineForFish;
import com.marklynch.ai.routines.AIRoutineForFisherman;
import com.marklynch.ai.routines.AIRoutineForGuard;
import com.marklynch.ai.routines.AIRoutineForHerbivoreWildAnimal;
import com.marklynch.ai.routines.AIRoutineForHunter;
import com.marklynch.ai.routines.AIRoutineForKidnapper;
import com.marklynch.ai.routines.AIRoutineForMinecart;
import com.marklynch.ai.routines.AIRoutineForMiner;
import com.marklynch.ai.routines.AIRoutineForMort;
import com.marklynch.ai.routines.AIRoutineForPig;
import com.marklynch.ai.routines.AIRoutineForRockGolem;
import com.marklynch.ai.routines.AIRoutineForThief;
import com.marklynch.ai.routines.AIRoutineForTrader;
import com.marklynch.level.constructs.Faction;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.constructs.effect.EffectBleed;
import com.marklynch.level.constructs.effect.EffectBurning;
import com.marklynch.level.constructs.effect.EffectCurse;
import com.marklynch.level.constructs.effect.EffectHeal;
import com.marklynch.level.constructs.effect.EffectPoison;
import com.marklynch.level.constructs.effect.EffectWet;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.actors.Actor;
import com.marklynch.utils.Texture;

public class GsonCreator {

	public static Gson createGson() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(GameObject.class, serializerForGameObject);
		gsonBuilder.registerTypeAdapter(Actor.class, serializerForActor);
		gsonBuilder.registerTypeAdapter(Texture.class, serializerForTexture);
		gsonBuilder.registerTypeAdapter(Faction.class, serializerForFaction);

		// Effects //
		gsonBuilder.registerTypeAdapter(Effect.class, serializerForEffect);
		gsonBuilder.registerTypeAdapter(EffectBleed.class, serializerForEffect);
		gsonBuilder.registerTypeAdapter(EffectBurning.class, serializerForEffect);
		gsonBuilder.registerTypeAdapter(EffectCurse.class, serializerForEffect);
		gsonBuilder.registerTypeAdapter(EffectHeal.class, serializerForEffect);
		gsonBuilder.registerTypeAdapter(EffectPoison.class, serializerForEffect);
		gsonBuilder.registerTypeAdapter(EffectWet.class, serializerForEffect);

		// AIRoutines //
		gsonBuilder.registerTypeAdapter(AIRoutine.class, serializerForAIRoutine);
		gsonBuilder.registerTypeAdapter(AIRoutineForCarnivoreNeutralWildAnimal.class, serializerForAIRoutine);
		gsonBuilder.registerTypeAdapter(AIRoutineForDoctor.class, serializerForAIRoutine);
		gsonBuilder.registerTypeAdapter(AIRoutineForFish.class, serializerForAIRoutine);
		gsonBuilder.registerTypeAdapter(AIRoutineForFisherman.class, serializerForAIRoutine);
		gsonBuilder.registerTypeAdapter(AIRoutineForGuard.class, serializerForAIRoutine);
		gsonBuilder.registerTypeAdapter(AIRoutineForHerbivoreWildAnimal.class, serializerForAIRoutine);
		gsonBuilder.registerTypeAdapter(AIRoutineForHunter.class, serializerForAIRoutine);
		gsonBuilder.registerTypeAdapter(AIRoutineForMinecart.class, serializerForAIRoutine);
		gsonBuilder.registerTypeAdapter(AIRoutineForMiner.class, serializerForAIRoutine);
		gsonBuilder.registerTypeAdapter(AIRoutineForPig.class, serializerForAIRoutine);
		gsonBuilder.registerTypeAdapter(AIRoutineForRockGolem.class, serializerForAIRoutine);
		gsonBuilder.registerTypeAdapter(AIRoutineForThief.class, serializerForAIRoutine);
		gsonBuilder.registerTypeAdapter(AIRoutineForTrader.class, serializerForAIRoutine);
		gsonBuilder.registerTypeAdapter(AIRoutineForKidnapper.class, serializerForAIRoutine);
		gsonBuilder.registerTypeAdapter(AIRoutineForBlind.class, serializerForAIRoutine);
		gsonBuilder.registerTypeAdapter(AIRoutineForMort.class, serializerForAIRoutine);
		Gson gson = gsonBuilder.create();
		return gson;
	}

	static JsonSerializer<Faction> serializerForFaction = new JsonSerializer<Faction>() {
		@Override
		public JsonElement serialize(Faction src, Type type, JsonSerializationContext context) {
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("id", src.id);
			return jsonObject;
		}
	};

	static JsonSerializer<Effect> serializerForEffect = new JsonSerializer<Effect>() {
		@Override
		public JsonElement serialize(Effect src, Type type, JsonSerializationContext context) {
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("effectName", src.effectName);
			if (src.source != null)
				jsonObject.addProperty("source", src.source.id);
			if (src.target != null)
				jsonObject.addProperty("target", src.target.id);
			jsonObject.addProperty("totalTurns", src.totalTurns);
			jsonObject.addProperty("turnsRemaining", src.turnsRemaining);
			jsonObject.addProperty("imageTexture", src.imageTexture.path);
			return jsonObject;
		}
	};

	static JsonSerializer<GameObject> serializerForGameObject = new JsonSerializer<GameObject>() {
		@Override
		public JsonElement serialize(GameObject src, Type type, JsonSerializationContext context) {
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("id", src.id);
			return jsonObject;
		}
	};

	static JsonSerializer<Actor> serializerForActor = new JsonSerializer<Actor>() {
		@Override
		public JsonElement serialize(Actor src, Type type, JsonSerializationContext context) {
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("id", src.id);
			return jsonObject;
		}
	};

	static JsonSerializer<Texture> serializerForTexture = new JsonSerializer<Texture>() {
		@Override
		public JsonElement serialize(Texture src, Type type, JsonSerializationContext context) {
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("path", src.path);
			return jsonObject;
		}
	};

	static JsonSerializer<AIRoutine> serializerForAIRoutine = new JsonSerializer<AIRoutine>() {
		@Override
		public JsonElement serialize(AIRoutine src, Type type, JsonSerializationContext context) {
			JsonObject jsonObject = new JsonObject();
			if (src.actor != null)
				jsonObject.addProperty("actor", src.actor.id);
			if (src.target != null)
				jsonObject.addProperty("target", src.target.id);
			if (src.targetSquare != null)
				jsonObject.addProperty("target", src.targetSquare.id);
			jsonObject.addProperty("searchCooldown", src.searchCooldown);
			if (src.searchCooldownActor != null)
				jsonObject.addProperty("searchCooldownActor", src.searchCooldownActor.id);
			jsonObject.addProperty("escapeCooldown", src.escapeCooldown);
			if (src.escapeCooldownAttacker != null)
				jsonObject.addProperty("escapeCooldownAttacker", src.escapeCooldownAttacker.id);
			jsonObject.addProperty("wokenUpCountdown", src.wokenUpCountdown);
			jsonObject.addProperty("state", "" + src.state);
			jsonObject.addProperty("keepInBounds", src.keepInBounds);
			jsonObject.addProperty("areaBounds", Save.getArrayListStringForInsertion(src.areaBounds));
			jsonObject.addProperty("sectionBounds", Save.getArrayListStringForInsertion(src.sectionBounds));
			jsonObject.addProperty("roomBounds", Save.getArrayListStringForInsertion(src.roomBounds));
			jsonObject.addProperty("squareBounds", Save.getArrayListStringForInsertion(src.squareBounds));
			jsonObject.addProperty("currentHobby", "" + src.currentHobby);
			if (src.actorToKeepTrackOf != null)
				jsonObject.addProperty("actorToKeepTrackOf", src.actorToKeepTrackOf.id);
			if (src.lastLocationSeenActorToKeepTrackOf != null)
				jsonObject.addProperty("lastLocationSeenActorToKeepTrackOf", src.lastLocationSeenActorToKeepTrackOf.id);
			jsonObject.addProperty("sleepCounter", src.sleepCounter);
			jsonObject.addProperty("ignoreList", Save.getArrayListStringForInsertion(src.ignoreList));

			if (src instanceof AIRoutineForBlind) {
				// public Sound bellSound = null;
				AIRoutineForBlind aiRoutineForBlind = (AIRoutineForBlind) src;
				if (aiRoutineForBlind.meatChunk != null)
					jsonObject.addProperty("meatChunk", aiRoutineForBlind.meatChunk.id);
				if (aiRoutineForBlind.originalMeatChunkSquare != null)
					jsonObject.addProperty("originalMeatChunkSquare", aiRoutineForBlind.originalMeatChunkSquare.id);
				jsonObject.addProperty("hangry", aiRoutineForBlind.hangry);
				jsonObject.addProperty("timeSinceEating", aiRoutineForBlind.timeSinceEating);
				jsonObject.addProperty("failedToGetPathToBellCount", aiRoutineForBlind.failedToGetPathToBellCount);
				jsonObject.addProperty("failedToGetPathToFoodCount", aiRoutineForBlind.failedToGetPathToFoodCount);
			} else if (src instanceof AIRoutineForGuard) {
				jsonObject.addProperty("patrolIndex", ((AIRoutineForGuard) src).patrolIndex);
			} else if (src instanceof AIRoutineForHerbivoreWildAnimal) {
				jsonObject.addProperty("hidingCount", ((AIRoutineForHerbivoreWildAnimal) src).hidingCount);
			} else if (src instanceof AIRoutineForMort) {
				AIRoutineForMort aiRoutineForMort = (AIRoutineForMort) src;
				jsonObject.addProperty("rangBellAsLastResort", aiRoutineForMort.rangBellAsLastResort);
				jsonObject.addProperty("retreatedToRoom", aiRoutineForMort.retreatedToRoom);
				jsonObject.addProperty("feedingDemoState", "" + aiRoutineForMort.feedingDemoState);
			} else if (src instanceof AIRoutineForThief) {
				// int theftCooldown = 0;
				jsonObject.addProperty("theftCooldown", ((AIRoutineForThief) src).theftCooldown);
			}

			return jsonObject;
		}
	};

}
