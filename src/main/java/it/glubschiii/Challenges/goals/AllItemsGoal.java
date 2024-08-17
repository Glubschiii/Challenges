package it.glubschiii.Challenges.goals;

import it.glubschiii.Challenges.Main;
import it.glubschiii.Challenges.timer.Timer;
import it.glubschiii.Challenges.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static it.glubschiii.Challenges.utils.Config.config;
import static it.glubschiii.Challenges.utils.MainInventoryManager.*;

//TODO: Inventar mit /allitems machen (Fehlende Blöcke - Derzeit Item (Mit crafting recipe wie auf Gomme) - bereits gesammelte Bläcke (Mit Zeitangabe) - etc)

/** @author Glubschiii | https://github.com/glubschiii
 @since 1.0.8
 */

public class AllItemsGoal implements Listener, CommandExecutor {
    public static BossBar bossBar;
    public static Material current;
    private static List<String> materials;
    private static int size;
    private static int sizeProgress;

    /*
    * Set of materials to be excluded from list
     */
    private static final Set<Material> excludedMaterials = new HashSet<>(Arrays.asList(
            Material.AIR,
            Material.POTTED_CORNFLOWER,
            Material.LIGHT_GRAY_WALL_BANNER,
            Material.MANGROVE_WALL_HANGING_SIGN,
            Material.TORCHFLOWER_CROP,
            Material.END_GATEWAY,
            Material.POTTED_BLUE_ORCHID,
            Material.SOUL_WALL_TORCH,
            Material.CHERRY_WALL_HANGING_SIGN,
            Material.FIRE_CORAL_WALL_FAN,
            Material.SPRUCE_WALL_SIGN,
            Material.POTTED_RED_TULIP,
            Material.POTTED_ORANGE_TULIP,
            Material.POTTED_ACACIA_SAPLING,
            Material.DARK_OAK_WALL_SIGN,
            Material.BAMBOO_WALL_SIGN,
            Material.COCOA,
            Material.GRAY_CANDLE_CAKE,
            Material.CARROTS,
            Material.JUNGLE_WALL_SIGN,
            Material.SOUL_FIRE,
            Material.POTTED_BIRCH_SAPLING,
            Material.MOVING_PISTON,
            Material.CANDLE_CAKE,
            Material.OAK_WALL_HANGING_SIGN,
            Material.DRAGON_WALL_HEAD,
            Material.WITHER_SKELETON_WALL_SKULL,
            Material.POTTED_CRIMSON_ROOTS,
            Material.POTTED_FLOWERING_AZALEA_BUSH,
            Material.LIGHT_GRAY_CANDLE_CAKE,
            Material.ACACIA_WALL_HANGING_SIGN,
            Material.BLACK_WALL_BANNER,
            Material.POTTED_FERN,
            Material.MAGENTA_WALL_BANNER,
            Material.TALL_SEAGRASS,
            Material.LAVA_CAULDRON,
            Material.BRAIN_CORAL_WALL_FAN,
            Material.POTTED_CRIMSON_FUNGUS,
            Material.POTTED_OXEYE_DAISY,
            Material.REDSTONE_WIRE,
            Material.POTTED_DANDELION,
            Material.POTTED_WARPED_FUNGUS,
            Material.PINK_WALL_BANNER,
            Material.POTTED_AZURE_BLUET,
            Material.ZOMBIE_WALL_HEAD,
            Material.RED_WALL_BANNER,
            Material.ATTACHED_PUMPKIN_STEM,
            Material.LIGHT_BLUE_CANDLE_CAKE,
            Material.POTTED_DARK_OAK_SAPLING,
            Material.POTTED_RED_MUSHROOM,
            Material.WARPED_WALL_SIGN,
            Material.YELLOW_CANDLE_CAKE,
            Material.POTTED_BAMBOO,
            Material.POTTED_ALLIUM,
            Material.PITCHER_CROP,
            Material.PURPLE_WALL_BANNER,
            Material.CAVE_VINES,
            Material.VOID_AIR,
            Material.WEEPING_VINES_PLANT,
            Material.MELON_STEM,
            Material.MAGENTA_CANDLE_CAKE,
            Material.POTTED_LILY_OF_THE_VALLEY,
            Material.BLUE_WALL_BANNER,
            Material.POTTED_PINK_TULIP,
            Material.BROWN_CANDLE_CAKE,
            Material.CAVE_AIR,
            Material.WHITE_CANDLE_CAKE,
            Material.ORANGE_CANDLE_CAKE,
            Material.SWEET_BERRY_BUSH,
            Material.FIRE,
            Material.POTTED_BROWN_MUSHROOM,
            Material.PISTON_HEAD,
            Material.PINK_CANDLE_CAKE,
            Material.WATER,
            Material.BAMBOO_WALL_HANGING_SIGN,
            Material.TRIPWIRE,
            Material.CRIMSON_WALL_HANGING_SIGN,
            Material.OAK_WALL_SIGN,
            Material.YELLOW_WALL_BANNER,
            Material.GREEN_WALL_BANNER,
            Material.LAVA,
            Material.JUNGLE_WALL_HANGING_SIGN,
            Material.WARPED_WALL_HANGING_SIGN,
            Material.POTTED_DEAD_BUSH,
            Material.PUMPKIN_STEM,
            Material.POTTED_JUNGLE_SAPLING,
            Material.GRAY_WALL_BANNER,
            Material.CAVE_VINES_PLANT,
            Material.DARK_OAK_WALL_HANGING_SIGN,
            Material.LIME_CANDLE_CAKE,
            Material.CRIMSON_WALL_SIGN,
            Material.ORANGE_WALL_BANNER,
            Material.CHERRY_WALL_SIGN,
            Material.GREEN_CANDLE_CAKE,
            Material.HORN_CORAL_WALL_FAN,
            Material.KELP_PLANT,
            Material.POTTED_CACTUS,
            Material.POTTED_AZALEA_BUSH,
            Material.POTTED_OAK_SAPLING,
            Material.END_PORTAL,
            Material.RED_CANDLE_CAKE,
            Material.DEAD_BUBBLE_CORAL_WALL_FAN,
            Material.SKELETON_WALL_SKULL,
            Material.TWISTING_VINES_PLANT,
            Material.LIGHT_BLUE_WALL_BANNER,
            Material.FROSTED_ICE,
            Material.LIME_WALL_BANNER,
            Material.PIGLIN_WALL_HEAD,
            Material.POTTED_POPPY,
            Material.ACACIA_WALL_SIGN,
            Material.BUBBLE_CORAL_WALL_FAN,
            Material.WHITE_WALL_BANNER,
            Material.TUBE_CORAL_WALL_FAN,
            Material.BIRCH_WALL_SIGN,
            Material.BLUE_CANDLE_CAKE,
            Material.POTTED_MANGROVE_PROPAGULE,
            Material.PURPLE_CANDLE_CAKE,
            Material.BIRCH_WALL_HANGING_SIGN,
            Material.WATER_CAULDRON,
            Material.POWDER_SNOW_CAULDRON,
            Material.DEAD_TUBE_CORAL_WALL_FAN,
            Material.BUBBLE_COLUMN,
            Material.ATTACHED_MELON_STEM,
            Material.POTTED_WARPED_ROOTS,
            Material.CYAN_CANDLE_CAKE,
            Material.DEAD_FIRE_CORAL_WALL_FAN,
            Material.WALL_TORCH,
            Material.CYAN_WALL_BANNER,
            Material.PLAYER_WALL_HEAD,
            Material.BIG_DRIPLEAF_STEM,
            Material.POTTED_WITHER_ROSE,
            Material.SPRUCE_WALL_HANGING_SIGN,
            Material.POWDER_SNOW,
            Material.REDSTONE_WALL_TORCH,
            Material.BROWN_WALL_BANNER,
            Material.BLACK_CANDLE_CAKE,
            Material.POTATOES,
            Material.NETHER_PORTAL,
            Material.POTTED_SPRUCE_SAPLING,
            Material.BEETROOTS,
            Material.DEAD_BRAIN_CORAL_WALL_FAN,
            Material.POTTED_WHITE_TULIP,
            Material.BAMBOO_SAPLING,
            Material.DEAD_HORN_CORAL_WALL_FAN,
            Material.CREEPER_WALL_HEAD,
            Material.POTTED_TORCHFLOWER,
            Material.MANGROVE_WALL_SIGN,
            Material.POTTED_CHERRY_SAPLING
    ));


