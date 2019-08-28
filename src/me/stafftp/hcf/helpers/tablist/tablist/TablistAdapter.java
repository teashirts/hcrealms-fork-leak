package me.stafftp.hcf.helpers.tablist.tablist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import me.stafftp.hcf.ConfigurationService;
import me.stafftp.hcf.factions.event.faction.EventFaction;
import me.stafftp.hcf.factions.event.faction.KothFaction;
import me.stafftp.hcf.factions.faction.FactionMember;
import me.stafftp.hcf.factions.faction.type.PlayerFaction;
import me.stafftp.hcf.helpers.tablist.TablistEntrySupplier;
import net.minecraft.util.com.google.common.collect.HashBasedTable;
import net.minecraft.util.com.google.common.collect.Table;
import net.minecraft.util.org.apache.commons.lang3.time.DurationFormatUtils;
import pls.carbon.hcf.Base;

public class TablistAdapter implements TablistEntrySupplier {
 

    public static final Comparator<PlayerFaction> FACTION_COMPARATOR = (faction1, faction2) -> {
        return Integer.compare(faction1.getOnlinePlayers().size(), faction2.getOnlinePlayers().size());
    };
 
    public static final Comparator<FactionMember> ROLE_COMPARATOR = (member1, member2) -> {
        return Integer.compare(member1.getRole().ordinal(), member2.getRole().ordinal());
    };
 
    private final Base plugin;
 
    public TablistAdapter(Base plugin) {
        this.plugin = plugin;
    }
 
