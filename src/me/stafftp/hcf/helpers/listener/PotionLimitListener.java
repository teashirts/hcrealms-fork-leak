package me.stafftp.hcf.helpers.listener;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import me.stafftp.hcf.ConfigurationService;
import pls.carbon.hcf.Base;

/**
 * Listener that prevents the brewing of illegal
 * {@link org.bukkit.potion.PotionEffectType}s.
 */
public class PotionLimitListener implements Listener {

	@SuppressWarnings("unused")
	private static final int EMPTY_BREW_TIME = 400;

	@SuppressWarnings("unused")
	private final Base plugin;

	public PotionLimitListener(Base plugin) {
		this.plugin = plugin;
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	public void onBrew(BrewEvent event) {
		if (!testValidity(event.getResults())) {
			event.setCancelled(true);
			event.getContents().getHolder().setBrewingTime(0);
		}
	}


	private boolean testValidity(ItemStack[] contents) {
		for (ItemStack stack : contents) {

			if (stack != null && stack.getType() == Material.POTION && stack.getDurability() != 0) {
				Potion potion = Potion.fromItemStack(stack);

				// Just to be safe, null check this.
				if (potion == null) {
					continue;
				}

				// Mundane potions etc, can return a null type
				PotionType type = potion.getType();
				if (type == null) {
					continue;
				}

				// I suck at naming methods & stuff
				if (potion.hasExtendedDuration() && ConfigurationService.isExtendedDurationDisallowed(type)) {
					return false;
				}

				if (type == PotionType.POISON && !potion.hasExtendedDuration() && potion.getLevel() == 1) {
					continue;
				}

				if (potion.getLevel() > ConfigurationService.getPotionLimit(type)) {
					return false;
				}
			}
		}

		return true;
	}

	
}
