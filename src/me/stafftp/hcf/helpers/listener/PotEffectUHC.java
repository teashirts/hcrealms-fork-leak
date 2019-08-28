package me.stafftp.hcf.helpers.listener;


import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import me.stafftp.hcf.ConfigurationService;
import me.stafftp.hcf.util.Color;

@SuppressWarnings("unused")
public class PotEffectUHC implements Listener {


	
	@EventHandler
	public void onPotionDrink(PlayerInteractEvent event) {
		if(event.getItem() == null) {
			return;
		}
		
		if(event.getItem().getType()!=Material.POTION) {
			return;
		}
		
		List<Short> disabled = new ArrayList<>();
		
		// Strength
		
		disabled.add((short) 16457);
		disabled.add((short) 16425);
		disabled.add((short) 16393);
		disabled.add((short) 8265);
		disabled.add((short) 8233);
		disabled.add((short) 8201);
		
		// Regeneration
		
		disabled.add((short)8193);
		disabled.add((short)8225);
		disabled.add((short)8257);
		disabled.add((short)16385);
		disabled.add((short)16417);
		disabled.add((short)16449);
		
		// Weakness
			
		disabled.add((short)8232);
		disabled.add((short)8264);
		disabled.add((short)16424);
		disabled.add((short)16456);
		
		// Instant Damage
		
		disabled.add((short)8268);
		disabled.add((short)8236);
		disabled.add((short)16460);
		disabled.add((short)16428);
		
		
		if(disabled.contains(event.getItem().getDurability())){
			event.setCancelled(true);
			event.getPlayer().getInventory().remove(event.getItem());
			event.getPlayer().sendMessage(Color.translate("&cYou can't use this potion."));
		}
	}
		
	@EventHandler
	public void onPotionUse(PlayerInteractEvent event) {
		if(event.getItem() == null) {
			return;
		}
		
		if(event.getItem().getType()!=Material.POTION) {
			return;
		}
		
		List<Short> disabled = new ArrayList<>();
		
		if((ConfigurationService.UHCF) == true) {
			
			//Health
			
			disabled.add((short)8197);
			disabled.add((short)8229);
			disabled.add((short)16389);
			disabled.add((short)16421);
			if(disabled.contains(event.getItem().getDurability())){
				event.setCancelled(true);
				event.getPlayer().getInventory().remove(event.getItem());
				event.getPlayer().sendMessage(Color.translate("&cYou can't use this potion."));
			}
		}
	}
}