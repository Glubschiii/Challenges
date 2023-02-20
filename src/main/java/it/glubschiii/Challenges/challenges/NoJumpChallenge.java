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

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Glubschiii | https://github.com/glubschiii
 * @since 1.0.3
 */
public class NoJumpChallenge implements Listener {

    private String prefix = ChatColor.DARK_GRAY + "[" + ChatColor.LIGHT_PURPLE + "P2YL" + ChatColor.DARK_GRAY + "] ";

    private final HashSet<@Nullable Object> prevPlayersOnGround = Sets.newHashSet();
    private Set<Material> excludedMaterials = new HashSet<>(Arrays.asList(Material.LADDER, Material.VINE, Material.TWISTING_VINES, Material.TWISTING_VINES_PLANT,
            Material.WEEPING_VINES, Material.WEEPING_VINES_PLANT, Material.CAVE_VINES, Material.CAVE_VINES_PLANT, Material.SCAFFOLDING, Material.WATER, Material.LAVA));

    @EventHandler
    public void onJump(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        // TODO: CHECK: Auf vines stirbt man usw
        // TODO: CHECK: Im Wasser, Lava stirbt man oft random
        // TODO: Wenn man angegriffen wird, stirbt man auch mit dem Jump grund
        if (player.getVelocity().getY() > 0) {
            double jumpVelocity = (double) 0.42F;
            if (player.hasPotionEffect(PotionEffectType.JUMP)) {
                jumpVelocity += (double) ((float) (player.getPotionEffect(PotionEffectType.JUMP).getAmplifier() + 1) * 0.1F);
            }
            if(!excludedMaterials.contains(event.getPlayer().getLocation().getBlock().getType()) && prevPlayersOnGround.contains(player.getUniqueId())) {
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