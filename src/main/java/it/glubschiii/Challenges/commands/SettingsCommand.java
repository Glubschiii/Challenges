package it.glubschiii.Challenges.commands;

import it.glubschiii.Challenges.utils.MainInventoryManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

import static it.glubschiii.Challenges.utils.MainInventoryManager.settingsInv;

/** @author Glubschiii | https://github.com/glubschiii
 @since 1.0.3
 */
public class SettingsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(args.length == 0) {
                player.openInventory(settingsInv);
            } else {
                player.sendMessage(ChatColor.RED + "Verwendung: /settings");
            }
        }
        return false;
    }
}
