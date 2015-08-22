package com.ulticraft.stability;

import com.ulticraft.core.Duration;

public class TimeManager
{
	private long secondsAlive;

	public TimeManager()
	{
		secondsAlive = 0;
	}

	public void update()
	{
		secondsAlive += 1000;
	}

	public long getSecondsAlive()
	{
		return secondsAlive/1000;
	}

	public String formatTime()
	{
		return new Duration(secondsAlive).shortDescription();
	}
}
