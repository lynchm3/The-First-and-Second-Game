package com.marklynch.level.constructs.decoration;

import com.marklynch.utils.ResourceUtils;
import com.marklynch.utils.Texture;

public class Decoration {

	public static Texture cloudTexture;

	public static void loadStaticImages() {
		cloudTexture = ResourceUtils.getGlobalImage("cloud.png", false);
	}

	// public void draw1() {
	//
	// }
	//
	// public void draw2() {
	//
	// }

	public void draw3() {

	}

	// public void update(int delta) {
	//
	// }

	public void updateRealtime(int delta) {

	}

}
