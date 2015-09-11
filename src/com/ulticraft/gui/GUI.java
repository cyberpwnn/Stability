package com.ulticraft.gui;

import java.util.ArrayList;

public class GUI
{
	private String name;
	private int size;
	private ArrayList<GUIElement> elements;
	
	public GUI(String name, int size)
	{
		if(size % 9 != 0)
		{
			size = size + (9 - (size % 9));
		}
		
		this.name = name;
		this.size = size;
		this.elements = new ArrayList<GUIElement>();
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getSize()
	{
		return size;
	}

	public void setSize(int size)
	{
		this.size = size + (9 - (size % 9));;
	}
	
	public ArrayList<GUIElement> getGUIElemtns()
	{
		return elements;
	}
	
	public void addGUIElement(GUIElement element)
	{
		elements.add(element);
	}
	
	public void delGUIElement(GUIElement element)
	{
		elements.remove(element);
	}
}
