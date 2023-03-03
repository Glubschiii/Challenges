package it.glubschiii.Challenges.timer;

import it.glubschiii.Challenges.Main;
import it.glubschiii.Challenges.utils.Config;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/** @author Glubschiii | https://github.com/glubschiii
 @since 1.0.1
 */
public class Timer extends TimerCommand {
//TODO: Zeit wird nach /reset confirm bzw. nach ändern im Menü bzw. nach reload (KP durch was) nicht immer richtig abgespeichert und dann wird falsche Zeit angezeigt
    private static boolean running; //true or false                                                                            // TODO: Wenn man in MC /timer "" eingibt, soll vorgeschlagen werden
    private static int time;                                                                                                   // TODO: was man eingeben kann, wie wenn man zb /gamemode "" eingibt

    public Timer(boolean running, int time) {
        this.running = running;
        this.time = time;

        run();
    }


    public static boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public static int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
    double count = -1.0;

        public void sendActionBar() {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (Main.getInstance().getColor(player) == null) {
                    //Main.getInstance().setColor(player, ChatColor.LIGHT_PURPLE);
                    var mm = MiniMessage.miniMessage();
                    Component parsed = mm.deserialize("<gradient:#6bd1ff:#e061f8:" + count + "><bold>" +
                            TimeCalculator.format(getTime()/5, ""));
                    player.sendActionBar(parsed);
                } else if(Main.getInstance().getColor(player) != null) {
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Main.getInstance().getColor(player).toString() +
                            ChatColor.BOLD + TimeCalculator.format(getTime()/5, "")));
                }
            }
        }

    private void run() {
        new BukkitRunnable() {
            @Override
            public void run() {

                sendActionBar();

                if (!isRunning()) {
                    count = count + 0.1;
                    if(count >= 1) {
                        count = -1;
                    }
                    /*for(Player all : Bukkit.getOnlinePlayers()) {
                        Location playerLoc = all.getLocation();
                        all.getWorld().spawnParticle(Particle.HEART, playerLoc, 10);            //TODO: Change particle effects
                    }*/
                    return;
                }
                if(((String) Config.get("timer-direction")).contains("up")) {
                    setTime(getTime() + 1);
                } else {
                    setTime(getTime() - 1);
                }
                count = count + 0.1;
                if(count >= 1) {
                    count = -1;
                }
                if(getTime() <= 0) {
                    setRunning(false);
                    setTime(0);
                }
            }
        }.runTaskTimer(Main.getInstance(), 4, 4);
    }
}
