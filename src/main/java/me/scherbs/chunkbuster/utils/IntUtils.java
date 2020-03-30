package me.scherbs.chunkbuster.utils;

public class IntUtils {

  public static boolean isInt(String input) {
    try {
      Integer.parseInt(input);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }
}
