package me.stafftp.hcf.special.Thanksgiving;

import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import pls.carbon.hcf.Base;


public class ThanksGivingListener implements Listener {

    private RebootRunnable rebootRunnable;

    Base plugin;

    public boolean cancel() {
        if (this.rebootRunnable != null) {
            this.rebootRunnable.cancel();
            this.rebootRunnable = null;
            return true;
        }

        return false;
    }

    public void start(long millis) {
        if (this.rebootRunnable == null) {
            this.rebootRunnable = new RebootRunnable(this, millis);
            this.rebootRunnable.runTaskLater(Base.getPlugin(), millis / 50L);
        }
    }

    public static class RebootRunnable extends BukkitRunnable {

        private ThanksGivingListener rebootTimer;
        private long startMillis;
        private static long endMillis;

        @SuppressWarnings("static-access")
		public RebootRunnable(ThanksGivingListener rebootTimer, long duration) {
            this.rebootTimer = rebootTimer;
            this.startMillis = System.currentTimeMillis();
            this.endMillis = this.startMillis + duration;
        }

        public long getRemaining() {
            return endMillis - System.currentTimeMillis();
        }

        @Override
        public void run() {
            this.cancel();
            this.rebootTimer.rebootRunnable = null;
        }
    }

    public RebootRunnable getRebootRunnable() {
        return rebootRunnable;
    }

	public long getRemaining() {
        return RebootRunnable.endMillis - System.currentTimeMillis();
	}
}
