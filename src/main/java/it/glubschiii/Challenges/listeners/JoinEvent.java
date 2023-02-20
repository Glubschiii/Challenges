package it.glubschiii.Challenges.listeners;

import it.glubschiii.Challenges.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/** @author Glubschiii | https://github.com/glubschiii
 @since 1.0.1
 */
public class JoinEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        event.setJoinMessage(ChatColor.GREEN + "» " + ChatColor.GRAY + "" + player.getDisplayName());

        Main.getInstance().getTablistManager().setTablist(player);

    }
}
