package me.stafftp.hcf.factions.faction.argument;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.stafftp.hcf.util.command.CommandArgument;
import net.md_5.bungee.api.ChatColor;
import pls.carbon.hcf.Base;

public class FactionBuildArgument extends CommandArgument {
	
	private final Base plugin;
	
    public FactionBuildArgument(Base plugin) {
        super("build", "Opens the build settings GUI.", "hcf.command.faction.build", new String[] { "" });
        this.plugin = plugin;
    }

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
				if(args.length != 1) {
					sender.sendMessage(ChatColor.RED + "Usage: " + this.getUsage(label));
					return true;
				}
				
				Player player = (Player)sender;
				plugin.getClaimSettings().open(player);
		}
		return true;
	}

	@Override
	public String getUsage(String label) {
        return '/' + label + ' ' + getName();
	}

}
