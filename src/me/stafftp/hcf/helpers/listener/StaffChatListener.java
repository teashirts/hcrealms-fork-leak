package me.stafftp.hcf.helpers.listener;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.stafftp.hcf.ConfigurationService;

public class StaffChatListener implements Listener {

	static StaffChatListener instance = new StaffChatListener();

	public static StaffChatListener getInstance() {
		return instance;
	}

	private ArrayList<Player> StaffChat = new ArrayList<Player>();

	public boolean isInStaffChat(Player p) {
		return StaffChat.contains(p);
	}

	public void setStaffChat(Player p, boolean b) {
		if (b) {
			if (isInStaffChat(p))
				return;
			StaffChat.add(p);
		} else {
			if (!isInStaffChat(p))
				return;
			StaffChat.remove(p);
		}
	}

	public ArrayList<Player> listInStaffChat() {
		return StaffChat;
	}

	@SuppressWarnings("deprecation")
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		if (StaffChatListener.getInstance().isInStaffChat(p)) {
			for (Player online : Bukkit.getOnlinePlayers()) {
				{
					{
						e.setCancelled(true);
						if (online.hasPermission("command.staffchat")) {
							//online.sendMessage(ChatColor.translateAlternateColorCodes('&',
								//	"&7(&c&lSC&7) &e" + p.getName() + ": &c" + e.getMessage()));
							online.sendMessage(ChatColor.translateAlternateColorCodes('&', ConfigurationService.SCMSG).replace("%staffmember%", p.getName()).replace("%message%", e.getMessage()));
						}
					}
				}
			}
		}
	}
}