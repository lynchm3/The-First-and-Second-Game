package com.marklynch.ui;

import com.marklynch.utils.TextUtils;

public class ActivityLog {

	public Object[] contents;
	public float height;

	public ActivityLog(Object[] contents) {
		super();
		this.contents = contents;

		// if (contents.length == 1 && contents[0] instanceof String)
		// height = TextUtils.getDimensions((String) contents[0],
		// ActivityLogger.width)[1];
		// else
		// height = 20;
		height = TextUtils.getDimensions(contents, ActivityLogger.textWidth)[1] + 10;

		// ActivityLogger.width;
	}
}
