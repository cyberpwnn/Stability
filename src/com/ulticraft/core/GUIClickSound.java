package com.ulticraft.core;

import org.bukkit.Sound;

public class GUIClickSound
{
	private Sound sound;
	private float pitch;
	private float volume;

	public GUIClickSound(Sound sound, float pitch, float volume)
	{
		this.sound = sound;
		this.pitch = pitch;
		this.volume = volume;
	}

	public Sound getSound()
	{
		return sound;
	}

	public void setSound(Sound sound)
	{
		this.sound = sound;
	}

	public float getPitch()
	{
		return pitch;
	}

	public void setPitch(float pitch)
	{
		this.pitch = pitch;
	}

	public float getVolume()
	{
		return volume;
	}

	public void setVolume(float volume)
	{
		this.volume = volume;
	}
}
