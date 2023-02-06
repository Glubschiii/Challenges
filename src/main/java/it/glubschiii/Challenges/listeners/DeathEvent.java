package it.glubschiii.Challenges.listeners;

import it.glubschiii.Challenges.Main;
import it.glubschiii.Challenges.timer.TimeCalculator;
import it.glubschiii.Challenges.timer.Timer;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.util.function.BiConsumer;

import static it.glubschiii.Challenges.timer.Timer.getTime;

/** @author Glubschiii | https://github.com/glubschiii
 @since 1.0.4
 */
public class DeathEvent implements Listener {

    static boolean isEventRunning = false;

    @EventHandler
    public static void onDeath(PlayerDeathEvent event) {
        if(isEventRunning) {
            return;
        }
        Timer timer = Main.getInstance().getTimer();
        isEventRunning = true;
        Player player = event.getEntity();
        Bukkit.getWorlds().forEach(world -> world.setGameRule(GameRule.SHOW_DEATH_MESSAGES, false));
        if(Timer.isRunning()) {
            for (Player all : Bukkit.getOnlinePlayers()) {
                spawnUpGoingParticleCircle((JavaPlugin) Main.getInstance(), all.getLocation(), Particle.SPELL_WITCH, 17, 1.0D, 2.0D);
                if (all != player) {
                    all.setHealth(0);
                }
            }
            Bukkit.getServer().broadcastMessage(ChatColor.RED + "Die Challenge wurde gescheitert! " + ChatColor.GOLD + "#FeelsBadMan ✞");
            Bukkit.getServer().broadcastMessage(ChatColor.GRAY + "Zeit verschwendet: " + ChatColor.GREEN.toString() + ChatColor.BOLD +
                    TimeCalculator.format(getTime()/5, ""));
            Bukkit.getServer().broadcastMessage(ChatColor.GRAY + "Seed: " + ChatColor.YELLOW.toString() + ChatColor.BOLD
                    + Bukkit.getWorld("world").getSeed());
            timer.setRunning(false);
        }
        event.setDeathMessage(null);
        isEventRunning = false;
    }

    public static void spawnUpGoingParticleCircle(@Nonnull JavaPlugin plugin, @Nonnull Location location, @Nonnull Particle particle, int points, double radius, double height) {
        spawnUpGoingParticleCircle(plugin, location, points, radius, height, (world, point) -> world.spawnParticle(particle, point, 1));
    }

    private static void spawnUpGoingParticleCircle(@Nonnull JavaPlugin plugin, @Nonnull Location location, int points, double radius, double height, @Nonnull BiConsumer<World, Location> player) {
        double i = 0;
        for (double y = 0.0D; y < height; y += 0.25D, i++) {
            double Y = y;
            Bukkit.getScheduler().runTaskLaterAsynchronously((Plugin)plugin, () -> spawnParticleCircle(location.clone().add(0.0D, Y, 0.0D), points, radius, player), (long)i);
        }
    }
    private static void spawnParticleCircle(@Nonnull Location location, int points, double radius, @Nonnull BiConsumer<World, Location> player) {
        World world = location.getWorld();
        if (world == null)
            return;
        for (int i = 0; i < points; i++) {
            double angle = 6.283185307179586D * i / points;
            Location point = location.clone().add(radius * Math.sin(angle), 0.0D, radius * Math.cos(angle));
            player.accept(world, point);
        }
    }
}