    /*
    * Method that is executed only at the start of the challenge
     */
    public static void allItems() throws IOException {
        // Use streams to filter out excluded materials and collect the remaining ones
        materials = new ArrayList<String>();
        for (Material material : Material.values()) {
            if (!excludedMaterials.contains(material)) {
                materials.add(String.valueOf(material));
            }
        }

        size = materials.size();

        Collections.shuffle(materials);

        Config.set("allitems.items", materials);
        Config.set("allitems.progress", 0);
        sizeProgress = 0;
        Config.set("allitems.size", size);
    }

    /*
     * Method executed at the start of the challenge (where boolean delete is false) and executed each time an item is collected (where boolean delete is true)
     */
    public static void updateItems(boolean delete) throws IOException {
        if (delete) {
            for (Player all : Bukkit.getOnlinePlayers()) {
                bossBar.removePlayer(all);
            }
            materials.remove(0);
        }
        if (materials.size() != 0) {
            current = Material.valueOf(materials.get(0));
        }
        if (materials.size() != 0) {
            bossBar = Bukkit.createBossBar(ChatColor.GRAY + "Item" + ChatColor.DARK_GRAY + " » " + ChatColor.WHITE + currentMessage(String.valueOf(current)) + " #"
                    + sizeProgress + "/" + size, BarColor.WHITE, BarStyle.SOLID);       //TODO: BossBar Progress geht immer Stückweise höher machen?
        } else {
            bossBar = Bukkit.createBossBar(ChatColor.GREEN + "Alle Items wurden registriert! (" + size + "/" + size + ")", BarColor.GREEN, BarStyle.SOLID);
        }
        for (Player all : Bukkit.getOnlinePlayers()) {
            bossBar.addPlayer(all);
        }
        Config.set("allitems.current", current.toString());
        Config.set("allitems.items", materials);
        Config.set("allitems.progress", sizeProgress);
        if (materials.size() != 0) {
            currentItem(current, currentMessage(String.valueOf(current)));          //TODO: Überprüfen ob bei jedem direkt das geöffnete! Inv geupdated wird
        } else {
            currentItem(Material.OAK_SIGN, ChatColor.RED + "Die Alle Items Herausforderung wurde bereits absolviert!");
        }
    }

