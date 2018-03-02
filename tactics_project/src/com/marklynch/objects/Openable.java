package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.Switch.SWITCH_TYPE;
import com.marklynch.objects.actions.ActionClose;
import com.marklynch.objects.actions.ActionLock;
import com.marklynch.objects.actions.ActionOpen;
import com.marklynch.objects.actions.ActionUnlock;
import com.marklynch.objects.units.Actor;
import com.marklynch.utils.Utils;

public abstract class Openable extends GameObject implements SwitchListener {
	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();

	public boolean open = false;
	public Key[] keys;
	public boolean locked = false;
	public String baseName;

	public Openable() {
		super();

	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	public boolean isOpen() {
		return open;
	}

	public abstract void open();

	public abstract void close();

	public void lock() {
		this.name = baseName + " (locked)";
		if (this.squareGameObjectIsOn == Game.level.squares[105][12]) {
			System.out.println("this.name c = " + this.name);
			Utils.printStackTrace();
		}
		locked = true;
	}

	public void unlock() {
		this.name = baseName;
		locked = false;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setAttributesForCopy(Openable openable, Square square, boolean locked, Actor owner, Key... keys) {

		super.setAttributesForCopy(openable, square, owner);
		openable.keys = keys;
		openable.locked = locked;

		if (square == Game.level.squares[105][12]) {
			System.out.println("locked = " + locked);
			System.out.println("this.name = " + this.name);
			System.out.println("openable.baseName = " + openable.baseName);
			System.out.println("baseName = " + baseName);
		}

		openable.baseName = this.baseName;
		openable.name = openable.baseName;

		if (square == Game.level.squares[105][12]) {
			System.out.println("openable.name a = " + this.name);
		}
		if (openable.locked)
			openable.name = openable.baseName + " (locked)";

		if (square == Game.level.squares[105][12]) {
			System.out.println("openable.name b = " + this.name);
		}

	}

	@Override
	public void zwitch(Switch zwitch) {
		if (zwitch.switchType == SWITCH_TYPE.OPEN_CLOSE) {
			if (open) {
				new ActionClose(zwitch, this).perform();
			} else {
				new ActionOpen(zwitch, this).perform();
			}
		} else if (zwitch.switchType == SWITCH_TYPE.LOCK_UNLOCK) {
			if (locked) {
				new ActionUnlock(zwitch, this).perform();
			} else {
				new ActionLock(zwitch, this).perform();
			}
		}

	}

}
