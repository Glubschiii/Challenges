package it.glubschiii.Challenges.commands;

import it.glubschiii.Challenges.utils.Config;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

/** @author Glubschiii | https://github.com/glubschiii
 @since 1.0.5
 */
public class ResetCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 1) {
            switch (args[0].toLowerCase()) {
                case "confirm":
                    //TODO: Welt richtig zurücksetzen
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.kickPlayer("Server restart");
                    }
                    try {
                        Config.set("reset.confirm", Boolean.valueOf(true));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "restart");
                    /*List<World> worlds = new ArrayList<>(Bukkit.getServer().getWorlds());
                    for (World w : worlds) {
                        w.setKeepSpawnInMemory(false);
                        Bukkit.unloadWorld(w, false);
                        w.getWorldFolder().delete();
                        Bukkit.getConsoleSender().sendMessage(w + "Wurde gelöscht!");
                    }
                    Bukkit.createWorld(new WorldCreator("world"));
                    Bukkit.createWorld(new WorldCreator("world_nether"));
                    Bukkit.createWorld(new WorldCreator("world_the_end"));
                    Bukkit.reload();*/
            }
        } else {
            sendUsage(sender);
        }


        return false;
    }
    private void sendUsage(CommandSender sender) {
        sender.sendMessage(ChatColor.DARK_RED.toString() + ChatColor.BOLD + "Möchtest du die Welt wirklich" +
                " ZURÜCKSETZEN? Bestätige mit /reset confirm");
    }
}
