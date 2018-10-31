package com.marklynch.objects.actions;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.utils.Texture;

public abstract class VariableQtyAction extends Action {

	public int qty = 1;

	public VariableQtyAction(String actionName) {
		super(actionName);
	}

	public VariableQtyAction(String actionName, Texture image, GameObject gameObjectPerformer,
			GameObject targetGameObject, Square targetSquare) {
		super(actionName, image, gameObjectPerformer, targetGameObject, targetSquare);
	}

}
