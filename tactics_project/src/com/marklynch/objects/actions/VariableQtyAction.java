package com.marklynch.objects.actions;

public abstract class VariableQtyAction extends Action {

	public int variable = 1;

	public VariableQtyAction(String actionName) {
		super(actionName);
	}

	public VariableQtyAction(String actionName, String imageName) {
		super(actionName, imageName);
	}

}
