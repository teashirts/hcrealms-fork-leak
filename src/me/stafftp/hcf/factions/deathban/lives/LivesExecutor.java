package me.stafftp.hcf.factions.deathban.lives;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import me.stafftp.hcf.factions.deathban.lives.argument.LivesCheckArgument;
import me.stafftp.hcf.factions.deathban.lives.argument.LivesCheckDeathbanArgument;
import me.stafftp.hcf.factions.deathban.lives.argument.LivesClearDeathbansArgument;
import me.stafftp.hcf.factions.deathban.lives.argument.LivesGiveArgument;
import me.stafftp.hcf.factions.deathban.lives.argument.LivesReviveArgument;
import me.stafftp.hcf.factions.deathban.lives.argument.LivesSetArgument;
import me.stafftp.hcf.factions.deathban.lives.argument.LivesSetDeathbanTimeArgument;
import me.stafftp.hcf.util.BukkitUtils;
import me.stafftp.hcf.util.command.ArgumentExecutor;
import me.stafftp.hcf.util.command.CommandArgument;
import pls.carbon.hcf.Base;

public class LivesExecutor
  extends ArgumentExecutor
{
  public LivesExecutor(Base plugin)
  {
    super("lives");
    addArgument(new LivesCheckArgument(plugin));
    addArgument(new LivesCheckDeathbanArgument(plugin));
    addArgument(new LivesClearDeathbansArgument(plugin));
    addArgument(new LivesGiveArgument(plugin));
    addArgument(new LivesReviveArgument(plugin));
    addArgument(new LivesSetArgument(plugin));
    addArgument(new LivesSetDeathbanTimeArgument());
  }
  
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
  {
    if (args.length < 1)
    {
      sender.sendMessage(ChatColor.DARK_GRAY + BukkitUtils.STRAIGHT_LINE_DEFAULT);
      sender.sendMessage(ChatColor.GOLD + "Lives Help");
      sender.sendMessage(ChatColor.YELLOW + " /lives check" + ChatColor.GOLD +" � " + ChatColor.GRAY + "Check how many lives a player currently has");
      sender.sendMessage(ChatColor.YELLOW + " /lives give" + ChatColor.GOLD +" � " + ChatColor.GRAY + "Gives a player a live if there death-banned");
      sender.sendMessage(ChatColor.YELLOW + " /lives revive" + ChatColor.GOLD +" � " + ChatColor.GRAY + "Revive a player that's death-banned");
      if (sender.hasPermission("lives.admin"))
      {
        sender.sendMessage(ChatColor.YELLOW + " /lives cleardeathbans" + ChatColor.GOLD +" � " + ChatColor.GRAY + "Clear all the server's deathbans");
        sender.sendMessage(ChatColor.YELLOW + " /lives set" + ChatColor.GOLD +" � " + ChatColor.GRAY + "Set the player's amount of lives.");
        sender.sendMessage(ChatColor.YELLOW + " /lives setdeathbantime" + ChatColor.GOLD +" � " + ChatColor.GRAY + "Sets the base deathban time.");
      }
      sender.sendMessage(ChatColor.DARK_GRAY + BukkitUtils.STRAIGHT_LINE_DEFAULT);
      return true;
    }
    CommandArgument argument2 = getArgument(args[0]);
    String permission2 = argument2 == null ? null : argument2.getPermission();
    if ((argument2 == null) || ((permission2 != null) && (!sender.hasPermission(permission2))))
    {
      sender.sendMessage(ChatColor.RED + "Command not found");
      return true;
    }
    argument2.onCommand(sender, command, label, args);
    return true;
  }
}
