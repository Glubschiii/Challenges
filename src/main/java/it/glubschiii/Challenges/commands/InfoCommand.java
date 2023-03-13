package it.glubschiii.Challenges.commands;

import net.kyori.adventure.text.event.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.jetbrains.annotations.NotNull;

import javax.swing.text.PlainDocument;
import java.awt.*;

public class InfoCommand implements CommandExecutor, Listener {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        TextComponent message = new TextComponent("This plugin was developed by Glubschiii.\nFor more information and updates, " +
                "please visit my GitHub [CLICK].\nThank you for using my plugin!");
        TextComponent link = new TextComponent("[CLICK]");
        link.setClickEvent(new net.md_5.bungee.api.chat.ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.OPEN_URL, "github.com/Glubschiii"));
        message.addExtra(link);
        
        commandSender.sendMessage(message);

        //TODO: Click machen (GitHub Link)

        return false;
    }

    private void onClick(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();

        if(message.contains("[CLICK]")) {
            ClickEvent.openUrl("github.com/Glubschiii");
        }
    }
}
