package com.marklynch.objects.actions;

import com.marklynch.utils.Texture;

public abstract class VariableQtyAction extends Action {

	public int qty = 1;

	public VariableQtyAction(String actionName) {
		super(actionName);
	}

	public VariableQtyAction(String actionName, Texture image) {
		super(actionName, image);
	}

}
