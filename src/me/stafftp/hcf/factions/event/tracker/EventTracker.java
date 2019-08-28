package me.stafftp.hcf.factions.event.tracker;

import org.bukkit.entity.Player;

import me.stafftp.hcf.factions.event.CaptureZone;
import me.stafftp.hcf.factions.event.EventTimer;
import me.stafftp.hcf.factions.event.EventType;
import me.stafftp.hcf.factions.event.faction.EventFaction;

public abstract interface EventTracker
{
  public abstract EventType getEventType();
  
  public abstract void tick(EventTimer paramEventTimer, EventFaction paramEventFaction);
  
  public abstract void onContest(EventFaction paramEventFaction, EventTimer paramEventTimer);
  
  public abstract boolean onControlTake(Player paramPlayer, CaptureZone paramCaptureZone);
  
  public abstract boolean onControlLoss(Player paramPlayer, CaptureZone paramCaptureZone, EventFaction paramEventFaction);
  
  public abstract void stopTiming();
}
