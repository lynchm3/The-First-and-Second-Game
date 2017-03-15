package com.marklynch.objects;

import com.marklynch.utils.ResourceUtils;

import mdesl.graphics.Texture;

public class Expressions {

	public static Texture questionMark;

	public static void loadExpressions() {

		questionMark = ResourceUtils.getGlobalImage("question_mark.png");
	}

}
