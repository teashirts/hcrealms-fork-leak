package me.stafftp.hcf.factions.deathban.lives.argument;

import java.util.Collections;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import me.stafftp.hcf.util.BukkitUtils;
import me.stafftp.hcf.util.JavaUtils;
import me.stafftp.hcf.util.base.BaseConstants;
import me.stafftp.hcf.util.command.CommandArgument;
import pls.carbon.hcf.Base;

public class LivesSetArgument
  extends CommandArgument
{
  private final Base plugin;
  
  public LivesSetArgument(Base plugin)
  {
    super("set", "Set how much lives a player has");
    this.plugin = plugin;
    this.permission = ("hcf.command.lives.argument." + getName());
  }
  
  public String getUsage(String label)
  {
    return '/' + label + ' ' + getName() + " <playerName> <amount>";
  }
  
  @SuppressWarnings("deprecation")
public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
  {
    if (args.length < 3)
    {
      sender.sendMessage(ChatColor.RED + "Usage: " + getUsage(label));
      return true;
    }
    Integer amount = JavaUtils.tryParseInt(args[2]);
    if (amount == null)
    {
      sender.sendMessage(ChatColor.RED + "'" + args[2] + "' is not a number.");
      return true;
    }
    OfflinePlayer target = BukkitUtils.offlinePlayerWithNameOrUUID(args[1]);
    if ((!target.hasPlayedBefore()) && (!target.isOnline()))
    {
      sender.sendMessage(String.format(BaseConstants.PLAYER_WITH_NAME_OR_UUID_NOT_FOUND, new Object[] { args[1] }));
      return true;
    }
    this.plugin.getDeathbanManager().setLives(target.getUniqueId(), amount.intValue());
    sender.sendMessage(ChatColor.YELLOW + target.getName() + " now has " + ChatColor.GOLD + amount + ChatColor.YELLOW + " lives.");
    return true;
  }
  
  public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args)
  {
    return args.length == 2 ? null : Collections.emptyList();
  }
}
