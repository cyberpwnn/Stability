package com.ulticraft.stability;

import java.util.ArrayList;
import java.util.HashMap;

public class Prediction
{
	private ArrayList<String> suggestions;
	private HashMap<Integer, ArrayList<Double>> tpsCross;
	private HashMap<Integer, Double> tpsAvg;
	private Stability pl;
	
	private String maxpl;
	
	public Prediction(Stability pl)
	{
		tpsCross = new HashMap<Integer, ArrayList<Double>>();
		tpsAvg = new HashMap<Integer, Double>();
		suggestions = new ArrayList<String>();
		this.pl = pl;
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
			if(tpsCross.get(i).size() > 1000)
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
				suggestions.add("Your max-players without lag is " + i + "/" + pl.getServer().getMaxPlayers());
				maxpl = i + "/" + pl.getServer().getMaxPlayers();
				break;
			}
		}
		
		return suggestions;
	}
	
	public String getMaxPlayers()
	{
		return maxpl;
	}
}
