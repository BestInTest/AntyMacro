package me.antymacro;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import me.antymacro.listeners.ClickListener;
import me.antymacro.listeners.JoinQuitListener;
import me.antymacro.tasks.ResetTask;

public class Main extends JavaPlugin {
	
	private static Main plugin;
	
	public static Main getPlugin() {
		return plugin;
	}
	
	public void onEnable() {
		plugin = this;
		saveDefaultConfig();
		Bukkit.getPluginManager().registerEvents(new JoinQuitListener(), this);
		Bukkit.getPluginManager().registerEvents(new ClickListener(), this);
		new ResetTask().runTaskTimer(this, 20, 20);
		getLogger().info("[AntyMacro] CM-AntyMacro Edited by BestInTest <3");
		getLogger().info("Original source can be found here: https://github.com/vexiowski/AntyMacro");
	}

}
