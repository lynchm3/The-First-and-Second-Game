package com.marklynch.level.constructs.area;

import com.marklynch.level.squares.Square;
import com.marklynch.utils.Texture;

public interface Place {

	public Texture getIcon();

	public Square getCentreSquare();

	public Long getId();

}
