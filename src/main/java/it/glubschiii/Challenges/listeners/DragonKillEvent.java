package it.glubschiii.Challenges.listeners;

import it.glubschiii.Challenges.Main;
import it.glubschiii.Challenges.timer.Timer;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.jetbrains.annotations.NotNull;

/** @author Glubschiii | https://github.com/glubschiii
 @since 1.0.5
 */
public class DragonKillEvent implements Listener {

    public void onDragonKill(EntityDeathEvent event) {
        @NotNull EntityType entity = event.getEntityType();
        Timer timer = Main.getInstance().getTimer();
        if(Timer.isRunning()) {
            if(entity == EntityType.ENDER_DRAGON) {
                Player player = event.getEntity().getKiller();
                if(player != null) {
                    World world = event.getEntity().getWorld();
                    if(world.getEnvironment() == World.Environment.THE_END) {
                        timer.setRunning(false);
                        //TODO: Challenge geschafft Methode machen & einfügen
                    }
                }
            }
        }
    }

}
