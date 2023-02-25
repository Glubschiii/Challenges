package it.glubschiii.Challenges.goals;

import it.glubschiii.Challenges.timer.Timer;
import it.glubschiii.Challenges.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static it.glubschiii.Challenges.utils.Config.config;
import static it.glubschiii.Challenges.utils.MainInventoryManager.*;

//TODO: Inventar mit /allitems machen (Fehlende Blöcke - Derzeit Item - bereits gesammelte Bläcke (Mit Zeitangabe) - etc)

/** @author Glubschiii | https://github.com/glubschiii
 @since 1.0.8
 */

public class AllItemsGoal implements Listener, CommandExecutor {
    public static BossBar bossBar;
    public static Material current;
    private static List<String> materials;
    private static int size;
    private static int sizeProgress;

    /*
    * Method that is executed only at the start of the challenge
     */
    public static void allItems() throws IOException {
        materials = new ArrayList<String>();
        for (Material material : Material.values()) {
            materials.add(String.valueOf(material));
        }
        size = materials.size();
        Collections.shuffle(materials);
        Config.set("allitems.items", materials);
        Config.set("allitems.progress", 0);
        sizeProgress = 0;
        Config.set("allitems.size", size);
    }

    /*
     * Method executed at the start of the challenge (where boolean delete is false) and executed each time an item is collected (where boolean delete is true)
     */
    public static void updateItems(boolean delete) throws IOException {
        if (delete) {
            for (Player all : Bukkit.getOnlinePlayers()) {
                bossBar.removePlayer(all);
            }
            materials.remove(0);
        }
        current = Material.valueOf(materials.get(0));
        if (materials.size() != 0) {                     //TODO: java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
            bossBar = Bukkit.createBossBar(ChatColor.GRAY + "Item" + ChatColor.DARK_GRAY + " » " + ChatColor.WHITE + currentMessage(String.valueOf(current)) + " #"
                    + sizeProgress + "/" + size, BarColor.WHITE, BarStyle.SOLID);
        } else {
            bossBar = Bukkit.createBossBar(ChatColor.GREEN + "Alle Items wurden registriert! (" + size + "/" + size + ")", BarColor.GREEN, BarStyle.SOLID);               //TODO: Wird nicht ausgeführt
        }
        for (Player all : Bukkit.getOnlinePlayers()) {
            bossBar.addPlayer(all);
        }
        Config.set("allitems.current", current.toString());
        Config.set("allitems.items", materials);
        Config.set("allitems.progress", sizeProgress);
        currentItem(current, currentMessage(String.valueOf(current)));
    }

    /*
    * Query whether the correct item was collected
     */
    @EventHandler
    public void onPickUp(PlayerPickupItemEvent event) throws IOException {         //TODO: Auch beim aus Chest nehmen usw. abfragen
        Item item = event.getItem();
        Player player = event.getPlayer();
        if (item.getItemStack().getType().equals(current) && Timer.isRunning()) {
            sizeProgress += 1;
            for (Player all : Bukkit.getOnlinePlayers()) {
                if (!(sizeProgress == size)) {
                    all.sendMessage(ChatColor.GRAY + "Folgendes Item wurde registriert: " + ChatColor.YELLOW.toString() + ChatColor.BOLD + currentMessage(String.valueOf(current))
                            + ChatColor.RESET + ChatColor.GRAY + ". Neues Item: " + ChatColor.GREEN.toString() + ChatColor.BOLD + currentMessage(materials.get(1)) + ChatColor.RESET
                            + ChatColor.DARK_GRAY + " (" + ChatColor.GRAY + "Es fehlen noch " + ChatColor.WHITE.toString() + ChatColor.BOLD + (size - sizeProgress) + ChatColor.RESET +
                            ChatColor.GRAY + " Items" + ChatColor.DARK_GRAY + ")");
                    all.sendTitle("#" + sizeProgress, "", 10, 17, 10);
                } else {            //TODO: Funktionalität überprüfen
                    all.sendMessage(ChatColor.GRAY + "Folgendes Item wurde registriert: " + ChatColor.YELLOW.toString() + ChatColor.BOLD + currentMessage(String.valueOf(current))
                            + ChatColor.RESET + ChatColor.GRAY + ". Du hast alle" + ChatColor.GREEN.toString() + ChatColor.BOLD
                            + " Items " + ChatColor.GRAY + "gesammelt!");
                    all.sendTitle(ChatColor.UNDERLINE + "FERTIG!", "", 10, 60, 30);
                }
            }
            updateItems(true);
        }
    }

