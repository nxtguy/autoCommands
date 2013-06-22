package com.nxtcraft;

import java.util.List;

import me.nxtguy.updater.Updater;
import me.nxtguy.updater.Updater.UpdateResult;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class Main extends JavaPlugin {
	
	public static BukkitTask timer;
	public static int timerID;
	
	public static int waitTime;
	
	public static JavaPlugin plugin;
	
	public static List<String> taskCommands;
	
	//Updater Class
		public static UpdateResult update;
		public static String name = "";
		public static String version;
		public static long size = 0;
	
	@SuppressWarnings("unchecked")
	@Override
	public void onEnable(){
		PluginDescriptionFile pdfFile = getDescription();
		plugin = this;
		Bukkit.getLogger().info("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
		Bukkit.getLogger().info(pdfFile.getName() + " v" + pdfFile.getVersion() + " has been enabled.");
		
		//Check if a new update is available
				long startTime = System.currentTimeMillis();
				Bukkit.getLogger().info("Checking for update from BukkitDev...");
				if (plugin.getConfig().getBoolean("updateCheck")){
					Updater updater = new Updater(plugin, "autocommands", this.getFile(), Updater.UpdateType.NO_DOWNLOAD, false); // Start Updater but just do a version check
					update = updater.getResult();
					if (update == Updater.UpdateResult.UPDATE_AVAILABLE) {
						name = updater.getLatestVersionString(); // Get the latest version
						size = updater.getFileSize(); // Get latest size
						version = updater.getLatestVersionString().substring(updater.getLatestVersionString().lastIndexOf('v') + 1);
						Bukkit.getLogger().info("There is a new update available!");
						Bukkit.getLogger().info("File name: " + name);
						Bukkit.getLogger().info("Latest Version: " + version);
						Bukkit.getLogger().info("File size: " + size);
					} else if (updater.getResult() == Updater.UpdateResult.NO_UPDATE){
						Bukkit.getLogger().info("You have the latest version of autoCommands. (Yay)");
					}
				} else {
					Bukkit.getLogger().info("WARNING: You have disabled update checking in the configuration file!");
					Bukkit.getLogger().info("This could be preventing you from downloading an important update.");
				}
				long endTime = System.currentTimeMillis();
				long duration = endTime - startTime;
				Bukkit.getLogger().info("-Update Check Time: " + duration + "ms-");
				Bukkit.getLogger().info("");
		
		
		plugin.getConfig().options().copyDefaults(true);
		saveConfig();
		
		if (plugin.getConfig().getBoolean("Task.enable") == true) {
			waitTime = plugin.getConfig().getInt("Task.time");
			
			waitTime = waitTime * 20;
			
			taskCommands = (List<String>) plugin.getConfig().getList("Task.commands");
			
			Runnable timerTask = new Timer();
			timer = Bukkit.getServer().getScheduler().runTaskTimer(plugin, timerTask, 20L, waitTime);
			timerID = timer.getTaskId();
		} else {
			Bukkit.getLogger().info("Task not enabled in the config!");
		}
		
		Bukkit.getLogger().info("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
	}
	
	@Override
	public void onDisable(){
		PluginDescriptionFile pdfFile = getDescription();
		
		Bukkit.getLogger().info(pdfFile.getName() + " v" + pdfFile.getVersion() + " has been disabled.");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		//Define needed variables.
		PluginDescriptionFile pdfFile = getDescription();
		
		if (cmd.getName().equalsIgnoreCase("autocommands")){
			if (args.length == 0){
				sender.sendMessage(ChatColor.GOLD + "-=-=-=-=-=-autoCommands-=-=-=-=-=-");
				sender.sendMessage(ChatColor.GOLD + "/autocommands update" + ChatColor.GRAY + " Force update to the latest version.");
				sender.sendMessage(ChatColor.GOLD + "-=-=-=-=-=-{ v" + pdfFile.getVersion() + " }-=-=-=-=-=-");
				return true;
			} else if (args.length == 1) {
				if (args[0].equalsIgnoreCase("update")){
					if (sender.isOp()) {
						sender.sendMessage(ChatColor.GOLD + "Forcing update...");
						Updater updater = new Updater(this, "autocommands", this.getFile(), Updater.UpdateType.NO_VERSION_CHECK, true); // Go straight to downloading, and announce progress to console.
						Updater.UpdateResult result = updater.getResult();
				        switch(result) {
				            case SUCCESS:
				                // Success: The updater found an update, and has readied it to be loaded the next time the server restarts/reloads
				            	sender.sendMessage(ChatColor.GOLD + "Awesome! New update downloaded!");
				            	sender.sendMessage(ChatColor.GOLD + "Please reload or restart the server to apply the downloaded update.");
				                break;
				            case FAIL_DOWNLOAD:
				                // Download Failed: The updater found an update, but was unable to download it.
				            	sender.sendMessage(ChatColor.GOLD + "Oh noes! Failed to download the new update!");
				            	sender.sendMessage(ChatColor.GOLD + "Please try again later or contact us if this keeps happening.");
				                break;
				            case FAIL_DBO:
				                // dev.bukkit.org Failed: For some reason, the updater was unable to contact DBO to download the file.
				            	sender.sendMessage(ChatColor.GOLD + "Oh noes! Failed to connect to dev.bukkit.org");
				            	sender.sendMessage(ChatColor.GOLD + "Please try again later or contact us if this keeps happening.");
				                break;
						default:
							sender.sendMessage(ChatColor.GOLD + "Oh noes! Unknown Error!");
			            	sender.sendMessage(ChatColor.GOLD + "Please try again later or contact us if this keeps happening.");
							break;
				        }
						return true;
					} else {
						sender.sendMessage(ChatColor.DARK_RED + "You are not allowed to force autoCommands to update.");
						return true;
					}
				}
				return false;
			}
			
		}
		return false;
	
	}

}
