package com.ulticraft.stability;

import java.util.ArrayList;

public class SampleArray
{
	private ArrayList<Sample> samples;
	
	public SampleArray()
	{
		samples = new ArrayList<Sample>();
	}
	
	public void addSample(Sample sample)
	{
		if(samples.size() >= 128)
		{
			samples.remove(0);
		}
		
		samples.add(sample);
	}
	
	public ArrayList<Sample> getSamples()
	{
		return samples;
	}
}
