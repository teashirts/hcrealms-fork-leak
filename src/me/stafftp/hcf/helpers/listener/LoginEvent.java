package me.stafftp.hcf.helpers.listener;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.stafftp.hcf.ConfigurationService;
import net.md_5.bungee.api.ChatColor;

public class LoginEvent implements Listener {
	
	@EventHandler
	public void Join(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		p.playSound(p.getLocation(), Sound.LEVEL_UP, 2F, 1F);

		if (!p.hasPlayedBefore()) {
			Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&7Welcome &r"  + p.getDisplayName() + "&7 to " + ConfigurationService.PRIMAIRY_COLOR + ConfigurationService.NAME + "&f! &7(#" + String.valueOf(Bukkit.getOfflinePlayers().length) + "&7)"));
		}
		p.sendMessage(ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + "------------------------------------");
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', ConfigurationService.PRIMAIRY_COLOR + "Welcome to " + ChatColor.BOLD + ConfigurationService.NAME + ChatColor.GRAY + " (" + ConfigurationService.MAP_NUMBER + ChatColor.GRAY + ")"));
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', ChatColor.GOLD + " » " + ConfigurationService.SECONDAIRY_COLOR + "Faction Size: " + ChatColor.GRAY + ConfigurationService.Faction_SIZE));
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', ChatColor.GOLD + " » " + ConfigurationService.SECONDAIRY_COLOR + "Map Kit: " + ChatColor.GRAY + ConfigurationService.MAP_KIT));
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', ChatColor.GOLD + " » " + ConfigurationService.SECONDAIRY_COLOR + "Teamspeak: " + ChatColor.GRAY + ConfigurationService.TEAMSPEAK_URL));
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', ChatColor.GOLD + " » " + ConfigurationService.SECONDAIRY_COLOR + "Website: " + ChatColor.GRAY + ConfigurationService.WEBSITE_URL));
		p.sendMessage(ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + "------------------------------------");
	}
}