    /*
    * Query whether the correct item was collected
     */
    @EventHandler
    public void onPickUp(PlayerPickupItemEvent event) throws IOException {         //TODO: Auch beim aus Chest nehmen usw. abfragen
        Timer timer = Main.getInstance().getTimer();
        Item item = event.getItem();
        Player player = event.getPlayer();
        if (item.getItemStack().getType().equals(current) && Timer.isRunning()) {
            sizeProgress += 1;
            for (Player all : Bukkit.getOnlinePlayers()) {
                if (!(sizeProgress == size)) {
                    all.sendMessage(ChatColor.GRAY + "Folgendes Item wurde registriert: " + ChatColor.YELLOW.toString() + ChatColor.BOLD + currentMessage(String.valueOf(current))
                            + ChatColor.RESET + ChatColor.GRAY + ". Neues Item: " + ChatColor.GREEN.toString() + ChatColor.BOLD + currentMessage(materials.get(1)) + ChatColor.RESET
                            + ChatColor.DARK_GRAY + " (" + ChatColor.GRAY + "Es fehlen noch " + ChatColor.WHITE.toString() + ChatColor.BOLD + (size - sizeProgress) + ChatColor.RESET +
                            ChatColor.GRAY + " Items" + ChatColor.DARK_GRAY + ")");
                    all.sendTitle("#" + sizeProgress, "", 10, 17, 10);
                } else {            //TODO: Funktionalität überprüfen
                    all.sendMessage(ChatColor.GRAY + "Folgendes Item wurde registriert: " + ChatColor.YELLOW.toString() + ChatColor.BOLD + currentMessage(String.valueOf(current))
                            + ChatColor.RESET + ChatColor.GRAY + ". Du hast alle" + ChatColor.GREEN.toString() + ChatColor.BOLD
                            + " Items " + ChatColor.GRAY + "gesammelt!");
                    all.sendTitle(ChatColor.UNDERLINE + "FERTIG!", "", 10, 60, 30);
                    ElderGuardianKillGoal.challengeCompleted(timer);
                }
            }
            updateItems(true);
        }
    }

    /*
    * This event is triggered when a new version of Paper Spigot is released, and it is necessary to verify whether the goal still works as previous.
     */
    /*@EventHandler
    public void onBlockBreak(BlockBreakEvent event) throws IOException {
        Player player = event.getPlayer();
        Bukkit.dispatchCommand(player, "allitems skip");
    }*/

