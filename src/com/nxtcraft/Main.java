package com.nxtcraft;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
	
	@Override
	public void onEnable(){
		PluginDescriptionFile pdfFile = getDescription();
		
		Bukkit.getLogger().info(pdfFile.getName() + " v" + pdfFile.getVersion() + " has been enabled.");
	}
	
	@Override
	public void onDisable(){
		PluginDescriptionFile pdfFile = getDescription();
		
		Bukkit.getLogger().info(pdfFile.getName() + " v" + pdfFile.getVersion() + " has been disabled.");
	}

}
