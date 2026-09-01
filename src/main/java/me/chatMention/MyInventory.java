package me.chatMention;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MyInventory implements InventoryHolder {

    private final Inventory inventory;

    public MyInventory(ChatMention plugin, int rows) {
        this.inventory = plugin.getServer().createInventory(this, rows*9, "MentionSettings");
        ItemStack item = new ItemStack(Material.PAPER);
        ItemMeta meta = item.getItemMeta();
        if (meta != null){
            meta.setDisplayName("Notifications");
            item.setItemMeta(meta);
        }
        this.inventory.setItem(13, item);
    }


    @Override
    public Inventory getInventory() {
        return this.inventory;
    }

}