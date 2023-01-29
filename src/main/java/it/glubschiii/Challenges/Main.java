package it.glubschiii.Challenges;

import it.glubschiii.Challenges.commands.*;
import it.glubschiii.Challenges.listeners.*;
import it.glubschiii.Challenges.commands.*;
import it.glubschiii.Challenges.listeners.*;
import it.glubschiii.Challenges.timer.Timer;
import it.glubschiii.Challenges.utils.Config;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

    /** @author Glubschiii | https://github.com/glubschiii
        @since 1.0
     */
public final class Main extends JavaPlugin {

    public static Main instance;

    private Timer timer;

    private HashMap<Player, ChatColor> color = new HashMap<>();
    private HashMap<Player, Integer> stage = new HashMap<Player, Integer>();

    public ChatColor getColor(Player p) {
        return color.get(p);
    }
    public void setColor(Player p, ChatColor color) {
        this.color.put(p, color);
    }

    public static boolean deletedFolders = false;

    public void onLoad() {
        Config config = new Config();
        instance = this;
        if(Config.contains("reset.confirm") && Config.getBoolean("reset.confirm").booleanValue()) {
            deleteWorlds();
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "Wurde gelöscht!");
            try {
                Config.set("reset.confirm", Boolean.valueOf(false));
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "restart");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            config.clearConfig();
            //TODO: Config zurücksetzen überprüfen...
        }
        deletedFolders = true;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Challenges Plugin geladen!");

        PluginManager manager = Bukkit.getPluginManager();
        manager.registerEvents(new JoinListener(), this);
        manager.registerEvents(new QuitListener(), this);
        manager.registerEvents(new DeathEvent(), this);
        manager.registerEvents(new EntityDamageEvent(), this);
        manager.registerEvents(new JumpListener(), this);
        manager.registerEvents(new SneakListener(), this);
        manager.registerEvents(new SettingsMenuInventoryClickEvent(), this);
        manager.registerEvents(new SpielregelnMenuInventoryClickEvent(), this);
        manager.registerEvents(new PickupItemEvent(), this);
        manager.registerEvents(new DragonKillEvent(), this);

        getCommand("timer").setExecutor(new TimerCommand());
        getCommand("backpack").setExecutor(new BackpackCommand());
        getCommand("heal").setExecutor(new HealCommand());
        getCommand("settings").setExecutor(new SettingsCommand());
        getCommand("reset").setExecutor(new ResetCommand());
        getCommand("gm").setExecutor(new GamemodeCommand());


        if (Config.get("timer") != null) {
            timer = new Timer(false, (Integer) Config.get("timer"));
        } else {
            timer = new Timer(false, 0);
        }

        if(Config.get("timer-direction") != null) {
            Config.get("timer-direction");
        } else {
            try {
                Config.set("timer-direction", "up");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if(Config.get("command.backpack.backpack-contents") != null) {
            Config.get("command.backpack.backpack-contents");
        }

        if(Config.get("difficulty") != null) {
            Config.get("difficulty");
        } else {
            try {
                Config.set("difficulty", "HARD");
                Bukkit.getWorlds().forEach(world -> world.setDifficulty(Difficulty.HARD));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if(Config.get("regeneration-status") != null) {
            Config.get("regeneration-status");
        } else {
            try {
                Config.set("regeneration-status", 0);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if(getConfig().getInt("regeneration-status") == 0) {
            Bukkit.getWorlds().forEach(world -> world.setGameRule(GameRule.NATURAL_REGENERATION, true));
        } else {
            Bukkit.getWorlds().forEach(world -> world.setGameRule(GameRule.NATURAL_REGENERATION, false));
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        try {
            Config.set("timer", Timer.getTime()/5);
            if(Config.get("timer-direction") == "down") {
                Config.set("timer-direction", "down");
            } else {
                Config.set("timer-direction", "up");
            }
            Config.set("difficulty", Bukkit.getWorld("world").getDifficulty().toString()); //TODO: Speichern überprüfen, noch hinzufügen beim ändern/enablen
            Config.set("regeneration-status", SettingsMenuInventoryClickEvent.getStatus());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Main getInstance() {
        return instance;
    }


    public Timer getTimer() {
        return timer;
    }

    public void deleteWorlds() {
        for(String w : List.of("world", "world_nether", "world_the_end")) {
            try {
                Files.walk(Paths.get(w)).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(file -> {
                    if(file.delete()) getLogger().info("File " + file.getName() + " successfully deleted!");
                });
            } catch (IOException ignored) {
            }
        }
    }

}
