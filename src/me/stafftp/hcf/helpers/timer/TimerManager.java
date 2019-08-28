package me.stafftp.hcf.helpers.timer;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import me.stafftp.hcf.factions.event.EventTimer;
import me.stafftp.hcf.factions.reboot.RebootTimer;
import me.stafftp.hcf.factions.sale.SaleTimer;
import me.stafftp.hcf.helpers.timer.type.AppleTimer;
import me.stafftp.hcf.helpers.timer.type.ArcherTimer;
import me.stafftp.hcf.helpers.timer.type.EnderPearlTimer;
import me.stafftp.hcf.helpers.timer.type.GappleTimer;
import me.stafftp.hcf.helpers.timer.type.HomeTimer;
import me.stafftp.hcf.helpers.timer.type.LogoutTimer;
import me.stafftp.hcf.helpers.timer.type.PvPTimerProtection;
import me.stafftp.hcf.helpers.timer.type.PvpClassWarmupTimer;
import me.stafftp.hcf.helpers.timer.type.SpawnTagTimer;
import me.stafftp.hcf.helpers.timer.type.StuckTimer;
import me.stafftp.hcf.helpers.timer.type.TeleportTimer;
import me.stafftp.hcf.special.Buy1Get1.Buy1Get1Timer;
import me.stafftp.hcf.special.Christmas.ChristmasTimer;
import me.stafftp.hcf.special.DoubleKey.DoubleKeyTimer;
import me.stafftp.hcf.special.FlashSale.FlashSaleTimer;
import me.stafftp.hcf.special.KeySale.KeySaleTimer;
import me.stafftp.hcf.special.Thanksgiving.ThanksGivingTimer;
import me.stafftp.hcf.special.TripleKey.TripleKeyTimer;
import me.stafftp.hcf.special.keyall.KeyallTimer;
import me.stafftp.hcf.util.Config;
import pls.carbon.hcf.Base;


public class TimerManager implements Listener {
	
	
    private final RebootTimer rebootTimer;
    private final SaleTimer saleTimer;
    private final ThanksGivingTimer thanksgivingtimer;
    private final KeyallTimer keyalltimer;
    private final ChristmasTimer christmastimer;
    private final KeySaleTimer keysaletimer;
    private final FlashSaleTimer flashsaletimer;
	public final SpawnTagTimer combatTimer;
    public final LogoutTimer logoutTimer;
    public final EnderPearlTimer enderPearlTimer;
    public final EventTimer eventTimer;
	public final GappleTimer gappleTimer;
	public final Buy1Get1Timer buy1get1timer;
	public final TripleKeyTimer triplekeytimer;
	public final DoubleKeyTimer doublekeytimer;
    public final PvPTimerProtection invincibilityTimer;
    private final PvpClassWarmupTimer pvpClassWarmupTimer;
    public final HomeTimer home;
    public final AppleTimer appleTimer;
    public final StuckTimer stuckTimer;
	public RebootTimer getRebootTimer() {
		return rebootTimer;
	}
	
	public SaleTimer getSaleTimer() {
		return saleTimer;
	}
	
	public ThanksGivingTimer getThanksGivingTimer() {
		return thanksgivingtimer;
	}
	
	public ChristmasTimer getChristmasTimer() {
		return christmastimer;
	}
	
	public KeyallTimer getKeyallTimer() {
		return keyalltimer;
	}
	
	public KeySaleTimer keysaleTimer() {
		return keysaletimer;
	}

	public final TeleportTimer teleportTimer;
    public final ArcherTimer archerTimer;
    private final Set<Timer> timers = new LinkedHashSet<>();
    private final JavaPlugin plugin;
    private Config config;

