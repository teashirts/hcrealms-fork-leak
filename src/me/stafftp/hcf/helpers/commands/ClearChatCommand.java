package me.stafftp.hcf.helpers.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClearChatCommand implements CommandExecutor {

    @SuppressWarnings("deprecation")
	@Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(strings.length != 1) {
            commandSender.sendMessage(ChatColor.RED +  "/clearchat <reason>");
        }

        for (int i = 0; i <= 1000; i++) {
            Bukkit.broadcastMessage("");
        }

        for (Player online : Bukkit.getOnlinePlayers()) {
            if(!online.hasPermission("core.staff")) break;
            online.sendMessage(ChatColor.RED + commandSender.getName() + " has cleared the chat for " + strings[0]);
        }


        return true;
    }
}
