package com.marklynch.data;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.objectweb.asm.Type;

import com.google.gson.Gson;
import com.marklynch.ai.routines.AIRoutine;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Faction;
import com.marklynch.level.constructs.GroupOfActors;
import com.marklynch.level.constructs.Stat;
import com.marklynch.level.constructs.Stat.HIGH_LEVEL_STATS;
import com.marklynch.level.constructs.area.Area;
import com.marklynch.level.constructs.bounds.structure.StructureRoom;
import com.marklynch.level.constructs.bounds.structure.StructureSection;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.constructs.enchantment.Enhancement;
import com.marklynch.level.constructs.inventory.Inventory;
import com.marklynch.level.constructs.power.Power;
import com.marklynch.level.constructs.requirementtomeet.RequirementToMeet;
import com.marklynch.level.quest.Quest;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.Bed;
import com.marklynch.objects.Carcass;
import com.marklynch.objects.Consumable;
import com.marklynch.objects.Corpse;
import com.marklynch.objects.DamageDealer;
import com.marklynch.objects.Discoverable;
import com.marklynch.objects.Door;
import com.marklynch.objects.Food;
import com.marklynch.objects.Furnace;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.GameObjectExploder;
import com.marklynch.objects.Gold;
import com.marklynch.objects.HidingPlace;
import com.marklynch.objects.Inspectable;
import com.marklynch.objects.Key;
import com.marklynch.objects.Landmine;
import com.marklynch.objects.Liquid;
import com.marklynch.objects.MapMarker;
import com.marklynch.objects.Matches;
import com.marklynch.objects.MeatChunk;
import com.marklynch.objects.MineCart;
import com.marklynch.objects.Mirror;
import com.marklynch.objects.Openable;
import com.marklynch.objects.Orb;
import com.marklynch.objects.Portal;
import com.marklynch.objects.PressurePlate;
import com.marklynch.objects.PressurePlateRequiringSpecificItem;
import com.marklynch.objects.Rail;
import com.marklynch.objects.RemoteDoor;
import com.marklynch.objects.Roof;
import com.marklynch.objects.Searchable;
import com.marklynch.objects.Seesaw;
import com.marklynch.objects.SmallHidingPlace;
import com.marklynch.objects.Stampable;
import com.marklynch.objects.Storage;
import com.marklynch.objects.Stump;
import com.marklynch.objects.Switch;
import com.marklynch.objects.Switch.SWITCH_TYPE;
import com.marklynch.objects.SwitchListener;
import com.marklynch.objects.Tree;
import com.marklynch.objects.UpdatesWhenSquareContentsChange;
import com.marklynch.objects.Vein;
import com.marklynch.objects.VoidHole;
import com.marklynch.objects.WallSupport;
import com.marklynch.objects.WallWithCrack;
import com.marklynch.objects.WantedPoster;
import com.marklynch.objects.WaterBody;
import com.marklynch.objects.WaterSource;
import com.marklynch.objects.Window;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.actors.Actor.HOBBY;
import com.marklynch.objects.actors.AggressiveWildAnimal;
import com.marklynch.objects.actors.Animal;
import com.marklynch.objects.actors.CarnivoreNeutralWildAnimal;
import com.marklynch.objects.actors.Doctor;
import com.marklynch.objects.actors.Fish;
import com.marklynch.objects.actors.Guard;
import com.marklynch.objects.actors.Guard.Shift;
import com.marklynch.objects.actors.HerbivoreWildAnimal;
import com.marklynch.objects.actors.Human;
import com.marklynch.objects.actors.Monster;
import com.marklynch.objects.actors.Pig;
import com.marklynch.objects.actors.Player;
import com.marklynch.objects.actors.Thief;
import com.marklynch.objects.actors.TinyNeutralWildAnimal;
import com.marklynch.objects.actors.Trader;
import com.marklynch.objects.actors.WildAnimal;
import com.marklynch.objects.tools.Axe;
import com.marklynch.objects.tools.Bell;
import com.marklynch.objects.tools.ContainerForLiquids;
import com.marklynch.objects.tools.FishingRod;
import com.marklynch.objects.tools.FlammableLightSource;
import com.marklynch.objects.tools.Knife;
import com.marklynch.objects.tools.Lantern;
import com.marklynch.objects.tools.Pickaxe;
import com.marklynch.objects.tools.Shovel;
import com.marklynch.objects.tools.Tool;
import com.marklynch.objects.weapons.Armor;
import com.marklynch.objects.weapons.BodyArmor;
import com.marklynch.objects.weapons.Helmet;
import com.marklynch.objects.weapons.LegArmor;
import com.marklynch.objects.weapons.Weapon;
import com.marklynch.utils.Texture;

