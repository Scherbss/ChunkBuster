package me.scherbs.chunkbuster.utils;

import java.util.Arrays;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Item {

  private ItemStack stack;
  private ItemMeta meta;

  public Item(ItemStack base) {
    this.stack = base;
    this.meta = base.getItemMeta();
  }

  public Item(Material base) {
    this(new ItemStack(base));
  }

  public static Item create(ItemStack base) {
    return new Item(base);
  }

  public static Item create(Material base) {
    return new Item(base);
  }

  public Item name(String name) {
    this.meta.setDisplayName(StringUtils.color(name));
    return this;
  }

  public Item lore(String... lore) {
    this.meta.setLore(Arrays.asList(lore));
    return this;
  }

  public Item amount(int amount) {
    this.stack.setAmount(amount);
    return this;
  }

  public Item data(int data) {
    return this.data((short) data);
  }

  public Item data(short data) {
    this.stack.setDurability(data);
    return this;
  }

  public Item enchant(Enchantment enchant) {
    return enchant(enchant, 1);
  }

  public Item enchant(Enchantment enchant, int level) {
    this.meta.addEnchant(enchant, level, true);
    return this;
  }

  public ItemStack build() {
    this.stack.setItemMeta(meta);

    return this.stack;
  }
}