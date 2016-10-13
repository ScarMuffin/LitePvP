package ru.pixelfine.kitpvp;

import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin {
	public static Logger logger = Logger.getLogger("Minecraft");
    	public static String prefix = "§8[§aKitPvP§8] §f";
	PluginManager pm = this.getServer().getPluginManager();
    	FileConfiguration config = getConfig();
    	static String world;
    	static Integer misSpawnY;
    	static Boolean disableWeather;
    	static Boolean disableInteractions;

	public void onEnable(){
		logger.info("[KitPvP] Plugin Enabled");
	}
	
	public void onDisable(){
		logger.info("[KitPvP] Plugin currently disabled");
	}
	
    	public void registerEvents() {
        	pm.registerEvents(new events(), this);
        	logger.info("[KitPvP] Events loaded!");
    	}

    	private void registerConfig() {
        	config.options().copyDefaults(true);
       		config.options().copyHeader(true);
        	saveConfig();
       		logger.info("[KitPvP] Config loaded!");
   	}

    	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        	Player p = null;
        	if (!(sender instanceof Player)) {
            		sender.sendMessage("§cOnly for players!");
            		return false;
        	}
        	p = (Player)sender;
        	if (cmd.getName().equalsIgnoreCase("setspawnlevel") && p.hasPermission("lobby.admin")) {
            		if (p.getLocation().getBlockY() > 0 && p.getLocation().getBlockY() < 255) {
               			Integer ylevel;
               			misSpawnY = ylevel = Integer.valueOf(p.getLocation().getBlockY());
              			String world = p.getWorld().getName();
                		this.config.set("SpawnProtection.world", world);
                		this.config.set("SpawnProtection.minSpawnY", ylevel);
                		this.saveConfig();
                		p.sendMessage("\u00a78[\u00a7aPixelNoPvpZone\u00a78] \u00a7fminSpawnY \u0443\u0441\u0442\u0430\u043d\u043e\u0432\u043b\u0435\u043d \u043d\u0430 \u0431\u043b\u043e\u043a\u0438 \u0443\u0440\u043e\u0432\u043d\u044f \u00a7a" + ylevel + " \u00a7f\u0432 \u043c\u0438\u0440\u0435 \u00a7a" + world);
            		} else p.sendMessage("\u00a78[\u00a7aPixelNoPvpZone\u00a78] \u00a7f\u041d\u0435\u0432\u0435\u0440\u043d\u0430\u044f \u043b\u043e\u043a\u0430\u0446\u0438\u044f!");
        	}
        	return false;
    	}
	
}
