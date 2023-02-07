package it.glubschiii.Challenges.timer;

import it.glubschiii.Challenges.Main;
import it.glubschiii.Challenges.timer.Timer;
import it.glubschiii.Challenges.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

/**
 * @author Glubschiii | https://github.com/glubschiii
 * @since 1.0.2
 */
public class TimerCommand implements CommandExecutor {

    private final String prefix = ChatColor.DARK_GRAY + "[" + ChatColor.LIGHT_PURPLE + "P2YL" + ChatColor.DARK_GRAY + "] ";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //TODO: Wenn man /timer oder so eingibt kommt error, usage geht nicht
        //TODO: Timer toggle buggt, wenn ich nicht als 1. auf den Server joine startet er nicht
        //TODO: /timer pause, resume, up, down... gibt auch sendUsage Meldung aus
        Timer timer = Main.getInstance().getTimer();
        if (sender instanceof Player) {
            if (args.length == 1) {
                switch (args[0].toLowerCase()) {
                    case "toggle": {
                        if (Timer.isRunning()) {
                            timer.setRunning(false);
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                all.sendMessage(prefix + ChatColor.YELLOW + "Der Timer wurde von " + ChatColor.WHITE + ChatColor.BOLD +
                                        ((Player) sender).getDisplayName() + "" + ChatColor.RESET + "" + ChatColor.YELLOW + " pausiert!");
                                //TODO: Sound ändern
                                all.playSound(all.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_DEATH, 3.0F, 0.5F);
                            }
                        } else {
                            timer.setRunning(true);
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                all.sendMessage(prefix + ChatColor.GREEN + "Der Timer wurde von " + ChatColor.WHITE + ChatColor.BOLD +
                                        ((Player) sender).getDisplayName() + "" + ChatColor.RESET + "" + ChatColor.GREEN + " fortgesetzt!");
                                //TODO: Sound ändern
                                all.playSound(all.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_YES, 3.0F, 0.5F);
                            }
                        }
                        break;
                    }

                    case "reset": {
                        timer.setRunning(false);
                        timer.setTime(0);
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.sendMessage(prefix + ChatColor.GREEN + "Der Timer wurde von " + ChatColor.WHITE + ChatColor.BOLD +
                                    ((Player) sender).getDisplayName() + "" + ChatColor.RESET + "" + ChatColor.GREEN + " zurückgesetzt!");
                        }
                        break;

                    }

                    case "up": {
                        if (Config.get("timer-direction") != "up") {
                            try {
                                Config.set("timer-direction", "up");
                                sender.sendMessage(prefix + ChatColor.YELLOW + "Der Timer zählt nun nach oben!");
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            sender.sendMessage(prefix + ChatColor.GREEN + "Der Timer zählt bereits nach oben!");
                        }
                        break;
                    }

                    case "down": {
                        if (Config.get("timer-direction") != "down") {
                            try {
                                Config.set("timer-direction", "down");
                                sender.sendMessage(prefix + ChatColor.YELLOW + "Der Timer zählt nun nach unten!");
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            sender.sendMessage(prefix + ChatColor.GREEN + "Der Timer zählt bereits nach unten!");
                        }
                        break;
                    }

                    case "pause": {
                        if (Timer.isRunning()) {
                            timer.setRunning(false);
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                all.sendMessage(prefix + ChatColor.YELLOW + "Der Timer wurde von " + ChatColor.WHITE + ChatColor.BOLD +
                                        ((Player) sender).getDisplayName() + "" + ChatColor.RESET + "" + ChatColor.YELLOW + " pausiert!");
                                //TODO: Sound ändern
                                all.playSound(all.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_DEATH, 3.0F, 0.5F);
                            }
                        } else {
                            sender.sendMessage(prefix + ChatColor.GREEN + "Der Timer ist bereits pausiert!");
                        }
                        break;
                    }

                    case "resume": {
                        if (!Timer.isRunning()) {
                            timer.setRunning(true);
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                all.sendMessage(prefix + ChatColor.GREEN + "Der Timer wurde von " + ChatColor.WHITE + ChatColor.BOLD +
                                        ((Player) sender).getDisplayName() + "" + ChatColor.RESET + "" + ChatColor.GREEN + " fortgesetzt!");
                                //TODO: Sound ändern
                                all.playSound(all.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_YES, 3.0F, 0.5F);
                            }
                        } else {
                            sender.sendMessage(prefix + ChatColor.GREEN + "Der Timer wurde bereits fortgesetzt!");
                        }
                        break;
                    }
                    default:
                        sendUsage(sender);
                }
            } else if (args[0].equalsIgnoreCase("color")) {
                try {
                    Main.getInstance().setColor((Player) sender, ChatColor.getByChar(args[1]));
                    timer.sendActionBar();
                    sender.sendMessage(prefix + ChatColor.GREEN + "Die Farbe des Timers wurde auf " + ChatColor.WHITE.toString() + ChatColor.BOLD + args[1] +
                            "" + ChatColor.RESET + "" + ChatColor.GREEN + " geändert!");
                } catch (Exception e) {
                    sendColorUsage(sender);
                }
            } else if (args[0].equalsIgnoreCase("set")) {       //TODO: s for secs, d for days... hinzufügbar
                try {
                    timer.setTime(Integer.parseInt(args[1]) * 5);
                    sender.sendMessage(prefix + ChatColor.GREEN + "Der Timer wurde auf " + ChatColor.WHITE + ChatColor.BOLD + args[1] +
                            "" + ChatColor.RESET + "" + ChatColor.GREEN + " gesetzt!");
                } catch (NumberFormatException e) {
                    sender.sendMessage(ChatColor.GREEN + "Dein Parameter 2 muss eine Zahl sein!");
                }
            } else {
                sendUsage(sender);
            }
        }
        return false;
    }

    // TODO: /timer show und /timer hide (Timer in der Actionbar wird eingeblendet - Standardmäßig!)
    private void sendUsage(CommandSender sender) {                                                                      // TODO: /timer disable (Timer in der Actionbar wird ausgeblendet)
        sender.sendMessage(ChatColor.GREEN + "Verwendung: " + ChatColor.WHITE + ChatColor.BOLD +
                "/timer toggle, /timer set <Zeit>, /timer reset, /timer <up|down>");                                              // TODO: /timer reverse (Timer soll nach unten zählen und wenn bereits aktiv nach oben)
    }                                                                                                                   // TODO: /timer change (Timer soll in Actionbar nicht mehr 3d 20h 5m

    private void sendColorUsage(CommandSender sender) {
        sender.sendMessage(ChatColor.GREEN + "Verwendung: " + ChatColor.WHITE + ChatColor.BOLD +
                "/timer color <Colorcode>");
    }
    // TODO: 4s (zb) anzeigen, sondern 03:20:05:04, wenn bereits aktiv
}                                                                                                                       // TODO: andersrum
