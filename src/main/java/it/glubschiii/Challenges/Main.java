package it.glubschiii.Challenges;

import it.glubschiii.Challenges.challenges.NoJumpChallenge;
import it.glubschiii.Challenges.challenges.NoSneakChallenge;
import it.glubschiii.Challenges.commands.*;
import it.glubschiii.Challenges.gamerules.DifficultyGamerule;
import it.glubschiii.Challenges.gamerules.RegenerationGamerule;
import it.glubschiii.Challenges.goals.*;
import it.glubschiii.Challenges.listeners.*;
import it.glubschiii.Challenges.timer.Timer;
import it.glubschiii.Challenges.timer.TimerCommand;
import it.glubschiii.Challenges.utils.Config;
import it.glubschiii.Challenges.utils.MainInventoryManager;
import it.glubschiii.Challenges.timer.PreTimer;
import it.glubschiii.Challenges.utils.TablistManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wither;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static it.glubschiii.Challenges.goals.AllItemsGoal.*;

/** @author Glubschiii | https://github.com/glubschiii
        @since 1.0
     */
public final class Main extends JavaPlugin {

    //TODO: Prefixe ändern / neues Design
    //TODO: /reset confirm fixxen!!
    //TODO: TabCompleter für alle Commands machen

    public static Main instance;

    private Timer timer;
    private TablistManager tablistManager;

    private HashMap<Player, ChatColor> color = new HashMap<>();
    private HashMap<Player, Integer> stage = new HashMap<Player, Integer>();

    public ChatColor getColor(Player p) {
        return color.get(p);
    }
    public void setColor(Player p, ChatColor color) {
        this.color.put(p, color);
    }

    public static boolean deletedFolders = false;

    //TODO: Wenn man einen Wert manuell in der Config ändert dann speichert es das nicht in das custom inv
    //TODO: Eng Übersetzung machen

    public void onLoad() {
        Config config = new Config();
        instance = this;
        if(Config.contains("reset.confirm") && Config.getBoolean("reset.confirm").booleanValue()) {
            deleteWorlds();
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "Welten gelöscht! Server wird nochmal restartet!");
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "Welten gelöscht! Server wird nochmal restartet!");
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "Welten gelöscht! Server wird nochmal restartet!");
            try {
                Config.set("reset.confirm", Boolean.valueOf(false));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            deletedFolders = true;
            Config.delete();
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "restart");
        }
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Challenges Plugin geladen!");

        PluginManager manager = Bukkit.getPluginManager();
        manager.registerEvents(new JoinEvent(), this);
        manager.registerEvents(new PreTimer(), this);
        manager.registerEvents(new QuitEvent(), this);
        manager.registerEvents(new DeathEvent(), this);
        manager.registerEvents(new EntityDamageEvent(), this);
        //manager.registerEvents(new NoJumpChallenge(), this);
        //manager.registerEvents(new NoSneakChallenge(), this);
        manager.registerEvents(new DifficultyGamerule(), this);
        manager.registerEvents(new RegenerationGamerule(), this);
        //manager.registerEvents(new PickupItemEvent(), this);              //TODO: Wäre für ABC-Challenge damals gedacht gewesen
        manager.registerEvents(new DragonKillGoal(), this);
        manager.registerEvents(new ElderGuardianKillGoal(), this);
        manager.registerEvents(new WitherKillGoal(), this);
        manager.registerEvents(new WardenKillGoal(), this);
        manager.registerEvents(new AllItemsGoal(), this);
        manager.registerEvents(new MainInventoryManager(), this);
        manager.registerEvents(new ChatEvent(), this);

        getCommand("timer").setExecutor(new TimerCommand());
        getCommand("backpack").setExecutor(new BackpackCommand());
        getCommand("heal").setExecutor(new HealCommand());
        getCommand("settings").setExecutor(new SettingsCommand());
        getCommand("reset").setExecutor(new ResetCommand());
        getCommand("gm").setExecutor(new GamemodeCommand());
        getCommand("allitems").setExecutor(new AllItemsGoal());

        tablistManager = new TablistManager();

        try {
            MainInventoryManager.puttingItems();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (Config.get("timer") != null) {
            timer = new Timer(false, (Integer) Config.get("timer")*5);
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
            Config.get("command.backpack.backpack-contents");           //TODO: Funktioniert nicht
        }

        if(Config.get("difficulty") != null) {
            Config.get("difficulty");
        } else {
            try {
                Config.set("difficulty", "HARD");       //TODO: Wenn es den path "difficulty" in der Config nicht gibt, dann stellt er ihn nicht auf "HARD" wie diese Zeile besagt, sondern auf "EASY"???? WTF?
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

        if(getConfig().contains("goals.enderdragon") && Config.getBoolean("goals.enderdragon").booleanValue()) {
            Config.getBoolean("goals.enderdragon").booleanValue();
        } else if(!getConfig().contains("goals.elderguardian") && !Config.getBoolean("goals.elderguardian").booleanValue() &&
                !getConfig().contains("goals.wither") && !Config.getBoolean("goals.wither").booleanValue() &&
                !getConfig().contains("goals.warden") && !Config.getBoolean("goals.warden").booleanValue()) {
            try {
                Config.set("goals.enderdragon", Boolean.valueOf(true));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if(getConfig().contains("goals.elderguardian") && Config.getBoolean("goals.elderguardian").booleanValue()) {
            Config.getBoolean("goals.elderguardian").booleanValue();
        }
        if(getConfig().contains("goals.wither") && Config.getBoolean("goals.wither").booleanValue()) {
            Config.getBoolean("goals.wither").booleanValue();
        }
        if(getConfig().contains("goals.warden") && Config.getBoolean("goals.warden").booleanValue()) {
            Config.getBoolean("goals.warden").booleanValue();
        }
        if(getConfig().contains("goals.allitems") && Config.getBoolean("goals.allitems").booleanValue()) {
            Config.getBoolean("goals.allitems").booleanValue();
            loadConfig();
            try {
                updateItems(false);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if(Config.get("allitems.items") != null) {
            Config.get("allitems.items");
        }
        if(Config.get("allitems.current") != null) {
            Config.get("allitems.current");
        }
        if(Config.get("allitems.progress") != null) {
            Config.get("allitems.progress");
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        try {
            Config.set("timer", Timer.getTime()/5);
            /*if(Config.get("timer-direction") == "down") {
                Config.set("timer-direction", "down");
            } else {
                Config.set("timer-direction", "up");
            }*/
            Config.set("difficulty", Bukkit.getWorld("world").getDifficulty().toString()); //TODO: Speichern überprüfen, noch hinzufügen beim ändern/enablen
            Config.set("regeneration-status", RegenerationGamerule.getStatus());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(bossBar != null) {
            bossBar.hide();
        }
    }

    public static Main getInstance() {
        return instance;
    }


    public Timer getTimer() {
        return timer;
    }

    public TablistManager getTablistManager() {
        return tablistManager;
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
