package it.glubschiii.Challenges.listeners;

import it.glubschiii.Challenges.Main;
import it.glubschiii.Challenges.utils.Config;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;
import java.net.MalformedURLException;

import static it.glubschiii.Challenges.commands.InfoCommand.runInfoCommand;
import static it.glubschiii.Challenges.goals.AllItemsGoal.bossBar;

/** @author Glubschiii | https://github.com/glubschiii
 @since 1.0.1
 */
public class JoinEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) throws IOException {
        Player player = event.getPlayer();
        runInfoCommand(player);

        if(player.hasPermission("challenges.*")) {
            event.setJoinMessage(ChatColor.GREEN + "» " + ChatColor.DARK_RED + "" + player.getDisplayName());
        } else {
            event.setJoinMessage(ChatColor.GREEN + "» " + ChatColor.GRAY + "" + player.getDisplayName());
        }

        Main.getInstance().getTablistManager().setTablist(player);
        Main.getInstance().getTablistManager().setAllPlayerTeams();


        if (Config.getBoolean("goals.allitems").booleanValue()) {
            bossBar.addPlayer(player);
        }

        if(!Config.getBoolean("challenge.started").booleanValue()) {
            player.getInventory().clear();

            giveItems(player, Material.ENDER_CHEST, ChatColor.LIGHT_PURPLE + "Cosmetics", (byte) 4);

            if(player.hasPermission("challenges.*")) {
                giveItems(player, Material.BOOK, ChatColor.GOLD + "Challenges", (byte) 2);
                giveItems(player, Material.CLOCK, ChatColor.YELLOW + "Timer", (byte) 6);
            }
        }

        if(player.getGameMode() != GameMode.SURVIVAL) {
            player.setGameMode(GameMode.SURVIVAL);
        }
    }

    private void giveItems(Player player, Material material, String type, byte slot) {
        ItemStack configuration = new ItemStack(material);
        ItemMeta configurationMeta = configuration.getItemMeta();
        configurationMeta.setDisplayName(ChatColor.GRAY + "〣 " + type + ChatColor.GRAY + " (Rechtsklick)");
        configuration.setItemMeta(configurationMeta);
        player.getInventory().setItem(slot, configuration);
    }
}
