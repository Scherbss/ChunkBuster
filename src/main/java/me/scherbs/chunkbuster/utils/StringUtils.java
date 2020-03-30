package me.scherbs.chunkbuster.utils;

import org.bukkit.ChatColor;

public class StringUtils {

  public static String color(String msg) {
    return ChatColor.translateAlternateColorCodes('&', msg);
  }

}
