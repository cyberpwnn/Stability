package com.ulticraft.core;

import java.text.DecimalFormat;

public class Format
{
	public static String memoryFormat(int mb)
	{
		String s = "";
		String k = "mb";
		Double m = (double) mb;
		
		if(mb > 1024)
		{
			k = "gb";
			m /= 1024;
		}
		
		s = new DecimalFormat("#.#").format(m);
		
		return s + k;
	}
	
	public static String percentFormat(Double percent)
	{
		return new DecimalFormat("#").format(percent);
	}
}
