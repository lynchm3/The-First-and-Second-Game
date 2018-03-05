package com.marklynch.objects;

import com.marklynch.utils.ResourceUtils;
import com.marklynch.utils.Texture;

public class ThoughtBubbles {

	public static Texture QUESTION_MARK;
	public static Texture MEAT_CHUNK;
	public static Texture JUSTICE;
	public static Texture ANGRY;

	public static void loadExpressions() {

		QUESTION_MARK = ResourceUtils.getGlobalImage("question_mark.png", false);
		MEAT_CHUNK = ResourceUtils.getGlobalImage("meat_chunk.png", false);
		JUSTICE = ResourceUtils.getGlobalImage("expression_justice.png", false);
		ANGRY = ResourceUtils.getGlobalImage("expression_angry.png", false);

	}

}
