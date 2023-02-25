package it.glubschiii.Challenges.listeners;

import it.glubschiii.Challenges.Main;
import it.glubschiii.Challenges.utils.Config;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static it.glubschiii.Challenges.goals.AllItemsGoal.bossBar;

/** @author Glubschiii | https://github.com/glubschiii
 @since 1.0.1
 */
public class JoinEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        event.setJoinMessage(ChatColor.GREEN + "» " + ChatColor.GRAY + "" + player.getDisplayName());

        Main.getInstance().getTablistManager().setTablist(player);

        if (Config.getBoolean("goals.allitems").booleanValue()) {
            bossBar.addPlayer(player);
        }
    }
}
