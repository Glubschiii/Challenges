package it.glubschiii.Challenges.listeners;

import it.glubschiii.Challenges.Main;
import it.glubschiii.Challenges.timer.Timer;
import it.glubschiii.Challenges.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.projectiles.ProjectileSource;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/** @author Glubschiii | https://github.com/glubschiii
    @since 1.0.2
 */
public class EntityDamageEvent implements Listener {
    Timer timer = Main.getInstance().getTimer();

    private String prefix = ChatColor.DARK_GRAY + "[" + ChatColor.LIGHT_PURPLE + "P2YL" + ChatColor.DARK_GRAY + "] ";
    private Map<org.bukkit.event.entity.EntityDamageEvent.DamageCause, String> damageCauseMap = new HashMap<>();
    {
        damageCauseMap.put(org.bukkit.event.entity.EntityDamageEvent.DamageCause.LAVA, "Lava");
        damageCauseMap.put(org.bukkit.event.entity.EntityDamageEvent.DamageCause.ENTITY_ATTACK, "Angriff");
        damageCauseMap.put(org.bukkit.event.entity.EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK, "Angriff");
        damageCauseMap.put(org.bukkit.event.entity.EntityDamageEvent.DamageCause.ENTITY_EXPLOSION, "Explosion");
        damageCauseMap.put(org.bukkit.event.entity.EntityDamageEvent.DamageCause.BLOCK_EXPLOSION, "Explosion");
        damageCauseMap.put(org.bukkit.event.entity.EntityDamageEvent.DamageCause.CONTACT, "Kontakt");
        damageCauseMap.put(org.bukkit.event.entity.EntityDamageEvent.DamageCause.CRAMMING, "Enge");
        damageCauseMap.put(org.bukkit.event.entity.EntityDamageEvent.DamageCause.DRAGON_BREATH, "Drachenatem");
        damageCauseMap.put(org.bukkit.event.entity.EntityDamageEvent.DamageCause.DROWNING, "Ertrinken");
        damageCauseMap.put(org.bukkit.event.entity.EntityDamageEvent.DamageCause.DRYOUT, "Austrocknen");
        damageCauseMap.put(org.bukkit.event.entity.EntityDamageEvent.DamageCause.FALL, "Fallschaden");
        damageCauseMap.put(org.bukkit.event.entity.EntityDamageEvent.DamageCause.POISON, "Vergiftung");
        damageCauseMap.put(org.bukkit.event.entity.EntityDamageEvent.DamageCause.VOID, "Void");
        damageCauseMap.put(org.bukkit.event.entity.EntityDamageEvent.DamageCause.FREEZE, "Erfrieren");
        damageCauseMap.put(org.bukkit.event.entity.EntityDamageEvent.DamageCause.SUFFOCATION, "Ersticken");
        damageCauseMap.put(org.bukkit.event.entity.EntityDamageEvent.DamageCause.FLY_INTO_WALL, "Ersticken");
        damageCauseMap.put(org.bukkit.event.entity.EntityDamageEvent.DamageCause.THORNS, "Dornen");
        damageCauseMap.put(org.bukkit.event.entity.EntityDamageEvent.DamageCause.LIGHTNING, "Blitz");
        damageCauseMap.put(org.bukkit.event.entity.EntityDamageEvent.DamageCause.FIRE, "Feuer");
        damageCauseMap.put(org.bukkit.event.entity.EntityDamageEvent.DamageCause.FIRE_TICK, "Feuer");
        damageCauseMap.put(org.bukkit.event.entity.EntityDamageEvent.DamageCause.HOT_FLOOR, "Heißer Boden");
        damageCauseMap.put(org.bukkit.event.entity.EntityDamageEvent.DamageCause.MAGIC, "Magie");
        damageCauseMap.put(org.bukkit.event.entity.EntityDamageEvent.DamageCause.SUICIDE, "Selbstmord");
        damageCauseMap.put(org.bukkit.event.entity.EntityDamageEvent.DamageCause.STARVATION, "Verhungern");
        damageCauseMap.put(org.bukkit.event.entity.EntityDamageEvent.DamageCause.PROJECTILE, "Projektil");
        damageCauseMap.put(org.bukkit.event.entity.EntityDamageEvent.DamageCause.SONIC_BOOM, "Sonic Boom");
        damageCauseMap.put(org.bukkit.event.entity.EntityDamageEvent.DamageCause.WITHER, "Ausdörrung");
        damageCauseMap.put(org.bukkit.event.entity.EntityDamageEvent.DamageCause.FALLING_BLOCK, "Schlag");
    }

