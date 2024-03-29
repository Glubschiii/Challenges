package it.glubschiii.Challenges.timer;

import com.destroystokyo.paper.event.player.PlayerAdvancementCriterionGrantEvent;
import io.papermc.paper.event.entity.EntityMoveEvent;
import it.glubschiii.Challenges.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import static it.glubschiii.Challenges.utils.MainInventoryManager.*;

/**
 * @author Glubschiii | https://github.com/glubschiii
 * @since 1.0.5
 */
public class PreTimer implements Listener {

    //TODO: Nur innerhalb 50 Blöcke gehen können

    @EventHandler
    private void onPlayerInteract(PlayerInteractEvent event) {
        if (!Timer.isRunning()) {
            event.setCancelled(true);
        }
        Player player = event.getPlayer();
        if(!Config.getBoolean("challenges.started").booleanValue()) {
            if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if(player.getItemInHand().getType().equals(Material.ENDER_CHEST)) {
                    player.openInventory(playerSettingsInv);        //TODO: Animation hinzufügen, zumindest erstmal bei dem Inv (maybe)
                } else if(player.getItemInHand().getType().equals(Material.BOOK) && player.hasPermission("challenges.*")) {
                    player.openInventory(settingsInv);
                } else if(player.getItemInHand().getType().equals(Material.CLOCK) && player.hasPermission("challenges.*")) {
                    player.openInventory(timerInv);
                }
            }
        }
    }

    @EventHandler
    private void onPickUp(PlayerPickupItemEvent event) {
        if (!Timer.isRunning()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onDrop(PlayerDropItemEvent event) {
        if (!Timer.isRunning()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onAttack(EntityDamageEvent event) {
        if (!Timer.isRunning()) {                    //TODO: Besser machen
            event.setCancelled(true);
        }
    }

    /*
    * Prevent that other entities except of players can't move if the timer isn't running
     */
    @EventHandler
    private void onMove(EntityMoveEvent event) {
        EntityType entity = event.getEntity().getType();
        if (!Timer.isRunning()) {
            if (entity != EntityType.PLAYER) {              //TODO: Besser machen
                event.setCancelled(true);
            }
        }
    }

    /*
    * Player's should only be able to walk within 50 blocks if the timer isn't running
     */
    @EventHandler
    private void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        player.getLocation();
        Location locationStart = event.getFrom();
        Location locationEnd = event.getTo();

        //TODO: Change the radius where players can move within
    }

    @EventHandler
    private void onHeal(EntityRegainHealthEvent event) {
        if (!Timer.isRunning()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onFoodLevelChange(FoodLevelChangeEvent event) {
        if (!Timer.isRunning()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onWeatherChange(WeatherChangeEvent event) {
        if (!Timer.isRunning()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onPlayerAdvancement(PlayerAdvancementCriterionGrantEvent event) {
        if (!Timer.isRunning()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onCrafting(CraftItemEvent event) {
        if (!Timer.isRunning()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onRegen(EntityRegainHealthEvent event) {
        if (!Timer.isRunning()) {
            event.setCancelled(true);           //TODO: geht nicht
        }
    }

    @EventHandler
    private void onInvClick(InventoryClickEvent event) {
        Inventory inventory = event.getClickedInventory();
        if(!Timer.isRunning()) {
            if(inventory != null) {
                if (inventory.getType() == InventoryType.PLAYER || event.getClickedInventory().getType() == InventoryType.CHEST) {
                    event.setCancelled(true);
                }
            }
        }
    }

    /*
    * Disable time changing if the timer isn't running
     */
    @EventHandler
    private void onJoin(PlayerJoinEvent event) {
        if(Timer.isRunning()) {
            Bukkit.getWorlds().forEach(world -> world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true));
        } else {
            Bukkit.getWorlds().forEach(world -> world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false));
        }
    }
}
