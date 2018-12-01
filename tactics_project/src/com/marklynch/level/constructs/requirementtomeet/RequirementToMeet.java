package com.marklynch.level.constructs.requirementtomeet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.marklynch.objects.actors.Actor;

public abstract class RequirementToMeet {

	public abstract boolean isRequirementMet(Actor actor);

	public abstract String getText();

	public static String getStringForSavingRequirementsToMeet(Object effects) {

		if (effects == null)
			return "";

		GsonBuilder gsonBuilder = new GsonBuilder();
		// gsonBuilder.registerTypeAdapter(PowerRequirementToMeet.class,
		// serializerForPowerRequirementToMeet);
		// gsonBuilder.registerTypeAdapter(SkillRequirementToMeet.class,
		// serializerForSkillRequirementToMeet);
		// gsonBuilder.registerTypeAdapter(StatRequirementToMeet.class,
		// serializerForStatRequirementToMeet);
		Gson gson = gsonBuilder.create();
		String jsonInString = gson.toJson(effects);
		return jsonInString;
	}

	// static JsonSerializer<PowerRequirementToMeet>
	// serializerForPowerRequirementToMeet = new
	// JsonSerializer<PowerRequirementToMeet>() {
	// @Override
	// public JsonElement serialize(PowerRequirementToMeet src, Type type,
	// JsonSerializationContext context) {
	// JsonObject jsonObject = new JsonObject();
	// if (src.power != null)
	// jsonObject.addProperty("power", src.power.getClass().getSimpleName());
	// return jsonObject;
	// }
	// };
	//
	// static JsonSerializer<SkillRequirementToMeet>
	// serializerForSkillRequirementToMeet = new
	// JsonSerializer<SkillRequirementToMeet>() {
	// @Override
	// public JsonElement serialize(SkillRequirementToMeet src, Type type,
	// JsonSerializationContext context) {
	// JsonObject jsonObject = new JsonObject();
	// return jsonObject;
	// }
	// };
	//
	// static JsonSerializer<StatRequirementToMeet>
	// serializerForStatRequirementToMeet = new
	// JsonSerializer<StatRequirementToMeet>() {
	// @Override
	// public JsonElement serialize(StatRequirementToMeet src, Type type,
	// JsonSerializationContext context) {
	// JsonObject jsonObject = new JsonObject();
	// HIGH_LEVEL_STATS stat;
	// float minimumStatLevel;
	// if (src.power != null)
	// jsonObject.addProperty("power", src.power.getClass().getSimpleName());
	// return jsonObject;
	// }
	// };

}
