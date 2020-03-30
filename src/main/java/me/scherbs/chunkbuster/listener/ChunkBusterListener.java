package me.scherbs.chunkbuster.listener;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import me.scherbs.chunkbuster.ChunkBuster;
import me.scherbs.chunkbuster.ChunkBusterPlugin;
import me.scherbs.chunkbuster.utils.Item;
import me.scherbs.chunkbuster.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ChunkBusterListener implements Listener {

  private static Inventory CONFIRM_INVENTORY;
  private static ItemStack YES, NO;

  static {
    CONFIRM_INVENTORY = Bukkit.createInventory(null, 9, "Please confirm...");

    for (int i = 0; i < 9; i++) {
      CONFIRM_INVENTORY.setItem(i, new Item(Material.STAINED_GLASS_PANE).name(" ").build());
    }

    YES = new Item(Material.INK_SACK)
        .data(10)
        .name("&aYES")
        .build();

    NO = new Item(Material.INK_SACK)
        .data(1)
        .name("&cNO")
        .build();

    CONFIRM_INVENTORY.setItem(2, YES);
    CONFIRM_INVENTORY.setItem(6, NO);
  }

  private Map<UUID, ChunkBuster> chunkBusterMap = new HashMap<>();

  @EventHandler
  public void onInventoryClick(InventoryClickEvent event) {
    if (event.getInventory() == null) {
      return;
    }
    if (event.getClickedInventory() == null || !event.getInventory().getTitle()
        .equalsIgnoreCase(CONFIRM_INVENTORY.getTitle())) {
      return;
    }
    if (event.getCurrentItem().getType() == Material.STAINED_GLASS_PANE) {
      event.setCancelled(true);
      return;
    }
    ChunkBuster chunkBuster = this.chunkBusterMap.get(event.getWhoClicked().getUniqueId());
    if (chunkBuster == null) {
      event.getWhoClicked().closeInventory();
      return;
    }

    if (event.getCurrentItem().isSimilar(YES)) {
      event.setCancelled(true);
      chunkBuster.bust((Player) event.getWhoClicked());
      this.chunkBusterMap.remove(event.getWhoClicked().getUniqueId());

      event.getWhoClicked().closeInventory();
      return;
    }
    if (event.getCurrentItem().isSimilar(NO)) {
      event.setCancelled(true);
      event.getWhoClicked().closeInventory();
    }
  }

  @EventHandler
  public void onCloseInventory(InventoryCloseEvent event) {
    if (!event.getInventory().getTitle().equalsIgnoreCase(CONFIRM_INVENTORY.getTitle())) {
      return;
    }
    Player player = (Player) event.getPlayer();
    ChunkBuster chunkBuster = this.chunkBusterMap.get(player.getUniqueId());
    if (chunkBuster == null) {
      return;
    }
    this.chunkBusterMap.remove(player.getUniqueId());
    player.getInventory().addItem(ChunkBusterPlugin.CHUNK_BUSTER.clone());
    player.sendMessage(StringUtils.color("&cChunkBuster cancelled."));
  }

  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent event) {
    Player player = event.getPlayer();
    ChunkBuster chunkBuster = this.chunkBusterMap.get(player.getUniqueId());
    if (chunkBuster == null) {
      return;
    }
    this.chunkBusterMap.remove(player.getUniqueId());
    player.getInventory().addItem(ChunkBusterPlugin.CHUNK_BUSTER.clone());
  }

  @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
  public void onBlockPlace(BlockPlaceEvent event) {
    Player player = event.getPlayer();
    if (player.getItemInHand() == null) {
      return;
    }
    if (!player.getItemInHand().hasItemMeta()) {
      return;
    }
    if (!player.getItemInHand().getItemMeta().hasDisplayName()) {
      return;
    }
    if (ChunkBusterPlugin.CHUNK_BUSTER.isSimilar(player.getItemInHand())) {
      event.getBlock().setType(Material.AIR);

      this.chunkBusterMap
          .put(player.getUniqueId(), new ChunkBuster(event.getBlock().getLocation()));
      player.openInventory(CONFIRM_INVENTORY);
    }
  }
}