    public TimerManager(Base plugin) {
        (this.plugin = plugin).getServer().getPluginManager().registerEvents(this, plugin);
        this.registerTimer(this.enderPearlTimer = new EnderPearlTimer(plugin));
        this.registerTimer(this.home = new HomeTimer());
        this.registerTimer(this.logoutTimer = new LogoutTimer());
        this.registerTimer(this.gappleTimer = new GappleTimer(plugin));
        this.registerTimer(this.stuckTimer = new StuckTimer());
        this.registerTimer(this.invincibilityTimer = new PvPTimerProtection(plugin));
        this.registerTimer(this.combatTimer = new SpawnTagTimer(plugin));
        this.registerTimer(this.teleportTimer = new TeleportTimer(plugin));
        this.registerTimer(this.eventTimer = new EventTimer(plugin));
        this.registerTimer(this.archerTimer = new ArcherTimer(plugin));
        this.registerTimer(this.pvpClassWarmupTimer = new PvpClassWarmupTimer(plugin));
        this.registerTimer(this.rebootTimer = new RebootTimer(plugin));
        this.registerTimer(this.saleTimer = new SaleTimer(plugin));
        this.registerTimer(this.thanksgivingtimer = new ThanksGivingTimer(plugin));
        this.registerTimer(this.christmastimer = new ChristmasTimer(plugin));
        this.registerTimer(this.appleTimer = new AppleTimer(plugin));
        this.registerTimer(this.buy1get1timer = new Buy1Get1Timer(plugin));
        this.registerTimer(this.keysaletimer = new KeySaleTimer(plugin));
        this.registerTimer(this.flashsaletimer = new FlashSaleTimer(plugin));
        this.registerTimer(this.doublekeytimer = new DoubleKeyTimer(plugin));
        this.registerTimer(this.triplekeytimer = new TripleKeyTimer(plugin));
        this.registerTimer(this.keyalltimer = new KeyallTimer(plugin));
        this.reloadTimerData();
    }

    public void registerTimer(Timer timer) {
        this.timers.add(timer);
        if (timer instanceof Listener) {
            this.plugin.getServer().getPluginManager().registerEvents((Listener) timer, this.plugin);
        }
    }

    public void unregisterTimer(Timer timer) {
        this.timers.remove(timer);
    }

    /**
     * Reloads the {@link Timer} data from storage.
     */
    public void reloadTimerData() {
        this.config = new Config(plugin, "timers");
        for (Timer timer : this.timers) {
            timer.load(this.config);
        }
    }

    /**
     * Saves the {@link Timer} data to storage.
     */
    public void saveTimerData() {
        for (Timer timer : this.timers) {
            timer.onDisable(this.config);
        }

        this.config.save();
    }

    public EventTimer getEventTimer() {
        return eventTimer;
    }

    public PvPTimerProtection getInvincibilityTimer() {
        return invincibilityTimer;
    }

    public SpawnTagTimer getCombatTimer() {
        return combatTimer;
    }

    public ArcherTimer getArcherTimer(){
    	return archerTimer;
    }
    
    
    public EnderPearlTimer getEnderPearlTimer() {
        return enderPearlTimer;
    }
    
    public GappleTimer getGappleTimer() {
        return gappleTimer;
    }

    public PvpClassWarmupTimer getPvpClassWarmupTimer() {
        return pvpClassWarmupTimer;
    }

    public AppleTimer getAppleTimer() {
    		return appleTimer;
    }
    
    public TeleportTimer getTeleportTimer() {
        return teleportTimer;
    }

    public StuckTimer getStuckTimer() {
        return stuckTimer;
    }

    public LogoutTimer getLogoutTimer() {
        return logoutTimer;
    }

    public Collection<Timer> getTimers() {
        return this.timers;
    }

	public KeySaleTimer getKeySaleTimer() {
		return keysaletimer;
	}

	public FlashSaleTimer getFlashSaleTimer() {
		return flashsaletimer;
	}

	public Buy1Get1Timer getBuy1Get1Timer() {
		return buy1get1timer;
	}

	public TripleKeyTimer getTripleKeyTimer() {
		// TODO Auto-generated method stub
		return triplekeytimer;
	}

	public DoubleKeyTimer getDoubleKeyTimer() {
		// TODO Auto-generated method stub
		return null;
	}

}
