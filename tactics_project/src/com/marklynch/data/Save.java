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

import com.marklynch.level.constructs.Group;
import com.marklynch.level.constructs.enchantment.Enhancement;
import com.marklynch.level.constructs.inventory.InventorySquare;
import com.marklynch.level.quest.Quest;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.HidingPlace;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.units.Actor;
import com.marklynch.utils.Texture;

public class Save {

	// When you decide to save
	// 1. turn on pause mode (if not already) - Show spinner w/ "Ending Turn"
	// 2. end the turn - Show spinner w/ "Ending Turn"
	// 3. end all animations - Show spinner w/ "Ending Turn"
	// 3. run save

	public static void save() {
		saveType(GameObject.class);
		// saveType(Door.class);
	}

	private static void saveType(Class clazz) {

		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db");
			Statement stat = conn.createStatement();

			System.out.println("saveType clazz.getSimpleName = " + clazz.getSimpleName());
			stat.executeUpdate("DROP TABLE IF EXISTS " + clazz.getSimpleName() + ";");

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

			// Make create table query and insert query template
			String createTableQuery = "CREATE TABLE " + clazz.getSimpleName() + " (";
			String insertQueryTemplate = "INSERT INTO " + clazz.getSimpleName() + " VALUES (";
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

			stat.executeUpdate(createTableQuery);

			// Actually do the big ol' insert
			PreparedStatement preparedStatement = conn.prepareStatement(insertQueryTemplate);
			for (Object object : (ArrayList<?>) clazz.getField("instances").get(null)) {// GameObject.instances

				int count = 1;
				for (Field field : fields) {

					Object value = field.get(object);

					// System.out.println("Adding " + field.getName() + " @ " + count);
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
					} else if (value instanceof Square) {
						preparedStatement.setLong(count, ((Square) value).id);
					} else if (value instanceof InventorySquare) {
						preparedStatement.setString(count, "TODO InventroySquare class");
					} else if (value instanceof Actor) {
						preparedStatement.setString(count, "TODO Actor class");
					} else if (value instanceof HidingPlace) {
						preparedStatement.setString(count, "TODO HidingPlace class");
					} else if (value instanceof Float) {
						preparedStatement.setFloat(count, (Float) value);
					} else if (value instanceof Quest) {
						preparedStatement.setLong(count, ((Quest) value).id);
					} else if (value instanceof GameObject[]) {
						preparedStatement.setString(count, "TODO GameObject[]");
					} else if (value instanceof Group) {
						preparedStatement.setLong(count, ((Group) value).id);
					} else if (value instanceof Action) {
						preparedStatement.setLong(count, ((Action) value).id);
					} else if (value instanceof Enhancement) {
						preparedStatement.setLong(count, ((Enhancement) value).id);
					} else if (value instanceof HashMap<?, ?>) {
						// Highlevelstats, may need to create a class HighLevelStats, yey.
						preparedStatement.setString(count, "TODO HashMap<?, ?> class");
					} else if (value instanceof ArrayList<?>) {
						// effects array, actions this turn array
						preparedStatement.setString(count, "TODO ArrayList<?> class");
					} else if (value instanceof Texture) {
						preparedStatement.setString(count, ((Texture) value).path);
					} else if (value instanceof Object) {
						preparedStatement.setString(count, "TODO Object class");
					} else if (value == null) {
						preparedStatement.setInt(count, 0);
					} else {
						// System.out.println("FAILED TO ADD");
					}

					count++;
				}

				// System.out.println("preparedStatement.toString() = " +
				// preparedStatement.toString());
				// System.out.println("count = " + count);

				preparedStatement.addBatch();
			}

			conn.setAutoCommit(false);
			preparedStatement.executeBatch();
			conn.setAutoCommit(true);

			ResultSet rs = stat.executeQuery("select * from " + clazz.getSimpleName() + ";");

			// while (rs.next()) {
			// System.out.println("id = " + rs.getLong("id"));
			// System.out.println("name = " + rs.getString("name"));
			// }
			rs.close();
			conn.close();

		} catch (Exception e) {
			System.err.println("saveGameObjects() error");
			e.printStackTrace();
		}

	}

}