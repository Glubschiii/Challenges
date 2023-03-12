package it.glubschiii.Challenges.listeners;

import it.glubschiii.Challenges.Main;
import it.glubschiii.Challenges.timer.Timer;
import it.glubschiii.Challenges.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.IOException;

import static it.glubschiii.Challenges.goals.AllItemsGoal.bossBar;

/** @author Glubschiii | https://github.com/glubschiii
 @since 1.0.1
 */
public class QuitEvent implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) throws IOException {
        Player player = event.getPlayer();

        if(player.hasPermission("challenges.*")) {
            event.setQuitMessage(ChatColor.RED + "« " + ChatColor.DARK_RED + "" + player.getDisplayName());
        } else {
            event.setQuitMessage(ChatColor.RED + "« " + ChatColor.GRAY + "" + player.getDisplayName());
        }

        Timer timer = Main.getInstance().getTimer();
        Config.set("timer", Timer.getTime()/5);

        if(Bukkit.getOnlinePlayers().size() <=1 && timer.isRunning()) {
                timer.setRunning(false);
        }

        if (Config.getBoolean("goals.allitems").booleanValue()) {
            bossBar.removePlayer(player);
        }
    }
}