package me.stafftp.hcf.special.DoubleKey;

import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import me.stafftp.hcf.helpers.timer.GlobalTimer;
import me.stafftp.hcf.helpers.timer.event.TimerExpireEvent;
import net.md_5.bungee.api.ChatColor;
import pls.carbon.hcf.Base;

public class DoubleKeyTimer extends GlobalTimer implements Listener {

	@SuppressWarnings("unused")
	private final Base plugin;
	
	public DoubleKeyTimer(Base plugin) {
		super("DoubleKeys",  TimeUnit.SECONDS.toMillis(1L));
		this.plugin = plugin;
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onExpire(TimerExpireEvent event) {
		if (event.getTimer() == this) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "broadcast &7The &a&lDoubleKey Sale &7has ended.");
		}
	}
	
	@Override
	public String getScoreboardPrefix() {
		return ChatColor.GREEN.toString() + ChatColor.BOLD.toString();
	}
	
}
