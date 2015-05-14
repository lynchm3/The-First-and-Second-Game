package com.marklynch.tactics.objects.level;

import java.util.Vector;

public class Level {

	public int width;
	public int height;
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
				squares[i][j].reachableBySelectedCaharater = false;
			}
		}
	}
	
	
	
	
	
}
