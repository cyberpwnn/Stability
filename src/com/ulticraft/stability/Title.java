package com.ulticraft.stability;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Title
{
	private Class<?> packetTitle;
	private Class<?> packetActions;
	private Class<?> nmsChatSerializer;
	private Class<?> chatBaseComponent;
	private String title;
	private ChatColor titleColor;
	private String subtitle;
	private ChatColor subtitleColor;
	private int fadeInTime;
	private int stayTime;
	private int fadeOutTime;
	private boolean ticks;
	private static final Map<Class<?>, Class<?>> CORRESPONDING_TYPES;

	static
	{
		CORRESPONDING_TYPES = new HashMap<Class<?>, Class<?>>();
	}

	public Title(final String title)
	{
		this.title = "";
		this.titleColor = ChatColor.WHITE;
		this.subtitle = "";
		this.subtitleColor = ChatColor.WHITE;
		this.fadeInTime = -1;
		this.stayTime = -1;
		this.fadeOutTime = -1;
		this.ticks = true;
		this.title = title;
		this.loadClasses();
	}

	public Title(final String title, final String subtitle)
	{
		this.title = "";
		this.titleColor = ChatColor.WHITE;
		this.subtitle = "";
		this.subtitleColor = ChatColor.WHITE;
		this.fadeInTime = -1;
		this.stayTime = -1;
		this.fadeOutTime = -1;
		this.ticks = true;
		this.title = title;
		this.subtitle = subtitle;
		this.loadClasses();
	}

	public Title(final Title title)
	{
		this.title = "";
		this.titleColor = ChatColor.WHITE;
		this.subtitle = "";
		this.subtitleColor = ChatColor.WHITE;
		this.fadeInTime = -1;
		this.stayTime = -1;
		this.fadeOutTime = -1;
		this.ticks = true;
		this.title = title.title;
		this.subtitle = title.subtitle;
		this.titleColor = title.titleColor;
		this.subtitleColor = title.subtitleColor;
		this.fadeInTime = title.fadeInTime;
		this.fadeOutTime = title.fadeOutTime;
		this.stayTime = title.stayTime;
		this.ticks = title.ticks;
		this.loadClasses();
	}

	public Title(final String title, final String subtitle, final int fadeInTime, final int stayTime, final int fadeOutTime)
	{
		this.title = "";
		this.titleColor = ChatColor.WHITE;
		this.subtitle = "";
		this.subtitleColor = ChatColor.WHITE;
		this.fadeInTime = -1;
		this.stayTime = -1;
		this.fadeOutTime = -1;
		this.ticks = true;
		this.title = title;
		this.subtitle = subtitle;
		this.fadeInTime = fadeInTime;
		this.stayTime = stayTime;
		this.fadeOutTime = fadeOutTime;
		this.loadClasses();
	}

	private void loadClasses()
	{
		this.packetTitle = this.getNMSClass("PacketPlayOutTitle");
		this.packetActions = this.getNMSClass("PacketPlayOutTitle$EnumTitleAction");
		this.chatBaseComponent = this.getNMSClass("IChatBaseComponent");
		this.nmsChatSerializer = this.getNMSClass("IChatBaseComponent$ChatSerializer");
	}

	public void setTitle(final String title)
	{
		this.title = title;
	}

	public String getTitle()
	{
		return this.title;
	}

	public void setSubtitle(final String subtitle)
	{
		this.subtitle = subtitle;
	}

	public String getSubtitle()
	{
		return this.subtitle;
	}

	public void setTitleColor(final ChatColor color)
	{
		this.titleColor = color;
	}

	public void setSubtitleColor(final ChatColor color)
	{
		this.subtitleColor = color;
	}

	public void setFadeInTime(final int time)
	{
		this.fadeInTime = time;
	}

	public void setFadeOutTime(final int time)
	{
		this.fadeOutTime = time;
	}

	public void setStayTime(final int time)
	{
		this.stayTime = time;
	}

	public void setTimingsToTicks()
	{
		this.ticks = true;
	}

	public void setTimingsToSeconds()
	{
		this.ticks = false;
	}

	public void send(final Player player)
	{
		if (this.packetTitle != null)
		{
			this.resetTitle(player);
			try
			{
				final Object handle = this.getHandle(player);
				final Object connection = this.getField(handle.getClass(), "playerConnection").get(handle);
				final Object[] actions = (Object[]) this.packetActions.getEnumConstants();
				final Method sendPacket = this.getMethod(connection.getClass(), "sendPacket", (Class<?>[]) new Class[0]);
				Object packet = this.packetTitle.getConstructor(this.packetActions, this.chatBaseComponent, Integer.TYPE, Integer.TYPE, Integer.TYPE).newInstance(actions[2], null, this.fadeInTime * (this.ticks ? 1 : 20), this.stayTime * (this.ticks ? 1 : 20), this.fadeOutTime * (this.ticks ? 1 : 20));
				if (this.fadeInTime != -1 && this.fadeOutTime != -1 && this.stayTime != -1)
				{
					sendPacket.invoke(connection, packet);
				}
				Object serialized = this.getMethod(this.nmsChatSerializer, "a", String.class).invoke(null, "{text:\"" + ChatColor.translateAlternateColorCodes('&', this.title) + "\",color:" + this.titleColor.name().toLowerCase() + "}");
				packet = this.packetTitle.getConstructor(this.packetActions, this.chatBaseComponent).newInstance(actions[0], serialized);
				sendPacket.invoke(connection, packet);
				if (this.subtitle != "")
				{
					serialized = this.getMethod(this.nmsChatSerializer, "a", String.class).invoke(null, "{text:\"" + ChatColor.translateAlternateColorCodes('&', this.subtitle) + "\",color:" + this.subtitleColor.name().toLowerCase() + "}");
					packet = this.packetTitle.getConstructor(this.packetActions, this.chatBaseComponent).newInstance(actions[1], serialized);
					sendPacket.invoke(connection, packet);
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	public void broadcast()
	{
		for (final Player p : Bukkit.getOnlinePlayers())
		{
			this.send(p);
		}
	}

	public void clearTitle(final Player player)
	{
		try
		{
			final Object handle = this.getHandle(player);
			final Object connection = this.getField(handle.getClass(), "playerConnection").get(handle);
			final Object[] actions = (Object[]) this.packetActions.getEnumConstants();
			final Method sendPacket = this.getMethod(connection.getClass(), "sendPacket", (Class<?>[]) new Class[0]);
			final Object packet = this.packetTitle.getConstructor(this.packetActions, this.chatBaseComponent).newInstance(actions[3], null);
			sendPacket.invoke(connection, packet);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void resetTitle(final Player player)
	{
		try
		{
			final Object handle = this.getHandle(player);
			final Object connection = this.getField(handle.getClass(), "playerConnection").get(handle);
			final Object[] actions = (Object[]) this.packetActions.getEnumConstants();
			final Method sendPacket = this.getMethod(connection.getClass(), "sendPacket", (Class<?>[]) new Class[0]);
			final Object packet = this.packetTitle.getConstructor(this.packetActions, this.chatBaseComponent).newInstance(actions[4], null);
			sendPacket.invoke(connection, packet);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private Class<?> getPrimitiveType(final Class<?> clazz)
	{
		return Title.CORRESPONDING_TYPES.containsKey(clazz) ? Title.CORRESPONDING_TYPES.get(clazz) : clazz;
	}

	@SuppressWarnings("rawtypes")
	private Class<?>[] toPrimitiveTypeArray(final Class<?>[] classes)
	{
		final int a = (classes != null) ? classes.length : 0;
		final Class[] types = new Class[a];
		for (int i = 0; i < a; ++i)
		{
			types[i] = this.getPrimitiveType(classes[i]);
		}
		return (Class<?>[]) types;
	}

	private static boolean equalsTypeArray(final Class<?>[] a, final Class<?>[] o)
	{
		if (a.length != o.length)
		{
			return false;
		}
		for (int i = 0; i < a.length; ++i)
		{
			if (!a[i].equals(o[i]) && !a[i].isAssignableFrom(o[i]))
			{
				return false;
			}
		}
		return true;
	}

	private Object getHandle(final Object obj)
	{
		try
		{
			return this.getMethod("getHandle", obj.getClass(), (Class<?>[]) new Class[0]).invoke(obj, new Object[0]);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	private Method getMethod(final String name, final Class<?> clazz, final Class<?>... paramTypes)
	{
		final Class[] t = this.toPrimitiveTypeArray(paramTypes);
		Method[] methods;
		for (int length = (methods = clazz.getMethods()).length, i = 0; i < length; ++i)
		{
			final Method m = methods[i];
			final Class[] types = this.toPrimitiveTypeArray(m.getParameterTypes());
			if (m.getName().equals(name) && equalsTypeArray(types, t))
			{
				return m;
			}
		}
		return null;
	}

	private String getVersion()
	{
		final String name = Bukkit.getServer().getClass().getPackage().getName();
		final String version = String.valueOf(name.substring(name.lastIndexOf(46) + 1)) + ".";
		return version;
	}

	private Class<?> getNMSClass(final String className)
	{
		final String fullName = "net.minecraft.server." + this.getVersion() + className;
		Class<?> clazz = null;
		try
		{
			clazz = Class.forName(fullName);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return clazz;
	}

	private Field getField(final Class<?> clazz, final String name)
	{
		try
		{
			final Field field = clazz.getDeclaredField(name);
			field.setAccessible(true);
			return field;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	private Method getMethod(final Class<?> clazz, final String name, final Class<?>... args)
	{
		Method[] methods;
		for (int length = (methods = clazz.getMethods()).length, i = 0; i < length; ++i)
		{
			final Method m = methods[i];
			if (m.getName().equals(name) && (args.length == 0 || this.ClassListEqual(args, m.getParameterTypes())))
			{
				m.setAccessible(true);
				return m;
			}
		}
		return null;
	}

	private boolean ClassListEqual(final Class<?>[] l1, final Class<?>[] l2)
	{
		boolean equal = true;
		if (l1.length != l2.length)
		{
			return false;
		}
		for (int i = 0; i < l1.length; ++i)
		{
			if (l1[i] != l2[i])
			{
				equal = false;
				break;
			}
		}
		return equal;
	}
}
