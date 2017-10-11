package com.buildingwithbukkit.example;

import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.permission.Permission;

/**
 * This is a quick example plugin with a singleton instance (singleton is optional!)<br>
 * It also shows an example using the VaultAPI
 */
public class ExamplePlugin extends JavaPlugin implements Listener {

	private static ExamplePlugin instance;
	public static ExamplePlugin getInstance() { return instance; }
	public ExamplePlugin() { instance = this; }
	
	private Permission perms = null;
	
	@Override
	public void onEnable() {
		// Just say hello!
		this.getLogger().log(Level.INFO, "Get building with the bukkit API!");
		
		if (!this.setupPermissions()) {
			this.getLogger().log(Level.WARNING, "Couldn't find a permissions provider!");
		}
	}
	
	private boolean setupPermissions() {
		RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
		this.perms = rsp.getProvider();
		return this.perms != null;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		if (this.perms == null) {
			// No permissions provider :'(
			return;
		}
		
		// Use the VaultAPI to see if they have a permission
		if (this.perms.has(event.getPlayer(), "example.intro")) {
			event.getPlayer().sendMessage(ChatColor.GOLD + "Welcome!");
		}
	}
	
}
