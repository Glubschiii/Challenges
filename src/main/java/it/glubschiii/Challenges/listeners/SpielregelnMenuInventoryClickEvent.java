package it.glubschiii.Challenges.listeners;

import it.glubschiii.Challenges.utils.Config;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;
import java.util.EnumSet;
import java.util.Objects;

/** @author Glubschiii | https://github.com/glubschiii
 @since 1.0.4
 */
public class SpielregelnMenuInventoryClickEvent extends SettingsMenuInventoryClickEvent implements Listener {

    public String prefix = ChatColor.DARK_GRAY + "[" + ChatColor.LIGHT_PURPLE + "P2YL" + ChatColor.DARK_GRAY + "] ";

    //TODO: In allen Klassen für alle Welten in einer Zeile etwas ändern mit for each
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) throws IOException {
        Player player = (Player) event.getWhoClicked();
        if (event.getView().getTitle().equalsIgnoreCase(ChatColor.GREEN + "Spielregeln" + ChatColor.DARK_GRAY + " • " +
                ChatColor.BLUE + "Seite 1")) {
            if (event.getCurrentItem() == null) {
                return;
            } else if (event.getCurrentItem().getType().equals(Material.GRAY_STAINED_GLASS_PANE)) {        // ODER: ((event.getSlot() == 0) {
                event.setCancelled(true);
            } else if (event.getCurrentItem().getType().equals(Material.SHIELD)) {
                event.setCancelled(true);
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
            } else if (event.getCurrentItem().getType().equals(Material.POTION)) {
                event.setCancelled(true);
                if (event.isLeftClick()) {
                    if(getStatus() == 0 || getStatus() == 1) {
                        setStatus();
                        Config.set("regeneration-status", getStatus());
                        if (getStatus() == 1) {
                            Bukkit.getWorlds().forEach(world -> world.setGameRule(GameRule.NATURAL_REGENERATION, false));
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                all.sendMessage(prefix + ChatColor.GRAY + " Die Regeneration wurde auf " + ChatColor.GOLD.toString() +
                                        ChatColor.BOLD + "UHC " + ChatColor.RESET + ChatColor.GRAY + "geändert.");
                                InventoryView iview = all.getOpenInventory();
                                if (iview.getTitle().equalsIgnoreCase(ChatColor.GREEN + "Spielregeln" + ChatColor.DARK_GRAY + " • " +
                                        ChatColor.BLUE + "Seite 1")) {
                                    Inventory contents = iview.getTopInventory();
                                    ItemStack regdye = new ItemStack(Material.ORANGE_DYE);
                                    ItemMeta regdyeMeta = regdye.getItemMeta();
                                    regdyeMeta.setDisplayName(ChatColor.GREEN + "UHC");
                                    regdye.setItemMeta(regdyeMeta);
                                    contents.setItem(20, regdye);
                                }
                            }
                            //TODO: Bei Status 2 kann man sich nicht mit Goldäpfeln/Heiltränken heilen
                        } else if (getStatus() == 2) {
                            Bukkit.getWorlds().forEach(world -> world.setGameRule(GameRule.NATURAL_REGENERATION, false));
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                all.sendMessage(prefix + ChatColor.GRAY + " Die Regeneration wurde auf " + ChatColor.GOLD.toString() +
                                        ChatColor.BOLD + "UUHC " + ChatColor.RESET + ChatColor.GRAY + "geändert.");
                                InventoryView iview = all.getOpenInventory();
                                if (iview.getTitle().equalsIgnoreCase(ChatColor.GREEN + "Spielregeln" + ChatColor.DARK_GRAY + " • " +
                                        ChatColor.BLUE + "Seite 1")) {
                                    Inventory contents = iview.getTopInventory();
                                    ItemStack regdye = new ItemStack(Material.RED_DYE);
                                    ItemMeta regdyeMeta = regdye.getItemMeta();
                                    regdyeMeta.setDisplayName(ChatColor.GREEN + "UUHC");
                                    regdye.setItemMeta(regdyeMeta);
                                    contents.setItem(20, regdye);
                                }
                            }
                        }
                    }
                } else if(event.isRightClick()) {
                    if (getStatus() == 2 || getStatus() == 1) {
                        minStatus();
                        Config.set("regeneration-status", getStatus());
                        if (getStatus() == 1) {
                            Bukkit.getWorlds().forEach(world -> world.setGameRule(GameRule.NATURAL_REGENERATION, false));
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                all.sendMessage(prefix + ChatColor.GRAY + " Die Regeneration wurde auf " + ChatColor.GOLD.toString() +
                                        ChatColor.BOLD + "UHC " + ChatColor.RESET + ChatColor.GRAY + "geändert.");
                                InventoryView iview = all.getOpenInventory();
                                if (iview.getTitle().equalsIgnoreCase(ChatColor.GREEN + "Spielregeln" + ChatColor.DARK_GRAY + " • " +
                                        ChatColor.BLUE + "Seite 1")) {
                                    Inventory contents = iview.getTopInventory();
                                    ItemStack regdye = new ItemStack(Material.ORANGE_DYE);
                                    ItemMeta regdyeMeta = regdye.getItemMeta();
                                    regdyeMeta.setDisplayName(ChatColor.GREEN + "Status UHC");
                                    regdye.setItemMeta(regdyeMeta);
                                    contents.setItem(20, regdye);
                                }
                            }
                        } else if (getStatus() == 0) {
                            Bukkit.getWorlds().forEach(world -> world.setGameRule(GameRule.NATURAL_REGENERATION, true));
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                all.sendMessage(prefix + ChatColor.GRAY + " Die Regeneration wurde auf " + ChatColor.GOLD.toString() +
                                        ChatColor.BOLD + "Normal " + ChatColor.RESET + ChatColor.GRAY + "geändert.");
                                InventoryView iview = all.getOpenInventory();
                                if (iview.getTitle().equalsIgnoreCase(ChatColor.GREEN + "Spielregeln" + ChatColor.DARK_GRAY + " • " +
                                        ChatColor.BLUE + "Seite 1")) {
                                    Inventory contents = iview.getTopInventory();
                                    ItemStack regdye = new ItemStack(Material.GREEN_DYE);
                                    ItemMeta regdyeMeta = regdye.getItemMeta();
                                    regdyeMeta.setDisplayName(ChatColor.GREEN + "Normal");
                                    regdye.setItemMeta(regdyeMeta);
                                    contents.setItem(20, regdye);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    @EventHandler(priority = EventPriority.HIGH)
    private void onRegen(EntityRegainHealthEvent regenevent) {
        // Wenn Status 1: Dann natural regeneration
        // Wenn Status 2: Dann nur mit Goldäpfeln und Tränke regenerieren
        // Wenn Status 3: Dannn nicht mehr regenerieren
        if(getStatus() == 0) {
            //Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "gamerule naturalregeneration true");
            regenevent.setCancelled(false);
        } else if(getStatus() == 1) {
            //Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "gamerule naturalregeneration false");
            regenevent.setCancelled(true);
        } else if(getStatus() == 2 && EnumSet.of(EntityRegainHealthEvent.RegainReason.SATIATED,
                EntityRegainHealthEvent.RegainReason.REGEN, EntityRegainHealthEvent.RegainReason.MAGIC,
                EntityRegainHealthEvent.RegainReason.MAGIC_REGEN, EntityRegainHealthEvent.RegainReason.EATING)
                .contains(regenevent.getRegainReason())) {
            //Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "gamerule natural-regeneration true");
            regenevent.setAmount(0.0D);
                regenevent.setCancelled(true); // oder true?
        }
    }
    @EventHandler
    private void onConsume(PlayerItemConsumeEvent consumeEvent) {
        Player player = consumeEvent.getPlayer();
        ItemStack consumed = consumeEvent.getItem();

        if(getStatus() == 2) {
            if(consumed.getType() == Material.GOLDEN_APPLE || consumed.getType() == Material.ENCHANTED_GOLDEN_APPLE) {
                consumeEvent.setCancelled(true);
            }
        }
    }
}