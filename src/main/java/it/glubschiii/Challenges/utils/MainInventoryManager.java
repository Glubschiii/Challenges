package it.glubschiii.Challenges.utils;

import it.glubschiii.Challenges.Main;
import it.glubschiii.Challenges.timer.Timer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Difficulty;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.io.IOException;

import static it.glubschiii.Challenges.gamerules.RegenerationGamerule.getStatus;

/**
 * @author Glubschiii | https://github.com/glubschiii
 * @since 1.0.5
 */

public class MainInventoryManager implements Listener {

    /*
     * Creating custom /settings inventories
     */
    public static Inventory settingsinv = Bukkit.createInventory(null, 45, ChatColor.GOLD + "Settings" + ChatColor.DARK_GRAY + " • " +
            ChatColor.BLUE + "Übersicht");
    public static Inventory gamerulesinv = Bukkit.createInventory(null, 54, ChatColor.GREEN + "Spielregeln" + ChatColor.DARK_GRAY + " • " +
            ChatColor.BLUE + "Seite 1");
    public static Inventory timerinv = Bukkit.createInventory(null, 36, ChatColor.YELLOW + "Timer" + ChatColor.DARK_GRAY + " • " +
            ChatColor.BLUE + "Seite 1");
    public static Inventory timerinv2 = Bukkit.createInventory(null, 36, ChatColor.YELLOW + "Timer" + ChatColor.DARK_GRAY + " • " +
            ChatColor.BLUE + "Seite 2");        //TODO: Namen und Farben ändern..
    /*
     * Putting items inside the custom /settings inventories and it's repositories
     */
    static ItemStack background = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setName(" ").toItemStack();
    static ItemStack background2 = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName(" ").toItemStack();
    static ItemStack back = new ItemBuilder(Material.DARK_OAK_DOOR).setName(ChatColor.AQUA + "Zurück").toItemStack();
    static ItemStack next = new ItemBuilder(Material.IRON_DOOR).setName(ChatColor.GRAY + "Seite 2").toItemStack();

    public static void puttingItems() throws IOException {
        int[] background2SlotsMainInv = {0, 1, 7, 8, 9, 17, 18, 26, 27, 35, 36, 37, 43, 44};
        int[] backgroundSlotsGamerulesInv = {0, 4, 8, 9, 13, 17, 18, 22, 26, 27, 31, 35, 36, 40, 44, 49, 53};
        int[] background2SlotsGamerulesInv = {1, 7, 10, 16, 19, 25, 28, 34, 37, 43, 46, 52};
        int[] backgroundSlotsTimerInv = {0, 2, 6, 8, 9, 11, 13, 15, 17, 18, 26, 29, 31, 33};
        int[] background2SlotsTimerInv = {1, 3, 5, 7, 10, 12, 14, 16, 19, 21, 23, 25, 28, 30, 32, 34};
        int[] backgroundSlotsTimerInv2 = {0, 2, 4, 6, 8, 9, 11, 13, 15, 17, 18, 20, 22, 24, 26, 29, 31, 33};
        int[] background2SlotsTimerInv2 = {28, 30, 32, 34};
        int[] modifyTimeSlotsTimerInv2 = {1, 3, 5, 7, 19, 21, 23, 25};
        puttingItemsHelper(background2SlotsMainInv, settingsinv, background2);
        puttingItemsHelper(backgroundSlotsGamerulesInv, gamerulesinv, background);
        puttingItemsHelper(background2SlotsGamerulesInv, gamerulesinv, background2);
        puttingItemsHelper(backgroundSlotsTimerInv, timerinv, background);
        puttingItemsHelper(background2SlotsTimerInv, timerinv, background2);
        puttingItemsHelper(backgroundSlotsTimerInv2, timerinv2, background);
        puttingItemsHelper(background2SlotsTimerInv2, timerinv2, background2);
        puttingItemsHelper(modifyTimeSlotsTimerInv2, timerinv2, modifytime);
        for (int i = 2; i <= 44; i++) {
            if (i <= 3 || i == 5 || i == 6 || (i >= 10 && i <= 16) || i == 19 || i == 21 || i == 23 || i == 25 || (i >= 28 && i <= 34) || (i >= 38 && i <= 39) || i == 41 || i == 42) {
                settingsinv.setItem(i, background);
            }
        }
        settingsinv.setItem(4, timer);
        settingsinv.setItem(20, herausforderungen);
        settingsinv.setItem(22, challenges);
        settingsinv.setItem(24, gamerules);
        settingsinv.setItem(40, settings);
        gamerulesinv.setItem(2, difficulty);
        gamerulesinv.setItem(11, regeneration);
        gamerulesinv.setItem(45, back);
        timerinv.setItem(4, toggle);
        timerinv.setItem(20, resume);
        timerinv.setItem(22, reset);
        timerinv.setItem(24, pause);
        timerinv.setItem(27, back);
        timerinv.setItem(35, next);
    }

