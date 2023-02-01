package it.glubschiii.Challenges.gamerules;

import it.glubschiii.Challenges.Main;
import it.glubschiii.Challenges.commands.SettingsCommand;
import it.glubschiii.Challenges.listeners.SettingsMenuInventoryClickEvent;
import it.glubschiii.Challenges.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameRule;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
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

/** @author Glubschiii | https://github.com/glubschiii
 @since 1.0.4
 */

public class RegenerationGamerule extends SettingsMenuInventoryClickEvent implements Listener {

    private static int status = Main.getInstance().getConfig().getInt("regeneration-status");
    private final int max_status = 2;
    private final int min_status = 0;

    private void setStatus() {
        if(status < max_status) {
            status++;
        }
    }

    private void minStatus() {
        if(status > min_status) {
            status--;
        }
    }

    public static int getStatus() {
        return status;
    }

    public void finalStatus(boolean naturalregeneration, String regstatus, Material dye) {
        Bukkit.getWorlds().forEach(world -> world.setGameRule(GameRule.NATURAL_REGENERATION, naturalregeneration));
        for (Player all : Bukkit.getOnlinePlayers()) {
            all.sendMessage(prefix + ChatColor.GRAY + " Die Regeneration wurde auf " + ChatColor.GOLD.toString() +
                    ChatColor.BOLD + regstatus + ChatColor.RESET + ChatColor.GRAY + " geändert.");
            InventoryView iview = all.getOpenInventory();
            if (iview.getTitle().equalsIgnoreCase(ChatColor.GREEN + "Spielregeln" + ChatColor.DARK_GRAY + " • " +
                    ChatColor.BLUE + "Seite 1")) {
                Inventory contents = iview.getTopInventory();
                ItemStack regdye = new ItemStack(dye);
                ItemMeta regdyeMeta = regdye.getItemMeta();
                regdyeMeta.setDisplayName(ChatColor.GREEN + regstatus);
                regdye.setItemMeta(regdyeMeta);
                contents.setItem(20, regdye);
            }
        }
    }

    public String prefix = ChatColor.DARK_GRAY + "[" + ChatColor.LIGHT_PURPLE + "P2YL" + ChatColor.DARK_GRAY + "] ";

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) throws IOException {
        Player player = (Player) event.getWhoClicked();
        if (event.getView().getTitle().equalsIgnoreCase(ChatColor.GREEN + "Spielregeln" + ChatColor.DARK_GRAY + " • " +
                ChatColor.BLUE + "Seite 1")) {
            if (event.getCurrentItem().getType().equals(Material.POTION)) {
                if (event.isLeftClick()) {
                    if(getStatus() == 0 || getStatus() == 1) {
                        setStatus();
                        Config.set("regeneration-status", getStatus());
                        //TODO: Config überprüfen
                        if (getStatus() == 1) {
                            finalStatus(false, "UHC", Material.ORANGE_DYE);
                        } else if (getStatus() == 2) {
                            finalStatus(false, "UUHC", Material.RED_DYE);
                        }
                    }
                } else if(event.isRightClick()) {
                    if (getStatus() == 2 || getStatus() == 1) {
                        minStatus();
                        Config.set("regeneration-status", getStatus());
                        if (getStatus() == 1) {
                            finalStatus(false, "UHC", Material.ORANGE_DYE);
                        } else if (getStatus() == 0) {
                            finalStatus(true, "Normal", Material.GREEN_DYE);
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
        } else if(getStatus() == 1 && EnumSet.of(EntityRegainHealthEvent.RegainReason.EATING, EntityRegainHealthEvent.RegainReason.REGEN)
                .contains(regenevent.getRegainReason())) {
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