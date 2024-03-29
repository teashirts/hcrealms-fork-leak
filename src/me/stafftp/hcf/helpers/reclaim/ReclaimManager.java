package me.stafftp.hcf.helpers.reclaim;

import java.util.List;

import org.bukkit.entity.Player;

import lombok.Data;
import me.stafftp.hcf.util.Config;
import pls.carbon.hcf.Base;

@Data
public class ReclaimManager {

	Base base;
	Config config;
	
	public ReclaimManager(Base base) {
		this.base = base;
		this.base.getCommand("reclaim").setExecutor(new ReclaimCommand(this));
		this.config = new Config(base,"reclaim");
		this.config.save();
	}
	
	
	public boolean hasReclaimed(Player player) {
		if(config.get("users."+player.getName()) == null){
			return false;
		}
		return true;
	}
	
	public void setReclaimed(Player player, boolean bool) {
		if(bool) {
			config.set("users."+player.getName(),1);
			config.save();
			return;
		}
		config.set("users."+player.getName(), null);
		config.save();
		return;
	}
	
	public List<String> getReclaim(String rank){
		return config.getStringList(rank+".commands");
	}
	

}
