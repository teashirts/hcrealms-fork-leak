package me.stafftp.hcf.helpers.commands;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.stafftp.hcf.factions.faction.type.PlayerFaction;
import me.stafftp.hcf.util.BukkitUtils;
import me.stafftp.hcf.util.base.Cooldowns;
import pls.carbon.hcf.Base;

@SuppressWarnings("unused")
public class LFACommand implements CommandExecutor {

private final Base plugin;
public LFACommand(Base plugin) {
	this.plugin = plugin;
}

    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This is a player only command");
            return true;
        }
        final Player player = (Player) sender;
        //final PlayerFaction playerFaction = this.plugin.getFactionManager().getPlayerFaction(player);
        //if(playerFaction == null) {
        //    sender.sendMessage(ChatColor.RED + "You are not in a faction.");
        //    return true;
        //}
        if (Cooldowns.isOnCooldown("lfa_cooldown", (Player)sender)) {
            sender.sendMessage(ChatColor.RED + "You cannot use this for another " + ChatColor.RED + ChatColor.BOLD.toString() + Base.getRemaining(Cooldowns.getCooldownForPlayerLong("lfa_cooldown", (Player)sender), true));
            return true;
        }
        Bukkit.broadcastMessage(ChatColor.DARK_GRAY + ChatColor.STRIKETHROUGH.toString() + "------------------------------");
        Bukkit.broadcastMessage(ChatColor.GOLD + ChatColor.BOLD.toString() + sender.getName() + ChatColor.YELLOW + " is looking for a " + ChatColor.GREEN + ChatColor.BOLD.toString() + "ALLY" + ChatColor.YELLOW + ".");
        Bukkit.broadcastMessage(ChatColor.DARK_GRAY + ChatColor.STRIKETHROUGH.toString() + "------------------------------");
        Cooldowns.addCooldown("lfa_cooldown", (Player)sender, 900);
        return false;
        
    }
    
}
