package me.scherbs.chunkbuster.commands;

import me.scherbs.chunkbuster.ChunkBusterPlugin;
import me.scherbs.chunkbuster.utils.IntUtils;
import me.scherbs.chunkbuster.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ChunkBusterCommand implements CommandExecutor {

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (!(sender.hasPermission("chunkbuster.give"))) {
      sender.sendMessage(StringUtils.color("&cNo permission."));
      return true;
    }
    if (args.length == 0 || args.length >= 3) {
      sender.sendMessage(StringUtils.color("&cUsage: /chunkbuster <player> <amount>"));
      return true;
    }
    Player target = Bukkit.getPlayer(args[0]);

    if (target == null) {
      sender.sendMessage(StringUtils.color("&cNo player found."));
      return true;
    }
    if (!IntUtils.isInt(args[1])) {
      sender.sendMessage(StringUtils.color("&cInvalid integer: " + args[1]));
      return true;
    }
    int amount = Integer.parseInt(args[1]);
    ItemStack clone = ChunkBusterPlugin.CHUNK_BUSTER.clone();
    clone.setAmount(amount);

    target.getInventory().addItem(clone);
    sender.sendMessage(
        StringUtils.color("&aGiven " + amount + " chunkbusters to " + target.getName() + "."));
    return true;
  }
}
