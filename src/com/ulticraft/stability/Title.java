package com.ulticraft.stability;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import com.ulticraft.core.NMS;

public class Title
{
	private String title;
	private ChatColor titleColor;
	private String subtitle;
	private ChatColor subtitleColor;
	private String subSubTitle;
	private int fadeInTime;
	private int stayTime;
	private int fadeOutTime;
	
	public Title(int fadeInTime, int stayTime, int fadeOutTime)
	{
		this.title = "";
		this.titleColor = ChatColor.WHITE;
		this.subtitle = "";
		this.subtitleColor = ChatColor.WHITE;
		this.fadeInTime = fadeInTime;
		this.stayTime = stayTime;
		this.fadeOutTime = fadeOutTime;
	}
	
	public Title()
	{
		this.title = "";
		this.titleColor = ChatColor.WHITE;
		this.subtitle = "";
		this.subtitleColor = ChatColor.WHITE;
		this.fadeInTime = 5;
		this.stayTime = 30;
		this.fadeOutTime = 30;
	}
	
	public Title(String title)
	{
		this.title = "";
		this.titleColor = ChatColor.WHITE;
		this.subtitle = "";
		this.subtitleColor = ChatColor.WHITE;
		this.fadeInTime = 5;
		this.stayTime = 30;
		this.fadeOutTime = 30;
		this.title = title;
	}
	
	public Title(String title, String subtitle)
	{
		this.title = "";
		this.titleColor = ChatColor.WHITE;
		this.subtitle = "";
		this.subtitleColor = ChatColor.WHITE;
		this.fadeInTime = 5;
		this.stayTime = 30;
		this.fadeOutTime = 30;
		this.title = title;
		this.subtitle = subtitle;
	}
	
	public Title(String title, String subtitle, String subSubTitle)
	{
		this.title = "";
		this.titleColor = ChatColor.WHITE;
		this.subtitle = "";
		this.subtitleColor = ChatColor.WHITE;
		this.fadeInTime = 5;
		this.stayTime = 30;
		this.fadeOutTime = 30;
		this.title = title;
		this.subtitle = subtitle;
		this.subSubTitle = subSubTitle;
	}
	
	public Title(Title title)
	{
		this.title = "";
		this.titleColor = ChatColor.WHITE;
		this.subtitle = "";
		this.subtitleColor = ChatColor.WHITE;
		this.fadeInTime = 5;
		this.stayTime = 30;
		this.fadeOutTime = 30;
		this.title = title.title;
		this.subtitle = title.subtitle;
		this.titleColor = title.titleColor;
		this.subtitleColor = title.subtitleColor;
		this.fadeInTime = title.fadeInTime;
		this.fadeOutTime = title.fadeOutTime;
		this.stayTime = title.stayTime;
		this.subSubTitle = title.getSubSubTitle();
	}
	
	public Title(String title, String subtitle, int fadeInTime, int stayTime, int fadeOutTime)
	{
		this.title = "";
		this.titleColor = ChatColor.WHITE;
		this.subtitle = "";
		this.subtitleColor = ChatColor.WHITE;
		this.fadeInTime = 5;
		this.stayTime = 30;
		this.fadeOutTime = 30;
		this.title = title;
		this.subtitle = subtitle;
		this.fadeInTime = fadeInTime;
		this.stayTime = stayTime;
		this.fadeOutTime = fadeOutTime;
	}
	
	public Title(String title, String subtitle, String subSubTitle, int fadeInTime, int stayTime, int fadeOutTime)
	{
		this.title = "";
		this.titleColor = ChatColor.WHITE;
		this.subtitle = "";
		this.subtitleColor = ChatColor.WHITE;
		this.fadeInTime = 5;
		this.stayTime = 30;
		this.fadeOutTime = 30;
		this.title = title;
		this.subtitle = subtitle;
		this.fadeInTime = fadeInTime;
		this.stayTime = stayTime;
		this.fadeOutTime = fadeOutTime;
		this.subSubTitle = subSubTitle;
	}
	
	public String getSubSubTitle()
	{
		return subSubTitle;
	}
	
	public void setSubSubTitle(String subSubTitle)
	{
		this.subSubTitle = subSubTitle;
	}
	
	public int getFadeInTime()
	{
		return fadeInTime;
	}
	
	public int getStayTime()
	{
		return stayTime;
	}
	
	public int getFadeOutTime()
	{
		return fadeOutTime;
	}
	
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	public String getTitle()
	{
		return this.title;
	}
	
	public void setSubTitle(String subtitle)
	{
		this.subtitle = subtitle;
	}
	
	public String getSubTitle()
	{
		return this.subtitle;
	}
	
	public void setTitleColor(ChatColor color)
	{
		this.titleColor = color;
	}
	
	public void setSubTitleColor(ChatColor color)
	{
		this.subtitleColor = color;
	}
	
	public void setFadeInTime(int time)
	{
		this.fadeInTime = time;
	}
	
	public void setFadeOutTime(int time)
	{
		this.fadeOutTime = time;
	}
	
	public void setStayTime(int time)
	{
		this.stayTime = time;
	}
	
	public void send(Player player)
	{
		if(title == null || title.equals(""))
		{
			title = " ";
		}
		
		if(subtitle == null || subtitle.equals(""))
		{
			subtitle = " ";
		}
		
		if(subSubTitle == null || subSubTitle.equals(""))
		{
			subSubTitle = " ";
		}
		
		NMS.sendTitle(player, fadeInTime, stayTime, fadeOutTime, title, subtitle);
		NMS.sendActionBar(player, subSubTitle);
	}
	
	public void sendTitleAsSubtitle(Player player)
	{
		if(title == null || title.equals(""))
		{
			title = " ";
		}
		
		if(subtitle == null || subtitle.equals(""))
		{
			subtitle = " ";
		}
		
		if(subSubTitle == null || subSubTitle.equals(""))
		{
			subSubTitle = " ";
		}
		
		NMS.sendTitle(player, fadeInTime, stayTime, fadeOutTime, "", title);
		NMS.sendActionBar(player, subtitle);
	}
		
	public void sendAll()
	{
		for(Player p : Bukkit.getOnlinePlayers())
		{
			this.send(p);
		}
	}
}