    /*
     * Method which helps to put items faster inside the custom inventories
     */
    private static void puttingItemsHelper(int[] name, Inventory invtype, ItemStack itemtype) {
        for (int slot : name) {
            invtype.setItem(slot, itemtype);
        }
    }

    /*
     * Creating items for the /settings inventory
     */
    static ItemStack timer = new ItemBuilder(Material.CLOCK).setName(ChatColor.YELLOW + "Timer").addLoreLine(" ")
            .addLoreLine(ChatColor.YELLOW + " Timer" + ChatColor.GRAY + " gibt die" + ChatColor.YELLOW + " Zeit" + ChatColor.GRAY + " an und")
            .addLoreLine(ChatColor.GRAY + " eine zeitliche" + ChatColor.YELLOW + " Begrenzung" + ChatColor.GRAY + " für")
            .addLoreLine(ChatColor.YELLOW + " Aufgaben" + ChatColor.GRAY + " und " + ChatColor.YELLOW + "Herausforderungen" + ChatColor.GRAY + ".")
            .addLoreLine(" ").addLoreLine(ChatColor.YELLOW + "" + ChatColor.ITALIC + "Klick: " + ChatColor.YELLOW + "Übersicht").toItemStack();
    static ItemStack herausforderungen = new ItemBuilder(Material.DRAGON_HEAD).setName(ChatColor.RED + "Herausforderungen").addLoreLine(" ")
            .addLoreLine(ChatColor.RED + " Herausforderungen " + ChatColor.GRAY + "geben das " + ChatColor.RED + "Ziel")
            .addLoreLine(ChatColor.GRAY + " und das " + ChatColor.RED + "Ende " + ChatColor.GRAY + "der " + ChatColor.RED + "Challenge " + ChatColor.GRAY + "vor.")
            .addLoreLine(" ").addLoreLine(ChatColor.RED + "" + ChatColor.ITALIC + "Klick: " + ChatColor.RED + "Übersicht").toItemStack();
    static ItemStack challenges = new ItemBuilder(Material.GRASS_BLOCK).setName(ChatColor.AQUA + "Challenges").addLoreLine(" ")
            .addLoreLine(ChatColor.AQUA + " Challenges " + ChatColor.GRAY + "verändern").addLoreLine(ChatColor.GRAY + " das " + ChatColor.AQUA + "Spielgeschehen " + ChatColor.GRAY + "auf")
            .addLoreLine(ChatColor.GRAY + " viele " + ChatColor.AQUA + "Arten " + ChatColor.GRAY + "und " + ChatColor.AQUA + "Weisen" + ChatColor.GRAY + ".")
            .addLoreLine(" ").addLoreLine(ChatColor.AQUA + "" + ChatColor.ITALIC + "Klick: " + ChatColor.AQUA + "Übersicht").toItemStack();
    static ItemStack gamerules = new ItemBuilder(Material.MAP).setName(ChatColor.GREEN + "Spielregeln").addLoreLine(" ")
            .addLoreLine(ChatColor.GREEN + " Spielregeln " + ChatColor.GRAY + "sind normale").addLoreLine(ChatColor.GREEN + " /gamerules " + ChatColor.GRAY + "und weitere")
            .addLoreLine(ChatColor.GREEN + " Veränderungen " + ChatColor.GRAY + "des Spielgeschehens").addLoreLine(ChatColor.GRAY + " mit normalen Mitteln von Minecraft.")
            .addLoreLine(" ").addLoreLine(ChatColor.GREEN + "" + ChatColor.ITALIC + "Klick: " + ChatColor.GREEN + "Übersicht").toItemStack();
    static ItemStack settings = new ItemBuilder(Material.REPEATER).setName(ChatColor.LIGHT_PURPLE + "Plugin-Einstellungen").addLoreLine(" ")
            .addLoreLine(ChatColor.LIGHT_PURPLE + " Plugin-Einstellungen" + ChatColor.GRAY + " passen")
            .addLoreLine(ChatColor.LIGHT_PURPLE + " Funktionalitäten" + ChatColor.GRAY + " des Plugins an,")
            .addLoreLine(ChatColor.GRAY + " für eine optimale" + ChatColor.LIGHT_PURPLE + " Spielererfahrung" + ChatColor.GRAY + ".")
            .addLoreLine(" ").addLoreLine(ChatColor.LIGHT_PURPLE + "" + ChatColor.ITALIC + "Klick: " + ChatColor.LIGHT_PURPLE + "Übersicht").toItemStack();

