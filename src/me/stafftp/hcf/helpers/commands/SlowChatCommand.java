package me.stafftp.hcf.helpers.commands;

import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import me.stafftp.hcf.util.JavaUtils;
import me.stafftp.hcf.util.base.BaseCommand;
import pls.carbon.hcf.Base;


public class SlowChatCommand extends BaseCommand {
    private static final long DEFAULT_DELAY;

    static {
        DEFAULT_DELAY = TimeUnit.MINUTES.toMillis(5L);
    }

    private final Base plugin;

    public SlowChatCommand(final Base plugin) {
        super("slowchat", "Slows the chat down for non-staff.");
        this.setAliases(new String[]{"slow"});
        this.setUsage("/(command)");
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        final long oldTicks = this.plugin.getServerHandler().getRemainingChatSlowedMillis();
        Long newTicks;
        if (oldTicks > 0L) {
            newTicks = 0L;
        } else if (args.length < 1) {
            newTicks = SlowChatCommand.DEFAULT_DELAY;
        } else {
            newTicks = JavaUtils.parse(args[0]);
            if (newTicks == -1L) {
                sender.sendMessage(ChatColor.RED + "Invalid duration, use the correct format: 10m1s");
                return true;
            }
        }
        this.plugin.getServerHandler().setChatSlowedMillis(newTicks);
        Bukkit.broadcastMessage(ChatColor.YELLOW + "Global chat " + (newTicks > 0L ? "has now been slowed for " + ChatColor.GOLD + DurationFormatUtils.formatDurationWords(newTicks, true, true) : "is no longer " + ChatColor.GOLD + "slowed"));
        return true;
    }
}