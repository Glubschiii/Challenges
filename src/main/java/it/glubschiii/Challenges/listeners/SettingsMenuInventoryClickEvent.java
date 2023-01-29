package it.glubschiii.Challenges.listeners;

import it.glubschiii.Challenges.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Difficulty;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.io.IOException;
import java.util.ArrayList;

/** @author Glubschiii | https://github.com/glubschiii
 @since 1.0.4
 */
public class SettingsMenuInventoryClickEvent implements Listener {

    public static int status = Main.getInstance().getConfig().getInt("regeneration-status");
    private final int max_status = 2;
    private final int min_status = 0;

    public void setStatus() {
        if(status < max_status) {
            status++;
        }
    }

    public void minStatus() {
        if(status > min_status) {
            status--;
        }
    }

    public static int getStatus() {
        return status;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) throws IOException {
        Player player = (Player) event.getWhoClicked();
        if (event.getView().getTitle().equalsIgnoreCase(ChatColor.GOLD + "Settings" + ChatColor.DARK_GRAY + " • " +
                ChatColor.BLUE + "Übersicht")) {
            if (event.getCurrentItem() == null) {
                return;
            } else if (event.getCurrentItem().getType().equals(Material.GRAY_STAINED_GLASS_PANE)) {                // ODER: ((event.getSlot() == 0) {
                event.setCancelled(true);
            } else if (event.getCurrentItem().getType().equals(Material.MAP)) {
                if (event.isLeftClick()) {
                    Inventory spielregelnmenu = Bukkit.createInventory(null, 36, ChatColor.GREEN + "Spielregeln" + ChatColor.DARK_GRAY + " • " +
                            ChatColor.BLUE + "Seite 1");
                    ItemStack background = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
                    ItemStack difficulty = new ItemStack(Material.SHIELD);
                    ItemStack regeneration = new ItemStack(Material.POTION);
                    if (Bukkit.getWorld("world").getDifficulty() == Difficulty.PEACEFUL) {
                        ItemStack difficultydye = new ItemStack(Material.WHITE_DYE);
                        ItemMeta difficultydyeMeta = difficultydye.getItemMeta();
                        difficultydyeMeta.setDisplayName(ChatColor.WHITE + "Friedlich");
                        difficultydye.setItemMeta(difficultydyeMeta);
                        spielregelnmenu.setItem(19, difficultydye);
                    }
                    if (Bukkit.getWorld("world").getDifficulty() == Difficulty.EASY) {
                        ItemStack difficultydye = new ItemStack(Material.GREEN_DYE);
                        ItemMeta difficultydyeMeta = difficultydye.getItemMeta();
                        difficultydyeMeta.setDisplayName(ChatColor.GREEN + "Einfach");
                        difficultydye.setItemMeta(difficultydyeMeta);
                        spielregelnmenu.setItem(19, difficultydye);
                    }
                    if (Bukkit.getWorld("world").getDifficulty() == Difficulty.NORMAL) {
                        ItemStack difficultydye = new ItemStack(Material.ORANGE_DYE);
                        ItemMeta difficultydyeMeta = difficultydye.getItemMeta();
                        difficultydyeMeta.setDisplayName(ChatColor.GOLD + "Normal");
                        difficultydye.setItemMeta(difficultydyeMeta);
                        spielregelnmenu.setItem(19, difficultydye);
                    }
                    if (Bukkit.getWorld("world").getDifficulty() == Difficulty.HARD) {
                        ItemStack difficultydye = new ItemStack(Material.RED_DYE);
                        ItemMeta difficultydyeMeta = difficultydye.getItemMeta();
                        difficultydyeMeta.setDisplayName(ChatColor.RED + "Schwer");
                        difficultydye.setItemMeta(difficultydyeMeta);
                        spielregelnmenu.setItem(19, difficultydye);
                    }
                    if(getStatus() == 0) {
                        ItemStack regdye = new ItemStack(Material.GREEN_DYE);
                        ItemMeta regdyeMeta = regdye.getItemMeta();
                        regdyeMeta.setDisplayName(ChatColor.GREEN + "Normal");
                        regdye.setItemMeta(regdyeMeta);
                        spielregelnmenu.setItem(20, regdye);
                    }
                    if(getStatus() == 1) {
                        ItemStack regdye = new ItemStack(Material.ORANGE_DYE);
                        ItemMeta regdyeMeta = regdye.getItemMeta();
                        regdyeMeta.setDisplayName(ChatColor.GOLD + "UHC");
                        regdye.setItemMeta(regdyeMeta);
                        spielregelnmenu.setItem(20, regdye);
                    }
                    if(getStatus() == 2) {
                        ItemStack regdye = new ItemStack(Material.RED_DYE);
                        ItemMeta regdyeMeta = regdye.getItemMeta();
                        regdyeMeta.setDisplayName(ChatColor.RED + "UUHC");
                        regdye.setItemMeta(regdyeMeta);
                        spielregelnmenu.setItem(20, regdye);
                    }
                    ItemMeta backgroundMeta = background.getItemMeta();
                    ItemMeta difficultyMeta = difficulty.getItemMeta();
                    ItemMeta regenerationMeta = regeneration.getItemMeta();

                    PotionMeta reg = (PotionMeta) regeneration.getItemMeta();
                    reg.setBasePotionData(new PotionData(PotionType.INSTANT_HEAL));
                    regeneration.setItemMeta(reg);
                    regenerationMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);

                    backgroundMeta.setDisplayName(" ");
                    difficultyMeta.setDisplayName(ChatColor.GREEN + "Schwierigkeitsgrad");
                    regenerationMeta.setDisplayName(ChatColor.RED + "Regeneration");
                    ArrayList<String> difficultylore = new ArrayList<>();
                    ArrayList<String> regenerationlore = new ArrayList<>();
                    difficultylore.add(" ");
                    difficultylore.add(ChatColor.GRAY + " Stellt die " + ChatColor.GREEN + "Schwierigkeitsstufe " + ChatColor.GRAY + "ein");
                    difficultylore.add(" ");
                    difficultylore.add(ChatColor.GREEN + " Erhöhen:");
                    difficultylore.add(ChatColor.DARK_GRAY + "- " + ChatColor.BLUE + "Linksklick");
                    difficultylore.add(ChatColor.GREEN + " Verringern:");
                    difficultylore.add(ChatColor.DARK_GRAY + "- " + ChatColor.BLUE + "Rechtsklick");
                    difficultyMeta.setLore(difficultylore);
                    regenerationlore.add(" ");
                    regenerationlore.add(ChatColor.GRAY + " Stellt die " + ChatColor.RED + "Regeneration " + ChatColor.GRAY + "ein");
                    regenerationlore.add(" ");
                    regenerationlore.add(ChatColor.RED + " Erhöhen:");
                    regenerationlore.add(ChatColor.DARK_GRAY + "- " + ChatColor.BLUE + "Linksklick");
                    regenerationlore.add(ChatColor.RED + " Verringern:");
                    regenerationlore.add(ChatColor.DARK_GRAY + "- " + ChatColor.BLUE + "Rechtsklick");
                    regenerationMeta.setLore(regenerationlore);

                    background.setItemMeta(backgroundMeta);
                    difficulty.setItemMeta(difficultyMeta);
                    regeneration.setItemMeta(regenerationMeta);
                    for (int b = 0; b <= 9; b++) {
                        spielregelnmenu.setItem(b, background);
                    }
                    for (int ba = 12; ba <= 18; ba++) {
                        spielregelnmenu.setItem(ba, background);
                    }
                    for (int bac = 21; bac <= 35; bac++) {
                        spielregelnmenu.setItem(bac, background);
                    }
                    spielregelnmenu.setItem(10, difficulty);
                    spielregelnmenu.setItem(11, regeneration);
                    player.openInventory(spielregelnmenu);
                }
            }
        }
    }
}