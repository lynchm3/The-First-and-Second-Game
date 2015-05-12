package com.marklynch.tactics.objects.level;

import java.util.Vector;

public class Level {

	int width;
	int height;
	private Square[][] squares;
	
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
				squares[i][j] = new Square(i,j,"grass.png");
			}
		}
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Square[][] getSquares() {
		return squares;
	}

	public void setSquares(Square[][] squares) {
		this.squares = squares;
	}
	
	
	
	
	
}
