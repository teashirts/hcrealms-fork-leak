package me.stafftp.hcf.special.keyall;

import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import me.stafftp.hcf.util.BukkitUtils;
import me.stafftp.hcf.util.JavaUtils;
import net.minecraft.util.org.apache.commons.lang3.time.DurationFormatUtils;
import pls.carbon.hcf.Base;

public class KeyallCommand implements CommandExecutor, TabCompleter {

	private final Base plugin;

	public KeyallCommand(Base plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!sender.hasPermission("command.sale")) {
			sender.sendMessage(ChatColor.RED + "No permission.");
			return true;
		}

		if (args.length == 0 || args.length > 2) {
            sender.sendMessage(
                    "§9§m--------------------------------");
            sender.sendMessage(
            		"§c/keyall start <duration> - Starts the keyall sale timer.");
            sender.sendMessage(
            		"§c/keyall end - Ends the current keyall sale timer.");
            sender.sendMessage(
                    "§9§m--------------------------------");
			return true;
		}

		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("end")) {
				if (plugin.getTimerManager().getKeyallTimer().clearCooldown()) {
					Bukkit.broadcastMessage(ChatColor.GOLD.toString() + ChatColor.STRIKETHROUGH
							+ BukkitUtils.STRAIGHT_LINE_DEFAULT);
					Bukkit.broadcastMessage(ChatColor.YELLOW + "The keyall has been cancelled!");
					Bukkit.broadcastMessage(ChatColor.GOLD.toString() + ChatColor.STRIKETHROUGH
							+ BukkitUtils.STRAIGHT_LINE_DEFAULT);
					return true;
				}
			} else {
				sender.sendMessage(ChatColor.RED + "Unknown sub-command!");
				return true;
			}
		}

		if (args.length == 2) {
			if (args[0].equalsIgnoreCase("start")) {
				long duration = JavaUtils.parse(args[1]);

				if (duration == -1L) {
					sender.sendMessage(ChatColor.RED + "'" + args[0] + "' is an invalid duration.");
					return true;
				}

				if (duration < 1000L) {
					sender.sendMessage(ChatColor.RED + "keyall time must last for at least 20 ticks.");
					return true;
				}

				KeyallTimer keyalltimer = plugin.getTimerManager().getKeyallTimer();

				if (!keyalltimer.setRemaining(duration, true)) {
					sender.sendMessage(ChatColor.RED + "The keyall is already on.");
					return true;
				}

				sender.sendMessage(ChatColor.GREEN + "Started keyall timer for "
						+ DurationFormatUtils.formatDurationWords(duration, true, true) + ".");

				Bukkit.broadcastMessage(ChatColor.GRAY + "The keyall " + ChatColor.GREEN.toString() + ChatColor.BOLD + "sale " + ChatColor.GRAY + "has started "
						+ ChatColor.GRAY + " for " + DurationFormatUtils.formatDurationWords(duration, true, true) + ChatColor.GRAY + ".");
			} else {
				sender.sendMessage(ChatColor.RED + "Unknown sub-command!");
				return true;
			}
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		return Collections.emptyList();
	}
}
