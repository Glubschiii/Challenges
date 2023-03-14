package it.glubschiii.Challenges.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static it.glubschiii.Challenges.utils.MainInventoryManager.playerSettingsInv;

public class CosmeticsCommand implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;

            player.openInventory(playerSettingsInv);

        } else {
            commandSender.sendMessage(ChatColor.RED + "Dieser Befehl kann nur als Spieler ausgeführt werden!");
        }


        return false;
    }
}
