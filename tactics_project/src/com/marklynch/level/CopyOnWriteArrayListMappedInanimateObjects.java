package com.marklynch.level;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("serial")
public class CopyOnWriteArrayListMappedInanimateObjects<GameObject> extends CopyOnWriteArrayList<GameObject> {

	ConcurrentHashMap<Class, CopyOnWriteArrayList<GameObject>> ConcurrentHashMap = new ConcurrentHashMap<Class, CopyOnWriteArrayList<GameObject>>();

	public CopyOnWriteArrayList<GameObject> get(Class clazz) {
		CopyOnWriteArrayList<GameObject> gameObjects = ConcurrentHashMap.get(clazz);
		if (gameObjects == null) {
			gameObjects = new CopyOnWriteArrayList<GameObject>();
			ConcurrentHashMap.put(clazz, gameObjects);
		}
		return gameObjects;
	}

	@Override
	public boolean add(GameObject gameObject) {
		if (!this.contains(gameObject)) {
			CopyOnWriteArrayList<GameObject> arrayList = ConcurrentHashMap.get(gameObject.getClass());
			if (arrayList == null) {
				arrayList = new CopyOnWriteArrayList<GameObject>();
				arrayList.add(gameObject);
				ConcurrentHashMap.put(gameObject.getClass(), arrayList);
			} else {
				arrayList.add(gameObject);
			}
			ConcurrentHashMap.get(gameObject.getClass()).size();
			return super.add(gameObject);
		}
		return false;
	}

	@Override
	public boolean remove(Object gameObject) {
		if (this.contains(gameObject)) {
			CopyOnWriteArrayList<GameObject> arrayList = ConcurrentHashMap.get(gameObject.getClass());
			arrayList.remove(gameObject);
			return super.remove(gameObject);
		}
		return false;
	}

}
