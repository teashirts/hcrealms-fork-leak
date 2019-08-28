package me.stafftp.hcf.factions.deathban;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.configuration.MemorySection;
import org.bukkit.entity.Player;

import gnu.trove.impl.Constants;
import me.stafftp.hcf.ConfigurationService;
import me.stafftp.hcf.factions.faction.type.Faction;
import me.stafftp.hcf.util.Config;
import me.stafftp.hcf.util.JavaUtils;
import me.stafftp.hcf.util.PersistableLocation;
import net.minecraft.util.gnu.trove.map.TObjectIntMap;
import net.minecraft.util.gnu.trove.map.hash.TObjectIntHashMap;
import net.minecraft.util.gnu.trove.procedure.TObjectIntProcedure;
import pls.carbon.hcf.Base;

public class FlatFileDeathbanManager implements DeathbanManager {

    @SuppressWarnings("unused")
	private static final int MAX_DEATHBAN_MULTIPLIER = 300;

    private final Base plugin;

    private TObjectIntMap<UUID> livesMap;
    private Config livesConfig;

    public FlatFileDeathbanManager(Base plugin) {
        this.plugin = plugin;
        this.reloadDeathbanData();
    }

    @Override
    public TObjectIntMap<UUID> getLivesMap() {
        return livesMap;
    }

    @Override
    public int getLives(UUID uuid) {
        return livesMap.get(uuid);
    }

    @Override
    public int setLives(UUID uuid, int lives) {
        livesMap.put(uuid, lives);
        return lives;
    }

    @Override
    public int addLives(UUID uuid, int amount) {
        return livesMap.adjustOrPutValue(uuid, amount, amount);
    }

    @Override
    public int takeLives(UUID uuid, int amount) {
        return setLives(uuid, getLives(uuid) - amount);
    }

    @Override
    public double getDeathBanMultiplier(final Player player) {
        for (int i = 5; i < 300; ++i) {
            if (player.hasPermission("hcf.deathban.multiplier." + i)) {
                return i / 100.0;
            }
        }
        return 1.0;
    }

    @Override
    public Deathban applyDeathBan(Player player, String reason)
    {
        Location location = player.getLocation();
        Faction factionAt = plugin.getFactionManager().getFactionAt(location);

        long duration = ConfigurationService.DEFAULT_DEATHBAN_DURATION;
        if (!factionAt.isDeathban()) {
            duration /= 2; // non-deathban factions should be 50% qui
            
        }
        else if (player.hasPermission("hcf.deathban.6"))
        {
            duration = JavaUtils.parse("60m"); // hcf.deathban.6
        }
        else if (player.hasPermission("hcf.deathban.5"))
        {
            duration = JavaUtils.parse("45m"); // hcf.deathban.5
        }
        else if (player.hasPermission("hcf.deathban.4"))
        {
            duration = JavaUtils.parse("25m"); // hcf.deathban.4
        }
        else if (player.hasPermission("hcf.deathban.3"))
        {
            duration = JavaUtils.parse("20m"); // hcf.deathban.3
        }
        else if (player.hasPermission("hcf.deathban.2"))
        {
            duration = JavaUtils.parse("15m"); // hcf.deathban.2
        }
        else if (player.hasPermission("hcf.deathban.1"))
        {
            duration = JavaUtils.parse("10m"); // hcf.deathban.1
        }

        duration *= getDeathBanMultiplier(player);
        duration *= factionAt.getDeathbanMultiplier();
        return applyDeathBan(player.getUniqueId(), new Deathban(reason, Math.min(MAX_DEATHBAN_TIME, duration), new PersistableLocation(location)));
    }

    @Override
    public Deathban applyDeathBan(UUID uuid, Deathban deathban) {
        plugin.getUserManager().getUser(uuid).setDeathban(deathban);
        return deathban;
    }

    @Override
    public void reloadDeathbanData() {
        livesConfig = new Config(plugin, "lives");
        Object object = livesConfig.get("lives");
        if (object instanceof MemorySection) {
            MemorySection section = (MemorySection) object;
            Set<String> keys = section.getKeys(false);
            livesMap = new TObjectIntHashMap<>(keys.size(), Constants.DEFAULT_LOAD_FACTOR, 0);
            for (String id : keys) {
                livesMap.put(UUID.fromString(id), livesConfig.getInt(section.getCurrentPath() + "." + id));
            }
        } else {
            livesMap = new TObjectIntHashMap<>(Constants.DEFAULT_CAPACITY, Constants.DEFAULT_LOAD_FACTOR, 0);
        }
    }

    @Override
    public void saveDeathbanData() {
        Map<String, Integer> saveMap = new LinkedHashMap<>(livesMap.size());
        livesMap.forEachEntry(new TObjectIntProcedure<UUID>() {
            @Override
            public boolean execute(UUID uuid, int i) {
                saveMap.put(uuid.toString(), i);
                return true;
            }
        });

        livesConfig.set("lives", saveMap);
        livesConfig.save();
    }
}
