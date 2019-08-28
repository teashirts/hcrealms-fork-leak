package me.stafftp.hcf.helpers.scoreboard.provider;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import me.stafftp.hcf.ConfigurationService;
import me.stafftp.hcf.factions.classes.PvpClass;
import me.stafftp.hcf.factions.classes.archer.ArcherClass;
import me.stafftp.hcf.factions.classes.bard.BardClass;
import me.stafftp.hcf.factions.classes.type.MinerClass;
import me.stafftp.hcf.factions.event.EventTimer;
import me.stafftp.hcf.factions.event.eotw.EOTWHandler;
import me.stafftp.hcf.factions.event.faction.EventFaction;
import me.stafftp.hcf.factions.sotw.SotwCommand;
import me.stafftp.hcf.factions.sotw.SotwTimer;
import me.stafftp.hcf.helpers.commands.CobbleCommand;
import me.stafftp.hcf.helpers.commands.StaffModeCommand;
import me.stafftp.hcf.helpers.listener.StaffChatListener;
import me.stafftp.hcf.helpers.listener.VanishListener;
import me.stafftp.hcf.helpers.scoreboard.SidebarEntry;
import me.stafftp.hcf.helpers.scoreboard.SidebarProvider;
import me.stafftp.hcf.helpers.timer.GlobalTimer;
import me.stafftp.hcf.helpers.timer.PlayerTimer;
import me.stafftp.hcf.helpers.timer.Timer;
import me.stafftp.hcf.helpers.timer.type.TeleportTimer;
import me.stafftp.hcf.special.KING.KingCommand;
import me.stafftp.hcf.util.BukkitUtils;
import me.stafftp.hcf.util.base.DateTimeFormats;
import pls.carbon.hcf.Base;

public class TimerSidebarProvider implements SidebarProvider {

	protected static String STRAIGHT_LINE = BukkitUtils.STRAIGHT_LINE_DEFAULT.substring(0, 14);
	protected static final String NEW_LINE = ChatColor.STRIKETHROUGH + "----------";

	private Base plugin;

	public TimerSidebarProvider(Base plugin) {
		this.plugin = plugin;
	}

	private static String handleBardFormat(long millis, boolean trailingZero) {
		return ((DecimalFormat) (trailingZero ? DateTimeFormats.REMAINING_SECONDS_TRAILING
				: DateTimeFormats.REMAINING_SECONDS).get()).format(millis * 0.001D);
	}
	@SuppressWarnings("unused")
	private String format(double tps) {
        return (tps > 18.0 ? ChatColor.GRAY : tps > 16.0 ? ChatColor.RED : ChatColor.DARK_RED).toString()
        		+ ((tps > 20.0) ? "*" : "") + Math.min(Math.round(tps * 100.0)/100.0, 20.0);
    }

	public SidebarEntry add(String s) {

		if (s.length() < 10) {
			return new SidebarEntry(s);
		}

		if (s.length() > 10 && s.length() < 20) {
			return new SidebarEntry(s.substring(0, 10), s.substring(10, s.length()), "");
		}

		if (s.length() > 20) {
			return new SidebarEntry(s.substring(0, 10), s.substring(10, 20), s.substring(20, s.length()));
		}

		return null;
	}

	@Override
	public String getTitle() {
		return ConfigurationService.SCOREBOARD_TITLE;
	}

