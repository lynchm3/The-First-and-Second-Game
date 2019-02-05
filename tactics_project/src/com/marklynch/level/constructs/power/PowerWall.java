package com.marklynch.level.constructs.power;

import org.lwjgl.util.Point;

import com.marklynch.actions.Action;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.templates.Templates;
import com.marklynch.utils.ResourceUtils;

public class PowerWall extends Power {

	private static String NAME = "Wall";

	public PowerWall() {
		this(null);
	}

	public PowerWall(GameObject source) {
		super(NAME, ResourceUtils.getGlobalImage("wall_cave.png", false), source, new Effect[] {}, 5, null,
				new Point[] { new Point(0, 0) }, 10, true, true, Crime.TYPE.CRIME_ASSAULT);
		selectTarget = true;
	}

	@Override
	public Power makeCopy(GameObject source) {
		return new PowerWall(source);
	}

	@Override
	public void cast(final GameObject source, GameObject targetGameObject, Square targetSquare, final Action action) {
//		source.setPrimaryAnimation(new AnimationPush(source, targetSquare, source.getPrimaryAnimation(), null));
//		super.cast(source, targetGameObject, targetSquare, action);

		targetSquare.inventory.add(Templates.VEIN.makeCopy(targetSquare, null, false, Templates.ORE, 0.1f));

	}

	@Override
	public boolean check(GameObject source, Square targetSquare) {
		if (targetSquare.inventory.canShareSquare)
			return true;

		disabledReason = "Theres a " + targetSquare.inventory.gameObjectThatCantShareSquare.name + " there!";
		return false;

	}

}
