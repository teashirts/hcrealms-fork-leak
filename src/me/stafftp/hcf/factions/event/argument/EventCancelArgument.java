package me.stafftp.hcf.factions.event.argument;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import me.stafftp.hcf.factions.event.EventTimer;
import me.stafftp.hcf.factions.event.EventType;
import me.stafftp.hcf.factions.event.tracker.KothTracker;
import me.stafftp.hcf.factions.faction.type.Faction;
import me.stafftp.hcf.util.command.CommandArgument;
import pls.carbon.hcf.Base;

@SuppressWarnings("unused")
public class EventCancelArgument
  extends CommandArgument
{
  private final Base plugin;
  
  public EventCancelArgument(Base plugin)
  {
    super("cancel", "Cancels a running event", new String[] { "stop", "end" });
    this.plugin = plugin;
    this.permission = ("hcf.command.event.argument." + getName());
  }
  
  
  public String getUsage(String label)
  {
    return '/' + label + ' ' + getName();
  }
  
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
  {
    EventTimer eventTimer = this.plugin.getTimerManager().eventTimer;
    Faction eventFaction = eventTimer.getEventFaction();
    if (!eventTimer.clearCooldown())
    {
      sender.sendMessage(ChatColor.RED + "There is not a running event.");
      return true;
    }
    return true;
  }
}
