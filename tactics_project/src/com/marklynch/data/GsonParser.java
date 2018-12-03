package com.marklynch.data;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.marklynch.ai.utils.AILine;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Faction;
import com.marklynch.level.constructs.GroupOfActors;
import com.marklynch.level.constructs.Investigation;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.Stat;
import com.marklynch.level.constructs.area.Area;
import com.marklynch.level.constructs.bounds.structure.StructureSection;
import com.marklynch.level.constructs.enchantment.Enhancement;
import com.marklynch.level.constructs.inventory.Inventory;
import com.marklynch.level.constructs.inventory.SquareInventory;
import com.marklynch.level.squares.Node;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor.Direction;
import com.marklynch.objects.inanimateobjects.Seesaw;
import com.marklynch.objects.inanimateobjects.Switch.SWITCH_TYPE;
import com.marklynch.objects.utils.SwitchListener;
import com.marklynch.utils.Color;
import com.marklynch.utils.ResourceUtils;
import com.marklynch.utils.Texture;

public class GsonParser {
	public static Gson createGson() {

		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Texture.class, deserializerForTexture);
		gsonBuilder.registerTypeAdapter(Faction.class, deserializerForIdable);
		gsonBuilder.registerTypeAdapter(GroupOfActors.class, deserializerForIdable);
		gsonBuilder.registerTypeAdapter(SwitchListener.class, deserializerForIdable);
		gsonBuilder.registerTypeAdapter(Square.class, deserializerForIdable);
		gsonBuilder.registerTypeAdapter(Node.class, deserializerForIdable);
		gsonBuilder.registerTypeAdapter(Area.class, deserializerForIdable);
		gsonBuilder.registerTypeAdapter(Crime.class, deserializerForCrime);
		gsonBuilder.registerTypeAdapter(Investigation.class, deserializerForInvestigation);
		gsonBuilder.registerTypeAdapter(Sound.class, deserializerForSound);
		gsonBuilder.registerTypeAdapter(AILine.class, deserializerForAILine);
		gsonBuilder.registerTypeAdapter(Enhancement.class, deserializerForEnhancement);
		gsonBuilder.registerTypeAdapter(Inventory.class, deserializerForInventory);
		gsonBuilder.registerTypeAdapter(SquareInventory.class, deserializerForInventory);
		gsonBuilder.registerTypeAdapter(SWITCH_TYPE.class, deserializerForSWITCH_TYPE);
		gsonBuilder.registerTypeAdapter(Color.class, deserializerForColor);
		gsonBuilder.registerTypeAdapter(StructureSection.class, deserializerForIdable);
		gsonBuilder.registerTypeAdapter(Direction.class, deserializerForDirection);
		gsonBuilder.registerTypeAdapter(Stat.class, deserializerForStat);

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

		// Structure Room
		ArrayList<Class<?>> structureRoomClasses = PackageUtils
				.getClasses("com.marklynch.level.constructs.bounds.structure.structureroom");
		for (Class<?> clazz : structureRoomClasses) {
			gsonBuilder.registerTypeAdapter(clazz, serializerForIdable);
		}

		Gson gson = gsonBuilder.create();
		return gson;

	}

	// change serialization for specific types
	public static JsonDeserializer<Texture> deserializerForTexture = new JsonDeserializer<Texture>() {
		@Override
		public Texture deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			return ResourceUtils.getGlobalImage(json.getAsString(), true);
		}
	};
}
