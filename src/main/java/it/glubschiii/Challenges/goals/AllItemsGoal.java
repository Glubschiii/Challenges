package it.glubschiii.Challenges.goals;

import it.glubschiii.Challenges.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;

//TODO: Progress in config speichern!!
//TODO: Namen von Items _ raus machen und nur ersten Buchstaben Uppercase machen
//TODO: Beim joinen Bossbar anzeigen
//TODO: Beim aktivieren/deaktivieren der Challenge wird obv. immer neue ArrayList gemacht und Progress wird nicht gesaved dann (soll aber schon)

/** @author Glubschiii | https://github.com/glubschiii
 @since 1.0.8
 */

public class AllItemsGoal implements Listener, CommandExecutor {
    private static BossBar bossBar;
    private static Material current;
    private static ArrayList<String> materials;
    private static int size;
    private static int sizeProgress = 0;


    public static void allItems() {
        materials = new ArrayList<String>();
        for (Material material : Material.values()) {
            materials.add(String.valueOf(material));
        }
        size = materials.size();
        Collections.shuffle(materials);
    }
    public static void updateItems(boolean delete) {
        if (delete) {
            for (Player all : Bukkit.getOnlinePlayers()) {
                bossBar.removePlayer(all);
            }
            materials.remove(0);
        }
        current = Material.valueOf(materials.get(0));
        if(materials.size() != 0) {                     //TODO: java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
            bossBar = Bukkit.createBossBar(ChatColor.GRAY + "Item" + ChatColor.DARK_GRAY + " » " + ChatColor.WHITE + current + " #"
                    + sizeProgress + "/" + size, BarColor.WHITE, BarStyle.SOLID);
        } else {
            bossBar = Bukkit.createBossBar("Es wurden alle Items gesammelt!", BarColor.WHITE, BarStyle.SOLID);               //TODO: Wird nicht ausgeführt / TODO: New Design
        }
        for(Player all : Bukkit.getOnlinePlayers()) {
            bossBar.addPlayer(all);
        }
    }

    @EventHandler
    public void onPickUp(PlayerPickupItemEvent event) {         //TODO: Auch beim aus Chest nehmen usw. abfragen
        Item item = event.getItem();
        Player player = event.getPlayer();
        if(item.getItemStack().getType().equals(current)) {
            sizeProgress += 1;
            for(Player all : Bukkit.getOnlinePlayers()) {
                if(!(sizeProgress == size)) {
                    all.sendMessage(ChatColor.GRAY + "Folgendes Item wurde registriert: " + ChatColor.YELLOW.toString() + ChatColor.BOLD + current + ChatColor.RESET +
                            ChatColor.GRAY + ". Neues Item: " + ChatColor.GREEN.toString() + ChatColor.BOLD + materials.get(1) + ChatColor.RESET + ChatColor.DARK_GRAY + " (" +
                            ChatColor.GRAY + "Es fehlen noch " + ChatColor.WHITE.toString() + ChatColor.BOLD + (size - sizeProgress) + ChatColor.RESET +
                            ChatColor.GRAY + " Items" + ChatColor.DARK_GRAY + ")");
                    all.sendTitle("#" + sizeProgress, "", 10, 17, 10);
                } else {            //TODO: Funktionalität überprüfen
                    all.sendMessage(ChatColor.GRAY + "Folgendes Item wurde registriert: " + ChatColor.YELLOW.toString() + ChatColor.BOLD + current + ChatColor.RESET +
                            ChatColor.GRAY + ". Du hast alle" + ChatColor.GREEN.toString() + ChatColor.BOLD
                            + " Items " + ChatColor.GRAY + "gesammelt!");
                    all.sendTitle(ChatColor.UNDERLINE + "FERTIG!", "", 10, 60, 30);
                }
            }
            updateItems(true);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(args.length == 0) {
                if(Config.getBoolean("goals.allitems").booleanValue()) {
                    sizeProgress += 1;
                    for(Player all : Bukkit.getOnlinePlayers()) {
                        if(!(sizeProgress == size)) {
                            all.sendMessage(ChatColor.GRAY + "Item geskippt. Neues Item: " + ChatColor.GREEN.toString()
                                    + ChatColor.BOLD + materials.get(1) + ChatColor.RESET + ChatColor.DARK_GRAY + " ("
                                    + ChatColor.GRAY + "Es fehlen noch " + ChatColor.WHITE.toString() + ChatColor.BOLD
                                    + (size - sizeProgress) + ChatColor.RESET + ChatColor.GRAY + " Items" + ChatColor.DARK_GRAY + ")");
                            all.sendTitle("#" + sizeProgress, "", 10, 17, 10);
                        } else {        //TODO: Funktionalität überprüfen
                            all.sendMessage(ChatColor.GRAY + "Item geskippt. Du hast alle" + ChatColor.GREEN.toString() + ChatColor.BOLD
                                    + " Items " + ChatColor.GRAY + "gesammelt!");
                            all.sendTitle(ChatColor.UNDERLINE + "FERTIG!", "", 10, 60, 30);
                        }
                    }
                    updateItems(true);
                } else {
                    player.sendMessage(ChatColor.RED + "Die Alle Items Herausforderung ist derzeit deaktiviert!");
                }
            } else {
                player.sendMessage(ChatColor.RED + "Verwendung: /skipitem");
            }
        }
        return false;
    }


    public static void deactivateAllItems() {
        if(bossBar != null) {
            bossBar.removeAll();
            bossBar.setVisible(false);
            bossBar = null;
        }
    }

}
