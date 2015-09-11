package com.ulticraft.gui;

import org.bukkit.Material;

public class GUIElement
{
	private int slot;
	private String name;
	private Material material;
	private GUITrigger trigger;
	private int size;
	private GUIClickSound clickSound;
	private Integer metadata;
	private String skullName;
	private boolean glinting;

	public GUIElement(int slot, String name, Material material, GUITrigger trigger)
	{
		this.slot = slot;
		this.name = name;
		this.material = material;
		this.trigger = trigger;
		this.size = 1;
		this.glinting = false;
	}
	
	public GUIElement(int slot, String name, String skullName, GUITrigger trigger)
	{
		this.slot = slot;
		this.name = name;
		this.skullName = skullName;
		this.trigger = trigger;
		this.size = 1;
		this.glinting = false;
	}

	public int getSlot()
	{
		return slot;
	}

	public void setSlot(int slot)
	{
		this.slot = slot;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Material getMaterial()
	{
		return material;
	}

	public void setMaterial(Material material)
	{
		this.material = material;
	}

	public GUITrigger getTrigger()
	{
		return trigger;
	}

	public void setTrigger(GUITrigger trigger)
	{
		this.trigger = trigger;
	}

	public int getSize()
	{
		return size;
	}

	public void setSize(int size)
	{
		this.size = size;
	}

	public GUIClickSound getClickSound()
	{
		return clickSound;
	}

	public void setClickSound(GUIClickSound clickSound)
	{
		this.clickSound = clickSound;
	}

	public short getMetadata()
	{
		return metadata.shortValue();
	}

	public void setMetadata(int metadata)
	{
		this.metadata = metadata;
	}
	
	public boolean hasMetadata()
	{
		return metadata != null;
	}

	public boolean isGlinting()
	{
		return glinting;
	}

	public void setGlinting(boolean glinting)
	{
		this.glinting = glinting;
	}

	public void setMetadata(Integer metadata)
	{
		this.metadata = metadata;
	}

	public String getSkullName()
	{
		return skullName;
	}

	public void setSkullName(String skullName)
	{
		this.skullName = skullName;
	}
	
	public boolean isSkull()
	{
		return this.skullName != null;
	}
}
