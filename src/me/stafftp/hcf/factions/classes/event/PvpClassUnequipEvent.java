package me.stafftp.hcf.factions.classes.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

import me.stafftp.hcf.factions.classes.PvpClass;

public class PvpClassUnequipEvent
  extends PlayerEvent
{
  public PvpClassUnequipEvent(Player player, PvpClass pvpClass)
  {
    super(player);
    this.pvpClass = pvpClass;
  }
  
  public static HandlerList getHandlerList()
  {
    return handlers;
  }
  
  public PvpClass getPvpClass()
  {
    return this.pvpClass;
  }
  
  public HandlerList getHandlers()
  {
    return handlers;
  }
  
  private static final HandlerList handlers = new HandlerList();
  private final PvpClass pvpClass;
}
