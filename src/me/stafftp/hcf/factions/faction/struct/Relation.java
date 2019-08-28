package me.stafftp.hcf.factions.faction.struct;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;

import me.stafftp.hcf.ConfigurationService;
import me.stafftp.hcf.util.BukkitUtils;

public enum Relation {
    MEMBER(3),
    ALLY(2),
    ENEMY(1);

    private final int value;

    private Relation(final int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public boolean isAtLeast(final Relation relation) {
        return this.value >= relation.value;
    }

    public boolean isAtMost(final Relation relation) {
        return this.value <= relation.value;
    }

    public boolean isMember() {
        return this == Relation.MEMBER;
    }

    public boolean isAlly() {
        return this == Relation.ALLY;
    }

    public boolean isEnemy() {
        return this == Relation.ENEMY;
    }

    public String getDisplayName() {
        switch(this) {
            case ALLY: {
                return this.toChatColour() + "alliance";
            }
            default: {
                return this.toChatColour() + this.name().toLowerCase();
            }
        }
    }

    @SuppressWarnings("incomplete-switch")
	public ChatColor toChatColour()
    {
      switch (this)
      {
      case ALLY: 
        return ConfigurationService.ALLY_COLOUR;
      case ENEMY: 
        return ConfigurationService.ENEMY_COLOUR;
      }
      return ConfigurationService.TEAMMATE_COLOUR;
    }

    public DyeColor toDyeColour() {
        return BukkitUtils.toDyeColor(this.toChatColour());
    }
}
