package me.stafftp.hcf.special.keyall;

import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import me.stafftp.hcf.helpers.timer.GlobalTimer;
import me.stafftp.hcf.helpers.timer.event.TimerExpireEvent;
import net.md_5.bungee.api.ChatColor;
import pls.carbon.hcf.Base;

public class KeyallTimer extends GlobalTimer implements Listener {

	@SuppressWarnings("unused")
	private final Base plugin;
	
	public KeyallTimer(Base plugin) {
		super("Keyall",  TimeUnit.SECONDS.toMillis(1L));
		this.plugin = plugin;
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onExpire(TimerExpireEvent event) {
		if (event.getTimer() == this) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "broadcast &7The Keyall &7has ended.");
		}
	}
	
	@Override
	public String getScoreboardPrefix() {
		return ChatColor.GOLD.toString() + ChatColor.BOLD.toString();
	}
	
}
