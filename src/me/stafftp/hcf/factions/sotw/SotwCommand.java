package me.stafftp.hcf.factions.sotw;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.google.common.collect.ImmutableList;

import me.stafftp.hcf.util.BukkitUtils;
import me.stafftp.hcf.util.JavaUtils;
import net.minecraft.util.org.apache.commons.lang3.time.DurationFormatUtils;
import pls.carbon.hcf.Base;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class SotwCommand implements CommandExecutor, TabCompleter {

	private static List<String> COMPLETIONS = ImmutableList.of("start", "end");

	private final Base plugin;
	public static ArrayList<UUID> enabled;

	public SotwCommand(Base plugin) {
		this.plugin = plugin;
	}

	@SuppressWarnings("unlikely-arg-type")
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = (Player)sender;
		if (!sender.hasPermission("sotw.admin")) {
			if (args.length != 1 || !args[0].equalsIgnoreCase("enable")) {
				sender.sendMessage("§cYou have sent a incorrect argument");
				return true;
			}
			if (plugin.getSotwTimer().getSotwRunnable() == null) {
				sender.sendMessage("§cSOTW is currently not active");
				return true;
			}
			if (enabled.contains(sender)) {
				sender.sendMessage("§cYou have already enabled your SOTW");
				return true;
			}
			enabled.add(player.getUniqueId());
			player.sendMessage("§cYou have enabled your SOTW, you may now PvP, Becareful!");
			return true;
		}
		if (args.length > 0) {
			if (args[0].equalsIgnoreCase("start")) {
				if (args.length < 2) {
					sender.sendMessage(
							ChatColor.RED + "Usage: /" + label + " " + args[0].toLowerCase() + " <duration>");
					return true;
				}

				long duration = JavaUtils.parse(args[1]);

				if (duration == -1L) {
					sender.sendMessage(ChatColor.RED + "'" + args[0] + "' is an invalid duration.");
					return true;
				}

				if (duration < 1000L) {
					sender.sendMessage(ChatColor.RED + "SOTW protection time must last for at least 20 ticks.");
					return true;
				}

				SotwTimer.SotwRunnable sotwRunnable = plugin.getSotwTimer().getSotwRunnable();

				if (sotwRunnable != null) {
					sender.sendMessage(
							ChatColor.RED + "SOTW protection is already enabled, use /" + label + " cancel to end it.");
					return true;
				}

				plugin.getSotwTimer().start(duration);
				sender.sendMessage(
						ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + "-------------------------------------");
				sender.sendMessage(ChatColor.GRAY + "");
				sender.sendMessage(ChatColor.RED + "Started SOTW protection for "
						+ DurationFormatUtils.formatDurationWords(duration, true, true) + ".");
				sender.sendMessage(ChatColor.GRAY + "");
				sender.sendMessage(
						ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + "-------------------------------------");
				return true;
			}

			if (args[0].equalsIgnoreCase("end") || args[0].equalsIgnoreCase("cancel")) {
				if (plugin.getSotwTimer().cancel()) {
					sender.sendMessage(ChatColor.RED + "Cancelled SOTW protection.");
					return true;
				}

				sender.sendMessage(ChatColor.RED + "SOTW protection is not active.");
				return true;
			}else if (args[0].equalsIgnoreCase("enable")) {
				if (plugin.getSotwTimer().getSotwRunnable() == null) {
					sender.sendMessage("sotw isnt active");
					return true;
				}
				if (enabled.contains(sender)) {
					sender.sendMessage("§cYou already enabled ur sotw timer");
					return true;
				}
				enabled.add(player.getUniqueId());
				player.sendMessage("§cYou have enabled your SOTW, you may now PvP, Becareful!");
				return true;
			}
		}

		sender.sendMessage(ChatColor.RED + "Usage: /" + label + " <start|end>");
		return true;
	}

	public List<String> onTabComplete(final CommandSender sender, final Command command, final String label,
			final String[] args) {
		return (args.length == 1) ? BukkitUtils.getCompletions(args, SotwCommand.COMPLETIONS) : Collections.emptyList();
	}

	static {
		COMPLETIONS = (List) ImmutableList.of((Object) "start", (Object) "end");
		SotwCommand.enabled = new ArrayList<UUID>();
	}
}
