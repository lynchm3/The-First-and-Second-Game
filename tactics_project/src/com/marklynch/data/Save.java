package com.marklynch.data;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.objectweb.asm.Type;

import com.marklynch.level.constructs.GroupOfActors;
import com.marklynch.level.constructs.enchantment.Enhancement;
import com.marklynch.level.constructs.inventory.Inventory;
import com.marklynch.level.constructs.inventory.InventorySquare;
import com.marklynch.level.quest.Quest;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.Bed;
import com.marklynch.objects.Carcass;
import com.marklynch.objects.Consumable;
import com.marklynch.objects.Corpse;
import com.marklynch.objects.DamageDealer;
import com.marklynch.objects.Discoverable;
import com.marklynch.objects.Door;
import com.marklynch.objects.Fence;
import com.marklynch.objects.Food;
import com.marklynch.objects.Furnace;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.GameObjectExploder;
import com.marklynch.objects.Gate;
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
import com.marklynch.objects.SwitchListener;
import com.marklynch.objects.Tree;
import com.marklynch.objects.UpdatesWhenSquareContentsChange;
import com.marklynch.objects.Vein;
import com.marklynch.objects.VoidHole;
import com.marklynch.objects.Wall;
import com.marklynch.objects.WallSupport;
import com.marklynch.objects.WallWithCrack;
import com.marklynch.objects.WantedPoster;
import com.marklynch.objects.WaterBody;
import com.marklynch.objects.WaterSource;
import com.marklynch.objects.Window;
import com.marklynch.objects.actions.Action;
import com.marklynch.utils.Texture;

public class Save {

	// When you decide to save
	// 1. turn on pause mode (if not already) - Show spinner w/ "Ending Turn"
	// 2. end the turn - Show spinner w/ "Ending Turn"
	// 3. end all animations - Show spinner w/ "Ending Turn"
	// 3. run save

	// Keep this in order they should be laoded.
	public static Class[] classesToSave = new Class[] {

			// LVL 3 GameObject subclass
			Gate.class, Seesaw.class,

			// LVL 2 GameObject subclass
			Door.class, Fence.class, Furnace.class, HidingPlace.class, Landmine.class, Matches.class,
			PressurePlate.class, PressurePlateRequiringSpecificItem.class, RemoteDoor.class, SmallHidingPlace.class,
			Storage.class, Vein.class, WallWithCrack.class, WaterBody.class,

			// LVL 1 GameObject subclass
			Bed.class, Carcass.class, Corpse.class, Discoverable.class, Food.class, GameObjectExploder.class,
			Gold.class, Inspectable.class, Key.class, Liquid.class, MapMarker.class, MeatChunk.class, MineCart.class,
			Mirror.class, Openable.class, Orb.class, Portal.class, Rail.class, Roof.class, Searchable.class,
			Stampable.class, Stump.class, Switch.class, Tree.class, VoidHole.class, Wall.class, WallSupport.class,
			WantedPoster.class, WaterSource.class, Window.class,

			// GameObject itself THE SUPERCLASS
			GameObject.class,

			// GameObject interfaces
			Consumable.class, DamageDealer.class, SwitchListener.class, UpdatesWhenSquareContentsChange.class };

	public static void save() {

		for (Class classToSave : classesToSave) {
			saveType(classToSave);
		}

	}

