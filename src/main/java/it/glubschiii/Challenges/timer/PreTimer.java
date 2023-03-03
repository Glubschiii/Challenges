package it.glubschiii.Challenges.timer;

import com.destroystokyo.paper.event.player.PlayerAdvancementCriterionGrantEvent;
import io.papermc.paper.event.entity.EntityMoveEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

/** @author Glubschiii | https://github.com/glubschiii
 @since 1.0.5
 */
public class PreTimer implements Listener {

    //TODO: Ingame-Zeit(Tag-Nacht Zyklus) soll sich nicht ändern
    //TODO: WEnn man brennt kriegt man schaden
    @EventHandler
    private void onPlayerInteract(PlayerInteractEvent event) {
        if(!Timer.isRunning()) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    private void onPickUp(PlayerPickupItemEvent event) {
        if(!Timer.isRunning()) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    private void onDrop(PlayerDropItemEvent event) {
        if(!Timer.isRunning()) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    private void onAttack(EntityDamageByEntityEvent event) {
        if(!Timer.isRunning()) {                    //TODO: Besser machen
            event.setCancelled(true);
        }
    }
    @EventHandler
    private void onMove(EntityMoveEvent event) {
        EntityType entity = event.getEntity().getType();
        if(!Timer.isRunning()) {
            if (entity != EntityType.PLAYER) {              //TODO: Besser machen
                event.setCancelled(true);
            }
        }
    }
    @EventHandler
    private void onHeal(EntityRegainHealthEvent event) {
        if(!Timer.isRunning()) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    private void onFoodLevelChange(FoodLevelChangeEvent event) {
        if(!Timer.isRunning()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onWeatherChange(WeatherChangeEvent event) {
        if(!Timer.isRunning()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onPlayerAdvancement(PlayerAdvancementCriterionGrantEvent event) {
        if(!Timer.isRunning()) {
            event.setCancelled(true);
        }
    }
}
