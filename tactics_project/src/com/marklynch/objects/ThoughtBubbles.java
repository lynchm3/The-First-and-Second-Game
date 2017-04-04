package com.marklynch.objects;

import com.marklynch.utils.ResourceUtils;

import mdesl.graphics.Texture;

public class ThoughtBubbles {

	public static Texture QUESTION_MARK;
	public static Texture MEAT_CHUNK;

	public static void loadExpressions() {

		QUESTION_MARK = ResourceUtils.getGlobalImage("question_mark.png");
		MEAT_CHUNK = ResourceUtils.getGlobalImage("meat_chunk.png");
	}

}
