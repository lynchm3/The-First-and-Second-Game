package com.marklynch.tactics.objects.unit;

import static com.marklynch.utils.LoadedResources.loadGlobalImage;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Vector;

import org.newdawn.slick.opengl.Texture;

import com.marklynch.tactics.objects.level.Square;

public class Actor {
	
	public enum Direction {
		UP, RIGHT, DOWN, LEFT
	}

	// attributes
	public int strength = 0;
	public int dexterity = 0;
	public int intelligence = 0;
	public int endurance = 0;
	public int travelDistance = 4;

	// image
	public String imagePath = "";
	public Texture imageTexture = null;
	public Square squareActorIsStandingOn;

	public Actor(int strength, int dexterity, int intelligence, int endurance,
			String imagePath, Square square) {
		super();
		this.strength = strength;
		this.dexterity = dexterity;
		this.intelligence = intelligence;
		this.endurance = endurance;
		this.imagePath = imagePath;
		this.imageTexture = loadGlobalImage(imagePath);
		this.squareActorIsStandingOn = square;
	}
	
	public void calculateWalkableSquares(Square[][] squares)
	{
		for(int i = 0; i<squares.length; i++)
		{
			for(int j = 0; j<squares.length; j++)
			{
				squares[i][j].walkable = false;
			}
		}
		
		Vector<Square> squaresInThisPath = new Vector<Square>();
		squaresInThisPath.add(this.squareActorIsStandingOn);
		
		if(travelDistance > 0)
		{
			calculateWalkableSquares(squares, this.travelDistance, this.squareActorIsStandingOn, Direction.UP, squaresInThisPath);
			calculateWalkableSquares(squares, this.travelDistance, this.squareActorIsStandingOn, Direction.RIGHT, squaresInThisPath);
			calculateWalkableSquares(squares, this.travelDistance, this.squareActorIsStandingOn, Direction.DOWN, squaresInThisPath);
			calculateWalkableSquares(squares, this.travelDistance, this.squareActorIsStandingOn, Direction.LEFT, squaresInThisPath);
		}
	}
	
	public void calculateWalkableSquares(Square[][] squares, int remainingDistance, Square parentSquare, Direction direction, Vector<Square> squaresInThisPath)
	{
		Square currentSquare = null;
		
		if(direction == Direction.UP)
		{
			if(parentSquare.y - 1 >= 0)
			{
				currentSquare = squares[parentSquare.x][parentSquare.y - 1];
			}
		}
		else if(direction == Direction.RIGHT)
		{
			if(parentSquare.x + 1 < squares.length)
			{
				currentSquare = squares[parentSquare.x + 1][parentSquare.y];
			}
		}
		else if(direction == Direction.DOWN)
		{

			if(parentSquare.y + 1 < squares[0].length)
			{
				currentSquare = squares[parentSquare.x][parentSquare.y + 1];
			}
		}
		else if(direction == Direction.LEFT)
		{
			if(parentSquare.x - 1 >= 0)
			{
				currentSquare = squares[parentSquare.x - 1][parentSquare.y];
			}
		}	
		
		if(currentSquare != null && currentSquare.actor == null && !squaresInThisPath.contains(currentSquare))
		{
			squaresInThisPath.add(currentSquare);
			currentSquare.walkable = true;
			remainingDistance -= parentSquare.travelCost;			
			if(remainingDistance > 0)
			{
				calculateWalkableSquares(squares, remainingDistance, currentSquare, Direction.UP, squaresInThisPath);
				calculateWalkableSquares(squares, remainingDistance, currentSquare, Direction.RIGHT, squaresInThisPath);
				calculateWalkableSquares(squares, remainingDistance, currentSquare, Direction.DOWN, squaresInThisPath);
				calculateWalkableSquares(squares, remainingDistance, currentSquare, Direction.LEFT, squaresInThisPath);
			}
			squaresInThisPath.remove(currentSquare);
		}
		
	}
	
	

}
