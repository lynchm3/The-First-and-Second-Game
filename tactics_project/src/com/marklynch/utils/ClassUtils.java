package com.marklynch.utils;

import java.lang.reflect.Field;

public class ClassUtils {

	public static boolean classContainsField(Class klass, String fieldName) {
		for (Field classField : klass.getFields()) {
			if (classField.getName().equals(fieldName)) {
				return true;
			}
		}
		return false;
	}
}
