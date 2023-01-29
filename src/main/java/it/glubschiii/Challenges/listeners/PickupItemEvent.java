package it.glubschiii.Challenges.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

/** @author Glubschiii | https://github.com/glubschiii
 @since 1.0.4
 */
public class PickupItemEvent implements Listener {

    @EventHandler
    public void onPickup(EntityPickupItemEvent event) {

        if(event.getEntityType() == EntityType.PLAYER) {
            for(Player all : Bukkit.getOnlinePlayers()) {
                // TODO: Auch noch machen, wenn Spieler etwas aus einer Chest rausnimmt
                all.sendMessage(ChatColor.DARK_GRAY + "» " + ChatColor.GOLD.toString() + ChatColor.BOLD + event.getEntity().getName() + " " +
                        ChatColor.RESET + "" + ChatColor.GRAY + "hat " + ChatColor.GOLD.toString() + ChatColor.BOLD +
                        event.getItem().getItemStack().getType() + " x" + event.getItem().getItemStack().getAmount()
                        + ChatColor.RESET + ChatColor.GRAY + " aufgesammelt.");
            }
        }
    }
}
