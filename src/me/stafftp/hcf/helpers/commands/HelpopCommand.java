package me.stafftp.hcf.helpers.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.base.Joiner;

import me.stafftp.hcf.util.base.Cooldowns;

public class HelpopCommand implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			System.out.println("Players only.");
		} else {
			Player p = (Player) sender;
			if (args.length < 2) {
				p.sendMessage(("§cUsage: /helpop <reason>"));
				return true;
			}
			final Player player = (Player) sender;
			if (Cooldowns.isOnCooldown("helpop_cooldown", player)) {
				sender.sendMessage("§cYou cannot do this for another §l"
						+ Cooldowns.getCooldownForPlayerInt("helpop_cooldown", player) / 1 + " §cseconds.");
				return true;
			}
			
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage("That player is not online");
                return false;
            } else {
    			p.sendMessage(("§aYour helpop has been sent to all online staff members."));
    			Cooldowns.addCooldown("helpop_cooldown", player, 60);
            }
			
			String reason = Joiner.on(' ').join(args).replace(target.getName(),"");
			for (Player p1 : Bukkit.getOnlinePlayers()) {
				if (p1.hasPermission("core.staff")) {
					p1.sendMessage(("§7(§6Helpop§7)§6 " + p.getName() + "§7 has requested help §6:" + " §f" + reason));
				}
			}
		}


		
		return false;
	}

}
