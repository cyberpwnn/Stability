package com.ulticraft.stability;

public class TPSSample implements Runnable
{
	public static int TICK_COUNT;
	public static long[] TICKS;
	public static long LAST_TICK;
	public static int REMAINING;

	static
	{
		TPSSample.TICK_COUNT = 0;
		TPSSample.TICKS = new long[600];
		TPSSample.LAST_TICK = 0L;
	}

	public static double getTPS()
	{
		return getTPS(100);
	}
	
	public static int getRemaining()
	{
		return REMAINING;
	}

	public static double getTPS(final int ticks)
	{
		try
		{
			if (TPSSample.TICK_COUNT < ticks)
			{
				REMAINING = ticks - TPSSample.TICK_COUNT;
				return -20.0;
			}
			
			final int target = (TPSSample.TICK_COUNT - 1 - ticks) % TPSSample.TICKS.length;
			final long elapsed = System.currentTimeMillis() - TPSSample.TICKS[target];
			double tpsc = ticks / (elapsed / 1000.0);
			if (tpsc > 20.0)
			{
				tpsc = 20.0;
			}
			return tpsc;
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			return 20.0;
		}
	}

	@SuppressWarnings("unused")
	public static long getElapsed(final int tickID)
	{
		final int length = TPSSample.TICKS.length;
		final long time = TPSSample.TICKS[tickID % TPSSample.TICKS.length];
		return System.currentTimeMillis() - time;
	}

	@Override
	public void run()
	{
		TPSSample.TICKS[TPSSample.TICK_COUNT % TPSSample.TICKS.length] = System.currentTimeMillis();
		++TPSSample.TICK_COUNT;
	}
}
