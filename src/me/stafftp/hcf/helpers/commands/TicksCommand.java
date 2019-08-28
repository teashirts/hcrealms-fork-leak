package me.stafftp.hcf.helpers.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class TicksCommand implements CommandExecutor{

	private String format(double tps) {
        return (tps > 18.0 ? ChatColor.GRAY : tps > 16.0 ? ChatColor.RED : ChatColor.DARK_RED).toString()
        		+ ((tps > 20.0) ? "*" : "") + Math.min(Math.round(tps * 100.0)/100.0, 20.0);
    }
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "This is a player only command");
			return true;
		}
		sender.sendMessage(ChatColor.GOLD + "Current TPS" + " " + this.format(Bukkit.getServer().spigot().getTPS()[0]));
		return false; 
	}
}