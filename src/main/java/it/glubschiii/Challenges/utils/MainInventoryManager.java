package it.glubschiii.Challenges.utils;

import it.glubschiii.Challenges.Main;
import it.glubschiii.Challenges.timer.Timer;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;

import java.io.IOException;
import java.util.Objects;

import static it.glubschiii.Challenges.challenges.WolfiChallenge.*;
import static it.glubschiii.Challenges.gamerules.DifficultyGamerule.prefix;
import static it.glubschiii.Challenges.gamerules.RegenerationGamerule.getStatus;
import static it.glubschiii.Challenges.goals.AllItemsGoal.*;

/**
 * @author Glubschiii | https://github.com/glubschiii
 * @since 1.0.5
 */

public class MainInventoryManager implements Listener {

    //TODO: /cosmetics command should open the cosmetics inv

    /*
     * Creating custom /settings inventories
     */
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
    public static Inventory goalsInv = Bukkit.createInventory(null, 54, ChatColor.RED + "Herausforderungen" + ChatColor.DARK_GRAY + " • " +
            ChatColor.BLUE + "Seite 1");
    public static Inventory challengesInv = Bukkit.createInventory(null, 36, ChatColor.AQUA + "Challenges");
    public static Inventory minecraftChallengesInv = Bukkit.createInventory(null, 54, ChatColor.GREEN + "Minecraft-Challenges" + ChatColor.DARK_GRAY + " • " +
            ChatColor.BLUE + "Seite 1");
    public static Inventory pluginSettingsInv = Bukkit.createInventory(null, 36, ChatColor.LIGHT_PURPLE + "Plugin-Einstellungen" + ChatColor.DARK_GRAY + " • " +
            ChatColor.BLUE + "Seite 1");

    /*
     * Creating custom "/allitems overview" inventories
     */
    public static Inventory allItemsOverviewInv = Bukkit.createInventory(null, 54, ChatColor.DARK_GRAY + "Alle Items Overview");

    /*
     * Creating custom "Wolfi Challenge" inventories
     */
    public static Inventory wolfiChallengeInv = Bukkit.createInventory(null, 9, ChatColor.GREEN + "Wolfi"
            + ChatColor.DARK_GRAY + " • " + ChatColor.BLUE + "Anpassungsmenü");
    public static Inventory wolfiNameChangeInv = Bukkit.createInventory(null, InventoryType.ANVIL, ChatColor.DARK_GRAY + "Namen ändern");
    public static Inventory wolfiCollarColorChangeInv = Bukkit.createInventory(null, 18, ChatColor.DARK_GRAY + "Halsbandfarbe ändern");

