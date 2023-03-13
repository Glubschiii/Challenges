package it.glubschiii.Challenges.commands;

import it.glubschiii.Challenges.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/** @author Glubschiii | https://github.com/glubschiii
 @since 1.0.3
 */
public class BackpackCommand implements CommandExecutor, Listener {

    private static Inventory inventory = null;
    private final String backpackName = ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + "Backpack";

    public BackpackCommand() {

        int slots = 27;

        if (Config.contains("command.backpack.slots")) {
            slots = (int) Config.get("command.backpack.slots");
        } else {
            try {
                Config.set("command.backpack.slots", 27);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        List<ItemStack> backpackContents = (List<ItemStack>) Config.get("command.backpack.backpack-contents");
        if(backpackContents != null) {
            inventory = Bukkit.createInventory(null, slots, backpackName);
            for(int i = 0; i < backpackContents.size() && i < slots; i++) {
                inventory.setItem(i, backpackContents.get(i));
            }
        } else {
            inventory = Bukkit.createInventory(null, slots, backpackName);
        }
    }

    public static void saveBackpack() throws IOException {
        List<ItemStack> items = new ArrayList<>();
        for (int i = 0; i < inventory.getSize(); i++) {
            items.add(inventory.getItem(i));
        }
        Config.set("command.backpack.backpack-contents", items);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player)sender;
            if(Config.getBoolean("settings.backpack").booleanValue()) {
                player.openInventory(inventory);
                player.playSound(player.getLocation(), Sound.BLOCK_ENDER_CHEST_OPEN, 3.0F, 0.5F);
                try {
                    saveBackpack();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                player.sendMessage(ChatColor.RED + "Das Backpack ist derzeit deaktiviert!");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Dieser Befehl kann nur als Spieler ausgeführt werden!");
        }
        return false;
    }

}
