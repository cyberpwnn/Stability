package com.ulticraft.stability;

import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapPalette;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

@SuppressWarnings("deprecation")
public class MapGraph extends MapRenderer
{
	private MapCanvas canvas;
	private SampleArray array;
	
	@Override
	public void render(MapView view, MapCanvas canvas, Player player)
	{
		this.canvas = canvas;
		
		clear();
		graphStats();
	}
	
	public void clear()
	{
		for(int i = 0; i < 128; i++)
		{
			for(int j = 0; j < 128; j++)
			{
				canvas.setPixel(i, j, MapPalette.WHITE);
			}
		}
	}
	
	public void graphStats()
	{
		int factorRam = (int) Math.round(array.getSamples().get(0).getMaxMemory() / 128);
		int workingLine = 0;
		int prevRamLevel = 0;
		
		int factorTps = 200/128;
		int prevTpsLevel = 0;
		
		int factorChunks = (int) Math.round(array.getSamples().get(0).getFreeMemory()/20000 / 128);
		int prevChunksLevel = 0;
		
		for(Sample i : array.getSamples())
		{			
			int ramLevel = (int) Math.round((i.getMaxMemory() - i.getFreeMemory()) / factorRam);
			int tpsLevel = 72 - (int) Math.round(Math.abs(i.getTps()*10) / factorTps) + 128;
			int chunksLevel = 127 - (int) Math.round(i.getChunksLoaded() / factorChunks);
						
			if(prevRamLevel == 0)
			{
				prevRamLevel = ramLevel;
			}
			
			if(prevTpsLevel == 0)
			{
				prevTpsLevel = tpsLevel;
			}
			
			if(prevChunksLevel == 0)
			{
				prevChunksLevel = chunksLevel;
			}
			
			if(prevRamLevel < ramLevel)
			{
				for(int j = prevRamLevel; j < ramLevel; j++)
				{
					canvas.setPixel(workingLine, j, MapPalette.RED);
				}
			}
			
			else
			{
				for(int j = prevRamLevel; j > ramLevel; j--)
				{
					canvas.setPixel(workingLine, j, MapPalette.RED);
				}
			}
			
			if(prevTpsLevel < tpsLevel)
			{
				for(int j = prevTpsLevel; j < tpsLevel; j++)
				{
					canvas.setPixel(workingLine, j, MapPalette.LIGHT_GREEN);
				}
			}
			
			else
			{
				for(int j = prevTpsLevel; j > tpsLevel; j--)
				{
					canvas.setPixel(workingLine, j, MapPalette.LIGHT_GREEN);
				}
			}
			
			if(prevChunksLevel < chunksLevel)
			{
				for(int j = prevChunksLevel; j < chunksLevel; j++)
				{
					canvas.setPixel(workingLine, j, MapPalette.BLUE);
				}
			}
			
			else
			{
				for(int j = prevChunksLevel; j > chunksLevel; j--)
				{
					canvas.setPixel(workingLine, j, MapPalette.BLUE);
				}
			}
			
			canvas.setPixel(workingLine, ramLevel, MapPalette.RED);
			canvas.setPixel(workingLine, tpsLevel, MapPalette.LIGHT_GREEN);
			canvas.setPixel(workingLine, chunksLevel, MapPalette.BLUE);
			
			prevRamLevel = ramLevel;
			prevTpsLevel = tpsLevel;
			prevChunksLevel = chunksLevel;
			workingLine++;
		}
	}
	
	public void setSampleArray(SampleArray array)
	{
		this.array = array;
	}
}
