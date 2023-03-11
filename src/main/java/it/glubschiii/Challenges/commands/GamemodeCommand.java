package it.glubschiii.Challenges.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/** @author Glubschiii | https://github.com/glubschiii
 @since 1.0.2
 */
public class GamemodeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player) {

            if(args.length == 1) {
                switch (args[0].toLowerCase()) {
                    case "0":
                    case "survival": {
                        if(((Player) sender).getGameMode() != GameMode.SURVIVAL) {
                            sender.sendMessage(ChatColor.DARK_GRAY + "» " + ChatColor.GRAY + "Du befindest dich nun im " + ChatColor.GOLD.toString() +
                                    ChatColor.BOLD + "Überlebensmodus" + ChatColor.RESET + "" + ChatColor.GRAY + ".");
                            ((Player) sender).setGameMode(GameMode.SURVIVAL);
                        } else {
                            sender.sendMessage(ChatColor.DARK_GRAY + "» " + ChatColor.GRAY + "Du befindest dich bereits im Überlebensmodus.");
                        }
                        break;
                    }
                    case "1":
                    case "creative": {
                        if(((Player) sender).getGameMode() != GameMode.CREATIVE) {
                            sender.sendMessage(ChatColor.DARK_GRAY + "» " + ChatColor.GRAY + "Du befindest dich nun im " + ChatColor.GOLD.toString() +
                                    ChatColor.BOLD + "Kreativmodus" + ChatColor.RESET + "" + ChatColor.GRAY + ".");
                            ((Player) sender).setGameMode(GameMode.CREATIVE);
                        } else {
                            sender.sendMessage(ChatColor.DARK_GRAY + "» " + ChatColor.GRAY + "Du befindest dich bereits im Kreativmodus.");
                        }
                        break;
                    }
                    case "2":
                    case "adventure": {
                        if(((Player) sender).getGameMode() != GameMode.ADVENTURE) {
                            sender.sendMessage(ChatColor.DARK_GRAY + "» " + ChatColor.GRAY + "Du befindest dich nun im " + ChatColor.GOLD.toString() +
                                    ChatColor.BOLD + "Abenteuermodus" + ChatColor.RESET + "" + ChatColor.GRAY + ".");
                            ((Player) sender).setGameMode(GameMode.ADVENTURE);
                        } else {
                            sender.sendMessage(ChatColor.DARK_GRAY + "» " + ChatColor.GRAY + "Du befindest dich bereits im Abenteuermodus.");
                        }
                        break;
                    }
                    case "3":
                    case "spectator": {
                        if(((Player) sender).getGameMode() != GameMode.SPECTATOR) {
                            sender.sendMessage(ChatColor.DARK_GRAY + "» " + ChatColor.GRAY + "Du befindest dich nun im " + ChatColor.GOLD.toString() +
                                    ChatColor.BOLD + "Zuschauermodus" + ChatColor.RESET + "" + ChatColor.GRAY + ".");
                            ((Player) sender).setGameMode(GameMode.SPECTATOR);
                        } else {
                            sender.sendMessage(ChatColor.DARK_GRAY + "» " + ChatColor.GRAY + "Du befindest dich bereits im Zuschauermodus.");
                        }
                        break;
                    }
                    default:
                        sendUsage(sender);
                }
            } else if(args.length == 2) {
                Player target = null;
                for(Player all : Bukkit.getOnlinePlayers()) {
                    if(all.getName().equalsIgnoreCase(args[1])) {
                        target = all.getPlayer();
                        continue;                   // Sobald Spieler gefunden wird, bricht es Schelife ab
                    }
                }
                switch (args[0].toLowerCase()) {
                    case "0":
                    case "survival":
                        if (target != null) {
                            if(target.getGameMode() != GameMode.SURVIVAL) {
                                sender.sendMessage(ChatColor.DARK_GRAY + "» " + ChatColor.GRAY + "Du hast " + ChatColor.GOLD + target.getName() + ChatColor.GRAY +
                                        " in den Überlebensmodus gesetzt!");
                                target.sendMessage(ChatColor.DARK_GRAY + "» " + ChatColor.GRAY + "Du wurdest von " + ChatColor.GOLD + sender.getName() +
                                        ChatColor.GRAY + " in den Überlebensmodus gesetzt!");
                                target.setGameMode(GameMode.SURVIVAL);
                            } else {
                                sender.sendMessage(ChatColor.DARK_GRAY + "» " + ChatColor.GRAY + "Der Spieler " + ChatColor.GOLD + args[1] + ChatColor.GRAY +
                                        " befindet sich bereits im Überlebensmodus.");
                            }
                        } else {
                            sender.sendMessage(ChatColor.DARK_GRAY + "» " + ChatColor.GRAY + "Der Spieler " + ChatColor.GOLD + args[1] + ChatColor.GRAY +
                                    " befindet sich derzeit nicht auf dem Server.");
                        }
                        break;
                    case "1":
                    case "creative":
                        if (target != null) {
                            if(target.getGameMode() != GameMode.CREATIVE) {
                                sender.sendMessage(ChatColor.DARK_GRAY + "» " + ChatColor.GRAY + "Du hast " + ChatColor.GOLD + target.getName() + ChatColor.GRAY +
                                        " in den Kreativmodus gesetzt!");
                                target.sendMessage(ChatColor.DARK_GRAY + "» " + ChatColor.GRAY + "Du wurdest von " + ChatColor.GOLD + sender.getName() +
                                        ChatColor.GRAY + " in den Kreativmodus gesetzt!");
                                target.setGameMode(GameMode.CREATIVE);
                            } else {
                                sender.sendMessage(ChatColor.DARK_GRAY + "» " + ChatColor.GRAY + "Der Spieler " + ChatColor.GOLD + args[1] + ChatColor.GRAY +
                                        " befindet sich bereits im Kreativmodus.");
                            }
                        } else {
                            sender.sendMessage(ChatColor.DARK_GRAY + "» " + ChatColor.GRAY + "Der Spieler " + ChatColor.GOLD + args[1] + ChatColor.GRAY +
                                    " befindet sich derzeit nicht auf dem Server.");
                        }
                        break;
                    case "2":
                    case "adventure":
                        if (target != null) {
                            if(target.getGameMode() != GameMode.ADVENTURE) {
                                sender.sendMessage(ChatColor.DARK_GRAY + "» " + ChatColor.GRAY + "Du hast " + ChatColor.GOLD + target.getName() + ChatColor.GRAY +
                                        " in den Abenteuermodus gesetzt!");
                                target.sendMessage(ChatColor.DARK_GRAY + "» " + ChatColor.GRAY + "Du wurdest von " + ChatColor.GOLD + sender.getName() +
                                        ChatColor.GRAY + " in den Abenteuermodus gesetzt!");
                                target.setGameMode(GameMode.ADVENTURE);
                            } else {
                                sender.sendMessage(ChatColor.DARK_GRAY + "» " + ChatColor.GRAY + "Der Spieler " + ChatColor.GOLD + args[1] + ChatColor.GRAY +
                                        " befindet sich bereits im Abenteuermodus.");
                            }
                        } else {
                            sender.sendMessage(ChatColor.DARK_GRAY + "» " + ChatColor.GRAY + "Der Spieler " + ChatColor.GOLD + args[1] + ChatColor.GRAY +
                                    " befindet sich derzeit nicht auf dem Server.");
                        }
                        break;
                    case "3":
                    case "spectator": {
                        if (target != null) {
                            if(target.getGameMode() != GameMode.SPECTATOR) {
                                sender.sendMessage(ChatColor.DARK_GRAY + "» " + ChatColor.GRAY + "Du hast " + ChatColor.GOLD + target.getName() + ChatColor.GRAY +
                                        " in den Zuschauermodus gesetzt!");
                                target.sendMessage(ChatColor.DARK_GRAY + "» " + ChatColor.GRAY + "Du wurdest von " + ChatColor.GOLD + sender.getName() +
                                        ChatColor.GRAY + " in den Zuschauermodus gesetzt!");
                                target.setGameMode(GameMode.SPECTATOR);
                            } else {
                                sender.sendMessage(ChatColor.DARK_GRAY + "» " + ChatColor.GRAY + "Der Spieler " + ChatColor.GOLD + args[1] + ChatColor.GRAY +
                                        " befindet sich bereits im Zuschauermodus.");
                            }
                        } else {
                            sender.sendMessage(ChatColor.DARK_GRAY + "» " + ChatColor.GRAY + "Der Spieler " + ChatColor.GOLD + args[1] + ChatColor.GRAY +
                                    " befindet sich derzeit nicht auf dem Server.");
                        }
                        break;
                    }
                    default:
                        sendUsage(sender);
                }
            } else {
                sendUsage(sender);
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Dieser Befehl kann nur als Spieler ausgeführt werden!");
            return false;
        }
        return false;
    }
    private void sendUsage(CommandSender sender) {
        sender.sendMessage(ChatColor.GREEN + "Verwendung: " + ChatColor.WHITE.toString() + ChatColor.BOLD +
                "/gm <0;1;2;3> <Spieler>");
    }
}