	private static void saveType(Class clazz) {
		ArrayList<Field> fields = null;
		ArrayList<Field> declaredFields = null;
		Statement statement = null;
		String createTableQuery = null;
		String insertQueryTemplate = null;

		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db");
			statement = conn.createStatement();

			statement.executeUpdate("DROP TABLE IF EXISTS " + clazz.getSimpleName() + ";");

			fields = new ArrayList<Field>(Arrays.asList(clazz.getFields()));
			declaredFields = new ArrayList<Field>(Arrays.asList(clazz.getDeclaredFields()));

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

			if (fields.isEmpty())
				return;

			// Make create table query and insert query template
			createTableQuery = "CREATE TABLE " + clazz.getSimpleName() + " (";
			insertQueryTemplate = "INSERT INTO " + clazz.getSimpleName() + " VALUES (";
			for (Field field : fields) {
				createTableQuery += field.getName();
				insertQueryTemplate += "?";
				if (fields.get(fields.size() - 1) != field) {
					createTableQuery += ",";
					insertQueryTemplate += ",";
				}
			}
			createTableQuery += ");";
			insertQueryTemplate += ");";

			statement.executeUpdate(createTableQuery);

			// Actually do the big ol' insert
			PreparedStatement preparedStatement = conn.prepareStatement(insertQueryTemplate);

			for (Object object : (ArrayList<?>) clazz.getField("instances").get(null)) {// GameObject.instances

				int count = 1;
				for (Field field : fields) {

					Object value = field.get(object); // THIS is the crashing line

					if (value instanceof Boolean) {
						preparedStatement.setBoolean(count, (Boolean) value);
					} else if (value instanceof Long) {
						preparedStatement.setLong(count, (Long) value);
					} else if (value instanceof Integer) {
						preparedStatement.setInt(count, (Integer) value);
					} else if (value instanceof Boolean) {
						preparedStatement.setBoolean(count, (Boolean) value);
					} else if (value instanceof String) {
						preparedStatement.setString(count, (String) value);
					} else if (value instanceof Float) {
						preparedStatement.setFloat(count, (Float) value);
					} else if (value instanceof Texture) {
						preparedStatement.setString(count, ((Texture) value).path);
						// Non-simple
					} else if (value instanceof InventorySquare) {
						preparedStatement.setLong(count, ((InventorySquare) value).id);
					} else if (value instanceof Inventory) {
						preparedStatement.setLong(count, ((Inventory) value).id);
					} else if (value instanceof Square) {
						preparedStatement.setLong(count, ((Square) value).id);
					} else if (value instanceof Quest) {
						preparedStatement.setLong(count, ((Quest) value).id);
					} else if (value instanceof GameObject[]) {
						preparedStatement.setString(count, "TODO GameObject[]");
					} else if (value instanceof GroupOfActors) {
						preparedStatement.setLong(count, ((GroupOfActors) value).id);
					} else if (value instanceof Action) {
						preparedStatement.setLong(count, ((Action) value).id);
					} else if (value instanceof Enhancement) {
						preparedStatement.setLong(count, ((Enhancement) value).id);
					} else if (value instanceof GameObject) {
						preparedStatement.setLong(count, ((GameObject) value).id);
					} else if (value instanceof HashMap<?, ?>) {
						// Highlevelstats, may need to create a class HighLevelStats, yey.
						preparedStatement.setString(count, "TODO HashMap<?, ?> class");
					} else if (value instanceof ArrayList<?>) {
						// effects array, actions this turn array
						preparedStatement.setString(count, "TODO ArrayList<?> class");
					} else if (value instanceof Object) {
						preparedStatement.setString(count, "TODO Object class " + value);
					} else if (value == null) {
						preparedStatement.setNull(count, Type.VOID);
					}

					count++;
				}

				preparedStatement.addBatch();
			}

			conn.setAutoCommit(false);
			preparedStatement.executeBatch();
			conn.setAutoCommit(true);

			ResultSet rs = statement.executeQuery("select * from " + clazz.getSimpleName() + ";");
			rs.close();
			conn.close();

		} catch (Exception e) {
			System.err.println("=======================");
			System.err.println("saveGameObjects() error");
			System.err.println("clazz = " + clazz);
			System.err.println("fields = " + fields);
			System.err.println("declaredFields = " + declaredFields);
			System.err.println("statement = " + statement);
			System.err.println("createTableQuery = " + createTableQuery);
			System.err.println("insertQueryTemplate = " + insertQueryTemplate);
			e.printStackTrace();
			System.err.println("=======================");
		}

	}

}