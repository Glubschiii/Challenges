package it.glubschiii.Challenges.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/** @author Glubschiii | https://github.com/glubschiii
 @since 1.0.2
 */
public class HealCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(args.length == 0) {
                player.setHealth(20);
                player.setFoodLevel(20);
                player.sendMessage(ChatColor.DARK_GRAY + "» " + ChatColor.GRAY + "Du wurdest geheilt!");
            } else if(args.length == 1) {
                //TODO: Es wird nur auf den ersten arg geachtet, "/heal g" heilt den Spieler "Glubschiii" bereits
                Player target = null;
                for(Player all : Bukkit.getOnlinePlayers()) {
                    if(all.getName().equalsIgnoreCase(args[0])) {
                        target = all.getPlayer();
                        continue;
                    }
                }
                if(target != null) {
                    target.setHealth(20);
                    target.setFoodLevel(20);
                    target.sendMessage(ChatColor.DARK_GRAY + "» " + ChatColor.GRAY + "Du wurdest von " + ChatColor.GOLD + player.getName() +
                            ChatColor.GRAY + " geheilt!");
                    player.sendMessage(ChatColor.DARK_GRAY + "» " + ChatColor.GRAY + "Du hast " + ChatColor.GOLD + target.getName() + ChatColor.GRAY +
                            " geheilt!");
                } else
                    player.sendMessage(ChatColor.DARK_GRAY + "» " + ChatColor.GRAY + "Der Spieler " + ChatColor.GOLD + args[0] + ChatColor.GRAY +
                            " befindet sich derzeit nicht auf dem Server.");
            } else
                player.sendMessage(ChatColor.RED + "Verwendung: /heal <SPIELER>");
        } else
            sender.sendMessage(ChatColor.RED + "Dieser Befehl kann nur als Spieler ausgeführt werden!");

        return false;
    }

}
