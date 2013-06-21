package com.nxtcraft;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class Main extends JavaPlugin {
	
	public static BukkitTask timer;
	
	public static JavaPlugin plugin;
	
	@Override
	public void onEnable(){
		PluginDescriptionFile pdfFile = getDescription();
		plugin = this;
		
		Bukkit.getLogger().info(pdfFile.getName() + " v" + pdfFile.getVersion() + " has been enabled.");
		
		Runnable timerTask = new Timer();
		timer = Bukkit.getServer().getScheduler().runTaskTimer(plugin, timerTask, 0L, 60L);
	}
	
	@Override
	public void onDisable(){
		PluginDescriptionFile pdfFile = getDescription();
		
		Bukkit.getLogger().info(pdfFile.getName() + " v" + pdfFile.getVersion() + " has been disabled.");
	}

}
