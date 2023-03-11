package it.glubschiii.Challenges.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Glubschiii | https://github.com/glubschiii
 * @since 1.0.6
 */
public class TabCompleteManager implements TabCompleter {

    private final List<String> timerTabComplete = Arrays.asList("toggle", "pause", "resume", "set", "reset", "up", "down", "color");
    private final List<String> allItemsTabComplete = Arrays.asList("overview", "skip");
    private final List<String> gameModeItemsTabComplete = Arrays.asList("1", "2", "3", "survival", "creative", "spectator", "adventure");
    private List<String> allOnlinePlayers = new ArrayList<>();

    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        if(cmd.getName().equalsIgnoreCase("timer")) {
            if(args.length == 1) {
                return timerTabComplete.stream().filter(option -> option.startsWith(args[0])).collect(Collectors.toList());
            } else {
                return Collections.emptyList();
            }
        } else if(cmd.getName().equalsIgnoreCase("allitems")) {
            if(args.length == 1) {
                return allItemsTabComplete.stream().filter(option -> option.startsWith(args[0])).collect(Collectors.toList());
            } else {
                return Collections.emptyList();
            }
        } else if(cmd.getName().equalsIgnoreCase("gm") || cmd.getName().equalsIgnoreCase("gamemode")) {
            if(args.length == 1) {
                return gameModeItemsTabComplete.stream().filter(option -> option.startsWith(args[0])).collect(Collectors.toList());
            } else if(args.length == 2) {
                for(Player all : Bukkit.getOnlinePlayers()) {
                    if(!allOnlinePlayers.contains(all)) {
                        allOnlinePlayers.add(all.getName());
                    }
                }
                return allOnlinePlayers.stream().filter(option -> option.startsWith(args[1])).collect(Collectors.toList());
            } else {
                return Collections.emptyList();
            }
        } else if(cmd.getName().equalsIgnoreCase("heal") || cmd.getName().equalsIgnoreCase("feed")) {
            if(args.length == 1) {
                for(Player all : Bukkit.getOnlinePlayers()) {
                    if(!allOnlinePlayers.contains(all)) {
                        allOnlinePlayers.add(all.getName());
                    }
                }
                return allOnlinePlayers.stream().filter(option -> option.startsWith(args[0])).collect(Collectors.toList());
            } else {
                return Collections.emptyList();
            }
        }
        return Collections.emptyList();
    }
}
//TODO: /timer set & /timer color