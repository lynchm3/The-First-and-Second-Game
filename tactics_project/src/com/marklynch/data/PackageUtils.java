package com.marklynch.data;

import java.io.File;
import java.net.URL;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.Enumeration;

public class PackageUtils {

	public static CopyOnWriteArrayList<Class<?>> getClasses(String packageName) {
		CopyOnWriteArrayList<Class<?>> classes = new CopyOnWriteArrayList<Class<?>>();
		try {

			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			assert classLoader != null;
			String path = packageName.replace('.', '/');
			Enumeration<URL> resources = classLoader.getResources(path);
			CopyOnWriteArrayList<File> dirs = new CopyOnWriteArrayList<File>();
			while (resources.hasMoreElements()) {
				URL resource = resources.nextElement();
				dirs.add(new File(resource.getFile()));
			}
			for (File directory : dirs) {
				classes.addAll(findClasses(directory, packageName));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return classes;
	}

	public static CopyOnWriteArrayList<Class<?>> findClasses(File directory, String packageName) {
		CopyOnWriteArrayList<Class<?>> classes = new CopyOnWriteArrayList<Class<?>>();
		try {
			if (!directory.exists()) {
				return classes;
			}
			File[] files = directory.listFiles();
			for (File file : files) {
				if (file.isDirectory()) {
					assert !file.getName().contains(".");
					classes.addAll(findClasses(file, packageName + "." + file.getName()));
				} else if (file.getName().endsWith(".class")) {
					Class<?> classToAdd = Class
							.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6));
					if (classToAdd.getEnclosingClass() == null)
						classes.add(classToAdd);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return classes;
	}
}