    /*
     * Creating custom cosmetics inventories
     */
    public static Inventory playerSettingsInv = Bukkit.createInventory(null, 27, ChatColor.GRAY + "〣 " + ChatColor.LIGHT_PURPLE + "Cosmetics");
    public static Inventory cosmeticsCategoriesInv = Bukkit.createInventory(null, 27, ChatColor.DARK_GRAY + "Kategorien");
    public static Inventory timerColorsInv = Bukkit.createInventory(null, 27, ChatColor.DARK_GRAY + "Timer-Farben");
    /*
     * Creating items for the "/allitems overview" inventory
     */
    public static ItemStack allItemsDefault = new ItemBuilder(Material.OAK_SIGN).setName(ChatColor.RED + "Dieses Item hat keine Vorschau").toItemStack();
    /*
     * Putting items inside the custom /settings inventories, /allitems overview, wolfi inventories and it's repositories
     */
    static ItemStack background = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setName(" ").toItemStack();
    static ItemStack background2 = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName(" ").toItemStack();
    static ItemStack background3 = new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).setName(" ").toItemStack();
    static ItemStack backgroundPurple = new ItemBuilder(Material.PURPLE_STAINED_GLASS_PANE).setName(" ").toItemStack();
    static ItemStack backgroundGreen = new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).setName(" ").toItemStack();
    static ItemStack backgroundRed = new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setName(" ").toItemStack();
    static ItemStack back = new ItemBuilder(Material.DARK_OAK_DOOR).setName(ChatColor.AQUA + "Zurück").toItemStack();
    static ItemStack backArrow = new ItemBuilder(Material.ARROW).setName(ChatColor.AQUA + "Zurück").toItemStack();
    static ItemStack backGlassPane = new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setName(ChatColor.AQUA + "Zurück").toItemStack();
    static ItemStack mainMenu = new ItemBuilder(Material.COMPARATOR).setName(ChatColor.RED + "Zurück zum Hauptmenü").toItemStack();
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
            .addLoreLine(ChatColor.GRAY + " sobald der " + ChatColor.DARK_PURPLE + "Enderdrache " + ChatColor.GRAY + "getötet wurde.").toItemStack();
    static ItemStack elderGuardianGoal = new ItemBuilder(Material.TRIDENT).setName(ChatColor.BLUE + "Großer Wächter").addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
            .addLoreLine(" ")      //TODO: ÜBERPRÜFEN: In der Haupthand... ItemFlag entfernen
            .addLoreLine(ChatColor.GRAY + " Die Challenge ist " + ChatColor.BLUE + "absolviert" + ChatColor.GRAY + ",")
            .addLoreLine(ChatColor.GRAY + " sobald der " + ChatColor.BLUE + "Große Wächter " + ChatColor.GRAY + "getötet wurde.").toItemStack();
    static ItemStack witherGoal = new ItemBuilder(Material.WITHER_SKELETON_SKULL).setName(ChatColor.DARK_GRAY + "Wither").addLoreLine(" ")
            .addLoreLine(ChatColor.GRAY + " Die Challenge ist " + ChatColor.DARK_GRAY + "absolviert" + ChatColor.GRAY + ",")
            .addLoreLine(ChatColor.GRAY + " sobald der " + ChatColor.DARK_GRAY + "Wither " + ChatColor.GRAY + "getötet wurde.").toItemStack();
    static ItemStack wardenGoal = new ItemBuilder(Material.SCULK_SENSOR).setName(ChatColor.AQUA + "Wärter").addLoreLine(" ")
            .addLoreLine(ChatColor.GRAY + " Die Challenge ist " + ChatColor.AQUA + "absolviert" + ChatColor.GRAY + ",")
            .addLoreLine(ChatColor.GRAY + " sobald der " + ChatColor.AQUA + "Wärter " + ChatColor.GRAY + "getötet wurde.").toItemStack();
    static ItemStack allItemsGoal = new ItemBuilder(Material.GRASS_BLOCK).setName(ChatColor.GREEN + "Alle Items").addLoreLine(" ")
            .addLoreLine(ChatColor.GRAY + " Die Challenge ist " + ChatColor.GREEN + "absolviert" + ChatColor.GRAY + ",")
            .addLoreLine(ChatColor.GRAY + " sobald " + ChatColor.GREEN + "alle Items " + ChatColor.GRAY + "gesammelt wurden.").toItemStack();
    /*
     * Creating items for the pluginSettings inventory
     */
    static ItemStack backpack = new ItemBuilder(Material.ENDER_CHEST).setName(ChatColor.DARK_PURPLE + "Backpack").addLoreLine(" ")
            .addLoreLine(ChatColor.GRAY + " Diese " + ChatColor.DARK_PURPLE + "Plugin-Einstellung" + ChatColor.GRAY + "" +
                    " stellt").addLoreLine(ChatColor.GRAY + " das" + ChatColor.DARK_PURPLE + " Backpack " + ChatColor.GRAY + "ein.").toItemStack();
    static ItemStack damageInChat = new ItemBuilder(Material.SPAWNER).setName(ChatColor.RED + "Schaden im Chat").addItemFlag(ItemFlag.HIDE_ITEM_SPECIFICS).addLoreLine(" ")
            .addLoreLine(ChatColor.GRAY + " Mit dieser " + ChatColor.RED + "Plugin-Einstellung" + ChatColor.GRAY + " wird der").addLoreLine(ChatColor.RED + " erlittene Schaden" +
                    ChatColor.GRAY + " der Spieler im " + ChatColor.RED + "Chat" + ChatColor.GRAY + " angezeigt.").toItemStack();
    static ItemStack registeredItems = new ItemBuilder(Material.CHEST).setName(ChatColor.GREEN + "Registrierte Items").toItemStack();
    static ItemStack pendingItems = new ItemBuilder(Material.CAULDRON).setName(ChatColor.RED + "Ausstehende Items").toItemStack();
    /*
     * Creating items for the challenges inventory
     */
    static ItemStack minecraftChallenges = new ItemBuilder(Material.CRAFTING_TABLE).setName(ChatColor.GREEN + "Minecraft-Challenges").toItemStack();    //TODO: Beschreibung
    static ItemStack randomizerChallenges = new ItemBuilder(Material.CHAIN_COMMAND_BLOCK).setName(ChatColor.BLUE + "Randomizer-Challenges").addLoreLine(" ")
            .addLoreLine(ChatColor.RED + " Coming Soon...").toItemStack();     //TODO: Beschreibung
    /*
     * Creating items for the minecraftChallenges inventory
     */
    static ItemStack noCraftingTableChallenge = new ItemBuilder(Material.CRAFTING_TABLE).setName(ChatColor.GOLD + "Ohne Werkbank").addLoreLine(" ")
            .addLoreLine(ChatColor.GRAY + " Die " + ChatColor.GOLD + "Einschränkung" + ChatColor.GRAY + " dieser Challenge besteht darin,")
            .addLoreLine(ChatColor.GRAY + " das Spielgeschehen " + ChatColor.GOLD + "ohne Werkbank" + ChatColor.GRAY + " zu bewältigen.").toItemStack();
    static ItemStack noFallDamageChallenge = new ItemBuilder(Material.FEATHER).setName(ChatColor.WHITE + "Ohne Fallschaden").addLoreLine(" ")
            .addLoreLine(ChatColor.GRAY + " Die " + ChatColor.WHITE + "Einschränkung" + ChatColor.GRAY + " dieser Challenge besteht darin,")
            .addLoreLine(ChatColor.GRAY + " das Spielgeschehen " + ChatColor.WHITE + "ohne Fallschaden" + ChatColor.GRAY + " zu bewältigen.").toItemStack();
    static ItemStack noArmorChallenge = new ItemBuilder(Material.LEATHER_CHESTPLATE).addItemFlag(ItemFlag.HIDE_ATTRIBUTES).addItemFlag(ItemFlag.HIDE_DYE).setColor(Color.YELLOW)
            .setName(ChatColor.YELLOW + "Ohne Rüstung").addLoreLine(" ").addLoreLine(ChatColor.GRAY + " Die " + ChatColor.YELLOW + "Einschränkung" + ChatColor.GRAY + " " +
                    "dieser Challenge besteht darin,").addLoreLine(ChatColor.GRAY + " das Spielgeschehen " + ChatColor.YELLOW + "ohne Rüstung" + ChatColor.GRAY +
                    " zu bewältigen.").toItemStack();
    static ItemStack limitedHealthChallenge = new ItemBuilder(Material.SPECTRAL_ARROW).setName(ChatColor.DARK_RED + "Limitierte Herzen").addLoreLine(" ")
            .addLoreLine(ChatColor.GRAY + " Die " + ChatColor.DARK_RED + "Einschränkung" + ChatColor.GRAY + " dieser Challenge besteht darin,")
            .addLoreLine(ChatColor.GRAY + " das Spielgeschehen " + ChatColor.DARK_RED + "mit limitierten Herzen" + ChatColor.GRAY + " zu bewältigen.").toItemStack();
    static ItemStack noJumpChallenge = new ItemBuilder(Material.FIREWORK_ROCKET).setName(ChatColor.RED + "Ohne Springen").addLoreLine(" ")
            .addLoreLine(ChatColor.GRAY + " Die " + ChatColor.RED + "Einschränkung" + ChatColor.GRAY + " dieser Challenge besteht darin,")
            .addLoreLine(ChatColor.GRAY + " das Spielgeschehen " + ChatColor.RED + "ohne Springen" + ChatColor.GRAY + " zu bewältigen.").toItemStack();
    static ItemStack noSneakChallenge = new ItemBuilder(Material.DIAMOND_BOOTS).addItemFlag(ItemFlag.HIDE_ATTRIBUTES).setName(ChatColor.BLUE + "Ohne Schleichen").addLoreLine(" ")
            .addLoreLine(ChatColor.GRAY + " Die " + ChatColor.BLUE + "Einschränkung" + ChatColor.GRAY + " dieser Challenge besteht darin,")
            .addLoreLine(ChatColor.GRAY + " das Spielgeschehen " + ChatColor.BLUE + "ohne Schleichen" + ChatColor.GRAY + " zu bewältigen.").toItemStack();
    static ItemStack wolfiChallenge = new ItemBuilder(Material.BONE).setName(ChatColor.DARK_GRAY + "Wolfi").addLoreLine(" ")
            .addLoreLine(ChatColor.GRAY + " Die " + ChatColor.DARK_GRAY + "Einschränkung" + ChatColor.GRAY + " dieser Challenge besteht darin,")
            .addLoreLine(ChatColor.GRAY + " das Spielgeschehen " + ChatColor.DARK_GRAY + "mit Wolfi" + ChatColor.GRAY + " zu bewältigen.").toItemStack();
    static ItemStack dividedHearts = new ItemBuilder(Material.HEART_OF_THE_SEA).setName(ChatColor.AQUA + "Geteilte Herzen").addLoreLine(" ")
            .addLoreLine(ChatColor.GRAY + " Die " + ChatColor.AQUA + "Einschränkung" + ChatColor.GRAY + " dieser Challenge besteht darin,")
            .addLoreLine(ChatColor.GRAY + " das Spielgeschehen " + ChatColor.AQUA + "mit geteilten Herzen" + ChatColor.GRAY + " zu bewältigen.").toItemStack();
    /*
     * Creating items for the Wolfi Challenge inventory
     */
    static ItemStack nameChange = new ItemBuilder(Material.NAME_TAG).setName(ChatColor.BLUE + "Namen ändern").toItemStack();
    static ItemStack collarColor = new ItemBuilder(Material.ARMOR_STAND).setName(ChatColor.DARK_GREEN + "Halsbandfarbe ändern").toItemStack();
    static ItemStack nameChangeInput = new ItemBuilder(Material.NAME_TAG).toItemStack();
    static ItemStack whiteCollarColor = new ItemBuilder(Material.WHITE_DYE).setName(ChatColor.WHITE + "Weiß").toItemStack();
    static ItemStack lightGrayCollarColor = new ItemBuilder(Material.LIGHT_GRAY_DYE).setName(ChatColor.GRAY + "Hellgrau").toItemStack();
    static ItemStack grayCollarColor = new ItemBuilder(Material.GRAY_DYE).setName(ChatColor.DARK_GRAY + "Grau").toItemStack();
    static ItemStack blackCollarColor = new ItemBuilder(Material.BLACK_DYE).setName(ChatColor.BLACK + "Schwarz").toItemStack();
    static ItemStack brownCollarColor = new ItemBuilder(Material.BROWN_DYE).setName(ChatColor.GOLD + "Braun").toItemStack();            //TODO: Hex Color code benutzen
    static ItemStack redCollarColor = new ItemBuilder(Material.RED_DYE).setName(ChatColor.RED + "Rot").toItemStack();
    static ItemStack orangeCollarColor = new ItemBuilder(Material.ORANGE_DYE).setName(ChatColor.GOLD + "Orange").toItemStack();
    static ItemStack yellowCollarColor = new ItemBuilder(Material.YELLOW_DYE).setName(ChatColor.YELLOW + "Gelb").toItemStack();
    static ItemStack limeCollarColor = new ItemBuilder(Material.LIME_DYE).setName(ChatColor.GREEN + "Hellgrün").toItemStack();
    static ItemStack greenCollarColor = new ItemBuilder(Material.GREEN_DYE).setName(ChatColor.DARK_GREEN + "Grün").toItemStack();
    static ItemStack cyanCollarColor = new ItemBuilder(Material.CYAN_DYE).setName(ChatColor.AQUA + "Türkis").toItemStack();
    static ItemStack lightBlueCollarColor = new ItemBuilder(Material.LIGHT_BLUE_DYE).setName(ChatColor.BLUE + "Hellblau").toItemStack();
    static ItemStack blueCollarColor = new ItemBuilder(Material.BLUE_DYE).setName(ChatColor.DARK_BLUE + "Blau").toItemStack();
    static ItemStack purpleCollarColor = new ItemBuilder(Material.PURPLE_DYE).setName(ChatColor.DARK_PURPLE + "Violett").toItemStack();
    static ItemStack magentaCollarColor = new ItemBuilder(Material.MAGENTA_DYE).setName(ChatColor.DARK_PURPLE + "Magenta").toItemStack();       //TODO: Hex Color code benutzen
    static ItemStack pinkCollarColor = new ItemBuilder(Material.PINK_DYE).setName(ChatColor.LIGHT_PURPLE + "Rosa").toItemStack();
    /*
     * Creating items for the playerSettings inventories
     */
    static ItemStack timerCosmetics = new ItemBuilder(Material.CLOCK).setName(ChatColor.GOLD + "Timer").toItemStack();
    static ItemStack timerColorCosmetics = new ItemBuilder(Material.CLOCK).setName(ChatColor.GOLD + "Farben").toItemStack();
    /*
     * Creating items for the Cosmetics inventory
     */
    //TODO: GRADIENT!!!
    static ItemStack blackTimerColor = new ItemBuilder(Material.BLACK_SHULKER_BOX).setName(ChatColor.BLACK.toString() + ChatColor.BOLD + "Schwarz").toItemStack();
    static ItemStack darkBlueTimerColor = new ItemBuilder(Material.BLUE_SHULKER_BOX).setName(ChatColor.DARK_BLUE.toString() + ChatColor.BOLD + "Blau").toItemStack();
    static ItemStack darkGreenTimerColor = new ItemBuilder(Material.GREEN_SHULKER_BOX).setName(ChatColor.DARK_GREEN.toString() + ChatColor.BOLD + "Dunkelgrün").toItemStack();
    static ItemStack darkTurquoiseTimerColor = new ItemBuilder(Material.CYAN_SHULKER_BOX).setName(ChatColor.DARK_AQUA.toString() + ChatColor.BOLD + "Türkis").toItemStack();
    static ItemStack darkRedTimerColor = new ItemBuilder(Material.RED_SHULKER_BOX).setName(ChatColor.DARK_RED.toString() + ChatColor.BOLD + "Dunkelrot").toItemStack();
    static ItemStack purpleTimerColor = new ItemBuilder(Material.PURPLE_SHULKER_BOX).setName(ChatColor.DARK_PURPLE.toString() + ChatColor.BOLD + "Violett").toItemStack();
    static ItemStack darkYellowTimerColor = new ItemBuilder(Material.ORANGE_SHULKER_BOX).setName(ChatColor.GOLD.toString() + ChatColor.BOLD + "Orange").toItemStack();
    static ItemStack lightGrayTimerColor = new ItemBuilder(Material.LIGHT_GRAY_SHULKER_BOX).setName(ChatColor.GRAY.toString() + ChatColor.BOLD + "Hellgrau").toItemStack();
    static ItemStack darkGrayTimerColor = new ItemBuilder(Material.GRAY_SHULKER_BOX).setName(ChatColor.DARK_GRAY.toString() + ChatColor.BOLD + "Grau").toItemStack();
    static ItemStack lightBlueTimerColor = new ItemBuilder(Material.LIGHT_BLUE_SHULKER_BOX).setName(ChatColor.BLUE.toString() + ChatColor.BOLD + "Hellblau").toItemStack();
    static ItemStack lightGreenTimerColor = new ItemBuilder(Material.LIME_SHULKER_BOX).setName(ChatColor.GREEN.toString() + ChatColor.BOLD + "Grün").toItemStack();
    static ItemStack lightTurquoiseTimerColor = new ItemBuilder(Material.CYAN_SHULKER_BOX).setName(ChatColor.AQUA.toString() + ChatColor.BOLD + "Aqua").toItemStack();
    static ItemStack lightRedTimerColor = new ItemBuilder(Material.PINK_SHULKER_BOX).setName(ChatColor.RED.toString() + ChatColor.BOLD + "Rot").toItemStack();
    static ItemStack magentaTimerColor = new ItemBuilder(Material.MAGENTA_SHULKER_BOX).setName(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + "Magenta").toItemStack();
    static ItemStack yellowTimerColor = new ItemBuilder(Material.YELLOW_SHULKER_BOX).setName(ChatColor.YELLOW.toString() + ChatColor.BOLD + "Gelb").toItemStack();
    static ItemStack whiteTimerColor = new ItemBuilder(Material.WHITE_SHULKER_BOX).setName(ChatColor.WHITE.toString() + ChatColor.BOLD + "Weiß").toItemStack();

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

    public static void currentItem(Material item, String current) {
        ItemStack currentItem = new ItemBuilder(item).setName(ChatColor.DARK_PURPLE + current).toItemStack();
        allItemsOverviewInv.setItem(22, currentItem);
    }

    private static void goalChange(String goal, Material material, String status, short slot, String config, boolean bool) throws IOException {
        for (Player all : Bukkit.getOnlinePlayers()) {
            if (bool) {
                all.sendMessage(prefix + ChatColor.GRAY + "Die Herausforderung wurde auf " + ChatColor.GOLD +
                        ChatColor.BOLD + goal + ChatColor.RESET + ChatColor.GRAY + " geändert.");
            }
            ItemStack dye = new ItemBuilder(material).setName(status).toItemStack();
            goalsInv.setItem(slot, dye);
        }
        Config.set("goals." + config, Boolean.valueOf(bool));
    }

    private static void settingsChange(Boolean bool, String message, Material material, String status, byte slot, String config) throws IOException {
        for (Player all : Bukkit.getOnlinePlayers()) {
            if (bool) {
                all.sendMessage(prefix + ChatColor.GRAY + message);
            }
            ItemStack dye = new ItemBuilder(material).setName(status).toItemStack();
            pluginSettingsInv.setItem(slot, dye);
        }
        Config.set("settings." + config, bool);
    }

    private static void challengeChange(String challenge, Material material, String status, short slot, String config, boolean bool) throws IOException {
        for (Player all : Bukkit.getOnlinePlayers()) {
            if (bool) {
                all.sendMessage(prefix + ChatColor.GRAY + " Die Challenge wurde auf " + ChatColor.GOLD +
                        ChatColor.BOLD + challenge + ChatColor.RESET + ChatColor.GRAY + " geändert.");
            }
            ItemStack dye = new ItemBuilder(material).setName(status).toItemStack();
            minecraftChallengesInv.setItem(slot, dye);
        }
        Config.set("challenges." + config, Boolean.valueOf(bool));
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

    /*
     * Puts background items in the inventories
     */
    public static void puttingItems() throws IOException {
        int[] background2SlotsMainInv = {0, 1, 7, 8, 9, 17, 18, 26, 27, 35, 36, 37, 43, 44};

        int[] backgroundSlotsgamerulesInv = {0, 4, 8, 9, 13, 17, 18, 22, 26, 27, 31, 35, 36, 40, 44, 49, 53};
        int[] background2SlotsgamerulesInv = {1, 5, 6, 7, 10, 14, 15, 16, 19, 20, 21, 23, 24, 25, 28, 29, 30, 32, 33, 34, 37, 38, 39, 41, 42, 43, 46, 47, 48, 50, 51, 52};

        int[] backgroundSlotstimerInv = {0, 2, 6, 8, 9, 11, 13, 15, 17, 18, 26, 29, 31, 33};
        int[] background2SlotstimerInv = {1, 3, 5, 7, 10, 12, 14, 16, 19, 21, 23, 25, 28, 30, 32, 34};

        int[] backgroundSlotstimerInv2 = {0, 2, 4, 6, 8, 9, 11, 13, 15, 17, 18, 20, 22, 24, 26, 29, 31, 33};
        int[] background2SlotstimerInv2 = {28, 30, 32, 34};
        int[] modifyTimeSlotstimerInv2 = {1, 3, 5, 7, 19, 21, 23, 25};  //TODO: Bei den unteren Knäpfen steht auch noch +10 obwohl da -10 sein sollte und Text ändern!

        int[] backgroundSlotstimerInv3 = {0, 2, 4, 6, 8, 9, 11, 15, 17, 18, 20, 22, 24, 26, 29, 33, 35};
        int[] background2SlotstimerInv3 = {1, 3, 5, 7, 10, 12, 14, 16, 19, 21, 23, 25, 28, 30, 32, 34};

        int[] backgroundSlotsGoalsInv = {0, 4, 8, 9, 13, 17, 18, 22, 26, 27, 31, 35, 36, 40, 44, 49, 53};
        int[] background2SlotsGoalsInv = {1, 7, 10, 16, 19, 20, 21, 23, 24, 25, 28, 32, 33, 34, 37, 38, 39, 41, 42, 43, 46, 47, 48, 50, 51, 52};

        int[] backgroundPurpleSlotsAllItemsOverviewInv = {12, 13, 14, 21, 23, 30, 31, 32};
        int[] backgroundGreenSlotsAllItemsOverviewInv = {18, 19, 20, 27, 29, 36, 37, 38};
        int[] backgroundRedSlotsAllItemsOverviewInv = {24, 25, 26, 33, 35, 42, 43, 44};

        int[] background2SlotsChallengesInv = {0, 1, 2, 3, 4, 5, 6, 7, 8, 18, 19, 20, 21, 22, 23, 24, 25, 26};
        int[] backgroundSlotsChallengesInv = {9, 10, 12, 13, 14, 16, 17, 27, 28, 29, 30, 31, 32, 33, 34, 35};

        int[] background2SlotsMinecraftChallengesInv = {0, 1, 7, 8, 18, 19, 20, 24, 25, 26, 36, 37, 38, 39, 40, 41, 42, 43, 44};
        int[] backgroundSlotsMinecraftChallengesInv = {9, 10, 16, 17, 27, 28, 29, 33, 34, 35, 46, 47, 48, 50, 51, 52, 53};

        int[] backgroundSlotsWolfiChallengeInv = {1, 7};
        int[] background2SlotsWolfiChallengeInv = {2, 4, 6};

        int[] backgroundSlotsPlayerSettingsInv = {1, 3, 5, 7, 9, 11, 15, 17, 19, 21, 23, 25};
        int[] background2SlotsPlayerSettingsInv = {0, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26};

        int[] background2SlotsTimerColorInv = {9, 10, 16, 17, 19, 20, 24, 25};

        int[] backgroundSlotsPluginSettingsInv = {0, 2, 4, 6, 8, 9, 13, 17, 18, 22, 27, 26, 29, 33, 35};
        int[] background2SlotsPluginSettingsInv = {1, 3, 5, 7, 10, 12, 14, 16, 19, 21, 23, 25, 28, 30, 32, 34};

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
        puttingItemsHelper(backgroundPurpleSlotsAllItemsOverviewInv, allItemsOverviewInv, backgroundPurple);
        puttingItemsHelper(backgroundGreenSlotsAllItemsOverviewInv, allItemsOverviewInv, backgroundGreen);
        puttingItemsHelper(backgroundRedSlotsAllItemsOverviewInv, allItemsOverviewInv, backgroundRed);
        puttingItemsHelper(background2SlotsChallengesInv, challengesInv, background2);
        puttingItemsHelper(backgroundSlotsChallengesInv, challengesInv, background);
        puttingItemsHelper(background2SlotsMinecraftChallengesInv, minecraftChallengesInv, background2);
        puttingItemsHelper(backgroundSlotsMinecraftChallengesInv, minecraftChallengesInv, background);
        puttingItemsHelper(backgroundSlotsWolfiChallengeInv, wolfiChallengeInv, background);
        puttingItemsHelper(background2SlotsWolfiChallengeInv, wolfiChallengeInv, background2);
        puttingItemsHelper(backgroundSlotsPlayerSettingsInv, playerSettingsInv, background);
        puttingItemsHelper(background2SlotsPlayerSettingsInv, playerSettingsInv, background2);
        puttingItemsHelper(backgroundSlotsPlayerSettingsInv, cosmeticsCategoriesInv, background);
        puttingItemsHelper(background2SlotsPlayerSettingsInv, cosmeticsCategoriesInv, background2);
        puttingItemsHelper(backgroundSlotsPluginSettingsInv, pluginSettingsInv, background);
        puttingItemsHelper(background2SlotsPluginSettingsInv, pluginSettingsInv, background2);
        puttingItemsHelper(background2SlotsTimerColorInv, timerColorsInv, background2);


        for (int i = 2; i <= 44; i++) {
            if (i <= 3 || i == 5 || i == 6 || (i >= 10 && i <= 16) || i == 19 || i == 21 || i == 23 || i == 25 || (i >= 28 && i <= 34) || (i >= 38 && i <= 39) || i == 41 || i == 42) {
                settingsInv.setItem(i, background);
            }
        }
        for (int i = 0; i <= 53; i++) {
            if (i <= 11 || (i >= 15 && i <= 17) || (i >= 39 && i <= 41) || i >= 45) {
                allItemsOverviewInv.setItem(i, background3);
            }
        }

        /*
         * Puts important items in the inventories
         */
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
        goalsInv.setItem(6, witherGoal);
        goalsInv.setItem(15, wardenGoal);
        goalsInv.setItem(29, allItemsGoal);
        goalsInv.setItem(45, back);

        allItemsOverviewInv.setItem(28, registeredItems);
        allItemsOverviewInv.setItem(34, pendingItems);

        challengesInv.setItem(11, minecraftChallenges);
        challengesInv.setItem(15, randomizerChallenges);
        challengesInv.setItem(31, backArrow);

        minecraftChallengesInv.setItem(2, noCraftingTableChallenge);
        minecraftChallengesInv.setItem(3, noFallDamageChallenge);
        minecraftChallengesInv.setItem(4, noArmorChallenge);
        minecraftChallengesInv.setItem(5, limitedHealthChallenge);
        minecraftChallengesInv.setItem(6, noJumpChallenge);
        minecraftChallengesInv.setItem(21, noSneakChallenge);
        minecraftChallengesInv.setItem(22, wolfiChallenge);
        minecraftChallengesInv.setItem(23, dividedHearts);
        minecraftChallengesInv.setItem(45, back);
        minecraftChallengesInv.setItem(49, mainMenu);

        wolfiChallengeInv.setItem(0, back);
        wolfiChallengeInv.setItem(3, nameChange);
        wolfiChallengeInv.setItem(5, collarColor);
        wolfiChallengeInv.setItem(8, back);
        wolfiNameChangeInv.setItem(0, nameChangeInput);

        wolfiCollarColorChangeInv.setItem(0, whiteCollarColor);
        wolfiCollarColorChangeInv.setItem(1, lightGrayCollarColor);
        wolfiCollarColorChangeInv.setItem(2, grayCollarColor);
        wolfiCollarColorChangeInv.setItem(3, blackCollarColor);
        wolfiCollarColorChangeInv.setItem(4, brownCollarColor);
        wolfiCollarColorChangeInv.setItem(5, redCollarColor);
        wolfiCollarColorChangeInv.setItem(6, orangeCollarColor);
        wolfiCollarColorChangeInv.setItem(7, yellowCollarColor);
        wolfiCollarColorChangeInv.setItem(8, limeCollarColor);
        wolfiCollarColorChangeInv.setItem(9, greenCollarColor);
        wolfiCollarColorChangeInv.setItem(10, cyanCollarColor);
        wolfiCollarColorChangeInv.setItem(11, lightBlueCollarColor);
        wolfiCollarColorChangeInv.setItem(12, blueCollarColor);
        wolfiCollarColorChangeInv.setItem(13, purpleCollarColor);
        wolfiCollarColorChangeInv.setItem(14, magentaCollarColor);
        wolfiCollarColorChangeInv.setItem(15, pinkCollarColor);
        wolfiCollarColorChangeInv.setItem(16, background2);
        wolfiCollarColorChangeInv.setItem(17, back);

        playerSettingsInv.setItem(13, timerCosmetics);

        cosmeticsCategoriesInv.setItem(13, timerColorCosmetics);

        pluginSettingsInv.setItem(11, backpack);
        pluginSettingsInv.setItem(15, damageInChat);
        pluginSettingsInv.setItem(31, backArrow);

        timerColorsInv.setItem(0, blackTimerColor);
        timerColorsInv.setItem(1, darkBlueTimerColor);
        timerColorsInv.setItem(2, darkGreenTimerColor);
        timerColorsInv.setItem(3, darkTurquoiseTimerColor);
        timerColorsInv.setItem(4, darkRedTimerColor);
        timerColorsInv.setItem(5, purpleTimerColor);
        timerColorsInv.setItem(6, darkYellowTimerColor);
        timerColorsInv.setItem(7, lightGrayTimerColor);
        timerColorsInv.setItem(8, darkGrayTimerColor);
        timerColorsInv.setItem(11, lightBlueTimerColor);
        timerColorsInv.setItem(12, lightGreenTimerColor);
        timerColorsInv.setItem(14, lightTurquoiseTimerColor);
        timerColorsInv.setItem(15, lightRedTimerColor);
        timerColorsInv.setItem(21, magentaTimerColor);
        timerColorsInv.setItem(22, yellowTimerColor);
        timerColorsInv.setItem(23, whiteTimerColor);
        timerColorsInv.setItem(18, backGlassPane);
        timerColorsInv.setItem(26, backGlassPane);
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
     * Checks if the config has a certain path set to true, if yes, dye color is green, if not, dye color is red
     */
    public void dyeColorCheck(String path, Inventory inventory, short slot) {
        if (Config.getBoolean(path).booleanValue()) {
            ItemStack dye = new ItemBuilder(Material.GREEN_DYE).setName(ChatColor.GREEN + "Aktiviert").toItemStack();
            inventory.setItem(slot, dye);
        } else {
            ItemStack dye = new ItemBuilder(Material.RED_DYE).setName(ChatColor.RED + "Deaktiviert").toItemStack();
            inventory.setItem(slot, dye);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) throws IOException {
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
                ChatColor.BLUE + "Seite 1") || event.getView().getTitle().equalsIgnoreCase(ChatColor.DARK_GRAY + "Alle Items Overview") ||
                event.getView().getTitle().equalsIgnoreCase(ChatColor.AQUA + "Challenges") ||
                event.getView().getTitle().equalsIgnoreCase(ChatColor.GREEN + "Minecraft-Challenges" + ChatColor.DARK_GRAY + " • " +
                        ChatColor.BLUE + "Seite 1") || event.getView().getTitle().equalsIgnoreCase(ChatColor.GREEN + "Wolfi"
                + ChatColor.DARK_GRAY + " • " + ChatColor.BLUE + "Anpassungsmenü") || event.getView().getTitle().equalsIgnoreCase(ChatColor.DARK_GRAY + "Namen ändern")
                || event.getView().getTitle().equalsIgnoreCase(ChatColor.DARK_GRAY + "Halsbandfarbe ändern") || event.getView().getTitle().equalsIgnoreCase(
                ChatColor.GRAY + "〣 " + ChatColor.LIGHT_PURPLE + "Cosmetics") || event.getView().getTitle().equalsIgnoreCase(
                ChatColor.DARK_GRAY + "Kategorien") || event.getView().getTitle().equalsIgnoreCase(ChatColor.LIGHT_PURPLE + "Plugin-Einstellungen"
                + ChatColor.DARK_GRAY + " • " + ChatColor.BLUE + "Seite 1") ||
                event.getView().getTitle().equalsIgnoreCase(ChatColor.DARK_GRAY + "Timer-Farben")) {
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
                        ItemStack difficultydye = new ItemBuilder(Material.WHITE_DYE, 1).setName("Friedlich").toItemStack();            //TODO: Design
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
            } else if (event.getCurrentItem().getType().equals(Material.DRAGON_HEAD)) {
                if (event.isLeftClick()) {
                    dyeColorCheck("goals.enderdragon", goalsInv, (short) 3);
                    dyeColorCheck("goals.elderguardian", goalsInv, (short) 12);
                    dyeColorCheck("goals.wither", goalsInv, (short) 5);
                    dyeColorCheck("goals.warden", goalsInv, (short) 14);
                    dyeColorCheck("goals.allitems", goalsInv, (short) 30);
                    player.openInventory(goalsInv);
                }
            } else if (event.getCurrentItem().getType().equals(Material.GRASS_BLOCK)) {
                if (event.isLeftClick()) {
                    player.openInventory(challengesInv);
                }
            } else if (event.getCurrentItem().getType().equals(Material.REPEATER)) {
                if (event.isLeftClick()) {
                    dyeColorCheck("settings.backpack", pluginSettingsInv, (short) 20);
                    dyeColorCheck("settings.damage", pluginSettingsInv, (short) 24);
                    player.openInventory(pluginSettingsInv);
                }
            }
        } else if (event.getView().getTitle().equalsIgnoreCase(ChatColor.GREEN + "Spielregeln" + ChatColor.DARK_GRAY + " • " +
                ChatColor.BLUE + "Seite 1")) {
            if (event.getCurrentItem().getType().equals(Material.DARK_OAK_DOOR)) {
                if (event.isLeftClick()) {
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
                if (event.isLeftClick()) {
                    player.openInventory(timerInv2);
                    buildNextPage((byte) 3, timerInv2);
                    timeCalculator();
                }
            } else if (event.getCurrentItem().getType().equals(Material.DARK_OAK_DOOR)) {
                if (event.isLeftClick()) {
                    player.openInventory(settingsInv);
                }
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
                if (event.isLeftClick()) {
                    player.openInventory(timerInv);
                }
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
                if (event.isLeftClick()) {
                    player.openInventory(timerInv2);
                }
            } else if (event.getCurrentItem().getType().equals(Material.COMPARATOR)) {
                if (event.isLeftClick()) {
                    player.openInventory(settingsInv);
                }
            }
        } else if (event.getView().getTitle().equalsIgnoreCase(ChatColor.RED + "Herausforderungen" + ChatColor.DARK_GRAY + " • " +
                ChatColor.BLUE + "Seite 1")) {
            if (Objects.requireNonNull(event.getCurrentItem()).getType().equals(Material.DRAGON_EGG) || event.getSlot() == 3) {
                if (event.isLeftClick()) {
                    if (!Config.getBoolean("goals.enderdragon").booleanValue()) {
                        goalChange("Enderdrache", Material.GREEN_DYE, ChatColor.GREEN + "Aktiviert", (short) 3, "enderdragon", true);
                    }
                } else if (event.isRightClick()) {
                    if (Config.getBoolean("goals.enderdragon").booleanValue()) {
                        goalChange("Enderdrache", Material.RED_DYE, ChatColor.RED + "Deaktiviert", (short) 3, "enderdragon", false);
                    }
                }
            } else if (event.getCurrentItem().getType().equals(Material.TRIDENT) || event.getSlot() == 12) {
                if (event.isLeftClick()) {
                    if (!Config.getBoolean("goals.elderguardian").booleanValue()) {
                        goalChange("Großer Wächter", Material.GREEN_DYE, ChatColor.GREEN + "Aktiviert", (short) 12, "elderguardian", true);
                    }
                } else if (event.isRightClick()) {
                    if (Config.getBoolean("goals.elderguardian").booleanValue()) {
                        goalChange("Großer Wächter", Material.RED_DYE, ChatColor.RED + "Deaktiviert", (short) 12, "elderguardian", false);
                    }
                }
            } else if (event.getCurrentItem().getType().equals(Material.WITHER_SKELETON_SKULL) || event.getSlot() == 5) {
                if (event.isLeftClick()) {
                    if (!Config.getBoolean("goals.wither").booleanValue()) {
                        goalChange("Wither", Material.GREEN_DYE, ChatColor.GREEN + "Aktiviert", (short) 5, "wither", true);
                    }
                } else if (event.isRightClick()) {
                    if (Config.getBoolean("goals.wither").booleanValue()) {
                        goalChange("Wither", Material.RED_DYE, ChatColor.RED + "Deaktiviert", (short) 5, "wither", false);
                    }
                }
            } else if (event.getCurrentItem().getType().equals(Material.SCULK_SENSOR) || event.getSlot() == 14) {
                if (event.isLeftClick()) {
                    if (!Config.getBoolean("goals.warden").booleanValue()) {
                        goalChange("Wärter", Material.GREEN_DYE, ChatColor.GREEN + "Aktiviert", (short) 14, "warden", true);
                    }
                } else if (event.isRightClick()) {
                    if (Config.getBoolean("goals.warden").booleanValue()) {
                        goalChange("Wärter", Material.RED_DYE, ChatColor.RED + "Deaktiviert", (short) 14, "warden", false);
                    }
                }
            } else if (event.getCurrentItem().getType().equals(Material.GRASS_BLOCK) || event.getSlot() == 30) {
                if (event.isLeftClick()) {
                    if (!Config.getBoolean("goals.allitems").booleanValue()) {
                        goalChange("Alle Items", Material.GREEN_DYE, ChatColor.GREEN + "Aktiviert", (short) 30, "allitems", true);
                        allItems();
                        updateItems(false);
                    }
                } else if (event.isRightClick()) {
                    if (Config.getBoolean("goals.allitems").booleanValue()) {
                        goalChange("Alle Items", Material.RED_DYE, ChatColor.RED + "Deaktiviert", (short) 30, "allitems", false);
                        deactivateAllItems();
                    }
                }
            } else if (event.getCurrentItem().getType().equals(Material.DARK_OAK_DOOR)) {
                if (event.isLeftClick()) {
                    player.openInventory(settingsInv);
                }
            }
        } else if (event.getView().getTitle().equalsIgnoreCase(ChatColor.AQUA + "Challenges")) {
            if (event.getCurrentItem().getType().equals(Material.CRAFTING_TABLE)) {
                if (event.isLeftClick()) {
                    dyeColorCheck("challenges.nocraftingtable", minecraftChallengesInv, (short) 11);
                    dyeColorCheck("challenges.nofalldamage", minecraftChallengesInv, (short) 12);
                    dyeColorCheck("challenges.noarmor", minecraftChallengesInv, (short) 13);
                    dyeColorCheck("challenges.limitedhealth", minecraftChallengesInv, (short) 14);
                    dyeColorCheck("challenges.nojump", minecraftChallengesInv, (short) 15);
                    dyeColorCheck("challenges.nosneak", minecraftChallengesInv, (short) 30);
                    dyeColorCheck("challenges.wolfi", minecraftChallengesInv, (short) 31);
                    dyeColorCheck("challenges.dividedhearts", minecraftChallengesInv, (short) 32);

                    player.openInventory(minecraftChallengesInv);
                }
            } else if (event.getCurrentItem().getType().equals(Material.CHAIN_COMMAND_BLOCK)) {
                if (event.isLeftClick()) {
                    //TODO
                }
            } else if (event.getCurrentItem().getType().equals(Material.ARROW)) {
                if (event.isLeftClick()) {
                    player.openInventory(settingsInv);
                }
            }
        } else if (event.getView().getTitle().equalsIgnoreCase(ChatColor.GREEN + "Minecraft-Challenges" + ChatColor.DARK_GRAY + " • " +
                ChatColor.BLUE + "Seite 1")) {
            if (event.getCurrentItem().getType().equals(Material.DARK_OAK_DOOR)) {
                if (event.isLeftClick()) {
                    player.openInventory(challengesInv);
                }
            } else if (event.getCurrentItem().getType().equals(Material.COMPARATOR)) {
                if (event.isLeftClick()) {
                    player.openInventory(settingsInv);
                }
            } else if (event.getCurrentItem().getType().equals(Material.CRAFTING_TABLE) || event.getSlot() == 11) {
                if (event.isLeftClick()) {
                    if (!Config.getBoolean("challenges.nocraftingtable").booleanValue()) {
                        challengeChange("Ohne Werkbank", Material.GREEN_DYE, ChatColor.GREEN + "Aktiviert", (short) 11, "nocraftingtable", true);
                    }
                } else if (event.isRightClick()) {
                    if (Config.getBoolean("challenges.nocraftingtable").booleanValue()) {
                        challengeChange("Ohne Werkbank", Material.RED_DYE, ChatColor.RED + "Deaktiviert", (short) 11, "nocraftingtable", false);
                    }
                }
            } else if (event.getCurrentItem().getType().equals(Material.FEATHER) || event.getSlot() == 12) {
                if (event.isLeftClick()) {
                    if (!Config.getBoolean("challenges.nofalldamage").booleanValue()) {
                        challengeChange("Ohne Fallschaden", Material.GREEN_DYE, ChatColor.GREEN + "Aktiviert", (short) 12, "nofalldamage", true);
                    }
                } else if (event.isRightClick()) {
                    if (Config.getBoolean("challenges.nofalldamage").booleanValue()) {
                        challengeChange("Ohne Fallschaden", Material.RED_DYE, ChatColor.RED + "Deaktiviert", (short) 12, "nofalldamage", false);
                    }
                }
            } else if (event.getCurrentItem().getType().equals(Material.LEATHER_CHESTPLATE) || event.getSlot() == 13) {
                if (event.isLeftClick()) {
                    if (!Config.getBoolean("challenges.noarmor").booleanValue()) {
                        challengeChange("Ohne Rüstung", Material.GREEN_DYE, ChatColor.GREEN + "Aktiviert", (short) 13, "noarmor", true);
                    }
                } else if (event.isRightClick()) {
                    if (Config.getBoolean("challenges.noarmor").booleanValue()) {
                        challengeChange("Ohne Rüstung", Material.RED_DYE, ChatColor.RED + "Deaktiviert", (short) 13, "noarmor", false);
                    }
                }
            } else if (event.getCurrentItem().getType().equals(Material.SPECTRAL_ARROW) || event.getSlot() == 14) {
                if (event.isLeftClick()) {
                    if (!Config.getBoolean("challenges.limitedhealth").booleanValue()) {
                        challengeChange("Limitierte Herzen", Material.GREEN_DYE, ChatColor.GREEN + "Aktiviert", (short) 14, "limitedhealth", true);
                    }
                } else if (event.isRightClick()) {
                    if (Config.getBoolean("challenges.limitedhealth").booleanValue()) {
                        challengeChange("Limitierte Herzen", Material.RED_DYE, ChatColor.RED + "Deaktiviert", (short) 14, "limitedhealth", false);
                    }
                }
            } else if (event.getCurrentItem().getType().equals(Material.FIREWORK_ROCKET) || event.getSlot() == 15) {
                if (event.isLeftClick()) {
                    if (!Config.getBoolean("challenges.nojump").booleanValue()) {
                        challengeChange("Ohne Springen", Material.GREEN_DYE, ChatColor.GREEN + "Aktiviert", (short) 15, "nojump", true);
                    }
                } else if (event.isRightClick()) {
                    if (Config.getBoolean("challenges.nojump").booleanValue()) {
                        challengeChange("Ohne Springen", Material.RED_DYE, ChatColor.RED + "Deaktiviert", (short) 15, "nojump", false);
                    }
                }
            } else if (event.getCurrentItem().getType().equals(Material.DIAMOND_BOOTS) || event.getSlot() == 30) {
                if (event.isLeftClick()) {
                    if (!Config.getBoolean("challenges.nosneak").booleanValue()) {
                        challengeChange("Ohne Sneaken", Material.GREEN_DYE, ChatColor.GREEN + "Aktiviert", (short) 30, "nosneak", true);
                    }
                } else if (event.isRightClick()) {
                    if (Config.getBoolean("challenges.nosneak").booleanValue()) {
                        challengeChange("Ohne Sneaken", Material.RED_DYE, ChatColor.RED + "Deaktiviert", (short) 30, "nosneak", false);
                    }
                }
            } else if (event.getCurrentItem().getType().equals(Material.BONE) || event.getSlot() == 31) {
                if (event.isLeftClick()) {
                    if (!Config.getBoolean("challenges.wolfi").booleanValue()) {
                        challengeChange("Wolfi", Material.GREEN_DYE, ChatColor.GREEN + "Aktiviert", (short) 31, "wolfi", true);
                        spawnWolfi();
                    }
                } else if (event.isRightClick()) {
                    if (Config.getBoolean("challenges.wolfi").booleanValue()) {
                        challengeChange("Wolfi", Material.RED_DYE, ChatColor.RED + "Deaktiviert", (short) 31, "wolfi", false);
                        removeWolfi();
                    }
                }
            } else if (event.getCurrentItem().getType().equals(Material.HEART_OF_THE_SEA) || event.getSlot() == 32) {
                if (event.isLeftClick()) {
                    if (!Config.getBoolean("challenges.dividedhearts").booleanValue()) {
                        challengeChange("Geteilte Herzen", Material.GREEN_DYE, ChatColor.GREEN + "Aktiviert", (short) 32, "dividedhearts", true);
                    }
                } else if (event.isRightClick()) {
                    if (Config.getBoolean("challenges.dividedhearts").booleanValue()) {
                        challengeChange("Geteilte Herzen", Material.RED_DYE, ChatColor.RED + "Deaktiviert", (short) 32, "dividedhearts", false);
                    }
                }
            }
        } else if (event.getView().getTitle().equalsIgnoreCase(ChatColor.GREEN + "Wolfi"
                + ChatColor.DARK_GRAY + " • " + ChatColor.BLUE + "Anpassungsmenü")) {
            if (event.getCurrentItem().getType().equals(Material.DARK_OAK_DOOR)) {
                if (event.isLeftClick()) {
                    player.closeInventory();
                }
            } else if (event.getCurrentItem().getType().equals(Material.NAME_TAG)) {
                if (event.isLeftClick()) {
                    player.openInventory(wolfiNameChangeInv);
                }
            } else if (event.getCurrentItem().getType().equals(Material.ARMOR_STAND)) {
                if (event.isLeftClick()) {
                    player.openInventory(wolfiCollarColorChangeInv);
                }
            }
        } else if (event.getView().getTitle().equalsIgnoreCase(ChatColor.DARK_GRAY + "Namen ändern")) { //TODO: geht alles nach nem reload nicht mehr
            Inventory inventory = event.getInventory();
            if (event.getSlot() == 0) {
                player.sendMessage("test1");
            }
            if (event.getSlot() == 2) {
                player.sendMessage("test2");
            }
            if (event.getRawSlot() == 2) {
                player.sendMessage("test3");
            }
            if (inventory instanceof AnvilInventory) {
                player.sendMessage("1");
                InventoryView view = event.getView();
                int rawSlot = event.getRawSlot();
                if (rawSlot == view.convertSlot(rawSlot)) {
                    player.sendMessage("2");
                    if (rawSlot == 2) {
                        player.sendMessage("3");
                        ItemStack item = event.getCurrentItem();
                        if (item != null) {
                            player.sendMessage("4");
                            ItemMeta meta = item.getItemMeta();
                            if (meta != null) {
                                player.sendMessage("5");            //TODO: Funktioniert alles nicht
                                if (meta.hasDisplayName()) {
                                    player.sendMessage("6");
                                    String displayName = meta.getDisplayName();
                                    player.sendMessage(displayName);
                                }
                            }
                        }
                    }
                }
            }
        } else if (event.getView().getTitle().equalsIgnoreCase(ChatColor.DARK_GRAY + "Halsbandfarbe ändern")) {
            if (event.getSlot() == 17) {         //TODO: geht alles nach nem reload nicht mehr
                player.openInventory(wolfiChallengeInv);
            } else if (event.getSlot() == 0) {
                wolf.setCollarColor(DyeColor.WHITE);
            } else if (event.getSlot() == 1) {
                wolf.setCollarColor(DyeColor.LIGHT_GRAY);
            } else if (event.getSlot() == 2) {
                wolf.setCollarColor(DyeColor.GRAY);
            } else if (event.getSlot() == 3) {
                wolf.setCollarColor(DyeColor.BLACK);
            } else if (event.getSlot() == 4) {
                wolf.setCollarColor(DyeColor.BROWN);
            } else if (event.getSlot() == 5) {
                wolf.setCollarColor(DyeColor.RED);
            } else if (event.getSlot() == 6) {
                wolf.setCollarColor(DyeColor.ORANGE);
            } else if (event.getSlot() == 7) {
                wolf.setCollarColor(DyeColor.YELLOW);
            } else if (event.getSlot() == 8) {
                wolf.setCollarColor(DyeColor.LIME);
            } else if (event.getSlot() == 9) {
                wolf.setCollarColor(DyeColor.GREEN);
            } else if (event.getSlot() == 10) {
                wolf.setCollarColor(DyeColor.CYAN);
            } else if (event.getSlot() == 11) {
                wolf.setCollarColor(DyeColor.LIGHT_BLUE);
            } else if (event.getSlot() == 12) {
                wolf.setCollarColor(DyeColor.BLUE);
            } else if (event.getSlot() == 13) {
                wolf.setCollarColor(DyeColor.PURPLE);
            } else if (event.getSlot() == 14) {
                wolf.setCollarColor(DyeColor.MAGENTA);
            } else if (event.getSlot() == 15) {
                wolf.setCollarColor(DyeColor.PINK);
            }
        } else if (event.getView().getTitle().equalsIgnoreCase(ChatColor.GRAY + "〣 " + ChatColor.LIGHT_PURPLE + "Cosmetics")) {
            if (event.getCurrentItem().getType().equals(Material.CLOCK)) {
                if (event.isLeftClick()) {
                    player.openInventory(cosmeticsCategoriesInv);       //TODO: Weiter machen
                }
            }
        } else if (event.getView().getTitle().equalsIgnoreCase(ChatColor.LIGHT_PURPLE + "Plugin-Einstellungen"
                + ChatColor.DARK_GRAY + " • " + ChatColor.BLUE + "Seite 1")) {
            if (event.getCurrentItem().getType().equals(Material.ARROW)) {
                if (event.isLeftClick()) {
                    player.openInventory(settingsInv);
                }
            } else if (Objects.requireNonNull(event.getCurrentItem()).getType().equals(Material.ENDER_CHEST) || event.getSlot() == 20) {
                if (event.isLeftClick()) {
                    if (!Config.getBoolean("settings.backpack").booleanValue()) {
                        settingsChange(true, "Das" + ChatColor.GOLD + ChatColor.BOLD + " Backpack "
                                        + ChatColor.RESET + ChatColor.GRAY + "wurde aktiviert.", Material.GREEN_DYE,
                                ChatColor.GREEN + "Aktiviert", (byte) 20, "backpack");
                    }
                } else if (event.isRightClick()) {
                    if (Config.getBoolean("settings.backpack").booleanValue()) {
                        settingsChange(false, "Das" + ChatColor.GOLD + ChatColor.BOLD + " Backpack "
                                        + ChatColor.RESET + ChatColor.GRAY + "wurde deaktiviert.", Material.RED_DYE,
                                ChatColor.RED + "Deaktiviert", (byte) 20, "backpack");
                    }
                }
            } else if (event.getCurrentItem().getType().equals(Material.SPAWNER) || event.getSlot() == 24) {
                if (event.isLeftClick()) {
                    if (!Config.getBoolean("settings.damage").booleanValue()) {
                        settingsChange(true, "Der" + ChatColor.GOLD + ChatColor.BOLD + " Schaden im Chat "
                                        + ChatColor.RESET + ChatColor.GRAY + "wird nun angezeigt.", Material.GREEN_DYE,
                                ChatColor.GREEN + "Aktiviert", (byte) 24, "damage");
                    }
                } else if (event.isRightClick()) {
                    if (Config.getBoolean("settings.damage").booleanValue()) {
                        settingsChange(false, "Der" + ChatColor.GOLD + ChatColor.BOLD + " Schaden im Chat "
                                        + ChatColor.RESET + ChatColor.GRAY + "wird nun nicht mehr angezeigt.", Material.RED_DYE,
                                ChatColor.GREEN + "Aktiviert", (byte) 24, "damage");
                    }
                }
            }
        } else if (event.getView().getTitle().equalsIgnoreCase(ChatColor.DARK_GRAY + "Kategorien")) {
            if (event.getCurrentItem().getType().equals(Material.CLOCK)) {
                if (event.isLeftClick()) {
                    player.openInventory(timerColorsInv);
                }
            }
        } else if (event.getView().getTitle().equalsIgnoreCase(ChatColor.DARK_GRAY + "Timer-Farben")) {
            if (event.getCurrentItem().getType().equals(Material.RED_STAINED_GLASS_PANE)) {
                if (event.isLeftClick()) {
                    player.openInventory(cosmeticsCategoriesInv);
                }
            } else if (event.getCurrentItem().getType().equals(Material.BLACK_SHULKER_BOX)) {
                if (event.isLeftClick()) {
                    Bukkit.dispatchCommand(player, "timer color 0");
                }
            } else if (event.getCurrentItem().getType().equals(Material.BLUE_SHULKER_BOX)) {
                if (event.isLeftClick()) {
                    Bukkit.dispatchCommand(player, "timer color 1");
                }
            } else if (event.getCurrentItem().getType().equals(Material.GREEN_SHULKER_BOX)) {
                if (event.isLeftClick()) {
                    Bukkit.dispatchCommand(player, "timer color 2");
                }
            } else if (event.getCurrentItem().getType().equals(Material.CYAN_SHULKER_BOX) && event.getSlot() == 3) {
                if (event.isLeftClick()) {
                    Bukkit.dispatchCommand(player, "timer color 3");
                }
            } else if (event.getCurrentItem().getType().equals(Material.RED_SHULKER_BOX)) {
                if (event.isLeftClick()) {
                    Bukkit.dispatchCommand(player, "timer color 4");
                }
            } else if (event.getCurrentItem().getType().equals(Material.PURPLE_SHULKER_BOX)) {
                if (event.isLeftClick()) {
                    Bukkit.dispatchCommand(player, "timer color 5");
                }
            } else if (event.getCurrentItem().getType().equals(Material.ORANGE_SHULKER_BOX)) {
                if (event.isLeftClick()) {
                    Bukkit.dispatchCommand(player, "timer color 6");
                }
            } else if(event.getCurrentItem().getType().equals(Material.LIGHT_GRAY_SHULKER_BOX)) {
                if(event.isLeftClick()) {
                    Bukkit.dispatchCommand(player, "timer color 7");
                }
            } else if(event.getCurrentItem().getType().equals(Material.GRAY_SHULKER_BOX)) {
                if(event.isLeftClick()) {
                    Bukkit.dispatchCommand(player, "timer color 8");
                }
            } else if(event.getCurrentItem().getType().equals(Material.LIGHT_BLUE_SHULKER_BOX)) {
                if(event.isLeftClick()) {
                    Bukkit.dispatchCommand(player, "timer color 9");
                }
            }
            else if(event.getCurrentItem().getType().equals(Material.LIME_SHULKER_BOX)) {
                if(event.isLeftClick()) {
                    Bukkit.dispatchCommand(player, "timer color a");
                }
            } else if(event.getCurrentItem().getType().equals(Material.CYAN_SHULKER_BOX) || event.getSlot() == 14) {
                if(event.isLeftClick()) {
                    Bukkit.dispatchCommand(player, "timer color b");
                }
            } else if(event.getCurrentItem().getType().equals(Material.PINK_SHULKER_BOX)) {
                if(event.isLeftClick()) {
                    Bukkit.dispatchCommand(player, "timer color c");
                }
            } else if(event.getCurrentItem().getType().equals(Material.MAGENTA_SHULKER_BOX)) {
                if(event.isLeftClick()) {
                    Bukkit.dispatchCommand(player, "timer color d");
                }
            } else if(event.getCurrentItem().getType().equals(Material.YELLOW_SHULKER_BOX)) {
                if(event.isLeftClick()) {
                    Bukkit.dispatchCommand(player, "timer color e");
                }
            } else if(event.getCurrentItem().getType().equals(Material.WHITE_SHULKER_BOX)) {
                if(event.isLeftClick()) {
                    Bukkit.dispatchCommand(player, "timer color f");
                }
            }
        }
    }
}
