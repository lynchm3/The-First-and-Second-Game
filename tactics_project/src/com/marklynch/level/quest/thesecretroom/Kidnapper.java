package com.marklynch.level.quest.thesecretroom;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.constructs.Faction;
import com.marklynch.level.constructs.area.Area;
import com.marklynch.level.constructs.bounds.structure.StructureRoom;
import com.marklynch.level.constructs.bounds.structure.StructureSection;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.tools.Bell;
import com.marklynch.objects.units.Actor;

public class Kidnapper extends Actor {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();

	public StructureRoom mortsMine;
	public StructureSection mortsRooms;
	public StructureRoom mortsRoom;
	public StructureRoom mortsVault;
	public static Square mortsStandingSpot = Game.level.squares[281][41];
	public GameObject mortsMeatChunk;
	public Bell mortsBell;
	public boolean performingFeedingDemo = false;
	public QuestTheSecretRoom questTheSecretRoom;
	public Square mortsRoomDoorway;
	public Square mortsVaultDoorway;

	public Kidnapper() {
		super();
		aiRoutine = new AIRoutineForKidnapper(this);
	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	@Override
	public void postLoad1() {
		super.postLoad1();
		aiRoutine = new AIRoutineForKidnapper(this);
	}

	@Override
	public void postLoad2() {
		super.postLoad2();
	}

	public Kidnapper makeCopy(String name, Square square, Faction faction, GameObject bed, int gold, GameObject[] mustHaves,
			GameObject[] mightHaves, Area area) {
		Kidnapper actor = new Kidnapper();
		setInstances(actor);
		super.setAttributesForCopy(name, actor, square, faction, bed, gold, mustHaves, mightHaves, area);

		return actor;
	}

}
