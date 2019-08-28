package me.stafftp.hcf.helpers.listener.fixes;


import net.minecraft.util.com.google.common.collect.ImmutableSet; 
import net.minecraft.util.com.google.common.collect.Sets;
import pls.carbon.hcf.Base;

import org.bukkit.event.*;
import org.bukkit.event.player.*;
import org.bukkit.*;

public class PearlGlitchListener implements Listener
{
    @SuppressWarnings("unused")
	private final ImmutableSet<Material> blockedPearlTypes;
    @SuppressWarnings("unused")
	private final Base plugin;

    @SuppressWarnings({ "unchecked", "rawtypes" })
	public PearlGlitchListener(final Base plugin) {
        this.blockedPearlTypes = (ImmutableSet<Material>)Sets.immutableEnumSet((Enum)Material.THIN_GLASS, (Enum[])new Material[] { Material.STEP, Material.IRON_FENCE, Material.FENCE, Material.NETHER_FENCE});
        this.plugin = plugin;
    }
    Location previous;
 
    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e)
    {
      if (((e.getMessage().startsWith("/minecraft:")) || (e.getMessage().startsWith("/bukkit:"))) && (!e.getPlayer().isOp()))
      {
        e.setCancelled(true);
      }
    }
    
    @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
    public void disableCommand23(PlayerCommandPreprocessEvent event)
    {
     	event.getPlayer();
      String message = event.getMessage().toLowerCase();
      String c = "/" + "me";
      if ((message.equals(c)) || (message.startsWith(c + " ")))
      {
       event.setCancelled(true);
      }
    }
    
    
    @EventHandler
    public void onMove(PlayerInteractEvent e){
        if(e.getPlayer().getLocation().getBlock() != null){
            if(e.getPlayer().getLocation().getBlock().getType() == Material.TRAP_DOOR){
                    if(!Base.getPlugin().getFactionManager().getFactionAt(e.getPlayer().getLocation()).equals(Base.getPlugin().getFactionManager().getPlayerFaction(e.getPlayer().getUniqueId()))) {
                        e.getPlayer().teleport(e.getPlayer().getLocation().add(0, 1, 0));
                    }
        }
        }
    }

}