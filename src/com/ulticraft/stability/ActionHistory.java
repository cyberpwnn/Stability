package com.ulticraft.stability;

import java.util.ArrayList;
import net.md_5.bungee.api.ChatColor;
import com.ulticraft.core.Duration;

public class ActionHistory
{
	private ArrayList<History> actions;

	public ActionHistory()
	{
		actions = new ArrayList<History>();
	}

	public void update()
	{
		for(History i : actions)
		{
			i.update();
		}
	}

	public void act(String action)
	{
		actions.add(new History(action));
	}

	public ArrayList<String> export()
	{
		ArrayList<String> e = new ArrayList<String>();

		for(History i : actions)
		{
			e.add(i.getAction() + ", " + ChatColor.GRAY + new Duration(i.getSeconds() * 1000) + " ago");
		}

		return e;
	}
}
