package it.glubschiii.Challenges.gamerules;

import it.glubschiii.Challenges.utils.Config;
import it.glubschiii.Challenges.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Difficulty;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.Objects;

/**
 * @author Glubschiii | https://github.com/glubschiii
 * @since 1.0.4
 */
public class DifficultyGamerule implements Listener {

    public static void diff(Difficulty difficulty, String diffi, Material material) throws IOException {
        Bukkit.getWorlds().forEach(world -> world.setDifficulty(difficulty));
        for (Player all : Bukkit.getOnlinePlayers()) {
            all.sendMessage(prefix + ChatColor.GRAY + " Der Schwierigkeitsgrad wurde auf " + ChatColor.GOLD.toString() +
                    ChatColor.BOLD + diffi + ChatColor.RESET + ChatColor.GRAY + " geändert.");
            InventoryView iview = all.getOpenInventory();
            if (iview.getTitle().equalsIgnoreCase(ChatColor.GREEN + "Spielregeln" + ChatColor.DARK_GRAY + " • " +
                    ChatColor.BLUE + "Seite 1")) {
                Inventory contents = iview.getTopInventory();
                ItemStack difficultydye = new ItemBuilder(material, 1).setName(diffi).toItemStack();
                contents.setItem(3, difficultydye);
            }
        }
        Config.set("difficulty", Bukkit.getWorld("world").getDifficulty().toString());
    }

    public static String prefix = ChatColor.DARK_GRAY + "[" + ChatColor.LIGHT_PURPLE + "P2YL" + ChatColor.DARK_GRAY + "] ";

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) throws IOException {
        Player player = (Player) event.getWhoClicked();
        if (event.getView().getTitle().equalsIgnoreCase(ChatColor.GREEN + "Spielregeln" + ChatColor.DARK_GRAY + " • " +
                ChatColor.BLUE + "Seite 1")) {
            if (event.getCurrentItem() == null) {
                return;
            }
            if (Objects.requireNonNull(event.getCurrentItem()).getType().equals(Material.SHIELD) || event.getSlot() == 3) {
                if (event.isLeftClick()) {
                    if (Objects.requireNonNull(Bukkit.getWorld("world")).getDifficulty() == Difficulty.PEACEFUL) {
                        diff(Difficulty.EASY, "Einfach", Material.GREEN_DYE);
                    } else if (Bukkit.getWorld("world").getDifficulty() == Difficulty.EASY) {
                        diff(Difficulty.NORMAL, "Normal", Material.ORANGE_DYE);
                    } else if (Bukkit.getWorld("world").getDifficulty() == Difficulty.NORMAL) {
                        diff(Difficulty.HARD, "Schwer", Material.RED_DYE);
                    }
                } else if (event.isRightClick()) {
                    if (Bukkit.getWorld("world").getDifficulty() == Difficulty.HARD) {
                        diff(Difficulty.NORMAL, "Normal", Material.ORANGE_DYE);
                    } else if (Bukkit.getWorld("world").getDifficulty() == Difficulty.NORMAL) {
                        diff(Difficulty.EASY, "Einfach", Material.GREEN_DYE);
                    } else if (Bukkit.getWorld("world").getDifficulty() == Difficulty.EASY) {
                        diff(Difficulty.PEACEFUL, "Friedlich", Material.WHITE_DYE);
                    }
                }
            }
        }
    }
}