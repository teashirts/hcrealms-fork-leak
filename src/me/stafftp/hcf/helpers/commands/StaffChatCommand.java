package me.stafftp.hcf.helpers.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.stafftp.hcf.helpers.listener.StaffChatListener;

public class StaffChatCommand implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Only Players Can Use This CMD!");
			return true;
		}
		Player p = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("StaffChat")) {
			if (!p.hasPermission("command.staffchat")) {
				p.sendMessage(ChatColor.RED + "You cannot execute this command.");
				return true;
			}
			if (StaffChatListener.getInstance().isInStaffChat(p)) {
				StaffChatListener.getInstance().setStaffChat(p, false);
				sender.sendMessage("§e§lStaffChat: §cfalse");
			} else {
				StaffChatListener.getInstance().setStaffChat(p, true);
				sender.sendMessage("§e§lStaffChat: §atrue");
			}
		}
		return true;
	}

}
