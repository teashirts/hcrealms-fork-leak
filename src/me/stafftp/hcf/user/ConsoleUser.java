package me.stafftp.hcf.user;


import org.bukkit.configuration.serialization.*;

import me.stafftp.hcf.util.ServerParticipator;

import java.util.*;

public class ConsoleUser extends ServerParticipator implements ConfigurationSerializable
{
    public static final UUID CONSOLE_UUID;
    String name;
    
    static {
        CONSOLE_UUID = UUID.fromString("00000000-0000-0000-0000-000000000000");
    }
    
    public ConsoleUser() {
        super(ConsoleUser.CONSOLE_UUID);
        this.name = "CONSOLE";
    }
    
    public ConsoleUser(final Map<String, Object> map) {
        super(map);
        this.name = "CONSOLE";
    }
    
    @Override
    public String getName() {
        return this.name;
    }
}
