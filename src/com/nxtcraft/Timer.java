package com.nxtcraft;

import org.bukkit.Bukkit;

public class Timer implements Runnable {

	@Override
	public void run() {
		Bukkit.getLogger().info("autoCommands timer ran!");
		
		int x = 0;
		while (Main.taskCommands.size() > x){
			Main.plugin.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), Main.taskCommands.get(x));
			
			x++;
		}

	}

}
