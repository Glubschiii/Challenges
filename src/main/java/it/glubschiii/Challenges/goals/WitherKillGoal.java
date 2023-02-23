package it.glubschiii.Challenges.goals;

import it.glubschiii.Challenges.Main;
import it.glubschiii.Challenges.timer.Timer;
import it.glubschiii.Challenges.utils.Config;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.jetbrains.annotations.NotNull;

public class WitherKillGoal implements Listener {

    @EventHandler
    public void onWitherKill(EntityDeathEvent event) {
        @NotNull EntityType entity = event.getEntityType();
        Timer timer = Main.getInstance().getTimer();
        if (Config.contains("goals.wither") && Config.getBoolean("goals.wither").booleanValue()) {
            if (Timer.isRunning()) {
                if (entity == EntityType.WITHER) {
                    Player player = event.getEntity().getKiller();
                    if (player != null) {
                        ElderGuardianKillGoal.challengeCompleted(timer);
                    }
                }
            }
        }
    }
}
