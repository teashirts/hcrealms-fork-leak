package me.stafftp.hcf.helpers.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CraftCommand implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cYou are not a player!");
            return true;
        }
        final Player p = (Player)sender;
        if (!p.hasPermission("command.craft")) {
            p.sendMessage("§cYou don't have permission.");
            return true;
        }
        p.openWorkbench(p.getLocation(), true);
        return true;
    }
}
