package com.marklynch.data;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.marklynch.ai.routines.AIRoutine;
import com.marklynch.ai.routines.AIRoutineForBlind;
import com.marklynch.ai.routines.AIRoutineForGuard;
import com.marklynch.ai.routines.AIRoutineForHerbivoreWildAnimal;
import com.marklynch.ai.routines.AIRoutineForMort;
import com.marklynch.ai.routines.AIRoutineForThief;
import com.marklynch.ai.utils.AILine;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Faction;
import com.marklynch.level.constructs.Investigation;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.constructs.enchantment.Enhancement;
import com.marklynch.level.constructs.inventory.Inventory;
import com.marklynch.level.constructs.inventory.SquareInventory;
import com.marklynch.level.constructs.power.Power;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.inanimateobjects.Seesaw;
import com.marklynch.utils.Texture;

public class GsonCreator {

	public static Gson createGson() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Texture.class, serializerForTexture);
		gsonBuilder.registerTypeAdapter(Faction.class, serializerForIdable);
		gsonBuilder.registerTypeAdapter(Crime.class, serializerForCrime);
		gsonBuilder.registerTypeAdapter(Investigation.class, serializerForInvestigation);
		gsonBuilder.registerTypeAdapter(Sound.class, serializerForSound);
		gsonBuilder.registerTypeAdapter(AILine.class, serializerForAILine);
		gsonBuilder.registerTypeAdapter(Enhancement.class, serializerForEnhancement);
		gsonBuilder.registerTypeAdapter(Inventory.class, serializerForInventory);
		gsonBuilder.registerTypeAdapter(SquareInventory.class, serializerForInventory);
		gsonBuilder.registerTypeAdapter(Square.class, serializerForIdable);
		gsonBuilder.registerTypeAdapter(Power.class, serializerForPower);
		// gsonBuilder.registerTypeAdapter(SWITCH_TYPE.class, serializerForSWITCH_TYPE);
		// gsonBuilder.registerTypeAdapter(Color.class, serializerForColor);
		// gsonBuilder.registerTypeAdapter(GroupOfActors.class,
		// serializerForGroupOfActors);
		// gsonBuilder.registerTypeAdapter(Shift.class, serializerForShift);

		// Add serializers for all GamObjects, Effects and aiRoutines
		ArrayList<Class<?>> gameObjectClasses = new ArrayList<Class<?>>();
		gameObjectClasses.addAll(PackageUtils.getClasses("com.marklynch.objects.actors"));
		gameObjectClasses.addAll(PackageUtils.getClasses("com.marklynch.objects.inanimateobjects"));
		gameObjectClasses.addAll(PackageUtils.getClasses("com.marklynch.objects.tools"));
		gameObjectClasses.addAll(PackageUtils.getClasses("com.marklynch.objects.weapons"));
		for (Class<?> clazz : gameObjectClasses) {
			gsonBuilder.registerTypeAdapter(clazz, serializerForIdable);
		}
		gsonBuilder.registerTypeAdapter(Seesaw.SeesawPart.class, serializerForIdable);

		// Effects
		ArrayList<Class<?>> effectClasses = PackageUtils.getClasses("com.marklynch.level.constructs.effect");
		for (Class<?> clazz : effectClasses) {
			gsonBuilder.registerTypeAdapter(clazz, serializerForEffect);
		}

		// AI Routines
		ArrayList<Class<?>> aiRoutineClasses = PackageUtils.getClasses("com.marklynch.ai.routines");
		for (Class<?> clazz : aiRoutineClasses) {
			gsonBuilder.registerTypeAdapter(clazz, serializerForAIRoutine);
		}

		// Power
		ArrayList<Class<?>> powerClasses = PackageUtils.getClasses("com.marklynch.level.constructs.power");
		for (Class<?> clazz : powerClasses) {
			gsonBuilder.registerTypeAdapter(clazz, serializerForPower);
		}

		// Quests
		ArrayList<Class<?>> questClasses = PackageUtils.getClasses("com.marklynch.level.quest");
		for (Class<?> clazz : questClasses) {
			gsonBuilder.registerTypeAdapter(clazz, serializerForIdable);
		}

		Gson gson = gsonBuilder.create();
		return gson;
	}

	static JsonSerializer<Idable> serializerForIdable = new JsonSerializer<Idable>() {
		@Override
		public JsonElement serialize(Idable src, Type type, JsonSerializationContext context) {
			return new JsonPrimitive(src.getId());
		}
	};

	static JsonSerializer<Power> serializerForPower = new JsonSerializer<Power>() {
		@Override
		public JsonElement serialize(Power src, Type type, JsonSerializationContext context) {
			return new JsonPrimitive(src.getClass().getSimpleName());
		}
	};

	// static JsonSerializer<Faction> serializerForFaction = new
	// JsonSerializer<Faction>() {
	// @Override
	// public JsonElement serialize(Faction src, Type type, JsonSerializationContext
	// context) {
	// JsonObject jsonObject = new JsonObject();
	// jsonObject.addProperty("id", src.id);
	// return jsonObject;
	// }
	// };

	// static JsonSerializer<Square> serializerForSquare = new
	// JsonSerializer<Square>() {
	// @Override
	// public JsonElement serialize(Square src, Type type, JsonSerializationContext
	// context) {
	// JsonPrimitive jsonPrimitive = new JsonPrimitive(src.id);
	// return jsonPrimitive;
	// }
	// };

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

	// static JsonSerializer<GameObject> serializerForGameObject = new
	// JsonSerializer<GameObject>() {
	// @Override
	// public JsonElement serialize(GameObject src, Type type,
	// JsonSerializationContext context) {
	// JsonObject jsonObject = new JsonObject();
	// jsonObject.addProperty("id", src.id);
	// return jsonObject;
	// }
	// };

	// static JsonSerializer<Actor> serializerForActor = new JsonSerializer<Actor>()
	// {
	// @Override
	// public JsonElement serialize(Actor src, Type type, JsonSerializationContext
	// context) {
	// JsonObject jsonObject = new JsonObject();
	// jsonObject.addProperty("id", src.id);
	// return jsonObject;
	// }
	// };

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
				if (aiRoutineForBlind.bellSound != null)
					jsonObject.addProperty("bellSound", Save.gson.toJson(aiRoutineForBlind.bellSound));
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

	static JsonSerializer<Crime> serializerForCrime = new JsonSerializer<Crime>() {
		@Override
		public JsonElement serialize(Crime src, Type type, JsonSerializationContext context) {
			JsonObject jsonObject = new JsonObject();
			if (src.performer != null)
				jsonObject.addProperty("performer", src.performer.id);
			if (src.victim != null)
				jsonObject.addProperty("victim", src.victim.id);
			jsonObject.addProperty("hasBeenToldToStop", src.hasBeenToldToStop);
			jsonObject.addProperty("resolved", src.resolved);
			jsonObject.addProperty("reported", src.reported);
			jsonObject.addProperty("stolenItems", Save.getGameObjectArrayStringForInsertion(src.stolenItems));
			jsonObject.addProperty("type", "" + src.type);
			jsonObject.addProperty("crimeListeners", Save.getArrayListStringForInsertion(src.crimeListeners));
			return jsonObject;
		}
	};

	static JsonSerializer<Investigation> serializerForInvestigation = new JsonSerializer<Investigation>() {
		@Override
		public JsonElement serialize(Investigation src, Type type, JsonSerializationContext context) {
			JsonObject jsonObject = new JsonObject();
			if (src.actor != null)
				jsonObject.addProperty("actor", src.actor.id);
			if (src.square != null)
				jsonObject.addProperty("square", src.square.id);
			jsonObject.addProperty("priority", src.priority);
			return jsonObject;
		}
	};

	static JsonSerializer<Sound> serializerForSound = new JsonSerializer<Sound>() {
		@Override
		public JsonElement serialize(Sound src, Type type, JsonSerializationContext context) {
			JsonObject jsonObject = new JsonObject();
			if (src.sourcePerformer != null)
				jsonObject.addProperty("sourcePerformer", src.sourcePerformer.id);
			if (src.sourceObject != null)
				jsonObject.addProperty("sourceObject", src.sourceObject.id);
			if (src.sourceSquare != null)
				jsonObject.addProperty("sourceSquare", src.sourceSquare.id);
			jsonObject.addProperty("destinationSquares", Save.getArrayListStringForInsertion(src.destinationSquares));
			jsonObject.addProperty("loudness", src.loudness);
			jsonObject.addProperty("legal", src.legal);
			if (src.actionType != null)
				jsonObject.addProperty("actionType", src.actionType.getName());
			return jsonObject;
		}
	};

	static JsonSerializer<AILine> serializerForAILine = new JsonSerializer<AILine>() {
		@Override
		public JsonElement serialize(AILine src, Type type, JsonSerializationContext context) {
			JsonObject jsonObject = new JsonObject();
			if (src.source != null)
				jsonObject.addProperty("source", src.source.id);
			if (src.target != null)
				jsonObject.addProperty("target", src.target.id);
			jsonObject.addProperty("aiLineType", "" + src.aiLineType);
			return jsonObject;
		}
	};

	static JsonSerializer<Enhancement> serializerForEnhancement = new JsonSerializer<Enhancement>() {
		@Override
		public JsonElement serialize(Enhancement src, Type type, JsonSerializationContext context) {
			JsonObject jsonObject = new JsonObject();

			jsonObject.addProperty("highLevelStats", Save.getHashMapStringForInsertion(src.highLevelStats));
			if (src.imageTexture != null)
				jsonObject.addProperty("imageTexture", src.imageTexture.path);
			jsonObject.addProperty("minRange", src.minRange);
			jsonObject.addProperty("maxRange", src.maxRange);
			jsonObject.addProperty("effect", Save.gson.toJson(src.effect));
			jsonObject.addProperty("templateId", src.templateId);
			jsonObject.addProperty("type", "" + src.type);
			return jsonObject;
		}
	};

	static JsonSerializer<Inventory> serializerForInventory = new JsonSerializer<Inventory>() {
		@Override
		public JsonElement serialize(Inventory src, Type type, JsonSerializationContext context) {
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("gameObjects", Save.gson.toJson(src.gameObjects));
			return jsonObject;
		}
	};

}
