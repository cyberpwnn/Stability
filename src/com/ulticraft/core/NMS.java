package com.ulticraft.core;

import java.lang.reflect.Field;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_9_R1.EntityPlayer;
import net.minecraft.server.v1_9_R1.IChatBaseComponent;
import net.minecraft.server.v1_9_R1.PacketPlayOutChat;
import net.minecraft.server.v1_9_R1.PacketPlayOutEntityVelocity;
import net.minecraft.server.v1_9_R1.PacketPlayOutGameStateChange;
import net.minecraft.server.v1_9_R1.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_9_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_9_R1.PlayerConnection;

public class NMS
{
	public static void sendTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle)
	{
		PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
		PacketPlayOutTitle packetPlayOutTimes = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, null, fadeIn.intValue(), stay.intValue(), fadeOut.intValue());
		connection.sendPacket(packetPlayOutTimes);
		
		if(subtitle != null)
		{
			subtitle = subtitle.replaceAll("%player%", player.getDisplayName());
			subtitle = ChatColor.translateAlternateColorCodes((char) '&', (String) subtitle);
			IChatBaseComponent titleSub = IChatBaseComponent.ChatSerializer.a((String) ("{\"text\": \"" + subtitle + "\"}"));
			PacketPlayOutTitle packetPlayOutSubTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, titleSub);
			connection.sendPacket(packetPlayOutSubTitle);
		}
		
		if(title != null)
		{
			title = title.replaceAll("%player%", player.getDisplayName());
			title = ChatColor.translateAlternateColorCodes((char) '&', (String) title);
			IChatBaseComponent titleMain = IChatBaseComponent.ChatSerializer.a((String) ("{\"text\": \"" + title + "\"}"));
			PacketPlayOutTitle packetPlayOutTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, titleMain);
			connection.sendPacket(packetPlayOutTitle);
		}
	}
	
	public static int ping(Player p)
	{
		return ((CraftPlayer) p).getHandle().ping;
	}
	
	public static void sendTabTitle(Player player, String header, String footer)
	{
		if(header == null)
		{
			header = "";
		}
		
		header = ChatColor.translateAlternateColorCodes((char) '&', (String) header);
		
		if(footer == null)
		{
			footer = "";
		}
		
		footer = ChatColor.translateAlternateColorCodes((char) '&', (String) footer);
		header = header.replaceAll("%player%", player.getDisplayName());
		footer = footer.replaceAll("%player%", player.getDisplayName());
		PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
		IChatBaseComponent tabTitle = IChatBaseComponent.ChatSerializer.a((String) ("{\"text\": \"" + header + "\"}"));
		IChatBaseComponent tabFoot = IChatBaseComponent.ChatSerializer.a((String) ("{\"text\": \"" + footer + "\"}"));
		PacketPlayOutPlayerListHeaderFooter headerPacket = new PacketPlayOutPlayerListHeaderFooter(tabTitle);
		
		try
		{
			Field field = headerPacket.getClass().getDeclaredField("b");
			field.setAccessible(true);
			field.set((Object) headerPacket, (Object) tabFoot);
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		finally
		{
			connection.sendPacket(headerPacket);
		}
	}
	
	public static void sendActionBar(Player player, String message)
	{
		CraftPlayer p = (CraftPlayer) player;
		IChatBaseComponent cbc = IChatBaseComponent.ChatSerializer.a((String) ("{\"text\": \"" + message + "\"}"));
		PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte) 2);
		p.getHandle().playerConnection.sendPacket(ppoc);
	}
	
	public static void sendEndCredits(Player p)
	{
		CraftPlayer craft = (CraftPlayer) p;
		EntityPlayer nms = craft.getHandle();
				
		nms.viewingCredits = true;
		nms.playerConnection.sendPacket(new PacketPlayOutGameStateChange(4, 0.0F));
	}
	
	public static void sendEntityVelocity(int id, double x, double y, double z, Player player)
	{
		PacketPlayOutEntityVelocity packet = new PacketPlayOutEntityVelocity(id, x, y, z);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	}
}