public class Save {
	public static HashMap<Class<?>, ArrayList<Field>> fieldsForEachClass = new HashMap<Class<?>, ArrayList<Field>>();

	public static Gson gson;

	// When you decide to save
	// 1. turn on pause mode (if not already) - Show spinner w/ "Ending Turn"
	// 2. end the turn - Show spinner w/ "Ending Turn"
	// 3. end all animations - Show spinner w/ "Ending Turn"
	// 3. run save

	// Keep this in order they should be loaded.
	public static Class<?>[] classesToSave = new Class[] {

			// Squares
			// Square.class,
			// Faction
			// Faction.class,

			// LVL 4 GameObject subclass in Actor package
			TinyNeutralWildAnimal.class, HerbivoreWildAnimal.class, Fish.class, CarnivoreNeutralWildAnimal.class,
			AggressiveWildAnimal.class,

			// LVL 3 GameObject subclass
			Seesaw.class,

			// LVL 3 GameObject subclass in Tool
			Shovel.class, Pickaxe.class, Knife.class, FlammableLightSource.class, FishingRod.class,
			ContainerForLiquids.class, Bell.class, Axe.class,

			// LVL 3 GameObject subclass in Actor package
			Player.class, WildAnimal.class, Trader.class, Thief.class, Pig.class, Guard.class, Doctor.class,

			// LVL 2 GameObject subclass in Tool
			Lantern.class,

			// LVL 2 GameObject subclass
			Door.class, Furnace.class, HidingPlace.class, Landmine.class, Matches.class, PressurePlate.class,
			PressurePlateRequiringSpecificItem.class, RemoteDoor.class, SmallHidingPlace.class, Storage.class,
			Vein.class, WallWithCrack.class, WaterBody.class,

			// LVL 2 GameObject subclass in Weapon package
			BodyArmor.class, Helmet.class, LegArmor.class,

			// LVL 2 GameObject subclass in Tool
			Tool.class,

			// LVL 2 GameObject subclass in Actor package
			Human.class, Monster.class, Animal.class,

			// LVL 1 GameObject subclass
			Bed.class, Carcass.class, Corpse.class, Discoverable.class, Food.class, GameObjectExploder.class,
			Gold.class, Inspectable.class, Key.class, Liquid.class, MapMarker.class, MeatChunk.class, MineCart.class,
			Mirror.class, Openable.class, Orb.class, Portal.class, Rail.class, Roof.class, Searchable.class,
			Stampable.class, Stump.class, Switch.class, Tree.class, VoidHole.class, /* Wall.class, */WallSupport.class,
			WantedPoster.class, WaterSource.class, Window.class,

			// LVL 1 GameObject subclass in Weapon package
			Weapon.class, Armor.class,

			// LVL 1 GameObject subclass in Actor package
			Actor.class,

			// GameObject itself THE SUPERCLASS
			GameObject.class,

			// GameObject interfaces
			Consumable.class, DamageDealer.class, SwitchListener.class, UpdatesWhenSquareContentsChange.class };

	static ArrayList<PreparedStatement> preparedStatements = new ArrayList<PreparedStatement>();
	static Connection conn;
	static long saveStartTime;
	static long saveEndTime1;
	static long saveEndTime2;
	public static String dbConn;

	public static void save() {

		if (gson == null)
			gson = GsonCreator.createGson();

		saveStartTime = System.currentTimeMillis();
		dbConn = "jdbc:sqlite:test" + System.currentTimeMillis() + ".db";

		try {

			// Create fields list for each class
			for (Class<?> classToSave : Save.classesToSave) {
				fieldsForEachClass.put(classToSave, Save.getFields(classToSave));
			}

			conn = DriverManager.getConnection(dbConn);

			createPreparedStatementForSquareInserts();
			createPreparedStatementForFactionInserts();

			// Create table for each class
			for (Class<?> classToSave : classesToSave) {
				createTable(classToSave);
			}

			// Create the insert statements for each class
			for (Class<?> classToSave : classesToSave) {

				PreparedStatement p = createPreparedStatementForInserts(classToSave);
				if (p != null)
					preparedStatements.add(p);
			}

			new DiskWritingThread().start();
		} catch (Exception e) {
			e.printStackTrace();
		}

		saveEndTime1 = System.currentTimeMillis();
		System.out.println("Non-disk save time = " + (saveEndTime1 - saveStartTime));

	}

