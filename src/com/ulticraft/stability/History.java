package com.ulticraft.stability;

import com.ulticraft.core.Duration;

public class History
{
	private String action;
	private int seconds;

	public History(String action)
	{
		this.seconds = 0;
		this.action = action;
	}

	public void update()
	{
		seconds += 1000;
	}

	public int getSeconds()
	{
		return seconds / 1000;
	}

	public String getAction()
	{
		return action;
	}

	public String format()
	{
		return new Duration(seconds).shortDescription();
	}
}
