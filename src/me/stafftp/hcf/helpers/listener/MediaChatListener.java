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

public class MediaChatListener implements Listener {

	static MediaChatListener instance = new MediaChatListener();

	public static MediaChatListener getInstance() {
		return instance;
	}

	private ArrayList<Player> MediaChat = new ArrayList<Player>();

	public boolean isInMediaChat(Player p) {
		return MediaChat.contains(p);
	}

	public void setMediaChat(Player p, boolean b) {
		if (b) {
			if (isInMediaChat(p))
				return;
			MediaChat.add(p);
		} else {
			if (!isInMediaChat(p))
				return;
			MediaChat.remove(p);
		}
	}

	public ArrayList<Player> listInMediaChat() {
		return MediaChat;
	}

	@SuppressWarnings("deprecation")
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		if (MediaChatListener.getInstance().isInMediaChat(p)) {
			for (Player online : Bukkit.getOnlinePlayers()) {
				{
					{
						e.setCancelled(true);
						if (online.hasPermission("command.mediachat")) {
							online.sendMessage(ChatColor.translateAlternateColorCodes('&', ConfigurationService.MCMSG).replace("%mediamember%", p.getName()).replace("%message%", e.getMessage()));
						}
					}
				}
			}
		}
	}


	
}