	@SuppressWarnings({ "static-access", "unused", "deprecation" })
	public List<SidebarEntry> getLines(Player player) {

		List<SidebarEntry> lines = new ArrayList<SidebarEntry>();
		EOTWHandler.EotwRunnable eotwRunnable = this.plugin.getEotwHandler().getRunnable();
		PvpClass pvpClass = this.plugin.getPvpClassManager().getEquippedClass(player);
		EventTimer eventTimer = this.plugin.getTimerManager().eventTimer;
		List<SidebarEntry> conquestLines = null;
		Collection<Timer> timers = this.plugin.getTimerManager().getTimers();
		EventFaction eventFaction = eventTimer.getEventFaction();

		if((ConfigurationService.UHCF) == true) {
			lines.add(new SidebarEntry(ChatColor.AQUA.toString() + ChatColor.BOLD + "U", "H", "CF " + ChatColor.GRAY + "(BETA)"));
		}
		
		if ((ConfigurationService.KIT_MAP) == true) {
			lines.add(this.add(Base.getPlugin().getConfig().getString("Scoreboard.Kills.Name").replace("%kills%", String.valueOf(player.getStatistic(Statistic.PLAYER_KILLS))).replaceAll("&", "§")));
            lines.add(this.add(Base.getPlugin().getConfig().getString("Scoreboard.Deaths.Name").replace("%deaths%", String.valueOf(player.getStatistic(Statistic.DEATHS))).replaceAll("&", "§")));
            //lines.add(this.add(ChatColor.translateAlternateColorCodes('&', Base.getPlugin().getConfig().getString("Scoreboard.Balance.Name").replaceAll("%balance%", String.valueOf(Base.getPlugin().getEconomyManager().getBalance(player.getUniqueId()))))));
		}
		

		if ((pvpClass != null) && ((pvpClass instanceof BardClass))) {
			BardClass bardClass = (BardClass) pvpClass;
			lines.add(new SidebarEntry(ChatColor.AQUA + ChatColor.BOLD.toString() + "Bard ",
					ChatColor.AQUA + ChatColor.BOLD.toString() + "Energy", ChatColor.GRAY + ": " + ChatColor.RED
							+ handleBardFormat(bardClass.getEnergyMillis(player), true)));
			long remaining2 = bardClass.getRemainingBuffDelay(player);
			if (remaining2 > 0L) {
				lines.add(new SidebarEntry(ChatColor.GREEN + ChatColor.BOLD.toString() + "Bard ",
						ChatColor.GREEN + ChatColor.BOLD.toString() + "Effect",
						ChatColor.GRAY + ": " + ChatColor.RED + Base.getRemaining(remaining2, true)));
			}
		}
		final SotwTimer.SotwRunnable sotwRunnable = this.plugin.getSotwTimer().getSotwRunnable();
		if (sotwRunnable != null) {
				if(SotwCommand.enabled.contains(player.getUniqueId())) {
					lines.add(new SidebarEntry(String.valueOf(ChatColor.GREEN.toString()) + ChatColor.BOLD, "SOTW Timer", ChatColor.GRAY + ": "));
					lines.add(new SidebarEntry(String.valueOf(ChatColor.GRAY.toString()) + ChatColor.GRAY, " » §aEnabled", ChatColor.GRAY + ": " + String.valueOf(ChatColor.RED.toString() + ChatColor.STRIKETHROUGH) + Base.getRemaining(sotwRunnable.getRemaining(), true)));

				} else {
					lines.add(new SidebarEntry(String.valueOf(ChatColor.GREEN.toString()) + ChatColor.BOLD, "SOTW Timer", ChatColor.GRAY + ": "));
					lines.add(new SidebarEntry(String.valueOf(ChatColor.GRAY.toString()), " » §aTime", ChatColor.GRAY + ": " + String.valueOf(ChatColor.RED.toString()) + Base.getRemaining(sotwRunnable.getRemaining(), true)));
					}
			}

		if ((pvpClass instanceof MinerClass)) {
			lines.add(new SidebarEntry(ChatColor.GREEN.toString(), "Active Class",
					ChatColor.GRAY + ": " + ChatColor.RED + "Miner"));
            lines.add(new SidebarEntry(ChatColor.GRAY + " » ", ChatColor.AQUA + "Diamonds", ChatColor.WHITE + ": " + ChatColor.WHITE + player.getStatistic(Statistic.MINE_BLOCK, Material.DIAMOND_ORE)));
			lines.add(new SidebarEntry(ChatColor.GRAY + " » ", ChatColor.GREEN + "Emeralds", ChatColor.WHITE + ": " + ChatColor.WHITE + player.getStatistic(Statistic.MINE_BLOCK, Material.EMERALD_ORE)));
            //lines.add(new SidebarEntry(ChatColor.GRAY + " » ", ChatColor.GOLD + "Gold", ChatColor.WHITE + ": " + ChatColor.WHITE + player.getStatistic(Statistic.MINE_BLOCK, Material.GOLD_ORE)));
            //lines.add(new SidebarEntry(ChatColor.GRAY + " » ", ChatColor.GRAY + "Iron", ChatColor.WHITE + ": " + ChatColor.WHITE + player.getStatistic(Statistic.MINE_BLOCK, Material.IRON_ORE)));
            lines.add(new SidebarEntry(ChatColor.GRAY + " » ", ChatColor.AQUA + "Cobble", ChatColor.WHITE + ": " + hasCobbleEnabled(player)));
		}

//	if ((pvpClass instanceof MinerClass)) {
	//		lines.add(new SidebarEntry(ChatColor.GREEN.toString(), "Active Class",
		//			ChatColor.GRAY + ": " + ChatColor.RED + "Miner"));
		//}

		if ((pvpClass instanceof ArcherClass)) {
			lines.add(new SidebarEntry(ChatColor.GREEN.toString(), "Active Class",
					ChatColor.GRAY + ": " + ChatColor.RED + "Archer"));
		}
		
        if(pvpClass instanceof ArcherClass) {
            if (ArcherClass.TAGGED.containsValue(player.getUniqueId())) {
                for (UUID uuid : ArcherClass.TAGGED.keySet()) {
                    Player tagged;
                    if (((ArcherClass.TAGGED.get(uuid)).equals(player.getUniqueId())) && ((tagged = Bukkit.getPlayer(uuid)) != null)) {
                        String name = tagged.getName();
                        if (name.length() > 14) {
                            name = name.substring(0, 14);
                        }
                        lines.add(new SidebarEntry(ConfigurationService.GOLD.toString() + " » " + ConfigurationService.YELLOW.toString(), ConfigurationService.YELLOW + name, ""));
                    }
                }
            }
            }

		/* if ((pvpClass instanceof BardClass)) {
			lines.add(new SidebarEntry(ChatColor.GREEN.toString(), "Active Class",
					ChatColor.GRAY + ": " + ChatColor.RED + "Bard"));
		}

		if ((pvpClass instanceof RogueClass)) {
			lines.add(new SidebarEntry(ChatColor.GREEN.toString(), "Active Class",
					ChatColor.GRAY + ": " + ChatColor.RED + "Rogue"));
		}*/

		for (Timer timer : timers) {
			if (((timer instanceof PlayerTimer)) && (!(timer instanceof TeleportTimer))) {
				PlayerTimer playerTimer = (PlayerTimer) timer;
				long remaining3 = playerTimer.getRemaining(player);
				if (remaining3 > 0L) {
					String timerName1 = playerTimer.getName();
					if (timerName1.length() > 14) {
						timerName1 = timerName1.substring(0, timerName1.length());
					}
					lines.add(new SidebarEntry(playerTimer.getScoreboardPrefix(), timerName1 + ChatColor.GRAY,
							": " + ChatColor.RED + Base.getRemaining(remaining3, true)));
				}
			} else if ((timer instanceof GlobalTimer)) {
				GlobalTimer playerTimer2 = (GlobalTimer) timer;
				long remaining3 = playerTimer2.getRemaining();
				if (remaining3 > 0L) {
					String timerName = playerTimer2.getName();
					if (timerName.length() > 14) {
						timerName = timerName.substring(0, timerName.length());
					}
					if (!timerName.equalsIgnoreCase("Conquest")) {
						lines.add(new SidebarEntry(playerTimer2.getScoreboardPrefix(), timerName + ChatColor.GRAY,
								": " + ChatColor.RED + Base.getRemaining(remaining3, true)));
					}
				}
			}
		}

		if (eotwRunnable != null) {
			long remaining4 = eotwRunnable.getTimeUntilStarting();
			if (remaining4 > 0L) {
				lines.add(new SidebarEntry(ChatColor.DARK_RED.toString() + ChatColor.BOLD, "EOTW" + ChatColor.GRAY + "",
						"" + ChatColor.GRAY + ": " + ChatColor.RED + Base.getRemaining(remaining4, true)));
			} else if ((remaining4 = eotwRunnable.getTimeUntilCappable()) > 0L) {
				lines.add(new SidebarEntry(ChatColor.DARK_RED.toString() + ChatColor.BOLD, "EOTW" + ChatColor.GRAY + "",
						"" + ChatColor.GRAY + ": " + ChatColor.RED + Base.getRemaining(remaining4, true)));
			}
		}

		
	    if(KingCommand.player != null) {
	    	Player player1 = Bukkit.getPlayer(KingCommand.kingName);
	    if((ConfigurationService.KIT_MAP) == true) {
	    	lines.add(new SidebarEntry("§7§m------", "------", "--------"));
	    }

	    	lines.add(new SidebarEntry(ConfigurationService.PRIMAIRY_COLOR + "King Event"));
	    	lines.add(new SidebarEntry(ConfigurationService.THIRD_COLOR + " » ", ConfigurationService.SECONDAIRY_COLOR + "King","§7:§f " + player1.getName()));
	    	lines.add(new SidebarEntry(ConfigurationService.THIRD_COLOR + " » ", ConfigurationService.SECONDAIRY_COLOR + "Location","§7: §f" + player1.getLocation().getBlockX() + ", " + player1.getLocation().getBlockZ()));
	    	lines.add(new SidebarEntry(ConfigurationService.THIRD_COLOR + " » ", ConfigurationService.SECONDAIRY_COLOR + "Prize","§7: §f" + KingCommand.kingPrize));

	    }
	    
		if ((StaffModeCommand.getInstance().isMod(player))) {
			if((ConfigurationService.KIT_MAP)) {
				lines.add(new SidebarEntry(ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + "---------", "-----", "------"));
			}
	        // lines.add(new SidebarEntry(ConfigurationService.SECONDAIRY_COLOR + " » " +  ConfigurationService.PRIMAIRY_COLOR + "Players", "§7: ", "§a" + Bukkit.getOnlinePlayers().length));    
	        // lines.add(new SidebarEntry( ConfigurationService.SECONDAIRY_COLOR + " »", ConfigurationService.PRIMAIRY_COLOR + " TPS", ChatColor.GRAY + ": " + this.format(Bukkit.getServer().spigot().getTPS()[0]))); 
			//lines.add(new SidebarEntry(ConfigurationService.SECONDAIRY_COLOR, " » " + ConfigurationService.PRIMAIRY_COLOR + "Vanish" + ChatColor.GRAY + ": ",
					//VanishListener.getInstance().isVanished(player) ? ChatColor.GREEN + "On" : ChatColor.RED + "Off"));
            //lines.add(this.add(ChatColor.translateAlternateColorCodes('&', Base.getPlugin().getConfig().getString("Scoreboard.Vanish.Name").replaceAll("%vanish%", VanishListener.isVanished(player) ? ChatColor.GREEN + "On" : ChatColor.RED + "Off"))));
			lines.add(new SidebarEntry(ChatColor.GRAY.toString() + "" + ChatColor.GOLD.toString() + ChatColor.BOLD, "Staff Mode" + "§7: ", VanishListener.getInstance().isVanished(player) ? ChatColor.GREEN + "" : ChatColor.RED + ""));
			lines.add(new SidebarEntry(ChatColor.GOLD + " » ", ChatColor.YELLOW + "Vanish" + ChatColor.GRAY + ": ", VanishListener.getInstance().isVanished(player) ? ChatColor.GREEN + "Enabled" : ChatColor.RED + "Disabled"));
			lines.add(new SidebarEntry(ChatColor.GOLD + " » ", ChatColor.YELLOW + "Staff Chat" + ChatColor.GRAY + ": ", StaffChatListener.getInstance().isInStaffChat(player) ? ChatColor.GREEN + "Enabled" : ChatColor.RED + "Disabled"));
			lines.add(new SidebarEntry("§6 » §eOnline", "§7: ", "§f" + Bukkit.getOnlinePlayers().length)); 
			}
		
		if ((conquestLines != null) && (!conquestLines.isEmpty())) {
			if (player.hasPermission("command.mod")) {
				conquestLines.add(new SidebarEntry("§7§m------", "------", "--------"));
			}
			conquestLines.addAll(lines);
			lines = conquestLines;
		}
		if (!lines.isEmpty()) {
			lines.add(0, new SidebarEntry(ChatColor.GRAY, ChatColor.STRIKETHROUGH + "-----------", "---------"));
            if (ConfigurationService.FOOTER) {
                lines.add(new SidebarEntry(""));
                lines.add(new SidebarEntry(ChatColor.translateAlternateColorCodes('&', ConfigurationService.FOOTERTEXT)));
            }
			lines.add(lines.size(), new SidebarEntry(ChatColor.GRAY, NEW_LINE, "----------"));
		}
		return lines;

	}
	

    public static String hasCobbleEnabled(final Player player) {
        if (CobbleCommand.disabled.contains(player)) {
            return "§cDisabled";
        }
        return "§aEnabled";
    }
	public static final ThreadLocal<DecimalFormat> CONQUEST_FORMATTER = new ThreadLocal<DecimalFormat>() {
		protected DecimalFormat initialValue() {
			return new DecimalFormat("00.0");
		}
	};

}