	public static void createPreparedStatementForSquareInserts() {

		fieldsForEachClass.put(Square.class, Save.getFields(Square.class));
		createTable(Square.class);
		String insertQueryTemplate = "INSERT INTO Square VALUES (?,?,?)";
		// id
		// inventory
		// floorImageTexture
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(insertQueryTemplate);
			for (Square square : Level.squaresToSave) {
				preparedStatement.setLong(1, square.id);
				preparedStatement.setString(2, square.inventory.getObjectIdListForSaving());
				preparedStatement.setString(3, square.getFloorImageTexture().path);
				preparedStatement.addBatch();
			}
			preparedStatements.add(preparedStatement);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void createPreparedStatementForFactionInserts() {

		fieldsForEachClass.put(Faction.class, Save.getFields(Faction.class));
		createTable(Faction.class);
		String insertQueryTemplate = "INSERT INTO Faction VALUES (?,?,?,?,?)";

		// public long id;
		// public String name;
		// public HashMap<Faction, Integer> relationships = new HashMap<Faction,
		// Integer>();
		// public ArrayList<Actor> actors = new ArrayList<Actor>();
		// public Texture imageTexture = null;

		try {
			PreparedStatement preparedStatement = conn.prepareStatement(insertQueryTemplate);
			for (Faction faction : Level.factions) {
				preparedStatement.setLong(1, faction.id);
				preparedStatement.setString(2, faction.name);
				preparedStatement.setString(3, getHashMapStringForInsertion(faction.relationships));
				preparedStatement.setString(4, getArrayListStringForInsertion(faction.actors));
				preparedStatement.setString(5, faction.imageTexture.path);
				preparedStatement.addBatch();
			}
			preparedStatements.add(preparedStatement);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static class DiskWritingThread extends Thread {
		@Override
		public void run() {

			try {

				// insert for each class
				conn.setAutoCommit(false);
				for (PreparedStatement preparedStatement : preparedStatements) {
					preparedStatement.executeBatch();
				}
				conn.setAutoCommit(true);
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			saveEndTime2 = System.currentTimeMillis();
			System.out.println("Disk save time = " + (saveEndTime2 - saveEndTime1));
			System.out.println("Total save time = " + (saveEndTime2 - saveStartTime));
		}
	}

	public static PreparedStatement createPreparedStatementForInserts(Class clazz) {
		ArrayList<Field> fields = null;
		String insertQueryTemplate = null;
		Object object1 = null;

		// clazz is FlammableLightSource

		try {

			fields = fieldsForEachClass.get(clazz);

			if (fields.isEmpty())
				return null;

			// Make create table query and insert query template
			insertQueryTemplate = "INSERT INTO " + clazz.getSimpleName() + " VALUES (";
			for (Field field : fields) {
				insertQueryTemplate += "?";
				if (fields.get(fields.size() - 1) != field) {
					insertQueryTemplate += ",";
				}
			}
			insertQueryTemplate += ");";

			// Actually do the big ol' insert

			PreparedStatement preparedStatement = conn.prepareStatement(insertQueryTemplate);

			for (Object object : (ArrayList<?>) clazz.getField("instances").get(null)) {// GameObject.instances

				object1 = object;
				int count = 1;
				for (Field field : fields) {

					Object value = field.get(object); // THIS is the crashing line

					if (value instanceof Boolean) {
						preparedStatement.setBoolean(count, (Boolean) value);
					} else if (value instanceof Long) {
						preparedStatement.setLong(count, (Long) value);
					} else if (value instanceof Integer) {
						preparedStatement.setInt(count, (Integer) value);
					} else if (value instanceof String) {
						preparedStatement.setString(count, (String) value);
					} else if (value instanceof Float) {
						preparedStatement.setFloat(count, (Float) value);
					} else if (value instanceof Double) {
						preparedStatement.setDouble(count, (Double) value);
					} else if (value instanceof boolean[]) {
						preparedStatement.setString(count, gson.toJson(value));
					} else if (value instanceof long[]) {
						preparedStatement.setString(count, gson.toJson(value));
					} else if (value instanceof int[]) {
						preparedStatement.setString(count, gson.toJson(value));
					} else if (value instanceof String[]) {
						preparedStatement.setString(count, gson.toJson(value));
					} else if (value instanceof float[]) {
						preparedStatement.setString(count, gson.toJson(value));
					} else if (value instanceof double[]) {
						preparedStatement.setString(count, gson.toJson(value));
					} else if (value instanceof HOBBY[]) {
						preparedStatement.setString(count, gson.toJson(value));
					} else if (value instanceof Texture) {
						preparedStatement.setString(count, ((Texture) value).path);
						// Non-simple
					} else if (value instanceof Inventory) {
						preparedStatement.setString(count, ((Inventory) value).getObjectIdListForSaving());
					} else if (value instanceof SwitchListener) {
						preparedStatement.setLong(count, ((SwitchListener) value).getId());
					} else if (value instanceof SwitchListener[]) {
						preparedStatement.setString(count,
								getSwitchListenerArrayStringForInsertion((SwitchListener[]) value));
					} else if (value instanceof Square) {
						preparedStatement.setLong(count, ((Square) value).id);
					} else if (value instanceof RequirementToMeet) {
						preparedStatement.setString(count, gson.toJson(value));
					} else if (value instanceof RequirementToMeet[]) {
						preparedStatement.setString(count, gson.toJson(value));
					} else if (value instanceof Power) {
						preparedStatement.setString(count, value.getClass().getSimpleName());
					} else if (value instanceof Quest) {
						preparedStatement.setLong(count, ((Quest) value).id);
					} else if (value instanceof SWITCH_TYPE) {
						preparedStatement.setString(count, value.toString());
					} else if (value instanceof Crime) {
						preparedStatement.setString(count, gson.toJson(value));
					} else if (value instanceof Crime[]) {
						preparedStatement.setString(count, gson.toJson(value));
					} else if (value instanceof GameObject[]) {
						preparedStatement.setString(count, getGameObjectArrayStringForInsertion((GameObject[]) value));
					} else if (value instanceof Square[]) {
						preparedStatement.setString(count, getSquareArrayStringForInsertion((Square[]) value));
					} else if (value instanceof Effect[]) {
						preparedStatement.setString(count, gson.toJson(value));
					} else if (value instanceof GroupOfActors) {
						preparedStatement.setString(count, gson.toJson(value));
					} else if (value instanceof Shift) {
						preparedStatement.setString(count, gson.toJson(value));
					} else if (value instanceof StructureRoom) {
						preparedStatement.setLong(count, ((StructureRoom) value).id);
					} else if (value instanceof Area) {
						preparedStatement.setLong(count, ((Area) value).id);
					} else if (value instanceof Action) {
						preparedStatement.setLong(count, ((Action) value).id);
					} else if (value instanceof Enhancement) {
						preparedStatement.setLong(count, ((Enhancement) value).id);
					} else if (value instanceof Faction) {
						preparedStatement.setLong(count, ((Faction) value).id);
					} else if (value instanceof AIRoutine) {
						preparedStatement.setString(count, gson.toJson(value));
					} else if (value instanceof GameObject) {
						preparedStatement.setLong(count, ((GameObject) value).id);
					} else if (value instanceof HashMap<?, ?>) {
						preparedStatement.setString(count, getHashMapStringForInsertion((HashMap<?, ?>) value));
					} else if (value instanceof ArrayList<?>) {
						preparedStatement.setString(count, getArrayListStringForInsertion((ArrayList<?>) value));
					} else if (value instanceof Object) {
						preparedStatement.setString(count, "TODO Object class " + value);
					} else if (value == null) {
						preparedStatement.setNull(count, Type.VOID);
					}

					count++;
				}

				preparedStatement.addBatch();
			}

			return preparedStatement;

		} catch (Exception e) {
			System.err.println("=======================");
			System.err.println("saveGameObjects() error");
			System.err.println("clazz = " + clazz);
			System.err.println("fields = " + fields);
			System.err.println("object = " + object1);
			System.err.println("insertQueryTemplate = " + insertQueryTemplate);
			e.printStackTrace();
			System.err.println("=======================");
		}

		return null;

	}

	public static String getGameObjectArrayStringForInsertion(GameObject[] array) {

		String result = "[";
		for (GameObject gameObject : array) {
			result += gameObject.id;
			if (array[array.length - 1] != gameObject) {
				result += ",";
			}
		}
		result += "]";
		return result;
	}

	public static String getSquareArrayStringForInsertion(Square[] array) {

		String result = "[";
		for (Square square : array) {
			result += square.id;
			if (array[array.length - 1] != square) {
				result += ",";
			}
		}
		result += "]";
		return result;
	}

	public static String getSwitchListenerArrayStringForInsertion(SwitchListener[] array) {

		String result = "[";
		for (SwitchListener switchListener : array) {
			result += switchListener.getId();
			if (array[array.length - 1] != switchListener) {
				result += ",";
			}
		}
		result += "]";
		return result;
	}

	public static String getArrayListStringForInsertion(ArrayList<?> arrayList) {

		if (arrayList.size() == 0) {
			return "";

		} else if (arrayList.get(0) instanceof GameObject) {

			String result = "";
			for (GameObject gameObject : (ArrayList<GameObject>) arrayList) {
				result += gameObject.id;
				if (arrayList.get(arrayList.size() - 1) != gameObject) {
					result += ",";
				}
			}
			return result;

		} else if (arrayList.get(0) instanceof Area) {

			String result = "";
			for (Area area : (ArrayList<Area>) arrayList) {
				result += area.id;
				if (arrayList.get(arrayList.size() - 1) != area) {
					result += ",";
				}
			}
			return result;

		} else if (arrayList.get(0) instanceof StructureSection) {

			String result = "";
			for (StructureSection structureSection : (ArrayList<StructureSection>) arrayList) {
				result += structureSection.id;
				if (arrayList.get(arrayList.size() - 1) != structureSection) {
					result += ",";
				}
			}
			return result;

		} else if (arrayList.get(0) instanceof StructureRoom) {

			String result = "";
			for (StructureRoom room : (ArrayList<StructureRoom>) arrayList) {
				result += room.id;
				if (arrayList.get(arrayList.size() - 1) != room) {
					result += ",";
				}
			}
			return result;

		} else if (arrayList.get(0) instanceof Square) {

			String result = "";
			for (Square square : (ArrayList<Square>) arrayList) {
				result += square.id;
				if (arrayList.get(arrayList.size() - 1) != square) {
					result += ",";
				}
			}
			return result;

		} else if (arrayList.get(0) instanceof Power) {

			String result = "";
			for (Power power : (ArrayList<Power>) arrayList) {
				result += power.getClass().getSimpleName();
				if (arrayList.get(arrayList.size() - 1) != power) {
					result += ",";
				}
			}
			return result;

		} else if (arrayList.get(0) instanceof Effect) {

			return gson.toJson(arrayList);

		} else if (arrayList.get(0) instanceof Crime) {

			return gson.toJson(arrayList);

		}

		return "TODO Object class " + arrayList;
	}

	public static String getHashMapStringForInsertion(HashMap<?, ?> hashMap) {
		if (hashMap.size() == 0) {
			return "";
		}

		Object[] keySet = hashMap.keySet().toArray();
		Class hashMapKeyClass = keySet[0].getClass();

		if (hashMapKeyClass == Faction.class) {
			return gson.toJson(hashMap);
		} else if (hashMapKeyClass == HIGH_LEVEL_STATS.class) {
			return Stat.getStringForSavingHIGH_LEVEL_STATS((HashMap<HIGH_LEVEL_STATS, Stat>) hashMap);
		}

		return "TODO Object class " + hashMap;
	}

	static void createTable(Class<?> clazz) {

		ArrayList<Field> fields = null;
		Statement statement = null;
		String createTableQuery = null;
		try {

			statement = conn.createStatement();
			fields = fieldsForEachClass.get(clazz);

			if (fields.isEmpty())
				return;

			// Make create table query and insert query template
			createTableQuery = "CREATE TABLE " + clazz.getSimpleName() + " (";
			for (Field field : fields) {
				createTableQuery += field.getName();
				if (fields.get(fields.size() - 1) != field) {
					createTableQuery += ",";
				}
			}
			createTableQuery += ");";

			statement.executeUpdate(createTableQuery);
		} catch (Exception e) {
			System.err.println("=======================");
			System.err.println("saveGameObjects() error");
			System.err.println("clazz = " + clazz);
			System.err.println("fields = " + fields);
			e.printStackTrace();
			System.err.println("=======================");

		}

	}

	static ArrayList<Field> getFields(Class<?> clazz) {

		try {
			ArrayList<Field> fields = new ArrayList<Field>(Arrays.asList(clazz.getFields()));
			ArrayList<Field> declaredFields = new ArrayList<Field>(Arrays.asList(clazz.getDeclaredFields()));

			// Remove transient and static fields, don't want to save them
			for (Field field : (ArrayList<Field>) fields.clone()) {
				if (
				//
				Modifier.isTransient(field.getModifiers())
						//
						|| Modifier.isStatic(field.getModifiers())
						//
						|| (!declaredFields.contains(field) && !field.getName().equals("id")))
				//
				{
					fields.remove(field);
				}
			}
			return fields;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

}