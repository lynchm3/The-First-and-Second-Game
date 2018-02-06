package com.marklynch.utils;

public class Color extends mdesl.graphics.Color {

	/** The fixed color transparent */
	public static final Color TRANSPARENT = new Color(0.0f, 0.0f, 0.0f, 0.0f);
	/** The fixed colour white */
	public static final Color WHITE = new Color(1.0f, 1.0f, 1.0f, 1.0f);
	/** The fixed colour yellow */
	public static final Color YELLOW = new Color(1.0f, 1.0f, 0, 1.0f);
	/** The fixed colour red */
	public static final Color RED = new Color(1.0f, 0, 0, 1.0f);
	/** The fixed colour blue */
	public static final Color BLUE = new Color(0, 0, 1.0f, 1.0f);
	/** The fixed colour green */
	public static final Color GREEN = new Color(0, 1.0f, 0, 1.0f);
	/** The fixed colour black */
	public static final Color BLACK = new Color(0, 0, 0, 1.0f);
	/** The fixed colour gray */
	public static final Color GRAY = new Color(0.5f, 0.5f, 0.5f, 1.0f);
	/** The fixed colour cyan */
	public static final Color CYAN = new Color(0, 1.0f, 1.0f, 1.0f);
	/** The fixed colour dark gray */
	public static final Color DARK_GRAY = new Color(0.3f, 0.3f, 0.3f, 1.0f);
	/** The fixed colour light gray */
	public static final Color LIGHT_GRAY = new Color(0.7f, 0.7f, 0.7f, 1.0f);
	/** The fixed colour dark pink */
	public final static Color PINK = new Color(255, 175, 175, 255);
	/** The fixed colour dark orange */
	public final static Color ORANGE = new Color(255, 200, 0, 255);
	/** The fixed colour dark magenta */
	public final static Color MAGENTA = new Color(255, 0, 255, 255);

	// /** The red component [0.0 - 1.0]. */
	// public float r;
	// /** The green component [0.0 - 1.0]. */
	// public float g;
	// /** The blue component [0.0 - 1.0]. */
	// public float b;
	// /** The alpha component [0.0 - 1.0]. */
	// public float a;

	public Color() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Color(Color color) {
		super(color);
		// TODO Auto-generated constructor stub
	}

	public Color(float r, float g, float b, float a) {
		super(r, g, b, a);
		// TODO Auto-generated constructor stub
	}

	public Color(float r, float g, float b) {
		super(r, g, b);
		// TODO Auto-generated constructor stub
	}

	public Color(int r, int g, int b, int a) {
		super(r, g, b, a);
		// TODO Auto-generated constructor stub
	}

	public Color(int r, int g, int b) {
		super(r, g, b);
		// TODO Auto-generated constructor stub
	}

	public Color(int value) {
		super(value);
		// TODO Auto-generated constructor stub
	}

}
