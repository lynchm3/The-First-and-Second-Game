package com.marklynch.data;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.marklynch.ai.routines.AIRoutine;
import com.marklynch.ai.routines.AIRoutineForCarnivoreNeutralWildAnimal;
import com.marklynch.ai.routines.AIRoutineForDoctor;
import com.marklynch.ai.routines.AIRoutineForFish;
import com.marklynch.ai.routines.AIRoutineForFisherman;
import com.marklynch.ai.routines.AIRoutineForGuard;
import com.marklynch.ai.routines.AIRoutineForHerbivoreWildAnimal;
import com.marklynch.ai.routines.AIRoutineForHunter;
import com.marklynch.ai.routines.AIRoutineForMinecart;
import com.marklynch.ai.routines.AIRoutineForMiner;
import com.marklynch.ai.routines.AIRoutineForPig;
import com.marklynch.ai.routines.AIRoutineForRockGolem;
import com.marklynch.ai.routines.AIRoutineForThief;
import com.marklynch.ai.routines.AIRoutineForTrader;
import com.marklynch.level.constructs.Faction;
import com.marklynch.level.constructs.area.Area;
import com.marklynch.level.constructs.bounds.structure.StructureRoom;
import com.marklynch.level.constructs.bounds.structure.StructureSection;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.constructs.effect.EffectBleed;
import com.marklynch.level.constructs.effect.EffectBurning;
import com.marklynch.level.constructs.effect.EffectCurse;
import com.marklynch.level.constructs.effect.EffectHeal;
import com.marklynch.level.constructs.effect.EffectPoison;
import com.marklynch.level.constructs.effect.EffectWet;
import com.marklynch.level.quest.caveoftheblind.AIRoutineForBlind;
import com.marklynch.level.quest.caveoftheblind.AIRoutineForMort;
import com.marklynch.level.quest.thesecretroom.AIRoutineForKidnapper;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.actors.Actor.HOBBY;
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

	public transient Actor actor;
	public transient GameObject target;
	public transient int searchCooldown = 0;
	public transient GameObject searchCooldownActor = null;
	public transient int escapeCooldown = 0;
	public transient GameObject escapeCooldownAttacker = null;
	public transient int wokenUpCountdown = 0;

	enum STATE {
		HUNTING, MINING, GO_TO_WILD_ANIMAL_AND_ATTACK, GO_TO_WILD_ANIMAL_AND_LOOT, GO_TO_BED_AND_GO_TO_SLEEP, PATROL, FREE_TIME, FISHING, SHOPKEEPING, THIEVING, UPDATING_SIGN, SWIMMING
	};

	public transient STATE state;

	public static enum AI_TYPE {
		FIGHTER, RUNNER, GUARD, HOSTILE, ANIMAL
	};

	public transient AI_TYPE aiType = AI_TYPE.FIGHTER;

	public transient boolean keepInBounds = false;
	public transient ArrayList<Area> areaBounds = new ArrayList<Area>();
	public transient ArrayList<StructureSection> sectionBounds = new ArrayList<StructureSection>();
	public transient ArrayList<StructureRoom> roomBounds = new ArrayList<StructureRoom>();
	public transient ArrayList<Square> squareBounds = new ArrayList<Square>();

	public transient HOBBY currentHobby = HOBBY.HUNTING;

	public transient Actor actorToKeepTrackOf = null;
	public transient Square lastLocationSeenActorToKeepTrackOf = null;
	public transient ArrayList<GameObject> ignoreList = new ArrayList<GameObject>();

	static JsonSerializer<AIRoutine> serializerForAIRoutine = new JsonSerializer<AIRoutine>() {
		@Override
		public JsonElement serialize(AIRoutine src, Type type, JsonSerializationContext context) {
			JsonObject jsonObject = new JsonObject();
			if (src.actor != null)
				jsonObject.addProperty("actor", src.actor.id);
			if (src.target != null)
				jsonObject.addProperty("target", src.target.id);
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
			jsonObject.addProperty("ignoreList", Save.getArrayListStringForInsertion(src.ignoreList));
			return jsonObject;
		}
	};

}
