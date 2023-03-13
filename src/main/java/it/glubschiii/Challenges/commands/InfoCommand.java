package it.glubschiii.Challenges.commands;

import it.glubschiii.Challenges.Main;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import javax.swing.text.PlainDocument;
import java.awt.*;

import static org.apache.logging.log4j.Level.valueOf;

public class InfoCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        TextComponent message = new TextComponent(ChatColor.GRAY + "══════════════════════════════\nThis plugin is developed by " +
                ChatColor.BLUE.toString() + ChatColor.BOLD + "Glubschiii.\n" + ChatColor.RESET.toString() + ChatColor.GRAY + "For more information and updates, " +
                "please\nvisit my GitHub: ");
        String hoverMessage = ChatColor.YELLOW + "Click here to get to my GitHub profile";
        TextComponent link = new TextComponent(ChatColor.GREEN.toString() + ChatColor.BOLD + "[CLICK]");

        link.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(hoverMessage).create()));
        link.setClickEvent(new net.md_5.bungee.api.chat.ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.OPEN_URL, "https://github.com/Glubschiii"));

        TextComponent thanks = new TextComponent(ChatColor.RESET.toString() + ChatColor.GRAY + "\nThank you for using my plugin!\n══════════════════════════════");
        link.addExtra(thanks);
        message.addExtra(link);

        
        commandSender.sendMessage(message);

        return false;
    }

    public static void runInfoCommand(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.dispatchCommand(player, "info");
            }
        }.runTaskTimer(Main.getInstance(), 108000, 108000);
    }
}