	@SuppressWarnings("deprecation")
	@Override
    public Table<Integer, Integer, String> getEntries(Player player) {
        Table<Integer, Integer, String> tab = HashBasedTable.create();
        PlayerFaction faction = plugin.getFactionManager().getPlayerFaction(player);
        tab.put(0, 12, ChatColor.GRAY + "HQ: " + ChatColor.GRAY + ((faction == null || faction.getHome() == null) ? "Not set" : (faction.getHome().getBlockX() + ", " + faction.getHome().getBlockZ())));
        tab.put(0, 8, ConfigurationService.SECONDAIRY_COLOR + "Faction Info");
        if (faction != null) {
            tab.put(0, 9, ChatColor.GRAY + "DTR: " + ChatColor.GRAY + String.format("%.2f", faction.getDeathsUntilRaidable()));
            tab.put(0, 10, ChatColor.GRAY + "Online: " + ChatColor.GRAY + faction.getOnlineMembers().size() + "/" + faction.getMembers().size());
            tab.put(0, 11, ChatColor.GRAY + "Balance: " + ChatColor.GRAY + "$" + faction.getBalance());
        } else {
            tab.put(0, 8, ConfigurationService.SECONDAIRY_COLOR + "");
            tab.put(0, 9, ChatColor.GRAY + "");
            tab.put(0, 10, ChatColor.GRAY + "");
            tab.put(0, 11, ChatColor.GRAY + "");
            tab.put(0, 12, ChatColor.GRAY + "");
        }
        tab.put(0, faction == null ? 0 : 0, ConfigurationService.SECONDAIRY_COLOR + "Player Info");
        tab.put(0, faction == null ? 1 : 1, ChatColor.GRAY + "Kills: " + ChatColor.GRAY + player.getStatistic(Statistic.PLAYER_KILLS));
        tab.put(0, faction == null ? 2 : 2, ChatColor.GRAY + "Deaths: " + ChatColor.GRAY + player.getStatistic(Statistic.DEATHS));
        tab.put(0, 4, ConfigurationService.SECONDAIRY_COLOR + "Location");
        tab.put(0, 5, plugin.getFactionManager().getFactionAt(player.getLocation()).getDisplayName(player));
        tab.put(0, 6, ChatColor.GRAY + getCardinalDirection(player) + " (" + player.getLocation().getBlockX() + ", " + player.getLocation().getBlockZ() + ")");
        tab.put(1, 0, ConfigurationService.PRIMAIRY_COLOR + "§l" + ConfigurationService.NAME);
        tab.put(1, 1, ConfigurationService.SECONDAIRY_COLOR + "Players Online");
        tab.put(1, 2, ChatColor.GRAY + "" + Bukkit.getOnlinePlayers().length + "/" + Bukkit.getMaxPlayers());
        String[] eventInfo = getKothInfo();
        for (int i = 0; i < 3; i ++) {
            tab.put(0, 14 + i, eventInfo[i]);
        }
        if (faction != null) {
            tab.put(1, 4, ConfigurationService.SECONDAIRY_COLOR + faction.getName());
            List<FactionMember> members = new ArrayList<>(faction.getMembers().values().stream().filter(member -> Bukkit.getPlayer(member.getUniqueId()) != null).collect(Collectors.toList()));
            Collections.sort(members, ROLE_COMPARATOR);
            for (int i = 5; i < 20; i ++) {
                int exact = i - 5;
                if (members.size() <= exact) {
                    continue;
                }
                if (i == 19 && members.size() > 19) {
                    tab.put(1, i, ChatColor.GREEN + "and " + (members.size() - 19) + " more...");
                    continue;
                }
                FactionMember member = members.get(exact);
                tab.put(1, i, ChatColor.GREEN + member.getRole().getAstrix() + net.md_5.bungee.api.ChatColor.GREEN + member.getName());
            }
        }
        tab.put(2, 0, ConfigurationService.SECONDAIRY_COLOR + "Faction List");
        List<PlayerFaction> PlayerTeams = new ArrayList<>(plugin.getFactionManager().getFactions().stream().filter(x -> x instanceof PlayerFaction).map(x -> (PlayerFaction) x).filter(x -> x.getOnlineMembers().size() > 0).collect(Collectors.toSet()));
        Collections.sort(PlayerTeams, FACTION_COMPARATOR);
        Collections.reverse(PlayerTeams);
        for (int i = 0; i < 20; i ++) {
            if (i >= PlayerTeams.size()) {
                break;
            }
            PlayerFaction next = PlayerTeams.get(i);
            tab.put(2, i + 1, next.getDisplayName(player) + ChatColor.GRAY + " (" + next.getOnlinePlayers().size() + ")");
        }
        //tab.put(3, 10, ChatColor.RED + "Warning!" + ConfigurationService.PRIMAIRY_COLOR + "You are on 1.8");
        //tab.put(3, 11, ConfigurationService.PRIMAIRY_COLOR + "Please use 1.7 for the");
        //tab.put(3, 12, ConfigurationService.PRIMAIRY_COLOR + "optimal playing experience");
        return tab;
    }
 
 
    public static String getCardinalDirection(Player player) {
        double rotation = (player.getLocation().getYaw() + 180F) % 360.0F;
        if (rotation < 0.0D) {
            rotation += 360.0D;
        }
        if ((0.0D <= rotation) && (rotation < 22.5D)) {
            return "N";
        }
        if ((22.5D <= rotation) && (rotation < 67.5D)) {
            return "NE";
        }
        if ((67.5D <= rotation) && (rotation < 112.5D)) {
            return "E";
        }
        if ((112.5D <= rotation) && (rotation < 157.5D)) {
            return "SE";
        }
        if ((157.5D <= rotation) && (rotation < 202.5D)) {
            return "S";
        }
        if ((202.5D <= rotation) && (rotation < 247.5D)) {
            return "SW";
        }
        if ((247.5D <= rotation) && (rotation < 292.5D)) {
            return "W";
        }
        if ((292.5D <= rotation) && (rotation < 337.5D)) {
            return "NW";
        }
        if ((337.5D <= rotation) && (rotation < 360.0D)) {
            return "N";
        }
        return null;
    }
 
    public String[] getKothInfo() {
        String[] info = new String[] {
        		ConfigurationService.SECONDAIRY_COLOR + "Next KOTH",
        		ChatColor.GRAY + "None Scheduled",
                ""
        };
        EventFaction eventFaction = plugin.getTimerManager().getEventTimer().getEventFaction();
        if (eventFaction instanceof KothFaction) {
            KothFaction faction = (KothFaction) eventFaction;
            Location center = faction.getCaptureZone().getCuboid().getCenter();
            info = new String[] {
            		ConfigurationService.SECONDAIRY_COLOR + faction.getName(),
            		ChatColor.GRAY + DurationFormatUtils.formatDuration(plugin.getTimerManager().getEventTimer().getRemaining(), "mm:ss"),
            		ChatColor.GRAY.toString() + center.getBlockX() + ", " + center.getBlockZ(),
            };
        }
        return info;
    }
 
    @Override
    public String getHeader(Player player) {
        return ConfigurationService.SECONDAIRY_COLOR + "You are playing on " + ConfigurationService.NAME;
    }
 
    @Override
    public String getFooter(Player player) {
        return "";
    }
 
 
}	

