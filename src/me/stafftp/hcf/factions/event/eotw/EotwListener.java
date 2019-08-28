package me.stafftp.hcf.factions.event.eotw;


import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import me.stafftp.hcf.factions.faction.event.FactionClaimChangeEvent;
import me.stafftp.hcf.factions.faction.event.cause.ClaimChangeCause;
import me.stafftp.hcf.factions.faction.type.Faction;
import me.stafftp.hcf.factions.faction.type.PlayerFaction;
import pls.carbon.hcf.Base;

public class EotwListener implements Listener {
    private final Base plugin;

    public EotwListener(final Base plugin) {
        this.plugin = plugin;
    }


    @SuppressWarnings("incomplete-switch")
	@EventHandler
    public void onMobSpawnFromSpawner(CreatureSpawnEvent e){
        if(plugin.getEotwHandler().isEndOfTheWorld()) {
            switch (e.getSpawnReason()) {
                case SPAWNER: {
                    if (e.getEntity().getType() != EntityType.PIG) {
                        e.setCancelled(true);
                    }
                }
                case SPAWNER_EGG: {
                    if (e.getEntity().getType() != EntityType.PIG) {
                        e.setCancelled(true);
                    }
                }
                case DISPENSE_EGG: {
                    if (e.getEntity().getType() != EntityType.PIG) {
                        e.setCancelled(true);
                    }
                }
            }
        }

    }


    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onFactionClaimChange(final FactionClaimChangeEvent event) {
        if(this.plugin.getEotwHandler().isEndOfTheWorld() && event.getCause() == ClaimChangeCause.CLAIM) {
            final Faction faction = event.getClaimableFaction();
            if(faction instanceof PlayerFaction) {
                event.setCancelled(true);
                event.getSender().sendMessage(ChatColor.RED + "Player based faction land cannot be claimed during EOTW.");
            }
        }
    }
}
