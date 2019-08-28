package me.stafftp.hcf.helpers.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pls.carbon.hcf.Base;


public class PartnerCommand implements CommandExecutor{
    
	public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This is a player only command");
            return true;
        }
        sender.sendMessage(ChatColor.GRAY + ChatColor.STRIKETHROUGH.toString() + "--------------------------------");
        sender.sendMessage(ChatColor.AQUA + ChatColor.BOLD.toString() + ChatColor.GOLD + "Partner rank Requirements");
        sender.sendMessage(ChatColor.AQUA + ChatColor.BOLD.toString() + ChatColor.GOLD + "Sub Requirement" + " » " + ChatColor.WHITE + Base.getPlugin(Base.class).getConfig().getString("partner_subs"));
        sender.sendMessage(ChatColor.AQUA + ChatColor.BOLD.toString() + ChatColor.GOLD + "View Requirement" +" » " + ChatColor.WHITE + Base.getPlugin(Base.class).getConfig().getString("partner_views"));
        sender.sendMessage(ChatColor.GRAY + ChatColor.STRIKETHROUGH.toString() + "--------------------------------");
        return false;
    }
}
