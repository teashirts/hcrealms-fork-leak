package me.stafftp.hcf.helpers.commands;


import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.stafftp.hcf.helpers.listener.DeathListener;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.util.org.apache.commons.lang3.StringUtils;


public class RefundCommand implements CommandExecutor
{
    public boolean onCommand(final CommandSender cs, final Command cmd, final String s, final String[] args) {
        @SuppressWarnings("unused")
		final String Usage = ChatColor.RED + "/" + s + " <playerName>";
        if (!(cs instanceof Player)) {
            cs.sendMessage(ChatColor.RED + "You must be a player");
            return true;
        }
        final Player p = (Player)cs;
        if (Bukkit.getPlayer(args[0]) == null) {
            p.sendMessage(ChatColor.RED + "That player is not currently online.");
            return true;
        }
        final Player target = Bukkit.getPlayer(args[0]);
        //Check if player has an inventory saved.
        if (DeathListener.PlayerInventoryContents.containsKey(target.getUniqueId())) {
            target.getInventory().setContents((ItemStack[])DeathListener.PlayerInventoryContents.get(target.getUniqueId()));
            target.getInventory().setArmorContents((ItemStack[])DeathListener.PlayerArmorContents.get(target.getUniqueId()));
            @SuppressWarnings("unused")
			final String reason = StringUtils.join((Object[])args, ' ', 2, args.length);
            Command.broadcastCommandMessage((CommandSender)p, ChatColor.GREEN + "You have returned " + target.getName() + "'s items.");
            DeathListener.PlayerArmorContents.remove(target.getUniqueId());
            DeathListener.PlayerInventoryContents.remove(target.getUniqueId());
            return true;
        }
        //If player does not have an inventory saved.
        p.sendMessage(ChatColor.RED.toString() + target.getName() + "'s inventory has already been rolled back by another staff member.");
        return false;
    }
}

