package me.stafftp.hcf.helpers.tablist;

import org.bukkit.entity.*;

public interface TablistEntrySupplier
{
    net.minecraft.util.com.google.common.collect.Table<Integer, Integer, String> getEntries(final Player p0);
    
    String getHeader(final Player p0);
    
    String getFooter(final Player p0);
}
