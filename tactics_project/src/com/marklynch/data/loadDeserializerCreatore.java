package com.marklynch.data;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.Faction;
import com.marklynch.level.constructs.GroupOfActors;
import com.marklynch.level.constructs.area.Area;
import com.marklynch.level.squares.Node;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.inanimateobjects.Seesaw;
import com.marklynch.objects.utils.SwitchListener;
import com.marklynch.utils.ResourceUtils;
import com.marklynch.utils.Texture;

public class loadDeserializerCreatore {

	public static Gson createLoadDeserializerGson() {

		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Texture.class, deserializerForTexture);
		gsonBuilder.registerTypeAdapter(Faction.class, deserializerForIdable);
		gsonBuilder.registerTypeAdapter(GroupOfActors.class, deserializerForIdable);
		gsonBuilder.registerTypeAdapter(SwitchListener.class, deserializerForIdable);
		gsonBuilder.registerTypeAdapter(Square.class, deserializerForIdable);
		gsonBuilder.registerTypeAdapter(Node.class, deserializerForIdable);
		gsonBuilder.registerTypeAdapter(Area.class, deserializerForIdable);
		// gsonBuilder.registerTypeAdapter(Crime.class, deserializerForCrime);
		// gsonBuilder.registerTypeAdapter(Investigation.class,
		// deserializerForInvestigation);
		// gsonBuilder.registerTypeAdapter(Sound.class, deserializerForSound);
		// gsonBuilder.registerTypeAdapter(AILine.class, deserializerForAILine);
		// gsonBuilder.registerTypeAdapter(Enhancement.class,
		// deserializerForEnhancement);
		// gsonBuilder.registerTypeAdapter(Inventory.class, deserializerForInventory);
		// gsonBuilder.registerTypeAdapter(SquareInventory.class,
		// deserializerForInventory);
		// gsonBuilder.registerTypeAdapter(SWITCH_TYPE.class,
		// deserializerForSWITCH_TYPE);
		// gsonBuilder.registerTypeAdapter(Color.class, deserializerForColor);
		// gsonBuilder.registerTypeAdapter(StructureSection.class,
		// deserializerForIdable);
		// gsonBuilder.registerTypeAdapter(Direction.class, deserializerForDirection);
		// gsonBuilder.registerTypeAdapter(Stat.class, deserializerForStat);

		// Add deserializers for all GamObjects, Effects and aiRoutines
		ArrayList<Class<?>> gameObjectClasses = new ArrayList<Class<?>>();
		gameObjectClasses.addAll(PackageUtils.getClasses("com.marklynch.objects.actors"));
		gameObjectClasses.addAll(PackageUtils.getClasses("com.marklynch.objects.inanimateobjects"));
		gameObjectClasses.addAll(PackageUtils.getClasses("com.marklynch.objects.tools"));
		gameObjectClasses.addAll(PackageUtils.getClasses("com.marklynch.objects.armor"));
		for (Class<?> clazz : gameObjectClasses) {
			gsonBuilder.registerTypeAdapter(clazz, deserializerForIdable);
		}
		gsonBuilder.registerTypeAdapter(Seesaw.SeesawPart.class, deserializerForIdable);

		// // Effects
		// ArrayList<Class<?>> effectClasses =
		// PackageUtils.getClasses("com.marklynch.level.constructs.effect");
		// for (Class<?> clazz : effectClasses) {
		// gsonBuilder.registerTypeAdapter(clazz, deserializerForEffect);
		// }
		//
		// // AI Routines
		// ArrayList<Class<?>> aiRoutineClasses =
		// PackageUtils.getClasses("com.marklynch.ai.routines");
		// for (Class<?> clazz : aiRoutineClasses) {
		// gsonBuilder.registerTypeAdapter(clazz, deserializerForAIRoutine);
		// }
		//
		// // Power
		// ArrayList<Class<?>> powerClasses =
		// PackageUtils.getClasses("com.marklynch.level.constructs.power");
		// for (Class<?> clazz : powerClasses) {
		// gsonBuilder.registerTypeAdapter(clazz, deserializerForPower);
		// }

		// Quests
		ArrayList<Class<?>> questClasses = PackageUtils.getClasses("com.marklynch.level.quest");
		for (Class<?> clazz : questClasses) {
			gsonBuilder.registerTypeAdapter(clazz, deserializerForIdable);
		}

		// Structure Room
		ArrayList<Class<?>> structureRoomClasses = PackageUtils
				.getClasses("com.marklynch.level.constructs.bounds.structure.structureroom");
		for (Class<?> clazz : structureRoomClasses) {
			gsonBuilder.registerTypeAdapter(clazz, deserializerForIdable);
		}

		Gson gson = gsonBuilder.create();
		return gson;

	}

	// change serialization for specific types
	public static JsonDeserializer<Object> deserializerForIdable = new JsonDeserializer<Object>() {
		@Override
		public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			return Level.ids.get(json.getAsLong());
		}
	};

	// change serialization for specific types
	public static JsonDeserializer<Texture> deserializerForTexture = new JsonDeserializer<Texture>() {
		@Override
		public Texture deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			return ResourceUtils.getGlobalImage(json.getAsString(), true);
		}
	};
}
