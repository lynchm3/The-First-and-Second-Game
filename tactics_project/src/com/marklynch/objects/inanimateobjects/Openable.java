package com.marklynch.objects.inanimateobjects;

import com.marklynch.actions.ActionClose;
import com.marklynch.actions.ActionLock;
import com.marklynch.actions.ActionOpen;
import com.marklynch.actions.ActionUnlock;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.Switch.SWITCH_TYPE;
import com.marklynch.objects.utils.SwitchListener;
import com.marklynch.utils.ArrayList;

public abstract class Openable extends GameObject implements SwitchListener {
	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>(GameObject.class);

	public boolean open = false;
	public Key[] keys;
	public boolean locked = false;
	public String baseName;
	public boolean isOpenable = true;
	public boolean lockable = true;

	public Openable() {
		super();
		type = "Openable";

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

		openable.isOpenable = isOpenable;
		openable.lockable = lockable;
		openable.keys = keys;
		for (Key key : keys) {
			openable.linkedObjects.add(key);
			key.linkedObjects.add(openable);
		}
		openable.locked = locked;

		openable.baseName = this.baseName;
		openable.name = openable.baseName;
		if (openable.locked)
			openable.name = openable.baseName + " (locked)";

	}

	@Override
	public void zwitch(Switch zwitch) {
		if (zwitch.switchType == SWITCH_TYPE.OPEN_CLOSE) {
			if (isOpenable) {
				if (open) {
					new ActionClose(zwitch, this).perform();
				} else {
					new ActionOpen(zwitch, this).perform();
				}
			}
		} else if (zwitch.switchType == SWITCH_TYPE.LOCK_UNLOCK) {
			if (lockable) {
				if (locked) {
					new ActionUnlock(zwitch, this).perform();
				} else {
					new ActionLock(zwitch, this).perform();
				}
			}
		}

	}

}
