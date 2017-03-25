package com.marklynch.level.constructs;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.utils.ArrayUtils;

public class Sound {
	public Actor sourceActor;
	public GameObject sourceObject;
	public Square sourceSquare;
	public ArrayList<Square> destinationSquares;
	public float loudness;
	public boolean legal;
	public Class action;

	public Sound(Actor sourceActor, GameObject sourceObject, Square sourceSquare, float loudness, boolean illegal,
			Class action) {
		super();
		this.sourceActor = sourceActor;
		this.sourceObject = sourceObject;
		this.sourceSquare = sourceSquare;
		this.loudness = loudness;

		destinationSquares = getAllSquaresWithinDistance(loudness);
		for (Square destinationSquare : destinationSquares) {
			destinationSquare.sounds.add(this);
		}

	}

	ArrayList<Square> getAllSquaresWithinDistance(float maxDistance) {
		ArrayList<Square> squares = new ArrayList<Square>();

		for (int distance = 0; distance <= maxDistance; distance++) {

			if (distance == 0)

			{
				squares.add(this.sourceSquare);
				continue;
			}

			boolean xGoingUp = true;
			boolean yGoingUp = true;
			for (float i = 0, x = -distance, y = 0; i < distance * 4; i++) {
				if (ArrayUtils.inBounds(Game.level.squares, this.sourceSquare.xInGrid + x,
						this.sourceSquare.yInGrid + y)) {
					squares.add(Game.level.squares[this.sourceSquare.xInGrid + (int) x][this.sourceSquare.yInGrid
							+ (int) y]);
				}

				if (xGoingUp) {
					if (x == distance) {
						xGoingUp = false;
						x--;
					} else {
						x++;
					}
				} else {
					if (x == -distance) {
						xGoingUp = true;
						x++;
					} else {
						x--;
					}
				}

				if (yGoingUp) {
					if (y == distance) {
						yGoingUp = false;
						y--;
					} else {
						y++;
					}
				} else {
					if (y == -distance) {
						yGoingUp = true;
						y++;
					} else {
						y--;
					}
				}

			}
		}

		return squares;
	}
}
