package it.glubschiii.Challenges.goals;

import it.glubschiii.Challenges.Main;
import it.glubschiii.Challenges.timer.TimeCalculator;
import it.glubschiii.Challenges.timer.Timer;
import it.glubschiii.Challenges.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.jetbrains.annotations.NotNull;

import static it.glubschiii.Challenges.timer.Timer.getTime;

/** @author Glubschiii | https://github.com/glubschiii
 @since 1.0.5
 */
public class DragonKillGoal implements Listener {

    @EventHandler
    public void onDragonKill(EntityDeathEvent event) {
        @NotNull EntityType entity = event.getEntityType();
        Timer timer = Main.getInstance().getTimer();
        if (Config.contains("goals.enderdragon") && Config.getBoolean("goals.enderdragon").booleanValue()) {
            if (Timer.isRunning()) {
                if (entity == EntityType.ENDER_DRAGON) {
                    Player player = event.getEntity().getKiller();
                    if (player != null) {
                        World world = event.getEntity().getWorld();
                        if (world.getEnvironment() == World.Environment.THE_END) {
                            ElderGuardianKillGoal.challengeCompleted(timer);
                        }
                    }
                }
            }
        }
    }
}
