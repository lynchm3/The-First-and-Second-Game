package com.marklynch.utils;

import java.io.IOException;
import java.util.HashMap;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class LoadedResources {

	private static HashMap<String, Texture> globalImages = new HashMap<String, Texture>();
	private static HashMap<String, Texture> levelImages = new HashMap<String, Texture>();
	
	public static Texture loadGlobalImage(String path)
	{
		
		Texture texture = getGlobalImage(path);
		
		if(texture != null)
		{
		
		}
		else
		{
		
			try{
				texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/images/" + path));
				globalImages.put(
						path
						, texture);		
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return texture;
	}
	
	public static Texture getGlobalImage(String path)
	{
		return globalImages.get(path);
	}
	
	public static Texture getLevellImage(String path)
	{
		return levelImages.get(path);
	}
	
	public static void unloadLevelImages()
	{

		//ResourceLoader??
		//TextureLoader??
		
//		Texture t;
//		t.release()
		
		//levelImages = null
		
	}
	
	public static void unloadLevelImage(String path)
	{

		//ResourceLoader??
		//TextureLoader??
		
//		Texture t;
//		t.release()
		
		//levelImages = null
		
	}
	
	public static void unloadGlobalImages()
	{

		//ResourceLoader??
		//TextureLoader??
		
//		Texture t;
//		t.release()
		
		//levelImages = null
		
	}
	
	public static void unloadGlobalImage(String path)
	{

		//ResourceLoader??
		//TextureLoader??
		
//		Texture t;
//		t.release()
		
		//levelImages = null
		
	}
	
	
	
	
	
}
