package it.glubschiii.Challenges.challenges;

import com.google.common.collect.Sets;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffectType;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.HashSet;

/**
 * @author Glubschiii | https://github.com/glubschiii
 * @since 1.0.3
 */
public class NoJumpChallenge implements Listener {

    private String prefix = ChatColor.DARK_GRAY + "[" + ChatColor.LIGHT_PURPLE + "P2YL" + ChatColor.DARK_GRAY + "] ";

    private final HashSet<@Nullable Object> prevPlayersOnGround = Sets.newHashSet();

    @EventHandler
    public void onJump(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        // TODO: Auf vines stirbt man usw
        // TODO: Im Wasser, Lava stirbt man oft random
        // TODO: Wenn man angegriffen wird, stirbt man auch mit dem Jump grund
        if (player.getVelocity().getY() > 0) {
            double jumpVelocity = (double) 0.42F;
            if (player.hasPotionEffect(PotionEffectType.JUMP)) {
                jumpVelocity += (double) ((float) (player.getPotionEffect(PotionEffectType.JUMP).getAmplifier() + 1) * 0.1F);
            }
            if (event.getPlayer().getLocation().getBlock().getType() != Material.LADDER && event.getPlayer().getLocation().getBlock().getType() != Material.VINE &&
                    prevPlayersOnGround.contains(player.getUniqueId())) {
                if (!player.isOnGround() && Double.compare(player.getVelocity().getY(), jumpVelocity) == 0) {
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        all.sendMessage(ChatColor.DARK_GRAY + "» " + ChatColor.GOLD.toString() + ChatColor.BOLD + player.getDisplayName() + " " +
                                ChatColor.RESET + "" + ChatColor.GRAY + "ist gesprungen.");
                    }
                    player.setHealth(0);
                }
            }
        }
        if (player.isOnGround()) {
            prevPlayersOnGround.add(player.getUniqueId());
        } else {
            prevPlayersOnGround.remove(player.getUniqueId());
        }
    }
}