package me.scherbs.chunkbuster;

import me.scherbs.chunkbuster.utils.StringUtils;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class ChunkBuster {

  private Location location;

  public ChunkBuster(Location location) {
    this.location = location;
  }

  public void bust(Player player) {
    Location location = getLocation();
    Chunk chunk = location.getChunk();

    int cx = chunk.getX() << 4;
    int cz = chunk.getZ() << 4;

    int counter = 0;
    for (int x = cx; x < cx + 16; x++) {
      for (int z = cz; z < cz + 16; z++) {
        for (int y = 0; y < location.getBlockY(); y++) {
          Block block = chunk.getBlock(x, y, z);

          if (block.getType() != Material.BEDROCK && block.getType() != Material.AIR) {
            block.setType(Material.AIR);
            counter++;
          }
        }
      }
    }

    player.sendMessage(StringUtils.color("&eSuccessfully busted " + counter + " blocks!"));
  }

  public Location getLocation() {
    return location;
  }
}
