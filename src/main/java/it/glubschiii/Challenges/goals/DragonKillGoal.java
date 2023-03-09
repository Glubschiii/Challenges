package it.glubschiii.Challenges.goals;

import it.glubschiii.Challenges.Main;
import it.glubschiii.Challenges.timer.TimeCalculator;
import it.glubschiii.Challenges.timer.Timer;
import it.glubschiii.Challenges.utils.Config;
import it.glubschiii.Challenges.utils.ItemBuilder;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

import static it.glubschiii.Challenges.gamerules.DifficultyGamerule.prefix;
import static it.glubschiii.Challenges.timer.Timer.getTime;

/** @author Glubschiii | https://github.com/glubschiii
 @since 1.0.5
 */
public class DragonKillGoal implements Listener {

    //TODO: Bei diesem Goal, aber auch beim ElderGuardianKillGoal, WardenKillGoal und WitherKillGoal message einbauen, dass dieser besiegt wurde

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
