package it.glubschiii.Challenges.gamerules;

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
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

public class DifficultyGamerule implements Listener {

    public String prefix = ChatColor.DARK_GRAY + "[" + ChatColor.LIGHT_PURPLE + "P2YL" + ChatColor.DARK_GRAY + "] ";

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if(event.getView().getTitle().equalsIgnoreCase(ChatColor.GREEN + "Spielregeln" + ChatColor.DARK_GRAY + " • " +
                ChatColor.BLUE + "Seite 1")) {
            if(event.getCurrentItem().getType().equals("Material.SHIELD")) {
                if (event.isLeftClick()) {
                    if (Objects.requireNonNull(Bukkit.getWorld("world")).getDifficulty() == Difficulty.PEACEFUL) {
                        Bukkit.getWorlds().forEach(world -> world.setDifficulty(Difficulty.EASY));
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.sendMessage(prefix + ChatColor.GRAY + " Der Schwierigkeitsgrad wurde auf " + ChatColor.GOLD.toString() +
                                    ChatColor.BOLD + "Einfach " + ChatColor.RESET + ChatColor.GRAY + "geändert.");
                            InventoryView iview = all.getOpenInventory();
                            if (iview.getTitle().equalsIgnoreCase(ChatColor.GREEN + "Spielregeln" + ChatColor.DARK_GRAY + " • " +
                                    ChatColor.BLUE + "Seite 1")) {
                                Inventory contents = iview.getTopInventory();
                                ItemStack difficultydye = new ItemStack(Material.GREEN_DYE);
                                ItemMeta difficultydyeMeta = difficultydye.getItemMeta();
                                difficultydyeMeta.setDisplayName(ChatColor.GREEN + "Einfach");
                                difficultydye.setItemMeta(difficultydyeMeta);
                                contents.setItem(19, difficultydye);
                            }
                        }
                    } else if (Bukkit.getWorld("world").getDifficulty() == Difficulty.EASY) {
                        Bukkit.getWorlds().forEach(world -> world.setDifficulty(Difficulty.NORMAL));
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.sendMessage(prefix + ChatColor.GRAY + " Der Schwierigkeitsgrad wurde auf " + ChatColor.GOLD.toString() +
                                    ChatColor.BOLD + "Normal " + ChatColor.RESET + ChatColor.GRAY + "geändert.");
                            InventoryView iview = all.getOpenInventory();
                            if (iview.getTitle().equalsIgnoreCase(ChatColor.GREEN + "Spielregeln" + ChatColor.DARK_GRAY + " • " +
                                    ChatColor.BLUE + "Seite 1")) {
                                Inventory contents = iview.getTopInventory();
                                ItemStack difficultydye = new ItemStack(Material.ORANGE_DYE);
                                ItemMeta difficultydyeMeta = difficultydye.getItemMeta();
                                difficultydyeMeta.setDisplayName(ChatColor.GOLD + "Normal");
                                difficultydye.setItemMeta(difficultydyeMeta);
                                contents.setItem(19, difficultydye);
                            }
                        }
                    } else if (Bukkit.getWorld("world").getDifficulty() == Difficulty.NORMAL) {
                        Bukkit.getWorlds().forEach(world -> world.setDifficulty(Difficulty.HARD));
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.sendMessage(prefix + ChatColor.GRAY + " Der Schwierigkeitsgrad wurde auf " + ChatColor.GOLD.toString() +
                                    ChatColor.BOLD + "Schwer " + ChatColor.RESET + ChatColor.GRAY + "geändert.");
                            InventoryView iview = all.getOpenInventory();
                            if (iview.getTitle().equalsIgnoreCase(ChatColor.GREEN + "Spielregeln" + ChatColor.DARK_GRAY + " • " +
                                    ChatColor.BLUE + "Seite 1")) {
                                Inventory contents = iview.getTopInventory();
                                ItemStack difficultydye = new ItemStack(Material.RED_DYE);
                                ItemMeta difficultydyeMeta = difficultydye.getItemMeta();
                                difficultydyeMeta.setDisplayName(ChatColor.RED + "Schwer");
                                difficultydye.setItemMeta(difficultydyeMeta);
                                contents.setItem(19, difficultydye);
                            }
                        }
                    }
                } else if (event.isRightClick()) {
                    if (Bukkit.getWorld("world").getDifficulty() == Difficulty.HARD) {
                        Bukkit.getWorlds().forEach(world -> world.setDifficulty(Difficulty.NORMAL));
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.sendMessage(prefix + ChatColor.GRAY + " Der Schwierigkeitsgrad wurde auf " + ChatColor.GOLD.toString() +
                                    ChatColor.BOLD + "Normal " + ChatColor.RESET + ChatColor.GRAY + "geändert.");
                            InventoryView iview = all.getOpenInventory();
                            if (iview.getTitle().equalsIgnoreCase(ChatColor.GREEN + "Spielregeln" + ChatColor.DARK_GRAY + " • " +
                                    ChatColor.BLUE + "Seite 1")) {
                                Inventory contents = iview.getTopInventory();
                                ItemStack difficultydye = new ItemStack(Material.ORANGE_DYE);
                                ItemMeta difficultydyeMeta = difficultydye.getItemMeta();
                                difficultydyeMeta.setDisplayName(ChatColor.GOLD + "Normal");
                                difficultydye.setItemMeta(difficultydyeMeta);
                                contents.setItem(19, difficultydye);
                            }
                        }
                    } else if (Objects.requireNonNull(Bukkit.getWorld("world")).getDifficulty() == Difficulty.NORMAL) {
                        Bukkit.getWorlds().forEach(world -> world.setDifficulty(Difficulty.EASY));
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.sendMessage(prefix + ChatColor.GRAY + " Der Schwierigkeitsgrad wurde auf " + ChatColor.GOLD.toString() +
                                    ChatColor.BOLD + "Einfach " + ChatColor.RESET + ChatColor.GRAY + "geändert.");
                            InventoryView iview = all.getOpenInventory();
                            if (iview.getTitle().equalsIgnoreCase(ChatColor.GREEN + "Spielregeln" + ChatColor.DARK_GRAY + " • " +
                                    ChatColor.BLUE + "Seite 1")) {
                                Inventory contents = iview.getTopInventory();
                                ItemStack difficultydye = new ItemStack(Material.GREEN_DYE);
                                ItemMeta difficultydyeMeta = difficultydye.getItemMeta();
                                difficultydyeMeta.setDisplayName(ChatColor.GREEN + "Einfach");
                                difficultydye.setItemMeta(difficultydyeMeta);
                                contents.setItem(19, difficultydye);
                            }
                        }
                    } else if (Objects.requireNonNull(Bukkit.getWorld("world")).getDifficulty() == Difficulty.EASY) {
                        Bukkit.getWorlds().forEach(world -> world.setDifficulty(Difficulty.PEACEFUL));
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.sendMessage(prefix + ChatColor.GRAY + " Der Schwierigkeitsgrad wurde auf " + ChatColor.GOLD.toString() +
                                    ChatColor.BOLD + "Friedlich " + ChatColor.RESET + ChatColor.GRAY + "geändert.");
                            InventoryView iview = all.getOpenInventory();
                            if (iview.getTitle().equalsIgnoreCase(ChatColor.GREEN + "Spielregeln" + ChatColor.DARK_GRAY + " • " +
                                    ChatColor.BLUE + "Seite 1")) {
                                Inventory contents = iview.getTopInventory();
                                ItemStack difficultydye = new ItemStack(Material.WHITE_DYE);
                                ItemMeta difficultydyeMeta = difficultydye.getItemMeta();
                                difficultydyeMeta.setDisplayName(ChatColor.WHITE + "Friedlich");
                                difficultydye.setItemMeta(difficultydyeMeta);
                                contents.setItem(19, difficultydye);
                            }
                        }
                    }
                }
            }
        }
    }
}
