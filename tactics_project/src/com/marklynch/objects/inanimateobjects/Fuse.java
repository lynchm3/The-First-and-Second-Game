package com.marklynch.objects.inanimateobjects;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.animation.primary.AnimationFuse;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.constructs.effect.EffectBurn;
import com.marklynch.level.constructs.effect.EffectWet;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.actors.Actor.Direction;
import com.marklynch.objects.utils.SwitchListener;
import com.marklynch.objects.utils.UpdatableGameObject;
import com.marklynch.ui.ActivityLog;
import com.marklynch.utils.CopyOnWriteArrayList;
import com.marklynch.utils.Texture;
import com.marklynch.utils.TextureUtils;

public class Fuse extends Line implements SwitchListener, UpdatableGameObject {

	public static final CopyOnWriteArrayList<GameObject> instances = new CopyOnWriteArrayList<GameObject>(GameObject.class);

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
	public CopyOnWriteArrayList<GameObject> gameObjectsToExplode = new CopyOnWriteArrayList<GameObject>(GameObject.class);

	private int turnLit = -1;

	public Direction connectedToExplosiveDirection = null;

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

		fuse.updateNeighborLines(square);

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

	public void updateNeighborLines(Square square) {
		super.updateNeighborLines(square);

		for (Direction direction : Direction.values()) {
			Square neighborSquare = this.getSquareInDirection(direction, square);
			if (neighborSquare == null)
				continue;
			Fuse neighborFuse = (Fuse) neighborSquare.inventory.getGameObjectWithTemplateId(this.templateId);
			if (neighborFuse == null)
				continue;
			neighborFuse.updateEndTextures();
		}
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

		if (connectedToExplosiveDirection != null) {
			if (connectedToExplosiveDirection == Direction.RIGHT) {
				super.imageTextureRightEnd = imageTextureRightNonLightableEndStatic;
			} else if (connectedToExplosiveDirection == Direction.LEFT) {
				super.imageTextureLeftEnd = imageTextureLeftNonLightableEndStatic;
			} else if (connectedToExplosiveDirection == Direction.UP) {
				super.imageTextureUpEnd = imageTextureUpNonLightableEndStatic;
			} else if (connectedToExplosiveDirection == Direction.DOWN) {
				super.imageTextureDownEnd = imageTextureDownNonLightableEndStatic;

			}
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
		} else if (effectToAdd instanceof EffectWet && this.lit) {
			this.setLit(false);
		}
	}

	public boolean draw1() {

		boolean shouldDraw = super.draw1();
		if (shouldDraw && lit && primaryAnimation == null) {

			float offSetX = 34;
			float offSetY = 99;

			if (drawLeftBufferStop && connectedToExplosiveDirection != Direction.LEFT) {
				offSetX = 0;
			} else if (drawRightBufferStop && connectedToExplosiveDirection != Direction.RIGHT) {
				offSetX = 128;
			} else if (drawUpBufferStop && connectedToExplosiveDirection != Direction.UP) {
				offSetY = 0;
			} else if (drawDownBufferStop && connectedToExplosiveDirection != Direction.DOWN) {
				offSetY = 128;
			} else {
				return shouldDraw;
			}

			TextureUtils.drawTexture(EffectBurn.flameTexture, //
					squareGameObjectIsOn.xInGridPixels - 8 + offSetX, //
					squareGameObjectIsOn.yInGridPixels - 16 + offSetY, //
					squareGameObjectIsOn.xInGridPixels + 8 + offSetX, //
					squareGameObjectIsOn.yInGridPixels + offSetY //
			);

		}

		return shouldDraw;

	}

	@Override
	public void update() {
		if (lit && this.turnLit != Level.turn && this.squareGameObjectIsOn != null) {
			Square square = this.squareGameObjectIsOn;
			CopyOnWriteArrayList<Square> connectedSquares = new CopyOnWriteArrayList<Square>(Square.class);
			connectedSquares.add(this.getSquareInDirection(direction1, square));
			connectedSquares.add(this.getSquareInDirection(direction2, square));
			final CopyOnWriteArrayList<Fuse> neighborFuses = new CopyOnWriteArrayList<Fuse>(Fuse.class);

			this.setPrimaryAnimation(new AnimationFuse(this, null

			) {

				@Override
				protected void animationSubclassRunCompletionAlgorightm(boolean wait) {
					super.animationSubclassRunCompletionAlgorightm(wait);

					for (Square neighborSquare : connectedSquares) {
						if (neighborSquare == null)
							continue;
						Fuse fuse = (Fuse) neighborSquare.inventory.getGameObjectWithTemplateId(Fuse.this.templateId);
						if (fuse != null) {
							neighborFuses.add(fuse);
							fuse.setLit(true);
							fuse.setLightable(true);
						}
					}

					for (GameObject gameObject : Fuse.this.gameObjectsToExplode) {
						gameObject.changeHealthSafetyOff(-gameObject.remainingHealth, null, null);
					}
//					Fuse.this.changeHealthSafetyOff(Fuse.this.remainingHealth, null, null);
					Fuse.this.squareGameObjectIsOn.inventory.remove(Fuse.this);
					for (Fuse neighborFuse : neighborFuses) {
						neighborFuse.updateImageTextures();
						neighborFuse.updateEndTextures();
					}

				}

			});
		}

	}

}
