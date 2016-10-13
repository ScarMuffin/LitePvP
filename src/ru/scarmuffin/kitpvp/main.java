package ru.scarmuffin.kitpvp;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class main extends JavaPlugin {
    Logger logger = Logger.getLogger("Minecraft");
    PluginManager pm;
    FileConfiguration config;
    static String world;
    static Integer misSpawnY;
    static Boolean disableWeather;
    static Boolean disableInteractions;

    public main() {
        this.pm = this.getServer().getPluginManager();
        this.config = this.getConfig();
    }
	public void onEnable(){
		Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "[KitPvP]" + ChatColor.GREEN + "Plugin Enabled");
	}
	public void onDisable(){
		Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "[KitPvP]" + ChatColor.GREEN + "Disabled");
	}
    public void registerEvents() {
        this.pm.registerEvents((Listener)new events(), (Plugin)this);
        this.logger.info(ChatColor.GREEN + "[KitPvP] Events loaded!");
    }

    private void registerConfig() {
        this.config.options().copyDefaults(true);
        this.config.options().copyHeader(true);
        this.saveConfig();
        this.logger.info(ChatColor.GREEN + "[KitPvP] Config loaded!");
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        Player p = null;
        if (!(sender instanceof Player)) {
            sender.sendMessage("\u00a7cOnly for players!");
            return false;
        }
        p = (Player)sender;
        if (cmd.getName().equalsIgnoreCase("setspawnlevel") && p.hasPermission("lobby.admin")) {
            if (p.getLocation().getBlockY() > 0 && p.getLocation().getBlockY() < 255) {
                Integer ylevel;
                misSpawnY = ylevel = Integer.valueOf(p.getLocation().getBlockY());
                String world = p.getWorld().getName();
                this.config.set("SpawnProtection.world", (Object)world);
                this.config.set("SpawnProtection.minSpawnY", (Object)ylevel);
                this.saveConfig();
                p.sendMessage("\u00a78[\u00a7aPixelNoPvpZone\u00a78] \u00a7fminSpawnY \u0443\u0441\u0442\u0430\u043d\u043e\u0432\u043b\u0435\u043d \u043d\u0430 \u0431\u043b\u043e\u043a\u0438 \u0443\u0440\u043e\u0432\u043d\u044f \u00a7a" + ylevel + " \u00a7f\u0432 \u043c\u0438\u0440\u0435 \u00a7a" + world);
            } else {
                p.sendMessage("\u00a78[\u00a7aPixelNoPvpZone\u00a78] \u00a7f\u041d\u0435\u0432\u0435\u0440\u043d\u0430\u044f \u043b\u043e\u043a\u0430\u0446\u0438\u044f!");
            }
        }
        return false;
    }
}