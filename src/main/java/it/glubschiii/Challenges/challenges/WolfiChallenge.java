package it.glubschiii.Challenges.challenges;

import it.glubschiii.Challenges.utils.Config;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.jetbrains.annotations.NotNull;

/** @author Glubschiii | https://github.com/glubschiii
 @since 1.0.8
 */
public class WolfiChallenge implements Listener {

    //TODO: Im Multiplayer ausprobieren
    //TODO: Im Inventar in der Mitte sollen die Spieler einen Wolf Kopf bekommen, wenn sie mit diesem auf den Hund rechtsklicken, dann
    // öffnet sich ein Inventar wo sie "Wolfi" umbenennen können. Auch die Halsbandfarbe soll dort einstellbar sein. (Datenbank? Besser als Config...)

    private static Wolf wolf;
    private static Animals animals;
    private static Tameable tameable;

    public static void spawnWolfi() {
        for (Player all : Bukkit.getOnlinePlayers()) {
            Location playerLoc = all.getLocation();
            World world = playerLoc.getWorld();
            int x = (int) (playerLoc.getX()-3);
            int y = (int) (playerLoc.getY());
            int z = (int) (playerLoc.getZ()-3);
            Block block = world.getBlockAt(x, y, z);
            block.setType(Material.AIR);

            wolf = (Wolf) world.spawnEntity(playerLoc.add(-3, 0, -3), EntityType.WOLF);
            wolf.setCustomName(all.getDisplayName() + "'s Wolfi");      //TODO: Besseres Design?
            wolf.setCustomNameVisible(true);
            wolf.setCollarColor(DyeColor.PURPLE);
            animals = (Animals) wolf;
            tameable = (Tameable) animals;
            tameable.setOwner(all);
            tameable.setTamed(true);
        }
    }

    @EventHandler
    private void onWolfiDeath(EntityDeathEvent event) {
        if (event.getEntity() instanceof Wolf) {
            Wolf wolf = (Wolf) event.getEntity();
            if (Config.contains("challenges.noarmor") && Config.getBoolean("challenges.noarmor").booleanValue()) {
                if (wolf.getCustomName() != null && wolf.getCustomName().contains("Wolfi")) {
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        all.sendMessage(ChatColor.DARK_GRAY + "» " + ChatColor.GOLD.toString() + ChatColor.BOLD + wolf.getCustomName() + " " +
                                ChatColor.RESET + "" + ChatColor.GRAY + "ist gestorben.");
                    }
                    Player player = (Player) tameable.getOwner();
                    player.setHealth(0);
                }
            }
        }
    }

    public static void removeWolfi() {

        if(wolf != null) {
            wolf.setHealth(0);              //TODO: Check if it works
        }
    }
}
