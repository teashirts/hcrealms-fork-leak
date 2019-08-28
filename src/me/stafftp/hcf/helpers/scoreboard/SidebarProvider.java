package me.stafftp.hcf.helpers.scoreboard;

import java.util.List;
import org.bukkit.entity.Player;

public abstract interface SidebarProvider
{
  public abstract String getTitle();
  
  public abstract List<SidebarEntry> getLines(Player paramPlayer);
}
