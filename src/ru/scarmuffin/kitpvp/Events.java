package ru.scarmuffin.kitpvp;

import org.bukkit.ChatColor;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class events implements Listener {
	@EventHandler(priority = EventPriority.NORMAL)
	public void onDamage(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player && event.getEntity()).getWorld().getName().equals(main.world) && event.getEntity().getLocation().getBlockY() >= main.misSpawnY) {
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onDamage(EntityDamageByEntityEvent event) {
		Entity damager = null;
		if (event.getDamager() instanceof Player) {
			damager = event.getDamager();
		} else if (event.getDamager() instanceof Arrow) {
			Arrow arrow = (Arrow) event.getDamager();
			damager = (Entity) arrow.getShooter();
		} else if (event.getDamager() instanceof ThrownPotion) {
			ThrownPotion potion = (ThrownPotion) event.getDamager();
			damager = (Entity) potion.getShooter();
		} else return;
		if (event.getEntity() instanceof Player && damager.getWorld().getName().equals(main.world)
				&& damager.getLocation().getBlockY() >= main.misSpawnY) {
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onIntecract(PlayerInteractEvent event) {
		if (main.disableInteractions.booleanValue() && event.getAction() == Action.RIGHT_CLICK_BLOCK
				&& event.getPlayer().getWorld().getName().equals(main.world)
				&& !event.getPlayer().hasPermission("lobby.admin")) {
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void weatherChange(WeatherChangeEvent event) {
		if (main.disableWeather.booleanValue() && event.getWorld().getName().equals(main.world) && event.toWeatherState()) {
			event.setCancelled(true);
			event.getWorld().setStorm(false);
			event.getWorld().setThundering(false);
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerDeath(PlayerDeathEvent e) {
		Player killed = e.getEntity().getPlayer();
		Player killer = e.getEntity().getKiller();	
		title.show(killed, ChatColor.RED + "Âû áûëè óáèòû " + ChatColor.GOLD + killer.getDisplayName(), null);
		title.sendActionBar(killer, ChatColor.WHITE + "Âû óáèëè èãðîêà" + ChatColor.GOLD + killed.getDisplayName());
	}

}
