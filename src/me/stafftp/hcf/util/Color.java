package me.stafftp.hcf.util;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;

public class Color
{
  public static String translate(String input)
  {
    return ChatColor.translateAlternateColorCodes('&', input);
  }
  
  public static List<String> translateFromArray(List<String> text)
  {
    List<String> messages = new ArrayList<String>();
    for (String string : text) {
      messages.add(translate(string));
    }
    return messages;
  }
}
