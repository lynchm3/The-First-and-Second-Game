package com.marklynch.ui;

import java.util.ArrayList;

import com.marklynch.objects.GameObject;
import com.marklynch.ui.button.Link;
import com.marklynch.utils.TextUtils;

public class ActivityLog {

	public ArrayList<Object> cleanContents = new ArrayList<Object>();
	public float height;
	public ArrayList<Link> links;

	public ActivityLog(Object[] contents) {
		super();

		for (Object object : contents) {
			if (object instanceof GameObject) {
				cleanContents.add(((GameObject) object).name);
				cleanContents.add(((GameObject) object).imageTexture);
			} else {
				cleanContents.add(object);
			}
		}

		height = TextUtils.getDimensions(contents, ActivityLogger.textWidth)[1] + 10;
		links = TextUtils.getLinks(contents);
		// this.contents = contents;
	}
}
