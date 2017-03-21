package com.marklynch.level;

import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings("serial")
public class ArrayListMappedInanimateObjects<GameObject> extends ArrayList<GameObject> {

	HashMap<Class, ArrayList<GameObject>> hashMap = new HashMap<Class, ArrayList<GameObject>>();

	public ArrayList<GameObject> get(Class clazz) {
		ArrayList<GameObject> gameObjects = hashMap.get(clazz);
		if (gameObjects == null) {
			gameObjects = new ArrayList<GameObject>();
			hashMap.put(clazz, gameObjects);
		}
		return gameObjects;
	}

	@Override
	public boolean add(GameObject gameObject) {
		if (!this.contains(gameObject)) {
			ArrayList<GameObject> arrayList = hashMap.get(gameObject.getClass());
			if (arrayList == null) {
				arrayList = new ArrayList<GameObject>();
				arrayList.add(gameObject);
				hashMap.put(gameObject.getClass(), arrayList);
			} else {
				arrayList.add(gameObject);
			}
			hashMap.get(gameObject.getClass()).size();
			return super.add(gameObject);
		}
		return false;
	}

	@Override
	public boolean remove(Object gameObject) {
		if (this.contains(gameObject)) {
			ArrayList<GameObject> arrayList = hashMap.get(gameObject.getClass());
			arrayList.remove(gameObject);
			return super.remove(gameObject);
		}
		return false;
	}

}
