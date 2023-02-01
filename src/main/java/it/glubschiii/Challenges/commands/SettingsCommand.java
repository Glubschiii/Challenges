package it.glubschiii.Challenges.commands;

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

/** @author Glubschiii | https://github.com/glubschiii
 @since 1.0.3
 */
public class SettingsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(args.length == 0) {

                Inventory settingsmenu = Bukkit.createInventory(null, 27, ChatColor.GOLD + "Settings" + ChatColor.DARK_GRAY + " • " +
                        ChatColor.BLUE + "Übersicht");

                ItemStack herausforderungen = new ItemStack(Material.DRAGON_HEAD);
                ItemStack challenges = new ItemStack(Material.GRASS_BLOCK);
                ItemStack spielregeln = new ItemStack(Material.MAP);
                ItemStack background = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);

                ItemMeta herausforderungenMeta = herausforderungen.getItemMeta();
                ItemMeta challengesMeta = challenges.getItemMeta();
                ItemMeta spielregelnMeta = spielregeln.getItemMeta();
                ItemMeta backgroundMeta = background.getItemMeta();

                herausforderungenMeta.setDisplayName(ChatColor.RED + "Herausforderungen");
                challengesMeta.setDisplayName(ChatColor.AQUA + "Challenges");
                spielregelnMeta.setDisplayName(ChatColor.GREEN + "Spielregeln");
                backgroundMeta.setDisplayName(" ");

                ArrayList<String> herausforderungenlore = new ArrayList<>();
                ArrayList<String> challengeslore = new ArrayList<>();
                ArrayList<String> spielregelnlore = new ArrayList<>();

                herausforderungenlore.add(" ");
                herausforderungenlore.add(ChatColor.RED + " Herausforderungen " + ChatColor.GRAY + "geben das " + ChatColor.RED + "Ziel");
                herausforderungenlore.add(ChatColor.GRAY + " und das " + ChatColor.RED + "Ende " + ChatColor.GRAY + "der " +
                        ChatColor.RED + "Challenge " + ChatColor.GRAY + "vor.");
                herausforderungenlore.add(" ");
                herausforderungenlore.add(ChatColor.RED + "" + ChatColor.ITALIC + "Klick: " + ChatColor.RED + "Übersicht");
                herausforderungenMeta.setLore(herausforderungenlore);

                challengeslore.add(" ");
                challengeslore.add(ChatColor.AQUA + " Challenges " + ChatColor.GRAY + "verändern");
                challengeslore.add(ChatColor.GRAY + " das " + ChatColor.AQUA + "Spielgeschehen " + ChatColor.GRAY + "auf");
                challengeslore.add(ChatColor.GRAY + " viele " + ChatColor.AQUA + "Arten " + ChatColor.GRAY + "und " + ChatColor.AQUA +
                        "Weisen" + ChatColor.GRAY + ".");
                challengeslore.add(" ");
                challengeslore.add(ChatColor.AQUA + "" + ChatColor.ITALIC + "Klick: " + ChatColor.AQUA + "Übersicht");
                challengesMeta.setLore(challengeslore);

                spielregelnlore.add(" ");
                spielregelnlore.add(ChatColor.GREEN + " Spielregeln " + ChatColor.GRAY + "sind normale");
                spielregelnlore.add(ChatColor.GREEN + " /gamerules " + ChatColor.GRAY + "und weitere");
                spielregelnlore.add(ChatColor.GREEN + " Veränderungen " + ChatColor.GRAY + "des Spielgeschehens");
                spielregelnlore.add(ChatColor.GRAY + " mit normalen Mitteln von Minecraft.");
                spielregelnlore.add("");
                spielregelnlore.add(ChatColor.GREEN + "" + ChatColor.ITALIC + "Klick: " + ChatColor.GREEN + "Übersicht");
                spielregelnMeta.setLore(spielregelnlore);

                //itemMeta.addEnchant(Enchantment.ARROW_DAMAGE, 5, false);

                herausforderungen.setItemMeta(herausforderungenMeta);
                challenges.setItemMeta(challengesMeta);
                spielregeln.setItemMeta(spielregelnMeta);
                background.setItemMeta(backgroundMeta);

                //item.setAmount(1);4
                settingsmenu.setItem(26, background);
                settingsmenu.setItem(25, background);
                settingsmenu.setItem(24, background);
                settingsmenu.setItem(23, background);
                settingsmenu.setItem(22, background);
                settingsmenu.setItem(21, background);
                settingsmenu.setItem(20, background);
                settingsmenu.setItem(19, background);
                settingsmenu.setItem(18, background);
                settingsmenu.setItem(17, background);
                settingsmenu.setItem(16, background);
                settingsmenu.setItem(15, spielregeln);
                settingsmenu.setItem(14, background);
                settingsmenu.setItem(13, background);
                settingsmenu.setItem(12, challenges);
                settingsmenu.setItem(11, herausforderungen);
                settingsmenu.setItem(10, background);
                settingsmenu.setItem(9, background);
                settingsmenu.setItem(8, background);
                settingsmenu.setItem(7, background);
                settingsmenu.setItem(6, background);
                settingsmenu.setItem(5, background);
                settingsmenu.setItem(4, background);
                settingsmenu.setItem(3, background);
                settingsmenu.setItem(2, background);
                settingsmenu.setItem(1, background);
                settingsmenu.setItem(0, background);

                player.openInventory(settingsmenu);



            } else
                player.sendMessage(ChatColor.RED + "Verwendung: /settings");
        }
        return false;
    }
}
