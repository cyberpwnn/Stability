package com.ulticraft.stability;

import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.ChatColor;

public class Prediction
{
	private ArrayList<String> suggestions;
	private HashMap<Integer, ArrayList<Double>> tpsCross;
	private HashMap<Integer, Double> tpsAvg;
	private Stability pl;
	private String score;
	
	private String maxpl;
	
	public Prediction(Stability pl)
	{
		tpsCross = new HashMap<Integer, ArrayList<Double>>();
		tpsAvg = new HashMap<Integer, Double>();
		suggestions = new ArrayList<String>();
		score = "0%";
		this.pl = pl;
	}
	
	public String getStabilityScore()
	{
		return score;
	}
	
	public void addSample(Sample sample)
	{		
		if(tpsCross.containsKey(sample.getPlayersOnline()))
		{
			ArrayList<Double> m = tpsCross.get(sample.getPlayersOnline());
			m.add(sample.getTps());
			tpsCross.put(sample.getPlayersOnline(), m);
		}
		
		else
		{
			ArrayList<Double> m = new ArrayList<Double>();
			m.add(sample.getTps());
			tpsCross.put(sample.getPlayersOnline(), m);
		}
		
		doReset();
	}
	
	public void doReset()
	{
		for(Integer i : tpsCross.keySet())
		{
			if(tpsCross.get(i).size() > pl.getConfiguration().getSampleCount())
			{
				tpsCross.get(i).remove(0);
			}
		}
	}
	
	public ArrayList<String> getSuggestions()
	{
		suggestions = new ArrayList<String>();
		
		for(Integer i : tpsCross.keySet())
		{
			double currentAvg = 0;
			
			for(Double j : tpsCross.get(i))
			{
				currentAvg += j;
			}
			
			tpsAvg.put(i, currentAvg / tpsCross.get(i).size());
		}
		
		for(Integer i : tpsAvg.keySet())
		{
			if(tpsAvg.get(i) < pl.getConfiguration().getThresholdTps())
			{
				if(i == 0 || tpsAvg.keySet().size() < 4)
				{
					break;
				}
				
				suggestions.add(ChatColor.YELLOW + "Your max-players without lag is " + i + "/" + pl.getServer().getMaxPlayers());
				maxpl = i + "/" + pl.getServer().getMaxPlayers();
				break;
			}
		}
		
		double avgRam = 0;
		double avgRed = 0;
		
		for(Sample i : pl.getSampler().getSampleArray().getSamples())
		{
			avgRam += i.getFreeMemory();
			avgRed += i.getRedstoneClocks();
		}
		
		avgRam /= pl.getSampler().getSampleArray().getSamples().size();
		avgRed /= pl.getSampler().getSampleArray().getSamples().size();
		
		int sk = 100;
		
		if(avgRam / 1024.0 / 1024.0 / (Runtime.getRuntime().maxMemory() / 1024L / 1024L) > 0.22 && avgRam / 1024.0 / 1024.0 / (Runtime.getRuntime().maxMemory() / 1024L / 1024L) < 0.34)
		{
			suggestions.add(ChatColor.RED + "Your server only has one third of RAM free.");
			sk -= 12;
		}
		
		else if(avgRam / 1024.0 / 1024.0 / (Runtime.getRuntime().maxMemory() / 1024L / 1024L) < 0.21 && avgRam / 1024.0 / 1024.0 / (Runtime.getRuntime().maxMemory() / 1024L / 1024L) > 0.12)
		{
			suggestions.add(ChatColor.DARK_RED + "Your server only has one fifth of RAM free.");
			sk -= 32;
		}
		
		else if(avgRam / 1024.0 / 1024.0 / (Runtime.getRuntime().maxMemory() / 1024L / 1024L) < 0.11)
		{
			suggestions.add(ChatColor.BOLD + "" + ChatColor.DARK_RED + "Your server is at risk of OOME's & Data Failure.");
			sk -= 62;
		}
		
		if(avgRed > pl.getConfiguration().getMaxRedstoneUpdates())
		{
			int times = (int) (avgRed / pl.getConfiguration().getMaxRedstoneUpdates());
			suggestions.add(ChatColor.YELLOW + "Redstone updates are " + times + " times as much, for your threshold.");
			sk -= 7;
		}
		
		if(pl.getSampler().getTimeManager().getSecondsAlive() > (24*60*60))
		{
			suggestions.add(ChatColor.RED + "Server Runtime Stale. Have periodic reboots to stabilize.");
			sk -= 25;
		}
		
		if(pl.getSampler().getAnalyzer().getLag() > 30)
		{
			suggestions.add(ChatColor.BOLD + "" + ChatColor.DARK_RED + "Your server has been lagging for over 1 minute");
			sk = 0;
		}
		
		if(sk < 0)
		{
			sk = 0;
		}
		
		score = sk + "%";
		
		return suggestions;
	}
	
	public String getMaxPlayers()
	{
		return maxpl;
	}
}
