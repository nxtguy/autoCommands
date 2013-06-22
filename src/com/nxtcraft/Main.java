package com.nxtcraft;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class Main extends JavaPlugin {
	
	public static BukkitTask timer;
	public static int timerID;
	
	public static int waitTime;
	
	public static JavaPlugin plugin;
	
	public static List<String> taskCommands;
	
	@SuppressWarnings("unchecked")
	@Override
	public void onEnable(){
		PluginDescriptionFile pdfFile = getDescription();
		plugin = this;
		
		Bukkit.getLogger().info(pdfFile.getName() + " v" + pdfFile.getVersion() + " has been enabled.");
		
		plugin.getConfig().options().copyDefaults(true);
		saveConfig();
		
		if (plugin.getConfig().getBoolean("Task.enable") == true) {
			waitTime = plugin.getConfig().getInt("Task.time");
			
			waitTime = waitTime * 20;
			
			taskCommands = (List<String>) plugin.getConfig().getList("Task.commands");
			
			Runnable timerTask = new Timer();
			timer = Bukkit.getServer().getScheduler().runTaskTimer(plugin, timerTask, 0L, waitTime);
			timerID = timer.getTaskId();
		} else {
			Bukkit.getLogger().info("Task not enabled in the config!");
		}
		
		
	}
	
	@Override
	public void onDisable(){
		PluginDescriptionFile pdfFile = getDescription();
		
		Bukkit.getLogger().info(pdfFile.getName() + " v" + pdfFile.getVersion() + " has been disabled.");
	}
	
	

}
