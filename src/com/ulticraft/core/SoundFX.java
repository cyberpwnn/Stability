package com.ulticraft.core;

import org.bukkit.Location;
import org.bukkit.Sound;

public class SoundFX
{
	public static void fillDropDown(Sound sound, Location location, float initial, int degrees)
	{
		float factor = 0.1f + (initial / (float)degrees);
		for(int i = 1; i < degrees; i++)
		{
			location.getWorld().playSound(location, sound, factor * i, factor * i);
		}
	}
}
