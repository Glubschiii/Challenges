package it.glubschiii.Challenges.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.EnumSet;

/** @author Glubschiii | https://github.com/glubschiii
 @since 1.0.5
 */

public class MainInventoryManager implements Listener {

    /*
     * Creating custom /settings inventories
     */
    public static Inventory settingsinv = Bukkit.createInventory(null, 45, ChatColor.GOLD + "Settings" + ChatColor.DARK_GRAY + " • " +
            ChatColor.BLUE + "Übersicht");
    /*
    * Putting items inside the custom /settings inventories
     */
    static ItemStack background = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE, 1).setName(" ").toItemStack();
    static ItemStack background2 = new ItemBuilder(Material.LIGHT_GRAY_STAINED_GLASS_PANE, 1).setName(" ").toItemStack();
    static ItemStack herausforderungen = new ItemBuilder(Material.DRAGON_HEAD, 1).setName(ChatColor.RED + "Herausforderungen").addLoreLine(" ").
            addLoreLine(ChatColor.RED + " Herausforderungen " + ChatColor.GRAY + "geben das " + ChatColor.RED + "Ziel").addLoreLine(ChatColor.GRAY + " und " +
                    "das " + ChatColor.RED + "Ende " + ChatColor.GRAY + "der " + ChatColor.RED + "Challenge " + ChatColor.GRAY + "vor.").addLoreLine(" ").addLoreLine(
                            ChatColor.RED + "" + ChatColor.ITALIC + "Klick: " + ChatColor.RED + "Übersicht").toItemStack();
    public static void puttingBackground(int i) {
        int[] backgroundItems = {0, 1, 7, 8, 9, 17, 18, 26, 27, 35, 36, 37, 43, 44};
        for (i = 0; i <= backgroundItems.length; i++) {
            settingsinv.setItem(i, background);
        }
        for(i = 2; i <= 6; i++) {
            settingsinv.setItem(i, background2);
            for(i = 10; i <= 16; i++) {
                settingsinv.setItem(i, background2);
            }
            for(i = 19; i <= 26; i++) {
                settingsinv.setItem(i, background2);
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        /*
        * Prevent player's from clicking on the items inside the custom invs.
         */
        if (event.getView().getTitle().equalsIgnoreCase(ChatColor.GOLD + "Settings" + ChatColor.DARK_GRAY + " • " +
                ChatColor.BLUE + "Übersicht") || event.getView().getTitle().equalsIgnoreCase(ChatColor.GREEN + "Spielregeln" + ChatColor.DARK_GRAY + " • " +
                ChatColor.BLUE + "Seite 1")) {
            event.setCancelled(true);
        }
    }
}
