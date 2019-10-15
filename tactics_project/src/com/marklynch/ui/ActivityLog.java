package com.marklynch.ui;

import java.util.concurrent.CopyOnWriteArrayList;

import com.marklynch.ui.button.Link;
import com.marklynch.utils.TextUtils;

public class ActivityLog {

	public Object[] contents;
	public float height;
	public CopyOnWriteArrayList<Link> links;

	public ActivityLog(Object[] contents) {
		super();
		this.contents = contents;
		height = TextUtils.getDimensions(contents, ActivityLogger.textWidth)[1] + 10;
		links = TextUtils.getLinks(contents);
	}
}
