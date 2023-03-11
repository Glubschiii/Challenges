package it.glubschiii.Challenges.challenges;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import it.glubschiii.Challenges.utils.Config;
import it.glubschiii.Challenges.utils.ItemBuilder;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

import static it.glubschiii.Challenges.utils.Config.config;
import static it.glubschiii.Challenges.utils.MainInventoryManager.wolfiChallengeInv;

/**
 * @author Glubschiii | https://github.com/glubschiii
 * @since 1.0.8
 */
public class WolfiChallenge implements Listener {

    //TODO: Im Multiplayer ausprobieren

    public static Wolf wolf;
    private static Animals animals;
    private static Tameable tameable;
    private static ItemStack wolfiMenu;
    private static UUID wolfUUID;
    private static UUID ownerUUID;

    public static void spawnWolfi() throws IOException {
        for (Player all : Bukkit.getOnlinePlayers()) {
            Location playerLoc = all.getLocation();
            World world = playerLoc.getWorld();
            int x = (int) (playerLoc.getX() - 3);
            int y = (int) (playerLoc.getY());
            int z = (int) (playerLoc.getZ() - 3);
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
            wolfUUID = wolf.getUniqueId();
            ownerUUID = wolf.getOwnerUniqueId();
            Config.set("wolfi." + ownerUUID + ".owner", ownerUUID.toString());
            Config.set("wolfi." + ownerUUID + ".wolf", wolfUUID.toString());

            wolfiMenu = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta skullMeta = (SkullMeta) wolfiMenu.getItemMeta();
            skullMeta.setDisplayName(ChatColor.GOLD + "Wolfi-Menü");
            GameProfile gameProfile = new GameProfile(UUID.randomUUID(), null);
            gameProfile.getProperties().put("textures", new Property("textures", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjlkMWQzMTEzZWM0M2FjMjk2MWRkNTlmMjgxNzVmYjQ3MTg4NzNjNmM0NDhkZmNhODcyMjMxN2Q2NyJ9fX0="));
            Field profileField = null;
            try {
                profileField = skullMeta.getClass().getDeclaredField("profile");
                profileField.setAccessible(true);
                profileField.set(skullMeta, gameProfile);
            } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
            wolfiMenu.setItemMeta(skullMeta);
            all.getInventory().setItem(22, wolfiMenu);
        }
    }

    @EventHandler
    private void onWolfiInteract(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        if (event.getRightClicked() instanceof Wolf && player.getItemInHand().getType().equals(Material.PLAYER_HEAD)) {
            player.openInventory(wolfiChallengeInv);            //TODO: Man kann auf jeden Wolf rechtsklicken mit dem Kopf
        } else if (event.getRightClicked() instanceof Wolf && player.getItemInHand().getType().equals(Material.BONE) || player.getItemInHand().getType().equals(Material.NAME_TAG)) {
            event.setCancelled(true);           //TODO: geht nicht
        }
    }

    @EventHandler
    private void onDropWolfiMenu(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        ItemStack itemStack = event.getItemDrop().getItemStack();
        if (itemStack.getType().equals(Material.PLAYER_HEAD)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onWolfiPlace(BlockPlaceEvent event) {
        if (event.getBlockPlaced().getType().equals(Material.PLAYER_HEAD)) {           //TODO: Kann immer noch auf die Seite eines Blockes platzieren / Als Kopf anziehen
            event.setCancelled(true);
        }
    }

    //TODO: Spieler soll den Kopf nicht in andere Inventare moven können

    @EventHandler
    private void onWolfiDeath(EntityDeathEvent event) throws IOException {
        if(event.getEntity() instanceof Wolf) {
            Wolf wolf = (Wolf) event.getEntity();
            if(wolf.getCustomName() != null) {
                for (Player all : Bukkit.getOnlinePlayers()) {
                    all.sendMessage(ChatColor.DARK_GRAY + "» " + ChatColor.GOLD.toString() + ChatColor.BOLD + wolf.getCustomName() + " " +
                            ChatColor.RESET + "" + ChatColor.GRAY + "ist gestorben.");
                }
                List<Player> playerList = new ArrayList<Player>(Bukkit.getServer().getOnlinePlayers());
                Player randomPlayer = playerList.get(new Random().nextInt(playerList.size()));
                randomPlayer.setHealth(0);
                Config.set("wolfi", null);
            }
        }
    }

    public static void removeWolfi() {
        if (wolf != null && wolf.getCustomName() != null) {
            wolf.setHealth(0);              //TODO: Geht nicht beim reloaden und dann löschen
            //TODO: Kopf aus Inv löschen
        }
    }
}
