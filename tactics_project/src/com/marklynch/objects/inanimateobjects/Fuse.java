package com.marklynch.objects.inanimateobjects;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.constructs.effect.EffectBurn;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.actors.Actor.Direction;
import com.marklynch.objects.utils.SwitchListener;
import com.marklynch.objects.utils.UpdatableGameObject;
import com.marklynch.ui.ActivityLog;
import com.marklynch.utils.ArrayList;
import com.marklynch.utils.Texture;

public class Fuse extends Line implements SwitchListener, UpdatableGameObject {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>(GameObject.class);

	public static Texture imageTextureLeftRightStatic;
	public static Texture imageTextureUpDownStatic;
	public static Texture imageTextureLeftUpStatic;
	public static Texture imageTextureRightUpStatic;
	public static Texture imageTextureLeftDownStatic;
	public static Texture imageTextureRightDownStatic;

	// Lit ends
	public static Texture imageTextureLeftLitEndStatic;
	public static Texture imageTextureRightLitEndStatic;
	public static Texture imageTextureUpLitEndStatic;
	public static Texture imageTextureDownLitEndStatic;

	// Lightable ends
	public static Texture imageTextureLeftLightableEndStatic;
	public static Texture imageTextureRightLightableEndStatic;
	public static Texture imageTextureUpLightableEndStatic;
	public static Texture imageTextureDownLightableEndStatic;

	// Non-lightable ends
	public static Texture imageTextureLeftNonLightableEndStatic;
	public static Texture imageTextureRightNonLightableEndStatic;
	public static Texture imageTextureUpNonLightableEndStatic;
	public static Texture imageTextureDownNonLightableEndStatic;

	private boolean lightable = false;
	private boolean lit = false;
	public ArrayList<GameObject> gameObjectsToExplode = new ArrayList<GameObject>(GameObject.class);

	private int turnLit = -1;

	public Fuse() {
		super();

		canBePickedUp = false;
		fitsInInventory = false;
		persistsWhenCantBeSeen = true;
		attackable = false;
		isFloorObject = true;
		moveable = false;
		orderingOnGound = 21;
		type = "Fuse";

		super.imageTextureLeftRight = imageTextureLeftRightStatic;
		super.imageTextureUpDown = imageTextureUpDownStatic;
		super.imageTextureLeftUp = imageTextureLeftUpStatic;
		super.imageTextureRightUp = imageTextureRightUpStatic;
		super.imageTextureLeftDown = imageTextureLeftDownStatic;
		super.imageTextureRightDown = imageTextureRightDownStatic;

		super.imageTextureLeftEnd = imageTextureLeftNonLightableEndStatic;
		super.imageTextureRightEnd = imageTextureRightNonLightableEndStatic;
		super.imageTextureUpEnd = imageTextureUpNonLightableEndStatic;
		super.imageTextureDownEnd = imageTextureDownNonLightableEndStatic;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	public Fuse makeCopy(Square square, Actor owner, Direction direction1, Direction direction2, boolean lightable) {
		Fuse fuse = new Fuse();
		setInstances(fuse);
		super.setAttributesForCopy(fuse, square, owner);
		fuse.direction1 = direction1;
		fuse.direction2 = direction2;
		fuse.lightable = lightable;

		fuse.updateImageTextures();

		fuse.updateNeighborLines();

		fuse.updateEndTextures();

		return fuse;
	}

	@Override
	public void zwitch(Switch zwitch) {
	}

	public void setLightable(boolean lightable) {
		this.lightable = lightable;
		updateEndTextures();
	}

	public void setLit(boolean lit) {
		this.lit = lit;
		this.turnLit = Level.turn;
		updateEndTextures();
	}

	public void updateEndTextures() {
		if (lit) {
			super.imageTextureLeftEnd = imageTextureLeftLitEndStatic;
			super.imageTextureRightEnd = imageTextureRightLitEndStatic;
			super.imageTextureUpEnd = imageTextureUpLitEndStatic;
			super.imageTextureDownEnd = imageTextureDownLitEndStatic;

		} else if (lightable) {
			super.imageTextureLeftEnd = imageTextureLeftLightableEndStatic;
			super.imageTextureRightEnd = imageTextureRightLightableEndStatic;
			super.imageTextureUpEnd = imageTextureUpLightableEndStatic;
			super.imageTextureDownEnd = imageTextureDownLightableEndStatic;
		} else {
			super.imageTextureLeftEnd = imageTextureLeftNonLightableEndStatic;
			super.imageTextureRightEnd = imageTextureRightNonLightableEndStatic;
			super.imageTextureUpEnd = imageTextureUpNonLightableEndStatic;
			super.imageTextureDownEnd = imageTextureDownNonLightableEndStatic;
		}

	}

	@Override
	public void addEffect(Effect effectToAdd) {
		if (remainingHealth <= 0)
			return;

		if (effectToAdd instanceof EffectBurn && this.lightable && !this.lit) {

			if (Level.shouldLog(this))
				Game.level.logOnScreen(new ActivityLog(new Object[] { this, " was ignited" }));

			this.setLit(true);
		}
	}

	@Override
	public void update() {
		if (lit && this.turnLit != Level.turn && this.squareGameObjectIsOn != null) {
			ArrayList<Square> connectedSquares = new ArrayList<Square>(Square.class);
			connectedSquares.add(this.getSquareInDirection(direction1));
			connectedSquares.add(this.getSquareInDirection(direction2));

			for (Square neighborSquare : connectedSquares) {
				Fuse fuse = (Fuse) neighborSquare.inventory.getGameObjectWithTemplateId(this.templateId);
				if (fuse != null)
					fuse.setLit(true);
			}

			for (GameObject gameObject : this.gameObjectsToExplode) {
				gameObject.changeHealthSafetyOff(-gameObject.remainingHealth, null, null);
			}

			this.squareGameObjectIsOn.inventory.remove(this);
			this.updateNeighborLines();
		}

	}

}