    /*
    * /allitems command, which has the values "overview" and "skip".
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Timer timer = Main.getInstance().getTimer();
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 1) {
                switch(args[0].toLowerCase()) {
                    /*
                    * An inventory opens listing the items still being searched for, the item currently being searched for, and the items already collected.
                     */
                    case "overview":
                        if (Config.getBoolean("goals.allitems").booleanValue()) {
                            /*if(allItemsOverviewInv.getItem(22) == null || Objects.requireNonNull(allItemsOverviewInv.getItem(22)).getType() == Material.AIR) {
                                allItemsOverviewInv.setItem(22, allItemsDefault);
                            }*/
                            ItemStack itemAt22 = allItemsOverviewInv.getItem(22);
                            if (itemAt22 == null || excludedMaterials.contains(itemAt22.getType())) {
                                allItemsOverviewInv.setItem(22, allItemsDefault);
                            }
                            player.openInventory(allItemsOverviewInv);
                        } else {
                            player.sendMessage(ChatColor.RED + "Die Alle Items Herausforderung ist derzeit deaktiviert!");
                        }
                        break;
                    /*
                     * If you can't get an item in Survival, you can use it to skip it
                     */
                    case "skip":
                        if(player.hasPermission("challenges.allitems")) {
                            if (Config.getBoolean("goals.allitems").booleanValue()) {
                                if (materials.size() != 0) {
                                    if (Timer.isRunning()) {
                                        sizeProgress += 1;
                                        for (Player all : Bukkit.getOnlinePlayers()) {
                                            if (!(sizeProgress == size)) {
                                                all.sendMessage(ChatColor.GRAY + "Item geskippt. Neues Item: " + ChatColor.GREEN.toString()
                                                        + ChatColor.BOLD + currentMessage(materials.get(1)) + ChatColor.RESET + ChatColor.DARK_GRAY + " ("
                                                        + ChatColor.GRAY + "Es fehlen noch " + ChatColor.WHITE.toString() + ChatColor.BOLD
                                                        + (size - sizeProgress) + ChatColor.RESET + ChatColor.GRAY + " Items" + ChatColor.DARK_GRAY + ")");
                                                all.sendTitle("#" + sizeProgress, "", 10, 17, 10);
                                            } else {        //TODO: Funktionalität überprüfen
                                                all.sendMessage(ChatColor.GRAY + "Item geskippt. Du hast alle" + ChatColor.GREEN.toString() + ChatColor.BOLD
                                                        + " Items " + ChatColor.GRAY + "gesammelt!");
                                                all.sendTitle(ChatColor.UNDERLINE + "FERTIG!", "", 10, 60, 30);
                                                ElderGuardianKillGoal.challengeCompleted(timer);
                                            }
                                        }
                                        try {
                                            updateItems(true);
                                        } catch (IOException e) {
                                            throw new RuntimeException(e);
                                        }
                                    } else {
                                        player.sendMessage(ChatColor.RED + "Der Timer ist noch pausiert!");
                                    }
                                } else {
                                    player.sendMessage(ChatColor.RED + "Die Alle Items Herausforderung wurde bereits absolviert!");
                                }
                            } else {
                                player.sendMessage(ChatColor.RED + "Die Alle Items Herausforderung ist derzeit deaktiviert!");
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + "Dazu hast du keine Rechte!");
                        }
                        break;
                    default:
                      sendErrorUsage(player);
                }
            } else {
                sendErrorUsage(player);
            }
        }
        return false;
    }

    private void sendErrorUsage(Player player) {
        player.sendMessage(ChatColor.GREEN + "Verwendung: " + ChatColor.WHITE + ChatColor.BOLD +
                "/allitems <overview|skip>");
    }


    public static void deactivateAllItems() throws IOException {
        if (bossBar != null) {
            bossBar.removeAll();
            bossBar.setVisible(false);
            bossBar = null;
        }
        Config.set("allitems", null);
    }

    private static String currentMessage(String current) {
        String[] words = current.split("_");
        for(short i = 0; i < words.length; i++) {
            String firstLetter = words[i].substring(0, 1);
            String restOfWord = words[i].substring(1);
            words[i] = firstLetter.toUpperCase() + restOfWord.toLowerCase();
        }
        return String.join(" ", words);
    }

    public static void loadConfig() {
        ConfigurationSection section = config.getConfigurationSection("allitems");
        if(section != null) {
            materials = section.getStringList("items");
            size = section.getInt("size");
            current = Material.valueOf(section.getString("current"));
            sizeProgress = section.getInt("progress");
        }
    }
}
