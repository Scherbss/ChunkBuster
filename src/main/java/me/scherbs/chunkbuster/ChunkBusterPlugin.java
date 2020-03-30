package me.scherbs.chunkbuster;

import java.util.ArrayList;
import java.util.List;
import me.scherbs.chunkbuster.commands.ChunkBusterCommand;
import me.scherbs.chunkbuster.listener.ChunkBusterListener;
import me.scherbs.chunkbuster.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class ChunkBusterPlugin extends JavaPlugin {

  public static ItemStack CHUNK_BUSTER;

  private static ChunkBusterPlugin instance;

  public static ChunkBusterPlugin getInstance() {
    return instance;
  }

  @Override
  public void onEnable() {
    instance = this;

    if (!getDataFolder().exists()) {
      getDataFolder().mkdirs();
    }
    saveDefaultConfig();

    Bukkit.getPluginManager().registerEvents(new ChunkBusterListener(), this);
    getCommand("chunkbuster").setExecutor(new ChunkBusterCommand());

    CHUNK_BUSTER = new ItemStack(
        Material.valueOf(
            ChunkBusterPlugin.getInstance().getConfig().getString("chunkbuster.material")));

    String name = ChunkBusterPlugin.getInstance().getConfig().getString("chunkbuster.name");
    List<String> lore = ChunkBusterPlugin.getInstance().getConfig()
        .getStringList("chunkbuster.lore");

    List<String> temp = new ArrayList<>();
    for (String string : lore) {
      string = StringUtils.color(string);
      temp.add(string);
    }
    lore.clear();
    lore.addAll(temp);

    ItemMeta meta = CHUNK_BUSTER.getItemMeta();
    meta.setDisplayName(StringUtils.color(name));
    meta.setLore(lore);

    CHUNK_BUSTER.setItemMeta(meta);
  }

  @Override
  public void onDisable() {

  }

}
