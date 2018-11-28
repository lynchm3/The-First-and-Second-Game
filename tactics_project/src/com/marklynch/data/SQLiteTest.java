package com.marklynch.data;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
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
import com.marklynch.level.constructs.Stat;
import com.marklynch.level.constructs.Stat.HIGH_LEVEL_STATS;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.constructs.enchantment.Enhancement;
import com.marklynch.level.constructs.inventory.Inventory;
import com.marklynch.level.constructs.inventory.InventorySquare;
import com.marklynch.level.quest.Quest;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.HidingPlace;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.units.Actor;

public class SQLiteTest {

	// When you decide to save
	// 1. turn on pause mode (if not already) - Show spinner w/ "Ending Turn"
	// 2. end the turn - Show spinner w/ "Ending Turn"
	// 3. end all animations - Show spinner w/ "Ending Turn"
	// 3. run save

	// To be calculated after loading
	// public float halfHeight;
	// public float halfWidth;

	@Target({ ElementType.FIELD })
	@Retention(RetentionPolicy.RUNTIME)
	public @interface saved {
	}

	public final static String tblGameObject = "GameObject";

	public static void saveGameObjects() {

		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db");
			Statement stat = conn.createStatement();

			stat.executeUpdate("DROP TABLE IF EXISTS " + tblGameObject + ";");

			ArrayList<Field> gameObjectFields = new ArrayList<Field>(Arrays.asList(GameObject.class.getFields()));

			// Remove transient and static fields, don't want to save them
			for (Field gameObjectField : (ArrayList<Field>) gameObjectFields.clone()) {
				if (Modifier.isTransient(gameObjectField.getModifiers())
						|| Modifier.isStatic(gameObjectField.getModifiers())) {
					gameObjectFields.remove(gameObjectField);
				}
			}

			// Make create table query

			String createTableQuery = "CREATE TABLE gameobject (";
			for (Field gameObjectField : gameObjectFields) {
				createTableQuery += gameObjectField.getName();
				if (gameObjectFields.get(gameObjectFields.size() - 1) != gameObjectField) {
					createTableQuery += ",";
				}
			}
			createTableQuery += ");";
			stat.executeUpdate(createTableQuery);
			// stat.executeUpdate("CREATE TABLE gameobject (id, name);");

			PreparedStatement prep = conn.prepareStatement("insert into " + tblGameObject + " values (?, ?);");
			for (GameObject gameObject : GameObject.instances) {

				// for(Attribute)
				for (Field gameObjectField : gameObjectFields) {

				}

				prep.setLong(1, gameObject.id);
				prep.setString(2, gameObject.name);
				prep.addBatch();
			}

			conn.setAutoCommit(false);
			prep.executeBatch();
			conn.setAutoCommit(true);

			ResultSet rs = stat.executeQuery("select * from " + tblGameObject + ";");
			while (rs.next()) {
				System.out.println("id = " + rs.getLong("id"));
				System.out.println("name = " + rs.getString("name"));
			}
			rs.close();
			conn.close();

		} catch (Exception e) {
			System.err.println("saveGameObjects() error");
			e.printStackTrace();
		}

	}

	public static void main(String[] args) throws Exception {
		saveGameObjects();
	}

	public void saveLongValue(PreparedStatement preparedStatement, GameObject gameObject, String attributeName) {

	}

	public void saveIntValue(PreparedStatement preparedStatement, GameObject gameObject, String attributeName) {

	}

	public void saveStringValue(PreparedStatement preparedStatement, GameObject gameObject, String attributeName) {

	}

	public void saveSquareValue(PreparedStatement preparedStatement, GameObject gameObject, String attributeName) {

	}

	public void saveInventroySquareValue(PreparedStatement preparedStatement, GameObject gameObject,
			String attributeName) {

	}

	public void saveBooleanValue(PreparedStatement preparedStatement, GameObject gameObject, String attributeName) {

	}

	public void saveFloatValue(PreparedStatement preparedStatement, GameObject gameObject, String attributeName) {

	}

	public void saveQuestValue(PreparedStatement preparedStatement, GameObject gameObject, String attributeName) {

	}

	public void saveActorValue(PreparedStatement preparedStatement, GameObject gameObject, String attributeName) {

	}

	public void saveHidingPlaceValue(PreparedStatement preparedStatement, GameObject gameObject, String attributeName) {

	}

	public void saveGameObjectArrayValue(PreparedStatement preparedStatement, GameObject gameObject,
			String attributeName) {

	}

	public void saveGroupValue(PreparedStatement preparedStatement, GameObject gameObject, String attributeName) {

	}

	public void saveObjectValue(PreparedStatement preparedStatement, GameObject gameObject, String attributeName) {

	}

	public void saveActionValue(PreparedStatement preparedStatement, GameObject gameObject, String attributeName) {

	}

	public void saveEnhancementValue(PreparedStatement preparedStatement, GameObject gameObject, String attributeName) {

	}

	public void saveHIGH_LEVEL_STATSValue(PreparedStatement preparedStatement, GameObject gameObject,
			String attributeName) {

	}

	public void saveEffectsArrayValue(PreparedStatement preparedStatement, GameObject gameObject,
			String attributeName) {

	}

	public void saveActionsArrayValue(PreparedStatement preparedStatement, GameObject gameObject,
			String attributeName) {

	}

	public long id;
	public int templateId;
	public String name = "";
	public int totalHealth = 0;
	public String imageTexturePath = null; // public transient Texture imageTexture = null;
	public Square squareGameObjectIsOn = null;
	public Square lastSquare = null;
	public InventorySquare inventorySquare = null;
	public Inventory inventory = new Inventory();
	public boolean showInventoryInGroundDisplay = false;;
	public boolean canShareSquare = true;
	public boolean fitsInInventory = true;
	public boolean canContainOtherObjects = false;
	public boolean blocksLineOfSight = false;
	public boolean persistsWhenCantBeSeen = false;
	public boolean attackable = true;
	public boolean moveable = true;
	public boolean canBePickedUp = true;
	public boolean decorative = false;
	public boolean floatsInWater = false;
	public boolean isFloorObject = false;
	public int value = 1;
	public int turnAcquired = 1;
	public float widthRatio = 1;
	public float heightRatio = 1;
	public float drawOffsetRatioX = 0;
	public float drawOffsetRatioY = 0;
	public float soundWhenHit = 1;
	public float soundWhenHitting = 1;
	public float soundDampening = 1;
	public float lightHandleX;
	public float lightHandlY;
	public boolean stackable = false;
	public float weight;
	public int remainingHealth = 1;
	public boolean favourite = false;
	public Inventory inventoryThatHoldsThisObject;
	public Quest quest;
	public transient Actor owner;
	public float height;
	public float width;
	public float anchorX, anchorY;
	public boolean backwards = false;
	public boolean hiding = false;
	public HidingPlace hidingPlace = null;
	public ArrayList<GameObject> attackers = new ArrayList<GameObject>();
	public Group group;
	public Object destroyedBy = null;
	public Action destroyedByAction = null;
	public boolean toSell = false;
	public boolean starred = false;
	public boolean flash = false;
	public float minRange = 1;
	public float maxRange = 1;
	public Enhancement enhancement;
	public int level = 1;
	public int thoughtsOnPlayer = 0;
	public boolean diggable = false;
	public boolean flipYAxisInMirror = true;
	public Actor beingFishedBy = null;
	public boolean fightingFishingRod = false;
	public float swimmingChangeX = 0;
	public float swimmingChangeY = 0;
	public GameObject fishingTarget;
	public GameObject equipped = null;
	public boolean bigShadow = false;
	public int orderingOnGound = 100;
	public String type = "Object";
	public int lastTurnThisWasMovedByMinecart = -1;
	public HashMap<HIGH_LEVEL_STATS, Stat> highLevelStats = new HashMap<HIGH_LEVEL_STATS, Stat>();
	public ArrayList<Effect> activeEffectsOnGameObject = new ArrayList<Effect>();
	public ArrayList<Action> actionsPerformedThisTurn = new ArrayList<Action>();

}