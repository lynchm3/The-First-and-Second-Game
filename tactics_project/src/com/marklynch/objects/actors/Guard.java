package com.marklynch.objects.actors;

import java.util.ArrayList;

import com.marklynch.ai.routines.AIRoutineForGuard;
import com.marklynch.level.constructs.Faction;
import com.marklynch.level.constructs.area.Area;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.armor.BodyArmor;
import com.marklynch.objects.armor.LegArmor;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.templates.Templates;

public class Guard extends Human {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();

	public Shift shift;
	public Square[] patrolSquares;

	public Guard() {
		super();
		type = "Guard";
	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	@Override
	public void postLoad1() {
		super.postLoad1();
		aiRoutine = new AIRoutineForGuard(this);
	}

	@Override
	public void postLoad2() {
		super.postLoad2();
	}

	public Guard makeCopy(String name, Square square, Faction faction, GameObject bed, int gold, GameObject[] mustHaves,
			GameObject[] mightHaves, Area area, int[] requiredEquipmentTemplateIds, HOBBY[] hobbies, Shift shift,
			Square... patrolSquares) {
		Guard actor = new Guard();
		setInstances(actor);
		super.setAttributesForCopy(name, actor, square, faction, bed, gold, mustHaves, mightHaves, area);
		actor.shift = shift;
		actor.patrolSquares = patrolSquares;
		actor.hobbies = hobbies;

		BodyArmor chainmail = Templates.CHAINMAIL.makeCopy(null, null);
		actor.inventory.add(chainmail);
		actor.bodyArmor = chainmail;

		LegArmor pants = Templates.PANTS.makeCopy(null, null);
		actor.inventory.add(pants);
		actor.legArmor = pants;

		return actor;
	}

	public static final Shift morningShift = new Shift(6, 14, 22, 6);
	public static final Shift dayShift = new Shift(14, 22, 22, 6);
	public static final Shift nightShift = new Shift(22, 6, 6, 14);

	public static class Shift {

		public final int workStart;
		public final int workEnd;
		public final int sleepStart;
		public final int sleepEnd;

		public Shift(int workStart, int workEnd, int sleepStart, int sleeptEnd) {
			super();
			this.workStart = workStart;
			this.workEnd = workEnd;
			this.sleepStart = sleepStart;
			this.sleepEnd = sleeptEnd;
		}
	}

}
