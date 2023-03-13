package it.glubschiii.Challenges.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

/**
 * @author Glubschiii | https://github.com/glubschiii
 * @since 1.0.6
 */
public class TablistManager {


    public void setTablist(Player player) {
        player.setPlayerListHeaderFooter(ChatColor.DARK_GRAY + "╾╾╾╾╾╾╾╾╾╾╾╾╾╾╾╾╾╾╾╾╾╾╾╾╾╾╾╾╾╾╾╾╾╾╾╾╾╾╾╾╾╾╾╾╾\n" +
                ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + "P2YL.tk\n" +         //TODO: Für andere deren IP anzeigen lassen? (In Config einstellbar machen?)
                ChatColor.GRAY + "Online" + ChatColor.DARK_GRAY + " » " + ChatColor.GREEN + Bukkit.getOnlinePlayers().size() + ChatColor.DARK_GRAY + "/" + ChatColor.GRAY +
                Bukkit.getServer().getMaxPlayers() + "\n" + ChatColor.GRAY + "Server" + ChatColor.DARK_GRAY + " » " + ChatColor.LIGHT_PURPLE + "Challenges\n",
                "\n" + ChatColor.GRAY + "Plugin" + ChatColor.DARK_GRAY + " » " + ChatColor.LIGHT_PURPLE + "/info\n" +
                ChatColor.GRAY + "Developer" + ChatColor.DARK_GRAY + " » " + ChatColor.LIGHT_PURPLE + "© Glubschiii\n" + ChatColor.DARK_GRAY +
                "╾╾╾╾╾╾╾╾╾╾╾╾╾╾╾╾╾╾╾╾╾╾╾╾╾╾╾╾╾╾╾╾╾╾╾╾╾╾╾╾╾╾╾╾╾");
    }

    public void setPlayerTeams(Player player) {
        Scoreboard scoreboard = player.getScoreboard();

        Team players = scoreboard.getTeam("players");
        Team admins = scoreboard.getTeam("admins");

        if(players == null) {
            players = scoreboard.registerNewTeam("players");
        }
        if(admins == null) {
            admins = scoreboard.registerNewTeam("admins");
        }

        players.setColor(ChatColor.GRAY);
        admins.setPrefix(ChatColor.DARK_RED + "Admin" + ChatColor.GRAY + " | " + ChatColor.DARK_RED);
        admins.setColor(ChatColor.DARK_RED);

        for(Player all : Bukkit.getOnlinePlayers()) {
            if(all.hasPermission("challenges.*")) {
                admins.addEntry(all.getName());
                continue;
            }

            players.addEntry(all.getName());
        }

    }

    public void setAllPlayerTeams() {
        Bukkit.getOnlinePlayers().forEach(this::setPlayerTeams);
    }

}
