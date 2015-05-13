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
	private int strength = 0;
	private int dexterity = 0;
	private int intelligence = 0;
	private int endurance = 0;
	private int travelDistance = 4;

	// image
	private String imagePath = "";
	private Texture imageTexture = null;
	private Square squareActorIsStandingOn;

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

	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public int getDexterity() {
		return dexterity;
	}

	public void setDexterity(int dexterity) {
		this.dexterity = dexterity;
	}

	public int getIntelligence() {
		return intelligence;
	}

	public void setIntelligence(int intelligence) {
		this.intelligence = intelligence;
	}

	public int getEndurance() {
		return endurance;
	}

	public void setEndurance(int endurance) {
		this.endurance = endurance;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Texture getImageTexture() {
		return imageTexture;
	}

	public void setImageTexture(Texture imageTexture) {
		this.imageTexture = imageTexture;
	}

	public Square getSquare() {
		return squareActorIsStandingOn;
	}

	public void setSquare(Square square) {
		this.squareActorIsStandingOn = square;
	}
	
	public void calculateWalkableSquares(Square[][] squares)
	{
		
		System.out.println("calculateWalkableSquares a");
		for(int i = 0; i<squares.length; i++)
		{
			for(int j = 0; j<squares.length; j++)
			{
				squares[i][j].setWalkable(false);
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
			if(parentSquare.getY() - 1 >= 0)
			{
				currentSquare = squares[parentSquare.getX()][parentSquare.getY() - 1];
			}
		}
		else if(direction == Direction.RIGHT)
		{
			if(parentSquare.getX() + 1 < squares.length)
			{
				currentSquare = squares[parentSquare.getX() + 1][parentSquare.getY()];
			}
		}
		else if(direction == Direction.DOWN)
		{

			if(parentSquare.getY() + 1 < squares[0].length)
			{
				currentSquare = squares[parentSquare.getX()][parentSquare.getY() + 1];
			}
		}
		else if(direction == Direction.LEFT)
		{
			if(parentSquare.getX() - 1 >= 0)
			{
				currentSquare = squares[parentSquare.getX() - 1][parentSquare.getY()];
			}
		}	
		
		if(currentSquare != null && currentSquare.getActor() == null && !squaresInThisPath.contains(currentSquare))
		{
			System.out.println("calculateWalkableSquares c");
			squaresInThisPath.add(currentSquare);
			currentSquare.setWalkable(true);
			remainingDistance -= parentSquare.getTravelCost();			
			if(remainingDistance > 0)
			{
				System.out.println("calculateWalkableSquares d");
				calculateWalkableSquares(squares, remainingDistance, currentSquare, Direction.UP, squaresInThisPath);
				calculateWalkableSquares(squares, remainingDistance, currentSquare, Direction.RIGHT, squaresInThisPath);
				calculateWalkableSquares(squares, remainingDistance, currentSquare, Direction.DOWN, squaresInThisPath);
				calculateWalkableSquares(squares, remainingDistance, currentSquare, Direction.LEFT, squaresInThisPath);
			}
			squaresInThisPath.remove(currentSquare);
		}
		
	}
	
	

}
