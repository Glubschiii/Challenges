package it.glubschiii.Challenges.challenges;

import it.glubschiii.Challenges.timer.Timer;
import it.glubschiii.Challenges.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class NoCraftingTableChallenge implements Listener {

    @EventHandler
    private void onCraftingTableCraft(CraftItemEvent event) {
        if (Config.contains("challenges.nocraftingtable") && Config.getBoolean("challenges.nocraftingtable").booleanValue()) {
            if (Timer.isRunning()) {
                Inventory inventory = event.getInventory();
                if(inventory.getType() == InventoryType.WORKBENCH) {
                    Player player = (Player) event.getWhoClicked();
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        all.sendMessage(ChatColor.DARK_GRAY + "» " + ChatColor.GOLD.toString() + ChatColor.BOLD + player.getDisplayName() + " " +
                                ChatColor.RESET + "" + ChatColor.GRAY + "hat etwas in der Werkbank hergestellt.");
                    }
                    player.setHealth(0);
                }
            }
        }
    }
}
