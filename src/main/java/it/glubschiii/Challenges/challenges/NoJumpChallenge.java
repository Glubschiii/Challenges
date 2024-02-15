package it.glubschiii.Challenges.challenges;

import com.google.common.collect.Sets;
import it.glubschiii.Challenges.Main;
import it.glubschiii.Challenges.timer.Timer;
import it.glubschiii.Challenges.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
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
            Material.WEEPING_VINES, Material.WEEPING_VINES_PLANT, Material.CAVE_VINES, Material.CAVE_VINES_PLANT, Material.SCAFFOLDING, Material.WATER, Material.LAVA,
            Material.POWDER_SNOW));

    @EventHandler
    public void onJump(PlayerMoveEvent event) {
        if (Config.contains("challenges.nojump") && Config.getBoolean("challenges.nojump").booleanValue()) {
            if (Timer.isRunning()) {
                Player player = event.getPlayer();
                // TODO: Wenn man angegriffen wird, stirbt man mit dem Jump grund
                if (player.getGameMode() != GameMode.CREATIVE || player.getGameMode() != GameMode.SPECTATOR) {
                    if (player.getVelocity().getY() > 0) {
                        double jumpVelocity = (double) 0.42F;
                        if (player.hasPotionEffect(PotionEffectType.JUMP)) {
                            jumpVelocity += (double) ((float) (player.getPotionEffect(PotionEffectType.JUMP).getAmplifier() + 1) * 0.1F);
                        }
                        if (!excludedMaterials.contains(event.getPlayer().getLocation().getBlock().getType()) && prevPlayersOnGround.contains(player.getUniqueId())) {
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
        }
    }

    @EventHandler
    public void onEntityDamageEvent(EntityDamageEvent event) {
        if (Config.contains("challenges.nojump") && Config.getBoolean("challenges.nojump").booleanValue()) {
            if (Timer.isRunning()) {
                if (event.getEntity() instanceof Player) {
                    Player player = (Player) event.getEntity();
                    if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK ||
                            event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE ||
                            event.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION ||
                            event.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
                        prevPlayersOnGround.add(player.getUniqueId());
                    }
                }
            }
        }
    }
}