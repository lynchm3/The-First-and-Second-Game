package com.marklynch.tactics.objects.level;

import java.util.Vector;

import com.marklynch.GameCursor;
import com.marklynch.tactics.objects.GameObject;
import com.marklynch.tactics.objects.unit.Actor;

public class Level {

	public int width;
	public int height;
	public GameCursor gameCursor;
	public Vector<Actor> actors;
	public Actor selectedActor = null;
	public Vector<GameObject> gameObjects;
	public Square[][] squares;
	
//	java representation of a grid??
//	2d array?
	
	public Level(int width, int height)
	{
		this.width = width;
		this.height = height;
		squares = new Square[width][height];
		initGrid();
	}
	
	private void initGrid()
	{
		for(int i = 0; i<width; i++)
		{
			for(int j = 0; j<height; j++)
			{
				squares[i][j] = new Square(i,j,"grass.png",1);
			}
		}
	}

	public void removeWalkingHighlight() {
		for(int i = 0; i<squares.length; i++)
		{
			for(int j = 0; j<squares.length; j++)
			{
				squares[i][j].reachableBySelectedCharater = false;
			}
		}
	}
	
	
	
	
	
}
