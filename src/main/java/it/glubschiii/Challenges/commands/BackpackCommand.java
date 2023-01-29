package it.glubschiii.Challenges.commands;

import it.glubschiii.Challenges.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/** @author Glubschiii | https://github.com/glubschiii
 @since 1.0.3
 */
public class BackpackCommand implements CommandExecutor {

    private final Inventory inventory;

    public BackpackCommand() {

        int slots = 27;

        if(Config.contains("command.backpack.slots")) {
            slots = (int) Config.get("command.backpack.slots");                 //TODO: Backpack wird nach reloard (etc.) nicht gespeichert | In Config speichern
        } else {                                                                //TODO: Enderchest sound bei /bp einfügen
            try {
                Config.set("command.backpack.slots", 27);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.inventory = Bukkit.createInventory(null, slots, "Backpack");
    }

    public void saveBackpack() throws IOException {
        List<ItemStack> items = new ArrayList<>();
        for (int i = 0; i < inventory.getSize(); i++) {
            items.add(inventory.getItem(i));
        }
        Config.set("command.backpack.backpack-contents", items);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player)sender;
            player.openInventory(inventory);
            try {
                saveBackpack();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Dieser Befehl kann nur als Spieler ausgeführt werden!");
        }
        return false;
    }

}
