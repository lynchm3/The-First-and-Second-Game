package com.marklynch.tactics.objects.weapons;
import static com.marklynch.utils.LoadedResources.loadGlobalImage;

import java.util.Vector;

import org.newdawn.slick.opengl.Texture;

import com.marklynch.tactics.objects.level.Square;
import com.marklynch.tactics.objects.unit.Actor.Direction;

public class Weapon {


	// attributes
	public int damage = 0;
	public int range = 0;

	// image
	public String imagePath = "";
	public Texture imageTexture = null;
	
	public Weapon(int damage, int range, String imagePath) {
		super();
		this.damage = damage;
		this.range = range;
		this.imagePath = imagePath;
		this.imageTexture = loadGlobalImage(imagePath);
	}
	
	public void calculateAttackableSquares(Square[][] squares)
	{
		for(int i = 0; i<squares.length; i++)
		{
			for(int j = 0; j<squares.length; j++)
			{
				if(squares[i][j].reachableBySelectedCharater)
				{					
					if(range > 0)
					{
						Vector<Square> squaresInThisPath = new Vector<Square>();
						squaresInThisPath.add(squares[i][j]);
						calculateAttackableSquares(squares, this.range, squares[i][j], Direction.UP, squaresInThisPath);
						calculateAttackableSquares(squares, this.range, squares[i][j], Direction.RIGHT, squaresInThisPath);
						calculateAttackableSquares(squares, this.range, squares[i][j], Direction.DOWN, squaresInThisPath);
						calculateAttackableSquares(squares, this.range, squares[i][j], Direction.LEFT, squaresInThisPath);
					}					
				}
			}
		}
	}
	
	public void calculateAttackableSquares(Square[][] squares, int remainingRange, Square parentSquare, Direction direction, Vector<Square> squaresInThisPath)
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
			currentSquare.weaponsThatCanAttack.add(this);
			remainingRange-= 1;			
			if(remainingRange > 0)
			{
				calculateAttackableSquares(squares, remainingRange, currentSquare, Direction.UP, squaresInThisPath);
				calculateAttackableSquares(squares, remainingRange, currentSquare, Direction.RIGHT, squaresInThisPath);
				calculateAttackableSquares(squares, remainingRange, currentSquare, Direction.DOWN, squaresInThisPath);
				calculateAttackableSquares(squares, remainingRange, currentSquare, Direction.LEFT, squaresInThisPath);
			}
			squaresInThisPath.remove(currentSquare);
		}
		
	}
}
