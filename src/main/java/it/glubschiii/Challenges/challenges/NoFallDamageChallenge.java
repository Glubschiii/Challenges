package it.glubschiii.Challenges.challenges;

import it.glubschiii.Challenges.timer.Timer;
import it.glubschiii.Challenges.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.jetbrains.annotations.NotNull;

/** @author Glubschiii | https://github.com/glubschiii
 @since 1.0.8
 */
public class NoFallDamageChallenge implements Listener {

    @EventHandler
    private void onFallDamage(EntityDamageEvent event) {
        if (Config.contains("challenges.nofalldamage") && Config.getBoolean("challenges.nofalldamage").booleanValue()) {
            if (Timer.isRunning()) {
                if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                    if (event.getEntityType() == EntityType.PLAYER) {
                        Player player = (Player) event.getEntity();
                        double finalDamage = event.getFinalDamage()/2;
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.sendMessage(ChatColor.DARK_GRAY + "» " + ChatColor.GOLD.toString() + ChatColor.BOLD + player.getDisplayName() + " " +
                                    ChatColor.RESET + "" + ChatColor.GRAY + "hat " + finalDamage + ChatColor.RED + " ❤ " + ChatColor.GRAY + "Fallschaden genommen.");
                        }
                        player.setHealth(0);
                    }
                }
            }
        }
    }
}
