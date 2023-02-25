package it.glubschiii.Challenges.utils;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;

/** @author Glubschiii | https://github.com/glubschiii
 @since 1.0.1
 */
public class Config {

    private static File file;
    public static YamlConfiguration config;

    public Config() {
        File dir = new File("./plugins/Challenges/");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        file = new File(dir, "config.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        config = YamlConfiguration.loadConfiguration(file);
    }

    public static boolean contains(String path) {
        return config.contains(path);
    }

    public static void set(String path, Object value) throws IOException {
        config.set(path, value);
        config.save(file);
    }

    public static Object get(String path) {
        if(!contains(path)) {
            return null;
        }
        return config.get(path);
    }

    public static int getInt(String path) {
        return config.getInt(path);
    }

    public static String getString(String path) {
        return config.getString(path);
    }

    public static Double getDouble(String path) {
        return config.getDouble(path);
    }

    public static ArrayList getArrayList(String path) {
        return (ArrayList)config.getList(path);
    }
    public static Boolean getBoolean(String path) {
        return config.getBoolean(path);
    }

    public static File getFile() {
        return file;
    }

}
