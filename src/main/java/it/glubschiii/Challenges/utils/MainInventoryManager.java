package it.glubschiii.Challenges.utils;

import it.glubschiii.Challenges.Main;
import it.glubschiii.Challenges.timer.Timer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Difficulty;
import org.bukkit.Material;
import org.bukkit.command.defaults.BukkitCommand;
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
    //TODO: Namen und Farben ändern..
    public static Inventory settingsInv = Bukkit.createInventory(null, 45, ChatColor.GOLD + "Settings" + ChatColor.DARK_GRAY + " • " +
            ChatColor.BLUE + "Übersicht");
    public static Inventory gamerulesInv = Bukkit.createInventory(null, 54, ChatColor.GREEN + "Spielregeln" + ChatColor.DARK_GRAY + " • " +
            ChatColor.BLUE + "Seite 1");
    public static Inventory timerInv = Bukkit.createInventory(null, 36, ChatColor.YELLOW + "Timer" + ChatColor.DARK_GRAY + " • " +
            ChatColor.BLUE + "Seite 1");
    public static Inventory timerInv2 = Bukkit.createInventory(null, 36, ChatColor.YELLOW + "Timer" + ChatColor.DARK_GRAY + " • " +
            ChatColor.BLUE + "Seite 2");
    public static Inventory timerInv3 = Bukkit.createInventory(null, 36, ChatColor.YELLOW + "Timer" + ChatColor.DARK_GRAY + " • " +
            ChatColor.BLUE + "Seite 3");
    public static Inventory goalsInv = Bukkit.createInventory(null, 54, ChatColor.RED + "Herausforderungen" + ChatColor.DARK_GRAY + ChatColor.DARK_GRAY + " • " +
            ChatColor.BLUE + "Seite 1");
    /*
     * Putting items inside the custom /settings inventories and it's repositories
     */
    static ItemStack background = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setName(" ").toItemStack();
    static ItemStack background2 = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName(" ").toItemStack();
    static ItemStack back = new ItemBuilder(Material.DARK_OAK_DOOR).setName(ChatColor.AQUA + "Zurück").toItemStack();
    static ItemStack mainMenu = new ItemBuilder(Material.COMPARATOR).setName(ChatColor.RED + "Zurück zum Hauptmenü").toItemStack();

    private static void buildNextPage(byte page, Inventory invtype) {
        ItemStack next = new ItemBuilder(Material.IRON_DOOR).setName(ChatColor.GRAY + "Seite " + page).toItemStack();
        invtype.setItem(35, next);
    }

    private static void timeDays(short amount) {
        ItemStack days = new ItemBuilder(Material.CLOCK, amount).setName(ChatColor.GRAY + "Tage").toItemStack();
        timerInv2.setItem(10, days);
    }

    private static void timeHours(short amount) {
        ItemStack hours = new ItemBuilder(Material.CLOCK, amount).setName(ChatColor.GRAY + "Stunden").toItemStack();
        timerInv2.setItem(12, hours);
    }

    private static void timeMinutes(short amount) {
        ItemStack minutes = new ItemBuilder(Material.CLOCK, amount).setName(ChatColor.GRAY + "Minuten").toItemStack();
        timerInv2.setItem(14, minutes);
    }

    private static void timeSeconds(short amount) {
        ItemStack seconds = new ItemBuilder(Material.CLOCK, amount).setName(ChatColor.GRAY + "Sekunden").toItemStack();
        timerInv2.setItem(16, seconds);
    }

    private static void timerDirectionChange(ChatColor direction, PotionEffectType potionEffectType, char arrow) {
        ItemStack timerDirection = new ItemBuilder(Material.TIPPED_ARROW).setName(direction + "Timerrichtung").addArrowEffect(potionEffectType, 1, 1, true)
                .addLoreLine(" ").addLoreLine(ChatColor.GRAY + " Verändert die" + direction + " Timerrichtung" + ChatColor.GRAY + ".")
                .addLoreLine(" ").addLoreLine(direction + " Nach oben: " + ChatColor.BLUE + "Linksklick").addLoreLine(direction + " Nach unten: " + ChatColor.BLUE + "Rechtsklick")
                .addLoreLine(" ").addLoreLine(ChatColor.GRAY + "Aktuell: " + ChatColor.BOLD + direction.toString() + arrow).toItemStack();
        timerInv3.setItem(13, timerDirection);
    }

    public static void timeCalculator() {
        /*
         * Calculating all the days in the timer
         */
        if (Timer.getTime() / 5 / 86400 >= 1) {
            timeDays((short) (Timer.getTime() / 5 / 86400));
        } else {
            timeDays((short) 1);
        }
        /*
         * Calculating all the remaining hours in the timer
         */
        short totalHours = (short) (Timer.getTime() / 5 / 3600);
        short fullDays = (short) (totalHours / 24);
        short remainingHours = (short) (totalHours - (fullDays * 24));
        if (remainingHours >= 1) {
            timeHours(remainingHours);
        } else {
            timeHours((short) 1);
        }
        /*
         * Calculating all the remaining minutes in the timer
         */
        short totalMinutes = (short) (Timer.getTime() / 5 / 60);
        short fullHours = (short) (totalMinutes / 60);
        short remainingMinutes = (short) (totalMinutes - (fullHours * 60));
        if (remainingMinutes >= 1) {
            timeMinutes(remainingMinutes);
        } else {
            timeMinutes((short) 1);
        }
        /*
         * Calculating all the remaining seconds in the timer
         */
        short totalSeconds = (short) (Timer.getTime() / 5 % 60);
        if (totalSeconds >= 1) {
            timeSeconds(totalSeconds);
        } else {
            timeSeconds((short) 1);
        }
    }

    public static void puttingItems() throws IOException {
        int[] background2SlotsMainInv = {0, 1, 7, 8, 9, 17, 18, 26, 27, 35, 36, 37, 43, 44};
        int[] backgroundSlotsgamerulesInv = {0, 4, 8, 9, 13, 17, 18, 22, 26, 27, 31, 35, 36, 40, 44, 49, 53};
        int[] background2SlotsgamerulesInv = {1, 7, 10, 16, 19, 25, 28, 34, 37, 43, 46, 52};
        int[] backgroundSlotstimerInv = {0, 2, 6, 8, 9, 11, 13, 15, 17, 18, 26, 29, 31, 33};
        int[] background2SlotstimerInv = {1, 3, 5, 7, 10, 12, 14, 16, 19, 21, 23, 25, 28, 30, 32, 34};
        int[] backgroundSlotstimerInv2 = {0, 2, 4, 6, 8, 9, 11, 13, 15, 17, 18, 20, 22, 24, 26, 29, 31, 33};
        int[] background2SlotstimerInv2 = {28, 30, 32, 34};
        int[] modifyTimeSlotstimerInv2 = {1, 3, 5, 7, 19, 21, 23, 25};  //TODO: Bei den unteren Knäpfen steht auch noch +10 obwohl da -10 sein sollte und Text ändern!
        int[] backgroundSlotstimerInv3 = {0, 2, 4, 6, 8, 9, 11, 15, 17, 18, 20, 22, 24, 26, 29, 33, 35};
        int[] background2SlotstimerInv3 = {1, 3, 5, 7, 10, 12, 14, 16, 19, 21, 23, 25, 28, 30, 32, 34};
        int[] backgroundSlotsGoalsInv = {0, 4, 8, 9, 13, 17, 18, 22, 26, 27, 31, 35, 36, 40, 44, 49, 53};
        int[] background2SlotsGoalsInv = {1, 5, 6, 7, 10, 14, 15, 16, 19, 20, 21, 23, 24, 25, 28, 29, 30, 32, 33, 34, 37, 38, 39, 41, 42, 43, 46, 47, 48, 50, 51, 52};

        puttingItemsHelper(background2SlotsMainInv, settingsInv, background2);
        puttingItemsHelper(backgroundSlotsgamerulesInv, gamerulesInv, background);
        puttingItemsHelper(background2SlotsgamerulesInv, gamerulesInv, background2);
        puttingItemsHelper(backgroundSlotstimerInv, timerInv, background);
        puttingItemsHelper(background2SlotstimerInv, timerInv, background2);
        puttingItemsHelper(backgroundSlotstimerInv2, timerInv2, background);
        puttingItemsHelper(background2SlotstimerInv2, timerInv2, background2);
        puttingItemsHelper(modifyTimeSlotstimerInv2, timerInv2, modifytime);
        puttingItemsHelper(backgroundSlotstimerInv3, timerInv3, background);
        puttingItemsHelper(background2SlotstimerInv3, timerInv3, background2);
        puttingItemsHelper(backgroundSlotsGoalsInv, goalsInv, background);
        puttingItemsHelper(background2SlotsGoalsInv, goalsInv, background2);
        for (int i = 2; i <= 44; i++) {
            if (i <= 3 || i == 5 || i == 6 || (i >= 10 && i <= 16) || i == 19 || i == 21 || i == 23 || i == 25 || (i >= 28 && i <= 34) || (i >= 38 && i <= 39) || i == 41 || i == 42) {
                settingsInv.setItem(i, background);
            }
        }
        settingsInv.setItem(4, timer);
        settingsInv.setItem(20, herausforderungen);
        settingsInv.setItem(22, challenges);
        settingsInv.setItem(24, gamerules);
        settingsInv.setItem(40, settings);
        gamerulesInv.setItem(2, difficulty);
        gamerulesInv.setItem(11, regeneration);
        gamerulesInv.setItem(45, back);
        timerInv.setItem(4, toggle);
        timerInv.setItem(20, resume);
        timerInv.setItem(22, reset);
        timerInv.setItem(24, pause);
        timerInv.setItem(27, back);
        timerInv2.setItem(27, back);
        timerInv3.setItem(27, back);
        timerInv3.setItem(31, mainMenu);
        goalsInv.setItem(2, dragonGoal);
        goalsInv.setItem(11, elderGuardianGoal);
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
            .addLoreLine(ChatColor.GREEN + " Shift-Rechtsklick" + ChatColor.GRAY + " für " + ChatColor.GREEN + "+10").toItemStack();
    /*
    * Creating items for the goals inventory
     */
    static ItemStack dragonGoal = new ItemBuilder(Material.DRAGON_EGG).setName(ChatColor.DARK_PURPLE + "Enderdrache").addLoreLine(" ")
            .addLoreLine(ChatColor.GRAY + " Die Challenge ist " + ChatColor.DARK_PURPLE + "absolviert" + ChatColor.GRAY + ",")
            .addLoreLine(" sobald der " + ChatColor.DARK_PURPLE + "Enderdrache" + ChatColor.GRAY + "getötet wurde.").toItemStack();
    static ItemStack elderGuardianGoal = new ItemBuilder(Material.TRIDENT).setName(ChatColor.DARK_BLUE + "Großer Wächter").addLoreLine(" ")
            .addLoreLine(ChatColor.GRAY + " Die Challenge ist " + ChatColor.DARK_BLUE + "absolviert" + ChatColor.GRAY + ",")
            .addLoreLine(" sobald der " + ChatColor.DARK_BLUE + "Große Wächter" + ChatColor.GRAY + "getötet wurde.").toItemStack();


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
                ChatColor.BLUE + "Seite 2") || event.getView().getTitle().equalsIgnoreCase(ChatColor.YELLOW + "Timer" + ChatColor.DARK_GRAY + " • " +
                ChatColor.BLUE + "Seite 3") || event.getView().getTitle().equalsIgnoreCase(ChatColor.RED + "Herausforderungen" + ChatColor.DARK_GRAY + " • " +
                ChatColor.BLUE + "Seite 1")) {
            if (event.getCurrentItem() == null) {
                return;
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
                        gamerulesInv.setItem(3, difficultydye);
                    } else if (Bukkit.getWorld("world").getDifficulty() == Difficulty.EASY) {
                        ItemStack difficultydye = new ItemBuilder(Material.GREEN_DYE, 1).setName("Einfach").toItemStack();
                        gamerulesInv.setItem(3, difficultydye);
                    } else if (Bukkit.getWorld("world").getDifficulty() == Difficulty.NORMAL) {
                        ItemStack difficultydye = new ItemBuilder(Material.ORANGE_DYE, 1).setName("Normal").toItemStack();
                        gamerulesInv.setItem(3, difficultydye);
                    } else if (Bukkit.getWorld("world").getDifficulty() == Difficulty.HARD) {
                        ItemStack difficultydye = new ItemBuilder(Material.RED_DYE, 1).setName("Schwer").toItemStack();
                        gamerulesInv.setItem(3, difficultydye);
                    }
                    if (getStatus() == 0) {
                        ItemStack regdye = new ItemBuilder(Material.GREEN_DYE, 1).setName("Normal").toItemStack();
                        gamerulesInv.setItem(12, regdye);
                    } else if (getStatus() == 1) {
                        ItemStack regdye = new ItemBuilder(Material.ORANGE_DYE, 1).setName("UHC").toItemStack();
                        gamerulesInv.setItem(12, regdye);
                    } else if (getStatus() == 2) {
                        ItemStack regdye = new ItemBuilder(Material.RED_DYE, 1).setName("UUHC").toItemStack();
                        gamerulesInv.setItem(12, regdye);
                    }
                    player.openInventory(gamerulesInv);
                }
            } else if (event.getCurrentItem().getType().equals(Material.CLOCK)) {
                if (event.isLeftClick()) {
                    player.openInventory(timerInv);
                    buildNextPage((byte) 2, timerInv);
                }
            } else if(event.getCurrentItem().getType().equals(Material.DRAGON_HEAD)) {
                if(event.isLeftClick()) {
                    player.openInventory(goalsInv);
                }
            }
        } else if (event.getView().getTitle().equalsIgnoreCase(ChatColor.GREEN + "Spielregeln" + ChatColor.DARK_GRAY + " • " +
                ChatColor.BLUE + "Seite 1")) {
            if (event.getCurrentItem().equals(Material.DARK_OAK_DOOR)) {
                if(event.isLeftClick()) {
                    player.openInventory(settingsInv);
                }
            }
        } else if (event.getView().getTitle().equalsIgnoreCase(ChatColor.YELLOW + "Timer" + ChatColor.DARK_GRAY + " • " +
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
                player.openInventory(timerInv2);
                buildNextPage((byte) 3, timerInv2);
                timeCalculator();
            } else if (event.getCurrentItem().getType().equals(Material.DARK_OAK_DOOR)) {
                player.openInventory(settingsInv);
            }
        } else if (event.getView().getTitle().equalsIgnoreCase(ChatColor.YELLOW + "Timer" + ChatColor.DARK_GRAY + " • " +
                ChatColor.BLUE + "Seite 2")) {
            Timer timer = Main.getInstance().getTimer();
            if (event.getSlot() == 1) {
                if (event.isLeftClick()) {
                    timer.setTime(Timer.getTime() + 86400 * 5);
                    timeCalculator();
                } else if (event.isShiftClick()) {
                    timer.setTime(Timer.getTime() + (86400 * 5) * 10);
                    timeCalculator();
                }
            } else if (event.getSlot() == 3) {
                if (event.isLeftClick()) {
                    timer.setTime(Timer.getTime() + 3600 * 5);
                    timeCalculator();
                } else if (event.isShiftClick()) {
                    timer.setTime(Timer.getTime() + (3600 * 5) * 9);
                    timeCalculator();
                }
            } else if (event.getSlot() == 5) {
                if (event.isLeftClick()) {
                    timer.setTime(Timer.getTime() + 60 * 5);
                    timeCalculator();
                } else if (event.isShiftClick()) {
                    timer.setTime(Timer.getTime() + (60 * 5) * 9);
                    timeCalculator();
                }
            } else if (event.getSlot() == 7) {
                if (event.isLeftClick()) {
                    timer.setTime(Timer.getTime() + 5);
                    timeCalculator();
                } else if (event.isShiftClick()) {
                    timer.setTime(Timer.getTime() + 5 * 9);
                    timeCalculator();
                }
            } else if (event.getCurrentItem().getType().equals(Material.IRON_DOOR)) {
                if (event.isLeftClick()) {
                    player.openInventory(timerInv3);
                    if (((String) Config.get("timer-direction")).contains("down")) {
                        //TODO: Apply PotionEffect to Spectral (or tipped) arrow correctly here and in the ItemBuilder
                        timerDirectionChange(ChatColor.RED, PotionEffectType.HEAL, '↡');
                    } else {
                        timerDirectionChange(ChatColor.GREEN, PotionEffectType.BLINDNESS, '↟');
                    }
                }
            } else if (event.getCurrentItem().getType().equals(Material.DARK_OAK_DOOR)) {
                player.openInventory(timerInv);
            }
        } else if (event.getView().getTitle().equalsIgnoreCase(ChatColor.YELLOW + "Timer" + ChatColor.DARK_GRAY + " • " +
                ChatColor.BLUE + "Seite 3")) {
            if (event.getSlot() == 13) {
                if (event.isLeftClick()) {
                    timerDirectionChange(ChatColor.GREEN, PotionEffectType.BLINDNESS, '↟');
                    Bukkit.dispatchCommand(player, "timer up");
                } else if (event.isRightClick()) {
                    timerDirectionChange(ChatColor.RED, PotionEffectType.HEAL, '↡');
                    Bukkit.dispatchCommand(player, "timer down");
                }
            } else if (event.getCurrentItem().getType().equals(Material.DARK_OAK_DOOR)) {
                player.openInventory(timerInv2);
            }
        }
    }
}
