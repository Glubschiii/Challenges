package it.glubschiii.Challenges.timer;

import io.papermc.paper.event.entity.EntityMoveEvent;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

/** @author Glubschiii | https://github.com/glubschiii
 @since 1.0.5
 */
public class PreTimer implements Listener {

    //TODO: Zeit soll sich nicht ändern
    //TODO: Wetter soll sich nicht ändern
    //TODO: Player's shouldn't be able to get achievements
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if(!Timer.isRunning()) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onPickUp(PlayerPickupItemEvent event) {
        if(!Timer.isRunning()) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if(!Timer.isRunning()) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event) {
        if(!Timer.isRunning()) {                    //TODO: Besser machen
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onMove(EntityMoveEvent event) {
        EntityType entity = event.getEntity().getType();
        if(!Timer.isRunning()) {
            if (entity != EntityType.PLAYER) {              //TODO: Besser machen
                event.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void onHeal(EntityRegainHealthEvent event) {
        if(!Timer.isRunning()) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        if(!Timer.isRunning()) {
            event.setCancelled(true);
        }
    }
}