    /*
     * Creating items for the gamerules inventory
     */
    static ItemStack difficulty = new ItemBuilder(Material.SHIELD).setName(ChatColor.GREEN + "Schwierigkeitsgrad").addLoreLine(" ")
            .addLoreLine(ChatColor.GRAY + " Stellt die " + ChatColor.GREEN + "Schwierigkeitsstufe " + ChatColor.GRAY + "ein").addLoreLine(" ")
            .addLoreLine(ChatColor.GREEN + " Erhöhen:").addLoreLine(ChatColor.DARK_GRAY + "- " + ChatColor.BLUE + "Linksklick")
            .addLoreLine(ChatColor.GREEN + " Verringern:").addLoreLine(ChatColor.DARK_GRAY + "- " + ChatColor.BLUE + "Rechtsklick").toItemStack();
    static ItemStack regeneration = new ItemBuilder(Material.POTION, 1).setName(ChatColor.RED + "Regeneration").addPotionEffect(PotionEffectType.REGENERATION, 1, 1, true)
            .addLoreLine(" ").addLoreLine(ChatColor.GRAY + " Stellt die " + ChatColor.RED + "Regeneration " + ChatColor.GRAY + "ein")
            .addLoreLine(" ").addLoreLine(ChatColor.RED + " Erhöhen:").addLoreLine(ChatColor.DARK_GRAY + "- " + ChatColor.BLUE + "Linksklick")
            .addLoreLine(ChatColor.RED + " Verringern:").addLoreLine(ChatColor.DARK_GRAY + "- " + ChatColor.BLUE + "Rechtsklick").toItemStack();