    @EventHandler
        public void onEntityDamageEvent(org.bukkit.event.entity.EntityDamageEvent e) {
        double FinalDamage = e.getFinalDamage() / 2;
        String damagecause = damageCauseMap.getOrDefault(e.getCause(), "Unbekannt");

        if(e instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent damageEvent = (EntityDamageByEntityEvent) e;
            //TODO: Code unten effizienter (wie oben) machen
            damageEvent.getDamager();
            Entity entity = damageEvent.getDamager();
            Player player = Bukkit.getPlayer(entity.getUniqueId());
            if(entity.getType() == EntityType.PLAYER && !entity.getUniqueId().equals(player.getUniqueId())) {
                Player damager = (Player) damageEvent.getDamager();
                damagecause += "[" + damager.getDisplayName() + "]";
            } else if(entity.getType() == EntityType.BEE) {
                damagecause += " [Biene]";
            } else if(entity.getType() == EntityType.CAVE_SPIDER) {
                damagecause += " [Höhlenspinne]";
            } else if(entity.getType() == EntityType.DOLPHIN) {
                damagecause += " [Delfin]";
            } else if(entity.getType() == EntityType.ENDERMAN) {
                damagecause += " [Enderman]";
            } else if(entity.getType() == EntityType.GOAT) {
                damagecause += " [Ziege]";
            } else if(entity.getType() == EntityType.IRON_GOLEM) {
                damagecause += " [Eisengolem]";
            } else if(entity.getType() == EntityType.LLAMA) {
                damagecause += " [Lama]";
            } else if(entity.getType() == EntityType.PANDA) {
                damagecause += " [Panda]";
            } else if(entity.getType() == EntityType.PIGLIN) {
                damagecause += " [Piglin]";
            } else if(entity.getType() == EntityType.POLAR_BEAR) {
                damagecause += " [Eisbär]";
            } else if(entity.getType() == EntityType.SPIDER) {
                damagecause += " [Spinne]";
            } else if(entity.getType() == EntityType.TRADER_LLAMA) {
                damagecause += " [Händlerlama]";
            } else if(entity.getType() == EntityType.WOLF) {
                damagecause += " [Wolf]";
            } else if(entity.getType() == EntityType.ZOMBIFIED_PIGLIN) {
                damagecause += " [Zombifizierter Piglin]";
            } else if(entity.getType() == EntityType.BLAZE) {
                damagecause += " [Lohe]";
            } else if(entity.getType() == EntityType.CREEPER) {
                damagecause += " [Creeper]";
            } else if(entity.getType() == EntityType.DROWNED) {
                damagecause += " [Ertrunkener]";
            } else if(entity.getType() == EntityType.ELDER_GUARDIAN) {
                damagecause += " [Großer Wächter]";
            } else if(entity.getType() == EntityType.ENDERMITE) {
                damagecause += " [Endermite]";
            } else if(entity.getType() == EntityType.EVOKER) {
                damagecause += " [Magier]";
            } else if(entity.getType() == EntityType.GHAST) {
                damagecause += " [Ghast]";
            } else if(entity.getType() == EntityType.GUARDIAN) {
                damagecause += " [Wächter]";
            } else if(entity.getType() == EntityType.HOGLIN) {
                damagecause += " [Hoglin]";
            } else if(entity.getType() == EntityType.HUSK) {
                damagecause += " [Wüstenzombie]";
            } else if(entity.getType() == EntityType.MAGMA_CUBE) {
                damagecause += " [Magmawürfel]";
            } else if(entity.getType() == EntityType.PHANTOM) {
                damagecause += " [Phantom]";
            } else if(entity.getType() == EntityType.PIGLIN_BRUTE) {
                damagecause += " [Piglin Barbar]";
            } else if(entity.getType() == EntityType.PILLAGER) {
                damagecause += " [Plünderer]";
            } else if(entity.getType() == EntityType.RAVAGER) {
                damagecause += " [Verwüster]";
            } else if(entity.getType() == EntityType.SHULKER) {
                damagecause += " [Shulker]";
            } else if(entity.getType() == EntityType.SILVERFISH) {
                damagecause += " [Silberfischchen]";
            } else if(entity.getType() == EntityType.SKELETON) {
                damagecause += " [Skelett]";
            } else if(entity.getType() == EntityType.SLIME) {
                damagecause += " [Schleim]";
            } else if(entity.getType() == EntityType.STRAY) {
                damagecause += " [Eiswanderer]";
            } else if(entity.getType() == EntityType.VEX) {
                damagecause += " [Plagegeist]";
            } else if(entity.getType() == EntityType.VINDICATOR) {
                damagecause += " [Diener]";
            } else if(entity.getType() == EntityType.WARDEN) {
                damagecause += " [Wärter]";
            } else if(entity.getType() == EntityType.WITCH) {
                damagecause += " [Hexe]";
            } else if(entity.getType() == EntityType.WITHER_SKELETON) {
                damagecause += " [Witherskelett]";
            } else if(entity.getType() == EntityType.ZOGLIN) {
                damagecause += " [Zoglin]";
            } else if(entity.getType() == EntityType.ZOMBIE) {
                damagecause += " [Zombie]";
            } else if(entity.getType() == EntityType.ZOMBIE_VILLAGER) {
                damagecause += " [Zombiedorfbewohner]";
            } else if(entity.getType() == EntityType.ENDER_DRAGON) {
                damagecause += " [Enderdrache]";
            } else if(entity.getType() == EntityType.WITHER) {
                damagecause += " [Wither]";
            }
        }
            //TODO: Wird nicht gerundet (z.B. bei /kill)
        if(e.getEntityType() == EntityType.PLAYER && Timer.isRunning() && Config.getBoolean("settings.damage").booleanValue()) {
            Bukkit.broadcastMessage(prefix + ChatColor.WHITE.toString() + ChatColor.BOLD + e.getEntity().getName() + ChatColor.RESET + "" +
                    ChatColor.GREEN + " hat durch " + ChatColor.WHITE + ChatColor.BOLD + damagecause + ChatColor.RESET + " " + ChatColor.GREEN +
                    FinalDamage + ChatColor.GREEN + " ❤ " + ChatColor.GREEN + "Schaden bekommen");     //TODO: Design changen
        }
    }
}