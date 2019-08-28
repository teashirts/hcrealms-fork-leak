package me.stafftp.hcf.helpers.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.stafftp.hcf.helpers.listener.MediaChatListener;

public class MediaChatCommand implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Only Players Can Use This CMD!");
			return true;
		}
		Player p = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("MediaChat")) {
			if (!p.hasPermission("command.mediachat")) {
				p.sendMessage(ChatColor.RED + "You cannot execute this command.");
				return true;
			}
			if (MediaChatListener.getInstance().isInMediaChat(p)) {
				MediaChatListener.getInstance().setMediaChat(p, false);
				sender.sendMessage("§e§lMedia-Chat: §cfalse");
			} else {
				MediaChatListener.getInstance().setMediaChat(p, true);
				sender.sendMessage("§e§lMedia-Chat: §atrue");
			}
		}
		return true;
	}

}