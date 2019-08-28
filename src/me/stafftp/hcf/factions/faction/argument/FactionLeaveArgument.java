package me.stafftp.hcf.factions.faction.argument;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.stafftp.hcf.factions.faction.struct.Relation;
import me.stafftp.hcf.factions.faction.struct.Role;
import me.stafftp.hcf.factions.faction.type.PlayerFaction;
import me.stafftp.hcf.util.command.CommandArgument;
import pls.carbon.hcf.Base;

import java.util.UUID;

public class FactionLeaveArgument extends CommandArgument {
    private final Base plugin;

    public FactionLeaveArgument(final Base plugin) {
        super("leave", "Leave your current faction.");
        this.plugin = plugin;
    }

    public String getUsage(final String label) {
        return '/' + label + ' ' + this.getName();
    }

    @SuppressWarnings("deprecation")
	public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can leave faction.");
            return true;
        }
        final Player player = (Player) sender;
        final PlayerFaction playerFaction = this.plugin.getFactionManager().getPlayerFaction(player);
        if(playerFaction == null) {
            sender.sendMessage(ChatColor.RED + "You are not in a faction.");
            return true;
        }
        final UUID uuid = player.getUniqueId();
        if(playerFaction.getMember(uuid).getRole() == Role.LEADER) {
            sender.sendMessage(ChatColor.RED + "You cannot leave factions as a leader. Either use " + ChatColor.DARK_AQUA + '/' + label + " disband" + ChatColor.RED + " or " + ChatColor.DARK_AQUA + '/' + label + " leader" + ChatColor.RED + '.');
            return true;
        }
        if(playerFaction.setMember(player, null)) {
            sender.sendMessage(ChatColor.YELLOW + "Successfully left the faction.");
            playerFaction.broadcast(Relation.ENEMY.toChatColour() + sender.getName() + ChatColor.YELLOW + " has left the faction.");
        }
        return true;
    }
}