    /*
    * /allitems command, which has the values "overview" and "skip".
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 1) {
                switch(args[0].toLowerCase()) {
                    /*
                    * An inventory opens listing the items still being searched for, the item currently being searched for, and the items already collected.
                     */
                    case "overview":
                        if (Config.getBoolean("goals.allitems").booleanValue()) {
                            if(allItemsOverviewInv.getItem(22) == null || Objects.requireNonNull(allItemsOverviewInv.getItem(22)).getType() == Material.AIR) {
                                allItemsOverviewInv.setItem(22, allItemsDefault);
                            }
                            player.openInventory(allItemsOverviewInv);
                        } else {
                            player.sendMessage(ChatColor.RED + "Die Alle Items Herausforderung ist derzeit deaktiviert!");      //TODO: Diese Message kommt nicht, instead kommt Fehler wenn Challenge aktiviert ist aber man rejoint und man "/allitems skip" eingibt
                        }
                        break;
                    /*
                     * If you can't get an item in Survival, you can use it to skip it
                     */
                    case "skip":
                        if (Config.getBoolean("goals.allitems").booleanValue()) {
                            if (Timer.isRunning()) {
                                sizeProgress += 1;
                                for (Player all : Bukkit.getOnlinePlayers()) {
                                    if (!(sizeProgress == size)) {
                                        all.sendMessage(ChatColor.GRAY + "Item geskippt. Neues Item: " + ChatColor.GREEN.toString()
                                                + ChatColor.BOLD + currentMessage(materials.get(1)) + ChatColor.RESET + ChatColor.DARK_GRAY + " ("
                                                + ChatColor.GRAY + "Es fehlen noch " + ChatColor.WHITE.toString() + ChatColor.BOLD
                                                + (size - sizeProgress) + ChatColor.RESET + ChatColor.GRAY + " Items" + ChatColor.DARK_GRAY + ")");
                                        all.sendTitle("#" + sizeProgress, "", 10, 17, 10);
                                    } else {        //TODO: Funktionalität überprüfen
                                        all.sendMessage(ChatColor.GRAY + "Item geskippt. Du hast alle" + ChatColor.GREEN.toString() + ChatColor.BOLD
                                                + " Items " + ChatColor.GRAY + "gesammelt!");
                                        all.sendTitle(ChatColor.UNDERLINE + "FERTIG!", "", 10, 60, 30);
                                    }
                                }
                                try {
                                    updateItems(true);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            } else {
                                player.sendMessage(ChatColor.RED + "Der Timer ist noch pausiert!");      //TODO: Diese Message kommt nicht, instead kommt Fehler wenn Challenge aktiviert ist aber man rejoint und man "/allitems skip" eingibt
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + "Die Alle Items Herausforderung ist derzeit deaktiviert!");      //TODO: Diese Message kommt nicht, instead kommt Fehler wenn Challenge aktiviert ist aber man rejoint und man "/allitems skip" eingibt
                        }
                        break;
                    default:
                      sendErrorUsage(player);
                }
            } else {
                sendErrorUsage(player);
            }
        }
        return false;
    }

    private void sendErrorUsage(Player player) {
        player.sendMessage(ChatColor.GREEN + "Verwendung: " + ChatColor.WHITE + ChatColor.BOLD +
                "/allitems <overview|skip>");
    }


    public static void deactivateAllItems() throws IOException {
        if (bossBar != null) {
            bossBar.removeAll();
            bossBar.setVisible(false);
            bossBar = null;
        }
        Config.set("allitems", null);
    }

    private static String currentMessage(String current) {
        String[] words = current.split("_");
        for(short i = 0; i < words.length; i++) {
            String firstLetter = words[i].substring(0, 1);
            String restOfWord = words[i].substring(1);
            words[i] = firstLetter.toUpperCase() + restOfWord.toLowerCase();
        }
        return String.join(" ", words);
    }

    public static void loadConfig() {
        ConfigurationSection section = config.getConfigurationSection("allitems");
        if(section != null) {
            materials = section.getStringList("items");
            size = section.getInt("size");
            current = Material.valueOf(section.getString("current"));
            sizeProgress = section.getInt("progress");
        }
    }
}
