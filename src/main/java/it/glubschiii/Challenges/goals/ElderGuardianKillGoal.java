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
 @since 1.0.6
 */
public class ElderGuardianKillGoal implements Listener {

    @EventHandler
    public void onElderGuardianKill(EntityDeathEvent event) {
        @NotNull EntityType entity = event.getEntityType();
        Timer timer = Main.getInstance().getTimer();
        if (Config.contains("goals.elderguardian") && Config.getBoolean("goals.elderguardian").booleanValue()) {
            if (Timer.isRunning()) {
                if (entity == EntityType.ELDER_GUARDIAN) {
                    Player player = event.getEntity().getKiller();
                    if (player != null) {
                        World world = event.getEntity().getWorld();
                        if (world.getEnvironment() == World.Environment.NORMAL) {
                            challengeCompleted(timer);
                        }
                    }
                }
            }
        }
    }

    static void challengeCompleted(Timer timer) {
        Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "Die Challenge wurde geschafft!");
        Bukkit.getServer().broadcastMessage(ChatColor.GRAY + "Absolviert in folgender Zeit: " + ChatColor.GREEN.toString() + ChatColor.BOLD +
                TimeCalculator.format(getTime() / 5, ""));
        Bukkit.getServer().broadcastMessage(ChatColor.GRAY + "Seed: " + ChatColor.YELLOW.toString() + ChatColor.BOLD
                + Bukkit.getWorld("world").getSeed());
        timer.setRunning(false);
    }
}
