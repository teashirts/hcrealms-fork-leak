package me.stafftp.hcf.helpers.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class StatsCommand implements CommandExecutor, Listener {
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		Player player = (Player) sender;
		if ((cmd.getName().equalsIgnoreCase("stats")) && ((sender instanceof Player)) && (args.length > 1)) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cUsage: /stats <player>"));
			return true;
		}
		if (args.length == 0) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cUsage: /stats <player>"));
			return true;
		}
		Player target = Bukkit.getServer().getPlayer(args[0]);
		if ((args.length == 1) && (target == null)) {
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThat player is currently offline."));
			return true;
		}
		onStatsGUI(player, target);

		return false;
	}

	public void onStatsGUI(Player player, Player target) {
		Inventory inv = Bukkit.createInventory(null, 9, ChatColor.YELLOW + "Statistics");

		ItemStack Diamond_ore = new ItemStack(Material.DIAMOND_ORE);
		ItemStack Emerald_ore = new ItemStack(Material.EMERALD_ORE);
		ItemStack Gold_ore = new ItemStack(Material.GOLD_ORE);
		ItemStack Iron_ore = new ItemStack(Material.IRON_ORE);
		ItemStack Coal_ore = new ItemStack(Material.COAL_ORE);
		ItemStack Lapis_ore = new ItemStack(Material.LAPIS_ORE);
		ItemStack RedStone_ore = new ItemStack(Material.REDSTONE_ORE);
		ItemStack Bone = new ItemStack(Material.BONE);
		ItemStack DiamondSword = new ItemStack(Material.DIAMOND_SWORD);

		ItemMeta DiamondMeta = Diamond_ore.getItemMeta();
		ItemMeta EmeraldMeta = Emerald_ore.getItemMeta();
		ItemMeta GoldMeta = Gold_ore.getItemMeta();
		ItemMeta IronMeta = Iron_ore.getItemMeta();
		ItemMeta CoalMeta = Coal_ore.getItemMeta();
		ItemMeta LapisMeta = Lapis_ore.getItemMeta();
		ItemMeta RedStoneMeta = RedStone_ore.getItemMeta();
		ItemMeta BoneMeta = Bone.getItemMeta();
		ItemMeta DiamondSwordMeta = DiamondSword.getItemMeta();

		DiamondMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', ChatColor.translateAlternateColorCodes('&', "&bDiamond(s): &f") + target.getStatistic(Statistic.MINE_BLOCK, Material.DIAMOND_ORE)));
		EmeraldMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&aEmerald(s): &f" + target.getStatistic(Statistic.MINE_BLOCK, Material.EMERALD_ORE)));
		IronMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7Iron(s): &f" + target.getStatistic(Statistic.MINE_BLOCK, Material.IRON_ORE)));
		GoldMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6Gold(s): &f" + target.getStatistic(Statistic.MINE_BLOCK, Material.GOLD_ORE)));
		RedStoneMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&cRedstone(s): &f" + target.getStatistic(Statistic.MINE_BLOCK, Material.REDSTONE_ORE)));
		CoalMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&8Coal(s): &f" + target.getStatistic(Statistic.MINE_BLOCK, Material.COAL_ORE)));
		LapisMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&9Lapis(s): &f" + target.getStatistic(Statistic.MINE_BLOCK, Material.LAPIS_ORE)));
		BoneMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&4Death(s): &f" + target.getStatistic(Statistic.DEATHS)));
		DiamondSwordMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&4Kill(s): &f" + target.getStatistic(Statistic.PLAYER_KILLS)));

		Diamond_ore.setItemMeta(DiamondMeta);
		Emerald_ore.setItemMeta(EmeraldMeta);
		Iron_ore.setItemMeta(IronMeta);
		Gold_ore.setItemMeta(GoldMeta);
		RedStone_ore.setItemMeta(RedStoneMeta);
		Coal_ore.setItemMeta(CoalMeta);
		Lapis_ore.setItemMeta(LapisMeta);
		Bone.setItemMeta(BoneMeta);
		DiamondSword.setItemMeta(DiamondSwordMeta);
		
		inv.setItem(1, Diamond_ore);
		inv.setItem(2, Emerald_ore);
		inv.setItem(3, Iron_ore);

		inv.setItem(4, Gold_ore);
		inv.setItem(5, RedStone_ore);
		inv.setItem(6, Coal_ore);
		inv.setItem(7, Lapis_ore);

		inv.setItem(0, DiamondSword);
		inv.setItem(8, Bone);

		player.openInventory(inv);
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getInventory().getTitle().startsWith(ChatColor.YELLOW + "Statistics")) {
			event.setCancelled(true);
		}
	}
	
}