    /*
     * Creating items for the timer inventories
     */
    static ItemStack toggle = new ItemBuilder(Material.TOTEM_OF_UNDYING).setName(ChatColor.DARK_PURPLE + "Timer toggle").addLoreLine(" ")
            .addLoreLine(ChatColor.DARK_PURPLE + " Startet" + ChatColor.GRAY + " / " + ChatColor.DARK_PURPLE + "Stoppt")
            .addLoreLine(ChatColor.GRAY + " den " + ChatColor.DARK_PURPLE + "Timer").toItemStack();
    static ItemStack resume = new ItemBuilder(Material.ENDER_EYE).setName(ChatColor.DARK_GREEN + "Timer fortsetzen").toItemStack();
    static ItemStack reset = new ItemBuilder(Material.BARRIER).setName(ChatColor.RED + "Timer zurücksetzen").toItemStack();
    static ItemStack pause = new ItemBuilder(Material.CHIPPED_ANVIL).setName(ChatColor.GOLD + "Timer pausieren").toItemStack();
    static ItemStack modifytime = new ItemBuilder(Material.ACACIA_BUTTON).setName(ChatColor.GREEN + "+1").addLoreLine(" ")
            .addLoreLine(ChatColor.GREEN + " Shift-Linksklick" + ChatColor.GRAY + " für " + ChatColor.GREEN + "+10").toItemStack();

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        /*
         * Prevent player's from clicking on the items inside the custom invs.
         */
        if (event.getView().getTitle().equalsIgnoreCase(ChatColor.GOLD + "Settings" + ChatColor.DARK_GRAY + " • " +
                ChatColor.BLUE + "Übersicht") || event.getView().getTitle().equalsIgnoreCase(ChatColor.GREEN + "Spielregeln" + ChatColor.DARK_GRAY + " • " +
                ChatColor.BLUE + "Seite 1") || event.getView().getTitle().equalsIgnoreCase(ChatColor.YELLOW + "Timer" + ChatColor.DARK_GRAY + " • " +
                ChatColor.BLUE + "Seite 1") || event.getView().getTitle().equalsIgnoreCase(ChatColor.YELLOW + "Timer" + ChatColor.DARK_GRAY + " • " +
                ChatColor.BLUE + "Seite 2")) {
            if (event.getCurrentItem() == null) {
                return;
            }
            if (event.getCurrentItem().getType().equals(Material.DARK_OAK_DOOR)) {
                player.openInventory(settingsinv);
            }
            event.setCancelled(true);
        }
        /*
         * Opening other custom invs by clicking on items inside them
         */
        if (event.getView().getTitle().equalsIgnoreCase(ChatColor.GOLD + "Settings" + ChatColor.DARK_GRAY + " • " +
                ChatColor.BLUE + "Übersicht")) {
            if (event.getCurrentItem().getType().equals(Material.MAP)) {
                if (event.isLeftClick()) {
                    /*
                     * Putting custom text and dyes into the inventories
                     */
                    if (Bukkit.getWorld("world").getDifficulty() == Difficulty.PEACEFUL) {
                        ItemStack difficultydye = new ItemBuilder(Material.WHITE_DYE, 1).setName("Friedlich").toItemStack();
                        gamerulesinv.setItem(3, difficultydye);
                    } else if (Bukkit.getWorld("world").getDifficulty() == Difficulty.EASY) {
                        ItemStack difficultydye = new ItemBuilder(Material.GREEN_DYE, 1).setName("Einfach").toItemStack();
                        gamerulesinv.setItem(3, difficultydye);
                    } else if (Bukkit.getWorld("world").getDifficulty() == Difficulty.NORMAL) {
                        ItemStack difficultydye = new ItemBuilder(Material.GREEN_DYE, 1).setName("Normal").toItemStack();
                        gamerulesinv.setItem(3, difficultydye);
                    } else if (Bukkit.getWorld("world").getDifficulty() == Difficulty.HARD) {
                        ItemStack difficultydye = new ItemBuilder(Material.GREEN_DYE, 1).setName("Schwer").toItemStack();
                        gamerulesinv.setItem(3, difficultydye);
                    }
                    if (getStatus() == 0) {
                        ItemStack regdye = new ItemBuilder(Material.GREEN_DYE, 1).setName("Normal").toItemStack();
                        gamerulesinv.setItem(12, regdye);
                    } else if (getStatus() == 1) {
                        ItemStack regdye = new ItemBuilder(Material.ORANGE_DYE, 1).setName("UHC").toItemStack();
                        gamerulesinv.setItem(12, regdye);
                    } else if (getStatus() == 2) {
                        ItemStack regdye = new ItemBuilder(Material.RED_DYE, 1).setName("UUHC").toItemStack();
                        gamerulesinv.setItem(12, regdye);
                    }
                    player.openInventory(gamerulesinv);
                }
            } else if (event.getCurrentItem().getType().equals(Material.CLOCK)) {
                if (event.isLeftClick()) {
                    player.openInventory(timerinv);
                }
            }
        }
        Timer timer = Main.getInstance().getTimer();
        if (event.getView().getTitle().equalsIgnoreCase(ChatColor.YELLOW + "Timer" + ChatColor.DARK_GRAY + " • " +
                ChatColor.BLUE + "Seite 1")) {
            if (event.getCurrentItem().getType().equals(Material.TOTEM_OF_UNDYING)) {
                if (event.isLeftClick()) {
                    Bukkit.dispatchCommand(player, "timer toggle");
                }
            } else if (event.getCurrentItem().getType().equals(Material.ENDER_EYE)) {
                if (event.isLeftClick()) {
                    Bukkit.dispatchCommand(player, "timer resume");
                }
            } else if (event.getCurrentItem().getType().equals(Material.BARRIER)) {
                if (event.isLeftClick()) {
                    Bukkit.dispatchCommand(player, "timer reset");
                }
            } else if (event.getCurrentItem().getType().equals(Material.CHIPPED_ANVIL)) {
                if (event.isLeftClick()) {
                    Bukkit.dispatchCommand(player, "timer pause");
                }
            } else if (event.getCurrentItem().getType().equals(Material.IRON_DOOR)) {
                player.openInventory(timerinv2);
            }
        }
        if (event.getView().getTitle().equalsIgnoreCase(ChatColor.YELLOW + "Timer" + ChatColor.DARK_GRAY + " • " +
                ChatColor.BLUE + "Seite 2")) {
            if (event.getSlot() == 1) {
                if (event.isLeftClick()) {
                    timer.setTime(Timer.getTime() + 86400 * 5);         //TODO: BessEr machen, sobald man d anhängen kann zu /timer set (Mit bukkit.dispatchCommand)
                    if (event.isShiftClick()) {
                        timer.setTime(Timer.getTime() + (86400 * 5) * 9);
                    }
                }
            } else if (event.getSlot() == 3) {
                if (event.isLeftClick()) {
                    timer.setTime(Timer.getTime() + 3600 * 5);
                    if(event.isShiftClick()) {
                        timer.setTime(Timer.getTime() + (3600 * 5) * 9);
                    }
                }
            } else if (event.getSlot() == 5) {
                if (event.isLeftClick()) {
                    timer.setTime(Timer.getTime() + 60 * 5);
                    if(event.isShiftClick()) {
                        timer.setTime(Timer.getTime() + (60 * 5) * 9);
                    }
                }
            } else if (event.getSlot() == 7) {
                if (event.isLeftClick()) {
                    timer.setTime(Timer.getTime() + 5);
                    if(event.isShiftClick()) {
                        timer.setTime(Timer.getTime() + 5 * 9);
                    }
                }
            }
        }
    }
}
