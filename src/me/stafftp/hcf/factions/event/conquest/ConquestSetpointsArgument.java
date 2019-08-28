package me.stafftp.hcf.factions.event.conquest;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import me.stafftp.hcf.factions.event.EventType;
import me.stafftp.hcf.factions.event.tracker.ConquestTracker;
import me.stafftp.hcf.factions.faction.type.Faction;
import me.stafftp.hcf.factions.faction.type.PlayerFaction;
import me.stafftp.hcf.util.JavaUtils;
import me.stafftp.hcf.util.command.CommandArgument;
import pls.carbon.hcf.Base;

public class ConquestSetpointsArgument
  extends CommandArgument
{
  private final Base plugin;
  
  public ConquestSetpointsArgument(Base plugin)
  {
    super("setpoints", "Sets the points of a faction in the Conquest event", "hcf.command.conquest.argument.setpoints");
    this.plugin = plugin;
  }
  
  public String getUsage(String label)
  {
    return '/' + label + ' ' + getName() + " <factionName> <amount>";
  }
  
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
  {
    if (args.length < 3)
    {
      sender.sendMessage(ChatColor.RED + "Usage: " + getUsage(label));
      return true;
    }
    Faction faction = this.plugin.getFactionManager().getFaction(args[1]);
    if (!(faction instanceof PlayerFaction))
    {
      sender.sendMessage(ChatColor.RED + "Faction " + args[1] + " is either not found or is not a player faction.");
      return true;
    }
    Integer amount = JavaUtils.tryParseInt(args[2]);
    if (amount == null)
    {
      sender.sendMessage(ChatColor.RED + "'" + args[2] + "' is not a number.");
      return true;
    }
    if (amount.intValue() > 300)
    {
      sender.sendMessage(ChatColor.RED + "Maximum points for Conquest is " + 300 + '.');
      return true;
    }
    PlayerFaction playerFaction = (PlayerFaction)faction;
    ((ConquestTracker)EventType.CONQUEST.getEventTracker()).setPoints(playerFaction, amount.intValue());
    Command.broadcastCommandMessage(sender, ChatColor.YELLOW + "Set the points of faction " + playerFaction.getName() + " to " + amount + '.');
    return true;
  }
}
